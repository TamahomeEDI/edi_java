package jp.co.edi_java.app.util.common;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonUtils {

	/**
	 * 日付フォーマットの文字列をDate型に変換する
	 *
	 * @param date String
	 * @param format String
	 * @param loc Locale
	 * @return Date
	 */
	public static Date parseDateStringToDate(String date, String format, Locale loc) {
		Date ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, loc);
			ret = sdf.parse(date);
		} catch (ParseException e) {
			log.info(e.getMessage());
		}
		return ret;
	}

	/**
	 * Date型を文字列の日付フォーマットに変換する
	 *
	 * @param date Date
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String formatDateToString(Date date, String format, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(date);
	}

	/**
	 * 指定月数前の月初を取得
	 *
	 * @param prevcount int
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getPastMonthFirst(int prevcount, String format, String timezone) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;
		if (Objects.isNull(format)) {
			format = "yyyyMMdd";
		}
		if (Objects.isNull(timezone)) {
			timezone = "JST";
		}
		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, startDay);
		// 指定月数前の月を取得
		cal.add(Calendar.MONTH, -prevcount);
		Date pastMonth = cal.getTime();

		// 対象年月の算出
		resultDate = formatDateToString(pastMonth, format, timezone);
		log.info("getPastMonthFirst: " + resultDate);
		return resultDate;
	}

	/**
	 * 指定月数前の月末を取得
	 *
	 * @param prevcount int
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getPastMonthEnd(int prevcount, String format, String timezone) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;
		if (Objects.isNull(format)) {
			format = "yyyyMMdd";
		}
		if (Objects.isNull(timezone)) {
			timezone = "JST";
		}
		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, startDay);
		// 指定月数前の月を取得
		cal.add(Calendar.MONTH, -prevcount);
		int endDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, endDay);
		Date pastMonth = cal.getTime();

		// 対象年月の算出
		resultDate = formatDateToString(pastMonth, format, timezone);
		log.info("getPastMonthEnd: " + resultDate);
		return resultDate;
	}

	/**
	 * 先月月初を取得
	 *
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getLastMonthFirst(String format, String timezone) {
		return getPastMonthFirst(1, format, timezone);
	}

	/**
	 * 先月月初を取得
	 *
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getLastMonthEnd(String format, String timezone) {
		return getPastMonthEnd(1, format, timezone);
	}

	/**
	 * 先月の会計期間の開始日を取得
	 *
	 * @param useMonth String
	 * @param useMonthFormat String
	 * @param useMonthLocale String
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getArchiveDateFromByLastAccountingMonth(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String format = "yyyy-MM-dd HH:mm:ss";
		String timezone = "JST";

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.ENGLISH);
			cal.setTime(nowDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// 指定月は前月をあらわすため翌月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 先月を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -1);
		// 先月2日を取得
		cal.add(Calendar.DATE, 1);
		Date lastMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(lastMonth, format, timezone);
		log.info("getArchiveDateFrom: " + resultDate);
		return resultDate;
	}

	/**
	 * 先月の会計期間の終了日を取得
	 *
	 * @param useMonth String
	 * @param useMonthFormat String
	 * @param useMonthLocale Locale
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getArchiveDateToByLastAccountingMonth(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String format = "yyyy-MM-dd HH:mm:ss";
		String timezone = "JST";

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.ENGLISH);
			cal.setTime(nowDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// 指定月は前月をあらわすため翌月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月1日を取得
		cal.set(Calendar.DATE, startDay);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date thisMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(thisMonth, format, timezone);
		log.info("getArchiveDateTo: " + resultDate);
		return resultDate;
	}
	/**
	 * 先月の月初を取得
	 *
	 * @param useMonth String
	 * @param useMonthFormat String
	 * @param useMonthLocale Locale
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getArchiveDateFromByLastMonth(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String format = "yyyy-MM-dd HH:mm:ss";
		String timezone = "JST";

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.ENGLISH);
			cal.setTime(nowDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// 指定月は前月をあらわすため翌月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 先月を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -1);
		Date lastMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(lastMonth, format, timezone);
		log.info("getArchiveDateFromByLastMonth: " + resultDate);

		return resultDate;
	}

	/**
	 * 先月の月初を取得
	 *
	 * @param useMonth String
	 * @param useMonthFormat String
	 * @param useMonthLocale Locale
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getArchiveDateToByLastMonth(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String format = "yyyy-MM-dd HH:mm:ss";
		String timezone = "JST";

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.ENGLISH);
			cal.setTime(nowDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			// 指定月は前月をあらわすため翌月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月1日を取得
		cal.set(Calendar.DATE, startDay);
		// 前月末を取得
		cal.add(Calendar.MONTH, -1);
		int endDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, endDay);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date lastMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(lastMonth, format, timezone);
		log.info("getArchiveDateToByLastMonth: " + resultDate);

		return resultDate;
	}

	/**
	 * 外部プロセスを実行する
	 *
	 * @param Command String[]
	 * @param runtime Runtime
	 * @param dir File
	 */
	public static void processDone(String[] Command, Runtime runtime, File dir) {
		Process p = null;
        try {
            p = runtime.exec(Command,null,dir);
        } catch (IOException e) {
        	throw new CoreRuntimeException(e.getMessage());
        }
        try {
            p.waitFor(); // プロセスが正常終了するまで待機
        } catch (InterruptedException e) {
        	throw new CoreRuntimeException(e.getMessage());
        }
	}

}
