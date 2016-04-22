package com.froad.cbank.coremodule.normal.boss.pojo.report;


/**
 * 会员注册分析报表 res
 * @author liaopeixin
 *	@date 2015年11月3日 上午10:48:27
 */
public class MemberReportRes {

	private String createTime;
	//(渠道)
	private String type;
	//(用户渠道数量)
	private Integer userResourceCount;
	//用户注册数
	private Integer userCount;
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	
	
}
