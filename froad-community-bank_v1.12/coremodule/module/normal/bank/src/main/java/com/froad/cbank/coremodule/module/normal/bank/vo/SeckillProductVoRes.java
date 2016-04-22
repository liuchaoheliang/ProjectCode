package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 秒杀商品列表显示数据
 * 
 * @author liuyanyun
 *
 */
public class SeckillProductVoRes implements Serializable {

	private static final long serialVersionUID = 1641416665036951891L;

	private String productId; // 商品ID
	private String productName; // 商品名称
	private String productType; // 商品类型
	private String merchantId;
	private String merchantName;
	private String orgCode; // 所属机构号
	private String orgName; // 所属机构名称
	private String price; // 优惠价格
	private String secPrice; // 秒杀价格
	private String vipSecPrice; // VIP秒杀价格
	private String startDate; // 秒杀开始时间
	private String endDate; // 秒杀结束时间
	private int buyLimit; // 秒杀限量
	private int store; // 商品库存
	private int secStore; // 秒杀库存
	private String auditState; // 审核状态
	private String auditStateName;// 审核状态
	private String isMarketable; // 上下架状态
	private String isMarketableName;// 上下架状态

	public SeckillProductVoRes(){}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(String secPrice) {
		this.secPrice = secPrice;
	}

	public String getVipSecPrice() {
		return vipSecPrice;
	}

	public void setVipSecPrice(String vipSecPrice) {
		this.vipSecPrice = vipSecPrice;
	}

	public int getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(int buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public int getSecStore() {
		return secStore;
	}

	public void setSecStore(int secStore) {
		this.secStore = secStore;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditStateName() {
		return auditStateName;
	}

	public void setAuditStateName(String auditStateName) {
		this.auditStateName = auditStateName;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getIsMarketableName() {
		return isMarketableName;
	}

	public void setIsMarketableName(String isMarketableName) {
		this.isMarketableName = isMarketableName;
	}
	
	
}
