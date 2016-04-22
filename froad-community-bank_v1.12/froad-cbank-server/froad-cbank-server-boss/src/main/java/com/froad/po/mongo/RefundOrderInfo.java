/**
 * Project Name:froad-cbank-server-boss
 * File Name:RefundOrderInfo.java
 * Package Name:com.froad.po.mongo
 * Date:2015年11月30日上午11:21:20
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * ClassName:RefundOrderInfo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月30日 上午11:21:20
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class RefundOrderInfo implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -8711088461062721550L;
	
	public RefundOrderInfo(){
		super();
	}

	public RefundOrderInfo(String refundId, String refundStatus,Date createTime,
			Date refundTime, String refundDesc, List<PaymentInfo> paymentInfo) {
		super();
		this.refundId = refundId;
		this.refundStatus = refundStatus;
		this.createTime = createTime;
		this.refundTime = refundTime;
		this.refundDesc = refundDesc;
		this.paymentInfo = paymentInfo;
	}

	
	/**
	 * 退款编号
	 */
	private String refundId;
	
	/**
	 * 退款状态
	 */
	private String refundStatus;
	
	/**
	 * 申请时间
	 */
	private Date createTime;
	
	/**
	 * 退款时间
	 */
	private Date refundTime;
	
	/**
	 * 退款说明
	 */
	private String refundDesc;
	
	@JSONField(name = "payment_info", serialize = true, deserialize = true)
	private List<PaymentInfo> paymentInfo;
	

	public List<PaymentInfo> getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(List<PaymentInfo> paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	@JSONField(name="_id", serialize = true, deserialize = true)
	public String getRefundId() {
		return refundId;
	}

	@JSONField(name="_id", serialize = true, deserialize = true)
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	@JSONField(name="refund_state", serialize = true, deserialize = true)
	public String getRefundStatus() {
		return refundStatus;
	}

	@JSONField(name="refund_state", serialize = true, deserialize = true)
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	@JSONField(name="create_time", serialize = true, deserialize = true)
	public Date getCreateTime() {
		return createTime;
	}

	@JSONField(name="create_time", serialize = true, deserialize = true)
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSONField(name="refund_time", serialize = true, deserialize = true)
	public Date getRefundTime() {
		return refundTime;
	}

	@JSONField(name="refund_time", serialize = true, deserialize = true)
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	@JSONField(name="reason", serialize = true, deserialize = true)
	public String getRefundDesc() {
		return refundDesc;
	}

	@JSONField(name="reason", serialize = true, deserialize = true)
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

}
