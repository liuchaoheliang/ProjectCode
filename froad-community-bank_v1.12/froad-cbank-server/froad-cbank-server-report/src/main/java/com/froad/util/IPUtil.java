package com.froad.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPUtil {
	
	/**
	 * retrieve local ip
	 * 
	 * @return
	 */
	public static String getLocalIp(){
		String localIp = "localhost";
		Enumeration<NetworkInterface> nets = null;
		NetworkInterface net = null;
		Enumeration<InetAddress> addresses = null;
		InetAddress address = null;
		
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()){
				net = nets.nextElement();
				addresses = net.getInetAddresses();
				
				while (addresses.hasMoreElements()){
					address = addresses.nextElement();
					if (address.isSiteLocalAddress()){
						localIp = address.getHostAddress();
						break;
					}
				}
				
				if (!localIp.equals("localhost")){
					break;
				}
			}
		} catch (Exception e) {
		}
		
		return localIp;
	}
	
	/**
	 * get node port
	 * 
	 * @return
	 */
	public static Integer getNodePort(){
		Integer port = null;
		
		port = Integer.parseInt(PropertiesUtil.getProperty("init", "port"));
		
		return port;
	}
}
