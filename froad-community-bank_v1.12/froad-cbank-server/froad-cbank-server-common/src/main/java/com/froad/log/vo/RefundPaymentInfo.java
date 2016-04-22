package com.froad.log.vo;

import java.util.List;

public class RefundPaymentInfo {
   
    private String payment_id;
    private Double refund_value;
    private String result_code;
    private String result_desc;
    private String type;
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public Double getRefund_value() {
		return refund_value;
	}
	public void setRefund_value(Double refund_value) {
		this.refund_value = refund_value;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
  
}
