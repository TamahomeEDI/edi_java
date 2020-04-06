package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.service.BillingCheckListService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("billingCheckList")
public class BillingCheckListController extends BaseController {

	@Autowired
	public BillingCheckListService billingCheckListService;

	/** チェックリスト対象カウント */
	@RequestMapping("/countCheckList")
	public ResponseEntity countCheckList(@Validated SearchForm form) {
		super.setResponseData("ret", billingCheckListService.countCheckList(form));
		return super.response();
	}

	/** チェックリスト出力 */
	@RequestMapping("/createCheckList")
	public ResponseEntity createCheckList(@Validated SearchForm form) {
		billingCheckListService.createCheckList(form);
		return super.response();
	}

}
