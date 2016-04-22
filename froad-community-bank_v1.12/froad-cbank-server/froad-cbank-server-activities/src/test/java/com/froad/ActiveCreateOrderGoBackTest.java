package com.froad;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.FailureGoBackReqVo;

public class ActiveCreateOrderGoBackTest {
	
	public static void main(String[] args){
		try{
			
			//"activeIdListIterator":{},"activeIdListSize":1,"clientId":"chongqing","memberCode":50000362098,"reqId":"12214E810000","setActiveIdList":true,"setClientId":true,"setMemberCode":true,"setReqId":true}
			//TSocket transport = new TSocket("127.0.0.1", 16501);
			TSocket transport = new TSocket("10.24.248.188", 16501);
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			FailureGoBackReqVo vo=new FailureGoBackReqVo();
			
			vo.setClientId("chongqing");
			vo.setMemberCode(50000362098L);
			List<String> xx=new ArrayList<String>();
			xx.add("chongqing-MJ-2015-002");
			vo.setActiveIdList(xx);
			ResultVo resultVo = service.createOrderFailureGoBack(vo);
			System.out.println(JSON.toJSONString(resultVo));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
