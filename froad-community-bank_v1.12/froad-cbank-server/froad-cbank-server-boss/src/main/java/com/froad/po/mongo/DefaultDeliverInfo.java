package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class DefaultDeliverInfo  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5938926813727194686L;
	
	public DefaultDeliverInfo(){
		super();
	}
	
	public DefaultDeliverInfo(String deliveryId, String consignee, String phone) {
		super();
		this.deliveryId = deliveryId;
		this.consignee = consignee;
		this.phone = phone;
	}
	
	private String deliveryId;
	private String consignee;
	private String phone;
	
	@JSONField(name = "delivery_id", serialize = true, deserialize = true)
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	
	@JSONField(name = "consignee", serialize = true, deserialize = true)
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@JSONField(name = "phone", serialize = true, deserialize = true)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
