package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

public class Update_Product_Part_Req extends BasePojo{
	
	@NotEmpty(value="商品ID不能为空")
	private String productId;
	/**
	 * 商品库存数量
	 */
	@NotEmpty(value="商品库存不能为空")
	private Integer store; 
	/**
	 * 商品审核状态
	 */
	private String auditState; 
	/**
	 * 团购或预售有效期结束
	 */
	@NotEmpty(value="结束时间不能为空")
	private String endTime; 
	/**
	 * 团购或预售券有效结束日
	 */
	@NotEmpty(value="有效期结束时间不能为空")
	private String expireEndTime; 


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(String expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	
}
