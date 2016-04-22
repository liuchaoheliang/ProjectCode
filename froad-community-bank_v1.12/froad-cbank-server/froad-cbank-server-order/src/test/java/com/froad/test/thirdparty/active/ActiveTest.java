package com.froad.test.thirdparty.active;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.common.beans.RefundOrderBean;
import com.froad.common.beans.ResultBean;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.active.impl.ActiveFuncImpl;
import com.froad.util.PropertiesUtil;

public class ActiveTest {

	 
	private static ActiveFunc activeFunc = new ActiveFuncImpl();
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		System.out.println("load properties finish");
		
//		test1();
		
//		test2();
		
//		test3();
		
		test4();
	}
	
	private static void test1(){
		OrderMongo order = new OrderMongo();
		order.setIsActive("1");
		order.setClientId("0007");
		order.setOrderId("0007");
		order.setMarketId("1");
		order.setMemberCode(7L);
		order.setPaymentTime(new Date().getTime());
		
//		ResultBean result = activeFunc.noticeMarketingPlatformPaySuccess(order);
		
//		System.out.println("resultCode=" + result.getCode() + ",resultMsg=" + result.getMsg());
	}
	
	private static void test2(){ 
		RefundHistory refundHis = new RefundHistory();
		
		refundHis.setClientId("chongqing");
		refundHis.setOrderId("0007");
		
		List<RefundShoppingInfo> rlist = new ArrayList<RefundShoppingInfo>();
		RefundShoppingInfo rinfo = new RefundShoppingInfo();
		rinfo.setSubOrderId("000701");
		
		List<RefundProduct> rdList = new ArrayList<RefundProduct>();
		RefundProduct rd = new RefundProduct();
		rd.setActiveId("chongqing-MJ-2015-002");
		rd.setProductId("A01");
		rd.setQuantity(1);
		rd.setVipQuantity(1);
		rdList.add(rd);
		
		
		RefundProduct rd2 = new RefundProduct();
		rd2.setActiveId("chongqing-MJ-2015-002");
		rd2.setProductId("A03");
		rd2.setQuantity(1);
		rd2.setVipQuantity(1);
		rdList.add(rd2);
		
		rinfo.setProducts(rdList);
		
		rlist.add(rinfo);
		
		refundHis.setShoppingInfo(rlist);
		
		ResultBean result = activeFunc.noticeMarketingPlatformRefund(refundHis,true);
		System.out.println("resultCode=" + result.getCode() + ",resultMsg=" + result.getMsg());
	}
	
	//退出完成
	private static void test3(){ 
		RefundHistory refundHis = new RefundHistory();
		
		refundHis.setMemberCode("0007");
		refundHis.setClientId("0007");
		refundHis.setOrderId("0007");
		
		List<RefundShoppingInfo> rlist = new ArrayList<RefundShoppingInfo>();
		RefundShoppingInfo rinfo = new RefundShoppingInfo();
		rinfo.setSubOrderId("000701");
		
		List<RefundProduct> rdList = new ArrayList<RefundProduct>();
		RefundProduct rd = new RefundProduct();
		rd.setActiveId("0007");
		rd.setProductId("A01");
		rd.setQuantity(4);
		rd.setVipQuantity(0);
		
		rdList.add(rd);
		
		rinfo.setProducts(rdList);
		
		rlist.add(rinfo);
		
		refundHis.setShoppingInfo(rlist);
		
		ResultBean result = activeFunc.noticeMarketingPlatformRefundFinish(refundHis);
		System.out.println("resultCode=" + result.getCode() + ",resultMsg=" + result.getMsg());
	}
	
	/**退款失败
	 */
	private static void test4(){ 
		RefundHistory refundHis = new RefundHistory();
		
		refundHis.setClientId("chongqing");
		refundHis.setOrderId("0007");
		
		List<RefundShoppingInfo> rlist = new ArrayList<RefundShoppingInfo>();
		RefundShoppingInfo rinfo = new RefundShoppingInfo();
		rinfo.setSubOrderId("000701");
		
		List<RefundProduct> rdList = new ArrayList<RefundProduct>();
		RefundProduct rd = new RefundProduct();
		rd.setActiveId("chongqing-MJ-2015-002");
		rd.setProductId("A01");
		rd.setDiscountQuantity(1);
		rd.setDiscountVipQuantity(1);
		
		rdList.add(rd);
		
		
		RefundProduct rd2 = new RefundProduct();
		rd2.setActiveId("chongqing-MJ-2015-002");
		rd2.setProductId("A03");
		rd2.setDiscountQuantity(1);
		rd2.setDiscountVipQuantity(1);
		
		rdList.add(rd2);
		
		rinfo.setProducts(rdList);
		
		rlist.add(rinfo);
		
		refundHis.setShoppingInfo(rlist);
		
		ResultBean result = activeFunc.noticeMarketingPlatformRefundFailure(refundHis);
		System.out.println("resultCode=" + result.getCode() + ",resultMsg=" + result.getMsg());
	}
}