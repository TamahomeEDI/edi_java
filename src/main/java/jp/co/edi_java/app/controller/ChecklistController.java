package jp.co.edi_java.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dto.ChecklistOrderDto;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.service.ChecklistService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("checklist")
public class ChecklistController extends BaseController {

	@Autowired
	public ChecklistService checklistService;

	/** 発注情報検索 */
	@RequestMapping("/getOrderList")
	public ResponseEntity getOrderList(@Validated SearchForm form) {
		List<ChecklistOrderDto> orderList = checklistService.getOrderList(form);
		super.setResponseData("ret", orderList);
		super.setResponseData("totalCount", orderList.size());
		return super.response();
	}

}
