package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.MEigyousyoEntity;
import jp.co.edi_java.app.entity.MEigyousyoGroupEntity;
import jp.co.edi_java.app.form.EigyousyoForm;
import jp.co.edi_java.app.service.EigyousyoService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("eigyousyo")
public class EigyousyoController extends BaseController {

	@Autowired
	public EigyousyoService eigyousyoService;

	/** 支店情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated EigyousyoForm form) {
		MEigyousyoEntity eigyousyo = eigyousyoService.get(form.eigyousyoCode);
		if(eigyousyo == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_620);
			return super.response();
		}
		super.setResponseData("ret", eigyousyo);
		return super.response();
	}

	/** 社員支店情報リスト取得 */
	@RequestMapping("/getListByUser")
	public ResponseEntity getListByUser(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getListByUser(form.syainCode));
		return super.response();
	}

	/** 社員本店情報リスト取得 */
	@RequestMapping("/getListByUserHeadOffice")
	public ResponseEntity getListByUserHeadOffice(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getListByUserHeadOffice(form.syainCode));
		return super.response();
	}

	/** 業者支店情報リスト取得 */
	@RequestMapping("/getListByPartner")
	public ResponseEntity getListByPartner(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getListByPartner(form.gyousyaCode, form.eigyousyoGroupCode));
		return super.response();
	}

	/** 地区本部単位の支店情報一覧取得 */
	@RequestMapping("/getListByGroup")
	public ResponseEntity getListByGroup(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getListByGroup(form.eigyousyoGroupCode));
		return super.response();
	}

	/** 地区本部情報取得 */
	@RequestMapping("/getGroup")
	public ResponseEntity getGroup(@Validated EigyousyoForm form) {
		MEigyousyoGroupEntity group = eigyousyoService.getGroup(form.eigyousyoGroupCode);
		if(group == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_621);
			return super.response();
		}
		super.setResponseData("ret", group);
		return super.response();
	}

	/** 全地区本部一覧取得 */
	@RequestMapping("/getGroupList")
	public ResponseEntity getGroupList(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getGroupList());
		return super.response();
	}

	/** 社員地区本部一覧取得 */
	@RequestMapping("/getGroupListByUser")
	public ResponseEntity getGroupListByUser(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getGroupListByUser(form.syainCode));
		return super.response();
	}

	/** 業者地区本部一覧取得 */
	@RequestMapping("/getGroupListByPartner")
	public ResponseEntity getGroupListByPartner(@Validated EigyousyoForm form) {
		super.setResponseData("ret", eigyousyoService.getGroupListByPartner(form.gyousyaCode));
		return super.response();
	}


}
