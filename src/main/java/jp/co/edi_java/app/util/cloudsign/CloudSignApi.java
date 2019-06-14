package jp.co.edi_java.app.util.cloudsign;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;
import jp.co.keepalive.springbootfw.util.http.HttpRequestHeaders;
import jp.co.keepalive.springbootfw.util.http.HttpRequestParams;
import jp.co.keepalive.springbootfw.util.http.HttpsUtil;

@Component
public class CloudSignApi {

	private static String ACCESS_TOKEN;
	private static long TOKEN_LIMIT = 0;

	private static String BASE_URL;
	private static String CLIENT_ID;
	private static String DOWNLOAD_PATH;

	private static String URL_TOKEN = "token";
	private static String URL_DOCUMENTS = "documents";
	private static String URL_FILES = "files";
	private static String URL_PARTICIPANTS = "participants";

	public static String PREFIX_TITLE_CONFIRMATION = "発注書兼請書_";
	public static String PREFIX_TITLE_CANCEL = "発注取消合意書_";
	public static String PREFIX_FILE_CONFIRMATION = "O_";
	public static String PREFIX_FILE_CANCEL = "C_";

	public static String FORM_TYPE_ORDER = "1";
	public static String FORM_TYPE_CANCEL = "2";

	public static String STATUS_UNCONFIRMED = "1";
	public static String STATUS_AGREE = "2";
	public static String STATUS_DISMISSAL = "3";

	private CloudSignApi(@Value("${cloudsign.base.url}") String baseUrl,
						@Value("${cloudsign.client.id}") String id,
						@Value("${cloudsign.download.path}") String downloadPath) {
		BASE_URL = baseUrl;
		CLIENT_ID = id;
		DOWNLOAD_PATH = downloadPath;
	}

	private static String getToken() {
		if(TOKEN_LIMIT <= System.currentTimeMillis()) {
			HttpRequestParams params = new HttpRequestParams();
			params.addParam("client_id", CLIENT_ID);
			String ret = HttpsUtil.get(BASE_URL + URL_TOKEN, null, params.getParams());
			Map<String, Object> map = JsonUtils.decode(ret);
			ACCESS_TOKEN = (String) map.get("access_token");
			TOKEN_LIMIT = System.currentTimeMillis() + (long)((Integer) map.get("expires_in")) * 1000;
		}
		System.out.println(ACCESS_TOKEN);
		return ACCESS_TOKEN;
	}

	public static Map<String, Object> postDocuments(String title, String note, String message, String templateId, boolean canTransfer) {
		HttpRequestHeaders headers = createCommonHeader();
		headers.addParam("Content-Type", "application/x-www-form-urlencoded");

		HttpRequestParams params = new HttpRequestParams();
		params.addParam("title", title);
		params.addParam("note", note);
		params.addParam("message", message);
		if(templateId != null) params.addParam("template_id", templateId);
		params.addParam("can_transfer", Boolean.toString(canTransfer));

		String ret = HttpsUtil.postParams(BASE_URL + URL_DOCUMENTS, headers.getParams(), params.getParams());
		return JsonUtils.decode(ret);
	}

	public static Map<String, Object> getDocumentId(String documentId) {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.get(BASE_URL + URL_DOCUMENTS + "/" + documentId, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	public static Map<String, Object> postDocumentId(String documentId) {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.postParams(BASE_URL + URL_DOCUMENTS + "/" + documentId, headers.getParams(), null);
		return JsonUtils.decode(ret);
	}

	public static Map<String, Object> postParticipants(String documentId, String email, String name) {
		HttpRequestHeaders headers = createCommonHeader();

		HttpRequestParams params = new HttpRequestParams();
		params.addParam("email", email);
		params.addParam("name", name);

		String ret = HttpsUtil.postParams(BASE_URL + URL_DOCUMENTS + "/" + documentId + "/" + URL_PARTICIPANTS, headers.getParams(), params.getParams());
		return JsonUtils.decode(ret);
	}

	public static Map<String, Object> postFile(String documentId, String filePath, String fileName) {
		HttpRequestHeaders headers = createCommonHeader();
		String ret = HttpsUtil.postFile(BASE_URL + URL_DOCUMENTS + "/" + documentId + "/" + URL_FILES, headers.getParams(), filePath, fileName, HttpsUtil.MIMETYPE_PDF);
		return JsonUtils.decode(ret);
	}

	public static String getFile(String documentId, String fileId, String fileName) {
		HttpRequestHeaders headers = createCommonHeader();
		headers.addParam("accept", "application/pdf");
		System.out.println(DOWNLOAD_PATH + fileName);
		HttpsUtil.getFile(BASE_URL + URL_DOCUMENTS + "/" + documentId + "/" + URL_FILES + "/" + fileId, headers.getParams(), DOWNLOAD_PATH + fileName);
		return DOWNLOAD_PATH + fileName;
	}

	private static HttpRequestHeaders createCommonHeader() {
		HttpRequestHeaders headers = new HttpRequestHeaders();
		String token = getToken();
		headers.addParam("Authorization", "Bearer " + token);
		headers.addParam("accept", "application/json");
		return headers;
	}

}
