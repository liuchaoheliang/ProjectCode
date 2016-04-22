package com.froad.cbank.coremodule.normal.boss.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;

/**
 * 文件工具类
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年7月1日 下午1:18:00
 */
public class FileUtil {

	/**
	 * 本地临时保存文件
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月1日 下午1:18:26
	 */
	public static void saveFile(String newFileName, MultipartFile file) throws BossException {
		try {
			//获取保存路径
			String saveFilePath = Constants.getImgLocalUrl();
			File fileDir = new File(saveFilePath);
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			//输出文件
			FileOutputStream out = new FileOutputStream(saveFilePath + File.separator + newFileName);
			out.write(file.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new BossException("99992", "文件保存失败");
		}
	}
	
	/**
	 * 下载文件
	 * @tilte downloadFile
	 * @author zxl
	 * @date 2015年8月17日 下午8:02:15
	 * @throws BossException
	 */
	public static void downloadFile(String srcDir,String fileName,String localDir,ScpConfig scp) throws BossException{
		Connection scpConnection = null;
		SCPClient scpClient = null;
		try{
			boolean isAuthed = false;
			if(scp.getPort()==0){
				scpConnection = new Connection(scp.getIp());
			}else{
				scpConnection = new Connection(scp.getIp(), scp.getPort());
			}
			
			scpConnection.connect();
			
			isAuthed = scpConnection.authenticateWithPassword(scp.getUserName(), scp.getPassWord());
			
			if(!isAuthed){
				throw new Exception("connect auth failed!");
			}
			
			scpClient = scpConnection.createSCPClient();
			
			scpClient.get(srcDir+fileName, localDir);
			
		}catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			throw new BossException("文件下载失败");
		}finally{
			if(scpConnection!=null){
				scpConnection.close();
			}
		}
	}
}
