package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

public class SeckillProductDetil implements Serializable {

	private static final long serialVersionUID = 7012087640199905550L;
	
	private List<String> parentOrgCodeList;  //法人行社
	private List<String> orgCodeList;  //提货网点
	private List<String> orgNameList;  //提货网点名称
	private String productName;// 商品名称
	private String productType;// 商品类型
	private String marketPrice;// 市场价
	private String secPrice;// 秒杀价
	private String vipSecPrice;// VIP秒杀价
	private String secStore;// 秒杀库存数量
	private String buyLimit;// 秒杀限购数量
	private String startDate;// 秒杀开始时间
	private String endDate;// 秒杀结束时间
	private String description;// 商品简介
	private String distributionType;// 配送方式
	private List<FileVo> fileList;// 商品图片
	private String productKnow;// 购买须知
	private String productDetails;// 商品详情
	private String auditComment;//审核备注
	
	public List<String> getParentOrgCodeList() {
		return parentOrgCodeList;
	}

	public void setParentOrgCodeList(List<String> parentOrgCodeList) {
		this.parentOrgCodeList = parentOrgCodeList;
	}

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	public List<String> getOrgNameList() {
		return orgNameList;
	}

	public void setOrgNameList(List<String> orgNameList) {
		this.orgNameList = orgNameList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
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

	public String getSecStore() {
		return secStore;
	}

	public void setSecStore(String secStore) {
		this.secStore = secStore;
	}

	public String getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(String buyLimit) {
		this.buyLimit = buyLimit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}

	public List<FileVo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileVo> fileList) {
		this.fileList = fileList;
	}

	public String getProductKnow() {
		return productKnow;
	}

	public void setProductKnow(String productKnow) {
		this.productKnow = productKnow;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	
}
