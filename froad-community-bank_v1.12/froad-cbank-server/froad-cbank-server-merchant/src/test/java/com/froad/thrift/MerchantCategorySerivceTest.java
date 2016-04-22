package com.froad.thrift;

import java.util.List;
import java.util.ArrayList;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.PageVo;

public class MerchantCategorySerivceTest {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String host = "10.43.2.3";
			int port = 15201;
			
			host = "127.0.0.1";
			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantCategoryService");
			MerchantCategoryService.Client service = new MerchantCategoryService.Client(mp);

			transport.open();

			MerchantCategoryVo   vo = new MerchantCategoryVo();
			vo.setClientId("chongqing");
			vo.setParentId(100000019);
			vo.setAreaId(2090);

			System.out.println(service.getMerchantCategoryByH5(vo));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

