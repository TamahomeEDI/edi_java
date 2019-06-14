package jp.co.edi_java.app.controller;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.http.CommonHttpClient;
import jp.co.keepalive.springbootfw.util.http.HttpRequestParams;
import jp.co.edi_java.app.SpringBootFwApplication;
import jp.co.edi_java.app.form.TestForm;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
@SpringBootTest(classes=SpringBootFwApplication.class)
public class TestControllerTest {

	private String uri;

	TestForm form;

	@Before
	public void setUp(){
		uri = "http://localhost:8080/test/get/";
	}

	@Test
	public void select() {
		uri = "http://localhost:8080/test/select/";
		HttpRequestParams params = new HttpRequestParams();
		params.addParam("testSeq", 1);
		ResponseEntity entity = CommonHttpClient.getResponseEntity(uri, params.getParams());
		assertEquals(ResponseCode.NORMAL_CODE_200, entity.resultStatus);
		assertEquals("test3", ((Map<String,String>)entity.data.get("ret")).get("name"));
	}

	@Test
	public void status200() {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam("testSeq", 1);
		params.addParam("id", "aaa");
		params.addParam("email", "aaa@keep-alive.co.jp");
		params.addParam("emailConfirm", "aaa@keep-alive.co.jp");
		String resultStatus = CommonHttpClient.getResultStatus(uri, params.getParams());
		assertEquals(ResponseCode.NORMAL_CODE_200, resultStatus);
	}

	@Test
	public void status400_1() {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam("id", "123456789012345678901");
		String resultStatus = CommonHttpClient.getResultStatus(uri, params.getParams());
		assertEquals(ResponseCode.ERROR_CODE_400, resultStatus);
	}

	@Test
	public void status400_2() {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam("email", "a");
		params.addParam("emailConfirm", "a");
		String resultStatus = CommonHttpClient.getResultStatus(uri, params.getParams());
		assertEquals(ResponseCode.ERROR_CODE_400, resultStatus);
	}

	@Test
	public void status400_3() {
		HttpRequestParams params = new HttpRequestParams();
		params.addParam("email", "aaa@keep-alive.co.jp");
		params.addParam("emailConfirm", "bbb@keep-alive.co.jp");
		String resultStatus = CommonHttpClient.getResultStatus(uri, params.getParams());
		assertEquals(ResponseCode.ERROR_CODE_400, resultStatus);
	}


}
