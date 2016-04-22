package com.froad.enums;


/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: CashType
 * @Description: 现金支付类型
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月18日 下午2:54:36
 */
public enum CashType {

	/**
	 * 快捷支付
	 */
	bank_fast_pay(51),

	/**
	 * 贴膜卡
	 */
	foil_card(20),
	
	/**
	 * 及时支付
	 */
	timely_pay(55),
	
	/**
	 * 网银支付
	 */
	bank_online(50),
	
	
	;
	
	private int code;

    private CashType(int code) {
        this.code = code;
    }
    
    public int code(){
    	return code;
    }
}
