package jp.co.edi_java.app.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import jp.co.edi_java.app.dao.MEigyousyoDao;
import jp.co.edi_java.app.dao.MKoujiDao;
import jp.co.edi_java.app.dao.SearchDao;
import jp.co.edi_java.app.dao.TOrderDao;
import jp.co.edi_java.app.dao.gyousya.MGyousyaDao;
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class OrderCheckListService {

	@Autowired
    public SearchDao searchDao;

	@Autowired
    public MGyousyaDao mGyousyaDao;

	@Autowired
    public TOrderDao tOrderDao;

	@Autowired
    public MKoujiDao mKoujiDao;

	@Autowired
    public MEigyousyoDao mEigyousyoDao;

	public static String USER_FLG_TAMA = "1";
	public static String USER_FLG_PARTNER = "0";

	public static String ORDER_STATUS_NOT_ORDERING = "0";
	public static String ORDER_STATUS_ORDERING = "1";

	//未発注の発注日（00000000）
	public static String ORDER_DATE_VALUE_NOT_ORDERING = "00000000";

	//発注精算フラグ（マイナス発注）
	public static String ORDER_SETTLEMENT_FLG_VALUE_X = "X";

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public int countCheckList(SearchForm form) {
		return searchDao.countOrderItemByVOrder(form);
	}

	@SuppressWarnings("unchecked")
	public void createCheckList(SearchForm form) {

		String folderPath = form.getFolderPath();
		String fileName = form.getFileName();
		List<VOrderStatusEntity> vOrderList = searchDao.selectVOrderNonLimit(form);
		List<TOrderItemEntity> tOrderItemList = searchDao.selectOrderItemByVOrder(form);
		Map<String, List<TOrderItemEntity>> orderItemMap = new HashMap<String, List<TOrderItemEntity>>();
		for (TOrderItemEntity entity : tOrderItemList) {
			if (! orderItemMap.containsKey(entity.getOrderNumber())) {
				orderItemMap.put(entity.getOrderNumber(), new ArrayList<TOrderItemEntity>());
			}
			orderItemMap.get(entity.getOrderNumber()).add(entity);
		}
		// チェクリスト出力フォルダの作成
		FileApi.createDirectories(folderPath);
		String fileFullPath = folderPath + fileName;
		createCheckListAux(vOrderList, orderItemMap, fileFullPath);

	}

	@SuppressWarnings("unchecked")
	private void createCheckListAux(List<VOrderStatusEntity> vOrderList, Map<String, List<TOrderItemEntity>> orderItemMap, String fileFullPath) {
		Workbook book = null;
		FileOutputStream fout = null;
		try {
			book = new SXSSFWorkbook();

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
            //日時表示用のスタイル
            CellStyle style_date = book.createCellStyle();
            style_date.setBorderTop(BorderStyle.THIN);
            style_date.setBorderBottom(BorderStyle.THIN);
            style_date.setBorderLeft(BorderStyle.THIN);
            style_date.setBorderRight(BorderStyle.THIN);
            style_date.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_date.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_date.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_date.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_date.setDataFormat(format.getFormat("yyyy年mm月dd日"));
            style_date.setVerticalAlignment(VerticalAlignment.TOP);
            style_date.setFont(font);

            Sheet sheet = book.createSheet();
            if (sheet instanceof SXSSFSheet) {
                ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
            }
            //シート名称の設定
            book.setSheetName(0, "受注明細一覧");
            String [] titleArray = {
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
            	if (title.equals("工事名")||title.equals("建築地")||title.equals("品名")) {
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
            if (Objects.nonNull(vOrderList) && !vOrderList.isEmpty() && Objects.nonNull(orderItemMap) && !orderItemMap.isEmpty()) {
            	for (VOrderStatusEntity order : vOrderList) {
            		List<TOrderItemEntity> orderItemList = orderItemMap.get(order.getOrderNumber());
            		int colnum = 0;
            		if (Objects.isNull(orderItemList) || orderItemList.isEmpty()) {
            			// 親項目のみ出力
            			rowNumber += 1;
            			row = sheet.createRow(rowNumber);
            			colnum = createParentColumn(sheet, row, order, colnum, style_str, style_int, style_postnum);
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
            				colnum = createParentColumn(sheet, row, order, colnum, style_str, style_int, style_postnum);
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
            				sheet.autoSizeColumn(colnum-1, true);
            				//数量
            				cell = row.createCell(colnum++);
            				cell.setCellStyle(style_double);
            				cell.setCellValue(item.getOrderQuantity());
            				sheet.autoSizeColumn(colnum-1, true);
            				//単位
            				cell = row.createCell(colnum++);
            				cell.setCellStyle(style_str);
            				cell.setCellValue(item.getUnit());
            				sheet.autoSizeColumn(colnum-1, true);
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

            // 外周の罫線
//            if (rowNumber > 0) { rowNumber = rowNumber -1; }
//            CellRangeAddress region = new CellRangeAddress(0,rowNumber, 0, colNumber);
//            RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
//            RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
//            RegionUtil.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex(), region, sheet);
//            RegionUtil.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex(), region, sheet);
//            RegionUtil.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex(), region, sheet);
//            RegionUtil.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex(), region, sheet);


            sheet.setDisplayGridlines(false);
            sheet.setFitToPage(true);
            PrintSetup printSetup = book.getSheetAt(0).getPrintSetup();
            printSetup.setLandscape(true);
            printSetup.setHResolution((short)(colNumber-1));
            sheet.setAutobreaks(true);
            sheet.setFitToPage(false);

            //ファイル出力
            fout = new FileOutputStream(fileFullPath);
            book.write(fout);
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

	private int createParentColumn(Sheet sheet, Row row, VOrderStatusEntity order, int colnum, CellStyle style_str,CellStyle style_int,CellStyle style_postnum) {
		Cell cell = null;
		// yosanFlg: 1:本体発注, 2:S発注, 3:A発注, 4:B発注, 5:C発注
		// c発注のみ「追加」と出力する
		String yosanType = "";
		if (Objects.nonNull(order.getYosanFlg()) && order.getYosanFlg() == 5) {
			yosanType = "追加";
		}
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
		if (date.length() == 8 && matcher.matches()) {
			String yyyy = date.substring(0, 4);
			String MM = date.substring(4, 6);
			String dd = date.substring(6, 8);
			ret = yyyy + '年' + MM + '月' + dd + '日';
		}
		return ret;
	}
}
