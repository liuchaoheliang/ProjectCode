package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;

public class ProductVoReq extends Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 客户端id */
	private String clientId;
	/** 商户ID */
	private String merchantId;
	/** 商品id */
	private String productId;
	/** 商品类型 */
	private String type;
	/** 商品名称 */
	private String name;
	/** 是否上架 0未上架,1已上架,2已下架,3已删除 */
	private Integer isMarketable;
	/** 销售价(价格/积分) */
	private String price;
	/** 市场价 */
	private String marketPrice;
	/** VIP价 */
	private int vipPrice;
	/** 库存量 */
	private String store;
	/** 已售出数量 */
	private String sellCount;
	/** 小图片 */
	private String smallImgUrl;
	/** 预售开始时间 */
	private String expireStartTime;
	/** 预售结束时间 */
	private String expireEndTime;
	/** 提货开始时间 */
	private String deliveryStartTime;
	/** 提货结束时间 */
	private String deliveryEndTime;
	/** 商品简介(副标题) */
	private String briefIntroduction;
	/** 商品详情 */
	private String introduction;
	/** 所属机构 */
	private String orgCode;
	/** 商品分类 */
	private String productCategory;
	/** 所属支行机构 */
	private List<String> pratenOrgCodeList;
	/** 提货方式 */
	private String takeType;
	/** 活动类型 */
	private String activitiesType;
	/** VIP限购量 */
	private int viplimitNum;
	/** 会员限购最小数量 */
	private int memberMinNum;
	/** 会员限购最大数量 */
	private int memberMaxNum;
	/** 限购开始时间 */
	private String limitStartDate;
	/** 限购结束时间 */
	private String limitEndDate;
	/** 限购最小数量 */
	private String minNum;
	/** 限购最大数量 */
	private String maxNum;
	/** 最大数量 */
	private Integer max;
	/** 贴膜卡最大购买数量 */
	private String tmoMaxNum;
	/** 排序 */
	private String orderValue;
	/** 配送方式 */
	private String distributionType;
	/** 商品全称 */
	private String fullName;
	/** 商品图片 */
	private List<FileVo> files;

	/*************** 积分商品字段 ***************/
	/** 积分范围 */
	private Integer scoreScope;
	/** 用户标识 */
	private Integer vStatus;
	/** 购买须知 */
	private String buyKnow;
	/** 审核状态 */
	private Integer auditState;

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public int getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(int vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSellCount() {
		return sellCount;
	}
	public void setSellCount(String sellCount) {
		this.sellCount = sellCount;
	}
	public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(String expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public String getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(String expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(String deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}
	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(String deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public List<String> getPratenOrgCodeList() {
		return pratenOrgCodeList;
	}
	public void setPratenOrgCodeList(List<String> pratenOrgCodeList) {
		this.pratenOrgCodeList = pratenOrgCodeList;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public String getActivitiesType() {
		return activitiesType;
	}
	public void setActivitiesType(String activitiesType) {
		this.activitiesType = activitiesType;
	}
	public int getViplimitNum() {
		return viplimitNum;
	}
	public void setViplimitNum(int viplimitNum) {
		this.viplimitNum = viplimitNum;
	}
	public int getMemberMinNum() {
		return memberMinNum;
	}
	public void setMemberMinNum(int memberMinNum) {
		this.memberMinNum = memberMinNum;
	}
	public int getMemberMaxNum() {
		return memberMaxNum;
	}
	public void setMemberMaxNum(int memberMaxNum) {
		this.memberMaxNum = memberMaxNum;
	}
	public String getLimitStartDate() {
		return limitStartDate;
	}
	public void setLimitStartDate(String limitStartDate) {
		this.limitStartDate = limitStartDate;
	}
	public String getLimitEndDate() {
		return limitEndDate;
	}
	public void setLimitEndDate(String limitEndDate) {
		this.limitEndDate = limitEndDate;
	}
	public String getMinNum() {
		return minNum;
	}
	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}
	public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	public String getTmoMaxNum() {
		return tmoMaxNum;
	}
	public void setTmoMaxNum(String tmoMaxNum) {
		this.tmoMaxNum = tmoMaxNum;
	}

	public String getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}
	public String getDistributionType() {
		return distributionType;
	}
	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}

	public List<FileVo> getFiles() {
		return files;
	}

	public void setFiles(List<FileVo> files) {
		this.files = files;
	}
	public Integer getScoreScope() {
		return scoreScope;
	}
	public void setScoreScope(Integer scoreScope) {
		this.scoreScope = scoreScope;
	}
	public Integer getvStatus() {
		return vStatus;
	}
	public void setvStatus(Integer vStatus) {
		this.vStatus = vStatus;
	}
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
