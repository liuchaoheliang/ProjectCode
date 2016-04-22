package com.froad.enums;

/**
 * 发货状态
 */
public enum ShippingStatus {

	unshipped("0","未发货"),
	
	shipped("1","已发货"),
	
	receipt("2","已收货"),
	
	untake("3","未提货"),
	
	token("4","已提货"),
	
	shipping("5", "出货中");
	
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
    
    /**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static ShippingStatus getType(String code){
    	for(ShippingStatus type : ShippingStatus.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}

}
