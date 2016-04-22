package com.froad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import com.froad.TaskConstants;
import com.froad.logback.LogCvt;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPUtils {

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	private static ChannelSftp connect(String host, int port, String username,String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			LogCvt.info("connect to sftp success! host: " + host);
		} catch (Exception e) {
			LogCvt.error("sftp connection exception",e);
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	private static void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean uploadFile(String fileURI){
		final Map<String, String> scpInfoMap = PropertiesUtil.loadProperties(TaskConstants.SCP_FILE_NAME);
		ChannelSftp sftp = connect(scpInfoMap.get(TaskConstants.SCP1_HOST), Integer.valueOf(scpInfoMap.get(TaskConstants.SCP1_PORT)), scpInfoMap.get(TaskConstants.SCP1_USERNAME), scpInfoMap.get(TaskConstants.SCP1_PASSWORD));
	
		upload(scpInfoMap.get(TaskConstants.SCP1_WORDIMAGER_DIR), fileURI, sftp);
		if (sftp != null) {
			try {
				if (sftp.getSession() != null) {
					sftp.getSession().disconnect();
				}
				
			} catch (JSchException e) {
				e.printStackTrace();
				return false;
			}
			sftp.disconnect();
			return true;
		}
		return true;
	}
	
	public static void main(String[] args) throws JSchException {
		System.setProperty("CONFIG_PATH", "./config");
		SFTPUtils sf = new SFTPUtils();
		String host = "10.43.1.138";
		int port = 22;
		String username = "sqyh";
		String password = "PgiXdrlKO1Lxj7nVE1tv";
		String directory = "/data/ubank/sqyh/";
		String uploadFile = "F:\\UBANK_C20151027.txt";
//		String downloadFile = "debuginfo.txt";
//		String saveFile = "D:\\tmp\\debuginfo.txt";
//		String deleteFile = "delete.txt";
		ChannelSftp sftp = sf.connect(host, port, username, password);
		sf.upload(directory, uploadFile, sftp);
		if (sftp != null) {
			if (sftp.getSession() != null) {
				sftp.getSession().disconnect();
			}
			sftp.disconnect();
		}
		
//		sf.download(directory, downloadFile, saveFile, sftp);
		// sf.delete(directory, deleteFile, sftp);
		// try{
		// sftp.cd(directory);
		// sftp.mkdir("ss");
		// System.out.println("finished");
		// }catch(Exception e){
		// e.printStackTrace();
		// }
	}
}
