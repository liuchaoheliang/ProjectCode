package com.froad.thrift;

/**
 * 银行模块入口
 * @ClassName: BankMain 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年4月11日 上午11:41:04 
 *
 */
public class BankMain {

	public static void main(String[] args) {
		
		ServerRun server = new ServerRun();
		server.run();
	}

}
