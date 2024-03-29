package jp.co.edi_java.app.util.common;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
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
	 * 先月月末を取得
	 *
	 * @param format String
	 * @param timezone String
	 * @return String
	 */
	public static String getLastMonthEnd(String format, String timezone) {
		return getPastMonthEnd(1, format, timezone);
	}

	/**
	 * 指定月数前の1日を取得
	 *
	 * @param prevcount int
	 * @return Date
	 */
	public static Date getPastMonthDateFrom(int prevcount) {
		Calendar cal = Calendar.getInstance();
		Date resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月から指定月数前を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -prevcount);

		resultDate = cal.getTime();
		return resultDate;
	}

	/**
	 * 指定月数前の月末日を取得
	 *
	 * @param prevcount int
	 * @return Date
	 */
	public static Date getPastMonthDateTo(int prevcount) {
		Calendar cal = Calendar.getInstance();
		Date resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月から指定月数前を取得し、その月末日を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -prevcount);
		int endDay = cal.getActualMaximum(Calendar.DATE);
		cal.set(Calendar.DATE, endDay);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		resultDate = cal.getTime();
		return resultDate;
	}

	/**
	 * 指定月数前の会計期間の開始日を取得
	 *
	 * @param prevcount int
	 * @return Date
	 */
	public static Date getPastAccountingMonthDateFrom(int prevcount) {
		Calendar cal = Calendar.getInstance();
		Date resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月から指定月数前を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -prevcount);
		// 先月2日を取得
		cal.add(Calendar.DATE, 1);

		resultDate = cal.getTime();
		return resultDate;
	}

	/**
	 * 指定月数前の会計期間の終了日を取得
	 *
	 * @param prevcount int
	 * @return Date
	 */
	public static Date getPastAccountingMonthDateTo(int prevcount) {
		Calendar cal = Calendar.getInstance();
		Date resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月から指定月数前を取得し、その翌月1日を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -prevcount+1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		resultDate = cal.getTime();
		return resultDate;
	}

	/**
	 * アーカイブ処理用 先月の会計期間の開始日を取得
	 *
	 * @param useMonth String
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
	 * アーカイブ処理用 先月の会計期間の終了日を取得
	 *
	 * @param useMonth String
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
	 * アーカイブ処理用 先月の月初を取得
	 *
	 * @param useMonth String
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
	 * アーカイブ処理用 先月の月末を取得
	 *
	 * @param useMonth String
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
	 * @param dir File
	 * @param long timeOutSec
	 */
	public static void processDone(String[] Command,  File dir, long timeOutSec, Locale locale) {
		boolean isFinished = false;
		// 1回リトライ
		for (int i = 0; i<2; i++) {
			isFinished = processDoneAux(Command, dir, timeOutSec, locale);
			if (isFinished) {
				break;
			}
		}
	}

	/**
	 * 外部プロセスを実行する
	 *
	 * @param Command String[]
	 * @param dir File
	 * @param long timeOutSec
	 */
	public static boolean processDoneAux(String[] Command, File dir, long timeOutSec, Locale locale) {
		List<String> cmdArray = Arrays.asList(Command);
		log.info("process call: " + String.join(" ", cmdArray));
		ProcessBuilder builder = new ProcessBuilder(cmdArray);
		// 作業ディレクトリ
		builder.directory(dir);
		// エラーを標準出力ストリームへ統合
		builder.redirectErrorStream(true);
		// 標準ログへ出力
		builder.inheritIO();

		if (Objects.nonNull(locale)) {
			Map<String, String> envMap = builder.environment();
			if (Objects.equals(locale, Locale.JAPAN)) {
				envMap.put("LANG", "ja_JP.UTF-8");
				envMap.put("LC_CTYPE", "ja_JP.UTF-8");
			} else if (Objects.equals(locale, Locale.ENGLISH)) {
				envMap.put("LANG", "C");
			}
		}

		Process p;
		boolean isFinished = false;
        try {
            p = builder.start();
        } catch (IOException e) {
        	throw new CoreRuntimeException(e.getMessage());
        }

        try {
        	// 標準ログへ出力するように変更したためコメントアウト
//        	new Thread(() -> {
//        		try (InputStream is = p.getInputStream()) {
//        			while (is.read() >= 0);
//        		} catch (IOException e) {
//        			throw new UncheckedIOException(e);
//        		}
//        	} ).start();

        	isFinished = p.waitFor(timeOutSec, TimeUnit.SECONDS); // プロセスが正常終了するまで待機

        	if (!isFinished) {
        		log.info("can not finish: " + ArrayUtils.toString(cmdArray));
        	}
        } catch (InterruptedException e) {
        	throw new CoreRuntimeException(e.getMessage());
        } finally {
        	if (p.isAlive()) {
        		p.destroy();
        	}
        }
        return isFinished;
	}

}
