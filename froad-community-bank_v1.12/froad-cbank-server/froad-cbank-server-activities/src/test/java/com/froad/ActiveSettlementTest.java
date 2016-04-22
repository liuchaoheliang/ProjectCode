package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.active.SettlementMarkOrderReq;
import com.froad.thrift.vo.active.SettlementMarkOrderRes;

public class ActiveSettlementTest {
	
	public static void main(String[] args){
		try{
			
			TSocket transport = new TSocket("127.0.0.1", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			
			SettlementMarkOrderReq vo=new SettlementMarkOrderReq();
			vo.setReqId("00001");
			vo.setOrderId("0007");
			vo.setSubOrderId("000701");
			vo.setProductId("A01");
			vo.setCount(3);
			vo.setVipCount(3);
			vo.setIsLast(true);
			
			
			SettlementMarkOrderRes resultVo = service.settlementMarkOrder(vo);
			
			System.out.println(JSON.toJSONString(resultVo));
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
