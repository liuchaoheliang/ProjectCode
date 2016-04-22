package com.froad.thrift;

import com.froad.monitor.impl.MonitorManager;

public class UdpMian {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MonitorManager mm=new MonitorManager();
		
		mm.send("111", "222", "333", "444", "555");
	}

}
