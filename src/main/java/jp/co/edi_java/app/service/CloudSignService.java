package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.SapOrderDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.sap.SapApi;

@Service
@Scope("request")
public class CloudSignService {

	@Autowired
    public OrderService orderService;

	@Autowired
    public MailService mailService;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	public static String FILE_FLG_OFF = "0";
	public static String FILE_FLG_ON = "1";
	public static String FILE_FLG_REJECT = "2";

	private static String STG_FLG;
	private static String STG_FLG_ON = "1";

	private CloudSignService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	//クラウドサイン連携情報の取得
	public TCloudSignEntity select(String fileId) {
		return tCloudSignDao.select(fileId);
	}

	//クラウドサイン連携情報のリスト取得
	public List<TCloudSignEntity> selectFileIdList(String formType) {
		return tCloudSignDao.selectNotAgreeList(formType);
	}

	//クラウドサインリマインド対象のリスト取得
	public List<TCloudSignEntity> selectRemindList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//本日の日付を取得
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		String applicationDateFrom = sdf.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String applicationDateTo = sdf.format(cal.getTime());
		return tCloudSignDao.selectRemindList(applicationDateFrom, applicationDateTo);
	}

	//合意日の更新
	public void update(String fileId) {
		TCloudSignEntity entity = select(fileId);
		entity.setExecutionDate(new Timestamp(System.currentTimeMillis()));
		tCloudSignDao.update(entity);
	}

	//帳票の種類に応じて発注情報の更新
	public void updateOrderAgree(TOrderEntity entity, String fileId, String formType) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		//発注請書の場合
		if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			entity.setFileIdOrder(fileId);
			entity.setConfirmationFlg(FILE_FLG_ON);
			entity.setConfirmationAgreeDate(time);
		}
		//発注取消合意書の場合
		else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
			entity.setFileIdCancel(fileId);
			entity.setCancelFlg(FILE_FLG_ON);
			entity.setCancelAgreeDate(time);
		}
		tOrderDao.update(entity);
	}

	//帳票の種類に応じて申請日の初期化
	public void updateOrderNotAgree(TOrderEntity entity, String formType) {
		//発注請書の場合
		if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			entity.setConfirmationFlg(FILE_FLG_REJECT);
			entity.setConfirmationRequestDate(null);
		}
		//発注取消合意書の場合
		else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
			entity.setCancelFlg(FILE_FLG_REJECT);
			entity.setCancelRequestDate(null);
		}
		tOrderDao.update(entity);
	}

	//クラウドサイン連携情報の削除
	public void delete(String fileId) {
		TCloudSignEntity entity = select(fileId);
		entity.setDeleteUser("system");
		entity.setDeleteDate(new Timestamp(System.currentTimeMillis()));
		entity.setDeleteFlg("1");
		tCloudSignDao.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> chkFileList(List<TCloudSignEntity> fileList, String formType) {
		int agreeCount = 0;
		int dismissalCount = 0;
		for (TCloudSignEntity tCloudSignEntity : fileList) {

			//発注情報の取得
			String orderNumber = tCloudSignEntity.getOrderNumber();
			TOrderEntity order = tOrderDao.select(orderNumber);

			//連携書類情報の取得
			String documentId = tCloudSignEntity.getFileId();
			Map<String, Object> ret = CloudSignApi.getDocumentId(documentId);

			//ステータス 1…保留、2…同意、3…却下
			String status = ret.get("status").toString();

			//同意の場合
			if(status.equals(CloudSignApi.STATUS_AGREE)) {

				//連携テーブルの更新
				update(documentId);

				//ファイルを取得しdriveと連携
				List<Map<String, Object>> files = (List<Map<String, Object>>)ret.get("files");
				String fileId = files.get(0).get("id").toString();
				String fileName = fileId + ".pdf";
				String filePath = CloudSignApi.getFile(documentId, fileId, fileName);
				String fileNo = "";
				if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
					fileNo = FileApi.FILE_NO_CONFRIMATION;
				}else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
					fileNo = FileApi.FILE_NO_CANCEL;
				}
				Map<String, Object> postRet = FileApi.postFile(order.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, fileNo, null, filePath, fileName, "pdf");

				//発注テーブルの更新
				String driveFileId = postRet.get("file_id").toString();
				updateOrderAgree(order, driveFileId, formType);

				sendConfirmationAgreeMail(orderNumber);

				agreeCount++;

			}
			//却下の場合
			else if(status.equals(CloudSignApi.STATUS_DISMISSAL)) {

				//連携テーブルの更新（削除）
				delete(documentId);
				//発注テーブルの更新（申請前に戻す）
				updateOrderNotAgree(order, formType);

				dismissalCount++;

			}
			//確認中の場合はなにもしない
		}
		Map<String, Object> count = new HashMap<>();
		count.put("agreeCount", agreeCount);
		count.put("dismissalCount", dismissalCount);
		return count;
	}

	//発注請書受入時にメールを送信
	public void sendConfirmationAgreeMail(String orderNumber) {
		//発注詳細取得（SAP）
		Map<String, Object> data = SapApi.orderDetail(orderNumber);
		//基本情報取得
		SapOrderDto dto = orderService.getHeader(data, orderNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(dto.getOrderInfo().getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//CC
		String cc = null;
		if(!STG_FLG.equals(STG_FLG_ON)) {
			cc = "jimu-" + eigyousyo.getEigyousyoCode() + "@tamahome.jp";
		}else {
			cc = MailService.STG_CC_MAIL;
		}

		//メール送信
		mailService.sendMailConfirmationAgree(syain.getSyainMail(), cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), dto.getGyousyaName(), orderNumber);
	}

	public int remindList(List<TCloudSignEntity> fileList) {
		int count = 0;
		for (TCloudSignEntity tCloudSignEntity : fileList) {
			//再送する
			CloudSignApi.postDocumentId(tCloudSignEntity.getFileId());
			count++;
		}
		return count;
	}

	public void remind(String documentId) {
		//再送する
		CloudSignApi.postDocumentId(documentId);
	}

}
