package jp.co.edi_java.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.form.OrderForm;
import jp.co.edi_java.app.service.OrderService;
import jp.co.edi_java.app.util.cloudsign.CloudSignApi;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("order")
public class OrderController extends BaseController {

	@Autowired
	public OrderService orderService;

	/** 発注情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated OrderForm form) {
		super.setResponseData("ret", orderService.get(form.getOrderNumber()));
		return super.response();
	}

	/** 発注情報更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated OrderForm form) {
		//発注番号返却
		super.setResponseData("ret", orderService.update(form));
		return super.response();
	}

	/** 請書発行 */
	@RequestMapping("/confirmation")
	public ResponseEntity confirmation(@Validated OrderForm form) {
		String orderNumber = form.getOrderNumber();
		orderService.confirmationInfo(form);
		orderService.setOrderDate(orderNumber);
		orderService.conectCloudSign(form, CloudSignApi.FORM_TYPE_ORDER);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 取消合意書発行 */
	@RequestMapping("/cancel")
	public ResponseEntity cancel(@Validated OrderForm form) {
		orderService.cancelInfo(form);
		orderService.conectCloudSign(form, CloudSignApi.FORM_TYPE_CANCEL);
		super.setResponseData("ret", "OK");
		return super.response();
	}


}
