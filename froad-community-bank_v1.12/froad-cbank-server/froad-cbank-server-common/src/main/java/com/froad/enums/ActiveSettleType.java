package com.froad.enums;

/**
 * 营销平台活动结算方式
 * @author yefeifei
 *
 */
public enum ActiveSettleType {
    
	pending("0","实时结算"),
    audit("1","延期结算");
	
    private String code;
    
    private String describe;
    
    private ActiveSettleType(String code,String describe){
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
    public static ActiveSettleType getType(String code){
        for(ActiveSettleType type : ActiveSettleType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
