package com.froad.monitor.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.enums.MonitorPointEnum;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.util.PropertiesUtil;

/**
 * 监控上报
 * 
 * @ClassName: MonitorManager
 * @Description: TODO
 * @author FQ
 * @date 2015年3月18日 上午10:38:30
 *
 */
public class MonitorManager implements MonitorService {

	private static String IP;
	private static int PORT;

	private static SocketAddress sa = null;
	
	public static Map<String, String> propertiesMap = null;

	// 初始化
	static {
		propertiesMap = PropertiesUtil.loadProperties("monitor");
		IP = propertiesMap.get("monitor.host");
		PORT = Integer.valueOf(propertiesMap.get("monitor.port"));
		sa = new InetSocketAddress(IP, PORT);
		
//		FileReader fr = null;
//		try {
//			Properties props = new Properties();
//			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"monitor.properties"));
//			props.load(fr);
//
//			IP = props.getProperty("monitor.host");
//			PORT = Integer.valueOf(props.getProperty("monitor.port"));
//			sa = new InetSocketAddress(IP, PORT);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if(fr != null){
//					fr.close();
//				}
//			} catch (IOException e) {
//			}
//		}
	}

	public void send(String businame, String attrId, String value, String type,String unionAttrId) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket();
			Map<String, String> sendData = new HashMap<String, String>();
			sendData.put("businame", businame);
			sendData.put("attr_id", attrId);
			sendData.put("value", value);
			sendData.put("type", type);
			sendData.put("union_attr_id", unionAttrId);

			String sendStr = JSON.toJSONString(sendData);
			LogCvt.debug("Monitor Send :" + sendStr);

			if (businame == null || "".equals(businame)) {
				LogCvt.debug("Monitor Send Parameters businame is null !");
				return;
			}

			if (attrId == null || "".equals(attrId)) {
				LogCvt.debug("Monitor Send Parameters attr_id is null !");
				return;
			}

			if (value == null || "".equals(value)) {
				LogCvt.debug("Monitor Send Parameters value null !");
				return;
			}

			byte[] buf = sendStr.getBytes("UTF-8"); // 数据
			DatagramPacket dp = new DatagramPacket(buf, buf.length, sa);
			ds.send(dp);
		} catch (Exception e) {
			LogCvt.error("监控消息推送异常...",e);
		} finally {
			if(null != ds)
				ds.close();
		}
	}
	
	@Override
	public void send(MonitorPointEnum point) {
		send(point.getBusinessName(), point.getAttId(), point.getValue(), point.getType(), point.getUnionAttrId());
	}

	@Override
	public void send(MonitorPointEnum point, String value) {
		send(point.getBusinessName(), point.getAttId(), value, point.getType(), point.getUnionAttrId());
	}

	public static void main(String[] args) {
		Map<String, String> sendData = new HashMap<String, String>();
		sendData.put("businame", "business_bank");
		sendData.put("attr_id", "b1d76141-7fdc-4183-ba4e-7b84f780c29d");
		sendData.put("value", "10");
		sendData.put("type", "int");
		sendData.put("union_attr_id", "b1637f6-21fb-4f46-8b27-2b78134e8567");

	}

}
