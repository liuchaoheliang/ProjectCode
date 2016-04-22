package com.froad.db.chonggou.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class DeliverInfo  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4224066258335793556L;
	
	public DeliverInfo(){
		super();
	}
	
	public DeliverInfo(String deliverId, String consignee, String phone,String isDefault,Long areaId) {
		super();
		this.deliveryId = deliverId;
		this.consignee = consignee;
		this.phone = phone;
		this.isDefault=isDefault;
		this.areaId=areaId;
	}
	@JSONField(name = "delivery_id", serialize = true, deserialize = true)
	private String deliveryId;
	@JSONField(name = "consignee", serialize = true, deserialize = true)
	private String consignee;
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	@JSONField(name = "isdefault", serialize = true, deserialize = true)
	private String isDefault;
	
	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Long areaId;
	
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliverId(String deliverId) {
		this.deliveryId = deliverId;
	}
	

	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	
}
