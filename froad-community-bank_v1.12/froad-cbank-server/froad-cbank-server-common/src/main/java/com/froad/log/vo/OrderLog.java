package com.froad.log.vo;
/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:04:26 
 */
public class OrderLog extends Head{
	private OrderDetailLog data;

	public OrderDetailLog getData() {
		return data;
	}

	public void setData(OrderDetailLog data) {
		this.data = data;
	}


	
}
