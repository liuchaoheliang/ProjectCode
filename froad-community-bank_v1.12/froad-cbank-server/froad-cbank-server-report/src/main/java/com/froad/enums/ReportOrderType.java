package com.froad.enums;

/**
 * 报表订单类型
 *
 */
public enum ReportOrderType {

	face2face("0","面对面", "面对面", "直接优惠"),
    group("1","团购", "团购", "特惠商户"),
    presell("2","精品预售", "精品预售", ""),
    special("3","名优特惠", "名优特惠", "名优特惠"),
    onlinePoint("4","线上积分兑换", "线上积分兑换", ""),
    dotGift("5","线下积分兑换", "线下积分兑换", "");
    
    private String code;
    
    private String orderDesc;
    
    private String productDesc;
    
    private String merchantDesc;
    
    private ReportOrderType(String code,String orderDesc, String productDesc, String merchantDesc){
        this.code=code;
        this.setOrderDesc(orderDesc);
        this.setProductDesc(productDesc);
        this.setMerchantDesc(merchantDesc);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return this.code;
    }
    
    /**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static ReportOrderType getByType(String code){
    	for(ReportOrderType type : ReportOrderType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	/**
	 * @return the merchantDesc
	 */
	public String getMerchantDesc() {
		return merchantDesc;
	}

	/**
	 * @param merchantDesc the merchantDesc to set
	 */
	public void setMerchantDesc(String merchantDesc) {
		this.merchantDesc = merchantDesc;
	}
    
}
