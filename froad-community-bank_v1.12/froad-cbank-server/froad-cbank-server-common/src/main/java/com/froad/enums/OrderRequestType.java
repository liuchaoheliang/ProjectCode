package com.froad.enums;

/**
 * 订单请求类型:仅用于交易订单后台区分
 * @author Arron
 *
 */
public enum OrderRequestType {
    /**
     * （网点礼品）线下积分兑换机构商户订单
     */
    offline_point_order("1", "线下积分兑换订单"),
    
    /**
     * 预售机构商户订单
     */
    presell_order("2", "预售机构商户订单"),

    /**
     * 普通商户（名优特惠）订单
     */
    online_point_order("3", "在线积分兑换订单"),
    
    /**
     * 普通商户（名优特惠）订单
     */
    special_merchant("5", "名优特惠商户订单"),
    
    /**
     * 普通商户（团购）订单
     */
    group_merchant("4", "团购商户订单"),
    
    /**
     * 面对面订单
     */
    face2face("0", "面对面订单");

    private String code;
    private String describe;

    private OrderRequestType(String code, String describe) {
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
}
