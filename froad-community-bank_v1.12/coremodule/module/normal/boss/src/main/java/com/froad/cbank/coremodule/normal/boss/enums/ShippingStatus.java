package com.froad.cbank.coremodule.normal.boss.enums;

/**
 * 发货状态
 */
public enum ShippingStatus {

	unshipped("0","未发货"),
	
	shipped("1","已发货"),
	
	receipt("2","已收货"),
	
	untake("3","未提货"),
	
	token("4","已提货");
	
    private String code;
    private String describe;

    private ShippingStatus(String code, String describe)
    {
        this.code = code;
        this.describe = describe;
    }

    public String getCode()
    {
        return code;
    }

    public String getDescribe()
    {
        return describe;
    }

    @Override
    public String toString()
    {
        return this.code;
    }

}
