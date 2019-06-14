/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 					:				:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.logging;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <b>[概要]</b><br>
 * <p>
 * Properties オブジェクトをダンプするユーティリティです。
 * </p>
 *
 * @author Atsushi.N
 */
public class PropertyDumpUtil {

	private static final Log LOGGER = LogFactory.getLog(PropertyDumpUtil.class);

	/**
	 * <p>
	 * ダンプ用文字列を返却します。
	 * </p>
	 *
	 * @param obj プロパティ型オブジェクト
	 * @return ダンプ用文字列
	 */
	public static String getDumpStr(Object obj){
		if(!LOGGER.isDebugEnabled()){
			return null;
		}
		Properties props = (Properties)obj;
		if(props == null){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		Iterator ite = props.entrySet().iterator();
		while(ite.hasNext()){
			Entry entry = (Entry)ite.next();
			String key = (String)entry.getKey();
			String val = (String)entry.getValue();
			buf.append(key).append(":").append(val).append("\n");
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * デバッグログにダンプします。
	 * プロパティの各エントリ（キーと値のペア）を出力します。
	 * </p>
	 *
	 * @param obj プロパティ型オブジェクト
	 */
	public static void dump(Object obj) {
		if(!LOGGER.isDebugEnabled()){
			return;
		}
		Properties props = (Properties)obj;
		if(props == null){
			return;
		}
		LOGGER.debug("===PropertyLoader Properties dump start.===");
		Iterator ite = props.entrySet().iterator();
		while(ite.hasNext()){
			Entry entry = (Entry)ite.next();
			String key = (String)entry.getKey();
			String val = (String)entry.getValue();
			LOGGER.debug(key + ":" + val);
		}
		LOGGER.debug("===PropertyLoader Properties dump end.===");
	}
}
