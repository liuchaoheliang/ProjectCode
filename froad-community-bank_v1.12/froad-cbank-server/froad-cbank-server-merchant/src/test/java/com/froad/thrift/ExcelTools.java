package com.froad.thrift;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.froad.logback.LogCvt;
import com.froad.util.PropertiesUtil;

public class ExcelTools {

	public static void main(String[] args) throws Exception {
		PropertiesUtil.load();
		// 读取Excel
		String excelPath = "C:\\Users\\cheno\\Desktop\\1111.xls";
		
		InputStream input = new FileInputStream(excelPath);
        Workbook wb = new HSSFWorkbook(input);  
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
        int index = 0;
        while (rows.hasNext()) {
        	index ++;
        	Row row = rows.next();
        	if(index == 1)continue;
            
            Cell merchantId = row.getCell(0);
            Cell phone = row.getCell(8);
            if (null != phone) {
            	phone.setCellType(Cell.CELL_TYPE_STRING);
			}
            Cell createTime = row.getCell(4);
            Cell username = row.getCell(7);
            
//            String areaName = areaNameCell.getStringCellValue();
//            String areaId = areaMap.get(areaName);
//            if(areaId == null){
//            	LogCvt.info("异常地区无区域ID："+areaName);
//            	continue;
//            }
            // update语句
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO cb_merchant_user VALUE(NULL,'");
            sb.append("2015-11-09 17:00:00");
            sb.append("','chongqing','");
            sb.append(merchantId.getStringCellValue());
            sb.append("','0',100000000,'");
            sb.append(username.getStringCellValue());
            sb.append("','','96e79218965eb72c92a549dd5a330112','','");
            if (null != phone) {
            	sb.append(phone.getStringCellValue());
			}
            sb.append("',FALSE,FALSE,'');");
            LogCvt.info(sb.toString());
        }
        System.out.println(index);
	}
	
}
