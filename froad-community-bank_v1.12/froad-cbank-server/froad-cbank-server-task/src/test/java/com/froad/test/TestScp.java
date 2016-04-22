package com.froad.test;

import java.io.File;
import java.util.Map;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import com.froad.TaskConstants;
import com.froad.logback.LogCvt;
import com.froad.util.PropertiesUtil;

public class TestScp {
	private static Connection scpConnection = null;
	
	private final static Map<String, String> scpInfoMap = PropertiesUtil.loadProperties(TaskConstants.SCP_FILE_NAME);
	
	private static void connectServer(){
		String hostname = null;
		int port = 0;
		String userName = null;
		String password = null;
		boolean isAuthed = false;
		
		try {
			hostname = scpInfoMap.get(TaskConstants.SCP_HOST).toString();
			port = Integer.valueOf(scpInfoMap.get(TaskConstants.SCP_PORT));
			userName = scpInfoMap.get(TaskConstants.SCP_USERNAME).toString();
			password = scpInfoMap.get(TaskConstants.SCP_PASSWORD).toString();
			
			scpConnection = new Connection(hostname, port);
			scpConnection.connect();
			
			isAuthed = scpConnection.authenticateWithPassword(userName, password);
			
			if(!isAuthed){
				return;
			}
		} catch (Exception e){
			LogCvt.error("连接SCP异常");
			LogCvt.error(e.getMessage(), e);
			return;
		}
	}
	
	public static void main(String[] args) {
//		SFTPv3Client sftpClient = null;
		try {
			if(scpConnection == null){
				connectServer();
			}
//			sftpClient = new SFTPv3Client(scpConnection);
			
			String dirName = scpInfoMap.get(TaskConstants.SCP_WORDIMAGER_DIR);
			Session session = scpConnection.openSession();
			String descDir = dirName+"2015-04";
			System.out.println(descDir);
			String command = "rm -rf "+descDir;
			
			session.execCommand(command);
			
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			if(scpConnection != null) scpConnection.close();
		}
	}
}
