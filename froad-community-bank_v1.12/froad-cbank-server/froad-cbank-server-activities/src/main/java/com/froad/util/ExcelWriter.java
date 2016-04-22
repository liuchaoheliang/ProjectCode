package com.froad.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.csvreader.CsvWriter;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelConstants;
import com.froad.ftp.FtpConstants;
import com.froad.logback.LogCvt;
import com.froad.scp.ScpUtil;
import com.froad.util.Checker;

/**
 * excel导出工具类
 * 
 * @author 张开
 *
 */
public class ExcelWriter {
	/**
	 * Excel每个工作簿的行数（系统默认）
	 */
	private static final int MAX_ERVERY_SHEET_ROW_DEFAULT = 50000;

	/**
	 * 每个Excel最大sheet数（系统默认）
	 */
	private static final int MAX_SHEET_COUNT_DEFAULT = 5;

	/**
	 * 每个Excel最大数据条数（系统默认）
	 */
	private static final int MAX_DATA_COUNT_DEFAULT = MAX_ERVERY_SHEET_ROW_DEFAULT
			* MAX_SHEET_COUNT_DEFAULT;

	/**
	 * Excel每个工作簿的行数（用户自定义）
	 */
	private Integer maxEverySheetRow;

	/**
	 * 每个Excel最大sheet数（用户自定义）
	 */
	private Integer maxSheetCount;

	/**
	 * 每个Excel最大数据条数（用户自定义）
	 */
	private Integer maxDataCount;

	/**
	 * 当前操作唯一ID
	 */
	private long writerID;

	/**
	 * 本地服务器目录（ftp.properties配置项）
	 */
	private String localFileDir;

	private int nextDataIndex = 0;

	/**
	 * 多个excel时，文件夹名
	 */
	private String folderName;

	/**
	 * 当前操作的excel文件名
	 */
	private String currentExcelName;

	/**
	 * 当前操作的row序号
	 */
	private int nextRowIndex = 0;

	/**
	 * 是否需创建文件夹
	 */
	private boolean isNeedCreateFolder = true;

	/**
	 * 当前excel写入序号
	 */
	private int excelIndex = 0;

	/**
	 * 当前sheet写入序号
	 */
	private int sheetIndex = 0;

	/**
	 * 当前row写入序号
	 */
	private long rowIndex = 0;

	/**
	 * 导出累计总耗时
	 */
	private long totalTime = 0;

	/**
	 * 返回下载链接URL
	 */
	private String url;

	public String getUrl(String zipPath) {
		upload(zipPath);
		LogCvt.info("导出：" + excelIndex + "个Excel，共" + rowIndex + "条数据，总耗时："
				+ this.totalTime + "ms，下载链接：" + url);
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 导出类初始化
	 */
	private ExcelWriter() {
	}

	/**
	 * 导出类初始化
	 * 
	 * @param everySheetCount
	 *            每个Sheet导出条数/每次分页查询条数（每次分页查询条数=每个sheet数据的条数）
	 */
	public ExcelWriter(int everySheetCount) {
		this.maxEverySheetRow = everySheetCount;
		init();
	}

	/**
	 * 导出类初始化
	 * 
	 * @param dataTotalCount
	 *            所有导出数据的总条数
	 * @param everySheetCount
	 *            每个Sheet导出条数/每次分页查询条数（每次分页查询条数=每个sheet数据的条数）
	 * @param maxErverySheetRow
	 *            每个Sheet最大行数
	 * @param maxSheetCount
	 *            每个Excel最大sheet数
	 */
	public ExcelWriter(long dataTotalCount, long everySheetCount,
			int maxErverySheetRow, int maxSheetCount) {
		// this.dataTotalCount = dataTotalCount;
		// this.everySheetCount = everySheetCount;
		this.maxEverySheetRow = maxErverySheetRow;
		this.maxSheetCount = maxSheetCount;
		this.maxDataCount = this.maxEverySheetRow * this.maxSheetCount;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.writerID = ExcelConstants.getUniqueId();
		this.localFileDir = FtpConstants.SFTP_PROPERTIES
				.get(FtpConstants.LOCAL_FILE_DIR);
		if (this.maxEverySheetRow == null) {
			this.maxEverySheetRow = MAX_ERVERY_SHEET_ROW_DEFAULT;
		}
		if (this.maxSheetCount == null) {
			this.maxSheetCount = MAX_SHEET_COUNT_DEFAULT;
		}
		if (this.maxDataCount == null) {
			this.maxDataCount = MAX_DATA_COUNT_DEFAULT;
		}
		// if(this.dataTotalCount > this.maxDataCount){
		// this.folderName = String.valueOf(this.writerID);
		// this.isNeedCreateFolder = true;
		// }else{
		// this.currentExcelName = String.valueOf(this.writerID);
		// }
		this.folderName = String.valueOf(this.writerID);
		this.currentExcelName = String.valueOf(this.writerID);
	}

	/**
	 * 生成Excel-2003 .xls
	 * 
	 * @param header
	 * @param data
	 * @param sheetName
	 * @return
	 */
	private HSSFWorkbook generate2003(List<String> header,
			List<List<String>> data, String sheetName) {
		return generate2003(header, data, sheetName, null);
	}

	/**
	 * 创建标题栏
	 * 
	 * @param sheet
	 * @param header
	 *            标题
	 * @param columWidthMap
	 *            每格宽度
	 */
	private void createTitle(HSSFSheet sheet, List<String> header,
			Map<Integer, Integer> columWidthMap, CellStyle style) {
		// 冻结第一行
		sheet.createFreezePane(0, 1, 0, 1);

		HSSFRow headRow = sheet.createRow((short) 0);
		// 创建行，0表示第一行（本例是excel的标题）
		headRow.setHeight((short) (400));
		// 循环excel的标题
		for (int j = 0; j < header.size(); j++) {
			// 设置列宽
			int width = columWidthMap.get(Integer.valueOf(j));
			if (width > 255 * 256) {
				width = 255 * 255;
			}
			sheet.setColumnWidth(j, width);
			// 使用行对象创建列对象，0表示第1列
			HSSFCell cell = headRow.createCell(j);
			// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			if (header.get(j) != null) {
				// 将创建好的样式放置到对应的单元格中
				/** cell.setCellStyle(cellStyle); */
				cell.setCellValue((String) header.get(j));// 为标题中的单元格设置值
				// 添加样式
				cell.setCellStyle(style);
			} else {
				cell.setCellValue("-");
			}
		}

	}

	/**
	 * 生成Excel-2003 .xls TODO
	 * 
	 * @param header
	 * @param data
	 * @param sheetName
	 * @param sheetIndex
	 *            sheet索引
	 * @param excelFileName
	 *            本地excel文件
	 * @return
	 */
	private HSSFWorkbook generate2003(List<String> header,
			List<List<String>> data, String sheetName, HSSFWorkbook workbook,
			boolean isAppend) {
		long exportBeginTime = System.currentTimeMillis();
		int rows = 0;// 总的记录数
		if (data != null && data.size() > 0) {
			rows = data.size();
		}
		int sheetNum = 0; // 指定sheet的页数
		if (this.sheetIndex > this.maxSheetCount) {
			this.sheetIndex = 0;
		}
		if (this.sheetIndex == 0) {
			sheetNum = this.maxSheetCount;
		} else {
			long remainDataNum = data.size() - this.nextDataIndex;
			long remainRow = this.maxEverySheetRow * this.maxSheetCount
					- this.rowIndex;
			if (remainDataNum > remainRow) {
			}
		}
		if (rows % maxEverySheetRow == 0) {
			sheetNum = rows / maxEverySheetRow;
		} else {
			sheetNum = rows / maxEverySheetRow + 1;
		}
		try {
			// 生成一个样式
			if (this.sheetIndex == 0 || this.sheetIndex > maxSheetCount) {
				this.sheetIndex = 0;
				this.excelIndex++;
			}

			// 创建样式对象
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

			// 计算每列的宽度
			Map<Integer, Integer> columWidthMap = autoSetColumnWidth(header,
					data);
			// 分sheet循环生成work

			for (int i = 1; i <= sheetNum + 1; i++) {// sheetNum+1为了没数据时能生成空标题
				// 避免有数据时会多生成一个sheet
				if (sheetNum > 0 && i == sheetNum + 1) {
					break;
				}
				// 循环2个sheet的值
				/*
				 * HSSFSheet sheet = null; if (sheetIndex != null && sheetIndex
				 * <= 5) { sheet = workbook.getSheetAt(sheetIndex);
				 * sheetIndex++; }else{ sheet = workbook.createSheet(sheetName +
				 * "-Page" + i); }
				 */
				sheetIndex++;
				HSSFSheet sheet = workbook.createSheet(sheetName + "-Page"
						+ sheetIndex);

				// sheet数达到最大
				if (this.sheetIndex == maxSheetCount) {
					this.sheetIndex = 0;
					this.nextRowIndex = 0;
				}

				// 如果行数超过最大行
				if (this.nextRowIndex > this.maxEverySheetRow + 1) {
					this.nextRowIndex = 0;
					// TODO
				} else {

				}

				// 如果当前行是第一行，创建标题栏
				if (this.nextRowIndex == 0) {
					createTitle(sheet, header, columWidthMap, style);
				}

				// 遍历集合数据，产生数据行
				// int count = 0;
				// 生成一行空数据
				for (int k = 0; k < (rows < maxEverySheetRow ? rows
						: maxEverySheetRow); k++) {
					if (((i - 1) * maxEverySheetRow + k) >= rows) {
						// 如果数据超出总的记录数的时候，就退出循环
						break;
					}
					++this.nextRowIndex;
					if (this.nextRowIndex > this.maxEverySheetRow) {
						break;
					}
					// 创建1行
					// ++count;
					HSSFRow row = sheet.createRow(this.nextRowIndex);
					this.rowIndex++;
					// 分页处理，获取每页的结果集，并将数据内容放入excel单元格
					List<String> rowList = (List<String>) data.get((i - 1)
							* maxEverySheetRow + k);
					// 遍历某一行的结果
					for (int n = 0; n < rowList.size(); n++) {
						// 使用行创建列对象
						HSSFCell cell = row.createCell(n);
						if (rowList.get(n) != null) {
							cell.setCellValue((String) rowList.get(n)
									.toString());
						} else {
							cell.setCellValue("");
						}
					}
				}
			}
			long exportEndTime = System.currentTimeMillis();
			LogCvt.info("写入[" + this.currentExcelName + "]，耗时："
					+ (exportEndTime - exportBeginTime) + "ms");
			return workbook;
		} catch (Exception e) {
			LogCvt.error("生成Excel异常：", e);
		}

		return null;
	}

	/**
	 * 生成Excel-2003 .xls
	 * 
	 * @param header
	 * @param data
	 * @param sheetName
	 * @param sheetIndex
	 *            sheet索引
	 * @param excelFileName
	 *            本地excel文件
	 * @return
	 */
	private HSSFWorkbook generate2003(List<String> header,
			List<List<String>> data, String sheetName, String excelFileName) {
		long exportBeginTime = System.currentTimeMillis();
		int rows = 0;// 总的记录数
		if (data != null && data.size() > 0) {
			rows = data.size();
		}
		int sheetNum = 0; // 指定sheet的页数
		// 如果每次实际导出数据，比默认每个sheet最大限制数多，则计算sheet数。
		if (Checker.isNotEmpty(data)) {
			if (data.size() > MAX_ERVERY_SHEET_ROW_DEFAULT) {
				if (rows % maxEverySheetRow == 0) {
					sheetNum = rows / maxEverySheetRow;
				} else {
					sheetNum = rows / maxEverySheetRow + 1;
				}
			} else {
				sheetNum = 1;
				maxEverySheetRow = data.size();
			}
		}

		try {
			// 生成一个样式
			HSSFWorkbook workbook = null;
			if (Checker.isNotEmpty(excelFileName) && this.sheetIndex > 0
					&& this.sheetIndex < maxSheetCount) {
				workbook = getWorkBook(excelFileName);
			} else {
				workbook = new HSSFWorkbook();
				this.sheetIndex = 0;
				this.excelIndex++;
			}

			// 创建样式对象
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

			// 计算每列的宽度
			Map<Integer, Integer> columWidthMap = autoSetColumnWidth(header,
					data);
			// 分sheet循环生成work

			for (int i = 1; i <= sheetNum + 1; i++) {// sheetNum+1为了没数据时能生成空标题
				// 避免有数据时会多生成一个sheet
				if (sheetNum > 0 && i == sheetNum + 1) {
					break;
				}
				// 循环2个sheet的值
				/*
				 * HSSFSheet sheet = null; if (sheetIndex != null && sheetIndex
				 * <= 5) { sheet = workbook.getSheetAt(sheetIndex);
				 * sheetIndex++; }else{ sheet = workbook.createSheet(sheetName +
				 * "-Page" + i); }
				 */
				sheetIndex++;
				HSSFSheet sheet = workbook.createSheet(sheetName + "-Page"
						+ sheetIndex);
				if (this.sheetIndex == maxSheetCount) {
					this.sheetIndex = 0;
				}
				// 冻结第一行
				sheet.createFreezePane(0, 1, 0, 1);
				// 使用workbook对象创建sheet对象
				HSSFRow headRow = sheet.createRow((short) 0);
				// 创建行，0表示第一行（本例是excel的标题）
				headRow.setHeight((short) (400));
				// 循环excel的标题
				for (int j = 0; j < header.size(); j++) {
					// 设置列宽
					int width = columWidthMap.get(Integer.valueOf(j));
					if (width > 255 * 120) {
						width = 255 * 120;
					}
					sheet.setColumnWidth(j, width);
					// 使用行对象创建列对象，0表示第1列
					HSSFCell cell = headRow.createCell(j);
					// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (header.get(j) != null) {
						// 将创建好的样式放置到对应的单元格中
						/** cell.setCellStyle(cellStyle); */
						cell.setCellValue((String) header.get(j));// 为标题中的单元格设置值
						// 添加样式
						cell.setCellStyle(style);
					} else {
						cell.setCellValue("-");
					}
				}

				// 遍历集合数据，产生数据行
				int count = 0;
				// 生成一行空数据
				for (int k = 0; k < (rows < maxEverySheetRow ? rows
						: maxEverySheetRow); k++) {
					if (((i - 1) * maxEverySheetRow + k) >= rows)
						// 如果数据超出总的记录数的时候，就退出循环
						break;
					// 创建1行
					++count;
					HSSFRow row = sheet.createRow(count);
					this.rowIndex++;
					// 分页处理，获取每页的结果集，并将数据内容放入excel单元格
					List<String> rowList = (List<String>) data.get((i - 1)
							* maxEverySheetRow + k);
					// 遍历某一行的结果
					for (int n = 0; n < rowList.size(); n++) {
						// 使用行创建列对象
						HSSFCell cell = row.createCell(n);
						if (rowList.get(n) != null) {
							cell.setCellValue((String) rowList.get(n)
									.toString());
						} else {
							cell.setCellValue("");
						}
					}
				}
			}
			long exportEndTime = System.currentTimeMillis();
			LogCvt.info("写入[" + this.currentExcelName + "]，耗时："
					+ (exportEndTime - exportBeginTime) + "ms");
			return workbook;
		} catch (Exception e) {
			LogCvt.error("生成Excel异常：", e);
		}

		return null;
	}

	private HSSFWorkbook getWorkBook(String excelFileName) throws IOException {
		InputStream input = new FileInputStream(excelFileName);
		POIFSFileSystem fs = new POIFSFileSystem(input);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		return wb;
	}

	/**
	 * 
	 * @param title
	 * @param subTitle
	 * @param header
	 * @param data
	 * @param foot
	 * @return
	 */
	private SXSSFWorkbook generate2007(List<String> header,
			List<List<String>> data, String sheetName, boolean isAppend) {
		long exportBeginTime = System.currentTimeMillis();
		int rows = 0;// 总的记录数
		if (data != null && data.size() > 0) {
			rows = data.size();
		}
		int sheetNum = 0; // 指定sheet的页数
		if (rows % maxEverySheetRow == 0) {
			sheetNum = rows / maxEverySheetRow;
		} else {
			sheetNum = rows / maxEverySheetRow + 1;
		}
		try {
			// 生成一个样式
			SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
			// 创建样式对象
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

			// 计算每列的宽度
			Map<Integer, Integer> columWidthMap = autoSetColumnWidth(header,
					data);
			// 分sheet循环生成work

			for (int i = 1; i <= sheetNum + 1; i++) {// sheetNum+1为了没数据时能生成空标题
				// 避免有数据时会多生成一个sheet
				if (sheetNum > 0 && i == sheetNum + 1) {
					break;
				}
				// 循环2个sheet的值
				Sheet sheet = workbook.createSheet(sheetName + "-Page" + i);
				// 冻结第一行
				sheet.createFreezePane(0, 1, 0, 1);
				// 使用workbook对象创建sheet对象
				Row headRow = sheet.createRow((short) 0);
				// 创建行，0表示第一行（本例是excel的标题）
				headRow.setHeight((short) (400));
				// 循环excel的标题
				for (int j = 0; j < header.size(); j++) {
					// 设置列宽
					sheet.setColumnWidth(j,
							columWidthMap.get(Integer.valueOf(j)));
					// 使用行对象创建列对象，0表示第1列
					Cell cell = headRow.createCell(j);
					// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (header.get(j) != null) {
						// 将创建好的样式放置到对应的单元格中
						/** cell.setCellStyle(cellStyle); */
						cell.setCellValue((String) header.get(j));// 为标题中的单元格设置值
						// 添加样式
						cell.setCellStyle(style);
					} else {
						cell.setCellValue("-");
					}
				}

				// 遍历集合数据，产生数据行
				int count = 0;
				// 生成一行空数据
				for (int k = 0; k < (rows < maxEverySheetRow ? rows
						: maxEverySheetRow); k++) {
					if (((i - 1) * maxEverySheetRow + k) >= rows)
						// 如果数据超出总的记录数的时候，就退出循环
						break;
					// 创建1行
					++count;
					Row row = sheet.createRow(count);
					// 分页处理，获取每页的结果集，并将数据内容放入excel单元格
					List<String> rowList = (List<String>) data.get((i - 1)
							* maxEverySheetRow + k);
					// 遍历某一行的结果
					for (int n = 0; n < rowList.size(); n++) {
						// 使用行创建列对象
						Cell cell = row.createCell(n);
						if (rowList.get(n) != null) {
							cell.setCellValue((String) rowList.get(n)
									.toString());
						} else {
							cell.setCellValue("");
						}
					}
				}
			}
			long exportEndTime = System.currentTimeMillis();
			LogCvt.info("生成Excel成功，耗时 : " + (exportEndTime - exportBeginTime)
					+ "毫秒");
			return workbook;
		} catch (Exception e) {
			LogCvt.error("生成Excel异常：", e);
		}

		return null;
	}

	private String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = null;
		if (Checker.isNotEmpty(this.folderName)) {
			fileName = format.format(new Date()) + "(" + this.excelIndex + ")"
					+ FtpConstants.XLS_SUFFIX;
		} else {
			fileName = format.format(new Date())
					+ Math.round((Math.random() * 10000))
					+ FtpConstants.XLS_SUFFIX;
		}
		return fileName;
	}

	private static String getFileShortName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date()) + Math.round((Math.random() * 10));
	}

	private static String getDefaultFoot() {
		String foot = "制表人：admin";
		return foot;
	}

	/**
	 * 自动设置列宽
	 */
	private static Map<Integer, Integer> autoSetColumnWidth(
			List<String> header, List<List<String>> data) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (header != null) {
			for (int i = 0; i < header.size(); i++) {
				map.put(i, header.get(i).getBytes().length * 1 * 256);
			}
		}
		if (data != null) {
			for (List<String> results : data) {
				for (int i = 0; i < results.size(); i++) {
					if (results.get(i) != null) {
						int result = results.get(i).getBytes().length * 1 * 256;
						if (map.get(i) < result) {
							map.put(i, result);
						}
					} else {
						int result = 0;
						if (map.get(i) < result) {
							map.put(i, result);
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 将Excel写到本地目录
	 * 
	 * @param workbook
	 *            Excel工作簿
	 * @param localFilePath
	 *            本地服务器路径
	 * @throws FroadBusinessException
	 */
	private void writeFile(HSSFWorkbook workbook, String localFilePath)
			throws FroadBusinessException {
		long startTime2 = System.currentTimeMillis();
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(localFilePath));
			workbook.write(bos);
			bos.flush();
		} catch (FileNotFoundException e) {
			LogCvt.error("Excel导出异常:", e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"导出失败");
		} catch (IOException e) {
			LogCvt.error("Excel导出异常:", e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"导出失败");
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					LogCvt.error("关闭OutputStream异常:", e);
				}
			}
		}
		long endTime2 = System.currentTimeMillis();
		LogCvt.info("导出[" + this.currentExcelName + "]共" + this.sheetIndex
				+ "个sheet，耗时：" + (endTime2 - startTime2) + "ms");
	}

	/**
	 * 将Excel写到本地目录
	 * 
	 * @param workbook
	 *            Excel工作簿
	 * @param localFilePath
	 *            本地服务器路径
	 * @throws FroadBusinessException
	 */
	private void writeFile(SXSSFWorkbook workbook, String localFilePath)
			throws FroadBusinessException {
		long startTime2 = System.currentTimeMillis();
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(localFilePath));
			workbook.write(bos);
			bos.flush();
		} catch (FileNotFoundException e) {
			LogCvt.error("Excel导出异常:", e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"导出失败");
		} catch (IOException e) {
			LogCvt.error("Excel导出异常:", e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"导出失败");
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					LogCvt.error("关闭OutputStream异常:", e);
				}
			}
		}
		long endTime2 = System.currentTimeMillis();
		LogCvt.info("EXCEL写本地文件耗时：" + (endTime2 - startTime2) + "ms");
	}

	/**
	 * @param header
	 *            表头名称
	 * @param data
	 *            数据
	 * @param sheetName
	 *            sheet名称
	 * @param excelFileName
	 *            excel文件名
	 * @return url
	 * @throws FroadBusinessException
	 */
	public void writeCsv(List<String> header, List<List<String>> data,
			String sheetName, String excelFileName ,String  sameDir)
			throws FroadBusinessException {
		long startTime = System.currentTimeMillis();
		// 校验
		if (header == null || header.size() <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"Excel标题栏不能为空");
		}
		/*
		 * if (data == null || data.size() <= 0) { throw new
		 * FroadBusinessException(ResultCode.failed.getCode(),"Excel数据不能为空"); }
		 * if (header.size() != data.get(0).size()) { throw new
		 * FroadBusinessException
		 * (ResultCode.failed.getCode(),"Excel标题栏和数据栏列数不一致：标题栏为"
		 * +header.size()+"列，数据为"+data.get(0).size()+"列"); }
		 */
		if(sameDir ==null)
		{
			if (StringUtils.isEmpty(this.localFileDir)) {
				LogCvt.error("未找到配置项：（本地导出目录）" + FtpConstants.LOCAL_FILE_DIR);
				throw new FroadBusinessException(ResultCode.failed.getCode(),
						"导出失败");
			}

			// 创建文件夹
			if (this.isNeedCreateFolder) {
				this.localFileDir = this.localFileDir + FtpConstants.SLASH
						+ this.folderName;
				makeDirs(this.localFileDir);
				this.isNeedCreateFolder = false;
			}
		}
		else
		{
			this.isNeedCreateFolder = false;
		}


		// 生成新文件
/*		HSSFWorkbook workbook = null;
		if (this.sheetIndex == 0) {
			// 生成Excel
			workbook = generate2003(header, data, sheetName);
			
			if(sameDir == null)
			{
				// 获取Excel文件名
				this.currentExcelName = getFileName();
				if (StringUtils.isNotEmpty(excelFileName)) {
					this.currentExcelName = excelFileName + FtpConstants.XLS_SUFFIX;
				}
			}
			else
			{
				this.localFileDir = sameDir;
				this.currentExcelName = excelFileName + FtpConstants.XLS_SUFFIX;
			}

			
			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;
			System.out.println("localFilePath : " + localFilePath);
			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
		} else {
			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;
			workbook = generate2003(header, data, sheetName, localFilePath);

			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
		}*/

		if (sameDir != null) {
			//创建文件夹
			if (this.isNeedCreateFolder) {
				this.localFileDir = this.localFileDir + FtpConstants.SLASH + this.folderName;
				makeDirs(this.localFileDir);
				this.isNeedCreateFolder = false;
			}
			
			if(this.rowIndex == 0){
				//获取Excel文件名
				this.currentExcelName = getCsvFileName();
				if(StringUtils.isNotEmpty(currentExcelName)){
					this.currentExcelName = excelFileName + "-" + getCsvFileName();
				}
			}
			
			this.localFileDir = sameDir;
		}
		
		//获取csv文件全路径
		String csvFilePath = this.localFileDir + FtpConstants.SLASH + this.currentExcelName;
		File file = new File(csvFilePath);
		boolean alreadyExists = file.exists();
		CsvWriter csvWriter = null;
		try {
			csvWriter = new CsvWriter(new FileOutputStream(file, true), ',', Charset.forName("GBK"));
			//写入标题栏
			if(!alreadyExists){
				csvWriter.writeRecord(header.toArray(new String[header.size()]));
			}
			
			for(List<String> dateStr : data){
				//写入内容
				csvWriter.writeRecord(dateStr.toArray(new String[dateStr.size()]));
				rowIndex++;
			}
		} catch (Exception e) {
			LogCvt.error("生成csv异常：",e);
		} finally {
			if(csvWriter != null){
				csvWriter.flush();
				csvWriter.close();
			}
		}
		
		LogCvt.info("已导出条数："+this.rowIndex);
		long endTime = System.currentTimeMillis();
		totalTime += (endTime-startTime);
		LogCvt.info("生成["+this.currentExcelName+"]，已导出："+this.rowIndex+"条，共耗时："+(endTime-startTime)+"ms");
	}
	
	/**
	 * @param header
	 *            表头名称
	 * @param data
	 *            数据
	 * @param sheetName
	 *            sheet名称
	 * @param excelFileName
	 *            excel文件名
	 * @return url
	 * @throws FroadBusinessException
	 */
	public void write(List<String> header, List<List<String>> data,
			String sheetName, String excelFileName ,String  sameDir)
			throws FroadBusinessException {
		long startTime = System.currentTimeMillis();
		// 校验
		if (header == null || header.size() <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"Excel标题栏不能为空");
		}
		/*
		 * if (data == null || data.size() <= 0) { throw new
		 * FroadBusinessException(ResultCode.failed.getCode(),"Excel数据不能为空"); }
		 * if (header.size() != data.get(0).size()) { throw new
		 * FroadBusinessException
		 * (ResultCode.failed.getCode(),"Excel标题栏和数据栏列数不一致：标题栏为"
		 * +header.size()+"列，数据为"+data.get(0).size()+"列"); }
		 */
		if(sameDir ==null)
		{
			if (StringUtils.isEmpty(this.localFileDir)) {
				LogCvt.error("未找到配置项：（本地导出目录）" + FtpConstants.LOCAL_FILE_DIR);
				throw new FroadBusinessException(ResultCode.failed.getCode(),
						"导出失败");
			}

			// 创建文件夹
			if (this.isNeedCreateFolder) {
				this.localFileDir = this.localFileDir + FtpConstants.SLASH
						+ this.folderName;
				makeDirs(this.localFileDir);
				this.isNeedCreateFolder = false;
			}
		}
		else
		{
			this.isNeedCreateFolder = false;
		}


		// 生成新文件
		HSSFWorkbook workbook = null;
		if (this.sheetIndex == 0) {
			// 生成Excel
			workbook = generate2003(header, data, sheetName);
			
			if(sameDir == null)
			{
				// 获取Excel文件名
				this.currentExcelName = getFileName();
				if (StringUtils.isNotEmpty(excelFileName)) {
					this.currentExcelName = excelFileName + FtpConstants.XLS_SUFFIX;
				}
			}
			else
			{
				this.localFileDir = sameDir;
				this.currentExcelName = excelFileName + FtpConstants.XLS_SUFFIX;
			}

			
			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;
			System.out.println("localFilePath : " + localFilePath);
			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
		} else {
			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;
			workbook = generate2003(header, data, sheetName, localFilePath);

			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
		}

		LogCvt.info("已导出条数：" + this.rowIndex);
		long endTime = System.currentTimeMillis();
		totalTime += (endTime - startTime);
		LogCvt.info("生成[" + this.currentExcelName + "]，已导出：" + this.rowIndex
				+ "条，共耗时：" + (endTime - startTime) + "ms");
	}
	
    /**
     * 
     * writeCsv:(生成csv文件).
     *
     * @author huangyihao
     * 2016年1月5日 下午2:44:51
     * @param header 表头名称
     * @param data 数据
     * @param excelFileName excel文件名
     *
     */
    public void writeCsv(List<String> header, List<List<String>> data, String excelFileName) throws FroadBusinessException{
    	long startTime = System.currentTimeMillis();
		//校验
		if (header == null || header.size() <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),"Excel标题栏不能为空");
		}
		if(StringUtils.isEmpty(this.localFileDir)){
			LogCvt.error("未找到配置项：（本地导出目录）"+FtpConstants.LOCAL_FILE_DIR);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
		}
		
		//创建文件夹
		if (this.isNeedCreateFolder) {
			this.localFileDir = this.localFileDir + FtpConstants.SLASH + this.folderName;
			makeDirs(this.localFileDir);
			this.isNeedCreateFolder = false;
		}
		
		if(this.rowIndex == 0){
			//获取Excel文件名
			this.currentExcelName = getCsvFileName();
			if(StringUtils.isNotEmpty(currentExcelName)){
				this.currentExcelName = excelFileName + "-" + getCsvFileName();
			}
		}
		
		//获取csv文件全路径
		String csvFilePath = this.localFileDir + FtpConstants.SLASH + this.currentExcelName;
		
		File file = new File(csvFilePath);
		boolean alreadyExists = file.exists();
		CsvWriter csvWriter = null;
		try {
			csvWriter = new CsvWriter(new FileOutputStream(file, true), ',', Charset.forName("GBK"));
			//写入标题栏
			if(!alreadyExists){
				csvWriter.writeRecord(header.toArray(new String[header.size()]));
			}
			
			for(List<String> dateStr : data){
				//写入内容
				csvWriter.writeRecord(dateStr.toArray(new String[dateStr.size()]));
				rowIndex++;
			}
		} catch (Exception e) {
			LogCvt.error("生成csv异常：",e);
		} finally {
			if(csvWriter != null){
				csvWriter.flush();
				csvWriter.close();
			}
		}
		
		LogCvt.info("已导出条数："+this.rowIndex);
		long endTime = System.currentTimeMillis();
		totalTime += (endTime-startTime);
		LogCvt.info("生成["+this.currentExcelName+"]，已导出："+this.rowIndex+"条，共耗时："+(endTime-startTime)+"ms");
    }
    
    private String getCsvFileName() {
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = null;
		if(Checker.isNotEmpty(this.folderName)){
			fileName = format.format(new Date()) + "("+this.excelIndex+")" + FtpConstants.CSV_SUFFIX;
		}else{
			fileName = format.format(new Date()) + Math.round((Math.random() * 10000)) + FtpConstants.CSV_SUFFIX;
		}
		return fileName;
    }

	/**
	 * @param header
	 *            表头名称
	 * @param data
	 *            数据
	 * @param sheetName
	 *            sheet名称
	 * @param excelFileName
	 *            excel文件名
	 * @return url
	 * @throws FroadBusinessException
	 */
	public void write(List<String> header, List<List<String>> data,
			String sheetName, String excelFileName, boolean isAppend)
			throws FroadBusinessException {
		long startTime = System.currentTimeMillis();
		// 校验
		if (header == null || header.size() <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"Excel标题栏不能为空");
		}
		if (data == null || data.size() <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"Excel数据不能为空");
		}
		if (header.size() != data.get(0).size()) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"Excel标题栏和数据栏列数不一致：标题栏为" + header.size() + "列，数据为"
							+ data.get(0).size() + "列");
		}

		if (StringUtils.isEmpty(this.localFileDir)) {
			LogCvt.error("未找到配置项：（本地导出目录）" + FtpConstants.LOCAL_FILE_DIR);
			throw new FroadBusinessException(ResultCode.failed.getCode(),
					"导出失败");
		}

		// 创建文件夹
		if (this.isNeedCreateFolder) {
			this.localFileDir = this.localFileDir + FtpConstants.SLASH
					+ this.folderName;
			makeDirs(this.localFileDir);
			this.isNeedCreateFolder = false;
		}

		if (isAppend) {
			// 是否追加当前workbook
			boolean isAppendCurrentWorkbook = false;
			int workbookCount = 0;
			// 每个workbook已写多余数
			long exist = this.rowIndex
					% (this.maxEverySheetRow * this.maxSheetCount);
			// 每个workbook总数
			long workbookMaxCount = this.maxEverySheetRow * this.maxSheetCount;
			if (exist > 0) {
				isAppendCurrentWorkbook = true;
				// 当前workbook剩余数
				long remain = workbookMaxCount - exist;
				if (data.size() > remain) {
					long count1 = (data.size() - remain) / workbookMaxCount;
					if (count1 > 0) {
						workbookCount += count1;
					}
					long count2 = (data.size() - remain) % workbookMaxCount;
					if (count2 > 0) {
						++workbookCount;
					}
				} else {
					workbookCount = 1;
				}
			} else {
				// 刚好创建新workbook
				long count1 = data.size() / workbookMaxCount;
				if (count1 > 0) {
					workbookCount += count1;
				}
				long count2 = data.size() % workbookMaxCount;
				if (count2 > 0) {
					++workbookCount;
				}
			}
			if (isAppendCurrentWorkbook) {
				// 获取Excel文件全路径
				String localFilePath = this.localFileDir + FtpConstants.SLASH
						+ this.currentExcelName;
				HSSFWorkbook workbook;
				try {
					workbook = getWorkBook(localFilePath);
				} catch (IOException e) {
					throw new FroadBusinessException(
							ResultCode.failed.getCode(), "导出失败");
				}
				workbook = generate2003(header, data, sheetName, workbook,
						isAppend);
				// 本地服务器写入Excel文件
				writeFile(workbook, localFilePath);
			}
			for (int i = 0; i < workbookCount; i++) {
				// 生成Excel
				HSSFWorkbook workbook = new HSSFWorkbook();
				workbook = generate2003(header, data, sheetName, workbook,
						isAppend);

				// 获取Excel文件名
				this.currentExcelName = getFileName();
				if (StringUtils.isNotEmpty(excelFileName)) {
					this.currentExcelName = excelFileName + "-" + getFileName();
				}

				// 获取Excel文件全路径
				String localFilePath = this.localFileDir + FtpConstants.SLASH
						+ this.currentExcelName;

				// 本地服务器写入Excel文件
				writeFile(workbook, localFilePath);
			}

		}

		// 生成新文件
		HSSFWorkbook workbook = null;
		if (this.sheetIndex == 0) {
			// 生成Excel
			workbook = generate2003(header, data, sheetName);

			// 获取Excel文件名
			this.currentExcelName = getFileName();
			if (StringUtils.isNotEmpty(excelFileName)) {
				this.currentExcelName = excelFileName + "-" + getFileName();
			}

			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;

			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
			
		} else {
			// 获取Excel文件全路径
			String localFilePath = this.localFileDir + FtpConstants.SLASH
					+ this.currentExcelName;
			System.out.println("========================" + localFilePath);
			workbook = generate2003(header, data, sheetName, localFilePath);

			// 本地服务器写入Excel文件
			writeFile(workbook, localFilePath);
		}

		LogCvt.info("已导出条数：" + this.rowIndex);
		long endTime = System.currentTimeMillis();
		totalTime += (endTime - startTime);
		LogCvt.info("生成[" + this.currentExcelName + "]，已导出：" + this.rowIndex
				+ "条，共耗时：" + (endTime - startTime) + "ms");
	}

	/**
	 * 上传文件
	 */
	private void upload(String zipPath) {
//		if (this.excelIndex > 1) {
//			String zipFileName = this.localFileDir + FtpConstants.SLASH
//					+ this.folderName + FtpConstants.ZIP_SUFFIX;
//			ZipCompressor zc = new ZipCompressor(zipFileName);
//			zc.setExcludes(".zip");
//			zc.compress(this.localFileDir);
			//fileName = this.folderName + FtpConstants.ZIP_SUFFIX;
		String	fileName = zipPath.substring(zipPath.lastIndexOf("/")+1,zipPath.length());
		String 	deleteDir = this.localFileDir;
//		} else {
//			fileName = this.currentExcelName;
//			deleteDir = this.localFileDir + FtpConstants.SLASH
//					+ this.folderName;
//		}

		try {
			// this.url = FtpUtil.upload(fileName,this.folderName);
			// this.url = FtpHelper.upload(fileName,this.folderName);
			this.url = ScpUtil.uploadFile(zipPath, fileName);
		} catch (FroadBusinessException e) {
			LogCvt.error("导出失败：", e);
		} finally {
			System.gc();
			File file = new File(deleteDir);
			deleteDir(file);
		}

	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 * @throws FroadBusinessException
	 */
	public void makeDirs(String dir) throws FroadBusinessException {
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			if (!fileDir.mkdirs()) {
				LogCvt.error("创建本地目录失败！");
				throw new FroadBusinessException(ResultCode.failed.getCode(),
						"导出失败");
			}
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return 删除成功返回true，否则返回false
	 */
	private boolean deleteDir(File dir) {
		 if (dir.isDirectory()) {
	            String[] children = dir.list();
	            //递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	}
	
	
	/**
	 * 通过递归调用删除一个文件夹及下面的所有文件
	 * @param file
	 */
	public static void deleteFile(File file){
		if(file.isFile()){//表示该文件不是文件夹
			file.delete();
		}else{
			//首先得到当前的路径
			String[] childFilePaths = file.list();
			for(String childFilePath : childFilePaths){
				File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);
				deleteFile(childFile);
			}
			file.delete();
		}
	}
	

	public String getLocalFileDir() {
		String path = this.localFileDir;// + FtpConstants.SLASH;
		System.out.println("----->" + path);
		return path;
	}

	public long getWriterID() {
		return writerID;
	}

	public void setWriterID(long writerID) {
		this.writerID = writerID;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getCurrentExcelName() {
		return currentExcelName;
	}

	public void setCurrentExcelName(String currentExcelName) {
		this.currentExcelName = currentExcelName;
	}
	
	

}
