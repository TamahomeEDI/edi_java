package jp.co.keepalive.springbootfw.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.logging.DumpUtil;

public class FileUtil {

    private static final String CONTENT_TYPE_STREAM = "application/octet-stream;";

    private static final String CONTENT_TYPE_JPEG = "image/jpeg";

//    private static final String CONTENT_DISPOSITION = "Content-Disposition";
//
//    private static final String ATTACHEMENT_FILENAME = "attachment; filename=";

    private static final int STREAM_BUFFER_SIZE = 1024*64;

    public static List<String> getFileList(String dirPath) {
        List<String> fileNameList = new ArrayList<>();

        File dir = new File(dirPath);
        File[] files1 = dir.listFiles();

        for (File file : files1) {
            fileNameList.add(file.getName());
        }

        return fileNameList;
    }

    public static void exeShell(String filePath) {
        List<String> list = new ArrayList<String>();
        list.add("/bin/sh");
        list.add(filePath);
        ProcessBuilder pb = new ProcessBuilder(list);
        try {
            pb.start();
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /**
     * CSVファイルをリストオブジェクトへ格納
     * @param filePath
     * @return
     */
    public static List<Map<String, String>> readCsvToMap(String filePath) {
        DumpUtil.dump("■readCsvToMap start");
        List<List<String>> csvData = readCsv(filePath, "SJIS");

        List<String> keyList = csvData.get(0);
        DumpUtil.dump("keyList.size()" + keyList.size());
        DumpUtil.dump(keyList);

        DumpUtil.dump("csvData.size() : " + csvData.size());
        List<Map<String, String>> dataList = new ArrayList<>();
        for(int i=1; i<csvData.size(); i++) {
            List<String> record = csvData.get(i);
            DumpUtil.dump("record.size() : " + record.size());
            DumpUtil.dump(record);
            //DumpUtil.dump(record);
            Map<String, String> data = new HashMap<>();
            for(int n=0; n<record.size(); n++) {
                String key = keyList.get(n).replace("\"", "");
                String camelKey = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
                data.put(camelKey, record.get(n).replace("\"", ""));
            }
            dataList.add(data);
        }

        return dataList;
    }

    /**
     * CSVファイルをリストオブジェクトへ格納
     * @param filePath
     * @param headerExclusionFlg
     * @return
     */
    public static List<List<String>> readCsv(String filePath) {
        return readCsv(filePath, "SJIS");
    }

    /**
     * CSVファイルをリストオブジェクトへ格納
     * @param filePath
     * @param headerExclusionFlg
     * @return
     */
    public static List<List<String>> readCsv(String filePath, String encode) {
        List<List<String>> recordList = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encode));

            String line;
            while ((line = br.readLine()) != null) {
                byte[] b = line.getBytes();
                line = new String(b, "UTF-8");
                recordList.add(Arrays.asList(line.split(",")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                //fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return recordList;
    }

    public static String readFirstStr(String filePath) {
        return readCsv(filePath).get(0).get(0);
    }

    public static String readFirstStrExcludeHeader(String filePath) {
        return readCsvExcludeHeader(filePath).get(0).get(0);
    }

    /**
     * CSVファイルをリストオブジェクトへ格納
     * @param filePath
     * @param headerExclusionFlg
     * @return
     */
    public static List<List<String>> readCsvExcludeHeader(String filePath) {
        return readCsvExcludeHeader(filePath, "UTF-8");
    }

    public static List<List<String>> readCsvExcludeHeader(String filePath, String encode) {
        List<List<String>> list = FileUtil.readCsv(filePath, encode);
        return list.subList(1, list.size());
    }

    /*
     * ファイル移動
     * 　from：移動元ファイルパス
     * 　to：移動先ファイルパス
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /*
     * ファイル移動
     * 　from：移動元ファイルパス
     * 　to：移動先ファイルパス
     */
    public static void moveFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        try {
        	FileUtils.moveFile(fromFile, toFile);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /*
     * ファイルコピー
     * 　from：移動元ファイルパス
     * 　to：移動先ファイルパス
     */
    public static void copyFile(String from, String to) {
        DumpUtil.dump("■■ファイルコピー■■");
        DumpUtil.dump("from path : " + from);
        DumpUtil.dump("to path : " + to);
        File fromFile = new File(from);
        File toFile = new File(to);
        try {
            FileUtils.copyFile(fromFile, toFile);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /*
     * ディレクトリコピー
     * 　from：移動元ファイルパス
     * 　to：移動先ファイルパス
     */
    public static void copyDirectory(String from, String to) {
        File fromDir = new File(from);
        File toDir = new File(to);
        try {
            FileUtils.copyDirectory(fromDir, toDir);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /*
     * ディレクトリ初期化
     * 　from：移動元ファイルパス
     * 　to：移動先ファイルパス
     */
    public static void cleanDirectory(String path){
        File dir = new File(path);
        try {
            FileUtils.cleanDirectory(dir);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    /*
     * ファイル削除
     * 　path：ファイルパス
     */
    public static void deleteFile(String path) {
        File deleteFile = new File(path);
        try {
            FileUtils.forceDelete(deleteFile);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static void writeJpegByStreaming(HttpServletResponse response, String filePath, String fileName){
        writeFile(response, CONTENT_TYPE_JPEG, filePath, fileName);
    }

    public static void writeFileByStreaming(HttpServletResponse response, String filePath, String fileName){
        writeFile(response, CONTENT_TYPE_STREAM, filePath, fileName);
    }

    public static void writePartialByStreaming(HttpServletResponse response, String filePath, String fileName, int from, int limit){
        writeFile(response, CONTENT_TYPE_STREAM, filePath, fileName, from, limit);
    }

    public static void writeFile(HttpServletResponse response, String contentType, String filePath, String fileName, int from, int limit){
        try {
            InputStream contentis = new FileInputStream(new File(filePath));
            response.setContentType(contentType);
            outByStreaming(response, contentis, from, limit);
        } catch (FileNotFoundException e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static void outByStreaming(HttpServletResponse response,
            InputStream contentis, int from, int limit) {
        try {
            OutputStream out = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(contentis);
            bis.skip(from);

            byte[] fbytes = new byte[STREAM_BUFFER_SIZE];
            int size = 0;
            while((size = bis.read(fbytes)) != -1) {
                if(limit >= STREAM_BUFFER_SIZE) {
                    out.write(fbytes, 0, size);
                } else {
                    out.write(fbytes, 0, limit);
                }
                limit -= size;
                if(limit<=0) break;
            }
            out.close();
            bis.close();
        } catch (IOException e) {
            DumpUtil.dump(e);
        }
    }

    public static void writeFile(HttpServletResponse response, String contentType, String filePath, String fileName){
        try {
            InputStream contentis = new FileInputStream(new File(filePath));
            response.setContentType(contentType);
//            response.setHeader(CONTENT_DISPOSITION, ATTACHEMENT_FILENAME
//                    + fileName);
            outByStreaming(response, contentis);
        } catch (FileNotFoundException e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static void outByStreaming(HttpServletResponse response,
            InputStream contentis) {
        try {
            OutputStream out = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(contentis);
            byte[] fbytes = new byte[STREAM_BUFFER_SIZE];
            int total = 0;
            int size = 0;
            while ((size = bis.read(fbytes)) != -1) {
                out.write(fbytes, 0, size);
                total+=size;
            }
            DumpUtil.dump("■■■stream total size = " + total);
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        }
    }

    public static void fileOutputByStream(InputStream is, String filePath) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            int size = 0;
            while ((size = is.read()) != -1) {
                os.write(size);
            }
        } catch (IOException e) {
            throw new CoreRuntimeException(e);
        } finally {
            try {
                if(os != null) {
                    os.close();
                }
            } catch (IOException e) {
                throw new CoreRuntimeException(e);
            }
        }
    }
}
