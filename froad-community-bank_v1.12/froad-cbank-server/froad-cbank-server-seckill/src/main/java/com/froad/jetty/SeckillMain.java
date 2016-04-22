package com.froad.jetty;

/**
 * 秒杀服务启动入口
 * 
 * @author wangzhangxu
 * @date 2015年4月25日 下午2:04:10
 * @version v1.0
 */
public class SeckillMain {

	public static void main(String[] args) {
		ServerRun server = new ServerRun();
		server.run();
	}

}
