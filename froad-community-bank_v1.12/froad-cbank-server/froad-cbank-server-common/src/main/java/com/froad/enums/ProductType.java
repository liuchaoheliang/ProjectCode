package com.froad.enums;

/**
 * 商品类型
 * @author wangyan
 *
 */
public enum ProductType {

    group("1","团购"),
    presell("2","预售"),
    special("3","名优特惠"),
    onlinePoint("4","在线积分兑换"),
    dotGift("5","网点礼品"),
    boutique("6","精品商城商品"),
    ;
    
    
    private String code;
    
    private String describe;
    
    private ProductType(String code,String describe){
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
    public static ProductType getType(String code){
    	for(ProductType type : ProductType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
}
