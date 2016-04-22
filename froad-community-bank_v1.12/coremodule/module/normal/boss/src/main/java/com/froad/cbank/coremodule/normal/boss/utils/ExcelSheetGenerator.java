package com.froad.cbank.coremodule.normal.boss.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelSheetGenerator {
	private static Logger logger = LoggerFactory.getLogger(ExcelSheetGenerator.class);
	private int colCount;
	private int rowCount = 0;
	// 总行数, 只用于查看进度, 不作为导出完成依据
	private int totalRowCount = -1;
	private SXSSFWorkbook workbook;
	private Sheet sheet;
	private Runnable completeCallback;

	public int getRowCount() {
		return rowCount;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public ExcelSheetGenerator(String sheetName, String... colNames) {
		init(sheetName, colNames);
	}

	private void init(String sheetName, String... colNames) {
		workbook = new SXSSFWorkbook(100);
		// String[] header = colNames;
		colCount = colNames.length;
		sheet = workbook.createSheet();
		workbook.setSheetName(0, sheetName);
		Row row = sheet.createRow(rowCount++);
		for (int i = 0; i < colCount; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(colNames[i]);
		}
	}

	public ExcelSheetGenerator(String sheetName, int rows, String... colNames) {
		init(sheetName, colNames);
		totalRowCount = rows;
	}
	
	public synchronized boolean addRow(String[] cols) {
		if (cols.length != colCount) {
			logger.error("Column number not coincident for sheet: " + this.workbook.getSheetName(0));
			throw new IllegalArgumentException("Column number not coincident for sheet");
		}
		Row row = sheet.createRow(rowCount++);
		for (int i = 0; i < colCount; i++) {
			Cell cell = row.createCell(i);
			String value = cols[i];
            cell.setCellValue(value);
		}
		return true;
	}
	

	public synchronized boolean addRow(Object... cols) {
		if (cols.length != colCount) {
			logger.error("Column number not coincident for sheet: " + this.workbook.getSheetName(0));
			throw new IllegalArgumentException("Column number not coincident for sheet");
		}
		Row row = sheet.createRow(rowCount++);
		for (int i = 0; i < colCount; i++) {
			Cell cell = row.createCell(i);
			Object value = cols[i];
			if(value instanceof Float) {
                cell.setCellValue((Float)value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer)value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double)value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long)value);
            } else {
                cell.setCellValue(String.valueOf(value));
            }
		}
		return true;
	}

	public void registerCompleteCallback(Runnable callback) {
		completeCallback = callback;
	}

	public void done() {
		completeCallback.run();
	}

	public SXSSFWorkbook getBook() {
		return workbook;
	}
	
	private void preWrite(){
		autoResizeAllColumns();
	}
	
	/**
	 * Invoked after setting data
	 */
	private void autoResizeAllColumns(){
		for (int i = 0; i < colCount; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public synchronized void write(OutputStream os) throws IOException {
		preWrite();
		workbook.write(os);
		workbook.dispose();
	}

	public void dispose() {
		workbook.dispose();
	}

	enum Format {
		STRING, DATE, LONG, DESCRIPTION
		// compose

	}

	public class RowTemplate {
		private final int length;
		private String[] keys;
		private Class<?>[] types;

		public RowTemplate(String[] keys, Class<?>[] types) {

			this.length = keys.length;
			this.keys = keys;
			this.types = types;
		}
	}

	public static abstract class RowFormatter<E> {

		public abstract String[] apply(E bean);
	}

	public RowTemplate createRowTemplate(String[] keys, Class<?>[] types) {
		assert keys.length == types.length;
		return new RowTemplate(keys, types);
	}

	public synchronized <E> void addRows(Class<?> clazz, List<E> rows, RowTemplate rowTemplate, String empty) {
		assert rowTemplate.length == colCount;

		for (int i = 0; i < rows.size(); i++) {
			List<String> columns = new ArrayList<String>();

			for (int j = 0; j < rowTemplate.length; j++) {
				try {
					Field field = clazz.getDeclaredField(rowTemplate.keys[j]);
					Object value = runGetter(field, clazz, rows.get(i));

					String out;
					Class<?> type = rowTemplate.types[j];
					if (type == Long.class) {
						out = FinanceFormatUtils.formatLongType((Long) value);
					} else if (type == String.class) {
						out = String.valueOf(value);
					} else if (type == Date.class) {
						out = FinanceFormatUtils.formatDate((Date) value);
					} else {
						// enum
						out = (String) type.getMethod("getDescription").invoke(value);
					}
					columns.add(out == null ? empty : out);
				} catch (NoSuchFieldException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				}
			}
			String[] row = new String[columns.size()];
			for (int k = 0; k < row.length; k++) {
				row[k] = columns.get(k);
			}
			addRow(row);
		}
	}

	public <E> void addRows(Class<?> clazz, List<E> rows, RowTemplate rowTemplate) {
		addRows(clazz, rows, rowTemplate, "");
	}

	public static <E> Object runGetter(Field field, Class<E> clazz, Object o) {

		for (Method method : clazz.getMethods()) {
			if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
				if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
					try {
						return method.invoke(o);
					} catch (Exception e) {
						logger.error("Could not determine method: " + method.getName(), e);
					}

				}
			}
		}
		return null;
	}
    
	
	
}
