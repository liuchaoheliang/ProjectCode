package com.froad.enums;

/**
 * 活动类型枚举
 * 
 * @author dongrui
 * @version $Id: ActivitiesType.java, v 0.1 2014年12月24日 上午9:33:24 Exp $
 */
public enum ClusterType {

    /** 自动成团  */
    auto("0", "自动成团"),

    /**手动成团 */
    manual("1", "手动成团");

    private String code;
    private String describe;

    private ClusterType(String code, String describe) {
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
