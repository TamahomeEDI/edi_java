/**************************************************************
 * システム名		:KeepAliveフレームワーク
 * サブシステム名	:
 * モジュール名		:CsvUtils.java
 *
 * 修正日			:修正者			:修正理由
 * ------------------------------------------
 * 2007/8/23		:成田　敦		:新規作成
 *
 **************************************************************/
package jp.co.keepalive.springbootfw.util.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

/**
 * <b>[概要]</b><br>
 * <p>
 * CSV ファイルを扱うためのユーティリティクラスです。
 * CSV ファイルの入出力に関する機能を提供します。
 * </p>
 *
 * @author Atsushi Narita
 */
public class CsvUtils {

	private CsvUtils() {
		// cannot instanciate
	}

	/**
	 * <p>
	 * CSVファイルからJavaBeansに復元します。
	 * </p>
	 *
	 * @param clazz JavaBeansのClass型
	 * @param columns CSVのカラム順
	 * @param filename ファイル名
	 * @param encoding 文字コード
	 * @return 復元されたJavaBeansのList
	 */
	public static List readCsvFileAsBean(Class clazz, String[] columns, String filename, String encoding) {
		return readCsvFileAsBean(clazz, columns, new File(filename), encoding);
	}

	/**
	 * <p>
	 * CSVファイルからJavaBeansに復元します。
	 * </p>
	 *
	 * @param clazz JavaBeansのクラス型
	 * @param columns CSVのカラム順
	 * @param file CSVファイル
	 * @param encoding 文字コード
	 * @return 復元されたJavaBeansのList
	 */
	public static List readCsvFileAsBean(Class clazz, String[] columns, File file, String encoding) {

		ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
		strat.setType(clazz);
		strat.setColumnMapping(columns);

		CsvToBean csv = new CsvToBean();
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			return csv.parse(strat, reader);
		} catch (UnsupportedEncodingException e) {
			throw new CoreRuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new CoreRuntimeException(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				throw new CoreRuntimeException(e);
			}
		}
	}

	/**
     * <p>
     * CSVファイルを読み込みMapオブジェクトに格納します。
     * 1行目のヘッダー行をマップのキーとします。
     * </p>
     *
     * @param filename CSVファイル名
     * @param encoding 文字コード
     * @return Map(CSV格納オブジェクト)
     */
    public static List<Map<String, Object>> readCsvFileToMap(String filename, String encoding) {
        List<Map<String, Object>> ret = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        CsvData csv =  readCsvFile(new File(filename), encoding);
        List<String[]> list = csv.getCsvList();
        for (String[] strings : list) {
            if(headers.size() == 0) {
                for (String string : strings) {
                    String camelKey = string.substring(0, 1).toLowerCase() + string.substring(1, string.length());
                    headers.add(camelKey);
                }
            } else {
                Map<String, Object> map = new HashMap<>();
                for(int i=0; i<headers.size(); i++) {
                    map.put(headers.get(i), strings[i]);
                }
                ret.add(map);
            }
        }
        return ret;
    }

	/**
	 * <p>
	 * CSVファイルを読み込みCsvDataオブジェクトに格納します。
	 * </p>
	 *
	 * @param filename CSVファイル名
	 * @param encoding 文字コード
	 * @return CsvData(CSV格納オブジェクト)
	 */
	public static CsvData readCsvFile(String filename, String encoding) {
		return readCsvFile(new File(filename), encoding);
	}

	/**
	 * <p>
	 * CSVファイルを読み込みCsvDataオブジェクトに格納します。
	 * </p>
	 *
	 * @param file CSVファイル
	 * @param encoding 文字コード
	 * @return CsvData(CSV格納オブジェクト)
	 */
	@SuppressWarnings("unchecked")
	public static CsvData readCsvFile(File file, String encoding) {
		CsvData ret;
		try {
			ret = new CsvData();
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(file),
					encoding)));
			List<String[]> all = reader.readAll();
			ret.setCsvList(all);
		} catch (FileNotFoundException e) {
			throw new CoreRuntimeException(e);
		} catch (IOException e) {
			throw new CoreRuntimeException(e);
		}
		return ret;
	}

	/**
	 * <p>
	 * CsvDataをCSVファイルに書き出します。
	 * </p>
	 *
	 * @param data CsvData(CSV格納オブジェクト)
	 * @param filename CSVファイル名
	 * @param encoding 文字コード
	 */
	public static void writeCsvFile(CsvData data, String filename, String encoding) {
		writeCsvFile(data, new File(filename), encoding);
	}

	/**
	 * <p>
	 * CsvDataをCSVファイルに書き出します。
	 * </p>
	 *
	 * @param data CsvData(CSV格納オブジェクト)
	 * @param file CSVファイル
	 * @param encoding 文字コード
	 */
	public static void writeCsvFile(CsvData data, File file, String encoding) {
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding)), ',', CSVWriter.NO_QUOTE_CHARACTER, "\r\n");
			writer.writeAll(data.getCsvList());
		} catch (IOException e) {
			throw new CoreRuntimeException(e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw new CoreRuntimeException(e);
			}
		}
	}

	/**
     * <p>
     * CsvDataをTSVファイルに書き出します。
     * </p>
     *
     * @param data CsvData(CSV格納オブジェクト)
     * @param filename CSVファイル名
     * @param encoding 文字コード
     */
    public static void writeTsvFile(CsvData data, String filename, String encoding) {
        writeTsvFile(data, new File(filename), encoding);
    }

	/**
     * <p>
     * CsvDataをCSVファイルに書き出します。
     * </p>
     *
     * @param data CsvData(CSV格納オブジェクト)
     * @param file CSVファイル
     * @param encoding 文字コード
     */
    public static void writeTsvFile(CsvData data, File file, String encoding) {
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding)), '\t', CSVWriter.NO_QUOTE_CHARACTER, "\r\n");
            writer.writeAll(data.getCsvList());
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new CoreRuntimeException(e);
            }
        }
    }

	/**
	 * <p>
	 * CsvDataをCSV文字列データとして返却します。
	 * </p>
	 *
	 * @param data CsvData(CSV格納オブジェクト)
	 * @param encoding 文字コード
	 * @return CSV文字列データ
	 */
	public static String writeCsvString(CsvData data, String encoding){
		CSVWriter writer = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(baos)));
		writer.writeAll(data.getCsvList());
		try {
			if (writer != null) {
				writer.close();
			}
			if (baos != null) {
				baos.close();
			}
		} catch (IOException e) {
			throw new CoreRuntimeException(e);
		}
		try {
			return baos.toString(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new CoreRuntimeException(e);
		}
	}

}
