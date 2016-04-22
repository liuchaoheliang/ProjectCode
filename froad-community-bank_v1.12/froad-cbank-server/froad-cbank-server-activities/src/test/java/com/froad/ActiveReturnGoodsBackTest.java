package com.froad;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ReturnMarketOrderBackReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderBackReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderProductBackReqVo;

public class ActiveReturnGoodsBackTest {

	//private static final String "0007" = null;

	public static void main(String[] args){
		try{
			
			TSocket transport = new TSocket("127.0.0.1", 16501);
			//TSocket transport = new TSocket("10.24.248.188", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
	
			ReturnMarketOrderBackReqVo vo=new ReturnMarketOrderBackReqVo();
			
			vo.setClientId("chongqing");
			vo.setReqId("00001");
			vo.setOrderId("0007");
			
			List<ReturnSubOrderProductBackReqVo> productList=new ArrayList<ReturnSubOrderProductBackReqVo>();
			ReturnSubOrderProductBackReqVo productVo=new ReturnSubOrderProductBackReqVo();
			productVo.setProductId("A01");
			
			productVo.setNormalCount(1);
			productVo.setNormalPrice(0);
			//productVo.setVipCount(1);
			//productVo.setVipPrice(0);

			productList.add(productVo);

			ReturnSubOrderBackReqVo returnSubOrderVo =new ReturnSubOrderBackReqVo();
			returnSubOrderVo.setSubOrderId("000701");
			returnSubOrderVo.setReturnSubOrderProductBackReqList(productList);
			
		
			/*
			ReturnSubOrderBackReqVo returnSubOrderVo1 =new ReturnSubOrderBackReqVo();
			returnSubOrderVo1.setSubOrderId("000702");
			List<ReturnSubOrderProductBackReqVo> productList1=new ArrayList<ReturnSubOrderProductBackReqVo>();
			ReturnSubOrderProductBackReqVo productVo1=new ReturnSubOrderProductBackReqVo();

			productVo1.setProductId("A02");
			
			productVo1.setNormalCount(1);
			productVo1.setNormalPrice(5.67);
			productVo1.setVipCount(1);
			productVo1.setVipPrice(6);

			productList1.add(productVo1);
			
			returnSubOrderVo1.setReturnSubOrderProductBackReqList(productList1);
			*/
			
			
			List<ReturnSubOrderBackReqVo> subList=new ArrayList<ReturnSubOrderBackReqVo>();
			//subList.add(returnSubOrderVo1);
			subList.add(returnSubOrderVo);
			
			
			
			
			
			
			vo.setReturnSubOrderBackReqList(subList);
			ResultVo resultVo = service.returnMarketOrderBack(vo);
			
			System.out.println(JSON.toJSONString(resultVo));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
