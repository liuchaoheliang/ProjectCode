package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 预售商品信息请求参数
 * @author Administrator
 *
 */
public class ProductVoReq extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String orgCode;   		//下属机构
	private String auditOrgCode;    //待审核列表下属机构
	private String productId;       //商品ID
	private String productName;     //商品名称
	private String merchantName;     //商户名称
	private String auditState;      //审核状态
	private String isMarketable;     //上下架状态
	private String priceStart;   //积分范围开始
	private String priceEnd;     //积分范围结束
	private List<String> orgCodeList;  //提货网点
	private List<String> productIdList;     //礼品编号List
	private String auditStartTime; // 审核开始时间
	private String auditEndTime;  //审核结束时间
	private String type;// 查询的商品类型
	private String categoryId;  //分类Id
	
	private String userId;
	private String token;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getPriceStart() {
		return priceStart;
	}
	public void setPriceStart(String priceStart) {
		this.priceStart = priceStart;
	}
	public String getPriceEnd() {
		return priceEnd;
	}
	public void setPriceEnd(String priceEnd) {
		this.priceEnd = priceEnd;
	}
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	public List<String> getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAuditStartTime() {
		return auditStartTime;
	}
	public void setAuditStartTime(String auditStartTime) {
		this.auditStartTime = auditStartTime;
	}
	public String getAuditEndTime() {
		return auditEndTime;
	}
	public void setAuditEndTime(String auditEndTime) {
		this.auditEndTime = auditEndTime;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
