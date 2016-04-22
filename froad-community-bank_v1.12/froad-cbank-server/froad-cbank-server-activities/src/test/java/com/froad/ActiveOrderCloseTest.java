package com.froad;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CloseMarketOrderReqVo;

public class ActiveOrderCloseTest {

	//private static final String "0007" = null;

	public static void main(String[] args){
		try{
			
			TSocket transport = new TSocket("127.0.0.1", 16501);
			//TSocket transport = new TSocket("10.24.248.188", 16501);
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			CloseMarketOrderReqVo vo=new CloseMarketOrderReqVo();
			vo.setClientId("chongqing");
			vo.setMemberCode(7);
			vo.setReason(1);
			vo.setReqId("00001");
			vo.setOrderId("0007");
			
			
			ResultVo resultVo = service.closeMarketOrder(vo);
			
			System.out.println(JSON.toJSONString(resultVo));
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
