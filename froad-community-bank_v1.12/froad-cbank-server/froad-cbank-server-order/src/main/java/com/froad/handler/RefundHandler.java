package com.froad.handler;

import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;
import com.froad.thrift.vo.refund.RefundStateRequestVo;
import com.froad.thrift.vo.refund.RefundStateResponseVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.thrift.vo.refund.RefundTicketsResponseVo;

public interface RefundHandler {
	/**
	 * 用户申请退款
	 * 
	 * @param refundResource
	 * @param refundRequestVo
	 * @return
	 */
	public RefundResponseVo refund(String refundResource, RefundRequestVo refundRequestVo);
	
	/**
	 * 更新退款状态
	 * 
	 * @param refundStateRequestVo
	 * @return
	 */
	public RefundStateResponseVo updateRefundState(RefundStateRequestVo refundStateRequestVo);
	
	/**
	 * 团购券过期自动退款
	 * 
	 * @param requestVo
	 * @return
	 */
	public RefundTicketsResponseVo refundTickets(RefundTicketsRequestVo requestVo);
	
	/**
	 * 退还VIP购买资格
	 * @param orderId
	 * @return
	 */
	public RefundResponseVo refundVipStatus(String orderId,String clientId,long memberCode,String option);
	
	/**
	 * 专用的boss退款会员精品预售订单
	 * doRefundOfBoutiqueBoss:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2016-1-5 下午5:21:30
	 * @param subOrderId
	 * @param refundReason
	 * @return
	 *
	 */
	public RefundResponseVo doRefundOfBoutiqueBoss(String subOrderId,String refundReason,String productId,int quantity,String clientId);
}
