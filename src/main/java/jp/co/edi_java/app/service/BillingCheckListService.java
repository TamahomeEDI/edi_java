package jp.co.edi_java.app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jp.co.edi_java.app.dao.SearchDao;
import jp.co.edi_java.app.dao.TBillingCheckListDao;
import jp.co.edi_java.app.dao.TCloudSignDao;
import jp.co.edi_java.app.dao.gyousya.TGyousyaAccountDao;
import jp.co.edi_java.app.dto.BillingCheckListDto;
import jp.co.edi_java.app.entity.TBillingCheckListEntity;
import jp.co.edi_java.app.entity.TCloudSignEntity;
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.entity.gyousya.TGyousyaAccountEntity;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.edi_java.app.util.consts.CommonConsts;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class BillingCheckListService {

	@Autowired
    public SearchDao searchDao;

	@Autowired
    public TCloudSignDao tCloudSignDao;

	@Autowired
    public TGyousyaAccountDao tGyousyaAccountDao;

	@Autowired
    public TBillingCheckListDao tBillingCheckListDao;

	/**
	 * チェックリストの出力対象件数をカウントする
	 * @param SearchForm form - 検索条件
	 * @return int - 出力対象件数
	 * */
	public int countCheckList(SearchForm form) {
		return searchDao.countOrderItemForBilling(form);
	}

    /**
     * 課金チェックリストを業者毎に作成し、GoogleDriveアップロード用タスクデータを生成する
     *
     */
    public void createCheckListBatch(String useMonth) {
		String from = CommonUtils.getArchiveDateFromByLastMonth(null);
		String to = CommonUtils.getArchiveDateToByLastMonth(null);
		String year = "";
		String month = "";
		String lastMonth = CommonUtils.getLastMonthFirst("yyyyMM", "JST");
		if (Objects.nonNull(useMonth)) {
			lastMonth = useMonth;
			from = CommonUtils.getArchiveDateFromByLastMonth(lastMonth);
			to = CommonUtils.getArchiveDateToByLastMonth(lastMonth);
		}
		year = lastMonth.substring(0, 4);
		month = lastMonth.substring(4, 6);

		// 各種業者向け課金チェックリストを生成する
		SearchForm searchForm = new SearchForm();
		searchForm.setApplicationDateFrom(from);
		searchForm.setApplicationDateTo(to);

		List<TGyousyaAccountEntity> gyousyaList = tGyousyaAccountDao.selectAll();
		for (TGyousyaAccountEntity gyousya : gyousyaList) {
			String gyousyaCode = gyousya.getGyousyaCode();
			searchForm.setGyousyaCode(gyousyaCode);

			Map<String, String> filePathMap = createCheckList(searchForm);
			if (filePathMap.containsKey("filePath") && filePathMap.containsKey("fileName")) {
				TBillingCheckListEntity billing = new TBillingCheckListEntity();
				billing.setGyousyaCode(gyousyaCode);
				billing.setFileName(filePathMap.get("fileName"));
				billing.setFilePath(filePathMap.get("filePath"));
				billing.setReportYear(year);
				billing.setReportMonth(month);
				billing.setReportYearMonth(year + month);
				billing.setCompleteFlg("0");
				tBillingCheckListDao.insert(billing);
			}
		}
    }


	/**
	 * クラウドサインの課金状況一覧を出力する
	 *
	 * @param SearchForm form - 検索条件
	 * @return Map<String, String> - fileName:String, filePath:String
	 *
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,String> createCheckList(SearchForm form) {
		Map<String,String> ret = new HashMap<String, String>();

		// クラウドサインレコードがそのまま集計単位
		List<TCloudSignEntity> cloudSignList = searchDao.selectCloudSignListForBilling(form);
		// 発注書
		List<VOrderStatusEntity> vOrderList = searchDao.selectVOrderListForBilling(form);
		// 発注書品目
		List<TOrderItemEntity> tOrderItemList = searchDao.selectOrderItemListForBilling(form);

		// 発注書マップ生成
		Map<String, VOrderStatusEntity> orderMap = new HashMap<String, VOrderStatusEntity>();
		Map<String, List<VOrderStatusEntity>> orderListMap = new HashMap<String, List<VOrderStatusEntity>>();
		for (VOrderStatusEntity entity : vOrderList) {
			orderMap.put(entity.getOrderNumber(), entity);
			if (Objects.nonNull(entity.getGroupOrderNumber())) {
				if (! orderListMap.containsKey(entity.getGroupOrderNumber())) {
					orderListMap.put(entity.getGroupOrderNumber(), new ArrayList<VOrderStatusEntity>());
				}
				orderListMap.get(entity.getGroupOrderNumber()).add(entity);
			}
		}
		// 発注書品目マップ生成
		Map<String, List<TOrderItemEntity>> orderItemMap = new HashMap<String, List<TOrderItemEntity>>();
		for (TOrderItemEntity entity : tOrderItemList) {
			if (! orderItemMap.containsKey(entity.getOrderNumber())) {
				orderItemMap.put(entity.getOrderNumber(), new ArrayList<TOrderItemEntity>());
			}
			orderItemMap.get(entity.getOrderNumber()).add(entity);
		}

		// 同一日時をベースキーとした場合の枝番カウンター
		Map<String, Integer> counter = new HashMap<String, Integer>();

		List<BillingCheckListDto> dtoList = new ArrayList<BillingCheckListDto>();

		// Orderからグループ毎にレコード取得
		for (TCloudSignEntity cloudSign : cloudSignList) {
			// グループキーのベース文字列を取得
			String summaryDate = getSummaryDate(cloudSign.getApplicationDate());
			String billingDate = getBillingDate(cloudSign.getApplicationDate());
			if (!counter.containsKey(summaryDate)) {
				counter.put(summaryDate,0);
			}
			// 枝番の作成
			Integer subKey = counter.get(summaryDate) + 1;
			counter.put(summaryDate, subKey);
			// グループキーの生成
			String summaryKey = summaryDate + "_" + String.format("%03d", subKey);
			// dtoに関連エンティティ詰め込み
			BillingCheckListDto dto = new BillingCheckListDto();
			dto.setSummaryKey(summaryKey);
			dto.setCloudSign(cloudSign);
			dto.setApplyDate(dateStringFormat(summaryDate));
			String chargeFlg = "無"; // 非課金
			String billingYearMonth = "請求無し";
			String eigyousyoName = "";
			if (Objects.nonNull(cloudSign.getGroupOrderNumber())) {
				List<VOrderStatusEntity> orderList = orderListMap.get(cloudSign.getGroupOrderNumber());
				if (Objects.nonNull(orderList)) {
					dto.setOrderList(orderList);
					Map<String, List<TOrderItemEntity>> orderDetailMap = new HashMap<String, List<TOrderItemEntity>>();

					for (VOrderStatusEntity order : orderList) {
						List<TOrderItemEntity> itemList = orderItemMap.get(order.getOrderNumber());
						if (Objects.nonNull(itemList)) {
							orderDetailMap.put(order.getOrderNumber(), itemList);
						}
						// 1:本体発注, 2:S発注, 3:a発注, 4:b発注, 5:c発注
						if (Objects.nonNull(order.getYosanFlg()) && order.getYosanFlg() == 1) {
							chargeFlg = "有"; // 課金
							billingYearMonth = dateStringFormatYearMonth(billingDate);
						}
						eigyousyoName = order.getEigyousyoName();
					}
					dto.setItemListMap(orderDetailMap);
				}
			} else {
				VOrderStatusEntity order = orderMap.get(cloudSign.getOrderNumber());
				if (Objects.nonNull(order)) {
					List<VOrderStatusEntity> orderList = new ArrayList<VOrderStatusEntity>();
					orderList.add(order);
					dto.setOrderList(orderList);
					Map<String, List<TOrderItemEntity>> orderDetailMap = new HashMap<String, List<TOrderItemEntity>>();
					List<TOrderItemEntity> itemList = orderItemMap.get(order.getOrderNumber());
					if (Objects.nonNull(itemList)) {
						orderDetailMap.put(order.getOrderNumber(), itemList);
					}
					dto.setItemListMap(orderDetailMap);
					// 1:本体発注, 2:S発注, 3:a発注, 4:b発注, 5:c発注
					if (Objects.nonNull(order.getYosanFlg()) && order.getYosanFlg() == 1) {
						chargeFlg = "有"; // 課金
						billingYearMonth = dateStringFormatYearMonth(billingDate);
					}
					eigyousyoName = order.getEigyousyoName();
				}
			}
			dto.setChargeFlg(chargeFlg);
			dto.setBillingDate(billingYearMonth);
			dto.setEigyousyoName(eigyousyoName);
			// グループキーにエンティティ紐付け
			dtoList.add(dto);
		}

		String baseFolderPath = CommonConsts.OUTPUT_BILLING_CHECK_LIST_DIR;
		UUID uuid = UUID.randomUUID();
		String folderPath = baseFolderPath + uuid + "/";
		String zipFolder = "doc";
		String zipFileName = "doc.zip";
		String subFolderPath = folderPath + zipFolder + "/";

		// チェクリスト出力フォルダの作成
		FileApi.createDirectories(subFolderPath);

		// エクセル出力が遅いので取りやめ
		//String fileName = form.getGyousyaCode() + "_checklist_cloudsign_order.xlsx";
		//String zipFileName = form.getGyousyaCode() + "_checklist_cloudsign_order.zip";
		//String fileFullPath = folderPath + fileName;
		//createCheckListAux(dtoList, folderPath, fileName, fileFullPath);

		String headerFileName = form.getGyousyaCode() + "_checklist_cloudsign_order_header.csv";
		String detailFileName = form.getGyousyaCode() + "_checklist_cloudsign_order_detail.csv";

		createCheckListHeaderCsv(dtoList, subFolderPath, headerFileName);
		createCheckListDetailCsv(dtoList, subFolderPath, detailFileName);

		// Zipファイル化
		File curdir = new File(folderPath);
		if (curdir.exists()) {
			String[] zipCommand = {"zip", "-r", zipFileName, zipFolder};
			long timeOutSec = 5 * 60;
			// zipコマンド
			CommonUtils.processDone(zipCommand, curdir, timeOutSec);
		}
		File zipfile = new File(folderPath + zipFileName);
		if (!zipfile.exists()) {
			return ret;
		}
		ret.put("filePath", folderPath + zipFileName);
		ret.put("fileName", zipFileName);
    	return ret;
	}

	/**
	 *
	 * @param cloudSignList
	 * @param folderPath
	 * @param fileName
	 * @param fileFullPath
	 */
	@SuppressWarnings("unchecked")
	private void createCheckListHeaderCsv(List<BillingCheckListDto> cloudSignList, String folderPath, String fileName) {
		String filePath = folderPath + fileName;
		File csvfile = new File(filePath);
		String strFDQ = "\"";
		String strEDQC = "\",";
		String strEDQ = "\"";
		String conma = ",";

		// title
		String [] titleArray = {
			"発注グループ","請求有無","支店名","発注日","請求年月"
		};
	    List<String> titles = new ArrayList<String>(Arrays.asList(titleArray));

		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfile),"MS932")))) {

			// title
			pw.println(strFDQ + String.join(strEDQC + strFDQ, titles) + strEDQ);

			// body
		    if (Objects.nonNull(cloudSignList) && !cloudSignList.isEmpty()) {
	        	for (BillingCheckListDto dto : cloudSignList) {
	        		String line = strFDQ + dto.getSummaryKey() + strEDQC +
	        				strFDQ + dto.getChargeFlg() + strEDQC +
	        				strFDQ + dto.getEigyousyoName() + strEDQC +
	        				strFDQ + dto.getApplyDate() + strEDQC +
	        				strFDQ + dto.getBillingDate() + strEDQ;
	        		pw.println(line);
	        	}
		    }
		} catch (IOException e) {
			throw new CoreRuntimeException(e.getMessage());
		}
	}

	/**
	 *
	 * @param cloudSignList
	 * @param folderPath
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	private void createCheckListDetailCsv(List<BillingCheckListDto> cloudSignList, String folderPath, String fileName) {
		String filePath = folderPath + fileName;
		File csvfile = new File(filePath);
		String strFDQ = "\"";
		String strEDQC = "\",";
		String strEDQ = "\"";
		String conma = ",";

		// title
		String [] titleArray = {
				"発注グループ",
				"施工支店名","工事コード","工事名","担当者名",
				"着工予定日","竣工予定日","引渡予定日",
				"建築地郵便番号","建築地",
				"発注番号","発注種別","細目工種","発注日","発注金額合計（税抜）",
				"品名","単価","数量","単位","発注金額（税抜）"
		};
		List<String> titles = new ArrayList<String>(Arrays.asList(titleArray));

		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfile),"MS932")))) {

			// title
		    pw.println(strFDQ + String.join(strEDQC + strFDQ, titles) + strEDQ);

			// body
		    if (Objects.nonNull(cloudSignList) && !cloudSignList.isEmpty()) {
	        	for (BillingCheckListDto dto : cloudSignList) {
	        		List<VOrderStatusEntity> vOrderList = dto.getOrderList();
					Map<String,List<TOrderItemEntity>> itemMap = dto.getItemListMap();
					for (VOrderStatusEntity order : vOrderList) {
						List<TOrderItemEntity> orderItemList = itemMap.get(order.getOrderNumber());
						if (Objects.isNull(orderItemList) || orderItemList.isEmpty()) {
							String line = "";
							line = createHeaderCsvColumn(dto, order, line);
							//品名
			        		line += strFDQ + strEDQC;
							//単価
			        		line += strFDQ + strEDQC;
							//数量
			        		line += strFDQ + strEDQC;
							//単位
			        		line += strFDQ + strEDQC;
							//発注金額（税抜）
			        		pw.println(line);
						} else {
							for (TOrderItemEntity item : orderItemList) {
								// 発注数量が0以下のものは出力しない
								if (item.getOrderQuantity() <= 0) {
									continue;
								}
								String line = "";
								line = createHeaderCsvColumn(dto, order, line);
								line = createDetailCsvColumn(item, line);
								pw.println(line);
							}
						}
					}
	        	}
		    }
		} catch (IOException e) {
			throw new CoreRuntimeException(e.getMessage());
		}
	}

	private String createHeaderCsvColumn(BillingCheckListDto dto, VOrderStatusEntity order, String line) {
		String strFDQ = "\"";
		String strEDQC = "\",";
		String strEDQ = "\"";
		String conma = ",";
		// 親項目のみ出力
		// yosanFlg: 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
		// c発注のみ「追加」と出力する
		String yosanType = "";
		if (Objects.nonNull(order.getYosanFlg()) && order.getYosanFlg() == 5) {
			yosanType = "追加";
		}
		//発注グループ
		line += strFDQ + dto.getSummaryKey() + strEDQC;
		//施工支店名
		line += strFDQ + order.getEigyousyoName() + strEDQC;
		//工事コード
		line += strFDQ + order.getKoujiCode() + strEDQC;
		//工事名
		line += strFDQ + order.getKoujiName() + strEDQC;
		//担当者名
		line += strFDQ + order.getSyainName() + strEDQC;
		//着工予定日
		line += strFDQ + dateStringFormat(order.getTyakkouDateY()) + strEDQC;
		//竣工予定日
		line += strFDQ + dateStringFormat(order.getSyunkouDateY()) + strEDQC;
		//引渡予定日
		line += strFDQ + dateStringFormat(order.getHikiwatasiDateY()) + strEDQC;
		//建築地郵便番号
		line += strFDQ + zipcodeStringFormat(order.getKentikutiYuubin()) + strEDQC;
		//建築地
		line += strFDQ + order.getKentikutiJyuusyo() + strEDQC;
		//発注番号
		line += strFDQ + order.getOrderNumber() + strEDQC;
		//発注種別
		line += strFDQ + yosanType + strEDQC;
		//細目工種
		line += strFDQ + order.getSaimokuKousyuName() + strEDQC;
		//発注日
		line += strFDQ + dateStringFormat(order.getOrderDate()) + strEDQC;
		//発注金額合計（税抜）
		String orderAmount = "";
		if (Objects.nonNull(order.getOrderAmount())) {
			orderAmount = order.getOrderAmount().toString();
		}
		line += orderAmount + conma;
		return line;
	}

	private String createDetailCsvColumn(TOrderItemEntity item, String line) {
		String strFDQ = "\"";
		String strEDQC = "\",";
		String strEDQ = "\"";
		String conma = ",";

		//品名
		line += strFDQ + item.getItemName() + strEDQC;
		//単価
		String unitPrice = "";
		if (Objects.nonNull(item.getOrderUnitPrice())) {
			unitPrice = item.getOrderUnitPrice().toString();
		}
		line += unitPrice + conma;
		//数量
		line += item.getOrderQuantity() + conma;
		//単位
		line += strFDQ + item.getUnit() + strEDQC;
		//発注金額（税抜）
		String itemOrderAmount = "";
		if (Objects.nonNull(item.getOrderAmount())) {
			itemOrderAmount = item.getOrderAmount().toString();
		}
		line += itemOrderAmount;
		return line;
	}

	/**
	 *
	 * @param cloudSignList
	 * @param folderPath
	 * @param fileName
	 * @param fileFullPath
	 */
	@SuppressWarnings("unchecked")
	private void createCheckListAux(List<BillingCheckListDto> cloudSignList, String folderPath, String fileName, String fileFullPath) {
		Workbook book = null;
		FileOutputStream fout = null;
		try {
			book = new SXSSFWorkbook(-1);

            Font font = book.createFont();
            font.setFontName("ＭＳ Pゴシック");
            font.setFontHeightInPoints((short) 11);

            DataFormat format = book.createDataFormat();
            //ヘッダのスタイル
            CellStyle style_header = book.createCellStyle();
            style_header.setBorderTop(BorderStyle.THIN);
            style_header.setBorderBottom(BorderStyle.THIN);
            style_header.setBorderLeft(BorderStyle.THIN);
            style_header.setBorderRight(BorderStyle.THIN);
            style_header.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_header.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_header.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_header.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style_header.setVerticalAlignment(VerticalAlignment.TOP);
            style_header.setFont(font);
            //文字のスタイル
            CellStyle style_str = book.createCellStyle();
            style_str.setBorderTop(BorderStyle.THIN);
            style_str.setBorderBottom(BorderStyle.THIN);
            style_str.setBorderLeft(BorderStyle.THIN);
            style_str.setBorderRight(BorderStyle.THIN);
            style_str.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_str.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_str.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_str.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_str.setVerticalAlignment(VerticalAlignment.TOP);
            style_str.setFont(font);
            //郵便番号スタイル
            CellStyle style_postnum = book.createCellStyle();
            style_postnum.setBorderTop(BorderStyle.THIN);
            style_postnum.setBorderBottom(BorderStyle.THIN);
            style_postnum.setBorderLeft(BorderStyle.THIN);
            style_postnum.setBorderRight(BorderStyle.THIN);
            style_postnum.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_postnum.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_postnum.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_postnum.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_postnum.setDataFormat(format.getFormat("〒###-####"));
            style_postnum.setAlignment(HorizontalAlignment.LEFT);
            style_postnum.setVerticalAlignment(VerticalAlignment.TOP);
            style_postnum.setFont(font);
            //整数用のスタイル
            CellStyle style_int = book.createCellStyle();
            style_int.setBorderTop(BorderStyle.THIN);
            style_int.setBorderBottom(BorderStyle.THIN);
            style_int.setBorderLeft(BorderStyle.THIN);
            style_int.setBorderRight(BorderStyle.THIN);
            style_int.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_int.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_int.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_int.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_int.setDataFormat(format.getFormat("#,##0;-#,##0"));
            style_int.setVerticalAlignment(VerticalAlignment.TOP);
            style_int.setAlignment(HorizontalAlignment.RIGHT);
            style_int.setFont(font);
            //小数用のスタイル
            CellStyle style_double = book.createCellStyle();
            style_double.setBorderTop(BorderStyle.THIN);
            style_double.setBorderBottom(BorderStyle.THIN);
            style_double.setBorderLeft(BorderStyle.THIN);
            style_double.setBorderRight(BorderStyle.THIN);
            style_double.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_double.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_double.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_double.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_double.setDataFormat(format.getFormat("#,##0.000;-#,##0.000"));
            style_double.setVerticalAlignment(VerticalAlignment.TOP);
            style_double.setAlignment(HorizontalAlignment.RIGHT);
            style_double.setFont(font);

            Sheet sheet0 = book.createSheet();
            Sheet sheet1 = book.createSheet();
            if (sheet0 instanceof SXSSFSheet) {
                ((SXSSFSheet) sheet0).trackAllColumnsForAutoSizing();
            }
            if (sheet1 instanceof SXSSFSheet) {
                ((SXSSFSheet) sheet1).trackAllColumnsForAutoSizing();
            }
            //シート名称の設定
            book.setSheetName(0, "受注一覧");
            book.setSheetName(1, "受注明細一覧");
            PrintSetup printSetup0 = book.getSheetAt(0).getPrintSetup();
            PrintSetup printSetup1 = book.getSheetAt(1).getPrintSetup();
            //long header_start = System.currentTimeMillis();
            createHeaderSheet(sheet0, printSetup0, cloudSignList, style_header, style_str);
            //long header_end = System.currentTimeMillis();
            createDetailSheet(sheet1, printSetup1, cloudSignList, style_header, style_str, style_postnum, style_int, style_double);
            //long detail_end = System.currentTimeMillis();
            //log.info((header_end - header_start) + "ms: create header sheet, " + (detail_end - header_end) + "ms: create detail sheet, " + (detail_end - header_start) + "ms: total");

            //ファイル出力
            //long start = System.currentTimeMillis();
            fout = new FileOutputStream(fileFullPath);
            //long end1 = System.currentTimeMillis();
            book.write(fout);
            //long end2 = System.currentTimeMillis();
            //log.info((end1 - start) + "ms: fout open, " + (end2 - end1) + "ms: write excel, " + (end2 - start) + "ms: total");

		} catch (FileNotFoundException e) {
			throw new CoreRuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new CoreRuntimeException(e.getMessage());
		} finally {

			if (fout != null) {
                try {
                    fout.close();
                }
                catch (IOException e) {
                	throw new CoreRuntimeException(e.getMessage());
                }
            }
            if (book != null) {
                try {
                    ((SXSSFWorkbook) book).dispose();
                    book.close();
                }
                catch (Exception e) {
                	throw new CoreRuntimeException(e.getMessage());
                }
            }
		}
	}

	/**
	 *
	 * 受注一覧シートを出力する
	 *
	 * @param Sheet sheet
	 * @param PrintSetup printSetup
	 * @param List<TCloudSignEntity> cloudSignList
	 * @param CellStyle style_header
	 * @param CellStyle style_str
	 */
	private void createHeaderSheet(Sheet sheet, PrintSetup printSetup, List<BillingCheckListDto> summaryList, CellStyle style_header, CellStyle style_str) {

        String [] titleArray = {
        	"発注グループ","請求有無","支店名","発注日","請求年月"
        };
        List<String> titles = new ArrayList<String>(Arrays.asList(titleArray));
        //ヘッダ行の作成
        int rowNumber = 0;
        int colNumber = 0;
        Cell cell = null;
        Row row = sheet.createRow(rowNumber);
        for (String title : titles) {
        	cell = row.createCell(colNumber);
        	cell.setCellStyle(style_header);
        	cell.setCellValue(title);
        	if (title.equals("発注グループ") || title.equals("支店名")) {
        		sheet.setColumnWidth(colNumber, 35*256);
        	} else {
        		sheet.autoSizeColumn(colNumber, true);
        	}
        	colNumber += 1;
        }
        //ウィンドウ枠の固定
        sheet.createFreezePane(0, 1);
        //ヘッダ行にオートフィルタの設定
        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colNumber-1));

        // データ行の出力
        if (Objects.nonNull(summaryList) && !summaryList.isEmpty()) {
        	for (BillingCheckListDto summary : summaryList) {
        		int colnum = 0;
        		rowNumber += 1;
        		row = sheet.createRow(rowNumber);
        		//発注グループ
        		cell = row.createCell(colnum++);
        		cell.setCellStyle(style_str);
        		cell.setCellValue(summary.getSummaryKey());
        		sheet.autoSizeColumn(colnum-1, true);
        		//請求有無
        		cell = row.createCell(colnum++);
        		cell.setCellStyle(style_str);
        		cell.setCellValue(summary.getChargeFlg());
        		sheet.autoSizeColumn(colnum-1, true);
        		//支店名
        		cell = row.createCell(colnum++);
        		cell.setCellStyle(style_str);
        		cell.setCellValue(summary.getEigyousyoName());
        		sheet.autoSizeColumn(colnum-1, true);
        		//発注日
        		cell = row.createCell(colnum++);
        		cell.setCellStyle(style_str);
        		cell.setCellValue(summary.getApplyDate());
        		sheet.autoSizeColumn(colnum-1, true);
        		//請求年月
        		cell = row.createCell(colnum++);
        		cell.setCellStyle(style_str);
        		cell.setCellValue(summary.getBillingDate());
        		sheet.autoSizeColumn(colnum-1, true);
        	}
        }
        sheet.setDisplayGridlines(false);
        sheet.setFitToPage(true);
        printSetup.setLandscape(true);
        printSetup.setHResolution((short)(colNumber-1));
        sheet.setAutobreaks(true);
        sheet.setFitToPage(false);
	}

	/**
	 *
	 * 受注明細一覧シートを出力する
	 *
	 * @param Sheet sheet
	 * @param PrintSetup printSetup
	 * @param List<TCloudSignEntity> cloudSignList
	 * @param CellStyle style_header
	 * @param CellStyle style_str
	 */
	private void createDetailSheet(Sheet sheet, PrintSetup printSetup, List<BillingCheckListDto> summaryList,
			CellStyle style_header, CellStyle style_str, CellStyle style_postnum, CellStyle style_int, CellStyle style_double) {

		String [] titleArray = {
				"発注グループ",
				"施工支店名","工事コード","工事名","担当者名",
				"着工予定日","竣工予定日","引渡予定日",
				"建築地郵便番号","建築地",
				"発注番号","発注種別","細目工種","発注日","発注金額合計（税抜）",
				"品名","単価","数量","単位","発注金額（税抜）"
		};
		List<String> titles = new ArrayList<String>(Arrays.asList(titleArray));

		//ヘッダ行の作成
		int rowNumber = 0;
		int colNumber = 0;
		Cell cell = null;
		Row row = sheet.createRow(rowNumber);
		for (String title : titles) {
			cell = row.createCell(colNumber);
			cell.setCellStyle(style_header);
			cell.setCellValue(title);
			if (title.equals("発注グループ")||title.equals("工事名")||title.equals("建築地")||title.equals("品名")) {
				sheet.setColumnWidth(colNumber, 35*256);
			} else if (title.equals("単価")||title.equals("数量")||title.equals("単位")) {
				sheet.setColumnWidth(colNumber, 9*256);
			} else {
				sheet.autoSizeColumn(colNumber, true);
			}
			colNumber += 1;
		}
		//ウィンドウ枠の固定
		sheet.createFreezePane(0, 1);
		//ヘッダ行にオートフィルタの設定
		sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colNumber-1));

		// データ行の出力
		if (Objects.nonNull(summaryList) && !summaryList.isEmpty()) {
			for (BillingCheckListDto summary : summaryList) {
				List<VOrderStatusEntity> vOrderList = summary.getOrderList();
				Map<String,List<TOrderItemEntity>> itemMap = summary.getItemListMap();
				for (VOrderStatusEntity order : vOrderList) {
					List<TOrderItemEntity> orderItemList = itemMap.get(order.getOrderNumber());
					int colnum = 0;
					if (Objects.isNull(orderItemList) || orderItemList.isEmpty()) {
						// 親項目のみ出力
						rowNumber += 1;
						row = sheet.createRow(rowNumber);
						colnum = createParentColumn(sheet, row, summary.getSummaryKey(), order, colnum, style_str, style_int, style_postnum);
						//品名
						cell = row.createCell(colnum++);
						cell.setCellStyle(style_str);
						cell.setBlank();
						sheet.autoSizeColumn(colnum-1, true);
						//単価
						cell = row.createCell(colnum++);
						cell.setCellStyle(style_int);
						cell.setBlank();
						sheet.autoSizeColumn(colnum-1, true);
						//数量
						cell = row.createCell(colnum++);
						cell.setCellStyle(style_double);
						cell.setBlank();
						sheet.autoSizeColumn(colnum-1, true);
						//単位
						cell = row.createCell(colnum++);
						cell.setCellStyle(style_str);
						cell.setBlank();
						sheet.autoSizeColumn(colnum-1, true);
						//発注金額（税抜）
						cell = row.createCell(colnum++);
						cell.setCellStyle(style_int);
						cell.setBlank();
						sheet.autoSizeColumn(colnum-1, true);
					} else {
						for (TOrderItemEntity item : orderItemList) {
							// 発注数量が0以下のものは出力しない
							if (item.getOrderQuantity() <= 0) {
								continue;
							}
							colnum = 0;
							rowNumber += 1;
							row = sheet.createRow(rowNumber);
							colnum = createParentColumn(sheet, row, summary.getSummaryKey(), order, colnum, style_str, style_int, style_postnum);
							//品名
							cell = row.createCell(colnum++);
							cell.setCellStyle(style_str);
							cell.setCellValue(item.getItemName());
							//単価
							cell = row.createCell(colnum++);
							cell.setCellStyle(style_int);
							String unitPrice = "";
							if (Objects.nonNull(item.getOrderUnitPrice())) {
								unitPrice = item.getOrderUnitPrice().toString();
							}
							cell.setCellFormula(unitPrice);
							//sheet.autoSizeColumn(colnum-1, true);
							//数量
							cell = row.createCell(colnum++);
							cell.setCellStyle(style_double);
							cell.setCellValue(item.getOrderQuantity());
							//sheet.autoSizeColumn(colnum-1, true);
							//単位
							cell = row.createCell(colnum++);
							cell.setCellStyle(style_str);
							cell.setCellValue(item.getUnit());
							//sheet.autoSizeColumn(colnum-1, true);
							//発注金額（税抜）
							cell = row.createCell(colnum++);
							cell.setCellStyle(style_int);
							String itemOrderAmount = "";
							if (Objects.nonNull(item.getOrderAmount())) {
								itemOrderAmount = item.getOrderAmount().toString();
							}
							cell.setCellFormula(itemOrderAmount);
							sheet.autoSizeColumn(colnum-1, true);
						}
					}
				}
			}
		}

		sheet.setDisplayGridlines(false);
		sheet.setFitToPage(true);
		printSetup.setLandscape(true);
		printSetup.setHResolution((short)(colNumber-1));
		sheet.setAutobreaks(true);
		sheet.setFitToPage(false);

	}

	private int createParentColumn(Sheet sheet, Row row, String summaryKey, VOrderStatusEntity order, int colnum, CellStyle style_str,CellStyle style_int,CellStyle style_postnum) {
		Cell cell = null;
		// yosanFlg: 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
		// c発注のみ「追加」と出力する
		String yosanType = "";
		if (Objects.nonNull(order.getYosanFlg()) && order.getYosanFlg() == 5) {
			yosanType = "追加";
		}

		//発注グループ
		cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
		cell.setCellValue(summaryKey);
		sheet.autoSizeColumn(colnum-1, true);
		//施工支店名
		cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getEigyousyoName());
    	sheet.autoSizeColumn(colnum-1, true);
    	//工事コード
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getKoujiCode());
    	sheet.autoSizeColumn(colnum-1, true);
    	//工事名
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getKoujiName());
    	//担当者名
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getSyainName());
    	sheet.autoSizeColumn(colnum-1, true);
  	    //着工予定日
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(dateStringFormat(order.getTyakkouDateY()));
    	sheet.autoSizeColumn(colnum-1, true);
    	//竣工予定日
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(dateStringFormat(order.getSyunkouDateY()));
    	sheet.autoSizeColumn(colnum-1, true);
    	//引渡予定日
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(dateStringFormat(order.getHikiwatasiDateY()));
    	sheet.autoSizeColumn(colnum-1, true);
    	//建築地郵便番号
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_postnum);
		cell.setCellFormula(order.getKentikutiYuubin());
    	//cell.setCellValue(order.getKentikutiYuubin());
    	sheet.autoSizeColumn(colnum-1, true);
    	//建築地
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getKentikutiJyuusyo());
  	    //発注番号
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getOrderNumber());
    	sheet.autoSizeColumn(colnum-1, true);
    	//発注種別
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(yosanType);
    	sheet.autoSizeColumn(colnum-1, true);
    	//細目工種
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(order.getSaimokuKousyuName());
    	sheet.autoSizeColumn(colnum-1, true);
    	//発注日
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_str);
    	cell.setCellValue(dateStringFormat(order.getOrderDate()));
    	sheet.autoSizeColumn(colnum-1, true);
    	//発注金額合計（税抜）
    	cell = row.createCell(colnum++);
		cell.setCellStyle(style_int);
		String orderAmount = "";
		if (Objects.nonNull(order.getOrderAmount())) {
			orderAmount = order.getOrderAmount().toString();
		}
    	cell.setCellFormula(orderAmount);
    	sheet.autoSizeColumn(colnum-1, true);
		return colnum;
	}

	private String dateStringFormat(String date) {
		String ret = "";
		if (Objects.isNull(date) || date.isEmpty()) {
			return ret;
		}
        Pattern pattern = java.util.regex.Pattern.compile("^[0-9]*$");
	    Matcher matcher = pattern.matcher(date);
		if (date.length() >= 8 && matcher.matches()) {
			String yyyy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			String dd = date.substring(6, 8);
			ret = yyyy + '年' + MM + '月' + dd + '日';
		}
		return ret;
	}

	private String dateStringFormatYearMonth(String date) {
		String ret = "";
		if (Objects.isNull(date) || date.isEmpty()) {
			return ret;
		}
        Pattern pattern = java.util.regex.Pattern.compile("^[0-9]*$");
	    Matcher matcher = pattern.matcher(date);
		if (date.length() >= 6 && matcher.matches()) {
			String yyyy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			ret = yyyy + '年' + MM + '月';
		}
		return ret;
	}

	private String zipcodeStringFormat(String zipcode) {
		String ret = "";
		if (Objects.isNull(zipcode) || zipcode.isEmpty()) {
			return ret;
		}
        Pattern pattern = java.util.regex.Pattern.compile("^[0-9]*$");
	    Matcher matcher = pattern.matcher(zipcode);
		if (zipcode.length() == 7 && matcher.matches()) {
			String head = zipcode.substring(0, 3);
			String detail = zipcode.substring(3, 7);
			ret = '〒' + head + '-' + detail;
		}
		return ret;
	}

	private String getSummaryDate(Timestamp appDateTimestamp) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String appDateStr = appDateTimestamp.toString();
		log.info("timestamp to string: " + appDateStr );

		Date appDate = CommonUtils.parseDateStringToDate(appDateStr, "yyyy-MM-dd HH:mm:ss", Locale.JAPAN);
		cal.setTime(appDate);
		// アーカイブ対象年月の算出
		resultDate = CommonUtils.formatDateToString(cal.getTime(), "yyyyMMddHHmmss", "JST");
		log.info("getSummaryDate: " + resultDate);
		return resultDate;
	}

	private String getBillingDate(Timestamp appDateTimestamp) {
		Calendar cal = Calendar.getInstance();
		String resultDate = null;

		String appDateStr = appDateTimestamp.toString();
		log.info("timestamp to string: " + appDateStr );

		Date appDate = CommonUtils.parseDateStringToDate(appDateStr, "yyyy-MM-dd HH:mm:ss", Locale.JAPAN);
		cal.setTime(appDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.add(Calendar.MONTH, 1);

		// アーカイブ対象年月の算出
		resultDate = CommonUtils.formatDateToString(cal.getTime(), "yyyyMMddHHmmss", "JST");
		log.info("getBillingDate: " + resultDate);
		return resultDate;
	}

}
