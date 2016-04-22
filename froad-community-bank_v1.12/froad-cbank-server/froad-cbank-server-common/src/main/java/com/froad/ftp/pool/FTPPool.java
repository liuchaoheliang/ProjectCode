/**
 * Project Name:Froad Cbank Server Common
 * File Name:FTPPool.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日上午10:22:08
 * Copyright (c) 2015, F-Road, Inc.
 *
*/
/**
 * Project Name:Froad Cbank Server Common
 * File Name:FTPPool.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日 上午10:22:08
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.ftp.pool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * ClassName:FTPPool
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月9日 上午10:22:08
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: FTPPool
 * Function: TODO ADD FUNCTION
 * date: 2015年9月9日 上午10:22:08
 *
 * @author vania
 * @version 
 */
public class FTPPool extends Pool<FTPClient>{
	
	/**
	 * Creates a new instance of FTPPool.
	 *
	 * @param poolConfig
	 * @param host FTP服务器地址
	 * @param port FTP服务器端口
	 * @param user FTP服务器用户名
	 * @param password FTP服务器用户密码
	 * @param passiveModeConf FTP连接模式：true:主动模式是从服务器端向客户端发起；false:被动模式是客户端向服务器端发起连接。
	 * @param encoding 编码
	 * 
	 */
	public FTPPool(GenericObjectPool.Config poolConfig, String host, int port, String user, String password, String passiveModeConf,String encoding) {
		super(poolConfig, new FTPPoolableObjectFactory(host, port, user, password, passiveModeConf,encoding));
	}
	
}
