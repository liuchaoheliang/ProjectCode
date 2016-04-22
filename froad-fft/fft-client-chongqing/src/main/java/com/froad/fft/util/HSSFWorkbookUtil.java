package com.froad.fft.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class HSSFWorkbookUtil {
	public static Logger logger = Logger.getLogger(HSSFWorkbookUtil.class);
	
	public static HSSFWorkbook getWorkBook(String excelPath) {
		HSSFWorkbook sendBook = null;
		try {
			File file = new File(excelPath);
			FileInputStream fileInputStream = new FileInputStream (file);
			sendBook = new HSSFWorkbook(fileInputStream);
		} catch (Exception e) {
			logger.error("excel创建失败" , e);
			throw new RuntimeException("excel创建失败");
		}
		return sendBook;
	}
	
	//所有边框
	public static HSSFCellStyle getCellStyle(HSSFWorkbook sendBook){
		HSSFCellStyle cellStyle = sendBook.createCellStyle();
		cellStyle.setBorderTop((short)1);//上边框
		cellStyle.setBorderRight((short)1);//右边框
		cellStyle.setBorderBottom((short)1);//下边框
		cellStyle.setBorderLeft((short)1);//左边框
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cellStyle.setWrapText(true);//单元格中的内容换行
		return cellStyle;
	}
	
	//无边框(字体加粗,红色)
	public static HSSFCellStyle getCellStyle2(HSSFWorkbook sendBook){
		HSSFCellStyle cellStyle = sendBook.createCellStyle();
		HSSFFont font = sendBook.createFont();
        font.setColor(HSSFFont.COLOR_RED);//字体颜色
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置字体加粗
        cellStyle.setFont(font);
		cellStyle.setBorderTop((short)0);//上边框
		cellStyle.setBorderRight((short)0);//右边框
		cellStyle.setBorderBottom((short)0);//下边框
		cellStyle.setBorderLeft((short)0);//左边框
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		cellStyle.setWrapText(true);//单元格中的内容换行
		return cellStyle;
	}

}
