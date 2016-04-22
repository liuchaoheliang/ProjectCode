package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 营销活动标签关联响应类
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 上午11:16:39
 */
public class ActiveTagRelationRes {
	private String clientId;//客户端ID
	private Long createTime;//创建时间
	private Long updateTime;//更新时间
	private String activeId;//活动ID
	private String itemType;//类型（0–商户、1–门店、2–商品）
	private String itemId;//商户/门店/商品标签ID
	
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
