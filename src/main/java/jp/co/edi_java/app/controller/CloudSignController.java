package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.CloudSignForm;
import jp.co.edi_java.app.service.CloudSignService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("cloudSign")
public class CloudSignController extends BaseController {

	@Autowired
	public CloudSignService cloudSignService;

	/**
	 * リマインド機能
	 * ヒアリング対応 ⑤発注メールの有効期限切れ対応
	 * 発注管理画面から「再送」ボタンを押下
	 */
	@RequestMapping("/remind")
	public ResponseEntity remind(@Validated CloudSignForm form) {
		cloudSignService.remind(form.getDocumentId());
		super.setResponseData("ret", "OK");
		return super.response();
	}


}
