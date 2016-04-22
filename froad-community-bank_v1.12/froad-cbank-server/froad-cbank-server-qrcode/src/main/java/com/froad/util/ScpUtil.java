package com.froad.util;

import java.io.IOException;
import java.util.Map;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;

import com.froad.QrCodeConstants;

public class ScpUtil {
	public static Connection scpConnection = null;
	public static final Map<String, String> scpInfoMap = PropertiesUtil.loadProperties(QrCodeConstants.QR_PROPERTIES_NAME);
	
	private static boolean connectServer(){
		String hostname = null;
		int port = 0;
		String userName = null;
		String password = null;
		boolean isAuthed = false;
		
		try {
			hostname = scpInfoMap.get(QrCodeConstants.PROPERTY_SCP_HOST).toString();
			port = Integer.valueOf(scpInfoMap.get(QrCodeConstants.PROPERTY_SCP_PORT));
			userName = scpInfoMap.get(QrCodeConstants.PROPERTY_SCP_USER).toString();
			password = scpInfoMap.get(QrCodeConstants.PROPERTY_SCP_PASSWORD).toString();
			
			scpConnection = new Connection(hostname, port);
			scpConnection.connect();
			
			isAuthed = scpConnection.authenticateWithPassword(userName, password);
			
			if(!isAuthed){
				return false;
			}
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * SCP 上传文件
	 * 
	 * @param fileName
	 * @param fileSize
	 * @param scpDir
	 */
	public static void uploadFile(String filePath, String fileName, String scpDir, boolean isRetry){
		SCPClient scpClient = null;
		
		try {
			// 如ssh连接已失效，重新连接
			if (null == scpConnection || isRetry){
				connectServer();
			}
			
			scpClient = scpConnection.createSCPClient();
			
			try {
				// 其他用户组仅允许读和执行权限
				scpClient.put(filePath, fileName, scpDir, "0774");
			} catch (Exception e) {
				// 如连接超出可用连接，重新连接
				if (!isRetry) {
					uploadFile(filePath, fileName, scpDir, true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建目录
	 * 
	 * @param dirName
	 * @return
	 * @throws IOException 
	 */
	public static void createDir(String dirName) throws IOException{
		SFTPv3Client sftpClient = null;
		
		try {
			// 如ssh连接已失效，重新连接
			if (null == scpConnection){
				connectServer();
			}
			
			sftpClient = new SFTPv3Client(scpConnection);
			// ls判断目录是否存在，若目录文件过多，效率较差
			//sftpClient.ls(dirName);
			
			sftpClient.mkdir(dirName, 0775);
		} catch (Exception e) {
			// 若目录存在，不作任何处理
		} finally {
			if (null != sftpClient){
				sftpClient.close();
			}
		}
	}
	
	/**
	 * 关闭SCP连接
	 */
	public void closeServer(){
		try {
			scpConnection.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
