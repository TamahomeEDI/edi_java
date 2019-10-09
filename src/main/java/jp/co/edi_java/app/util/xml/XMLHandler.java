package jp.co.edi_java.app.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class XMLHandler extends DefaultHandler {

    /**
     * XMLドキュメント
     */
    private String document = null;

    /**
     * 結果マップ
     */
    private Map rootMap = new HashMap();

    /**
     * 現在の要素名
     */
    private String currentTagName = null;

    /**
     * レコードをラップするタグ名
     */
    private String recordTagName = null;

    /**
     * レコードのリスト
     */
    private ArrayList recordList = null;

    /**
     * レコード用マップ
     */
    private Map[] recordMap = new HashMap[2000];

    /**
     * レコード用マップの番号
     */
    private int recordNum = 0;

    /**
     * レコードかどうかのフラグ
     */
    private boolean isRecord = false;

    /**
     * コンストラクタ
     */
    public XMLHandler(String document) {
        this.document = document;
    }

    /**
     * 引数のdocumentをパースしてMapにマッピングします
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public void parse() {
        //SAXパーサーファクトリを生成
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //SAXパーサーを生成
        try {
            SAXParser parser = factory.newSAXParser();
            //XMLをこのクラスのハンドラーで処理します
            ByteArrayInputStream  bais = new ByteArrayInputStream(this.document.getBytes("UTF-8"));
            //パース処理
            parser.parse(bais, this);
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

    /**
     * 結果のマップ取得
     * @return 結果のマップ
     */
    @SuppressWarnings("rawtypes")
    public Map getXMLMap() {
        return rootMap;
    }

    /**
     * ドキュメント開始時
     */
    @Override
	public void startDocument() {}

    /**
     * 要素の開始タグ読み込み時
     */
    @Override
	@SuppressWarnings("rawtypes")
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("Record")) {
            if (recordList == null) {
                recordList = new ArrayList();
            }
            recordMap[recordNum] = new HashMap();
            isRecord = true;
        }
        if (!isRecord) {
            recordTagName = qName;
        }
        currentTagName = qName;
    }

    /**
     * テキストデータ読み込み時<br>
     * UTF-8で読み込んだ文字データをSJISとしてMapに格納する
     */
    @Override
	@SuppressWarnings("unchecked")
    public void characters(char[] ch, int offset, int length) {
        try {
            //UTF-8をSJISに変換する
            String value = new String(new String(ch, offset, length).getBytes("UTF-8"), "UTF-8");
            //要素の値が空の場合、何もしない
            if (value.trim().length() == 0) {
                return;
            }
            if (isRecord) {
                recordMap[recordNum].put(currentTagName, value);
            } else {
                rootMap.put(currentTagName, value);
            }
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 要素の終了タグ読み込み時
     */
    @Override
	@SuppressWarnings("unchecked")
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("Record")) {
            recordList.add(recordMap[recordNum]);
            rootMap.put(recordTagName, recordList);
            recordNum++;
            isRecord = false;
        }
    }

    /**
     * ドキュメント終了時
     */
    @Override
	public void endDocument() {}
}