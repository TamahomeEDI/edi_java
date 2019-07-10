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

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.SearchDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dto.ChecklistOrderDetailDto;
import jp.co.edi_java.app.dto.ChecklistOrderDto;
import jp.co.edi_java.app.dto.GyousyaEigyousyoDto;
import jp.co.edi_java.app.dto.SearchKoujiInfoDto;
import jp.co.edi_java.app.dto.SearchOrderInfoDto;
import jp.co.edi_java.app.form.SearchForm;
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
public class ChecklistService {

	@Autowired
    public SearchDao searchDao;

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	public static String USER_FLG_TAMA = "1";
	public static String USER_FLG_PARTNER = "0";

	public static String ORDER_STATUS_NOT_ORDERING = "0";
	public static String ORDER_STATUS_ORDERING = "1";

	//未発注の発注日（00000000）
	public static String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//発注精算フラグ（マイナス発注）
	public static String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@SuppressWarnings("unchecked")
	public ArrayList<ChecklistOrderDto> getOrderList(SearchForm form) {

		//結果リストの初期化
		ArrayList<ChecklistOrderDto> ret = new ArrayList<ChecklistOrderDto>();

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
		//施工支店リストの取得
		List<GyousyaEigyousyoDto> eigyousyoList = mEigyousyoDao.selectListByPartner(form.getGyousyaCode(),null);
		//SAPデータ取得結果リストの初期化
		List<Map<String, Object>> sapOrderList = new ArrayList<Map<String, Object>>();

		//発注番号リスト
		List<String> orderNumberList =  new ArrayList<String>();
		//SAPOrderマップ
		Map<String, Map<String, Object>> sapOrderMap = new HashMap<String, Map<String, Object>>();
		//工事コードマップ
		Map<String, String> koujiMap = new HashMap<String, String>();

		// 施工支店分のSAPデータ取得
		if (Objects.nonNull(eigyousyoList)) {
			for (GyousyaEigyousyoDto eigyousyo : eigyousyoList) {
				form.setEigyousyoCode(eigyousyo.getEigyousyoCode());
				form.setEigyousyoGroupCode(eigyousyo.getEigyousyoGroupCode());

				//sapからデータ取得
				Map<String, Object> data = SapApi.orderList(form);

				//結果情報取得
				Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
				if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
					throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
				}
				Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
				if (Objects.nonNull(obj)) {
					if(obj instanceof List) {
						//1件より多い場合
						for (Map<String, Object> o : (List<Map<String, Object>>)obj) {
							String orderSettlementFlg = o.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
							String orderNumber = o.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
							if (Objects.nonNull(orderNumber) && !orderNumber.isEmpty()) {
								if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
									//発注番号をキャッシュ
									orderNumberList.add(orderNumber);
									//SAPOrderをキャッシュ
									sapOrderMap.put(orderNumber, o);
									//工事コードを取得
									koujiMap.put(o.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString(), "1");

									sapOrderList.add(o);
								}
							}
						}
					} else {
						//1件の場合
						Map<String, Object> o = (Map<String, Object>)obj;
						String orderSettlementFlg = o.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
						String orderNumber = o.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
						if (Objects.nonNull(orderNumber) && !orderNumber.isEmpty()) {
							if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
								//発注番号をキャッシュ
								orderNumberList.add(orderNumber);
								//SAPOrderをキャッシュ
								sapOrderMap.put(orderNumber, o);
								//工事コードを取得
								koujiMap.put(o.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString(), "1");

								sapOrderList.add(o);
							}
						}
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

		// 工事情報の取得
		List<String> koujiCodeList =  new ArrayList<String>();
		for (String kc : koujiMap.keySet()) {
			koujiCodeList.add(kc);
		}
		Map<String, SearchKoujiInfoDto> skinfoMap = new HashMap<String, SearchKoujiInfoDto>();
		List<SearchKoujiInfoDto> koujiList = searchDao.getKoujiInfoList(koujiCodeList, form.getKoujiName());
		for (SearchKoujiInfoDto koujiDto: koujiList) {
			//工事情報のキャッシュ
			skinfoMap.put(koujiDto.getKoujiCode(), koujiDto);
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
				//検索ステータスと発注ステータスが一致しないデータを除外
				if(!order.getOrderStatus().equals(searchOrderStatus)) {
					continue;
				}
			}

			if(!(paramFlg && searchOrderInfoDto == null) && searchKoujiInfoDto != null) {
				//発注書一覧の表示対象に対し発注情報詳細を取得して、チェックリストデータを生成する
				ChecklistOrderDto dto = getChecklistData(orderNumber, searchOrderInfoDto, searchKoujiInfoDto);
				if (Objects.nonNull(dto)) {
					ret.add(dto);
				}
			}
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

	/** チェックリスト出力用データの作成 */
	@SuppressWarnings("unchecked")
	private ChecklistOrderDto getChecklistData(String orderNumber, SearchOrderInfoDto searchOrderInfoDto, SearchKoujiInfoDto searchKoujiInfoDto) {
		//発注詳細取得
		Map<String, Object> data = SapApi.orderDetail(orderNumber);
		//発注詳細は1件の場合と複数件の場合がある（複数件の場合はマイナス発注有）
		Object sapObj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		Map<String, Object> retDetail = null;
		//オブジェクトがListの場合
		if (sapObj instanceof List){
			List<Map<String, Object>> retDetailList = (List<Map<String, Object>>) sapObj;
			//発注詳細分チェックする
			for (Map<String, Object> detail : retDetailList) {
				//発注精算フラグ取得
				String orderSettlementFlg = detail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
				//マイナス発注じゃなかったら発注詳細とする
				if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
					retDetail = detail;
					break;
				}
			}
			//発注詳細が存在しなかったら
			if (retDetail == null) {
				throw new CoreRuntimeException(ResponseCode.ERROR_CODE_1000);
			}
		}
		//List以外の場合
		else {
			retDetail = (Map<String, Object>) sapObj;
		}
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if (SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		String orderDate = retDetail.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
		ChecklistOrderDto ret = new ChecklistOrderDto();
		ret.setOrderNumber(retDetail.get(SapApiConsts.PARAMS_ID_SEBELN).toString());
		ret.setGyousyaCode(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
		ret.setGyousyaName(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSNM).toString());
		ret.setKoujiCode(retDetail.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString());
		ret.setKoujiName(searchKoujiInfoDto.getKoujiName());
		ret.setEigyousyoCode(searchKoujiInfoDto.getEigyousyoCode());
		ret.setEigyousyoName(searchKoujiInfoDto.getEigyousyoName());
		ret.setSyainCode(searchKoujiInfoDto.getTantouSyainCode());
		ret.setSyainName(searchKoujiInfoDto.getSyainName());
		ret.setTyakkouDate(searchKoujiInfoDto.getTyakkouDate());
		ret.setTyakkouDateY(searchKoujiInfoDto.getTyakkouDateY());
		ret.setSyunkouDate(searchKoujiInfoDto.getSyunkouDate());
		ret.setSyunkouDateY(searchKoujiInfoDto.getSyunkouDateY());
		ret.setKentikutiYuubin(searchKoujiInfoDto.getKentikutiYuubin());
		ret.setKentikutiJyuusyo(searchKoujiInfoDto.getKentikutiJyuusyo());
		ret.setSaimokuKousyuCode(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKNO).toString());
		ret.setSaimokuKousyuName(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKSY).toString());
		ret.setOrderAmount(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString());
		//SAPで発注済みのものはそれを優先
		if (!StringUtils.isNullString(orderDate) && !orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
			ret.setOrderDate(orderDate);
		}
		//EDIで発注済みのもので発注依頼日がnullでない場合はそれを優先
		else if(searchOrderInfoDto != null && searchOrderInfoDto.getConfirmationRequestDate() != null) {
			ret.setOrderDate(sdf.format(searchOrderInfoDto.getConfirmationRequestDate()));
		}
		//上記以外はSAPのデータを設定
		else {
			ret.setOrderDate(orderDate);
		}

		ArrayList<ChecklistOrderDetailDto> itemList = new ArrayList<ChecklistOrderDetailDto>();

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01002);
		List<Map<String, Object>> retItemList = new ArrayList<>();
		if (obj instanceof List) {
			retItemList = (List<Map<String, Object>>)obj;
		} else {
			retItemList.add((Map<String, Object>)obj);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : retItemList) {
			ChecklistOrderDetailDto dto = new ChecklistOrderDetailDto();
			dto.setName(map.get(SapApiConsts.PARAMS_ID_MAKTX).toString());
			dto.setPrice(map.get(SapApiConsts.PARAMS_ID_ZHTTNK).toString());
			dto.setQuantity(map.get(SapApiConsts.PARAMS_ID_ZMENGE).toString());
			dto.setUnit(map.get(SapApiConsts.PARAMS_ID_ZTANIN).toString());
			dto.setAmount(map.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString());
			dto.setAmountTax(map.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString());
			dto.setAmountTaxInclud(map.get(SapApiConsts.PARAMS_ID_ZHTKZTG).toString());
			dto.setDeliveryQuantity(map.get(SapApiConsts.PARAMS_ID_ZUKEMG).toString());
			dto.setDeliveryAmount(map.get(SapApiConsts.PARAMS_ID_ZUKEKN).toString());
			dto.setRemainQuantity(map.get(SapApiConsts.PARAMS_ID_ZZANMG).toString());
			dto.setRemainAmount(map.get(SapApiConsts.PARAMS_ID_ZZANKN).toString());
			itemList.add(dto);
		}
		ret.setItemList(itemList);
		return ret;
	}
}
