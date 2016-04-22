namespace java com.froad.thrift.vo.settlement

include "BizMonitor.thrift"
include "Common.thrift"

/**
*结算表结构
*/
struct BossSettlementVo{
	1:string id,			# 主键ID
	2:string settlementId,	# 结算ID
	3:i64 createTime,		# 创建时间
	4:string clientId,		# 客户端ID
	5:string orderId,		# 订单ID
	6:string subOrderId,	# 子订单ID
	7:string merchantId,	# 商户ID
	8:string outletId,		# 门店ID
	9:i32 type,				# 结算类型(3-面对面/1-团购)
	10:string paymentId,	# 支付ID
	11:string settleState,	# 结算状态
	12:double money,		# 结算金额
	13:string remark,		# 备注
	14:string productName,	# 商品名称
	15:string productId,	# 商品ID
	16:i32 productCount,	# 消费数量
	17:list<string> tickets,# 结算卷ids
    18:string merchantName,	# 商户名称
	19:string outletName,	# 门店名称
	20:string billNo; 		#结算账单号
}

/**
 * 分页结构
 */
struct BossSettlementPage{
	1:Common.PageVo page,			# 分页信息
	4:optional string orderId,		# 订单号
	5:optional string clientId,		# 客户端ID
	6:optional string merchantName, 	# 商户名称
	7:optional string outletName,           # 门店名称
	8:optional string settleState,		# 结算状态
	9:optional list<BossSettlementVo> respList, # 查询记录数
	10:optional string billNo; /**账单编号*/
	11:optional string productName; /**商品名称*/
}

/**
* 对外接口
*/
service BossSettlementService extends BizMonitor.BizMonitorService {
	
	/**
	 * 分页查询
	 */
	BossSettlementPage queryByPage(1:BossSettlementPage page);
	
	/**
	 * 更新结算状态
	 * 
	 * @param id 结算ID
	 * @param status 结算状态
	 * @param remark 结算备注
	 */
	Common.ResultVo updateSettleState(1:string id, 2:string status, 3:string remark);
	
	/**
     *结算报表导出
     */
    Common.ExportResultRes exportSettlementQueryByPage(1:BossSettlementPage page);
     
}

