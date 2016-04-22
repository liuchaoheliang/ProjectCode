package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品基本信息实体类
 * @author Administrator
 *
 */
public class ProductInfoVoReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String productId;        //商品ID
	private String productName;      //商品名称
	private String productFullName;  //商品全称
	private String merchantId;       //商户ID
	private String merchantName;     //商户名称
	private String orgCode;   		 //下属机构
	private String orgName;          //机构名称
	private String auditState;       //审核状态
	private String isMarketable;     //上下架状态
	private String auditComment;     //审核备注
	
	private String startDate;//开始日期
	private String endDate;	//结束日期
	private String takeStartDate;    //限购开始时间
	private String takeEndDate;      //限购结束时间
	private String consumeStartDate; //开始消费日期
	private String consumeEndDate;	 //最后消费日期
	private String contractEndtime;  //签约到期时间
	
	private List<String> pratenOrgCodeList;  //法人行社
	private List<String> orgCodeList;        //提货网点
	private List<String> orgNameList;        //提货网点名称
	private ArrayList<FileVo> files;         //图片
	private ArrayList<ActivitiesVo> activities; //活动类型
	private Long merchantType;	//商品分类
	
	private String marketPrice;    //市场价/原价
	private String salePrice;      //预售价/特惠价
	private String groupPrice;	   //团购价
	private String vipPrice;       //vip价
	private String point;          //积分
	private String postage;        //邮费
	private String address;        //地区
	private String phone;          //电话
	 
	private Integer storeNum;        //库存数量
	private Integer limitNum;        //限购数量
	private Integer viplimitNum;     //vip限购数量
	private Integer memberlimitNum;  //普通会员限购数量
	
	private String description;     //商品简介
	private String distributionType; //配送方式
	private String productKnow;      //购买须知
	private String productDetails;   //商品详情
	private Boolean isDelete;       //是否删除
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
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
	public String getAuditComment() {
		return auditComment;
	}
	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
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
	public String getTakeStartDate() {
		return takeStartDate;
	}
	public void setTakeStartDate(String takeStartDate) {
		this.takeStartDate = takeStartDate;
	}
	public String getTakeEndDate() {
		return takeEndDate;
	}
	public void setTakeEndDate(String takeEndDate) {
		this.takeEndDate = takeEndDate;
	}
	public String getConsumeStartDate() {
		return consumeStartDate;
	}
	public void setConsumeStartDate(String consumeStartDate) {
		this.consumeStartDate = consumeStartDate;
	}
	public String getConsumeEndDate() {
		return consumeEndDate;
	}
	public void setConsumeEndDate(String consumeEndDate) {
		this.consumeEndDate = consumeEndDate;
	}
	public String getContractEndtime() {
		return contractEndtime;
	}
	public void setContractEndtime(String contractEndtime) {
		this.contractEndtime = contractEndtime;
	}
	public List<String> getPratenOrgCodeList() {
		return pratenOrgCodeList;
	}
	public void setPratenOrgCodeList(List<String> pratenOrgCodeList) {
		this.pratenOrgCodeList = pratenOrgCodeList;
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
	public ArrayList<FileVo> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<FileVo> files) {
		this.files = files;
	}
	public ArrayList<ActivitiesVo> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<ActivitiesVo> activities) {
		this.activities = activities;
	}
	public Long getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(Long merchantType) {
		this.merchantType = merchantType;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getGroupPrice() {
		return groupPrice;
	}
	public void setGroupPrice(String groupPrice) {
		this.groupPrice = groupPrice;
	}
	public String getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getPostage() {
		return postage;
	}
	public void setPostage(String postage) {
		this.postage = postage;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStoreNum() {
		return storeNum;
	}
	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}
	public Integer getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}
	public Integer getViplimitNum() {
		return viplimitNum;
	}
	public void setViplimitNum(Integer viplimitNum) {
		this.viplimitNum = viplimitNum;
	}
	public Integer getMemberlimitNum() {
		return memberlimitNum;
	}
	public void setMemberlimitNum(Integer memberlimitNum) {
		this.memberlimitNum = memberlimitNum;
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
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
}
