package jp.co.edi_java.app.util.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailExUtils {

	public static final String PROPS_KEY_MAIL_SMTP_HOST = "mail.smtp.host";

	public static final String PROPS_VALUE_MAIL_SMTP_HOST = "localhost";

	public static final String PROPS_MAIL_SMTP_PORT = "mail.smtp.port";

	public static final String PROPS_MAIL_SMTP_PORT_NUM = "25";

	public static final String PROPS_MAIL_SMTP_MSGID_USER = "mail.user";

	public static final String PROPS_MAIL_SMTP_MSGID_USER_NAME = "root";

	public static final String PROPS_KEY_MAIL_HOST = "mail.host";

	public static final String PROPS_VALUE_MAIL_HOST = "localhost";

	public static final String PROPS_KEY_MAIL_SMTP_AUTH = "mail.smtp.auth";

	public static final String MAIL_ENCODING = "UTF-8";

	public static final String MAIL_TEMP_FILE_DIR = "/home/web/edi_php/tmp/mailattached/";

	/** 単一宛先 */
	public static void sendMail(String mailTo, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
		sendMail(mailTo, null, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}
	/** 単一宛先 + CC */
	public static void sendMail(String mailTo, String mailCc, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
		sendMail(mailTo, mailCc, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}
	/** 複数宛先 */
	public static void sendMail(List<String> toList, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
		String mailTo = "";
		if (Objects.nonNull(toList) && !toList.isEmpty()) {
			mailTo = String.join(",", toList);
		}
	    sendMail(mailTo, null, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}
	/** 複数宛先 + CC */
    public static void sendMail(List<String> toList, String mailCc, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
    	String mailTo = "";
		if (Objects.nonNull(toList) && !toList.isEmpty()) {
			mailTo = String.join(",", toList);
		}
		sendMail(mailTo, mailCc, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
    }
	/** 複数宛先 + 複数CC */
    public static void sendMail(List<String> toList, List<String> ccList, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
    	String mailTo = "";
    	String mailCc = "";
    	if (Objects.nonNull(toList) && !toList.isEmpty()) {
    		mailTo = String.join(",", toList);
    	}
    	if (Objects.nonNull(ccList) && !ccList.isEmpty()) {
    		mailTo = String.join(",", ccList);
    	}
		sendMail(mailTo, mailCc, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
    }
    /** 複数宛先 + CC + Bcc */
    public static void sendMail(List<String> toList, String mailCc, String mailBcc, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
    	String mailTo = "";
		if (Objects.nonNull(toList) && !toList.isEmpty()) {
			mailTo = String.join(",", toList);
		}
		sendMail(mailTo, mailCc, mailBcc, mailFromAddress, mailFromSign, mailSubject, mailContent);
    }
    /** 複数宛先 + 複数CC + Bcc */
    public static void sendMail(List<String> toList, List<String> ccList, String mailBcc, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
    	String mailTo = "";
    	String mailCc = "";
    	if (Objects.nonNull(toList) && !toList.isEmpty()) {
    		mailTo = String.join(",", toList);
    	}
    	if (Objects.nonNull(ccList) && !ccList.isEmpty()) {
    		mailTo = String.join(",", ccList);
    	}
		sendMail(mailTo, mailCc, mailBcc, mailFromAddress, mailFromSign, mailSubject, mailContent);
    }
    /** 複数宛先 + 複数CC + 複数Bcc */
    public static void sendMail(List<String> toList, List<String> ccList, List<String> bccList, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
    	String mailTo = "";
    	String mailCc = "";
    	String mailBcc = "";
    	if (Objects.nonNull(toList) && !toList.isEmpty()) {
    		mailTo = String.join(",", toList);
    	}
    	if (Objects.nonNull(ccList) && !ccList.isEmpty()) {
    		mailTo = String.join(",", ccList);
    	}
    	if (Objects.nonNull(bccList) && !bccList.isEmpty()) {
    		mailTo = String.join(",", bccList);
    	}
		sendMail(mailTo, mailCc, mailBcc, mailFromAddress, mailFromSign, mailSubject, mailContent);
    }
    /** マルチパートメール（HTML、添付ファイルメール） */
    public static void sendMPartMail(List<String> toList, List<String> ccList, List<String> bccList, String mailFromAddress, String mailFromSign, String mailSubject, String mailTxtContent, String mailHtmlContent, List<Map<String,String>> fileList) {
    	String mailTo = "";
    	String mailCc = "";
    	String mailBcc = "";
    	if (Objects.nonNull(toList) && !toList.isEmpty()) {
    		mailTo = String.join(",", toList);
    	}
    	if (Objects.nonNull(ccList) && !ccList.isEmpty()) {
    		mailTo = String.join(",", ccList);
    	}
    	if (Objects.nonNull(bccList) && !bccList.isEmpty()) {
    		mailTo = String.join(",", bccList);
    	}
    	sendMPartMail(mailTo, mailCc, mailBcc, mailFromAddress, mailFromSign, mailSubject, mailTxtContent, mailHtmlContent, fileList);
    }

    /** プレーンテキストメール */
    public static void sendMail(String mailTo, String mailCc, String mailBcc, String mailFromAddress, String mailFromSign, String mailSubject, String mailContent) {
        Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty(PROPS_MAIL_SMTP_PORT, PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);


        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setHeader("Content-Type","text/plain; charset=UTF-8");
            mimeMessage.setRecipients(Message.RecipientType.TO, mailTo);
            if(Objects.nonNull(mailCc)) {
                mimeMessage.setRecipients(Message.RecipientType.CC, mailCc);
            }
            if(Objects.nonNull(mailBcc)) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, mailBcc);
            }
            InternetAddress from = new InternetAddress(mailFromAddress, mailFromSign, MAIL_ENCODING);
            mimeMessage.setFrom(from);
            mimeMessage.setSubject(mailSubject, MAIL_ENCODING);
            mimeMessage.setText(mailContent, MAIL_ENCODING);
            Transport.send(mimeMessage);
        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        } catch (MessagingException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /** マルチパートメール（HTML、添付ファイルメール） */
    public static void sendMPartMail(String mailTo, String mailCc, String mailBcc, String mailFromAddress, String mailFromSign, String mailSubject, String mailTxtContent, String mailHtmlContent, List<Map<String,String>> fileList) {
        Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty(PROPS_MAIL_SMTP_PORT, PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);

        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
        	//mailTo = "shinji-yamaguchi@tamahome.jp";
        	//mailCc = "";

        	//from
        	InternetAddress from = new InternetAddress(mailFromAddress, mailFromSign, MAIL_ENCODING);
        	mimeMessage.setFrom(from);
        	//to
            mimeMessage.setRecipients(Message.RecipientType.TO, mailTo);
            //cc
            if(Objects.nonNull(mailCc)) {
                mimeMessage.setRecipients(Message.RecipientType.CC, mailCc);
            }
            //bcc
            if(Objects.nonNull(mailBcc)) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, mailBcc);
            }
            //subject
            mimeMessage.setSubject(mailSubject, MAIL_ENCODING);
            // マルチパートオブジェクトを生成
            Multipart mp = new MimeMultipart();
            if (Objects.nonNull(mailTxtContent)) {
            	//テキストメッセージ本文
            	MimeBodyPart mbp1 = new MimeBodyPart();
            	mbp1.setText(mailTxtContent, MAIL_ENCODING);
            	mbp1.setHeader("Content-Type","text/plain; charset=UTF-8");
            	mp.addBodyPart(mbp1);
            }
            //html
        	if (Objects.nonNull(mailHtmlContent)) {
        		//テキストメッセージ本文
                MimeBodyPart mbp2 = new MimeBodyPart();
                mbp2.setText(mailHtmlContent, MAIL_ENCODING);
                mbp2.setHeader("Content-Type","text/html; charset=UTF-8");
                mp.addBodyPart(mbp2);
        	}
        	if (Objects.nonNull(fileList)) {
        		for(Map<String,String> fileMap : fileList) {
        			String filePath = fileMap.get("filePath");
        			String fileName = fileMap.get("fileName");
        			if (Objects.nonNull(filePath)) {
        				File attachmentFile = new File(filePath);
        				if (attachmentFile.exists()) {
        					FileDataSource fds = new FileDataSource(attachmentFile);
        					MimeBodyPart mbp = new MimeBodyPart();
        					mbp.setDataHandler(new DataHandler(fds));
        					if (Objects.isNull(fileName)) {
        						fileName = fds.getName();
        					}
        					mbp.setFileName(MimeUtility.encodeWord(fileName));
        					mp.addBodyPart(mbp);
        				}
        			}
        		}
        	}
        	mimeMessage.setContent(mp);
        	mimeMessage.setSentDate(new Date());
            Transport.send(mimeMessage);
        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        } catch (MessagingException e) {
            throw new CoreRuntimeException(e);
        }

    }

}
