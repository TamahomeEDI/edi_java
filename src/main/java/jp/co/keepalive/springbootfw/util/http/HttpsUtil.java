package jp.co.keepalive.springbootfw.util.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.file.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpsUtil {

	public static final String MIMETYPE_PDF = "application/pdf";

    private static final Log LOGGER = LogFactory
            .getLog(HttpsUtil.class);

    public static final String HTTPS_GET = "GET";
    public static final String HTTPS_POST = "POST";
    public static final String HTTPS_PUT = "PUT";
    public static final String HTTPS_DELETE = "DELETE";

    public static final int RESPONSE_CODE_200 = 200;

    public static final String TWO_HYPHEN = "--";
    public static final String EOL = "\r\n";
    public static final String CHARSET = "UTF-8";

    public static String get(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return execute(HTTPS_GET, uri, headers, params);
    }

    public static String post(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        return execute(HTTPS_POST, uri, headers, params);
    }

    public static void getFile(String uri, List<NameValuePair> headers, String destPath) {
    	try {
	      URL url = new URL(uri);

	      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	      conn.setAllowUserInteraction(false);
	      conn.setInstanceFollowRedirects(true);
	      conn.setRequestMethod(HTTPS_GET);
	      setHeaders(conn, headers);
	      conn.connect();

	      if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
	    	  throw new HttpException("HTTPS STATUS = " + conn.getResponseCode());
	      }

	      // Input Stream
	      DataInputStream is
	        = new DataInputStream(
	            conn.getInputStream());

	      FileUtil.fileOutputByStream(is, destPath);

	      // Close Stream
	      is.close();

	    } catch (Exception e) {
	      throw new CoreRuntimeException(e);
	    }
    }

    public static String postFile(String uri, List<NameValuePair> headers, String filePath, String fileName, String mimetype) {
    	String TWO_HYPHEN = "--";
        String EOL = "\r\n";
        String BOURDARY = String.format("%x", new Random().hashCode());
        String CHARSET = "UTF-8";

        String result = "";

        // 送信するコンテンツを成形する
        StringBuilder contentsBuilder = new StringBuilder();
        String closingContents = "";
        int iContentsLength = 0;
        File file = null;
        FileInputStream fis = null;

        file = new File(filePath);

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
        HttpsURLConnection conn = null;
        DataOutputStream os = null;
        BufferedReader br = null;

        try {
            URL url = new URL(uri);

            conn = (HttpsURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // キャッシュを使用しない
            conn.setUseCaches(false);

            // HTTPストリーミングを有効にする
            conn.setChunkedStreamingMode(0);

            // リクエストヘッダを設定する
            conn.setRequestMethod(HTTPS_POST);
            setHeaders(conn, headers);
            conn.setRequestProperty("Content-Type", String.format("multipart/form-data; boundary=%s%s", "------------------------",BOURDARY));
            conn.setRequestProperty("Content-Length", String.valueOf(iContentsLength));

            // データを送信する
            os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(contentsBuilder.toString());

            // ファイルの送信
            if (file != null) {
                byte buffer[] = new byte[1024];
                fis = new FileInputStream(file);

                while (fis.read(buffer, 0, buffer.length) > -1) {
                    os.write(buffer, 0, buffer.length);
                }
            }

            os.writeBytes(closingContents);

            log.info("POST FILE CONNECTION: " + conn.toString());
            log.info("POST FILE CONTENTS: " + contentsBuilder.toString());
            log.info("POST FILE CLOSING_CONTENTS: " + closingContents.toString());

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

    public static String execute(String method, String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        try {
        	URL url = new URL(uri + getQuery(params));
            conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod(method);
            setContext(conn);
            setHostnameVerifier(conn);
            setHeaders(conn, headers);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            while((line=br.readLine()) != null)
	            {
	                str.append(line);
	            }
            } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
	            String line;
	            while((line=br.readLine()) != null)
	            {
	                str.append(line);
	            }
            	throw new HttpException("HTTPS STATUS = " + conn.getResponseCode() + "\r\n REQUEST_URL = " + url + "\r\n ERR_CONTENT = " + str);
            }
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.warn(e);
                }
            }
            conn.disconnect();
        }
        return str.toString();
    }

    public static String postParams(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        String BOURDARY = String.format("%x", new Random().hashCode());

        String result = "";

        // 送信するコンテンツを成形する
        StringBuilder contentsBuilder = new StringBuilder();
        String closingContents = "";
        int iContentsLength = 0;
        FileInputStream fis = null;

        //パラメータの設定
        if(params != null && params.size() > 0) {
            for (NameValuePair nameValuePair : params) {
                contentsBuilder.append(String.format("%s%s%s", "--------------------------", BOURDARY, EOL));
                contentsBuilder.append(String.format("Content-Disposition: form-data; name=\"" + nameValuePair.getName() + "\"%s", EOL));
                contentsBuilder.append(String.format("%s", EOL));
                contentsBuilder.append(String.format("%s%s", nameValuePair.getValue(), EOL));
            }
        }

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
        HttpsURLConnection conn = null;
        DataOutputStream os = null;
        BufferedReader br = null;

        try {
            URL url = new URL(uri);

            conn = (HttpsURLConnection)url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // キャッシュを使用しない
            conn.setUseCaches(false);

            // HTTPストリーミングを有効にする
            conn.setChunkedStreamingMode(0);

            // リクエストヘッダを設定する
            conn.setRequestMethod(HTTPS_POST);
            setHeaders(conn, headers);
            conn.setRequestProperty("Content-Type", String.format("multipart/form-data; boundary=%s%s", "------------------------",BOURDARY));
            conn.setRequestProperty("Content-Length", String.valueOf(iContentsLength));

            // データを送信する
            os = new DataOutputStream(conn.getOutputStream());
            String string = contentsBuilder.toString();
			byte[] bytes = string.getBytes();
            for (int i = 0;i < bytes.length; i++){
                os.writeByte(bytes[i]);
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
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
	            String line;
	            while((line=br.readLine()) != null)
	            {
	            	resultBuilder.append(line);
	            }
	            result = resultBuilder.toString();
            	throw new HttpException("HTTPS STATUS = " + conn.getResponseCode() + "\r\n REQUEST_URL = " + url + "\r\n ERR_CONTENT = " + result);
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

    public static Document getXml(String uri, List<NameValuePair> headers, List<NameValuePair> params) {
        Document doc = null;
        HttpsURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(uri + getQuery(params));
            conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod(HTTPS_GET);
            setContext(conn);
            setHostnameVerifier(conn);
            //setHeaders(conn, headers);

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = builder.parse(new InputSource(conn.getInputStream()));
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOGGER.warn(e);
                }
            }
            try {
                conn.disconnect();
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        return doc;
    }

    private static void setContext(HttpsURLConnection conn) {
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,
                            new X509TrustManager[] { new LooseTrustManager() },
                            new SecureRandom());
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            throw new CoreRuntimeException(e);
        }
    }

    private static void setHostnameVerifier(HttpsURLConnection conn) {
        conn.setHostnameVerifier(new LooseHostnameVerifier());
    }

    private static void setHeaders(HttpsURLConnection conn, List<NameValuePair> headers) {
        if(headers != null && headers.size() > 0) {
            for (NameValuePair nameValuePair : headers) {
                conn.setRequestProperty(nameValuePair.getName(), nameValuePair.getValue());
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

}
