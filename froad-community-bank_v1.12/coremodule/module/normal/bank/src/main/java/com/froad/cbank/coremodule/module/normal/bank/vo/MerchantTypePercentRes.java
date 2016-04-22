package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午4:39:59 
 */
public class MerchantTypePercentRes implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5614386162954697631L;
	
	/**
	 * 开始时间
	 */
	private String beginDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 机构号
	 */
	private String orgCode;
	/**
	 * 商户类型
	 */
	private String type;
	/**
	 * 商户类型占比
	 */
	private BigDecimal percent;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	} 
}
