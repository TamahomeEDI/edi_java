package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dto.GyousyaAccountDto;
import jp.co.edi_java.app.dto.GyousyaDto;
import jp.co.edi_java.app.entity.gyousya.MGyousyaEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaMailaddressEntity;
import jp.co.edi_java.app.form.PartnerForm;
import jp.co.edi_java.app.service.PartnerService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("partner")
public class PartnerController extends BaseController {

	@Autowired
	public PartnerService partnerService;

	/** 業者情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated PartnerForm form) {
		String gyousyaCode = form.gyousyaCode;
		MGyousyaEntity partner = partnerService.select(gyousyaCode);
		if(partner == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_601);
		}
		TGyousyaAccountEntity partnerAccount = partnerService.selectAccount(gyousyaCode);
		List<TGyousyaMailaddressEntity> mailList = partnerService.selectMailaddressAll(gyousyaCode);
		Map<String, Object> ret = new HashMap<>();
		ret.put("partner", partner);
		ret.put("partnerAccount", partnerAccount);
		ret.put("mailaddressList", mailList);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** トークンからアカウント情報取得 */
	@RequestMapping("/getByToken")
	public ResponseEntity getByToken(@Validated PartnerForm form) {
		TGyousyaAccountEntity partnerAccount = partnerService.selectAccountByToken(form.getToken());
		if(partnerAccount == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_601);
			return super.response();
		}

		super.setResponseData("ret", partnerAccount);
		return super.response();
	}

	/** アカウント情報登録 */
	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated PartnerForm form) {
		String gyousyaCode = form.gyousyaCode;
		MGyousyaEntity partner = partnerService.select(gyousyaCode);
		if(partner == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_601);
			return super.response();
		}

		TGyousyaAccountEntity partnerAccount = partnerService.selectAccount(gyousyaCode);
		if(partnerAccount != null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_800);
			return super.response();
		}

//		TGyousyaMailaddressEntity partnerMailaddress = partnerService.selectByMailaddress(form.mailaddress);
//		if(partnerMailaddress != null) {
//			super.setErrorCode(ResponseCode.ERROR_CODE_801);
//			return super.response();
//		}

		partnerService.regist(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** アカウント情報更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated PartnerForm form) {
		partnerService.update(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** パスワード設定 */
	@RequestMapping("/setPassword")
	public ResponseEntity setPassword(@Validated PartnerForm form) {
		partnerService.setPassword(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** パスワード忘れ */
	@RequestMapping("/passwordForget")
	public ResponseEntity passwordForget(@Validated PartnerForm form) {
		partnerService.passwordForget(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 支店単位の業者情報一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated PartnerForm form) {
		List<GyousyaDto> list = partnerService.getList(form.eigyousyoCode, form.torihikiStatus);
		super.setResponseData("ret", list);
		return super.response();
	}

	/** 業者情報全件取得 */
	@RequestMapping("/getAll")
	public ResponseEntity getAll(@Validated PartnerForm form) {
		List<MGyousyaEntity> list = partnerService.getAll();
		super.setResponseData("ret", list);
		return super.response();
	}

	/** 見積作成時の業者検索 */
	@RequestMapping("/searchEstimate")
	public ResponseEntity search(@Validated PartnerForm form) {
		List<MGyousyaEntity> list = partnerService.searchEstimate(form);
		super.setResponseData("ret", list);
		return super.response();
	}

	/** アカウント発行リマインド */
	@RequestMapping("/remind")
	public ResponseEntity remind(@Validated PartnerForm form) {
		String gyousyaCode = form.gyousyaCode;
		TGyousyaAccountEntity partnerAccount = partnerService.selectAccount(gyousyaCode);
		if(partnerAccount == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_601);
		}

		partnerService.remind(gyousyaCode, form.syainCode);
		super.setResponseData("ret", "OK");
		return super.response();
	}
//
//	@RequestMapping("/chkMail")
//	public ResponseEntity chkMail(@Validated PartnerForm form) {
//		super.setResponseData("ret", partnerService.chkMail(form.getMailaddress(), form.getGyousyaCode()));
//		return super.response();
//	}

	/** アカウント発行時の業者検索 */
	@RequestMapping("/searchAccount")
	public ResponseEntity searchAccount(@Validated PartnerForm form) {
		List<GyousyaAccountDto> list = partnerService.searchAccount(form);
		int count = partnerService.countAccount(form);
		Map<String, Object> ret = new HashMap<>();
		ret.put("partnerList", list);
		ret.put("totalCount", count);
		super.setResponseData("ret", ret);
		return super.response();
	}


}
