package com.froad.handler;

import java.util.List;

import com.froad.po.Ticket;
import com.froad.po.mongo.OrderMongo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerResponseVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyResponseVo;

public interface TicketHandler {
	/**
	 * 生成券
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Ticket> generateTicket(OrderMongo order);
	
	/**
	 * 验券
	 * 
	 * @param requestVo
	 * @return
	 */
	public TicketVerifyResponseVo verifyTickets(TicketVerifyRequestVo requestVo);
	
	/**
	 * 验券(同一种商品合并成一条)
	 * @Title: verifyTicketsOfMerger 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param requestVo
	 * @return
	 * @return TicketVerifyResponseVo    返回类型 
	 * @throws
	 */
	public TicketVerifyOfMergerResponseVo verifyTicketsOfMerger(TicketVerifyOfMergerRequestVo requestVo);
}
