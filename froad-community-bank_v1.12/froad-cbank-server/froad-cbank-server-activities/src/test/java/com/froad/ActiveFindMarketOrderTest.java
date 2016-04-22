package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.active.FindMarketOrderReqVo;
import com.froad.thrift.vo.active.FindMarketOrderResVo;

public class ActiveFindMarketOrderTest {

	//private static final String "0007" = null;

	public static void main(String[] args){
		try{
			
			TSocket transport = new TSocket("127.0.0.1", 16501);
			//TSocket transport = new TSocket("10.24.248.188", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			
			FindMarketOrderReqVo vo=new FindMarketOrderReqVo();
			vo.setClientId("chongqing");
			vo.setReqId("00001");
			vo.setOrderId("0007");
			vo.setSubOrderId("000703");
			vo.setProductId("A01");
			
			
			FindMarketOrderResVo resultVo = service.findMarketOrder(vo);
			System.out.println(JSON.toJSONString(resultVo));
			System.out.println(JSON.toJSONString(resultVo.getFindMarketSubOrderList().get(0)));
			//System.out.println(JSON.toJSONString(resultVo.getFindMarketSubOrderList().get(1)));
			//System.out.println(JSON.toJSONString(resultVo.getFindMarketSubOrderList().get(2)));
			//System.out.println(JSON.toJSONString(resultVo.getFindMarketSubOrderList().get(3)));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
