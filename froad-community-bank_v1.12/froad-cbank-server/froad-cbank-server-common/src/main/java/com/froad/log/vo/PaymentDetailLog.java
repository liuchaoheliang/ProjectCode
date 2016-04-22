package com.froad.log.vo;

import java.util.List;

/**
 * 类描述：支付DATA数据类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:25:01 
 */
public class PaymentDetailLog {
	
	private String order_id;
	
	private List<SubOrders> sub_orders;
	
	private List<Payments> payments;

	public List<SubOrders> getSub_orders() {
		return sub_orders;
	}

	public void setSub_orders(List<SubOrders> sub_orders) {
		this.sub_orders = sub_orders;
	}

	public List<Payments> getPayments() {
		return payments;
	}

	public void setPayments(List<Payments> payments) {
		this.payments = payments;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
	
}
