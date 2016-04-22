package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.po.MinatoSingleParamInfo;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.MinatoSingleLocationVo;
import com.froad.thrift.vo.active.MinatoSingleParamVo;

public class TestProductActive {
	public static void main(String[] args) {
		try{
			TSocket transport = new TSocket("localhost", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ActiveSearchService");
			ActiveSearchService.Client service = new ActiveSearchService.Client(mp);			
			transport.open();
				System.out.println("读取开始-----------------");
			MinatoSingleParamVo mspli = new MinatoSingleParamVo();
			MinatoSingleParamInfo minatoSingleParamInfo = new MinatoSingleParamInfo();
			PageVo pageVo = new PageVo();
			pageVo.setPageSize(10);
			mspli.setActiveId("chongqing-MJ-2015-004");
			mspli.setClientId("chongqing");
			mspli.setCookieId("1023");
			mspli.setAreaId("2095");
			mspli.setProductType("2");
			MinatoSingleLocationVo vo = new MinatoSingleLocationVo();	
			//mspli.setProductId("0D820CA18005");//[1278BFA20000, 0D8219418002, 0D820C318003, 0D820C318004, 0D820CC18000, 0DDC19F20000]
			/*vo.setLatitude(29.576822);
			vo.setLongitude(106.503276);
			mspli.minatoSingleLocation = vo;*/
			service.findProductListOfMinatoSingle(pageVo, mspli);			
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
