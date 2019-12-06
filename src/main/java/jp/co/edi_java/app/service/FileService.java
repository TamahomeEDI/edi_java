package jp.co.edi_java.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.MKarekiDao;
import jp.co.edi_java.app.entity.MKarekiEntity;
import jp.co.edi_java.app.form.FileForm;
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

//	/** zipで一括ダウンロード*/
//	public String multiDownload(FileForm form) {
//		UUID uuid = UUID.randomUUID();
//        String sessionId = uuid.toString();
//		String fileName = "";
//		// 1: 請書、2: 納品出来高報告書
//		String downloadType = form.getDownloadType();
//		String folderPath = "";
//		List<String> orderNumberList = form.getOrderNumberList();
//		if (Objects.nonNull(downloadType)) {
//			if (Objects.equals(downloadType, "1")) {
//				// 請書ダウンロード
//				List<TOrderEntity> orderList = orderService.selectInfo(orderNumberList);
//				for (TOrderEntity order : orderList) {
//					String fileId = order.getFileIdOrder();
//					FileApi.getFile(order.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo(), form.getFileId(), form.getFileType(), form.getFileName());
//				}
//			} else if (Objects.equals(downloadType, "2")) {
//				// 納品書、出来高報告書ダウンロード
//				List<TDeliveryEntity> deliveryList = deliveryService.selectListByMultiOrder(orderNumberList, "0");
//				List<TWorkReportEntity> workReportList = workReportService.selectListByMultiOrder(orderNumberList, "0");
//				for (TDeliveryEntity delivery : deliveryList) {
//					String fileId = delivery.getFileId();
//				}
//				for (TWorkReportEntity workReport : workReportList) {
//					String fileId = workReport.getFileId();
//				}
//
//			}
//		}
//		// downloadType=1 TOrder load by ordernumber
//		// downloadType=2 TDelivery,TWorkReport load by ordernumber
//		// 1つのフォルダにダウンロードし、zipコマンドで固める
//		// zipコマンドはRuntimeで実行する
//
//		//FileApi.getFile(form.getKoujiCode(), form.getToshoCode(), form.getFileCode(), form.getFileNo(), form.getFileId(), form.getFileType(), form.getFileName());
//		return fileName;
//	}
}
