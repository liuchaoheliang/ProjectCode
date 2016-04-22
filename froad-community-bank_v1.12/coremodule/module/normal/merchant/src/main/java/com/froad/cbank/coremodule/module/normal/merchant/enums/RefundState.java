package com.froad.cbank.coremodule.module.normal.merchant.enums;


/**
 * 退款状态
 */
public enum RefundState {

	REFUND_INIT("1","待处理"),
	REFUND_PROCESSING("2","退款中"),
	REFUND_SUCCESS("3","退款完成"),
	REFUND_FAILED("4","退款失败"),
	REFUND_MANUAL_SUCCESS("5","异常处理完成"),
	REFUND_AUDIT_PROCESSING("6","商户审核中"),
	REFUND_AUDIT_REJECTED("7","商户审核拒绝"),
	REFUND_AUDIT_PASSED("8","商户审核通过");
	
	private String code;
	
	private String description;
	
	private RefundState(String code,String description){
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
