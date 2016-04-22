package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;

/**
 * 秒杀商品详情返回结果体
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:55:42
 * @version v1.0
 */
public class SeckillProductDetailPojo {

	/**
	 * 商品图片pojo
	 * 
	 * @author wangzhangxu
	 */
	public class ImageInfo {
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
	 * 
	 * @author wangzhangxu
	 *
	 */
	public class MerchantOutlet {

		private String outletId;
		private String outletName;
		private String address;

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

	}

	private String clientId;
	private String merchantId;
	private String productId;
	private String type;
	private String deliveryOption;
	private String name;
	private String fullName;
	private String price;
	private String vipPrice;
	private String marketPrice;
	private String deliveryMoney;
	private String store;
	private String buyLimit;
	private String briefIntroduction;
	private String introduction;
	private String buyKnow;
	private String startTime;
	private String endTime;
	private String trueBuyerNumber;
	private String merchantName;
	private String phone;
	private Long serverTime;
	
	private List<MerchantOutlet> merchantOutletList;
	private List<ImageInfo> imageInfoList;

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

	public String getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(String deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}
	
	public String getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(String buyLimit) {
		this.buyLimit = buyLimit;
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
	
	public String getTrueBuyerNumber() {
		return trueBuyerNumber;
	}

	public void setTrueBuyerNumber(String trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getServerTime() {
		return serverTime;
	}

	public void setServerTime(Long serverTime) {
		this.serverTime = serverTime;
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

}
