namespace java com.froad.thrift.vo.providerorder

include "Common.thrift"

struct ProviderOrderQueryReq {
	/** 订单编号 **/
	1:optional string orderId;
	/** 用户编号 **/
	2:optional i64 memberCode;
	/** 客户端ID **/
	3:optional string clientId;
	/** 发货状态 **/
	4:optional string shippingStatus;
	/** 用户手机号 **/
	5:optional string phone;
	/** 起始时间 **/
	6:optional i64 begTime;
	/** 结束时间 **/
	7:optional i64 endTime;
}

struct ProviderOrderQueryVo {
	/** 订单编号 **/
	1:optional string orderId;
	/** 用户编号 **/
	2:optional i64 memberCode;
	/** 用户手机号 **/
	3:optional string phone;
	/** 订单总金额 **/
	4:optional double totalPrice;
	/** 创建时间 **/
	5:optional i64 createTime;
	/** 客户端ID **/
	6:optional string clientId;
	/** 收货地址 **/
	7:optional string recvAddress;
	/** 发货状态 **/
	8:optional string shippingStatus;
	/** 物流公司编号 **/
	9:optional string shippingCorpCode;
	/** 物流公司名称 **/
	10:optional string shippingCorp;
	/** 物流单号 **/
	11:optional string shippingId;
	/** 上传时间 **/
	12:optional i64 inputTime;
	/** 订单状态 **/
	13:optional string orderStatus;
	/** 客户端ID **/
	14:optional string clientName;
	/** 子订单编号 **/
	15:optional string subOrderId;
	/** 收货人姓名 **/
	16:optional string consignee;
	/** 商品信息 **/
	17:optional string productInfo;
}

struct ProviderOrderQueryPageVo {
	/** 返回描述 **/
	1:required Common.ResultVo resultVo;
	/** 分页 **/
	2:required Common.PageVo pageVo;
	/** 分页查询结果集 **/
	3:required list<ProviderOrderQueryVo> orderList;
}

struct ShippingExcelInfoVo {
	/** 订单编号 **/
	1:optional string orderId;
	/** 订单状态 **/
	2:optional string orderStatus;
	/** 用户编号 **/
	3:optional string memberCode;
	/** 用户手机号 **/
	4:optional string phone;
	/** 订单总金额 **/
	5:optional string totalPrice;
	/** 创建时间 **/
	6:optional string createTime;
	/** 客户端名称 **/
	7:optional string clientName;
	/** 收货地址 **/
	8:optional string recvAddress;
	/** 发货状态描述 **/
	9:optional string shippingStatusDesc;
	/** 物流公司名称 **/
	10:optional string shippingCorp;
	/** 物流单号 **/
	11:optional string shippingId;
	/** 上传时间 **/
	12:optional string inputTime;
	/** 收货人姓名 **/
	13:optional string consignee;
	/** 商品信息 **/
	14:optional string productInfo;
}

struct UpdateShippingInfoReq {
	/** 子订单编号 **/
	1:optional string subOrderId;
	/** 物流公司编号 **/
	2:optional string shippingCorpCode;
	/** 物流单号 **/
	3:optional string shippingId;
	/** 上传时间 **/
	4:optional i64 inputTime;
}
















