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

		//出来高番号返却
		super.setResponseData("ret", workReportNumber);
		return super.response();
	}

	/** 出来高報告書登録後メール送信 */
	@RequestMapping("/sendmail")
	public ResponseEntity sendmail(@Validated WorkReportForm form) {
		workReportService.sendMailWorkReport(form, false);

		return super.response();
	}

	/** 出来高報告書No デコード結果取得 */
	@RequestMapping("/decodeWorkReportNumber")
	public ResponseEntity decodeWorkReportNumber(@Validated WorkReportForm form) {
		String workReportNumber = workReportService.decodeWorkReportNumber(form);
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

	/** 未受入出来高情報存在チェック */
	@RequestMapping("/checkUnconfirmWorkReport")
	public ResponseEntity checkUnconfirmWorkReport(@Validated WorkReportForm form) {
		super.setResponseData("ret", workReportService.checkUnconfirmWorkReport(form.orderNumber));
		return super.response();
	}

	/** 発注番号単位の出来高報告書一覧取得 */
	@RequestMapping("/getList")
	public ResponseEntity getList(@Validated WorkReportForm form) {
		super.setResponseData("ret", workReportService.getList(form.orderNumber, form.remandFlg));
		return super.response();
	}

	/** 納品受入用明細 */
	@RequestMapping("/getDeliveryDetail")
	public ResponseEntity getDeliveryDetail(@Validated WorkReportForm form) {
		super.setResponseData("ret", workReportService.getDeliveryDetail(form.orderNumber, form.userId));
		return super.response();
	}

	/** 出来高報告書更新 */
	@RequestMapping("/update")
	public ResponseEntity update(@Validated WorkReportForm form) {
		workReportService.update(form);
		return super.response();
	}

	/** 出来高報告書取消（論理削除） */
	@RequestMapping("/softdelete")
	public ResponseEntity softdelete(@Validated WorkReportForm form) {
		workReportService.softdelete(form);
		return super.response();
	}

	/** 出来高報告書取消後メール送信 */
	@RequestMapping("/sendmailWorkReportCancel")
	public ResponseEntity sendmailWorkReportCancel(@Validated WorkReportForm form) {
		workReportService.sendMailWorkReportCancel(form);

		return super.response();
	}

	/** 受入 */
	@RequestMapping("/approve")
	public ResponseEntity approve(@Validated WorkReportForm form) {
		workReportService.approve(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 差戻 */
	@RequestMapping("/sendback")
	public ResponseEntity sendback(@Validated WorkReportForm form) {
		workReportService.sendback(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** ジョブカン申請による受入承認待ちステータスへの更新 */
	@RequestMapping("/apply")
	public ResponseEntity apply(@Validated WorkReportForm form) {
		List<TWorkReportEntity> errorList = workReportService.apply(form);
		super.setResponseData("ret", errorList);
		return super.response();
	}

	/** ジョブカン申請否認による未承認ステータスへの更新 */
	@RequestMapping("/reject")
	public ResponseEntity reject(@Validated WorkReportForm form) {
		workReportService.reject(form);
		super.setResponseData("ret", "OK");
		return super.response();
	}

	/** 出来高報告書 未受入対象 リマインドメール送信リクエスト */
	@RequestMapping("/remindWorkReportAcceptance")
	public ResponseEntity remindWorkReportAcceptance(@Validated WorkReportForm form) {
		//出来高書のID一覧取得
		List<TWorkReportEntity> remindWList = workReportService.selectRemindListBySyain(form.getEigyousyoCode(), form.getSyainCode());
		//納品書 未受入対象 リマインドメール
		workReportService.remindList(remindWList);
		super.setResponseData("ret", "OK");
		return super.response();
	}
}
