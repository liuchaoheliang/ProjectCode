package com.froad.po;

import java.util.Date;

/**商户商品统计
 * @ClassName: ReportMerchantProduct 
 * @Description:  
 * @author longyunbo
 * @date 2015年5月21日 下午7:01:00 
 */
public class ReportMerchantProduct {
	private Long id;				//编号
	private Date createTime;		//日期
	private String clientId;		//客戶端ID
	private String productId;		//商品
	private String orgCode;			//商户机构号
	private String orgName;			//商户机构名
	private String fOrgCode;		//1级机构号
	private String fOrgName;		//1级机构名
	private String sOrgCode;		//2级机构号
	private String sOrgName;		//2级机构名
	private String tOrgCode;		//3级机构号
	private String tOrgName;		//3级机构名
	private String lOrgCode;		//4级机构号
	private String lOrgName;		//4级机构名
	private String type;			//业务类型
	private String merchantId;		//商户号
	private String merchantName;	//商户名
	private String category;		//类目
	private String categoryName;	//类目名称
	private Long totalProducts;		//商户商品总数
	private Long newCount;			//新增商品总数
	private Long saleCount;			//销售商品总数
	private Long saleAmount;		//商品销售金额
	private Long refundAmount;		//退款金额
	private String isMarketable;	//上下架标识
	private String auditState;		//审核状态
	private Date auditTime;			//审核时间
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the totalProducts
	 */
	public Long getTotalProducts() {
		return totalProducts;
	}
	/**
	 * @param totalProducts the totalProducts to set
	 */
	public void setTotalProducts(Long totalProducts) {
		this.totalProducts = totalProducts;
	}
	/**
	 * @return the newCount
	 */
	public Long getNewCount() {
		return newCount;
	}
	/**
	 * @param newCount the newCount to set
	 */
	public void setNewCount(Long newCount) {
		this.newCount = newCount;
	}
	/**
	 * @return the saleCount
	 */
	public Long getSaleCount() {
		return saleCount;
	}
	/**
	 * @param saleCount the saleCount to set
	 */
	public void setSaleCount(Long saleCount) {
		this.saleCount = saleCount;
	}
	/**
	 * @return the saleAmount
	 */
	public Long getSaleAmount() {
		return saleAmount;
	}
	/**
	 * @param saleAmount the saleAmount to set
	 */
	public void setSaleAmount(Long saleAmount) {
		this.saleAmount = saleAmount;
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
	 * @return the isMarketable
	 */
	public String getIsMarketable() {
		return isMarketable;
	}
	/**
	 * @param isMarketable the isMarketable to set
	 */
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	/**
	 * @return the auditState
	 */
	public String getAuditState() {
		return auditState;
	}
	/**
	 * @param auditState the auditState to set
	 */
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	/**
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
}
