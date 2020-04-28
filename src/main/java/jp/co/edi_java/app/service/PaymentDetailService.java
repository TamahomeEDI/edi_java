package jp.co.edi_java.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.TLocalDocumentListDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.entity.TLocalDocumentListEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.file.FileApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class PaymentDetailService {

	@Autowired
    public TGyousyaAccountDao tGyousyaAccountDao;

	@Autowired
    public TLocalDocumentListDao tLocalDocumentListDao;

    /**
     * 支払通知書ファイルの存在チェックを行い、GoogleDriveアップロード用フォルダへ移動する
     *
     */
    public void getPaymentDetailFiles() {
    	String moveToPath = CommonConsts.OUTPUT_PAYMENT_DETAIL_DIR;
    	FileApi.createDirectories(moveToPath);
    	// マウント済である前提
    	String baseFolderPath = CommonConsts.INPUT_PAYMENT_DETAIL_DIR;
    	File fromDir = new File(baseFolderPath);
    	File toDir = new File(moveToPath);
    	if (!fromDir.exists() || !fromDir.isDirectory()) {
    		log.info("not exists : " + baseFolderPath);
    		return;
    	}
    	// 業者コード長
    	int gyousyaCodeLen = 7;
    	// タイムアウト設定
    	long timeOutSec = 2 * 60;
    	// 移動先フォルダのファイル削除
    	File [] toDirFileList = toDir.listFiles();
    	if (Objects.nonNull(toDirFileList)) {
			for (int i=0; i < toDirFileList.length; i++) {
				File f = toDirFileList[i];
				if (Objects.nonNull(f)) {
					if (f.isFile()) {
						f.delete();
					}
				}
			}
    	}
    	// ファイル名の文字コード変換
    	File [] beforeList = fromDir.listFiles();
		if (Objects.nonNull(beforeList)) {
			for (int i=0; i < beforeList.length; i++) {
				File f = beforeList[i];
				if (Objects.nonNull(f)) {
					log.info("file name: " + f.getName());

					String fileName = f.getName();
					String reg = "[^a-zA-Z0-9*-_.]+";
					String wildcard = fileName.replaceAll(reg, "*");
					log.info("wild card: " + wildcard);

					String gyousyaCode = "";
					String reg2 = "^A[0-9]{6}";
					Pattern p = Pattern.compile(reg2);
					Matcher m = p.matcher(fileName);
					// ファイル名の先頭が業者コード
					if (fileName.length() >= gyousyaCodeLen && m.find()) {
						gyousyaCode = fileName.substring(0, gyousyaCodeLen);
					}
					String extension = "";
					if (fileName.lastIndexOf(".") != -1) {
						extension = fileName.substring(fileName.lastIndexOf("."));
					}

					UUID uuid = UUID.randomUUID();
					String newName = gyousyaCode + "_" + uuid.toString() + extension;

					// 文字コード変換せず業者コード以下の文字列をUUIDに置き換え
					// 基本的に業者コードの重複したファイルは存在しない前提、1業者1ファイル
					String[] mvCommand = {"bash", "-c", "mv " + wildcard + " " + newName};
					CommonUtils.processDone(mvCommand, fromDir, timeOutSec, Locale.JAPAN);

				}
			}
		}
		// ファイルの移動
		File [] afterList = fromDir.listFiles();
		if (Objects.nonNull(afterList)) {
			for (int i=0; i < afterList.length; i++) {
				File f = afterList[i];
				if (Objects.nonNull(f)) {
					log.info("move target file name: " + f.getName());
					if (f.isFile()) {
						String fileName = f.getName();
						String gyousyaCode = "";
						String reg = "^A[0-9]{6}";
						Pattern p = Pattern.compile(reg);
						Matcher m = p.matcher(fileName);
						// ファイル名の先頭が業者コード
						if (fileName.length() >= gyousyaCodeLen && m.find()) {
							gyousyaCode = fileName.substring(0, gyousyaCodeLen);
						}

						if (!Objects.equals(gyousyaCode, "")) {
							String moveTo = moveToPath + fileName;
							String[] mvCommand = {"bash", "-c", "mv " + fileName + " " + moveTo};
							CommonUtils.processDone(mvCommand, fromDir, timeOutSec, Locale.JAPAN);
						}
					}
					// 移動元フォルダ内のファイル削除
					if (f.exists()) {
						f.delete();
					}
				}
			}
		}
    }
    /**
     * 支払明細書ファイルの存在チェックを行い、GoogleDriveアップロード用タスクデータを生成する
     *
     */
    public void createPaymentDetailList(String useMonth) {
		String from = CommonUtils.getArchiveDateFromByLastMonth(null);
		String to = CommonUtils.getArchiveDateToByLastMonth(null);
		String year = "";
		String month = "";
		String lastMonth = CommonUtils.getLastMonthFirst("yyyyMM", "JST");
		if (Objects.nonNull(useMonth)) {
			lastMonth = useMonth;
			from = CommonUtils.getArchiveDateFromByLastMonth(lastMonth);
			to = CommonUtils.getArchiveDateToByLastMonth(lastMonth);
		}
		year = lastMonth.substring(0, 4);
		month = lastMonth.substring(4, 6);

		List<TGyousyaAccountEntity> gyousyaList = tGyousyaAccountDao.selectAll();
		Map<String, Map<String, String>> gyousyaFileMap = createPaymentDetailMap(year + month);

		for (TGyousyaAccountEntity gyousya : gyousyaList) {
			String gyousyaCode = gyousya.getGyousyaCode();
			if (!gyousyaFileMap.containsKey(gyousyaCode)) {
				continue;
			}
			Map<String, String> filePathMap = gyousyaFileMap.get(gyousyaCode);
			if (Objects.isNull(filePathMap)) {
				continue;
			}
			if (filePathMap.containsKey("filePath") && filePathMap.containsKey("fileName")) {
				TLocalDocumentListEntity document = new TLocalDocumentListEntity();
				document.setGyousyaCode(gyousyaCode);
				document.setFileName(filePathMap.get("fileName"));
				document.setFilePath(filePathMap.get("filePath"));
				document.setReportYear(year);
				document.setReportMonth(month);
				document.setReportYearMonth(year + month);
				document.setDocumentType(CommonConsts.DOCUMENT_TYPE_PAYMENT_DETAIL);
				document.setCompleteFlg("0");
				tLocalDocumentListDao.insert(document);
			}
		}
    }

    /**
     * 指定フォルダ内にある支払明細書ファイルをzipファイルへ変換してファイルパスを返す
     *
     * @param date
     * @return Map<String, Map<String, String>> {gyousyaCode : {filePath: "" ,fileName: ""}}
     */
    public Map<String,Map<String, String>> createPaymentDetailMap(String date) {
    	Map<String,Map<String, String>> ret = new HashMap<String,Map<String, String>>();
    	String baseFolderPath = CommonConsts.OUTPUT_PAYMENT_DETAIL_DIR;
    	int gyousyaCodeLen = 7;

    	File dir = new File(baseFolderPath);

    	if (dir.exists() && dir.isDirectory()) {
    		File [] list = dir.listFiles();
    		if (Objects.nonNull(list)) {
    			Map<String,List<File>> fileMap = new HashMap<String,List<File>>();
    			for (File f : list) {
    				// ファイルのみ取得
    				if (Objects.nonNull(f) && f.isFile()) {
    					String fileName = f.getName();
    					String gyousyaCode = "";
    					String reg = "^A[0-9]{6}";
						Pattern p = Pattern.compile(reg);
						Matcher m = p.matcher(fileName);
						// ファイル名の先頭が業者コードに一致
    					if (fileName.length() >= gyousyaCodeLen && m.find()) {
    						gyousyaCode = fileName.substring(0, gyousyaCodeLen);
    					}
    					// 業者毎にファイルをまとめる
    					if (gyousyaCode != "") {
    						if (! fileMap.containsKey(gyousyaCode)) {
    							fileMap.put(gyousyaCode, new ArrayList<File>());
    						}
    						fileMap.get(gyousyaCode).add(f);
    					}
    				}
    			}
    			// zipファイル化
    			for (String key : fileMap.keySet()) {
    				List<File> fileList = fileMap.get(key);
    				UUID uuid = UUID.randomUUID();
    				String folderPath = baseFolderPath + uuid + "/";
    				String zipFolder = "doc";
    				String zipFileName = "doc.zip";
    				String subFolderPath = folderPath + zipFolder + "/";
    				// zip化用フォルダ作成
    				FileApi.createDirectories(subFolderPath);
    				for (int i=0; i < fileList.size(); i++) {
    					File f = fileList.get(i);
    					String extension = "";
    					if (f.getName().lastIndexOf(".") != -1) {
    						extension = f.getName().substring(f.getName().lastIndexOf("."));
    					}
    					String fileName = key + "_payment_" + i + "_" + date + extension;
    					File moveTo = new File(subFolderPath + fileName);
    					f.renameTo(moveTo);
    				}
    				// Zipファイル化
    				File curdir = new File(folderPath);
    				if (curdir.exists()) {
    					String[] zipCommand = {"zip", "-r", zipFileName, zipFolder};
    					long timeOutSec = 2 * 60;
    					// zipコマンド
    					CommonUtils.processDone(zipCommand, curdir, timeOutSec, null);
    				}
    				File zipfile = new File(folderPath + zipFileName);
    				if (zipfile.exists()) {
    					Map<String, String> pathMap = new HashMap<String, String>();
        				pathMap.put("filePath", folderPath + zipFileName);
        				pathMap.put("fileName", zipFileName);
        				ret.put(key, pathMap);
    				}
    			}
    		}
    	}

    	return ret;
    }
}
