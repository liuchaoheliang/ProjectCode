package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;

public class DeliverInfo  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4224066258335793556L;
	
	public DeliverInfo(){
		super();
	}
	
	public DeliverInfo(Long deliverId, String consignee, String phone) {
		super();
		this.deliverId = deliverId;
		this.consignee = consignee;
		this.phone = phone;
	}
	
	private Long deliverId;
	private String consignee;
	private String phone;
	
	@JSONField(name = "deliver_id")
	public Long getDeliverId() {
		return deliverId;
	}
	public void setDeliverId(Long deliverId) {
		this.deliverId = deliverId;
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
