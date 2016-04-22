package com.froad.thrift;

import com.froad.thrift.ServerRun;

/**
 * 服务入口
 * 
 * @ClassName: ServerMain
 * @author FQ
 * @date 2015年4月9日 上午11:37:03
 *
 */
public class QRCodeMain {

	public static void main(String[] args) {
		ServerRun server = new ServerRun();
		server.run();
	}

}
