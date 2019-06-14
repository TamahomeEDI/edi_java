package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.InspectionReceiptForm;
import jp.co.edi_java.app.service.InspectionReceiptService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("inspectionReceipt")
public class InspectionReceiptController extends BaseController {

	@Autowired
	public InspectionReceiptService inspectionReceiptService;

	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated InspectionReceiptForm form) {
//		inspectionReceiptService.regist(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	@RequestMapping("/get")
	public ResponseEntity get(@Validated InspectionReceiptForm form) {
//		super.setResponseData("ret", inspectionReceiptService.get());
		return super.response();
	}


}
