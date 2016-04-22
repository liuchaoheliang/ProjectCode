package com.froad.enums;

/**
 * 秒杀结果标识
 * 
 * @author wangzhangxu
 */
public enum SeckillResultFlagEnum {
    
    accept("0", "受理成功，排队中"),
    order_failed("1", "订单创建失败"),
    order_success("2", "订单创建成功"),
    order_pay_ing("3", "订单支付中"),
    order_pay_failed("4", "订单支付失败"),
    order_pay_success("5", "订单支付成功");
    
    private String code;
    
    private String describe;
    
    private SeckillResultFlagEnum(String code,String describe){
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
    public static SeckillResultFlagEnum getType(String code){
    	for(SeckillResultFlagEnum type : SeckillResultFlagEnum.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}

}
