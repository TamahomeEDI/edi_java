package jp.co.edi_java.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TKaikeiKijunEntity;
import jp.co.edi_java.app.form.KaikeiKijunForm;
import jp.co.edi_java.app.service.KaikeiKijunService;
import jp.co.edi_java.app.service.PartnerService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("kaikeiKijun")
public class KaikeiKijunController extends BaseController {

	@Autowired
	public KaikeiKijunService kaikeiKijunService;

	@Autowired
	public PartnerService partnerService;

	/** 業者番号のhash化しURLを作成 */
	@RequestMapping("/convertToHash")
	public ResponseEntity convertToHash(@Validated KaikeiKijunForm form) {
		List<TKaikeiKijunEntity> list = kaikeiKijunService.getAll();
		List<String> ret = kaikeiKijunService.convertToHash(list);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** hash化された業者番号を基に業者情報と完成基準の情報を取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated KaikeiKijunForm form) {
		TKaikeiKijunEntity kaikeiKijun = kaikeiKijunService.getByHashCode(form.hashCode);
		if(kaikeiKijun == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_790);
			return super.response();
		}

//		MGyousyaEntity gyousya = partnerService.select(gyousyaCode);
//		if(gyousya == null) {
//			super.setErrorCode(ResponseCode.ERROR_CODE_601);
//			return super.response();
//		}

		super.setResponseData("kaikeiKijun", kaikeiKijun);
//		super.setResponseData("gyousya", gyousya);
		return super.response();
	}

	/** 完成基準情報の更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated KaikeiKijunForm form) {
		String gyousyaCode = form.gyousyaCode;
		TKaikeiKijunEntity kaikeiKijun = kaikeiKijunService.get(gyousyaCode);
		if(kaikeiKijun == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_790);
			return super.response();
		}
		kaikeiKijunService.update(form);
		kaikeiKijunService.sendMail(gyousyaCode);
		super.setResponseData("ret", "OK");
		return super.response();
	}


}
