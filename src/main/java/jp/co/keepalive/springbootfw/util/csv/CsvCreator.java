/**************************************************************
 * システム名		:KeepAliveフレームワーク
 * サブシステム名	:
 * モジュール名		:CsvCreator.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/23		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>[概要]</b><br>
 * <p>
 * {@link CsvData} を作成する機能を提供するクラスです。
 * </p>
 * <p>
 * Sample:DAO から List を取得し、CsvData を作成後、CSV ファイルに書き出す例
 * </p>
 * <p>
 * <pre>
 * <code>
 *  List personList = personDao.selectAll();
 * 	CsvCreator csvCreator = new CsvCreator();
 *  // 適宜ヘッダを作成
 *  csvCreator.add("従業員ID");
 *  csvCreator.add("部署");
 *  csvCreator.add("名前");
 *
 *  // ヘッダ行終了
 *  csvCreator.endRecord();
 *
 *  // カラム順にaddメソッドを呼び出す
 *  for(int i = 0; i < personList.size(); i++){
 *  	Person person = (Person)personList.get(i);
 *  	csvCreator.add(person.getStaffId());
 *  	csvCreator.add(person.getDepartment());
 *  	csvCreator.add(person.getName());
 *
 *  	//行終了
 *  	csvCreator.endRecord();
 *  }
 *
 *  // 作成完了しCsvDataを取得
 *  CsvData csv = csvCreator.complete();
 *
 *  // CsvUtilsを使用し、ファイルに書き出す
 *  CsvUtils.writeCsvFile(csv, "D:/csv/person_list.csv", "SJIS");
 * </code>
 * </pre>
 * </p>
 *
 * @author Atsushi Narita
 */
public class CsvCreator {

	private List<String> columns = new ArrayList<String>();

	private String[] completeColumns;

	private CsvData csv = new CsvData();

	/**
	 * <p>
	 * カラムにデータを追加します。
	 * </p>
	 * @param value 文字列データ
	 */
	public void add(int value){
        columns.add(String.valueOf(value));
    }

	public void add(String value){
	    if(value == null) {
		    columns.add("");
	    } else {
	        columns.add(value.replaceAll(",", " "));
	    }
	}

	public void addTsvVal(String value){
        if(value == null) {
            columns.add("");
        } else {
            columns.add(value);
        }
    }

	/**
	 * <p>
	 * 1レコードの終了(改行)を宣言します。
	 * </p>
	 */
	public void endRecord(){
		completeColumns = columns.toArray(new String[columns.size()]);
		csv.addRow(completeColumns);
		columns.clear();
	}

	/**
	 * <p>
	 * CSV の作成を終了し CsvData を取得します。
	 * </p>
	 */
	public CsvData complete(){
		return csv;
	}
}
