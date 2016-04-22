package com.froad.po.resp;


public class Resp {
	private BaseResp base;

	private AmountQuota amountLat;

	private OrderQuota orderLat;

	private MemberQuota memberLat;

	private MerchantInfoResp merchantInfoResp;

	private ProductInfoResp productInfoResp;

	public BaseResp getBase() {
		return base;
	}

	public void setBase(BaseResp base) {
		this.base = base;
	}

	public AmountQuota getAmountLat() {
		return amountLat;
	}

	public void setAmountLat(AmountQuota amountLat) {
		this.amountLat = amountLat;
	}

	public OrderQuota getOrderLat() {
		return orderLat;
	}

	public void setOrderLat(OrderQuota orderLat) {
		this.orderLat = orderLat;
	}

	public MemberQuota getMemberLat() {
		return memberLat;
	}

	public void setMemberLat(MemberQuota memberLat) {
		this.memberLat = memberLat;
	}

	public MerchantInfoResp getMerchantInfoResp() {
		return merchantInfoResp;
	}

	public void setMerchantInfoResp(MerchantInfoResp merchantInfoResp) {
		this.merchantInfoResp = merchantInfoResp;
	}

	public ProductInfoResp getProductInfoResp() {
		return productInfoResp;
	}

	public void setProductInfoResp(ProductInfoResp productInfoResp) {
		this.productInfoResp = productInfoResp;
	}


	
	

}
