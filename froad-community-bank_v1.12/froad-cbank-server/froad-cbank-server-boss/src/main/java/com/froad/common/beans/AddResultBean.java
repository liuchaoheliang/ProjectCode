package com.froad.common.beans;

public class AddResultBean {

    /**
     * 结果码
     */
    private String code;
    
    /**
     * 结果码描述信息
     */
    private String message;
    
    /**
     * 新加商品返回的shangpinid或其他表id
     */
    private String id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
