/**
 * Project Name:froad-cbank-server-common
 * File Name:ClientFunctionModule.java
 * Package Name:com.froad.enums
 * Date:2015年9月18日下午4:17:19
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;

import com.froad.util.Checker;

/**
 * ClassName:ClientFunctionModule
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 下午4:17:19
 * @author   kevin
 * @version  
 * @see 	 
 */
public enum ClientFunctionModule {
	discount_merchant(1,"特惠商户"),
	discount_product(2,"特惠商品"),
	pre_sell(3,"精品预售"),
	code_pay(4,"扫码支付"),
	points_pay(5,"积分兑换")
	;
	
	  
	private int code;
	
	private String msg;
	
    private ClientFunctionModule(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	
	public static ClientFunctionModule getByType(int type){
    	if(Checker.isNotEmpty(type)){
    		for(ClientFunctionModule o : values()){
    			if(o.getCode() == type){
    				return o;
    			}
    		}
    	}
    	return null;
    }
}
