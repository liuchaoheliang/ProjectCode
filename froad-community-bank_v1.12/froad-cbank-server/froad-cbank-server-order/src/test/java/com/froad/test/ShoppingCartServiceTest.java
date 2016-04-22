package com.froad.test;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoReq;


public class ShoppingCartServiceTest {

	
	public static void main(String[] args) throws TException {

		// 数据转换
		ShoppingCartVoReq cart = new ShoppingCartVoReq();
		cart.setMemberCode(100000l);
		cart.setClientId("1000000000");
		cart.setMerchantId("1000001");
		cart.setNum(4);
		cart.setProductId("1550000");
		cart.setOutletId("1000000");
		cart.setProductIdIsSet(true);
		cart.setVipLevel(1);
		
		// 调用Thrift服务
		TSocket transport = new TSocket("localhost", 8899);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);

		TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"shoppingCartService");
		ShoppingCartService.Client service1 = new ShoppingCartService.Client(mp1);
		transport.open();
		// 添加购物车
//		System.out.println(service1.addCart(cart));
//		System.out.println(service1.getCartCount(100000l, 1000000000l));
//		System.out.println(service1.deleteCartByProductId(100000, 1000000000, 1000001, 1550002, 5));
//		System.out.println(service1.deleteCart(100000, 1000000000));
//		System.out.println(service1.getCartByProductId(100000l, 1000000000l, "1000001", "1550000", 1));
		
		System.out.println(service1.getCart(100000l, "1000000000", 1));
		transport.close();
		
		
	}
}

