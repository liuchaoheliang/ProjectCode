package com.froad.fft.persistent.common.enums;

/**
 * 单复和合胆
 * 
 * @author FQ
 * 
 */
public enum LotteryNumType {

	simplex("1", "单式"), 
	compound("2", "复式"), 
	bale_no("3", "包号"), 
	future_value("4", "和值"), 
	bile_drag("5", "胆拖"), 
	nine_games("9", "9场"), 
	fourteen_games("14", "14场");

	private String code;
	private String describe;

	private LotteryNumType(String code, String describe) {
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
