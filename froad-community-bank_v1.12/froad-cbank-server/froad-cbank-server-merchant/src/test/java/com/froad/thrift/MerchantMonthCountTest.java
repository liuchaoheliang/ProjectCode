package com.froad.thrift;

import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantMonthCountService;
import com.froad.thrift.vo.MerchantMonthCountVo;
import com.froad.thrift.vo.PageVo;

public class MerchantMonthCountTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			TSocket transport = new TSocket("localhost", 15201);
			TSocket transport = new TSocket("10.43.1.9", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantMonthCountService");
			MerchantMonthCountService.Client service = new MerchantMonthCountService.Client(mp);

			transport.open();

			MerchantMonthCountVo merchantMonthCountVo = new MerchantMonthCountVo();
			merchantMonthCountVo.setClientId("anhui");
			merchantMonthCountVo.setMerchantId("01FC88740000");
			merchantMonthCountVo.setYear("2015");
			merchantMonthCountVo.setMonth("05");
			merchantMonthCountVo = service.getMerchantMonthCount(merchantMonthCountVo);
			System.out.println(JSON.toJSONString(merchantMonthCountVo));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
