package jp.co.edi_java.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    					if (fileName.length() >= gyousyaCodeLen) {
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
    					String extension = f.getName().substring(f.getName().lastIndexOf("."));
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
    					CommonUtils.processDone(zipCommand, curdir, timeOutSec);
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
