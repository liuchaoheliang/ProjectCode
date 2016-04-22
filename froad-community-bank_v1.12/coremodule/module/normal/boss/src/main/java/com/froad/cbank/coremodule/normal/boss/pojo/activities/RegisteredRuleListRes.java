package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 注册促销规则列表响应类
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 下午2:05:25
 */
public class RegisteredRuleListRes {
	private String clientId;//客户端ID
	private String clientName;//客户端名称
	private String activeId;//活动ID
	private Boolean triggerType;//触发方式（false–注册、true–首单交易）
	private String awardType;//奖励方式（1-满减、2-红包、3-实物）
	private Boolean isAwardCre;//是否奖励推荐人（false-否-不奖励、true-是-奖励）
	
	private String status;//活动状态（0-待提交、1-审核中、2-审核不通过、3-启用、4-禁用）
	private String activeName;//活动名称
	private Long expireStartTime;//有效期开始时间
	private Long expireEndTime;//有效期结束时间
	private String description;//活动描述
	
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
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public Boolean getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(Boolean triggerType) {
		this.triggerType = triggerType;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public Boolean getIsAwardCre() {
		return isAwardCre;
	}
	public void setIsAwardCre(Boolean isAwardCre) {
		this.isAwardCre = isAwardCre;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Long getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(Long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public Long getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
