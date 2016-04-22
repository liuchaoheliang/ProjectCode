package com.froad.cbank.coremodule.normal.boss.enums;

public enum PlatType {
    
    boss("1","boss"),
    bank("2","银行"),
    merchant("3","商户");
    
    private String code;
    
    private String describe;
    
    private PlatType(String code,String describe){
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
    public static PlatType getType(String code){
        for(PlatType type : PlatType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
