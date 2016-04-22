package com.froad.enums;


/**
 * 配送方式
 */
public enum DeliveryType {
	
	home("0","送货上门"),
	
	take("1","网点自提"),

	home_or_take("2","配送或自提");
	
    private String code;
    private String describe;

    private DeliveryType(String code, String describe)
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
    public static DeliveryType getType(String code){
    	for(DeliveryType type : DeliveryType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}

}
