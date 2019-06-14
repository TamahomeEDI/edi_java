package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.MKoujiEntity;
import jp.co.edi_java.app.form.KoujiForm;
import jp.co.edi_java.app.service.KoujiService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("kouji")
public class KoujiController extends BaseController {

	@Autowired
	public KoujiService koujiService;

	/** 工事情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated KoujiForm form) {
		MKoujiEntity kouji = koujiService.get(form.koujiCode);
		if(kouji == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_610);
		}

		super.setResponseData("ret", kouji);
		return super.response();
	}

	/** 工事情報一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated KoujiForm form) {
		super.setResponseData("ret", koujiService.getList(form.eigyousyoCode, form.syainCode, form.kanseiKbn, form.projectTypeCode));
		return super.response();
	}

	/** 工事区分一覧取得 */
	@RequestMapping("/getProjectType")
	public ResponseEntity getProjectType(@Validated KoujiForm form) {
		super.setResponseData("ret", koujiService.getProjectType());
		return super.response();
	}


}
