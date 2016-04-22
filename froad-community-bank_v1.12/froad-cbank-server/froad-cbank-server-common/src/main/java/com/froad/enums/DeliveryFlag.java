package com.froad.enums;

public enum DeliveryFlag {

	/** 
     * 是否补全配送信息:
     */
    FILLED("0", "秒杀成功+已填信息"),

    TAKE("1", "秒杀成功+自提"),
    
    HOME("2", "秒杀成功+配送"),
    
    TAKE_HOME("3", "秒杀成功+自提、配送 ");

    private String code;
    private String describe;

    private DeliveryFlag(String code, String describe) {
        this.code = code;
        this.describe = describe;
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
}
