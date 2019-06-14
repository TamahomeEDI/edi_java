package jp.co.keepalive.springbootfw.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;

public class HttpUtil {

    private static final Log LOGGER = LogFactory
            .getLog(HttpUtil.class);

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";

    public static final int RESPONSE_CODE_200 = 200;

    public static final boolean CODE_CHECK_FLG_TRUE = true;
    public static final boolean CODE_CHECK_FLG_FALSE = false;

    public static final String TWO_HYPHEN = "--";
    public static final String EOL = "\r\n";
    public static final String CHARSET = "UTF-8";

    /*
     * GET
     */
    public static void getNoResponse(String uri) {
        HttpGet method = new HttpGet(uri);
        executeNoResponse(method);
    }

    public static CloseableHttpResponse get(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return get(uri, headers, params, CODE_CHECK_FLG_TRUE);
    }

    public static CloseableHttpResponse get(String uri, List<NameValuePair> headers, List<NameValuePair> params, boolean codeCheckFlg) {
        HttpGet method = new HttpGet(uri + getQuery(params));
        setHeader(method, headers);
        return execute(method, codeCheckFlg);
    }

    private static void setHeaders(HttpURLConnection conn, List<NameValuePair> headers) {
        if(headers != null && headers.size() > 0) {
            for (NameValuePair nameValuePair : headers) {
                conn.setRequestProperty(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
    }

    /*
     * POST
     */
    public static CloseableHttpResponse post(String uri, List<NameValuePair> headers, Object params) {
        return post(uri, headers, params, CODE_CHECK_FLG_TRUE);
    }

    public static CloseableHttpResponse post(String uri, List<NameValuePair> headers, Object params, boolean codeCheckFlg) {
        HttpPost method = new HttpPost(uri);
        setEntity(method, params);
        setHeader(method, headers);
        return execute(method, codeCheckFlg);
    }

    /*
     * PUT
     *
     */
    public static CloseableHttpResponse put(String uri, List<NameValuePair> headers, Object params) {
        return put(uri, headers, params, CODE_CHECK_FLG_TRUE);
    }

    public static CloseableHttpResponse put(String uri, List<NameValuePair> headers, Object params, boolean codeCheckFlg) {
        HttpPut method = new HttpPut(uri);
        setEntity(method, params);
        setHeader(method, headers);
        return execute(method, codeCheckFlg);
    }

    /*
     * DELETE
     */
    public static CloseableHttpResponse delete(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return delete(uri, headers, params, CODE_CHECK_FLG_TRUE);
    }

    public static CloseableHttpResponse delete(String uri, List<NameValuePair> headers, List<NameValuePair> params, boolean codeCheckFlg) {
        HttpDelete method = new HttpDelete(uri + getQuery(params));
        setHeader(method, headers);
        return execute(method, codeCheckFlg);
    }

    /*
     * Header設定
     */
    private static void setHeader(HttpUriRequest method, List<NameValuePair> headers) {
        if(headers != null && headers.size() > 0) {
            for (NameValuePair header : headers) {
                method.addHeader(header.getName(), header.getValue());
            }
        }
    }

    /*
     * クエリ設定
     */
    private static String getQuery(List<NameValuePair> params) {
        String query = "";
        if(params != null && params.size() > 0) {
            for (NameValuePair pair : params) {
                query += pair.getName() + "=" + pair.getValue() + "&";
            }
            //String encodeStr = URLEncoder.encode(str,"utf-8");
        }
        if(query.length() == 0) {
            return query;
        }
        return "?" + query.substring(0, query.length()-1);
    }

    /*
     * エンティティ設定
     */
    @SuppressWarnings("unchecked")
    private static void setEntity(HttpEntityEnclosingRequestBase method, Object params) {
        try {
            if(params instanceof String) {
                method.setEntity(new StringEntity((String) params, "UTF-8"));
            } else if (params instanceof List) {
                method.setEntity(new UrlEncodedFormEntity((List<NameValuePair>) params, "UTF-8"));
            }

        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        }
    }

    private static CloseableHttpResponse execute(HttpUriRequest method, boolean codeCheckFlg) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        LOGGER.debug("HttpUtil execute :" + method.getMethod() + " " + method.getURI());
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(method);
            int statusCode = response.getStatusLine().getStatusCode();
            if (codeCheckFlg && statusCode != 200 && statusCode != 201) {
                response.close();
                throw new CoreRuntimeException(
                        "応答が異常です.HTTP Status CODE:"
                                + statusCode);
            }
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
        return response;
    }

    private static void executeNoResponse(HttpUriRequest method) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        LOGGER.debug("HttpUtil executeNoResponse :" + method.getMethod() + " " + method.getURI());
        try {
            httpclient.execute(method);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return;
    }

    public static String toXmlString(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity();
        // Read the response body.
        InputStream is = null;
        try {
            is = entity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer buf;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            buf = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
                buf.append("\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new CoreRuntimeException(e);
            }
        }
        LOGGER.debug("toXmlString return :" + buf.toString());
        return buf.toString();
    }


    /*
     * StringをDocumentに変換
     */
    public static Document toDoc(CloseableHttpResponse response) {
        String str = toXmlString(response);
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(new InputSource(new StringReader(str)));
        } catch (SAXException | IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return doc;
    }

    /*
     * StringをDocumentに変換
     */
    public static String toJson(CloseableHttpResponse response) {
        HttpEntity entity = response.getEntity();
        // Read the response body.
        InputStream is = null;
        try {
            is = entity.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer buf;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            buf = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
            }
        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new CoreRuntimeException(e);
            }
        }
        LOGGER.debug("toJson return :" + buf.toString());
        return buf.toString();
    }

    public static String postFile(String uri, List<NameValuePair> headers, List<NameValuePair> params, String filePath, String fileName, String mimetype) {
        String BOURDARY = String.format("%x", new Random().hashCode());

        String result = "";

        // 送信するコンテンツを成形する
        StringBuilder contentsBuilder = new StringBuilder();
        String closingContents = "";
        int iContentsLength = 0;
        File file = null;
        FileInputStream fis = null;

        file = new File(filePath);

        //パラメータの設定
        if(params != null && params.size() > 0) {
            for (NameValuePair nameValuePair : params) {
                contentsBuilder.append(String.format("%s%s%s", "--------------------------", BOURDARY, EOL));
                contentsBuilder.append(String.format("Content-Disposition: form-data; name=\"" + nameValuePair.getName() + "\"%s", EOL));
                contentsBuilder.append(String.format("%s", EOL));
                contentsBuilder.append(String.format("%s%s", nameValuePair.getValue(), EOL));
            }
        }

        // ファイル情報のセット
        contentsBuilder.append(String.format("%s%s%s", "--------------------------", BOURDARY, EOL));
        contentsBuilder.append(String.format("Content-Disposition: form-data; name=\"" + "name" + "\"%s", EOL));
        contentsBuilder.append(String.format("%s", EOL));
        contentsBuilder.append(String.format("%s%s", fileName, EOL));
        contentsBuilder.append(String.format("%s%s%s", "--------------------------", BOURDARY, EOL));
        contentsBuilder.append(String.format("Content-Disposition: form-data; name=\""+ "uploadfile" +"\"; filename=\"%s\"%s", fileName, EOL));

        // ファイルサイズの取得
        iContentsLength += file.length();
        contentsBuilder.append(String.format("Content-Type: %s%s", mimetype, EOL));

        contentsBuilder.append(EOL);

        closingContents = String.format("%s%s%s%s%s", EOL, "--------------------------", BOURDARY, TWO_HYPHEN, EOL);

        // コンテンツの長さを取得
        try {
            iContentsLength += contentsBuilder.toString().getBytes(CHARSET).length;
            iContentsLength += closingContents.getBytes(CHARSET).length;
        } catch (UnsupportedEncodingException e) {
            throw new CoreRuntimeException(e);
        }

        // サーバへ接続する
        HttpURLConnection conn = null;
        DataOutputStream os = null;
        BufferedReader br = null;

        try {
            URL url = new URL(uri);

            conn = (HttpURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // キャッシュを使用しない
            conn.setUseCaches(false);

            // HTTPストリーミングを有効にする
            conn.setChunkedStreamingMode(0);

            // リクエストヘッダを設定する
            conn.setRequestMethod(HTTP_POST);
            setHeaders(conn, headers);
            conn.setRequestProperty("Content-Type", String.format("multipart/form-data; boundary=%s%s", "------------------------",BOURDARY));
            conn.setRequestProperty("Content-Length", String.valueOf(iContentsLength));

            // データを送信する
            os = new DataOutputStream(conn.getOutputStream());
            //日本語を送るときはwriteBytesは使わない
//            os.writeBytes(contentsBuilder.toString());
            //こっち使う
            String string = contentsBuilder.toString();
			byte[] bytes = string.getBytes();
            for (int i = 0;i < bytes.length; i++){
                os.writeByte(bytes[i]);
            }

            //初期化…2019/4/1（ファイルアップロード改善）
            int bytesRead, bytesAvailable, bufferSize;
            int maxBufferSize = 1 * 1024 * 1024;

            // ファイルの送信
            if (file != null) {
                byte buffer[] = new byte[1024];
                fis = new FileInputStream(file);

//                while (fis.read(buffer, 0, buffer.length) > -1) {
//                    os.write(buffer, 0, buffer.length);
//                }

                //↓これじゃないとダメっぽい…2019/4/1（ファイルアップロード改善）
                // ファイルを一定サイズ毎読み書きする
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fis.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                	os.write(buffer, 0, bufferSize);
                    bytesAvailable = fis.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fis.read(buffer, 0, bufferSize);
                }

            }

            os.writeBytes(closingContents);

            // 接続が確立したとき
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder resultBuilder = new StringBuilder();
                String line = "";

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                // レスポンスの読み込み
                while ((line = br.readLine()) != null) {
                    resultBuilder.append(String.format("%s%s", line, EOL));
                }
                result = resultBuilder.toString();
            }
            // 接続が確立できなかったとき
            else {
                StringBuilder resultBuilder = new StringBuilder();
                String line = "";
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

                // レスポンスの読み込み
                while ((line = br.readLine()) != null) {
                    resultBuilder.append(String.format("%s%s", line, EOL));
                }
                throw new HttpException("HTTPS STATUS = " + conn.getResponseCode() + " message = " + resultBuilder.toString());
            }
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        } finally {
            // 開いたら閉じる
            try {
                if (br != null) br.close();
                if (os != null) {
                    os.flush();
                    os.close();
                }
                if (fis != null) fis.close();
                if (conn != null) conn.disconnect();
            } catch (IOException e) {
                throw new CoreRuntimeException(e);
            }
        }

        // 代入したレスポンスを返す
        return result;
    }

    public static void close(CloseableHttpResponse response) {
        try {
            response.close();
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }




}