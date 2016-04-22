package com.froad.fft.persistent.common.enums;

/**
 * 彩种编码
 * @author FQ
 *
 */
public enum LotteryNo {
	
	FC_SSQ("FC_SSQ", "双色球"),
	FC_3D("FC_3D", "3D"),
	FC_QLC("FC_QLC", "七乐彩"),
	TC_CJDLR("TC_CJDLR", "大乐透"),
	TC_22_5("TC_22_5", "22选5"),   
	TC_QXC("TC_QXC", "七星彩"),
	TC_PL3("TC_PL3", "排列3"),	   
	TC_PL5("TC_PL5", "排列5"),   
	TC_6CBQCSF("TC_6CBQCSF", "足彩六场半"),
	TC_4CJQ("TC_4CJQ", "足彩4进球"),	   
	TC_ZQSPF("TC_ZQSPF", "足彩胜平负");
	
	private String code;
	private String describe;
	
	private LotteryNo(String code,String describe){
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
