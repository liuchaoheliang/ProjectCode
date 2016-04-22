package com.froad.log.vo;
/**
 * 类描述：支付日志实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:24:19 
 */
public class PaymentLog extends Head {
	private PaymentDetailLog data;

	public PaymentDetailLog getData() {
		return data;
	}

	public void setData(PaymentDetailLog data) {
		this.data = data;
	}


	
}
