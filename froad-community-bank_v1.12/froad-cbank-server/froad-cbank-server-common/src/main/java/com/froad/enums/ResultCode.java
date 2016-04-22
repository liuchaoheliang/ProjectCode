package com.froad.enums;

public enum ResultCode {

	success("0000","操作成功"),
	failed("9999","操作失败"), //描述的错误信息自定义
	notAllParameters("6666","参数不全"),
	
	bank_error("9997", "Bank Server后台系统繁忙!"),
	merchant_error("9998", "Merchant Server后台系统繁忙!"),
	boss_error("9996", "Boss Server后台系统繁忙!"),
	finity_error("9995", "Finity Server后台系统繁忙!"),
	
	// 公共
	merchant_not_exist("0001","该商户已不存在"),
	merchant_enable("0002","您选择的商户已失效，请更换商品"),
	prodcut_not_exist("0003","找不到您选择的商品信息"),
	prodcut_enable("0004","您选择的商品已失效，请更换商品"),
	prodcut_notin_outlet("0005","您选择的商品无法在此门店使用"),
	org_enable("0006","您选择的提货网点已失效，请更换提货网点"),
	product_store_not_enough("0006","商品库存不足"),
	product_not_in_time("0007","该产品还不在预售期内，您无法购买，您可以先收藏它"),
	outlet_product_not_exists("0008","门店面对面商品不存在"),
	product_un_prell("0009","该商品并非预售商品，请确认"),
	product_num_enough("0010","商品数量超限，最多99个"),
	provider_not_exist("0011", "该供应商已不存在"),
	product_no_in_delivery_time("0012", "商品已过期"),
	
	
	//---------------------------------购物车
	shopping_cart_limit("1001","购物车已满，请您先清理购物车再加入"),
	shopping_cart_inser_fail("1002","添加失败"),
	shopping_cart_buy_limit("1003","购买数量超限"),//文案替换，原文“该商品已加入购物车，请您检查该商品的可购买数量是否达到上限”
	shopping_cart_merchant_limit("1004","购物车商户超限"),
	tip_cart("1005","购物车信息提示"),
	merchant_type_error("1006","购物车不支持此商户类型"),
	product_un_market("1007","商品未上架"),
	price_change("1008","价格变动"),
	shopping_cart_store_over("1009","超过库存数量，无法修改"),
	shopping_cart_limit_over("1010","超过限购数量，无法修改"),
	//---------------------------------订单
	member_order_inser_fail("2001","很抱歉，您的订单创建失败，请您确认商品信息再次提交"),
	member_order_type_unknow("2002", "订单类型不正确"),
	member_order_query_face2face_empty("2003", "面对面支付订单为空"),
	member_suborder_query_empty("2004", "子订单为空"),
	member_order_is_empty("2005", "订单数据为空"),
	member_order_settlement_empty("2006", "订单的结算记录为空"),
	org_info_not_exists("2007", "机构信息不存在"),
	merchant_org_not_exists("2008", "商户对应机构信息不存在"),
	order_not_create("2009", "订单未创建"),
	member_order_type_deliveryOption_unknow("2010", "配送方式不能为空"),
	check_market_active_fail("2011", "营销活动校验失败"),
	
	//---------------------------------支付
	payment_order_none("3001","支付订单不存在"),
	payment_order_status_unsupport("3002","订单正在支付中或订单已完成支付"),
	payment_params_error("3003","请求参数不合法"),
	payment_order_error("3004","订单总价参数错误"),
	payment_paytype_none("3005","所选支付方式错误或当前客户端不支持"),
	payment_paytype_notfound("3006","未找到当前客户所支持的支付方式"),
	payment_paytype_unsupport("3007","指定的支付机构号错误或不支持"),
	payment_payamount_notequal("3008","支付金额与订单金额不匹配"),
	payment_store_lack("3009","库存不足"),
	payment_point_lack("3010","用户积分不足"),
	payment_member_lack("3011","付款会员不存在"),
	payment_openapi_payment_result_unknow("3012","未确认支付结果"),
	payment_openapi_payment_paid("3013","已成功支付"),
	payment_openapi_payment_faild("3014","支付失败	"),
	payment_thirted_request_faild("3015","请求第三方发生请求异常"),
	payment_create_payment_faild("3016","支付流水生成失败"),
	payment_foil_card_none("3017","贴膜卡手机号不存在"),
	payment_full_give_check_faild("3018","请求营销平台校验失败"),
	
	
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
	refund_ticket_not_found("4010", "退款，提货码过期或提货码信息不存在"),
	refund_froad_point_lack("4011","用户当前方付通积分值不足以扣除已赠送的积分值"),
	refund_group_ticket_insufficient("4012", "退款，有效团购提货码数量小于退款商品数量"),
	refund_point_insufficient("4013", "订单可退积分小于请求退款积分，退款失败"),
	refund_order_not_exists("4014", "退款记录不存在"),
	refund_order_cannot_modifystate("4015", "当前退款状态不能被修改"),
	refund_audit_do_result_fail("4016", "退款审核结果处理失败"),
	refund_only_group_expire_refund("4017", "仅支持团购提货码过期退款"),
	refund_exceed_presell_period("4018", "预售商品不在预售期内"),
	refund_query_active_error("4019", "调用活动模块获取优惠的价格失败"),
	refund_query_active_exp("4020", "调用活动模块获取优惠的价格大于退款价格"),
	
	
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
	no_id_error("7002","主键id不能为空"),
	check_token_logout("7012","由于您长时间未操作登录已超时，为保障您的账户安全请重新登录"),
	check_token_out("7013","您的账号已经在其他地方登录，为确保账号安全请立即修改密码"),
	
	// 商户
	// 用户登录
	login_not_exist("8001","用户名不存在"),
	login_password_error("8004","密码输入错误"),
	login_not_pass_audit("8005","商户尚未通过审核"),
	login_is_delete("8006","用户已被禁用"),
	login_username_not("8007","用户名不能为空"),
	login_password_not("8008","密码不能为空"),
	// 商户用户
	add_merchant_user_param_error("8010","您录入的商户信息有误，请检查后再次提交。"),
	add_merchant_user_db_lapse("8011","您添加商户用户失败，请检查用户信息是否正确。"),
	add_merchant_user_redis_lapse("8012","您添加商户用户失败，请检查用户信息是否正确。"),
	add_merchant_user_app_lapse("8013","您添加商户用户失败，请检查用户信息是否正确。"),
	del_merchant_user_id_empty("8014","您删除商户用户失败，请重新尝试。"),
	del_merchant_user_db_lapse("8015","您删除商户用户失败，请重新尝试。"),
	del_merchant_user_redis_lapse("8016","您删除商户用户失败，请重新尝试。"),
	del_merchant_user_app_lapse("8017","您删除商户用户失败，请重新尝试。"),
	upd_merchant_user_id_empty("8018","您修改商户用户信息失败，请检查信息后再次尝试。"),
	upd_merchant_user_db_lapse("8019","您修改商户用户信息失败，请检查信息后再次尝试。"),
	upd_merchant_user_redis_lapse("8020","您修改商户用户信息失败，请检查信息后再次尝试。"),
	upd_merchant_user_app_lapse("8021","您修改商户用户信息失败，请检查信息后再次尝试。"),
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
	add_outlet_connent_param_empty("8010",""),
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
	product_exits("10015","商品已收藏，不能再次收藏"),
	prudocut_status_sign("10016","商品批量上下架标志"),
	
	//boss
	payment_channel_not_exist("11001", "选择了未初始化的支付方式"),
	business_zone_name_not_only("11002", "同级地区下商户标签名不能重复"),
	update_zone_name_not_exists("11003", "商圈标签信息不存在"),
	input_partial_fail("11004", "部分数据导入失败"),
	
	//---------------------------------审核引擎
	take_error("12001","创建实例接口完成，但执行审核任务异常"),
	  
	market_active_rule_invalid("18001","活动失效"),
	market_active_rule_init_fail("18002","初始化活动失败"),
	market_active_rule_read_fail("18003","读取活动失败"),
	market_active_person_init_fail("18004","初始化个人资格失败 "),
	market_active_person_buy_invalid("18005","个人无购买资格 "),
	market_active_person_cut_fail("18006","个人扣减资格异常"),
	market_active_person_rollback_fail("18007","个人回滚资格异常"),
	market_active_global_rule_invalid("18008","初始化当日全局资格失败"),
	market_active_global_rule_buy_fail("18009","当天无购买资格"),
	market_active_global_rule_cut_fail("18010","当天资格扣减失败"),
	market_active_global_rule_rollback_fail("18011","当天资格回滚失败"),
	market_active_base_global_rule_invalid("18012","初始化全局资格失败"),
	market_active_base_global_rule_buy_fail("18013","无全局资格"),
	market_active_base_global_rule_cut_fail("18014","全局资格扣取失败"),
	market_active_base_global_rule_rollback_fail("18015","全局资格回滚失败"),
	market_active_rule_no_start("18016","活动还未开始生效")
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
