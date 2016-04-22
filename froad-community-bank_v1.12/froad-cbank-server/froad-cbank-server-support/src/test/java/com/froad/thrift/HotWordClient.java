package com.froad.thrift;

import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.HotWordService;
import com.froad.thrift.vo.ActivitiesPageVoRes;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.HotWordPageRes;
import com.froad.thrift.vo.HotWordVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;

public class HotWordClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15701);
//			TSocket transport = new TSocket("10.24.248.187",15701); 
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			OriginVo originVo = new OriginVo();
			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"HotWordService");
			HotWordService.Client service4 = new HotWordService.Client(mp4);
			
			transport.open();
			PageVo page =new PageVo();
			page.setPageSize(10);
			page.setPageNumber(1);
			HotWordVo hotWordVo=new HotWordVo();
			hotWordVo.setClientId("anhui");
			hotWordVo.setCategoryType(2);
			hotWordVo.setAreaId(1864);
			HotWordPageRes res=service4.searchHotWord(page, hotWordVo);
			System.out.println("============"+JSONObject.toJSONString(res.getPage()));
			System.out.println("============"+JSONObject.toJSONString(res.getHotList()));
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
