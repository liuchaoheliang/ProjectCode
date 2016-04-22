package com.froad.cbank.coremodule.normal.boss.pojo.activities;

public class SustainActiveRelationVo {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	/**
	 * 开始时间
	 */
	private Long beginTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 *  活动ID
	 */
	private String activeId;
	/**
	 * 支持活动类型
	 */
	private String sustainActiveType;
	/**
	 * 支持活动ID
	 */
	private String sustainActiveId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 客户端名称
	 */
	private String clientName;
	
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getSustainActiveType() {
		return sustainActiveType;
	}
	public void setSustainActiveType(String sustainActiveType) {
		this.sustainActiveType = sustainActiveType;
	}
	public String getSustainActiveId() {
		return sustainActiveId;
	}
	public void setSustainActiveId(String sustainActiveId) {
		this.sustainActiveId = sustainActiveId;
	}
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
