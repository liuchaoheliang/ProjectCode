package com.froad.fft.dto;

import com.froad.fft.enums.FriendLinkType;

import java.io.Serializable;
import java.util.Date;

/**
 * 友情链接
 * @author FQ
 *
 */
public class FriendLinkDto implements Serializable{
	


	private Long id;
	private Date createTime;
	private String name;//名称
	private FriendLinkType type;//类型
	private String logo;//logo
	private String url;//链接地址
	private Integer orderValue;//排序
	private Long clientId;//所属客户端
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
