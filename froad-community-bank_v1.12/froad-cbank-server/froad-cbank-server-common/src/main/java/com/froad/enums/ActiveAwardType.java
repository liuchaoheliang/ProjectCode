package com.froad.enums;

/**
 * 营销平台活动类型
 * @author yefeifei
 *
 */
public enum ActiveAwardType {
    //奖励方式1满减2红包3实物4.联盟积分5.银行积分
	fullCut("1","满减活动"),
    vouchers("2","红包"),
    product("3","实物"),
    unionIntegral("4","联盟积分"),
    bankIntegral("5","银行积分"),
    ;
	
    private String code;
    
    private String describe;
    
    private ActiveAwardType(String code,String describe){
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
    public static ActiveAwardType getType(String code){
        for(ActiveAwardType type : ActiveAwardType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
