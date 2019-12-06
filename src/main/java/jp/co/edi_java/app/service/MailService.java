package jp.co.edi_java.app.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.dao.syain.MSyainDao;
import jp.co.edi_java.app.dto.MailEstimateRegistDto;
import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.util.mail.MailContents;
import jp.co.edi_java.app.util.mail.MailExUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class MailService {

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	@Autowired
    public MSyainDao mSyainDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

    public static final String MAIL_ADDR_FROM = "noreply@tamahome.jp";
    public static final String MAIL_ADDR_FROM_KAIKEI = "th-edi@tamahome.jp";
    public static final String MAIL_SIGN_FROM = "タマホーム電子発注システム";

    public static final String STG_CC_MAIL = "to-suzuki@tamahome.jp, tmh-0398-suzuki@ezweb.ne.jp";
    //public static final String STG_CC_MAIL = "shinji-yamaguchi@tamahome.jp";
    public static final String STG_BCC_MAIL = "shinji-yamaguchi@tamahome.jp";

    private static String STG_FLG;
	private static String STG_FLG_ON = "1";
	private static String TEMPORARY_SYAIN_CODE = "990000";


	private MailService(@Value("${stg.flg}") String flg) {
		STG_FLG = flg;
	}

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
	public void sendMailConfirmationAgree(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, List<String> orderNumberList, List<Map<String,String>> fileList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getConfirmationAgreeSubject(),
			MailContents.getConfirmationAgreeBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumberList),
			null,
			fileList
		);
	}

	/*
     * 08_請書受領通知
     */
	public void sendMailConfirmationDismissal(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, List<String> orderNumberList, List<Map<String,String>> fileList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		sendMPartMail(toList,ccList,null,
			MailContents.getConfirmationDismissalSubject(),
			MailContents.getConfirmationDismissalBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumberList),
			null,
			fileList
		);
	}

    /*
     * 09-1_納品受領通知
     */
	public void sendMailDelivery(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TDeliveryEntity delivery, List<Map<String,String>> fileList, List<TDeliveryItemEntity> itemList, Boolean remind) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("delivery send mail: " + to);
		String orderNumber = delivery.getOrderNumber();
		String deliveryNumber = delivery.getDeliveryNumber();
		String deliveryDate = delivery.getDeliveryDate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(remind),
			//MailContents.getDeliveryBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, deliveryDate, itemList),
			null,
			MailContents.getDeliveryHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, deliveryDate, itemList),
			fileList
		);
	}

    /*
     * 09-2_出来高受領通知
     */
	public void sendMailWorkReport(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TWorkReportEntity workReport, List<Map<String,String>> fileList, List<TWorkReportItemEntity> itemList, Boolean remind) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		log.info("work report send mail: " + to);
		String orderNumber = workReport.getOrderNumber();
		String workReportNumber = workReport.getWorkReportNumber();
		String workReportDate = workReport.getWorkReportDate();
		int workRate = workReport.getWorkRate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportSubject(remind),
			//MailContents.getWorkReportBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workReportNumber, workReportDate, itemList),
			null,
			MailContents.getWorkReportHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workRate, workReportNumber, workReportDate, itemList),
			fileList
		);
	}

    /*
     * 納品取消通知
     */
	public void sendMailDeliveryCancel(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TDeliveryEntity delivery, List<TDeliveryItemEntity> itemList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		String orderNumber = delivery.getOrderNumber();
		String deliveryNumber = delivery.getDeliveryNumber();
		String deliveryDate = delivery.getDeliveryDate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportCancelSubject(),
			null,
			MailContents.getDeliveryCancelHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, deliveryDate, itemList),
			null
		);
	}

    /*
     * 出来高取消通知
     */
	public void sendMailWorkReportCancel(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TWorkReportEntity workReport, List<TWorkReportItemEntity> itemList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		String orderNumber = workReport.getOrderNumber();
		String workReportNumber = workReport.getWorkReportNumber();
		String workReportDate = workReport.getWorkReportDate();
		int workRate = workReport.getWorkRate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportCancelSubject(),
			null,
			MailContents.getWorkReportCancelHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workRate, workReportNumber, workReportDate, itemList),
			null
		);
	}


	/*
     * 納品書 受入否認メール
     */
	public void sendMailDeliveryReject(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TDeliveryEntity delivery, List<Map<String,String>> fileList, List<TDeliveryItemEntity> itemList, String comments) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		String orderNumber = delivery.getOrderNumber();
		String deliveryNumber = delivery.getDeliveryNumber();
		String deliveryDate = delivery.getDeliveryDate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportRejectSubject(),
			null,
			MailContents.getDeliveryRejectHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, deliveryNumber, deliveryDate, itemList, comments),
			fileList
		);
	}

    /*
     * 出来高報告書 受入否認メール
     */
	public void sendMailWorkReportReject(String to, String cc, String eigyousyoName, String syainName, String koujiName, String gyousyaName, TWorkReportEntity workReport, List<Map<String,String>> fileList, List<TWorkReportItemEntity> itemList, String comments) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);

		String orderNumber = workReport.getOrderNumber();
		String workReportNumber = workReport.getWorkReportNumber();
		String workReportDate = workReport.getWorkReportDate();
		int workRate = workReport.getWorkRate();
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportRejectSubject(),
			null,
			MailContents.getWorkReportRejectHtmlBody(eigyousyoName, syainName, koujiName, gyousyaName, orderNumber, workRate, workReportNumber, workReportDate, itemList, comments),
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

	public void sendMailRemindDeliveryAcceptance(String to, String cc, String eigyousyoCode, String eigyousyoName, String syainCode, String syainName, List<Map<String, String>> remindTargetList) {
		List<String> toList = new ArrayList<String>();
		toList.add(to);
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc);
		sendMPartMail(toList,ccList,null,
			MailContents.getDeliveryWorkReportRemindListSubject(),
			//MailContents.getDeliveryWorkReportRemindListBody(eigyousyoCode, eigyousyoName, syainCode, syainName, remindTargetList),
			null,
			MailContents.getDeliveryWorkReportRemindListHtmlBody(eigyousyoCode, eigyousyoName, syainCode, syainName, remindTargetList),
			null
		);
	}

	//納品書、出来高報告書の未受入対象を一覧化してリマインドメールとして送付
	public void remindDeliveryAcceptanceList(List<TDeliveryEntity> deliveryList, List<TWorkReportEntity> workReportList) {
		// 月末5日前か判定
		//本日の日付を取得
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 当日
		Date today = cal.getTime();
		// 月末日
		int endDay = cal.getActualMaximum(Calendar.DATE);
		// 月末日のDateを取得
		cal.set(Calendar.DATE, endDay);
		Date endDateMonth = cal.getTime();
		// 月末日から5日前のDateを取得
		cal.set(Calendar.DATE, endDay-5);
		Date beforeEndDate5 = cal.getTime();

		Calendar from = Calendar.getInstance();
		from.setTime(beforeEndDate5);

		Calendar to = Calendar.getInstance();
		to.setTime(endDateMonth);

		log.info("today: " + today.toString() + " from: " + beforeEndDate5.toString() + " to: " + endDateMonth.toString());

		// 当日が月末日5日前から月末日の間かどうか
		cal.setTime(today);
		if ((cal.compareTo(from) > 0 && cal.compareTo(to) <= 0)) {
			remindDeliveryAcceptanceListAux(deliveryList, workReportList);
		}
	}

	public void remindDeliveryAcceptanceListAux(List<TDeliveryEntity> deliveryList, List<TWorkReportEntity> workReportList) {
		DecimalFormat df = new DecimalFormat("#,###");
		Map<String, String> koujiCodeMap = new HashMap<String, String>();
		List<String> koujiCodeList = new ArrayList<String>();
		Map<String, String> gyousyaCodeMap = new HashMap<String, String>();
		List<String> gyousyaCodeList = new ArrayList<String>();

		Map<String, List<TDeliveryEntity>> koujiDeliveryMap = new HashMap<String, List<TDeliveryEntity>>();
		for (TDeliveryEntity delivery : deliveryList) {
			String koujiCode = delivery.getKoujiCode();
			String gyousyaCode = delivery.getGyousyaCode();

			if (!koujiDeliveryMap.containsKey(koujiCode)) {
				koujiDeliveryMap.put(koujiCode, new ArrayList<TDeliveryEntity>());
				if (!koujiCodeMap.containsKey(koujiCode)) {
					koujiCodeMap.put(koujiCode, koujiCode);
					koujiCodeList.add(koujiCode);
				}
			}
			koujiDeliveryMap.get(koujiCode).add(delivery);
			// 業者コードキャッシュ
			if (!gyousyaCodeMap.containsKey(gyousyaCode)) {
				gyousyaCodeMap.put(gyousyaCode, gyousyaCode);
				gyousyaCodeList.add(gyousyaCode);
			}
		}

		Map<String, List<TWorkReportEntity>> koujiWorkReportMap = new HashMap<String, List<TWorkReportEntity>>();
		for (TWorkReportEntity workReport : workReportList) {
			String koujiCode = workReport.getKoujiCode();
			String gyousyaCode = workReport.getGyousyaCode();
			if (Objects.isNull(koujiCode)) {
				continue;
			}
			if (!koujiWorkReportMap.containsKey(koujiCode)) {
				koujiWorkReportMap.put(koujiCode, new ArrayList<TWorkReportEntity>());
				if (!koujiCodeMap.containsKey(koujiCode)) {
					koujiCodeMap.put(koujiCode, koujiCode);
					koujiCodeList.add(koujiCode);
				}
			}
			koujiWorkReportMap.get(koujiCode).add(workReport);
			// 業者コードキャッシュ
			if (!gyousyaCodeMap.containsKey(gyousyaCode)) {
				gyousyaCodeMap.put(gyousyaCode, gyousyaCode);
				gyousyaCodeList.add(gyousyaCode);
			}
		}

		// 工事情報
		List<MKoujiEntity> koujiList = mKoujiDao.selectListByMultiCode(koujiCodeList);
		Map<String, MKoujiEntity> koujiMap = new HashMap<String, MKoujiEntity>();
		Map<String, String> syainCodeMap = new HashMap<String, String>();
		List<String> syainCodeList = new ArrayList<String>();
		Map<String, String> eigyousyoCodeMap = new HashMap<String, String>();
		List<String> eigyousyoCodeList = new ArrayList<String>();
		for (MKoujiEntity kouji : koujiList) {
			koujiMap.put(kouji.getKoujiCode(), kouji);
			String syainCode = kouji.getTantouSyainCode();
			if (!syainCodeMap.containsKey(syainCode)) {
				syainCodeMap.put(syainCode, syainCode);
				syainCodeList.add(syainCode);
			}
			String eigyousyoCode = kouji.getEigyousyoCode();
			if (!eigyousyoCodeMap.containsKey(eigyousyoCode)) {
				eigyousyoCodeMap.put(eigyousyoCode, eigyousyoCode);
				eigyousyoCodeList.add(eigyousyoCode);
			}
		}
		// 社員情報
		List<MSyainEntity> syainList = mSyainDao.selectListByMultiCode(syainCodeList);
		Map<String, MSyainEntity> syainMap = new HashMap<String, MSyainEntity>();
		for (MSyainEntity syain : syainList) {
			syainMap.put(syain.getSyainCode(), syain);
		}
		// 支店情報
		List<MEigyousyoEntity> eigyousyoList = mEigyousyoDao.selectList(eigyousyoCodeList);
		Map<String, MEigyousyoEntity> eigyousyoMap = new HashMap<String, MEigyousyoEntity>();
		for (MEigyousyoEntity eigyousyo : eigyousyoList) {
			eigyousyoMap.put(eigyousyo.getEigyousyoCode(), eigyousyo);
		}
		// 業者情報
		List<MGyousyaEntity> gyousyaList = mGyousyaDao.selectListByMultiCode(gyousyaCodeList);
		Map<String, MGyousyaEntity> gyousyaMap = new HashMap<String, MGyousyaEntity>();
		for (MGyousyaEntity gyousya : gyousyaList) {
			gyousyaMap.put(gyousya.getGyousyaCode(), gyousya);
		}

		Map<String, Map<String, List<Map<String, String>>>> eigyousyoSyainDWMap = new HashMap<String, Map<String, List<Map<String, String>>>>();
		// 支店コード別社員コード毎納品書リスト
		for (String koujiCode : koujiDeliveryMap.keySet()) {
			MKoujiEntity kouji = koujiMap.get(koujiCode);
			String eigyousyoCode = kouji.getEigyousyoCode();
			String syainCode = kouji.getTantouSyainCode();
			MEigyousyoEntity eigyousyo = eigyousyoMap.get(eigyousyoCode);
			MSyainEntity syain = syainMap.get(syainCode);
			List<TDeliveryEntity> koujiDeliveryList = koujiDeliveryMap.get(koujiCode);
			if (!eigyousyoSyainDWMap.containsKey(eigyousyoCode)) {
				eigyousyoSyainDWMap.put(eigyousyoCode, new HashMap<String, List<Map<String, String>>>());
			}
			Map<String, List<Map<String, String>>> syainDeliveryMap = eigyousyoSyainDWMap.get(eigyousyoCode);
			if (!syainDeliveryMap.containsKey(syainCode)) {
				syainDeliveryMap.put(syainCode, new ArrayList<Map<String, String>>());
			}
			for (TDeliveryEntity delivery : koujiDeliveryList) {
				String gyousyaCode = delivery.getGyousyaCode();
				MGyousyaEntity gyousya = gyousyaMap.get(gyousyaCode);
				Map<String, String> dmap = new HashMap<String, String>();
				dmap.put("gyousyaCode", gyousyaCode);
				dmap.put("gyousyaName", gyousya.getGyousyaName());
				dmap.put("eigyousyoCode", eigyousyoCode);
				dmap.put("eigyousyoName", eigyousyo.getEigyousyoName());
				dmap.put("syainCode", syainCode);
				dmap.put("syainName", syain.getSyainName());
				dmap.put("koujiCode", koujiCode);
				dmap.put("koujiName", kouji.getKoujiName());
				String orderAmount = (Objects.nonNull(delivery.getOrderAmount())) ? df.format(delivery.getOrderAmount()) : "0";
				dmap.put("orderAmount", orderAmount);
				dmap.put("deliveryDate", delivery.getDeliveryDate());
				dmap.put("orderNumber", delivery.getOrderNumber());
				syainDeliveryMap.get(syainCode).add(dmap);
			}
		}
		// 支店コード別社員コード毎出来高報告書リスト
		for (String koujiCode : koujiWorkReportMap.keySet()) {
			MKoujiEntity kouji = koujiMap.get(koujiCode);
			String eigyousyoCode = kouji.getEigyousyoCode();
			String syainCode = kouji.getTantouSyainCode();
			MEigyousyoEntity eigyousyo = eigyousyoMap.get(eigyousyoCode);
			MSyainEntity syain = syainMap.get(syainCode);
			List<TWorkReportEntity> koujiWorkReportList = koujiWorkReportMap.get(koujiCode);
			if (!eigyousyoSyainDWMap.containsKey(eigyousyoCode)) {
				eigyousyoSyainDWMap.put(eigyousyoCode, new HashMap<String, List<Map<String, String>>>());
			}
			Map<String, List<Map<String, String>>> syainDeliveryMap = eigyousyoSyainDWMap.get(eigyousyoCode);
			if (!syainDeliveryMap.containsKey(syainCode)) {
				syainDeliveryMap.put(syainCode, new ArrayList<Map<String, String>>());
			}
			for (TWorkReportEntity workReport : koujiWorkReportList) {
				String gyousyaCode = workReport.getGyousyaCode();
				MGyousyaEntity gyousya = gyousyaMap.get(gyousyaCode);
				Map<String, String> dmap = new HashMap<String, String>();
				dmap.put("gyousyaCode", gyousyaCode);
				dmap.put("gyousyaName", gyousya.getGyousyaName());
				dmap.put("eigyousyoCode", eigyousyoCode);
				dmap.put("eigyousyoName", eigyousyo.getEigyousyoName());
				dmap.put("syainCode", syainCode);
				dmap.put("syainName", syain.getSyainName());
				dmap.put("koujiCode", koujiCode);
				dmap.put("koujiName", kouji.getKoujiName());
				String orderAmount = (Objects.nonNull(workReport.getOrderAmount())) ? df.format(workReport.getOrderAmount()) : "0";
				dmap.put("orderAmount", orderAmount);
				dmap.put("deliveryDate", workReport.getWorkReportDate());
				dmap.put("orderNumber", workReport.getOrderNumber());
				syainDeliveryMap.get(syainCode).add(dmap);
			}
		}
		// メール送信
		for (String eigyousyoCode : eigyousyoSyainDWMap.keySet()) {
			MEigyousyoEntity eigyousyo = eigyousyoMap.get(eigyousyoCode);
			Map<String, List<Map<String, String>>> syainDWMap = eigyousyoSyainDWMap.get(eigyousyoCode);
			for (String syainCode : syainDWMap.keySet()) {
				MSyainEntity syain = syainMap.get(syainCode);
				String to = getMailTo(syain, eigyousyoCode);
				String cc = getMailCc(eigyousyoCode);
				sendMailRemindDeliveryAcceptance(to, cc, eigyousyoCode, eigyousyo.getEigyousyoName(), syainCode, syain.getSyainName(), syainDWMap.get(syainCode));
			}
		}
	}

	public String getMailTo(MSyainEntity syain, String eigyousyoCode) {
		List<String> toList = new ArrayList<String>();
		if (Objects.nonNull(syain) && Objects.nonNull(syain.getSyainCode())) {
			//'990000'ユーザは工務長宛にメール
			if ((Objects.equals(syain.getSyainCode(),TEMPORARY_SYAIN_CODE) || syain.getTaisyokuFlg() == 1) && Objects.nonNull(eigyousyoCode)) {
				List<MSyainEntity> s3SyainList = mSyainDao.selectListBySyokusyu3(eigyousyoCode);
				for (MSyainEntity s3Syain : s3SyainList) {
					if (Objects.nonNull(s3Syain.getSyainMail())) {
						toList.add(s3Syain.getSyainMail());
					}
				}
			} else {
				if (Objects.nonNull(syain.getSyainMail())) {
					toList.add(syain.getSyainMail());
				}
			}
		}
		//to
		String to = "";
		if (!toList.isEmpty()) {
			to = String.join(",", toList);
		}
		return to;
	}

	public String getMailCc(String eigyousyoCode) {
		String cc = "";
		if(!STG_FLG.equals(STG_FLG_ON) && Objects.nonNull(eigyousyoCode)) {
			cc = "jimu-" + eigyousyoCode + "@tamahome.jp";
		}else {
			cc = STG_CC_MAIL;
		}
		return cc;
	}
}
