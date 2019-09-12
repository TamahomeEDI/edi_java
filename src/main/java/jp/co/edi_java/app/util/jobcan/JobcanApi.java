package jp.co.edi_java.app.util.jobcan;

import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.edi_java.app.dto.JobcanDto;
import jp.co.keepalive.springbootfw.util.dxo.JsonUtils;
import jp.co.keepalive.springbootfw.util.http.HttpRequestHeaders;
import jp.co.keepalive.springbootfw.util.http.HttpUtil;

@Component
public class JobcanApi {

	private static String BASE_URL;

	private JobcanApi(@Value("${jobcanapi.base.url}") String url) {
		BASE_URL = url;
	}

	//申請
	public static Map<String, Object> postApply(JobcanDto jobcanPrams) {
		HttpRequestHeaders headers = createCommonHeader();
		headers.addParam("Content-Type", "application/json; charset=UTF-8");
		String json = JsonUtils.encode(jobcanPrams);
		CloseableHttpResponse response = HttpUtil.post(BASE_URL, headers.getParams(), json);
		String ret = HttpUtil.toJson(response);
		return JsonUtils.decode(ret);
	}

	private static HttpRequestHeaders createCommonHeader() {
			HttpRequestHeaders headers = new HttpRequestHeaders();
			headers.addParam("accept", "application/json");
			return headers;
	}

}