package jp.co.edi_java.app.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;

import jp.co.edi_java.app.dao.MConstantsDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.google.TArchiveFileDao;
import jp.co.edi_java.app.dao.google.TArchiveFolderDao;
import jp.co.edi_java.app.dao.google.TGoogleDriveDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dto.GoogleDriveDto;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.google.TArchiveFileEntity;
import jp.co.edi_java.app.entity.google.TArchiveFolderEntity;
import jp.co.edi_java.app.entity.google.TGoogleDriveEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.form.GoogleDriveForm;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.google.GoogleDriveApi;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class GoogleDriveService {

	@Autowired
    public TGoogleDriveDao tGoogleDriveDao;
	@Autowired
    public TGyousyaAccountDao tGyousyaAccountDao;
	@Autowired
    public TArchiveFolderDao tArchiveFolderDao;
	@Autowired
    public TArchiveFileDao tArchiveFileDao;
	@Autowired
	public TOrderDao tOrderDao;
	//定数マスタ
	@Autowired
	public MConstantsDao mConstantsDao;

	private static final String FILE_TYPE_FOLDER = "0";
	private static final String FILE_TYPE_FILE = "1";

	// 環境毎のルートフォルダを用意
	private static String ROOT_FOLDER_PATH = "EDI_prod";
	private static String ARCHIVE_FOLDER_NAME = "archive_reports";
	private static String ARCHIVE_FOLDER_PATH = ROOT_FOLDER_PATH + "/" + ARCHIVE_FOLDER_NAME;

	private static String[] DOCUMENT_TYPES = {"order", "delivery", "workReport"};
	private static Map<String, String> REPORT_TYPE_MAP = new HashMap<String, String>();
	static {
		REPORT_TYPE_MAP.put("order", "10");
		REPORT_TYPE_MAP.put("delivery", "20");
		REPORT_TYPE_MAP.put("workReport", "30");
	}


	private GoogleDriveService( @Value("${googledrive.root.folder}") String rootFolder) {
		ROOT_FOLDER_PATH = rootFolder;
		ARCHIVE_FOLDER_PATH = ROOT_FOLDER_PATH + "/" + ARCHIVE_FOLDER_NAME;
	}

	/**
     * Archive Folder
     */
    public List<TArchiveFolderEntity> selectArchiveFolder(TArchiveFolderEntity folder) {
    	return tArchiveFolderDao.selectList(folder);
    }

	/**
     * Archive File
     */
    public TArchiveFileEntity getArchiveFile(String fileId) {
    	return tArchiveFileDao.selectByFileId(fileId);
    }

    /**
     * Archive File
     */
    public TArchiveFileEntity getArchiveFileWithGyousyaCode(String fileId, String gyousyaCode) {
    	TArchiveFileEntity ret = null;
    	if (Objects.nonNull(fileId) && Objects.nonNull(gyousyaCode)) {
    		ret = tArchiveFileDao.selectByFileIdWithGyousyaCode(fileId, gyousyaCode);
    	}
    	return ret;
    }

	/**
     * Archive File list
     */
    public Map<String, List<GoogleDriveDto>> getArchiveFileByTerm(GoogleDriveForm form) {
    	Map<String, List<GoogleDriveDto>> ret = new HashMap<String, List<GoogleDriveDto>>();
    	int prevCount = 3;
    	if (Objects.nonNull(form) && Objects.nonNull(form.getPrevMonthCount())) {
    		prevCount = form.getPrevMonthCount();
    	}
    	String prevMonth = getPastMonth(prevCount);
    	String thisMonth = getPastMonth(0);
		String fromYearMonth = prevMonth.substring(0, 4) + prevMonth.substring(4, 6);
		String toYearMonth = thisMonth.substring(0, 4) + thisMonth.substring(4, 6);
		String gyousyaCode = form.getGyousyaCode();
		List<String> documentTypes = Arrays.asList(DOCUMENT_TYPES);

		// order, delivery, workReport フォルダ
		for (String doctype : documentTypes) {
			String reportType = REPORT_TYPE_MAP.get(doctype);
			List<GoogleDriveDto> fileList = tArchiveFileDao.selectListByTerm(fromYearMonth, toYearMonth, reportType, gyousyaCode);
			ret.put(doctype, fileList);
		}
    	return ret;
    }

    /**
     * Google Drive ファイルダウンロード
     */
    public String downloadFile(GoogleDriveForm form) {
    	String filePath = null;
    	if (Objects.nonNull(form) && Objects.nonNull(form.getFileId()) && Objects.nonNull(form.getGyousyaCode())) {
    		TArchiveFileEntity file = getArchiveFileWithGyousyaCode(form.getFileId(),form.getGyousyaCode());
    		String downloadPath = form.getDownloadPath();
    		UUID uuid = UUID.randomUUID();
    		if (Objects.isNull(downloadPath)) {
    			downloadPath = CommonConsts.OUTPUT_FILE_DIR + uuid;
    		}
    		Drive service = GoogleDriveApi.connectGoogleDrive();
    		filePath = GoogleDriveApi.downloadFile(service, file.getFileId(), downloadPath, file.getFileId());
    	}
    	return filePath;
    }

    /**
     * Google Drive table insert or update
     */
    public void upsertGoogleDrive(TGoogleDriveEntity googleDrive) {
    	TGoogleDriveEntity saveObj = null;
    	if (Objects.nonNull(googleDrive) && Objects.nonNull(googleDrive.getFileId())) {
    		saveObj = tGoogleDriveDao.selectByFileId(googleDrive.getFileId());
    	} else {
    		return;
    	}
    	if (Objects.isNull(saveObj)) {
    		saveObj = new TGoogleDriveEntity();
    		saveObj.setFileId(googleDrive.getFileId());
        	saveObj.setFileName(googleDrive.getFileName());
        	saveObj.setFilePath(googleDrive.getFilePath());
        	saveObj.setFolderPath(googleDrive.getFolderPath());
        	saveObj.setFileType(googleDrive.getFileType());
        	saveObj.setParentFileId(googleDrive.getParentFileId());
        	tGoogleDriveDao.insert(saveObj);
    	} else {
    		saveObj.setFileId(googleDrive.getFileId());
    		saveObj.setFileName(googleDrive.getFileName());
        	saveObj.setFilePath(googleDrive.getFilePath());
        	saveObj.setFolderPath(googleDrive.getFolderPath());
        	saveObj.setFileType(googleDrive.getFileType());
        	saveObj.setParentFileId(googleDrive.getParentFileId());
        	tGoogleDriveDao.update(saveObj);
    	}
    }

    /**
     * Archive Folder insert or update
     */
    public void upsertArchiveFolder(TArchiveFolderEntity folder) {
    	TArchiveFolderEntity saveObj = null;
    	if (Objects.nonNull(folder) && Objects.nonNull(folder.getFolderId())) {
    		saveObj = tArchiveFolderDao.selectByFolderId(folder.getFolderId());
    	} else {
    		return;
    	}
    	if (Objects.isNull(saveObj)) {
    		saveObj = new TArchiveFolderEntity();
    		saveObj.setFolderId(folder.getFolderId());
        	saveObj.setFolderName(folder.getFolderName());
        	saveObj.setFolderPath(folder.getFolderPath());
        	saveObj.setGyousyaCode(folder.getGyousyaCode());
        	saveObj.setReportType(folder.getReportType());
        	saveObj.setReportYear(folder.getReportYear());
        	saveObj.setReportMonth(folder.getReportMonth());
        	saveObj.setReportYearMonth(folder.getReportYearMonth());
        	tArchiveFolderDao.insert(saveObj);
    	} else {
    		saveObj.setFolderId(folder.getFolderId());
        	saveObj.setFolderName(folder.getFolderName());
        	saveObj.setFolderPath(folder.getFolderPath());
        	saveObj.setGyousyaCode(folder.getGyousyaCode());
        	saveObj.setReportType(folder.getReportType());
        	saveObj.setReportYear(folder.getReportYear());
        	saveObj.setReportMonth(folder.getReportMonth());
        	saveObj.setReportYearMonth(folder.getReportYearMonth());
        	tArchiveFolderDao.update(saveObj);
    	}
    }

    /**
     * Archive File insert or update
     */
    public void upsertArchiveFile(TArchiveFileEntity file) {
    	TArchiveFileEntity saveObj = null;
    	if (Objects.nonNull(file) && Objects.nonNull(file.getFileId())) {
    		saveObj = getArchiveFile(file.getFileId());
    	} else {
    		return;
    	}
    	if (Objects.isNull(saveObj)) {
    		saveObj = new TArchiveFileEntity();
    		saveObj.setFileId(file.getFileId());
        	saveObj.setFileName(file.getFileName());
        	saveObj.setFilePath(file.getFilePath());
        	saveObj.setParentFolderId(file.getParentFolderId());
        	tArchiveFileDao.insert(saveObj);
    	} else {
    		saveObj.setFileId(file.getFileId());
        	saveObj.setFileName(file.getFileName());
        	saveObj.setFilePath(file.getFilePath());
        	saveObj.setParentFolderId(file.getParentFolderId());
        	tArchiveFileDao.update(saveObj);
    	}
    }

    /**
     * delete and insert archive folder info
     */
    public void refreshArchiveFolder(List<TArchiveFolderEntity> folderList, String year, String month) {
    	// year, month に合致するフォルダレコードをリフレッシュする
    	// フォルダで業者別年月別にzipファイルを管理しているため、不要フォルダのパスを削除すれば
    	// ひとまず画面上にゴミファイルを表示することはなくなる
    	// ファイルレコードのリフレッシュをするにはフォルダ配下の生きているファイル情報を取得する必要あり
    	//tArchiveFileDao.deleteByYearMonth(year, month);
    	tArchiveFolderDao.deleteByYearMonth(year, month);
    	for (TArchiveFolderEntity entity : folderList) {
    		upsertArchiveFolder(entity);
    	}
    }

    /**
     * Google Drive table delete
     */
    public void deleteGoogleDrive(TGoogleDriveEntity googleDrive) {
    	if (Objects.nonNull(googleDrive) && Objects.nonNull(googleDrive.getFileId())) {
    		tGoogleDriveDao.deleteByFileId(googleDrive.getFileId());
    		tArchiveFolderDao.deleteByFolderId(googleDrive.getFileId());
    		tArchiveFileDao.deleteByFolderId(googleDrive.getFileId());
    		tArchiveFileDao.deleteByFileId(googleDrive.getFileId());
    	} else {
    		return;
    	}
    }
    /**
     * Google Drive との同期
     */
//    public void syncGoogleDrive() {
//    	Drive drive = GoogleDriveApi.connectGoogleDrive();
//    	Map<String,Map<String,Object>> filesMap = GoogleDriveApi.getFileList(drive);
//
//    	// GoogleDriveに存在しないファイルIDレコードを削除
//    	List<TGoogleDriveEntity> driveRecordList = tGoogleDriveDao.selectAll();
//    	for (TGoogleDriveEntity entity : driveRecordList) {
//    		if (!filesMap.containsKey(entity.fileId)) {
//    			deleteGoogleDrive(entity);
//    			log.info("delete file id: " + entity.fileId);
//    		}
//    	}
//    }

    /**
     * Google Drive 初回環境用フォルダ構築
     */
    public void initializeGoogleDrive(String useMonth) {
		String year = "";
		String month = "";
		String lastMonth = getLastMonth();
		if (Objects.nonNull(useMonth)) {
			lastMonth = useMonth;
		}
		if (Objects.nonNull(lastMonth)) {
			year = lastMonth.substring(0, 4);
			month = lastMonth.substring(4, 6);
		} else {
			return;
		}

		List<String> documentTypes = Arrays.asList(DOCUMENT_TYPES);
		List<TArchiveFolderEntity> fileStorPathList = new ArrayList<TArchiveFolderEntity>();

		// Google Driveに存在しないファイルIDを削除
		// TODO 存在しているファイルも削除されるので要調査
		//syncGoogleDrive();

    	// root フォルダ
    	createFolderGoogleDrive(ROOT_FOLDER_PATH, ROOT_FOLDER_PATH, "root", null);
    	TGoogleDriveEntity rootDrive = tGoogleDriveDao.selectByFilePath(ROOT_FOLDER_PATH);
    	// archive フォルダ
    	createFolderGoogleDrive(ARCHIVE_FOLDER_NAME, ARCHIVE_FOLDER_PATH, ROOT_FOLDER_PATH, rootDrive.getFileId());
    	TGoogleDriveEntity archiveDrive = tGoogleDriveDao.selectByFilePath(ARCHIVE_FOLDER_PATH);
    	// 業者 フォルダ
    	List<TGyousyaAccountEntity> gyousyaList = tGyousyaAccountDao.selectAll();
    	for (TGyousyaAccountEntity entity : gyousyaList) {
    		String gyousyaRootPath = ARCHIVE_FOLDER_PATH + "/" + entity.getGyousyaCode();
    		createFolderGoogleDrive(entity.getGyousyaCode(), gyousyaRootPath, ARCHIVE_FOLDER_PATH, archiveDrive.getFileId());
    		TGoogleDriveEntity gyousyaDrive = tGoogleDriveDao.selectByFilePath(gyousyaRootPath);
    		String gyousyaYearPath = gyousyaRootPath + "/" + year;
    		String gyousyaMonthPath = gyousyaYearPath + "/" + month;
        	// 年 フォルダ
    		createFolderGoogleDrive(year, gyousyaYearPath, gyousyaRootPath, gyousyaDrive.getFileId());
    		TGoogleDriveEntity gyousyaYearDrive = tGoogleDriveDao.selectByFilePath(gyousyaYearPath);
    		// 月 フォルダ
    		createFolderGoogleDrive(month, gyousyaMonthPath, gyousyaYearPath, gyousyaYearDrive.getFileId());
    		TGoogleDriveEntity gyousyaMonthDrive = tGoogleDriveDao.selectByFilePath(gyousyaMonthPath);
    		// order, delivery, workReport フォルダ
    		for (String doctype : documentTypes) {
    			String gyousyaDocPath = gyousyaMonthPath + "/" + doctype;
    			String reportType = REPORT_TYPE_MAP.get(doctype);
    			createFolderGoogleDrive(doctype, gyousyaDocPath, gyousyaMonthPath, gyousyaMonthDrive.getFileId());
    			TGoogleDriveEntity gyousyaDocDrive = tGoogleDriveDao.selectByFilePath(gyousyaDocPath);
    			TArchiveFolderEntity arc = new TArchiveFolderEntity();
    			arc.setFolderId(gyousyaDocDrive.getFileId());
    			arc.setFolderName(gyousyaDocDrive.getFileName());
    			arc.setFolderPath(gyousyaDocDrive.getFilePath());
    			arc.setGyousyaCode(entity.getGyousyaCode());
    			arc.setReportType(reportType);
    			arc.setReportYear(year);
    			arc.setReportMonth(month);
    			arc.setReportYearMonth(year+month);
    			fileStorPathList.add(arc);
    		}
    	}
    	refreshArchiveFolder(fileStorPathList, year, month);
    }

    /**
     * Google Drive フォルダ作成
     */
    public void createFolderGoogleDrive(String fileName, String filePath, String folderPath, String parentFolderId) {
    	TGoogleDriveEntity googleDrive = new TGoogleDriveEntity();
    	googleDrive.setFileName(fileName);
    	googleDrive.setFilePath(filePath);
    	googleDrive.setFolderPath(folderPath);
    	googleDrive.setFileType(FILE_TYPE_FOLDER);
    	googleDrive.setParentFileId(parentFolderId);
    	// EDI Google drive管理テーブルへの存在チェック
    	int cnt = tGoogleDriveDao.countByFilePath(googleDrive.getFilePath());
    	if (cnt < 1) {
        	// connect google drive
        	Drive drive = GoogleDriveApi.connectGoogleDrive();
        	// root folder作成
        	String folderId = GoogleDriveApi.createFolder(drive, parentFolderId, fileName);
        	// folderId save
    		googleDrive.setFileId(folderId);
    		upsertGoogleDrive(googleDrive);
    	}
    }

    /**
     * Google Drive ファイルアップロード
     * @param String fileName for GoogleDrive
     * @param String filePath for GoogleDrive
     * @param String folderPath for GoogleDrive
     * @param String parentFolderId for GoogleDrive
     * @param String localFilePath - upload file full path
     * @param String mimeType - upload file type
     */
    public TGoogleDriveEntity uploadFileGoogleDrive(String fileName, String filePath, String folderPath, String parentFolderId, String localFilePath, String mimeType) {
    	TGoogleDriveEntity googleDrive = new TGoogleDriveEntity();
    	googleDrive.setFileName(fileName);
    	googleDrive.setFilePath(filePath);
    	googleDrive.setFolderPath(folderPath);
    	googleDrive.setFileType(FILE_TYPE_FILE);
    	googleDrive.setParentFileId(parentFolderId);
    	// EDI Google drive管理テーブルへの存在チェック
    	int cnt = tGoogleDriveDao.countByFilePath(googleDrive.getFilePath());
    	if (cnt < 1) {
        	// connect google drive
        	Drive drive = GoogleDriveApi.connectGoogleDrive();
        	// root folder作成
        	Map<String, String> fileProp = GoogleDriveApi.uploadFile(drive, parentFolderId, localFilePath, fileName, mimeType);
        	// fileId save
    		googleDrive.setFileId(fileProp.get("id"));
    		upsertGoogleDrive(googleDrive);
    	}
    	return googleDrive;
    }

    /**
     * Google Drive 請書アップロード
     */
    public void createArchiveOrder(String useMonth) {
		String from = getArchiveDateFrom(null);
		String to = getArchiveDateTo(null);
		String year = "";
		String month = "";
		String lastMonth = getLastMonth();
		if (Objects.nonNull(useMonth)) {
			lastMonth = useMonth;
			from = getArchiveDateFrom(lastMonth);
			to = getArchiveDateTo(lastMonth);
		}
		year = lastMonth.substring(0, 4);
		month = lastMonth.substring(4, 6);
		// アーカイブ対象
		TArchiveFolderEntity condition = new TArchiveFolderEntity();
		condition.setReportYear(year);
		condition.setReportMonth(month);
		condition.setReportType(REPORT_TYPE_MAP.get("order"));
		List<TArchiveFolderEntity> archiveFolderList = selectArchiveFolder(condition);
		// 請書ダウンロード and ZIP化してGoogle Driveへアップロード
		for (TArchiveFolderEntity folder : archiveFolderList) {
			String localFilePath = createOrderZip(folder.getGyousyaCode(), from, to);
			if (Objects.nonNull(localFilePath)) {
				String fileName = "order_" + folder.getGyousyaCode() + "_" + year + month + ".zip";
				TGoogleDriveEntity driveResult = uploadFileGoogleDrive(fileName, folder.getFolderPath() + "/" + fileName, folder.getFolderPath(), folder.getFolderId(), localFilePath, "application/zip");
				TArchiveFileEntity archiveFile = new TArchiveFileEntity();
				archiveFile.setFileId(driveResult.getFileId());
				archiveFile.setFileName(driveResult.getFileName());
				archiveFile.setFilePath(driveResult.getFilePath());
				archiveFile.setParentFolderId(driveResult.getParentFileId());
				upsertArchiveFile(archiveFile);
			}
		}
    }

    /**
     * Google Drive 請書ファイルzip化
     */
    private String createOrderZip(String gyousyaCode, String from, String to) {
    	UUID uuid = UUID.randomUUID();
		String folderPath = CommonConsts.OUTPUT_FILE_DIR + uuid;

		String zipFolder = "doc";
		String zipFileName = "doc.zip";
		String subFolderPath = "";
		String zipFilePath = "";
		if (Objects.equals(folderPath.substring(folderPath.length()-1),"/")) {
			subFolderPath = folderPath + zipFolder;
			zipFilePath = folderPath + zipFileName;
		} else {
			subFolderPath = folderPath + "/" + zipFolder;
			zipFilePath = folderPath + "/" + zipFileName;
		}
		List<TOrderEntity> orderList = tOrderDao.selectListForArchive(gyousyaCode, from, to);
		boolean created = false;

		for (TOrderEntity order : orderList) {
			String fileId = order.getFileIdOrder();
			if (Objects.nonNull(fileId)) {
				String fileName = order.getKoujiCode() + "_" + order.getGyousyaCode() + "_" + order.getOrderNumber() + ".pdf";
				FileApi.getFile(order.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_CONFIRMATION, fileId, "pdf", fileName, subFolderPath);
				created = true;
			}
		}

		// Zipファイル化
		File curdir = new File(folderPath);
		if (created && curdir.exists()) {
			String[] zipCommand = {"zip", "-r", zipFileName, zipFolder};
			Runtime runtime = Runtime.getRuntime();
			// zipコマンド
			processDone(zipCommand, runtime, curdir);
		}
		File zipfile = new File(zipFilePath);
		if (!zipfile.exists()) {
			return null;
		}
    	return zipFilePath;
    }

	private String getPastMonth(int prevcount) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, startDay);
		// 指定月数前の月を取得
		cal.add(Calendar.MONTH, -prevcount);
		Date pastMonth = cal.getTime();

		// 対象年月の算出
		resultDate = formatDateToString(pastMonth, "yyyyMMdd", "JST");
		log.info("getPastMonth: " + resultDate);
		return resultDate;
	}

	private String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		cal.set(Calendar.DATE, startDay);
		// 先月を取得
		cal.add(Calendar.MONTH, -1);
		Date lastMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(lastMonth, "yyyyMMdd", "JST");
		log.info("getLastMonth: " + resultDate);
		return resultDate;
	}

	private String getArchiveDateFrom(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.JAPAN);
			cal.setTime(nowDate);
			// 指定月は前月のため現在月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 先月を取得
		cal.set(Calendar.DATE, startDay);
		cal.add(Calendar.MONTH, -1);
		// 先月2日を取得
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lastMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(lastMonth, "yyyy-MM-dd HH:mm:ss", "JST");
		log.info("getArchiveDateFrom: " + resultDate);
		return resultDate;
	}

	private String getArchiveDateTo(String useMonth) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		// 本日の日付を取得
		Date nowDate = new Date();
		cal.setTime(nowDate);
		if (Objects.nonNull(useMonth)) {
			nowDate = parseDateStringToDate(useMonth, "yyyyMM", Locale.JAPAN);
			cal.setTime(nowDate);
			// 指定月は前月のため現在月に修正
			cal.add(Calendar.MONTH, 1);
		}

		// 月初日
		int startDay = cal.getActualMinimum(Calendar.DATE);
		// 当月1日を取得
		cal.set(Calendar.DATE, startDay);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		Date thisMonth = cal.getTime();

		// アーカイブ対象年月の算出
		resultDate = formatDateToString(thisMonth, "yyyy-MM-dd HH:mm:ss", "JST");
		log.info("getArchiveDateTo: " + resultDate);
		return resultDate;
	}

	private Date parseDateStringToDate(String date, String format, Locale loc) {
		Date ret = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format, loc);
			ret = sdf.parse(date);
		} catch (ParseException e) {
			log.info(e.getMessage());
		}
		return ret;
	}

	private String formatDateToString(Date date, String format, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(date);
	}

	/** 外部プロセスの実行 */
	private void processDone(String[] Command, Runtime runtime, File dir) {
		Process p = null;
        try {
            p = runtime.exec(Command,null,dir);
        } catch (IOException e) {
        	throw new CoreRuntimeException(e.getMessage());
        }
        try {
            p.waitFor(); // プロセスが正常終了するまで待機
        } catch (InterruptedException e) {
        	throw new CoreRuntimeException(e.getMessage());
        }
	}

}
