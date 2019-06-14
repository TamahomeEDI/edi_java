/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:ClassNameUtil.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/6/14		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.lang;


/**
 * <b>[概要]</b><br>
 * <p>
 * クラス名の操作に関するユーティリティです。
 * </p>
 *
 * @author Atsushi Narita
 */
public class ClassNameUtil {

	private ClassNameUtil(){
		// cannot instanciate
	}

	/**
	 * <p>
	 * 指定されたオブジェクトの完全修飾クラス名を取得します。
	 * </p>
	 *
	 * @param obj インスタンス
	 * @return 完全修飾クラス名
	 */
	public static String getFullQueryClassName(Object obj){
		return obj.getClass().getName();
	}

	/**
	 * <p>
     * 指定されたオブジェクトのクラス名を取得します。<br/>
     * 取得されるクラス名はパッケージ部分を除いたクラス名です。
     * </p>
     *
     * @param obj インスタンス
     * @return クラス名
     */
	public static String getSimpleClassName(Object obj) {
		if(obj == null){
			return "";
		}
		String fullName = obj.getClass().getName();
		return	getSimpleClassName(fullName);
	}

	/**
	 * <p>
	 * このクラスのクラス名を取得します。<br/>
	 * 取得されるクラス名はパッケージ部分を除いたクラス名です。
	 * </p>
	 *
	 * @param fullName 完全修飾クラス名
	 * @return クラス名
	 */
	public static String getSimpleClassName(String fullName){
		if(StringUtils.isNull(fullName)){
			return "";
		}
		int lastPeriod = fullName.lastIndexOf(".");

		if (lastPeriod == -1) {
			return fullName;
		}

		return fullName.substring(lastPeriod + 1);
	}
}
