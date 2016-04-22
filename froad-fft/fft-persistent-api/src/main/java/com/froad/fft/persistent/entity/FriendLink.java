package com.froad.fft.persistent.entity;

import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.FriendLinkType;

/**
 * 友情链接
 * @author FQ
 *
 */
public class FriendLink extends BaseEntity {

	private String name;//名称
	private FriendLinkType type;//类型
	private String logo;//logo
	private String url;//链接地址
	private Integer orderValue;//排序
	private Long clientId;//所属客户端
	
	private DataState dataState;//数据状态：有效、删除
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FriendLinkType getType() {
		return type;
	}
	public void setType(FriendLinkType type) {
		this.type = type;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public DataState getDataState() {
		return dataState;
	}
	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	
}
