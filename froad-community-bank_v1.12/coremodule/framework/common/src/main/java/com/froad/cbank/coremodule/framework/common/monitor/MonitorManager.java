package com.froad.cbank.coremodule.framework.common.monitor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 监控发送
 * @ClassName MonitorManager
 * @author zxl
 * @date 2015年5月9日 上午11:02:30
 */
public class MonitorManager implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MonitorManager.class);
	private String pk;
	private String value;
	private MonitorEnums enums;
	private SocketAddress sa;
	public MonitorManager(MonitorEnums enums, String value,SocketAddress sa,String pk) {
		this.pk = pk;
		this.value = value;
		this.enums = enums;
		this.sa = sa;
	}

	public void run() {
		DatagramSocket ds = null;
		try {
			Thread.currentThread().setName(pk+"-Monitor");
			
			Map<String, String> sendData = new HashMap<String, String>();
			
			if (enums == null) {
				return;
			}

			if (value == null || "".equals(value)) {
				return;
			}
			
			sendData.put("businame", enums.getBusiname());
			sendData.put("attr_id", enums.getAttrId());
			sendData.put("value", value);
			sendData.put("type", enums.getType());

			String sendStr = JSON.toJSONString(sendData);
			
			ds = new DatagramSocket();
			
			logger.info("MONITOR SEND : " + sendStr);
			
			byte[] buf = sendStr.getBytes("UTF-8"); // 数据
			DatagramPacket dp = new DatagramPacket(buf, buf.length, sa);
			ds.send(dp);
			
		} catch (Exception e) {
			logger.error("MONITOR SEND ERROR : " + e.getMessage());
		} finally {
			if(ds!=null){
				ds.close();
			}
		}
	}
}
