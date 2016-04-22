package com.froad.CB.po.user;
/**
 * *******************************************************
 *<p> 工程: communityBusiness </p>
 *<p> 类名: UserAppend.java </p>
 *<p> 描述: *-- <b>User信息分分通库追加属性字段</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2013-9-25 上午09:43:15 </p>
 ********************************************************
 */
public class UserAppend {
	
	
	private Integer id;  //
	private String userID;	//关联账户账务平台用户基表userID
	private String isPointActivateLock;	// 激活积分 1-未锁定 0-锁定
	private Integer pointActivateFailureTimes;	//激活积分连续错误次数
	private String pointActivateLockTime;	//激活积分锁定时间
	private String pointActivateTryTime;	//激活积分尝试时间
	private String firstLogin;	//是否第一次登录系统 0-第一次 1-已经登录过
	private String bank;	//相关银行
	private String activateCode;	//活码激
	private String blance;	//	用户余额
	private String state;	//状态
	private String create_time;	//创建时间
	private String update_time;	//更新时间
	private String remark;	//备注


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getIsPointActivateLock() {
		return isPointActivateLock;
	}
	public void setIsPointActivateLock(String isPointActivateLock) {
		this.isPointActivateLock = isPointActivateLock;
	}
	public Integer getPointActivateFailureTimes() {
		return pointActivateFailureTimes;
	}
	public void setPointActivateFailureTimes(Integer pointActivateFailureTimes) {
		this.pointActivateFailureTimes = pointActivateFailureTimes;
	}
	public String getPointActivateLockTime() {
		return pointActivateLockTime;
	}
	public void setPointActivateLockTime(String pointActivateLockTime) {
		this.pointActivateLockTime = pointActivateLockTime;
	}
	public String getPointActivateTryTime() {
		return pointActivateTryTime;
	}
	public void setPointActivateTryTime(String pointActivateTryTime) {
		this.pointActivateTryTime = pointActivateTryTime;
	}
	public String getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getActivateCode() {
		return activateCode;
	}
	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}
	public String getBlance() {
		return blance;
	}
	public void setBlance(String blance) {
		this.blance = blance;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
