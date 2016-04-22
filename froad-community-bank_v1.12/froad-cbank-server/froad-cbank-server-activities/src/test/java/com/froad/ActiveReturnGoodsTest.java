package com.froad;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.active.ReturnMarketOrderReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderResVo;
import com.froad.thrift.vo.active.ReturnSubOrderProductReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderReqVo;

public class ActiveReturnGoodsTest {

	//private static final String "0007" = null;

	public static void main(String[] args){
		try{
			
			TSocket transport = new TSocket("127.0.0.1", 16501);
			//TSocket transport = new TSocket("10.24.248.188", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			
			ReturnMarketOrderReqVo vo=new ReturnMarketOrderReqVo();
			
			vo.setClientId("chongqing");
			vo.setReqId("00001");
			vo.setOrderId("0007");
			vo.setIsQuery(true);
			
			//0.07*1000

			List<ReturnSubOrderProductReqVo> productList=new ArrayList<ReturnSubOrderProductReqVo>();
			ReturnSubOrderProductReqVo productVo=new ReturnSubOrderProductReqVo();
			productVo.setNormalCount(1);
			productVo.setVipCount(0);
			productVo.setProductId("A01");
			productList.add(productVo);
			

			ReturnSubOrderReqVo returnSubOrderVo =new ReturnSubOrderReqVo();
			returnSubOrderVo.setSubOrderId("000701");
			returnSubOrderVo.setReturnSubOrderProductReqList(productList);
			
		
			/*
			ReturnSubOrderReqVo returnSubOrderVo1 =new ReturnSubOrderReqVo();
			returnSubOrderVo1.setSubOrderId("000702");
			List<ReturnSubOrderProductReqVo> productList1=new ArrayList<ReturnSubOrderProductReqVo>();
			ReturnSubOrderProductReqVo productVo1=new ReturnSubOrderProductReqVo();
			productVo1.setNormalCount(1);
			productVo1.setProductId("A02");
			productVo1.setVipCount(1);
			productList1.add(productVo1);
			
			returnSubOrderVo1.setReturnSubOrderProductReqList(productList1);
			*/
			
			List<ReturnSubOrderReqVo> subList=new ArrayList<ReturnSubOrderReqVo>();
			//subList.add(returnSubOrderVo1);
			subList.add(returnSubOrderVo);
			
			
			
			
			
			
			vo.setReturnSubOrderReqList(subList);
			
			
			
			ReturnMarketOrderResVo resultVo = service.returnMarketOrder(vo);
//			System.out.println(JSON.toJSONString(resultVo.getResultVo()));
			System.out.println(JSON.toJSONString(resultVo.getReturnSubOrderResList().get(0)));
			//System.out.println(JSON.toJSONString(resultVo.getReturnSubOrderResList().get(1)));
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
