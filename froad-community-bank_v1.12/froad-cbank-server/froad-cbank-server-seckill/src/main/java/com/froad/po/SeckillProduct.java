package com.froad.po;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.po.mongo.ProductDetail;

/**
 * 秒杀商品
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午3:58:35
 * @version v1.0
 */
public class SeckillProduct implements Serializable {
	
	private static final long serialVersionUID = 6378775862271627364L;
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 商户ID
	 */
	private String merchantId;
	
	/**
	 * 商品ID
	 */
	private String productId;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 商品类型
	 */
	private String productType;
	
	/**
	 * 商品图片
	 */
	private String productImage;
	
	/**
	 * 商品配送方式
	 */
	private String deliveryOption;
	
	/**
	 * 秒杀库存数量
	 */
	private Integer store;
	
	/**
	 * 秒杀价
	 */
	private Integer secPrice;
	
	/**
	 * VIP秒杀价
	 */
	private Integer vipSecPrice;
	
	/**
	 * 商品运费
	 */
	private Integer deliveryMoney;
	
	/**
	 * 秒杀开始时间
	 */
	private Date startTime;
	
	/**
	 * 秒杀结束时间
	 */
	private Date endTime;
	
	/**
	 * 秒杀单用户限购
	 */
	private Integer buyLimit;
	
	/**
	 * 上线状态(0-未上架,1-已上架,2-已下架,3-已删除)
	 */
	private String isMarketable;
	
	public SeckillProduct(){}
	
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getSecPrice() {
		return secPrice;
	}

	public void setSecPrice(Integer secPrice) {
		this.secPrice = secPrice;
	}

	public Integer getVipSecPrice() {
		return vipSecPrice;
	}

	public void setVipSecPrice(Integer vipSecPrice) {
		this.vipSecPrice = vipSecPrice;
	}

	public Integer getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(Integer deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(Integer buyLimit) {
		this.buyLimit = buyLimit;
	}

	public String getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	
	public Map<String, String> toMap(){
		Map<String, String> productMap = new HashMap<String, String>();
		productMap.put("merchant_id", ObjectUtils.toString(merchantId));
		productMap.put("store", ObjectUtils.toString(store));
		productMap.put("sec_price", ObjectUtils.toString(secPrice));
		productMap.put("vip_sec_price", ObjectUtils.toString(vipSecPrice));
		productMap.put("start_time", ObjectUtils.toString(startTime.getTime()));
		productMap.put("end_time", ObjectUtils.toString(endTime.getTime()));
		productMap.put("buy_limit", ObjectUtils.toString(buyLimit));
		productMap.put("is_marketable", ObjectUtils.toString(isMarketable));
		return productMap;
	}
	
	/**
	 * 解析为秒杀商品对象
	 * 
	 * @param seckillProductMap 秒杀商品信息
	 * @param productMap 基本商品信息
	 * 
	 * @return SeckillProduct
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午3:58:35
	 */
	public static SeckillProduct parseMap(Map<String, String> seckillProductMap, ProductDetail productDetail){
		SeckillProduct product = null;
		if (seckillProductMap != null) {
			product = new SeckillProduct();
			product.setMerchantId(seckillProductMap.get("merchant_id"));
			product.setStore(Integer.parseInt(seckillProductMap.get("sec_store")));
			product.setSecPrice(Integer.parseInt(seckillProductMap.get("sec_price")));
			product.setVipSecPrice(Integer.parseInt(seckillProductMap.get("vip_sec_price")));
			product.setStartTime(new Date(Long.parseLong(seckillProductMap.get("start_time"))));
			product.setEndTime(new Date(Long.parseLong(seckillProductMap.get("end_time"))));
			product.setBuyLimit(Integer.parseInt(seckillProductMap.get("buy_limit")));
			product.setIsMarketable(seckillProductMap.get("is_marketable"));
			
			if (productDetail != null) {
				product.setProductName(productDetail.getName());
				product.setProductType(productDetail.getProductType());
				if (productDetail.getImageInfo() != null && productDetail.getImageInfo().size() != 0) {
					product.setProductImage(productDetail.getImageInfo().get(0).getThumbnail());
				}
				product.setDeliveryOption(productDetail.getDeliveryOption());
			}
		}
		
		return product;
	}
	
}
