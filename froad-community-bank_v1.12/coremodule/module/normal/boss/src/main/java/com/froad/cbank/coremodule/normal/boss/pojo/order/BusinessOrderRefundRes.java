package com.froad.cbank.coremodule.normal.boss.pojo.order;

import java.io.Serializable;
import java.util.List;

public class BusinessOrderRefundRes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//-------------------退款信息----------------------//
	/** 退款编号 */
	private String refundId;
	/** 退款状态 1:待处理;2:退款中;3:退款完成;4:退款失败;5:异常处理完成;6:商户审核中;7:商户审核拒绝;8:商户审核通过*/
	private String refundStatus;
	/** 退款金额 */
	private String refundValue;
	/** 退款积分 */
	private String refundPoint;
	/** 申请时间 */
	private String applyTime;
	/** 退款时间 */
	private String refundTime;
	/** 退款说明 */
	private String refundDesc;
	//-------------------交易概要信息----------------------//
	/** 退款对应的商品Id*/
	private List<BusinessOrderTradeRes> tradeRes;
	
	
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRefundValue() {
		return refundValue;
	}
	public void setRefundValue(String refundValue) {
		this.refundValue = refundValue;
	}
	public String getRefundPoint() {
		return refundPoint;
	}
	public void setRefundPoint(String refundPoint) {
		this.refundPoint = refundPoint;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	public String getRefundDesc() {
		return refundDesc;
	}
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}
	public List<BusinessOrderTradeRes> getTradeRes() {
		return tradeRes;
	}
	public void setTradeRes(List<BusinessOrderTradeRes> tradeRes) {
		this.tradeRes = tradeRes;
	}
	
	

}
