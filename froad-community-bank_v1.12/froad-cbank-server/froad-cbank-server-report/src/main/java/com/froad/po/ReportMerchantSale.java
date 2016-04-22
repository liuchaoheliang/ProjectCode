package com.froad.po;

import java.util.Date;

/**商户商品销售详情
 * @ClassName: ReportMerchantSale 
 * @Description:  
 * @author longyunbo
 * @date 2015年5月21日 下午7:01:18 
 */
public class ReportMerchantSale {

	private Long id;				//编号	
	private Date createTime;		//日期	
	private String clientId;		//客户端ID
	private String merchantId;		//商户ID
	private String merchantName;	//商户名
	private String merchantTypes;	//商户类型
	private String orgCode;			//商户机构号
	private String fOrgCode;		//1级机构号
	private String fOrgName;		//1级机构名
	private String sOrgCode;		//2级机构号
	private String sOrgName;		//2级机构名
	private String tOrgCode;		//3级机构号
	private String tOrgName;		//3级机构名
	private String lOrgCode;		//4级机构号
	private String lOrgName;		//4级机构名
	private Long saleProductNumber;	//商品销售数量
	private Long saleProductAmount;	//商品总金额
	private Long totalOrder;		//订单总数
	private Long totalAmount;		//订单总金额
	private Long cash;				//现金金额
	private Long bankPointAmount;	//银行积分金额
	private Long fftPointAmount;	//联盟积分金额
	private Long refundAmount;		//退款金额
	private Long buyTrips;			//购买人次
	private String orderType;		//订单类型
	
	private Integer week;			//周
	
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
	 * @return the merchantTypes
	 */
	public String getMerchantTypes() {
		return merchantTypes;
	}
	/**
	 * @param merchantTypes the merchantTypes to set
	 */
	public void setMerchantTypes(String merchantTypes) {
		this.merchantTypes = merchantTypes;
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
	 * @return the saleProductNumber
	 */
	public Long getSaleProductNumber() {
		return saleProductNumber;
	}
	/**
	 * @param saleProductNumber the saleProductNumber to set
	 */
	public void setSaleProductNumber(Long saleProductNumber) {
		this.saleProductNumber = saleProductNumber;
	}
	/**
	 * @return the saleProductAmount
	 */
	public Long getSaleProductAmount() {
		return saleProductAmount;
	}
	/**
	 * @param saleProductAmount the saleProductAmount to set
	 */
	public void setSaleProductAmount(Long saleProductAmount) {
		this.saleProductAmount = saleProductAmount;
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
	/**
	 * @return the cash
	 */
	public Long getCash() {
		return cash;
	}
	/**
	 * @param cash the cash to set
	 */
	public void setCash(Long cash) {
		this.cash = cash;
	}
	/**
	 * @return the bankPointAmount
	 */
	public Long getBankPointAmount() {
		return bankPointAmount;
	}
	/**
	 * @param bankPointAmount the bankPointAmount to set
	 */
	public void setBankPointAmount(Long bankPointAmount) {
		this.bankPointAmount = bankPointAmount;
	}
	/**
	 * @return the fftPointAmount
	 */
	public Long getFftPointAmount() {
		return fftPointAmount;
	}
	/**
	 * @param fftPointAmount the fftPointAmount to set
	 */
	public void setFftPointAmount(Long fftPointAmount) {
		this.fftPointAmount = fftPointAmount;
	}
	/**
	 * @return the refundAmount
	 */
	public Long getRefundAmount() {
		return refundAmount;
	}
	/**
	 * @param refundAmount the refundAmount to set
	 */
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	/**
	 * @return the buyTrips
	 */
	public Long getBuyTrips() {
		return buyTrips;
	}
	/**
	 * @param buyTrips the buyTrips to set
	 */
	public void setBuyTrips(Long buyTrips) {
		this.buyTrips = buyTrips;
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
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	
}
