package com.froad.enums;

public enum ExceptionOrderType {

	AUTO_REFUND_POINT_FAILED("00","组合支付，现金支付失败，系统自动退还积分失败"),
	AUTO_PRESENT_POINT_FAILED("01","自动赠送积分失败"),
	REFUND_POINT_FAILED("02","用户主动退积分，现金成功积分失败"),
	REFUND_PRESENT_POINT_FAILED("03","扣除已赠送的积分失败");
	
	
	private String code;
	private String desc;
	
	public String getCode() {
		return code;
	}
	
	public String getDesc(){
		return desc;
	}
	
	private ExceptionOrderType(String code,String desc) {
        this.code = code;
        this.desc = desc;
    }
	
	@Override
	public String toString(){
		return this.code;
	}
}
