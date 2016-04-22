package com.froad.enums;

/**
 * 营销平台 ActiveItemType
 * @author yefeifei
 *
 */
public enum VouchersStatus {
    init("0","初始化"),
    using("1","使用中"),
    paySuccess("2","支付成功");
    
    private String code;
    
    private String describe;
    
    private VouchersStatus(String code,String describe){
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
    public static VouchersStatus getType(String code){
        for(VouchersStatus type : VouchersStatus.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
