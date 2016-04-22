package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

/**
 * excel导出工具类
 * @author lijianjun
 * @date 2014-03-18 
 */
/**
 * @author Administrator
 *
 */
public class ExcelUtil {

	public static int MAX_ROWS =65535;
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
			List<String> header, List<List<String>> data, String foot) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();

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

			// 生成并设置另一个样式
			CellStyle style2 = workbook.createCellStyle();
			style2.setFillForegroundColor(HSSFColor.WHITE.index);
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 生成另一个字体
			Font font2 = workbook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			// 把字体应用到当前的样式
			style2.setFont(font2);
			//减去标题MAX_ROWS-1 分页数
			int pageSize=data!=null?data.size()%MAX_ROWS>0?data.size()/MAX_ROWS+1:data.size()/MAX_ROWS:0;
			if (header != null) {
				int number=0;//data索引标注
				for(int k=0;k<pageSize;k++){
					// 得到excel的第0张表
					Sheet sheet = workbook.createSheet(title+"-"+k);
					// 产生表格标题行
					Row row = sheet.createRow(0);
					//设置标题行高
					row.setHeight((short)(400));
					//循环头部格式
					for (short i = 0; i < header.size(); i++) {
						//根据内容自适应宽度
						sheet.setColumnWidth(i, columWidthMap.get(Integer.valueOf(i)));
						Cell cell = row.createCell(i);
						cell.setCellStyle(style);
						cell.setCellValue(header.get(i));
					}
					
					// 遍历集合数据，产生数据行
					//每次减去MAX_ROWS
//					Iterator<List<String>> it = data.iterator();
					int index = 0;
					for(int j=0;j<MAX_ROWS;j++){
						if(number>(data.size()-1))break;
						row = sheet.createRow(++index);
						
						List<String> results =data.get(number++);
						for (int i = 0; i < results.size(); i++) {
							Cell cell = row.createCell(i);
							cell.setCellStyle(style2);
							cell.setCellValue(results.get(i));
						}
					}
				}
			}
			return workbook;
		} catch (Exception e) {
			LogCvt.info("Excel导出"+e.getMessage(), e);
		} finally {
		}

		return null;
	}

	public static String getFileName(){
		return ExcelUtil.format.format(new Date()) + Math.round((Math.random() * 10)) + ".xls";
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
