/**
 * Project Name:Froad Cbank Server Common
 * File Name:FTPPoolableObjectFactory.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日上午10:22:31
 * Copyright (c) 2015, F-Road, Inc.
 *
*/
/**
 * Project Name:Froad Cbank Server Common
 * File Name:FTPPoolableObjectFactory.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日 上午10:22:31
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.ftp.pool;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.BasePoolableObjectFactory;

import com.froad.logback.LogCvt;

/**
 * ClassName:FTPPoolableObjectFactory
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月9日 上午10:22:31
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: FTPPoolableObjectFactory
 * Function: TODO ADD FUNCTION
 * date: 2015年9月9日 上午10:22:31
 *
 * @author vania
 * @version 
 */
public class FTPPoolableObjectFactory extends BasePoolableObjectFactory {
	private String host;
	private int port;
	private String user;
	private String password;
	private String passiveModeConf;
	private String encoding;

	public FTPPoolableObjectFactory(String host, int port, String user, String password, String passiveModeConf,String encoding) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.passiveModeConf = passiveModeConf;
		this.encoding = encoding;
		LogCvt.info("ftp连接池启动成功！host:" + host + ",port:" + port + ",user:" + user + ",encoding:" + encoding);
	}

	@Override
	public Object makeObject() throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(host, port);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {  
			ftpClient.disconnect();  
            LogCvt.error("FTP服务器拒绝连接");  
            throw new Exception("FTP服务器拒绝连接");  
        }
		ftpClient.setControlEncoding(encoding);
		ftpClient.setControlKeepAliveTimeout(300);// set timeout to 5 minutes
//		ftpClient.setDataTimeout(120000);
//		ftpClient.setConnectTimeout(connectTimeout);
		if(!ftpClient.login(user, password)){
			ftpClient.disconnect();  
            LogCvt.error("登陆验证失败，请检查账号和密码是否正确");  
            throw new Exception("登陆验证失败，请检查账号和密码是否正确");  
		}
		boolean passiveMode = false;
		if (passiveModeConf == null || Boolean.parseBoolean(passiveModeConf) == true) {
			passiveMode = true;
		}
		if (passiveMode) {
			ftpClient.enterLocalPassiveMode();
		}
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		return ftpClient;
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		if (obj instanceof FTPClient) {
			FTPClient ftpClient = (FTPClient) obj;
			if (!ftpClient.isConnected())
				return;
			try {
				if (!ftpClient.logout()) { // 注销登录
					throw new Exception("注销登录失败");
				}
				LogCvt.debug("FTPClient销毁成功!");
			} catch (Exception e) {
				LogCvt.error("FTPClient销毁失败，原因：" + e.getMessage(), e);
			} finally {
				if (ftpClient.isConnected())
					ftpClient.disconnect();
			}
		}
	}

	@Override
	public boolean validateObject(Object obj) {
		if (obj instanceof FTPClient) {
			FTPClient ftpClient = (FTPClient) obj;
			try {
				return ftpClient.isConnected();
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
}
