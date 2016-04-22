package com.froad.cbank.coremodule.module.normal.bank.vo.report;


public class ReprotResp {
	
	private BaseRespVo base;
	
	private AmountQuotaResp amountLat;
	
	private OrderQuotaResp orderLat;
	
	private MemberQuotaResp memberLat;

	private MerchantInfoResp merchantLat;

	private ProductInfoResp productLat;

	public BaseRespVo getBase() {
		return base;
	}

	public void setBase(BaseRespVo base) {
		this.base = base;
	}

	public AmountQuotaResp getAmountLat() {
		return amountLat;
	}

	public void setAmountLat(AmountQuotaResp amountLat) {
		this.amountLat = amountLat;
	}

	public OrderQuotaResp getOrderLat() {
		return orderLat;
	}

	public void setOrderLat(OrderQuotaResp orderLat) {
		this.orderLat = orderLat;
	}

	public MemberQuotaResp getMemberLat() {
		return memberLat;
	}

	public void setMemberLat(MemberQuotaResp memberLat) {
		this.memberLat = memberLat;
	}

	public MerchantInfoResp getMerchantLat() {
		return merchantLat;
	}

	public void setMerchantLat(MerchantInfoResp merchantLat) {
		this.merchantLat = merchantLat;
	}

	public ProductInfoResp getProductLat() {
		return productLat;
	}

	public void setProductLat(ProductInfoResp productLat) {
		this.productLat = productLat;
	}

	
	
 
}
