/**
 * Project Name:coremodule-user
 * File Name:CartResProductPojo.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.pojo
 * Date:2015年11月9日下午6:10:32
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.pojo;
/**
 * ClassName:CartResProductPojo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月9日 下午6:10:32
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class CartResActivePojo {

	  /**
	   * 活动id
	   */
	  public String activeId; // required
	  /**
	   * 活动名称
	   */
	  public String activeName; // required
	  /**
	   * 活动类型
	   */
	  public String activeType; // required
	  /**
	   * 满减金额
	   */
	  public Double cutMoney; // required
	  /**
	   * 活动状态
	   */
	  public String activeStatus; // required
	  
	  /**
	   * 活动状态描述信息
	   */
	  public String statusMsg;
	  
	  /**
	 * isMinato:是否展示去凑单
	 */
	public Boolean isMinato;
	
	/**
	 * giveType:红包  or 积分
	 */
	public String giveType;
	
	/**
	 * giveMoney:赠送值
	 */
	public Double giveMoney;
	
	/**
	 * isLowestDelete:是否最低删除
	 */
	public Boolean isLowestDelete;
	  
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}


	public Double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Boolean getIsMinato() {
		return isMinato;
	}
	public void setIsMinato(Boolean isMinato) {
		this.isMinato = isMinato;
	}
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	public Double getGiveMoney() {
		return giveMoney;
	}
	public void setGiveMoney(Double giveMoney) {
		this.giveMoney = giveMoney;
	}
	public Boolean getIsLowestDelete() {
		return isLowestDelete;
	}
	public void setIsLowestDelete(Boolean isLowestDelete) {
		this.isLowestDelete = isLowestDelete;
	}
	  
}
