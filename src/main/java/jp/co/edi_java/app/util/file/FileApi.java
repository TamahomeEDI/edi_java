package jp.co.edi_java.app.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;
import jp.co.keepalive.springbootfw.util.http.CommonHttpClient;
import jp.co.keepalive.springbootfw.util.http.HttpRequestHeaders;
import jp.co.keepalive.springbootfw.util.http.HttpRequestParams;
import jp.co.keepalive.springbootfw.util.http.HttpUtil;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

@Component
public class FileApi {

	private static String BASE_URL;
	private static String INFO_URL = "info";

	private static String OUTPUT_FILE_DIR = "/home/web/edi_php/tmp/downloadFile/";

	private static String PARAMS_KEY_FILE_NAME = "file_name";
	private static String PARAMS_KEY_SYSTEM_USER_ID = "system_user_id";
	private static String PARAMS_KEY_SYSTEM_ID = "system_id";
	private static String PARAMS_VALUE_SYSTEM_ID = "4";	//電子発注

	public static String TOSHO_CODE_EDI = "09";
	public static String FILE_CODE_FORM = "03";			//帳票
	public static String FILE_NO_CONFRIMATION = "02";	//請書
	public static String FILE_NO_CANCEL = "05";			//取消合意書
	public static String FILE_NO_DELIVERY = "03";		//納品書
	public static String FILE_NO_WORK_REPORT = "04";	//出来高報告書

	private FileApi(@Value("${fileapi.base.url}") String url) {
		BASE_URL = url;
	}

	//アップロード
	public static Map<String, Object> postFile(String koujiCode, String toshoCode, String fileCode, String fileNo, String registSyainCode, String filePath, String fileName, String fileType) {
		HttpRequestHeaders headers = createCommonHeader();
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_FILE_NAME, fileName);
		if(registSyainCode != null) {
			params.addParam(PARAMS_KEY_SYSTEM_USER_ID, registSyainCode);
		}
		params.addParam(PARAMS_KEY_SYSTEM_ID, PARAMS_VALUE_SYSTEM_ID);
		String url = BASE_URL + koujiCode + "/" + toshoCode + "/" + fileCode + "/" + fileNo;
		String ret = HttpUtil.postFile(url, headers.getParams(), params.getParams(), filePath, fileName, fileType);
		return JsonUtils.decode(ret);
	}

	//アップロード
	public static Map<String, Object> postFileTest(String koujiCode, String toshoCode, String fileCode, String fileNo, String registSyainCode, String filePath, String fileName, String fileType) {
		HttpRequestHeaders headers = createCommonHeader();
		HttpRequestParams params = new HttpRequestParams();
		params.addParam(PARAMS_KEY_FILE_NAME, fileName);
		if(registSyainCode != null) {
			params.addParam(PARAMS_KEY_SYSTEM_USER_ID, registSyainCode);
		}
		params.addParam(PARAMS_KEY_SYSTEM_ID, PARAMS_VALUE_SYSTEM_ID);
		String url = BASE_URL + koujiCode + "/" + toshoCode + "/" + fileCode + "/" + fileNo;
		String ret = HttpUtil.postFile(url, headers.getParams(), params.getParams(), filePath, fileName, fileType);
		return JsonUtils.decode(ret);
	}

	//ダウンロード
	public static String getFile(String koujiCode, String toshoCode, String fileCode, String fileNo, String fileId, String fileType, String fileName, String folderPath) {
		createDirectories(folderPath);
		return getFileAux(koujiCode, toshoCode, fileCode, fileNo, fileId, fileType, fileName, folderPath);
	}

	//ダウンロード
	public static String getFile(String koujiCode, String toshoCode, String fileCode, String fileNo, String fileId, String fileType, String fileName) {
		return getFileAux(koujiCode, toshoCode, fileCode, fileNo, fileId, fileType, fileName, OUTPUT_FILE_DIR);
	}

	//ダウンロード
	public static String getFileAux(String koujiCode, String toshoCode, String fileCode, String fileNo, String fileId, String fileType, String fileName, String folderPath) {
		HttpRequestHeaders headers = createCommonHeader();
		String outputFilePath = "";
		if (Objects.isNull(folderPath)) {
			folderPath = OUTPUT_FILE_DIR;
		}
		if (!Objects.equals(folderPath.substring(folderPath.length()-1),"/")) {
			folderPath = folderPath + "/";
		}
		if (!StringUtils.isNullString(fileName)) {
			outputFilePath = folderPath + fileName;
		} else {
			outputFilePath = folderPath + fileId + "." + fileType;
		}
		String url = BASE_URL + koujiCode + "/" + toshoCode + "/" + fileCode + "/" + fileNo + "/" + fileId;
		try {
			CommonHttpClient.getFileContent(url, headers.getParams(), null, outputFilePath, CommonHttpClient.METHOD_GET, true);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new CoreRuntimeException(e.getMessage());
		}
		return outputFilePath;
	}

	//詳細取得
	public static Map<String, Object> getFileInfo(String fileId) {
		HttpRequestHeaders headers = createCommonHeader();
		String url = BASE_URL + fileId + "/" + INFO_URL;
		CloseableHttpResponse response = HttpUtil.get(url, headers.getParams(), null);
		String ret = HttpUtil.toJson(response);
		return JsonUtils.decode(ret);
	}

	//一覧取得
	public static Map<String, Object> getFileList(String koujiCode, String toshoCode, String fileCode, String fileNo) {
		HttpRequestHeaders headers = createCommonHeader();
		String url = BASE_URL + koujiCode + "/" + toshoCode + "/" + fileCode;
		if(!StringUtils.isNullString(fileNo)) {
			url = url + "/" + fileNo;
		}
		CloseableHttpResponse response = HttpUtil.get(url, headers.getParams(), null);
		String ret = HttpUtil.toJson(response);
		return JsonUtils.decode(ret);
	}

	//削除
	public static Map<String, Object> delete(String koujiCode, String toshoCode, String fileCode, String fileNo, String fileId) {
		HttpRequestHeaders headers = createCommonHeader();
		String url = BASE_URL + koujiCode + "/" + toshoCode + "/" + fileCode + "/" + fileNo + "/" + fileId;
		CloseableHttpResponse response = HttpUtil.delete(url, headers.getParams(), null);
		String ret = HttpUtil.toJson(response);
		return JsonUtils.decode(ret);
	}

	/** ディレクトリの作成 */
	public static void createDirectories(String folderPath) {
		if (Objects.isNull(folderPath)) {
			return;
		}
		try {
			Path path = Paths.get(folderPath);
			if (! Files.exists(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException e) {
			throw new CoreRuntimeException(e.getMessage());
		}
	}

	private static HttpRequestHeaders createCommonHeader() {
			HttpRequestHeaders headers = new HttpRequestHeaders();
			headers.addParam("accept", "application/json");
			return headers;
	}

}