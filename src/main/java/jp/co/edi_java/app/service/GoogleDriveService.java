package jp.co.edi_java.app.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.api.services.drive.Drive;

import jp.co.edi_java.app.dao.MConstantsDao;
import jp.co.edi_java.app.dao.TDeliveryDao;
import jp.co.edi_java.app.dao.TLocalDocumentListDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.TWorkReportDao;
import jp.co.edi_java.app.dao.google.TArchiveFileDao;
import jp.co.edi_java.app.dao.google.TArchiveFolderDao;
import jp.co.edi_java.app.dao.google.TGoogleDriveDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dto.GoogleDriveDto;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TLocalDocumentListEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.google.TArchiveFileEntity;
import jp.co.edi_java.app.entity.google.TArchiveFolderEntity;
import jp.co.edi_java.app.entity.google.TGoogleDriveEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.form.GoogleDriveForm;
import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.google.GoogleDriveApi;
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
	@Autowired
	public TDeliveryDao tDeliveryDao;
	@Autowired
	public TWorkReportDao tWorkReportDao;
	@Autowired
	public TLocalDocumentListDao tBillingCheckListDao;
	//定数マスタ
	@Autowired
	public MConstantsDao mConstantsDao;

	private static final String FILE_TYPE_FOLDER = "0";
	private static final String FILE_TYPE_FILE = "1";

	// 環境毎のルートフォルダを用意
	private static String ROOT_FOLDER_PATH = "EDI_prod";
	private static String ARCHIVE_FOLDER_NAME = "archive_reports";
	private static String ARCHIVE_FOLDER_PATH = ROOT_FOLDER_PATH + "/" + ARCHIVE_FOLDER_NAME;
	// Driveの業者コード別年月フォルダ直下に配置するドキュメント種類フォルダ
	private static String[] DOCUMENT_TYPES = {"order", "delivery", "workReport", "billingCheckList", "inspectionReceipt", "paymentDetail"};
	private static Map<String, String> REPORT_TYPE_MAP = new HashMap<String, String>();
	static {
		REPORT_TYPE_MAP.put("order", CommonConsts.DOCUMENT_TYPE_ORDER);
		REPORT_TYPE_MAP.put("delivery", CommonConsts.DOCUMENT_TYPE_DELIVERY);
		REPORT_TYPE_MAP.put("workReport", CommonConsts.DOCUMENT_TYPE_WORK_REPORT);
		REPORT_TYPE_MAP.put("billingCheckList", CommonConsts.DOCUMENT_TYPE_BILLING_CHECK_LIST);
		REPORT_TYPE_MAP.put("inspectionReceipt", CommonConsts.DOCUMENT_TYPE_INSPECTION_RECEIPT);
		REPORT_TYPE_MAP.put("paymentDetail", CommonConsts.DOCUMENT_TYPE_PAYMENT_DETAIL);
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
    	String prevMonth = CommonUtils.getPastMonthFirst(prevCount, "yyyyMM", "JST");
    	String thisMonth = CommonUtils.getPastMonthFirst(0, "yyyyMM", "JST");
		String fromYearMonth = prevMonth;
		String toYearMonth = thisMonth;
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
		String lastMonth = CommonUtils.getLastMonthFirst("yyyyMM", "JST");
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
    	} else {
    		// 登録済データを返す
    		googleDrive = tGoogleDriveDao.selectByFilePath(filePath);
    	}
    	return googleDrive;
    }

    /**
     * Google Drive 請書、納品書、出来高報告書アップロード
     */
    public void createArchiveReport(String reportName, String useMonth) {
		String from = "";
		String to = "";
		String year = "";
		String month = "";
		String lastMonth = CommonUtils.getLastMonthFirst("yyyyMM", "JST");
		if (Objects.equals(reportName, "delivery") || Objects.equals(reportName, "workReport")) {
			if (Objects.nonNull(useMonth)) {
				lastMonth = useMonth;
				from = CommonUtils.getArchiveDateFromByLastAccountingMonth(lastMonth);
				to = CommonUtils.getArchiveDateToByLastAccountingMonth(lastMonth);
			} else {
				from = CommonUtils.getArchiveDateFromByLastAccountingMonth(null);
				to = CommonUtils.getArchiveDateToByLastAccountingMonth(null);
			}
		} else {
			if (Objects.nonNull(useMonth)) {
				lastMonth = useMonth;
				from = CommonUtils.getArchiveDateFromByLastMonth(lastMonth);
				to = CommonUtils.getArchiveDateToByLastMonth(lastMonth);
			} else {
				from = CommonUtils.getArchiveDateFromByLastMonth(null);
				to = CommonUtils.getArchiveDateToByLastMonth(null);
			}
		}
		year = lastMonth.substring(0, 4);
		month = lastMonth.substring(4, 6);
		// アーカイブ対象
		TArchiveFolderEntity condition = new TArchiveFolderEntity();
		condition.setReportYear(year);
		condition.setReportMonth(month);
		condition.setReportType(REPORT_TYPE_MAP.get(reportName));
		condition.setIgnoreArchiveFinished("1");
		List<TArchiveFolderEntity> archiveFolderList = selectArchiveFolder(condition);
		// 請書ダウンロード and ZIP化してGoogle Driveへアップロード
		for (TArchiveFolderEntity folder : archiveFolderList) {
			String localFilePath = createReportZip(folder.getGyousyaCode(), from, to, REPORT_TYPE_MAP.get(reportName));
			if (Objects.nonNull(localFilePath)) {
				String fileName = reportName + "_" + folder.getGyousyaCode() + "_" + year + month + ".zip";
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
     * Google Drive 請書、納品書、出来高報告書ファイルzip化
     */
    private String createReportZip(String gyousyaCode, String from, String to, String reportType) {
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
		boolean created = false;
		if (Objects.equals(reportType, REPORT_TYPE_MAP.get("order"))) {
			created = getFileOrder(gyousyaCode, from, to, subFolderPath);
		} else if (Objects.equals(reportType, REPORT_TYPE_MAP.get("delivery"))) {
			created = getFileDelivery(gyousyaCode, from, to, subFolderPath);
		} else if (Objects.equals(reportType, REPORT_TYPE_MAP.get("workReport"))) {
			created = getFileWorkReport(gyousyaCode, from, to, subFolderPath);
		}

		// Zipファイル化
		File curdir = new File(folderPath);
		if (created && curdir.exists()) {
			String[] zipCommand = {"zip", "-r", zipFileName, zipFolder};
			long timeOutSec = 5 * 60;
			// zipコマンド
			CommonUtils.processDone(zipCommand, curdir, timeOutSec, null);

		}
		File zipfile = new File(zipFilePath);
		if (!zipfile.exists()) {
			return null;
		}
    	return zipFilePath;
    }
    /**
     * 発注書のダウンロード
     *
     * @param gyousyaCode
     * @param from
     * @param to
     * @param subFolderPath
     * @return
     */
    private boolean getFileOrder(String gyousyaCode, String from, String to, String subFolderPath) {
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
		return created;
    }
    /**
     * 納品書のダウンロード
     *
     * @param gyousyaCode
     * @param from
     * @param to
     * @param subFolderPath
     * @return
     */
    private boolean getFileDelivery(String gyousyaCode, String from, String to, String subFolderPath) {
		List<TDeliveryEntity> deliveryList = tDeliveryDao.selectListForArchive(gyousyaCode, from, to, CommonConsts.REMAND_FLG_OFF);
		List<TDeliveryEntity> deliveryRemandList = tDeliveryDao.selectListForArchive(gyousyaCode, from, to, CommonConsts.REMAND_FLG_ON);
		boolean created = false;

		for (TDeliveryEntity delivery : deliveryList) {
			String fileId = delivery.getFileId();
			if (Objects.nonNull(fileId)) {
				String fileName = delivery.getKoujiCode() + "_" + delivery.getGyousyaCode() + "_" + delivery.getOrderNumber() + "_" + delivery.getDeliveryNumber() + ".pdf";
				FileApi.getFile(delivery.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_DELIVERY, fileId, "pdf", fileName, subFolderPath);
				created = true;
			}
		}
		for (TDeliveryEntity delivery : deliveryRemandList) {
			String fileId = delivery.getFileId();
			if (Objects.nonNull(fileId)) {
				String fileName = delivery.getKoujiCode() + "_" + delivery.getGyousyaCode() + "_" + delivery.getOrderNumber() + "_" + delivery.getDeliveryNumber() + "_ng.pdf";
				FileApi.getFile(delivery.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_DELIVERY, fileId, "pdf", fileName, subFolderPath);
				created = true;
			}
		}
		return created;
    }
    /**
     * 出来高報告書のダウンロード
     *
     * @param gyousyaCode
     * @param from
     * @param to
     * @param subFolderPath
     * @return
     */
    private boolean getFileWorkReport(String gyousyaCode, String from, String to, String subFolderPath) {
		List<TWorkReportEntity> workReportList = tWorkReportDao.selectListForArchive(gyousyaCode, from, to, CommonConsts.REMAND_FLG_OFF);
		List<TWorkReportEntity> workReportRemandList = tWorkReportDao.selectListForArchive(gyousyaCode, from, to, CommonConsts.REMAND_FLG_ON);
		boolean created = false;

		for (TWorkReportEntity workReport : workReportList) {
			String fileId = workReport.getFileId();
			if (Objects.nonNull(fileId)) {
				String fileName = workReport.getKoujiCode() + "_" + workReport.getGyousyaCode() + "_" + workReport.getOrderNumber() + "_" + workReport.getWorkReportNumber()+ ".pdf";
				FileApi.getFile(workReport.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_WORK_REPORT, fileId, "pdf", fileName, subFolderPath);
				created = true;
			}
		}
		for (TWorkReportEntity workReport : workReportRemandList) {
			String fileId = workReport.getFileId();
			if (Objects.nonNull(fileId)) {
				String fileName = workReport.getKoujiCode() + "_" + workReport.getGyousyaCode() + "_" + workReport.getOrderNumber() + "_" + workReport.getWorkReportNumber() + "_ng.pdf";
				FileApi.getFile(workReport.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_WORK_REPORT, fileId, "pdf", fileName, subFolderPath);
				created = true;
			}
		}
		return created;
    }

    /**
     * Google Drive 受注明細一覧、支払明細、検収明細等の家歴以外のファイルアップロード
     */
    public void createArchiveLocalDocument(String reportName, String useMonth) {
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
		// アーカイブ対象業者
		TArchiveFolderEntity condition = new TArchiveFolderEntity();
		condition.setReportYear(year);
		condition.setReportMonth(month);
		condition.setReportType(REPORT_TYPE_MAP.get(reportName));
		condition.setIgnoreArchiveFinished("1");
		List<TArchiveFolderEntity> archiveFolderList = selectArchiveFolder(condition);
		// アーカイブ対象チェックリスト
		TLocalDocumentListEntity billingCond = new TLocalDocumentListEntity();
		billingCond.setReportYear(year);
		billingCond.setReportMonth(month);
		billingCond.setDocumentType(REPORT_TYPE_MAP.get(reportName));
		billingCond.setCompleteFlg("0");
		List<TLocalDocumentListEntity> billingList = tBillingCheckListDao.selectList(billingCond);
		// 業者別チェックリストマップ生成
		Map<String, List<TLocalDocumentListEntity>> billingMap = new HashMap<String, List<TLocalDocumentListEntity>>();
		for (TLocalDocumentListEntity entity : billingList) {
			if (! billingMap.containsKey(entity.getGyousyaCode())) {
				billingMap.put(entity.getGyousyaCode(), new ArrayList<TLocalDocumentListEntity>());
			}
			billingMap.get(entity.getGyousyaCode()).add(entity);
		}
		// ZIP済チェックリストをGoogle Driveへアップロード
		for (TArchiveFolderEntity folder : archiveFolderList) {
			List<TLocalDocumentListEntity> checkList = (billingMap.containsKey(folder.getGyousyaCode())) ? billingMap.get(folder.getGyousyaCode()) : null;
			if (Objects.nonNull(checkList)) {
				// zipファイル1つになっている前程
				TLocalDocumentListEntity billing = checkList.get(0);
				String localFilePath = billing.getFilePath();

				if (Objects.nonNull(localFilePath)) {
					String fileName = reportName + "_" + folder.getGyousyaCode() + "_" + year + month + ".zip";
					TGoogleDriveEntity driveResult = uploadFileGoogleDrive(fileName, folder.getFolderPath() + "/" + fileName, folder.getFolderPath(), folder.getFolderId(), localFilePath, "application/zip");
					TArchiveFileEntity archiveFile = new TArchiveFileEntity();
					archiveFile.setFileId(driveResult.getFileId());
					archiveFile.setFileName(driveResult.getFileName());
					archiveFile.setFilePath(driveResult.getFilePath());
					archiveFile.setParentFolderId(driveResult.getParentFileId());
					upsertArchiveFile(archiveFile);
					// 処理済にする
					billing.setCompleteFlg("1");
					tBillingCheckListDao.update(billing);
				}
			}
		}
    }

}
