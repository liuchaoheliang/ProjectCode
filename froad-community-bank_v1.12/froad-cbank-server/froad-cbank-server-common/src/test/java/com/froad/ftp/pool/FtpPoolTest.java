/**
 * Project Name:Froad Cbank Server Common
 * File Name:FtpPoollTest.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日上午10:54:55
 * Copyright (c) 2015, F-Road, Inc.
 *
*/
/**
 * Project Name:Froad Cbank Server Common
 * File Name:FtpPoollTest.java
 * Package Name:com.froad.ftp.pool
 * Date:2015年9月9日 上午10:54:55
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.ftp.pool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

/**
 * ClassName:FtpPoollTest
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月9日 上午10:54:55
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: FtpPoollTest
 * Function: TODO ADD FUNCTION
 * date: 2015年9月9日 上午10:54:55
 *
 * @author vania
 * @version 
 */
public class FtpPoolTest {

	/**
	 * main:(这里用一句话描述这个方法的作用).
	 *
	 * @author vania
	 * 2015年9月9日 上午10:54:55
	 * @param args
	 * 
	 */
	public static void main(String[] args) throws Exception {
		GenericObjectPool.Config config = new Config();
		// 最大池容量
		config.maxActive = 5;
		// 从池中取对象达到最大时,继续创建新对象.
		config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
		// 池为空时取对象等待的最大毫秒数.
		config.maxWait = 60 * 1000;
		// 取出对象时验证(此处设置成验证ftp是否处于连接状态).
		config.testOnBorrow = true;
		// 还回对象时验证(此处设置成验证ftp是否处于连接状态).
		config.testOnReturn = true;
//		FTPPool pool = new FTPPool(config, "10.24.234.100", 21, "wangyi", "wangyi", "true");
		FTPPool pool = new FTPPool(config, "10.24.248.193", 21, "root", "froad123!@#", "true","UFT-8");
		System.out.println("borrowSize1:" + pool.borrowSize());
		System.out.println("inPoolSize1:" + pool.inPoolSize());
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 8; i++) {
			FTPClient ftpClient = pool.getResource();
			System.out.println("ftpClient" + (i + 1) + " isConnected:" + ftpClient.isConnected());
			System.out.println("list===>"+ftpClient.list());
			// ftpClient.disconnect();
			// pool.returnResource(ftpClient);
			pool.returnResource(ftpClient);
		}
		System.out.println("time:" + (System.currentTimeMillis() - begin));
		System.out.println("borrowSize2:" + pool.borrowSize());
		System.out.println("inPoolSize2:" + pool.inPoolSize());
		pool.destroy();
	}

}
