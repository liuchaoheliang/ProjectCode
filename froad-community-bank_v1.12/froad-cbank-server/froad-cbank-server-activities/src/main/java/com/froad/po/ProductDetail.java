package com.froad.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品详情
 * 
 * @ClassName: ProductDetail
 * @Description: TODO
 * @author share 2015年3月21日
 * @modify share 2015年3月21日
 */
public class ProductDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSONField(name = "client_id")
	private String clientId;
	@JSONField(name = "_id")
	private String productId;
	@JSONField(name = "merchant_id")
	private String merchantId;

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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
