package com.froad.log.vo;
/**
 * 类描述：商品日志实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:15:15 
 */
public class ProductLog extends Head {
	private ProductDetailLog data;

	public ProductDetailLog getData() {
		return data;
	}

	public void setData(ProductDetailLog data) {
		this.data = data;
	}


	
}
