package com.froad.cbank.coremodule.framework.common.monitor;

/**
 * 监控类型枚举
 * @ClassName MonitorEnums
 * @author zxl
 * @date 2015年5月9日 上午11:05:35
 */
public enum MonitorEnums {
	
	/**
	 * thrift通讯异常
	 */
	thrift_error("cbank-web-backend","b128","int"),
	
	/**
	 * 商品评价列表
	 */
	merchant_goods_commect("cbank-web-backend","b129","float"),
	
	/**
	 * 订单下载
	 */
	merchant_order_download("cbank-web-backend","b130","float"),
	
	/**
	 * 提货码下载
	 */
	merchant_ticket_download("cbank-web-backend","b131","float"),
	
	/**
	 * 面对面创建
	 */
	merchant_goods_create("cbank-web-backend","b132","float"),
	
	/**
	 * 订单查询pc
	 */
	merchant_order_list_pc("cbank-web-backend","b133","float"),
	
	/**
	 * 订单查询h5
	 */
	merchant_order_list_h5("cbank-web-backend","b134","float"),
	
	/**
	 * 提货码查询
	 */
	merchant_ticket_list("cbank-web-backend","b135","float"),
	/**
	 * 个人用户申请退款
	 */
	user_do_refund("cbank-web-backend","b137","float"),
	/**
	 * 个人用户获取二维码
	 */
	user_get_qrcode("cbank-web-backend","b140","float"),
	/**
	 * 个人获取门店列表
	 */
	user_outlet_list("cbank-web-backend", "b141", "float"),
	/**
	 * 个人获取商品评论列表
	 */
	user_product_comment_list("cbank-web-backend", "b142", "float"),
	/**
	 * 个人获取（个人）商品评论列表
	 */
	user_self_product_comment_list("cbank-web-backend", "b144", "float"),
	/**
	 * 个人获取门店评论列表
	 */
	user_outlet_comment_list("cbank-web-backend", "b145", "float"),
	/**
	 * 个人获取（个人）门店评论列表
	 */
	user_self_outlet_comment_list("cbank-web-backend", "b146", "float"),
	/**
	 * 个人商品评论
	 */
	user_product_comment("cbank-web-backend", "b147", "int"),
	/**
	 * 个人门店评论
	 */
	user_outlet_comment("cbank-web-backend", "b148", "int"),
	
	
	
	
	/**
	 * 个人订单支付时间
	 */
	user_order_pay_time("cbank-web-backend", "b143", "float"),
	/**
	 * 个人订单支付成功笔数
	 */
	user_order_pay_success("cbank-web-backend", "b149", "int"),
	/**
	 * 个人购物车查询耗时
	 */
	user_cart_query_time("cbank-web-backend", "b151", "float"),
	/**
	 * 个人登录次数统计
	 */
	user_login_statistics("cbank-web-backend", "b152", "int"),
	/**
	 * 个人注册次数统计
	 */
	user_register_statistics("cbank-web-backend", "b153", "int"),
	/**
	 * 个人登录用时
	 */
	user_login_time("cbank-web-backend", "b154", "float"),
	/**
	 * 个人注册用时
	 */
	user_register_time("cbank-web-backend", "b155", "float"),
	/**
	 * 个人登录token校验异常
	 */
	user_token_verify_exception("cbank-web-backend", "b156", "int"),
	/**
	 * 个人创建订单耗时
	 */
	user_order_generate_time("cbank-web-backend", "b157", "float"),
	/**
	 * 个人创建订单次数统计
	 */
	user_order_generate_statistics("cbank-web-backend", "b158", "int"),
	/**
	 * 个人创建面对面订单耗时
	 */
	user_qrorder_generate_time("cbank-web-backend", "b159", "float"),
	/**
	 * 个人创建面对面订单次数统计
	 */
	user_qrorder_generate_statistics("cbank-web-backend", "b160", "int"),
	/**
	 * 个人查询订单列表耗时
	 */
	user_order_list_query_time("cbank-web-backend", "b161", "float"),
	/**
	 * 个人查询订单列表耗时
	 */
	user_order_detail_query_time("cbank-web-backend", "b162", "float"),
	/**
	 * 个人修改登录密码发送短信
	 */
	user_sms_update_loginpwd("cbank-web-backend", "b163", "int"),
	/**
	 * 个人修改支付密码发送短信
	 */
	user_sms_update_paypwd("cbank-web-backend", "b164", "int"),
	/**
	 * 个人绑定手机号发送短信
	 */
	user_sms_bind_phone("cbank-web-backend", "b165", "int"),
	/**
	 * 个人更换手机号发送短信
	 */
	user_sms_change_phone("cbank-web-backend", "b166", "int"),
	/**
	 * 个人找回支付密码发送短信
	 */
	user_sms_find_paypwd("cbank-web-backend", "b184", "int"),
	/**
	 * 个人支付校验发送短信
	 */
	user_sms_fastpay_verify("cbank-web-backend", "b185", "int"),
	/**
	 * 个人签约银行卡发送短信
	 */
	user_sms_sign_bankcard("cbank-web-backend", "b186", "int"),
	/**
	 * 个人设置支付密码成功发送短信
	 */
	user_sms_set_paypwd_success("cbank-web-backend", "b187", "int"),
	/**
	 * 个人注册发送短信
	 */
	user_sms_register("cbank-web-backend", "b188", "int"),
	/**
	 * 个人找回登录密码发送短信
	 */
	user_sms_find_loginpwd("cbank-web-backend", "b189", "int"),
	/**
	 * 个人联合登陆绑定手机号发送短信
	 */
	user_sms_bind_unionlogin_phone("cbank-web-backend", "b190", "int"),
	
	
	/*******************银行PC************************/
	/**
	 * 银行PC登录
	 */
	bank_login_ve("cbank-web-backend","b167","float"),
	/**
	 * 银行PC商户列表
	 */
	bank_merchant_lt("cbank-web-backend","b168","float"),
	/**
	 * 银行PC商户新增
	 */
	bank_merchant_ad("cbank-web-backend","b169","float"),
	/**
	 * 银行PC商户修改
	 */
	bank_merchant_ue("cbank-web-backend","b170","float"),
	/**
	 * 银行PC机构新增
	 */
	bank_bankOrg_ad("cbank-web-backend","b171","float"),
	/**
	 * 银行PC机构修改
	 */
	bank_bankOrg_ue("cbank-web-backend","b172","float"),
	/**
	 * 银行PC商户导出
	 */
	bank_merchant_mhet("cbank-web-backend","b173","float"),
	/**
	 * 银行PC预售商品导出
	 */
	bank_product_pret("cbank-web-backend","b174","float"),
	/**
	 * 银行PC团购商品导出
	 */
	bank_product_goet("cbank-web-backend","b175","float"),
	/**
	 * 银行PC名优特惠商品导出
	 */
	bank_product_pfet("cbank-web-backend","b176","float"),
	/**
	 * 银行PC线下积分礼品导出
	 */
	bank_product_liet("cbank-web-backend","b177","float"),
	/**
	 * 银行PC团购订单导出
	 */
	bank_order_goet("cbank-web-backend","b178","float"),
	/**
	 * 银行PC预售订单导出
	 */
	bank_order_poet("cbank-web-backend","b179","float"),
	/**
	 * 银行PC面对面订单导出
	 */
	bank_order_coet("cbank-web-backend","b180","float"),
	/**
	 * 银行PC名优特惠订单导出
	 */
	bank_order_fpoet("cbank-web-backend","b181","float"),
	/**
	 * 银行PC积分礼品列表
	 */
	bank_lineProduct_lt("cbank-web-backend","b182","float"),
	/**
	 * 银行PC预售商品列表
	 */
	bank_presaleProduct_lt("cbank-web-backend","b183","float"),
	/**
	 * 银行PC线下积分兑换订单导出
	 */
	bank_order_ceoet("cbank-web-backend","b191","float"),
	/**
	 * 银行PC提货列表导出
	 */
	bank_take_et("cbank-web-backend","b192","float"),
	/**
	 * 银行PC名优特惠商品列表
	 */
	bank_preferentialProduct_lt("cbank-web-backend","b193","float"),
	/**
	 * 银行PC团购商品列表
	 */
	bank_groupProduct_lt("cbank-web-backend","b194","float"),
	/**
	 * 银行PC预售订单列表
	 */
	bank_preferential_order_lt("cbank-web-backend","b195","float"),
	/**
	 * 银行PC团购订单列表
	 */
	bank_group_order_lt("cbank-web-backend","b196","float"),
	/**
	 * 银行PC名优特惠订单列表
	 */
	bank_preferent_order_lt("cbank-web-backend","b197","float"),
	/**
	 * 银行PC面对面订单列表
	 */
	bank_confront_order_lt("cbank-web-backend","b198","float"),
	/**
	 * 银行PC积分兑换订单列表
	 */
	bank_lien_order_lt("cbank-web-backend","b199","float"),
	
	;
	
	/**
	 * businame 业务名称唯一标示符，从“接入申请系统”获得
	 */
	private String businame;
	/**
	 * attrId 业务监控项唯一标示符，从“接入申请系统”获得
	 */
	private String attrId;
	/**
	 * type int|float|string
	 */
	private String type;
	
	private MonitorEnums(String businame,String attrId,String type){
		this.businame = businame;
		this.attrId = attrId;
		this.type = type;
	}

	public String getBusiname() {
		return businame;
	}

	public String getAttrId() {
		return attrId;
	}


	public String getType() {
		return type;
	}

}
