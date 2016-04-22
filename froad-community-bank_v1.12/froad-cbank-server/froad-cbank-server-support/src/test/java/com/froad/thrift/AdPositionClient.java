package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.PageVo;

public class AdPositionClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 8899);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"AdPositionService");
			AdPositionService.Client service4 = new AdPositionService.Client(mp4);
			
			//-----------------------分页参数设置----------------
			PageVo page = new PageVo();
			page.setPageNumber(1);
			page.setPageSize(6);
			transport.open();
			
			AdPositionVo adpositionVo =new AdPositionVo(); 
			adpositionVo.setPositionPage("111122");
			adpositionVo.setClientId("1000");
//			adpositionVo.setName("测试");
//			adpositionVo.setWidth(12);
//			adpositionVo.setHeight(300);
//			adpositionVo.setPositionStyle("1");
//			adpositionVo.setPositionPoint(5555);
//			adpositionVo.setDescription("33333333333333");
			adpositionVo.setId(100000001L);
//			adpositionVo.setIsEnable(true);
			
			/**
			 * 条件查询
			 */
			List<AdPositionVo> list=null;
			list=service4.getAdPosition(adpositionVo);
			
			for (AdPositionVo adVo2 : list) {
				System.out.println("-----------"+JSONObject.toJSONString(adVo2));
			}
//			
//			System.out.println("-----"+service4.getAdPosition(adpositionVo));
//			System.out.println("-----"+service4.updateAdPosition(adpositionVo));
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
