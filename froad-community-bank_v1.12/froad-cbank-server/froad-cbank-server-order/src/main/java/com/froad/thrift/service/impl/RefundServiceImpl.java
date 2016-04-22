package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.enums.RefundResource;
import com.froad.handler.RefundHandler;
import com.froad.handler.RefundQueryHandler;
import com.froad.handler.impl.RefundHandlerImpl;
import com.froad.handler.impl.RefundQueryHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.refund.RefundDetailRequestVo;
import com.froad.thrift.vo.refund.RefundListRequestVo;
import com.froad.thrift.vo.refund.RefundListResponseVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;
import com.froad.thrift.vo.refund.RefundStateRequestVo;
import com.froad.thrift.vo.refund.RefundStateResponseVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.thrift.vo.refund.RefundTicketsResponseVo;

public class RefundServiceImpl  extends BizMonitorBaseService  implements RefundService.Iface {
	public RefundServiceImpl() {}
	public RefundServiceImpl(String name, String version) {
		super(name, version);
	}
	@Override
	public RefundResponseVo doOrderRefund(RefundRequestVo refundRequestVo)
			throws TException {
		RefundResponseVo responseVo = null;
		RefundHandler refundHandler = null;
		
		LogCvt.info(new StringBuffer("退款申请：").append(refundRequestVo.toString()).toString());
		refundHandler = new RefundHandlerImpl();
		responseVo = refundHandler.refund(RefundResource.USER_REFUND.getCode(), refundRequestVo);
		LogCvt.info(new StringBuffer("退款响应：").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public RefundListResponseVo getRefundList(
			RefundListRequestVo refundListRequestVo) throws TException {
		RefundQueryHandler queryHandler = null;
		RefundListResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("查找退款列表：").append(refundListRequestVo.toString()).toString());
		queryHandler = new RefundQueryHandlerImpl();
		responseVo = queryHandler.getRefundList(refundListRequestVo);
		LogCvt.info(new StringBuffer("查找退款列表响应：").append(responseVo.getPageVo().toString()).toString());
		
		return responseVo;
	}

	@Override
	public RefundResponseVo getRefundDetail(RefundDetailRequestVo requestVo) throws TException {
		RefundQueryHandler queryHandler = null;
		RefundResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("查找退款详情：").append(requestVo.toString()).toString());
		queryHandler = new RefundQueryHandlerImpl();
		responseVo = queryHandler.getRefundDetail(requestVo.getRefundId());
		LogCvt.info(new StringBuffer("查找退款详情响应：").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public RefundStateResponseVo updateRefundState(RefundStateRequestVo refundStateRequestVo) throws TException {
		RefundHandler refundHandler =  new RefundHandlerImpl();
		RefundStateResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("更新退款状态request：").append(refundStateRequestVo.toString()).toString());
		responseVo = refundHandler.updateRefundState(refundStateRequestVo);
		LogCvt.info(new StringBuffer("更新退款状态response：").append(responseVo.toString()).toString());
		
		return responseVo;
	}

	@Override
	public RefundTicketsResponseVo refundTickets(
			RefundTicketsRequestVo requestVo) throws TException {
		RefundHandler refundHandler = null;
		RefundTicketsResponseVo responseVo = null;
		
		LogCvt.info(new StringBuffer("过期退款申请：").append(requestVo.getTicketList()).toString());
		refundHandler = new RefundHandlerImpl();
		responseVo = refundHandler.refundTickets(requestVo);
		LogCvt.info(new StringBuffer("过期退款响应，成功条数：")
				.append(responseVo.getSuccessListSize()).append(" 失败条数：")
				.append(responseVo.getFailedListSize()).toString());
		
		return responseVo;
	}
	@Override
	public RefundResponseVo doOrderRefundOfVIP(String orderId,String clientId,long memberCode,String option) throws TException {
		RefundHandler refundHandler = new RefundHandlerImpl();
		return refundHandler.refundVipStatus(orderId, clientId, memberCode, option);
	}
	@Override
	public RefundResponseVo doRefundOfBoutiqueBoss(String subOrderId,String refundReason,String productId,int quantity,String clientId) throws TException {
		RefundHandler refundHandler = new RefundHandlerImpl();
		return refundHandler.doRefundOfBoutiqueBoss(subOrderId, refundReason, productId, quantity,clientId);
	}
}
