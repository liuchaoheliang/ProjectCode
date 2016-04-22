package com.froad.log.vo;

import java.util.List;

public class OrderRefundDetailLog {

	private String refund_id;
	private Long create_time;
	private String member_code;
	private String order_id;
	private String refund_resource;
	private String refund_state;
	private Long refund_time;
	private Double refund_price;
	private List<RefundProductInfo> products;
	private String merchant_id;
	private String merchant_name;
	private String sub_order_id;
//	private String type;
	private List<RefundPaymentInfo> payment_info;
	
	private String merchant_category_id;
	private String order_type;
	private String payment_type;
	private String f_org_code;
	private String s_org_code;
	private String t_org_code;
	private String l_org_code;
	
	
	
	
	

	public Double getRefund_price() {
		return refund_price;
	}

	public void setRefund_price(Double refund_price) {
		this.refund_price = refund_price;
	}

	public List<RefundPaymentInfo> getPayment_info() {
		return payment_info;
	}

	public void setPayment_info(List<RefundPaymentInfo> payment_info) {
		this.payment_info = payment_info;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public String getSub_order_id() {
		return sub_order_id;
	}

	public void setSub_order_id(String sub_order_id) {
		this.sub_order_id = sub_order_id;
	}

//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public String getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public Long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}

	public String getMember_code() {
		return member_code;
	}

	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRefund_resource() {
		return refund_resource;
	}

	public void setRefund_resource(String refund_resource) {
		this.refund_resource = refund_resource;
	}

	public String getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}

	public Long getRefund_time() {
		return refund_time;
	}

	public void setRefund_time(Long refund_time) {
		this.refund_time = refund_time;
	}

	public List<RefundProductInfo> getProducts() {
		return products;
	}

	public void setProducts(List<RefundProductInfo> products) {
		this.products = products;
	}

	public String getMerchant_category_id() {
		return merchant_category_id;
	}

	public void setMerchant_category_id(String merchant_category_id) {
		this.merchant_category_id = merchant_category_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getF_org_code() {
		return f_org_code;
	}

	public void setF_org_code(String f_org_code) {
		this.f_org_code = f_org_code;
	}

	public String getS_org_code() {
		return s_org_code;
	}

	public void setS_org_code(String s_org_code) {
		this.s_org_code = s_org_code;
	}

	public String getT_org_code() {
		return t_org_code;
	}

	public void setT_org_code(String t_org_code) {
		this.t_org_code = t_org_code;
	}

	public String getL_org_code() {
		return l_org_code;
	}

	public void setL_org_code(String l_org_code) {
		this.l_org_code = l_org_code;
	}



  
}
