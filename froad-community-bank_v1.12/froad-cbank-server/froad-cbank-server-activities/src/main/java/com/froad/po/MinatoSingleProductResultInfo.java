package com.froad.po;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

 /**
  * @ClassName: MinatoSingleProductResultInfo
  * @Description: 凑单商品信息PO
  * @author froad-shenshaocheng 2015年11月10日
  * @modify froad-shenshaocheng 2015年11月10日
 */
public class MinatoSingleProductResultInfo {

	/**
	 * @Fields clientId : 客户端ID
	 */
	@JSONField(name = "client_id", serialize = true, deserialize = true)
	private String clientId;
	/**
	 * @Fields merchantId : 商户ID
	 */
	@JSONField(name = "merchant_id", serialize = true, deserialize = true)
	private String merchantId;
	/**
	 * @Fields productId : 商品ID
	 */
	@JSONField(name = "_id", serialize = true, deserialize = true)
	private String productId;
	/**
	 * @Fields type : 类型
	 */
	@JSONField(name = "product_type", serialize = true, deserialize = true)
	private String type;
	/**
	 * @Fields name : 商品名称
	 */
	@JSONField(name = "name", serialize = true, deserialize = true)
	private String name;
	/**
	 * @Fields fullName : 商品全名
	 */
	@JSONField(name = "full_name", serialize = true, deserialize = true)
	private String fullName;
	/**
	 * @Fields price : 商品价格
	 */
	@JSONField(name = "price", serialize = true, deserialize = true)
	private Double price;
	/**
	 * @Fields marketPrice : 市场价格
	 */
	@JSONField(name = "market_price", serialize = true, deserialize = true)
	private Double marketPrice;
	/**
	 * @Fields sellCount : 销售数量
	 */
	@JSONField(name = "sell_count", serialize = true, deserialize = true)
	private Integer sellCount;
	/**
	 * @Fields startTime : 销售开始时间
	 */
	@JSONField(name = "start_time", serialize = true, deserialize = true)
	private Long startTime;
	/**
	 * @Fields endTime : 销售结束时间
	 */
	@JSONField(name = "end_time", serialize = true, deserialize = true)
	private Long endTime;
	/**
	 * @Fields briefIntroduction : 简介
	 */
	@JSONField(name = "brief_introduction", serialize = true, deserialize = true)
	private String briefIntroduction;
	/**
	 * @Fields smallImgUrl : 图片
	 */
	@JSONField(name = "image_info", serialize = true, deserialize = true)
	private List<ImageInfo> imageInfoList;
	/**
	 * @Fields isSeckill : 是否秒杀0非秒杀,1秒杀,2秒杀未上架
	 */
	@JSONField(name = "is_seckill", serialize = true, deserialize = true)
	private String isSeckill;
	/**
	 * @Fields store : 库存
	 */
	@JSONField(name = "store_count", serialize = true, deserialize = true)
	private Integer store;
	/**
	 * @Fields serverTime : 服务时间
	 */
	private Long serverTime;
	/**
	 * @Fields isPoint : 是否参与送积分活动
	 */
	private Boolean isPoint;
	/**
	 * @Fields vipPrice : IVP价格
	 */
	private Double vipPrice;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public List<ImageInfo> getImageInfoList() {
		return imageInfoList;
	}
	public void setImageInfoList(List<ImageInfo> imageInfoList) {
		this.imageInfoList = imageInfoList;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Long getServerTime() {
		return serverTime;
	}
	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
	public Boolean getIsPoint() {
		return isPoint;
	}
	public void setIsPoint(Boolean isPoint) {
		this.isPoint = isPoint;
	}
	public Double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}


}
