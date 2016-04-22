package com.froad.cbank.coremodule.module.normal.user.pojo;

/**
 * 推荐精品商城商品
 * @author yfy
 *
 */
public class BoutiqueProductPojo {
	
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 商品全称
	 */
	private String fullName;
	/**
	 * 销售价
	 */
	private String price;
	/**
	 * vip价格
	 */
	private String vipPrice;
	/**
	 * 销售数量
	 */
	private Integer sellCount;
	/**
	 * 简介
	 */
	private String briefIntroduction;
	/**
	 * 服务器时间
	 */
	private Long serverTime;
	/**
	 * 是否推荐
	 */
	private Boolean isRecommend;
	/**
	 * 是否新品
	 */
	private Boolean isNew;
	/**
	 * 是否热销
	 */
	private Boolean isHot;
	/**
	 * 是否秒杀 0非秒杀,1秒杀,2秒杀未上架
	 */
	private String isSeckill;
	/**
	 * 商品图片
	 */
	public ProductImagePojo image;
	/**
	 * 我的vip推荐商品图片
	 */
	private String imgUrl;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public Long getServerTime() {
		return serverTime;
	}
	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public ProductImagePojo getImage() {
		return image;
	}
	public void setImage(ProductImagePojo image) {
		this.image = image;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
