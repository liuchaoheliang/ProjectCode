package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-6-3下午6:05:50 
 */
public class MerchantContractDetailRes implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5852852989668631370L;
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
	 * 签约人
	 */
	private String constractStaff;
	/**
	 * 新增商户数
	 */
	private int addMerchantCount;
	/**
	 * 动账商户数
	 */
	private int changeAmountCount;
	/**
	 * 结余商户数
	 */
	private int totalMerchantCount;
	/**
	 * 新增占比
	 */
	private BigDecimal addPercent;
	/**
	 * 订单数
	 */
	private int orderCount;
	/**
	 * 订单金额
	 */
	private BigDecimal totalAmount;
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
	public String getConstractStaff() {
		return constractStaff;
	}
	public void setConstractStaff(String constractStaff) {
		this.constractStaff = constractStaff;
	}
	public int getAddMerchantCount() {
		return addMerchantCount;
	}
	public void setAddMerchantCount(int addMerchantCount) {
		this.addMerchantCount = addMerchantCount;
	}
	public int getChangeAmountCount() {
		return changeAmountCount;
	}
	public void setChangeAmountCount(int changeAmountCount) {
		this.changeAmountCount = changeAmountCount;
	}	
	public int getTotalMerchantCount() {
		return totalMerchantCount;
	}
	public void setTotalMerchantCount(int totalMerchantCount) {
		this.totalMerchantCount = totalMerchantCount;
	}
	public BigDecimal getAddPercent() {
		return addPercent;
	}
	public void setAddPercent(BigDecimal addPercent) {
		this.addPercent = addPercent;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
