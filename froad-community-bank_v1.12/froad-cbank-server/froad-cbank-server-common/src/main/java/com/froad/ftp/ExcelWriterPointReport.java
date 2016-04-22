package com.froad.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import com.csvreader.CsvWriter;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.scp.ScpUtil;

/**
 * 积分报表excel导出工具类
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-common<br/>
 * File Name : ExcelWriterPointReport.java<br/>
 * 
 * Date : 2015年12月30日 上午11:38:04 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class ExcelWriterPointReport {
	
	/**
	 * 当前操作唯一ID
	 */
	private long writerID;
	
	/**
	 * 本地服务器目录（ftp.properties配置项）
	 */
	private String localFileDir;
	
	/**
	 * 多个excel时，文件夹名
	 */
	private String folderName;
	
	/**
	 * 当前操作的excel文件名
	 */
	private String currentExcelName;
	
	/**
	 * 当前excel写入序号
	 */
	private int excelIndex = 0;
	
	/**
	 * 当前row写入序号
	 */
	private int rowIndex = 0;
	
	/**
	 * 共写入多少条数据，不包括标题行
	 */
	private int rowCount = 0;
	
	/**
	 * 导出累计总耗时
	 */
	private long totalTime = 0;
	
	/**
	 * csv文件分割符
	 */
	private char separator = ',';
	
	/**
	 * 字符集格式
	 */
	private Charset charset = Charset.forName("GBK");
	
	/**
	 * 返回下载链接URL
	 */
	private String url;
	
	public String getUrl(String zipName) {
		upload(zipName);
		LogCvt.info("导出："+excelIndex+"个Excel，共"+rowCount+"条数据，总耗时："+this.totalTime+"ms，下载链接："+url);
		return url;
	}
	
	/**
	 * 导出类初始化
	 */
	public ExcelWriterPointReport(){
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		this.writerID = ExcelConstants.getUniqueId();
		this.localFileDir = FtpConstants.SFTP_PROPERTIES.get(FtpConstants.LOCAL_FILE_DIR);
		
		this.folderName = String.valueOf(this.writerID);
		this.currentExcelName = String.valueOf(this.writerID);
	}
	

	/**
	 * 自动设置列宽
	 */
	private static Map<Integer, Integer> autoSetColumnWidth(List<String> header, List<List<String>> data) {
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
	 * @param workbook Excel工作簿
	 * @param localFilePath 本地服务器路径
	 * @throws FroadBusinessException
	 */
	private void writeFile(HSSFWorkbook workbook,String localFilePath) throws FroadBusinessException{
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(localFilePath));
			workbook.write(bos);
			bos.flush();
		} catch (FileNotFoundException e) {
			LogCvt.error("Excel导出异常:",e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
		} catch (IOException e) {
			LogCvt.error("Excel导出异常:",e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
		} finally {
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					LogCvt.error("关闭OutputStream异常:",e);
				}
			}
		}
	}
	
	/**
	 * 上传文件
	 */
	private void upload(String zipName){
		String fileName = null;
		String deleteDir = null;
		if (this.excelIndex >1) {
			if(StringUtils.isEmpty(zipName)){
				zipName = this.folderName;
			}
			String zipFileName = this.localFileDir + FtpConstants.SLASH + zipName + FtpConstants.ZIP_SUFFIX;
			ZipCompressor zc = new  ZipCompressor(zipFileName);  
			zc.setExcludes(".zip");
//			zc.compress(this.localFileDir); 
			zc.compressNoFolder(this.localFileDir); 
//	        fileName = this.folderName + FtpConstants.ZIP_SUFFIX;
			fileName = zipName + FtpConstants.ZIP_SUFFIX;
	        deleteDir = this.localFileDir ;// FtpConstants.SLASH +this.folderName;
		}else{
			fileName = this.currentExcelName;
			deleteDir = this.localFileDir ;//+ FtpConstants.SLASH + this.currentExcelName;
		}
		
		try {
			
			//将本地生成的文件上传到文件服务器
			String localPath  = this.localFileDir + FtpConstants.SLASH + fileName;
			this.url = ScpUtil.uploadFile(localPath, fileName);
		} catch (FroadBusinessException e) {
			LogCvt.error("导出失败：",e);
		} finally {
			//上传完成之后删除本地生成的文件
			File file = new File(deleteDir);
			deleteDir(file);
		}
		
	}
	
	/**
	 * 创建目录
	 * @param dir
	 * @throws FroadBusinessException
	 */
	public void makeDirs(String dir) throws FroadBusinessException{
		File fileDir = new File(dir);
		if(!fileDir.exists()){
			if(!fileDir.mkdirs()){
				LogCvt.error("创建本地目录失败！");
				throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
			}
		}
	}
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
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
	
    
    /////////////////////////////////
    
    public void createFolder() throws FroadBusinessException{
		//创建文件夹
		this.localFileDir = this.localFileDir + FtpConstants.SLASH + this.folderName;
		makeDirs(this.localFileDir);
    }
    /**
	 * @param header 表头名称
	 * @param data 数据
	 * @param sheetName sheet名称
	 * @param excelFileName excel文件名
	 * @return url
	 * @throws FroadBusinessException
	 * @throws IOException 
	 */
	public void writeDetialHead(String titile,String[] msg,String[] headerStr, String sheetName,int sheetIndex,String excelFileName) throws FroadBusinessException, IOException{
		
		this.currentExcelName = excelFileName;
		
		//校验
		if (headerStr == null || headerStr.length <= 0) {
			throw new FroadBusinessException(ResultCode.failed.getCode(),"Excel标题栏不能为空");
		}
		List<String> header =  Arrays.asList(headerStr);
		//交易目录
		if(StringUtils.isEmpty(this.localFileDir)){
			LogCvt.error("未找到配置项：（本地导出目录）"+FtpConstants.LOCAL_FILE_DIR);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
		}
		
		this.rowIndex = 0;
		
		//获取Excel文件全路径
		String localFilePath = this.localFileDir + FtpConstants.SLASH + this.currentExcelName;
		
		//生成新文件
		HSSFWorkbook workbook = null;
		if(sheetIndex == 0){
			workbook = new HSSFWorkbook();
			this.excelIndex++;
		}else{
			workbook = getWorkBook(localFilePath);
		}
		String realSheetName = sheetName + "-Page" + sheetIndex;
		//生产sheet
		HSSFSheet sheet = workbook.createSheet(sheetName + "-Page" + sheetIndex);
		
		CellStyle style1 = createStyle1(workbook);
		
		this.createOneTitle(sheet, titile, style1, header.size());
		this.createTowTitle(sheet, msg, style1);
		
		// 计算每列的宽度
		Map<Integer, Integer> columWidthMap = autoSetColumnWidth(header, null);
		
		this.createTitle(sheet, header, columWidthMap, style1);
		
		//本地服务器写入Excel文件
		writeFile(workbook,localFilePath);
		
		LogCvt.info("生成["+this.currentExcelName+"]中["+realSheetName+"]的标头");
	}
	
	/**
	 * 追加数据
	 * Function : writeDetialData<br/>
	 * 2015年12月30日 下午2:29:55 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param datas
	 * @param sheetName
	 * @param sheetIndex
	 * @param excelFileName
	 * @throws FroadBusinessException
	 * @throws IOException
	 */
	public void writeDetialData(List<List<String>> datas, String sheetName,int sheetIndex,String excelFileName) throws FroadBusinessException, IOException{
		long startTime = System.currentTimeMillis();
		
		//获取Excel文件全路径
		String localFilePath = this.localFileDir + FtpConstants.SLASH + this.currentExcelName;
		
		//生成新文件
		HSSFWorkbook workbook = getWorkBook(localFilePath);
		
		//生产sheet
		String realSheetName = sheetName + "-Page" + sheetIndex;
		HSSFSheet sheet = workbook.getSheet(realSheetName);
		
		//将数据写入
		appendGenerate2003(workbook, sheet, datas);
		
		//本地服务器写入Excel文件
		writeFile(workbook,localFilePath);
		
		LogCvt.info("已导出条数："+this.rowIndex);
		long endTime = System.currentTimeMillis();
		totalTime += (endTime-startTime);
		LogCvt.info("生成["+this.currentExcelName+"]，已导出："+this.rowIndex+"条，共耗时："+(endTime-startTime)+"ms");
	}
    
    //从已存在的文件中中获取HSSFWorkbook对象
	private HSSFWorkbook getWorkBook(String excelFileName) throws IOException{
		 InputStream input = new FileInputStream(excelFileName);
        POIFSFileSystem fs = new POIFSFileSystem(input);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        return wb;
	}
    
	/**
	 * 创建标题栏(首行)
	 * @param sheet 
	 * @param header 标题
	 * @param columWidthMap 每格宽度
	 */
	private void createOneTitle(HSSFSheet sheet,String titile,CellStyle style,int rowSize){
		
		// 创建行，0表示第一行（本例是excel的标题）
		HSSFRow headRow = sheet.createRow((short) this.rowIndex++);
		headRow.setHeight((short) (400));
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowSize-1));
		
		HSSFCell cell = headRow.createCell(0);
		
		cell.setCellValue(titile);// 为标题中的单元格设置值
		// 添加样式
		cell.setCellStyle(style);
	}
	
	/**
	 * 创建标题栏(首行)
	 * @param sheet 
	 * @param header 标题
	 * @param columWidthMap 每格宽度
	 */
	private void createTowTitle(HSSFSheet sheet,String[] msgs,CellStyle style){
		
		// 创建行，0表示第一行（本例是excel的标题）
		HSSFRow headRow = sheet.createRow((short) this.rowIndex++);
		headRow.setHeight((short) (400));
		
		HSSFCell cell = headRow.createCell(0);
		cell.setCellValue("结算时间");
		cell.setCellStyle(style);
		
		cell = headRow.createCell(1);
		cell.setCellValue("开始时间");
		
		cell = headRow.createCell(2);
		cell.setCellValue(msgs[0]);
		
		cell = headRow.createCell(3);
		cell.setCellValue("结束时间");
		
		cell = headRow.createCell(4);
		cell.setCellValue(msgs[1]);
		
		
		cell = headRow.createCell(5);
		cell.setCellValue("银行积分合计");
		cell.setCellStyle(style);
		
		cell = headRow.createCell(6);
		cell.setCellValue(msgs[2]);
		
		cell = headRow.createCell(7);
		cell.setCellValue("联盟积分合计");
		cell.setCellStyle(style);
		
		cell = headRow.createCell(8);
		cell.setCellValue(msgs[3]);
	}
	
	/**
	 * 创建标题栏
	 * @param sheet 
	 * @param header 标题
	 * @param columWidthMap 每格宽度
	 */
	private void createTitle(HSSFSheet sheet,List<String> header,Map<Integer, Integer> columWidthMap,CellStyle style){
		// 冻结第一行
//		sheet.createFreezePane(0, 1, 0, 1);

		HSSFRow headRow = sheet.createRow((short) this.rowIndex++);
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
	 * 往sheet中追加数据Excel-2003   .xls
	 * @param header
	 * @param data
	 * @param sheetName
	 * @param sheetIndex sheet索引
	 * @param excelFileName 本地excel文件
	 * @return
	 */
	private void appendGenerate2003(HSSFWorkbook workbook,HSSFSheet sheet,List<List<String>> datas) {
		if(datas == null || datas.size() == 0){
			return;
		}
		
		// 生成并设置另一个样式
		CellStyle style2 = this.createStyle2(workbook);
			
		HSSFRow row = null;
		List<String> rowList = null;
		for(int i=0;i<datas.size();i++){
			rowList = datas.get(i);
			row = sheet.createRow(this.rowIndex++);
			
			// 遍历某一行的结果
			for (int n = 0; n < rowList.size(); n++) {
				// 使用行创建列对象
				HSSFCell cell = row.createCell(n);
				cell.setCellStyle(style2);
				if (rowList.get(n) != null) {
					cell.setCellValue((String) rowList.get(n).toString());
				} else {
					cell.setCellValue("");
				}
			}
		}
		this.rowCount += datas.size();
	}
	
	
	private CellStyle createStyle1(HSSFWorkbook workbook){
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
		
		return style;
	}
	
	private CellStyle createStyle2(HSSFWorkbook workbook){
		// 创建样式对象
		CellStyle style2 = workbook.createCellStyle();
		// 设置这些样式
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
		
		return style2;
	}
	
	
	/**
	 * 生产csv文件
	 * Function : writeCsv<br/>
	 * 2016年1月26日 下午6:02:54 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param titile 第一行的总标题
	 * @param msg 第二行汇总消息
	 * @param headerStr 第三行数据表头
	 * @param excelFileName 文件名
	 * @param datas 待导出的数据集合
	 * @throws FroadBusinessException
	 */
	 public void writeCsv(String titile,String[] msg,String[] headerStr,String excelFileName,List<List<String>> datas) throws FroadBusinessException{
	    	long startTime = System.currentTimeMillis();
	    	
			if(StringUtils.isEmpty(this.localFileDir)){
				LogCvt.error("未找到配置项：（本地导出目录）"+FtpConstants.LOCAL_FILE_DIR);
				throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
			}
			
			//获取csv文件全路径
			String csvFilePath = this.localFileDir + FtpConstants.SLASH + excelFileName;
			
			File file = new File(csvFilePath);
			boolean alreadyExists = file.exists();
			CsvWriter csvWriter = null;
			try {
				csvWriter = new CsvWriter(new FileOutputStream(file, true), separator, charset);
				//写入标题栏
				if(!alreadyExists){
					this.excelIndex++;
					
					String[] titiles = new String[1];
					titiles[0] = titile;
					
					csvWriter.writeRecord(titiles);
					
					csvWriter.writeRecord(msg);
					
					csvWriter.writeRecord(headerStr);
				}
				
				if(datas != null && datas.size() > 0){
					for(List<String> dateStr : datas){
						//写入内容
						csvWriter.writeRecord(dateStr.toArray(new String[dateStr.size()]));
						rowIndex++;
					}
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
}
