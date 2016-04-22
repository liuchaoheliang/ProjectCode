package com.froad.cbank.coremodule.normal.boss.pojo.label;

/**
 * 门店推荐活动详情
 * @author yfy
 * @date 2015年10月22日  下午16:11:11
 */
public class OutletRecommendVo {
	
	/**
	 * 活动id
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 活动名称 
	 */
	private String activityName;
	/**
	 * 活动编号 
	 */
	private String activityNo;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 描述
	 */
	private String activityDesc;
	/**
	 * 状态: 启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5
	 */
	private String status;
	/**
	 * 活动类型: 商户活动1; 门店活动2; 商品活动3
	 */
	private String activityType;
	/**
	 * 活动Logo
	 */
	private String logoUrl;
	/**
	 * 创建时间 
	 */
	private Long createTime;
	
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
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityNo() {
		return activityNo;
	}
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
}
