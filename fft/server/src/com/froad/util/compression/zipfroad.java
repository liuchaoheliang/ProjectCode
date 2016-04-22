package com.froad.util.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


public class zipfroad {
	private static  Logger logger = Logger.getLogger(zipfroad.class);
	
	/**
	  * 方法描述：取得压缩包中的 文件列表(文件夹,文件自选)
	  * @param: zipFileString		压缩包名字
 				bContainFolder	是否包括 文件夹
				bContainFile		是否包括 文件
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Feb 3, 2012 2:01:30 PM
	  */
	public static List<File> GetFileList(String zipFileString, boolean bContainFolder, boolean bContainFile)throws Exception {
		
		//android.util.Log.v("XZip", "GetFileList(String)");
		
		List<File> fileList = new ArrayList<File>();
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
		
			if (zipEntry.isDirectory()) {
		
				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(szName);
				if (bContainFolder) {
					fileList.add(folder);
				}
		
			} else {
				File file = new File(szName);
				if (bContainFile) {
					fileList.add(file);
				}
			}
		}//end of while
		
		inZip.close();
		
		return fileList;
	}
	
	
	/**
	  * 方法描述：返回压缩包中的文件InputStream
	  * @param:  zipFileString		压缩文件的名字
 				 fileString	解压文件的名字
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Feb 3, 2012 2:03:16 PM
	  */
	public static InputStream UpZip(String zipFileString, String fileString)throws Exception {
		//android.util.Log.v("XZip", "UpZip(String, String)");
		ZipFile zipFile = new ZipFile(zipFileString);
		ZipEntry zipEntry = zipFile.getEntry(fileString);
		
		return zipFile.getInputStream(zipEntry);

	}
	
	
	/**
	  * 方法描述： 解压一个压缩文档 到指定位置
	  * @param:  zipFileString	压缩包的名字
	 				outPathString	指定的路径
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Feb 3, 2012 2:05:09 PM
	  */
	public static void UnZipFolder(File zipFileString, String outPathString)throws Exception {
		//android.util.Log.v("XZip", "UnZipFolder(String, String)");
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
		
			if (zipEntry.isDirectory()) {
		
				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(outPathString + File.separator + szName);
				folder.mkdirs();
		
			} else {
		
				File file = new File(outPathString + File.separator + szName);
				file.createNewFile();
				// get the output stream of the file
				FileOutputStream out = new FileOutputStream(file);
				int len;
				byte[] buffer = new byte[1024];
				// read (len) bytes into buffer
				while ((len = inZip.read(buffer)) != -1) {
					// write (len) byte from buffer at the position 0
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}//end of while
		
		inZip.close();
	
	}//end of func
	
	
	/**
	  * 方法描述：压缩文件,文件夹
	  * @param: srcFileString	要压缩的文件/文件夹名字  
	  * 		zipFileString	指定压缩的目的和名字
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Feb 3, 2012 2:05:44 PM
	  */
	public static void ZipFolder(String srcFileString, String zipFileString)throws Exception {		
		//创建Zip包
		ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
		
		//打开要输出的文件
		File file = new File(srcFileString);

		//压缩
		ZipFiles(file.getParent()+File.separator, file.getName(), outZip);
		
		//完成,关闭
		outZip.flush();
		//outZip.finish();
		outZip.close();
		//file=null;
	
	}//end of func
	
	
	/**
	  * 方法描述：压缩文件
	  * @param: folderString  zipOutputSteam zipOutputSteam
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Feb 3, 2012 2:06:57 PM
	  */
	private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam)throws Exception{
		//android.util.Log.v("XZip", "ZipFiles(String, String, ZipOutputStream)");
		
		if(zipOutputSteam == null)
			return;
		
		File file = new File(folderString+fileString);
		
		//判断是不是文件
		if (file.isFile()) {

			ZipEntry zipEntry =  new ZipEntry(fileString);
			FileInputStream inputStream = new FileInputStream(file);
			zipOutputSteam.putNextEntry(zipEntry);
			
			int len;
			byte[] buffer = new byte[4096];
			
			while((len=inputStream.read(buffer)) != -1)
			{
				zipOutputSteam.write(buffer, 0, len);
			}
			
			zipOutputSteam.closeEntry();
			
		//	inputStream.close();
			
		}else {
			
			//文件夹的方式,获取文件夹下的子文件
			String fileList[] = file.list();
			
			//如果没有子文件, 则添加进去即可
			if (fileList.length <= 0) {
				ZipEntry zipEntry =  new ZipEntry(fileString+File.separator);
				zipOutputSteam.putNextEntry(zipEntry);
				zipOutputSteam.closeEntry();
			}else{
				//如果有子文件, 遍历子文件
				for (int i = 0; i < fileList.length; i++) {
					ZipFiles(folderString, fileString+File.separator+fileList[i], zipOutputSteam);
				}//end of for
			}	
		}//end of if
		
	}//end of func
	
	public static void main(String args[]) throws Exception{
		long startTime = System.currentTimeMillis();
		
		String zipPath="f:\\test\\cc.zip";
		String folder="f:\\test\\clientconfigure";
		ZipFolder(folder,zipPath);

		long endTime = System.currentTimeMillis();
		logger.info("zip：用时" + (endTime - startTime) + "ms");
		/*delete file*/
		File file=new File("f:\\test\\cc.zip");
		System.out.println(file.delete());

	}
}
