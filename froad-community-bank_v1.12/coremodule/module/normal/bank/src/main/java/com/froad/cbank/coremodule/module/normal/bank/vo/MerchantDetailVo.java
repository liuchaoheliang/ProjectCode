package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-2下午5:32:05 
 */
public class MerchantDetailVo implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7907913858994375444L;
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
	private Integer totalMerchantCount;
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
	
	public Integer getTotalMerchantCount() {
		return totalMerchantCount;
	}
	public void setTotalMerchantCount(Integer totalMerchantCount) {
		this.totalMerchantCount = totalMerchantCount;
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
