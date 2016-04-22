
	 /**
  * 文件名：ProductDeliveryMap.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.entity;


	/**
 * 类描述：预售商品和提货点关联表
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午8:03:48 
 */
public class ProductPresellDelivery extends BaseEntity{
	private Long productPresellId;//预售商品ID
	private Long presellDeliveryId;//预售提货点ID
	
	public Long getProductPresellId() {
		return productPresellId;
	}
	public void setProductPresellId(Long productPresellId) {
		this.productPresellId = productPresellId;
	}
	public Long getPresellDeliveryId() {
		return presellDeliveryId;
	}
	public void setPresellDeliveryId(Long presellDeliveryId) {
		this.presellDeliveryId = presellDeliveryId;
	}
	
}
