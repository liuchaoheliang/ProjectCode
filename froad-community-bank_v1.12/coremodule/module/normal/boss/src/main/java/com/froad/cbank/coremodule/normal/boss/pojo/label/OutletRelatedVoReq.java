package com.froad.cbank.coremodule.normal.boss.pojo.label;

/**
 *  门店推荐活动关联的门店信息
 *  @author yfy
 *  @date 2015年10月22日  下午16:12:31
 *
 */
public class OutletRelatedVoReq {
	
	/**
	 * 门店关联ID
	 */
	private Long id;
	/**
	 * 客户端
	 */
	private String clientId;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 活动编号
	 */
	private String activityNo;
	/**
	 * 门店ID
	 */
	private String outletId;
	/**
	 * 门店名称
	 */
	private String outletName;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 地区
	 */
	private String areaName;
	/**
	 * 权重
	 */
	private String weight;
	/**
	 * 状态 启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5
	 */
	private String status;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
}
