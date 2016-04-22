/**
 * 
 */
package com.froad.cbank.coremodule.module.normal.bank.enums;


/**
 * 订单来源
 */
public enum ReportPayType {
    undefined("0","未知"),
	froadPoints("1","方付通积分支付"),
	bankPoints("2","银行积分支付"),
	cash("3", "快捷支付"),
	cashCard("4", "贴膜卡支付"),
	froadPointsAndCash("5", "方付通积分+快捷支付"),
	froadPointsAndCashCard("6", "方付通积分+贴膜卡支付"),
	bankPointsAndCash("7", "银行积分+快捷支付"),
	bankPointsAndCashCard("8", "银行积分+贴膜卡支付"),
	;
	
	
	private String code;
	
	private String describe;
	
	private ReportPayType(String code,String describe){
		this.code=code;
		this.describe=describe;
	}
	
	public static  ReportPayType getReportPayType(String code){
		for(ReportPayType t:ReportPayType.values()){
			if(t.getCode().equals(code)){
				return t;
			}
		}
		return undefined;
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
	
	@Override
    public String toString() {
        return this.code;
    }
}
