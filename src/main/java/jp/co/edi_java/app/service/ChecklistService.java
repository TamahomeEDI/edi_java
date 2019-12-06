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
import jp.co.edi_java.app.dto.ChecklistOrderDetailDto;
import jp.co.edi_java.app.dto.ChecklistOrderDto;
import jp.co.edi_java.app.dto.GyousyaEigyousyoDto;
import jp.co.edi_java.app.dto.SearchKoujiInfoDto;
import jp.co.edi_java.app.dto.SearchOrderInfoDto;
import jp.co.edi_java.app.entity.TOrderItemEntity;
import jp.co.edi_java.app.entity.VOrderStatusEntity;
import jp.co.edi_java.app.form.SearchForm;
import jp.co.edi_java.app.util.file.FileApi;
import jp.co.edi_java.app.util.sap.SapApi;
import jp.co.edi_java.app.util.sap.SapApiAnalyzer;
import jp.co.edi_java.app.util.sap.SapApiConsts;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import jp.co.keepalive.springbootfw.util.consts.ResponseCode;
import jp.co.keepalive.springbootfw.util.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("request")
public class ChecklistService {

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

	// ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ 旧処理
	@SuppressWarnings("unchecked")
	public ArrayList<ChecklistOrderDto> getOrderList(SearchForm form) {

		//結果リストの初期化
		ArrayList<ChecklistOrderDto> ret = new ArrayList<ChecklistOrderDto>();

		//社員フラグ
		String userFlg = form.getUserFlg();
		//発注ステータス
		String searchOrderStatus = form.getOrderStatus();


		boolean paramFlg = false;
		if(!StringUtils.isNullString(form.getConfirmationDateFrom()) || !StringUtils.isNullString(form.getConfirmationDateTo()) ||
		   !StringUtils.isNullString(form.getCompletionDateFrom()) || !StringUtils.isNullString(form.getCompletionDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelRequestDateFrom()) || !StringUtils.isNullString(form.getOrderCancelRequestDateTo()) ||
		   !StringUtils.isNullString(form.getOrderCancelAgreeDateFrom()) || !StringUtils.isNullString(form.getOrderCancelAgreeDateTo())) {
			paramFlg = true;
		}
		//施工支店リストの取得
		List<GyousyaEigyousyoDto> eigyousyoList = mEigyousyoDao.selectListByPartner(form.getGyousyaCode(),null);
		//SAPデータ取得結果リストの初期化
		List<Map<String, Object>> sapOrderList = new ArrayList<Map<String, Object>>();

		//発注番号リスト
		List<String> orderNumberList =  new ArrayList<String>();
		//SAPOrderマップ
		Map<String, Map<String, Object>> sapOrderMap = new HashMap<String, Map<String, Object>>();
		//工事コードマップ
		Map<String, String> koujiMap = new HashMap<String, String>();

		// 施工支店分のSAPデータ取得
		if (Objects.nonNull(eigyousyoList)) {
			for (GyousyaEigyousyoDto eigyousyo : eigyousyoList) {
				form.setEigyousyoCode(eigyousyo.getEigyousyoCode());
				form.setEigyousyoGroupCode(eigyousyo.getEigyousyoGroupCode());

				//sapからデータ取得
				Map<String, Object> data = SapApi.orderList(form);

				//結果情報取得
				Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
				if(SapApiAnalyzer.chkResultInfo(resultInfo)) {
					throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
				}
				Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
				if (Objects.nonNull(obj)) {
					if(obj instanceof List) {
						//1件より多い場合
						for (Map<String, Object> o : (List<Map<String, Object>>)obj) {
							String orderSettlementFlg = o.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
							String orderNumber = o.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
							if (Objects.nonNull(orderNumber) && !orderNumber.isEmpty()) {
								if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
									//発注番号をキャッシュ
									orderNumberList.add(orderNumber);
									//SAPOrderをキャッシュ
									sapOrderMap.put(orderNumber, o);
									//工事コードを取得
									koujiMap.put(o.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString(), "1");

									sapOrderList.add(o);
								}
							}
						}
					} else {
						//1件の場合
						Map<String, Object> o = (Map<String, Object>)obj;
						String orderSettlementFlg = o.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
						String orderNumber = o.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
						if (Objects.nonNull(orderNumber) && !orderNumber.isEmpty()) {
							if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
								//発注番号をキャッシュ
								orderNumberList.add(orderNumber);
								//SAPOrderをキャッシュ
								sapOrderMap.put(orderNumber, o);
								//工事コードを取得
								koujiMap.put(o.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString(), "1");

								sapOrderList.add(o);
							}
						}
					}
				}
			}
		}

		//sapのデータが存在しなかったら空のリストを返却
		if(Objects.isNull(sapOrderList) || sapOrderList.size() < 1) {
			return ret;
		}

		// EDIの発注情報取得
		List<SearchOrderInfoDto> sorderinfoList = searchDao.getOrderInfoList(orderNumberList);
		Map<String, SearchOrderInfoDto> sorderinfoMap = new HashMap<String, SearchOrderInfoDto>();
		for (SearchOrderInfoDto soinfDto : sorderinfoList) {
			Map<String, Object> sap = sapOrderMap.get(soinfDto.getOrderNumber());
			String orderDate = sap.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
			setOrderStatus(soinfDto, orderDate);
			// EDI発注情報をキャッシュ
			sorderinfoMap.put(soinfDto.getOrderNumber(), soinfDto);
		}
		// EDIの発注情報取得（検索条件あり）
		List<SearchOrderInfoDto> sorderinfoList2 = searchDao.selectOrderInfoList(orderNumberList, form);
		Map<String, SearchOrderInfoDto> sorderinfoMap2 = new HashMap<String, SearchOrderInfoDto>();
		for (SearchOrderInfoDto soinfDto : sorderinfoList2) {
			Map<String, Object> sap = sapOrderMap.get(soinfDto.getOrderNumber());
			String orderDate = sap.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
			setOrderStatus(soinfDto, orderDate);
			// orderstatusで絞り込み
			if (Objects.nonNull(searchOrderStatus)) {
				if (soinfDto.getOrderStatus().equals(searchOrderStatus)) {
					// EDI発注情報をキャッシュ
					sorderinfoMap2.put(soinfDto.getOrderNumber(), soinfDto);
				}
			} else {
				// EDI発注情報をキャッシュ
				sorderinfoMap2.put(soinfDto.getOrderNumber(), soinfDto);
			}
		}

		// 工事情報の取得
		List<String> koujiCodeList =  new ArrayList<String>();
		for (String kc : koujiMap.keySet()) {
			koujiCodeList.add(kc);
		}
		Map<String, SearchKoujiInfoDto> skinfoMap = new HashMap<String, SearchKoujiInfoDto>();

		List<SearchKoujiInfoDto> koujiList = searchDao.getKoujiInfoList(koujiCodeList, form.getKoujiName(), null, null);
		for (SearchKoujiInfoDto koujiDto: koujiList) {
			//工事情報のキャッシュ
			skinfoMap.put(koujiDto.getKoujiCode(), koujiDto);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : sapOrderList) {
			String orderNumber = map.get(SapApiConsts.PARAMS_ID_SEBELN).toString();
			String koujiCode = map.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString();
			String orderDate = map.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();

			//発注情報取得
			SearchOrderInfoDto order = sorderinfoMap.get(orderNumber);
			//発注情報取得（検索条件あり）
			SearchOrderInfoDto searchOrderInfoDto = sorderinfoMap2.get(orderNumber);
			//工事検索情報取得
			SearchKoujiInfoDto searchKoujiInfoDto = skinfoMap.get(koujiCode);

			//EDIにデータが存在しない
			if(searchOrderInfoDto == null) {

				//業者の場合、未発注(発注日が00000000または空文字)データを除外
				if(userFlg.equals(USER_FLG_PARTNER) && (StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
					continue;
				}
				//検索ステータスが未発注または発注済み以外の場合、データを除外
				else if(!(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) || searchOrderStatus.equals(ORDER_STATUS_ORDERING))) {
					continue;
				}
				//検索ステータスが未発注の場合、未発注(発注日が00000000または空文字)以外のデータを除外
				else if(searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))) {
					continue;
				}
				//検索ステータスが未発注以外の場合、未発注(発注日が00000000または空文字)データを除外
				else if((StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) && !searchOrderStatus.equals(ORDER_STATUS_NOT_ORDERING)) {
					continue;
				}
				//検索ステータスと発注ステータスが一致しないデータを除外
				if(order != null) {
					if(!order.getOrderStatus().equals(searchOrderStatus)) {
						continue;
					}
				}
			}
			//EDIにデータが存在する…検索条件にヒットするorステータスに該当しないデータ
			else {
				if(searchOrderInfoDto.getOrderStatus().equals(ORDER_STATUS_NOT_ORDERING) && !(StringUtils.isNullString(orderDate) || orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING))){
					searchOrderInfoDto.setOrderStatus(ORDER_STATUS_ORDERING);
					order.setOrderStatus(ORDER_STATUS_ORDERING);
				}
				//検索ステータスと発注ステータスが一致しないデータを除外
				if(!order.getOrderStatus().equals(searchOrderStatus)) {
					continue;
				}
			}

			if(!(paramFlg && searchOrderInfoDto == null) && searchKoujiInfoDto != null) {
				//発注書一覧の表示対象に対し発注情報詳細を取得して、チェックリストデータを生成する
				ChecklistOrderDto dto = getChecklistData(orderNumber, searchOrderInfoDto, searchKoujiInfoDto);
				if (Objects.nonNull(dto)) {
					ret.add(dto);
				}
			}
		}
		return ret;
	}

	/** オーダーステータスの設定 */
	private void setOrderStatus(SearchOrderInfoDto soinfDto, String orderDate) {
		//オーダーステータスの算出
		String remandFlg = Objects.nonNull(soinfDto.getRemandFlg()) ? soinfDto.getRemandFlg() : "";
		String receiptFlg = Objects.nonNull(soinfDto.getManagerReceiptFlg()) ? soinfDto.getManagerReceiptFlg() : "";
		String confirmationFlg = Objects.nonNull(soinfDto.getConfirmationFlg()) ? soinfDto.getConfirmationFlg() : "";
		orderDate = Objects.nonNull(orderDate) ? orderDate : "";
		soinfDto.setOrderStatus("0");
		if (confirmationFlg.equals("2")) {
			soinfDto.setOrderStatus("6");
		} else {
			if (remandFlg.equals("1")) {
				soinfDto.setOrderStatus("5");
			} else {
				if (confirmationFlg.equals("0")) {
					if (Objects.isNull(soinfDto.getConfirmationRequestDate())) {
						soinfDto.setOrderStatus("0");
						if (! orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
							soinfDto.setOrderStatus("1");
						}
					} else {
						soinfDto.setOrderStatus("1");
					}
				} else if (confirmationFlg.equals("1") && Objects.isNull(soinfDto.getWorkNumber())) {
					soinfDto.setOrderStatus("2");
				} else if (Objects.nonNull(soinfDto.getWorkNumber()) && receiptFlg.equals("0")) {
					soinfDto.setOrderStatus("3");
				} else if (receiptFlg.equals("1") && remandFlg.equals("0")) {
					soinfDto.setOrderStatus("4");
				}
			}
		}
	}

	/** チェックリスト出力用データの作成 */
	@SuppressWarnings("unchecked")
	private ChecklistOrderDto getChecklistData(String orderNumber, SearchOrderInfoDto searchOrderInfoDto, SearchKoujiInfoDto searchKoujiInfoDto) {
		//発注詳細取得
		Map<String, Object> data = SapApi.orderDetail(orderNumber);
		//発注詳細は1件の場合と複数件の場合がある（複数件の場合はマイナス発注有）
		Object sapObj = data.get(SapApiConsts.PARAMS_KEY_T_E_01004);
		Map<String, Object> retDetail = null;
		//オブジェクトがListの場合
		if (sapObj instanceof List){
			List<Map<String, Object>> retDetailList = (List<Map<String, Object>>) sapObj;
			//発注詳細分チェックする
			for (Map<String, Object> detail : retDetailList) {
				//発注精算フラグ取得
				String orderSettlementFlg = detail.get(SapApiConsts.PARAMS_ID_ZHCSSF).toString();
				//マイナス発注じゃなかったら発注詳細とする
				if (!(!StringUtils.isNullString(orderSettlementFlg) && orderSettlementFlg.equals(ORDER_SETTLEMENT_FLG_VALUE_X))) {
					retDetail = detail;
					break;
				}
			}
			//発注詳細が存在しなかったら
			if (retDetail == null) {
				throw new CoreRuntimeException(ResponseCode.ERROR_CODE_1000);
			}
		}
		//List以外の場合
		else {
			retDetail = (Map<String, Object>) sapObj;
		}
		Map<String, Object> resultInfo = SapApiAnalyzer.analyzeResultInfo(data);
		if (SapApiAnalyzer.chkResultInfo(resultInfo)) {
			throw new CoreRuntimeException(resultInfo.get(SapApiConsts.PARAMS_ID_ZMESSAGE).toString());
		}

		String orderDate = retDetail.get(SapApiConsts.PARAMS_ID_ZORDDT).toString();
		ChecklistOrderDto ret = new ChecklistOrderDto();
		ret.setOrderNumber(retDetail.get(SapApiConsts.PARAMS_ID_SEBELN).toString());
		ret.setGyousyaCode(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
		ret.setGyousyaName(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSNM).toString());

//		MGyousyaEntity gyousya = mGyousyaDao.select(retDetail.get(SapApiConsts.PARAMS_ID_ZGYSCD).toString());
//		if (Objects.nonNull(gyousya)) {
//			ret.setGyousyaName(gyousya.getGyousyaName());
//		}

		ret.setKoujiCode(retDetail.get(SapApiConsts.PARAMS_ID_ZWRKCD).toString());
		ret.setKoujiName(searchKoujiInfoDto.getKoujiName());
		ret.setEigyousyoCode(searchKoujiInfoDto.getEigyousyoCode());
		ret.setEigyousyoName(searchKoujiInfoDto.getEigyousyoName());
		ret.setSyainCode(searchKoujiInfoDto.getTantouSyainCode());
		ret.setSyainName(searchKoujiInfoDto.getSyainName());
		ret.setTyakkouDate(searchKoujiInfoDto.getTyakkouDate());
		ret.setTyakkouDateY(searchKoujiInfoDto.getTyakkouDateY());
		ret.setSyunkouDate(searchKoujiInfoDto.getSyunkouDate());
		ret.setSyunkouDateY(searchKoujiInfoDto.getSyunkouDateY());
		ret.setKentikutiYuubin(searchKoujiInfoDto.getKentikutiYuubin());
		ret.setKentikutiJyuusyo(searchKoujiInfoDto.getKentikutiJyuusyo());
		ret.setSaimokuKousyuCode(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKNO).toString());
		ret.setSaimokuKousyuName(retDetail.get(SapApiConsts.PARAMS_ID_ZSMKSY).toString());
		ret.setOrderAmount(retDetail.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString());
		//SAPで発注済みのものはそれを優先
		if (!StringUtils.isNullString(orderDate) && !orderDate.equals(ORDER_DATE_VALUE_NOT_ORDERING)) {
			ret.setOrderDate(orderDate);
		}
		//EDIで発注済みのもので発注依頼日がnullでない場合はそれを優先
		else if(searchOrderInfoDto != null && searchOrderInfoDto.getConfirmationRequestDate() != null) {
			ret.setOrderDate(sdf.format(searchOrderInfoDto.getConfirmationRequestDate()));
		}
		//上記以外はSAPのデータを設定
		else {
			ret.setOrderDate(orderDate);
		}

		ArrayList<ChecklistOrderDetailDto> itemList = new ArrayList<ChecklistOrderDetailDto>();

		Object obj = data.get(SapApiConsts.PARAMS_KEY_T_E_01002);
		List<Map<String, Object>> retItemList = new ArrayList<>();
		if (obj instanceof List) {
			retItemList = (List<Map<String, Object>>)obj;
		} else {
			retItemList.add((Map<String, Object>)obj);
		}

		//データの詰め替え＆必要なデータ取得
		for (Map<String, Object> map : retItemList) {
			ChecklistOrderDetailDto dto = new ChecklistOrderDetailDto();
			dto.setName(map.get(SapApiConsts.PARAMS_ID_MAKTX).toString());
			dto.setPrice(map.get(SapApiConsts.PARAMS_ID_ZHTTNK).toString());
			dto.setQuantity(map.get(SapApiConsts.PARAMS_ID_ZMENGE).toString());
			dto.setUnit(map.get(SapApiConsts.PARAMS_ID_ZTANIN).toString());
			dto.setAmount(map.get(SapApiConsts.PARAMS_ID_ZHTKGK).toString());
			dto.setAmountTax(map.get(SapApiConsts.PARAMS_ID_ZHTKZG).toString());
			dto.setAmountTaxInclud(map.get(SapApiConsts.PARAMS_ID_ZHTKZTG).toString());
			dto.setDeliveryQuantity(map.get(SapApiConsts.PARAMS_ID_ZUKEMG).toString());
			dto.setDeliveryAmount(map.get(SapApiConsts.PARAMS_ID_ZUKEKN).toString());
			dto.setRemainQuantity(map.get(SapApiConsts.PARAMS_ID_ZZANMG).toString());
			dto.setRemainAmount(map.get(SapApiConsts.PARAMS_ID_ZZANKN).toString());
			itemList.add(dto);
		}
		ret.setItemList(itemList);
		return ret;
	}
}
