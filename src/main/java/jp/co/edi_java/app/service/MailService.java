package jp.co.edi_java.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.edi_java.app.util.mail.MailExUtils;

@Service
@Scope("request")
public class MailService {

    public static final String MAIL_ADDR_FROM = "noreply@tamahome.jp";
    public static final String MAIL_ADDR_FROM_KAIKEI = "th-edi@tamahome.jp";
    public static final String MAIL_SIGN_FROM = "タマホーム電子発注システム";

    public static final String STG_CC_MAIL = "to-suzuki@tamahome.jp";
    public static final String STG_BCC_MAIL = "to-suzuki@tamahome.jp,shinji-yamaguchi@tamahome.jp";

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
     * 09-1_納品受領通知
     */
	public void sendMailDelivery(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList, String deliveryNumber) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(),
			MailContents.getDeliveryBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber),
			null,
			fileList
		);
	}

    /*
     * 09-2_出来高受領通知
     */
	public void sendMailWorkReport(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, List<Map<String,String>> fileList, String workReportNumber) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(),
			MailContents.getWorkReportBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workReportNumber),
			null,
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
//		sendMail("m.hirano@keep-alive.co.jp",
//				MailContents.getSapSubject(),
//				MailContents.getSapBody(param));
//	}

}
