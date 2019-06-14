package jp.co.edi_java.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.SearchDao;
import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.TWorkReportDao;
import jp.co.edi_java.app.dto.SapSearchOrderDto;
import jp.co.edi_java.app.dto.SearchDeliveryDto;
import jp.co.edi_java.app.dto.SearchEstimateDto;
import jp.co.edi_java.app.dto.SearchKoujiInfoDto;
import jp.co.edi_java.app.dto.SearchOrderInfoDto;
import jp.co.edi_java.app.dto.SearchWorkReportDto;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Service
@Scope("request")
public class SearchService {

	@Autowired
    public SearchDao searchDao;

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public TDeliveryDao tDeliveryDao;

	@Autowired
    public TWorkReportDao tWorkReportDao;

	@Autowired
    public MailService mailService;

	public static String USER_FLG_TAMA = "1";
	public static String USER_FLG_PARTNER = "0";

	public static String ORDER_STATUS_NOT_ORDERING = "0";
	public static String ORDER_STATUS_ORDERING = "1";

	//未発注の発注日（00000000）
	public static String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//発注精算フラグ（マイナス発注）
	public static String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

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

				//EDIにデータが存在しない
				if(searchOrderInfoDto == null) {
					//業者の場合、未発注(発注日が00000000または空文字)データを除外
					if(userFlg.equals(USER_FLG_PARTNER) && (StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
						continue;
					}
					//検索ステータスが未発注または発注済み以外の場合、データを除外
					else if(!(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) || searchOrderStatus.equals(ORDER_STATUS_ORDERING))) {
						continue;
					}
					//検索ステータスが未発注の場合、未発注(発注日が00000000または空文字)以外のデータを除外
					else if(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
						continue;
					}
					//検索ステータスが未発注以外の場合、未発注(発注日が00000000または空文字)データを除外
					else if((StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) && !searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING)) {
						continue;
					}
					//検索ステータスと発注ステータスが一致しないデータを除外
					if(order != null) {
						if(!order.getOrderStatus().equals(searchOrderStatus)) {
							continue;
						}
					}
				}
				//EDIにデータが存在する…検索条件にヒットするorステータスに該当しないデータ
				else {
					if(searchOrderInfoDto.getOrderStatus().equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))){
						searchOrderInfoDto.setOrderStatus(ORDER_STATUS_ORDERING);
						order.setOrderStatus(ORDER_STATUS_ORDERING);
					}
	//				//検索ステータスが未発注かつsearchOrderInfoDtoのステータスが未発注以外の場合、データを除外
	//				if(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) && searchOrderInfoDto.getOrderStatus() != ORDER_STATUS_NOT_ORDERING) {
	//					continue;
	//				}
					//検索ステータスと発注ステータスが一致しないデータを除外
					if(!order.getOrderStatus().equals(searchOrderStatus)) {
						continue;
					}
				}

				if(!(paramFlg && searchOrderInfoDto == null) && searchKoujiInfoDto != null) {
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
					ret.add(dto);
				}
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


}
