/**
 * Project Name:coremodule-user
 * File Name:SubOrderSimplePojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年8月26日下午4:02:28
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * ClassName:SubOrderSimplePojo
 * Reason:	 子订单详情POJO
 * Date:     2015年8月26日 下午4:02:28
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class SubOrderSimplePojo {
	  /**
	   * 订单ID
	   */
	  public String orderId; // required
	  /**
	   * 子订单ID
	   */
	  public String subOrderId; // required
	  /**
	   * 支付时间
	   */
	  public long paymentTime; // required
	  
	  
	  /**
	 * productList:子订单关联的商品信息
	 */
	public List<OrderProductPojo> productList;


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getSubOrderId() {
		return subOrderId;
	}


	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}


	public long getPaymentTime() {
		return paymentTime;
	}


	public void setPaymentTime(long paymentTime) {
		this.paymentTime = paymentTime;
	}


	public List<OrderProductPojo> getProductList() {
		return productList;
	}


	public void setProductList(List<OrderProductPojo> productList) {
		this.productList = productList;
	}
	  
	  
	  
	  
	  
}
