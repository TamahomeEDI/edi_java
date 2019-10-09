package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TDeliveryEntity;
import jp.co.edi_java.app.entity.TDeliveryItemEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.edi_java.app.service.DeliveryService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Scope("request")
@RequestMapping("delivery")
public class DeliveryController extends BaseController {

	@Autowired
	public DeliveryService deliveryService;

	/** 納品情報登録 */
	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated DeliveryForm form) {
		log.info("delivery regist ordernumber: " + form.orderNumber);
		TDeliveryEntity delivery = deliveryService.getByOrderNumber(form.orderNumber, form.deliveryCount);
		if(delivery != null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_652);
			return super.response();
		}

		//納品番号返却
		String deliveryNumber = deliveryService.regist(form);

		super.setResponseData("ret", deliveryNumber);
		return super.response();
	}

	/** 納品情報登録後メール送信 */
	@RequestMapping("/sendmail")
	public ResponseEntity sendmail(@Validated DeliveryForm form) {
		//納品受領通知
		deliveryService.sendMailDelivery(form, false);

		return super.response();
	}

	/** 納品書No エンコード結果取得 */
	@RequestMapping("/encodeDeliveryNumber")
	public ResponseEntity encodeDeliveryNumber(@Validated DeliveryForm form) {
		String encryptNumber = deliveryService.encodeDeliveryNumber(form);
		//納品書番号返却
		super.setResponseData("ret", encryptNumber);
		return super.response();
	}

	/** 納品書No デコード結果取得 */
	@RequestMapping("/decodeDeliveryNumber")
	public ResponseEntity decodeDeliveryNumber(@Validated DeliveryForm form) {
		String deliveryNumber = deliveryService.decodeDeliveryNumber(form);
		//納品書番号返却
		super.setResponseData("ret", deliveryNumber);
		return super.response();
	}

	/** 納品情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated DeliveryForm form) {
		TDeliveryEntity delivery = deliveryService.get(form.deliveryNumber);
		if(delivery == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_650);
			return super.response();
		}

		List<TDeliveryItemEntity> itemList = deliveryService.getItemList(form.deliveryNumber);
		if(itemList.size() == 0) {
			super.setErrorCode(ResponseCode.ERROR_CODE_651);
			return super.response();
		}

		Map<String, Object> ret = new HashMap<>();
		ret.put("delivery", delivery);
		ret.put("itemList", itemList);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 発注番号単位の納品情報一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated DeliveryForm form) {
		super.setResponseData("ret", deliveryService.getList(form.orderNumber, form.remandFlg));
		return super.response();
	}

	/** 納品受入用明細 */
	@RequestMapping("/getDeliveryDetail")
	public ResponseEntity getDeliveryDetail(@Validated DeliveryForm form) {
		super.setResponseData("ret", deliveryService.getDeliveryDetail(form.orderNumber, form.userId));
		return super.response();
	}

	/** 納品情報更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated DeliveryForm form) {
		deliveryService.update(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 納品情報削除 */
	@RequestMapping("/delete")
	public ResponseEntity delete(@Validated DeliveryForm form) {
		deliveryService.delete(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 受入 */
	@RequestMapping("/approve")
	public ResponseEntity approve(@Validated DeliveryForm form) {
		deliveryService.approve(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 差戻 */
	@RequestMapping("/sendback")
	public ResponseEntity sendback(@Validated DeliveryForm form) {
		deliveryService.sendback(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** ジョブカン申請による受入承認待ちステータスへの更新 */
	@RequestMapping("/apply")
	public ResponseEntity apply(@Validated DeliveryForm form) {
		List<TDeliveryEntity> errorList = deliveryService.apply(form);
		super.setResponseData("ret", errorList);
		return super.response();
	}

	/** ジョブカン申請否認による未承認ステータスへの更新 */
	@RequestMapping("/reject")
	public ResponseEntity reject(@Validated DeliveryForm form) {
		deliveryService.reject(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

}
