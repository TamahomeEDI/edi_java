package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import jp.co.edi_java.app.dao.MSaimokuKousyuDao;
import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TDeliveryItemDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.dto.DeliveryDto;
import jp.co.edi_java.app.dto.JobcanDeliveryItemDto;
import jp.co.edi_java.app.dto.JobcanDto;
import jp.co.edi_java.app.dto.SapOrderDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.crypto.CipherUtils;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.jobcan.JobcanApi;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class DeliveryService {

	@Autowired
    public MailService mailService;

	@Autowired
    public OrderService orderService;

	@Autowired
    public TDeliveryDao tDeliveryDao;

	@Autowired
    public TDeliveryItemDao tDeliveryItemDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public MSaimokuKousyuDao mSaimokuKousyuDao;

	@Autowired
    public TGyousyaMailaddressDao tGyousyaMailaddressDao;

	private static String STG_FLG;
	private static String STG_FLG_ON = "1";
	private static String TEMPORARY_SYAIN_CODE = "990000";
	private static String ORDER_TYPE_DELIVERY = "1";
	public static String TOSHO_CODE_EDI = "09";
	public static String FILE_CODE_FORM = "03";			//帳票
	public static String FILE_NO_DELIVERY = "03";		//納品書

	private DeliveryService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public TDeliveryEntity get(String deliveryNumber) {
		return tDeliveryDao.select(deliveryNumber);
	}

	public TDeliveryEntity getByOrderNumber(String orderNumber, int deliveryCount) {
		return tDeliveryDao.selectByOrderNumber(orderNumber, deliveryCount);
	}

	public List<TDeliveryItemEntity> getItemList(String deliveryNumber) {
		return tDeliveryItemDao.selectAll(deliveryNumber);
	}

	public List<DeliveryDto> getList(String orderNumber, String remandFlg) {
		List<DeliveryDto> dtoList = new ArrayList<>();
		List<TDeliveryEntity> list = tDeliveryDao.selectAll(orderNumber, remandFlg);
		for (TDeliveryEntity delivery : list) {
			DeliveryDto dto = new DeliveryDto();
			List<TDeliveryItemEntity> itemList = getItemList(delivery.getDeliveryNumber());
			BeanUtils.copyBeanToBean(delivery, dto);
			dto.setItemList(itemList);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/** 納品受入用明細取得 （納品書 初期レコード作成用）*/
	public List<TDeliveryItemEntity> getDeliveryDetail(String orderNumber, String userId) {
		List<TDeliveryItemEntity> ret = new ArrayList<TDeliveryItemEntity>();
		if (Objects.isNull(orderNumber) || Objects.isNull(userId)) {
			return ret;
		}
		// 詳細
		Map<String, Object> data = SapApi.getDeliveryItemList(orderNumber, userId);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_04003);
		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		if(obj instanceof List) {
			itemList = (List<Map<String, Object>>)obj;
		}else {
			itemList.add((Map<String, Object>)obj);
		}
		for (Map<String, Object> itemMap : itemList) {
			String lineNo = itemMap.get(SapApiConsts.PARAMS_ID_EBELP).toString();
			int itemNumber = Integer.valueOf(lineNo);

			TDeliveryItemEntity dtl = new TDeliveryItemEntity();
			// 明細の転記
			//品目コード
			dtl.setItemNumber(itemNumber);
			//明細ID
			dtl.setJcoEbelp(lineNo);
			//品目名
			dtl.setItemName(itemMap.get(SapApiConsts.PARAMS_ID_TXZ01).toString());
			//発注数量
			dtl.setOrderQuantity(Double.valueOf(itemMap.get(SapApiConsts.PARAMS_ID_ZHTMNG).toString()));
			//納品数量
			dtl.setDeliveryQuantity(Double.valueOf(itemMap.get(SapApiConsts.PARAMS_ID_ZMENGE).toString()));
			//発注残数量
			dtl.setDeliveryRemainingQuantity(Double.valueOf(itemMap.get(SapApiConsts.PARAMS_ID_MENGE).toString()));
			//単位
			dtl.setUnit(itemMap.get(SapApiConsts.PARAMS_ID_MEINS).toString());
			ret.add(dtl);
		}
		return ret;
	}

	//納品書リマインド対象のリスト取得
	public List<TDeliveryEntity> selectRemindList() {
		return tDeliveryDao.selectUnconfirmList();
	}

	public String regist(DeliveryForm form) {
		//納品情報登録
		String deliveryNumber = registDelivery(form);
		//納品明細登録
		registDeliveryItem(deliveryNumber, form.itemList, form.userId);
		return deliveryNumber;
	}

	//納品情報登録
	private String registDelivery(DeliveryForm form) {
		String deliveryNumber = "D_" + System.currentTimeMillis();
		TDeliveryEntity entity = new TDeliveryEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setDeliveryNumber(deliveryNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tDeliveryDao.insert(entity);
		return deliveryNumber;
	}

	//納品明細登録
	private void registDeliveryItem(String deliveryNumber, List<TDeliveryItemEntity> itemList, String userId) {
		int count = 1;
		for (TDeliveryItemEntity item : itemList) {
			TDeliveryItemEntity entity = new TDeliveryItemEntity();
			BeanUtils.copyBeanToBean(item, entity);
			entity.setDeliveryNumber(deliveryNumber);
			entity.setItemNumber(count);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			tDeliveryItemDao.insert(entity);
			count++;
		}
	}

	public int update(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
//		BeanUtils.copyBeanToBean(form, entity);
		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setFileId(form.getFileId());
		entity.setUpdateUser(form.getUserId());
		return tDeliveryDao.update(entity);
	}

	public int delete(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
		return tDeliveryDao.delete(entity);
	}

	/** ジョブカン承認待ち */
	public void apply(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				if (Objects.equals(entity.getStaffReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getManagerReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getRemandFlg(), CommonConsts.REMAND_FLG_OFF)) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					entity.setStaffReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
					entity.setStaffReceiptDate(timestamp);
					entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_OFF);
					entity.setManagerReceiptDate(null);
					entity.setRemandFlg(CommonConsts.REMAND_FLG_OFF);
					entity.setRemandDate(null);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.updateWf(entity);
					applyToJobcan(entity);
				}
			}
		}
	}
	/** ジョブカン申請 */
	private void applyToJobcan(TDeliveryEntity delivery) {
		String deliveryNumber = delivery.getDeliveryNumber();
		String koujiCode = delivery.getKoujiCode();
		String orderNumber = delivery.getOrderNumber();
		String encryptNumber = encodeDeliveryNumber(deliveryNumber);
		String gyousyaCode = delivery.getGyousyaCode();
		String saimokuKousyuCode = delivery.getSaimokuKousyuCode();

		//工事情報
	    MKoujiEntity kouji = mKoujiDao.select(koujiCode);
	    //発注書情報
	    SapOrderDto order = orderService.get(orderNumber);
	    //業者情報
	    MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
	    //細目工種情報
	    MSaimokuKousyuEntity saimoku = mSaimokuKousyuDao.select(saimokuKousyuCode);
	    //支店
	    String eigyousyoCode = kouji.getEigyousyoCode();
	    MEigyousyoEntity eigyousyo = mEigyousyoDao.select(eigyousyoCode);
	    //施工担当社員
	    String syainCode = kouji.getTantouSyainCode();
	    MSyainEntity syain = mSyainDao.select(syainCode);
	    String userId = syainCode;

	    if (Objects.nonNull(syain) && Objects.nonNull(syain.getSyainCode())) {
	    	//'990000'ユーザは工務課長を申請ユーザにする
	    	if (Objects.equals(syain.getSyainCode(),TEMPORARY_SYAIN_CODE) && Objects.nonNull(eigyousyoCode)) {
	    		List<MSyainEntity> s3SyainList = mSyainDao.selectListBySyokusyu3(eigyousyoCode);
	    		for (MSyainEntity s3Syain : s3SyainList) {
	    			if (Objects.nonNull(s3Syain.getSyainMail())) {
	    				userId = s3Syain.getSyainCode();
	    				break;
	    			}
	    		}
	    	}
	    }

	    //Jobcan連携項目
	  	JobcanDto jobcanDto = new JobcanDto();

	    jobcanDto.setOrderType(ORDER_TYPE_DELIVERY);
	    jobcanDto.setReportNumber(deliveryNumber);
	    jobcanDto.setEigyousyoCode(eigyousyoCode);
	    jobcanDto.setEigyousyoName(eigyousyo.getEigyousyoName());
	    jobcanDto.setSyainCode(syainCode);
	    jobcanDto.setSyainName(syain.getSyainName());
	    jobcanDto.setKoujiCode(koujiCode);
	    jobcanDto.setKoujiName(kouji.getKoujiName());
	    jobcanDto.setGyousyaCode(gyousyaCode);
	    jobcanDto.setGyousyaName(gyousya.getGyousyaName());
	    jobcanDto.setOrderNumber(orderNumber);
	    jobcanDto.setSaimokuKousyuCode(saimokuKousyuCode);
	    jobcanDto.setSaimokuKousyuName(saimoku.getSaimokuKousyuName());
	    jobcanDto.setOrderDate(order.getOrderDate());
	    jobcanDto.setDeliveryDate(delivery.getDeliveryDate());
	    jobcanDto.setDeliveryCount(delivery.getDeliveryCount());
	    jobcanDto.setWorkRate(0);
	    jobcanDto.setUserCode(userId);
	    jobcanDto.setFileId(delivery.getFileId());
	    jobcanDto.setToshoCode(TOSHO_CODE_EDI);
	    jobcanDto.setFileCode(FILE_CODE_FORM);
	    jobcanDto.setFileNo(FILE_NO_DELIVERY);
	    jobcanDto.setEncryptNumber(encryptNumber);
	    List<JobcanDeliveryItemDto> jobcanItemList = new ArrayList<JobcanDeliveryItemDto>();
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		for (TDeliveryItemEntity entity : itemList) {
			JobcanDeliveryItemDto item = new JobcanDeliveryItemDto();
			item.setDeliveryNumber(entity.getDeliveryNumber());
			item.setItemNumber(entity.getItemNumber());
			item.setItemName(entity.getItemName());
			item.setOrderQuantity(entity.getOrderQuantity());
			item.setDeliveryQuantity(entity.getDeliveryQuantity());
			item.setDeliveryRemainingQuantity(entity.getDeliveryRemainingQuantity());
			item.setUnit(entity.getUnit());
			item.setCompleteDeliveryFlg(entity.getCompleteDeliveryFlg());
			jobcanItemList.add(item);
		}
		jobcanDto.setDeliveryItemList(jobcanItemList);

		JobcanApi.postApply(jobcanDto);

	}
	/** ジョブカン承認済 受入 */
	public void approve(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				if (Objects.equals(entity.getManagerReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getRemandFlg(), CommonConsts.REMAND_FLG_OFF)) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
					entity.setManagerReceiptDate(timestamp);
					entity.setRemandFlg(CommonConsts.REMAND_FLG_OFF);
					entity.setRemandDate(null);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.updateWf(entity);
					// SAP連携
					approveSap(entity, form);
				}
			}
		}
	}
	/** SAP連携 */
	private void approveSap(TDeliveryEntity delivery, DeliveryForm form) {
		String deliveryNumber = delivery.getDeliveryNumber();
		String orderNumber = delivery.getOrderNumber();
		String userCode = form.getUserId();
		//工事情報
	    MKoujiEntity kouji = mKoujiDao.select(delivery.getKoujiCode());
		String eigyousyoCode = kouji.getEigyousyoCode();
		String acceptanceDate = form.getApproveDate();
		String approverCode = form.getApproverCode();
		String approveDate = form.getApproveDate();
		String approveDateTime = form.getApproveDateTime();

		// 明細情報取得
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		// ■■■■■■■■■■■■■ 受入入力レコードの作成
		Map<String, Object> result = SapApi.setDeliveryItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, itemList, userCode);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		// ■■■■■■■■■■■■■ 作成したレコードの取得
		Map<String, Object> dataWFSeqNo = SapApi.getDeliveryWFSeqNo(eigyousyoCode, userCode);
		resultInfo = SapApiAnalyzer.analyzeResultInfo(dataWFSeqNo);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		Object wfobj = dataWFSeqNo.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		List<Map<String, Object>> applyTargetList = new ArrayList<>();
		if(wfobj instanceof List) {
			applyTargetList = (List<Map<String, Object>>)wfobj;
		} else {
			applyTargetList.add((Map<String, Object>)wfobj);
		}

		Map<String, Object> targetObj = null;
		for (Map<String, Object> itemMap : applyTargetList) {
			String ordNum = itemMap.get(SapApiConsts.PARAMS_ID_EBELN).toString();
			String acptDate = itemMap.get(SapApiConsts.PARAMS_ID_ZUKDAT).toString();
			acptDate = acptDate.replaceAll("/",""); // 「/」を削除
			// 登録したレコードを取得
			if (Objects.equals(ordNum, orderNumber) && Objects.equals(acptDate, acceptanceDate)) {
				targetObj = itemMap;
				break;
			}
		}
		// 作成したレコードが見つからない
		if (Objects.isNull(targetObj)) {
			throw new CoreRuntimeException("not found Sap Object: " + orderNumber);
		}
		//申請番号ダミー
		String wfNumber = deliveryNumber + "_" + approveDate + approveDateTime;
		//レコード登録日
		String recordDate = targetObj.get(SapApiConsts.PARAMS_ID_ERDAT1).toString();
		//シーケンス番号
		String wfSeqNo = targetObj.get(SapApiConsts.PARAMS_ID_ZSEQNO).toString();
		//更新日
		String lastUpdateDate = targetObj.get(SapApiConsts.PARAMS_ID_AEDAT).toString();
		//更新時間
		String lastUpdateTime = targetObj.get(SapApiConsts.PARAMS_ID_AEZEIT).toString();

		log.info("before date formatting: " + orderNumber + " " + lastUpdateDate + " " + lastUpdateTime);
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
		log.info("after date formatting: " + orderNumber + " " + lastUpdateDate + " " + lastUpdateTime);

		// ■■■■■■■■■■■■■ 申請
		result = SapApi.applyDeliveryWorkReport(eigyousyoCode, orderNumber, recordDate, wfSeqNo, acceptanceDate, wfNumber, lastUpdateDate, lastUpdateTime, userCode);
		resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		// ■■■■■■■■■■■■■ 決済
		result = SapApi.approveDeliveryWorkReport(wfNumber, approverCode, approveDate, approveDateTime, userCode);
		resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
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

	/** ジョブカン否認 */
	public void reject(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				if (Objects.equals(entity.getManagerReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getRemandFlg(), CommonConsts.REMAND_FLG_OFF)) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					entity.setStaffReceiptFlg(CommonConsts.RECEIPT_FLG_OFF);
					entity.setStaffReceiptDate(null);
					entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_OFF);
					entity.setManagerReceiptDate(null);
					entity.setRemandFlg(CommonConsts.REMAND_FLG_OFF);
					entity.setRemandDate(null);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tDeliveryDao.updateWf(entity);
					//否認時のメール送信
					sendMailDeliveryReject(entity, form.getApproverComments());
				}
			}
		}
	}
	/** 業者へ差戻 */
	public void sendback(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
				if (Objects.equals(entity.getStaffReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getManagerReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getRemandFlg(), CommonConsts.REMAND_FLG_OFF)) {
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					entity.setStaffReceiptFlg(CommonConsts.RECEIPT_FLG_OFF);
					entity.setStaffReceiptDate(null);
					entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_OFF);
					entity.setManagerReceiptDate(null);
					entity.setRemandFlg(CommonConsts.REMAND_FLG_ON);
					entity.setRemandDate(timestamp);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());

					tDeliveryDao.updateWf(entity);

					//差し戻し用のメール送信

					String gyousyaCode = entity.getGyousyaCode();
					List<String> addressList = tGyousyaMailaddressDao.selectList(gyousyaCode);
					MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
					mailService.sendMailDeliveryRemand(addressList, gyousya, entity);
				}
			}
		}
	}

	//納品書Noのエンコード
	public String encodeDeliveryNumber(DeliveryForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getDeliveryNumber())) {
			ret = encodeDeliveryNumber(form.getDeliveryNumber());
		}
		return ret;
	}

	//納品書Noのエンコード
	public String encodeDeliveryNumber(String deliveryNumber) {
		String ret = "";
		if (Objects.nonNull(deliveryNumber)) {
			ret = CipherUtils.getEncryptAES(deliveryNumber);
		}
		return ret;
	}

	//納品書Noのデコード
	public String decodeDeliveryNumber(DeliveryForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptDeliveryNumber())) {
			ret = decodeDeliveryNumber(form.getEncryptDeliveryNumber());
		}
		return ret;
	}

	//納品書Noのデコード
	public String decodeDeliveryNumber(String encryptNumber) {
		String ret = "";
		if (Objects.nonNull(encryptNumber)) {
			ret = CipherUtils.getDecryptAES(encryptNumber);
		}
		return ret;
	}

	//納品書の受入確認メール再送
	public void remindList(List<TDeliveryEntity> deliveryList) {
		if (Objects.nonNull(deliveryList)) {
			DeliveryForm form = new DeliveryForm();
			for (TDeliveryEntity delivery : deliveryList) {
				form.setDeliveryNumber(delivery.getDeliveryNumber());
				form.setGyousyaCode(delivery.getGyousyaCode());
				sendMailDelivery(form, true);
			}
		}
	}

	//納品書登録時にメールを送信
	public void sendMailDelivery(DeliveryForm form, Boolean remind) {
		String deliveryNumber = form.getDeliveryNumber();
		String gyousyaCode = form.getGyousyaCode();

		//納品情報取得
		TDeliveryEntity delivery = get(deliveryNumber);
		if (Objects.isNull(delivery)) {
			log.info("delivery nothing: " + deliveryNumber);
			return;
		}
		//納品書明細取得
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(delivery.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);

		String eigyousyoCode = eigyousyo.getEigyousyoCode();

		String to = getMailTo(syain, eigyousyoCode);
		String cc = getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(delivery);

		//メール送信
		mailService.sendMailDelivery(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery.getOrderNumber(), fileList, deliveryNumber, itemList, remind);
	}

	//納品書ジョブカン申請否認時にメールを送信
	public void sendMailDeliveryReject(TDeliveryEntity delivery, String rejectComments) {

		//納品情報取得
		if (Objects.isNull(delivery)) {
			log.info("delivery nothing in sendMailDeliveryReject");
			return;
		}

		String deliveryNumber = delivery.getDeliveryNumber();
		String gyousyaCode = delivery.getGyousyaCode();

		//納品書明細取得
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(delivery.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);

		String eigyousyoCode = eigyousyo.getEigyousyoCode();

		String to = getMailTo(syain, eigyousyoCode);
		String cc = getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(delivery);

		//メール送信
		mailService.sendMailDeliveryReject(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery.getOrderNumber(), fileList, deliveryNumber, itemList, rejectComments);
	}

	private List<Map<String,String>> getAttachedFile(TDeliveryEntity delivery) {
		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		if (Objects.nonNull(delivery.getFileId())) {
			String fileName = delivery.getFileId() + ".pdf";
			String filePath = FileApi.getFile(delivery.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, FileApi.FILE_NO_DELIVERY, delivery.getFileId(), "pdf", fileName);
			//ファイル名をわかりやすい名前に変更して添付
			fileName = "納品書.pdf";

			if (Objects.nonNull(filePath)) {
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("filePath", filePath);
				fileMap.put("fileName", fileName);
				fileList.add(fileMap);
			}
		}
		return fileList;
	}

	private String getMailTo(MSyainEntity syain, String eigyousyoCode) {
		List<String> toList = new ArrayList<String>();
		if (Objects.nonNull(syain) && Objects.nonNull(syain.getSyainCode())) {
			//'990000'ユーザは工務長宛にメール
			if (Objects.equals(syain.getSyainCode(),TEMPORARY_SYAIN_CODE) && Objects.nonNull(eigyousyoCode)) {
				List<MSyainEntity> s3SyainList = mSyainDao.selectListBySyokusyu3(eigyousyoCode);
				for (MSyainEntity s3Syain : s3SyainList) {
					if (Objects.nonNull(s3Syain.getSyainMail())) {
						toList.add(s3Syain.getSyainMail());
					}
				}
			} else {
				if (Objects.nonNull(syain.getSyainMail())) {
					toList.add(syain.getSyainMail());
				}
			}
		}
		//to
		String to = "";
		if (!toList.isEmpty()) {
			to = String.join(",", toList);
		}
		return to;
	}

	private String getMailCc(String eigyousyoCode) {
		String cc = "";
		if(!STG_FLG.equals(STG_FLG_ON) && Objects.nonNull(eigyousyoCode)) {
			cc = "jimu-" + eigyousyoCode + "@tamahome.jp";
		}else {
			cc = MailService.STG_CC_MAIL;
		}
		return cc;
	}
}
