package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class RecvInfo  implements java.io.Serializable {
	

	

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2748710964152590010L;



	public RecvInfo(){
		super();
	}
	
	public RecvInfo(String recvId, String consignee, String address,
			String phone,String isDefault,Long areaId) {
		super();
		this.recvId = recvId;
		this.consignee = consignee;
		this.address = address;
		this.phone = phone;
		this.isDefault=isDefault;
		this.areaId=areaId;
	}
	@JSONField(name = "consignee", serialize = true, deserialize = true)
	private String consignee;
	@JSONField(name = "address", serialize = true, deserialize = true)
	private String address;
	@JSONField(name = "phone", serialize = true, deserialize = true)
	private String phone;
	@JSONField(name = "isdefault", serialize = true, deserialize = true)
	private String isDefault;
	@JSONField(name = "recv_id", serialize = true, deserialize = true)
	private String recvId;
	
	@JSONField(name = "area_id", serialize = true, deserialize = true)
	private Long areaId;
	//0-无效，1-有效
	@JSONField(name = "isenable", serialize = true, deserialize = true)
	private String isEnable;
	//2-提货，1-常用
	@JSONField(name = "type", serialize = true, deserialize = true)
	private String type;
	
	@JSONField(name = "createtime", serialize = true, deserialize = true)
	private long creatTime;
	@JSONField(name = "isdeliverdeault", serialize = true, deserialize = true)
	private String isDeliverDefault;
	
	
	
	public String getIsDeliverDefault() {
		return isDeliverDefault;
	}

	public void setIsDeliverDefault(String isDeliverDefault) {
		this.isDeliverDefault = isDeliverDefault;
	}

	public long getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
}
