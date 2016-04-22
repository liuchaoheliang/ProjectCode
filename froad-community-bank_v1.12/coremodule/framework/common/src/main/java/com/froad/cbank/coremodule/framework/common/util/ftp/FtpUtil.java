package com.froad.cbank.coremodule.framework.common.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp工具类
 * @ClassName FtpUtil
 * @author zxl
 * @date 2015年4月1日 下午2:22:00
 */
public class FtpUtil {
	
	/**
	 * 文件上传
	 * @tilte upload
	 * @author zxl
	 * @date 2015年4月1日 下午2:44:03
	 * @param path 上传路径
	 * @param file 上传文件
	 * @param cfg 连接配置
	 * @throws Exception
	 */
	public static void upload(String path, File file, FtpConfig cfg) throws Exception {
		
		FTPClient ftp = new FTPClient();
		InputStream inputStream = null;
		try {
			
			if(!file.exists()){
				throw new Exception("file is not exists!");
			}
			
			inputStream = new FileInputStream(file);
			//connect
			if(cfg.getPort()==0){
				ftp.connect(cfg.getIp());
			}else{
				ftp.connect(cfg.getIp(), cfg.getPort());
			}
			//login
			ftp.login(cfg.getUserName(), cfg.getPassWord());
			//stream mode
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			//binary mode
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//pass mode
			ftp.enterLocalPassiveMode();
			
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				String directory = path;
				if (!ftp.changeWorkingDirectory(directory)) {
					String[] paths = StringUtils.split(directory, "/");
					String p = "/";
					ftp.changeWorkingDirectory(p);
					for (String s : paths) {
						p += s + "/";
						if (!ftp.changeWorkingDirectory(p)) {
							ftp.makeDirectory(s);
							ftp.changeWorkingDirectory(p);
						}
					}
				}
				ftp.storeFile(file.getName(), inputStream);
				ftp.logout();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
				if(ftp.isConnected()){
					ftp.disconnect();
				}
			} catch (Exception e2) {
			}
		}
	}
	
	/**
	 * 文件上传
	 * @tilte upload
	 * @author zxl
	 * @date 2015年4月1日 下午4:04:23
	 * @param path 上传路径
	 * @param filepath 上传文件路径
	 * @param cfg
	 * @throws Exception
	 */
	public static void upload(String path, String filepath, FtpConfig cfg) throws Exception {
		File file = new File(filepath);
		upload(path, file, cfg);
	}
	
	/**
	 * 文件删除
	 * @tilte delete
	 * @author zxl
	 * @date 2015年4月1日 下午4:11:23
	 * @param path 文件目录
	 * @param filename 文件名
	 * @param cfg
	 * @return
	 */
	public static boolean delete(String path , String filename, FtpConfig cfg){
		FTPClient ftp = new FTPClient();
		try {
			if(cfg.getPort()==0){
				ftp.connect(cfg.getIp());
			}else{
				ftp.connect(cfg.getIp(), cfg.getPort());
			}
			//login
			ftp.login(cfg.getUserName(), cfg.getPassWord());
			//pass mode
			ftp.enterLocalPassiveMode();
			
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				if(ftp.changeWorkingDirectory(path)){
					if(ftp.deleteFile(filename)){
						return true;
					}
				}
				ftp.logout();
				return false;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			if(ftp.isConnected()){
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try{
			FtpConfig cfg = new FtpConfig("192.168.18.180", "ftpuser", "ftpuser");
			File file = new File("/home/ling/Pictures/Baby/psb.jpg");
			FtpUtil.upload("/var/www/img", file, cfg);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
