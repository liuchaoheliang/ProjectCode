package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;

public class DefaultRecvInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 792893904460210361L;
	
	public DefaultRecvInfo(){
		super();
	}
	
	public DefaultRecvInfo(String recvId, String consignee, String address,
			String phone) {
		super();
		this.recvId = recvId;
		this.consignee = consignee;
		this.address = address;
		this.phone = phone;
	}
	private String recvId;
	private String consignee;
	private String address;
	private String phone;
	
	@JSONField(name = "recv_id", serialize = true, deserialize = true)
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	
	@JSONField(name = "consignee", serialize = true, deserialize = true)
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	@JSONField(name = "address", serialize = true, deserialize = true)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@JSONField(name = "phone", serialize = true, deserialize = true)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
