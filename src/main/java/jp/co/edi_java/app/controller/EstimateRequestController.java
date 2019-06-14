package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dto.EstimateDto;
import jp.co.edi_java.app.entity.TEstimateRequestEntity;
import jp.co.edi_java.app.form.EstimateRequestForm;
import jp.co.edi_java.app.service.EstimateRequestService;
import jp.co.edi_java.app.service.EstimateService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("estimateRequest")
public class EstimateRequestController extends BaseController {

	@Autowired
	public EstimateRequestService estimateRequestService;

	@Autowired
	public EstimateService estimateService;

	/** 見積依頼登録 */
	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated EstimateRequestForm form) {
		//見積依頼番号返却
		super.setResponseData("ret", estimateRequestService.regist(form));
		return super.response();
	}

	/** 見積依頼取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated EstimateRequestForm form) {
		String estimateRequestNumber = form.estimateRequestNumber;
		//見積依頼詳細取得
		TEstimateRequestEntity estimateRequest = estimateRequestService.get(estimateRequestNumber);
		if(estimateRequest == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_630);
		}

		//見積依頼添付ファイル一覧取得
		List<Map<String, Object>> fileList = estimateRequestService.getFileIdList(estimateRequestNumber);

		//見積一覧取得
		List<EstimateDto> estimateList = estimateService.selectListByEstimateRequestNumber(estimateRequestNumber);

		Map<String, Object> ret = new HashMap<>();
		ret.put("estimateRequest", estimateRequest);
		ret.put("fileList", fileList);
		ret.put("estimateList", estimateList);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 見積依頼更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated EstimateRequestForm form) {
		estimateRequestService.update(form);
		return super.response();
	}

	/** 見積依頼削除 */
	@RequestMapping("/delete")
	public ResponseEntity delete(@Validated EstimateRequestForm form) {
		estimateRequestService.delete(form);
		return super.response();
	}


}
