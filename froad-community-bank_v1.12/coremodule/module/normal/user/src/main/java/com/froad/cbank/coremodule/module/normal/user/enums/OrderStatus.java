package com.froad.cbank.coremodule.module.normal.user.enums;


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
	
	public static OrderStatus getOrderStatus(String code){
		for(OrderStatus os : OrderStatus.values()){
			if(os.code.equals(code)){
				return os;
			}
		}
		return null;
	}
	
	@Override
    public String toString() {
        return this.code;
    }
}
