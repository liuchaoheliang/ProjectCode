package com.froad.enums;


/**
 * MongoDB数据库字段与java字段对照
 */
public enum FieldMapping {

	ID("_id","_id", "mongo objectid"),
	CREATE_TIME("create_time","createTime", "生成时间"),
	CLIENT_ID("client_id","clientId", "客户端ID"),
	FORG_CODE("forg_code","forgCode", "一级机构"),
	SORG_CODE("sorg_code","sorgCode", "二级机构"),
	TORG_CODE("torg_code","torgCode", "三级机构"),
	LORG_CODE("lorg_code","lorgCode", "四级机构"),
	MEMBER_CODE("member_code","memberCode", "会员编号"),
	MEMBER_NAME("member_name","memberName", "会员名"),
	ORDER_ID("order_id","orderId", "订单号"),
	SUB_ORDER_ID("sub_order_id","suborderId", "子订单号"),
	TICKET_ID("ticket_id","ticketId", "券号"),
	SMS_NUMBER("sms_number","smsNumber", "接收短信手机号"),
	TYPE("type","type", "类型"),
	PRODUCT_ID("product_id","productId", "商品ID"),
	PRODUCT_NAME("product_name","productName", "商品名"),
	QUANTITY("quantity","quantity", "数量"),
	STATUS("status","status", "状态"),
	REFUND_ID("refund_id","refundId", "退款ID"),
	CONSUME_TIME("consume_time","consumeTime", "消费时间"),
	EXPIRE_TIME("expire_time","expireTime", "过期时间"),
	REFUND_TIME("refund_time","refundTime", "退款时间"),
	MERCHANT_USER_ID("merchant_user_id","merchantUserId", "券消费商户操作员ID"),
	MERCHANT_USER_NAME("merchant_user_name","merchantUserName", "券消费商户操作员名字"),
	REFUND_STATE("refund_state","refundState", "退款状态"),
	PAYMENT_INFO("payment_info","paymentInfo", "支付信息"),
	BILL_NO("bill_no","billNo", "账单编号"),
	PAYMENT_ID("payment_id","paymentId", "支付流水"),
	REFUND_VALUE("refund_value","refundValue", "退款现金、积分"),
	RESULT_CODE("result_code","resultCode", "结果码"),
	RESULT_DESC("result_desc","resultDesc", "返回描述"),
	SHOPPING_INFO("shopping_info","shoppingInfo", "购买商品信息"),
	MERCHANT_ID("merchant_id","merchantId", "商户ID"),
	MERCHANT_NAME("merchant_name","merchantName", "商户名称"),
	PRODUCT_LIST("products","products", "商品列表"),
	IMAGE("image","image", "图片URL"),
	OUTLET_ID("outlet_id","outletId", "门店ID"),
	OUTLET_NAME("outlet_name","outletName", "门店名称"),
	ORG_CODE("org_code","orgCode", "机构号"),
	START_TIME("start_time","startTime", "开始时间"),
	END_TIME("end_time","endTime", "结束时间"),
	REASON("reason","reason", "原因"),
	RECV_ID("recv_id","recvId", "收货地址ID"),
	SETTLE_STATE("settle_state","settleState", "结算状态"),
	TICKETS("tickets","tickets", "券")

	
	, PROCESS_TYPE("process_type", "processType", "流程类型")
	, PROCESS_TYPE_DETAIL("process_type_detail", "processTypeDetail", "流程类型详情")
	, BESS_ID("bess_id", "bessId", "业务id")
	, BESS_DATA("bess_data", "bessData", "业务数据")
	, AUDIT_STATE("audit_state", "auditState", "审核状态")
	, TASK_STATE("task_state", "taskState", "任务状态")
	, INSTANCE_STATE("instance_state", "instanceState", "审核流水状态")
	, CREATE_ACTOR("create_actor", "createActor", "审核创建人信息")
	, AUDIT_ACTOR("audit_actor", "auditActor", "已执行过审核的审核人信息")
	, NEXT_ACTOR("next_actor", "nextActor", "下一个审核人信息")
	, INSTANCE_ID("instance_id", "instanceId", "审核流水号")
	, ACTOR_ID("actor_id", "actorId", "审核操作人id")
	, ACTOR_USER_NAME("actor_user_name", "actorUserName", "审核操作人用户名")
	, ORG_ID("org_id", "orgId", "审核操作人所属组织")
	, FINISH_TIME("finish_time", "finishTime", "完成时间")
	;

	
	private String mongoField;
	private String javaField;
	private String description;
	
	private FieldMapping(String mongoField,String javaField, String description){
		this.mongoField=mongoField;
		this.javaField=javaField;
		this.description=description;
	}

	/**
	 * @return the mongoField
	 */
	public String getMongoField() {
		return mongoField;
	}

	/**
	 * @param mongoField the mongoField to set
	 */
	public void setMongoField(String mongoField) {
		this.mongoField = mongoField;
	}

	/**
	 * @return the javaField
	 */
	public String getJavaField() {
		return javaField;
	}

	/**
	 * @param javaField the javaField to set
	 */
	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 根据mongoDB字段获取枚举
	 * @Title: getFieldMappingByMongoDBField 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param mongoDBField
	 * @return
	 * @return FieldMapping    返回类型 
	 * @throws
	 */
	public static FieldMapping getFieldMappingByMongoDBField(String mongoDBField) {
		if(mongoDBField == null)return null;
		FieldMapping[] es = FieldMapping.values();
		for (FieldMapping fieldMapping : es) {
			if (fieldMapping.getMongoField().equals(mongoDBField))
				return fieldMapping;
		}
		return null;
	}

	/**
	 * 根据java字段获取枚举
	 * @Title: getFieldMappingByJavaField 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param javaField
	 * @return
	 * @return FieldMapping    返回类型 
	 * @throws
	 */
	public static FieldMapping getFieldMappingByJavaField(String javaField) {
		if(javaField == null)return null;
		FieldMapping[] es = FieldMapping.values();
		for (FieldMapping fieldMapping : es) {
			if (fieldMapping.getJavaField().equals(javaField))
				return fieldMapping;
		}
		return null;
	}
	
	
}
