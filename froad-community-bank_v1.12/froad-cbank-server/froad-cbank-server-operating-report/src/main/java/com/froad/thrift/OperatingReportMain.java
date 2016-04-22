package com.froad.thrift;

import com.froad.util.PropertiesUtil;

/**
 * Hello world!
 *
 */
public class OperatingReportMain {
	
    public static void main( String[] args ){
    	PropertiesUtil.load();
    	ServerRun thrift = new ServerRun();
    	thrift.run();
    }
}
