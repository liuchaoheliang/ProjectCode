package com.froad.enums;

/**
 * 订单类型
 * 
 * @author Arron
 * 
 */
public enum OperType {

    /**
     * 查询
     */
    QUERY("1", "查询"),
    /**
     * 下载
     */
    DOWN("2", "下载");

    private String code;
    private String describe;

    private OperType(String code, String describe) {
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
