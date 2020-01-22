package jp.co.edi_java.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.FileForm;
import jp.co.edi_java.app.service.FileService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Scope("request")
@RequestMapping("file")
public class FileController extends BaseController {

	@Autowired
	public FileService fileService;

	/** ファイル登録 */
	@RequestMapping("/upload")
	public ResponseEntity upload(@Validated FileForm form) {
		Map<String, Object> ret = fileService.upload(form);
		String fileId = ret.get("file_id").toString();
		super.setResponseData("ret", fileId);
		return super.response();
	}

	/** ダウンロード */
	@RequestMapping("/download")
	public ResponseEntity download(@Validated FileForm form) {
		super.setResponseData("ret", fileService.download(form));
		return super.response();
	}

	/** 複数ダウンロード */
	@RequestMapping("/multiDownload")
	public ResponseEntity multiDownload(@Validated FileForm form) {
//		List<String> orderNumberList = form.getOrderNumberList();
//		String downloadType = form.getDownloadType();
//		String filePath = "";
//		if (Objects.nonNull(downloadType)) {
//			if (Objects.equals(downloadType, "1")) {
//				filePath = fileService.multiDownloadOrder(form);
//			} else if (Objects.equals(downloadType, "2")) {
//				filePath = fileService.multiDownloadDelivery(form);
//			}
//		}
		super.setResponseData("ret", fileService.multiDownload(form));
		return super.response();
	}

	/** ファイル情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated FileForm form) {
		super.setResponseData("ret", fileService.get(form.getFileId()));
		return super.response();
	}

	/** ファイル情報一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated FileForm form) {
		super.setResponseData("ret", fileService.getList(form));
		return super.response();
	}

	/** ファイル削除 */
	@RequestMapping("/delete")
	public ResponseEntity delete(@Validated FileForm form) {
		Map<String, Object> ret = fileService.delete(form);
		String fileId = ret.get("file_id").toString();
		super.setResponseData("ret", fileId);
		return super.response();
	}

	/** 家歴一覧取得 */
	@RequestMapping("/getKarekiList")
	public ResponseEntity getKarekiList(@Validated FileForm form) {
		super.setResponseData("ret", fileService.getKarekiList(form.fileCode));
		return super.response();
	}


}
