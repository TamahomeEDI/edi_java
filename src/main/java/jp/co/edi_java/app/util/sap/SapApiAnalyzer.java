package jp.co.edi_java.app.util.sap;

import java.util.List;
import java.util.Map;

public class SapApiAnalyzer {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> analyzeResult(Map<String, Object> data, String tagName) {
        return (Map<String, Object>)data.get(tagName);
    }

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> analyzeResultList(Map<String, Object> data, String tagName) {
        return (List<Map<String, Object>>)data.get(tagName);
    }

	@SuppressWarnings("unchecked")
	public static Map<String, Object> analyzeResultInfo(Map<String, Object> data) {
        Map<String, Object> resultInfo = (Map<String, Object>)data.get(SapApiConsts.NODE_NAME_T_ERROR);
        return resultInfo;
    }

	/**
	 * SAP処理結果確認
	 * @param errContent
	 * @return エラーあり：true なし：false
	 */
    public static boolean chkResultInfo(Map<String, Object> resultInfo) {
    	String ret = resultInfo.get(SapApiConsts.PARAMS_ID_ZRESULT).toString();
    	if(ret.equals(SapApiConsts.ZRESULT_VALUE_SUCCESS)) {
    		return false;
    	}
    	return true;
    }


}
