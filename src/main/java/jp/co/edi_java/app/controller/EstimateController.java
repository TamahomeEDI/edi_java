package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TEstimateEntity;
import jp.co.edi_java.app.form.EstimateForm;
import jp.co.edi_java.app.service.EstimateService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("estimate")
public class EstimateController extends BaseController {

	@Autowired
	public EstimateService estimateService;

	/** 見積情報取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated EstimateForm form) {
		TEstimateEntity estimate = estimateService.select(form.estimateNumber);
		if(estimate == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_640);
		}

		List<Map<String, Object>> fileList = estimateService.getFileIdList(estimate.getEstimateNumber());
		Map<String, Object> ret = new HashMap<>();
		ret.put("estimate", estimate);
		ret.put("fileList", fileList);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 見積情報登録 */
	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated EstimateForm form) {
		//見積番号返却
		super.setResponseData("ret", estimateService.regist(form));
		return super.response();
	}

	/** 見積情報更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated EstimateForm form) {
		estimateService.update(form);
		return super.response();
	}

	/** 見積情報削除 */
	@RequestMapping("/delete")
	public ResponseEntity delete(@Validated EstimateForm form) {
		estimateService.delete(form);
		return super.response();
	}

	/** 見積既読 */
	@RequestMapping("/read")
	public ResponseEntity read(@Validated EstimateForm form) {
		estimateService.read(form);
		return super.response();
	}


}
