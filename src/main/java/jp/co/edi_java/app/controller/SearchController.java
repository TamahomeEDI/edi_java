package jp.co.edi_java.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.edi_java.app.dto.SapSearchOrderDto;
import jp.co.edi_java.app.dto.SearchDeliveryDto;
import jp.co.edi_java.app.dto.SearchEstimateDto;
import jp.co.edi_java.app.dto.SearchWorkReportDto;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.service.SearchService;
import jp.co.keepalive.springbootfw.controller.BaseController;
import jp.co.keepalive.springbootfw.entity.ResponseEntity;

@RestController
@Scope("request")
@RequestMapping("search")
public class SearchController extends BaseController {

	@Autowired
	public SearchService searchService;

	/** 見積情報検索 */
	@RequestMapping("/getEstimate")
	public ResponseEntity getEstimate(@Validated SearchForm form) {
		List<SearchEstimateDto> estimateList = searchService.getEstimate(form);
		int totalCount = searchService.countEstimate(form);
		Map<String, Object> ret = new HashMap<>();
		ret.put("estimateList", estimateList);
		ret.put("totalCount", totalCount);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 発注情報検索 */
	@RequestMapping("/getOrder")
	public ResponseEntity getOrder(@Validated SearchForm form) {
		List<SapSearchOrderDto> orderList = searchService.getOrder(form);
		super.setResponseData("ret", orderList);
		super.setResponseData("totalCount", orderList.size());
		super.setResponseData("limitOver", false);
		//Map<String, Object> sap = searchService.getSap(form);
		//super.setResponseData("sap", sap);
		return super.response();
	}

	/** 発注情報検索 ダッシュボードの代替機能 */
	@RequestMapping("/getMultiOrder")
	public ResponseEntity getMultiOrder(@Validated SearchForm form) {
		List<SapSearchOrderDto> orderList = searchService.getMultiOrder(form);
		super.setResponseData("ret", orderList);
		super.setResponseData("totalCount", orderList.size());
		super.setResponseData("limitOver", false);

		return super.response();
	}

	/** 発注情報検索 */
	@RequestMapping("/getOrderByKouji")
	public ResponseEntity getOrderByKouji(@Validated SearchForm form) {
		Map<String, Object> ret = searchService.getOrderByKouji(form);
		if (ret.get("limitOver").equals(true)) {
			super.setResponseData("limitOver", true);
		} else {
			List<SapSearchOrderDto> orderList = (List<SapSearchOrderDto>)ret.get("orderList");
			super.setResponseData("ret", orderList);
			super.setResponseData("totalCount", orderList.size());
			super.setResponseData("limitOver", false);
		}
		return super.response();
	}

	/** 納品・出来高情報検索 */
	@RequestMapping("/getReport")
	public ResponseEntity getReport(@Validated SearchForm form) {
		//納品一覧取得
		int deliveryTotalCount = searchService.countDelivery(form);
		List<SearchDeliveryDto> deliveryList = searchService.getDelivery(form);
		int workReportTotalCount = searchService.countWorkReport(form);
		List<SearchWorkReportDto> workReportList = searchService.getWorkReport(form);
		Map<String, Object> ret = new HashMap<>();
		ret.put("deliveryList", deliveryList);
		ret.put("deliveryTotalCount", deliveryTotalCount);
		ret.put("workReportList", workReportList);
		ret.put("workReportTotalCount", workReportTotalCount);
		super.setResponseData("ret", ret);
		return super.response();
	}

	/** 検収明細情報検索 */
	@RequestMapping("/getInspectionReceipt")
	public ResponseEntity getInspectionReceipt(@Validated SearchForm form) {
//		List<String> inspectionReceiptList = searchService.getInspectionReceipt(form);
//		super.setResponseData("ret", inspectionReceiptList);
		return super.response();
	}


}
