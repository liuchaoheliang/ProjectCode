package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 会员详情查询实体
 * @author: yfy
 */
public class MemberInfoVo implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;

	private Long   memberCode;		//会员号
	private String loginID;         //登录ID(用户名)
	private String uname;			//用户姓名
	private String sex ;       	 	//性别0：男 1:女性2：保密
	private Integer age ;         	//年龄
	private String mobile;		    //电话号码
	private String email;			//邮箱 
	private String identityType;	//证件类型
	private String identityTypeName;//证件类型名称
	private String identityKey;		//证件号       
	private String cardNo;          //绑定银行卡号
	private String createTime;      //注册时间
	private String updateTime;      //更新时间
	private String lastLoginTime;   //最近登录时间
	private Integer status;			//用户活动状态1正常状态(已激活)，2.未激活，3已锁定,4已禁用
	private String payMode;         //支付方式
	private List<HashMap<String,String>> identifyList;
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityTypeName() {
		return identityTypeName;
	}
	public void setIdentityTypeName(String identityTypeName) {
		this.identityTypeName = identityTypeName;
	}
	public String getIdentityKey() {
		return identityKey;
	}
	public void setIdentityKey(String identityKey) {
		this.identityKey = identityKey;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<HashMap<String, String>> getIdentifyList() {
		return identifyList;
	}
	public void setIdentifyList(List<HashMap<String, String>> identifyList) {
		this.identifyList = identifyList;
	}
	
}
