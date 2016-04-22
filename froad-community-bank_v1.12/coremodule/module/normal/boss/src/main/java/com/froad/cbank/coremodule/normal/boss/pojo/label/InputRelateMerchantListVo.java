package com.froad.cbank.coremodule.normal.boss.pojo.label;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月28日 下午3:04:46
 * @desc 用于临时存放读取excel时的数据
 */
public class InputRelateMerchantListVo {
	private String merchantId; // optional
	  /**
	   * 权重 *
	   */
	private String weight; // optional
	  /**
	   * 活动编号 *
	   */
	private String activityNo; // optional
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	
	
}
