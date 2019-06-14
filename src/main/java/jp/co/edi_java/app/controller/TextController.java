package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.TextForm;
import jp.co.edi_java.app.service.TextService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("text")
public class TextController extends BaseController {

	@Autowired
	public TextService textService;

	/** 注釈取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated TextForm form) {
		super.setResponseData("ret", textService.getList(form.formType));
		return super.response();
	}

}
