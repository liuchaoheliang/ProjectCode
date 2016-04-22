package com.froad.enums;


/**
 * 退款来源
 */
public enum RefundResource {

	USER_REFUND("1","用户申请退款"),
	SYSTEM_REFUND("2","系统自动退款");
	
	private String code;
	
	private String description;
	
	private RefundResource(String code,String description){
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
