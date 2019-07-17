package jp.co.edi_java.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.SearchDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.TWorkReportDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dto.SapSearchOrderDto;
import jp.co.edi_java.app.dto.SearchDeliveryDto;
import jp.co.edi_java.app.dto.SearchEstimateDto;
import jp.co.edi_java.app.dto.SearchKoujiInfoDto;
import jp.co.edi_java.app.dto.SearchOrderInfoDto;
import jp.co.edi_java.app.dto.SearchWorkReportDto;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class SearchService {

	@Autowired
    public SearchDao searchDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public TDeliveryDao tDeliveryDao;

	@Autowired
    public TWorkReportDao tWorkReportDao;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
    public MailService mailService;

	public static String USER_FLG_TAMA = "1";
	public static String USER_FLG_PARTNER = "0";

	private String ORDER_STATUS_NOT_ORDERING = "0";
	private String ORDER_STATUS_ORDERING = "1";

	//未発注の発注日（00000000）
	private String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//発注精算フラグ（マイナス発注）
	private String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

	//クラウドサイン種別
	private String CLOUD_SIGN_FORM_TYPE_ORDER = "1";

	//工事単位検索上限
	private int LIMIT_KOUJI_COUNT = 300;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public List<SearchEstimateDto> getEstimate(SearchForm form) {
		int offset = (form.getPage() - 1) * form.getCount();
		return searchDao.selectEstimate(form, form.getCount(), offset);
	}

	public int countEstimate(SearchForm form) {
		return searchDao.countEstimate(form);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<SapSearchOrderDto> getOrder(SearchForm form) {
		//社員フラグ
		String userFlg = form.getUserFlg();
		//発注ステータス
		String searchOrderStatus = form.getOrderStatus();

		//検索結果リストの初期化
		ArrayList<SapSearchOrderDto> ret = new ArrayList<SapSearchOrderDto>();

		boolean paramFlg = false;
		if(!StringUtils.isNullString(form.getConfirmationDateFrom()) || !StringUtils.isNullString(form.getConfirmationDateTo()) ||
		   !StringUtils.isNullString(form.getCompletionDateFrom()) || !StringUtils.isNullString(form.getCompletionDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelRequestDateFrom()) || !StringUtils.isNullString(form.getOrderCancelRequestDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelAgreeDateFrom()) || !StringUtils.isNullString(form.getOrderCancelAgreeDateTo())) {
			paramFlg = true;
		}

		//sapからデータ取得
		Map<String, Object> data = SapApi.orderList(form);

		//結果情報取得
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		//sapのデータが存在しなかったら空のリストを返却
		if(obj == null) {
			return ret;
		}

		List<Map<String, Object>> resultList = new ArrayList<>();
		//1件より多い場合
		if(obj instanceof List) {
			resultList = (List<Map<String, Object>>)obj;
		}
		//1件の場合
		else {
			resultList.add((Map<String, Object>)obj);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : resultList) {
			String orderNumber = map.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
			String koujiCode = map.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString();
			String orderDate = map.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
			String orderSettlementFlg = map.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();

			if(!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {

				//発注情報取得…SAPとのステータスが複雑化したので追加（あとで作り直した方がいいかも）
				SearchOrderInfoDto order = searchDao.getOrderInfo(orderNumber, orderDate);

				//発注検索情報取得
				SearchOrderInfoDto searchOrderInfoDto = searchDao.selectOrderInfo(orderNumber, form, orderDate);
				//工事検索情報取得
				SearchKoujiInfoDto searchKoujiInfoDto = searchDao.selectKoujiInfo(koujiCode, form.getKoujiName());
				//クラウドサイン情報取得
				TCloudSignEntity cloudSignInfo = tCloudSignDao.selectNewest(orderNumber, CLOUD_SIGN_FORM_TYPE_ORDER);

				if (checkSearchResult(searchOrderStatus, orderDate, userFlg, order, searchOrderInfoDto)) {
					continue;
				}

				if(!(paramFlg && searchOrderInfoDto == null) && searchKoujiInfoDto != null) {
					SapSearchOrderDto dto = createSearchResultData(map, orderNumber, koujiCode, orderDate, searchOrderInfoDto, searchKoujiInfoDto, cloudSignInfo);
					ret.add(dto);
				}

			}
		}
		return ret;

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderByKouji(SearchForm form) {

		//社員フラグ
		String userFlg = form.getUserFlg();
		//発注ステータス
		String searchOrderStatus = form.getOrderStatus();

		boolean paramFlg = false;
		if(!StringUtils.isNullString(form.getConfirmationDateFrom()) || !StringUtils.isNullString(form.getConfirmationDateTo()) ||
		   !StringUtils.isNullString(form.getCompletionDateFrom()) || !StringUtils.isNullString(form.getCompletionDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelRequestDateFrom()) || !StringUtils.isNullString(form.getOrderCancelRequestDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelAgreeDateFrom()) || !StringUtils.isNullString(form.getOrderCancelAgreeDateTo())) {
			paramFlg = true;
		}

		//検索結果リストの初期化
		Map<String, Object> ret = new HashMap<String, Object>();
		ArrayList<SapSearchOrderDto> resultList = new ArrayList<SapSearchOrderDto>();
		ret.put("limitOver", false);
		ret.put("orderList", resultList);

		int koujiCount = searchDao.countKoujiInfo(form.getKoujiCode(), form.getKoujiName(), form.getEigyousyoCode());
		log.info("kouji count: " + koujiCount);
		//工事情報が多い場合は追加で絞り込み条件を指定してもらうように返却
		if (koujiCount > LIMIT_KOUJI_COUNT) {
			ret.put("limitOver", true);
			return ret;
		}
		// 工事情報の取得
		Map<String, SearchKoujiInfoDto> skinfoMap = new HashMap<String, SearchKoujiInfoDto>();
		//工事コードマップ
		List<String> koujiCodeList =  new ArrayList<String>();
		koujiCodeList.add(form.getKoujiCode());
		List<SearchKoujiInfoDto> koujiInfoList = searchDao.getKoujiInfoList(koujiCodeList, form.getKoujiName(), form.getEigyousyoCode());

		if (koujiInfoList.isEmpty()) {
			return ret;
		}

		Map<String, Boolean> gyousyaMap = new HashMap<String, Boolean>();
		List<String> gyousyaCodeList =  new ArrayList<String>();
		// 工事に関連する業者を取得
		for (SearchKoujiInfoDto kouji: koujiInfoList) {
			log.info("kouji code: " + kouji.getKoujiCode());
			//工事情報のキャッシュ
			skinfoMap.put(kouji.getKoujiCode(), kouji);
			//sapからデータ取得
			Map<String, Object> postIdData = SapApi.orderListByKouji(kouji.getKoujiCode());
			List<Map<String, Object>> postIdList = getSapListData(postIdData, SapApiConsts.PARAMS_KEY_T_E_02003);
			for (Map<String, Object> postId : postIdList) {
				log.info(postId.get(SapApiConsts.PARAMS_ID_POSID).toString());
				//sapからデータ取得
				Map<String, Object> orderData = SapApi.orderListByPosId(postId.get(SapApiConsts.PARAMS_ID_POSID).toString(),form.getLoginUserCode());
				List<Map<String, Object>> orderListTmp = getSapListData(orderData, SapApiConsts.PARAMS_KEY_T_E_03004);
				for (Map<String, Object> orderTmp : orderListTmp) {
					String gyousyaCode = orderTmp.get(SapApiConsts.PARAMS_ID_LIFNR).toString();
					if (Objects.nonNull(gyousyaCode) && !gyousyaCode.isEmpty()) {
						if (!gyousyaMap.containsKey(gyousyaCode)) {
							gyousyaCodeList.add(gyousyaCode);
							gyousyaMap.put(gyousyaCode,true);
						}
					}
				}
			}
		}

		//SAPデータ取得結果リストの初期化
		List<Map<String, Object>> sapOrderList = new ArrayList<Map<String, Object>>();
		//発注番号リスト
		List<String> orderNumberList =  new ArrayList<String>();
		//SAPOrderマップ
		Map<String, Map<String, Object>> sapOrderMap = new HashMap<String, Map<String, Object>>();

		//業者コード分検索を実施
		for (String gyousyaCode : gyousyaCodeList) {
			form.setGyousyaCode(gyousyaCode);
			Map<String, Object> data = SapApi.orderList(form);
			List<Map<String, Object>> orderListTmp = getSapListData(data, SapApiConsts.PARAMS_KEY_T_E_01004);
			for (Map<String, Object> orderTmp : orderListTmp) {
				String orderSettlementFlg = orderTmp.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
				String orderNumber = orderTmp.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
				if (Objects.nonNull(orderNumber) && !orderNumber.isEmpty()) {
					if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
						//発注番号をキャッシュ
						orderNumberList.add(orderNumber);
						//SAPOrderをキャッシュ
						sapOrderMap.put(orderNumber, orderTmp);
						sapOrderList.add(orderTmp);
					}
				}
			}
		}
		//sapのデータが存在しなかったら空のリストを返却
		if(Objects.isNull(sapOrderList) || sapOrderList.size() < 1) {
			return ret;
		}

		// EDIの発注情報取得
		List<SearchOrderInfoDto> sorderinfoList = searchDao.getOrderInfoList(orderNumberList);
		Map<String, SearchOrderInfoDto> sorderinfoMap = new HashMap<String, SearchOrderInfoDto>();
		for (SearchOrderInfoDto soinfDto : sorderinfoList) {
			Map<String, Object> sap = sapOrderMap.get(soinfDto.getOrderNumber());
			String orderDate = sap.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
			setOrderStatus(soinfDto, orderDate);
			// EDI発注情報をキャッシュ
			sorderinfoMap.put(soinfDto.getOrderNumber(), soinfDto);
		}
		// EDIの発注情報取得（検索条件あり）
		List<SearchOrderInfoDto> sorderinfoList2 = searchDao.selectOrderInfoList(orderNumberList, form);
		Map<String, SearchOrderInfoDto> sorderinfoMap2 = new HashMap<String, SearchOrderInfoDto>();
		for (SearchOrderInfoDto soinfDto : sorderinfoList2) {
			Map<String, Object> sap = sapOrderMap.get(soinfDto.getOrderNumber());
			String orderDate = sap.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
			setOrderStatus(soinfDto, orderDate);
			// orderstatusで絞り込み
			if (Objects.nonNull(searchOrderStatus)) {
				if (soinfDto.getOrderStatus().equals(searchOrderStatus)) {
					// EDI発注情報をキャッシュ
					sorderinfoMap2.put(soinfDto.getOrderNumber(), soinfDto);
				}
			} else {
				// EDI発注情報をキャッシュ
				sorderinfoMap2.put(soinfDto.getOrderNumber(), soinfDto);
			}
		}
		// クラウドサイン情報の取得
		Map<String, TCloudSignEntity> cloudSignMap = new HashMap<String, TCloudSignEntity>();
		List<TCloudSignEntity> cloudSignInfoList = tCloudSignDao.selectNewestList(orderNumberList, CLOUD_SIGN_FORM_TYPE_ORDER);
		for (TCloudSignEntity cloudSignInfo : cloudSignInfoList) {
			//クラウドサイン情報のキャッシュ
			if (!cloudSignMap.containsKey(cloudSignInfo.getOrderNumber())) {
				//登録日の降順に並んでいる中で発注番号毎に一番最初に出現するもののみキャッシュ
				cloudSignMap.put(cloudSignInfo.getOrderNumber(), cloudSignInfo);
			}
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : sapOrderList) {
			String orderNumber = map.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
			String koujiCode = map.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString();
			String orderDate = map.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();

			//発注情報取得
			SearchOrderInfoDto order = sorderinfoMap.get(orderNumber);
			//発注情報取得（検索条件あり）
			SearchOrderInfoDto searchOrderInfoDto = sorderinfoMap2.get(orderNumber);
			//工事検索情報取得
			SearchKoujiInfoDto searchKoujiInfoDto = skinfoMap.get(koujiCode);
			//クラウドサイン情報取得
			TCloudSignEntity cloudSignInfo = cloudSignMap.get(orderNumber);

			if (checkSearchResult(searchOrderStatus, orderDate, userFlg, order, searchOrderInfoDto)) {
				continue;
			}

			if(!(paramFlg && searchOrderInfoDto == null) && searchKoujiInfoDto != null) {
				SapSearchOrderDto dto = createSearchResultData(map, orderNumber, koujiCode, orderDate, searchOrderInfoDto, searchKoujiInfoDto, cloudSignInfo);
				resultList.add(dto);
			}
		}

		return ret;
	}

	public Map<String, Object> getSap(SearchForm form) {

		//sapからデータ取得
		Map<String, Object> data = SapApi.orderList(form);

		return data;

	}

	public List<SearchWorkReportDto> getWorkReport(SearchForm form) {
		int offset = (form.getPage() - 1) * form.getCount();
		return searchDao.selectWorkReport(form, form.getCount(), offset);
	}

	public List<SearchDeliveryDto> getDelivery(SearchForm form) {
		int offset = (form.getPage() - 1) * form.getCount();
		return searchDao.selectDelivery(form, form.getCount(), offset);
	}

	public void getInspectionReceipt(SearchForm form) {
		int offset = (form.getPage() - 1) * form.getCount();
		//SAPの検索使う
//		return searchDao.selectInspectionReceipt(form);
	}

	/** sapデータの除外対象判定 */
	private Boolean checkSearchResult(String searchOrderStatus, String orderDate, String userFlg, SearchOrderInfoDto order, SearchOrderInfoDto searchOrderInfoDto) {
		//EDIにデータが存在しない
		if(searchOrderInfoDto == null) {

			//業者の場合、未発注(発注日が00000000または空文字)データを除外
			if(userFlg.equals(USER_FLG_PARTNER) && (StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
				return true;
			}
			//検索ステータスが未発注または発注済み以外の場合、データを除外
			else if(!(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) || searchOrderStatus.equals(ORDER_STATUS_ORDERING))) {
				return true;
			}
			//検索ステータスが未発注の場合、未発注(発注日が00000000または空文字)以外のデータを除外
			else if(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
				return true;
			}
			//検索ステータスが未発注以外の場合、未発注(発注日が00000000または空文字)データを除外
			else if((StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) && !searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING)) {
				return true;
			}
			//検索ステータスと発注ステータスが一致しないデータを除外
			if(order != null) {
				if(!order.getOrderStatus().equals(searchOrderStatus)) {
					return true;
				}
			}
		}
		//EDIにデータが存在する…検索条件にヒットするorステータスに該当しないデータ
		else {
			if(searchOrderInfoDto.getOrderStatus().equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))){
				searchOrderInfoDto.setOrderStatus(ORDER_STATUS_ORDERING);
				order.setOrderStatus(ORDER_STATUS_ORDERING);
			}
			//検索ステータスと発注ステータスが一致しないデータを除外
			if(!order.getOrderStatus().equals(searchOrderStatus)) {
				return true;
			}
		}
		return false;
	}
	/** 検索結果データ作成 */
	private SapSearchOrderDto createSearchResultData(Map<String, Object> map, String orderNumber,
			String koujiCode, String orderDate, SearchOrderInfoDto searchOrderInfoDto, SearchKoujiInfoDto searchKoujiInfoDto, TCloudSignEntity cloudSignInfo) {

		String documentId = null;
		SapSearchOrderDto dto = new SapSearchOrderDto();

		dto.setOrderNumber(orderNumber);
		dto.setAcceptStatus(map.get(SapApiConsts.PARAMS_ID_ZNKFLG).toString());
		dto.setGyousyaCode(map.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
		dto.setGyousyaName(map.get(SapApiConsts.PARAMS_ID_ZGYSNM).toString());
		dto.setKoujiCode(koujiCode);
		dto.setSapOrderDate(orderDate);
		//SAPで発注済みのものはそれを優先
		if(!StringUtils.isNullString(orderDate) && !orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
			dto.setOrderDate(orderDate);
		}
		//EDIで発注済みのもので発注依頼日がnullでない場合はそれを優先
		else if(searchOrderInfoDto != null && searchOrderInfoDto.getConfirmationRequestDate() != null) {
			dto.setOrderDate(sdf.format(searchOrderInfoDto.getConfirmationRequestDate()));
		}
		//上記以外はSAPのデータを設定
		else {
			dto.setOrderDate(orderDate);
		}
		dto.setSaimokuKousyuCode(map.get(SapApiConsts.PARAMS_ID_ZSMKNO).toString());
		dto.setSaimokuKousyuName(map.get(SapApiConsts.PARAMS_ID_ZSMKSY).toString());
		dto.setOrderAmount(map.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString());
		dto.setOrderAmountTax(map.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString());
		dto.setAcceptAmount(map.get(SapApiConsts.PARAMS_ID_ZUKKGK).toString());
		dto.setAcceptAmountTax(map.get(SapApiConsts.PARAMS_ID_ZUKKZG).toString());
		dto.setOrderInfo(searchOrderInfoDto);
		dto.setKoujiInfo(searchKoujiInfoDto);
		dto.setSettlementCompFlg(map.get(SapApiConsts.PARAMS_ID_ZSKFLG).toString());
		dto.setOrderSettlementFlg(map.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString());
		/** ヒアリング対応 ⑤発注メールの有効期限切れ対応 */
		if(cloudSignInfo != null && (searchOrderInfoDto != null &&
				searchOrderInfoDto.getConfirmationRequestDate() != null && searchOrderInfoDto.getConfirmationAgreeDate() == null)) {
			documentId = cloudSignInfo.getFileId();
		}
		dto.setDocumentId(documentId);
		return dto;
	}

	/** sapデータ受信結果の整形 */
	private List<Map<String, Object>> getSapListData(Map<String, Object> data, String argKey) {
		List<Map<String, Object>> ret = new ArrayList<>();

		//結果情報取得
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
			log.info(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
			return ret;
			//throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		Object sap = data.get(argKey);

		//sapのデータが存在しなかったら空のリストを返却
		if(sap == null) {
			return ret;
		}

		//1件より多い場合
		if(sap instanceof List) {
			ret = (List<Map<String, Object>>)sap;
		}
		//1件の場合
		else {
			ret.add((Map<String, Object>)sap);
		}

		return ret;
	}

	/** オーダーステータスの設定 */
	private void setOrderStatus(SearchOrderInfoDto soinfDto, String orderDate) {
		//オーダーステータスの算出
		String remandFlg = Objects.nonNull(soinfDto.getRemandFlg()) ? soinfDto.getRemandFlg() : "";
		String staffReceiptFlg = Objects.nonNull(soinfDto.getStaffReceiptFlg()) ? soinfDto.getStaffReceiptFlg() : "";
		String confirmationFlg = Objects.nonNull(soinfDto.getConfirmationFlg()) ? soinfDto.getConfirmationFlg() : "";
		orderDate = Objects.nonNull(orderDate) ? orderDate : "";
		if (remandFlg.equals("1")) {
			soinfDto.setOrderStatus("5");
		} else if (confirmationFlg.equals("0")) {
			if (Objects.isNull(soinfDto.getConfirmationRequestDate())) {
				soinfDto.setOrderStatus("0");
				if (! orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
					soinfDto.setOrderStatus("1");
				}
			} else {
				soinfDto.setOrderStatus("1");
			}
		} else if (confirmationFlg.equals("1")) {
			if (Objects.isNull(soinfDto.getWorkNumber())) {
				soinfDto.setOrderStatus("2");
			}
		} else if (Objects.nonNull(soinfDto.getWorkNumber())) {
			if (staffReceiptFlg.equals("0")) {
				soinfDto.setOrderStatus("3");
			}
		} else if (staffReceiptFlg.equals("1")) {
			if (remandFlg.equals("0")) {
				soinfDto.setOrderStatus("4");
			}
		} else {
			soinfDto.setOrderStatus("0");
		}
	}

}
