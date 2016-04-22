package com.froad.thrift;

import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.vo.ActivitiesPageVoRes;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;

public class ActivitiesClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
//			TSocket transport = new TSocket("localhost", 15701);
			TSocket transport = new TSocket("10.43.2.3",15701); 
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			OriginVo originVo = new OriginVo();
			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"ActivitiesService");
			ActivitiesService.Client service4 = new ActivitiesService.Client(mp4);
			
			transport.open();

			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
