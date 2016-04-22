package com.froad.log.vo;

/**
 * 类描述：支付DATA中，支付流水
 */
public class Payments {
	private String payment_id;
    private Long     payment_time;
    private String  payment_status;
    private String  bill_no;
    private String   payment_org_no;
    private String   payment_reason;
    private String  payment_type;

    private String   payment_type_details;
    private String   payment_value;
    private String  point_rate;
    private String  from_account_no;
    private String  from_user_name;
    private String  to_account_no;
    private String  to_account_name;
    private String  result_code;
    private String  remark;
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public Long getPayment_time() {
		return payment_time;
	}
	public void setPayment_time(Long payment_time) {
		this.payment_time = payment_time;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public String getPayment_org_no() {
		return payment_org_no;
	}
	public void setPayment_org_no(String payment_org_no) {
		this.payment_org_no = payment_org_no;
	}
	public String getPayment_reason() {
		return payment_reason;
	}
	public void setPayment_reason(String payment_reason) {
		this.payment_reason = payment_reason;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getPayment_type_details() {
		return payment_type_details;
	}
	public void setPayment_type_details(String payment_type_details) {
		this.payment_type_details = payment_type_details;
	}
	public String getPayment_value() {
		return payment_value;
	}
	public void setPayment_value(String payment_value) {
		this.payment_value = payment_value;
	}
	public String getPoint_rate() {
		return point_rate;
	}
	public void setPoint_rate(String point_rate) {
		this.point_rate = point_rate;
	}
	public String getFrom_account_no() {
		return from_account_no;
	}
	public void setFrom_account_no(String from_account_no) {
		this.from_account_no = from_account_no;
	}
	public String getFrom_user_name() {
		return from_user_name;
	}
	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
	}
	public String getTo_account_no() {
		return to_account_no;
	}
	public void setTo_account_no(String to_account_no) {
		this.to_account_no = to_account_no;
	}
	public String getTo_account_name() {
		return to_account_name;
	}
	public void setTo_account_name(String to_account_name) {
		this.to_account_name = to_account_name;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
