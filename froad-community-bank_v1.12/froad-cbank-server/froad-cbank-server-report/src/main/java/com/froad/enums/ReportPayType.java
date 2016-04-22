package com.froad.enums;

/**
 * 支付方式
 *
 */
public enum ReportPayType {
	
	fft_point("1","方付通积分支付"),
    bank_point("2","银行积分支付"),
    quick_pay("3","快捷支付"),
    film_pay("4","贴膜卡支付"),
    fft_and_quick_pay("5","方付通积分+快捷支付"),
    fft_and_film_pay("6","方付通积分+贴膜卡支付"),
    bank_and_quick_pay("7","银行积分+快捷支付"),
    bank_and_film_pay("8","银行积分+贴膜卡支付"),
    ;
    
    
    private String code;
    
    private String desc;
    
    private ReportPayType(String code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String toString() {
        return this.code;
    }
    
    /**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static ReportPayType getType(String code){
    	for(ReportPayType type : ReportPayType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
}
