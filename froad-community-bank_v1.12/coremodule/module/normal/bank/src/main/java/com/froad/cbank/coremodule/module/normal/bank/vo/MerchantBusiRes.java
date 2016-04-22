package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-8下午5:31:02 
 */
public class MerchantBusiRes implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -7364191586597066519L;
	private String  clientId;
	private String orgCode;
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
