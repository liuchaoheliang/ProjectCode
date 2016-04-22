package com.froad.cbank.coremodule.framework.common.util.excel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * excel导出工具类
 * @ClassName JxlsExcelUtil
 * @author zxl
 * @date 2015年5月22日 上午11:49:02
 */
public class JxlsExcelUtil {
	
	/**
	 * excel导出
	 * @tilte export
	 * @author zxl
	 * @date 2015年5月22日 上午11:51:41
	 * @param templatePath 模版路径
	 * @param beans Bean数据
	 * @return
	 * @throws Exception 
	 */
	public static ByteArrayOutputStream export(String templatePath,HashMap<String,Object> beans) throws Exception {
		InputStream is = null; 
		try {
			is = new BufferedInputStream(new FileInputStream(templatePath));
			//关联模板
			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, beans);
			ByteArrayOutputStream outs = new ByteArrayOutputStream();
			workbook.write(outs);
			return outs;
		} catch (Exception e) {
			throw e;
		}finally{
			if(is != null){
				is.close();
			}
		}
	}
	
	/**
	 * excel导出
	 * @tilte export
	 * @author zxl
	 * @date 2015年5月22日 下午1:46:09
	 * @param templateFile 模版文件
	 * @param beans Bean数据
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream export(File templateFile,HashMap<String,Object> beans) throws Exception {
		InputStream is = null; 
		try {
			is = new BufferedInputStream(new FileInputStream(templateFile));
			//关联模板
			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, beans);
			ByteArrayOutputStream outs = new ByteArrayOutputStream();
			workbook.write(outs);
			return outs;
		} catch (Exception e) {
			throw e;
		}finally{
			if(is != null){
				is.close();
			}
		}
	}
}
