package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TExclusiveEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.ExclusiveForm;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
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

	//排他制御用（発注情報更新用）
	@Autowired
	public ExclusiveService exclusiveService;


	public static String FILE_FLG_OFF = "0";
	public static String FILE_FLG_ON = "1";
	public static String FILE_FLG_REJECT = "2";

	private static String STG_FLG;
	private static String STG_FLG_ON = "1";
	private static String TEMPORARY_SYAIN_CODE = "990000";

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
		cal.set(Calendar.HOUR_OF_DAY, 0);
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
		Map<String, Object> count = new HashMap<>();
		String fileBaseName = "";
		// ファイル名の生成
		if (formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			fileBaseName = CloudSignApi.PREFIX_FILE_CONFIRMATION;
		} else {
			fileBaseName = CloudSignApi.PREFIX_FILE_CANCEL;
		}
		//排他制御用
		ExclusiveForm exform = new ExclusiveForm();
		exform.setExclusiveObjectName("T_ORDER");
		UUID uuid = UUID.randomUUID();
        String sessionId = uuid.toString();
        exform.setExclusiveSessionId(sessionId);

        // 更新対象の発注番号収集
        List<String> targetList = new ArrayList<String>();
        Map<String,String> existsTargetMap = new HashMap<String,String>();
        for (TCloudSignEntity tCloudSignEntity : fileList) {
			List<TOrderEntity> groupOrderList = new ArrayList<TOrderEntity>();
			String orderNumber = tCloudSignEntity.getOrderNumber();
			String groupOrderNumber = tCloudSignEntity.getGroupOrderNumber();

			//まとめ発注の発注書
			if (Objects.nonNull(groupOrderNumber)) {
				groupOrderList = tOrderDao.selectListByGroupOrder(groupOrderNumber);
				if (Objects.nonNull(groupOrderList) && !groupOrderList.isEmpty()) {
					for (TOrderEntity groupOrder : groupOrderList) {
						if (!existsTargetMap.containsKey(groupOrder.getOrderNumber())) {
							existsTargetMap.put(groupOrder.getOrderNumber(),groupOrder.getOrderNumber());
							targetList.add(groupOrder.getOrderNumber());
						}
					}
				}
			} else {
				if (!existsTargetMap.containsKey(orderNumber)) {
					existsTargetMap.put(orderNumber, orderNumber);
					targetList.add(orderNumber);
				}
			}
        }

        //発注情報のロック
		exform.setExclusiveObjectKeyList(targetList);
		Map<String,List<TExclusiveEntity>> lockResult = exclusiveService.getMultiLock(exform);
		List<TExclusiveEntity> failList = lockResult.get(exclusiveService.getFailKey());
		//まとめてロック取得できない場合は次回更新として終了する
		if (Objects.nonNull(failList) && !failList.isEmpty()) {
			exclusiveService.releaseMultiLock(exform);
			count.put("agreeCount", agreeCount);
			count.put("dismissalCount", dismissalCount);
			return count;
		}

		for (TCloudSignEntity tCloudSignEntity : fileList) {
			List<String> orderNumberList = new ArrayList<String>();
			List<TOrderEntity> groupOrderList = new ArrayList<TOrderEntity>();
			String orderNumber = tCloudSignEntity.getOrderNumber();
			String groupOrderNumber = tCloudSignEntity.getGroupOrderNumber();

			//まとめ発注の発注書
			if (Objects.nonNull(groupOrderNumber) && !groupOrderNumber.isEmpty()) {
				groupOrderList = tOrderDao.selectListByGroupOrder(groupOrderNumber);
				if (Objects.nonNull(groupOrderList) && !groupOrderList.isEmpty()) {
					for (TOrderEntity groupOrder : groupOrderList) {
						orderNumberList.add(groupOrder.getOrderNumber());
					}
				}
			} else {
				orderNumberList.add(orderNumber);
			}
			//発注情報の取得
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
			//請書のダウンロード
			Map<String, Map<String,String>> fileInfo = getCloudSignDocument(ret, order, documentId, formType, status);
			//ファイル情報
			String fileName = "";
			String filePath = "";
			String driveFileId = "";
			Map<String,String> fileNameMap = new HashMap<String,String>();
			Map<String,String> filePathMap = new HashMap<String,String>();

			if (status.equals(CloudSignApi.STATUS_AGREE)) { //同意の場合
				log.info("status is agree : " + orderNumber );
				//連携テーブルの更新
				update(documentId);

				if (Objects.nonNull(groupOrderList) && !groupOrderList.isEmpty()) {
					for (TOrderEntity groupOrder : groupOrderList) {
						//ファイル情報
						String fileNameOrg = fileBaseName + groupOrder.getGyousyaCode() + "_" + groupOrder.getOrderNumber() + ".pdf";
						 Map<String,String> param = fileInfo.get(fileNameOrg);
						 if (Objects.nonNull(param)) {
							 fileName = param.get("fileName");
							 filePath = param.get("filePath");
							 driveFileId = param.get("driveFileId");
							 fileNameMap.put(groupOrder.getOrderNumber(), fileName);
							 filePathMap.put(groupOrder.getOrderNumber(), filePath);
						 }
						//発注テーブルの更新
						updateOrderAgree(groupOrder, driveFileId, formType);
						//請書受領連携
						sendOrderNumber(groupOrder);
					}
				} else {
					//ファイル情報
					String fileNameOrg = fileBaseName + order.getGyousyaCode() + "_" + order.getOrderNumber() + ".pdf";
					 Map<String,String> param = fileInfo.get(fileNameOrg);
					 if (Objects.nonNull(param)) {
						 fileName = param.get("fileName");
						 filePath = param.get("filePath");
						 driveFileId = param.get("driveFileId");
						 fileNameMap.put(order.getOrderNumber(), fileName);
						 filePathMap.put(order.getOrderNumber(), filePath);
					 }
					//発注テーブルの更新
					updateOrderAgree(order, driveFileId, formType);
					//請書受領連携
					sendOrderNumber(order);
				}
				// メール送信
				sendConfirmationAgreeMail(orderNumberList,filePathMap,fileNameMap);

				agreeCount++;
			} else if (status.equals(CloudSignApi.STATUS_DISMISSAL)) { //却下の場合
				log.info("status is dismissal : " + orderNumber);
				//連携テーブルの更新（削除）
				delete(documentId);

				if (Objects.nonNull(groupOrderList) && !groupOrderList.isEmpty()) {
					for (TOrderEntity groupOrder : groupOrderList) {
						//ファイル情報
						String fileNameOrg = fileBaseName + groupOrder.getGyousyaCode() + "_" + groupOrder.getOrderNumber() + ".pdf";
						 Map<String,String> param = fileInfo.get(fileNameOrg);
						 if (Objects.nonNull(param)) {
							 fileName = param.get("fileName");
							 filePath = param.get("filePath");
							 driveFileId = param.get("driveFileId");
							 fileNameMap.put(groupOrder.getOrderNumber(), fileName);
							 filePathMap.put(groupOrder.getOrderNumber(), filePath);
						 }
						//発注テーブルの更新
						updateOrderNotAgree(groupOrder, formType);
					}
				} else {
					//ファイル情報
					String fileNameOrg = fileBaseName + order.getGyousyaCode() + "_" + order.getOrderNumber() + ".pdf";
					 Map<String,String> param = fileInfo.get(fileNameOrg);
					 if (Objects.nonNull(param)) {
						 fileName = param.get("fileName");
						 filePath = param.get("filePath");
						 driveFileId = param.get("driveFileId");
						 fileNameMap.put(order.getOrderNumber(), fileName);
						 filePathMap.put(order.getOrderNumber(), filePath);
					 }
					//発注テーブルの更新（申請前に戻す）
					updateOrderNotAgree(order, formType);
				}
				// メール送信
				sendConfirmationDismissalMail(orderNumberList,filePathMap,fileNameMap);

				dismissalCount++;
			} else {
				//確認中の場合はなにもしない
				log.info("status is confirming : " + orderNumber);
			}
		}
		//ロックのリリース
		exclusiveService.releaseMultiLock(exform);
		count.put("agreeCount", agreeCount);
		count.put("dismissalCount", dismissalCount);
		return count;
	}

	/** クラウドサインの請書取得 */
	private Map<String, Map<String,String>> getCloudSignDocument(Map<String, Object> ret, TOrderEntity order, String documentId, String formType, String status) {
		Map<String, Map<String,String>> fileInfo = new HashMap<String, Map<String,String>>();

		String orderNumber = order.getOrderNumber();

		String fileId = "";
		String fileNameOrg = "";
		String fileName = "";
		String filePath = "";
		String fileNo = "";
		String driveFileId = "";

		if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			fileNo = FileApi.FILE_NO_CONFRIMATION;
		}else if(formType.equals(CloudSignApi.FORM_TYPE_CANCEL)){
			fileNo = FileApi.FILE_NO_CANCEL;
		}

		if (Objects.isNull(ret.get("files"))) {
			throw new CoreRuntimeException("can not get files param from cloudsign: " + orderNumber + " " + documentId);
		} else {
			log.info("get files from cloudsign : " + orderNumber + " " + documentId);
			//ファイルを取得しdriveと連携
			List<Map<String, Object>> files = (List<Map<String, Object>>)ret.get("files");
			if (Objects.nonNull(files) && !files.isEmpty()) {
				for (Map<String, Object> f : files) {
					if (Objects.nonNull(f.get("id")) && Objects.nonNull(f.get("name"))) {
						Map<String,String> param = new HashMap<String,String>();
						fileId = f.get("id").toString();
						fileNameOrg = f.get("name").toString();
						fileName = fileId + ".pdf";
						filePath = CloudSignApi.getFile(documentId, fileId, fileName);
						// 同意の場合のみ。却下の場合はメール添付利用のみのため
						if(status.equals(CloudSignApi.STATUS_AGREE)) {
							// 請書の家歴連携
							Map<String, Object> postRet = FileApi.postFile(order.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, fileNo, null, filePath, fileName, "application/pdf");
							if (Objects.nonNull(postRet) && Objects.nonNull(postRet.get("file_id"))) {
								// google drive file id
								driveFileId = postRet.get("file_id").toString();
								log.info("google drive file id is : " + driveFileId + ", file is: " + fileNameOrg + " " + filePath);
							} else {
								throw new CoreRuntimeException("can not post file to google drive: " + fileNameOrg + " " + filePath);
							}
						}
						param.put("fileId",fileId);
						param.put("fileNameOrg",fileNameOrg);
						param.put("fileName",fileName);
						param.put("filePath",filePath);
						param.put("fileNo",fileNo);
						param.put("driveFileId",driveFileId);
						fileInfo.put(fileNameOrg, param);
					}
				}
			} else {
				throw new CoreRuntimeException("cloudsign files is empty: " + orderNumber + " " + documentId);
			}
		}
		return fileInfo;
	}

	/** 発注請書受入時にメールを送信 */
	public void sendConfirmationAgreeMail(List<String> orderNumberList, Map<String,String> filePathMap, Map<String,String> fileNameMap) {
		String orderNumber = orderNumberList.get(0);

		//発注情報取得
		 VOrderStatusEntity vOrder = orderService.getVOrder(orderNumber);

		//工事名取得
		String koujiName = vOrder.getKoujiName();
		//業者名取得
		String gyousyaName = vOrder.getGyousyaName();
		//支店情報取得
		String eigyousyoCode = vOrder.getEigyousyoCode();
		String eigyousyoName = vOrder.getEigyousyoName();
		//社員情報取得
		String syainCode = vOrder.getSyainCode();
		MSyainEntity syain = mSyainDao.select(syainCode);

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);

		String newFileNameBase = "注文請書";
		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		for (String num : orderNumberList) {
			String filePath = filePathMap.get(num);
			String fileName = fileNameMap.get(num);
			if (Objects.nonNull(filePath) && Objects.nonNull(fileName)) {
				String newFileName = newFileNameBase + "_" + num + ".pdf";
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("filePath", filePath);
				fileMap.put("fileName", newFileName);
				fileList.add(fileMap);
			}
		}
		//メール送信
		mailService.sendMailConfirmationAgree(to, cc, eigyousyoName, syain.getSyainName(), koujiName, gyousyaName, orderNumberList, fileList);
	}

	/** 発注請書受入却下時にメールを送信 */
	public void sendConfirmationDismissalMail(List<String> orderNumberList, Map<String,String> filePathMap, Map<String,String> fileNameMap) {
		String orderNumber = orderNumberList.get(0);

		//発注情報取得
		 VOrderStatusEntity vOrder = orderService.getVOrder(orderNumber);

		//工事名取得
		String koujiName = vOrder.getKoujiName();
		//業者名取得
		String gyousyaName = vOrder.getGyousyaName();
		//支店情報取得
		String eigyousyoCode = vOrder.getEigyousyoCode();
		String eigyousyoName = vOrder.getEigyousyoName();
		//社員情報取得
		String syainCode = vOrder.getSyainCode();
		MSyainEntity syain = mSyainDao.select(syainCode);

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);

		String newFileNameBase = "注文請書";
		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		for (String num : orderNumberList) {
			String filePath = filePathMap.get(num);
			String fileName = fileNameMap.get(num);
			if (Objects.nonNull(filePath) && Objects.nonNull(fileName)) {
				String newFileName = newFileNameBase + "_" + num + ".pdf";
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("filePath", filePath);
				fileMap.put("fileName", newFileName);
				fileList.add(fileMap);
			}
		}
		//メール送信
		mailService.sendMailConfirmationDismissal(to, cc, eigyousyoName, syain.getSyainName(), koujiName, gyousyaName, orderNumberList, fileList);
	}

	/** クラウドサインのリマインド */
	public int remindList(List<TCloudSignEntity> fileList) {
		int count = 0;
		for (TCloudSignEntity tCloudSignEntity : fileList) {
			//再送する
			CloudSignApi.postDocumentId(tCloudSignEntity.getFileId());
			count++;
		}
		return count;
	}
	/** クラウドサインのリマインド単体 */
	public void remind(String documentId) {
		//再送する
		CloudSignApi.postDocumentId(documentId);
	}

	/** 請書 発注番号連携 */
	private void sendOrderNumber(TOrderEntity order) {
		String orderNumber = order.getOrderNumber();
		String koujiCode = order.getKoujiCode();
		String insertUser = order.getInsertUser();
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(koujiCode);
		String sapUser = kouji.getTantouSyainCode();
		if (Objects.isNull(sapUser)) {
			sapUser = TEMPORARY_SYAIN_CODE;
		}
		// sap 請書未受領一覧検索
		log.info("selectUkeshoJyuryou: " + koujiCode + " " + sapUser);
		Map<String, Object> nonJyuryouData = SapApi.selectUkeshoJyuryou(koujiCode, sapUser);
		//請書未受領一覧検索結果取得
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(nonJyuryouData);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			//log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
			//return;
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
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
					Date updateDate = CommonUtils.parseDateStringToDate(lastUpdateDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					Date updateTime = CommonUtils.parseDateStringToDate(lastUpdateTime, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
					if (Objects.nonNull(updateDate)) {
						// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
						lastUpdateDate = CommonUtils.formatDateToString(updateDate, "yyyyMMdd", "JST");
					}
					if (Objects.nonNull(updateTime)) {
						// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
						lastUpdateTime = CommonUtils.formatDateToString(updateTime, "HH:mm:ss", "JST");
					}
					log.info("setOrderNumberByUkeshoJyuryou: " + orderNumber + " " + lastUpdateDate + " " + lastUpdateTime);
					Map<String, Object> result = SapApi.setOrderNumberByUkeshoJyuryou(orderNumber, lastUpdateDate, lastUpdateTime);
					resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
					if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
						//log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
						throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
					}
					break;
				}
			}
		}
	}

	/* ■■■■■■■■■■■■■■■■■■■■■■■■ 単発処理 ■■■■■■■■■■■■■■■■■■■■■■■ */
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
							Map<String, Object> postRet = FileApi.postFile(order.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, fileNo, null, filePath, fileName, "application/pdf");
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
