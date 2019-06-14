/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:StringPrintWriter.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/28		:成田　敦		:新規作成
 *
 **************************************************************/

package jp.co.keepalive.springbootfw.util.logging;


import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * <b>[概要]</b><br>
 * <p>
 * 文字列をストリームに出力するためのクラスです。
 * </p>
 *
 * @author Atsushi Narita
 */
public class StringPrintWriter extends PrintWriter {

    /**
     * <p>
     * このクラスのインスタンスを作成します。
     * </p>
     */
    public StringPrintWriter() {

        super(new StringWriter());
    }

    /**
     * <p>
     * ストリームの内容を <code>String</code> で取得します。
     * </p>
     * <p>
     * このメソッドを呼び出すと、ストリームは自動的に <code>close()</code> されます。
     * </p>
     *
     * @return  ストリームの内容
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        super.flush();
        return super.out.toString();
    }
}