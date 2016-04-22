namespace java com.froad.thrift.vo.shopingcart


/**
 * 购物车
 */
struct ShoppingCartVoRes{
	1:i64 memberCode,		#会员ID
	2:string clientId,		#客户端ID
	3:string merchantId,	# 商户ID
	4:string merchantName,	# 商户名称
	5:string type,			# 商户类型  1-团购商户2-预售机构商户3-名优特惠商户
	6:bool merchantStatus,	# 商户是否有效
	7:string productId,		# 商品ID
	8:string productName,	# 商品名称
	9:string productImage,	# 商品图片
	10:double money,		# 价格
	11:double vipMoney,		# VIP优惠价格
	12:i32 quantity,		# 数量
	13:i32 vipQuantity,		# vip数量
	14:string status,		# 商品状态 0-未上架 1-商品正常 2-商品下架 3-商品库存不足 4-商品限购数量超限 5-商品过期 6-商品无效 7-库存紧张 8-限购商品
	15:string orgCode,		# 门店ID
	16:string orgName,		# 门店名称
	17:bool orgStatus,		# 门店是否有效
	18:i64 time,			# 更新时间
	19:double deliveryMoney,# 运费
	20:string errCode,
	21:string errMsg;
}

/**
 *  购物车参数Req
 */
struct ShoppingCartVoReq{
	1:i64 memberCode,
	2:string clientId,
	3:string merchantId,
	4:string productId,
	5:optional string outletId,
	6:i32 num,
	7:i32 vipLevel;
}