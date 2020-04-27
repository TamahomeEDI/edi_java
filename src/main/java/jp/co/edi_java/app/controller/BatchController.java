package jp.co.edi_java.app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dao.MConstantsDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.entity.MConstantsEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.service.BillingCheckListService;
import jp.co.edi_java.app.service.CloudSignService;
import jp.co.edi_java.app.service.DeliveryService;
import jp.co.edi_java.app.service.GoogleDriveService;
import jp.co.edi_java.app.service.InspectionReceiptService;
import jp.co.edi_java.app.service.JtmService;
import jp.co.edi_java.app.service.MailService;
import jp.co.edi_java.app.service.OrderService;
import jp.co.edi_java.app.service.PaymentDetailService;
import jp.co.edi_java.app.service.WorkReportService;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.edi_java.app.util.mail.MailExUtils;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.logging.SystemLoggingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Scope("request")
@RequestMapping("batch")
public class BatchController extends BaseController {

	@Autowired
	public JtmService jtmService;

	@Autowired
	public CloudSignService cloudSignService;

	@Autowired
	public GoogleDriveService googleDriveService;

	@Autowired
	public InspectionReceiptService inspectionReceiptService;

	@Autowired
	public PaymentDetailService paymentDetailService;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
	public DeliveryService deliveryService;

	@Autowired
	public WorkReportService workReportService;

	@Autowired
	public OrderService orderService;

	@Autowired
	public MailService mailService;

	@Autowired
	public BillingCheckListService billingCheckListService;

	//定数マスタ
	@Autowired
	public MConstantsDao mConstantsDao;

	private String adminEmail = "t-iida@tamahome.jp, to-suzuki@tamahome.jp, shinji-yamaguchi@tamahome.jp";
	//private String adminEmail = "shinji-yamaguchi@tamahome.jp";

	/** 支店マスタ取得 */
	@RequestMapping("/getEigyousyoAll")
	public ResponseEntity getEigyousyoAll() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
//			jtmService.backupEigyousyo();
			jtmService.truncateEigyousyo();

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
			Map<String, Object> countMap = jtmService.insertKoujiAll(); //指定
//			Map<String, Object> countMap = jtmService.insertKoujiAll2(); //全件

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

	/** 発注情報取得 */
	@RequestMapping("/getOrderAll")
	public ResponseEntity getOrderAll() {
		try {
			long start = System.currentTimeMillis();

			Map<String, Object> countMap = jtmService.upsertOrderAll();

			long end = System.currentTimeMillis();
			super.setResponseData("ret",countMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/** 発注情報取得 */
	@RequestMapping("/getOrderBackUp")
	public ResponseEntity getOrderBackUp() {
		try {
			long start = System.currentTimeMillis();
			//バックアップ処理
			Map<String, Object> countMap = jtmService.backupOrder();

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
	 * 納品日翌日と月末までの３日間は工務担当者へ処理漏れがないかリマインド
	 *
	 */
	@RequestMapping("/remindDeliveryAcceptance")
	public ResponseEntity remindDeliveryAcceptance() {
		try {
			long start = System.currentTimeMillis();

			//納品書のID一覧取得
			List<TDeliveryEntity> remindDList = deliveryService.selectRemindList(false);
			//出来高書のID一覧取得
			List<TWorkReportEntity> remindWList = workReportService.selectRemindList(false);
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

	/**
	 * 納品出来高受入リマインド機能(一覧)
	 *
	 */
	@RequestMapping("/remindDeliveryAcceptanceList")
	public ResponseEntity remindDeliveryAcceptanceList() {
		try {
			long start = System.currentTimeMillis();

			//納品書のID一覧取得
			List<TDeliveryEntity> remindDList = deliveryService.selectRemindList(true);
			//出来高書のID一覧取得
			List<TWorkReportEntity> remindWList = workReportService.selectRemindList(true);
			//メールを送信
			int countD = remindDList.size();
			int countW = remindWList.size();
			// 納品書、出来高報告書の未受入一覧をメール送信
			mailService.remindDeliveryAcceptanceList(remindDList, remindWList);

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

	/**
	 *
	 * GoogleDrive report archive all
	 *
	 */
	@RequestMapping("/archiveReportFiles")
	public ResponseEntity archiveReportFiles() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 格納フォルダの作成
			googleDriveService.initializeGoogleDrive(yearMonth);
			// 請書のアーカイブ
			googleDriveService.createArchiveReport("order", yearMonth);
			// 納品書のアーカイブ
			googleDriveService.createArchiveReport("delivery", yearMonth);
			// 出来高報告書のアーカイブ
			googleDriveService.createArchiveReport("workReport", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive initialize only
	 *
	 */
	@RequestMapping("/initializeGoogleDrive")
	public ResponseEntity initializeGoogleDrive() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 格納フォルダの作成
			googleDriveService.initializeGoogleDrive(yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive order only
	 *
	 */
	@RequestMapping("/createArchiveOrder")
	public ResponseEntity createArchiveOrder() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 請書のアーカイブ
			googleDriveService.createArchiveReport("order", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive delivery only
	 *
	 */
	@RequestMapping("/createArchiveDelivery")
	public ResponseEntity createArchiveDelivery() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 請書のアーカイブ
			googleDriveService.createArchiveReport("delivery", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive work report only
	 *
	 */
	@RequestMapping("/createArchiveWorkReport")
	public ResponseEntity createArchiveWorkReport() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 請書のアーカイブ
			googleDriveService.createArchiveReport("workReport", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * Local server create billing check list
	 *
	 */
	@RequestMapping("/createBillingCheckList")
	public ResponseEntity createBillingCheckList() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			billingCheckListService.createCheckListBatch(yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive 受注一覧、受注明細一覧
	 *
	 */
	@RequestMapping("/createArchiveBillingCheckList")
	public ResponseEntity createArchiveBillingCheckList() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 受注一覧、受注明細一覧のアーカイブ
			googleDriveService.createArchiveLocalDocument("billingCheckList", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}
	/**
	 *
	 * 検収明細書のファイルパスリスト作成
	 *
	 */
	@RequestMapping("/getInspectionReceiptFiles")
	public ResponseEntity getInspectionReceiptFiles() {
		try {
			long start = System.currentTimeMillis();

			// 検収明細書のファイルパスリスト作成
			inspectionReceiptService.getInspectionReceiptFiles();

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}
	/**
	 *
	 * 支払明細書のファイルパスリスト作成
	 *
	 */
	@RequestMapping("/getPaymentDetailFiles")
	public ResponseEntity getPaymentDetailFiles() {
		try {
			long start = System.currentTimeMillis();

			// 支払明細書のファイルパスリスト作成
			paymentDetailService.getPaymentDetailFiles();

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}
	/**
	 *
	 * 検収明細書のファイルパスリスト作成
	 *
	 */
	@RequestMapping("/createInspectionReceiptList")
	public ResponseEntity createInspectionReceiptList() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 検収明細書のファイルパスリスト作成
			inspectionReceiptService.createInspectionReceiptList(yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * 支払明細書のファイルパスリスト作成
	 *
	 */
	@RequestMapping("/createPaymentDetailList")
	public ResponseEntity createPaymentDetailList() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 支払明細書のファイルパスリスト作成
			paymentDetailService.createPaymentDetailList(yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive 検収明細
	 *
	 */
	@RequestMapping("/createArchiveInspectionReceipt")
	public ResponseEntity createArchiveInspectionReceipt() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 検収明細のアーカイブ
			googleDriveService.createArchiveLocalDocument("inspectionReceipt", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/**
	 *
	 * GoogleDrive archive 支払明細
	 *
	 */
	@RequestMapping("/createArchivePaymentDetail")
	public ResponseEntity createArchivePaymentDetail() {
		try {
			long start = System.currentTimeMillis();
			// 定数マスタで年月指定時は指定した年月を処理する
			String yearMonth = null;
			MConstantsEntity mConstants = mConstantsDao.select(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
			if (Objects.isNull(mConstants)) {
				mConstants = new MConstantsEntity();
				mConstants.setConstantsKey(CommonConsts.T_ARCHIVE_FILES_YEAR_MONTH);
				mConstantsDao.insert(mConstants);
			} else if (Objects.nonNull(mConstants.getConstantsValue())) {
				yearMonth = mConstants.getConstantsValue().trim();
				if (yearMonth == "") {
					yearMonth = null;
				}
			}
			// 支払明細のアーカイブ
			googleDriveService.createArchiveLocalDocument("paymentDetail", yearMonth);

			mConstants.setConstantsValue(null);
			mConstantsDao.update(mConstants);

			long end = System.currentTimeMillis();

			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}

	/* ■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 以下単発実行のバッチ処理 ※ データメンテナンス等 ■■■■■■■■■■■■■■■■■■■■■■■■■■■■ */
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

	/**
	 * 請書のファイルIDリフレッシュ
	 *
	 */
	@RequestMapping("/refreshOrderReport")
	public ResponseEntity refreshOrderReport() {
		try {
			long start = System.currentTimeMillis();

			String[] array = {
					"4505465855","4505571869","4505578957","4505579851","4505582720",
					"4505584263","4505585980","4505586514","4505590506","4505595911",
					"4505595921","4505596159","4505606257","4505607453","4505607492",
					"4505608852","4505609562","4505610478","4505610856","4505615220",
					"4505617227","4505619396","4505629425","4505630650","4505652552",
					"4505652555","4505657401","4505657523","4505657654","4505658686",
					"4505659516","4505661416","4505661419","4505665988","4505674109",
					"4505674268","4505676575","4505678976","4505679920","4505680319",
					"4505680630","4505688010","4505688113","4505693155","4505696781",
					"4505697792","4505704570","4505707023","4505711285","4505714263",
					"4505718852","4505722264","4505722268","4505725165","4505727226",
					"4505730462","4505731752","4505750201","4505752530","4505755437",
					"4505758080","4505759088","4505761426","4505772678","4505775322",
					"4505778766","4505789155"};
			List<String> orderNumberList = Arrays.asList(array);
//			//発注請書のID一覧取得
//			List<String> orderNumberList = new ArrayList<String>();
//			orderNumberList.add("4505576864");
//			orderNumberList.add("4505577036");

			List<TCloudSignEntity> confirmationFileList = tCloudSignDao.selectNewestList(orderNumberList, CloudSignApi.FORM_TYPE_ORDER);
			//リストを確認
			Map<String, Object> confrimationCountMap = cloudSignService.refreshOrderReport(confirmationFileList, CloudSignApi.FORM_TYPE_ORDER);

			long end = System.currentTimeMillis();
			super.setResponseData("confrimationCountMap",confrimationCountMap);
			super.setResponseData("time",(end - start)  + "ms");
		} catch (Exception e) {
			String msg = SystemLoggingUtil.getStackTraceString(e);
			MailExUtils.sendMail(adminEmail, MailService.MAIL_ADDR_FROM, MailService.MAIL_SIGN_FROM, MailContents.getSystemBatchErrSubject(), msg);
		}
		return super.response();
	}
	/**
	 * クラウドサインリマインド
	 * 特定のデータへ対してのリマインド
	 *
	 */
	@RequestMapping("/remindCloudSignManual")
	public ResponseEntity remindCloudSignManual() {
		try {
			long start = System.currentTimeMillis();
			String[] array = {"4505580547","4505590757","4505610800","4505600551","4505594535",
					"4505595491","4505601595","4505598774","4505590135","4505583792","4505588515",
					"4505602671","4505595319","4505594146","4505605167","4505596183","4505610496",
					"4505610589","4505611349","4505595828","4505598149","4505607419",
					"4505589463","4505588389","4505596807","4505589881","4505589569","4505609562",
					"4505585208","4505587189","4505580486","4505611905","4505610758","4505596631",
					"4505583475","4505585967","4505600472","4505600448","4505600369","4505600348",
					"4505590531","4505587050","4505587300","4505587299","4505599026","4505584896",
					"4505584894","4505584893","4505584891","4505594444","4505531899","4505566307",
					"4505605160","4505545269","4505602355","4505571118","4505612075","4505611115",
					"4505598042","4505583896","4505599722","4505597220","4505588621","4505597456",
					"4505504349","4505583638","4505593490","4505596820","4505599573","4505611110",
					"4505602082","4505597095","4505583350","4505597097","4505585161","4505610759",
					"4505593251","4505592372","4505585980","4505610879","4505600079",
					"4505590297","4505579270","4505608329","4505592164","4505605231","4505610725",
					"4505609141","4505609193","4505607415","4505594367","4505592427","4505598361",
					"4505590171","4505583292","4505590162","4505590167","4505539128","4505599692",
					"4505599716","4505610403","4505597500","4505599706","4505588440","4505609226",
					"4505609596","4505611361","4505603692","4505603534","4505607365","4505590299",
					"4505596296","4505606787","4505597040","4505596827","4505611268","4505591546",
					"4505585387","4505609500"};
			List<String> orderNumberList = Arrays.asList(array);

			//発注請書のID一覧取得
			List<TCloudSignEntity> remindList = tCloudSignDao.selectNewestList(orderNumberList, CloudSignApi.FORM_TYPE_ORDER);
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

}
