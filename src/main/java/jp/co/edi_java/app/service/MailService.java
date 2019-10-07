package jp.co.edi_java.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.edi_java.app.util.mail.MailExUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class MailService {

    public static final String MAIL_ADDR_FROM = "noreply@tamahome.jp";
    public static final String MAIL_ADDR_FROM_KAIKEI = "th-edi@tamahome.jp";
    public static final String MAIL_SIGN_FROM = "タマホーム電子発注システム";

    public static final String STG_CC_MAIL = "to-suzuki@tamahome.jp, tmh-0398-suzuki@ezweb.ne.jp";
    public static final String STG_BCC_MAIL = "shinji-yamaguchi@tamahome.jp";

    /** TO：1件 */
    private void sendMail(String toMail, String subject, String body) {
    	MailExUtils.sendMail(toMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

    /** TO：1件 */
    private void sendMailKaikei(String toMail, String subject, String body) {
    	MailExUtils.sendMail(toMail, MAIL_ADDR_FROM_KAIKEI, MAIL_SIGN_FROM, subject, body);
	}

    /** TO：1件 CC：1件 */
	private void sendMail(String toMail, String ccMail, String subject, String body) {
		MailExUtils.sendMail(toMail, ccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

	/** TO：複数件 */
	private void sendMail(List<String> toList, String subject, String body) {
		MailExUtils.sendMail(toList, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

	/** TO：複数件 CC：1件 */
    private void sendMail(List<String> toList, String ccMail,  String subject, String body) {
        MailExUtils.sendMail(toList, ccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
    }

	/** TO：複数件 CC：1件 BCC：1件 */
    private void sendMail(List<String> toList, String ccMail, String bccMail, String subject, String body) {
        MailExUtils.sendMail(toList, ccMail, bccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
    }

    /** 添付ファイルメール */
    private void sendMPartMail(List<String> toList, List<String> ccList, List<String> bccList, String subject, String txtBody, String htmlBody, List<Map<String,String>> fileList) {
        MailExUtils.sendMPartMail(toList, ccList, bccList, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, txtBody, htmlBody, fileList);
    }

    /*
     * 01_タマ残処理件数
     */
	public void sendMailUserProcessLog(String to, String date) {
//		sendMail(to,
//				MailContents.getUserProcessLogSubject(date),
//				MailContents.getUserProcessLogBody());
	}

    /*
     * 02_見積作成通知
     */
	public void sendMailEstimateRegist(String to, String cc, MailEstimateRegistDto dto) {
		sendMail(to,
				cc,
				MailContents.getEstimateRegistSubject(),
				MailContents.getEstimateRegistBody(dto.getEstimateNumber(), dto.getEigyousyoName(), dto.getSyainName(), dto.getKoujiName(), dto.getGyousyaName(), dto.getPartnerBikou()));
	}

    /*
     * 03_業者残処理件数
     */
	public void sendMailPartnerProcessLog(List<String> toList, String date) {
//		sendMail(toList,
//				MailContents.getPartnerProcessLogSubject(date),
//				MailContents.getPartnerProcessLogBody());
	}

    /*
     * 04_アカウント発行
     */
	public void sendMailPartnerRegist(List<String> toList, String bcc, String gyousyaName, String token) {
		sendMail(toList,
				null,
				bcc,
				MailContents.getPartnerRegistSubject(),
				MailContents.getPartnerRegistBody(gyousyaName, token));
	}

    /*
     * 05_アカウント発行リマインド
     */
	public void sendMailPartnerRegistRemind(List<String> toList, String bcc, String gyousyaName, String token) {
		sendMail(toList,
				null,
				bcc,
				MailContents.getPartnerRegistRemindSubject(),
				MailContents.getPartnerRegistRemindBody(gyousyaName, token));
	}

    /*
     * 06_パスワード再発行依頼
     */
	public void sendMailForgetPassword(String to, String gyousyaName, String token) {
		sendMail(to,
				MailContents.getForgetPasswordSubject(),
				MailContents.getForgetPasswordBody(gyousyaName, token));
	}

    /*
     * 07-1_受入差し戻し（納品）
     */
	public void sendMailDeliveryRemand(List<String> toList, MGyousyaEntity gyousya, TDeliveryEntity delivery) {
		sendMail(toList,
				MailContents.getRemandSubject(),
				MailContents.getDeliveryRemandBody(delivery.getDeliveryNumber(), gyousya.getGyousyaName(), delivery.getUserBikou()));
	}

    /*
     * 07-2_受入差し戻し（出来高）
     */
	public void sendMailWorkReportRemand(List<String> toList, MGyousyaEntity gyousya, TWorkReportEntity workReport) {
		sendMail(toList,
				MailContents.getRemandSubject(),
				MailContents.getWorkReportRemandBody(workReport.getWorkReportNumber(), gyousya.getGyousyaName(), workReport.getUserBikou()));
	}

    /*
     * 08_請書受領通知
     */
	public void sendMailConfirmationAgree(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getConfirmationAgreeSubject(),
			MailContents.getConfirmationAgreeBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber),
			null,
			fileList
		);
	}

	/*
     * 08_請書受領通知
     */
	public void sendMailConfirmationDismissal(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getConfirmationDismissalSubject(),
			MailContents.getConfirmationDismissalBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber),
			null,
			fileList
		);
	}

    /*
     * 09-1_納品受領通知
     */
	public void sendMailDelivery(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList, String deliveryNumber, List<TDeliveryItemEntity> itemList, Boolean remind) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("delivery send mail: " + to);
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(remind),
			//MailContents.getDeliveryBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, itemList),
			null,
			MailContents.getDeliveryHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, itemList),
			fileList
		);
	}

    /*
     * 09-2_出来高受領通知
     */
	public void sendMailWorkReport(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, Integer workRate, List<Map<String,String>> fileList, String workReportNumber, List<TWorkReportItemEntity> itemList, Boolean remind) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("work report send mail: " + to);
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(remind),
			//MailContents.getWorkReportBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workReportNumber, itemList),
			null,
			MailContents.getWorkReportHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workRate, workReportNumber, itemList),
			fileList
		);
	}

	/*
     * 納品書 受入否認メール
     */
	public void sendMailDeliveryReject(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList, String deliveryNumber, List<TDeliveryItemEntity> itemList, String comments) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("delivery send mail: " + to);
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportRejectSubject(),
			//MailContents.getDeliveryBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, itemList),
			null,
			MailContents.getDeliveryRejectHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, itemList, comments),
			fileList
		);
	}

    /*
     * 出来高報告書 受入否認メール
     */
	public void sendMailWorkReportReject(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, Integer workRate, List<Map<String,String>> fileList, String workReportNumber, List<TWorkReportItemEntity> itemList, String comments) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("work report send mail: " + to);
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportRejectSubject(),
			//MailContents.getWorkReportBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workReportNumber, itemList),
			null,
			MailContents.getWorkReportRejectHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workRate, workReportNumber, itemList, comments),
			fileList
		);
	}

    /*
     * 90_会計基準（回答完了）
     */
	public void sendMailKaikeiKijun(String to, String gyousyaName, String staffName, String kaikeiKijun, String registDate) {
		sendMailKaikei(to,
				MailContents.getKaikeiKijunSubject(),
				MailContents.getKaikeiKijunBody(gyousyaName, staffName, kaikeiKijun, registDate));
	}

    /*
     * sapパラメータ確認
     */
//	public void sendMailSap(List<NameValuePair> param) {
//		sendMail("shinji-yamaguchi@tamahome.jp",
//				MailContents.getSapSubject(),
//				MailContents.getSapBody(param));
//	}

}
