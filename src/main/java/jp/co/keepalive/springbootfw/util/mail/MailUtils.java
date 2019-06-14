package jp.co.keepalive.springbootfw.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;
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

public class MailUtils {

	public static final String PROPS_KEY_MAIL_SMTP_HOST = "mail.smtp.host";

	public static final String PROPS_VALUE_MAIL_SMTP_HOST = "localhost";
//	public static final String PROPS_VALUE_MAIL_SMTP_HOST = "mailsv01.tama.net";

	public static final String PROPS_MAIL_SMTP_PORT = "mail.smtp.port";

	public static final String PROPS_MAIL_SMTP_PORT_NUM = "25";

	public static final String PROPS_MAIL_SMTP_MSGID_USER = "mail.user";

	public static final String PROPS_MAIL_SMTP_MSGID_USER_NAME = "root";

	public static final String PROPS_KEY_MAIL_HOST = "mail.host";

	public static final String PROPS_VALUE_MAIL_HOST = "localhost";
//	public static final String PROPS_VALUE_MAIL_HOST = "mailsv01.tama.net";

	public static final String PROPS_KEY_MAIL_SMTP_AUTH = "mail.smtp.auth";

	public static final String MAIL_ENCODING = "ISO-2022-JP";

	public static final String MAIL_TEMP_FILE_DIR = "/tmp/";


	public static void sendMail(String mailTo, String mailFromAddress,
			String mailFromSign, String mailSubject, String mailContent) {
		sendMail(mailTo, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}

	public static void sendMailCc(String mailTo, String mailCc, String mailFromAddress,
			String mailFromSign, String mailSubject, String mailContent) {
		sendMail(mailTo, mailCc, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}

	public static void sendMail(List<String> toList, String mailFromAddress,
	        String mailFromSign, String mailSubject, String mailContent) {
	    sendMail(toList, null, mailFromAddress, mailFromSign, mailSubject, mailContent);
	}

    public static void sendMail(List<String> toList, String mailCc, String mailFromAddress,
                                String mailFromSign, String mailSubject, String mailContent) {
        String toStr = "";
        for (String to : toList) {
            toStr += to + ",";
        }
        toStr = toStr.substring(0, toStr.length()-1);

        Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty(PROPS_MAIL_SMTP_PORT, PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);


        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setHeader("Content-Type","text/plain; charset=ISO-2022-JP");
            mimeMessage.setRecipients(Message.RecipientType.TO, toStr);
            if(mailCc != null) {
                mimeMessage.setRecipients(Message.RecipientType.CC, mailCc);
            }
            InternetAddress from = new InternetAddress(mailFromAddress,
                    mailFromSign, MAIL_ENCODING);
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

    public static void sendMail(List<String> toList, String mailCc, String mailBcc, String mailFromAddress,
                                String mailFromSign, String mailSubject, String mailContent) {
        String toStr = "";
        for (String to : toList) {
            toStr += to + ",";
        }
        toStr = toStr.substring(0, toStr.length()-1);

        Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty(PROPS_MAIL_SMTP_PORT, PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);


        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setHeader("Content-Type","text/plain; charset=ISO-2022-JP");
            mimeMessage.setRecipients(Message.RecipientType.TO, toStr);
            if(mailCc != null) {
                mimeMessage.setRecipients(Message.RecipientType.CC, mailCc);
            }
            if(mailBcc != null) {
                mimeMessage.setRecipients(Message.RecipientType.BCC, mailBcc);
            }
            InternetAddress from = new InternetAddress(mailFromAddress,
                    mailFromSign, MAIL_ENCODING);
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

    public static void sendMail(String mailTo, String mailCc, String mailFromAddress,
            String mailFromSign, String mailSubject, String mailContent) {
        Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty(PROPS_MAIL_SMTP_PORT, PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);


        Session session = Session.getDefaultInstance(mailProps, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setHeader("Content-Type","text/plain; charset=ISO-2022-JP");
            mimeMessage.setRecipients(Message.RecipientType.TO, mailTo);
            if(mailCc != null) {
                mimeMessage.setRecipients(Message.RecipientType.CC, mailCc);
            }
            InternetAddress from = new InternetAddress(mailFromAddress,
                    mailFromSign, MAIL_ENCODING);
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

    public static void sendMail(String to, List<String> ccList, String from,
            String fromSign, String subject, String content, List<String> fileNameList) {

        Properties props = createMailProps();
        Session session=Session.getDefaultInstance(props,null);
        MimeMessage mimeMessage  =new MimeMessage(session);
        try {
            // 送信元メールアドレスと送信者名を指定
            mimeMessage.setFrom(new InternetAddress(from,"",MAIL_ENCODING));
            // 送信先メールアドレスを指定
            mimeMessage.setRecipients(Message.RecipientType.TO,to);
            // CC あれば指定
            for (String cc : ccList) {
                mimeMessage.setRecipients(Message.RecipientType.CC, cc);
            }
            // メールのタイトルを指定
            mimeMessage.setSubject(subject,MAIL_ENCODING);

            // マルチパートオブジェクトを生成
            Multipart mp = new MimeMultipart();

            // １つ目は本文
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(content, MAIL_ENCODING);
            mbp.setHeader("Content-Type","text/plain; charset=ISO-2022-JP");
            mp.addBodyPart(mbp);

            // ２つ目以降は画像
            for (String fileName : fileNameList) {
                MimeBodyPart fileMbp = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(MAIL_TEMP_FILE_DIR + fileName);
                DataHandler imgdh = new DataHandler(fds);
                fileMbp.setDataHandler(imgdh);
                fileMbp.setFileName(MimeUtility.encodeWord(fileName));
                mp.addBodyPart(fileMbp);
            }

            mimeMessage.setContent(mp);
            mimeMessage.setSentDate(new java.util.Date());
            // 送信します
            Transport.send(mimeMessage);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

	private static Properties createMailProps() {
	    Properties mailProps = new Properties();
        mailProps.setProperty(PROPS_KEY_MAIL_SMTP_HOST, PROPS_VALUE_MAIL_SMTP_HOST);
        mailProps.setProperty("PROPS_MAIL_SMTP_PORT", PROPS_MAIL_SMTP_PORT_NUM);

        mailProps.setProperty(PROPS_MAIL_SMTP_MSGID_USER, PROPS_MAIL_SMTP_MSGID_USER_NAME);
        mailProps.setProperty(PROPS_KEY_MAIL_HOST, PROPS_VALUE_MAIL_HOST);
        return mailProps;
	}

}
