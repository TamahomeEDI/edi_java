package jp.co.edi_java.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.service.CloudSignService;
import jp.co.edi_java.app.service.DeliveryService;
import jp.co.edi_java.app.service.JtmService;
import jp.co.edi_java.app.service.MailService;
import jp.co.edi_java.app.service.OrderService;
import jp.co.edi_java.app.service.WorkReportService;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.edi_java.app.util.mail.MailExUtils;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.logging.SystemLoggingUtil;

@RestController
@Scope("request")
@RequestMapping("batch")
public class BatchController extends BaseController {

	@Autowired
	public JtmService jtmService;

	@Autowired
	public CloudSignService cloudSignService;

	@Autowired
	public DeliveryService deliveryService;

	@Autowired
	public WorkReportService workReportService;

	@Autowired
	public OrderService orderService;

	private String adminEmail = "t-iida@tamahome.jp, to-suzuki@tamahome.jp, shinji-yamaguchi@tamahome.jp";
	//private String adminEmail = "shinji-yamaguchi@tamahome.jp";

	/** 支店マスタ取得 */
	@RequestMapping("/getEigyousyoAll")
	public ResponseEntity getEigyousyoAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
//			jtmService.backupEigyousyo();
			jtmService.truncateEigyousyo();;

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertEigyousyoAll();
//			Map<String, Object> countMap = jtmService.insertEigyousyoAll2();
			//洗い替え
			Map<String, Object> countMap = jtmService.insertEigyousyoAll3();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 地区本部マスタ取得 */
	@RequestMapping("/getEigyousyoGroupAll")
	public ResponseEntity getEigyousyoGroupAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
//			jtmService.backupEigyousyoGroup();
			jtmService.truncateEigyousyoGroup();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertEigyousyoGroupAll();
//			Map<String, Object> countMap = jtmService.insertEigyousyoGroupAll2();
			//洗い替え
			Map<String, Object> countMap = jtmService.insertEigyousyoGroupAll3();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 業者マスタ取得 */
	@RequestMapping("/getGyousyaAll")
	public ResponseEntity getGyousyaAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupGyousya();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertGyousyaAll();
			Map<String, Object> countMap = jtmService.insertGyousyaAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 業者支店マスタ取得 */
	@RequestMapping("/getGyousyaEigyousyoAll")
	public ResponseEntity getGyousyaEigyousyoAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupGyousyaEigyousyo();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertGyousyaEigyousyoAll(0);
			Map<String, Object> countMap = jtmService.insertGyousyaEigyousyoAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 業者細目マスタ取得 */
	@RequestMapping("/getGyousyaSaimokuAll")
	public ResponseEntity getGyousyaSaimokuAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupGyousyaSaimoku();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertGyousyaSaimokuAll();
			Map<String, Object> countMap = jtmService.insertGyousyaSaimokuAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 工事マスタ取得 */
	@RequestMapping("/getKoujiAll")
	public ResponseEntity getKoujiAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupKouji();

			//Insert処理
			Map<String, Object> countMap = jtmService.insertKoujiAll();	//指定
//			Map<String, Object> countMap = jtmService.insertKoujiAll2();	//全件

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 細目工種マスタ取得 */
	@RequestMapping("/getSaimokuKousyuAll")
	public ResponseEntity getSaimokuKousyuAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupSaimokuKousyu();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertSaimokuKousyuAll();
			Map<String, Object> countMap = jtmService.insertSaimokuKousyuAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 社員マスタ取得 */
	@RequestMapping("/getSyainAll")
	public ResponseEntity getSyainAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupSyain();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertSyainAll();
			Map<String, Object> countMap = jtmService.insertSyainAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 社員支店マスタ取得 */
	@RequestMapping("/getSyainEigyousyoAll")
	public ResponseEntity getSyainEigyousyoAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupSyainEigyousyo();
			jtmService.truncateSyainEigyousyo();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertSyainEigyousyoAll();
//			Map<String, Object> countMap = jtmService.insertSyainEigyousyoAll2();
			//洗い替え
			Map<String, Object> countMap = jtmService.insertSyainEigyousyoAll3();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 家歴マスタ取得 */
	@RequestMapping("/getKarekiAll")
	public ResponseEntity getKarekiAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			jtmService.backupKareki();

			//Insert処理
//			Map<String, Object> countMap = jtmService.insertKarekiAll();
			Map<String, Object> countMap = jtmService.insertKarekiAll2();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** クラウドサイン情報確認 */
	@RequestMapping("/chkCloudSign")
	public ResponseEntity chkCloudSign() {
		try {
			long start = System.currentTimeMillis();

			//発注請書のID一覧取得
			List<TCloudSignEntity> confirmationFileList = cloudSignService.selectFileIdList(CloudSignApi.FORM_TYPE_ORDER);
			//リストを確認
			Map<String, Object> confrimationCountMap = cloudSignService.chkFileList(confirmationFileList, CloudSignApi.FORM_TYPE_ORDER);

			//発注取消合意書のID一覧取得
			List<TCloudSignEntity> cancelFileList = cloudSignService.selectFileIdList(CloudSignApi.FORM_TYPE_CANCEL);
			//リストを確認
			Map<String, Object> cancelCountMap = cloudSignService.chkFileList(cancelFileList, CloudSignApi.FORM_TYPE_CANCEL);

			long end = System.currentTimeMillis();
			super.setResponseData("confrimationCountMap",confrimationCountMap);
			super.setResponseData("cancelCountMap",cancelCountMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 * クラウドサインリマインド機能
	 * ヒアリング対応 ⑤発注メールの有効期限切れ対応
	 * 7日経っても承認していない業者へバッチで一括送信
	 */
	@RequestMapping("/remindCloudSign")
	public ResponseEntity remindCloudSign() {
		try {
			long start = System.currentTimeMillis();

			//発注請書のID一覧取得
			List<TCloudSignEntity> remindList = cloudSignService.selectRemindList();
			//メールを送信
			int count = cloudSignService.remindList(remindList);

			long end = System.currentTimeMillis();
			super.setResponseData("count",count);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 * 納品出来高受入リマインド機能
	 * 毎日工務担当者へ処理漏れがないかリマインド
	 *
	 */
	@RequestMapping("/remindDeliveryAcceptance")
	public ResponseEntity remindDeliveryAcceptance() {
		try {
			long start = System.currentTimeMillis();

			//納品書のID一覧取得
			List<TDeliveryEntity> remindDList = deliveryService.selectRemindList();
			//出来高書のID一覧取得
			List<TWorkReportEntity> remindWList = workReportService.selectRemindList();
			//メールを送信
			int countD = remindDList.size();
			deliveryService.remindList(remindDList);
			int countW = remindWList.size();
			workReportService.remindList(remindWList);
			long end = System.currentTimeMillis();
			super.setResponseData("countDelivery",countD);
			super.setResponseData("countWorkReport",countW);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}


	/* 単発実行のバッチ処理 ※ データメンテナンス等 */
	/**
	 *
	 * 発注日のSAP連携
	 *
	 */
	@RequestMapping("/refreshOrderDate")
	public ResponseEntity refreshOrderDate() {
		try {
			long start = System.currentTimeMillis();

			Map<String, Integer> cntMap = orderService.refreshOrderDate();

			long end = System.currentTimeMillis();
			super.setResponseData("countSAPUpdateOK",cntMap.get("sapOK"));
			super.setResponseData("countSAPUpdateNG",cntMap.get("sapNG"));
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}









}
