package com.froad.cbank.coremodule.module.normal.bank.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.thrift.TException;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.vo.ExcelReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrderReqOfOptimize;
import com.froad.cbank.coremodule.module.normal.bank.vo.SettlementReqVo;
import com.froad.thrift.vo.BankOperatorCheckVoRes;

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
	private static final int SPLIT_COUNT = 50000; // Excel每个工作簿的行数
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 
	 * @param title
	 * @param subTitle
	 * @param header
	 * @param data
	 * @param foot
	 * @return
	 */
	public static HSSFWorkbook generate(List<String> header, List<List<String>> data, String sheetName) {
		long exportBeginTime = System.currentTimeMillis();
		int rows=0 ;// 总的记录数
		if(data!=null&&data.size()>0){
			rows=data.size();
		}
		int sheetNum = 0; // 指定sheet的页数
		if (rows % SPLIT_COUNT == 0) {
			sheetNum = rows / SPLIT_COUNT;
		} else {
			sheetNum = rows / SPLIT_COUNT + 1;
		}
		try {
			// 生成一个样式
			HSSFWorkbook workbook = new HSSFWorkbook();
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
			Map<Integer, Integer> columWidthMap = autoSetColumnWidth(header, data);
			// 分sheet循环生成work
			
			for (int i = 1; i <= sheetNum+1; i++) {//sheetNum+1为了没数据时能生成空标题
				//避免有数据时会多生成一个sheet
				if(sheetNum>0&&i==sheetNum+1){
					break;
				}
				// 循环2个sheet的值
				HSSFSheet sheet = workbook.createSheet(sheetName + "-Page" + i);
				// 使用workbook对象创建sheet对象
				HSSFRow headRow = sheet.createRow((short) 0);
				// 创建行，0表示第一行（本例是excel的标题）
				headRow.setHeight((short) (400));
				// 循环excel的标题
				for (int j = 0; j < header.size(); j++) {
					// 设置列宽
					sheet.setColumnWidth(j, columWidthMap.get(Integer.valueOf(j)));
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
				//生成一行空数据
				for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
					if (((i - 1) * SPLIT_COUNT + k) >= rows)
						// 如果数据超出总的记录数的时候，就退出循环
						break;
					// 创建1行
					++count;
					HSSFRow row = sheet.createRow(count);
					// 分页处理，获取每页的结果集，并将数据内容放入excel单元格
					ArrayList<String> rowList = (ArrayList<String>) data.get((i - 1) * SPLIT_COUNT + k);
					// 遍历某一行的结果
					for (int n = 0; n < rowList.size(); n++) {
						// 使用行创建列对象
						HSSFCell cell = row.createCell(n);
						if (rowList.get(n) != null) {
							cell.setCellValue((String) rowList.get(n).toString());
						} else {
							cell.setCellValue("");
						}
					}
				}
			}
			long exportEndTime = System.currentTimeMillis();
			LogCvt.info("生成workbook耗时 : " + (exportEndTime - exportBeginTime) + "毫秒");
			return workbook;
		} catch (Exception e) {
			LogCvt.info("Excel导出" + e.getMessage(), e);
		} finally {
		}

		return null;
	}

	public static String getFileName() {
		return ExcelUtil.format.format(new Date()) + Math.round((Math.random() * 10)) + ".xls";
	}

	public static String getFileShortName() {
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
	public static Map<Integer, Integer> autoSetColumnWidth(List<String> header, List<List<String>> data) {
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
	 * 
	 * doExport:执行导出操作
	 *
	 * @author 明灿 2015年8月10日 下午5:32:46
	 * @param response
	 * @param map
	 * @param str
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 *
	 */
	public static void doExport(HttpServletResponse response, Map<String, Object> map, String str)
			throws UnsupportedEncodingException, IOException {
		Workbook workBook = (Workbook) map.get("workBook");
		// 下载excel
		response.setContentType("application/ms-excel;charset=UTF-8");
		String headerStr = str + ExcelUtil.getFileShortName();
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(headerStr.getBytes("GBK"), "ISO-8859-1") + ".xls");
		OutputStream os = response.getOutputStream();
		workBook.write(os);
		os.flush();
		os.close();
	}

	/**
	 * 
	 * checkToken:导出操作的登录校验
	 *
	 * @author 明灿 2015年8月10日 下午5:33:23
	 * @param reqVo
	 * @param clientId
	 * @param resVo
	 * @throws BankException
	 * @throws TException
	 *
	 */
	public static void checkToken(ExcelReqVo reqVo, String clientId, BankOperatorCheckVoRes resVo)
			throws BankException, TException {
		/*************** 校验登入情况begin ***************/
		if (reqVo.getUserId() == null || reqVo.getToken() == null) {
			throw new BankException(EnumTypes.timeout.getCode(), EnumTypes.timeout.getMessage());
		}
		if (!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			throw new BankException(EnumTypes.timeout.getCode(), resVo.getResult().getResultDesc());
		}
		/*************** 校验登入情况end ***************/
	}
	
	/**
	 * 
	 * checkToken:结算查询校验
	 *
	 * @author 明灿
	 * 2015年8月18日 下午2:40:30
	 * @param reqVo
	 * @param resVo
	 * @throws BankException
	 * @throws TException
	 *
	 */
	public static void checkToken(SettlementReqVo reqVo, BankOperatorCheckVoRes resVo)
			throws BankException, TException {
		/*************** 校验登入情况begin ***************/
		if (reqVo.getUserId() == null || reqVo.getToken() == null) {
			throw new BankException(EnumTypes.timeout.getCode(), EnumTypes.timeout.getMessage());
		}
		if (!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			throw new BankException(EnumTypes.timeout.getCode(), resVo.getResult().getResultDesc());
		}
		/*************** 校验登入情况end ***************/
	}
	
	/**
	 * 
	 * checkToken:订单优化
	 *
	 * @author 明灿
	 * 2015年8月26日 下午6:47:24
	 * @param reqVo
	 * @param resVo
	 * @throws BankException
	 * @throws TException
	 *
	 */
	public static void checkToken(OrderReqOfOptimize reqVo, BankOperatorCheckVoRes resVo) throws BankException,
			TException {
		/*************** 校验登入情况begin ***************/
		if (reqVo.getUserId() == null || reqVo.getToken() == null) {
			throw new BankException(EnumTypes.timeout.getCode(), EnumTypes.timeout.getMessage());
		}
		if (!resVo.getResult().getResultCode().equals(EnumTypes.success.getCode())) {
			throw new BankException(EnumTypes.timeout.getCode(), resVo.getResult().getResultDesc());
		}
		/*************** 校验登入情况end ***************/
	}

	public static void main(String[] args) {
		List<String> title = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			title.add(i+"");
		}
		HSSFWorkbook workbook = generate(title, null, "00000");
		System.out.println(workbook);
	}
}
