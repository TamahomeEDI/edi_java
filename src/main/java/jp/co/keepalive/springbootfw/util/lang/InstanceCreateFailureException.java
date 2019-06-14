/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:InstanceCreateFailureException.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/6/28		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.lang;

/**
 * <b>[概要]</b><br>
 * <p>
 * この例外は、インスタンスの生成に失敗したことを表します。
 * </p>
 *
 * @author Atsushi Narita
 */
public class InstanceCreateFailureException extends RuntimeException {

	private static final long serialVersionUID = 2647176370307577233L;

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     */
	public InstanceCreateFailureException() {
		super();
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     * @param cause Throwable オブジェクト
     */
	public InstanceCreateFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     */
	public InstanceCreateFailureException(String message) {
		super(message);
	}

	/**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param cause Throwable オブジェクト
     */
	public InstanceCreateFailureException(Throwable cause) {
		super(cause);
	}

}
