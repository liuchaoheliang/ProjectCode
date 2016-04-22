package com.froad.handler;

import com.froad.thrift.vo.refund.RefundListRequestVo;
import com.froad.thrift.vo.refund.RefundListResponseVo;
import com.froad.thrift.vo.refund.RefundResponseVo;

public interface RefundQueryHandler {
	/**
	 * 获取退款详细信息
	 * 
	 * @param refundId
	 * @return
	 */
	public RefundResponseVo getRefundDetail(String refundId);
	
	/**
	 * 获取退款列表
	 * 
	 * @param refundListRequestVo
	 * @return
	 */
	public RefundListResponseVo getRefundList(RefundListRequestVo refundListRequestVo);
}
