package com.froad.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel导出工具类
 * @author lijianjun
 * @date 2014-03-18 
 */
/**
 * @author Administrator
 *
 */
public class ReportExcelUtil {

	public static int MAX_ROWS = 20000;
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	/**
	 * 
	 * @param title
	 * @param subTitle
	 * @param header
	 * @param data
	 * @param foot
	 * @return
	 */
	public static HSSFWorkbook generate(String title, String subTitle, 
			List<String> header, List<List<String>> data, String foot, int sheetIndex) {
		return generate(null, title, subTitle, header, data, foot, sheetIndex);
	}
	
	public static HSSFWorkbook generate(HSSFWorkbook wb, String title, String subTitle,
			List<String> header, List<List<String>> data, String foot, int sheetIndex) {
		try {
			HSSFWorkbook workbook = wb;
			if (workbook == null) {
				workbook = new HSSFWorkbook();
				workbook.createSheet("1");
				workbook.createSheet("2");
				workbook.createSheet("3");
				workbook.createSheet("4");
				workbook.createSheet("5");
				workbook.createSheet("6");
				workbook.createSheet("7");
			}
			
			int defaultName = sheetIndex + 1;
			title = defaultName + title;
			Sheet sheet = workbook.getSheet(defaultName + "");
			workbook.setSheetName(sheetIndex, title);
			
			// 设置表格默认列宽度为15个字节
//			sheet.setDefaultColumnWidth((short) 21);
			Map<Integer,Integer> columWidthMap = autoSetColumnWidth(header,data);
			// 生成一个样式
			CellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 生成一个字体
			Font font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 把字体应用到当前的样式
			style.setFont(font);

			// 生成数字样式
			CellStyle numStyle = workbook.createCellStyle();
			//style2.setFillForegroundColor(HSSFColor.WHITE.index);
			//style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			//style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			//style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			//style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			//style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			numStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			numStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			// 生成百分比样式
			CellStyle perStyle = workbook.createCellStyle();
			perStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			perStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			perStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
			
			// 生成另一个字体
			//Font font2 = workbook.createFont();
			//font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			// 把字体应用到当前的样式
			//style2.setFont(font2);

			// 产生表格标题行
			int rowIndex = 0;
			Row row = null;
			if (!StringUtils.isEmpty(subTitle)) {
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, header.size() - 1));
				row = sheet.createRow(0);
				//row.setHeight((short)200);
				Cell cell = row.createCell((short)0);
				
				// 把字体应用到当前的样式
				CellStyle subTitleStyle = workbook.createCellStyle();
				subTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中  
				subTitleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
				
				Font subTitleFont = workbook.createFont();
				subTitleFont.setFontHeightInPoints((short) 14);
				subTitleStyle.setFont(subTitleFont);
				
				cell.setCellStyle(subTitleStyle);
				cell.setCellValue(subTitle);
				rowIndex++;
			}
			
			row = sheet.createRow(rowIndex);
			if (header != null) {
				//设置标题行高
				row.setHeight((short)(400));
				
				for (short i = 0; i < header.size(); i++) {
					//根据内容自适应宽度
					sheet.setColumnWidth(i, columWidthMap.get(Integer.valueOf(i)));
					Cell cell = row.createCell(i);
					cell.setCellStyle(style);
					cell.setCellValue(header.get(i));
				}
			}

			// 遍历集合数据，产生数据行
			if (data != null) {
				Iterator<List<String>> it = data.iterator();
				
				while (it.hasNext()) {
					rowIndex++;
					row = sheet.createRow(rowIndex);
					
					List<String> results = it.next();
					for (int i = 0; i < results.size(); i++) {
						Cell cell = row.createCell(i);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numStyle);
						
						if (StringUtils.isEmpty(results.get(i))) {
							cell.setCellValue(results.get(i));
							continue;
						}
						
						if (results.size() == 6 && i != 1) {
							cell.setCellValue(new Double(results.get(i)));
						} else if (results.size() == 5) {
							if (i == 1 || i == 3) {
								cell.setCellValue(new Double(results.get(i)));
							} else if (i == 2 || i == 4) {
								cell.setCellStyle(perStyle);
								cell.setCellValue(new Double(results.get(i)));
							} else {
								cell.setCellValue(results.get(i));
							}
						} else {
							cell.setCellValue(results.get(i));
						}
					}
				}
			}
			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return null;
	}

	public static String getFileName(){
		return ReportExcelUtil.format.format(new Date()) + Math.round((Math.random() * 10)) + ".xls";
	}
	public static String getFileShortName(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date()) + Math.round((Math.random() * 10));
	}

	public static String getDefaultFoot() {
		String foot = "制表人：admin";
		return foot;
	}
	
	/**
	 * 自动设置列宽
	 */
	public static Map<Integer,Integer> autoSetColumnWidth(List<String> header, List<List<String>> data){
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		if (header != null) {
			for (int i = 0; i < header.size(); i++) {
				map.put(i, header.get(i).getBytes().length * 1 * 256);
			}
		}
		if (data != null) {
			for(List<String> results : data){
				for (int i = 0; i < results.size(); i++) {
				  if(results.get(i)!=null){
					int result =  results.get(i).getBytes().length * 1 * 256;
					if(map.get(i) < result){
						map.put(i, result);
					}
				  }else{
						int result =  0;
						if(map.get(i) < result){
							map.put(i, result);
						}
				  }
				}
			}
		}
		return map;
	}
}
