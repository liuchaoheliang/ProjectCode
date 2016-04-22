package com.froad;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.active.CheckOrderReqVo;
import com.froad.thrift.vo.active.CheckOrderResVo;
import com.froad.thrift.vo.active.CreateMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateResultVo;
import com.froad.thrift.vo.active.MarketSubOrderProductVo;
import com.froad.thrift.vo.active.MarketSubOrderVo;
import com.froad.thrift.vo.active.OrderProductVo;
import com.froad.thrift.vo.active.ShoppingCartReqProductVo;
import com.froad.thrift.vo.active.ShoppingCartReqVo;
import com.froad.thrift.vo.active.ShoppingCartResVo;

public class ActiveRunClient {

	public static void main(String[] args){
		try{
			TSocket transport = null;
//			transport = new TSocket("10.24.248.187", 16501);
//			transport = new TSocket("127.0.0.1", 16501);
			transport = new TSocket("10.24.248.188", 16501);
//			transport = new TSocket("10.43.1.133", 16501);
//			transport = new TSocket("10.43.1.131", 9102);
//			transport = new TSocket("10.43.1.123", 16501);
//			transport = new TSocket("10.43.1.121", 9102);
//			transport = new TSocket("10.43.1.101", 9102);
//			transport = new TSocket("10.43.1.9", 16501);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ActiveRunService");
			ActiveRunService.Client service = new ActiveRunService.Client(mp);
			
			transport.open();
			
//			ResultVo resultVo = service.updateMarketOrder(null);
//			System.out.println(JSON.toJSONString(resultVo));
			
			// 进入购物车
//			goShoppingCart(service);
			
			// 创建订单
//			createOrder(service);
			
			// 检查订单
			checkOrder(service);
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 检查订单
	public static void checkOrder(ActiveRunService.Client service) throws Exception{
			
		CheckOrderReqVo checkOrderReqVo = new CheckOrderReqVo();
			
		checkOrderReqVo.setReqId("1448D7A40000");
		checkOrderReqVo.setClientId("chongqing");
		checkOrderReqVo.setMemberCode(50000362098l);
		checkOrderReqVo.setOrderMoney(98.0);
		
		List<OrderProductVo> orderProductList = new ArrayList<OrderProductVo>();
		OrderProductVo product1 = new OrderProductVo();
		product1.setProductId("0D820C618000");
		product1.setProductName("金丝楠木手串2.0");
		product1.setActiveId("chongqing-MJ-2015-012");
		product1.setGeneralPrice(98.0);
		product1.setGeneralCount(1);
		product1.setVipPrice(0.0);
		product1.setVipCount(0);
		orderProductList.add(product1);
		checkOrderReqVo.setOrderProductList(orderProductList);
		checkOrderReqVo.setIsF2FOrder(false);
		List<String> vouchersIds = new ArrayList<String>();
		vouchersIds.add("sad81nkxs");
		checkOrderReqVo.setVouchersIds(vouchersIds);
		
		CheckOrderResVo checkOrderResVo = service.checkOrder(checkOrderReqVo);
		System.out.println("订单检查结果:\n"+JSON.toJSONString(checkOrderResVo));
	}
	
	// 创建订单
	public static void createOrder(ActiveRunService.Client service) throws Exception{
		CreateMarketOrderReqVo createMarketOrderReqVo = new CreateMarketOrderReqVo();
		createMarketOrderReqVo.setReqId("1448D7A40000");
		createMarketOrderReqVo.setClientId("chongqing");
		createMarketOrderReqVo.setMemberCode(50000362098l);
		createMarketOrderReqVo.setPhone("13345678901");
		createMarketOrderReqVo.setOrderId("0D821DA18005");
		createMarketOrderReqVo.setOrderOriMoney(98.0);
		createMarketOrderReqVo.setOrderMoney(98.0);
		
		List<MarketSubOrderVo> marketSubOrderList = new ArrayList<MarketSubOrderVo>();
		// 子订单A
		MarketSubOrderVo subOrder1 = new MarketSubOrderVo();
		// 子订单A - 编号
		subOrder1.setSubOrderId("0D821DA18002");
		// 子订单A - 商品列表
		List<MarketSubOrderProductVo> marketSubOrderProductList1 = new ArrayList<MarketSubOrderProductVo>();
		// 子订单A - 商品列表 - 商品A
		MarketSubOrderProductVo product1 = new MarketSubOrderProductVo();
		product1.setActiveId("chongqing-MJ-2015-012");
		product1.setProductId("0D820C618000");
		product1.setProductName("金丝楠木手串2.0");
		product1.setGeneralPrice(98.0);
		product1.setGeneralCount(1);
		product1.setVipPrice(0.0);
		product1.setVipCount(0);
		marketSubOrderProductList1.add(product1);
		subOrder1.setMarketSubOrderProductList(marketSubOrderProductList1);
		marketSubOrderList.add(subOrder1);
		createMarketOrderReqVo.setMarketSubOrderList(marketSubOrderList);
		List<String> vouchersIds = new ArrayList<String>();
		vouchersIds.add("UIWUE006");
		createMarketOrderReqVo.setVouchersIds(vouchersIds);
		CreateResultVo createResultVo = service.createMarketOrder(createMarketOrderReqVo);
		System.out.println("订单创建结果:\n"+JSON.toJSONString(createResultVo));
	}
	
	// 进入购物车
	public static void goShoppingCart(ActiveRunService.Client service) throws Exception{
		
		ShoppingCartReqVo shoppingCartReqVo = new ShoppingCartReqVo();
		shoppingCartReqVo.setReqId("1234567890");
		shoppingCartReqVo.setClientId("chongqing");
		shoppingCartReqVo.setMemberCode(52001976226l);
		List<ShoppingCartReqProductVo> shoppingCartReqProductList = new ArrayList<ShoppingCartReqProductVo>();
		ShoppingCartReqProductVo scp1 = new ShoppingCartReqProductVo();
		scp1.setProductId("0D821AD18004");
		scp1.setProductTotalMoney(200.0);
		scp1.setVipTotalMoney(0);
		ShoppingCartReqProductVo scp2 = new ShoppingCartReqProductVo();
		scp2.setProductId("0D820CC18001");
		scp2.setProductTotalMoney(220.0);
		scp2.setVipTotalMoney(0);
		ShoppingCartReqProductVo scp3 = new ShoppingCartReqProductVo();
		scp3.setProductId("0D821AD18005");
		scp3.setProductTotalMoney(19.6);
		scp3.setVipTotalMoney(0);
		shoppingCartReqProductList.add(scp1);
		shoppingCartReqProductList.add(scp2);
		shoppingCartReqProductList.add(scp3);
		shoppingCartReqVo.setShoppingCartReqProductList(shoppingCartReqProductList);
		
		ShoppingCartResVo shoppingCartResVo = service.goShoppingCart(shoppingCartReqVo);
		System.out.println("购物车响应:\n"+JSON.toJSONString(shoppingCartResVo));
	}
}
