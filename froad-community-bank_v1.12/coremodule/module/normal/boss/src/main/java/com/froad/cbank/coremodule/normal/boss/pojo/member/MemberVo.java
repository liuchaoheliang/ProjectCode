package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

/**
 * 会员信息修改提交实体
 * @author: yfy
 */
public class MemberVo implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private Long memberCode;		//会员号
	private String mobile;		    //电话号码
	private Integer userState;		//用户状态1正常状态(已激活)，2.未激活，3已锁定,4已禁用
	private String action;          //操作信息
	private String actionCode;      //操作执行语句 {mobile:****, status:*}
	private String demo;            //操作备注
	private String creater;         //操作人
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
