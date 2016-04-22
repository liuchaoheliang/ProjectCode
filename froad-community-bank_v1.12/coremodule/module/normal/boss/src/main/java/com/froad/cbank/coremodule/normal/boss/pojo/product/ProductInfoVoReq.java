package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

/**
 * 商品基本信息实体类
 * 
 * @author Administrator
 *
 */
public class ProductInfoVoReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;

	public String clientId; // 客户端id
	public String clientName; // 客户端名称
	private String productId; // 商品ID
	private String name; // 商品名称
	private String fullName; // 商品全称
	private String productSn; // 商品编号
	private String merchantId; // 商户ID
	private String merchantName; // 商户名称
	private String orgCode; // 下属机构
	private String orgName; // 机构名称
	private String auditState; // 审核状态
	/** 是否上架 0未上架,1已上架,2已下架,3已删除 */
	private String isMarketable; // 上下架状态
	public long rackTime; // 上架时间

	public long auditTime; // 审核时间
	private String auditComment; // 审核备注

	private Integer max;// 最大限购量
	private String type;// 商品类型

	private String productType; // 商品分类
	public String auditOrgCode; // 待审核机构编号
	public String auditStage; // 审核步骤
	public String reviewStaff; // 复核人
	public String auditStaff; // 审核人

	public String auditStartOrgCode; // 起始审核机构编号
	public String auditEndOrgCode; // 最终审核机编号

	public int storeCount; // 用户收藏数

	public String startTime; // 团购或预售有效期开始
	private String endTime; // 团购或预售有效期结束
	public double deliveryMoney;// 运费
	public long expireStartTime; // 团购或预售券有效起始日
	public long expireEndTime; // 团购或预售券有效结束日
	public int trueBuyerNumber; // 团购或预售真实购买数量
	public int virtualBuyerNumber; // 团购或预售虚拟购买数量
	public String productSupplier; // 供货商
	public int maxPerOutlet; // 自提商品每家门店最大支持数量
	public long deliveryStartTime; // 提货-开始
	public long deliveryEndTime; // 提货-结束
	public boolean clusterState; // 是否成功成团
	public boolean clusterType; // 成团类型
	public long downTime; // 下架时间

	public boolean isSeckill; // 是否秒杀

	private String takeStartDate; // 限购开始时间
	private String takeEndDate; // 限购结束时间
	private String consumeStartDate; // 开始消费日期
	private String consumeEndDate; // 最后消费日期
	private String contractEndtime; // 签约到期时间
	public long serverTime; // 服务器时间

	private List<String> pratenOrgCodeList; // 法人行社
	private List<String> orgCodeList; // 提货网点
	private List<String> orgNameList; // 提货网点名称
	private List<FileVo> files; // 图片

	private Long merchantType; // 商品分类
	public String weight; // 商品重量
	public String weightUnit; // 重量单位

	private String marketPrice; // 市场价/原价
	private String price; // 预售价/特惠价
	public double cost; // 成本价
	private String groupPrice; // 团购价
	private String vipPrice; // vip价
	private String point; // 积分
	private String postage; // 邮费
	private String address; // 地区
	private String phone; // 电话

	public int orderValue; // 排序(1-10)

	public boolean isBest; // 是否为精品
	public boolean isLimit; // 是否限购

	public int sellCount; // 销售数量
	private Integer store; // 库存数量
	private Integer limitNum; // 限购数量
	private Integer viplimitNum; // vip限购数量
	private Integer memberlimitNum; // 普通会员限购数量

	private String briefIntroduction; // 商品简介
	private String deliveryOption; // 配送方式
	private String buyKnow; // 购买须知
	private String introduction; // 商品详情
	private Boolean isDelete; // 是否删除

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public List<FileVo> getFiles() {
		return files;
	}

	public void setFiles(List<FileVo> files) {
		this.files = files;
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

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getSellCount() {
		return sellCount;
	}

	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getBuyKnow() {
		return buyKnow;
	}

	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public long getRackTime() {
		return rackTime;
	}

	public void setRackTime(long rackTime) {
		this.rackTime = rackTime;
	}

	public long getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditOrgCode() {
		return auditOrgCode;
	}

	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public String getReviewStaff() {
		return reviewStaff;
	}

	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}

	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}

	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}

	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}

	public int getStoreCount() {
		return storeCount;
	}

	public void setStoreCount(int storeCount) {
		this.storeCount = storeCount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public long getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public long getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public int getTrueBuyerNumber() {
		return trueBuyerNumber;
	}

	public void setTrueBuyerNumber(int trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}

	public int getVirtualBuyerNumber() {
		return virtualBuyerNumber;
	}

	public void setVirtualBuyerNumber(int virtualBuyerNumber) {
		this.virtualBuyerNumber = virtualBuyerNumber;
	}

	public String getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(String productSupplier) {
		this.productSupplier = productSupplier;
	}

	public int getMaxPerOutlet() {
		return maxPerOutlet;
	}

	public void setMaxPerOutlet(int maxPerOutlet) {
		this.maxPerOutlet = maxPerOutlet;
	}

	public long getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(long deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public long getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(long deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public boolean isClusterState() {
		return clusterState;
	}

	public void setClusterState(boolean clusterState) {
		this.clusterState = clusterState;
	}

	public boolean isClusterType() {
		return clusterType;
	}

	public void setClusterType(boolean clusterType) {
		this.clusterType = clusterType;
	}

	public long getDownTime() {
		return downTime;
	}

	public void setDownTime(long downTime) {
		this.downTime = downTime;
	}

	public boolean isSeckill() {
		return isSeckill;
	}

	public void setSeckill(boolean isSeckill) {
		this.isSeckill = isSeckill;
	}

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

	public boolean isBest() {
		return isBest;
	}

	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}

	public boolean isLimit() {
		return isLimit;
	}

	public void setLimit(boolean isLimit) {
		this.isLimit = isLimit;
	}

}
