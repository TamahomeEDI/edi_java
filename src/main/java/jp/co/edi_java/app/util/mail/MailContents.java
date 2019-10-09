package jp.co.edi_java.app.util.mail;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.util.crypto.CipherUtils;

@Component
public class MailContents {

	private static String BASE_URL;
	private static String PREFIX = "";

	private static String STG_FLG_ON = "1";

	private MailContents(@Value("${edi.base.url}") String url,
						  @Value("${stg.flg}") String flg) {
		BASE_URL = url;
		if(flg.equals(STG_FLG_ON)) {
			PREFIX = "【検証環境】";
		}
	}

    /*
     * 01_タマ残処理件数
     * to 社員
     */
    public static String getUserProcessLogSubject(String date) {
        return PREFIX + "【" + date + "のお知らせ】 TH-EDI";
    }

    public static String getUserProcessLogBody(String eigyousyoName, String syainName, int deliveryCount, int workReportCount) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + "支店\n"
                + syainName + " 様\n"
                + "\n"
                + "承認待ち納品： " + deliveryCount + " 件\n"
                + "出来高報告書： " + workReportCount + " 件\n"
                + "\n"
                + "url\n";
    }

    /*
     * 02_見積作成通知
     * to 社員
     */
    public static String getEstimateRegistSubject() {
        return PREFIX + "【見積・設計受領のお知らせ】 TH-EDI";
    }

    public static String getEstimateRegistBody(String estimateNumber, String eigyousyoName, String syainName, String koujiName, String gyousyaName, String partnerBikou) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + "\n"
                + syainName + " 様\n"
                + "\n"
                + "見積・設計を受領しました。\n"
                + "\n"
                + "工事名： " + koujiName + "\n"
                + "業者名： " + gyousyaName + "\n"
                + "備考：\n"
                + partnerBikou + "\n"
                + "\n"
                + BASE_URL + "/user/estimate/detail?estimateNumber=" + estimateNumber + "\n";
    }

    /*
     * 03_業者残処理件数
     * to 業者
     */
    public static String getPartnerProcessLogSubject(String date) {
        return PREFIX + "【" + date + "のお知らせ】 TH-EDI";
    }

    public static String getPartnerProcessLogBody(String gyousyaName, String date, int estimateCount, int confirmationCount, int workCount, int inspectionReceiptCount, int orderCancelCount) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + date + "の状況をお知らせ致します。\n"
                + "\n"
                + "見積依頼： " + estimateCount + " 件\n"
                + "請書未返信： " + confirmationCount + " 件\n"
                + "納品/出来高報告書未返信： " + workCount + " 件\n"
                + "検収明細書： " + inspectionReceiptCount + " 件\n"
                + "発注取消合意書： " + orderCancelCount + " 件\n"
                + "\n"
                + "下記URLよりご確認下さい。\n"
                + "url\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 04_アカウント発行
     * to 業者
     */
    public static String getPartnerRegistSubject() {
        return PREFIX + "【アカウント発行のお知らせ】 TH-EDI";
    }

    public static String getPartnerRegistBody(String gyousyaName, String token) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "TH-EDIシステムのアカウントが発行されました。\n"
                + "アカウント情報は以下となります。\n"
                + "\n"
                + "　ログインID：業者番号\n"
                + "　パスワード：初回アクセス時にご登録いただきます\n"
                + "\n"
                + "下記URLへにアクセスしアカウントの本登録を完了させて下さい。\n"
                + BASE_URL + "/partner/authentication/?t=" + token + "\n"
                + "\n"
                + "\n"
                + "※お使いのメールソフトによってはURLが途中で改行されることがあります。\n"
                + "　その場合は、最初の「http://」から末尾の英数字までをブラウザに\n"
                + "　直接コピー＆ペーストしてアクセスしてください。\n"
                + "\n"
                + "※当メールは送信専用メールアドレスから配信されています。\n"
                + "　このままご返信いただいてもお答えできませんのでご了承ください。\n"
                + "\n"
                + "※当メールに心当たりの無い場合は、誠に恐れ入りますが\n"
                + "　破棄して頂けますよう、よろしくお願い致します。\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 05_アカウント発行リマインド
     * to 業者
     */
    public static String getPartnerRegistRemindSubject() {
    	return PREFIX + "※再送信【アカウント発行のお知らせ】 TH-EDI";
    }

    public static String getPartnerRegistRemindBody(String gyousyaName, String token) {
        return ""
                + "\n"
                + "このメールは再送信です。\n"
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "TH-EDIシステムのアカウントが発行されました。\n"
                + "アカウント情報は以下となります。\n"
                + "\n"
                + "　ログインID：業者番号\n"
                + "　パスワード：初回アクセス時にご登録いただきます\n"
                + "\n"
                + "下記URLへにアクセスしアカウントの本登録を完了させて下さい。\n"
                + BASE_URL + "/partner/authentication/?t=" + token + "\n"
                + "\n"
                + "\n"
                + "※お使いのメールソフトによってはURLが途中で改行されることがあります。\n"
                + "　その場合は、最初の「http://」から末尾の英数字までをブラウザに\n"
                + "　直接コピー＆ペーストしてアクセスしてください。\n"
                + "\n"
                + "※当メールは送信専用メールアドレスから配信されています。\n"
                + "　このままご返信いただいてもお答えできませんのでご了承ください。\n"
                + "\n"
                + "※当メールに心当たりの無い場合は、誠に恐れ入りますが\n"
                + "　破棄して頂けますよう、よろしくお願い致します。\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 06_パスワード再発行依頼
     * to 業者
     */
    public static String getForgetPasswordSubject() {
        return PREFIX + "【パスワード再発行の手続き】 TH-EDI";
    }

    public static String getForgetPasswordBody(String gyousyaName, String token) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "TH-EDIシステムのパスワード再発行手続きを承りました。\n"
                + "\n"
                + "下記URLへにアクセスしパスワードの変更を完了させて下さい。\n"
                + BASE_URL + "/partner/password/reset/?t=" + token + "\n"
                + "\n"
                + "\n"
                + "※お使いのメールソフトによってはURLが途中で改行されることがあります。\n"
                + "　その場合は、最初の「http://」から末尾の英数字までをブラウザに\n"
                + "　直接コピー＆ペーストしてアクセスしてください。\n"
                + "\n"
                + "※当メールは送信専用メールアドレスから配信されています。\n"
                + "　このままご返信いただいてもお答えできませんのでご了承ください。\n"
                + "\n"
                + "※当メールに心当たりの無い場合は、誠に恐れ入りますが\n"
                + "　破棄して頂けますよう、よろしくお願い致します。\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 07-1_受入差し戻し（納品）
     * to 業者
     */
    public static String getRemandSubject() {
        return PREFIX + "【納品・出来高差し戻しのお知らせ】 TH-EDI";
    }

    public static String getDeliveryRemandBody(String deliveryNumber, String gyousyaName, String userBikou) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "納品・出来高が差し戻されました。\n"
                + "\n"
                + "差し戻し理由：\n"
                + userBikou + "\n"
                + "\n"
                + "\n"
                + "詳細は、下記URLよりご確認下さい。\n"
                + BASE_URL + "/partner/delivery/detail/?deliveryNumber=" + deliveryNumber + "\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 07-2_受入差し戻し（出来高）
     * to 業者
     */
    public static String getWorkReportRemandBody(String workReportNumber, String gyousyaName, String userBikou) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + " 御中\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "納品・出来高が差し戻されました。\n"
                + "\n"
                + "差し戻し理由：\n"
                + userBikou + "\n"
                + "\n"
                + "\n"
                + "詳細は、下記URLよりご確認下さい。\n"
                + BASE_URL + "/partner/workreport/detail/?workReportNumber=" + workReportNumber + "\n"
                + "\n"
                + "\n"
                + "何卒宜しくお願い申し上げます。\n";
    }

    /*
     * 08_請書受領
     * to 社員
     */
    public static String getConfirmationAgreeSubject() {
        return PREFIX + "【請書受領のお知らせ】 TH-EDI";
    }

    public static String getConfirmationAgreeBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "請書を受領しました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + BASE_URL + "/user/order/detail/?orderNumber=" + orderNumber + "\n";
    }

    /*
     * 08_受注拒否
     * to 社員
     */
    public static String getConfirmationDismissalSubject() {
        return PREFIX + "【発注差戻のお知らせ】 TH-EDI";
    }

    public static String getConfirmationDismissalBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "以下発注書は受理されず、却下されました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + BASE_URL + "/user/order/detail/?orderNumber=" + orderNumber + "\n";
    }

    /*
     * 09-1_納品受領
     * to 社員
     */
    public static String getDeliveryWorkReportSubject(Boolean remind) {
    	String subject = "【納品出来高報告のお知らせ】 TH-EDI";
    	if (remind) {
    		subject = "【再送】【納品出来高報告のお知らせ】 TH-EDI";
    	}
        return PREFIX + subject;
    }

    public static String getDeliveryBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, String deliveryNumber, String deliveryDate, List<TDeliveryItemEntity> itemList) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {
    		//detail = String.format("%-30s", "品名") + "| " + String.format("%-10s", "発注数量") + "| " + String.format("%-10s", "納入数") + "| " + String.format("%-10s", "納入残") + "| " + String.format("%-8s", "単位") + "\n\n";
    		detail = "[品名],  [発注数量],  [納入数],  [納入残],  [単位]\n\n";
    		for (TDeliveryItemEntity item : itemList) {
    			//detail += String.format("%-30s", item.getItemName()) + "| " + String.format("%10s", item.getOrderQuantity()) + "| " + String.format("%10s", item.getDeliveryQuantity()) + "| " + String.format("%10s", item.getDeliveryRemainingQuantity()) + "| " + String.format("%-8s", item.getUnit()) + "\n";
    			detail += item.getItemName() + ",  " + item.getOrderQuantity() + ",  " + item.getDeliveryQuantity() + ",  " + item.getDeliveryRemainingQuantity() + ",  " + item.getUnit() + "\n";
    		}
    	}

    	String encryptNumber = CipherUtils.getEncryptAES(deliveryNumber);

    	return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "納品出来高報告を受領しました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + deliveryDate + "\n"
                + "\n"
                + "\n"
                + "受入： " + BASE_URL + "/user/delivery/acceptance/apply?t=" + encryptNumber + "\n"
                + "\n"
                + "差戻： " + BASE_URL + "/user/delivery/acceptance/sendback?t=" + encryptNumber + "\n"
                + "\n\n"
                + detail;
    }

    public static String getDeliveryHtmlBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, String deliveryNumber, String deliveryDate, List<TDeliveryItemEntity> itemList) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {
    		detail = "<table border=\"1\" style=\"border-collapse:collapse;width:100%;max-width:1024px;padding:2px 4px 2px 4px;\">\n"
    				+ "<tr><th style=\"text-align:left;\">[品名]</th><th style=\"text-align:left;\">[発注数量]</th>\n"
        			+ "<th style=\"text-align:left;\">[納入数]</th><th style=\"text-align:left;\">[納入残]</th><th style=\"text-align:left;\">[単位]</th></tr>\n";
    		for (TDeliveryItemEntity item : itemList) {
    			detail += "<tr><td style=\"width:40%\">" + item.getItemName()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getOrderQuantity()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getDeliveryQuantity()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getDeliveryRemainingQuantity()
    				+ "</td><td style=\"width:15%;\">" + item.getUnit() + "</td></tr>\n";
    		}
    		detail += "</table>";
    	}

    	String encryptNumber = CipherUtils.getEncryptAES(deliveryNumber);

    	return ""
    			+ "<!DOCTYPE html>\n"
    			+ "<html lang=\"ja\">\n"
    			+ "<head>\n"
    			+ "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1\">\n"
    			+ "<meta charset=\"UTF-8\">\n"
    			+ "<title></title>\n"
    			+ "<style></style></head><body>\n"
                + "<pre>\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "納品出来高報告を受領しました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + deliveryDate + "\n"
                + "\n"
                + "\n</pre>"
                + "<a href='" + BASE_URL + "/user/delivery/acceptance/apply?t=" + encryptNumber + "' target='_blank'>受入</a>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<a href='" + BASE_URL + "/user/delivery/acceptance/sendback?t=" + encryptNumber + "' target='_blank'>差戻</a>"
                + "<br><br>"
                + detail
                + "</body></html>";
    }

    /*
     * 09-2_出来高受領
     * to 社員
     */
    public static String getWorkReportBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, int workRate, String workReportNumber, String workReportDate, List<TWorkReportItemEntity> itemList) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {
    		detail = "[品名],  [発注数量],  [単位]\n\n";
    		for (TWorkReportItemEntity item : itemList) {
    			detail += item.getItemName() + ",  " + item.getOrderQuantity() +  ",  " + item.getUnit() + "\n";
    		}
    	}

    	String encryptNumber = CipherUtils.getEncryptAES(workReportNumber);

    	return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "納品出来高報告を受領しました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + workReportDate + "\n"
                + "\n"
                + "\n"
                + "受入： " + BASE_URL + "/user/workreport/acceptance/apply?t=" + encryptNumber + "\n"
                + "\n"
                + "差戻： " + BASE_URL + "/user/workreport/acceptance/sendback?t=" + encryptNumber + "\n"
                + "\n\n"
                + "【出来高：" + workRate + " パーセント】\n"
                + detail;
    }

    public static String getWorkReportHtmlBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, int workRate, String workReportNumber, String workReportDate, List<TWorkReportItemEntity> itemList) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {
    		detail = "<table border=\"1\" style=\"border-collapse:collapse;width:100%;max-width:1024px;padding:2px 4px 2px 4px;\">\n"
    				+ "<tr><th style=\"text-align:left;\">[品名]</th><th style=\"text-align:left;\">[発注数量]</th><th style=\"text-align:left;\">[単位]</th></tr>\n";
    		for (TWorkReportItemEntity item : itemList) {
    			detail += "<tr><td style=\"width:64%\">" + item.getItemName()
    				+ "</td><td style=\"text-align:right;width:18%\">" + item.getOrderQuantity()
    				+ "</td><td style=\"width:18%;\">" + item.getUnit() + "</td></tr>\n";
    		}
    		detail += "</table>";
    	}
    	String encryptNumber = CipherUtils.getEncryptAES(workReportNumber);

    	return ""
    			+ "<!DOCTYPE html>\n"
    			+ "<html lang=\"ja\">\n"
    			+ "<head>\n"
    			+ "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1\">\n"
    			+ "<meta charset=\"UTF-8\">\n"
    			+ "<title></title>\n"
    			+ "<style></style></head><body>\n"
                + "<pre>\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "納品出来高報告を受領しました。\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + workReportDate + "\n"
                + "\n"
                + "\n</pre>"
                + "<a href='" + BASE_URL + "/user/workreport/acceptance/apply?t=" + encryptNumber + "' target=_blank>受入</a>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<a href='" + BASE_URL + "/user/workreport/acceptance/sendback?t=" + encryptNumber + "' target=_blank>差戻</a>"
                + "<br><br>"
                + "<p style=\"font-weight:bold;font-size:medium;\">【出来高：" + workRate + " パーセント】</p>\n"
                + detail
                + "</body></html>";
    }

    public static String getDeliveryWorkReportRejectSubject() {
    	String subject = "【否認】【納品出来高報告のお知らせ】 TH-EDI";

        return PREFIX + subject;
    }

    /*
     * 納品書 受入申請否認時メール
     *
     */
    public static String getDeliveryRejectHtmlBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, String deliveryNumber, String deliveryDate, List<TDeliveryItemEntity> itemList, String comments) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {
    		detail = "<table border=\"1\" style=\"border-collapse:collapse;width:100%;max-width:1024px;padding:2px 4px 2px 4px;\">\n"
    				+ "<tr><th style=\"text-align:left;\">[品名]</th><th style=\"text-align:left;\">[発注数量]</th>\n"
        			+ "<th style=\"text-align:left;\">[納入数]</th><th style=\"text-align:left;\">[納入残]</th><th style=\"text-align:left;\">[単位]</th></tr>\n";
    		for (TDeliveryItemEntity item : itemList) {
    			detail += "<tr><td style=\"width:40%\">" + item.getItemName()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getOrderQuantity()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getDeliveryQuantity()
    				+ "</td><td style=\"text-align:right;width:15%\">" + item.getDeliveryRemainingQuantity()
    				+ "</td><td style=\"width:15%;\">" + item.getUnit() + "</td></tr>\n";
    		}
    		detail += "</table>";
    	}

    	String encryptNumber = CipherUtils.getEncryptAES(deliveryNumber);

    	return ""
    			+ "<!DOCTYPE html>\n"
    			+ "<html lang=\"ja\">\n"
    			+ "<head>\n"
    			+ "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1\">\n"
    			+ "<meta charset=\"UTF-8\">\n"
    			+ "<title></title>\n"
    			+ "<style></style></head><body>\n"
                + "<pre>\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "納品書の受入申請が否認されました。業者へ差戻を行うか再度申請ください。\n"
                + "\n"
                + "[承認者コメント]\n"
                + comments + "\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + deliveryDate + "\n"
                + "\n"
                + "\n</pre>"
                + "<a href='" + BASE_URL + "/user/delivery/acceptance/apply?t=" + encryptNumber + "' target='_blank'>受入(再申請)</a>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<a href='" + BASE_URL + "/user/delivery/acceptance/sendback?t=" + encryptNumber + "' target='_blank'>差戻</a>"
                + "<br><br>"
                + detail
                + "</body></html>";
    }

    /*
     * 出来高報告書 受入申請否認時メール
     *
     */
    public static String getWorkReportRejectHtmlBody(String eigyousyoName, String syainName, String koujiName, String gyousyaName, String orderNumber, int workRate, String workReportNumber, String workReportDate, List<TWorkReportItemEntity> itemList, String comments) {

    	String detail = "";
    	if (Objects.nonNull(itemList) && ! itemList.isEmpty()) {

    		detail = "<table border=\"1\" style=\"border-collapse:collapse;width:100%;max-width:1024px;padding:2px 4px 2px 4px;\">\n"
    				+ "<tr><th style=\"text-align:left;\">[品名]</th><th style=\"text-align:left;\">[発注数量]</th><th style=\"text-align:left;\">[単位]</th></tr>\n";
    		for (TWorkReportItemEntity item : itemList) {
    			detail += "<tr><td style=\"width:64%\">" + item.getItemName()
    				+ "</td><td style=\"text-align:right;width:18%\">" + item.getOrderQuantity()
    				+ "</td><td style=\"width:18%;\">" + item.getUnit() + "</td></tr>\n";
    		}
    		detail += "</table>";
    	}
    	String encryptNumber = CipherUtils.getEncryptAES(workReportNumber);

    	return ""
    			+ "<!DOCTYPE html>\n"
    			+ "<html lang=\"ja\">\n"
    			+ "<head>\n"
    			+ "<meta name=\"viewport\" content=\"width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1\">\n"
    			+ "<meta charset=\"UTF-8\">\n"
    			+ "<title></title>\n"
    			+ "<style></style></head><body>\n"
                + "<pre>\n"
                + "------------------------------------------------------------------\n"
                + "本メールはTH-EDIシステムから自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + eigyousyoName + " 支店\n"
                + syainName + " 様\n"
                + "\n"
                + "出来高報告書の受入申請が否認されました。業者へ差戻を行うか再度申請ください。\n"
                + "\n"
                + "[承認者コメント]\n"
                + comments + "\n"
                + "\n"
                + "工事名：" + koujiName + "\n"
                + "業者名：" + gyousyaName + "\n"
                + "発注番号：" + orderNumber + "\n"
                + "納品日：" + workReportDate + "\n"
                + "\n"
                + "\n</pre>"
                + "<a href='" + BASE_URL + "/user/workreport/acceptance/apply?t=" + encryptNumber + "' target=_blank>受入(再申請)</a>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "<a href='" + BASE_URL + "/user/workreport/acceptance/sendback?t=" + encryptNumber + "' target=_blank>差戻</a>"
                + "<br><br>"
                + "<p style=\"font-weight:bold;font-size:medium;\">【出来高：" + workRate + " パーセント】</p>\n"
                + detail
                + "</body></html>";
    }


    /*
     * 90_会計基準（回答完了）
     * to 業者
     */
    public static String getKaikeiKijunSubject() {
        return PREFIX + "【回答を受け付けました】 会計基準について";
    }

    public static String getKaikeiKijunBody(String gyousyaName, String staffName, String kaikeiKijun, String registDate) {
        return ""
                + "\n"
                + "------------------------------------------------------------------\n"
                + "本メールは自動送信されています。\n"
                + "------------------------------------------------------------------\n"
                + "\n"
                + gyousyaName + "\n"
                + staffName + "様\n"
                + "\n"
                + "いつもお世話になっております。\n"
                + "タマホーム株式会社でございます。\n"
                + "\n"
                + "会計基準についてご回答をいただき、誠にありがとうございます。\n"
                + "下記内容で処理を進めさせて頂きます。\n"
                + "\n"
                + "＜ご回答内容＞\n"
                + "　会計基準　 ：会計基準が「" + getKaikeiKijun(kaikeiKijun) + "」である\n"
                + "　回答日　　  ：" + registDate + "\n"
                + "　ご担当者様：" + staffName + "様\n"
                + "\n"
                + "ご確認の程、宜しくお願い致します。\n"
                + "\n"
                + "\n"
                + "----------------------------------------------------------------------\n"
                + "タマホーム株式会社　工務資材部　\n"
                + "　TEL：03-6408-1680\n";
    }

    private static String getKaikeiKijun(String kaikeiKijun) {
    	if(kaikeiKijun.equals("1")) {
    		return "完成基準";
    	}else if(kaikeiKijun.equals("2")) {
    		return "進行基準";
    	}
        return "未入力";
    }

    /*
     * 99_エラー通知
     * to システム管理者
     */
    public static String getSystemErrSubject() {
    	return PREFIX + "【システムエラーのお知らせ】 TH-EDI";
    }

    /*
     * 99_エラー通知（バッチ）
     * to システム管理者
     */
    public static String getSystemBatchErrSubject() {
    	return PREFIX + "【バッチ処理エラーのお知らせ】 TH-EDI";
    }

}
