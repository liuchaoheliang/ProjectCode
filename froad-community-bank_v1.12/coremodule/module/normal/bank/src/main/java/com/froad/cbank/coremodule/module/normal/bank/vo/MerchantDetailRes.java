package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午5:31:07 
 */
public class MerchantDetailRes implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3196175459088748925L;
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
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 新增商户数
	 */
	private Integer addCount;
	/**
	 * 动账商户数
	 */
	private Integer changeAmountCount;
	/**
	 * 结余商户数
	 */
	private Integer totalCount;
	/**
	 * 商户占比
	 */
	private BigDecimal percent;
	/**
	 * 订单数
	 */
	private Integer orderCount;
	/**
	 * 订单数
	 */
	private BigDecimal orderAmount;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getAddCount() {
		return addCount;
	}
	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}
	public Integer getChangeAmountCount() {
		return changeAmountCount;
	}
	public void setChangeAmountCount(Integer changeAmountCount) {
		this.changeAmountCount = changeAmountCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
}
