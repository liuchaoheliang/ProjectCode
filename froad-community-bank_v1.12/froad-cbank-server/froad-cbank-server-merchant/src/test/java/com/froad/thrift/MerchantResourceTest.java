package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.MerchantResourceService;
import com.froad.thrift.vo.MerchantResourceVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResourceVo;
//import com.froad.thrift.service.MerchantAccountService;
//import com.froad.thrift.service.HelloService;
//import com.froad.thrift.service.UserService;

public class MerchantResourceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String host= "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantResourceService");
			MerchantResourceService.Client service = new MerchantResourceService.Client(mp);

			transport.open();


			// 新增
			MerchantResourceVo merchantResourceVo = new MerchantResourceVo(); 
//			merchantResourceVo.setClientId("anhui");
			merchantResourceVo.setName("测试资源1");
			merchantResourceVo.setIcon("");
			merchantResourceVo.setUrl("http://www.baidu.com");
			merchantResourceVo.setApi("/baidu/com");
			merchantResourceVo.setType("1");
			merchantResourceVo.setParentId(100000000);
			merchantResourceVo.setTreePath("");
			merchantResourceVo.setIsEnabled(true);
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.bank);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("添加商户");
			
			
//			System.out.println(service.addMerchantResource(originVo, merchantResourceVo));
			System.out.println(service.moveMerchantResourceTo(originVo, 300000000, 400000000, 0));
//			System.out.println(service.getMerchantResource( new MerchantResourceVo()));
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
