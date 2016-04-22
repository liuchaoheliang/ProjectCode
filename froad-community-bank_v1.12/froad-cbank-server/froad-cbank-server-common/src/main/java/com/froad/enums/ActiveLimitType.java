package com.froad.enums;

/**
 * 营销平台活动限制类型
 * @author yefeifei
 *
 */
public enum ActiveLimitType {
    
	Notlimited("0","不限制"),
    limitMerchant("1","限制商户"),
    limitMendian("2","限制门店"),
    limitGoods("3","限制商品"),
	undefined("4","未定义");
	
    private String code;
    
    private String describe;
    
    private ActiveLimitType(String code,String describe){
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
    public static ActiveLimitType getType(String code){
        for(ActiveLimitType type : ActiveLimitType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
