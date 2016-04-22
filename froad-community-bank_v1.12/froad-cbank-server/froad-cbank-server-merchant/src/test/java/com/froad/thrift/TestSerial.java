package com.froad.thrift;

import com.froad.util.PropertiesUtil;
import com.froad.util.SerialNumberUtil;

public class TestSerial {

	
	public static void main(String[] args) {
		PropertiesUtil.load();
		for(int i =0; i<10; i++){
		System.out.println(SerialNumberUtil.getAuditIdSerialNumber());
		System.out.println(SerialNumberUtil.getTaskIdSerialNumber());
		}
	}
}
