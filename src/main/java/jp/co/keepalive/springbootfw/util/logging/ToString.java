/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:ToString.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/08/05		:成田　敦	:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.logging;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.keepalive.springbootfw.util.dxo.BeanUtils;
import jp.co.keepalive.springbootfw.util.lang.ClassNameUtil;

/**
 * <b>[概要]</b><br>
 * <p>
 * オブジェクトを文字列表現に変換します。
 * </p>
 *
 * @author atsushi.narita
 */
public class ToString {

	/** ロガークラス */
	private final Log log = LogFactory.getLog(getClass());

	/** インデントに用いる文字列 */
	private static final String INDENT_STR = "\t";

	/** 展開レベル */
	private final int expandingLevel;

	/** ネストレベル */
	private int nestLevel = 0;

	/** 文字列表現出力ストリーム */
	private StringPrintWriter spw;

	private Map<String, Object> cyclicRefCheckMap = new HashMap<String, Object>();

	/**
     * <p>
     * <code>ToString</code>オブジェクトを構築します。
     * </p>
     * <p>
     * 展開レベルには 規定値<code>0</code> が設定されます。
     */
	public ToString() {

		this.expandingLevel = 0;
	}

	/**
     * <p>
     * 展開レベルをもとに、 <code>ToString</code> オブジェクトを構築します。
     * </p>
     * <p>
     * 展開レベルを指定すると、指定した値と同じネストレベルまで オブジェクトの内容を複数行に展開します。
     * </p>
     * <p>
     * <b>展開する場合（展開レベル1）:</b>
     * </p>
     *
     * <pre>
     *  	jp.co.asahilife.khfw.mock.web.MockHttpServletRequest
     *  	{
     *  		Test2=[value2.1, value2.2]
     *  		Test1=value1
     *    }
     * </pre>
     *
     * <p>
     * <b>展開しない場合（展開レベル0）:</b>
     * </p>
     *
     * <pre>
     *   	{Test2=[value2.1, value2.2], Test1=value1}
     * </pre>
     *
     * @param expandingLevel
     *            展開レベル
     */
	public ToString(int expandingLevel) {

		this.expandingLevel = expandingLevel;
	}

	/**
     * <p>
     * 指定されたオブジェクトを文字列表現に変換して返却します。
     * </p>
     *
     * @param obj
     *            変換対象のオブジェクト
     *
     * @return 変換後の文字列表現。変換中に障害が発生した場合は <code>null</code>
     */
	public String from(Object obj) {

		spw = new StringPrintWriter();
		try {
			convert(obj);
			return spw.toString();
		} finally {
			spw.close();
		}
	}

	/**
     * <p>
     * 指定されたオブジェクトを指定された展開レベルで文字列表現に変換して返却します。
     * </p>
     *
     * @param obj
     *            変換対象のオブジェクト
     * @param expandingLevel
     *            展開レベル
     *
     * @return 変換後の文字列表現。変換中に障害が発生した場合は <code>null</code>
     *
     * @see ToString#from(Object)
     */
	public static String from(Object obj, int expandingLevel) {

		ToString toString = new ToString(expandingLevel);
		return toString.from(obj);
	}

	/**
     * <p>
     * オブジェクトを文字列表現に変換します。
     * </p>
     * <p>
     * オブジェクトの型に適合するメソッドにディスパッチします。
     * </p>
     *
     * @param obj
     *            変換対象のオブジェクト
     */
	private void convert(Object obj) {

		if (obj == null) {
			convertNull();
		} else if (obj instanceof Properties) {
			convertMap((Map) obj);
		} else if (obj instanceof Map) {
			convertMap((Map) obj);
		} else if (obj instanceof Collection) {
			convertCollection((Collection) obj);
		} else if (obj.getClass().isArray()) {
			convertArray(obj);
		} else if (obj instanceof HttpServletRequest) {
			convertHttpServletRequest((HttpServletRequest) obj);
		} else if (obj instanceof HttpSession) {
			convertHttpSession((HttpSession) obj);
		} else if (obj instanceof Date) {
			convertDate((Date) obj);
		} else if (obj instanceof InputStream) {
			convertInputStream();
		} else if (BeanUtils.isJavaBeans(obj)) {
			convertBean(obj);
		} else if (obj instanceof String) {
			convertObject(obj);
		} else if (obj instanceof Number) {
			convertObject(obj);
		} else if (obj instanceof Boolean) {
			convertObject(obj);
		} else if (obj instanceof Date) {
			convertObject(obj);
		} else if (obj instanceof Throwable) {
			convertObject(obj);
		} else if (obj.getClass().isArray()) {
			convertObject(obj);
		} else {
			spw.print(ClassNameUtil.getFullQueryClassName(obj) + "は不完全なJavaBeans、またはダンプ不可能なオブジェクトです.");
		}
	}

	/**
     * <p>
     * <code>null</code> を文字列表現に変換します。
     * </p>
     *
     * @param map
     *            変換対象の <code>Map</code> オブジェクト
     */
	private void convertNull() {

		spw.print("<null>");
	}

	/**
     * <p>
     * <code>Map</code> オブジェクトを文字列表現に変換します。
     * </p>
     *
     * @param map
     *            変換対象の <code>Map</code> オブジェクト
     */
	private void convertMap(Map map) {

		if (isExpanding()) {
			spw.println(getClassName(map));
			convertMapMultiLine(map);
		} else {
			convertMapOneLine(map);
		}
	}

	/**
     * <p>
     * <code>Map</code> オブジェクトを複数行の文字列表現に変換します。
     * </p>
     *
     * @param map
     *            変換対象の<code>Map</code>オブジェクト
     */
	private void convertMapMultiLine(Map map) {

		Set entrySet = map.entrySet();
		Iterator it = entrySet.iterator();
		spw.print(getIndent());
		spw.println("{");
		nest();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			spw.print(getIndent());
			convert(entry.getKey());
			spw.print("=");
			convert(entry.getValue());
			spw.println();
		}
		up();
		spw.print(getIndent());
		spw.print("}");
	}

	/**
     * <p>
     * <code>Map</code> オブジェクトを１行の文字列表現に変換します。
     * </p>
     *
     * @param map
     *            変換対象の <code>Map</code> オブジェクト
     */
	private void convertMapOneLine(Map map) {

		Set entrySet = map.entrySet();
		Iterator it = entrySet.iterator();
		String delim = "";
		spw.print("{");
		nest();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			spw.print(delim);
			if (isCyclicRef(entry.getKey())) {
				spw.print(entry.getKey() + "で循環参照を検知しました.");
				cyclicRefCheckMap.clear();
				continue;
			}
			convert(entry.getKey());
			spw.print("=");
			convert(entry.getValue());
			delim = ", ";
		}
		cyclicRefCheckMap.clear();
		up();
		spw.print("}");
	}

	private boolean isCyclicRef(Object obj) {
		Object tmp = cyclicRefCheckMap.get(obj.toString());
		if (tmp != null) {
			return true;
		}
		cyclicRefCheckMap.put(obj.toString(), obj);
		return false;
	}

	/**
     * <p>
     * <code>Collection</code> オブジェクトを文字列表現に変換します。
     * </p>
     *
     * @param col
     *            変換対象の <code>Collection</code> オブジェクト
     */
	private void convertCollection(Collection col) {

		if (isExpanding()) {
			spw.println(getClassName(col));
			convertCollectionMultiLine(col);
		} else {
			convertCollectionOneLine(col);
		}
	}

	/**
     * <p>
     * <code>Collection</code> オブジェクトを複数行の文字列表現に変換します。
     * </p>
     *
     * @param col
     *            変換対象の <code>Collection</code> オブジェクト
     */
	private void convertCollectionMultiLine(Collection col) {

		Iterator it = col.iterator();
		spw.print(getIndent());
		spw.println("[");
		nest();
		while (it.hasNext()) {
			spw.print(getIndent());
			convert(it.next());
			spw.println();
		}
		up();
		spw.print(getIndent());
		spw.print("]");
	}

	/**
     * <p>
     * <code>Collection</code> オブジェクトを１行の文字列表現に変換します。
     * </p>
     *
     * @param col
     *            変換対象の <code>Collection</code> オブジェクト
     */
	private void convertCollectionOneLine(Collection col) {

		Iterator it = col.iterator();
		String delim = "";
		spw.print("[");
		nest();
		while (it.hasNext()) {
			spw.print(delim);
			convert(it.next());
			delim = ", ";
		}
		up();
		spw.print("]");
	}

	/**
     * <p>
     * 配列を文字列表現に変換します。
     * </p>
     *
     * @param array
     *            変換対象の配列
     */
	private void convertArray(Object array) {

		if (isExpanding()) {
			spw.println(getClassName(array));
			convertArrayMultiLine(array);
		} else {
			convertArrayOneLine(array);
		}
	}

	/**
     * <p>
     * 配列を複数行の文字列表現に変換します。
     * </p>
     *
     * @param array
     *            変換対象の配列
     */
	private void convertArrayMultiLine(Object array) {

		spw.print(getIndent());
		spw.println("[");
		nest();
		for (int i = 0, end = Array.getLength(array); i < end; ++i) {
			spw.print(getIndent());
			convert(Array.get(array, i));
			spw.println();
		}
		up();
		spw.print(getIndent());
		spw.print("]");
	}

	/**
     * <p>
     * 配列を１行の文字列表現に変換します。
     * </p>
     *
     * @param array
     *            変換対象の配列
     */
	private void convertArrayOneLine(Object array) {

		String delim = "";
		spw.print("[");
		nest();
		for (int i = 0, end = Array.getLength(array); i < end; ++i) {
			spw.print(delim);
			convert(Array.get(array, i));
			delim = ", ";
		}
		up();
		spw.print("]");
	}

	/**
     * <p>
     * <code>HttpServletRequest</code> が持つリクエストパラメータを文字列表現に変換します。
     * </p>
     *
     * @param request
     *            変換対象の <code>HttpServletRequest</code> オブジェクト
     */
	private void convertHttpServletRequest(HttpServletRequest request) {

		Map map = request.getParameterMap();
		if (isExpanding()) {
			spw.println(getClassName(request));
			convertMapMultiLine(map);
		} else {
			convertMapOneLine(map);
		}
	}

	/**
     * <p>
     * <code>HttpSession</code> がもつアトリビュートを文字列表現に変換します。
     * </p>
     *
     * @param session
     *            変換対象の <code>HttpSession</code> オブジェクト
     */
	private void convertHttpSession(HttpSession session) {

		if (isExpanding()) {
			convertHttpSessionMultiLine(session);
		} else {
			convertHttpSessionOneLine(session);
		}
	}

	/**
     * <p>
     * <code>HttpSession</code> が持つアトリビュートを複数行の文字列表現に変換します。
     * </p>
     *
     * @param session
     *            変換対象の <code>HttpSession</code> オブジェクト
     */
	private void convertHttpSessionMultiLine(HttpSession session) {

		Enumeration attributeNames = session.getAttributeNames();
		spw.println(getClassName(session));
		spw.print(getIndent());
		spw.println("{");
		nest();
		while (attributeNames.hasMoreElements()) {
			String name = (String) attributeNames.nextElement();
			spw.print(getIndent());
			convert(name);
			spw.print("=");
			convert(session.getAttribute(name));
			spw.println();
		}
		up();
		spw.print(getIndent());
		spw.print("}");
	}

	/**
     * <p>
     * <code>HttpSession</code> が持つアトリビュートを1行の文字列表現に変換します。
     * </p>
     *
     * @param session
     *            変換対象の <code>HttpSession</code> オブジェクト
     */
	private void convertHttpSessionOneLine(HttpSession session) {

		Enumeration attributeNames = session.getAttributeNames();
		String delim = "";
		spw.print("{");
		nest();
		while (attributeNames.hasMoreElements()) {
			String name = (String) attributeNames.nextElement();
			spw.print(delim);
			convert(name);
			spw.print("=");
			convert(session.getAttribute(name));
			delim = ", ";
		}
		up();
		spw.print("}");
	}

	/**
     * <p>
     * JavaBeanオブジェクトを文字列表現に変換します。
     * </p>
     *
     * @param bean
     *            変換対象のJavaBeanオブジェクト
     */
	private void convertBean(Object bean) {

		Map map;

		try {
			map = PropertyUtils.describe(bean);
		} catch (IllegalAccessException iae) {
			log.warn("オブジェクトのプロパティ取得に失敗しました。");
			DumpUtil.dumpStackTrace(iae);
			spw.print("<error>");
			return;
		} catch (InvocationTargetException ite) {
			log.warn("オブジェクトのプロパティ取得に失敗しました。");
			DumpUtil.dumpStackTrace(ite);
			spw.print("<error>");
			return;
		} catch (NoSuchMethodException nsme) {
			log.warn("オブジェクトのプロパティ取得に失敗しました。");
			DumpUtil.dumpStackTrace(nsme);
			spw.print("<error>");
			return;
		}

		map.remove("class");

		if (isExpanding()) {
			spw.println(getClassName(bean));
			convertMapMultiLine(map);
		} else {
			convertMapOneLine(map);
		}
	}

	/**
     * <p>
     * <code>Date</code> オブジェクトを <code>yyyy/MM/dd/HH/mm/ss/SSS</code>
     * 形式で文字列表現に変換します。
     * </p>
     *
     * @param bean
     *            変換対象の <code>Date</code> オブジェクト
     */
	private void convertDate(Date date) {

		DateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSS");
		spw.print(format.format(date));
	}

	/**
     * <p>
     * <code>InputStream</date> オブジェクトを文字列表現に変換します。
	 * </p>
	 *
     */
	private void convertInputStream() {

		spw.print("<InputStream>");
	}

	/**
     * <p>
     * オブジェクトを文字列表現に変換します。
     * </p>
     *
     * @param obj
     *            変換対象のオブジェクト
     */
	private void convertObject(Object obj) {
		spw.print(obj.toString());
	}

	/**
     * <p>
     * ネストレベルを１段階深くします。
     * </p>
     */
	private void nest() {

		++nestLevel;
	}

	/**
     * <p>
     * ネストレベルを１段階戻します。
     * </p>
     *
     * @throws IllegalStateException
     *             ネストしていない状態でこのメソッドが呼び出された場合
     */
	private void up() {

		if (nestLevel <= 0) {
			throw new IllegalStateException("ネストしていないない状態でネスト解除が要求されました");
		}
		--nestLevel;
	}

	/**
     * <p>
     * ネストしたオブジェクトの展開要否を判定します。
     * </p>
     * <p>
     * 展開レベルがネストレベルより大きい場合を展開要と判定します。
     * </p>
     *
     * @return ネストしたオブジェクトの展開の要否。展開要は <code>true</code>、そうでない場合は
     *         <code>false</code>
     */
	private boolean isExpanding() {

		return (expandingLevel > nestLevel);
	}

	/**
     * <p>
     * ネストレベルに対応した長さのインデント文字列を返却します。
     * </p>
     *
     * @return インデント
     */
	private String getIndent() {

		StringBuffer buf = new StringBuffer(nestLevel);
		for (int i = 0; i < nestLevel; ++i) {
			buf.append(INDENT_STR);
		}
		return buf.toString();
	}

	/**
     * <p>
     * 指定されたオブジェクトのクラス名を返却します。
     * </p>
     * <p>
     * クラス名は以下の形式。
     * </p>
     *
     * <blockquote><table summary="">
     * <tr>
     * <th>型 </th>
     * <th>クラス名 </th>
     * </tr>
     * <tr>
     * <td>String型 </td>
     * <td>java.lang.String </td>
     * </tr>
     * <tr>
     * <td>Object配列型</td>
     * <td>java.lang.Object[]</td>
     * </tr>
     * <tr>
     * <td>int配列型 </td>
     * <td>int[] </td>
     * </tr>
     * </table></blockquote>
     *
     * @param obj
     *            クラス名を取得する対象のオブジェクト
     *
     * @return クラス名
     */
	public static String getClassName(Object obj) {

		Class clazz = obj.getClass();
		String fqdName = clazz.getName();
		int dimension = 0;
		int length = fqdName.length();
		while (dimension < length && fqdName.charAt(dimension) == '[') {
			++dimension;
		}
		StringBuffer dispName = new StringBuffer();
		if (dimension == 0) {
			dispName.append(fqdName);
		} else if (length - dimension == 1) {
			char oneCharName = fqdName.charAt(dimension);
			switch (oneCharName) {
			case 'B':
				dispName.append("byte");
				break;
			case 'C':
				dispName.append("char");
				break;
			case 'D':
				dispName.append("double");
				break;
			case 'F':
				dispName.append("float");
				break;
			case 'I':
				dispName.append("int");
				break;
			case 'J':
				dispName.append("long");
				break;
			case 'S':
				dispName.append("short");
				break;
			case 'Z':
				dispName.append("boolean");
				break;
			default:
				dispName.append(oneCharName);
				break;
			}
		} else {
			dispName.append(fqdName.substring(dimension + 1, length - 1));
		}
		for (int i = 0; i < dimension; ++i) {
			dispName.append("[]");
		}
		return dispName.toString();
	}
}