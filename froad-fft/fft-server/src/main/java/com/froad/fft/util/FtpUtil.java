package com.froad.fft.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.froad.fft.bean.SystemConfig;

/**
 * ftp工具
 * 
 * @author FQ
 * 
 */
public class FtpUtil {

	final static Logger logger = Logger.getLogger(FtpUtil.class);
	
	
	/**
	 * 连接到服务器
	 * @throws IOException 
	 */
	public static FTPClient connectServer() throws IOException {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		FTPClient ftpClient = new FTPClient();
//		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		ftpClient.setDefaultPort(systemConfig.getFtpPort());
		ftpClient.connect(systemConfig.getFtpHost());
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			logger.warn("FTP 拒绝连接:" +systemConfig.getFtpPort());
			ftpClient.disconnect();
			return null;
		}
		if (!ftpClient.login(systemConfig.getFtpUsername(), systemConfig.getFtpPassword())) {
			logger.warn("FTP 拒绝登录：" + systemConfig.getFtpPort()+"|"+systemConfig.getFtpUsername());
			ftpClient.logout();
			ftpClient.disconnect();
			return null;
		}
		// 设置缓冲区改为5M
		ftpClient.setBufferSize(1024 * 1024 * 5);
		ftpClient.setControlEncoding(systemConfig.getFtpEncoding());
		ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();
		return ftpClient;
	}
	

	/**
	 * 上传文件
	 * 
	 * @param path
	 * @param inputStream
	 * @return
	 */
	public static boolean uploadFile(String path, InputStream inputStream) {
		
		FTPClient ftpClient = null;
		try {
			logger.info("开始连接FTP服务器");
			ftpClient=connectServer();
			
			if(ftpClient != null){
				logger.info("连接FTP服务器成功");
				SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
				String directory = StringUtils.substringBeforeLast(path, "/");
				String filename = StringUtils.substringAfterLast(path, "/");
				
				if(systemConfig.getIsSystemDebug()){
					logger.info("上传文件路径："+path);
				}

				if (!ftpClient.changeWorkingDirectory(directory)) {
					String[] paths = StringUtils.split(directory, "/");
					String p = "/";
					ftpClient.changeWorkingDirectory(p);
					for (String s : paths) {
						p += s + "/";
						if (!ftpClient.changeWorkingDirectory(p)) {
							ftpClient.makeDirectory(s);
							ftpClient.changeWorkingDirectory(p);
						}
					}
				}
				ftpClient.storeFile(filename, inputStream);
				ftpClient.logout();
				ftpClient.disconnect();
				logger.info("FTP文件上传结束");
				return true;
			}
			else{
				logger.info("连接FTP服务器失败");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("FTP上传异常：", e);
			return false;
		} 
		finally{
			if(inputStream !=null)
			{
				try {
					inputStream.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
}
