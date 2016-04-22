package com.froad.fft.persistent.common.enums;

/**
 * 玩法
 * 
 * @author FQ
 * 
 */
public enum LotteryPlayType {

	directly("1", "直选"), 
	group_3("2", "组选3  适合3D、排列三"), 
	group_6("3","组选6  适合3D、排列三"), 
	group("8", "组选");

	private String code;
	private String describe;

	private LotteryPlayType(String code, String describe) {
		this.code = code;
		this.describe = describe;
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
