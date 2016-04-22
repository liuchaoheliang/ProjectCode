package com.froad.cbank.coremodule.module.normal.merchant.pojo;

public class Query_Outlet_Comment_Req extends BasePojo {

	/**
	 * 门店名称
	 */
	private String outletName; 

	/**
	 * 星级
	 */
	private Integer starLevel; 

	/**
	 * 门店名称
	 */
	private String merchantName; 

	/**
	 * 开始时间
	 */
	private String begTime; 
	/**
	 * 接收时间
	 */
	private String endTime; 
	
	private String isReply;

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getBegTime() {
		return begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Integer getStarLevel() {
		return starLevel;
	}

	public void setStarLevel(Integer starLevel) {
		this.starLevel = starLevel;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
