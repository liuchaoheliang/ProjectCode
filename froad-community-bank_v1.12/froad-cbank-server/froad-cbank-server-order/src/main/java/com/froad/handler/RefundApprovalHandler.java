package com.froad.handler;

import java.util.Map;


public interface RefundApprovalHandler {
	/**
	 * 更新退款审批结果
	 * 
	 * @param noticeMap - key=paymentId(积分或现金支付流水号), value=isSuccess
	 * @return
	 */
	public boolean updateApprovalResult(Map<String, Boolean> noticeMap,String... refundId);
}
