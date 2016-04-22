/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: MongoTableName.java
 * @Package com.froad.util
 * @Description: TODO
 * @author vania
 * @date 2015年3月28日
 */

package com.froad.util;


/**    
 * <p>Title: MongoTableName.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月28日 下午3:00:10   
 */

public class MongoTableName {
    
	/**
     * 银行模块：机构商户关系表及角色资源关系表
     */
	public static final String CB_BANK_USER_RESOURCE = "cb_bank_user_resource";
	public static final String CB_ORG_RELATION = "cb_org_relation";
    /**
     * 商户门店收藏
     */
    public static final String CB_MERCHANT_OUTLET_FAVORITE = "cb_merchant_outlet_favorite";
    public static final String CB_MERCHANT_ROLE_RESOURCE = "cb_merchant_role_resource";
    public static final String CB_OUTLET_COMMENT = "cb_outlet_comment";
    
    /** 
     * mongodb 商户详情表 
     */
	public final static String CB_MERCHANT_DETAIL = "cb_merchant_detail";
	/** 
	 * mongodb 门店详情表 
	 */
	public final static String CB_OUTLET_DETAIL = "cb_outlet_detail";
	/**
	 * mongodb 门店商品收藏收货地址表 
	 */
	public final static String CB_MERCHANT_OUTLETFAVORITE = "cb_merchant_outletfavorite";
	
	/**
     * mongodb 商品详细信息
     */
    public static final String CB_PRODUCT_DETAIL = "cb_product_details";
    
    /**
     * mongodb 面对面商品信息
     */
    public static final String CB_OUTLET_PRODUCT = "cb_outlet_product";
    
    /**
     * mongodb 商品品论与回复表信息
     */
    public static final String CB_PRODUCT_COMMENT = "cb_product_comment";
    /**
     * 退货集合
     */
    public static final String COLLECTION_REFUND_HISTORY = "cb_good_return";
    /**
     * 用户订单表(mongodb)
     */
    public static final String CB_MEMBER_ORDER = "cb_member_order";
    /**
     * 订单表
     */
    public static final String CB_ORDER = "cb_order";
    
    /**
     * 子订单表
     */
    public static final String CB_SUBORDER_PRODUCT = "cb_suborder_product";
    /**
     * 订单发货表
     */
    public static final String CB_SHIPPING = "cb_shipping";
    /**
     * 结算表
     */
    public static final String 	CB_SETTLEMENT 		= "cb_settlement";
    /**
     * 购物车
     */
    public static final String  CB_PRE_SHOPPING    	= "cb_pre_shopping";
    /**
     * 退货表
     */
    public static final String CB_ORDER_REFUNDS = "cb_order_refunds";
    /**
     * 支付信息表
     */
    public static final String CB_PAYMENT = "cb_payment";
    /**
     * 券表
     */
    public static final String CB_TICKET = "cb_ticket";
    
    public static final String  CB_SMS_LOG = "cb_sms_log";
    
    /**
     * 审核框架-审核详情表
     */
    public static final String  CB_AUDIT_INSTANCE_DETAIL = "cb_audit_instance_detail";
    
    /**
     * 积分结算明细表
     */
    public static final String  CB_POINT_SETTLEMENT_DETAIL = "cb_point_settlement_detail";
    
}
