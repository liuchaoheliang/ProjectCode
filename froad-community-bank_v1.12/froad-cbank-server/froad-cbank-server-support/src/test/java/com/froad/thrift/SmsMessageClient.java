package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;

public class SmsMessageClient {
	
	public static void main(String[] args) {

		TTransport transport = null;
		TProtocol protocol = null;
		TMultiplexedProtocol multiProtocol = null;
		SmsMessageService.Client client = null;
		SmsMessageVo smsMessageVo = null;
	    String keyword = null;
	    Long clientId = null;
		
	    try { 
	        // 设置调用的服务地址为本地，端口为 8899 
	        transport = new TSocket("localhost", 15701); 
//	    	 transport = new TSocket("10.43.2.3",15701); 
//	    	transport = new TSocket("10.43.2.3",15701); 
	        transport.open(); 
	        
	        // 设置传输协议为 TBinaryProtocol 
	        protocol = new TBinaryProtocol(transport); 
	        multiProtocol = new TMultiplexedProtocol(protocol,"SmsMessageService");
	        client = new SmsMessageService.Client(multiProtocol);
	        
	        transport.close(); 
	    } catch (TTransportException e) { 
	        e.printStackTrace(); 
	    } catch (TException e) { 
	        e.printStackTrace(); 
	    } 
	} 
	

}

