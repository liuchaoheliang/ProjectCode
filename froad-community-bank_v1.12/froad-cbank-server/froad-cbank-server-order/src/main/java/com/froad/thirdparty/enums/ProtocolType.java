package com.froad.thirdparty.enums;

public enum ProtocolType {

	/** 消费积分 */
	consumPoints("consumPoints", "消费积分"),

	/** 退积分 */
	refundPoints("refundPoints", "退积分"),

	/** 兑充积分 */
	exchPoints("exchPoints", "兑充积分"),

	/** 赠送积分 */
	presentPoints("presentPoints", "赠送积分"),

	/** 积分卡充值 */
	cardDepositPoints("cardDepositPoints", "积分卡充值"),

	/** 积分转出 */
	donationAppPoints("donationAppPoints", "积分转出"),

	/** 积分转入 */
	donationCfPoints("donationCfPoints", "积分转入"),

	/** 积分转换 */
	payAndPresentPoints("payAndPresentPoints", "积分转换"),

	/** 积分提现 */
	cashapp("cashapp", "积分提现"),

	/** 积分冲正 */
	reversePoints("reversePoints", "积分冲正");

	public static ProtocolType valOf(String arg) {
		try {
			return valueOf(arg);
		} catch (Exception e) {
			return null;
		}
	}

	private String describe;

	private String code;

	private ProtocolType(String describe) {
		this.describe = describe;
	}

	private ProtocolType(String code, String describe) {
		this.setCode(code);
		this.describe = describe;
	}

	public String getDescribe() {
		return describe;
	}

	@Override
	public String toString() {
		return this.describe;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
