namespace java com.froad.thrift.vo.ticket

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"


/**
 * 券概要信息VO
 */
struct TicketSummaryVo{
	/** 券ID */
	1:string ticketId,
	/** 商品ID */
	2:string productId,		
	/** 商品数量  */
	3:i32 quantity,		
	/** 备注  */	
	4:string remark,		
	/** 商品名称  */
	5:string productName,
}

/**
 * 券详细信息VO
 */
struct TicketDetailVo{
	/** 券ID */
	1:string ticketId,			
	/** 商户ID */	
	2:string merchantId,			
	/** 商户名 */
	3:string merchantName,		
	/** 商品类型 */	
	4:string productId,				
	/** 商品名称 */
	5:string productName,			
	/** 商品数量 */
	6:i32 quantity,					
	/** 券状态 */
	7:string status,				
	/** 券状态信息 */
	8:string statusMsg,				
	/** 手机号 */
	9:string mobile,				
	/** 提货人编号 */
	10:string memberCode,			
	/** 提货人 */
	11:string memberName,			
	/** 大订单号 */
	12:string orderId,				
	/** 预售自提网店名称 */
	13:string orgName = "",			
	/** 消费时间(可空，如已消费则返回long值的string，如无则返回空值) */
	14:string consumeTime = "",		
	/** 消费门店ID(可空，如已消费则返回long值的string，如无则返回空值) */
	15:string outletId = "",		
	/** 消费门店名称(可空，如已消费则返回long值的string，如无则返回空值) */
	16:string outletName = "",		
	/** 有效期 */
	17:i64 expireTime,				
	/** 退款时间(可空，如已退款则返回long值的string，如无则返回空值) */
	18:string refundTime = "",		
	/** 券生成时间 */
	19:i64 createTime,				
	/** 券二维码地址 */
	20:string imageUrl,				
	/** 商户操作员 */
	21:i64 merchantUserId,			
	/** 商户操作员名字 */
	22:string merchantUserName,		
	/** 子订单类型 */
	23:string type,
	/** 商品价格 */
	24:string price = "",
	/** 子订单号 */
	25:string subOrderId,
	/** 预售二级机构码 */
	26:string sorgCode,
	/** 客户端id */
	27:string clientId,
}

/**
 * 验券请求VO
 */
struct TicketVerifyRequestVo{
	/** 客户端ID */
	1:string clientId,						
	/** 请求来源，1-银行端，2-商户端，3-个人端 */
	2:string resource,					
	/** 商户ID */
	3:string merchantId,				
	/** 消费门店ID */
	4:string outletId,					
	/** 消费门店名称 */
	5:string outletName,				
	/** 机构号 */
	6:string orgCode,					
	/** 商户操作员 */
	7:i64 merchantUserId,				
	/** 商户操作员名字 */
	8:string merchantUserName,			
	/** 券ID列表 */
	9:list<TicketSummaryVo> ticketList,	
	/** 操作来源 */
	10:Common.OriginVo originVo,		
}

/**
 * 验券请求VO(相同商品合并成一个)
 */
struct TicketVerifyOfMergerRequestVo{
	
	/** 客户端ID */
	//1:string clientId,						
	/** 请求来源，1-银行端，2-商户端，3-个人端 */
	//2:string resource,					
	/** 商户ID */
	1:string merchantId,				
	/** 消费门店ID */
	2:string outletId,					
	/** 消费门店名称 */
	3:string outletName,				
	/** 机构号 */
	4:string orgCode,					
	/** 商户操作员 */
	//7:i64 merchantUserId,				
	/** 商户操作员名字 */
	5:string merchantUserName,	
	
	/** 提货人编号 */
	6:string memberCode,		
	/** 子订单id */
	7:string subOrderId,
	/** 必须要验证的券号(客户提供给门店操作员的券号) */
	8:string mustValidTicketId,		
	/** 券ID列表 */
	9:list<TicketSummaryVo> ticketList,	
	/** 操作来源 */
	10:Common.OriginVo originVo,		
}

/**
 * 验券响应VO
 */
struct TicketVerifyResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,				
	/** 验证成功券列表 */
	2:list<TicketSummaryVo> validTickets,	
	/** 验证失败券列表 */
	3:list<TicketSummaryVo> inValidTickets,	
}

/**
 * 合并验券响应VO
 */
struct TicketVerifyOfMergerResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,				
	/** 本次验证成功券列表 */
	2:list<TicketSummaryVo> validTickets,
	/** 验证失败券列表 */
	3:list<TicketSummaryVo> inValidTickets,		
	/** 未验证券列表 */
	4:list<TicketSummaryVo> noValidTickets,	
	/** 本次用户已提货以及未提货商品信息 */
	5:list<MemberProductVerifyInfoVo> memberProductVerifyInfos,
}

/**
 * 用户已提货以及未提货商品信息
 */
struct MemberProductVerifyInfoVo{
	/** 商品id */	
	1:string productId,				
	/** 商品名称 */
	2:string productName,
	/** 本次验证成功商品数量*/	
	3:i32 validProductsCount,	
	/** 未验证商品数量(key为商品id   value为数量) */
	4:i32 noValidProductsCount,
}

/**
 * 查找券列表请求VO
 *  1) source: "1"–根据会员编号(memberCode)查找，"2"-根据券有效期(startDate, endDate)时间范围查找，
 *			   "3"-按商户/门店(merchantId/outletId + ticketId(optional))查找，"4"-按机构码(orgCode)查找，"5"-按子订单(subOrderId+memberCode)查找
 *  2) type: null-"所有类型"，"1"-"团购"，"2"-"预售"
 *  3) status: null-"所有状态"，"0"-"待发送(初始状态)"，"1"-"已发送(未消费)"，"2"-"已消费"，"3"-"已过期"，"4"-"已退款","5"-"即将到期"
 *  4) startDate, endDate 时间格式 - 类似System.currentTimeMillis()
 *  5) pageNumber, pageSize均为null时，查找所有页数
 *  6) resource, 请求来源，1-银行端，2-商户端，3-个人端
 */
struct TicketListRequestVo{
	/** 客户端ID */
	1:string clientId,			
	/** 请求来源，1-银行端，2-商户端，3-个人端 */
	2:string resource,		
	/** 查找选项 */
	3:string source,		
	/** 会员编号(可null) */
	4:string memberCode,	
	/** 商户ID(可null) */
	5:string merchantId,	
	/** 门店ID(可null) */
	6:string outletId,		
	/** 机构码  */
	7:string orgCode,	
	/** 子订单号  */
	8:string subOrderId,	
	/** 开始时间(如source !=2，可传null；否则传long时间值) */
	9:string startDate,		
	/** 结束时间 (如source !=2，可null；否则传long时间值) */
	10:string endDate,		
	/** 类型 */
	11:string type,			
	/** 券状态 */
	12:string status,		
	/** 根据提货码查找相关券 */
	13:string ticketId,	
	/** 根据操作员券 */
	14:string merchantUserName,		
	/** 订单号 */
	15:string orderId,	
	/** 手机号码 */
	16:string mobile,	
	/** 分页信息 */
	17:Common.PageVo pageVo,
	/** 排序字段(不传默认为创建时间倒序,1-根据消费时间倒序) */
	18:string sortField;			
}


/**
 * 券详细信息请求VO
 *  1)option: "1"–根据券号(ticketId)查找有效券是否存在，"2"-根据商户ID和券ID查找券(merchantId + ticketId)，"3"-根据券ID和状态查找券(ticketId + status)。
 *  2)resource, 请求来源，1-银行端，2-商户端，3-个人端
 */
struct TicketDetailRequestVo{
	/** 查找选项 */
	1:string option,		
	/** 客户端ID */
	2:string clientId,			
	/** 请求来源，1-银行端，2-商户端，3-个人端 */
	3:string resource,		
	/** 商户ID(可null) */
	4:string merchantId,	
	/** 券ID */
	5:string ticketId,		
	/** 券状态(可null) */
	6:string status,		
}

/**
 * 根据券ID获取相关券
 *  1)resource, 请求来源，1-银行端，2-商户端，3-个人端
 */
struct TicketRelatedRequestVo{
	/** 请求来源，1-银行端，2-商户端，3-个人端 */
	1:string resource,		
	/** 券ID */
	2:string ticketId,		
	/** 网点，仅预售需传 */
	3:string orgCode,	
}

/**
 * 券详细信息响应VO
 */
struct TicketDetailResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,					
	/** 商品详情 */
	2:TicketDetailVo ticketDetail,				
}

/**
 * 查找券列表响应VO
 */
struct TicketListResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,					
	/** 商品类型 */
	2:list<TicketDetailVo> ticketDetailList,	
	/** 分页信息 */
	3:Common.PageVo pageVo,						
}


struct VerifyTicketListByPageResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,					
	/** 商品类型 */
	2:list<TicketDetailVo> ticketDetailList,
	/** 总金额 */	
	3:string totalAmount;
	/** 分页信息 */
	4:Common.PageVo pageVo,						
}

/**
 * 券商品信息VO
 */
struct TicketProductVo{
	/** 商品ID */
	1:string productId,		
	/** 商品名 */
	2:string productName,		
	/** 商品数量  */
	3:i32 quantity,		
	/** 提货状态  */	
	4:string status,		
}

/**
 * 券商品详细信息响应VO
 */
struct TicketProductResponseVo{
	/** 返回结果 */
	1:Common.ResultVo resultVo,					
	/** 券ID */
	2:string ticketId,			
	/** 券状态 */
	3:string status,				
	/** 提货人编号 */
	4:string memberCode,			
	/** 提货人 */
	5:string memberName,			
	/** 大订单号 */
	6:string orderId,				
	/** 预售自提网店名称 */
	7:string orgName = "",			
	/** 消费时间(可空，如已消费则返回long值的string，如无则返回空值) */
	8:string consumeTime = "",		
	/** 消费门店ID(可空，如已消费则返回long值的string，如无则返回空值) */
	9:string outletId = "",		
	/** 消费门店名称(可空，如已消费则返回long值的string，如无则返回空值) */
	10:string outletName = "",		
	/** 有效期 */
	11:i64 expireTime,				
	/** 退款时间(可空，如已退款则返回long值的string，如无则返回空值) */
	12:string refundTime = "",		
	/** 券生成时间 */
	13:i64 createTime,				
	/** 券二维码地址 */
	14:string imageUrl,				
	/** 商户操作员 */
	15:i64 merchantUserId,			
	/** 商户操作员名字 */
	16:string merchantUserName,		
	/** 子订单类型 */
	17:string type,
	/** 商户 */
	18:string merchantId,			
	/** 商户名 */
	19:string merchantName,		
	/** 子订单类型 */
	20:list<TicketProductVo> ticketProduct,				
}