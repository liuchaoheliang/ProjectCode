package com.froad.cbank.coremodule.module.normal.user.enums;

/**
 * 发货状态
 */
public enum ShippingStatus {

	unshipped("0","未发货"),
	
	shipped("1","已发货"),
	
	receipt("2","已收货"),
	
	untake("3","未提货"),
	
	token("4","已提货"),
	
	shipping("5","出货中");
	
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

    public static ShippingStatus getShippingStatus(String code){
    	for(ShippingStatus s : ShippingStatus.values()){
    		if(s.getCode().equals(code)){
    			return s;
    		}
    	}
    	return null;
    }
}
