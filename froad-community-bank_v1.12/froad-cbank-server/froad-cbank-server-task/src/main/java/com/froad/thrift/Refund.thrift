include "Common.thrift"

namespace java com.froad.thrift.service


/**
 * 券过期自动退款requestVo
 */
struct RefundTicketsRequestVo{
	/** 过期券  */
	1:list<RefundTicketVo> ticketList,
}

/**
 * 退款券信息
 */
struct RefundTicketVo{
	/** 子订单ID  */
	1:string suborderId,
	/** 券ID  */
	2:string ticketId,
	/** 商品ID  */
	3:string productId,
	/** 商品数量  */
	4:i32 quantity,	
	/** 备注  */
	5:string remark,		
}

/**
 * 券过期自动退款responseVo
 */
struct RefundTicketsResponseVo{
	/** 返回结果  */
	1:Common.ResultVo resultVo,
	/** 处理成功的过期券  */
	2:list<RefundTicketVo> successList,
	/** 处理失败的过期券  */
	3:list<RefundTicketVo> failedList,		
}

/**
* 退款对外接口
*/
service RefundService {
	
	/**
	* 券过期自动退款接口
	*/
	RefundTicketsResponseVo refundTickets(1:RefundTicketsRequestVo requestVo);	
}