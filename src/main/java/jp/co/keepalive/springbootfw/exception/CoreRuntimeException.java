/**************************************************************
 * システム名        :
 * サブシステム名  :
 * モジュール名       :CoreRuntimeException.java
 *
 * 修正日          :修正者            :修正理由
 * ------------------------------------------
 * 2007/6/1         :成田　敦       :新規作成
 *
 **************************************************************/

package jp.co.keepalive.springbootfw.exception;

import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>[概要]</b><br>
 * <p>
 * このクラスは、フレームワーク内でスローされる実行時例外の基本となるクラスです。<br/>
 * フレームワークでは、例外の原因がアプリケーションのバグや環境設定の不備に起因するような場合に、
 * このクラスもしくはそのサブクラスをスローします。
 * </p>
 *
 * @author Atsushi Narita
 */
@Getter
@Setter
public class CoreRuntimeException extends RuntimeException {

	private String statusCode = ResponseCode.ERROR_CODE_500;

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     */
    public CoreRuntimeException() {
    }

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     */
    public CoreRuntimeException(String message) {
        super(message);
    }

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param cause Throwableオブジェクト
     */
    public CoreRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     * @param cause Throwableオブジェクト
     */
    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     * @param statusCode ステータスコード
     * @param cause Throwableオブジェクト
     */
    public CoreRuntimeException(String message, String statusCode) {
    	super(message);
    	this.statusCode = statusCode;
    }

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     *
     * @param message メッセージ(Throwable.getMessage() 用に保存される)
     * @param statusCode ステータスコード
     * @param cause Throwableオブジェクト
     */
    public CoreRuntimeException(String message, String statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

}
