package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class DeliverInfo  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4224066258335793556L;
	
	public DeliverInfo(){
		super();
	}
	
	public DeliverInfo(String deliverId, String consignee, String phone) {
		super();
		this.deliveryId = deliverId;
		this.consignee = consignee;
		this.phone = phone;
	}
	
	private String deliveryId;
	private String consignee;
	private String phone;
	
	@JSONField(name = "delivery_id")
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliverId(String deliverId) {
		this.deliveryId = deliverId;
	}
	
	@JSONField(name = "consignee")
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@JSONField(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
