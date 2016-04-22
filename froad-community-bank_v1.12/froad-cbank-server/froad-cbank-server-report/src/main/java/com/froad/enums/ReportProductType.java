package com.froad.enums;

/**
 * 商品类型
 * @author wangyan
 *
 */
public enum ReportProductType {
	
	face("0","面对面"),
    group("1","团购"),
    presell("2","预售"),
    special("3","名优特惠"),
    onlinePoint("4","线上积分兑换"),
    dotGift("5","线下积分兑换");
    
    
    private String code;
    
    private String describe;
    
    private ReportProductType(String code,String describe){
        this.code=code;
        this.describe=describe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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
    public static ReportProductType getType(String code){
    	for(ReportProductType type : ReportProductType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
}
