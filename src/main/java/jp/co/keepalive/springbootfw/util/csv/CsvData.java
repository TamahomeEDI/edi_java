/**************************************************************
 * システム名		:KeepAliveフレームワーク
 * サブシステム名	:
 * モジュール名		:CsvData.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/23		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.csv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

/**
 * <b>[概要]</b><br>
 * <p>
 * CSV データの保持、およびその作成のためのヘルパーメソッドを提供します。
 * </p>
 *
 * @author Atsushi Narita
 */
public class CsvData implements Serializable {

	/**
     * <code>serialVersionUID</code>
     */
	private static final long serialVersionUID = 1L;

	private List<String[]> csvList = new ArrayList<String[]>();

	/**
	 * <p>
	 * CSVの指定行を<code>List</code>として取得します。
	 * </p>
	 *
	 * @param index 行番号
	 * @return CSV行の<code>List</code>
	 */
	public List getRowAsList(int index){
		return Arrays.asList(csvList.get(index));
	}

	/**
	 * <p>
	 * CSVの指定行を取得します。
	 * </p>
	 *
	 * @param index 行番号
	 * @return CSV行
	 */
	public String[] getRow(int index){
		return csvList.get(index);
	}

	/**
	 * <p>
	 * CSVの指定行、指定カラムの文字列を取得します。
	 * </p>
	 *
	 * @param rowindex 行番号
	 * @param colindex 列番号
	 * @return CSV要素
	 */
	public String readColumn(int rowindex, int colindex){
		return csvList.get(rowindex)[colindex];
	}

	/**
	 * <p>
	 * CSVに行を追加します。
	 * </p>
	 *
	 * @param line CSV行
	 */
	public void addRow(String[] line) {
		csvList.add(line);
	}

	/**
	 * <p>
	 * CSVから指定行を削除します。
	 * </p>
	 *
	 * @param index 行番号
	 */
	public void removeRow(int index) {
		csvList.remove(index);
	}

	/**
	 * <p>
	 * CSVの指定行を更新します。
	 * </p>
	 *
	 * @param line CSV行
	 * @param index 行番号
	 */
	public void updateRow(String[] line, int index) {
		String[] cols = csvList.get(index);
		if (cols.length != line.length) {
			throw new CoreRuntimeException("更新対象行のカラム数が異なります.対象カラム数:" + cols.length + ",実際:" + line.length);
		}
		for(int i = 0; i < line.length; i++){
			cols[i] = line[i];
		}
		csvList.set(index, cols);
	}

	/**
	 * <p>
	 * CSVの全件リストを取得します。
	 * </p>
	 *
	 * @return CSVの全件<code>List</code>
	 */
	public List<String[]> getCsvList() {
		return csvList;
	}

	/**
	 * <p>
	 * CSVの全件リストを設定します。
	 * </p>
	 *
	 * @param csvList CSVの全件<code>List</code>
	 */
	public void setCsvList(List<String[]> csvList) {
		this.csvList = csvList;
	}

}
