package com.froad.po;
/**
 * 商户业务销售详情
 * @ClassName: MerchantSaleDetail 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月3日 下午5:01:55
 */
public class MerchantSaleDetail {
	private String merchantId;
	private String merchantName;
	private String merchantType;
	private String orgCode;
	private String orgName;
	private int groupOrderCount;
	private int faceOrderCount;
	private int specialOrderCount;
	private int presellOrderCount;
	private int orderCount;
	private double orderAmount;
	private double refundAmount;
	private String forgCode;		//一级机构号
	private String forgName;		//一级机构名
	private String sorgCode;		//二级机构号
	private String sorgName;		//二级机构名
	private String torgCode;		//三级机构号
	private String torgName;		//三级机构名
	private String lorgCode;		//四级机构号
	private String lorgName;		//四级机构名
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
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
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
	public int getGroupOrderCount() {
		return groupOrderCount;
	}
	public void setGroupOrderCount(int groupOrderCount) {
		this.groupOrderCount = groupOrderCount;
	}
	public int getFaceOrderCount() {
		return faceOrderCount;
	}
	public void setFaceOrderCount(int faceOrderCount) {
		this.faceOrderCount = faceOrderCount;
	}
	public int getSpecialOrderCount() {
		return specialOrderCount;
	}
	public void setSpecialOrderCount(int specialOrderCount) {
		this.specialOrderCount = specialOrderCount;
	}
	public int getPresellOrderCount() {
		return presellOrderCount;
	}
	public void setPresellOrderCount(int presellOrderCount) {
		this.presellOrderCount = presellOrderCount;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getForgCode() {
		return forgCode;
	}
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	public String getForgName() {
		return forgName;
	}
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	public String getSorgCode() {
		return sorgCode;
	}
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	public String getSorgName() {
		return sorgName;
	}
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	public String getTorgCode() {
		return torgCode;
	}
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	public String getTorgName() {
		return torgName;
	}
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	public String getLorgCode() {
		return lorgCode;
	}
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	public String getLorgName() {
		return lorgName;
	}
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	
	
	
}
