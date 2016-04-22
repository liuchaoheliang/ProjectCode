namespace java com.froad.thrift.vo.pointsettlement

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * 请求VO
 */
struct PointSettlementReqVo{
	/** 客户端ID  */
	1:required string clientId;
	/** 商户ID  */
	2:string merchantId;
	/** 结算开始时间  */
	3:i64 startTime;
	/** 结算结束时间 */
	4:i64 endTime;
	/** 是否面对面（1：是 ，0：否） */
	5:string isQrcode;
	/** 分页信息 */
    6:Common.PageVo page; 
}


/**
 * 积分结算详情响应Vo
 */
struct PointSettlementResVo{
	 /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 联盟积分总计 */
     2:double froadPointCount;
     
     /** 银行积分总计 */
     3:double bankPointCount;
     
     /** 积分结算详情集合 */
     4:list<PointSettlementDetailResVo> detailResVoList;
     
     /** 分页信息 */
     5:optional Common.PageVo page;	
}

/**
 * 积分结算详情响应VO
 */
struct PointSettlementDetailResVo{
	/** 订单编号 */
	1:string orderId;
	/** 会员号 */
	2:string memberCode;
	/** 会员名  */
	3:string memberName;
	/** 会员手机号  */
	4:string mobile;
	/** 商品名称  */
	5:string productName;
	/** 商品单价  */
	6:double productPrice;
	/** 商品数量  */
	7:i32 productQuantity;
	/** 商品总价  */
	8:double productTotalPrice;
	/** 结算总价  */
	9:double totalPrice;
	/** 支付方式  */
	10:string paymentMethod;
	/** 银行积分  */
	11:double bankPoint;
	/** 银行积分比例  */
	12:string bankPointRate;
	/** 联盟积分  */
	13:double froadPoint;
	/** 现金  */
	14:double cash;
	/** 结算时间  */
	15:i64 settlementTime;
	/** 所属商户  */
	16:string merchantName;
	/** 客户端Id  */
	17:string clientId;
}

/**
 * 积分结算商户汇总响应Vo
 */
struct PointSettlementMerchantResVo{
	 /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 积分结算详情集合 */
     2:list<PointSettlementMerchantDetailResVo> detailResVoList;
     
     /** 分页信息 */
     3:optional Common.PageVo page;	
}
/**
 * 积分结算商户汇总详情响应Vo
 */
struct PointSettlementMerchantDetailResVo{
	/** 所属商户  */
	1:string merchantName;
	/** 联盟积分总计 */
    2:double froadPointCount;
    /** 银行积分总计 */
    3:double bankPointCount;
    /** 银行积分比例  */
	4:string bankPointRate;
	/** 订单数量 */
	5:i32 orderCount; 
}

