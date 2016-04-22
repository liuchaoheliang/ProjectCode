package com.froad.cbank.coremodule.normal.boss.enums;


/**
 * 订单状态
 */
public enum OrderStatus {

	create("1","订单创建"),
	closed("2","订单用户关闭"),
	sysclosed("3","订单系统关闭"),
	paying("4","订单支付中"),
	payfailed("5","订单支付失败"),
	paysuccess("6","订单支付完成");
	
	private String code;
	
	private String describe;
	
	private OrderStatus(String code,String describe){
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
    public static OrderStatus getType(String code){
    	for(OrderStatus type : OrderStatus.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
    public static String getDescriptionByCode(String code){
		for(OrderStatus e : OrderStatus.values()){
			if(e.getCode().equals(code)){
				return e.getDescribe();
			}
		}
		return "";
	}
    
    /**
     * 通过code获取订单描述
     * @param code
     * @return
     */
	public static String getDescribeByCode(String code){
		for(OrderStatus e : OrderStatus.values()){
			if(e.getCode().equals(code)){
				return e.getDescribe();
			}
		}
		return "";
	}
}
