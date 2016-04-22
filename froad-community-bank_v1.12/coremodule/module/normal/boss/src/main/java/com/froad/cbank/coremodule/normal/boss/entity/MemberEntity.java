package com.froad.cbank.coremodule.normal.boss.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 会员注册分析报表 entity
 * @author liaopeixin
 *	@date 2015年11月3日 上午10:48:27
 */
@Alias("member")
public class MemberEntity {
	private String id;
	private String clientId;
	//(渠道)
	private String type;
	//(用户渠道数量)
	private Integer userResourceCount;
	//用户注册数
	private Integer userCount;
	//创建时间
	private Date CreateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getUserResourceCount() {
		return userResourceCount;
	}
	public void setUserResourceCount(Integer userResourceCount) {
		this.userResourceCount = userResourceCount;
	}
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	
	
	
}
