package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.entity.syain.MSyainEntity;
import jp.co.edi_java.app.form.AuthForm;
import jp.co.edi_java.app.service.AuthService;
import jp.co.edi_java.app.service.PartnerService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("auth")
public class AuthController extends BaseController {

	//ユーザ区分
	public String USER_FLG_USER = "1";		//タマホーム
	public String USER_FLG_PARTNER = "0";	//業者

	@Autowired
	public AuthService authService;

	@Autowired
	public PartnerService partnerService;

	/** 社員ログイン */
	@RequestMapping("/loginUser")
	public ResponseEntity loginUser(@Validated AuthForm form) {
		MSyainEntity syain = authService.userLogin(form.syainCode, form.password);
		if(syain == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_900);
		}
		super.setResponseData("ret", syain);
		return super.response();
	}

	/** 業者ログイン */
	@RequestMapping("/loginPartner")
	public ResponseEntity loginPartner(@Validated AuthForm form) {
		//業者コードとパスワードでアカウント情報を取得
		TGyousyaAccountEntity gyousyaAccount = authService.partnerLogin(form.gyousyaCode, form.password);
		if(gyousyaAccount == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_900);
		}
		//業者情報の取得
		MGyousyaEntity gyousya = partnerService.select(form.gyousyaCode);
		if(gyousya == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_900);
		}
		super.setResponseData("ret", gyousya);
		return super.response();
	}


}
