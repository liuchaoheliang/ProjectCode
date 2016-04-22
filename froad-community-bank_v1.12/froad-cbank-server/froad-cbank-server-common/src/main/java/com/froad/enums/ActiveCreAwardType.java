package com.froad.enums;

/**
 * 营销平台活动类型
 * @author yefeifei
 *
 */
public enum ActiveCreAwardType {
    
	vouchers("0","红包"),
    product("1","实物"),
    ;
	
    private String code;
    
    private String describe;
    
    private ActiveCreAwardType(String code,String describe){
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
    public static ActiveCreAwardType getType(String code){
        for(ActiveCreAwardType type : ActiveCreAwardType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
