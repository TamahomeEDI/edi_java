package jp.co.edi_java.app.util.sap;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.w3c.dom.Document;

import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.xml.DomParser;
import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;
import jp.co.keepalive.springbootfw.util.http.CommonHttpClient;
import jp.co.keepalive.springbootfw.util.http.HttpRequestHeaders;
import jp.co.keepalive.springbootfw.util.http.HttpRequestParams;
import jp.co.keepalive.springbootfw.util.http.HttpsUtil;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Component
public class SapApi {

	private static String BASE_URL;

	//共通パラメータ（モジュール名）
	private static String PARAMS_KEY_BAPI = "BAPI";

	//モジュール名
	public static String PARAMS_VALUE_MODULE_UPD_UKESHO = "Z_JRI15P203_1003_UPD_UKESHO";		//請書登録
	public static String PARAMS_VALUE_MODULE_REQ_INSDATA = "Z_JRI15P203_0803_REQ_INSDATA";	//納品・出来高報告受入登録	※初期開発では使わない
	public static String PARAMS_VALUE_MODULE_INS_KENSHU = "Z_JRI15P203_1103_INS_KENSHU";		//検収明細登録
	public static String PARAMS_VALUE_MODULE_SEL_DATA = "Z_JRI15P206_0103_SEL_DATA";			//発注取消合意書登録
	public static String PARAMS_VALUE_MODULE_GET_WBSLIST = "Z_JRI15P207_0101_GET_WBSLIST";	//発注一覧取得
	public static String PARAMS_VALUE_MODULE_GET_WBSLIST2 = "Z_JRI15P207_0103_GET_WBSLIST2";	//発注詳細取得

	private static String PARAMS_VALUE_ZSCRID = "P30006";	//画面ID
	private static String PARAMS_VALUE_ZAPLID = "0";		//アプリケーションID（FAX=0）

	private SapApi(@Value("${sap.base.url}") String url) {
		BASE_URL = url;
	}

	//請書登録
	public static Map<String, Object> confirmation(String orderNumber, String eigyousyoCode, String syainCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_UPD_UKESHO);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_03001  + "." + SapApiConsts.PARAMS_ID_ZSCRID, PARAMS_VALUE_ZSCRID);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_03001  + "." + SapApiConsts.PARAMS_ID_ZAPLID, PARAMS_VALUE_ZAPLID);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_03001  + "." + SapApiConsts.PARAMS_ID_ZPRCTR, eigyousyoCode);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03002 + "." + SapApiConsts.PARAMS_ID_EBELN, orderNumber);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03003 + "." + SapApiConsts.PARAMS_ID_ZJUSNM, syainCode);
		Document doc = CommonHttpClient.postXML(BASE_URL, null, params.getParams());
		Map<String, Object> parsedList = DomParser.parse(doc, SapApiConsts.NODE_NAME_XML);
		return parsedList;
	}

	//納品書受入登録
	public static Map<String, Object> deliveryAcceptanceRegist(String constructionCode, String toshoCode, String fileCode, String fileNo, String fileId) {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.post(BASE_URL, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	//出来高報告書受入登録
	public static Map<String, Object> workReportAcceptanceRegist(String constructionCode, String toshoCode, String fileCode, String fileNo) {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.get(BASE_URL, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	//検収明細登録
	public static Map<String, Object> inspectionReceiptRegist() {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.get(BASE_URL, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	//発注取消合意書登録
	public static Map<String, Object> orderCancelRegist() {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.get(BASE_URL, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	//発注一覧取得
	public static Map<String, Object> orderList(@Validated SearchForm form) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_WBSLIST);
		//地区本部コード
		if(!StringUtils.isNullString(form.getEigyousyoGroupCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZJTHCD, form.getEigyousyoGroupCode());
		}
		//支店コード
		if(!StringUtils.isNullString(form.getEigyousyoCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZSKSCD, form.getEigyousyoCode());
		}
		//工事コード
		if(!StringUtils.isNullString(form.getKoujiCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZWRKCD, form.getKoujiCode());
		}
		//※工事状況
		if(!StringUtils.isNullString(form.getKoujiStatus())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZWRKST, form.getKoujiStatus());
		}
		//※業者コード
		if(!StringUtils.isNullString(form.getGyousyaCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZGYSCD, form.getGyousyaCode());
		}
		//細目工種コード
		if(!StringUtils.isNullString(form.getSaimokuKousyuCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSMKNO, form.getSaimokuKousyuCode());
		}
//		//納入完了フラグ
//		if(!StringUtils.isNullString(form.getProcess())) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHTSTA, form.getProcess());
//		}
		//発注番号（購買伝票番号）
		if(!StringUtils.isNullString(form.getOrderNumber())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_SEBELN, form.getOrderNumber());
		}
		//※進捗
		if(!StringUtils.isNullString(form.getProcess())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZPSMST, form.getProcess());
		}
		//工務担当社員コード
		if(!StringUtils.isNullString(form.getSyainCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZKMTNM, form.getSyainCode());
		}
		//着工実績日from
		if(!StringUtils.isNullString(form.getKoujiStartDateFrom())) {
			String koujiStartDateFrom = form.getKoujiStartDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_F, koujiStartDateFrom);
		}
		//着工実績日to
		if(!StringUtils.isNullString(form.getKoujiStartDateTo())) {
			String koujiStartDateTo = form.getKoujiStartDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_T, koujiStartDateTo);
		}
		//竣工実績日from
		if(!StringUtils.isNullString(form.getKoujiCompleteDateFrom())) {
			String koujiCompleteDateFrom = form.getKoujiCompleteDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_F, koujiCompleteDateFrom);
		}
		//竣工実績日to
		if(!StringUtils.isNullString(form.getKoujiCompleteDateTo())) {
			String koujiCompleteDateTo = form.getKoujiCompleteDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_T, koujiCompleteDateTo);
		}
		//引渡実績日from
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateFrom())) {
			String koujiDeliveryDateFrom = form.getKoujiDeliveryDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_F, koujiDeliveryDateFrom);
		}
		//引渡実績日to
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateTo())) {
			String koujiDeliveryDateTo = form.getKoujiDeliveryDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_T, koujiDeliveryDateTo);
		}
		//発注日from
		if(!StringUtils.isNullString(form.getOrderDateFrom())) {
			String orderDateFrom = form.getOrderDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_F, orderDateFrom);
		}
		//発注日to
		if(!StringUtils.isNullString(form.getOrderDateTo())) {
			String orderDateTo = form.getOrderDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_T, orderDateTo);
		}

//		//後で仕様変更入る
//		//社員で発注ステータスが「未発注」の場合「発注日を」00000000にする
//		if(form.getUserFlg().equals(SearchService.USER_FLG_TAMA) && orderStatusList.contains(SearchService.ORDER_STATUS_NOT_ORDERING)) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_F, "00000000");
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_T, "00000000");
//		}

		//請書発行日from
		if(!StringUtils.isNullString(form.getConfirmationDateFrom())) {
			String confirmationDateFrom = form.getConfirmationDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_F, confirmationDateFrom);
		}
		//請書発行日to
		if(!StringUtils.isNullString(form.getConfirmationDateTo())) {
			String confirmationDateTo = form.getConfirmationDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_T, confirmationDateTo);
		}
		//納品・出来高報告日from
		if(!StringUtils.isNullString(form.getDeliveryDateFrom())) {
			String deliveryDateFrom = form.getDeliveryDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_F, deliveryDateFrom);
		}
		//納品・出来高報告日to
		if(!StringUtils.isNullString(form.getDeliveryDateTo())) {
			String deliveryDateTo = form.getDeliveryDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_T, deliveryDateTo);
		}
		//承認完了日from
		if(!StringUtils.isNullString(form.getCompletionDateFrom())) {
			String completionDateFrom = form.getCompletionDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_F, completionDateFrom);
		}
		//承認完了日to
		if(!StringUtils.isNullString(form.getCompletionDateTo())) {
			String completionDateTo = form.getCompletionDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_T, completionDateTo);
		}

		Document doc = CommonHttpClient.postXML(BASE_URL, null, params.getParams());
		Map<String, Object> parsedList = DomParser.parse(doc, SapApiConsts.NODE_NAME_XML);
		parsedList.put("params", params.getParams());
		return parsedList;
	}

	//発注詳細取得
	public static Map<String, Object> orderDetail(String orderNumber) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_WBSLIST2);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_SEBELN, orderNumber);
		Document doc = CommonHttpClient.postXML(BASE_URL, null, params.getParams());
		Map<String, Object> parsedList = DomParser.parse(doc, SapApiConsts.NODE_NAME_XML);
		return parsedList;
	}

	private static HttpRequestHeaders createCommonHeader() {
		HttpRequestHeaders headers = new HttpRequestHeaders();
		headers.addParam("accept", "application/json");
		return headers;
	}












	//発注一覧取得
	public static List<NameValuePair> orderParam(@Validated SearchForm form) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_WBSLIST);
		//地区本部コード
		if(!StringUtils.isNullString(form.getEigyousyoGroupCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZJTHCD, form.getEigyousyoGroupCode());
		}
		//支店コード
		if(!StringUtils.isNullString(form.getEigyousyoCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZSKSCD, form.getEigyousyoCode());
		}
		//工事コード
		if(!StringUtils.isNullString(form.getKoujiCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZWRKCD, form.getKoujiCode());
		}
		//※工事状況
		if(!StringUtils.isNullString(form.getKoujiStatus())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZWRKST, form.getKoujiStatus());
		}
		//※業者コード
		if(!StringUtils.isNullString(form.getGyousyaCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZGYSCD, form.getGyousyaCode());
		}
		//細目工種コード
		if(!StringUtils.isNullString(form.getSaimokuKousyuCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSMKNO, form.getSaimokuKousyuCode());
		}
//		//納入完了フラグ
//		if(!StringUtils.isNullString(form.getProcess())) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHTSTA, form.getProcess());
//		}
		//発注番号（購買伝票番号）
		if(!StringUtils.isNullString(form.getOrderNumber())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_SEBELN, form.getOrderNumber());
		}
		//※進捗
		if(!StringUtils.isNullString(form.getProcess())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZPSMST, form.getProcess());
		}
		//工務担当社員コード
		if(!StringUtils.isNullString(form.getSyainCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZKMTNM, form.getSyainCode());
		}
		//着工実績日from
		if(!StringUtils.isNullString(form.getKoujiStartDateFrom())) {
			String koujiStartDateFrom = form.getKoujiStartDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_F, koujiStartDateFrom);
		}
		//着工実績日to
		if(!StringUtils.isNullString(form.getKoujiStartDateTo())) {
			String koujiStartDateTo = form.getKoujiStartDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_T, koujiStartDateTo);
		}
		//竣工実績日from
		if(!StringUtils.isNullString(form.getKoujiCompleteDateFrom())) {
			String koujiCompleteDateFrom = form.getKoujiCompleteDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_F, koujiCompleteDateFrom);
		}
		//竣工実績日to
		if(!StringUtils.isNullString(form.getKoujiCompleteDateTo())) {
			String koujiCompleteDateTo = form.getKoujiCompleteDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_T, koujiCompleteDateTo);
		}
		//引渡実績日from
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateFrom())) {
			String koujiDeliveryDateFrom = form.getKoujiDeliveryDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_F, koujiDeliveryDateFrom);
		}
		//引渡実績日to
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateTo())) {
			String koujiDeliveryDateTo = form.getKoujiDeliveryDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_T, koujiDeliveryDateTo);
		}
		//発注日from
		if(!StringUtils.isNullString(form.getOrderDateFrom())) {
			String orderDateFrom = form.getOrderDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_F, orderDateFrom);
		}
		//発注日to
		if(!StringUtils.isNullString(form.getOrderDateTo())) {
			String orderDateTo = form.getOrderDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_T, orderDateTo);
		}

//		//後で仕様変更入る
//		//社員で発注ステータスが「未発注」の場合「発注日を」00000000にする
//		if(form.getUserFlg().equals(SearchService.USER_FLG_TAMA) && orderStatusList.contains(SearchService.ORDER_STATUS_NOT_ORDERING)) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_F, "00000000");
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_T, "00000000");
//		}

		//請書発行日from
		if(!StringUtils.isNullString(form.getConfirmationDateFrom())) {
			String confirmationDateFrom = form.getConfirmationDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_F, confirmationDateFrom);
		}
		//請書発行日to
		if(!StringUtils.isNullString(form.getConfirmationDateTo())) {
			String confirmationDateTo = form.getConfirmationDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_T, confirmationDateTo);
		}
		//納品・出来高報告日from
		if(!StringUtils.isNullString(form.getDeliveryDateFrom())) {
			String deliveryDateFrom = form.getDeliveryDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_F, deliveryDateFrom);
		}
		//納品・出来高報告日to
		if(!StringUtils.isNullString(form.getDeliveryDateTo())) {
			String deliveryDateTo = form.getDeliveryDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_T, deliveryDateTo);
		}
		//承認完了日from
		if(!StringUtils.isNullString(form.getCompletionDateFrom())) {
			String completionDateFrom = form.getCompletionDateFrom().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_F, completionDateFrom);
		}
		//承認完了日to
		if(!StringUtils.isNullString(form.getCompletionDateTo())) {
			String completionDateTo = form.getCompletionDateTo().replace("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_T, completionDateTo);
		}
		return params.getParams();
	}

}