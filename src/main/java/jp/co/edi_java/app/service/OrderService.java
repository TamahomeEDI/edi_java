package jp.co.edi_java.app.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.FileOrderRelDao;
import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.MSaimokuKousyuDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.TOrderItemDao;
import jp.co.edi_java.app.dao.VOrderStatusDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaMailaddressDao;
import jp.co.edi_java.app.dao.jtm.DHattyuuKanriDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.OrderSummaryDto;
import jp.co.edi_java.app.dto.SapOrderDetailDto;
import jp.co.edi_java.app.dto.SapOrderDto;
import jp.co.edi_java.app.dto.SearchVOrderDto;
import jp.co.edi_java.app.entity.FileOrderRelEntity;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.OrderForm;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class OrderService {

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public TOrderItemDao tOrderItemDao;

	@Autowired
    public VOrderStatusDao vOrderStatusDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public MGyousyaDao mGyousyaDao
    ;
	@Autowired
	public TGyousyaAccountDao tGyousyaAccountDao;

	@Autowired
	public TGyousyaMailaddressDao tGyousyaMailaddressDao;

	@Autowired
    public FileOrderRelDao fileOrderRelDao;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
	public MSaimokuKousyuDao mSaimokuKousyuDao;

	@Autowired
	public DHattyuuKanriDao dHattyuuKanriDao;

	public static String FILE_FLG_OFF = "0";
	public static String FILE_FLG_ON = "1";
	public static String FILE_FLG_REJECT = "2";

	//未発注の発注日（00000000）
	public static String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//精算完了フラグ（マイナス発注が存在する）
	public static String SETTLEMENT_COMP_FLG_VALUE_X = "X";
	//発注精算フラグ（マイナス発注）
	public static String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/** T_ORDERの情報を取得する */
	public TOrderEntity selectInfo(String orderNumber) {
		return tOrderDao.select(orderNumber);
	}

	/** T_ORDERの情報を取得する */
	public List<TOrderEntity> selectInfo(List<String> orderNumberList) {
		List<TOrderEntity> orderInfoList = tOrderDao.selectList(orderNumberList);
		return orderInfoList;
	}

	/** V_ORDER_STATUSの情報を取得する。※ JTMから発注情報を取得する前提 */
	public VOrderStatusEntity getVOrder(String orderNumber) {
		return vOrderStatusDao.select(orderNumber);
	}

	/** V_ORDER_STATUSの情報を複数取得する。一括発注で利用する。※ JTMから発注情報を取得する前提 */
	public List<VOrderStatusEntity> getMultiVOrder(List<String> orderNumberList) {
		return vOrderStatusDao.selectList(orderNumberList);
	}

	/** T_ORDER_ITEMの情報を複数取得する。※ JTMから発注情報を取得する前提 */
	public List<TOrderItemEntity> selectOrderItem(String orderNumber) {
		return tOrderItemDao.selectAll(orderNumber);
	}

	public List<Map<String, Object>> getFileIdList(String orderNumber) {
		List<FileOrderRelEntity> fileRelList = fileOrderRelDao.selectList(orderNumber);
		List<Map<String, Object>> ret = new ArrayList<>();
		for (FileOrderRelEntity fileEstimateRequestRelEntity : fileRelList) {
			Map<String, Object> fileInfo = FileApi.getFileInfo(fileEstimateRequestRelEntity.getFileId());
			ret.add(fileInfo);
		}
		return ret;
	}

	/** SAPの発注情報を取得する。 ※ JCOから発注情報を取得する前提 */
	public SapOrderDto get(String orderNumber) {
		//発注詳細取得（SAP）
		Map<String, Object> data = SapApi.orderDetail(orderNumber);
		//基本情報取得
		SapOrderDto ret = getHeader(data, orderNumber);
		//明細一覧取得
		List<SapOrderDetailDto> itemList = getItemList(data);
		ret.setItemList(itemList);
		//添付ファイル一覧取得
		List<Map<String, Object>> fileList = getFileIdList(orderNumber);
		ret.setFileList(fileList);
		return ret;
	}

	/** SAPの発注情報のヘッダ情報を取得する。 ※ JCOから発注情報を取得する前提 */
	@SuppressWarnings("unchecked")
	public SapOrderDto getHeader(Map<String, Object> data, String orderNumber) {

		Map<String, Object> retDetail = getSapData(data);

		//表示に必要な情報を取得
		TOrderEntity orderInfo = tOrderDao.select(orderNumber);
		SapOrderDto ret = getSapOrderDto(retDetail, orderInfo);

		return ret;
	}

	/**
	 * 一括発注用 発注情報を複数取得する。 ※ JTMから発注情報を取得する前提
	 * @param orderNumberList
	 * @return List<SearchVOrderDto>
	 */
	@SuppressWarnings("unchecked")
	public List<SearchVOrderDto> getVOrderForMultiOrder(List<String> orderNumberList) {
		List<SearchVOrderDto> resultList = new ArrayList<SearchVOrderDto>();
		if (Objects.isNull(orderNumberList) || orderNumberList.isEmpty()) {
			return resultList;
		}
		List<VOrderStatusEntity> vOrderList = getMultiVOrder(orderNumberList);
		Map<String, List<Map<String, Object>>> fileListMap = getFileIdListMap(orderNumberList);
		// 発注情報キャッシュ用
		Map<String, VOrderStatusEntity> vOrderMap = new HashMap<String, VOrderStatusEntity>();
		// 業者コードキャッシュ用
		Map<String, Boolean> existsGyousya = new HashMap<String, Boolean>();
		List<String> gyousyaCodeList = new ArrayList<String>();
		for (VOrderStatusEntity vOrder : vOrderList) {
			if (Objects.nonNull(vOrder) && Objects.nonNull(vOrder.getOrderNumber())) {
				vOrderMap.put(vOrder.getOrderNumber(), vOrder);
			}
			//業者コードキャッシュ
			if (!existsGyousya.containsKey(vOrder.getGyousyaCode())) {
				gyousyaCodeList.add(vOrder.getGyousyaCode());
				existsGyousya.put(vOrder.getGyousyaCode(),true);
			}
		}
		//業者メールアドレス情報取得
		Map<String, List<TGyousyaMailaddressEntity>> mailaddressMap = new HashMap<String, List<TGyousyaMailaddressEntity>>();
		List<TGyousyaMailaddressEntity> mailaddressList = tGyousyaMailaddressDao.selectListByMultiCode(gyousyaCodeList);
		for (TGyousyaMailaddressEntity mailaddress : mailaddressList) {
			if (Objects.nonNull(mailaddress) && Objects.nonNull(mailaddress.getGyousyaCode())) {
				// 業者情報のキャッシュ
				if (!mailaddressMap.containsKey(mailaddress.getGyousyaCode())) {
					mailaddressMap.put(mailaddress.getGyousyaCode(), new ArrayList<TGyousyaMailaddressEntity>());
				}
				mailaddressMap.get(mailaddress.getGyousyaCode()).add(mailaddress);
			}
		}
		// 発注情報生成
		for (String orderNumber : orderNumberList) {
			SearchVOrderDto ret = new SearchVOrderDto();
			VOrderStatusEntity vOrder = vOrderMap.get(orderNumber);
			//発注情報
			ret.setOrder(vOrder);
			//発注明細一覧
			List<TOrderItemEntity> itemList = selectOrderItem(orderNumber);
			ret.setItemList(itemList);
			//ファイル一覧
			List<Map<String, Object>> fileList = fileListMap.get(orderNumber);
			ret.setFileList(fileList);
			//業者メールアドレス
			List<TGyousyaMailaddressEntity> mailaddr = mailaddressMap.get(vOrder.getGyousyaCode());
			ret.setMailaddressList(mailaddr);
			resultList.add(ret);
		}
		return resultList;
	}

	/**
	 * 一括発注用 SAPの発注情報を複数取得する。 ※ JCOから発注情報を取得する前提
	 * @param orderNumberList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SapOrderDto> multiGet(List<String> orderNumberList) {
		List<SapOrderDto> orderList = new ArrayList<SapOrderDto>();

		if (Objects.isNull(orderNumberList) || orderNumberList.isEmpty()) {
			return orderList;
		}
		//表示に必要な情報を取得
		List<TOrderEntity> orderInfoList = tOrderDao.selectList(orderNumberList);
		Map<String, List<Map<String, Object>>> fileListMap = getFileIdListMap(orderNumberList);
		Map<String, TOrderEntity> orderInfoMap = new HashMap<String, TOrderEntity>();
		for (TOrderEntity orderInfo : orderInfoList) {
			if (Objects.nonNull(orderInfo) && Objects.nonNull(orderInfo.getOrderNumber())) {
				orderInfoMap.put(orderInfo.getOrderNumber(), orderInfo);
			}
		}
		// 工事コードキャッシュ用
		Map<String, Boolean> existsKouji = new HashMap<String, Boolean>();
		List<String> koujiCodeList = new ArrayList<String>();
		// 業者コードキャッシュ用
		Map<String, Boolean> existsGyousya = new HashMap<String, Boolean>();
		List<String> gyousyaCodeList = new ArrayList<String>();

		// 発注情報生成
		for (String orderNumber : orderNumberList) {
			Map<String, Object> data = SapApi.orderDetail(orderNumber);
			Map<String, Object> retDetail = getSapData(data);
			TOrderEntity orderInfo = orderInfoMap.get(orderNumber);

			SapOrderDto ret = getSapOrderDto(retDetail, orderInfo);

			//明細一覧取得
			List<SapOrderDetailDto> itemList = getItemList(data);
			ret.setItemList(itemList);
			//ファイル一覧設定
			List<Map<String, Object>> fileList = fileListMap.get(orderNumber);
			ret.setFileList(fileList);

			//工事コードキャッシュ
			if (!existsKouji.containsKey(ret.getKoujiCode())) {
				koujiCodeList.add(ret.getKoujiCode());
				existsKouji.put(ret.getKoujiCode(),true);
			}
			//業者コードキャッシュ
			if (!existsGyousya.containsKey(ret.getGyousyaCode())) {
				gyousyaCodeList.add(ret.getGyousyaCode());
				existsGyousya.put(ret.getGyousyaCode(),true);
			}
			orderList.add(ret);
		}
		//工事情報取得
		Map<String, MKoujiEntity> koujiInfoMap = new HashMap<String, MKoujiEntity>();
		List<MKoujiEntity> koujiInfoList = mKoujiDao.selectListByMultiCode(koujiCodeList);
		//工事に紐付く各エンティティ取得
		Map<String, Boolean> existsEigyousyo = new HashMap<String, Boolean>();
		List<String> eigyousyoCodeList = new ArrayList<String>();
		Map<String, Boolean> existsSyain = new HashMap<String, Boolean>();
		List<String> syainCodeList = new ArrayList<String>();
		for (MKoujiEntity koujiInfo : koujiInfoList) {
			if (Objects.nonNull(koujiInfo) && Objects.nonNull(koujiInfo.getKoujiCode())) {
				// 工事情報のキャッシュ
				koujiInfoMap.put(koujiInfo.getKoujiCode(), koujiInfo);
				// 社員コードのキャッシュ
				if (!existsSyain.containsKey(koujiInfo.getTantouSyainCode())) {
					syainCodeList.add(koujiInfo.getTantouSyainCode());
					existsSyain.put(koujiInfo.getTantouSyainCode(),true);
				}
				// 営業所コードのキャッシュ
				if (!existsEigyousyo.containsKey(koujiInfo.getEigyousyoCode())) {
					eigyousyoCodeList.add(koujiInfo.getEigyousyoCode());
					existsEigyousyo.put(koujiInfo.getEigyousyoCode(),true);
				}
			}
		}
		//業者情報取得
		Map<String, MGyousyaEntity> gyousyaInfoMap = new HashMap<String, MGyousyaEntity>();
		List<MGyousyaEntity> gyousyaInfoList = mGyousyaDao.selectListByMultiCode(gyousyaCodeList);
		for (MGyousyaEntity gyousyaInfo : gyousyaInfoList) {
			if (Objects.nonNull(gyousyaInfo) && Objects.nonNull(gyousyaInfo.getGyousyaCode())) {
				// 業者情報のキャッシュ
				gyousyaInfoMap.put(gyousyaInfo.getGyousyaCode(), gyousyaInfo);
			}
		}
		//業者アカウント情報取得
		Map<String, TGyousyaAccountEntity> partnerAccountMap = new HashMap<String, TGyousyaAccountEntity>();
		List<TGyousyaAccountEntity> partnerAccountList = tGyousyaAccountDao.selectListByMultiCode(gyousyaCodeList);
		for (TGyousyaAccountEntity partnerAccount : partnerAccountList) {
			if (Objects.nonNull(partnerAccount) && Objects.nonNull(partnerAccount.getGyousyaCode())) {
				// 業者情報のキャッシュ
				partnerAccountMap.put(partnerAccount.getGyousyaCode(), partnerAccount);
			}
		}
		//業者メールアドレス情報取得
		Map<String, List<TGyousyaMailaddressEntity>> mailaddressMap = new HashMap<String, List<TGyousyaMailaddressEntity>>();
		List<TGyousyaMailaddressEntity> mailaddressList = tGyousyaMailaddressDao.selectListByMultiCode(gyousyaCodeList);
		for (TGyousyaMailaddressEntity mailaddress : mailaddressList) {
			if (Objects.nonNull(mailaddress) && Objects.nonNull(mailaddress.getGyousyaCode())) {
				// 業者情報のキャッシュ
				if (!mailaddressMap.containsKey(mailaddress.getGyousyaCode())) {
					mailaddressMap.put(mailaddress.getGyousyaCode(), new ArrayList<TGyousyaMailaddressEntity>());
				}
				mailaddressMap.get(mailaddress.getGyousyaCode()).add(mailaddress);
			}
		}
		//社員情報取得
		Map<String, MSyainEntity> syainInfoMap = new HashMap<String, MSyainEntity>();
		List<MSyainEntity> syainInfoList = mSyainDao.selectListByMultiCode(syainCodeList);
		for (MSyainEntity syainInfo : syainInfoList) {
			if (Objects.nonNull(syainInfo) && Objects.nonNull(syainInfo.getSyainCode())) {
				// 社員情報のキャッシュ
				syainInfoMap.put(syainInfo.getSyainCode(), syainInfo);
			}
		}
		//営業所情報取得
		Map<String, MEigyousyoEntity> eigyousyoInfoMap = new HashMap<String, MEigyousyoEntity>();
		List<MEigyousyoEntity> eigyousyoInfoList = mEigyousyoDao.selectList(eigyousyoCodeList);
		for (MEigyousyoEntity eigyousyoInfo : eigyousyoInfoList) {
			if (Objects.nonNull(eigyousyoInfo) && Objects.nonNull(eigyousyoInfo.getEigyousyoCode())) {
				// 営業所情報のキャッシュ
				eigyousyoInfoMap.put(eigyousyoInfo.getEigyousyoCode(), eigyousyoInfo);
			}
		}
		for (SapOrderDto order : orderList) {
			MKoujiEntity kouji = koujiInfoMap.get(order.getKoujiCode());
			MGyousyaEntity gyousya = gyousyaInfoMap.get(order.getGyousyaCode());
			MSyainEntity syain = syainInfoMap.get(kouji.getTantouSyainCode());
			MEigyousyoEntity eigyousyo = eigyousyoInfoMap.get(kouji.getEigyousyoCode());
			TGyousyaAccountEntity partnerAccount = partnerAccountMap.get(order.getGyousyaCode());
			List<TGyousyaMailaddressEntity> mailaddr = mailaddressMap.get(order.getGyousyaCode());
			order.setKoujiInfo(kouji);
			order.setGyousyaInfo(gyousya);
			order.setSyainInfo(syain);
			order.setEigyousyoInfo(eigyousyo);
			order.setPartnerAccount(partnerAccount);
			order.setMailaddressList(mailaddr);
		}
		return orderList;
	}

	/** SAPの発注情報明細を取得する。 ※ JCOから発注情報を取得する前提 */
	@SuppressWarnings("unchecked")
	private List<SapOrderDetailDto> getItemList(Map<String, Object> data) {
		ArrayList<SapOrderDetailDto> itemList = new ArrayList<SapOrderDetailDto>();

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01002);
		List<Map<String, Object>> retItemList = new ArrayList<>();
		if(obj instanceof List) {
			retItemList = (List<Map<String, Object>>)obj;
		}else {
			retItemList.add((Map<String, Object>)obj);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : retItemList) {
			SapOrderDetailDto dto = new SapOrderDetailDto();
			dto.setName(map.get(SapApiConsts.PARAMS_ID_MAKTX).toString());
			dto.setPrice(map.get(SapApiConsts.PARAMS_ID_ZHTTNK).toString());
			dto.setQuantity(map.get(SapApiConsts.PARAMS_ID_ZMENGE).toString());
			dto.setUnit(map.get(SapApiConsts.PARAMS_ID_ZTANIN).toString());
			dto.setAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString()));
			dto.setAmountTax(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString()));
			dto.setAmountTaxInclud(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZHTKZTG).toString()));
			dto.setDeliveryQuantity(map.get(SapApiConsts.PARAMS_ID_ZUKEMG).toString());
			dto.setDeliveryAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZUKEKN).toString()));
			dto.setRemainQuantity(map.get(SapApiConsts.PARAMS_ID_ZZANMG).toString());
			dto.setRemainAmount(Integer.valueOf(map.get(SapApiConsts.PARAMS_ID_ZZANKN).toString()));
			itemList.add(dto);
		}
		return itemList;
	}

	/** TOP画面用に発注情報のステータスサマリを取得する。 ※ JTMから発注情報を取得する前提 */
	@SuppressWarnings("unchecked")
	public List<OrderSummaryDto> getSummary(OrderForm form) {
		ArrayList<OrderSummaryDto> itemList = new ArrayList<OrderSummaryDto>();

		List<VOrderStatusEntity> vOrderList = vOrderStatusDao.countByStatus(form.getEigyousyoCode(),form.getGyousyaCode());
		if (Objects.isNull(vOrderList) || vOrderList.isEmpty()) {
			return itemList;
		}
		for (VOrderStatusEntity entity : vOrderList) {
			OrderSummaryDto dto = new OrderSummaryDto();
			dto.setEigyousyoCode(form.getEigyousyoCode());
			dto.setGyousyaCode(form.getGyousyaCode());
			dto.setOrderStatus(entity.getOrderStatus());
			dto.setOrderCount(entity.getCount());
			itemList.add(dto);
		}
		return itemList;
	}

	public String update(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		//orderInfoが存在するならupdate
		if(orderInfo != null) {
			//BeanUtils.copyBeanToBean(form, orderInfo);
			setInputColumn(form, orderInfo);
			orderInfo.setUpdateUser(form.getUserId());
			tOrderDao.update(orderInfo);
		}
		//存在しないならinsert
		else {
			TOrderEntity entity = new TOrderEntity();
			//BeanUtils.copyBeanToBean(form, entity);
			setInputColumn(form, orderInfo);
			MSaimokuKousyuEntity saimoku = mSaimokuKousyuDao.select(form.getSaimokuKousyuCode());
			entity.setOrderType(saimoku.getHattyuuSyubetuFlg());
			entity.setInsertUser(form.getUserId());
			entity.setUpdateUser(form.getUserId());
			tOrderDao.insert(entity);
		}
		List<String> fileIdList = form.getFileIdList();
		if(fileIdList != null) {
			//発注添付ファイル登録
			registFile(orderNumber, fileIdList, form.userId);
		}
		return orderNumber;
	}

	private void setInputColumn(OrderForm form, TOrderEntity orderInfo) {
		orderInfo.setOrderNumber(form.getOrderNumber());
		orderInfo.setKoujiCode(form.getKoujiCode());
		orderInfo.setSaimokuKousyuCode(form.getSaimokuKousyuCode());
		orderInfo.setUserBikou(form.getUserBikou());
		orderInfo.setPartnerBikou(form.getPartnerBikou());
	}

	//発注添付ファイル登録
	private void registFile(String orderNumber, List<String> fileIdList, String userId) {
		for (String fileId : fileIdList) {
			FileOrderRelEntity entity = new FileOrderRelEntity();
			entity.setOrderNumber(orderNumber);
			entity.setFileId(fileId);
			entity.setInsertUser(userId);
			entity.setUpdateUser(userId);
			fileOrderRelDao.insert(entity);
		}
	}

	/** 発注日とまとめ発注用のキーを発注情報に反映 */
	public void multiConfirmationInfo(OrderForm form) {
		if (Objects.nonNull(form.getOrderNumberList())) {
			OrderForm orderForm = new OrderForm();
			for (String orderNumber : form.getOrderNumberList()) {
				orderForm.setOrderNumber(orderNumber);
				orderForm.setGroupOrderNumber(form.getGroupOrderNumber());
				orderForm.setUserId(form.getUserId());
				confirmationInfo(orderForm);
			}
		}
	}

	/** 発注日とまとめ発注用のキーを発注情報に反映 */
	public void confirmationInfo(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		Timestamp orderDateTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String orderDate = sdf.format(orderDateTime);
		String groupOrderNumber = form.getGroupOrderNumber();
		orderInfo.setConfirmationFlg(FILE_FLG_OFF);
		orderInfo.setConfirmationRequestDate(new Timestamp(System.currentTimeMillis()));
		orderInfo.setOrderDate(orderDate);
		orderInfo.setGroupOrderNumber(groupOrderNumber);
		orderInfo.setUpdateUser(form.getUserId());
		tOrderDao.update(orderInfo);
	}

	public void cancelInfo(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		orderInfo.setCancelFlg(FILE_FLG_OFF);
		orderInfo.setCancelRequestDate(new Timestamp(System.currentTimeMillis()));
		orderInfo.setUpdateUser(form.getUserId());
		tOrderDao.update(orderInfo);
	}

	/** 発注日をSAPとJTMへ連携 */
	public void multiSendOrderDate(OrderForm form) {
		if (Objects.nonNull(form.getOrderNumberList())) {
			OrderForm orderForm = new OrderForm();
			for (String orderNumber : form.getOrderNumberList()) {
				orderForm.setOrderNumber(orderNumber);
				orderForm.setUserId(form.getUserId());
				sendOrderDate(orderForm);
			}
		}
	}

	/** 発注日をSAPとJTMへ連携 */
	public void sendOrderDate(OrderForm form) {
		String orderNumber = form.getOrderNumber();
		TOrderEntity orderInfo = selectInfo(orderNumber);
		String koujiCode = orderInfo.getKoujiCode();
		String syainCode = form.getUserId();
		String orderDate = orderInfo.getOrderDate();
		String orderDateSlash = orderInfo.getOrderDate();
		int jtmHattyuuSyubetuFlg = orderInfo.getJtmHattyuuSyubetuFlg();
		int jtmHattyuuSeqNo = orderInfo.getJtmHattyuuSeqNo();
		int jtmKanriSeqNo = orderInfo.getJtmKanriSeqNo();

		if (Objects.isNull(orderDate) || orderDate.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			orderDateSlash = sdf.format(orderInfo.getConfirmationRequestDate());
			orderDate = orderDateSlash.replaceAll("/", "");
		} else {
			Pattern pattern = java.util.regex.Pattern.compile("^[0-9]*$");
		    Matcher matcher = pattern.matcher(orderDate);
			if (orderDate.length() == 8 && matcher.matches()) {
				String yyyy = orderDate.substring(0, 4);
				String MM = orderDate.substring(4, 6);
				String dd = orderDate.substring(6, 8);
				orderDateSlash = yyyy + '/' + MM + '/' + dd;
			} else {
				throw new CoreRuntimeException("Can not send orderDate to JTM and SAP");
			}
		}
		// TO JTM
		dHattyuuKanriDao.updateHattyuuDate(orderDate, syainCode, koujiCode, orderNumber, jtmHattyuuSyubetuFlg, jtmHattyuuSeqNo, jtmKanriSeqNo);
		dHattyuuKanriDao.commit();

		// TO SAP
		Map<String, Object> data = SapApi.setOrderDate(orderNumber, koujiCode, orderDateSlash);
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
	}

	public void conectCloudSign(OrderForm form, String formType) {
		String orderNumber = form.getOrderNumber();
		String groupOrderNumber = (Objects.nonNull(form.getGroupOrderNumber())) ?  form.getGroupOrderNumber() : null;
		String title = "";
		String fileName = "";
		String eigyousyoCode = (Objects.nonNull(form.getEigyousyoCode())) ?  form.getEigyousyoCode() : "";
		String eigyousyoName = (Objects.nonNull(form.getEigyousyoName())) ?  form.getEigyousyoName() : "";
		String gyousyaCode = (Objects.nonNull(form.getGyousyaCode())) ?  form.getGyousyaCode() : "";
		String gyousyaName = (Objects.nonNull(form.getGyousyaName())) ?  form.getGyousyaName() : "";
		String saimokuKousyuName = (Objects.nonNull(form.getSaimokuKousyuName())) ?  form.getSaimokuKousyuName() : "";
		String koujiCode = (Objects.nonNull(form.getKoujiCode())) ?  form.getKoujiCode() : "";
		String koujiName = (Objects.nonNull(form.getKoujiName())) ?  form.getKoujiName() : "";
		// メールタイトル、ファイル名の生成
		if(formType.equals(CloudSignApi.FORM_TYPE_ORDER)) {
			title = CloudSignApi.PREFIX_TITLE_CONFIRMATION;
			fileName = CloudSignApi.PREFIX_FILE_CONFIRMATION;
		}else {
			title = CloudSignApi.PREFIX_TITLE_CANCEL;
			fileName = CloudSignApi.PREFIX_FILE_CANCEL;
		}
		// 2019/12/09 リリース対象外
		//title = title + eigyousyoName + "_" + koujiName;
		//fileName = fileName + gyousyaCode + "_" + koujiCode + "_" + eigyousyoCode;
		title = title + eigyousyoName + "_" + saimokuKousyuName + "_" + koujiName + "_" + orderNumber;
		fileName = fileName + gyousyaCode + "_" + orderNumber;

		Map<String, Object> documentRet = CloudSignApi.postDocuments(title, "", "", null, false);
		String documentId = documentRet.get("id").toString();

		if(StringUtils.isNullString(documentId)) {
			throw new CoreRuntimeException("documentId not found");
		}

		String mailaddress = form.getMailaddress();
		int plus = mailaddress.indexOf("+");
		if(plus != -1) {
			int at = mailaddress.indexOf("@");
			String mailaddressBefore = mailaddress.substring(0, plus);
			String mailaddressAfter = mailaddress.substring(at);
			mailaddress = mailaddressBefore + mailaddressAfter;
		}

		if(StringUtils.isNullString(mailaddress)) {
			throw new CoreRuntimeException("mailaddress not found");
		}

		CloudSignApi.postParticipants(documentId, mailaddress, gyousyaName);

		CloudSignApi.postFile(documentId, form.getFilePath(), fileName + ".pdf");

		CloudSignApi.postDocumentId(documentId);

		//連携テーブルの登録
		registCloudSign(documentId, orderNumber, groupOrderNumber, formType, form.getUserId());
	}

	public void registCloudSign(String fileId, String orderNumber, String groupOrderNumber, String formType, String userId) {
		TCloudSignEntity entity = new TCloudSignEntity();
		entity.setFileId(fileId);
		entity.setOrderNumber(orderNumber);
		entity.setGroupOrderNumber(groupOrderNumber);
		entity.setFormType(formType);
		entity.setApplicationDate(new Timestamp(System.currentTimeMillis()));
		entity.setInsertUser(userId);
		entity.setUpdateUser(userId);
		tCloudSignDao.insert(entity);
	}

	private Map<String, List<Map<String, Object>>> getFileIdListMap(List<String> orderNumberList) {
		List<FileOrderRelEntity> fileRelList = fileOrderRelDao.selectListByMultiNum(orderNumberList);
		Map<String, List<Map<String, Object>>> ret = new HashMap<String, List<Map<String, Object>>>();
		for (FileOrderRelEntity fileEstimateRequestRelEntity : fileRelList) {
			Map<String, Object> fileInfo = FileApi.getFileInfo(fileEstimateRequestRelEntity.getFileId());
			if (!ret.containsKey(fileEstimateRequestRelEntity.getOrderNumber())) {
				ret.put(fileEstimateRequestRelEntity.getOrderNumber(), new ArrayList<Map<String, Object>>());
			}
			ret.get(fileEstimateRequestRelEntity.getOrderNumber()).add(fileInfo);
		}
		return ret;
	}

	/**
	 * sapのデータから発注情報のヘッダ情報を取得する
	 * @param data
	 * @return Map<Strign, Object>
	 */
	private Map<String, Object> getSapData(Map<String, Object> data) {
		//発注詳細は1件の場合と複数件の場合がある（複数件の場合はマイナス発注有）
		Object sapObj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		Map<String, Object> retDetail = null;
		//オブジェクトがListの場合
		if(sapObj instanceof List){
			List<Map<String, Object>> retDetailList = (List<Map<String, Object>>) sapObj;
			//発注詳細分チェックする
			for (Map<String, Object> detail : retDetailList) {
				//発注精算フラグ取得
				String orderSettlementFlg = detail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
				//マイナス発注じゃなかったら発注詳細とする
				if(!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
					retDetail = detail;
					break;
				}
			}
			//発注詳細が存在しなかったら
			if(retDetail == null) {
				throw new CoreRuntimeException(ResponseCode.ERROR_CODE_1000);
			}
		}
		//List以外の場合
		else {
			retDetail = (Map<String, Object>) sapObj;
		}
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}
		return retDetail;
	}

	/**
	 *
	 * 発注情報を取得する
	 *
	 * @param retDetail
	 * @param orderInfo
	 * @return SapOrderDto
	 */
	private SapOrderDto getSapOrderDto(Map<String, Object> retDetail, TOrderEntity orderInfo) {
		SapOrderDto ret = new SapOrderDto();
		String orderDate = retDetail.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
		ret.setOrderNumber(retDetail.get(SapApiConsts.PARAMS_ID_SEBELN).toString());
		ret.setDeliveryStatus(retDetail.get(SapApiConsts.PARAMS_ID_ZNKFLG).toString());
		ret.setGyousyaCode(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
		ret.setGyousyaName(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSNM).toString());
		ret.setKoujiCode(retDetail.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString());
		ret.setSapOrderDate(orderDate);
		//SAPで発注済みのものはそれを優先
		if(!StringUtils.isNullString(orderDate) && !orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
			ret.setOrderDate(orderDate);
		}
		//EDIで発注済みのもので発注依頼日がnullでない場合はそれを優先
		else if(orderInfo != null && orderInfo.getConfirmationRequestDate() != null) {
			ret.setOrderDate(sdf.format(orderInfo.getConfirmationRequestDate()));
		}
		//上記以外はSAPのデータを設定
		else {
			ret.setOrderDate(orderDate);
		}
		ret.setSaimokuKousyuCode(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKNO).toString());
		ret.setSaimokuKousyuName(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKSY).toString());
		ret.setOrderAmount(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString()));
		ret.setOrderAmountTax(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString()));
		ret.setDeliveryAmount(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZUKKGK).toString()));
		ret.setDeliveryAmountTax(Integer.valueOf(retDetail.get(SapApiConsts.PARAMS_ID_ZUKKZG).toString()));
		ret.setSettlementCompFlg(retDetail.get(SapApiConsts.PARAMS_ID_ZSKFLG).toString());
		ret.setOrderSettlementFlg(retDetail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString());
		ret.setOrderInfo(orderInfo);

		return ret;
	}

	/* ■■■■■■■■■■■■■■■■■■■■■■■■ 単発処理 ■■■■■■■■■■■■■■■■■■■■■■■ */
	public Map<String,Integer> refreshOrderDate() {
		Map<String,Integer> cntMap = new HashMap<String,Integer>();

		//発注日をSAPへ連携
		List<TOrderEntity> orderList = tOrderDao.selectListByOrdered();
		Integer cntOK = 0;
		Integer cntNG = 0;
		if (Objects.nonNull(orderList)) {
			for (TOrderEntity orderInfo : orderList) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String orderDate = sdf.format(orderInfo.getConfirmationRequestDate());
				Map<String, Object> data = SapApi.setOrderDate(orderInfo.getOrderNumber(), orderInfo.getKoujiCode(), orderDate);
				Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
				if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
					cntNG += 1;
					log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
				} else {
					cntOK += 1;
				}
			}
		}
		cntMap.put("sapOK",cntOK);
		cntMap.put("sapNG",cntNG);
		return cntMap;
	}

}
