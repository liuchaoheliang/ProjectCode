package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.vo.DeliveryCorpPageVoRes;
import com.froad.thrift.vo.DeliveryCorpVo;
import com.froad.thrift.vo.PageVo;

public class DeliveryCorpClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 8899);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"DeliveryCorpService");
			DeliveryCorpService.Client service4 = new DeliveryCorpService.Client(mp4);
			
			//-----------------------分页参数设置----------------
			PageVo page = new PageVo();
			page.setPageNumber(1);
			page.setPageSize(3);
			transport.open();
			
			DeliveryCorpVo deliveryCorpVo =new DeliveryCorpVo(); 
//			deliveryCorpVo.setId(100000000L);
			deliveryCorpVo.setClientId("1000");
//			deliveryCorpVo.setName("天天快递");
//			deliveryCorpVo.setUrl("http://www.ttkdex.com");
			deliveryCorpVo.setOrderValue(1);
			deliveryCorpVo.setIsEnable(true);
			/**
			 * 条件查询
			 */
			List<DeliveryCorpVo> list=null;
			DeliveryCorpPageVoRes res=null;
//			list=service4.getDeliveryCorp(deliveryCorpVo);
			
			res=service4.getDeliveryCorpByPage(page, deliveryCorpVo);
			list =res.getDeliveryCorpVoList();
			for (DeliveryCorpVo adVo2 : list) {
				System.out.println("-----------"+JSONObject.toJSONString(adVo2));
			}
//			
//			System.out.println("-----"+service4.addDeliveryCorp(deliveryCorpVo));
//			System.out.println("-----"+service4.updateDeliveryCorp(deliveryCorpVo));
//			System.out.println("-----"+service4.deleteDeliveryCorp(deliveryCorpVo));
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
