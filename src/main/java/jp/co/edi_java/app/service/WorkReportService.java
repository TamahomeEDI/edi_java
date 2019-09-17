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
import jp.co.edi_java.app.dao.TWorkReportDao;
import jp.co.edi_java.app.dao.TWorkReportItemDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.ApprovalDto;
import jp.co.edi_java.app.dto.JobcanDto;
import jp.co.edi_java.app.dto.JobcanWorkReportItemDto;
import jp.co.edi_java.app.dto.SapOrderDto;
import jp.co.edi_java.app.dto.WorkReportDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.WorkReportForm;
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
public class WorkReportService {

	@Autowired
    public MailService mailService;

	@Autowired
    public OrderService orderService;

	@Autowired
    public TWorkReportDao tWorkReportDao;

	@Autowired
    public TWorkReportItemDao tWorkReportItemDao;

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
	private static String ORDER_TYPE_WORK_REPORT = "2";
	public static String TOSHO_CODE_EDI = "09";
	public static String FILE_CODE_FORM = "03";			//帳票
	public static String FILE_NO_WORK_REPORT = "04";		//出来高報告書

	private WorkReportService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

	public TWorkReportEntity get(String workReportNumber) {
		return tWorkReportDao.select(workReportNumber);
	}

	public TWorkReportEntity getByOrderNumber(String orderNumber, int workReportCount) {
		return tWorkReportDao.selectByOrderNumber(orderNumber, workReportCount);
	}

	public List<TWorkReportItemEntity> getItemList(String workReportNumber) {
		return tWorkReportItemDao.selectAll(workReportNumber);
	}

	public List<WorkReportDto> getList(String orderNumber, String remandFlg) {
		List<WorkReportDto> dtoList = new ArrayList<>();
		List<TWorkReportEntity> list = tWorkReportDao.selectAll(orderNumber, remandFlg);
		for (TWorkReportEntity workReport : list) {
			WorkReportDto dto = new WorkReportDto();
			List<TWorkReportItemEntity> itemList = getItemList(workReport.getWorkReportNumber());
			BeanUtils.copyBeanToBean(workReport, dto);
			dto.setItemList(itemList);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/** 納品受入用明細取得 （出来高報告書 初期レコード作成用）*/
	public List<TWorkReportItemEntity> getDeliveryDetail(String orderNumber, String userId) {
		List<TWorkReportItemEntity> ret = new ArrayList<TWorkReportItemEntity>();
		if (Objects.isNull(orderNumber) || Objects.isNull(userId)) {
			return ret;
		}
		// 詳細
		Map<String, Object> data = SapApi.getWorkReportItemList(orderNumber, userId);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_04004);
		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		if(obj instanceof List) {
			itemList = (List<Map<String, Object>>)obj;
		}else {
			itemList.add((Map<String, Object>)obj);
		}
		for (Map<String, Object> itemMap : itemList) {
			String lineNo = itemMap.get(SapApiConsts.PARAMS_ID_EBELP).toString();
			int itemNumber = Integer.valueOf(lineNo);

			TWorkReportItemEntity dtl = new TWorkReportItemEntity();
			// 明細の転記
			//品目コード
			dtl.setItemNumber(itemNumber);
			//明細ID
			dtl.setJcoEbelp(lineNo);
			//品目名
			dtl.setItemName(itemMap.get(SapApiConsts.PARAMS_ID_TXZ01).toString());
			//発注数量
			dtl.setOrderQuantity(Double.valueOf(itemMap.get(SapApiConsts.PARAMS_ID_ZHTMNG).toString()));
			//単位
			dtl.setUnit(itemMap.get(SapApiConsts.PARAMS_ID_MEINS).toString());
			ret.add(dtl);
		}
		return ret;
	}

	//出来高書リマインド対象のリスト取得
	public List<TWorkReportEntity> selectRemindList() {
		return tWorkReportDao.selectUnconfirmList();
	}

	public String regist(WorkReportForm form) {
		//出来高報告情報登録
		String workReportNumber = registWorkReport(form);
		//出来高報告明細登録
		registWorkReportItem(workReportNumber, form.itemList, form.userId);
		return workReportNumber;
	}

	//出来高報告情報登録
	private String registWorkReport(WorkReportForm form) {
		String workReportNumber = "R_" + System.currentTimeMillis();
		TWorkReportEntity entity = new TWorkReportEntity();
		BeanUtils.copyBeanToBean(form, entity);
		entity.setWorkReportNumber(workReportNumber);
		entity.setInsertUser(form.getUserId());
		entity.setUpdateUser(form.getUserId());
		tWorkReportDao.insert(entity);
		return workReportNumber;
	}

	//出来高報告明細登録
	private void registWorkReportItem(String workReportNumber, List<TWorkReportItemEntity> itemList, String userId) {
		int count = 1;
		for (TWorkReportItemEntity item : itemList) {
			TWorkReportItemEntity entity = new TWorkReportItemEntity();
			BeanUtils.copyBeanToBean(item, entity);
			entity.setWorkReportNumber(workReportNumber);
			entity.setItemNumber(count);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			tWorkReportItemDao.insert(entity);
			count++;
		}
	}

	public int update(WorkReportForm form) {
		TWorkReportEntity entity = this.get(form.workReportNumber);
//		BeanUtils.copyBeanToBean(form, entity);
		entity.setUserBikou(form.getUserBikou());
		entity.setPartnerBikou(form.getPartnerBikou());
		entity.setFileId(form.getFileId());
		entity.setUpdateUser(form.getUserId());
		return tWorkReportDao.update(entity);
	}

	public int delete(WorkReportForm form) {
		TWorkReportEntity entity = this.get(form.workReportNumber);
		return tWorkReportDao.delete(entity);
	}

	/** ジョブカン承認待ち */
	public void apply(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
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
					tWorkReportDao.updateWf(entity);
					applyToJobcan(entity);
				}
			}
		}
	}
	/** ジョブカン申請 */
	private void applyToJobcan(TWorkReportEntity workReport) {
		String workReportNumber = workReport.getWorkReportNumber();
		String koujiCode = workReport.getKoujiCode();
		String orderNumber = workReport.getOrderNumber();
		String encryptNumber = encodeWorkReportNumber(workReportNumber);
		String gyousyaCode = workReport.getGyousyaCode();
		String saimokuKousyuCode = workReport.getSaimokuKousyuCode();

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

	    jobcanDto.setOrderType(ORDER_TYPE_WORK_REPORT);
	    jobcanDto.setReportNumber(workReportNumber);
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
	    jobcanDto.setDeliveryDate(workReport.getWorkReportDate());
	    jobcanDto.setDeliveryCount(workReport.getWorkReportCount());
	    jobcanDto.setWorkRate(workReport.getWorkRate());
	    jobcanDto.setUserCode(userId);
	    jobcanDto.setFileId(workReport.getFileId());
	    jobcanDto.setToshoCode(TOSHO_CODE_EDI);
	    jobcanDto.setFileCode(FILE_CODE_FORM);
	    jobcanDto.setFileNo(FILE_NO_WORK_REPORT);
	    jobcanDto.setEncryptNumber(encryptNumber);
	    List<JobcanWorkReportItemDto> jobcanItemList = new ArrayList<JobcanWorkReportItemDto>();
		List<TWorkReportItemEntity> itemList = getItemList(workReportNumber);
		for (TWorkReportItemEntity entity : itemList) {
			JobcanWorkReportItemDto item = new JobcanWorkReportItemDto();
			item.setWorkReportNumber(entity.getWorkReportNumber());
			item.setItemNumber(entity.getItemNumber());
			item.setItemName(entity.getItemName());
			item.setOrderQuantity(entity.getOrderQuantity());
			item.setUnit(entity.getUnit());
			jobcanItemList.add(item);
		}
		jobcanDto.setWorkReportItemList(jobcanItemList);

		JobcanApi.postApply(jobcanDto);

	}
	/** ジョブカン承認済 受入 */
	public void approve(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
				if (Objects.equals(entity.getManagerReceiptFlg(), CommonConsts.RECEIPT_FLG_OFF) &&
						Objects.equals(entity.getRemandFlg(), CommonConsts.REMAND_FLG_OFF)) {
					//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String approveDate = form.getApproveDate() + " " + form.getApproveDateTime();
					Date receiptDate = parseDateStringToDate(approveDate, "yyyyMMdd HHmmss", Locale.ENGLISH);
					Timestamp timestamp = new Timestamp(receiptDate.getTime());
					log.info("approveDate: " + approveDate);
					entity.setManagerReceiptFlg(CommonConsts.RECEIPT_FLG_ON);
					entity.setManagerReceiptDate(timestamp);
					entity.setRemandFlg(CommonConsts.REMAND_FLG_OFF);
					entity.setRemandDate(null);
					entity.setUserBikou(dto.getUserBikou());
					entity.setUpdateUser(form.getUserId());
					tWorkReportDao.updateWf(entity);
					// SAP連携
					approveSap(entity, form);
				}
			}
		}
	}

	/** SAP連携 */
	private void approveSap(TWorkReportEntity workReport, WorkReportForm form) {
		String workReportNumber = workReport.getWorkReportNumber();
		String orderNumber = workReport.getOrderNumber();
		String userCode = form.getUserId();
		//工事情報
	    MKoujiEntity kouji = mKoujiDao.select(workReport.getKoujiCode());
		String eigyousyoCode = kouji.getEigyousyoCode();
		String acceptanceDate = form.getApproveDate();
		String approverCode = form.getApproverCode();
		String approveDate = form.getApproveDate();
		String approveDateTime = form.getApproveDateTime();
		String workRate = String.valueOf(workReport.getWorkRate());

		//JCO result
		Map<String, Object> result = null;
		Map<String, Object> resultInfo = null;
		//申請対象
		Map<String, Object> targetObj = null;

		//申請番号ダミー
		String wfNumber = workReportNumber + "_" + approveDate + approveDateTime;

		// ■■■■■■■■■■■■■ 受入入力レコードの存在チェック
		// 申請済レコードの存在チェック
		targetObj = getWorkReportSapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "0");

		if (Objects.isNull(targetObj)) {
			// 未申請レコードの存在チェック
			targetObj = getWorkReportSapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "");
			if (Objects.isNull(targetObj)) {
				// ■■■■■■■■■■■■■ 受入入力レコードの作成
				result = SapApi.setWorkReportItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, workRate, userCode);
				resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
				if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
					throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
				}
			} else {
				//シーケンス番号
				String wfSeqNo = targetObj.get(SapApiConsts.PARAMS_ID_ZSEQNO).toString();
				//更新日
				String lastUpdateDate = targetObj.get(SapApiConsts.PARAMS_ID_AEDAT).toString();
				//更新時間
				String lastUpdateTime = targetObj.get(SapApiConsts.PARAMS_ID_AEZEIT).toString();
				log.info("before date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime);
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
				log.info("after date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime);
				// ■■■■■■■■■■■■■ 受入入力レコードの上書き
				result = SapApi.setWorkReportItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateTime, workRate, userCode);
				resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
				if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
					throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
				}
			}
			// ■■■■■■■■■■■■■ 作成したレコードの取得
			// 作成済レコードの存在チェック
			targetObj = getWorkReportSapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "");
			// 作成したレコードが見つからない
			if (Objects.isNull(targetObj)) {
				throw new CoreRuntimeException("not found Sap Object: " + orderNumber);
			}
			//支店コード
			String sapEigyousyoCode = targetObj.get(SapApiConsts.PARAMS_ID_PRCTR).toString();
			//レコード登録日
			String recordDate = targetObj.get(SapApiConsts.PARAMS_ID_ERDAT1).toString();
			//シーケンス番号
			String wfSeqNo = targetObj.get(SapApiConsts.PARAMS_ID_ZSEQNO).toString();
			//更新日
			String lastUpdateDate = targetObj.get(SapApiConsts.PARAMS_ID_AEDAT).toString();
			//更新時間
			String lastUpdateTime = targetObj.get(SapApiConsts.PARAMS_ID_AEZEIT).toString();

			log.info("before date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime + " " + recordDate);
			// Sapへ請書未受領データの排他制御用変更日付、変更時間がEnglishロケールフォーマットのため変換が必要
			Date updateDate = parseDateStringToDate(lastUpdateDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date updateTime = parseDateStringToDate(lastUpdateTime, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date registDate = parseDateStringToDate(recordDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			if (Objects.nonNull(updateDate)) {
				// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				lastUpdateDate = formatDateToString(updateDate, "yyyyMMdd", "JST");
			}
			if (Objects.nonNull(updateTime)) {
				// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				lastUpdateTime = formatDateToString(updateTime, "HH:mm:ss", "JST");
			}
			if (Objects.nonNull(registDate)) {
				// レコード登録日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				recordDate = formatDateToString(registDate, "yyyyMMdd", "JST");
			}
			log.info("after date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime + " " + recordDate);

			// ■■■■■■■■■■■■■ 申請
			result = SapApi.applyDeliveryWorkReport(sapEigyousyoCode, orderNumber, recordDate, wfSeqNo, acceptanceDate, wfNumber, lastUpdateDate, lastUpdateTime, userCode);
			resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
			if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
				throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
			}
		}

		// ■■■■■■■■■■■■■ 決済
		result = SapApi.approveDeliveryWorkReport(wfNumber, approverCode, approveDate, approveDateTime, userCode);
		resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
	}

	private Map<String, Object> getWorkReportSapRecord (String orderNumber, String acceptanceDate, String eigyousyoCode, String userCode, String wfStatus) {
		// ■■■■■■■■■■■■■ 作成したレコードの取得
		Map<String, Object> data = SapApi.getWorkReportWFSeqNo(eigyousyoCode, userCode, wfStatus);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		Object wfobj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
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
		return targetObj;
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
	public void reject(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
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
					tWorkReportDao.updateWf(entity);
					//否認時のメール送信
					sendMailWorkReportReject(entity, form.getApproverComments());
				}
			}
		}
	}
	/** 業者へ差戻 */
	public void sendback(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
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
					tWorkReportDao.updateWf(entity);

					//差し戻し用のメール送信
					String gyousyaCode = entity.getGyousyaCode();
					List<String> addressList = tGyousyaMailaddressDao.selectList(gyousyaCode);
					MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);
					mailService.sendMailWorkReportRemand(addressList, gyousya, entity);
				}
			}
		}
	}

	//納品書Noのエンコード
	public String encodeWorkReportyNumber(WorkReportForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getWorkReportNumber())) {
			ret = encodeWorkReportNumber(form.getWorkReportNumber());
		}
		return ret;
	}

	//納品書Noのエンコード
	public String encodeWorkReportNumber(String workReportNumber) {
		String ret = "";
		if (Objects.nonNull(workReportNumber)) {
			ret = CipherUtils.getEncryptAES(workReportNumber);
		}
		return ret;
	}

	//納品書Noのデコード
	public String decodeWorkReportNumber(WorkReportForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptWorkReportNumber())) {
			ret = decodeWorkReportNumber(form.getEncryptWorkReportNumber());
		}
		return ret;
	}

	//納品書Noのデコード
	public String decodeWorkReportNumber(String encryptNumber) {
		String ret = "";
		if (Objects.nonNull(encryptNumber)) {
			ret = CipherUtils.getDecryptAES(encryptNumber);
		}
		return ret;
	}

	//出来高報告書の受入確認メール再送
	public void remindList(List<TWorkReportEntity> workReportList) {
		if (Objects.nonNull(workReportList)) {
			WorkReportForm form = new WorkReportForm();
			for (TWorkReportEntity workReport : workReportList) {
				form.setWorkReportNumber(workReport.getWorkReportNumber());
				sendMailWorkReport(form, true);
			}
		}
	}

	//出来高報告書登録時にメールを送信
	public void sendMailWorkReport(WorkReportForm form, Boolean remind) {
		String workReportNumber = form.getWorkReportNumber();

		//出来高情報取得
		TWorkReportEntity workReport = get(workReportNumber);
		if (Objects.isNull(workReport)) {
			log.info("workReport nothing: " + workReportNumber);
			return;
		}
		//出来高明細取得
		List<TWorkReportItemEntity> itemList = getItemList(workReportNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(workReport.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(workReport.getGyousyaCode());

		String eigyousyoCode = eigyousyo.getEigyousyoCode();

		String to = getMailTo(syain, eigyousyoCode);
		String cc = getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(workReport);

		//メール送信
		mailService.sendMailWorkReport(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport.getOrderNumber(), workReport.getWorkRate(), fileList, workReportNumber, itemList, remind);
	}

	//出来高報告書ジョブカン申請否認時にメールを送信
	public void sendMailWorkReportReject(TWorkReportEntity workReport, String rejectComments) {

		//出来高情報取得
		if (Objects.isNull(workReport)) {
			log.info("workReport nothing in sendMailWorkReportReject");
			return;
		}

		String workReportNumber = workReport.getWorkReportNumber();
		String gyousyaCode = workReport.getGyousyaCode();

		//出来高報告書明細取得
		List<TWorkReportItemEntity> itemList = getItemList(workReportNumber);
		//工事情報取得
		MKoujiEntity kouji = mKoujiDao.select(workReport.getKoujiCode());
		//支店情報取得
		MEigyousyoEntity eigyousyo = mEigyousyoDao.select(kouji.getEigyousyoCode());
		//社員情報取得
		MSyainEntity syain = mSyainDao.select(kouji.getTantouSyainCode());
		//業者情報取得
		MGyousyaEntity gyousya = mGyousyaDao.select(gyousyaCode);

		String eigyousyoCode = eigyousyo.getEigyousyoCode();

		String to = getMailTo(syain, eigyousyoCode);
		String cc = getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(workReport);
		//メール送信
		mailService.sendMailWorkReportReject(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport.getOrderNumber(), workReport.getWorkRate(), fileList, workReportNumber, itemList, rejectComments);
	}

	private List<Map<String,String>> getAttachedFile(TWorkReportEntity workReport) {
		//添付ファイル
		List<Map<String,String>> fileList = new ArrayList<Map<String,String>>();
		if (Objects.nonNull(workReport.getFileId())) {
			String fileName = workReport.getFileId() + ".pdf";
			String filePath = FileApi.getFile(workReport.getKoujiCode(), FileApi.TOSHO_CODE_EDI, FileApi.FILE_CODE_FORM, FileApi.FILE_NO_WORK_REPORT, workReport.getFileId(), "pdf", fileName);
			//ファイル名をわかりやすい名前に変更して添付
			fileName = "出来高報告書.pdf";

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
