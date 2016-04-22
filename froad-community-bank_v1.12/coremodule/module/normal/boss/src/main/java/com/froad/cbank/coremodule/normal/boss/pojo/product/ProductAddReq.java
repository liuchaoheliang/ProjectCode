package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

/**
 * 商品新增，修改
 * 
 * @ClassName ProductAddReq
 * @author zxl
 * @date 2015年7月27日 上午9:33:01
 */
public class ProductAddReq {

	/**
	 * 商品id
	 */
	private String productId; 
	/**
	 * 商品类型 "1":团购,"2":预售,"3":名优特惠,"4":在线积分兑换,"5":网点礼品;
	 */
	@NotEmpty(value="商品类型不能为空")
	private String type; 
	/**
	 * 商品分类id
	 */
	@NotEmpty(value="商品分类不能为空")
	private Long categoryId; 
	/**
	 * 客户端id 即所属行id
	 */
	@NotEmpty(value="所属行不能为空")
	private String clientId; 
	/**
	 * 所属行的以及机构编号
	 */
	private String orgCode; 
	/**
	 * 商品简称
	 */
	@NotEmpty(value="商品简称不能为空")
	@Regular(reg="^.{1,16}$",value="商品简称最多不能超过16个字符")
	private String name; 
	/**
	 * 商品全称
	 */
	@NotEmpty(value="商品全称不能为空")
	@Regular(reg="^.{1,32}$",value="商品全称最多不能超过32个字符")
	private String fullName; 
	/**
	 * 副标题
	 */
	@NotEmpty(value="副标题不能为空")
	@Regular(reg="^.{1,64}$",value="副标题最多不能超过64个字符")
	private String briefIntroduction; 
	/**
	 * 销售价
	 */
	@NotEmpty(value="销售价不能为空")
	private Double price; 
	/**
	 * 市场价
	 */
	@NotEmpty(value="市场价不能为空")
	private Double marketPrice; 
	
	/**
	 * 运费
	 */
	private Double deliveryMoney;
	/**
	 * VIP价
	 */
	private Double vipPrice; 
	/**
	 * 限购数量
	 */
	@NotEmpty(value="限购数量不能为空")
	private Integer max; 
	/**
	 * vip限购数量
	 */
	private Integer maxVip; 
	/**
	 * 库存数量
	 */
	@NotEmpty(value="库存数量不能为空")
	private Integer store; 
	/**
	 * 商品重量
	 */
	private String weight; 
	/**
	 * 重量单位
	 */
	private String weightUnit; 
	/**
	 * 销售开始
	 */
	@NotEmpty(value="开始时间不能为空")
	private String startTime; 
	/**
	 * 销售结束
	 */
	@NotEmpty(value="结束时间不能为空")
	private String endTime; 
	/**
	 * 提货-开始
	 */
	@NotEmpty(value="提货开始时间不能为空")
	private String deliveryStartTime; 
	/**
	 * 提货-结束
	 */
	@NotEmpty(value="提货结束时间不能为空")
	private String deliveryEndTime; 
	/**
	 * 提货方式 "0":送货上门,"1":网点自提,"2":配送或自提;
	 */
	@NotEmpty(value="提货方式不能为空")
	private String deliveryOption; 
	/**
	 * 介绍即商品详情
	 */
	private String introduction; 
	/**
	 * 商品购买须知
	 */
	private String buyKnow; 
	/**
	 * 图片
	 */
	private List<FileVo> imgList;
	
	/**
	 * 机构
	 */
	private String orgCodes;
	
	/**
	 * 提货网点
	 */
	private String outletIds;
	
	/**
	 * vip规则
	 */
	private String vipId;
	
	
	
	public Double getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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


	public String getOutletIds() {
		return outletIds;
	}

	public void setOutletIds(String outletIds) {
		this.outletIds = outletIds;
	}

	public List<FileVo> getImgList() {
		return imgList;
	}

	public void setImgList(List<FileVo> imgList) {
		this.imgList = imgList;
	}
	
	
}
