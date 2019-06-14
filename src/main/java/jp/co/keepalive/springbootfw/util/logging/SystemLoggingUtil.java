/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:SystemLoggingUtil.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/07/24		:成田敦			:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;

/**
 * <b>[概要]</b><br>
 * <p>
 * ログを整形して出力します。
 * </p>
 *
 * @author Atsushi Narita
 */
public class SystemLoggingUtil {

	private static final String DELIM = "\n";

	private static final String CR = "\r";

	private static final String EMPTY = "";

	/**
	 * <p>
	 * スタックトレースを行区切りで出力します。
	 * </p>
	 *
	 * @param LOGGER <code>ロガー</code>
	 * @param th Throwable オブジェクト
	 */
	public static void traceWarn(Log LOGGER, Throwable th){
		StringTokenizer tokenizer = getStackTraceTokens(th);
		while(tokenizer.hasMoreTokens()){
			LOGGER.warn(tokenizer.nextToken().replaceAll(CR, EMPTY));
		}
	}

	/**
	 * <p>
	 * スタックトレースを行区切りで出力します。
	 * </p>
	 *
	 * @param LOGGER <code>ロガー</code>
	 * @param th Throwable オブジェクト
	 */
	public static void traceError(Log LOGGER, Throwable th){
		StringTokenizer tokenizer = getStackTraceTokens(th);
		while(tokenizer.hasMoreTokens()){
			LOGGER.error(tokenizer.nextToken().replaceAll(CR, EMPTY));
		}
	}

	/**
	 * <p>
	 * スタックトレースを行区切りで出力します。
	 * </p>
	 *
	 * @param LOGGER <code>ロガー</code>
	 * @param th Throwable オブジェクト
	 */
	public static void traceFatal(Log LOGGER, Throwable th){
		StringTokenizer tokenizer = getStackTraceTokens(th);
		while(tokenizer.hasMoreTokens()){
			LOGGER.fatal(tokenizer.nextToken().replaceAll(CR, EMPTY));
		}
	}

	/**
	 * <p>
	 * SQLを行区切りで出力します。
	 * </p>
	 *
	 * @param LOGGER <code>ロガー</code>
	 * @param sql SQL
	 */
	public static void dumpSql(Log LOGGER, String sql){
		StringTokenizer tokenizer = getSqlStatementTokens(sql);
		while(tokenizer.hasMoreTokens()){
			LOGGER.debug(tokenizer.nextToken().replaceAll(CR, EMPTY));
		}
	}

	/**
	 * <p>
	 * Throwable オブジェクトを改行で分割したトークンを返します。
	 * </p>
	 *
	 * @param th Throwable オブジェクト
	 * @return トークン
	 */
	public static StringTokenizer getStackTraceTokens(Throwable th){
		StringTokenizer tokenizer = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			th.printStackTrace(ps);
			tokenizer = new StringTokenizer(baos.toString(), DELIM);
			ps.close();
			baos.close();
		} catch (IOException e) {
		}
		return tokenizer;
	}

	public static String getStackTraceString(Throwable th){
	    String ret = null;
	    try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            th.printStackTrace(ps);
            ret = baos.toString();
            ps.close();
            baos.close();
        } catch (IOException e) {
        }
	    return ret;
	}

	/**
	 * <p>
	 * SQL 文字列を改行で分割したトークンを返します。
	 * </p>
	 *
	 * @param sql SQL
	 * @return トークン
	 */
	private static StringTokenizer getSqlStatementTokens(String sql){
		return new StringTokenizer(sql, DELIM);
	}

}
