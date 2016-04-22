package com.froad.po.shoppingcart.req;


public class ShoppingCartReq {

	private long memberCode; 
	private String clientId; 
	private String merchantId; 
	private String productId; 
	private String outletId; 
	private int num; 
	private int vipLevel;
	
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
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
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	} 
}

