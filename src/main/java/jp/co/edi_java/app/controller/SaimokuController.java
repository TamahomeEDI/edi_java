package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.MSaimokuKousyuEntity;
import jp.co.edi_java.app.form.SaimokuForm;
import jp.co.edi_java.app.service.SaimokuService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("saimoku")
public class SaimokuController extends BaseController {

	@Autowired
	public SaimokuService saimokuService;

	/** 細目工種取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated SaimokuForm form) {
		MSaimokuKousyuEntity saimoku = saimokuService.get(form.saimokuKousyuCode);
		if(saimoku == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_690);
		}

		super.setResponseData("ret", saimoku);
		return super.response();
	}

	/** 細目工種全件取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated SaimokuForm form) {
		super.setResponseData("ret", saimokuService.getList());
		return super.response();
	}


}
