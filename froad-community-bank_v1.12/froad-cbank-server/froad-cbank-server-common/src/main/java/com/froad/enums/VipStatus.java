package com.froad.enums;

/**
 * VIP规则状态
 * @author wangyan
 *
 */
public enum VipStatus {
    
    ineffect("0","未生效"),
    effect("1","已生效"),
    canceled("2","已作废"),
    deleted("3","已删除");
    
    private String code;
    
    private String describe;
    
    private VipStatus(String code,String describe){
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
    public static VipStatus getType(String code){
        for(VipStatus type : VipStatus.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
