/**************************************************************
 * システム名		:
 * サブシステム名	:
 * モジュール名		:BinaryData.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/17		:成田　敦		:新規作成
 *
 **************************************************************/

package jp.co.keepalive.springbootfw.util.dxo;

import java.io.Serializable;

/**
 * <b>[概要]</b><br>
 * <p>
 * 画像、文書などのバイナリデータを扱うためのクラスです。<br/>
 * サービスでバイナリデータのやり取りを行う場合は、
 * このクラスにバイナリデータを格納して DTO を作成します。
 * これは、データが画像、文書などのバイナリデータなのか、
 * それとも単純なバイト配列なのかを区別するためのものです。
 * </p>
 *
 * @author Atsushi Narita
 */
public class BinaryData implements Serializable {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private byte[] bytes;

	/**
	 * 指定されたバイト配列で BinaryData を作成します。
	 *
	 * @param bytes バイナリデータ(バイト配列)
	 */
	public BinaryData(byte[] bytes){
		this.bytes = bytes;
	}

	/**
	 * <p>
	 * バイナリデータ(バイト配列)を取得します.
	 * </p>
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * <p>
	 * バイナリデータ(バイト配列)を設定します.
	 * </p>
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
