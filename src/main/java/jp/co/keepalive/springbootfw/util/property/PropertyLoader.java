/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:PropertyLoader.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.property;

import java.io.InputStream;
import java.util.Properties;

import jp.co.keepalive.springbootfw.util.logging.PropertyDumpUtil;

/**
 * <b>[概要]</b><br>
 * <p>
 * プロパティファイルから
 * プロパティ値を取得するシングルトンクラスです。
 * </p>
 *
 * @author Atsushi Narita
 * @version 1.0
 */
public class PropertyLoader {

	private static PropertyLoader singleton;

	private Properties props;


	private static final String CORE_PROPS_FILENAME = "application.properties";

	// cannot instanciation
	private PropertyLoader(String filename) {
		load(filename);
	}

	static {
		singleton = new PropertyLoader(CORE_PROPS_FILENAME);
	}

	/**
	 * <p>
     * このクラスのインスタンスを取得します。
     * 取得されるインスタンスはシングルトンになります。
     * </p>
     */
	public static PropertyLoader getInstance() {
		return singleton;
	}

	/**
	 * <p>
     * 指定されたキーに該当するプロパティ値を取得します。
     * </p>
     *
     * @param key
     *            プロパティファイルに記述されるキー名
     * @return プロパティの値
     */
	public String getPropertyValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * <p>
     * 指定されたキーに該当するプロパティ値を取得します。
     * 該当するキーが存在しない場合、defaultValue が返却されます.
     * </p>
     *
     * @param key
     *            プロパティファイルに記述されるKey名
     * @param defaultValue
     *            プロパティファイルにKeyが見つからなかった場合のデフォルト返却値
     * @return プロパティの値
     */
	public String getPropertyValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	/**
	 * <p>
	 * 指定されたプロパティファイルを読み込みます。
	 * </p>
	 *
	 * @param filename プロパティファイル名
	 */
	private void load(String filename) {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(filename);
			props = new Properties();
			props.load(is);
		} catch (Exception ex) {
			throw new PropertyLoadFailureException(ex);
		}
	}

	/**
	 * <p>
	 * プロパティのダンプ出力文字列を返します。
	 * </p>
	 */
	@Override
	public String toString(){
		return PropertyDumpUtil.getDumpStr(props);
	}

	/**
	 * <p>
	 * プロパティオブジェクトを返します。
	 * </p>
	 */
	public Properties getProps() {
		return props;
	}

	/**
	 * <p>
	 * プロパティオブジェクトを設定します。
	 * </p>
	 */
	public void setProps(Properties props) {
		this.props = props;
	}
}
