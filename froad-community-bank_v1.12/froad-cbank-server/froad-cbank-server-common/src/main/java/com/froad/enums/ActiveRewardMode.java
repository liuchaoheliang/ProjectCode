package com.froad.enums;

public enum ActiveRewardMode {

	vouchers("1", "红包"), entity("2", "实物"), bankintegral("3", "银行积分"), unionintegral(
			"4", "联盟积分");

	private String code;

	private String describe;

	private ActiveRewardMode(String code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	@Override
	public String toString() {
		return this.code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/**
	 * 通过code取得类型
	 * 
	 * @param code
	 * @return
	 */
	public static ActiveRewardMode getType(String code) {
		for (ActiveRewardMode type : ActiveRewardMode.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
	}
}
