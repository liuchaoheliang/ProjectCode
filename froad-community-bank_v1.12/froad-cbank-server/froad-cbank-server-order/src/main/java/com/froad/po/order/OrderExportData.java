package com.froad.po.order;

import java.util.List;

public class OrderExportData {
	private List<String> header;
	private List<List<String>> data;
	private String sheetName;
	private String excelFileName;
	
	public List<String> getHeader() {
		return header;
	}
	public void setHeader(List<String> header) {
		this.header = header;
	}
	public List<List<String>> getData() {
		return data;
	}
	public void setData(List<List<String>> data) {
		this.data = data;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
}
