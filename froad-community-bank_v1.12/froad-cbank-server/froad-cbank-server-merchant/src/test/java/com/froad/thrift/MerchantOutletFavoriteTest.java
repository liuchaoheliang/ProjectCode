package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.vo.RecvInfoVo;


public class MerchantOutletFavoriteTest {

	public static void main(String[] args) {
		try {

//			TSocket transport = new TSocket("localhost", 15201);
//			TSocket transport = new TSocket("10.43.2.3", 15201);
			TSocket transport = new TSocket("10.43.1.9", 15201);

			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantOutletFavoriteService");
			MerchantOutletFavoriteService.Client service = new MerchantOutletFavoriteService.Client(mp);

			transport.open();
			
			String clientId="anhui";
			long memberCode=30005240170l;

			// TODO
//			List<RecvInfoVo> rs = service.getRecvInfoVo(clientId, memberCode, null);
//			System.out.println(JSON.toJSONString(rs));
		
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
