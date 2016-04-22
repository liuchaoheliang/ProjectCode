package com.froad.enums;

/**
 * 营销平台 ActiveItemType
 * @author yefeifei
 *
 */
public enum ActiveItemType {
    //活动类型：1商户活动；2门店活动；3商品活动
    merchant("1","商户"),
    portal("2","门户"),
    goods("3","商品"),
    unLimint("0","不限"),
    undefined("4","未定义");
    
    private String code;
    
    private String describe;
    
    private ActiveItemType(String code,String describe){
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
    public static ActiveItemType getType(String code){
        for(ActiveItemType type : ActiveItemType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
