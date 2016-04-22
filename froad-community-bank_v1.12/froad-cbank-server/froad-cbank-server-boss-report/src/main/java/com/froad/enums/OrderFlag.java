package com.froad.enums;

/**
 * OrderFlag
 */
public enum OrderFlag {

	place("1","下单"),
    refund("2","退单"),
    ;
    
    private String code;
    
    private String description;
    
    private OrderFlag(String code,String description){
        this.code=code;
        this.description=description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return this.code;
    }
    
}
