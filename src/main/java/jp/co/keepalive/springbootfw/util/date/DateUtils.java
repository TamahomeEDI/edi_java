/**************************************************************
 * システム名		:KeepAliveフレームワーク
 * サブシステム名	:
 * モジュール名		:CsvUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/23		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;

/**
 * 日時関連のユーティリティクラス
 */
public class DateUtils {

	public static final String DATE_FORMAT_DATE = "yyyy/MM/dd";
	public static final String DATE_FORMAT_TIMESTAMP = "yyyy/MM/dd HH:mm:ss";

	public static String toStr(long time, String format) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.JAPAN);

		return sdf.format(date);
	}

	public static String toStr(Timestamp time, String format) {
		Date date = new Date(time.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.JAPAN);

		return sdf.format(date);
	}

	public static String format(String dateStr, String fromFormat, String toFormat) {
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.JAPAN);

		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new CoreRuntimeException(e);
		}

		SimpleDateFormat sdf2 = new SimpleDateFormat(toFormat);
		return sdf2.format(date);
	}

	public static Date toDate(String dateStr, String format) {
		Date date;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.JAPAN);

		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new CoreRuntimeException(e);
		}
		return date;
	}

	public static Timestamp toTimestamp(String dateStr, String format) {
		Timestamp time;
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.JAPAN);

		try {
			time = new Timestamp(sdf.parse(dateStr).getTime());
		} catch (ParseException e) {
			throw new CoreRuntimeException(e);
		}
		return time;
	}

	public static boolean validatePeriod(Timestamp reserveTime, long limitMills) {
		long now = System.currentTimeMillis();
		long insertDateTime = reserveTime.getTime();

		if (insertDateTime + limitMills < now) {
			return false;
		}

		return true;
	}

	/** 対応する日時書式文字列 */
	private final static List<String> dateFormats;

	/** 対応する日時書式（dateFormatsと1：1で対応させている） */
	private final static List<SimpleDateFormat> sdfs;

	/**
	 *
	 */
	static {
		/*
		 * 対応する日時書式を下記で追加しておく
		 */
		dateFormats = new ArrayList<String>();
		dateFormats.add("yyyy/MM/dd");
		dateFormats.add("yyyy-MM-dd");
		dateFormats.add("yyyyMMdd");
		dateFormats.add("yyyy/MM/dd HH:mm:ss");
		dateFormats.add("yyyy-MM-dd HH:mm:ss");
		dateFormats.add("yyyyMMdd HH:mm:ss");
		dateFormats.add("yyyyMMddHHmmss");
		dateFormats.add("yyyy/MM/dd HH:mm");
		dateFormats.add("yyyy-MM-dd HH:mm");
		dateFormats.add("yyyyMMdd HH:mm");
		dateFormats.add("yyyy/MM/dd HHmm");
		dateFormats.add("yyyy-MM-dd HHmm");
		dateFormats.add("yyyyMMdd HHmm");
		dateFormats.add("yyyyMMddHHmm");

		sdfs = new ArrayList<SimpleDateFormat>();
		for (String format : dateFormats) {
			sdfs.add(new SimpleDateFormat(format));
		}
	}

	/**
	 * 文字列をDate型に変換する。
	 * <p>
	 * 変換に使用する日時書式については{@link DateUtils#strToDate(String, String)}を参照。
	 * </p>
	 * ※当メソッドはformatにnullを渡して{@link DateUtils#strToDate(String, String)}を実行している。
	 *
	 * @param value
	 *            文字列値
	 * @return 変換後のDateオブジェクト
	 */
	public static Date strToDate(String value) {
		return strToDate(value, null);
	}

	/**
	 * 文字列をDate型に変換する。
	 * <p>
	 * formatに日時書式が渡されている場合は、この書式で変換を試みる。失敗したらnullを返す。<br/>
	 * formatがnullの場合は下記の書式で順次、変換を試みる。この場合、最初に変換に成功した書式で変換した結果を返す。全部の書式で失敗したらnullを返す。
	 * </p>
	 * <dd>
	 * <dt>対応している書式</dt>
	 * <dl>
	 * <ul>
	 * <li>"yyyy/MM/dd"</li>
	 * <li>"yyyy-MM-dd"</li>
	 * <li>"yyyyMMdd"</li>
	 * <li>"yyyy/MM/dd HH:mm:ss"</li>
	 * <li>"yyyy-MM-dd HH:mm:ss"</li>
	 * <li>"yyyyMMdd HH:mm:ss"</li>
	 * <li>"yyyyMMddHHmmss"</li>
	 * <li>"yyyy/MM/dd HH:mm"</li>
	 * <li>"yyyy-MM-dd HH:mm"</li>
	 * <li>"yyyyMMdd HH:mm"</li>
	 * <li>"yyyy/MM/dd HHmm"</li>
	 * <li>"yyyy-MM-dd HHmm"</li>
	 * <li>"yyyyMMdd HHmm"</li>
	 * <li>"yyyyMMddHHmm"</li>
	 * </ul>
	 * </dl>
	 * </dd>
	 *
	 * @param value
	 *            文字列値
	 * @param format
	 *            日時書式（nullの場合は、内部で対応している書式で変換を試みる）
	 * @return 変換後のDateオブジェクト
	 */
	public static Date strToDate(String value, String format) {
		if (StringUtils.isEmpty(format)) {
			for (int i = 0; i < dateFormats.size(); ++i) {
				String dateFormat = dateFormats.get(i);
				if (dateFormat.length() != value.length()) {
					continue;
				}
				try {
					return sdfs.get(i).parse(value);
				} catch (ParseException pe) {
					continue;
				}
			}
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(value);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * 日時オブジェクトの年月日を"yyyyMMdd"形式の文字列に変換して返す
	 *
	 * @param date
	 * @return "yyyyMMdd"形式の文字列
	 */
	private static String getDate(Calendar date) {
		StringBuffer sb = new StringBuffer();
		sb.append(date.get(Calendar.YEAR));
		sb.append(String.format("%02d", date.get(Calendar.MONTH) + 1));
		sb.append(String.format("%02d", date.get(Calendar.DATE)));
		return sb.toString();
	}

	/**
	 * 文字列をsql.Date型に変換する。
	 *
	 * @param strDate
	 *            文字列日付
	 * @return sql.Date
	 */
	public static java.sql.Date strToSqlDate(String strDate) {
		Date utilDate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		try {
			utilDate = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new CoreRuntimeException(e);
		}

//		Date utilDate = toDate(strDate, "yyyy-MM-dd HH:mm:ss");
		return new java.sql.Date(utilDate.getTime());
	}
}
