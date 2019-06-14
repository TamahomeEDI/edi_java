/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:DumpUtil.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/6/28		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <b>[概要]</b><br>
 * <p>
 * オブジェクトをデバッグログにダンプするためのクラスです。
 * オブジェクトの型に合わせて適切な出力方法を選択します。
 * </p>
 *
 * @author Atsushi.N
 */
public class DumpUtil {

	private static final Log LOGGER = LogFactory.getLog(DumpUtil.class);

	/** ログ出力のデフォルト展開レベル */
	private static final int DEFAULT_EXPANDING_LEVEL = 1;

	/** オブジェクトログ出力の展開レベル */
	private static final int OBJECT_EXPANDING_LEVEL = 2;

	/** セッションログ出力の展開レベル */
	private static final int SESSION_EXPANDING_LEVEL = 3;

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
     * <p>
     * オブジェクトの型に合わせたデバッグログへのダンプを行います。
     * </p>
     *
     * @param obj
     *            ダンプ対象オブジェクト
     */
	public static void dump(Object obj) {

		if (!LOGGER.isDebugEnabled()) {
			return;
		} else if (obj instanceof Map) {
			dumpMap((Map) obj);
		} else if (obj instanceof Collection) {
			dumpCollection((Collection) obj);
		} else if (obj instanceof Object[]) {
			dumpArray((Object[]) obj);
		} else if (obj instanceof HttpServletRequest) {
			dumpRequest((HttpServletRequest) obj);
		} else if (obj instanceof HttpSession) {
			dumpSession((HttpSession) obj);
		} else {
			dumpObject(obj);
		}
	}

	/**
     * <p>
     * {@link #dump(Object)} で出力するダンプ文字列を返します。
     * </p>
     *
     * @param obj
     *            ダンプ対象オブジェクト
     */
	public static String getDumpStr(Object obj) {
		ToString ts = new ToString(DEFAULT_EXPANDING_LEVEL);
		if (obj instanceof Throwable) {
			return getStackTraceStr((Throwable) obj);
		}
		return ts.from(obj);
	}

	/**
     * <p>
     * <code>Map</code> のダンプを出力します。
     * </p>
     *
     * @param map
     *            ダンプ出力対象の <code>Map</code>
     */
	private static void dumpMap(Map map) {
		String className = map.getClass().getName();
		ToString ts = new ToString(DEFAULT_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< マップ型(" + className + ")のダンプを開始します。 >>>>>>>>>>");
		outputDebugByLine(ts.from(map));
		LOGGER.debug("<<<<<<<<<< マップ型(" + className + ")のダンプを終了します。 >>>>>>>>>>");
	}

	/**
     * <p>
     * <code>Collection</code> のダンプを出力します。
     * </p>
     *
     * @param list
     *            ダンプ出力対象の <code>Collection</code>
     */
	private static void dumpCollection(Collection collection) {
		String className = collection.getClass().getName();
		ToString ts = new ToString(DEFAULT_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< コレクション型(" + className + ")のダンプを開始します。 >>>>>>>>>>");
		outputDebugByLine(ts.from(collection));
		LOGGER.debug("<<<<<<<<<< コレクション型(" + className + ")のダンプを終了します。 >>>>>>>>>>");
	}

	/**
     * <p>
     * 配列のダンプを出力します。
     * </p>
     *
     * @param array
     *            ダンプ出力対象の配列
     */
	private static void dumpArray(Object[] array) {

		ToString ts = new ToString(DEFAULT_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< 配列のダンプを開始します。 >>>>>>>>>>");
		outputDebugByLine(ts.from(array));
		LOGGER.debug("<<<<<<<<<< 配列のダンプを終了します。 >>>>>>>>>>");
	}

	/**
     * <p>
     * リクエストパラメータのダンプを出力する。
     * </p>
     *
     * @param request
     *            ダンプ出力対象のリクエスト
     */
	private static void dumpRequest(HttpServletRequest request) {

		ToString ts = new ToString(DEFAULT_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< リクエストパラメータのダンプを開始します。 >>>>>>>>>>");
		outputDebugByLine(ts.from(request));
		LOGGER.debug("<<<<<<<<<< リクエストパラメータのダンプを終了します。 >>>>>>>>>>");
	}

	/**
     * <p>
     * リクエストパラメータのダンプを出力する。
     * </p>
     *
     * @param session
     *            ダンプ出力対象のセッション
     */
	private static void dumpSession(HttpSession session) {

		String sessionId = (session == null) ? "null" : session.getId();

		ToString ts = new ToString(SESSION_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< セッション情報のダンプを開始します。[sessionId = " + sessionId + "] >>>>>>>>>>");
		outputDebugByLine(ts.from(session));
		LOGGER.debug("<<<<<<<<<< セッション情報のダンプを終了します。[sessionId = " + sessionId + "] >>>>>>>>>>");
	}

	/**
     * <p>
     * Objectのダンプを出力します。
     * </p>
     *
     * @param obj
     *            ダンプ出力対象のObject
     */
	private static void dumpObject(Object obj) {

		ToString ts = new ToString(OBJECT_EXPANDING_LEVEL);
		LOGGER.debug("<<<<<<<<<< Objectのダンプを開始します。 >>>>>>>>>>");
		outputDebugByLine(ts.from(obj));
		LOGGER.debug("<<<<<<<<<< Objectのダンプを終了します。 >>>>>>>>>>");
	}

	/**
     * <p>
     * 指定された文字列のダンプを行単位で出力する。
     * </p>
     *
     * @param string
     *            ダンプ出力対象の文字列
     */
	private static void outputDebugByLine(String string) {

		String[] strings = string.split(LINE_SEPARATOR);

		for (int i = 0; i < strings.length; i++) {
			LOGGER.debug(strings[i]);
		}

	}

	/**
     * 指定したメッセージに基づいてシステム例外のログを出力します。
     *
     * @param message
     *            システム例外のログメッセージ
     * @param t
     *            例外オブジェクト
     */
	public static void dumpSystemException(Object message, Throwable t) {

		if (t == null) {
			throw new IllegalArgumentException("例外オブジェクトが指定されていません。");
		}
		LOGGER.error(message);
		dumpStackTrace(t);

	}

	/**
     * システム例外のログを出力します。
     *
     * @param t
     *            例外オブジェクト
     */
	public static void dumpSystemException(Throwable t) {

		if (t == null) {
			throw new IllegalArgumentException("例外オブジェクトが指定されていません。");
		}
		LOGGER.error(t.getMessage());
		dumpStackTrace(t);

	}

	/**
     * スタックトレースをログヘッダを付加してWARNレベルで出力します。
     *
     * @param t
     *            例外オブジェクト
     */
	public static void dumpStackTrace(Throwable t) {

		if (!LOGGER.isWarnEnabled()) {
			return;
		}

		String[] stackTraces = getStackTrace(t);
		for (int i = 0; i < stackTraces.length; i++) {
			LOGGER.warn(stackTraces[i]);
		}
	}

	private static String getStackTraceStr(Throwable t) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}

	/**
     * 例外オブジェクトのスタックトレースを行単位に分割して取得します。
     *
     * @param t
     *            例外オブジェクト
     *
     * @return 行単位に分割したスタックトレース
     */
	private static String[] getStackTrace(Throwable t) {
		return getStackTraceStr(t).split(LINE_SEPARATOR);
	}
}