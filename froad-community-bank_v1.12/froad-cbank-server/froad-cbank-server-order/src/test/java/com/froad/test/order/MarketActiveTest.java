package com.froad.test.order;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.PropertiesUtil;

/**
 * 测试满减活动
 * @author open
 *
 */
public class MarketActiveTest {
	
	
	public void checkOrderForMarketActiveTest(){
		OrderLogicImpl logic = new OrderLogicImpl();
		ResultBean result = logic.getOrderByOrderId("chongqing", "0D821DA18005");
		System.out.println(JSON.toJSONString(result));
		OrderMongo order = (OrderMongo) result.getData();
		ResultBean result2 =  logic.getSubOrderListByOrderId("chongqing", "0D821DA18005");
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) result2.getData();
		subOrderList.get(0).getProducts().get(0).setActiveId("chongqing-MJ-2015-002");
		System.out.println(JSON.toJSONString(subOrderList));
		logic.checkOrderForMarketActive(order, subOrderList);
	}
	
	public void createOrderFailureGoBackForMarketActiveTest(){
		OrderLogicImpl logic = new OrderLogicImpl();
		ResultBean result2 =  logic.getSubOrderListByOrderId("chongqing", "0D821DA18005");
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) result2.getData();
		subOrderList.get(0).getProducts().get(0).setActiveId("chongqing-MJ-2015-002");
		System.out.println(JSON.toJSONString(subOrderList));
//		logic.createOrderFailureGoBackForMarketActive(subOrderList, true);
	}
	
	public void createMarketOrderForMarketActiveTest(){
		OrderLogicImpl logic = new OrderLogicImpl();
		ResultBean result = logic.getOrderByOrderId("chongqing", "0D821DA18005");
		System.out.println(JSON.toJSONString(result));
		OrderMongo order = (OrderMongo) result.getData();
		ResultBean result2 =  logic.getSubOrderListByOrderId("chongqing", "0D821DA18005");
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) result2.getData();
		subOrderList.get(0).getProducts().get(0).setActiveId("chongqing-MJ-2015-002");
		System.out.println(JSON.toJSONString(subOrderList));
		logic.createMarketOrderForMarketActive(order,subOrderList);
		System.out.println("创建营销活动订单后，订单信息："+JSON.toJSONString(order));
	}
	
	public void closeMarketOrderTest(){
		OrderLogicImpl logic = new OrderLogicImpl();
		ResultBean result = logic.getOrderByOrderId("chongqing", "0D821DA18005");
		System.out.println(JSON.toJSONString(result));
		OrderMongo order = (OrderMongo) result.getData();
		ResultBean result2 =  logic.getSubOrderListByOrderId("chongqing", "0D821DA18005");
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) result2.getData();
		subOrderList.get(0).getProducts().get(0).setActiveId("chongqing-MJ-2015-002");
		System.out.println(JSON.toJSONString(subOrderList));
		logic.closeMarketOrder(order,true);
	}

	public static void main(String[] args) {
		PropertiesUtil.load();
		MarketActiveTest test = new MarketActiveTest();
//		test.checkOrderForMarketActiveTest();
		test.createOrderFailureGoBackForMarketActiveTest();
//		test.createMarketOrderForMarketActiveTest();
//		test.closeMarketOrderTest();
	}

}
