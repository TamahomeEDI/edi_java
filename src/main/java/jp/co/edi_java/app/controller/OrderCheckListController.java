package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.service.OrderCheckListService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("orderCheckList")
public class OrderCheckListController extends BaseController {

	@Autowired
	public OrderCheckListService orderCheckListService;

	/** チェックリスト対象カウント */
	@RequestMapping("/countCheckList")
	public ResponseEntity countCheckList(@Validated SearchForm form) {
		super.setResponseData("ret", orderCheckListService.countCheckList(form));
		return super.response();
	}

	/** チェックリスト出力 */
	@RequestMapping("/createCheckList")
	public ResponseEntity createCheckList(@Validated SearchForm form) {
		orderCheckListService.createCheckList(form);
		return super.response();
	}

}
