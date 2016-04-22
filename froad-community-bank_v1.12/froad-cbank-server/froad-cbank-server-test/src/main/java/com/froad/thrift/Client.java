package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.UserService;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("127.0.0.1", 9999);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);


			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"UserService");
			UserService.Client service1 = new UserService.Client(mp1);

			transport.open();

			System.out.println(service1.getById(0));
			System.out.println(service1.getName());
			System.out.println(service1.getVersion());
			System.out.println(service1.aliveSince());
			System.out.println(service1.getServiceCount());
			System.out.println(service1.getServiceBizMethods());
			System.out.println(service1.getBizMethodInvokeInfo("getById"));
			System.out.println(service1.getBizMethodsInvokeInfo());

			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
