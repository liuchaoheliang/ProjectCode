package com.froad.po.shoppingcart.req;

import java.util.List;

/**
 *  订单购物车请求信息
 * @author Administrator
 *
 */
public class OrderShoppingListReq {

	private long memberCode;			// 会员号
	
	private String clientId;				// 客户端ID
	
	private String merchantId;			// 商户ID
	
	private String productId;			// 商品ID

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
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

	
}
