package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 红包规则列表查询返回结果实体类
 * @author yfy
 */
public class VouchersRuleListRes {
	/**
	 * 主键ID
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
	 * 规则编号
	 */
	private String activeId;
	/**
	 * 规则名称
	 */
	private String activeName;
	/**
	 * 有效期开始时间
	 */
	private Long expireStartTime;
	/**
	 * 有效期结束时间
	 */
	private Long expireEndTime;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 红包最小金额
	 */
	private String minMoney;
	/**
	 * 红包最大金额
	 */
	private String maxMoney;
	/**
	 * 红包总额
	 */
	private String totalMoney;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 活动范围0-不限制 1-限制商户 2-限制门店(预留) 3-限制商品
	 */
	private String limitType;
	/**
	 * 状态0-待提交1-审核中2-审核不通过3-启动 4-禁止
	 */
	private String status;
	
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
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}
	public String getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
