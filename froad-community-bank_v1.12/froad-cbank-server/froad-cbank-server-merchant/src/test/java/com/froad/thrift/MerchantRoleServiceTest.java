package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.MerchantRoleService;

public class MerchantRoleServiceTest {
	




	public static void main(String[] args) {
		try {
//			TSocket transport = new TSocket("10.43.1.122", 15201);
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantRoleService");
			MerchantRoleService.Client service = new MerchantRoleService.Client(mp);

			transport.open();
			

			System.out.println(service.getMerchantRoleByClientIdAndRoleDesc("chongqing", "Role_Administrators"));

		
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}
