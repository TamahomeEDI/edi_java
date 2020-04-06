package jp.co.keepalive.springbootfw.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.w3c.dom.Document;

import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;
import jp.co.keepalive.springbootfw.util.file.FileUtil;


public class CommonHttpClient{

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";


    public static void getFileContent(String uri, List<NameValuePair> headers, List<NameValuePair> params, String filePath, String method, boolean codeCheckFlg) throws Exception {
        CloseableHttpResponse ret;
        if(!method.equals(METHOD_POST)) {
            ret = HttpUtil.get(uri, headers, params, codeCheckFlg);
        } else {
            ret = HttpUtil.post(uri, headers, params, codeCheckFlg);
        }

        InputStream is = null;
        try {
            is = ret.getEntity().getContent();
            FileUtil.fileOutputByStream(ret.getEntity().getContent(), filePath);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * GET
     */
    public static String get(String uri) {
        return get(uri, null, null);
    }

    public static String get(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return analyzeResponse((HttpUtil.get(uri, headers, params)));
    }

    public static String getJson(String uri) {
        return analyzeJsonResponse((HttpUtil.get(uri, null, null)));
    }

    public static String getResultStatus(String uri, List<NameValuePair> params) {
        String json = analyzeJsonResponse((HttpUtil.get(uri, null, params)));
        Map<String, Object> ret = JsonUtils.decode(json);
        return (String) ret.get("resultStatus");
    }

    @SuppressWarnings("unchecked")
	public static ResponseEntity getResponseEntity(String uri, List<NameValuePair> params) {
        String json = analyzeJsonResponse((HttpUtil.get(uri, null, params)));
        Map<String, Object> ret = JsonUtils.decode(json);
        ResponseEntity entity = new ResponseEntity();
        entity.setResultStatus((String) ret.get("resultStatus"));
        entity.setData((Map<String, Object>) ret.get("data"));
        return entity;
    }

    /*
     * POST
     */
    public static CloseableHttpResponse post(String uri, Object params) {
        return HttpUtil.post(uri, null, params);
    }

    public static String post(String uri, List<NameValuePair> headers, Object params) {
        return analyzeResponse((HttpUtil.post(uri, headers, params)));
    }

    public static Document postXML(String uri, List<NameValuePair> headers, Object params) {
        return analyzeResponseDoc((HttpUtil.post(uri, headers, params, HttpUtil.CODE_CHECK_FLG_FALSE)));
    }

    public static Document postXMLWithThrowException(String uri, List<NameValuePair> headers, Object params) throws Exception {
    	Document doc = null;
    	try {
    		doc = analyzeResponseDoc(HttpUtil.post(uri, headers, params, HttpUtil.CODE_CHECK_FLG_FALSE));
    	} catch (Exception e) {
    		throw new Exception(e);
    	}
        return doc;
    }

    /*
     * PUT
     *
     */
    public static String put(String uri) {
        return put(uri, null, null);
    }

    public static String put(String uri, List<NameValuePair> headers, Object params) {
        return analyzeResponse((HttpUtil.put(uri, headers, params)));
    }

    /*
     * DELETE
     */
    public static String delete(String uri) {
        return delete(uri, null, null);
    }

    public static String delete(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return analyzeResponse((HttpUtil.delete(uri, headers, params)));
    }

    /*
     * 型変換
     */
    // String
    private static String analyzeResponse(CloseableHttpResponse response) {
        String xml = HttpUtil.toXmlString(response);
        HttpUtil.close(response);
        return xml;
    }

    // Document
    private static Document analyzeResponseDoc(CloseableHttpResponse response) {
        Document doc = HttpUtil.toDoc(response);
        HttpUtil.close(response);
        return doc;
    }

    private static String analyzeJsonResponse(CloseableHttpResponse response) {
        String json = HttpUtil.toJson(response);
        HttpUtil.close(response);
        return json;
    }
}
