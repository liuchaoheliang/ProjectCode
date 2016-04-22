package com.froad.fft.persistent.common.enums;

/**
 * 支付表的支付状态
 * @author FQ
 *
 */
public enum PayState {
	
	pay_exception("9000","支付异常"),
	pay_cancel("9001","支付取消"),
	
	/**
	 * 金额和积分的支付状态
	 */
	pay_wait("1000","等待支付"),
	pay_request_success("1002","支付请求发送成功"),
	pay_request_fail("1003","支付请求发送失败"),
	pay_success("1004","支付成功"),
	pay_fail("1005","支付失败"),
	
	refund_request_success("1010","发送退款请求成功"),
	refund_request_fail("1011","发送退款请求失败"),
	refund_success("1012","退款或退分成功"),
	refund_fail("1013","退款或退分失败"),

	
	/**
	 * 话费充值
	 */
	hfcz_success("3000","话费充值成功"),
	hfcz_fail("3001","话费充值失败"),
	hfcz_request_success("3002","发送话费充值请求成功"),
	hfcz_request_fail("3003","发送话费充值请求失败"),
	
	/**
	 * 彩票
	 */
	lottery_success("4000","彩票购买成功-出票成功"),
	lottery_fail("4001","彩票购买失败"),
	lottery_request_success("4002","发送彩票请求成功-出票中"),
	lottery_request_fail("4003","发送彩票请求失败"),
	lottery_win("4004","彩票中奖-已中奖派奖中"),
	lottery_send("4005","已派奖");


	private String code;
	private String describe;
	
	private PayState(String code,String describe){
		this.code=code;
		this.describe=describe;
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
