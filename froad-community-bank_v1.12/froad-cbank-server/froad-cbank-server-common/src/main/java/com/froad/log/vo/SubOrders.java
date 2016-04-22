package com.froad.log.vo;

/**
 * 类描述：支付DATA中，子订单
 */
public class SubOrders {
	private String sub_order_id;
	private String order_type;
	private String org_code;
	public String getSub_order_id() {
		return sub_order_id;
	}
	public void setSub_order_id(String sub_order_id) {
		this.sub_order_id = sub_order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	

}
