package com.froad.cbank.coremodule.module.normal.user.pojo;

import java.util.List;


	/**
	 * 类描述：特惠商品POJO
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年7月3日 下午1:23:28 
	 */
public class GroupProductPojo {
	/**
	   * 客户端id
	   */
	  private String clientId; // required
	  /**
	   * 门店ID
	   */
	  private String outletId; // required
	  /**
	   * 店名
	   */
	  private String outletName; // required
	  /**
	   * 门店地址
	   */
	  private String address; // required
	  /**
	   * 门店评论星级
	   */
	  private String starLevel; // required
	  /**
	   * 距离
	   */
	  private double distance; // required
	  /**
	   * 商户ID
	   */
	  private String merchantId; // required
	  /**
	   * 商户名
	   */
	  private String merchantName; // required
	  
	  
	  private List<ProductListPojo> productList;
	  
	  


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


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


	public String getStarLevel() {
		return starLevel;
	}


	public void setStarLevel(String starLevel) {
		this.starLevel = starLevel;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
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


	public List<ProductListPojo> getProductList() {
		return productList;
	}


	public void setProductList(List<ProductListPojo> productList) {
		this.productList = productList;
	}

	 
}
