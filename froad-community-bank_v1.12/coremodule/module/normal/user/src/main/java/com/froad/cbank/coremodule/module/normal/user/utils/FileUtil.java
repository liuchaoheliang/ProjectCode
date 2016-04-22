package com.froad.cbank.coremodule.module.normal.user.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 
 */
public class FileUtil {

	
	/**
	 * 保存文件到临时目录
	 * @param newFileName
	 * @param filedata
	 * @return 文件保存路径
	 * @throws Exception
	 */
	public static String saveLocalTemp(String newFileName, byte[] filedata) throws Exception{
		
		String saveDir = Constants.getFileTempDir();
		
		File dirFile = new File(saveDir);
		
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		
		String fileUrl = saveDir + File.separator + newFileName;
		
		OutputStream os = new FileOutputStream(new File(fileUrl));
		
		os.write(filedata);
		os.flush();
		os.close();
		
		return fileUrl;
	}
	
	
	
	
	
	
}
