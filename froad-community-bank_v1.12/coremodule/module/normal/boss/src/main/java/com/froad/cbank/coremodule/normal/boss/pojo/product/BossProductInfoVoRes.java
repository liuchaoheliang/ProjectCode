package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

/**
 * boss商品管理详情实体类
 * @author yfy
 * @date 2015年7月27日 上午10:40:12
 */
public class BossProductInfoVoRes {

    private String productId; // 商品id
    private String type; // 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;
    private Long categoryId; // 商品分类id
    private String categoryName; //  商品分类名称
    private String clientId; // 客户端id 即所属行id
    private String clientName; // 客户端名称
    private String bankName; //所属银行
    private String orgCode; // 所属行的以及机构编号
    private String name; // 商品简称
    private String fullName; // 商品全称
    private String briefIntroduction; // 副标题
    private String vipId;// vip活动ID
    private String vipName; //vip规则名称
    private boolean isRemoveVipId;// 修改商品时候是否删除VIP规则绑定关系
    private Double price; // 销售价
    private Double marketPrice; // 市场价
    private Double vipPrice; // VIP价
    private Double deliveryMoney;// 运费
    private Integer max; // 限购数量
    private Integer maxVip; // vip限购数量
    private Integer store; // 库存数量
    private String weight; // 商品重量
    private String weightUnit; // 重量单位
    private String startTime; // 销售开始
    private String endTime; // 销售结束
    private String deliveryStartTime; //提货-开始
    private String deliveryEndTime; // 提货-结束
    private String deliveryOption; // 提货方式  "0":送货上门,"1":网点自提,"2":配送或自提;
    private String auditStatus;// 审核状态 0-待审核1-审核通过2-审核未通过
    private String marketableStatus;// 上下架状态 0-未上架1-已上架2-已下架3-已删除
    private String introduction; // 介绍即商品详情
    private String buyKnow; // 商品购买须知
    
    private List<String> orgCodes; // 商品提货网点所属行社的机构代码列表
	private List<BossOutletVoRes> outletVoList; // 商品提货网点的门店ID和门店名称
	private List<FileVo> imgList; // 图片
	
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getVipId() {
		return vipId;
	}
	public void setVipId(String vipId) {
		this.vipId = vipId;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public boolean isRemoveVipId() {
		return isRemoveVipId;
	}
	public void setRemoveVipId(boolean isRemoveVipId) {
		this.isRemoveVipId = isRemoveVipId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Double getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getMaxVip() {
		return maxVip;
	}
	public void setMaxVip(Integer maxVip) {
		this.maxVip = maxVip;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getMarketableStatus() {
		return marketableStatus;
	}
	public void setMarketableStatus(String marketableStatus) {
		this.marketableStatus = marketableStatus;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}
	public List<String> getOrgCodes() {
		return orgCodes;
	}
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
	public List<BossOutletVoRes> getOutletVoList() {
		return outletVoList;
	}
	public void setOutletVoList(List<BossOutletVoRes> outletVoList) {
		this.outletVoList = outletVoList;
	}
	public List<FileVo> getImgList() {
		return imgList;
	}
	public void setImgList(List<FileVo> imgList) {
		this.imgList = imgList;
	}
	
}
