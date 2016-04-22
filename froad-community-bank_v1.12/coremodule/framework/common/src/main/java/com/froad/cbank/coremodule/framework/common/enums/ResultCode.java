package com.froad.cbank.coremodule.framework.common.enums;

public enum ResultCode {

	success("0000","操作成功"),
	failed("9999","操作失败"), //描述的错误信息自定义
	notAllParameters("6666","参数不全"),
	
	// 公共
	merchant_not_exist("0001","商户信息不存在"),
	merchant_enable("0002","商户状态无效"),
	prodcut_not_exist("0003","商品信息不存在"),
	prodcut_enable("0004","商品状态无效"),
	prodcut_notin_outlet("0005","商品不属于此门店"),
	org_enable("0006","提货网点状态无效"),
	product_store_not_enough("0006","商品库存不足"),
	product_not_in_time("0007","商品不再预销售期内"),
	outlet_product_not_exists("0008","门店面对面商品不存在"),
	product_un_prell("0009","商品非预售商品"),
	
	//---------------------------------购物车
	shopping_cart_limit("1001","购物车商品数量超限"),
	shopping_cart_inser_fail("1002","添加失败"),
	shopping_cart_buy_limit("1003","超过产品限购数量"),
	shopping_cart_merchant_limit("1004","购物车商户超限"),
	tip_cart("1005","购物车信息提示"),
	merchant_type_error("1006","购物车不支持此商户类型"),
	//---------------------------------订单
	member_order_inser_fail("2001","用户订单插入失败"),
	member_order_type_unknow("2002", "订单类型不正确"),
	member_order_query_face2face_empty("2003", "面对面支付订单为空"),
	member_suborder_query_empty("2004", "子订单为空"),
	member_order_is_empty("2005", "订单数据位空"),
	member_order_settlement_empty("2006", "订单的结算记录为空"),
	org_info_not_exists("2007", "机构信息不存在"),
	merchant_org_not_exists("2008", "商户对应机构信息不存在"),
	
	//---------------------------------支付
	payment_order_none("3001","支付订单不存在"),
	payment_order_status_unsupport("3002","订单状态不允许支付"),
	payment_params_error("3003","请求参数不合法"),
	payment_order_error("3004","预支付订单数据错误"),
	payment_paytype_none("3005","所选支付方式错误或当前客户端不支持"),
	payment_paytype_notfound("3006","未找到当前客户所支持的支付方式"),
	payment_paytype_unsupport("3007","指定的支付机构号错误或不支持"),
	payment_payamount_notequal("3008","支付金额与订单金额不匹配"),
	payment_store_lack("3009","库存不足"),
	payment_point_lack("3010","用户积分不足"),
	payment_member_lack("3011","会员不存在"),
	payment_openapi_payment_result_unknow("3012","未确认支付结果，系统异常"),
	payment_openapi_payment_paid("3013","已成功支付"),
	payment_openapi_payment_faild("3014","支付失败	"),
	
	
	
	//---------------------------------退款
	refund_order_imcomplete("4001", "原订单支付未完成或支付失败"),
	refund_product_not_match("4002", "退款商品与原订单商品不匹配"),
	refund_request_exists("4003", "同一订单不允许同时发起多个退款请求"),
	refund_product_deliveried("4004", "商品已配送，不允许退款"),
	refund_quantity_exceed("4005", "请求退款商品数量超出原订单数"),
	refund_abnormal_state("4006", "订单库存已退或已关闭，不允许退款"),
	refund_face2face_payment("4007", "名对面支付，不能退款"),
	refund_manual_success("4008", "异常处理完成"),
	refund_finance_approved("4009", "财务审批通过"),
	refund_ticket_not_found("4010", "退款，卷过期或券信息不存在"),
	refund_froad_point_lack("4011","用户当前方付通积分值不足以扣除已赠送的积分值"),
	refund_group_ticket_insufficient("4012", "退款，有效团购券数量小于退款商品数量"),
	refund_point_insufficient("4013", "订单可退积分小于请求退款积分，退款失败"),
	refund_order_not_exists("4014", "退款记录不存在"),
	refund_order_cannot_modifystate("4015", "当前退款状态不能被修改"),
	refund_audit_do_result_fail("4016", "退款审核结果处理失败"),
	refund_only_group_expire_refund("4017", "仅支持团购券过期退款"),
	
	
	//---------------------------------结算
	settlement_un_qrcode("5000","非面对面结算订单"),
	settlement_no_pay("5001","此订单未支付成功"),
	settlement_settlemented("5002","结算记录已请求过，不能重复结算"),
	settlement_suborder_not_exist("5003","子订单记录不存在，无法进行结算"),
	settlement_ticket_not_exists("5004","团购结算,卷信息不存在"),
	settlement_ticket_un_comsume("5005","团购结算,卷尚未结算"),
	settlement_not_exists("5006","结算记录不存在"),
	settlement_state_has_change("5007","结算状态已更新"),
	settlement_state_error("5008","结算更新状态错误"),
	settlement_info_not_exists("5009","结算记录不存在"),
	settlement_ticket_error("50010","团购结算,无可结算的卷信息"),
	
	
	
	//---------------------------------用户登录
	bankoperator_not_exist("7001","用户信息不存在"),
	bankoperator_not_status("7002","用户信息不可用"),
	bankoperator_delete("7003","用户信息已删除"),
	bankoperator_password_error("7004","用户密码错误"),
	bankoperator_token_error("7005","用户token不匹配"),
	no_id_error("7006","主键id不能为空"),
	
	
	// 商户
	// 用户登录
	login_not_exist("8001","商户用户信息不存在"),
	login_password_error("8004","商户用户密码错误"),
	// 商户用户
	add_merchant_user_param_error("8010","新增商户用户 参数有误 - "),
	add_merchant_user_db_lapse("8011","新增商户用户  - DB操作有误"),
	add_merchant_user_redis_lapse("8012","新增商户用户 - Redis操作有误"),
	add_merchant_user_app_lapse("8013","新增商户用户 - 程序操作有误 - "),
	del_merchant_user_id_empty("8014","删除MerchantUser失败 - 主键id不能为空"),
	del_merchant_user_db_lapse("8015","删除MerchantUser失败 - DB操作有误"),
	del_merchant_user_redis_lapse("8016","删除MerchantUser失败 - Redis操作有误"),
	del_merchant_user_app_lapse("8017","删除MerchantUser失败 - 程序操作有误 - "),
	upd_merchant_user_id_empty("8018","更新MerchantUser失败 - 主键id不能为空"),
	upd_merchant_user_db_lapse("8019","更新MerchantUser失败 - DB操作有误"),
	upd_merchant_user_redis_lapse("8020","更新MerchantUser失败 - Redis操作有误"),
	upd_merchant_user_app_lapse("8021","更新MerchantUser失败 - 程序操作有误 - "),
	// 商户角色
	add_merchant_role_db_lapse("8022","新增商户角色  - DB操作有误"),
	add_merchant_role_app_lapse("8023","新增商户角色 - 程序操作有误 - "),
	del_merchant_role_db_lapse("8024","删除商户角色  - DB操作有误"),
	del_merchant_role_app_lapse("8025","删除商户角色 - 程序操作有误 - "),
	upd_merchant_role_db_lapse("8026","更新商户角色  - DB操作有误"),
	upd_merchant_role_app_lapse("8027","更新商户角色 - 程序操作有误 - "),
	// 商户菜单资源
	add_merchant_resource_db_lapse("8028","新增商户菜单资源  - DB操作有误"),
	add_merchant_resource_app_lapse("8029","新增商户菜单资源 - 程序操作有误 - "),
	del_merchant_resource_db_lapse("8030","删除商户菜单资源 - DB操作有误"),
	del_merchant_resource_app_lapse("8031","删除商户菜单资源 - 程序操作有误 - "),
	upd_merchant_resource_db_lapse("8032","更新商户菜单资源  - DB操作有误"),
	upd_merchant_resource_app_lapse("8033","更新商户菜单资源 - 程序操作有误 - "),
	// 商户角色菜单资源
	add_merchant_role_resource_mongo_lapse("8034","新增商户角色菜单资源  - mongo操作有误"),
	add_merchant_role_resource_app_lapse("8035","新增商户角色菜单资源 - 程序操作有误 - "),
	del_merchant_role_resource_mongo_lapse("8036","删除商户角色菜单资源 - mongo操作有误"),
	del_merchant_role_resource_app_lapse("8037","删除商户角色菜单资源 - 程序操作有误 - "),
	upd_merchant_role_resource_mongo_lapse("8038","更新商户角色菜单资源  - mongo操作有误"),
	upd_merchant_role_resource_app_lapse("8039","更新商户角色菜单资源 - 程序操作有误 - "),
	// 门店评论
	add_outlet_connent_param_empty("8010","新增门店评论 必备参数为空 - "),
	add_outlet_connent_mongo_lapse("8011","新增门店评论  - mongo操作有误"),
	add_outlet_connent_app_lapse("8013","新增门店评论 - 程序操作有误 - "),
	del_outlet_connent_id_empty("8014","删除门店评论 - 主键id不能为空"),
	del_outlet_connent_mongo_lapse("8015","删除门店评论 - mongo操作有误"),
	del_outlet_connent_app_lapse("8017","删除门店评论 - 程序操作有误 - "),
	upd_outlet_connent_id_empty("8018","更新门店评论 - 主键id不能为空"),
	upd_outlet_connent_mongo_lapse("8019","更新门店评论 - mongo操作有误"),
	upd_outlet_connent_app_lapse("8021","更新门店评论 - 程序操作有误 - "),
	add_outlet_connent_recomment_mongo_lapse("8011","新增门店评论回复  - mongo操作有误"),
	add_outlet_connent_recomment_app_lapse("8013","新增门店评论回复 - 程序操作有误 - "),
	
	//收藏
	outlet_count_limit("9001","门店收藏数已达上限"),
	outlet_exits("9003","门店已收藏，不能再次收藏"),
	outlet_detail_no_exits("9005","门店详情表中不存在该门店"),
		
	recvInfo_limit("9002","收货地址数已达上限"),
	
	deliveryInfo_limit("9004","提货信息数已达上限"),
   	 //商品
		productcomment_param_empty("10010","必备参数为空"),
		productcomment_is_comment("10011","用户已经评论过此订单"),
		product_no_exits("10012","商品信息不存在"),
		product_count_limit("10014","商品收藏数已达上限"),
		product_exits("10015","商品已收藏，不能再次收藏")
	;

	private String code;
	
	private String msg;
	
    private ResultCode(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
