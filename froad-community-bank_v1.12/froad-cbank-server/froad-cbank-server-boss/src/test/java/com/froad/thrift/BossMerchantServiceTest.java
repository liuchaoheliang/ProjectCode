package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BossMerchantService;
import com.froad.thrift.service.BossProductService;
import com.froad.thrift.vo.BossProductFilterVo;
import com.froad.thrift.vo.PageVo;

public class BossMerchantServiceTest {
	
	public static void main(String[] args) {
		try {
			
//			TSocket transport = new TSocket("10.43.1.9", 15201);
			TSocket transport = new TSocket("127.0.0.1", 15401);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, BossMerchantService.class.getSimpleName());
			BossMerchantService.Client service = new BossMerchantService.Client(mp);

			transport.open();
			System.out.println(service.getSubBankOutletToList("chongqing", "340101"));
			
			transport.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
