package com.froad.CB.po;

import java.io.Serializable;

import com.froad.CB.common.Pager;

/**
 * 类描述：临时积分表
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Mar 7, 2013 9:51:22 AM
 */
public class TempPoint extends Pager implements Serializable {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String accountName;

	private String accountNumber;

	private String pointValue;

	private String cardType;

	private String isActivate;

	private String state;

	private String createTime;

	private String updateTime;

	private String remark;
	
	private String phone;
	
	private String identificationCard;
	
	private String activateAccount;

	private String indentificationCardElse; //其他证件号码
	
//	private String isAccountLocked;//账号是否锁定
//	private Integer loginFailureCount;//连续登陆失败次数
//	private String lockedDate;//账号锁定日期
//	private String lastTryTime;//尝试登录的时间
	
	public String getActivateAccount() {
		return activateAccount;
	}

	public void setActivateAccount(String activateAccount) {
		this.activateAccount = activateAccount;
	}

	public String getIdentificationCard() {
		return identificationCard;
	}

	public void setIdentificationCard(String identificationCard) {
		this.identificationCard = identificationCard;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName == null ? null : accountName.trim();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber == null ? null : accountNumber
				.trim();
	}

	public String getPointValue() {
		return pointValue;
	}

	public void setPointValue(String pointValue) {
		this.pointValue = pointValue == null ? null : pointValue.trim();
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType == null ? null : cardType.trim();
	}

	public String getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate == null ? null : isActivate.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIndentificationCardElse() {
		return indentificationCardElse;
	}

	public void setIndentificationCardElse(String indentificationCardElse) {
		this.indentificationCardElse = indentificationCardElse;
	}

//	public String getIsAccountLocked() {
//		return isAccountLocked;
//	}
//
//	public void setIsAccountLocked(String isAccountLocked) {
//		this.isAccountLocked = isAccountLocked;
//	}
//
//	public Integer getLoginFailureCount() {
//		return loginFailureCount;
//	}
//
//	public void setLoginFailureCount(Integer loginFailureCount) {
//		this.loginFailureCount = loginFailureCount;
//	}
//
//	public String getLockedDate() {
//		return lockedDate;
//	}
//
//	public void setLockedDate(String lockedDate) {
//		this.lockedDate = lockedDate;
//	}
//
//	public String getLastTryTime() {
//		return lastTryTime;
//	}
//
//	public void setLastTryTime(String lastTryTime) {
//		this.lastTryTime = lastTryTime;
//	}
	
}