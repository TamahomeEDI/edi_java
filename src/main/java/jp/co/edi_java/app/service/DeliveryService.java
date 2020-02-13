package jp.co.edi_java.app.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.edi_java.app.util.common.CommonUtils;
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

	public String checkUnconfirmDelivery(String orderNumber) {
		List<TDeliveryEntity> entityList = tDeliveryDao.checkUnconfirmList(orderNumber);
		String deliveryNumber = "";
		if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
			deliveryNumber = entityList.get(0).getDeliveryNumber();
		}
		return deliveryNumber;
	}

	public TDeliveryEntity getByOrderNumber(String orderNumber, int deliveryCount) {
		return tDeliveryDao.selectByOrderNumber(orderNumber, deliveryCount);
	}

	public List<TDeliveryEntity> selectListByMultiOrder(List<String> orderNumberList, String remandFlg) {
		return tDeliveryDao.selectListByMultiOrder(orderNumberList, remandFlg);
	}

	public List<TDeliveryItemEntity> getItemList(String deliveryNumber) {
		return tDeliveryItemDao.selectAll(deliveryNumber);
	}

	public List<DeliveryDto> getList(String orderNumber, String remandFlg) {
		List<DeliveryDto> dtoList = new ArrayList<>();
		List<TDeliveryEntity> list = tDeliveryDao.selectList(orderNumber, remandFlg);
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

			TDeliveryItemEntity dtl = new TDeliveryItemEntity();
			// 明細の転記
			//品目コード
			dtl.setItemNumber(itemNumber);
			//明細ID
			dtl.setJcoEbelp(lineNo);
			//品目名
			dtl.setItemName(itemName);
			//発注数量
			dtl.setOrderQuantity(qtyDbl);
			//納品数量
			String deliveryQty = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_ZMENGE))) {
				deliveryQty = itemMap.get(SapApiConsts.PARAMS_ID_ZMENGE).toString();
			}
			dtl.setDeliveryQuantity(Double.valueOf(deliveryQty));
			//発注残数量
			String deliveryRemainQty = "";
			if (Objects.nonNull(itemMap.get(SapApiConsts.PARAMS_ID_MENGE))) {
				deliveryRemainQty = itemMap.get(SapApiConsts.PARAMS_ID_MENGE).toString();
			}
			dtl.setDeliveryRemainingQuantity(Double.valueOf(deliveryRemainQty));
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

	//納品書リマインド対象のリスト取得
	public List<TDeliveryEntity> selectRemindList(boolean forceAll) {

		//本日の日付を取得
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 当日
		Date today = cal.getTime();
		// 月末日
		int endDay = cal.getActualMaximum(Calendar.DATE);
		// 昨日が納品日のものを検索するため昨日取得
		cal.add(Calendar.DATE, -1);
		Date deliveryDate = cal.getTime();
		String deliveryDateStr = CommonUtils.formatDateToString(deliveryDate, "yyyy/MM/dd", "JST");
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

		log.info("today: " + today.toString() + " deliveryDate: " + deliveryDate.toString() + " from: " + beforeEndDate3.toString() + " to: " + endDateMonth.toString());

		List<TDeliveryEntity> deliveryListTmp1 = new ArrayList<TDeliveryEntity>();
		List<TDeliveryEntity> deliveryListTmp2 = new ArrayList<TDeliveryEntity>();
		List<TDeliveryEntity> deliveryList = new ArrayList<TDeliveryEntity>();

		// 当日が月末日3日前から月末日の間かどうか
//		cal.setTime(today);
//		if ((cal.compareTo(from) > 0 && cal.compareTo(to) <= 0) || forceAll) {
//			deliveryListTmp1 = tDeliveryDao.selectUnconfirmList(null);
//		}
//		// 納品日が月末日3日前から月末の期間外か
//		cal.setTime(deliveryDate);
//		if (!(cal.compareTo(from) > 0 && cal.compareTo(to) <= 0)) {
//			deliveryListTmp2 = tDeliveryDao.selectUnconfirmList(deliveryDateStr);
//		}
		if (forceAll) {
			deliveryListTmp1 = tDeliveryDao.selectUnconfirmList(null);
		} else {
			// 納品日の翌日にリマインドするのみに変更
			deliveryListTmp2 = tDeliveryDao.selectUnconfirmList(deliveryDateStr);
		}
		Map<String, Boolean> exists = new HashMap<String, Boolean>();
		for (TDeliveryEntity entity : deliveryListTmp1) {
			String deliveryNumber = entity.getDeliveryNumber();
			if (!exists.containsKey(deliveryNumber)) {
				exists.put(deliveryNumber,true);
				deliveryList.add(entity);
			}
		}
		for (TDeliveryEntity entity : deliveryListTmp2) {
			String deliveryNumber = entity.getDeliveryNumber();
			if (!exists.containsKey(deliveryNumber)) {
				exists.put(deliveryNumber,true);
				deliveryList.add(entity);
			}
		}

		return deliveryList;
	}

	//担当社員別納品書リマインド対象のリスト取得
	public List<TDeliveryEntity> selectRemindListBySyain(String eigyousyoCode, String syainCode) {

		List<TDeliveryEntity> deliveryList = new ArrayList<TDeliveryEntity>();
		deliveryList = tDeliveryDao.selectUnconfirmListBySyain(eigyousyoCode,syainCode);

		return deliveryList;
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
		entity.setCompleteFlg("1");
		for (TDeliveryItemEntity item : form.itemList) {
			if (item.getDeliveryRemainingQuantity() > 0) {
				entity.setCompleteFlg("0");
			}
		}
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

	public int softdelete(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
		int count = 0;
		if (Objects.nonNull(entity)) {
			entity.setDeleteFlg("1");
			//entity.setFileId(null);
			entity.setUpdateUser(form.getUserId());
			count = tDeliveryDao.softdelete(entity);
			List<TDeliveryItemEntity> detail = this.getItemList(form.deliveryNumber);
			if (Objects.nonNull(detail)) {
				for (TDeliveryItemEntity item : detail) {
					item.setDeleteFlg("1");
					item.setUpdateUser(form.getUserId());
					tDeliveryItemDao.softdelete(item);
				}
			}
		}
		return count;
	}

	public int delete(DeliveryForm form) {
		TDeliveryEntity entity = this.get(form.deliveryNumber);
		return tDeliveryDao.delete(entity);
	}

	/** ジョブカン承認待ち */
	public List<TDeliveryEntity> apply(DeliveryForm form) {
		List<ApprovalDto> approvalList = form.getDeliveryApprovalList();
		List<TDeliveryEntity> errorList = new ArrayList<TDeliveryEntity>();
		if(approvalList != null) {
			for (ApprovalDto dto : approvalList) {
				TDeliveryEntity entity = this.get(dto.getWorkNumber());
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
					tDeliveryDao.updateWf(entity);
					applyToJobcan(entity,kouji,syain);
				}
			}
		}
		return errorList;
	}
	/** ジョブカン申請 */
	private void applyToJobcan(TDeliveryEntity delivery, MKoujiEntity kouji, MSyainEntity syain) {
		String deliveryNumber = delivery.getDeliveryNumber();
		String koujiCode = delivery.getKoujiCode();
		String orderNumber = delivery.getOrderNumber();
		String encryptNumber = encodeDeliveryNumber(deliveryNumber);
		String gyousyaCode = delivery.getGyousyaCode();
		String saimokuKousyuCode = delivery.getSaimokuKousyuCode();

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
					//Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String approveDate = form.getApproveDate() + " " + form.getApproveDateTime();
					Date receiptDate = CommonUtils.parseDateStringToDate(approveDate, "yyyyMMdd HHmmss", Locale.ENGLISH);
					Timestamp timestamp = new Timestamp(receiptDate.getTime());
					log.info("approveDate: " + approveDate);
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

	private String calcAcceptanceDate(String acceptanceDate) {
		Calendar cal = Calendar.getInstance();
		String resultDate = "";
		// 納品日
		Date actTmp = CommonUtils.parseDateStringToDate(acceptanceDate, "yyyy/MM/dd", Locale.ENGLISH);
		cal.setTime(actTmp);
		Date actDate = cal.getTime();
		log.info("acceptanceDate: " + acceptanceDate + " ,actdate: " + actDate.toString());
		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 当日
		Date today = cal.getTime();
		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 月末日
		int endDay = cal.getActualMaximum(Calendar.DATE);
		//当月1日を取得
		cal.set(Calendar.DATE, startDay);
		Date from = cal.getTime();
		// 月末日の翌日を取得
		cal.set(Calendar.DATE, endDay+1);
		Date to = cal.getTime();

		// 受入日の算出
		resultDate = CommonUtils.formatDateToString(nowDate, "yyyyMMdd", "JST");
		log.info("compareToFrom: " + String.valueOf(actDate.compareTo(from)));
		log.info("compareToTo: " + String.valueOf(actDate.compareTo(to)));
		if (actDate.compareTo(from) > 0 && actDate.compareTo(to) <= 0) {
			resultDate = CommonUtils.formatDateToString(actDate, "yyyyMMdd", "JST");
		}
		log.info("today: " + today.toString() + " acceptanceDate: " + resultDate.toString() + " from: " + from.toString() + " to: " + to.toString());
		return resultDate;
	}


	/** SAP連携 */
	private void approveSap(TDeliveryEntity delivery, DeliveryForm form) {
		String deliveryNumber = delivery.getDeliveryNumber();
		String orderNumber = delivery.getOrderNumber();
		String userCode = form.getUserId();
		//工事情報
	    MKoujiEntity kouji = mKoujiDao.select(delivery.getKoujiCode());
		String eigyousyoCode = kouji.getEigyousyoCode();

		// 受入日は受入リンクをクリックした申請日 => 受入日は納品日
		// String acceptanceDate = form.getApproveDate();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//String acceptanceDate = sdf.format(delivery.getStaffReceiptDate());
		String deliveryDate = delivery.getDeliveryDate();
		String acceptanceDate = calcAcceptanceDate(deliveryDate);

		String approverCode = form.getApproverCode();
		String approveDate = form.getApproveDate();
		String approveDateTime = form.getApproveDateTime();

		// 明細情報取得
		List<TDeliveryItemEntity> itemList = getItemList(deliveryNumber);
		//JCO result
		Map<String, Object> result = null;
		Map<String, Object> resultInfo = null;
		//申請対象
		Map<String, Object> targetObj = null;

		//申請番号ダミー
		String wfNumber = deliveryNumber + "_" + approveDate + approveDateTime;

		// ■■■■■■■■■■■■■ 受入入力レコードの存在チェック
		// 申請済レコードの存在チェック
		targetObj = getDeliverySapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "0");

		if (Objects.isNull(targetObj)) {
			// 未申請レコードの存在チェック
			targetObj = getDeliverySapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "");
			boolean createSapRecordFlg = false;
			if (Objects.isNull(targetObj)) {
				// ■■■■■■■■■■■■■ 受入入力レコードの作成
				createSapRecordFlg = createDeliverySapRecord(eigyousyoCode, orderNumber, acceptanceDate, null, null, null, itemList, userCode);
			} else {
				//シーケンス番号
				String wfSeqNo = targetObj.get(SapApiConsts.PARAMS_ID_ZSEQNO).toString();
				//更新日
				String lastUpdateDate = targetObj.get(SapApiConsts.PARAMS_ID_AEDAT).toString();
				//更新時間
				String lastUpdateTime = targetObj.get(SapApiConsts.PARAMS_ID_AEZEIT).toString();
				log.info("before date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime);
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
				log.info("after date formatting: " + wfNumber + " " + orderNumber + " " + wfSeqNo + " " + lastUpdateDate + " " + lastUpdateTime);
				// ■■■■■■■■■■■■■ 受入入力レコードの上書き
				createSapRecordFlg = createDeliverySapRecord(eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateTime, itemList, userCode);
			}
			// ■■■■■■■■■■■■■ レコードを作成していない場合
			if (!createSapRecordFlg) {
				return;
			}
			// ■■■■■■■■■■■■■ 作成したレコードの取得
			// 作成済レコードの存在チェック
			targetObj = getDeliverySapRecord(orderNumber, acceptanceDate, eigyousyoCode, userCode, "");
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
			Date updateDate = CommonUtils.parseDateStringToDate(lastUpdateDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date updateTime = CommonUtils.parseDateStringToDate(lastUpdateTime, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date registDate = CommonUtils.parseDateStringToDate(recordDate, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			if (Objects.nonNull(updateDate)) {
				// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				lastUpdateDate = CommonUtils.formatDateToString(updateDate, "yyyyMMdd", "JST");
			}
			if (Objects.nonNull(updateTime)) {
				// 排他制御用変更日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				lastUpdateTime = CommonUtils.formatDateToString(updateTime, "HH:mm:ss", "JST");
			}
			if (Objects.nonNull(registDate)) {
				// レコード登録日付、変更時間がJSTのため、UTCエポックで9時間マイナスと時間がずれてしまうためタイムゾーン指定
				recordDate = CommonUtils.formatDateToString(registDate, "yyyyMMdd", "JST");
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

	private boolean createDeliverySapRecord(String eigyousyoCode, String orderNumber, String acceptanceDate, String wfSeqNo, String lastUpdateDate, String lastUpdateTime, List<TDeliveryItemEntity> itemList, String userCode) {
		boolean createSapRecordFlg = true;
		// ■■■■■■■■■■■■■ 受入確認モジュールでレコード作成
		// EDIの明細データを取得できない
		if (Objects.isNull(itemList) || itemList.isEmpty()) {
			throw new CoreRuntimeException("EDI delivery detail data is empty");
		}

		// 詳細
		Map<String, Object> result = SapApi.getDeliveryItemList(orderNumber, userCode);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		Object obj = result.get(SapApiConsts.PARAMS_KEY_T_E_04003);
		List<Map<String, Object>> sapDetailData = new ArrayList<Map<String, Object>>();
		if(obj instanceof List) {
			sapDetailData = (List<Map<String, Object>>)obj;
		}else {
			sapDetailData.add((Map<String, Object>)obj);
		}
		// SAPの明細データを取得できない
		if (Objects.isNull(sapDetailData) || sapDetailData.isEmpty()) {
			throw new CoreRuntimeException("SAP delivery detail data is empty");
		}

		//業者の作成した受入入力データ
		Map<String, TDeliveryItemEntity> detailMap = new HashMap<String, TDeliveryItemEntity>();
		for (TDeliveryItemEntity itm : itemList) {
			if (Objects.nonNull(itm.getJcoEbelp())) {
				detailMap.put(itm.getJcoEbelp(), itm);
			}
		}
		// 受入入力レコード作成用にデータ詰め替え
		List<Map<String, String>> sapDetail = new ArrayList<Map<String, String>>();
		for (Map<String, Object> sapMap : sapDetailData) {
			String lineNo = sapMap.get(SapApiConsts.PARAMS_ID_EBELP).toString();
			if (!detailMap.containsKey(lineNo)) {
				throw new CoreRuntimeException("not found SAP delivery detail data in EDI DeliveryItem : " + orderNumber + " , JCO_EBELP : " + lineNo);
			}
			TDeliveryItemEntity itm = detailMap.get(lineNo);
			Map<String, String> params = new HashMap<String,String>();

			// 単価
			String netprStr = "0";
			BigDecimal netpr = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_NETPR))) {
				netprStr = sapMap.get(SapApiConsts.PARAMS_ID_NETPR).toString().trim();
				log.info("SAP NETPR: " + netprStr);
				if (!netprStr.isEmpty()) {
					netprStr = netprStr.replaceAll(",", "");
					netpr = new BigDecimal(netprStr);
				}
			}

			// 発注数量
			String zhtmngStr = "0";
			BigDecimal zhtmng = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZHTMNG))) {
				zhtmngStr = sapMap.get(SapApiConsts.PARAMS_ID_ZHTMNG).toString().trim();
				log.info("SAP ZHTMNG: " + zhtmngStr);
				if (!zhtmngStr.isEmpty()) {
					zhtmngStr = zhtmngStr.replaceAll(",", "");
					zhtmng = new BigDecimal(zhtmngStr);
				}
			}
			// 発注残数量 (SAP上の発注残)
			String mengeStr = "0";
			BigDecimal paidQty = BigDecimal.ZERO;
			BigDecimal menge = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_MENGE))) {
				mengeStr = sapMap.get(SapApiConsts.PARAMS_ID_MENGE).toString().trim();
				log.info("SAP MENGE: " + mengeStr);
				if (!mengeStr.isEmpty()) {
					mengeStr = mengeStr.replaceAll(",", "");
					menge = new BigDecimal(mengeStr);
				}
			}

			// 納入受入残金額 (SAP上の受入残)
			String zukzknStr = "0";
			BigDecimal zukzkn = BigDecimal.ZERO;
			if (Objects.nonNull(sapMap.get(SapApiConsts.PARAMS_ID_ZUKZKN))) {
				zukzknStr = sapMap.get(SapApiConsts.PARAMS_ID_ZUKZKN).toString().trim();
				log.info("SAP ZUKZKN: " + zukzknStr);
				if (!zukzknStr.isEmpty()) {
					zukzknStr = zukzknStr.replaceAll(",", "");
					zukzkn = new BigDecimal(zukzknStr);
				}
			}

			// 納入済数量 = 発注数量 - 発注残数量
			paidQty = zhtmng.subtract(menge);

			// 発注残数量 (EDIで入力した発注残数量)
			BigDecimal ediMenge = BigDecimal.ZERO;
			if (Objects.nonNull(itm.getDeliveryRemainingQuantity())) {
				ediMenge = new BigDecimal(String.valueOf(itm.getDeliveryRemainingQuantity()));
				log.info("EDI MENGE: " + ediMenge.toString());
			}
			// 納入数量 (EDIで入力した納入数量)
			BigDecimal ediZmenge = BigDecimal.ZERO;
			if (Objects.nonNull(itm.getDeliveryQuantity())) {
				ediZmenge = new BigDecimal(String.valueOf(itm.getDeliveryQuantity()));
				log.info("EDI ZMENGE: " + ediZmenge.toString());
			}

			// 発注金額
			BigDecimal ediZhtkgk = BigDecimal.ZERO;
			// 納入金額
			BigDecimal ediSumpr = BigDecimal.ZERO;
			// 納入済金額
			BigDecimal ediPaid = BigDecimal.ZERO;
			// 納入受入残金額
			BigDecimal ediZukzkn = BigDecimal.ZERO;

			// 金額の再計算 単価 * 発注数量
			ediZhtkgk = netpr.multiply(zhtmng); // 発注金額の算出（JCOで取得できないため）
			ediSumpr = netpr.multiply(ediZmenge); // 納入金額の算出
			ediPaid = netpr.multiply(paidQty); // 納入済金額の算出

			ediZhtkgk = ediZhtkgk.setScale(0, BigDecimal.ROUND_HALF_UP);
			ediSumpr = ediSumpr.setScale(0, BigDecimal.ROUND_HALF_UP);
			ediPaid = ediPaid.setScale(0, BigDecimal.ROUND_HALF_UP);

			if (ediMenge.compareTo(BigDecimal.ZERO) == 0) {
				ediZukzkn = BigDecimal.ZERO;
			} else if (ediPaid.compareTo(BigDecimal.ZERO) == 1) {
				// 納入受入残金額 = 発注金額 - 納入金額 - 納入済金額
				ediZukzkn = ediZhtkgk.subtract(ediSumpr).subtract(ediPaid);
			} else {
				// 納入受入残金額 = 発注金額 - 納入金額
				ediZukzkn = ediZhtkgk.subtract(ediSumpr);
			}
			log.info("EDI ZHTKGK: " + ediZhtkgk.toString());
			log.info("EDI SUMPR: " + ediSumpr.toString());
			log.info("EDI PAID: " + ediPaid.toString());
			log.info("EDI ZUKZKN: " + ediZukzkn.toString());

			// EDI側の納入数量+発注残数量とJCO側の発注残数量が一致しない場合は手動で申請決済済と判断
			BigDecimal testQty = BigDecimal.ZERO;
			testQty = ediZmenge.add(ediMenge);
			log.info("SAP QTY: " + menge.toString());
			log.info("TEST QTY: " + testQty.toString());
			if (menge.compareTo(testQty) != 0) {
				if (menge.compareTo(BigDecimal.ZERO) != 0) {
					// 分納ケースで一致していない場合はエラーにして確認後ステータス更新を行う
					throw new CoreRuntimeException("Mismatch quantity (Delivery): " + orderNumber + " , JCO_EBELP : " + lineNo + " , remain quantity(SAP) : " + menge.toString() + " , delivery quantity(EDI) : " + ediZmenge.toString() + " , delivery remain quantity(EDI) : " + ediMenge.toString());
				} else if (ediMenge.compareTo(BigDecimal.ZERO) != 0) {
					// 完納しているのにEDI側で残が残っている場合はエラーにして確認後ステータス更新を行う
					throw new CoreRuntimeException("Mismatch quantity (Delivery): " + orderNumber + " , JCO_EBELP : " + lineNo + " , remain quantity(SAP) : " + menge.toString() + " , delivery quantity(EDI) : " + ediZmenge.toString() + " , delivery remain quantity(EDI) : " + ediMenge.toString());
				} else {
					// 当該発注についてレコード作成しない
					log.info("Probably completed with SAP (Delivery): " + orderNumber + " , JCO_EBELP : " + lineNo + " , remain quantity(SAP) : " + menge.toString() + " , delivery quantity(EDI) : " + ediZmenge.toString() + " , delivery remain quantity(EDI) : " + ediMenge.toString());
					createSapRecordFlg = false;
				}
			}

			// 品目コード
			params.put(SapApiConsts.PARAMS_ID_MATNR, sapMap.get(SapApiConsts.PARAMS_ID_MATNR).toString());
			// テキスト(短)
			params.put(SapApiConsts.PARAMS_ID_TXZ01, sapMap.get(SapApiConsts.PARAMS_ID_TXZ01).toString());
			// 仕様名
			params.put(SapApiConsts.PARAMS_ID_ZMHNAM, sapMap.get(SapApiConsts.PARAMS_ID_ZMHNAM).toString());
			// 発注残数量
			params.put(SapApiConsts.PARAMS_ID_MENGE, menge.toString()); // SAPの発注残を渡す 今回納入数量は差し引かない
			// 納入数量
			params.put(SapApiConsts.PARAMS_ID_ZMENGE, ediZmenge.toString()); // 今回納入数量を渡す
			// 発注単位
			params.put(SapApiConsts.PARAMS_ID_MEINS, sapMap.get(SapApiConsts.PARAMS_ID_MEINS).toString());
			// 単価
			params.put(SapApiConsts.PARAMS_ID_NETPR, netpr.toString());
			// 納入金額
			params.put(SapApiConsts.PARAMS_ID_SUMPR, ediSumpr.toString()); // 今回納入金額を渡す
			// 購買伝票の明細番号
			params.put(SapApiConsts.PARAMS_ID_EBELP, lineNo);
			// 発注数量
			params.put(SapApiConsts.PARAMS_ID_ZHTMNG, zhtmng.toString());
			// 単位コード
			params.put(SapApiConsts.PARAMS_ID_ZTANIC, sapMap.get(SapApiConsts.PARAMS_ID_ZTANIC).toString());
			// 発注金額
			params.put(SapApiConsts.PARAMS_ID_ZHTKGK, "");
			// 納入受入残金額
			params.put(SapApiConsts.PARAMS_ID_ZUKZKN, zukzkn.toString()); // SAPの受入残を渡す 今回納入金額は差し引かない

			sapDetail.add(params);
		}
		if (!createSapRecordFlg) {
			// 申請レコードを作成せず、エラーにせず終了
			return createSapRecordFlg;
		}

		if (Objects.nonNull(wfSeqNo) && Objects.nonNull(lastUpdateDate) && Objects.nonNull(lastUpdateTime)) {
			// ■■■■■■■■■■■■■ 受入入力レコードの上書き
			result = SapApi.setDeliveryItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateTime, sapDetail, userCode);
		} else {
			// ■■■■■■■■■■■■■ 受入入力レコードの新規作成
			result = SapApi.setDeliveryItemQuantity(eigyousyoCode, orderNumber, acceptanceDate, sapDetail, userCode);
		}
		resultInfo = SapApiAnalyzer.analyzeResultInfo(result);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		return createSapRecordFlg;
	}

	private Map<String, Object> getDeliverySapRecord(String orderNumber, String acceptanceDate, String eigyousyoCode, String userCode, String wfStatus) {
		// ■■■■■■■■■■■■■ 受入確認モジュールで作成したレコードの取得
		Map<String, Object> targetObj = null;
		Map<String, Object> data = SapApi.getDeliveryWFSeqNo(eigyousyoCode, userCode, wfStatus);
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(delivery);

		//メール送信
		mailService.sendMailDelivery(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery, fileList, itemList, remind);
	}

	//納品書取消時にメールを送信
	public void sendMailDeliveryCancel(DeliveryForm form) {
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);

		//メール送信
		mailService.sendMailDeliveryCancel(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery, itemList);
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

		String to = mailService.getMailTo(syain, eigyousyoCode);
		String cc = mailService.getMailCc(eigyousyoCode);
		List<Map<String,String>> fileList = getAttachedFile(delivery);

		//メール送信
		mailService.sendMailDeliveryReject(to, cc, eigyousyo.getEigyousyoName(), syain.getSyainName(), kouji.getKoujiName(), gyousya.getGyousyaName(), delivery, fileList, itemList, rejectComments);
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

}
