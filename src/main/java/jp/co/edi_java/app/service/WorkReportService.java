package jp.co.edi_java.app.service;

import java.math.BigDecimal;
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
import jp.co.edi_java.app.entity.TOrderItemEntity;
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
	private static String SUM_SEQNO_CODE = "000";
	private static String LIMIT_WORK_RATE = "100";
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

	public String checkUnconfirmWorkReport(String orderNumber) {
		List<TWorkReportEntity> entityList = tWorkReportDao.checkUnconfirmList(orderNumber);
		String workReportNumber = "";
		if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
			workReportNumber = entityList.get(0).getWorkReportNumber();
		}
		return workReportNumber;
	}

	public TWorkReportEntity getByOrderNumber(String orderNumber, int workReportCount) {
		return tWorkReportDao.selectByOrderNumber(orderNumber, workReportCount);
	}

	public List<TWorkReportEntity> selectListByMultiOrder(List<String> orderNumberList, String remandFlg) {
		return tWorkReportDao.selectListByMultiOrder(orderNumberList, remandFlg);
	}

	public List<TWorkReportItemEntity> getItemList(String workReportNumber) {
		return tWorkReportItemDao.selectAll(workReportNumber);
	}

	public List<WorkReportDto> getList(String orderNumber, String remandFlg) {
		List<WorkReportDto> dtoList = new ArrayList<>();
		List<TWorkReportEntity> list = tWorkReportDao.selectList(orderNumber, remandFlg);
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
		List<TOrderItemEntity> orderItemList = orderService.selectOrderItem(orderNumber);
		Map<String, TOrderItemEntity> orderItemMap = new HashMap<String, TOrderItemEntity>();

		for (TOrderItemEntity orderItem : orderItemList) {
			String itemName = "";
			if (Objects.nonNull(orderItem.getItemName())) {
				itemName = orderItem.getItemName();
				itemName = itemName.replaceAll("\t", " ");
				itemName = itemName.trim();
			}
			String netprStr = "";
			if (Objects.nonNull(orderItem.getOrderUnitPrice())) {
				netprStr = orderItem.getOrderUnitPrice().toString();
			}
			String qtyStr = "0";
			if (Objects.nonNull(orderItem.getOrderQuantity())) {
				qtyStr = String.valueOf(orderItem.getOrderQuantity());
			}
			String unit = "";
			if (Objects.nonNull(orderItem.getUnit())) {
				unit = orderItem.getUnit().trim();
			}
			String mapkey = itemName + "_"
					+ netprStr + "_"
					+ qtyStr + "_"
					+ unit;
			orderItemMap.put(mapkey, orderItem);
		}

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
			String lineNo = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_EBELP))) {
				lineNo = itemMap.get(SapApiConsts.PARAMS_ID_EBELP).toString();
			}
			int itemNumber = Integer.valueOf(lineNo);

			String itemName = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_TXZ01))) {
				itemName = itemMap.get(SapApiConsts.PARAMS_ID_TXZ01).toString();
				itemName = itemName.replaceAll("\t", " ");
				itemName = itemName.trim();
			}
			String qtyStr = "0";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_ZHTMNG))) {
				qtyStr = itemMap.get(SapApiConsts.PARAMS_ID_ZHTMNG).toString().trim();
			}
			if (!qtyStr.isEmpty()) {
				qtyStr = qtyStr.replaceAll(",", "");
			}
			double qtyDbl = Double.valueOf(qtyStr);
			qtyStr = String.valueOf(qtyDbl);
			String unit = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_MEINS))) {
				unit = itemMap.get(SapApiConsts.PARAMS_ID_MEINS).toString().trim();
			}
			String netprStr = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_NETPR))) {
				netprStr = itemMap.get(SapApiConsts.PARAMS_ID_NETPR).toString().trim();
			}
			BigDecimal netpr = BigDecimal.ZERO;
			if (!netprStr.isEmpty()) {
				netprStr = netprStr.replaceAll(",", "");
				netpr = new BigDecimal(netprStr);
			}
			String mapkey = itemName + "_"
					+ netprStr + "_"
					+ qtyStr + "_"
					+ unit;
			TOrderItemEntity orderItem = orderItemMap.get(mapkey);

			TWorkReportItemEntity dtl = new TWorkReportItemEntity();
			// 明細の転記
			//品目コード
			dtl.setItemNumber(itemNumber);
			//明細ID
			dtl.setJcoEbelp(lineNo);
			//品目名
			dtl.setItemName(itemName);
			//発注数量
			dtl.setOrderQuantity(qtyDbl);
			//単位
			dtl.setUnit(unit);
			//単価
			dtl.setOrderUnitPrice(netpr);

			//金額
			if (Objects.nonNull(orderItem)) {
				dtl.setOrderAmount(orderItem.getOrderAmount());
				dtl.setOrderAmountTax(orderItem.getOrderAmountTax());
			} else {
				BigDecimal qty = new BigDecimal(qtyStr);
				BigDecimal amount = netpr.multiply(qty);
				amount = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
				dtl.setOrderAmount(amount);
				BigDecimal tax = amount.multiply(new BigDecimal("0.10"));
				tax = tax.setScale(0, BigDecimal.ROUND_DOWN);
				dtl.setOrderAmountTax(tax);
			}
			ret.add(dtl);
		}
		return ret;
	}

	//出来高書リマインド対象のリスト取得
	public List<TWorkReportEntity> selectRemindList(boolean forceAll) {


		//本日の日付を取得
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 当日
		Date today = cal.getTime();
		// 月末日
		int endDay = cal.getActualMaximum(Calendar.DATE);
		// 昨日が納品日のものを検索するため昨日取得
		cal.add(Calendar.DATE, -1);
		Date workReportDate = cal.getTime();
		String workReportDateStr = formatDateToString(workReportDate, "yyyy/MM/dd", "JST");
		// 月末日のDateを取得
		cal.set(Calendar.DATE, endDay);
		Date endDateMonth = cal.getTime();
		// 月末日から3日前のDateを取得
		cal.set(Calendar.DATE, endDay-3);
		Date beforeEndDate3 = cal.getTime();

		Calendar from = Calendar.getInstance();
		from.setTime(beforeEndDate3);

		Calendar to = Calendar.getInstance();
		to.setTime(endDateMonth);

		log.info("today: " + today.toString() + " workReportDate: " + workReportDate.toString() + " from: " + beforeEndDate3.toString() + " to: " + endDateMonth.toString());

		List<TWorkReportEntity> workReportListTmp1 = new ArrayList<TWorkReportEntity>();
		List<TWorkReportEntity> workReportListTmp2 = new ArrayList<TWorkReportEntity>();
		List<TWorkReportEntity> workReportList = new ArrayList<TWorkReportEntity>();

//		// 当日が月末日3日前から月末日までの期間かどうか
//		cal.setTime(today);
//		if ((cal.compareTo(from) > 0 && cal.compareTo(to) <= 0) || forceAll) {
//			workReportListTmp1 = tWorkReportDao.selectUnconfirmList(null);
//		}
//		// 納品日が月末日3日前から月末の期間外か
//		cal.setTime(workReportDate);
//		if (!(cal.compareTo(from) > 0 && cal.compareTo(to) <= 0)) {
//			workReportListTmp2 = tWorkReportDao.selectUnconfirmList(workReportDateStr);
//		}
		if (forceAll) {
			workReportListTmp1 = tWorkReportDao.selectUnconfirmList(null);
		} else {
			// 納品日の翌日のみリマインドするように変更
			workReportListTmp2 = tWorkReportDao.selectUnconfirmList(workReportDateStr);
		}
		Map<String, Boolean> exists = new HashMap<String, Boolean>();
		for (TWorkReportEntity entity : workReportListTmp1) {
			String workReportNumber = entity.getWorkReportNumber();
			if (!exists.containsKey(workReportNumber)) {
				exists.put(workReportNumber,true);
				workReportList.add(entity);
			}
		}
		for (TWorkReportEntity entity : workReportListTmp2) {
			String workReportNumber = entity.getWorkReportNumber();
			if (!exists.containsKey(workReportNumber)) {
				exists.put(workReportNumber,true);
				workReportList.add(entity);
			}
		}

		return workReportList;
	}

	//担当社員別納品書リマインド対象のリスト取得
	public List<TWorkReportEntity> selectRemindListBySyain(String eigyousyoCode, String syainCode) {

		List<TWorkReportEntity> workReportList = new ArrayList<TWorkReportEntity>();
		workReportList = tWorkReportDao.selectUnconfirmListBySyain(eigyousyoCode,syainCode);

		return workReportList;
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
		entity.setCompleteFlg("1");
		if (entity.getWorkRate() < 100) {
			entity.setCompleteFlg("0");
		}
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

	public int softdelete(WorkReportForm form) {
		TWorkReportEntity entity = this.get(form.workReportNumber);
		int count = 0;
		if (Objects.nonNull(entity)) {
			entity.setDeleteFlg("1");
			//entity.setFileId(null);
			entity.setUpdateUser(form.getUserId());
			count = tWorkReportDao.softdelete(entity);
			List<TWorkReportItemEntity> detail = this.getItemList(form.workReportNumber);
			if (Objects.nonNull(detail)) {
				for (TWorkReportItemEntity item : detail) {
					item.setDeleteFlg("1");
					item.setUpdateUser(form.getUserId());
					tWorkReportItemDao.softdelete(item);
				}
			}
		}
		return count;
	}

	/** ジョブカン承認待ち */
	public List<TWorkReportEntity> apply(WorkReportForm form) {
		List<ApprovalDto> approvalList = form.getWorkReportApprovalList();
		List<TWorkReportEntity> errorList = new ArrayList<TWorkReportEntity>();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TWorkReportEntity entity = this.get(dto.getWorkNumber());
				MKoujiEntity kouji = mKoujiDao.select(entity.getKoujiCode());
				String syainCode = kouji.getTantouSyainCode();
				//施工担当社員
			    MSyainEntity syain = mSyainDao.select(syainCode);
			    if (Objects.nonNull(syain.getTaisyokuFlg()) && syain.getTaisyokuFlg() == 1) {
			    	//退職者のため申請不能
			    	errorList.add(entity);
			    	continue;
			    }
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
					applyToJobcan(entity, kouji, syain);
				}
			}
		}
		return errorList;
	}
	/** ジョブカン申請 */
	private void applyToJobcan(TWorkReportEntity workReport, MKoujiEntity kouji, MSyainEntity syain) {
		String workReportNumber = workReport.getWorkReportNumber();
		String koujiCode = workReport.getKoujiCode();
		String orderNumber = workReport.getOrderNumber();
		String encryptNumber = encodeWorkReportNumber(workReportNumber);
		String gyousyaCode = workReport.getGyousyaCode();
		String saimokuKousyuCode = workReport.getSaimokuKousyuCode();

		//工事情報
	    //MKoujiEntity kouji = mKoujiDao.select(koujiCode);
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
	    //MSyainEntity syain = mSyainDao.select(syainCode);
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
		// 受入日は受入リンクをクリックした申請日 => 受入日は納品日
		// String acceptanceDate = form.getApproveDate();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//String acceptanceDate = sdf.format(workReport.getStaffReceiptDate());

		String workReportDate = workReport.getWorkReportDate();
		String acceptanceDate = workReportDate.replaceAll("/", "");

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
				createWorkReportSapRecord(eigyousyoCode, orderNumber, acceptanceDate, null, null, null, workRate, userCode);
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
				createWorkReportSapRecord(eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateTime, workRate, userCode);
			}
			// ■■■■■■■■■■■■■ 作成したレコードの取得
			// 作成済レコードの存在チェック
			targetObj = getWorkReportSapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "");
			// 作成したレコードが見つからない
			if (Objects.isNull(targetObj)) {
				throw new CoreRuntimeException("not found Sap Object: " + orderNumber + " " + acceptanceDate + " " + eigyousyoCode + " " + userCode);
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


	private void createWorkReportSapRecord(String eigyousyoCode, String orderNumber, String acceptanceDate, String wfSeqNo, String lastUpdateDate, String lastUpdateTime, String workRate, String userCode) {
		// ■■■■■■■■■■■■■ 受入確認モジュールでレコード作成

		// 詳細
		Map<String, Object> result = SapApi.getWorkReportItemList(orderNumber, userCode);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		// SAP workRate 100%, EDI workRate 100%  の場合は既に決裁済と判断
		Object tE04003 = result.get(SapApiConsts.PARAMS_KEY_T_E_04003);
		List<Map<String, Object>> sapTE04003List = new ArrayList<Map<String, Object>>();
		if(tE04003 instanceof List) {
			sapTE04003List = (List<Map<String, Object>>)tE04003;
		}else {
			sapTE04003List.add((Map<String, Object>)tE04003);
		}
		// SAPの明細データを取得できない
		if (Objects.isNull(sapTE04003List) || sapTE04003List.isEmpty()) {
			throw new CoreRuntimeException("SAP workreport detail data TE04003 is empty");
		}
		for (Map<String, Object> sapTE04003 : sapTE04003List) {
			if (Objects.nonNull(sapTE04003.get(SapApiConsts.PARAMS_ID_ZSEQNO)) && Objects.equals(sapTE04003.get(SapApiConsts.PARAMS_ID_ZSEQNO).toString(), SUM_SEQNO_CODE)) {
				if (Objects.nonNull(sapTE04003.get(SapApiConsts.PARAMS_ID_ZSATEIRT)) && Objects.equals(sapTE04003.get(SapApiConsts.PARAMS_ID_ZSATEIRT).toString(), LIMIT_WORK_RATE)) {
					if (Objects.nonNull(workRate) && Objects.equals(workRate, LIMIT_WORK_RATE)) {
						throw new CoreRuntimeException("Probably completed with SAP (WorkReport): " + orderNumber);
					}
				}
				break;
			}
		}

		Object tE04004 = result.get(SapApiConsts.PARAMS_KEY_T_E_04004);
		List<Map<String, Object>> sapDetailData = new ArrayList<Map<String, Object>>();
		if(tE04004 instanceof List) {
			sapDetailData = (List<Map<String, Object>>)tE04004;
		}else {
			sapDetailData.add((Map<String, Object>)tE04004);
		}
		// SAPの明細データを取得できない
		if (Objects.isNull(sapDetailData) || sapDetailData.isEmpty()) {
			throw new CoreRuntimeException("SAP workreport detail data is empty");
		}

		// 受入入力レコード作成用にデータ詰め替え
		List<Map<String, String>> sapDetail = new ArrayList<Map<String, String>>();
		for (Map<String, Object> sapMap : sapDetailData) {

			Map<String, String> params = new HashMap<String,String>();

			// 発注金額
			String zhtkgkStr = "0";
			BigDecimal zhtkgk = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZHTKGK))) {
				zhtkgkStr = sapMap.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString().trim();
				log.info("ZHTKGK: " + zhtkgkStr);
				if (!zhtkgkStr.isEmpty()) {
					zhtkgkStr = zhtkgkStr.replaceAll(",", "");
					zhtkgk = new BigDecimal(zhtkgkStr);
				}
			}
			// 単価
			String netprStr = "0";
			BigDecimal netpr = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_NETPR))) {
				netprStr = sapMap.get(SapApiConsts.PARAMS_ID_NETPR).toString().trim();
				log.info("NETPR: " + netprStr);
				if (!netprStr.isEmpty()) {
					netprStr = netprStr.replaceAll(",", "");
					netpr = new BigDecimal(netprStr);
				}
			}
			// 納入金額
			String sumprStr = "0";
			BigDecimal sumpr = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_SUMPR))) {
				sumprStr = sapMap.get(SapApiConsts.PARAMS_ID_SUMPR).toString().trim();
				log.info("SUMPR: " + sumprStr);
				if (!sumprStr.isEmpty()) {
					sumprStr = sumprStr.replaceAll(",", "");
					sumpr = new BigDecimal(sumprStr);
				}
			}
			// 納入受入残金額
			String zukzknStr = "0";
			BigDecimal zukzkn = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZUKZKN))) {
				zukzknStr = sapMap.get(SapApiConsts.PARAMS_ID_ZUKZKN).toString().trim();
				log.info("ZUKZKN: " + zukzknStr);
				if (!zukzknStr.isEmpty()) {
					zukzknStr = zukzknStr.replaceAll(",", "");
					zukzkn = new BigDecimal(zukzknStr);
				}
			}
			// 発注数量
			String zhtmngStr = "0";
			BigDecimal zhtmng = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZHTMNG))) {
				zhtmngStr = sapMap.get(SapApiConsts.PARAMS_ID_ZHTMNG).toString().trim();
				log.info("ZHTMNG: " + zhtmngStr);
				if (!zhtmngStr.isEmpty()) {
					zhtmngStr = zhtmngStr.replaceAll(",", "");
					zhtmng = new BigDecimal(zhtmngStr);
				}
			}
			// 発注残数量
			String mengeStr = "0";
			BigDecimal menge = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_MENGE))) {
				mengeStr = sapMap.get(SapApiConsts.PARAMS_ID_MENGE).toString().trim();
				log.info("MENGE: " + mengeStr);
				if (!mengeStr.isEmpty()) {
					mengeStr = mengeStr.replaceAll(",", "");
					menge = new BigDecimal(mengeStr);
				}
			}
			// 納入数量
			String zmengeStr = "0";
			BigDecimal zmenge = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZMENGE))) {
				zmengeStr = sapMap.get(SapApiConsts.PARAMS_ID_ZMENGE).toString().trim();
				log.info("ZMENGE " + zmengeStr);
				if (!zmengeStr.isEmpty()) {
					zmengeStr = zmengeStr.replaceAll(",", "");
					zmenge = new BigDecimal(zmengeStr);
				}
			}

			// 品目コード
			params.put(SapApiConsts.PARAMS_ID_MATNR, sapMap.get(SapApiConsts.PARAMS_ID_MATNR).toString());
			// テキスト(短)
			params.put(SapApiConsts.PARAMS_ID_TXZ01, sapMap.get(SapApiConsts.PARAMS_ID_TXZ01).toString());
			// 仕様名
			params.put(SapApiConsts.PARAMS_ID_ZMHNAM, sapMap.get(SapApiConsts.PARAMS_ID_ZMHNAM).toString());
			// 発注残数量
			params.put(SapApiConsts.PARAMS_ID_MENGE, menge.toString());
			// 納入数量
			params.put(SapApiConsts.PARAMS_ID_ZMENGE, zmenge.toString());
			// 発注単位
			params.put(SapApiConsts.PARAMS_ID_MEINS, sapMap.get(SapApiConsts.PARAMS_ID_MEINS).toString());
			// 単価
			params.put(SapApiConsts.PARAMS_ID_NETPR, netpr.toString());
			// 納入金額
			params.put(SapApiConsts.PARAMS_ID_SUMPR, sumpr.toString());
			// 購買伝票の明細番号
			params.put(SapApiConsts.PARAMS_ID_EBELP, sapMap.get(SapApiConsts.PARAMS_ID_EBELP).toString());
			// 発注数量
			params.put(SapApiConsts.PARAMS_ID_ZHTMNG, zhtmng.toString());
			// 単位コード
			params.put(SapApiConsts.PARAMS_ID_ZTANIC, sapMap.get(SapApiConsts.PARAMS_ID_ZTANIC).toString());
			// 発注金額
			params.put(SapApiConsts.PARAMS_ID_ZHTKGK, zhtkgk.toString());
			// 納入受入残金額
			params.put(SapApiConsts.PARAMS_ID_ZUKZKN, zukzkn.toString());

			sapDetail.add(params);
		}

		if (Objects.nonNull(wfSeqNo) && Objects.nonNull(lastUpdateDate) && Objects.nonNull(lastUpdateTime)) {
			// ■■■■■■■■■■■■■ 受入入力レコードの上書き
			result = SapApi.setWorkReportItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateTime, workRate, sapDetail, userCode);
		} else {
			// ■■■■■■■■■■■■■ 受入入力レコードの新規作成
			result = SapApi.setWorkReportItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, workRate, sapDetail, userCode);
		}
		resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
	}

	private Map<String, Object> getWorkReportSapRecord (String orderNumber, String acceptanceDate, String eigyousyoCode, String userCode, String wfStatus) {
		// ■■■■■■■■■■■■■ 作成したレコードの取得
		Map<String, Object> targetObj = null;
		Map<String, Object> data = SapApi.getWorkReportWFSeqNo(eigyousyoCode, userCode, wfStatus);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
			return targetObj;
		}
		Object wfobj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		List<Map<String, Object>> applyTargetList = new ArrayList<>();
		if(wfobj instanceof List) {
			applyTargetList = (List<Map<String, Object>>)wfobj;
		} else {
			applyTargetList.add((Map<String, Object>)wfobj);
		}

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

	//出来高報告書Noのエンコード
	public String encodeWorkReportyNumber(WorkReportForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getWorkReportNumber())) {
			ret = encodeWorkReportNumber(form.getWorkReportNumber());
		}
		return ret;
	}

	//出来高報告書Noのエンコード
	public String encodeWorkReportNumber(String workReportNumber) {
		String ret = "";
		if (Objects.nonNull(workReportNumber)) {
			ret = CipherUtils.getEncryptAES(workReportNumber);
		}
		return ret;
	}

	//出来高報告書Noのデコード
	public String decodeWorkReportNumber(WorkReportForm form) {
		String ret = "";
		if (Objects.nonNull(form) && Objects.nonNull(form.getEncryptWorkReportNumber())) {
			ret = decodeWorkReportNumber(form.getEncryptWorkReportNumber());
		}
		return ret;
	}

	//出来高報告書Noのデコード
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(workReport);

		//メール送信
		mailService.sendMailWorkReport(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport, fileList, itemList, remind);
	}

	//出来高報告書取消時にメールを送信
	public void sendMailWorkReportCancel(WorkReportForm form) {
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);

		//メール送信
		mailService.sendMailWorkReportCancel(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport, itemList);
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(workReport);
		//メール送信
		mailService.sendMailWorkReportReject(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), workReport, fileList, itemList, rejectComments);
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

}
