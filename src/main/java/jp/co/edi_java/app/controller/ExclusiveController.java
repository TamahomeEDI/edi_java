package jp.co.edi_java.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TExclusiveEntity;
import jp.co.edi_java.app.form.ExclusiveForm;
import jp.co.edi_java.app.service.ExclusiveService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("exclusive")
public class ExclusiveController extends BaseController {

	@Autowired
	public ExclusiveService exclusiveService;

	/**
	 * 排他制御用ロック取得機能
	 *
	 */
	@RequestMapping("/getLock")
	public ResponseEntity getLock(@Validated ExclusiveForm form) {
		Map<String,List<TExclusiveEntity>> result = exclusiveService.getLock(form);

		super.setResponseData("ret", result);
		return super.response();
	}

	/**
	 * 排他制御用ロック破棄機能
	 *
	 */
	@RequestMapping("/releaseLock")
	public ResponseEntity releaseLock(@Validated ExclusiveForm form) {
		exclusiveService.releaseLock(form);

		return super.response();
	}

	/**
	 * 排他制御用ロック取得機能
	 *
	 */
	@RequestMapping("/getMultiLock")
	public ResponseEntity getMultiLock(@Validated ExclusiveForm form) {
		Map<String,List<TExclusiveEntity>> result = exclusiveService.getMultiLock(form);

		super.setResponseData("ret", result);
		return super.response();
	}

	/**
	 * 排他制御用ロック破棄機能
	 *
	 */
	@RequestMapping("/releaseMultiLock")
	public ResponseEntity releaseMultiLock(@Validated ExclusiveForm form) {
		exclusiveService.releaseMultiLock(form);

		return super.response();
	}

}
