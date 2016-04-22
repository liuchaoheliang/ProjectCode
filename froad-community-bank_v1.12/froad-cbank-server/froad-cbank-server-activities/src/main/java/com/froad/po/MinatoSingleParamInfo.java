package com.froad.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MinatoSingleParamInfo implements Serializable {

	/**
	 * 请求id
	 */
	private String reqId;
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 商品id
	 */
	private String productId;

	/**
	 * 活动ID
	 */
	private String activeId;

	/**
	 * 区域id
	 */
	private String areaId;
	/**
	 * cookie id
	 */
	private String cookieId;
	
	/**
	 * 商品类型
	 */
	private String productType;
	/**
	 * 凑单坐标
	 */
	private MinatoSingleLocationInfo minatoSingleLocation;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCookieId() {
		return cookieId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public MinatoSingleLocationInfo getMinatoSingleLocation() {
		return minatoSingleLocation;
	}

	public void setMinatoSingleLocation(
			MinatoSingleLocationInfo minatoSingleLocation) {
		this.minatoSingleLocation = minatoSingleLocation;
	}
}
