/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:StringUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/06/18	:成田敦			:新規作成
 * 2018/03/13	:内田　薫			:Pitlock側DB分離作業に伴う共通メソッド追加
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.lang;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.date.DateUtils;
import jp.co.keepalive.springbootfw.util.logging.DumpUtil;

/**
 * <b>[概要]</b><br>
 * <p>
 * String 関連のユーティリティクラスです。
 * </p>
 *
 * @author Atsushi Narita
 * @author Kaoru Uchida
 */
public final class StringUtils {

	private static Log LOGGER = LogFactory.getLog(StringUtils.class);

	/**
	 * <p>
	 * 空文字 <code>("")</code>
	 * </p>
	 */
	public static final String EMPTY = "";

	/**
	 * <p>
	 * デリミタ(メッセージ置換文字列の区切り文字)
	 * </p>
	 */
	public static final String DELIMITER_MESSAGE = "||";

	/**
	 * <p>
	 * パディングの最大値
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * <p>
	 * 改行文字<code>("\r")</code>
	 * </p>
	 */
	private static final String CR = "\r";

	/**
	 * <p>
	 * 改行文字<code>("\n")</code>
	 * </p>
	 */
	private static final String LF = "\n";

	/**
	 * <p>
	 * デリミタ<code>(",")</code>
	 * </p>
	 */
	private static final String DELIMITER_COMMA = ",";

	/**
	 * <p>
	 * 半角スペース<code>(" ")</code>
	 * </p>
	 */
	private static final String SPACE = " ";

	/**
	 * Unicodeスペース文字（下表をスペース文字と扱う）
	 * <table border=1>
	 * <tr>
	 * <td>HORIZONTAL TABULATION（水平タブ）</td>
	 * </tr>
	 * <tr>
	 * <td>LINE FEED（改行）</td>
	 * </tr>
	 * <tr>
	 * <td>VERTICAL TABULATION（垂直タブ）</td>
	 * </tr>
	 * <tr>
	 * <td>FORM FEED（改ページ）</td>
	 * </tr>
	 * <tr>
	 * <td>CARRIAGE RETURN（改行）</td>
	 * </tr>
	 * <tr>
	 * <td>FILE SEPARATOR</td>
	 * </tr>
	 * <tr>
	 * <td>GROUP SEPARATOR</td>
	 * </tr>
	 * <tr>
	 * <td>RECORD SEPARATOR</td>
	 * </tr>
	 * <tr>
	 * <td>UNIT SEPARATOR</td>
	 * </tr>
	 * <tr>
	 * <td>SPACE（半角スペース）</td>
	 * </tr>
	 * <tr>
	 * <td>NO-BREAK SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>OGHAM SPACE MARK</td>
	 * </tr>
	 * <tr>
	 * <td>MONGOLIAN VOWEL SEPARATOR</td>
	 * </tr>
	 * <tr>
	 * <td>EN QUAD</td>
	 * </tr>
	 * <tr>
	 * <td>EM QUAD</td>
	 * </tr>
	 * <tr>
	 * <td>EN SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>EM SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>THREE-PER-EM SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>FOUR-PER-EM SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>SIX-PER-EM SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>FIGURE SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>PUNCTUATION SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>THIN SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>HAIR SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>ZERO WIDTH SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>NARROW NO-BREAK SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>MEDIUM MATHEMATICAL SPACE</td>
	 * </tr>
	 * <tr>
	 * <td>IDEOGRAPHIC SPACE（全角スペース）</td>
	 * </tr>
	 * <tr>
	 * <td>ZERO WIDTH NO-BREAK SPACE</td>
	 * </tr>
	 * </table>
	 */
	private static final HashSet<Character> spaces;

	/**
	 * <p>
	 * 全角スペースと半角スペース
	 * </p>
	 */
	private static final String SPACE_ALL = "\u0020\u3000";

	/**
	 * <p>
	 * paddingで使用する文字列配列
	 * </p>
	 */
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	/**
	 * <p>
	 * abbreviateInByte()で使用するデフォルトエンコーディング
	 * </p>
	 */
	private static final String DEFAULT_ENCODING_FOR_ABBREVIATE = "UTF-8";

	/**
	 * <p>
	 * スタティックイニシャライザ.
	 * </p>
	 */
	static {
		PADDING[32] = "                                                                ";
		spaces = new HashSet<Character>();
		spaces.add(new Character('\n')); // LINE FEED（改行）
		spaces.add(new Character('\r')); // CARRIAGE RETURN（改行）
		spaces.add(new Character('\u0009')); // HORIZONTAL TABULATION（水平タブ）
		spaces.add(new Character('\u000B')); // VERTICAL TABULATION（垂直タブ）
		spaces.add(new Character('\u000C')); // FORM FEED（改ページ）
		spaces.add(new Character('\u001C')); // FILE SEPARATOR
		spaces.add(new Character('\u001D')); // GROUP SEPARATOR
		spaces.add(new Character('\u001E')); // RECORD SEPARATOR
		spaces.add(new Character('\u001F')); // UNIT SEPARATOR
		spaces.add(new Character('\u0020')); // SPACE（半角スペース）
		spaces.add(new Character('\u00A0')); // NO-BREAK SPACE
		spaces.add(new Character('\u1680')); // OGHAM SPACE MARK
		spaces.add(new Character('\u180E')); // MONGOLIAN VOWEL SEPARATOR
		spaces.add(new Character('\u2000')); // EN QUAD
		spaces.add(new Character('\u2001')); // EM QUAD
		spaces.add(new Character('\u2002')); // EN SPACE
		spaces.add(new Character('\u2003')); // EM SPACE
		spaces.add(new Character('\u2004')); // THREE-PER-EM SPACE
		spaces.add(new Character('\u2005')); // FOUR-PER-EM SPACE
		spaces.add(new Character('\u2006')); // SIX-PER-EM SPACE
		spaces.add(new Character('\u2007')); // FIGURE SPACE
		spaces.add(new Character('\u2008')); // PUNCTUATION SPACE
		spaces.add(new Character('\u2009')); // THIN SPACE
		spaces.add(new Character('\u200A')); // HAIR SPACE
		spaces.add(new Character('\u200B')); // ZERO WIDTH SPACE
		spaces.add(new Character('\u202F')); // NARROW NO-BREAK SPACE
		spaces.add(new Character('\u205F')); // MEDIUM MATHEMATICAL SPACE
		spaces.add(new Character('\u3000')); // IDEOGRAPHIC SPACE（全角スペース）
		spaces.add(new Character('\uFEFF')); // ZERO WIDTH NO-BREAK SPACE
	}

	/**
	 * <p>
	 * デフォルトコンストラクタ.
	 * <p>
	 */
	private StringUtils() {

		super();
	}

	/**
	 * <p>
	 * 改行文字<code>CR("\r")</code>を除去した文字列を返します. <code>null</code>が指定された場合は<code>null</code>を返却します.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.deleteCR(null)          = null
	 * StringUtils.deleteCR("")            = ""
	 * StringUtils.deleteCR(" \r")         = " "
	 * StringUtils.deleteCR("test\r")      = "test"
	 * StringUtils.deleteCR("abc\rtest\r") = "abctest"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return <code>CR("\r")</code>を除去した文字列
	 */
	public static String deleteCR(String str) {

		if (isNull(str)) {
			return null;
		}
		return CharSetUtils.delete(str, CR);
	}

	/**
	 * <p>
	 * 改行文字<code>LF("\n")</code>を除去した文字列を返します. <code>null</code>が指定された場合は<code>null</code>を返却します.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.deleteLF(null)          = null
	 * StringUtils.deleteLF("")            = ""
	 * StringUtils.deleteCR(" \n")         = " "
	 * StringUtils.deleteLF("test\n")      = "test"
	 * StringUtils.deleteLF("abc\ntest\n") = "abctest"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return <code>LF("\n")</code>を除去した文字列
	 */
	public static String deleteLF(String str) {

		if (isNull(str)) {
			return null;
		}
		return CharSetUtils.delete(str, LF);
	}

	/**
	 * <p>
	 * 改行文字<code>CR("\r")</code>を文字列の最後に追加する. <code>null</code>が指定された場合は<code>null</code>を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.insertCR(null)   = null
	 * StringUtils.insertCR("")     = "\r"
	 * StringUtils.insertCR(" ")    = " \r"
	 * StringUtils.insertCR("test") = "test\r"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return <code>CR("\r")</code>を追加した文字列
	 */
	public static String insertCR(String str) {

		if (isNull(str)) {
			return null;
		}
		str = str + (CR);
		return str;
	}

	/**
	 * <p>
	 * 改行文字<code>LF("\n")</code>を文字列の最後に追加する. <code>null</code>が指定された場合は<code>null</code>を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.insertCR(null)   = null
	 * StringUtils.insertCR("")     = "\n"
	 * StringUtils.insertCR(" ")    = " \n"
	 * StringUtils.insertCR("test") = "test\n"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return <code>LF("\n")</code>を追加した文字列
	 */
	public static String insertLF(String str) {

		if (isNull(str)) {
			return null;
		}
		str = str + (LF);
		return str;
	}

	/**
	 * <p>
	 * 文字列がnullであるか判定する. 空文字<code>("")</code>をnullとみなさない点が {@link #isNullString(String) isNullString(String)} との相違点となる.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isNull(null) = true;
	 * StringUtils.isNull("") = false;
	 * StringUtils.isNull(" ") = false;
	 * StringUtils.isNull("test") = false;
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 *
	 * @return 文字列がnullの場合はtrue
	 */
	public static final boolean isNull(String str) {

		return (str == null) ? true : false;
	}

	/**
	 * <p>
	 * 文字列がnullまたは長さ0であるか判定する. 判定対象文字列にトリムは実行されない. {@link #isNullString(String, boolean) isNullString(String, boolean)} のtrim実行フラグにfalseを指定した場合と同等.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isNullString(null)   = true
	 * StringUtils.isNullString("")     = true
	 * StringUtils.isNullString(" ")    = false
	 * StringUtils.isNullString("test") = false
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 *
	 * @return 文字列がnullまたは長さ0の場合はtrue
	 *
	 * @see #isNullString(String, boolean)
	 */
	public static final boolean isNullString(String str) {

		return isNullString(str, false);
	}

	/**
	 * <p>
	 * 文字列がnullまたは長さ0であるか判定する. トリム実行フラグにtrueを設定した場合は判定対象文字列にトリムをかけた後に判定を行う.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isNullString(null, true)    = true
	 * StringUtils.isNullString(null, false)   = true
	 * StringUtils.isNullString("", true)      = true
	 * StringUtils.isNullString("", false)     = true
	 * StringUtils.isNullString(" ", true)     = true
	 * StringUtils.isNullString(" ", false)    = false
	 * StringUtils.isNullString("test", true)  = false
	 * StringUtils.isNullString("test", false) = false
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 * @param isTrim
	 *            トリム実行フラグ
	 *
	 * @return 文字列がnullまたは長さ0の場合はtrue
	 *
	 * @see #isNullString(String, boolean, boolean)
	 */
	public static final boolean isNullString(String str, boolean isTrim) {

		return isNullString(str, isTrim, false);
	}

	/**
	 * <p>
	 * 文字列がnullまたは長さ0であるか判定する. トリム実行フラグにtrueを設定した場合は判定対象文字列にトリムをかけた後に判定を行う. トリム(全角・半角)フラグにtrueを設定した場合は、トリムの際に全角スペース・半角スペースともに削除する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isNullString(null, true, true)    = true
	 * StringUtils.isNullString("", true, true)      = true
	 * StringUtils.isNullString("", true, false)     = true
	 * StringUtils.isNullString(" ", true, true)     = true
	 * StringUtils.isNullString(" ", true, false)    = true
	 * StringUtils.isNullString("　", true, true)    = true
	 * StringUtils.isNullString("　", true, false)   = false
	 * StringUtils.isNullString("test", true, true)  = false
	 * StringUtils.isNullString("test", true, false) = false
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 * @param isTrim
	 *            トリム実行フラグ
	 * @param isTrimAll
	 *            トリム(全角・半角)フラグ
	 *
	 * @return 文字列がnullまたは長さ0の場合はtrue
	 *
	 * @see #trimAll(String)
	 * @see #trim(String)
	 */
	public static final boolean isNullString(String str, boolean isTrim, boolean isTrimAll) {

		if (isTrim) {
			str = (isTrimAll) ? trimAll(str) : trim(str);
		}

		return (isNull(str) || (str.length() == 0)) ? true : false;
	}

	/**
	 * <p>
	 * 指定文字列が、指定された接頭辞で始まるかどうかを判定する. パラメータのいずれかが<code>null</code>の場合は<code>null</code>を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.startsWith("", "")             = false
	 * StringUtils.startsWith(null, null)         = false
	 * StringUtils.startsWith("test", "testtest") = false
	 * StringUtils.startsWith("test", "t")        = true
	 * StringUtils.startsWith("test", "T")        = false
	 * StringUtils.startsWith("test", "e")        = false
	 * </pre>
	 *
	 * @param str
	 *            指定文字列
	 * @param prefix
	 *            接頭辞
	 *
	 * @return 文字列の接頭辞である場合はtrue
	 */
	public static boolean startsWith(String str, String prefix) {

		if ((isNull(str)) || (isNull(prefix))) {
			return false;
		}
		return (str.length() > prefix.length()) && (str.substring(0, prefix.length()).equals(prefix));
	}

	/**
	 * <p>
	 * 指定文字列内にあるすべてのfromTextをtoTextに置換し、新しい文字列を返す. パラメータのいずれかが<code>null</code>の場合は<code>null</code>を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.replace("", "a", "b")     = ""
	 * StringUtils.replace(null, "a", "b")   = null
	 * StringUtils.replace("test", "t", "")  = "es"
	 * StringUtils.replace("test", "T", "")  = "test"
	 * StringUtils.replace("test", "t", "a") = "aesa"
	 * StringUtils.replace("test", "T", "a") = "test"
	 * </pre>
	 *
	 * @param str
	 *            指定文字列
	 * @param fromText
	 *            置換対象文字列
	 * @param toText
	 *            置換文字列
	 *
	 * @return 置換することで生成された文字列
	 */
	public static final String replace(String str, String fromText, String toText) {

		if ((isNull(str)) || (isNull(fromText)) || (isNull(toText))) {
			return null;
		}

		StringBuffer buf = new StringBuffer(100);
		int pos = 0;
		int pos2 = 0;
		while (true) {
			pos = str.indexOf(fromText, pos2);
			if (pos == 0) {
				buf.append(toText);
				pos2 = fromText.length();
			} else if (pos > 0) {
				buf.append(str.substring(pos2, pos));
				buf.append(toText);
				pos2 = pos + fromText.length();
			} else {
				buf.append(str.substring(pos2));
				break;
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Java変数名の大文字使用法に従って、最初の文字を大文字から小文字に変換する. <code>null</code>もしくは長さが0の文字列が指定された場合はその文字列を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.decapitalize("")     = ""
	 * StringUtils.decapitalize(null)   = null
	 * StringUtils.decapitalize(" ")    = " "
	 * StringUtils.decapitalize("Test") = "test"
	 * StringUtils.decapitalize("test") = "test"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return 小文字に変換された文字列
	 *
	 * @see Character#toLowerCase(char)
	 */
	public static String decapitalize(String str) {

		if (isNullString(str)) {
			return str;
		}
		char chars[] = str.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}

	/**
	 * <p>
	 * Javaクラス名の大文字使用法に従って、最初の文字を小文字から大文字に変換する. <code>null</code>もしくは長さが0の文字列が指定された場合はその文字列を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.decapitalize("")     = ""
	 * StringUtils.decapitalize(null)   = null
	 * StringUtils.decapitalize(" ")    = " "
	 * StringUtils.decapitalize("Test") = "Test"
	 * StringUtils.decapitalize("test") = "Test"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 *
	 * @return 小文字に変換された文字列
	 *
	 * @see Character#toLowerCase(char)
	 */
	public static String capitalize(String str) {

		if (isNullString(str)) {
			return str;
		}
		char chars[] = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	/**
	 * <p>
	 * 文字列の左側から指定文字数分の文字列を返す. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li><code>対象文字列 = null</code>の場合は<code>null</code>を返却する.</li>
	 * <li><code>指定文字数 < 0</code>の場合は<code>""</code>を返却する.</li>
	 * <li><code>対象文字列長 ≦ 指定文字数</code>の場合は<code>対象文字列</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.left(null, 任意の整数)       = null
	 * StringUtils.left(任意の文字列, 負の整数) = ""
	 * StringUtils.left("abc", -1)              = ""
	 * StringUtils.left("", 任意の整数)         = ""
	 * StringUtils.left("", 1)                  = ""
	 * StringUtils.left("abc", 0)               = ""
	 * StringUtils.left("abc", 2)               = "ab"
	 * StringUtils.left("abc", 4)               = "abc"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param len
	 *            指定文字数
	 *
	 * @return 左側から指定文字数の文字列
	 */
	public static String left(String str, int len) {

		if (isNull(str)) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * <p>
	 * 文字列の右側から指定文字数の文字列を返す. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li><code>対象文字列 = null</code>の場合は<code>null</code>を返却する.</li>
	 * <li><code>指定文字数 < 0</code>の場合は<code>""</code>を返却する.</li>
	 * <li><code>対象文字列長 ≦ 指定文字数</code>の場合は<code>対象文字列</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.right(null, 任意の整数)       = null
	 * StringUtils.right(任意の文字列, 負の整数) = ""
	 * StringUtils.right("abc", -1)              = ""
	 * StringUtils.right("", 任意の整数)         = ""
	 * StringUtils.right("", 1)                  = ""
	 * StringUtils.right("abc", 0)               = ""
	 * StringUtils.right("abc", 2)               = "bc"
	 * StringUtils.right("abc", 4)               = "abc"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param len
	 *            指定文字数
	 *
	 * @return 右側から指定文字数の文字列
	 */
	public static String right(String str, int len) {

		if (isNull(str)) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * <p>
	 * 文字列の指定文字位置から指定文字数の文字列を返す. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li><code>指定文字列 = null</code> の場合はnullを返却する.</li>
	 * <li><code>指定文字数 < 0 || 指定文字位置 > 対象文字列長</code> の場合は空文字<code>("")</code> を返却する.</li>
	 * <li><code>指定文字位置 < 0</code> の場合は指定文字位置を0とみなす.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.mid(null, *, *)                             = null
	 * StringUtils.mid(任意の文字列, 任意の文字位置, 負の整数) = ""
	 * StringUtils.mid("abc", "1", -1)                         = ""
	 * StringUtils.mid("", 0, 任意の文字位置)                  = ""
	 * StringUtils.mid("abc", 0, 2)                            = "ab"
	 * StringUtils.mid("abc", 0, 4)                            = "abc"
	 * StringUtils.mid("abc", 2, 4)                            = "c"
	 * StringUtils.mid("abc", 4, 2)                            = ""
	 * StringUtils.mid("abc", -2, 2)                           = "ab"
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param pos
	 *            指定文字位置
	 * @param len
	 *            指定文字数
	 *
	 * @return 指定文字位置から指定文字数の文字列
	 */
	public static String mid(String str, int pos, int len) {

		if (isNull(str)) {
			return null;
		}
		if ((len < 0) || (pos > str.length())) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * <p>
	 * 指定された文字列を指定されたデリミタで区切り配列で返却する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li><code>対象文字列 = null</code> の場合は要素0の配列を返却する.</li>
	 * <li>デリミタが<code>null</code>もしくは長さ0の文字列の場合は対象文字列を未編集で配列に格納して返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.delimitedStringToStringArray(null, 任意のデリミタ) = []
	 * StringUtils.delimitedStringToStringArray("", 任意のデリミタ)   = []
	 * StringUtils.delimitedStringToStringArray("a,b,c", "")          = ["a,b,c"]
	 * StringUtils.delimitedStringToStringArray("a,b,c", ",")         = ["a", "b", "c"]
	 * StringUtils.delimitedStringToStringArray("a*b*c", "*")         = ["a", "b", "c"]
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param delim
	 *            デリミタ
	 *
	 * @return 分割された文字列
	 */
	public static final String[] delimitedStringToStringArray(String str, String delim) {

		if (isNull(str)) {
			return new String[0];
		}
		if (isNullString(delim)) {
			return new String[] { str };
		}

		List<String> result = new ArrayList<String>();
		int pos = 0;
		int delPos = 0;
		while ((delPos = str.indexOf(delim, pos)) != -1) {
			result.add(str.substring(pos, delPos));
			pos = delPos + delim.length();
		}
		if (str.length() > 0 && pos <= str.length()) {
			result.add(str.substring(pos));
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * <p>
	 * 配列に指定文字を付加して文字列を返す. その際に以下のルールを適用する.
	 *
	 * <ul>
	 * <li>指定された配列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>指定されたデリミタが<code>null</code>の場合は空文字<code>("")</code>をデリミタとする.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.join(null, 任意のデリミタ)     = null
	 * StringUtils.join([], 任意のデリミタ)       = ""
	 * StringUtils.join([null], 任意のデリミタ)   = ""
	 * StringUtils.join(["a", "b", "c"], "--")    = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)    = "abc"
	 * StringUtils.join(["a", "b", "c"], "")      = "abc"
	 * StringUtils.join([null, "", "a"], ',')     = ",,a"
	 * </pre>
	 *
	 * @param array
	 *            配列文字
	 * @param delim
	 *            デリミタ
	 *
	 * @return 指定文字を付加した文字列
	 */
	public static String join(Object[] array, String delim) {

		if (array == null) {
			return null;
		}
		if (isNull(delim)) {
			delim = EMPTY;
		}
		int arraySize = array.length;

		int bufSize = ((arraySize == 0) ? 0 : arraySize * (((array[0] == null) ? 16 : array[0].toString().length()) + ((!isNull(delim)) ? delim.length() : 0)));

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(delim);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * 文字列の先頭と最後から空白(半角スペース)を削除する. 対象文字列が<code>null</code>の場合はnullを返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.trim(null)      = null
	 * StringUtils.trim("")        = ""
	 * StringUtils.trim("abc")     = "abc"
	 * StringUtils.trim(" abc ")   = "abc"
	 * StringUtils.trim("  abc  ") = "abc"
	 * StringUtils.trim("  abc")   = "abc"
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 文字列の前後から空白(半角スペース)を削除した文字列
	 *
	 * @see String#trim()
	 */
	public static final String trim(String str) {

		return (isNull(str) ? null : str.trim());
	}

	/**
	 * <p>
	 * 文字列の先頭と最後から空白(半角スペース、全角スペース)を削除する. 対象文字列が<code>null</code>の場合はnullを返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.trimAll(null) = null;
	 * StringUtils.trimAll("") = "";
	 * StringUtils.trimAll("abc") = "abc";
	 * StringUtils.trimAll("abc") = "abc";
	 * StringUtils.trimAll("　  abc 　") = "abc";
	 * StringUtils.trimAll(" 　abc") = "abc";
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 文字列の前後から空白(半角スペース、全角スペース)を削除した文字列
	 */
	public static final String trimAll(String str) {

		if (isNull(str)) {
			return null;
		}

		int begin = getTrimLeftPos(str, SPACE_ALL);
		int end = getTrimRightPos(str, SPACE_ALL) + 1;
		if (begin >= end) {
			return "";
		}
		return str.substring(begin, end);
	}

	/**
	 * <p>
	 * 文字列の先頭から空白を削除する. <code>StringUtils#trimLeft(string, " ")</code>と同様に動作する.<br>
	 * 詳細は {@link #trimLeft(String,String) trimLeft(String,String)} の使用例を参照.
	 * </p>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 対象文字列の先頭から空白を削除した文字列
	 *
	 * @see #trimLeft(String, String)
	 */
	public static final String trimLeft(final String str) {

		return trimLeft(str, SPACE);
	}

	/**
	 * <p>
	 * 指定された文字列の先頭から指定された文字列を削除する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合はnullを返却する.</li>
	 * <li>トリム対象文字列が<code>null</code>の場合は<code>" "</code>とみなす.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.trimLeft("", 任意の文字列) = "";
	 * StringUtils.trimLeft(null, 任意の文字列) = null;
	 * StringUtils.trimLeft("abc", " ") = "abc";
	 * StringUtils.trimLeft(" abc ", " ") = "abc ";
	 * StringUtils.trimLeft(" abc ", "a") = " abc ";
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param trimText
	 *            トリム対象文字列
	 *
	 * @return 対象文字列の先頭からトリム対象文字列を削除した文字列
	 */
	public static final String trimLeft(final String str, String trimText) {

		if (isNull(str)) {
			return null;
		}
		if (isNull(trimText)) {
			trimText = SPACE;
		}
		int pos = getTrimLeftPos(str, trimText);
		return str.substring(pos);
	}

	private static final int getTrimLeftPos(final String str, String trimText) {

		int pos = 0;
		for (; pos < str.length(); pos++) {
			if (trimText.indexOf(str.charAt(pos)) < 0) {
				break;
			}
		}
		return pos;
	}

	/**
	 * <p>
	 * 文字列の最後から空白を削除する. <code>StringUtils#trimRight(string, " ")</code>と同様に動作する.<br>
	 * 詳細は {@link #trimRight(String,String) StringUtils#trimRight(String,String)} の使用例を参照.
	 * </p>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 対象文字列の最後から空白を削除した文字列
	 *
	 * @see #trimRight(String,String)
	 */
	public static final String trimRight(final String str) {

		return trimRight(str, SPACE);
	}

	/**
	 * <p>
	 * 文字列の右側のみ空白を削除する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合はnullを返却する.</li>
	 * <li>トリム対象文字列が<code>null</code>の場合は半角スペース<code>(" ")</code>とみなす.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.trimRight("", 任意の文字列) = "";
	 * StringUtils.trimRight(null, 任意の文字列) = null;
	 * StringUtils.trimRight("abc", " ") = "abc";
	 * StringUtils.trimRight(" abc ", " ") = " abc";
	 * StringUtils.trimRight(" abc ", "c") = " abc ";
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param trimText
	 *            トリム対象文字列
	 *
	 * @return 対象文字列の最後から空白を削除した文字列
	 */
	public static final String trimRight(final String str, String trimText) {

		if (isNull(str)) {
			return null;
		}
		if (isNull(trimText)) {
			trimText = SPACE;
		}
		int pos = getTrimRightPos(str, trimText);
		return str.substring(0, pos + 1);
	}

	private static final int getTrimRightPos(final String str, String trimText) {

		int pos = str.length() - 1;
		for (; pos >= 0; pos--) {
			if (trimText.indexOf(str.charAt(pos)) < 0) {
				break;
			}
		}
		return pos;
	}

	/**
	 * <p>
	 * {@link java.util.StringTokenizer StringTokenizer} を使用して指定文字列を指定デリミタで分割し、文字列配列を生成する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>トリム実行フラグを<code>true</code>にした場合、分割した文字列をトリムして配列に格納する.</li>
	 * <li>空文字無視フラグを<code>true</code>にした場合、分割した文字列が空文字の場合に配列に格納しない.</li>
	 * <li>処理対象文字列が<code>null</code>もしくは空文字<code>("")</code>の場合は要素<code>0</code>のString配列を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.delimitedStringToStringArray(null, ",", true, true)          = []
	 * StringUtils.delimitedStringToStringArray("", ",", true, true)            = []
	 * StringUtils.delimitedStringToStringArray("a,b,c", ",", true, true)       = ["a", "b", "c"]
	 * StringUtils.delimitedStringToStringArray("a, b , ,c", ",", true, true)   = ["a", "b", "c"]
	 * StringUtils.delimitedStringToStringArray("a, b , ,c", ",", true, false)  = ["a", "b", "", "c"]
	 * StringUtils.delimitedStringToStringArray("a, b , ,c", ",", false, true)  = ["a", " b ", "c"]
	 * StringUtils.delimitedStringToStringArray("a, b , ,c", ",", false, false) = ["a", " b ", " ", "c"]
	 * </pre>
	 *
	 * @param str
	 *            処理対象文字列
	 * @param delim
	 *            デリミタ
	 * @param trimTokens
	 *            トリム実行フラグ
	 * @param ignoreEmpty
	 *            空文字無視フラグ
	 *
	 * @return 指定文字列から生成した<code>String</code>配列
	 */
	public static final String[] delimitedStringToStringArray(String str, String delim, boolean trimTokens, boolean ignoreEmpty) {

		String[] strArray = new String[0];

		if ((isNullString(str))) {
			return strArray;
		}

		StringTokenizer st = new StringTokenizer(str, delim);
		List<String> tokens = new ArrayList<String>();

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!(ignoreEmpty && token.length() == 0)) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * <p>
	 * "||"区切り文字列を"||"で分割し、文字列配列を生成する. 対象文字列が<code>null</code>の場合は要素数<code>0</code>の配列を返却する. <code>StringUtils#delimitedStringToStringArray(str, DELIMITER_MESSAGE)</code>と同様に動作する.<br>
	 * 詳細は {@link #delimitedStringToStringArray(String,String) StringUtils.delimitedStringToStringArray(String,String)} を参照.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.messageDelimitedListToStringArray(null)    = []
	 * StringUtils.messageDelimitedListToStringArray("")      = []
	 * StringUtils.messageDelimitedListToStringArray("a||b||c") = ["a", "b", "c"]
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 処理対象文字列をメッセージ区切り文字で分割して生成した文字列配列
	 *
	 * @see #delimitedStringToStringArray(String, String)
	 */
	public static final String[] messageDelimitedStringToStringArray(String str) {

		return delimitedStringToStringArray(str, DELIMITER_MESSAGE);
	}

	/**
	 * <p>
	 * カンマ区切り文字列をカンマで分割し、文字列配列を生成する. 対象文字列が<code>null</code>の場合は要素数<code>0</code>の配列を返却する. <code>StringUtils#delimitedStringToStringArray(string, ",")</code>と同様に動作する.<br>
	 * 詳細は {@link #delimitedStringToStringArray(String,String) StringUtils.delimitedStringToStringArray(String,String)} を参照.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.commaDelimitedListToStringArray(null)    = []
	 * StringUtils.commaDelimitedListToStringArray("")      = []
	 * StringUtils.commaDelimitedListToStringArray("a,b,c") = ["a", "b", "c"]
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 *
	 * @return 処理対象文字列をカンマで分割して生成した文字列配列
	 *
	 * @see #delimitedStringToStringArray(String, String)
	 */
	public static final String[] commaDelimitedStringToStringArray(String str) {

		return delimitedStringToStringArray(str, DELIMITER_COMMA);
	}

	/**
	 * <p>
	 * 対象文字列の前方に指定された追加文字を付加する. 対象文字列が<code>null</code>の場合は<code>null</code>を返却する. <code>StringUtils.padLeft(string, string.length() + 1, 'a')</code>と同様に動作する.<br>
	 * 詳細は {@link #padLeft(String,int,char) StringUtils.padLeft(String,int,char)} の使用例を参照.
	 * </p>
	 *
	 * @param str
	 *            対象文字列
	 * @param padChar
	 *            追加文字
	 *
	 * @return 文字列の前方に指定文字を追加した文字列
	 *
	 * @see #padLeft(String, int, char)
	 */
	public static final String padLeft(String str, char padChar) {

		if (isNull(str)) {
			return null;
		}

		return padLeft(str, str.length() + 1, padChar);
	}

	/**
	 * <p>
	 * 対象文字列の前方に指定された追加文字を付加する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>指定された<code>文字サイズ - 対象文字列長 <= 0</code>の場合は対象文字列を未編集で返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.padLeft(null, 3, 'a') = null;
	 * StringUtils.padLeft("", 3, 'a') = "aaa";
	 * StringUtils.padLeft("test", 3, 'a') = "test";
	 * StringUtils.padLeft("test", 4, 'a') = "test";
	 * StringUtils.padLeft("test", 5, 'a') = "atest";
	 * StringUtils.padLeft("test", 6, 'a') = "aatest";
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param size
	 *            文字サイズ
	 * @param padChar
	 *            追加文字
	 *
	 * @return 文字列の前方に指定文字を追加した文字列
	 */
	public static final String padLeft(String str, int size, char padChar) {

		if (isNull(str)) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > PAD_LIMIT) {
			return padLeft(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	/**
	 * <p>
	 * 対象文字列の前方に指定された追加文字を付加する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>追加文字列が<code>null</code>または長さ<code>0</code>の場合は半角スペース<code>("")</code>とみなす.</li>
	 * <li>指定された<code>文字サイズ - 対象文字列長 <= 0</code>の場合は対象文字列を未編集で返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.padLeft(null, 3, "abc") = null;
	 * StringUtils.padLeft("", 6, "abc") = "abcabc";
	 * StringUtils.padLeft("test", 3, "ab") = "test";
	 * StringUtils.padLeft("test", 4, "ab") = "test";
	 * StringUtils.padLeft("test", 5, "ab") = "atest";
	 * StringUtils.padLeft("test", 6, "ab") = "abtest";
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param size
	 *            文字サイズ
	 * @param padStr
	 *            追加文字列
	 *
	 * @return 文字列の前方に指定文字を追加した文字列
	 */
	public static final String padLeft(String str, int size, String padStr) {

		if (isNull(str)) {
			return null;
		}

		if (isNullString(padStr)) {
			padStr = SPACE;
		}

		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}

		int padLen = padStr.length();
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return padLeft(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * <p>
	 * 指定された回数分、指定された文字を追加する.
	 * </p>
	 *
	 * @param repeat
	 *            追加回数
	 * @param padChar
	 *            追加文字
	 *
	 * @return パディングを実行した文字列
	 *
	 * @throws IndexOutOfBoundsException
	 *             <code>指定された追加回数 < 0<code>の場合
	 */
	private static final String padding(int repeat, char padChar) throws IndexOutOfBoundsException {

		String pad = PADDING[padChar];
		if (isNull(pad)) {
			pad = String.valueOf(padChar);
		}
		while (pad.length() < repeat) {
			pad = pad.concat(pad);
		}
		PADDING[padChar] = pad;
		return pad.substring(0, repeat);
	}

	/**
	 * <p>
	 * 対象文字列の後方に指定された追加文字を付加する. 対象文字列が<code>null</code>もしくは長さ<code>0</code>の場合は<code>null</code>を返却する. <code>StringUtils.padRight(string, string.length() + 1, 'a')</code>と同様に動作する.<br>
	 * 詳細は {@link #padRight(String,int,char) StringUtils.padRight(String,int,char)} の使用例を参照.
	 * </p>
	 *
	 * @param str
	 *            文字列
	 * @param padChar
	 *            追加文字
	 *
	 * @return 文字列の後方に指定文字を追加した文字列
	 *
	 * @see #padRight(String, int, char)
	 */
	public static final String padRight(String str, char padChar) {

		if (isNull(str)) {
			return null;
		}

		return padRight(str, str.length() + 1, padChar);
	}

	/**
	 * <p>
	 * 対象文字列の後方に指定された追加文字を付加する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>指定された<code>文字サイズ - 対象文字列長 <= 0</code>の場合は対象文字列を未編集で返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.padRight(null, 3, 'a') = null;
	 * StringUtils.padRight("", 3, 'a') = "aaa";
	 * StringUtils.padRight("test", 3, 'a') = "test";
	 * StringUtils.padRight("test", 4, 'a') = "test";
	 * StringUtils.padRight("test", 5, 'a') = "testa";
	 * StringUtils.padRight("test", 6, 'a') = "testaa";
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param size
	 *            文字サイズ
	 * @param padChar
	 *            追加文字
	 *
	 * @return 文字列の後方に指定文字を追加した文字列
	 */
	public static final String padRight(String str, int size, char padChar) {

		if (isNull(str)) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > PAD_LIMIT) {
			return padRight(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	/**
	 * <p>
	 * 対象文字列の後方に指定された追加文字を付加する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>追加文字列が<code>null</code>もしくは長さ<code>0</code>の場合は半角スペース<code>("")</code>とみなす.</li>
	 * <li>指定された<code>文字サイズ - 対象文字列長 <= 0</code>の場合は対象文字列を未編集で返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.padLeft(null, 3, "abc") = null;
	 * StringUtils.padLeft("", 6, "abc") = "abcabc";
	 * StringUtils.padLeft("test", 3, "ab") = "test";
	 * StringUtils.padLeft("test", 4, "ab") = "test";
	 * StringUtils.padLeft("test", 5, "ab") = "testa";
	 * StringUtils.padLeft("test", 6, "ab") = "testab";
	 * </pre>
	 *
	 * @param str
	 *            文字列
	 * @param size
	 *            文字サイズ
	 * @param padStr
	 *            追加文字列
	 *
	 * @return 文字列の後方に指定文字を追加した文字列
	 */
	public static final String padRight(String str, int size, String padStr) {

		if (isNull(str)) {
			return null;
		}
		if (isNullString(padStr)) {
			padStr = SPACE;
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return padRight(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * <p>
	 * 2番目の文字列から1番目の文字列との差異を取り出す. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>1番目の文字列が<code>null</code>の場合は2番目の文字列を返却する.</li>
	 * <li>2番目の文字列が<code>null</code>の場合は1番目の文字列を返却する.</li>
	 * <li>2つの文字列が同一の場合は<code>""</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.difference(null, null)       = null
	 * StringUtils.difference("", "")           = ""
	 * StringUtils.difference("", "abc")        = "abc"
	 * StringUtils.difference("abc", "")        = ""
	 * StringUtils.difference("abc", "abc")     = ""
	 * StringUtils.difference("ab", "abxyz")    = "xyz"
	 * StringUtils.difference("abcde", "abxyz") = "xyz"
	 * StringUtils.difference("abcde", "xyz")   = "xyz"
	 * </pre>
	 *
	 * @param str1
	 *            対象文字列
	 * @param str2
	 *            対象文字列
	 *
	 * @return 1番目の文字列に対する2番目の文字列の差異部分
	 */
	public static String difference(String str1, String str2) {

		if (str1 == null) {
			return str2;
		}

		if (str2 == null) {
			return str1;
		}

		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return EMPTY;
		}

		return str2.substring(at);
	}

	/**
	 * <p>
	 * 2つの文字列を比較し、異なる文字が始まる位置を返却する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>2つの文字列が同一の場合は<code>-1</code>を返却する.</li>
	 * <li>2つの文字列のいずれかが<code>null</code>もしくは長さ<code>0</code>の場合は<code>0</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.indexOfDifference(null, null)       = -1
	 * StringUtils.indexOfDifference("", "")           = -1
	 * StringUtils.indexOfDifference("", "abc")        = 0
	 * StringUtils.indexOfDifference("abc", "")        = 0
	 * StringUtils.indexOfDifference("abc", "abc")     = -1
	 * StringUtils.indexOfDifference("ab", "abxyz")    = 2
	 * StringUtils.indexOfDifference("abcde", "abxyz") = 2
	 * StringUtils.indexOfDifference("abcde", "xyz")   = 0
	 * </pre>
	 *
	 * @param str1
	 *            対象文字列
	 * @param str2
	 *            対象文字列
	 *
	 * @return 異なる文字が始まる位置
	 */
	public static final int indexOfDifference(String str1, String str2) {

		if (str1 == null || str2 == null) {
			return 0;
		}

		if (str1.equals(str2)) {
			return -1;
		}

		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}

		if (i < str2.length() || i < str1.length()) {
			return i;
		}

		return -1;
	}

	/**
	 * <p>
	 * 指定された文字列を指定された回数繰り返した文字列を生成する. その際には以下のルールを適用する.
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>もしくは長さ<code>0</code>の場合は<code>null</code>を返却する.</li>
	 * <li>繰返し数が<code>0</code>以下の場合は<code>""</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.repeat(null, 2) = null
	 * StringUtils.repeat("", 0)   = ""
	 * StringUtils.repeat("", 2)   = ""
	 * StringUtils.repeat("a", 3)  = "aaa"
	 * StringUtils.repeat("ab", 2) = "abab"
	 * StringUtils.repeat("a", -2) = ""
	 * </pre>
	 *
	 * @param str
	 *            対象文字列
	 * @param repeat
	 *            繰返し数
	 *
	 * @return 対象文字列を繰り返して生成した文字列
	 */
	public static String repeat(String str, int repeat) {

		if (str == null) {
			return null;
		}

		if (repeat <= 0) {
			return EMPTY;
		}

		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}

		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return padding(repeat, str.charAt(0));
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];
			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}
			return new String(output1);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuffer buf = new StringBuffer(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	/**
	 * <p>
	 * 対象文字列を有効文字数に収まるように省略する. <code>StringUtils.abbreviateInChar(target, maxCharLength, null)</code>と同様に動作する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * abbreviateInChar("abcdefghijk", 10) = "abcdefghij"
	 * </pre>
	 *
	 * @param target
	 *            対象文字列
	 * @param maxCharLength
	 *            有効文字数
	 *
	 * @return 文字列省略処理適用後の文字列
	 *
	 * @see #abbreviateInChar(String, int, String)
	 */
	public static final String abbreviateInChar(final String target, final int maxCharLength) {

		return abbreviateInChar(target, maxCharLength, null);
	}

	/**
	 * <p>
	 * 対象文字列を有効文字数に収まるように省略する.<br>
	 * 接尾辞が指定された場合、接尾辞の文字数も合わせて有効文字数に収まるように省略する.<br>
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>対象文字列が有効文字数以内の場合は、対象文字列をそのまま返却する.</li>
	 * <li>有効文字数が負数の場合は、対象文字列をそのまま返却する.</li>
	 * <li>接尾辞の文字数が有効文字数を超える場合は、対象文字列をそのまま返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * abbreviateInChar( null, 10, "..." )               = null
	 * abbreviateInChar( "", 10, "..." )                 = ""
	 * abbreviateInChar( "abcdefghijk", -10, "..." )     = "abcdefghijk"
	 * abbreviateInChar( "abcdefghijk", 2, "..." )       = "abcdefghijk"  ※suffixが有効バイト数より大きいので未編集
	 * abbreviateInChar( "abcdefghijk", 3, "..." )       = "..."
	 * abbreviateInChar( "abcdefghijk", 4, "..." )       = "a..."
	 * abbreviateInChar( "abcdefghijk", 10, "..." )      = "abcdefg..."
	 * abbreviateInChar( "abcdefghij", 10, "..." )       = "abcdefghij"
	 * abbreviateInChar( "a", 10, "..." )                = "a"
	 * abbreviateInChar( "aＢＣdeＦＧhiＪk", 10, "..." ) = "aＢＣdeＦＧ..."
	 * abbreviateInChar( "aＢＣdeＦＧhiＪ", 10, "..." )  = "aＢＣdeＦＧhiＪ"
	 * </pre>
	 *
	 * @param target
	 *            対象文字列
	 * @param maxCharLength
	 *            有効文字数
	 * @param suffix
	 *            接尾辞（省略記号）
	 *
	 * @return 文字列省略処理適用後の文字列
	 */
	public static final String abbreviateInChar(final String target, final int maxCharLength, final String suffix) {

		if (isNullString(target)) {
			return target;
		}

		if (maxCharLength < 0) {
			return target;
		}

		if (target.length() <= maxCharLength) {
			return target;
		}

		StringBuffer buff = new StringBuffer();

		int lengthIfAdded = 0;
		if (!isNullString(suffix)) {
			lengthIfAdded = suffix.length();
			if (lengthIfAdded > maxCharLength) {
				return target;
			}
		}

		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			lengthIfAdded++;
			if (lengthIfAdded > maxCharLength) {
				break;
			}
			buff.append(c);
		}

		if (!isNullString(suffix)) {
			buff.append(suffix);
		}

		return buff.toString();
	}

	/**
	 * <p>
	 * 対象文字列を有効バイト数に収まるように省略する.<br>
	 * エンコーディングには、<code>"UTF-8"</code>を使用する.<br>
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>対象文字列が有効バイト数以内の場合は、対象文字列をそのまま返却する.</li>
	 * <li>有効文字数が負数の場合は、対象文字列をそのまま返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * abbreviateInByte( null, 10 )              = null
	 * abbreviateInByte( "", 10 )                = ""
	 * abbreviateInByte( "abcdefghijk", -10 )    = "abcdefghijk"
	 * abbreviateInByte( "abcdefghijk", 10 )     = "abcdefghij"
	 * abbreviateInByte( "a", 10 )               = "a"
	 * abbreviateInByte( "aＢＣdeＦghiＪk", 10 ) = "aＢＣdeＦg"
	 * abbreviateInByte( "aＢＣdeＦＧhiＪ", 10 ) = "aＢＣdeＦ"
	 * </pre>
	 *
	 * @param target
	 *            対象文字列
	 * @param maxByteLength
	 *            有効バイト数
	 *
	 * @return 文字列省略処理適用後の文字列.
	 *
	 * @see #abbreviateInByte(String,String,int,String,String)
	 */
	public static final String abbreviateInByte(final String target, final int maxByteLength) {

		return abbreviateInByte(target, DEFAULT_ENCODING_FOR_ABBREVIATE, maxByteLength, null, DEFAULT_ENCODING_FOR_ABBREVIATE);
	}

	/**
	 * <p>
	 * 対象文字列を有効バイト数に収まるように省略する.<br>
	 * 接尾辞が指定された場合、接尾辞のバイト数も合わせて有効バイト数に収まるように省略する.<br>
	 * エンコーディングには、"UTF-8"を使用する.<br>
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>対象文字列が有効バイト数以内の場合は、対象文字列をそのまま返却する.</li>
	 * <li>有効文字数が負数の場合は、対象文字列をそのまま返却する.</li>
	 * <li>接尾辞が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * </ul>
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * abbreviateInByte( null, 10, "...(略)" )              = null
	 * abbreviateInByte( "", 10, "...(略)" )                = ""
	 * abbreviateInByte( "abcdefghijk", -10, "...(略)" )    = "abcdefghijk"
	 * abbreviateInByte( "abcdefghijk", 10, "...(略)" )     = "abc...(略)"
	 * abbreviateInByte( "abcdefghijk", 7, "...(略)" )      = "...(略)"
	 * abbreviateInByte( "abcdefghijk", 6, "...(略)" )      = "abcdefghijk"  ※suffixが有効バイト数より大きいので未編集
	 * abbreviateInByte( "abcdefghij", 10, "...(略)" )      = "abcdefghij"
	 * abbreviateInByte( "a", 10, "...(略)" )               = "a"
	 * abbreviateInByte( "aＢＣdeＦghiＪk", 10, "...(略)" ) = "aＢ...(略)"
	 * abbreviateInByte( "ＡbcＣdeＦＧhi", 10, "...(略)" )  = "Ａb...(略)"
	 * abbreviateInByte( "ＡＢcＣdeＦＧhi", 10, "...(略)" ) = "Ａ...(略)"
	 * </pre>
	 *
	 * @param target
	 *            対象文字列
	 * @param maxByteLength
	 *            有効バイト数
	 * @param suffix
	 *            接尾辞（省略記号）
	 *
	 * @return 文字列省略処理適用後の文字列.
	 *
	 * @see #abbreviateInByte(String, String, int, String, String)
	 */
	public static final String abbreviateInByte(final String target, final int maxByteLength, final String suffix) {

		return abbreviateInByte(target, DEFAULT_ENCODING_FOR_ABBREVIATE, maxByteLength, suffix, DEFAULT_ENCODING_FOR_ABBREVIATE);
	}

	/**
	 * <p>
	 * 対象文字列を有効バイト数に収まるように省略する.<br>
	 * 接尾辞が指定された場合、接尾辞のバイト数も合わせて有効バイト数に収まるように省略する.
	 * </p>
	 *
	 * <ul>
	 * <li>対象文字列が<code>null</code>の場合は<code>null</code>を返却する.</li>
	 * <li>対象文字列が有効バイト数以内の場合は、対象文字列をそのまま返却する.</li>
	 * <li>有効文字数が負数の場合は、対象文字列をそのまま返却する.</li>
	 * <li>接尾辞のバイト数が有効バイト数を超える場合は、対象文字列をそのまま返却する.</li>
	 * </ul>
	 *
	 * <p>
	 * 使用例は {@link #abbreviateInByte(String,int,String) StringUtils.abbreviateInByte(String,int,String)} を参照.
	 * </p>
	 *
	 * @param target
	 *            対象文字列
	 * @param targetEncoding
	 *            対象文字列のエンコーディング（サポートエンコーディング："utf-8"）
	 * @param maxByteLength
	 *            有効バイト数
	 * @param suffix
	 *            接尾辞（省略記号）
	 * @param suffixEncoding
	 *            接尾辞（省略記号）のエンコーディング（サポートエンコーディング："utf-8"）
	 *
	 * @return 文字列省略処理適用後の文字列.
	 */
	public static final String abbreviateInByte(final String target, final String targetEncoding, final int maxByteLength, final String suffix, final String suffixEncoding) {

		if (isNullString(target)) {
			return target;
		}

		if (maxByteLength < 0) {
			return target;
		}

		if (!targetEncoding.equals(DEFAULT_ENCODING_FOR_ABBREVIATE)) {
			throw new RuntimeException("サポート対象のエンコーディングを指定してください.[targetEncoding=" + targetEncoding + "]");
		}

		if (!suffixEncoding.equals(DEFAULT_ENCODING_FOR_ABBREVIATE)) {
			throw new RuntimeException("サポート対象のエンコーディングを指定してください.[suffixEncoding=" + suffixEncoding + "]");
		}

		if (getByteLength(target, targetEncoding) <= maxByteLength) {
			return target;
		}

		StringBuffer buff = new StringBuffer();

		int lengthIfAdded = 0;
		if (!isNullString(suffix)) {
			lengthIfAdded = getByteLength(suffix, suffixEncoding);
			if (lengthIfAdded > maxByteLength) {
				return target;
			}
		}

		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			lengthIfAdded += getByteLength(c, targetEncoding);
			if (lengthIfAdded > maxByteLength) {
				break;
			}
			buff.append(c);
		}

		if (!isNullString(suffix)) {
			buff.append(suffix);
		}

		return buff.toString();
	}

	/**
	 * <p>
	 * 対象文字列のバイト長を返却する.
	 * </p>
	 *
	 * @param str
	 *            対象文字列
	 * @param encodeing
	 *            対象文字列のエンコーディング
	 *
	 * @return 対象文字列のバイト長
	 */
	public static final int getByteLength(String str, String encodeing) {

		try {
			return str.getBytes(encodeing).length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("サポート対象のエンコーディングを指定してください.[encodeing=" + encodeing + "]", e);
		}
	}

	/**
	 * <p>
	 * 対象文字のバイト長を返却する.
	 * </p>
	 *
	 * @param c
	 *            対象文字
	 * @param encodeing
	 *            対象文字のエンコーディング
	 *
	 * @return 対象文字のバイト長
	 */
	static final int getByteLength(char c, String encodeing) {

		try {
			return String.valueOf(c).getBytes(encodeing).length;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("サポート対象のエンコーディングを指定してください.[encodeing=" + encodeing + "]", e);
		}
	}

	/**
	 * <p>
	 * 対象文字列のバイト数を返却する.
	 * </p>
	 * <p>
	 * 第二引数でNULLを無害化するかを選択する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringValidatorUtils.getByteLength("")         = 0
	 * StringValidatorUtils.getByteLength(null)       = nullable:false=NullPointerException, nullable:true=0
	 * StringValidatorUtils.getByteLength(" ")        = 1
	 * StringValidatorUtils.getByteLength("　")       = 2
	 * StringValidatorUtils.getByteLength("２００５") = 8
	 * StringValidatorUtils.getByteLength("2005")     = 4
	 * StringValidatorUtils.getByteLength("かた")     = 4
	 * StringValidatorUtils.getByteLength("カタ")     = 4
	 * StringValidatorUtils.getByteLength("ｶﾅ")       = 2
	 * StringValidatorUtils.getByteLength("AB")       = 2
	 * StringValidatorUtils.getByteLength("ab")       = 2
	 * StringValidatorUtils.getByteLength("aB123")    = 5
	 * StringValidatorUtils.getByteLength("aあカ")    = 5
	 * StringValidatorUtils.getByteLength("書き方")   = 6
	 * </pre>
	 *
	 * @param value
	 *            対象文字列
	 *
	 * @return int バイト数
	 */
	public static int getByteLength(String value, boolean nullable) {

		if (value == null && nullable) {
			value = "";
		}
		if (value == null) {
			throw new NullPointerException("引数がNULLです.");
		}

		int byteLength = 0; // バイト数

		byteLength = value.getBytes().length;

		return byteLength;
	}

	/**
	 * <p>
	 * 対象文字列のバイト数を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringValidatorUtils.getByteLength("")         = 0
	 * StringValidatorUtils.getByteLength(null)       = NullPointerException
	 * StringValidatorUtils.getByteLength(" ")        = 1
	 * StringValidatorUtils.getByteLength("　")       = 2
	 * StringValidatorUtils.getByteLength("２００５") = 8
	 * StringValidatorUtils.getByteLength("2005")     = 4
	 * StringValidatorUtils.getByteLength("かた")     = 4
	 * StringValidatorUtils.getByteLength("カタ")     = 4
	 * StringValidatorUtils.getByteLength("ｶﾅ")       = 2
	 * StringValidatorUtils.getByteLength("AB")       = 2
	 * StringValidatorUtils.getByteLength("ab")       = 2
	 * StringValidatorUtils.getByteLength("aB123")    = 5
	 * StringValidatorUtils.getByteLength("aあカ")    = 5
	 * StringValidatorUtils.getByteLength("書き方")   = 6
	 * </pre>
	 *
	 * @param value
	 *            対象文字列
	 *
	 * @return int バイト数
	 */
	public static int getByteLength(String value) {
		return getByteLength(value, false);
	}

	/**
	 * <p>
	 * 対象文字列の文字数を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringValidatorUtils.getLength("")         = 0
	 * StringValidatorUtils.getLength(null)       = nullable:false=NullPointerException, nullable:true=0
	 * StringValidatorUtils.getLength(" ")        = 1
	 * StringValidatorUtils.getLength("　")       = 1
	 * StringValidatorUtils.getLength("２００５") = 4
	 * StringValidatorUtils.getLength("2005")     = 4
	 * StringValidatorUtils.getLength("かた")     = 2
	 * StringValidatorUtils.getLength("カタ")     = 2
	 * StringValidatorUtils.getLength("ｶﾅ")       = 2
	 * StringValidatorUtils.getLength("AB")       = 2
	 * StringValidatorUtils.getLength("ab")       = 2
	 * StringValidatorUtils.getLength("aB123")    = 5
	 * StringValidatorUtils.getLength("aあカ")    = 3
	 * StringValidatorUtils.getLength("書き方")   = 3
	 * </pre>
	 *
	 * @param value
	 *            対象文字列
	 *
	 * @return int 文字数
	 */
	public static int getLength(String value, boolean nullable) {

		if (value == null && nullable) {
			value = "";
		}
		if (value == null) {
			throw new NullPointerException("引数がNULLです.");
		}

		int length = 0; // 文字数

		length = value.replaceAll("\n", "").replaceAll("\r", "").length();

		return length;
	}

	/**
	 * <p>
	 * 対象文字列の文字数を返却する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringValidatorUtils.getLength("")         = 0
	 * StringValidatorUtils.getLength(null)       = NullPointerException
	 * StringValidatorUtils.getLength(" ")        = 1
	 * StringValidatorUtils.getLength("　")       = 1
	 * StringValidatorUtils.getLength("２００５") = 4
	 * StringValidatorUtils.getLength("2005")     = 4
	 * StringValidatorUtils.getLength("かた")     = 2
	 * StringValidatorUtils.getLength("カタ")     = 2
	 * StringValidatorUtils.getLength("ｶﾅ")       = 2
	 * StringValidatorUtils.getLength("AB")       = 2
	 * StringValidatorUtils.getLength("ab")       = 2
	 * StringValidatorUtils.getLength("aB123")    = 5
	 * StringValidatorUtils.getLength("aあカ")    = 3
	 * StringValidatorUtils.getLength("書き方")   = 3
	 * </pre>
	 *
	 * @param value
	 *            対象文字列
	 *
	 * @return int 文字数
	 */
	public static int getLength(String value) {
		return getLength(value, false);
	}

	/**
	 * <p>
	 * 文字列がメールアドレス形式であるか判定する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isMail("test@test.co.jp") = true;
	 * StringUtils.isMail("test@@test.co.jp") = false;
	 * StringUtils.isMail("test@test..co.jp") = false;
	 * StringUtils.isMail(" ") = false;
	 * StringUtils.isMail(null) = false;
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 *
	 * @return 文字列がメールアドレス形式の場合はtrue
	 */
	public static boolean isMail(String str) {
		if (isNull(str)) {
			return false;
		}
		String ptnStr = "[\\w\\.\\-]+@(?:[\\w\\-]+\\.)+[\\w\\-]+";
		Pattern ptn = Pattern.compile(ptnStr);
		Matcher mc = ptn.matcher(str);
		return !mc.matches();
	}

	/**
	 * <p>
	 * 文字列がフラグ形式であるか判定する.
	 * </p>
	 *
	 * <b>使用例:</b>
	 *
	 * <pre>
	 * StringUtils.isFlg("0") = true;
	 * StringUtils.isFlg("1") = true;
	 * StringUtils.isFlg("2") = false;
	 * StringUtils.isFlg(" ") = false;
	 * StringUtils.isFlg(null) = false;
	 * </pre>
	 *
	 * @param str
	 *            判定文字列
	 *
	 * @return 文字列がメールアドレス形式の場合はtrue
	 */
	public static boolean isFlg(String str) {
		if (isNull(str)) {
			return false;
		}
		return (str.equals("0") || str.equals("1"));
	}

	/**
	 * <p>
	 * 正規表現にマッチするか判定する
	 * </p>
	 *
	 * @param regex
	 *            正規表現
	 * @param str
	 *            判定文字列
	 *
	 * @return 正規表現に一致する場合はtrue
	 */
	public static boolean isMatch(String regex, String str) {
		if (isNull(regex) || isNull(str)) {
			System.out.println("NG");
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	/**
	 * 引数の文字列(Shift_JIS)を、UTF-8にエンコードする。
	 *
	 * @param value
	 *            変換対象の文字列
	 * @return エンコードされた文字列
	 */
	public static String sjisToUtf8(String value) throws UnsupportedEncodingException {
		byte[] srcStream = value.getBytes("SJIS");
		byte[] destStream = (new String(srcStream, "SJIS")).getBytes("UTF-8");
		value = new String(destStream, "UTF-8");
		value = StringUtils.convert(value, "SJIS", "UTF-8");
		return value;
	}

	/**
	 * 引数の文字列(UTF-8)を、Shift_JISにエンコードする。
	 *
	 * @param value
	 *            変換対象の文字列
	 * @return エンコードされた文字列
	 */
	public static String utf8ToSjis(String value) {
		try {
			byte[] srcStream = value.getBytes("UTF-8");
			value = convert(new String(srcStream, "UTF-8"), "UTF-8", "SJIS");
			byte[] destStream = value.getBytes("SJIS");
			value = new String(destStream, "SJIS");
		} catch (Exception e) {
			DumpUtil.dump(e);
			return value;
		}
		return value;
	}

	/**
	 * 文字列がNULL、空、すべて空白属性文字のいずれかならtrueを返す。
	 * <p>
	 * ※対象となる空白文字はUnicodeで空白として扱われる29種類の文字。（{@link #spaces}を参照）
	 * </p>
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return trimBothSide(str).length() == 0;
	}

	/**
	 * 文字列両側の空白文字を削除する
	 * <p>
	 * ※対象となる空白文字はUnicodeで空白として扱われる29種類の文字。（{@link #spaces}を参照）
	 * </p>
	 * <strong>スペースを完全に除去したい場合に使用する</strong>
	 *
	 * @param str
	 * @return
	 */
	public static String trimBothSide(String str) {
		if ((str = trimLeftside(str)) != null && str.length() > 0) {
			return trimRightside(str);
		}
		return str;
	}

	/**
	 * 文字列左側の空白文字を削除する
	 * <p>
	 * ※対象となる空白文字はUnicodeで空白として扱われる29種類の文字。（{@link #spaces}を参照）
	 * </p>
	 * <strong>スペースを完全に除去したい場合に使用する</strong>
	 *
	 * @param str
	 * @return
	 */
	public static String trimLeftside(String str) {
		int len;
		if (str == null || (len = str.length()) == 0) {
			return str;
		}
		int top = -1;
		for (int i = 0; i < len; ++i) {
			if (spaces.contains(str.charAt(i)) == false) {
				top = i;
				break;
			}
		}
		switch (top) {
		case 0:
			return str;
		case -1:
			return "";
		default:
			return str.substring(top);
		}
	}

	/**
	 * 文字列右側の空白文字を削除する
	 * <p>
	 * ※対象となる空白文字はUnicodeで空白として扱われる29種類の文字。（{@link #spaces}を参照）
	 * </p>
	 * <strong>スペースを完全に除去したい場合に使用する</strong>
	 *
	 * @param str
	 * @return
	 */
	public static String trimRightside(String str) {
		int len;
		if (str == null || (len = str.length()) == 0) {
			return str;
		}
		int last = -1;
		for (int i = len - 1; i >= 0; --i) {
			if (spaces.contains(str.charAt(i)) == false) {
				last = i;
				break;
			}
		}
		if (last == len - 1) {
			return str;
		}
		switch (last) {
		case -1:
			return "";
		default:
			return str.substring(0, last + 1);
		}
	}

	/**
	 * 引数の文字列を、エンコードする。
	 *
	 * @param value
	 *            変換対象の文字列
	 * @param src
	 *            変換前の文字コード
	 * @param dest
	 *            変換後の文字コード
	 * @return エンコードされた文字列
	 */
	private static String convert(String value, String src, String dest) throws UnsupportedEncodingException {
		Map<String, String> conversion = createConversionMap(src, dest);
		char oldChar;
		char newChar;
		String key;
		for (Iterator<String> itr = conversion.keySet().iterator(); itr.hasNext();) {
			key = itr.next();
			oldChar = toChar(key);
			newChar = toChar(conversion.get(key));
			value = value.replace(oldChar, newChar);
		}
		return value;
	}

	/**
	 * エンコード情報を作成する
	 *
	 * @param src
	 *            変換前の文字コード
	 * @param dest
	 *            変換後の文字コード
	 * @return エンコードされた文字列
	 */
	private static Map<String, String> createConversionMap(String src, String dest) throws UnsupportedEncodingException {
		Map<String, String> conversion = new HashMap<String, String>();
		if ((src.equals("UTF-8")) && (dest.equals("SJIS"))) {
			// －（全角マイナス）
			conversion.put("U+FF0D", "U+2212");
			// ～（全角チルダ）
			conversion.put("U+FF5E", "U+301C");
			// ￠（セント）
			conversion.put("U+FFE0", "U+00A2");
			// ￡（ポンド）
			conversion.put("U+FFE1", "U+00A3");
			// ￢（ノット）
			conversion.put("U+FFE2", "U+00AC");
			// ―（全角マイナスより少し幅のある文字）
			conversion.put("U+2015", "U+2014");
			// ∥（半角パイプが2つ並んだような文字）
			conversion.put("U+2225", "U+2016");

		} else if ((src.equals("SJIS")) && (dest.equals("UTF-8"))) {
			// －（全角マイナス）
			conversion.put("U+2212", "U+FF0D");
			// ～（全角チルダ）
			conversion.put("U+301C", "U+FF5E");
			// ￠（セント）
			conversion.put("U+00A2", "U+FFE0");
			// ￡（ポンド）
			conversion.put("U+00A3", "U+FFE1");
			// ￢（ノット）
			conversion.put("U+00AC", "U+FFE2");
			// ―（全角マイナスより少し幅のある文字）
			conversion.put("U+2014", "U+2015");
			// ∥（半角パイプが2つ並んだような文字）
			conversion.put("U+2016", "U+2225");

		} else {
			throw new UnsupportedEncodingException("この文字コードはサポートしていません。\n・src=" + src + ",dest=" + dest);
		}
		return conversion;
	}

	/**
	 * 16進表記の文字を取得する。
	 *
	 * @param value
	 *            変換対象の文字列
	 * @return 16進表記の文字
	 */
	private static char toChar(String value) {
		return (char) Integer.parseInt(value.trim().substring("U+".length()), 16);
	}

	/**
	 * Listの中身をデリミタ区切りの文字列で返却する
	 *
	 * @param list
	 *            変換対象のリスト
	 * @return デリミタ区切りの文字列
	 */
	public static String arrayListToDelimitedString(List<String> list, String delimiter) {
		if (list == null || list.size() == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			if (sb.length() > 0) {
				sb.append(delimiter);
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 文字列が下記の場合にtrueを返す。
	 * <ul>
	 * <li>null</li>
	 * <li>文字列長が0</li>
	 * <li>全て空白</li>
	 * </ul>
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		return isNullString(str, true, true);
	}

	/**
	 * 各フィールドの値を区切り文字で連結した文字列を生成する<br/>
	 * ※各フィールドの値はtoString()の値を使用するので、独自の書式で文字列化したい場合は使えない。
	 *
	 * @param thisObj
	 *            対象フィールドを持つオブジェクト
	 * @param fieldNames
	 *            連結対象となるフィールドの名前の配列
	 * @param separater
	 *            区切り文字
	 * @return 連結した文字列
	 */
	public static String generateStringForInfoValue(Object thisObj, String[] fieldNames, String separater) {
		StringBuilder sb = new StringBuilder(300);
		for (int i = 0; i < fieldNames.length; ++i) {
			String fieldName = fieldNames[i];
			try {
				Object objValue = ClassUtils.getConvertedValueWithGetter(thisObj, fieldName, true);
				if (i > 0) {
					sb.append(separater);
				}
				if (objValue != null) {
					sb.append(objValue.toString());
				}
			} catch (Exception e) {
				LOGGER.fatal("指定されたインスタンスフィールドの取得に失敗しました。フィールド名：" + fieldName, e);
				throw new CoreRuntimeException(e);
			}
		}
		return sb.toString();
	}

	/**
	 * 各フィールドの値を区切り文字で連結した文字列を分解して各フィールドに値をセットする<br/>
	 * ※日時関連のクラスは対応していない。
	 *
	 * @param thisObj
	 *            対象フィールドを持つオブジェクト
	 * @param info
	 *            各フィールドの値を垂直線で連結した文字列
	 * @param fieldNames
	 *            設定対象のフィールドの名前の配列
	 * @param separater
	 *            区切り文字
	 */
	public static void setValuesToFieldsFromInfoValue(Object thisObj, String info, String[] fieldNames, String separater) {
		if (info == null || info.length() == 0) {
			return;
		}
		String[] values = info.split(separater);
		for (int i = 0; i < values.length; ++i) {
			String value = values[i];
			String fieldName = fieldNames[i];
			try {
				Field field = ClassUtils.getField(thisObj, fieldName);
				Class<?> fieldType = field.getType();
				Object objValue = null;
				switch (fieldType.getName()) {
				case "java.lang.String":
					objValue = value;
					break;

				case "java.lang.Integer":
				case "int":
					objValue = value == null ? null : Integer.valueOf(value);
					break;

				case "java.lang.Short":
				case "short":
					objValue = value == null ? null : Short.valueOf(value);
					break;

				case "java.lang.Long":
				case "long":
					objValue = value == null ? null : Long.valueOf(value);
					break;

				case "java.lang.Byte":
				case "byte":
					objValue = value == null ? null : Byte.valueOf(value);
					break;

				case "java.lang.Float":
				case "float":
					objValue = value == null ? null : Float.valueOf(value);
					break;

				case "java.lang.Double":
				case "double":
					objValue = value == null ? null : Double.valueOf(value);
					break;

				case "java.lang.Boolean":
				case "boolean":
					objValue = ClassUtils.toBoolean(value);
					break;

				case "java.util.Date":
					objValue = DateUtils.strToDate(value);
					break;

				default:
					LOGGER.error("未対応のフィールドクラスです。クラス：" + fieldType.getName());
					throw new CoreRuntimeException();
				}

				ClassUtils.setValueWithSetter(thisObj, fieldName, fieldType, objValue, null, true);

			} catch (Exception e) {
				LOGGER.fatal("指定されたインスタンスフィールドの取得に失敗しました。フィールド名：" + fieldName, e);
				throw new CoreRuntimeException(e);
			}
		}
	}

	/**
	 * 文字が数字かどうかチェックする。
	 * <p>
	 * ※半角数字、全角数字を数字と判定する。
	 * </p>
	 * ※{@link java.lang.Character#isDigit(char)}は半角数字、全角数字の他にアラビア語の数字、ヒンディー語の数字も数字と判定してしまうため、当メソッドを用意した。
	 *
	 * @param c
	 * @return 数字の場合trueを返す
	 */
	public static boolean isDigit(char c) {
		if ('\u0030' <= c && c <= '\u0039') {
			// ISO-LATIN-1 数字 ('0' - '9')
			return true;
		} else if ('\uFF10' <= c && c <= '\uFF19') {
			// 全角数字
			return true;
		}
		return false;
	}

	/**
	 * 全角数字だったら半角数字に変換する。全角数字以外はそのまま返す。
	 *
	 * @param c
	 * @return 半角数字
	 */
	public static char toHalfWidthDigit(char c) {
		switch (c) {
		case '\uFF10':
			return '\u0030';
		case '\uFF11':
			return '\u0031';
		case '\uFF12':
			return '\u0032';
		case '\uFF13':
			return '\u0033';
		case '\uFF14':
			return '\u0034';
		case '\uFF15':
			return '\u0035';
		case '\uFF16':
			return '\u0036';
		case '\uFF17':
			return '\u0037';
		case '\uFF18':
			return '\u0038';
		case '\uFF19':
			return '\u0039';
		}
		return c;
	}

}