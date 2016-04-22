package com.froad.enums;

/**
 *  结算类型
  * @ClassName: Settlement
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public enum SettlementType {
	
	group("1","团购"),
	
	special("2","名优特惠"),
	
	face2face("3","面对面");
	
    private String code;
    private String describe;

    private SettlementType(String code, String describe)
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
    
    public static SettlementType getSettlementByCode(String code){
    	if (code == null)
			return null;
    	for(SettlementType value : SettlementType.values()){
    		if(value.getCode().equals(code)){
    			return value;
    		}
    	}
    	return null;
    }

	public static String getDescription(String code) {
		SettlementType status = getSettlementByCode(code);
		return status == null ? null : status.getDescribe();
	}

    @Override
    public String toString()
    {
        return this.code;
    }
}

