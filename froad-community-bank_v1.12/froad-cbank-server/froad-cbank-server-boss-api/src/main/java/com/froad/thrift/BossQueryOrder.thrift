namespace java com.froad.thrift.vo.order

include "BizMonitor.thrift"
include "Common.thrift"

/**
 * 团购交易查询req
 */
struct BossGroupOrderListReq {
	/**订单编号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**手机号*/
	3:string phone;
	
	/** 
   	 * 券号状态
     * "1"   - 未消费：[1:已发送;]
     * "2"   - 已消费：[2:已消费;]
     * "3"   - 已过期：[3:已过期;]
     * "4,7" - 已退款：[4:已退款;7:已过期退款;]
     * "5,9" - 退款中：[5:退款中;9:已过期退款中;]
     * "6"   - 退款失败：[6:退款失败;]
     */
	4:string ticketStatus;
	
	/**结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录*/
	5:string settlementStatus;
	
	/**商品名称*/
	6:string productName;
	
	/**用户名称:登录账号*/
	7:string memberName;
	
	/**支付开始时间*/
	8:i64 startTime;
	
	/**支付结束时间*/
    9:i64 endTime;
    
    /**分页*/
    10:Common.PageVo pageVo;
}


/**
 * 团购交易查询res
 */
struct BossGroupOrderListRes {
	/**订单数据*/
	1:list<BossGroupOrderVo> orders;
	/**查询结果*/
	2:Common.ResultVo resultVo;	
	/**分页数据*/
	3:Common.PageVo pageVo;
}

/**
 * 团购交易查询res-list-vo
 */
struct BossGroupOrderVo {
	/**订单号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**购买(支付)时间*/
	3:i64 paymentTime;
	
	/**门店名称*/
	4:string outletName;

	/**商品名称*/
	5:string productName;

	/**用户名称*/
	6:string memberName;

	/**券号状态*/
	7:string ticketStatus;
	
	/**消费时间*/
	8:i64 consumeTime;
	
	/**过期时间*/
	9:i64 expireTime;
	
	/**退款时间*/
	10:i64 refundTime;
	
	/** 手机号 */
	11:string phone;
	
	/**券发送时间*/
	12:i64 sendTime;
	
	/**提货人*/
	13:string consigneeName;
	
	
}


/**
 * 预售交易查询req
 */
struct BossPresellOrderListReq {
	/**订单编号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**手机号*/
	3:string phone;
	
	/** 
   	 * 券号状态
     * "1"   - 未消费：[1:已发送;]
     * "2"   - 已消费：[2:已消费;]
     * "3"   - 已过期：[3:已过期;]
     * "4,7" - 已退款：[4:已退款;7:已过期退款;]
     * "5,9" - 退款中：[5:退款中;9:已过期退款中;]
     * "6"   - 退款失败：[6:退款失败;]
     */
	4:string ticketStatus;
	
	/**结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录*/
	5:string settlementStatus;
	
	/**商品名称*/
	6:string productName;
	
	/**用户名:提(收)货人姓名*/
	7:string memberName;
	
	/**支付开始时间*/
	8:i64 startTime;
	
	/**支付结束时间*/
    9:i64 endTime;
    
    /**分页*/
    10:Common.PageVo pageVo;
}


/**
*预售交易查询res
*/
struct BossPresellOrderListRes {
	/**订单数据*/
	1:list<BossPresellOrderVo> orders;
	/**查询结果*/
	2:Common.ResultVo resultVo;
	/**分页数据*/
	3:Common.PageVo pageVo;
}

/**
*预售交易查询res-list-vo
*/
struct BossPresellOrderVo {
	/**订单号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**购买(支付)时间*/
	3:i64 paymentTime;
	
	/**提货方式:0:送货上门;1:网点自提;2:配送或自提*/
	4:string deliveryType
	
	/**商品名称*/
	5:string productName;

	/**用户名称*/
	6:string memberName;

	/**提货开始时间*/
	7:i64 deliveryStartTime;
	
	/**提货结束时间*/
	8:i64 deliveryEndTime;
	
	/**收货人*/
	9:string consigneeName;
	
	/**收货人电话*/
	10:string consigneePhone;
	
	/**券号状态*/
	11:string ticketStatus;
	
	/**消费时间*/
	12:i64 consumeTime;
	
	/**过期时间*/
	13:i64 expireTime;
	
	/**退款时间*/
	14:i64 refundTime;
	
	/**clinetId*/
	15:string clientId;
	
	/**券表_id*/
	16:string id;
	
}

/**
 *预售交易详情
 */
struct BossPresellDetialRes{
	/**订单号*/
	1:string orderId;
	
	/**券号*/
	2:string ticketId;
	
	/**购买(支付)时间*/
	3:i64 paymentTime;
	
	/**商品名称*/
	4:string productName;
	
	/**用户名称*/
	5:string memberName;
	
	/**券号状态*/
	6:string ticketStatus;
	
	/**提货开始日期*/
	7:i64 deliveryStartTime;
	
	/**提货方式*/
	8:string deliveryType
	
	/**提货人*/
	9:string consigneeName;
	
	/**提货人电话*/
	10:string consigneePhone;
	
	/**提货网点*/
	11:string consigneeAddress;
	
	/**消费时间*/
	12:i64 consumeTime;
	
	/**提货结束日期*/
	13:i64 deliveryEndTime;
	
}

/**Boss订单查询*/
struct BossPointOrderVo {
	/**订单编号*/
	1:string orderId,
	/**下单时间*/
	2:i64 createTime;
	/**总价*/
	3:double totalMoney;
	/**订单状态*/
	4:string orderStatus,
	/**商品ID*/
	5:string productId,
	/**商品名称*/
	6:string productName,
	/**购买数量*/
	7:i32 buyCount,
	/**提货人*/
	8:string reciver,
	/**城市*/
	9:string city,
	/**地址*/
	10:string address,
	/**手机号*/
	11:string phone
}

struct BossPointOrderListReq {
	/**所属客户端*/
	1:string clientId,
	/**开始时间*/
	2:i64 startTime,
	/**结束时间*/
    3:i64 endTime,
    /**商品名称*/
    4:string productName, 
	/**订单编号*/
	5:string subOrderId,
	/**订单状态*/
	6:string orderStatus
    /**分页*/
    7:Common.PageVo pageVo,
}

struct BossPointOrderListRes {
	/**订单数据*/
	1:list<BossPointOrderVo> orders,	
	/**查询结果*/
	2:Common.ResultVo resultVo,		
	/**分页数据*/
	3:Common.PageVo pageVo,
}


/**
*预售账单查询req
*/
struct BossPresellBillListReq {
	/**订单编号*/
	1:string subOrderId;
	
	/**订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成*/
	2:string orderStatus;
	
	/**商品名称*/
	3:string productName;
	
	/**客户端*/
	4:string clientId;
	
	/**订单创建开始时间*/
	5:i64 startTime;
	
	/**订单创建结束时间*/
	6:i64 endTime;
	
	/**分页*/
    7:Common.PageVo pageVo;
}

/**
*预售账单查询res
*/
struct BossPresellBillListRes {
	/**订单数据*/
	1:list<BossPresellBillVo> billVo;
	/**查询结果*/
	2:Common.ResultVo resultVo;	
	/**分页数据*/
	3:Common.PageVo pageVo;

}

/**
*预售账单vo
*/
struct BossPresellBillVo {
	/**子订单编号*/
	1:string subOrderId;
	
	/**下单时间*/
	2:i64 createTime;
	
	/**商品名称*/
	3:string productName;
	
	/**购买数量*/
	4:i32 buyQuantity;
	
	/**支付方式；1：现金支付；2：联盟积分支付；3：银行积分支付；4：联盟积分+现金支付；5：银行积分+现金支付；6：赠送积分*/
	5:string paymentMethod;
	
	/**总价*/
	6:double totalPrice;
	
	/**现金*/
	7:double cashAmount;
	
	/**银行积分*/
	8:double bankPoint;
	
	/**联盟积分*/
	9:double froadPoint;
	
	/**配送方式*/
	10:string deliveryOption;
	
	/**配送地址*/
	11:string address;
	
	/**提（收）货人*/
	12:string consignee;
	
	/**提（收）货人电话*/
	13:string consigneePhone;
	
	/**所属行社*/
	14:string orgName;
	
	/**提货网点*/
	15:string consigneeOrgName;
	
	/**订单状态1:订单创建；2:订单用户关闭;3:订单系统关闭;4:订单支付中;5:订单支付失败;6:订单支付完成*/
	16:string orderStatus;
	
	/**账单号*/
	17:string billNo;
	
	/**当前子订单对应的大订单下时候有退款：0：有；1：没有*/
	18:string isExistsRefund;
	
}


/**
*订单查询req
*/
struct BossQueryOrderListReq {
	/**订单编号*/
	1:string orderId,
	
	/**支付编号*/
	2:string paymentId,
	
	/**用户编号*/
	3:i64 memberCode,
	
	/**提货手机号*/
	4:string phone,
	
	/**订单来源*/
	5:string createSource,
	
	/**订单状态*/
	6:string orderStatus,
	
	/**支付方式*/
	7:string paymentMethod,
	
	/**所属客户端*/
	8:string clientId,
	
	/**开始时间*/
	9:i64 startTime,
	
	/**结束时间*/
    10:i64 endTime,
    
    /**分页*/
    11:Common.PageVo pageVo,
}
/**
*订单查询相应res
*/
struct BossQueryOrderListRes {
	/**订单数据*/
	1:list<BossQueryOrderVo> orders,	
	/**查询结果*/
	2:Common.ResultVo resultVo,		
	/**分页数据*/
	3:Common.PageVo pageVo,
}
/**
*订单查询Vo
*/
struct BossQueryOrderVo {
	/**用户编号*/
	1:i64 memberCode,
	/**订单状态*/
	2:string orderStatus,
	/**所属客户端*/
	3:string clientId,
	/**支付方式*/
	4:string paymentMethod,
	/**订单来源*/
	5:string createSource,
	/**支付时间*/
	6:i64 paymentTime,
	/**收银交易操作人*/
	7:i64 merchantUserId,
	/**总金额*/
	8:double totalPrice,
	/**积分类型*/
	9:i32 pointType
	/**积分金额*/
	10:double points
	/**现金金额*/
	11:double realPrice,
	/**积分兑换比例*/
	12:string pointRate,
	/**赠送积分数*/
	13:double givePoints
	/**备注*/
	14:string remark,
	/**是否面对面-面对面没有详细内容*/
	15:i32 isQrcode,
	/**订单号*/
	16:string orderId,
	/**创建时间*/
	17:i64 createTime;
}

struct BossQueryOrderDetailReq{
	/**订单号*/
	1:required string orderId,
	/**是否面对面标识1：面对面0.不为面对面*/
	2:required i32 isQrcode,
	/**客户端号*/	
	3:string clientId,
}
struct BossQueryOrderDetailRes{
	/**子订单列表*/
	1:list<BossSubOrderVo> subOrders,
	/**查询结果*/
	2:Common.ResultVo resultVo,
}

struct BossSubOrderVo{
	/**商户名*/
	1:string merchantName,
	/**子订单类型*/
	2:string type,
	/**配送状态-名优特惠有值*/
	3:string deliveryState,
	/**商品*/
	4:list<BossSubOrderProductVo> products,
	/**商户ID*/
	5:string merchantId,
	/**接口通用后加上名优特惠的物流信息 物流ID**/
	6:string deliveryCorpId, 		
   	/**物流名称**/
    7:string deliveryCorpName, 
    /**物流单号**/
    8:string trackingNo,			
    /**创建时间**/	
    9:i64 shippingTime,			
    /**子订单编号**/
    10:string subOrderId,		
    /**订单金额*/
    11:double totalPrice,
    /**订单子状态*/
    12:string refundState,
}
/**商品信息-按mongo表类型*/
struct BossSubOrderProductVo {
	/**商品编号*/
	1:string productId,
	/**商品名称*/
	2:string productName,
	/**商品单价*/
	3:double money,
	/**商品VIP价格*/
	4:double vipMoney,
	/**普通购买数量*/
	5:i32 quantity,
	/**VIP购买数量*/
	6:i32 vipQuantity,
	/**赠送积分*/
	7:double points,
	/**配送或者自提方式*/
	8:string deliveryOption,
	/**运费*/
	9:double deliveryMoney,	
	
	/**增加有效期*/
	/**团购预售（券的有效期-开始时间）*/
	10:i64 startTime,
	/**团购预售（券的有效期-结束时间）*/
	11:i64 endTime,
	/**小计*/
	12:double subTotalMoney,
	/**退款数量*/
	13:i32 refundNumber,
	/**剩余数量*/
	14:i32 surplusNumber,
	/**配送或者自提状态*/
	15:string deliveryState,
	/**预售和团购的消费状态 1：未消费， 2. 已消费， 3. 部分消费*/
	16:i32 consumeStatus;
}


/**
 * 面对面惠付订单查询请求
 */
struct BossPrefPayOrderListReq{
	/**订单编号*/
	1:optional string orderId;
	/**用户会员名*/
	2:optional string memberName;
	/**所属客户端*/
	3:optional string clientId;
	/**订单创建来源*/
	4:optional string createSource;
	/**支付类型*/
	5:optional string orderType;
	/**支付方式*/
	6:optional string paymentMethod;
	/**订单状态*/
	7:optional string orderStatus;
	/**订单创建开始时间*/
	8:optional i64 begTime;
	/**订单创建结束时间*/
	9:optional i64 endTime;
}

/**
 * 面对面惠付订单列表
 */
struct BossPrefPayOrderListInfoVo{
	/**订单编号*/
	1:optional string orderId;
	/**订单状态*/
	2:optional string orderStatus;
	/**用户会员名*/
	3:optional string memberName;
	/**用户手机号*/
	4:optional string phone;
	/**支付类型*/
	5:optional string orderType;
	/**所属商户*/
	6:optional string merchantName;
	/**所属门店*/
	7:optional string outletName;
	/**支付方式*/
	8:optional string paymentMethod;
	/**订单总金额*/
	9:optional double totalPrice;
	/**订单现金*/
	10:optional double cash;
	/**联盟积分*/
	11:optional double fftPoints;
	/**银行积分*/
	12:optional double bankPoints;
	/**银行积分兑换比例*/
	13:optional string pointRate;
	/**订单创建时间*/
	14:optional i64 createTime;
	/**订单支付时间*/
	15:optional i64 paymentTime;
	/**客户端ID*/
	16:optional string clientId;
	/**客户端名称*/
	17:optional string clientName;
	/**支付账单号*/
	18:optional string billNos;
	/**订单创建来源*/
	19:optional string createSource;
}


/**
 * 面对面惠付订单分页返回
 */
struct BossPrefPayOrderListRes{
	/**查询结果*/
	1:Common.ResultVo resultVo;	
	/**分页数据*/
	2:Common.PageVo pageVo;
	/**面对面惠付订单列表*/
	3:list<BossPrefPayOrderListInfoVo> infoVos;
}

/**
 * 面对面惠付订单详情
 */
struct BossPrefPayOrderDetailInfoVo{
	/**订单编号*/
	1:optional string orderId;
	/**订单创建时间*/
	2:optional i64 createTime;
	/**用户会员名*/
	3:optional string memberName;
	/**用户真实姓名*/
	4:optional string userName;
	/**客户端名称*/
	5:optional string clientName;
	/**订单创建来源*/
	6:optional string createSource;
	/**支付类型*/
	7:optional string orderType;
	/**支付方式*/
	8:optional string paymentMethod;
	/**订单总金额*/
	9:optional double totalPrice;
	/**订单现金*/
	10:optional double cash;
	/**联盟积分*/
	11:optional double fftPoints;
	/**银行积分*/
	12:optional double bankPoints;
	/**银行积分兑换比例*/
	13:optional string pointRate;
	/**优惠金额*/
	14:optional double discount;
	/**红包折扣*/
	15:optional double redPacketDiscount;
	/**订单状态*/
	16:optional string orderStatus;
	/**订单支付时间*/
	17:optional i64 paymentTime;
	/**支付账单号*/
	18:optional string billNos;
	/**结算所属商户全称*/
	19:optional string merchantName;
	/**交易所属门店全称*/
	20:optional string outletName;
	/**结算状态*/
	21:optional string settleStatus;
	/**结算账单号*/
	22:optional string settleBillNos;
	/**结算时间*/
	23:optional i64 settleTime;
}


/**
 * 面对面惠付订单详情返回
 */
struct BossPrefPayOrderDetailRes{
	/**查询结果*/
	1:Common.ResultVo resultVo;	
	/**面对面惠付订单详情*/
	2:BossPrefPayOrderDetailInfoVo infoVo;
}


/**
 * Boss订单（交易）查询
 */
service BossOrderQueryService extends BizMonitor.BizMonitorService {

	/**
	 * 团购交易查询
	 */
	BossGroupOrderListRes queryGroupOrderList(1:BossGroupOrderListReq req);
	
	/**
	 * 预售交易查询
	 */
	BossPresellOrderListRes queryPresellOrderList(1:BossPresellOrderListReq req);
	
	 /**
      * Boss 查询线上积分兑换订单列表
      */
     BossPointOrderListRes queryPointOrderList(1:BossPointOrderListReq req);
     
     /**
      * 预售交易详情
      */
     BossPresellDetialRes getPresellDetial(1:string clientId, 2:string ticketId);
     
     /**
      * 预售交易账单查询
      */
     BossPresellBillListRes getPresellBillList(1:BossPresellBillListReq req);
     
     /**
      * 预售交易账单报表导出
      */
      Common.ExportResultRes getPresellBillExportUrl(1:BossPresellBillListReq req);
     
     /**
      * 订单查询
      */
     BossQueryOrderListRes getOrderList(1:BossQueryOrderListReq req);
     
     /**
      * 订单详情
      */
     BossQueryOrderDetailRes getOrderDetail(1:BossQueryOrderDetailReq req);
     
     
     
     /**
      * 订单查询导出
      */
     Common.ExportResultRes exportQueryOrderList(1:BossQueryOrderListReq req);
     
     /**
      * 预售交易(预售券)导出
      */
     Common.ExportResultRes exportPresellOrderTicket(1:BossPresellOrderListReq req);
     
     /**
      * 团购交易（团购券）导出
      */
     Common.ExportResultRes exportGroupOrderTicket(1:BossGroupOrderListReq req);
     
     /**
      * 线上积分兑换导出
      */
     Common.ExportResultRes exportPointOrderTicket(1:BossPointOrderListReq req);
     
     
     /**
      * 面对面惠付订单列表查询
      */
     BossPrefPayOrderListRes queryPrefPayOrderList(1:BossPrefPayOrderListReq req, 2:Common.PageVo pageVo);
     
     /**
      * 面对面惠付订单列表导出
      */
     Common.ExportResultRes exportPrefPayOrderList(1:BossPrefPayOrderListReq req);
     
     /**
      * 面对面惠付订单详情
      */
     BossPrefPayOrderDetailRes getPrefPayOrderDetail(1:string clientId, 2:string orderId);
     
}






