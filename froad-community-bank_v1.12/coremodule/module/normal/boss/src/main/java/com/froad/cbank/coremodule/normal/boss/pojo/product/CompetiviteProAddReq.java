package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

/**
 * 
 * @ClassName: CompetiviteProAddReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月25日 下午1:18:37 
 * @desc <p>添加/修改精品商品请求实体</p>
 */
public class CompetiviteProAddReq {
	
	private String id;
	@NotEmpty(value="客户端id不能为空")
	private String clientId;//客户端id
	@NotEmpty(value="供货商不能为空")
	private String merchantId;//供货商id
	@NotEmpty(value="商品简称不能为空")
	@Regular(reg="^.{1,22}$",value="商品简称最多不能超过22个字符")
	private String name;	//商品简称
	@NotEmpty(value="商品全称不能为空")
	@Regular(reg="^.{1,48}$",value="商品全称最多不能超过48个字符")
	private String fullName;	//商品全称	
	
	@NotEmpty(value="商品副标题不能为空")
	@Regular(reg="^.{1,28}$",value="商品副标题最多不能超过28个字符")
	private String briefIntroduction;	//简介(副标题)	
	@NotEmpty(value="商品搜索关键字不能为空")
	@Regular(reg="^.{1,24}$",value="商品搜索关键字最多不能超过24个字符")
	private String seoKeyWords;//	搜索关键词	
	
	@NotEmpty(value="精品价不能为空")
	private Double price;//商城价
	@NotEmpty(value="市场价不能为空")
	private Double marketPrice;//	市场价
	
	private Double vipPrice;	//VIP价	
	
	@NotEmpty(value="商品分类不能为空")
	private Long categoryId;	//商品分类id	
	
	@NotEmpty(value="最低购买数量不能为空")
	private Integer min;	//限购数量
	@NotEmpty(value="最大购买数量不能为空")
	private Integer max;	//限购数量
	
	@NotEmpty(value="vip限购数量不能为空")
	private Integer maxVip;	//VIP限购数量
	@NotEmpty(value="商品库存数量不能为空")
	private Integer store	;//库存数量	
	
	private Boolean isRecommend;	//是否推荐	
	private Boolean isHot;	//是否热销	
	private Boolean isNew;	//是否新品	
	@NotEmpty(value="是否批量发货不能为空")
	private Boolean isBatchDelivery;	//是否批量发货
	
	private String deliveryTime;	//销售开始
	private String type;
	
	@NotEmpty(value="购买须知不能为空")
	private String buyKnow;	//购买须知
	@NotEmpty(value="商品介绍不能为空")
	private String introduction;	//商品介绍
	@NotEmpty(value="售后说明不能为空")
	private String afterShop;	//售后说明
	
	private String productId;
	/**
	 * 图片
	 */
	private List<FileVo> images;
	
	 /**
	   * 是否秒杀 0非秒杀,1秒杀
	   */
	@NotEmpty(value="是否为秒杀商品不能为空")
	private String isSeckill; // required
	
	
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
	public String getSeoKeyWords() {
		return seoKeyWords;
	}
	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
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
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
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
	public Boolean getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsBatchDelivery() {
		return isBatchDelivery;
	}
	public void setIsBatchDelivery(Boolean isBatchDelivery) {
		this.isBatchDelivery = isBatchDelivery;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getAfterShop() {
		return afterShop;
	}
	public void setAfterShop(String afterShop) {
		this.afterShop = afterShop;
	}
	public List<FileVo> getImages() {
		return images;
	}
	public void setImages(List<FileVo> images) {
		this.images = images;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	
	
}
