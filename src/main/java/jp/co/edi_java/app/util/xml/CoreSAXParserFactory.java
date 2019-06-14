/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:CoreSAXParserFactory.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/6/1			:成田　敦		:新規作成
 *
 **************************************************************/

package jp.co.edi_java.app.util.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.lang.ClassUtils;
import jp.co.keepalive.springbootfw.util.property.PropertyLoader;

/**
 * <b>[概要]</b><br>
 * <p>
 * SAX パーサのランタイムを選択するファクトリークラスです。<br>
 * keepalive-core.properties の "sax.parser.factory.product" が "xerces"の場合は、Xerces実装が
 * crimsonの場合はCrimson実装が選択されます。
 * ※留意事項※
 * Piccoloライブラリは高速ですが、マルチスレッドに対応しないため、対応しません。
 * システムデフォルトのSAXParserはアプリケーションサーバ実装を取得してしまうため、使用不可とします。
 * </p>
 *
 * @author Atsushi Narita
 */
public class CoreSAXParserFactory {

	private static final String SAX_PARSER_FACTORY_PRODUCT = "sax.parser.factory.product";

	private static final String XERCES_SAX_PARSER = "xerces";

	private static final String CRIMSON_SAX_PARSER = "crimson";

	private CoreSAXParserFactory() {
		// cannot instanciate
	}

	/**
     * <p>
     * keepalive-core.properties の設定に基づき SAXParser のインスタンスを返却します。
     * </p>
     */
	public static SAXParser newInstance() {
		try {
			String propval = PropertyLoader.getInstance().getPropertyValue(SAX_PARSER_FACTORY_PRODUCT);
			if (propval.equalsIgnoreCase(XERCES_SAX_PARSER)) {
				SAXParserFactory factory = (SAXParserFactory) ClassUtils.getClass(
						"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl",
						Thread.currentThread().getContextClassLoader()).newInstance();
				return factory.newSAXParser();
			} else if (propval.equalsIgnoreCase(CRIMSON_SAX_PARSER)) {
				SAXParserFactory factory = (SAXParserFactory) ClassUtils.getClass(
						"org.apache.crimson.jaxp.SAXParserFactoryImpl", Thread.currentThread().getContextClassLoader())
						.newInstance();
				return factory.newSAXParser();
			}  else {
				throw new CoreRuntimeException("bridge.properitesに" + SAX_PARSER_FACTORY_PRODUCT + "が設定されていません.");
			}

		} catch (InstantiationException e) {
			throw new CoreRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new CoreRuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new CoreRuntimeException(e);
		} catch (SAXException e) {
			throw new CoreRuntimeException(e);
		}
	}

}