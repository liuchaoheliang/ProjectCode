package com.froad.enums;

/**
 * 营销平台活动类型
 * @author yefeifei
 *
 */
public enum ActiveType {
    
	fullCut("1","满减活动"),
    fullGive("0","满送"),
    vouchers("3","代金券"),
    discount("4","打折促销"),
    firstPayment("7","首单满减"),
    registerGive("6","注册送");
	
    private String code;
    
    private String describe;
    
    private ActiveType(String code,String describe){
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
    public static ActiveType getType(String code){
        for(ActiveType type : ActiveType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
