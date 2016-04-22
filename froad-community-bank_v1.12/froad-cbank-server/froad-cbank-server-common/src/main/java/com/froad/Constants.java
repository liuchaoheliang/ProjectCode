package com.froad;

/**
 * 常量
 * @ClassName: Constants 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年3月13日 下午1:21:48 
 *
 */
public class Constants {
	public static final String NEWLINE = System.getProperty("line.separator"); // 操作系统的回车字符串

	/**
	 * 系统变量
	 */
	public static final String WORK_PATH 		= "WORK_PATH";
	public static final String PORT 			= "PORT";
	public static final String CONFIG_PATH 		= "CONFIG_PATH";
	public static final String MODULE_NAME		= "MODULE_NAME";
	
	/**
	 *  用户在一个client下限放的数量
	 */
	public static final int SHOPPING_CART_CLIENT_PRODUCT_LIMIT 			=49;
	public static final int SHOPPING_CART_CLIENT_PRODUCT_NUM_LIMIT 		=99;
	public static final int SHOPPING_CART_CLIENT_MERCHANT_LIMIT 		=5;
	
	/**
	 * 团购
	 */
	public static final String GOUP_TYPE 						= "1";
	/**
	 * 预售
	 */
	public static final String PRESELL_TYPE						= "2";
	
	/**
	 * 商品状态：未上架
	 */
	public static final String PRODUCT_MARKETABLE_0 			= "0";
	
	/**
	 * 商品状态：上架
	 */
	public static final String PRODUCT_MARKETABLE_1 			= "1";
	
	/**
	 * 商品状态：下架
	 */
	public static final String PRODUCT_MARKETABLE_2 			= "2";
	
	/**
	 * 商品状态：库存不足
	 */
	public static final String PRODUCT_STORE_NOT_ENOUGH_3 		= "3";
	
	/**
	 * 商品状态：商品限购数量超限
	 */
	public static final String PRODUCT_LIMIT_4 					= "4";
	
	/**
	 * 商品状态：商品过期
	 */
	public static final String PRODUCT_EXPIRE_5 				= "5";
	
	/**
	 * 商品状态：商品无效
	 */
	public static final String PRODUCT_INVALID_6 				= "6";
	
	/**
	 * 商品状态：商品库存紧张
	 */
	public static final String PRODUCT_SKU_JZ_7 				= "7";
	/**
	 *  商品为限购商品
	 */
	public static final String PRODUCT_LIMIT_8 					= "8";
	
	/**
     * 指定商品类型可以根据区域查询逗号分隔比如1,2,3,4,5
     */
    public static final String GOODS_AREA_PRODUCT_PRODUCT_TYPE = "5";
	
    
    /**
     * Server后台监控项中的businame值
     */
    public static final String MONITOR_BUSINAME  = "cbank-server-backend";
    
    /**
     * clientId
     */
    public static final String ANHUI  = "anhui";
    public static final String CHONGQING  = "chongqing";
    public static final String TAIZHOU  = "taizhou";
    
    /** 白名单同步或审核成功通过 **/
	public static final String SYN_OR_AUDIT_SUCCESS = "0";
	
    /** 白名单同步通知 **/
	public static final Integer SYN_TYPE = 0;
	
	/** 白名单审核通知 **/
	public static final Integer AUDIT_TYPE = 1;	
	
	/** 白名单同步发送短信，重置密码的默认值 **/
	public static final String RESET_PASSWORD_INIT= "111111";
	
	/** 白名单同步发送短信,短信模板类型 **/
	public static final Integer RESET_PASSWORD_SMS_TYPE = 1104;
	
	/** 录入审核白名单商户类型 **/
	public static final String INPUT_AUDIT_MERCHANT_TYPE = "0";
	
	/** 录入审核白名单门店类型 **/
	public static final String INPUT_AUDIT_OUTLET_TYPE = "1";
	
	/** 0-同步成功 **/
	public static final String SYN_SUCCESS = "0";
	
	/** 1-同步失败 **/
	public static final String SYN_FAIL = "1";
	
	/** 录入审核 **/
	public static final Integer INPUT_AUDIT = 0;
	
	/** 编辑审核**/
	public static final Integer EDIT_AUDIT = 1;
	
	/**评论类型为面对面评论 **/
	public static final Integer COMMENT_TYPE_FACETOFACE = 1;
	
	/**评论类型为商户评论**/
	public static final Integer COMMENT_TYPE_NONE = 0;	
	
	/** 门店有效状态为非删除状态**/
	public static final String DISABLE_STATUS_NODELETE="2";
}