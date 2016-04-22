package com.froad.handler;

import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailResponseVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketProductResponseVo;
import com.froad.thrift.vo.ticket.TicketRelatedRequestVo;
import com.froad.thrift.vo.ticket.VerifyTicketListByPageResponseVo;

public interface TicketQueryHandler {
	/**
	 * 查找券详情
	 * 
	 * @param requestVo
	 * @return
	 */
	public TicketDetailResponseVo findTicketDetails(TicketDetailRequestVo requestVo);
	
	/**
	 * 延长券有效期
	 * 
	 * @param ticketId
	 * @param endDate
	 * @return
	 */
	public ResultVo extendEndDate(String ticketId, long endDate);
	
	/**
	 * 延长商品id的券有效期
	 * @Title: extendEndDateByProductId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param productId
	 * @param endDate
	 * @return
	 * @return ResultVo    返回类型 
	 * @throws
	 */
	public ResultVo extendEndDateByProductId(String productId, long endDate);
	
	/**
	 * 分页获取券
	 * 
	 * @param requestVo
	 * @return
	 */
	public TicketListResponseVo findListWithPage(TicketListRequestVo requestVo);
	
	/**
	 * 分页导出获取券
	 * exportListWithPage:
	 *
	 * @author vania
	 * 2015年9月1日 下午6:51:53
	 * @param requestVo
	 * @return
	 *
	 */
	public ExportResultRes exportListWithPage(TicketListRequestVo requestVo);
	
	/**
	 * 分页查询提货成功的券
	 * @Title: findVerifyTicketListByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param requestVo
	 * @return VerifyTicketListByPageResponseVo    返回类型 
	 * @throws
	 */
	public VerifyTicketListByPageResponseVo findVerifyTicketListByPage(TicketListRequestVo requestVo);
	
	/**
	 * 获取相关有效券
	 * 
	 * @param requestVo
	 * @return
	 */
	public TicketListResponseVo findRelatedTickets(TicketRelatedRequestVo requestVo);
	
	/**
	 * 获取个人端券详情
	 * 
	 * @param requestVo
	 * @return
	 */
	public TicketProductResponseVo findTicketProduct(TicketDetailRequestVo requestVo);
}
