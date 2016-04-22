/**
 * Project Name:froad-cbank-server-boss
 * File Name:PaymentInfo.java
 * Package Name:com.froad.po.mongo
 * Date:2015年12月2日上午10:21:43
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ClassName:PaymentInfo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月2日 上午10:21:43
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class PaymentInfo implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -3813418115674139835L;
	
	public PaymentInfo(){
		super();
	}
	
	public PaymentInfo(String paymentId, String refundValue, String resultCode,
			String result_desc, String type) {
		super();
		this.paymentId = paymentId;
		this.refundValue = refundValue;
		this.resultCode = resultCode;
		this.result_desc = result_desc;
		this.type = type;
	}

	private String paymentId;
	private String refundValue;
	private String resultCode;
	private String result_desc;
	private String type;

	@JSONField(name = "payment_id", serialize = true, deserialize = true)
	public String getPaymentId() {
		return paymentId;
	}

	@JSONField(name = "payment_id", serialize = true, deserialize = true)
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@JSONField(name = "refund_value", serialize = true, deserialize = true)
	public String getRefundValue() {
		return refundValue;
	}

	@JSONField(name = "refund_value", serialize = true, deserialize = true)
	public void setRefundValue(String refundValue) {
		this.refundValue = refundValue;
	}

	@JSONField(name = "result_code", serialize = true, deserialize = true)
	public String getResultCode() {
		return resultCode;
	}

	@JSONField(name = "result_code", serialize = true, deserialize = true)
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@JSONField(name = "result_desc", serialize = true, deserialize = true)
	public String getResult_desc() {
		return result_desc;
	}

	@JSONField(name = "result_desc", serialize = true, deserialize = true)
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}

	@JSONField(name = "type", serialize = true, deserialize = true)
	public String getType() {
		return type;
	}

	@JSONField(name = "type", serialize = true, deserialize = true)
	public void setType(String type) {
		this.type = type;
	}
	
	
}
