package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.entity.TWorkReportEntity;
import jp.co.edi_java.app.entity.TWorkReportItemEntity;
import jp.co.edi_java.app.form.DeliveryForm;
import jp.co.edi_java.app.form.WorkReportForm;
import jp.co.edi_java.app.service.WorkReportService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;

@RestController
@Scope("request")
@RequestMapping("workReport")
public class WorkReportController extends BaseController {

	@Autowired
	public WorkReportService workReportService;

	/** 出来高報告書登録 */
	@RequestMapping("/regist")
	public ResponseEntity regist(@Validated WorkReportForm form) {
		TWorkReportEntity workReport = workReportService.getByOrderNumber(form.orderNumber, form.workReportCount);
		if(workReport != null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_662);
			return super.response();
		}

		String workReportNumber = workReportService.regist(form);
		workReportService.sendMailWorkReport(workReportNumber, form.getUserId());
		//出来高番号返却
		super.setResponseData("ret", workReportNumber);
		return super.response();
	}

	/** 出来高報告書取得 */
	@RequestMapping("/get")
	public ResponseEntity get(@Validated WorkReportForm form) {
		TWorkReportEntity workReport = workReportService.get(form.workReportNumber);
		if(workReport == null) {
			super.setErrorCode(ResponseCode.ERROR_CODE_660);
			return super.response();
		}

		List<TWorkReportItemEntity> itemList = workReportService.getItemList(form.workReportNumber);
		if(itemList.size() == 0) {
			super.setErrorCode(ResponseCode.ERROR_CODE_661);
			return super.response();
		}

		Map<String, Object> ret = new HashMap<>();
		ret.put("workReport", workReport);
		ret.put("itemList", itemList);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 発注番号単位の出来高報告書一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated DeliveryForm form) {
		super.setResponseData("ret", workReportService.getList(form.orderNumber, form.remandFlg));
		return super.response();
	}

	/** 出来高報告書更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated WorkReportForm form) {
		workReportService.update(form);
		return super.response();
	}

	/** 出来高報告書削除 */
	@RequestMapping("/delete")
	public ResponseEntity delete(@Validated WorkReportForm form) {
		workReportService.delete(form);
		return super.response();
	}

	/** 出来高報告書受入登録 */
	@RequestMapping("/approval")
	public ResponseEntity approval(@Validated WorkReportForm form) {
		workReportService.approval(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}


}
