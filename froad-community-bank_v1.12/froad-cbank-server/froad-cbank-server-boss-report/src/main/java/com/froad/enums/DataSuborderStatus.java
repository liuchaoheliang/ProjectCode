package com.froad.enums;

/**
 * OrderStatus
 */
public enum DataSuborderStatus {

	unpaid("11","未支付"),
	paid("12","已支付"),
	refunded("21","已退款"),
    ;
    
    private String code;
    
    private String description;
    
    private DataSuborderStatus(String code,String description){
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
