package com.froad.db.chongqing.entity;

import com.froad.cbank.persistent.entity.Order;
import com.froad.cbank.persistent.entity.OrderDetails;

public class OrderEx extends Order {

	private OrderDetails detail;

	public OrderDetails getDetail() {
		return detail;
	}

	public void setDetail(OrderDetails detail) {
		this.detail = detail;
	}
	
}

