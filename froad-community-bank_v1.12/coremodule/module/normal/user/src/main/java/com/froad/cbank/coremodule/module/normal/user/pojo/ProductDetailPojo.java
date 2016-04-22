package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 	商品详情返回结果体
 * @author liuchao
 *
 */
public class ProductDetailPojo {
	
	/**
	 * 商品图片pojo
	 * @author liuchao
	 *
	 */
	public class ImageInfo{
		private String title;
		private String source;
		private String large;
		private String medium;
		private String thumbnail;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getLarge() {
			return large;
		}
		public void setLarge(String large) {
			this.large = large;
		}
		public String getMedium() {
			return medium;
		}
		public void setMedium(String medium) {
			this.medium = medium;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
	}
	
	
	/**
	 * 商户门店
	 * @author liuchao
	 *
	 */
	public class MerchantOutlet{
		
		private String outletId;
		private String outletName;
		private String address;
		private String phone;
		private String merchantId;
		
		public String getOutletId() {
			return outletId;
		}
		public void setOutletId(String outletId) {
			this.outletId = outletId;
		}
		public String getOutletName() {
			return outletName;
		}
		public void setOutletName(String outletName) {
			this.outletName = outletName;
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
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		
	}
	
	public class ProductActivitie{
		  /**
		   * 活动Id
		   */
		  private String activitiesId; // required
		  /**
		   * 活动类型:0-VIP价,1-赠送积分
		   */
		  private String activitiesType; // required
		  /**
		   * 当活动类型为赠送积分时使用points字段
		   */
		  private int points; // optional
		  /**
		   * 当活动类型为VIP价时vip_price字段为json字符串，格式为{0:xx,1:xx,2:xx}分别表示vip等级以及对应的价格
		   */
		  private String vipPrice; // optional
		public String getActivitiesId() {
			return activitiesId;
		}
		public void setActivitiesId(String activitiesId) {
			this.activitiesId = activitiesId;
		}
		public String getActivitiesType() {
			return activitiesType;
		}
		public void setActivitiesType(String activitiesType) {
			this.activitiesType = activitiesType;
		}
		public int getPoints() {
			return points;
		}
		public void setPoints(int points) {
			this.points = points;
		}
		public String getVipPrice() {
			return vipPrice;
		}
		public void setVipPrice(String vipPrice) {
			this.vipPrice = vipPrice;
		}
		  
	}
	
	
	private String clientId;
	private String merchantId ;
	private String orgCode ;
	private String productId;
	private String type;
	private String deliveryOption;
	private String name;
	private String fullName;
	private String price ;
	private String marketPrice;
	private String store;
	private String sellCount;
	private Boolean isLimit  ;
	private String briefIntroduction ;
	private String introduction;
	private String buyKnow ;
	private String startTime ;
	private String endTime ;
	private String expireStartTime ;
	private String expireEndTime ;
	private String trueBuyerNumber ;
	private String deliveryStartTime ;
	private String deliveryEndTime ;
	private String clusteringNumber	 ;
	private String virtualBuyerNumber;
	private String merchantName;
	private String phone;
	private Long serverTime;
	private  int num;
	private  int vipNum;
	private  int totalNum;
	private String isSeckill;
	private String secStore;
	private String secStartTime;
	private String secEndTime;
	private String orgName;
	private int outletNum;
	public String isMarketable; 
	/**
	 * 运费
	 */
	private double deliveryMoney;
	private List<ProductActivitie>  productActivities;
	private List<MerchantOutlet> merchantOutletList ;
	private List<ImageInfo> imageInfoList ;
	private  ProductBuyLimitPojo buyLimit;

	private Boolean isCollected;//是否已收藏
	
	
	/**
	   * 是否参与送积分活动
	   */
	  public boolean isPoint; // required
	  /**
	   * 是否参与VIP规则活动
	   */
	  public boolean isVip; // required
	  /**
	   * VIP价
	   */
	  public double vipPrice; // required
	  
	/**
	 * activePojo:关联的活动信息
	 */
	private List<FindActivePojo> activePojo = new ArrayList<FindActivePojo>();

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ProductBuyLimitPojo getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(ProductBuyLimitPojo buyLimit) {
		this.buyLimit = buyLimit;
	}
	public List<MerchantOutlet> getMerchantOutletList() {
		return merchantOutletList;
	}
	public void setMerchantOutletList(List<MerchantOutlet> merchantOutletList) {
		this.merchantOutletList = merchantOutletList;
	}
	public List<ImageInfo> getImageInfoList() {
		return imageInfoList;
	}
	public void setImageInfoList(List<ImageInfo> imageInfoList) {
		this.imageInfoList = imageInfoList;
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
	public String getClusteringNumber() {
		return clusteringNumber;
	}
	public void setClusteringNumber(String clusteringNumber) {
		this.clusteringNumber = clusteringNumber;
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
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
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
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

	public Boolean getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
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
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
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
	public String getTrueBuyerNumber() {
		return trueBuyerNumber;
	}
	public void setTrueBuyerNumber(String trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}
	public String getVirtualBuyerNumber() {
		return virtualBuyerNumber;
	}
	public void setVirtualBuyerNumber(String virtualBuyerNumber) {
		this.virtualBuyerNumber = virtualBuyerNumber;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Boolean getIsCollected() {
		return isCollected;
	}
	public void setIsCollected(Boolean isCollected) {
		this.isCollected = isCollected;
	}
	public Long getServerTime() {
		return serverTime;
	}
	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getVipNum() {
		return vipNum;
	}
	public void setVipNum(int vipNum) {
		this.vipNum = vipNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public double getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(double deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public List<ProductActivitie> getProductActivities() {
		return productActivities;
	}
	public void setProductActivities(List<ProductActivitie> productActivities) {
		this.productActivities = productActivities;
	}

	public String getIsSeckill() {
		return isSeckill;
	}
	public void setIsSeckill(String isSeckill) {
		this.isSeckill = isSeckill;
	}
	public String getSecStore() {
		return secStore;
	}
	public void setSecStore(String secStore) {
		this.secStore = secStore;
	}
	public String getSecStartTime() {
		return secStartTime;
	}
	public void setSecStartTime(String secStartTime) {
		this.secStartTime = secStartTime;
	}
	public String getSecEndTime() {
		return secEndTime;
	}
	public void setSecEndTime(String secEndTime) {
		this.secEndTime = secEndTime;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getOutletNum() {
		return outletNum;
	}
	public void setOutletNum(int outletNum) {
		this.outletNum = outletNum;
	}
	public boolean isIsPoint() {
		return isPoint;
	}
	public void setIsPoint(boolean isPoint) {
		this.isPoint = isPoint;
	}
	public boolean isIsVip() {
		return isVip;
	}
	public void setIsVip(boolean isVip) {
		this.isVip = isVip;
	}
	public double getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public boolean isPoint() {
		return isPoint;
	}
	public void setPoint(boolean isPoint) {
		this.isPoint = isPoint;
	}
	public boolean isVip() {
		return isVip;
	}
	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}
	public List<FindActivePojo> getActivePojo() {
		return activePojo;
	}
	public void setActivePojo(List<FindActivePojo> activePojo) {
		this.activePojo = activePojo;
	}
	
}
