/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

namespace java com.froad.thrift.vo.coremodule

/**
* 报表基础请求
*/
struct ReportReqVo{
  /**查询纬度类型：amountLat 金额纬度;orderLat 订单纬度;merchantLat 商户纬度;productLat 商品纬度;memberLat 用户纬度;不传则所有**/
  1: optional string latitudeTupe,  
  /**统计类型：day：按天;week：按周;month：按月;diy：自定义**/
  2: optional string countType, 
  /**筛选开始日期**/
  3: optional string startDate, 
  /**筛选结束日期**/
  4: optional string endDate, 
  /**银行**/
  5: optional string clientId, 
  /**业务平台**/
  6: optional string platform, 
  /**机构号**/
  7: optional string orgCode, 
  /**机构等级**/
  8: optional i32 orgLevel, 
  /**是否选中商户维度**/
  9: optional bool checkedMerchant,
  /**是否选中商户类目纬度**/
  10: optional bool checkedMerchantCategory,
  /**业务类型**/
  11: optional string  orderType, 
  /**支付方式**/
  12: optional string  payType, 

  /**金额指标—金额是否选中**/
  13: optional bool checkedAmount,
  /**金额指标—累积金额否选中**/
  14: optional bool checkedAmountCumulation,
  /**金额指标—退款金额否选中**/
  15: optional bool checkedAmountRefund,
  /**金额指标—成交金额否选中**/
  16: optional bool checkedAmountTurnover,
  /**金额指标-累积成交金额否选中**/
  17: optional bool checkedAmountCumulationTurnover,

  /**订单指标-订单否选中**/
  18: optional bool checkedOrderCount,
  /**订单指标-累积订单否选中**/
  19: optional bool checkedOrderCumulation,
  /**订单指标-退款订单否选中**/
  20: optional bool checkedOrderRefund,
  /**订单指标-成交订单否选中**/
  21: optional bool checkedOrderTurnover,
  /**订单指标-累积成交订单否选中**/
  22: optional bool checkedOrderCumulationTurnover,


  /**商户指标--商户数 否选中**/
  23: optional bool checkedMerchantSum,
  /**商户指标--解约商户数 否选中**/
  24: optional bool checkedMerchantCancelContract,
  /**商户指标--累积商户数 否选中**/
  25: optional bool checkedMerchantCumulation,
  /**商户指标--门店数 否选中**/
  26: optional bool checkedOutletCount,
  /**商户指标--累积门店数 否选中**/
  27: optional bool checkedOutletCumulation,

  /**商品指标—商品数量 否选中**/
  28: optional bool checkedProductSum,
  /**商品指数—累积商品数量 否选中**/
  29: optional bool checkedProductCumulation,
  /**商品指数—下架商品数量 否选中**/
  30: optional bool checkedProductDownSum,
  /**商品只是—累积下架商品数量 否选中**/
  31: optional bool checkedProductDownComulation,

  /**用户指数-消费用户数 否选中**/
  32: optional bool checkedMemberCount,
  /**用户指数—累积消费用户数 否选中**/
  33: optional bool checkedMemberComulationCount;
  /**指标类型*/
  34: optional string type;
  
  /**用户指标查询    起始年*/
  35: optional string startYear;
   /**用户指标查询    结束年*/
  36: optional string endYear;
   /**用户指标查询    起始月*/
  37: optional string startMonth;
   /**用户指标查询    结束月*/
  38: optional string endMonth;
   /**用户指标查询    起始周*/
  39: optional string startWeek;
   /**用户指标查询    结束周*/
  40: optional string endWeek;
  
}

/**
* 报表基础返回信息
*/
struct ReportBaseRespVo{
   /**维度天**/
   1: optional string day,
   /**维度周**/
   2: optional string week,
   /**维度月**/
   3: optional string month,
   /**维度年**/
   4: optional string year,
   /**银行**/
   5: optional string clientId,
   /**业务平台**/
   6: optional string platform,
   /**所属机构**/
   7: optional string orgCode,
   /**所属机构名称**/
   8: optional string orgName,
   /**1级机构**/
   9: optional string provinceOrgCode,
   /**2级机构**/
   10: optional string cityOrgCode,
   /**3级机构**/
   11: optional string countyOrgCode,
   /**4级机构**/
   12: optional string villageOrgCode,
   /**商户ID**/
   13: optional string merchantId,
   /**商户名称**/
   14: optional string merchantName,
   /**商户类目**/
   15: optional string merchantCategoryId,
   /**商户类目名称**/
   16: optional string merchantCategoryName,
   /**业务类型**/
   17: optional string orderType,
   /**业务类型名称**/
   18: optional string orderTypeName,
   /**支付方式**/
   19: optional string payType,
   /**支付方式名称**/
   20: optional string payTypeName;
}

/**
* 金额指标信息
*/
struct ReportAmountQuotaVo{
   /**金额**/
   1: optional i64 orderAmount,
   /**累积金额**/
   2: optional i64 orderTotalPrice,
   /**退款金额**/
   3: optional i64 refundAmount,
   /**orderAmount**/
   4: optional i64 refundTotalAmount,
   /**成交金额=金额-退款金额**/
   5: optional i64 amountTurnover,
   /**累积成交金额=累积金额-累积退款金额**/
   6: optional i64 amountCumulatiTurnover;
}

/**
* 订单指标信息
*/
struct ReportOrderQuotaVo{
   /**订单**/
   1: optional i64 orderCount,
   /**累计订单**/
   2: optional i64 orderUmulation,
   /**退款订单**/
   3: optional i64 orderRefund,
   /**累计退款订单**/
   4: optional i64 refundTotalCount,
   /**成交订单=订单-退款订单**/
   5: optional i64 orderTurnover,
   /**累积成交订单=累积订单-累积退款订单**/
   6: optional i64 orderCumulationTurnover;
}
/**
* 用户指标
*/
struct ReportMemberQuotaVo{
	/**消费用户数**/
   1: optional i64 consumptionMemberCount,
   /**累计消费用户数**/
   2: optional i64 consumptionMemberComulation,
}

/**
 *商户指标信息
 */
struct MerchantInfoRespVo{
	
   /**商户数量**/
   1: optional i32 merchantCount,
   /**解约商户数**/
   2: optional i32 merchantCancelContract,
   /**累计商户数**/
   3: optional i32 merchantCumulation,
   /**门店数**/
   4: optional i32 outletSum,
   /**累计门店数**/
   5: optional i32 outletCumulation;
   
}

/**
 *商品指标信息
 */
struct ProductInfoRespVo{
	
   /**商品数量**/
   1: optional i32 productCount,
   /**累计商品数量**/
   2: optional i32 productCumulation,
   /**下架商品数量**/
   3: optional i32 productDownSum,
   /**累计下架商品数量**/
   4: optional i32 productDownComulation,
}


/**
* 报表信息
*/
struct ReportRespVo{
   
   /**报表基础返回信息*/
   1: ReportBaseRespVo baseRespVo ;
    /**金额指标信息*/
   2: ReportAmountQuotaVo amountLat;
    /**订单指标信息*/
   3: ReportOrderQuotaVo orderLat;
    /**商户指标信息*/
   4: MerchantInfoRespVo merchantInfoRespVo;
    /**用户指标信息*/
   5: ReportMemberQuotaVo memberLat;
   /**商品指标信息*/
   6: ProductInfoRespVo productInfoRespVo;
}

 
/**
 * 报表信息分页的响应
 */
struct ReportRespPageVo { 
	/** 分页基础信息 */
	1:Common.PageVo page,
	/** 信息列表 */
	2:list<ReportRespVo> reportResp;
}

/**
 * 任务列表请求信息
 */
struct DefineTaskReqVo{
	
   /**银行**/
   1: optional string clientId,
   /**登录人**/
   2: optional i64 loginId,
   
}


/**
 * 任务列表返回信息
 */
struct DefineTaskRespVo{
	
   /**银行**/
   1: optional string clientId,
   /**登录人**/
   2: optional i64 loginId,
   /**提交时间**/
   3: optional i64 commitTime,
   /**任务完成时间**/
   4: optional i64 completeTime,
   /**状态**/
   5: optional string status;
   /**下载url**/
   6: optional string url;
}

/**
 * 任务列表分页的响应
 */
struct DefineTaskRespPageVo { 
	/** 分页基础信息 */
	1:Common.PageVo page,
	/** 信息列表 */
	2:list<DefineTaskRespVo> defineTaskRespVo;
}

