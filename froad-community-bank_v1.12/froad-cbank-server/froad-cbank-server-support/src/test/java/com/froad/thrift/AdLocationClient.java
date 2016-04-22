package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.enums.AdParamType;
import com.froad.enums.TerminalType;
import com.froad.thrift.service.AdLocationService;
import com.froad.thrift.vo.AdLocationVo;

public class AdLocationClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15701);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"AdLocationService");
			AdLocationService.Client service4 = new AdLocationService.Client(mp4);
			
			transport.open();
			
			// 新增
			add(service4);
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static void add(AdLocationService.Client service4) throws Exception{
		AdLocationVo adLocationVo = new AdLocationVo();
		adLocationVo.setName("特惠商户首页");
	//	adLocationVo.setTerminalType(TerminalType.ANDRIOD.getCode());
		adLocationVo.setPositionPage("THSH");
		adLocationVo.setSizeLimit(213);
		adLocationVo.setWidth(300);
		adLocationVo.setHeight(75);
		adLocationVo.setParamOneType(AdParamType.area.getCode());
		adLocationVo.setParamTwoType(AdParamType.merchant.getCode());
		adLocationVo.setDescription("特惠商户首页的广告位");
		service4.addAdLocation(null, adLocationVo);
	}
}
