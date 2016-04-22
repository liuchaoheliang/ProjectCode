package com.froad.enums;

import com.froad.util.Checker;

/**
 * 订单类型
 * 
 * @author Arron
 * 
 */
public enum OrderType {

    /**
     * 面对面订单
     */
    face2face("0", "面对面订单"),
    /**
     * 普通商户（团购）订单
     */
    group_merchant("1", "团购商户订单"),
    /**
     * 预售机构商户订单
     */
    presell_org("2", "预售机构商户订单"),

    /**
     * 普通商户（名优特惠）订单
     */
    special_merchant("3", "名优特惠商户订单"),
//    /**
//     * 积分兑换机构商户订单
//     */
//    points_org("4", "积分兑换机构商户订单");

    /*
     * 在线积分兑换机构商户子订单
     */
    online_points_org("4", "线上积分兑换机构商户子订单"),
    
    /*
     * 网点礼品（线下积分兑换）机构商户子订单
     */
    offline_points_org("5", "线下积分兑换机构商户子订单"),
    
    /*
     * 开通VIP
     */
    open_vip("6", "开通VIP订单"),
    
    /*
     * 精品
     */
    competitive("7", "精品");
    
    private String code;
    private String describe;

    private OrderType(String code, String describe) {
        this.code = code;
        this.describe = describe;
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
    
    public static OrderType getByType(String type){
    	if(Checker.isNotEmpty(type)){
    		for(OrderType o : values()){
    			if(o.getCode().equals(type)){
    				return o;
    			}
    		}
    	}
    	return null;
    }
    
    
}
