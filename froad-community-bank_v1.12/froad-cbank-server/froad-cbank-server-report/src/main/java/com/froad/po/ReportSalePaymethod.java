package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付方式占比
 * 
 * @author Arron
 * 
 */
public class ReportSalePaymethod implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4481946110918771701L;
	private Long id;				//编号	
	private Date createTime;		//日期	
	private String clientId;		//客户端ID
	private String merchantId;		//商户ID
	private String merchantName;	//商户名
	private String orderType;		//商户名
	private String orgCode;			//商户机构号
	private String fOrgCode;		//1级机构号
	private String fOrgName;		//1级机构名
	private String sOrgCode;		//2级机构号
	private String sOrgName;		//2级机构名
	private String tOrgCode;		//3级机构号
	private String tOrgName;		//3级机构名
	private String lOrgCode;		//4级机构号
	private String lOrgName;		//4级机构名
	private String payMethod;		//1.现金支付，2.联盟积分支付，3.银行积分支付，4.联盟积分+现金，5.银行积分+现金
	private Long totalOrder;		//订单数
	private Long totalAmount;		//订单金额
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the merchantId
	 */
	public String getMerchantId() {
		return merchantId;
	}
	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the fOrgCode
	 */
	public String getForgCode() {
		return fOrgCode;
	}
	/**
	 * @param fOrgCode the fOrgCode to set
	 */
	public void setForgCode(String fOrgCode) {
		this.fOrgCode = fOrgCode;
	}
	/**
	 * @return the fOrgName
	 */
	public String getForgName() {
		return fOrgName;
	}
	/**
	 * @param fOrgName the fOrgName to set
	 */
	public void setForgName(String fOrgName) {
		this.fOrgName = fOrgName;
	}
	/**
	 * @return the sOrgCode
	 */
	public String getSorgCode() {
		return sOrgCode;
	}
	/**
	 * @param sOrgCode the sOrgCode to set
	 */
	public void setSorgCode(String sOrgCode) {
		this.sOrgCode = sOrgCode;
	}
	/**
	 * @return the sOrgName
	 */
	public String getSorgName() {
		return sOrgName;
	}
	/**
	 * @param sOrgName the sOrgName to set
	 */
	public void setSorgName(String sOrgName) {
		this.sOrgName = sOrgName;
	}
	/**
	 * @return the tOrgCode
	 */
	public String getTorgCode() {
		return tOrgCode;
	}
	/**
	 * @param tOrgCode the tOrgCode to set
	 */
	public void setTorgCode(String tOrgCode) {
		this.tOrgCode = tOrgCode;
	}
	/**
	 * @return the tOrgName
	 */
	public String getTorgName() {
		return tOrgName;
	}
	/**
	 * @param tOrgName the tOrgName to set
	 */
	public void setTorgName(String tOrgName) {
		this.tOrgName = tOrgName;
	}
	/**
	 * @return the lOrgCode
	 */
	public String getLorgCode() {
		return lOrgCode;
	}
	/**
	 * @param lOrgCode the lOrgCode to set
	 */
	public void setLorgCode(String lOrgCode) {
		this.lOrgCode = lOrgCode;
	}
	/**
	 * @return the lOrgName
	 */
	public String getLorgName() {
		return lOrgName;
	}
	/**
	 * @param lOrgName the lOrgName to set
	 */
	public void setLorgName(String lOrgName) {
		this.lOrgName = lOrgName;
	}
	/**
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}
	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	/**
	 * @return the totalOrder
	 */
	public Long getTotalOrder() {
		return totalOrder;
	}
	/**
	 * @param totalOrder the totalOrder to set
	 */
	public void setTotalOrder(Long totalOrder) {
		this.totalOrder = totalOrder;
	}
	/**
	 * @return the totalAmount
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
}
