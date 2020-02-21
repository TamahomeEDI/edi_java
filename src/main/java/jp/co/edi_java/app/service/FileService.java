package jp.co.edi_java.app.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MKarekiDao;
import jp.co.edi_java.app.entity.MKarekiEntity;
import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TOrderEntity;
import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.form.FileForm;
import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.file.FileApi;

@Service
@Scope("request")
public class FileService {

	@Autowired
    public OrderService orderService;
	@Autowired
    public DeliveryService deliveryService;
	@Autowired
    public WorkReportService workReportService;

	@Autowired
    public MKarekiDao mKarekiDao;

	public Map<String, Object> upload(FileForm form) {
		return FileApi.postFile(form.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo(), form.getRegistSyainCode(), form.getFilePath(), form.getFileName(), form.getFileType());
	}

	public Map<String, Object> uploadTest(String koujiCode, String toshoCode, String fileCode, String fileNo, String registSyainCode, String filePath, String fileName, String fileType) {
		return FileApi.postFileTest(koujiCode, toshoCode, fileCode, fileNo, registSyainCode, filePath, fileName, fileType);
	}

	public String download(FileForm form) {
		return FileApi.getFile(form.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo(), form.getFileId(), form.getFileType(), form.getFileName());
	}

	public Map<String, Object> get(String fileId) {
		return FileApi.getFileInfo(fileId);
	}

	public Map<String, Object> getList(FileForm form) {
		return FileApi.getFileList(form.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo());
	}

	public List<MKarekiEntity> getKarekiList(String fileCode) {
		return mKarekiDao.selectList(fileCode);
	}

	public Map<String, Object> delete(FileForm form) {
		return FileApi.delete(form.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo(), form.getFileId());
	}

	/** zipで一括ダウンロード */
	public String multiDownload(FileForm form) {
		String folderPath = form.getFolderPath();
		if (Objects.isNull(folderPath)) {
			UUID uuid = UUID.randomUUID();
			folderPath = CommonConsts.OUTPUT_FILE_DIR + uuid;
		}
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
		// 1: 請書、2: 納品出来高報告書
		String downloadType = form.getDownloadType();
		List<String> orderNumberList = form.getOrderNumberList();
		boolean created = false;
		if (Objects.nonNull(downloadType)) {
			if (Objects.equals(downloadType, "1")) {
				// 請書ダウンロード
				List<TOrderEntity> orderList = orderService.selectInfo(orderNumberList);
				for (TOrderEntity order : orderList) {
					String fileId = order.getFileIdOrder();
					if (Objects.nonNull(fileId)) {
						String fileName = order.getKoujiCode() + "_" + order.getGyousyaCode() + "_" + order.getOrderNumber() + ".pdf";
						FileApi.getFile(order.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_CONFIRMATION, fileId, "pdf", fileName, subFolderPath);
						created = true;
					}
				}
			} else if (Objects.equals(downloadType, "2")) {
				// 納品書、出来高報告書ダウンロード
				List<TDeliveryEntity> deliveryList = deliveryService.selectListByMultiOrder(orderNumberList, null); // 差戻し含む
				List<TWorkReportEntity> workReportList = workReportService.selectListByMultiOrder(orderNumberList, null); // 差戻し含む
				for (TDeliveryEntity delivery : deliveryList) {
					String fileId = delivery.getFileId();
					if (Objects.nonNull(fileId)) {
						String fileName = delivery.getKoujiCode() + "_" + delivery.getGyousyaCode() + "_" + delivery.getOrderNumber() + "_" + delivery.getDeliveryNumber();
						if (Objects.equals(delivery.getRemandFlg(), "0")) {
							fileName = fileName + ".pdf";
						} else {
							fileName = fileName + "_ng.pdf";
						}
						FileApi.getFile(delivery.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_DELIVERY, fileId, "pdf", fileName, subFolderPath);
						created = true;
					}
				}
				for (TWorkReportEntity workReport : workReportList) {
					String fileId = workReport.getFileId();
					if (Objects.nonNull(fileId)) {
						String fileName = workReport.getKoujiCode() + "_" + workReport.getGyousyaCode() + "_" + workReport.getOrderNumber() + "_" + workReport.getWorkReportNumber();
						if (Objects.equals(workReport.getRemandFlg(), "0")) {
							fileName = fileName + ".pdf";
						} else {
							fileName = fileName + "_ng.pdf";
						}
						FileApi.getFile(workReport.getKoujiCode(), CommonConsts.FILE_TOSHO_CODE, CommonConsts.FILE_TYPE_DELIVERY, CommonConsts.FILE_NO_WORK_REPORT, fileId, "pdf", fileName, subFolderPath);
						created = true;
					}
				}
			}
		}

		// Zipファイル化
		File curdir = new File(folderPath);
		if (created && curdir.exists()) {
			String[] zipCommand = {"zip", "-r", zipFileName, zipFolder};
			long timeOutSec = 5 * 60;
			// zipコマンド
			CommonUtils.processDone(zipCommand, curdir, timeOutSec);
		}
		return zipFilePath;
	}

}
