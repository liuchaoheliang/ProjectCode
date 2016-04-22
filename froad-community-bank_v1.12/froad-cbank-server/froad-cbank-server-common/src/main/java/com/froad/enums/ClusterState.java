package com.froad.enums;

/**
 * 预售成团状态
 * 
 * @author dongrui
 * @version $Id: ClusterState.java, v 0.1 2014年12月24日 下午1:55:55 Exp $
 */
public enum ClusterState {
    wait("0", "未成团"), success("1", "成团成功"), fail("2", "成团失败");

    private String code;
    private String describe;

    private ClusterState(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
