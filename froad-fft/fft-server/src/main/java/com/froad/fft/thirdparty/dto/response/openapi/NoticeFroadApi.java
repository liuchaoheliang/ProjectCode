package com.froad.fft.thirdparty.dto.response.openapi;

public class NoticeFroadApi {

	private Order order;
	
	private System system;

	private Pay pay;
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public System getSystem() {
		return system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

	public Pay getPay() {
		return pay;
	}

	public void setPay(Pay pay) {
		this.pay = pay;
	}
	
	
}
