package jp.co.edi_java.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dto.GoogleDriveDto;
import jp.co.edi_java.app.form.GoogleDriveForm;
import jp.co.edi_java.app.service.GoogleDriveService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Scope("request")
@RequestMapping("googleDrive")
public class GoogleDriveController extends BaseController {

	@Autowired
	public GoogleDriveService googleDriveService;

	/** ArchiveFile ダウンロード */
	@RequestMapping("/download")
	public ResponseEntity download(@Validated GoogleDriveForm form) {
		super.setResponseData("ret", googleDriveService.downloadFile(form));
		return super.response();
	}

	/** ArchiveFile 単体ロード */
	@RequestMapping("/getArchiveFile")
	public ResponseEntity getArchiveFile(@Validated GoogleDriveForm form) {
		super.setResponseData("ret", googleDriveService.getArchiveFileWithGyousyaCode(form.getFileId(), form.getGyousyaCode()));
		return super.response();
	}

	/** ArchiveFile 複数ロード */
	@RequestMapping("/getArchiveFileByTerm")
	public ResponseEntity getArchiveFileByTerm(@Validated GoogleDriveForm form) {
		Map<String, List<GoogleDriveDto>> ret = googleDriveService.getArchiveFileByTerm(form);
		for (String doctype : ret.keySet()) {
			super.setResponseData(doctype, ret.get(doctype));
		}
		return super.response();
	}



}
