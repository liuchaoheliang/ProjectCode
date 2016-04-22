package com.froad.cbank.coremodule.normal.boss.enums;

public enum Platform {
    
    boss("boss","运营支撑平台"),
    bank("bank","银行管理平台"),
    merchant("merchant","商户管理平台");
    
    private String code;
    
    private String describe;
    
    private Platform(String code,String describe){
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
    public static Platform getType(String code){
        for(Platform type : Platform.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
