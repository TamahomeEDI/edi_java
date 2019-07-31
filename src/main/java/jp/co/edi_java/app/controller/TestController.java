package jp.co.edi_java.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.doma.jdbc.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.config.MukoukaDbConfig;
import jp.co.edi_java.app.dao.jtm.VOrderSyainDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.form.TestForm;
import jp.co.edi_java.app.service.FileService;
import jp.co.edi_java.app.service.MailService;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@RestController
@Scope("request")
@RequestMapping("test")
@Slf4j //log.xxx を使用するためのアノテーション
public class TestController extends BaseController {

	//@Slf4j が自動生成するコード
	//private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestController.class);

//	@Autowired
//	public TestService testService;

	private String id = "d8887560-dd95-49de-8cd6-15314b82b2ca";

	@Autowired
    public MailService mailService;

	@Autowired
    public FileService fileService;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public VOrderSyainDao vOrderSyainDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@RequestMapping("/fileTest")
	public ResponseEntity chkTest(@Validated TestForm form) throws FileNotFoundException {
//		Map<String, Object> ret = fileService.uploadTest(", toshoCode, fileCode, fileNo, registSyainCode, filePath, "test.xlsx", "xlsx")
//		String fileId = ret.get("file_id").toString();
		File file = new File("/home/web/edi_php/tmp/file/excel_original.xlsx");
		FileInputStream fis = new FileInputStream(file);
		super.setResponseData("fileのサイズ", file.length());
//		super.setResponseData("fileのサイズ", fis.);
		return super.response();
	}

	@RequestMapping("/db2")
	public ResponseEntity db2() {
		MukoukaDbConfig db2 = new MukoukaDbConfig();
		List<Map<String, Object>> result = transactionTemplate.execute( status -> {
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			List<Map<String, Object>> list = new ArrayList<>();
			try {
				conn = db2.getDataSource().getConnection();
				st = conn.createStatement();
				rs =  st.executeQuery("select * from V_ORDER_SYAIN");

				while (rs.next()) {
					Map<String, Object> map= new HashMap<>();
					map.put("syainCode", rs.getObject("syainCode"));
					list.add(map);
				}

			} catch (SQLException e) {
				throw new CoreRuntimeException(e);
			} finally {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					rs = null;
					st = null;
					conn = null;
					throw new CoreRuntimeException(e);
				}
			}
			return list;
		});

		//ここにInsert処理

		super.setResponseData("ret",result);
		return super.response();
	}


	/**
	 * クラウドサイン
	 */
	//ドキュメント送信
	@RequestMapping("/postDocuments")
	public ResponseEntity postDocuments() {
		Map<String, Object> ret = CloudSignApi.postDocuments("タイトル２", "メモ２", "メッセージ２", null, false);
		super.setResponseData("ret", ret);
		return super.response();
	}

	//ドキュメントID取得
	@RequestMapping("/getDocumentId")
	@SuppressWarnings("unchecked")
	public ResponseEntity getDocumentId(@Validated TestForm form) {
		Map<String, Object> ret = CloudSignApi.getDocumentId(form.getDocumentId());
		List<Map<String, Object>> files = (List<Map<String, Object>>)ret.get("files");
		String fileId = files.get(0).get("id").toString();

		super.setResponseData("ret", ret);
		super.setResponseData("files", files);
		super.setResponseData("fileId", fileId);
		return super.response();
	}

	//ドキュメントID送信
	@RequestMapping("/postDocumentId")
	public ResponseEntity postDocumentId() {
		Map<String, Object> ret = CloudSignApi.postDocumentId(id);
		super.setResponseData("ret", ret);
		return super.response();
	}

	//アドレス送信
	@RequestMapping("/postParticipants")
	public ResponseEntity postParticipants() {
		Map<String, Object> ret = CloudSignApi.postParticipants(id, "shinji-yamaguchi@tamahome.jp", "やまぐち");
		super.setResponseData("ret", ret);
		return super.response();
	}

	//ファイル送信
	@RequestMapping("/postFile")
	public ResponseEntity postFile() {
		Map<String, Object> ret = CloudSignApi.postFile("caf38984-59d2-46dc-9772-496aab41a403", "/tmp/test.pdf", "Book1.pdf");
//		Map<String, Object> ret = CloudSignApi.postFile(id, "C:\\Users\\suzuk\\Desktop\\tama\\Book1.pdf", "Book1.pdf");
		super.setResponseData("ret", ret);
		return super.response();
	}

	//ファイル取得
	@RequestMapping("/getFile")
	public ResponseEntity getFile() {
		CloudSignApi.getFile(id, "b3ab87c5-b919-4ed3-bfac-6418d26d1919", "Book1.pdf");
		super.setResponseData("ret", "OK");
		return super.response();
	}
	/**
	 * クラウドサイン
	 */


	@RequestMapping("/")
	public ResponseEntity index() {
		super.setResponseData("ret", "index");
		return super.response();
	}

	@RequestMapping("/exception")
	public ResponseEntity exception() {
		log.debug("[TestController.exception] START");
		throw new RuntimeException("test");
	}

	@RequestMapping("/select")
	public ResponseEntity select(@Validated TestForm form) {
//		TestEntity ret = testService.select(form.testSeq);
//		super.setResponseData("ret", ret);
		return super.response();
	}

	@RequestMapping("/selectAll")
	public ResponseEntity selectAll() {
//		List<TestEntity> ret = testService.selectAll();
//		super.setResponseData("ret", ret);
		return super.response();
	}

//	@RequestMapping("/insert")
//	public ResponseEntity insert(@Validated TestForm form) {
//		testService.insert(form);
//		super.setResponseData("ret", "OK");
//		return super.response();
//	}
//
//	@RequestMapping("/update")
//	public ResponseEntity update(@Validated TestForm form) {
//		testService.update(form);
//		super.setResponseData("ret", "OK");
//		return super.response();
//	}
//
//	@RequestMapping("/delete")
//	public ResponseEntity delete(@Validated TestForm form) {
//		testService.delete(form);
//		super.setResponseData("ret", "OK");
//		return super.response();
//	}
//
//	@RequestMapping("/get")
//	public ResponseEntity get(@Validated TestForm form) {
//		super.setResponseData("input", form);
//		return super.response();
//	}
//
//	@RequestMapping("/error")
//	public ResponseEntity error(@Validated TestForm form) {
//		testService.update(form);
//		if(!form.id.equals("100")) {
//			String str = null;
//			str.equals("");
//		}
//		return super.response();
//	}

	@RequestMapping("/autocommit")
	public ResponseEntity autocommit() {
		Config config1 = mSyainDao.getInjectedConfig();
		Config config2 = vOrderSyainDao.getInjectedConfig();
		try {
			super.setResponseData("mSyain",config1.getDataSource().getConnection().getAutoCommit());
			super.setResponseData("vOrderSyain",config2.getDataSource().getConnection().getAutoCommit());
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return super.response();
	}

	@RequestMapping("/date")
	public ResponseEntity date() {
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		super.setResponseData("timestamp",timestamp);



		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DATE, 1);


		super.setResponseData("年",cal.get(Calendar.YEAR));
		super.setResponseData("月",cal.get(Calendar.MONTH)+1);
//		super.setResponseData("今月初",cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//		super.setResponseData("今月末",cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		super.setResponseData("今月",sdf.format(cal.getTime()));

		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DATE, 1);

		super.setResponseData("来月",sdf.format(cal.getTime()));

		return super.response();
	}


}
