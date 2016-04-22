namespace java com.froad.thrift.vo

include "BizMonitor.thrift"
include "Common.thrift"

/**
*结算表结构
*/
struct SettlementVo{
	/** 主键ID */
	1:string id,
		
	/** 结算ID */		
	2:string settlementId,	
	
	/** 创建时间 */
	3:i64 createTime,	
		
	/** 客户端ID */
	4:string clientId,	
		
	/** 订单ID */
	5:string orderId,	
		
	/** 子订单ID */
	6:string subOrderId,	
	
	/** 商户ID */
	7:string merchantId,	
	
	/** 门店ID */
	8:string outletId,		
	
	/** 结算类型(1-团购,2-名优特惠,3-面对面) */
	9:i32 type,		
			
	/** 支付ID */
	10:string paymentId,	
	
	/** 结算状态(0-未结算,1-结算中,2-结算成功,3-结算失败,4-无效结算记录) */
	11:string settleState,	
	
	/** 结算金额 */
	12:double money,	
		
	/** 备注 */
	13:string remark,		
	
	/** 商品名称 */
	14:string productName,	
	
	/** 商品ID */
	15:string productId,	
	
	/** 消费数量 */
	16:i32 productCount,	
	
	/** 结算卷ids */
	17:list<string> tickets,
    
    /** 商户名称 */
    18:string merchantName,	
	
	/** 门店名称 */
	19:string outletName,
	
	/** 结算账单号 */
	20:string billNo; 
}

/**
 * 分页结构
 */
struct SettlementPage{
	/** 分页信息 */
	1:Common.PageVo page,					
	
	/** 开始日期 */
	2:optional i64 begDate,			
	
	/** 结束日期 */
	3:optional i64 endDate,			
	
	/** 订单号 */
	4:optional string orderId,		
	
	/** 客户端ID */
	5:optional string clientId,		
	
	/** 商户名称 */
	6:optional string merchantName, 	
	
	/** 门店名称 */
	7:optional string outletName,           
	
	/** 结算状态[等于](0-未结算,1-结算中,2-结算成功,3-结算失败,4-无效结算记录) */
	8:optional string settleState,		
	
	/** 查询记录数 */
	9:optional list<SettlementVo> respList, 
	
	/** 账单编号 */
	10:optional string billNo; 
	
	/** 结算类型(1-团购,2-名优特惠,3-面对面) */
	11:optional string type; 
	
	/** 券ID */
	12:optional string ticketId; 
	
	/** 结算状态[等于](同settleState字段) */
	13:optional list<string> inSettleState,
	
	/** 结算状态[不等于](同settleState字段) */
	14:optional list<string> notInSettleState,	
}

/**
 *  结算查询VO
 */
struct SettlementVoReq{
	/** 开始日期 */
	1:optional i64 begDate,		
	
	/** 结束日期 */
	2:optional i64 endDate,
			
	/** 订单号 */
	3:optional string orderId,
		
	/** 客户端ID */
	4:optional string clientId,
		
	/** 商户名称 */
	5:optional string merchantName, 	
	
	/** 门店名称 */
	6:optional string outletName,           
	
	/** 结算状态 */
	7:optional string settleState,	
}



/**
* 对外接口
*/
service SettlementService extends BizMonitor.BizMonitorService {
	
	/**
	 * 分页查询
	 */
	SettlementPage queryByPage(1:SettlementPage page);
	/**
	 * 分页导出结算记录成Excel
	 */
	Common.ExportResultRes exportSettlements(1:SettlementPage page);
	
	/**
	 * 查询记录
	 */
	list<SettlementVo> queryList(1:SettlementVoReq req);
	
	/**
	 *  更新
	 */
	Common.ResultVo updateSettleState(1:string id,2:string type,3:string remark);
	/**
	 *  获取详情
	 */
	SettlementVo queryById(1:string id);
	/**
	 *  确认收货名优特惠结算接口
	 *  subOrderId:子订单ID
	 *  source:1-用户确认收货，2-系统确认收货
	 */
	Common.ResultVo specialSettle(1:string subOrderId,2:i32 source);
	
	
	/**
	 * 根据券id集合查询券的结算状态
	 */
	list<SettlementVo> getTicketIdSettlementList(1:list<string> ticketIdList);
	
	
}

