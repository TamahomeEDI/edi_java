package jp.co.edi_java.app.service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.keepalive.springbootfw.util.mail.MailUtils;

@Service
@Scope("request")
public class MailService {

    public static final String MAIL_ADDR_FROM = "noreply@tamahome.jp";
    public static final String MAIL_ADDR_FROM_KAIKEI = "th-edi@tamahome.jp";
    public static final String MAIL_SIGN_FROM = "タマホーム電子発注システム";

    public static final String STG_CC_MAIL = "th-ml+1@keep-alive.co.jp";
    public static final String STG_BCC_MAIL = "th-ml+2@keep-alive.co.jp";

    /** TO：1件 */
    private void sendMail(String toMail, String subject, String body) {
    	MailUtils.sendMail(toMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

    /** TO：1件 */
    private void sendMailKaikei(String toMail, String subject, String body) {
    	MailUtils.sendMail(toMail, MAIL_ADDR_FROM_KAIKEI, MAIL_SIGN_FROM, subject, body);
	}

    /** TO：1件 CC：1件 */
	private void sendMail(String toMail, String ccMail, String subject, String body) {
		MailUtils.sendMail(toMail, ccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

	/** TO：複数件 */
	private void sendMail(List<String> toList, String subject, String body) {
		MailUtils.sendMail(toList, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
	}

	/** TO：複数件 CC：1件 */
    private void sendMail(List<String> toList, String ccMail,  String subject, String body) {
        MailUtils.sendMail(toList, ccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
    }

	/** TO：複数件 CC：1件 BCC：1件 */
    private void sendMail(List<String> toList, String ccMail, String bccMail, String subject, String body) {
        MailUtils.sendMail(toList, ccMail, bccMail, MAIL_ADDR_FROM, MAIL_SIGN_FROM, subject, body);
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
	public void sendMailConfirmationAgree(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber) {
		sendMail(to,
				cc,
				MailContents.getConfirmationAgreeSubject(),
				MailContents.getConfirmationAgreeBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber));
	}

    /*
     * 09-1_納品受領通知
     */
	public void sendMailDelivery(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, String deliveryNumber) {
		sendMail(to,
				cc,
				MailContents.getDeliveryWorkReportSubject(),
				MailContents.getDeliveryBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber));
	}

    /*
     * 09-2_出来高受領通知
     */
	public void sendMailWorkReport(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, String workReportNumber) {
		sendMail(to,
				cc,
				MailContents.getDeliveryWorkReportSubject(),
				MailContents.getWorkReportBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workReportNumber));
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
	public void sendMailSap(List<NameValuePair> param) {
		sendMail("m.hirano@keep-alive.co.jp",
				MailContents.getSapSubject(),
				MailContents.getSapBody(param));
	}

}
