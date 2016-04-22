package com.froad.cbank.coremodule.framework.common.util.scp;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;

/**
 * scp工具类
 * @ClassName ScpUtil
 * @author zxl
 * @date 2015年4月15日 上午10:57:52
 */
public class ScpUtil {
	
	/**
	 * 创建连接
	 * @tilte connectServer
	 * @author zxl
	 * @date 2015年4月15日 上午10:57:33
	 * @param cfg
	 * @return
	 * @throws Exception
	 */
	private static Connection connectServer(ScpConfig cfg) throws Exception{
		boolean isAuthed = false;
		Connection scpConnection = null;
		if(cfg.getPort()==0){
			scpConnection = new Connection(cfg.getIp());
		}else{
			scpConnection = new Connection(cfg.getIp(), cfg.getPort());
		}
		
		scpConnection.connect();
		
		isAuthed = scpConnection.authenticateWithPassword(cfg.getUserName(), cfg.getPassWord());
		
		if(!isAuthed){
			throw new Exception("connect auth failed!");
		}
		return scpConnection;
	}
	
	/**
	 * 文件上传
	 * @tilte uploadFile
	 * @author zxl
	 * @date 2015年4月15日 上午11:09:21
	 * @param localFilePath 本地文件
	 * @param scpFileName 远程保存文件名
	 * @param scpDir 远程目录
	 * @param cfg 连接配置
	 * @throws Exception
	 */
	public static void uploadFile(String localFilePath, String scpFileName, String scpDir,ScpConfig cfg) throws Exception{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		try {
			scpConnection = connectServer(cfg);
			scpClient = scpConnection.createSCPClient();
			
			//检查远程目录，不存在创建
			createDir(scpDir,scpConnection);
			
			//文件上传
			scpClient.put(localFilePath, scpFileName, scpDir, "0774");
			
		} catch (Exception e) {
			throw e;
		}finally{
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 文件上传 批量
	 * @tilte uploadFile
	 * @author zxl
	 * @date 2015年4月15日 上午11:09:21
	 * @param localFilePath 本地文件
	 * @param scpFileName 远程保存文件名
	 * @param scpDir 远程目录
	 * @param cfg 连接配置
	 * @throws Exception
	 */
	public static void uploadFile(String[] localFilePath, String[] scpFileName, String scpDir,ScpConfig cfg) throws Exception{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		try {
			scpConnection = connectServer(cfg);
			scpClient = scpConnection.createSCPClient();
			
			//检查远程目录，不存在创建
			createDir(scpDir,scpConnection);
			
			//文件上传
			scpClient.put(localFilePath, scpFileName, scpDir, "0774");
			
		} catch (Exception e) {
			throw e;
		}finally{
			closeServer(scpConnection);
		}
	}
	
	/**
	 * 远程目录创建
	 * @tilte createDir
	 * @author zxl
	 * @date 2015年4月15日 上午11:12:10
	 * @param dirName
	 * @param scpConnection
	 * @throws IOException
	 */
	private static void createDir(String dirName,Connection scpConnection) throws IOException{
		SFTPv3Client sftpClient = null;
		
		try {
			sftpClient = new SFTPv3Client(scpConnection);
			sftpClient.mkdir(dirName, 0775);
		} catch (IOException e) {
			// 目录存在,不处理
		} finally {
			if (null != sftpClient){
				sftpClient.close();
			}
		}
	}
	
	/**
	 * 关闭连接
	 * @tilte closeServer
	 * @author zxl
	 * @date 2015年4月15日 上午11:12:57
	 * @param scpConnection
	 */
	private static void closeServer(Connection scpConnection){
		try {
			if(scpConnection!=null)
				scpConnection.close();
		} catch (Exception e){
		}
	}
	
	public static void main(String[] args) {
		try {
			ScpConfig scp = new ScpConfig("192.168.18.180", "ftpuser", "ftpuser");
			ScpUtil.uploadFile("/home/ling/Pictures/Baby/psb.jpg", "mybaby.jpg", "/var/www/img", scp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
