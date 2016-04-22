package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class RecvInfo  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 792893904460210361L;
	
	public RecvInfo(){
		super();
	}
	
	public RecvInfo(String recvId, String consignee, String address,
			String phone, Integer areaId) {
		super();
		this.recvId = recvId;
		this.consignee = consignee;
		this.address = address;
		this.phone = phone;
		this.areaId = areaId;
	}
	
	@JSONField(name = "recv_id", serialize = true, deserialize = true)
	private String recvId;
	
	@JSONField(name = "consignee", serialize = true, deserialize = true)
	private String consignee;
	
	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	
	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Integer areaId;
	
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
}
