/**
 * Project Name:froad-cbank-server-boss
 * File Name:BankAccessDto.java
 * Package Name:com.froad.po
 * Date:2015年9月18日下午3:12:10
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.Date;

/**
 * ClassName:BankAccessDto
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 下午3:12:10
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BankAccessDto {
	
	  private Long id; 	
	
	 /**
	   * 编号
	   */
	  public String clientNo; // required
	  /**
	   * 客户端名称
	   */
	  public String clientName; // required
	  /**
	   * 功能模块
	   */
	  public String functionDesc; // required
	  /**
	   * 支付方式
	   */
	  public String paymentMethodDesc; // required
	  /**
	   * 配置时间
	   */
	  public Date createTime; // required
	  /**
	   * 更新时间
	   */
	  public Date updateTime; // required
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getFunctionDesc() {
		return functionDesc;
	}
	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
