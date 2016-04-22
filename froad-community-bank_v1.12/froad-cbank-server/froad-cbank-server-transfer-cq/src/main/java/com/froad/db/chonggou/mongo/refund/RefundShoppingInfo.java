package com.froad.db.chonggou.mongo.refund;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class RefundShoppingInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 原子订单ID
	 */
	private String subOrderId = null;
	/**
	 * 原子订单类型
	 */
	private String type = null;
	/**
	 * 商户ID
	 */
	private String merchantId = null;
	/**
	 * 商户名称
	 */
	private String merchantName = null;
	/**
	 * 商品列表
	 */
	private List<RefundProduct> products = null;

	/**
	 * @return the products
	 */
	@JSONField(name="products")
	public List<RefundProduct> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	@JSONField(name="products")
	public void setProducts(List<RefundProduct> products) {
		this.products = products;
	}

	/**
	 * @return the subOrderId
	 */
	@JSONField(name="sub_order_id")
	public String getSubOrderId() {
		return subOrderId;
	}

	/**
	 * @param subOrderId the subOrderId to set
	 */
	@JSONField(name="sub_order_id")
	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	/**
	 * @return the type
	 */
	@JSONField(name="type")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	@JSONField(name="type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the merchantId
	 */
	@JSONField(name="merchant_id")
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	@JSONField(name="merchant_id")
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @return the merchantName
	 */
	@JSONField(name="merchant_name")
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	@JSONField(name="merchant_name")
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
