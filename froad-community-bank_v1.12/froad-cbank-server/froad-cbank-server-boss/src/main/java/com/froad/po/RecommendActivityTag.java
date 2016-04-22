package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class RecommendActivityTag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// id
	private Long id;
	
	// 客户端号
	private String clientId;
	
	// 活动名称
	private String activityName;
	
	// 活动编号
	private String activityNo;
	
	// 操作人
	private String operator;
	
	// 描述
	private String activityDesc;
	
	// 状态: 启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5
	private String status;
	
	// 活动类型: 商户活动1; 门店活动2; 商品活动3
	private String activityType;
	
	// 活动logo_url
	private String logoUrl;
	
	// 创建时间
	private Date createTime;
	
	// 更新时间
	private Date updateTime;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
