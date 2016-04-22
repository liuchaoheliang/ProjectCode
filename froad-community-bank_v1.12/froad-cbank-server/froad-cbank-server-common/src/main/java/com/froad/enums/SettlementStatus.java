package com.froad.enums;

/**
 *  结算状态
  * @ClassName: Settlement
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public enum SettlementStatus {
	
	unsettlemnt("0","未结算"),
	
	settlementing("1","结算中"),
	
	settlementsucc("2","结算成功"),
	
	settlementfailed("3","结算失败"),
	
	settlementNoInvalid("4","无效结算记录");
	
    private String code;
    private String describe;

    private SettlementStatus(String code, String describe)
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
    
    public static SettlementStatus getSettlementByCode(String code){
    	if(code == null) return null;
    	for(SettlementStatus value : SettlementStatus.values()){
    		if(value.getCode().equals(code)){
    			return value;
    		}
    	}
    	return null;
    }
    
    public static String getDescription(String code){
    	SettlementStatus status = getSettlementByCode(code);
		return status == null ? null : status.getDescribe();
    }

    @Override
    public String toString()
    {
        return this.code;
    }
}

