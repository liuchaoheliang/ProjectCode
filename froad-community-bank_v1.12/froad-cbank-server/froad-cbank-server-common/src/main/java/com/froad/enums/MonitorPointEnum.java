package com.froad.enums;

import com.froad.Constants;

/**
 * 监控项 (点)
 * 
 * @author wangzhangxu
 * @date 2015年5月5日 上午9:41:50
 * @version v1.0
 */
public enum MonitorPointEnum {
	
	/** 秒杀模块队列写入异常监控项 */
	Seckill_Beanstalkd_Put_Failure(Constants.MONITOR_BUSINAME, "b42", "1", "int", ""), 
	/** 秒杀模块下单进入排队的数量 */
	Seckill_In_Line_Count(Constants.MONITOR_BUSINAME, "b213", "1", "int", ""), 
	
	/** 秒杀模块下单请求数量 */
	Seckill_Place_Order_Count(Constants.MONITOR_BUSINAME, "b206", "1", "int", ""), 
	
	/** 秒杀模块下单请求耗时 */
	Seckill_Place_Order_Timeout(Constants.MONITOR_BUSINAME, "b210", "1", "int", ""), 
	/** 秒杀模块秒杀结果轮询请求数量 */
	Seckill_Query_Order_Count(Constants.MONITOR_BUSINAME, "b211", "1", "int", ""), 
	/** 秒杀模块秒杀结果轮询请求耗时 */
	Seckill_Query_Order_Timeout(Constants.MONITOR_BUSINAME, "b212", "1", "int", ""), 
	
	/** 下单Worker秒杀订单生成数量 */
	Worker_Order_Create_Count(Constants.MONITOR_BUSINAME, "b207", "1", "int", ""), 
	/** 下单Worker秒杀成功订单数量 */
	Worker_Order_Success_Count(Constants.MONITOR_BUSINAME, "b208", "1", "int", ""), 
	/** 下单Worker处理队列任务数量 */
	Worker_Process_Job_Count(Constants.MONITOR_BUSINAME, "b209", "1", "int", ""), 
	/** 下单Worker模块调用Thrift耗时长监控项 */
	Worker_Thrift_Timeout(Constants.MONITOR_BUSINAME, "b43", "0", "float", ""), 
	/** 下单Worker模块调用Thrift异常监控项 */
	Worker_Thrift_Error_Failure(Constants.MONITOR_BUSINAME, "b44", "1", "int", ""), 
	/** 下单Worker检查商品库存小于零监控项 */
	Worker_Stock_Lt_Zero_Success(Constants.MONITOR_BUSINAME, "b45", "0", "int", ""), 
	Worker_Stock_Lt_Zero_Failure(Constants.MONITOR_BUSINAME, "b45", "1", "int", ""), 
	
	/** 交易结算入库失败回滚监控项 */
	Trans_settle_mongofail_rollback(Constants.MONITOR_BUSINAME, "b55", "1", "int", ""),
	
	/** 外围 */
	/** 短信发送 监控项 */
	Support_Sms_Send_Success(Constants.MONITOR_BUSINAME,"b46","0","int",""),
	Support_Sms_Send_Failure(Constants.MONITOR_BUSINAME,"b138","1","int",""),
	/** 生成图片验证码 监控项 */
	Support_Get_Image_Verification_Success(Constants.MONITOR_BUSINAME,"b47","0","int",""),
	Support_Get_Image_Verification_Failure(Constants.MONITOR_BUSINAME,"b139","1","int",""),
	
	/** 定时任务 */
	/** 核对支付系统结果 */
	Timertask_Check_TransResults_Success(Constants.MONITOR_BUSINAME,"b48","0","int",""),
	Timertask_Check_TransResults_Failure(Constants.MONITOR_BUSINAME,"b48","1","int",""),
	/** 预售商品成团发券 */
	Timertask_Cluster_Presell_Success(Constants.MONITOR_BUSINAME,"b49","0","int",""),
	Timertask_Cluster_Presell_Failure(Constants.MONITOR_BUSINAME,"b49","1","int",""),
	/** 确认收货 */
	Timertask_Confirm_Receipt_Success(Constants.MONITOR_BUSINAME,"b50","0","int",""),
	Timertask_Confirm_Receipt_Failure(Constants.MONITOR_BUSINAME,"b50","1","int",""),
	/** 处理过期券 */
	Timertask_Handle_Expired_Ticket_Success(Constants.MONITOR_BUSINAME,"b51","0","int",""),
	Timertask_Handle_Expired_Ticket_Failure(Constants.MONITOR_BUSINAME,"b51","1","int",""),
	/** 处理过期商户 */
	Timertask_Handle_Expired_Merchant_Success(Constants.MONITOR_BUSINAME,"b52","0","int",""),
	Timertask_Handle_Expired_Merchant_Failure(Constants.MONITOR_BUSINAME,"b52","1","int",""),
	/** 返回库存 */
	Timertask_Return_Store_Success(Constants.MONITOR_BUSINAME,"b53","0","int",""),
	Timertask_Return_Store_Failure(Constants.MONITOR_BUSINAME,"b53","1","int",""),
	/** 关闭订单 */
	Timertask_Close_Tran_Failure(Constants.MONITOR_BUSINAME,"b136","1","int",""),
	
	/**搜索最热门店时长监控*/
	Merchant_HottestOutlet_Timeout_Success(Constants.MONITOR_BUSINAME, "b78", "0", "int", ""),
	Merchant_HottestOutlet_Timeout_Failure(Constants.MONITOR_BUSINAME, "b78", "1", "int", ""), 
	
	/**附近搜索门店时长监控*/
	Merchant_NearOutlet_Timeout_Success(Constants.MONITOR_BUSINAME, "b79", "0", "int", ""),
	Merchant_NearOutlet_Timeout_Failure(Constants.MONITOR_BUSINAME, "b79", "1", "int", ""),
	
	/**boss用户登录获取角色/资源异常次数**/
	Finity_Role_Login_Failure(Constants.MONITOR_BUSINAME, "b221", "1", "int", ""),
	Finity_Resource_Login_Failure(Constants.MONITOR_BUSINAME, "b222", "1", "int", ""),
	Finity_Role_Select_Failure(Constants.MONITOR_BUSINAME, "b223", "1", "int", ""),
	Finity_Resource_Select_Failure(Constants.MONITOR_BUSINAME, "b224", "1", "int", ""),
	
	
	/**每分钟银行用户登录次数*/
	Bank_userLogin_times(Constants.MONITOR_BUSINAME, "b81", "1", "int", ""),
	/**每分钟银行用户登录失败次数*/
	Bank_userLogin_failureTimes(Constants.MONITOR_BUSINAME, "b82", "1", "int", ""),
	
	/** 二维码生成每秒触发次数 */
	Qrcode_Qrcode_Gen_Count(Constants.MONITOR_BUSINAME, "b76", "1", "int", ""),
	/** 图片验证码生成每秒触发次数 */
	Qrcode_Wordimage_Gen_Count(Constants.MONITOR_BUSINAME, "b77", "1", "int", ""),
	/** 二维码生成每秒失败次数 */
	Qrcode_Qrcode_Gen_Failed_Count(Constants.MONITOR_BUSINAME, "b117", "1", "int", ""),
	/** 图片验证码生成每秒失败次数 */
	Qrcode_Wordimage_Gen_Failed_Count(Constants.MONITOR_BUSINAME, "b118", "1", "int", ""),
	
	/** 退款支付异常次数 */
	Order_Refund_Pay_Failed_Count(Constants.MONITOR_BUSINAME, "b57", "1", "int", ""),
	/** 用户申请退款次数 */
	Order_Refund_Request_Count(Constants.MONITOR_BUSINAME, "b119", "1", "int", ""),
	/** 退款时扣回赠送积分异常次数 */
	Order_Refund_Return_Creditpoint_Failed_Count(Constants.MONITOR_BUSINAME, "b59", "1", "int", ""),
	/** 退款审核通过回滚库存失败次数 */
	Order_Refund_Rollback_Store_Failed_Count(Constants.MONITOR_BUSINAME, "b61", "1", "int", ""),
	/** 退款失败提货码回滚到有效状态失败次数 */
	Order_Refund_Rollback_Ticket_Failed_Count(Constants.MONITOR_BUSINAME, "b71", "1", "int", ""),
	
	/** 提货码对应二维码生成异常次数 */
	Order_Ticket_Qrcode_Gen_Failed_Count(Constants.MONITOR_BUSINAME, "b72", "1", "int", ""),
	/** 团购提货码验证后结算异常次数 */
	Order_Ticket_Settlement_Failed_Count(Constants.MONITOR_BUSINAME, "b73", "1", "int", ""),
	/** 提货时券状态更新异常次数 */
	Order_Ticket_Update_Ticket_Status_Failed_Count(Constants.MONITOR_BUSINAME, "b74", "1", "int", ""),
	/** 提货时子订单提货状态更新异常次数 */
	Order_Ticket_Update_Suborder_Status_Failed_Count(Constants.MONITOR_BUSINAME, "b75", "1", "int", ""),
	/** 每秒验券次数 */
	Order_Ticket_Verify_Count(Constants.MONITOR_BUSINAME, "b120", "1", "int", ""),
	
	/**订单创建时校验商户商品缓存信息失败次数*/
	Order_Checkmerchantandgood_Failed_Count(Constants.MONITOR_BUSINAME, "b96", "1", "int", ""),
	/**订单创建时校验商户商品缓存信息失败次数*/
	Order_Checkmerchantandgood_Success_Count(Constants.MONITOR_BUSINAME, "b96", "0", "int", ""),
	/**面对面订单创建每秒创建次数*/
	Order_Createf2f_Count(Constants.MONITOR_BUSINAME, "b99", "1", "int", ""),
	/**面对面订单创建每秒创建异常次数*/
	Order_Createf2f_Failed_Count(Constants.MONITOR_BUSINAME, "b104", "1", "int", ""),
	/**团购订单创建每秒创建次数*/
	Order_Creategroup_Count(Constants.MONITOR_BUSINAME, "b100", "1", "int", ""),
	/**团购订单创建每秒创建异常次数*/
	Order_Creategroup_Failed_Count(Constants.MONITOR_BUSINAME, "b105", "1", "int", ""),
	/**线下积分兑换订单创建每秒创建次数*/
	Order_Createofflineexchange_Count(Constants.MONITOR_BUSINAME, "b102", "1", "int", ""),
	/**线下积分兑换订单创建每秒创建异常次数*/
	Order_Createofflineexchange_Failed_Count(Constants.MONITOR_BUSINAME, "b107", "1", "int", ""),
	/**线上积分兑换订单创建每秒创建次数*/
	Order_Createonlineexchange_Count(Constants.MONITOR_BUSINAME, "b103", "1", "int", ""),
	/**线上积分兑换订单创建每秒创建异常次数*/
	Order_Createonlineexchange_Failed_Count(Constants.MONITOR_BUSINAME, "b108", "1", "int", ""),
	/**预售订单创建每秒创建次数*/
	Order_Createpresell_Count(Constants.MONITOR_BUSINAME, "b101", "1", "int", ""),
	/**预售订单创建每秒创建异常次数*/
	Order_Createpresell_Failed_Count(Constants.MONITOR_BUSINAME, "b106", "1", "int", ""),
	/**订单创建时库存变动从redis同步到mysql异常次数*/
	Order_Createupdatestore_Failed_Count(Constants.MONITOR_BUSINAME, "b109", "1", "int", ""),
	/**订单创建时商品销售数量更新异常次数*/
	Order_Createupdatesellcount_Failed_Count(Constants.MONITOR_BUSINAME, "b110", "1", "int", ""),
	
	
	//---------------------支付模块------------------------
	/**与第三方系统交互失败*/
	order_pay_thridparty_mutual_failed(Constants.MONITOR_BUSINAME,"b66","1","int",""),
	/**交易用户不存在或异常*/
	order_pay_user_void(Constants.MONITOR_BUSINAME,"b65","1","int",""),
	/**反向解约快捷签约失败*/
	order_pay_reverse_cancel_fast_pay_failed(Constants.MONITOR_BUSINAME,"b69","1","int",""),
	/**响应报文验签失败*/
	order_pay_verify_sign_failed(Constants.MONITOR_BUSINAME,"b70","1","int",""),
	/**结算订单失败*/
	order_pay_settle_failed(Constants.MONITOR_BUSINAME,"b67","1","int",""),
	/**自动退还积分失败*/
	order_pay_auto_refund_point_failed(Constants.MONITOR_BUSINAME,"b60","1","int",""),
	/**组合支付用户主动退款退还积分失败*/
	order_pay_user_refund_point_failed(Constants.MONITOR_BUSINAME,"b63","1","int",""),
	/**扣除用户赠送积分失败*/
	order_pay_deduct_present_point_failed(Constants.MONITOR_BUSINAME,"b64","1","int",""),
	/**获取缓存信息失败*/
	order_pay_query_redis_failed(Constants.MONITOR_BUSINAME,"b56","1","int",""),
	/**自动赠送积分失败*/
	order_pay_auto_present_point_failed(Constants.MONITOR_BUSINAME,"b62","1","int",""),
	/**支付完成后后续逻辑执行失败*/
	order_pay_dopaytrailing_failed(Constants.MONITOR_BUSINAME,"b58","1","int",""),
	/**支付流水保存失败*/
	order_pay_save_payment_failed(Constants.MONITOR_BUSINAME,"b68","1","int","");
	
	//---------------------支付模块------------------------
	
	;
	
	private String businessName;
	private String attId;
	private String value;
	private String type;
	private String unionAttrId;
	
	private MonitorPointEnum(String businessName, String attId, String value, String type, String unionAttrId){
		this.businessName = businessName;
		this.attId = attId;
		this.value = value;
		this.type = type;
		this.unionAttrId = unionAttrId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnionAttrId() {
		return unionAttrId;
	}

	public void setUnionAttrId(String unionAttrId) {
		this.unionAttrId = unionAttrId;
	}

}
