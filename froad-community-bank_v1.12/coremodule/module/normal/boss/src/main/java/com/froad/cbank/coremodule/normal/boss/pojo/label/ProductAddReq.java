package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;

/**
 * 关联商品请求
 * 
 * @ClassName ProductAddReq
 * @author zxl
 * @date 2015年10月28日 下午2:43:01
 */
public class ProductAddReq {

	/**
	 * 商品id *
	 */
	@NotEmpty(value="商品id不能为空")
	private String productId; 
	/**
	 * 权重 *
	 */
	@NotEmpty(value="权重不能为空")
	private String weight; 
	/**
	 * 活动标签id *
	 */
	private long activityId;
	/**
	 * 活动编号 *
	 */
	@NotEmpty(value="活动编号不能为空")
	private String activityNo; 
	/**
	 * 客户端号 *
	 */
	private String clientId;
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	} 

}
