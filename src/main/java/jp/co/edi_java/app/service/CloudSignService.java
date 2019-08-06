package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

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
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		log.info("call chkFileList");
		for (TCloudSignEntity tCloudSignEntity : fileList) {
			//発注情報の取得
			String orderNumber = tCloudSignEntity.getOrderNumber();
			TOrderEntity order = tOrderDao.select(orderNumber);

			//連携書類情報の取得
			String documentId = tCloudSignEntity.getFileId();
			Map<String, Object> ret = CloudSignApi.getDocumentId(documentId);
			if (Objects.isNull(ret) || Objects.isNull(ret.get("status"))) {
				log.info("can not get documentId from cloudsign : " + orderNumber + " " + documentId);
				continue;
			}
			//ステータス 1…保留、2…同意、3…却下
			String status = ret.get("status").toString();

			//同意の場合
			if(status.equals(CloudSignApi.STATUS_AGREE)) {
				log.info("status is agree : " + orderNumber);
				//連携テーブルの更新
				update(documentId);

				String fileId = "";
				String fileName = "";
				String filePath = "";
				String fileNo = "";
				String driveFileId = "";

				if (Objects.isNull(ret.get("files"))) {
					log.info("can not get files from cloudsign: " + orderNumber + " " + documentId);
				} else {
					log.info("get files from cloudsign : " + orderNumber);
					//ファイルを取得しdriveと連携
					List<Map<String, Object>> files = (List<Map<String, Object>>)ret.get("files");
					if (Objects.nonNull(files) && !files.isEmpty()) {
						if (Objects.nonNull(files.get(0)) && Objects.nonNull(files.get(0).get("id"))) {
							log.info("cloudsign file id is not null: " + orderNumber);
							fileId = files.get(0).get("id").toString();
							fileName = fileId + ".pdf";
							filePath = CloudSignApi.getFile(documentId, fileId, fileName);

							if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
								fileNo = FileApi.FILE_NO_CONFRIMATION;
							}else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
								fileNo = FileApi.FILE_NO_CANCEL;
							}
							Map<String, Object> postRet = FileApi.postFile(order.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, fileNo, null, filePath, fileName, "pdf");
							log.info("postFile done: " + orderNumber);
							if (Objects.nonNull(postRet)) {
								// google drive file id
								driveFileId = postRet.get("file_id").toString();
								log.info("google drive file id is : " + driveFileId + ", order number is: " + orderNumber);
							} else {
								log.info("can not post files to google drive: " + orderNumber);
							}
						} else {
							log.info("cloudsign file id is null : " + orderNumber);
						}
					} else {
						log.info("cloudsign files is empty: " + orderNumber);
					}
				}
				//発注テーブルの更新
				updateOrderAgree(order, driveFileId, formType);
				//請書受領連携
				sendOrderNumber(order);
				// メール送信
				sendConfirmationAgreeMail(orderNumber,filePath,fileName);

				agreeCount++;

			}
			//却下の場合
			else if(status.equals(CloudSignApi.STATUS_DISMISSAL)) {
				log.info("status is dismissal : " + orderNumber);
				//連携テーブルの更新（削除）
				delete(documentId);
				//発注テーブルの更新（申請前に戻す）
				updateOrderNotAgree(order, formType);

				dismissalCount++;

			}
			//確認中の場合はなにもしない
			log.info("status is confirming : " + orderNumber);
		}
		Map<String, Object> count = new HashMap<>();
		count.put("agreeCount", agreeCount);
		count.put("dismissalCount", dismissalCount);
		return count;
	}

	//発注請書受入時にメールを送信
	public void sendConfirmationAgreeMail(String orderNumber, String filePath, String fileName) {

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
		String newFileName = "注文請書.pdf";
		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		if (Objects.nonNull(filePath)) {
			Map<String,String> fileMap = new HashMap<String,String>();
			fileMap.put("filePath", filePath);
			fileMap.put("fileName", newFileName);
			fileList.add(fileMap);
		}
		//メール送信
		mailService.sendMailConfirmationAgree(syain.getSyainMail(), cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), dto.getGyousyaName(), orderNumber, fileList);
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

	private void sendOrderNumber(TOrderEntity order) {
		String orderNumber = order.getOrderNumber();
		String koujiCode = order.getKoujiCode();
		String insertUser = order.getInsertUser();
		// sap 請書未受領一覧検索
		log.info("selectUkeshoJyuryou: " + koujiCode + " " + insertUser);
		Map<String, Object> nonJyuryouData = SapApi.selectUkeshoJyuryou(koujiCode, insertUser);
		//請書未受領一覧検索結果取得
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(nonJyuryouData);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
			return;
			//throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		List<Map<String, Object>> sapList = new ArrayList<>();
		Object sap = nonJyuryouData.get(SapApiConsts.PARAMS_KEY_T_E_04002);
		if(Objects.nonNull(sap)) {
			//1件より多い場合
			if(sap instanceof List) {
				sapList = (List<Map<String, Object>>)sap;
			}
			//1件の場合
			else {
				sapList.add((Map<String, Object>)sap);
			}
		}
		// 請書未受領更新
		for (Map<String, Object> sapObj : sapList) {
			//対象の発注番号と一致するものを更新
			if (Objects.nonNull(sapObj.get(SapApiConsts.PARAMS_ID_EBELN))) {
				if (sapObj.get(SapApiConsts.PARAMS_ID_EBELN).toString().equals(orderNumber)) {
					String lastUpdateDate = sapObj.get(SapApiConsts.PARAMS_ID_AEDAT).toString();
					String lastUpdateTime = sapObj.get(SapApiConsts.PARAMS_ID_AEZEIT).toString();
					log.info("before setOrderNumberByUkeshoJyuryou: " + koujiCode + " " + orderNumber + " " + lastUpdateDate + " " + lastUpdateTime);
					// Sapへ請書未受領データの排他制御用変更日付、変更時間がEnglishロケールフォーマットのため変換が必要
					Date updateDate = parseDateStringToDate(lastUpdateDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					Date updateTime = parseDateStringToDate(lastUpdateTime, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					if (Objects.nonNull(updateDate)) {
						// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
						lastUpdateDate = formatDateToString(updateDate, "yyyyMMdd", "JST");
					}
					if (Objects.nonNull(updateTime)) {
						// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
						lastUpdateTime = formatDateToString(updateTime, "HH:mm:ss", "JST");
					}
					log.info("setOrderNumberByUkeshoJyuryou: " + orderNumber + " " + lastUpdateDate + " " + lastUpdateTime);
					Map<String, Object> result = SapApi.setOrderNumberByUkeshoJyuryou(orderNumber, lastUpdateDate, lastUpdateTime);
					resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
					if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
						log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
						//throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
					}
					break;
				}
			}
		}
	}

	private Date parseDateStringToDate(String date, String format, Locale loc) {
		Date ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, loc);
			ret = sdf.parse(date);
		} catch (ParseException e) {
			log.info(e.getMessage());
		}
		return ret;
	}
	private String formatDateToString(Date date, String format, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(date);
	}

	/* 単発処理 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> refreshOrderReport(List<TCloudSignEntity> fileList, String formType) {
		int agreeCount = 0;

		for (TCloudSignEntity tCloudSignEntity : fileList) {
			//発注情報の取得
			String orderNumber = tCloudSignEntity.getOrderNumber();
			TOrderEntity order = tOrderDao.select(orderNumber);

			//連携書類情報の取得
			String documentId = tCloudSignEntity.getFileId();
			Map<String, Object> ret = CloudSignApi.getDocumentId(documentId);
			if (Objects.isNull(ret) || Objects.isNull(ret.get("status"))) {
				log.info("can not get documentId from cloudsign : " + orderNumber + " " + documentId);
				continue;
			}
			//ステータス 1…保留、2…同意、3…却下
			String status = ret.get("status").toString();

			//同意の場合
			if(status.equals(CloudSignApi.STATUS_AGREE)) {
				log.info("status is agree : " + orderNumber);

				String fileId = "";
				String fileName = "";
				String filePath = "";
				String fileNo = "";
				String driveFileId = "";

				if (Objects.isNull(ret.get("files"))) {
					log.info("can not get files from cloudsign: " + orderNumber + " " + documentId);
				} else {
					log.info("get files from cloudsign : " + orderNumber);
					//ファイルを取得しdriveと連携
					List<Map<String, Object>> files = (List<Map<String, Object>>)ret.get("files");
					if (Objects.nonNull(files) && !files.isEmpty()) {
						if (Objects.nonNull(files.get(0)) && Objects.nonNull(files.get(0).get("id"))) {
							log.info("cloudsign file id is not null: " + orderNumber);
							fileId = files.get(0).get("id").toString();
							fileName = fileId + ".pdf";
							filePath = CloudSignApi.getFile(documentId, fileId, fileName);

							if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
								fileNo = FileApi.FILE_NO_CONFRIMATION;
							}else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
								fileNo = FileApi.FILE_NO_CANCEL;
							}
							Map<String, Object> postRet = FileApi.postFile(order.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, fileNo, null, filePath, fileName, "pdf");
							log.info("postFile done: " + orderNumber);
							if (Objects.nonNull(postRet)) {
								// google drive file id
								driveFileId = postRet.get("file_id").toString();
								log.info("google drive file id is : " + driveFileId + ", order number is: " + orderNumber);
							} else {
								log.info("can not post files to google drive: " + orderNumber);
							}
						} else {
							log.info("cloudsign file id is null : " + orderNumber);
						}
					} else {
						log.info("cloudsign files is empty: " + orderNumber);
					}
				}
				//発注テーブルの更新
				if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
					order.setFileIdOrder(driveFileId);
					order.setConfirmationFlg(FILE_FLG_ON);
					order.setConfirmationAgreeDate(tCloudSignEntity.getExecutionDate());
				}
				tOrderDao.update(order);

				//請書受領連携
				sendOrderNumber(order);

				agreeCount++;

			}
		}
		Map<String, Object> count = new HashMap<>();
		count.put("agreeCount", agreeCount);

		return count;
	}
}
