package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Face_Group_Req extends BasePojo {
	/**
	 * 类型
	 */
	private String type; 
	/**
	 * 状态
	 */
	private String status; 
	/**
	 * 门店id
	 */
	private String outletId; 
	/**
	 * 提货状态
	 */
	private String deliveryStatus; 
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
