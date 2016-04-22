package com.froad.cbank.coremodule.normal.boss.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;

/**
 * excel读取工具
 * @ClassName ExcelReaderUtils
 * @author zxl
 * @date 2015年10月29日 上午11:00:47
 */
public class ExcelReaderUtils{

	
	/**
	 * 文件读取
	 * @tilte readExcel
	 * @author zxl
	 * @date 2015年10月29日 上午11:58:09
	 * @param is 输入流
	 * @param fileName 文件名
	 * @param RowMax 读取最大行数 null读全部
	 * @param CellNum 读取列表 不为空
	 * @param list 
	 * @throws BossException
	 * @throws Exception
	 */
	public static void readExcel(InputStream is,String fileName,Integer RowMax,Integer CellNum,List<List<String>> list) throws BossException, Exception{

		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		try{
			if(CellNum == null || CellNum == 0){
				return;
			}
			if(RowMax != null && RowMax == 0){
				return;
			}
			if (fileName.endsWith("xls")) {  
                wb = new HSSFWorkbook(is);  
            } else {  
                wb = new XSSFWorkbook(is);  
            }   
			//sheet
			sheet = wb.getSheetAt(0);
			
			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			if(rowNum == 0){
				return;
			}else{
				rowNum++;
			}
			if(RowMax != null){
				rowNum = rowNum>RowMax?RowMax:rowNum;
			}
			for (int rowIndex = 0; rowIndex < rowNum; rowIndex++){
				row = sheet.getRow(rowIndex);
				List<String> l = new ArrayList<String>();
				for(int celIndex = 0; celIndex < CellNum ; celIndex++){
					l.add(getStringCellValue(row.getCell(celIndex)));
				}
				list.add(l);
			}
		} catch (FileNotFoundException e){
			LogCvt.info(e.getMessage());
			throw new BossException("文件读取失败");
		}finally{
			if (is != null){
				is.close();
			}
		}
	}
	
	public static String getStringCellValue(Cell cell) {
    	if (cell == null){
        	return "";
        }
        String strCell = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
        	BigDecimal bd = new BigDecimal(String.valueOf(cell.getNumericCellValue()));  
        	strCell = bd.toPlainString();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
        	strCell = "";  
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }
    
	
	public static void main(String[] args) {
		try {
			File f = new File("/home/ling/Desktop/boss1.6.0.xlsx");
			List<List<String>> list = new ArrayList<List<String>>();
			ExcelReaderUtils.readExcel(new FileInputStream(f), f.getName(), null, 10, list);
			
			for(List<String> l : list){
				System.err.println(l.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}