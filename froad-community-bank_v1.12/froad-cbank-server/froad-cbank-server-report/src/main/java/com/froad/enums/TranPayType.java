package com.froad.enums;

/**
 * 商品类型
 * @author wangyan
 *
 */
public enum TranPayType {
	
	pointPay("1","积分支付"),
    quickPay("2","快捷支付"),
    filmPay("3","贴膜卡支付"),
    pointAndFilmPay("4","积分+贴膜卡支付"),
    pointAndQuickPay("5","积分+快捷支付"),
    ;
    
    
    private String code;
    
    private String describe;
    
    private TranPayType(String code,String describe){
        this.code=code;
        this.describe=describe;
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
    
    /**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static TranPayType getType(String code){
    	for(TranPayType type : TranPayType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
}
