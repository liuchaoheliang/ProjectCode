package com.froad.enums;

public enum SubOrderType {
    
    /*
     * 普通商户（团购）子订单
     */
    group_merchant("1", "团购商户子订单"),
    
    /*
     * 预售机构商户子订单
     */
    presell_org("2", "预售机构商户子订单"),
    
    /*
     * 普通商户（名优特惠）子订单
     */
    special_merchant("3", "名优特惠商户子订单"),

    /*
     * 在线积分兑换机构商户子订单
     */
    online_points_org("4", "线上积分兑换机构商户子订单"),
    
    /*
     * 网点礼品（线下积分兑换）机构商户子订单
     */
    offline_points_org("5", "线下积分兑换机构商户子订单"),
    
    /*
     * 精品商城 子订单
     */
    boutique("6", "精品商城子订单");
    

    private String code;
    private String describe;

    private SubOrderType(String code, String describe) {
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
