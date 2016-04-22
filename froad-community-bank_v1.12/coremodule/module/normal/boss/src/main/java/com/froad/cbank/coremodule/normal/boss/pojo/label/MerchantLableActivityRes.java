package com.froad.cbank.coremodule.normal.boss.pojo.label;

import java.io.Serializable;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月22日 下午5:16:31
 * @desc 商户活动列表res
 */
public class MerchantLableActivityRes implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 7292639772061030055L;
	/**
	   * id *
	   */
	private long id; // optional
	  /**
	   * 客户端ID *
	   */
	private String clientId; // optional
	  /**
	   * 活动名称 *
	   */
	private String activityName; // optional
	  /**
	   * 活动编号 *
	   */
	private String activityNo; // optional
	  /**
	   * 操作人 *
	   */
	private String operator; // optional
	  /**
	   * 描述*
	   */
	private String activityDesc; // optional
	  /**
	   * 状态: 启1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5 *
	   */
	private String status; // optional
	  /**
	   * 活动类型: 商户活动1; 门店活动2; 商品活动3 *
	   */
	private String activityType; // optional
	  /**
	   * 活动logo_url *
	   */
	private String logoUrl; // optional
	  /**
	   * 创建时间 *
	   */
	private String createTime; // optional
	  /**
	   * 更新时间 *
	   */
	private String updateTime; // optional
	/**
	 * 客户端名称
	 */
	private String clientName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	
}
