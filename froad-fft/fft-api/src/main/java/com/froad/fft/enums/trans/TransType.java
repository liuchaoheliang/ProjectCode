package com.froad.fft.enums.trans;

/**
 * 交易类型
 * @author FQ
 *
 */
public enum TransType {

	points_exchange("01","积分兑换"),
	group("02","团购交易"),
	points_rebate("03","积分返利"),
	points_withdraw("04","积分提现"),
	collect("05","收款"),
	present_points("06","送积分"),
	check_in("07","用户签到送积分"),
	presell("08","预售交易");
	
	private String code;
	
	private String describe;
	
	private TransType(String code,String describe){
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
