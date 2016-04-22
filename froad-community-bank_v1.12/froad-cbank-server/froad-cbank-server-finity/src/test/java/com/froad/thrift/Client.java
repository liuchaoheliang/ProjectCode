package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.vo.finity.FinityResourceVo; 
public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 16301);
//			TSocket transport = new TSocket("10.43.2.238",15101);
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"FinityResourceService");
			FinityResourceService.Client service1 = new FinityResourceService.Client(mp1);
 
			transport.open();
			
			FinityResourceVo rVo=new FinityResourceVo();
			 List<FinityResourceVo> rList= service1.getFinityResource(rVo);
			 System.out.println(rList.size());
			 
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
