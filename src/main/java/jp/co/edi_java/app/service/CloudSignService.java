package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.file.FileApi;

@Service
@Scope("request")
public class CloudSignService {

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
    public TOrderDao tOrderDao;

	public static String FILE_FLG_OFF = "0";
	public static String FILE_FLG_ON = "1";
	public static String FILE_FLG_REJECT = "2";

	//クラウドサイン連携情報の取得
	public TCloudSignEntity select(String fileId) {
		return tCloudSignDao.select(fileId);
	}

	//クラウドサイン連携情報のリスト取得
	public List<TCloudSignEntity> selectFileIdList(String formType) {
		return tCloudSignDao.selectNotAgreeList(formType);
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

}
