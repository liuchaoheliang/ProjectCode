package com.froad.enums;

/**
 * 营销平台活动状态
 * @author yefeifei
 *
 */
public enum ActiveIdCode {
    
	MJ("MJ","满减"),
    MZ("MZ","满赠"),
    HB("HB","红包"),
    ZC("ZC","注册送");
	
    private String code;
    
    private String describe;
    
    private ActiveIdCode(String code,String describe){
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
    public static ActiveIdCode getType(String code){
        for(ActiveIdCode type : ActiveIdCode.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
