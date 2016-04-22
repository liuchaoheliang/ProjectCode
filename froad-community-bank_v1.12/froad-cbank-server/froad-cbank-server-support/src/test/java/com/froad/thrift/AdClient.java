package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.service.AdService;
import com.froad.thrift.vo.AdPageVoRes;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.PageVo;

public class AdClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("127.0.0.1", 15701);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"AdService");
			AdService.Client service2 = new AdService.Client(mp2);
			
			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"AdPositionService");
			AdPositionService.Client service4 = new AdPositionService.Client(mp4);

			
			//-----------------------分页参数设置----------------
			PageVo page = new PageVo();
			page.setPageNumber(0);
			page.setPageSize(3);
			transport.open();
			
			AdPositionVo adpositionVo =new AdPositionVo(); 
			adpositionVo.setPositionPage("1");
			adpositionVo.setClientId("1000");
			AdVo adVo =new AdVo();
//			adVo.setId(100000002);
//			adVo.setClientId(100000);
//			adVo.setTitle("促销大减价222");
//			adVo.setType(1);
//			adVo.setContent("www.Ssas.com");
//			adVo.setLink("/froad-cbank/image/201501/adb53b16-f85f-4a70-a6fd-28398d6b2f74-thumbnail.jpg");
//			adVo.setIsEnabled(true);
//			adVo.setIsBlankTarge(true);
//			adVo.setAdPositionId(1);
//			adVo.setClickCount(0);
//			Date d =new Date();
//			 Long time = d.getTime();
//			long endTime=Long.valueOf(DateUtil.formatDateTime("HHmmss",Calendar.getInstance().getTime()));
//			adVo.setEndTime(new Date().getTime());
//			adVo.setPath("test");
//			System.out.println("add==========="+service2.addAd(adVo));
//			System.out.println("update==========="+service2.updateAd(adVo));
//			System.out.println("delete==========="+service2.deleteAd(adVo));
			
			/**
			 * 条件查询
			 */
			List<AdVo> list=null;
			list=service2.getAd(adVo,adpositionVo);
			/**
			 * 分页查询
			 */
//			AdPageVoRes Pagelist =service2.getAdByPage(page, adVo);
//		    list=Pagelist.getAdVoList();
			System.out.println("============"+JSONObject.toJSONString(list));
			for (AdVo adVo2 : list) {
				
				System.out.println("============"+adVo2.getId());
				System.out.println("============"+JSONObject.toJSONString(adVo2));
			}
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
