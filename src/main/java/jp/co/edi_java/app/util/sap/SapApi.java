package jp.co.edi_java.app.util.sap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.w3c.dom.Document;

import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.xml.DomParser;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.http.CommonHttpClient;
import jp.co.keepalive.springbootfw.util.http.HttpRequestHeaders;
import jp.co.keepalive.springbootfw.util.http.HttpRequestParams;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SapApi {

	private static String BASE_URL;

	private static int RETRY_POST_COUNT = 3;

	//共通パラメータ（モジュール名）
	private static String PARAMS_KEY_BAPI = "BAPI";

	//モジュール名
	//請書登録
	public static String PARAMS_VALUE_MODULE_UPD_UKESHO = "Z_JRI15P203_1003_UPD_UKESHO";				//請書登録
	public static String PARAMS_VALUE_MODULE_SEL_UKESHO = "Z_JRI15P203_1004_SEL_UKESHO";				//請書受領登録対象検索
	public static String PARAMS_VALUE_MODULE_UPD_UKESHO_JYURYOU = "Z_JRI15P203_1009_UPD_JYURYOU";		//請書受領登録

	public static String PARAMS_VALUE_MODULE_INS_KENSHU = "Z_JRI15P203_1103_INS_KENSHU";				//検収明細登録
	public static String PARAMS_VALUE_MODULE_SEL_DATA = "Z_JRI15P206_0103_SEL_DATA";					//発注取消合意書登録

	//検索表示関連
	public static String PARAMS_VALUE_MODULE_GET_WBSLIST = "Z_JRI15P207_0101_GET_WBSLIST";				//発注一覧取得(支店、業者、工事状況単位)
	public static String PARAMS_VALUE_MODULE_GET_WBSLIST2 = "Z_JRI15P207_0103_GET_WBSLIST2";			//発注詳細取得
	public static String PARAMS_VALUE_MODULE_GET_CNSTCT_CL = "Z_JRI15P203_0101_GET_CNSTCT_CL";			//発注一覧取得(工事一覧)
	public static String PARAMS_VALUE_MODULE_GET_CNSTRT_VW = "Z_JRI15P203_0102_GET_CNSTRT_VW";			//発注一覧取得(会社、工事コード単位)
	public static String PARAMS_VALUE_MODULE_GET_ITEM_DTL2 = "Z_JRI15P203_0103_GET_ITEM_DTL2";			//発注情報取得(POSID単位)

	//納品書・出来高報告書受入
	public static String PARAMS_VALUE_MODULE_SEL_GOODS_2 = "Z_JRI15P203_0504_SEL_GOODS_2";				//納品書受入入力_詳細
	public static String PARAMS_VALUE_MODULE_GET_DETAILS = "Z_JRI15P203_0904_GET_DETAILS";				//出来高報告書受入入力_詳細
	public static String PARAMS_VALUE_MODULE_INS_DTLDATA = "Z_JRI15P203_0805_INS_DTLDATA";				//納品書・出来高報告書受入入力_確認
	public static String PARAMS_VALUE_MODULE_GET_HEADDATE = "Z_JRI15P203_0801_GET_HEADDATE";			//納品書・出来高報告書受入入力_表示
	public static String PARAMS_VALUE_MODULE_REQ_INSDATA = "Z_JRI15P203_0803_REQ_INSDATA";				//納品書・出来高報告書受入入力_申請
	public static String PARAMS_VALUE_MODULE_APPR_INSDATA1 = "Z_JRI15P203_0804_APPR_INSDATA1";			//納品書・出来高報告書受入入力_決済

	public static String PARAMS_VALUE_MODULE_SET_ZHTYDT = "Z_JRI15P207_0104_SET_ZHTYDT";				//発注日連携

	private static String PARAMS_VALUE_ZSCRID = "P30006";												//画面ID
	private static String PARAMS_VALUE_ZAPLID = "0";													//アプリケーションID（FAX=0）
	private static String PARAMS_VALUE_BUKRS = "1000";													//会社コード
	private static String PARAMS_VALUE_ZFAXJU = "X";													//請書FAX未受領選択フラグ
	private static String PARAMS_VALUE_ZGENJU = "X";													//請書原本未受領選択フラグ
	private static String PARAMS_VALUE_ZDTSBT_DELIVERY = "1";											//データ種別 納品書
	private static String PARAMS_VALUE_ZDTSBT_WORK_REPORT = "2";										//データ種別 出来高報告書
	private static String PARAMS_VALUE_ZKNFLG = "X";													//確認済フラグ
	private static String PARAMS_VALUE_ZWFSTA = "9";													//決済済

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
		return retryPostXML(BASE_URL, null, params.getParams());
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
//		//※工事状況
//		if(!StringUtils.isNullString(form.getKoujiStatus())) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZWRKST, form.getKoujiStatus());
//		}
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
//		//※進捗
//		if(!StringUtils.isNullString(form.getProcess())) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZPSMST, form.getProcess());
//		}
		//工務担当社員コード
		if(!StringUtils.isNullString(form.getSyainCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZKMTNM, form.getSyainCode());
		}
		//着工実績日from
		if(!StringUtils.isNullString(form.getKoujiStartDateFrom())) {
			String koujiStartDateFrom = form.getKoujiStartDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_F, koujiStartDateFrom);
		}
		//着工実績日to
		if(!StringUtils.isNullString(form.getKoujiStartDateTo())) {
			String koujiStartDateTo = form.getKoujiStartDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZCKJSD_T, koujiStartDateTo);
		}
		//竣工実績日from
		if(!StringUtils.isNullString(form.getKoujiCompleteDateFrom())) {
			String koujiCompleteDateFrom = form.getKoujiCompleteDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_F, koujiCompleteDateFrom);
		}
		//竣工実績日to
		if(!StringUtils.isNullString(form.getKoujiCompleteDateTo())) {
			String koujiCompleteDateTo = form.getKoujiCompleteDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKJSD_T, koujiCompleteDateTo);
		}
		//引渡実績日from
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateFrom())) {
			String koujiDeliveryDateFrom = form.getKoujiDeliveryDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_F, koujiDeliveryDateFrom);
		}
		//引渡実績日to
		if(!StringUtils.isNullString(form.getKoujiDeliveryDateTo())) {
			String koujiDeliveryDateTo = form.getKoujiDeliveryDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZHWJSD_T, koujiDeliveryDateTo);
		}
		//発注日from
		if(!StringUtils.isNullString(form.getOrderDateFrom())) {
			String orderDateFrom = form.getOrderDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZORDDT_F, orderDateFrom);
		}
		//発注日to
		if(!StringUtils.isNullString(form.getOrderDateTo())) {
			String orderDateTo = form.getOrderDateTo().replaceAll("/", "");
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
			String confirmationDateFrom = form.getConfirmationDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_F, confirmationDateFrom);
		}
		//請書発行日to
		if(!StringUtils.isNullString(form.getConfirmationDateTo())) {
			String confirmationDateTo = form.getConfirmationDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSKHKD_T, confirmationDateTo);
		}
		//納品・出来高報告日from
		if(!StringUtils.isNullString(form.getDeliveryDateFrom())) {
			String deliveryDateFrom = form.getDeliveryDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_F, deliveryDateFrom);
		}
		//納品・出来高報告日to
		if(!StringUtils.isNullString(form.getDeliveryDateTo())) {
			String deliveryDateTo = form.getDeliveryDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZNOUHD_T, deliveryDateTo);
		}
		//承認完了日from
		if(!StringUtils.isNullString(form.getCompletionDateFrom())) {
			String completionDateFrom = form.getCompletionDateFrom().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_F, completionDateFrom);
		}
		//承認完了日to
		if(!StringUtils.isNullString(form.getCompletionDateTo())) {
			String completionDateTo = form.getCompletionDateTo().replaceAll("/", "");
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZSNKRD_T, completionDateTo);
		}

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//発注詳細取得
	public static Map<String, Object> orderDetail(String orderNumber) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_WBSLIST2);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_SEBELN, orderNumber);
		return retryPostXML(BASE_URL, null, params.getParams());
	}

	private static HttpRequestHeaders createCommonHeader() {
		HttpRequestHeaders headers = new HttpRequestHeaders();
		headers.addParam("accept", "application/json");
		return headers;
	}

	//発注日連携
	public static Map<String, Object> setOrderDate(String orderNumber, String koujiCode, String orderDate) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_SET_ZHTYDT);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_SEBELN, orderNumber);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZWRKCD, koujiCode);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZHTYDT, orderDate);
		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//請書発注番号連携検索
	public static Map<String, Object> selectUkeshoJyuryou(String koujiCode, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_SEL_UKESHO);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_04001  + "." + SapApiConsts.PARAMS_ID_POSID, koujiCode);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_04001  + "." + SapApiConsts.PARAMS_ID_ZFAXJU, PARAMS_VALUE_ZFAXJU);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_04001  + "." + SapApiConsts.PARAMS_ID_ZGENJU, PARAMS_VALUE_ZGENJU);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04003  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);
		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//請書発注番号連携
	public static Map<String, Object> setOrderNumberByUkeshoJyuryou(String orderNumber, String lastUpdateDate, String lastUpdateTime) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_UPD_UKESHO_JYURYOU);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_09001  + "." + SapApiConsts.PARAMS_ID_EBELN, orderNumber);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_09001  + "." + SapApiConsts.PARAMS_ID_AEDAT, lastUpdateDate);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_09001  + "." + SapApiConsts.PARAMS_ID_AEZEIT, lastUpdateTime);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_09001  + "." + SapApiConsts.PARAMS_ID_ZFAXJU, PARAMS_VALUE_ZFAXJU);
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_09001  + "." + SapApiConsts.PARAMS_ID_ZGENJU, PARAMS_VALUE_ZGENJU);
		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//発注一覧取得(工事一覧)
	public static Map<String, Object> koujiList(@Validated SearchForm form) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_CNSTCT_CL);
		//※実ユーザコード（ログインユーザの社員コード）
		if(!StringUtils.isNullString(form.getLoginUserCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_IE_01003  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, form.getLoginUserCode());
		}
		//支店コード
		if(!StringUtils.isNullString(form.getEigyousyoCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZSKSTN, form.getEigyousyoCode());
		}
		//工事コード
		if(!StringUtils.isNullString(form.getKoujiCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZWRKCD, form.getKoujiCode());
		}
//		//工事名称
		//JCO側で文字化けし、ヒットしない。JCOに合わせたエンコードが必要
//		if(!StringUtils.isNullString(form.getKoujiName())) {
//			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZPRJNM, form.getKoujiName());
//		}
		//工務担当社員コード
		if(!StringUtils.isNullString(form.getSyainCode())) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001 + "." + SapApiConsts.PARAMS_ID_ZKMTCD, form.getSyainCode());
		}

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//発注一覧取得(工事単位)
	public static Map<String, Object> orderListByKouji(String koujiCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_CNSTRT_VW);
		//※会社コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_02001  + "." + SapApiConsts.PARAMS_ID_BUKRS, PARAMS_VALUE_BUKRS);
		//※工事コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_02001  + "." + SapApiConsts.PARAMS_ID_ZWRKCD, koujiCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//発注情報取得(POSID単位)
	public static Map<String, Object> orderListByPosId(String posId, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_ITEM_DTL2);
		//※会社コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_BUKRS, PARAMS_VALUE_BUKRS);

		//※POSID
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_POSID, posId);

		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03003  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//納品書受入入力_詳細情報取得
	public static Map<String, Object> getDeliveryItemList(String orderNumber, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_SEL_GOODS_2);

		//発注書番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_04001  + "." + SapApiConsts.PARAMS_ID_EBELN, orderNumber);

		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04004  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//出来高報告書受入入力_詳細情報取得
	public static Map<String, Object> getWorkReportItemList(String orderNumber, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_DETAILS);

		//発注書番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_EBELN, orderNumber);

		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04006  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//納品書・出来高報告書受入入力_確認 (納品数の登録) update
	public static Map<String, Object> setDeliveryItemQuantity(String eigyousyoCode, String orderNumber, String acceptanceDate, String wfSeqNo, String lastUpdateDate, String lastUpdateDateTime, List<Map<String, String>> sapDetail, String userCode) {
		return setDeliveryWorkReportItemQuantity(PARAMS_VALUE_ZDTSBT_DELIVERY, eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateDateTime, null, sapDetail, userCode);
	}
	//納品書・出来高報告書受入入力_確認 (納品数の登録) insert
	public static Map<String, Object> setDeliveryItemQuantity(String eigyousyoCode, String orderNumber, String acceptanceDate, List<Map<String, String>> sapDetail, String userCode) {
		return setDeliveryWorkReportItemQuantity(PARAMS_VALUE_ZDTSBT_DELIVERY, eigyousyoCode, orderNumber, acceptanceDate, null, null, null, null, sapDetail, userCode);
	}
	//納品書・出来高報告書受入入力_確認 (査定率の登録) update
	public static Map<String, Object> setWorkReportItemQuantity(String eigyousyoCode, String orderNumber, String acceptanceDate, String wfSeqNo, String lastUpdateDate, String lastUpdateDateTime, String workRate, List<Map<String, String>> sapDetail, String userCode) {
		return setDeliveryWorkReportItemQuantity(PARAMS_VALUE_ZDTSBT_WORK_REPORT, eigyousyoCode, orderNumber, acceptanceDate, wfSeqNo, lastUpdateDate, lastUpdateDateTime, workRate, sapDetail, userCode);
	}
	//納品書・出来高報告書受入入力_確認 (査定率の登録) insert
	public static Map<String, Object> setWorkReportItemQuantity(String eigyousyoCode, String orderNumber, String acceptanceDate, String workRate, List<Map<String, String>> sapDetail, String userCode) {
		return setDeliveryWorkReportItemQuantity(PARAMS_VALUE_ZDTSBT_WORK_REPORT, eigyousyoCode, orderNumber, acceptanceDate, null, null, null, workRate, sapDetail, userCode);
	}
	//納品書・出来高報告書受入入力_確認 (納品数の登録)
	public static Map<String, Object> setDeliveryWorkReportItemQuantity(String dataType, String eigyousyoCode, String orderNumber, String acceptanceDate, String wfSeqNo, String lastUpdateDate, String lastUpdateDateTime, String workRate, List<Map<String, String>> sapDetail, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_INS_DTLDATA);

		//※支店コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_PRCTR, eigyousyoCode);
		//※発注番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_EBELN, orderNumber);
		//※データ種別
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZDTSBT, dataType);
		//※受入日付
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZUKDAT, acceptanceDate);

		//受入査定率
		if (Objects.isNull(workRate)) {
			workRate = "0";
		}
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZUKEST, workRate);

		//受入金額
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZUKEKN, "0");

		//受入金額税額
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZUKEZG, "0");

		//SEQ
		if (Objects.isNull(wfSeqNo)) {
			wfSeqNo = "000";
		}
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_ZSEQNO, wfSeqNo);

		//最終更新日
		if (Objects.nonNull(lastUpdateDate)) {
			params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_AEDAT, lastUpdateDate);
		}

		//最終更新時間
		if (Objects.isNull(lastUpdateDateTime)) {
			lastUpdateDateTime = "00:00:00";
		}
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_05001  + "." + SapApiConsts.PARAMS_ID_AEZEIT, lastUpdateDateTime);

		if (Objects.nonNull(sapDetail)) {
			for (Map<String, String> item : sapDetail) {
				// 品目コード
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_MATNR, item.get(SapApiConsts.PARAMS_ID_MATNR));
				// テキスト(短)
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_TXZ01, item.get(SapApiConsts.PARAMS_ID_TXZ01));
				// 仕様名
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZMHNAM, item.get(SapApiConsts.PARAMS_ID_ZMHNAM));
				// 発注残数量
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_MENGE, item.get(SapApiConsts.PARAMS_ID_MENGE));
				// 納入数量
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZMENGE, item.get(SapApiConsts.PARAMS_ID_ZMENGE));
				// 発注単位
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_MEINS, item.get(SapApiConsts.PARAMS_ID_MEINS));
				// 単価
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_NETPR, item.get(SapApiConsts.PARAMS_ID_NETPR));
				// 納入金額
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_SUMPR, item.get(SapApiConsts.PARAMS_ID_SUMPR));
				// 購買伝票の明細番号
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_EBELP, item.get(SapApiConsts.PARAMS_ID_EBELP));
				// 発注数量
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZHTMNG, item.get(SapApiConsts.PARAMS_ID_ZHTMNG));
				// 単位コード
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZTANIC, item.get(SapApiConsts.PARAMS_ID_ZTANIC));
				// 発注金額
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZHTKGK, item.get(SapApiConsts.PARAMS_ID_ZHTKGK));
				// 納入受入残金額
				params.addParam(SapApiConsts.PARAMS_KEY_T_I_05002  + "." + SapApiConsts.PARAMS_ID_ZUKZKN, item.get(SapApiConsts.PARAMS_ID_ZUKZKN));
			}
		}
		//行数(上書き更新の場合も00000のままで影響無し)
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_05003  + "." + SapApiConsts.PARAMS_ID_ZLOWNO, "00000");
		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_05003  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//納品書_表示
	public static Map<String, Object> getDeliveryWFSeqNo(String eigyousyoCode, String userCode, String wfStatus) {
		return getWFSeqNo(PARAMS_VALUE_ZDTSBT_DELIVERY, eigyousyoCode, userCode, wfStatus);
	}

	//出来高報告書_表示
	public static Map<String, Object> getWorkReportWFSeqNo(String eigyousyoCode, String userCode, String wfStatus) {
		return getWFSeqNo(PARAMS_VALUE_ZDTSBT_WORK_REPORT, eigyousyoCode, userCode, wfStatus);
	}

	//納品書・出来高報告書受入入力_表示
	public static Map<String, Object> getWFSeqNo(String dataType, String eigyousyoCode, String userCode, String wfStatus) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_GET_HEADDATE);

		//※支店コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_PRCTR, eigyousyoCode);
		//※データ種別
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01001  + "." + SapApiConsts.PARAMS_ID_ZDTSBT, dataType);
		//※WFステータス
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01002  + "." + SapApiConsts.PARAMS_ID_ZWFSTA, wfStatus);
		//※確認済フラグ
		params.addParam(SapApiConsts.PARAMS_KEY_T_I_01003  + "." + SapApiConsts.PARAMS_ID_ZKNFLG, PARAMS_VALUE_ZKNFLG);
		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_01005  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//納品書・出来高報告書受入入力_申請
	public static Map<String, Object> applyDeliveryWorkReport(String eigyousyoCode, String orderNumber, String recordDate, String wfSeqNo, String acceptanceDate,  String wfNumber, String lastUpdateDate, String lastUpdateDateTime, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_REQ_INSDATA);
		// 会社コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_BUKRS, PARAMS_VALUE_BUKRS);
		// 支店コード
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZBSDKY, eigyousyoCode);
		// 発注番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZBSDK1, orderNumber);
		// レコード登録日
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZBSDK2, recordDate);
		// 連番
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZBSDK3, wfSeqNo);
		// 受入日付
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZBSDK4, acceptanceDate);
		// WF番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_ZWKFNO, wfNumber);
		// 変更日付
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_AEDAT, lastUpdateDate);
		// 変更時間
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03001  + "." + SapApiConsts.PARAMS_ID_AEZEIT, lastUpdateDateTime);
		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_03002  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);

		return retryPostXML(BASE_URL, null, params.getParams());
	}

	//納品書・出来高報告書受入入力_決済
	public static Map<String, Object> approveDeliveryWorkReport(String wfNumber, String approverCode, String approveDate, String approveDateTime, String userCode) {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_BAPI, PARAMS_VALUE_MODULE_APPR_INSDATA1);
		//T_IE_04001
		// WF番号
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZWKFNO, wfNumber);
		// WFステータス
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZWFSTA, PARAMS_VALUE_ZWFSTA);
		//承認者
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZSNSHA, approverCode);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZSNYMD, approveDate);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZSNTIM, approveDateTime);
		// 決済者
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZKSSHA, approverCode);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZKSYMD, approveDate);
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04001  + "." + SapApiConsts.PARAMS_ID_ZKSTIM, approveDateTime);

		//※実ユーザコード（ログインユーザの社員コード）
		params.addParam(SapApiConsts.PARAMS_KEY_T_IE_04002  + "." + SapApiConsts.PARAMS_ID_ZJUSNM, userCode);


		return retryPostXML(BASE_URL, null, params.getParams());
	}

	private static Map<String, Object> retryPostXML(String baseUrl, List<NameValuePair> namePairList, Object params) {
		Map<String, Object> parsedList = new HashMap<String, Object>();
		for (int i=0;i < RETRY_POST_COUNT;i++) {
			try {
				Document doc = CommonHttpClient.postXMLWithThrowException(BASE_URL, null, params);
				parsedList = DomParser.parse(doc, SapApiConsts.NODE_NAME_XML);
				parsedList.put("params", params);
				break;
			} catch (Exception e) {
				log.info("post xml count: " +(i+1));
				log.info(e.getMessage());
				if (i+1 < RETRY_POST_COUNT) {
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException interrupted) {
						log.info(interrupted.getMessage());
					}
				} else {
					throw new CoreRuntimeException(e.getMessage());
				}
			}
		}
		return parsedList;
	}

}