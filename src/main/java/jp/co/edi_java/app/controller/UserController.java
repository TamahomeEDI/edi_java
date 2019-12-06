package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.UserForm;
import jp.co.edi_java.app.service.UserService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	public UserService userService;

	/** 社員情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated UserForm form) {
		MSyainEntity syain = userService.get(form.syainCode);
		if(syain == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_600);
		}

		super.setResponseData("ret", syain);
		return super.response();
	}

	//未使用
//	/** 支店リスト単位の社員支店情報リスト取得 */
//	@RequestMapping("/getListByEigyousyoList")
//	public ResponseEntity getListByEigyousyoList(@Validated UserForm form) {
//		super.setResponseData("ret", userService.getListByEigyousyoList(form.eigyousyoCodeList));
//		return super.response();
//	}

	/** 支店単位の社員支店情報リスト取得 */
	@RequestMapping("/getListByEigyousyo")
	public ResponseEntity getListByEigyousyo(@Validated UserForm form) {
		super.setResponseData("ret", userService.getListByEigyousyo(form.eigyousyoCode));
		return super.response();
	}

	@RequestMapping("/getListBySyokusyu3")
	public ResponseEntity getListBySyokusyu3(@Validated UserForm form) {
		super.setResponseData("ret", userService.getListBySyokusyu3(form.eigyousyoCode));
		return super.response();
	}

	/** 社員コード デコード結果取得 */
	@RequestMapping("/decodeSyainCode")
	public ResponseEntity decodeSyainCode(@Validated UserForm form) {
		String syainCode = userService.decodeSyainCode(form);
		//納品書番号返却
		super.setResponseData("ret", syainCode);
		return super.response();
	}
}
