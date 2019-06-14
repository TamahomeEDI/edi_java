/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:PropertyLoadFailureException.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 		:成田　敦		:新規作成
 *
 **************************************************************/

package jp.co.keepalive.springbootfw.util.property;

/**
 * <b>[概要]</b><br>
 * <p>
 * この例外は、{@link PropertyLoader} でプロパティファイルのロードに失敗したことを表します。
 * </p>
 *
 * @author Atsushi Narita
 * @version 1.0
 */
public class PropertyLoadFailureException extends RuntimeException {

	private static final long serialVersionUID = 1986090544714671650L;

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     */
	public PropertyLoadFailureException() {
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     */
	public PropertyLoadFailureException(String message) {
		super(message);
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param cause Throwable オブジェクト
     */
	public PropertyLoadFailureException(Throwable cause) {
		super(cause);
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     * @param cause Throwable オブジェクト
     */
	public PropertyLoadFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}
