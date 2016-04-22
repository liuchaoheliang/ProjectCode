package com.froad.enums;


/**
 * 退款状态
 */
public enum SubOrderRefundState {

	REFUND_INIT("1","未退款"),
	REFUND_PROCESSING("2","退款中"),
	REFUND_SUCCESS("3","退款完成"),
	REFUND_PART("4","部分退款");
	
	private String code;
	
	private String description;
	
	private SubOrderRefundState(String code,String description){
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
