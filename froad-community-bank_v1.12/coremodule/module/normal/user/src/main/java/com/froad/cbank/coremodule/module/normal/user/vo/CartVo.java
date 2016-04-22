package com.froad.cbank.coremodule.module.normal.user.vo;

import java.io.Serializable;


/** 
 * 购物车入参类
 * @ClassName: CartVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015年4月11日 下午4:08:10 
 */ 
public class CartVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1041662721146931551L;
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @Create Author: hjz
	 * @date 2015年4月11日 下午4:08:07
	 * 含义 TODO
	 */
	
	private String merchantId;
	private String productId;
	private String outletId;
	private Integer num;
	private String[] productIds; 
	private String type;
	private String deliveryOption;//提货方式  0 配送， 1 自提  
	
	
	
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
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String[] getProductIds() {
		return productIds;
	}
	public void setProductIds(String[] productIds) {
		this.productIds = productIds;
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

}
