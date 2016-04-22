namespace java com.froad.thrift.vo.order

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/**
 * 添加订单-商品
 */
struct AddProductVo {
     /** 商品ID */
     1:required string productId;   

     /** 普通数量 */
     2:required i32 quantity;       

     /** 预售商品自提网点|线下积分兑换网点 */
     3:string orgCode;      

     /** 预售商品自提网点|线下积分兑换网点名称 */        
     4:string orgName;              
     
     /** 配送|自提 */
     5:optional string deliveryType;   

     /** VIP购买数量 */
     6:required i32 vipQuantity;

     /** 活动ID */
     7:string activeId;

     /** 满赠活动ID */
     8:string giveActiveId;
}

/**
 * 物流详情
 */
struct ShippingDetailVo {
     /** 物流公司ID */
     1:required string deliveryCorpId;
     
     /** 物流公司名称 */
     2:required string deliveryCorpName;
     
     /** 物流单号 */
     3:required string trackingNo;

     /** 发货时间 */
     4:i64 shippingTime;

     /** 收货时间 */
     5:i64 receiptTime;

     /** 收货状态 */
     6:string shippingStatus;
     
     /** 备注 */
     7:string remark;
}

/**
 * 添加订单-商户
 */
struct AddMerchantVo {
     /** 商户ID */
     1:required string merchantId;

     /** 商品信息 */
     2:required list<AddProductVo> addProductVoList;
}

/**
 * 商品详细
 */
struct ProductDetailVo {
     /** 商品ID */
     1:string productId;   
     
     /** 商品名称 */
     2:string productName;      

     /** 商品图片 */
     3:string productImage;   

     /** 单价 */
     4:double money;           

     /** 数量 */
     5:i32 quantity;           
     
     /** VIP单价 */
     6:double vipMoney;     

     /** VIP数量 */
     7:i32 vipQuantity;         
    
     /** 运费 */
     8:double deliveryMoney;    

     /** 商品总价 */
     9:double totalMoney;       

     /** 发货|提货状态 */
     10:string deliveryState;   
     
     /** 是否可评价 */
     11:bool isEnableComment;

     /** 是否已评价 */
     12:bool isCommented;

     /** 可退款数量 */
     13:i32 canRefundCount;

     /** 开始时间 */
     14:i64 startTime;

     /** 截止时间 */
     15:i64 endTime;

     /** 机构号 */
     16:string orgCode;

     /** 机构名 */
     17:string orgName;

     /** 退货数量 */
     18:i32 refundCount;

     /** 可收货数量 */
     19:i32 canReceiveCount;

     /** 商品类型 */
     20:string productType;
     
     /** 活动ID */
     21:string activeId;
}

/**
 * 商品概要
 */
struct ProductSummaryVo {
     /** 商品ID */
     1:string productId;       

     /** 商品名称 */
     2:string productName;      

     /** 商品图片 */
     3:string productImage;  

     /** 商户名称 */
     4:string merchantName;

     /** 商品类型 */
     5:string productType;
}

/**
 * 订单概要信息
 */
struct OrderSummaryVo {
     /** 订单ID */
     1:string orderId;

     /** 订单状态 */
     2:string orderStatus;              

     /** 订单实际总货币值 */
     3:double totalPrice;                             

     /** 商品概要信息 */
     4:list<ProductSummaryVo> productSummaryVoList; 

     /** 是否可取消 */
     5:bool isEnableCancel;

     /** 是否可支付 */
     6:bool isEnablePay;

     /** 是否秒杀 */
     7:bool isSeckill;

     /** 
      * 是否补全配送信息:
      * 0：秒杀成功+已填信息
      * 1：秒杀成功+自提
      * 2：秒杀成功+配送
      * 3：秒杀成功+自提、配送 
      */
     8:string deliveryFlag;

     /** 是否VIP订单 */
     9:bool isVipOrder;

     /** VIP优惠金额 */
     10:double vipDiscount;
}

/**
 * 子订单详情
 */
struct SubOrderDetailVo {
     /** 子订单ID */
     1:string subOrderId; 

     /** 子订单类型 */
     2:string type;        

     /** 商户ID|机构号 */
     3:string merchantId;

     /** 商户名称|机构名称 */
     4:string merchantName;

     /** 子订单汇总金额 */
     5:double subTotalMoney;                  

     /** 商品信息 */
     6:list<ProductDetailVo> productDetailVoList;    

     /** 是否可确认收货 */
     7:bool isEnableConfirmReceive;

     /** 是否可退款 */
     8:bool isEnableRefund;

     /** 是否可查看券码 */
     9:bool isEnableSeeTicket;

     /** 物流详细信息 */
     10:ShippingDetailVo shippingDetailVo;

     /** 子订单退款状态 */
     11:string refundState;
    
     /** 精品商城物流配送状态。0:未发货；1已发货；2.已收货**/
     12:string deliveryState;
}

/**
 * 商品返回信息
 */
struct ProductReturnVo {
     /** 商品ID */
     1:string productId;   
     
     /** 商品名称 */
     2:string productName;      

     /** 商品状态 */
     3:string productStatus;

     /** 预售商品自提网点|线下积分兑换网点-机构号 */
     4:string orgCode;      

     /** 预售商品自提网点|线下积分兑换网点-名称 */
     5:string orgName;

     /** 预售商品自提网点|线下积分兑换网点-状态 */ 
     6:string orgStatus;

     /** 0000：ok，其他异常 */
     7:string errCode;

     /** 提示信息 */
     8:string errMsg;
     
}

/**
 * 商户返回信息
 */
struct MerchantReturnVo {
     /** 子订单类型 */
     1:string type;

     /** 商户ID|机构号 */
     2:string merchantId;

     /** 商户名称|机构名称 */
     3:string merchantName;

     /** 商户状态 */
     4:string merchantStatus;

     /** 商品信息 */
     5:list<ProductReturnVo> productReturnVoList;

     /** 0000：ok，其他异常 */
     6:string errCode;

     /** 提示信息 */
     7:string errMsg;
}

/**
 * 配送|提货信息详情
 */
struct DeliverInfoDetailVo {
     /** 提货|收货人 */
     1:string consignee;     

     /** 提货|收货地址 */
     2:string address;       

     /** 接收券的手机号 */
     3:string phone;         

     /** 收货人信息ID */
     4:string recvId;
}

/**
 * 订单详情
 */
struct OrderDetailVo {
     /** 订单ID */ 
     1:string orderId;            

     /** 总金额 */
     2:double totalPrice;                           

     /** 订单状态 */
     3:string orderStatus;     

     /** 订单创建时间 */
     4:i64 createTime;

     /** 订单支付方式 */
     5:string paymentMethod;       

     /** 订单支付时间 */
     6:i64 paymentTime;            

     /** 分分通积分 */
     7:double fftPoints;          

     /** 银行通积分 */
     8:double bankPoints;                            

     /** 发货信息 */
     9:DeliverInfoDetailVo deliverInfoDetailVo;      

     /** 子订单信息 */
     10:list<SubOrderDetailVo> subOrderDetailVoList; 
     
     /** 是否可取消 */
     11:bool isEnableCancel;

     /** 是否可支付 */
     12:bool isEnablePay;

     /** 是否可退款 */
     13:bool isEnableRefund;

     /** 收货信息ID */
     14:string recvId;

     /** 是否秒杀 */
     15:bool isSeckill;

     /** 
      * 是否补全配送信息:
      * 0：秒杀成功+已填信息
      * 1：秒杀成功+自提
      * 2：秒杀成功+配送
      * 3：秒杀成功+自提、配送 
      */
     16:string deliveryFlag;

     /** 是否VIP订单 */
     17:bool isVipOrder;

     /** VIP优惠金额 */
     18:double vipDiscount;

     /** 赠送积分 */
     19:double givePoints;

     /** 赠送金额 */
     20:double giveMoney;

}

/**
 * 面对面支付订单概要信息
 */
struct QrcodeOrderSummaryVo {
     /** 订单ID */
     1:string orderId;                                  

     /** 订单状态 */
     2:string orderStatus;

     /** 订单实际总货币值 */
     3:double realPrice;

     /** 商户ID */
     4:string merchantId;  

     /** 商户名称 */
     5:string merchantName; 

     /** 创建时间 */
     6:i64 createTime;

     /** 总金额 */
     7:double totalPrice;

     /** 支付时间 */
     8:i64 paymentTime;

     /** 是否VIP订单 */
     9:bool isVipOrder;

     /** 门店ID */
     10:string outletId;

     /** 门店名称 */
     11:string outletName;

     /** 是否可取消 */
     12:bool isEnableCancel;

     /** 是否可支付 */
     13:bool isEnablePay;
}

/**
 * 面对面支付订单详情
 */
struct QrcodeOrderDetailVo {
     /** 订单ID */
     1:string orderId;

     /** 实际总货币值 */                                                     
     2:double realPrice;   

     /** 订单状态 */
     3:string orderStatus;

     /** 订单创建时间 */
     4:i64 createTime;

     /** 订单支付方式 */
     5:string paymentMethod;

     /** 商户号 */
     6:string merchantId;

     /** 商户名称 */
     7:string merchantName;

     /** 商户图片 */
     8:string merchantImg;

     /** 付款账号 */
     9:string accountNo;

     /** 支付号 */
     10:string paymentId;                            
     
     /** 备注 */
     11:string remark;  

     /** 方付通积分 */
     12:double fftPoints;

     /** 银行积分 */
     13:double bankPoints;

     /** 支付渠道 */
     14:string paymentChannel;

     /** 总金额 */
     15:double totalPrice;

     /** 支付时间 */
     16:i64 paymentTime;

     /** 门店ID */
     17:string outletId;

     /** 门店名称 */
     18:string outletName;

     /** 消费总金额 */
     19:double consumeMoney;

     /** 优惠金额 */
     20:double cutMoney;

     /** 积分抵扣金额 */
     21:double pointMoney;
     
     /** 门店logo */
     22:string outletImg;

     /** 门店折扣金额 */
     23:double discountMoney;

     /** 手机号 */
     24:string phone;
}

/**
 * 1.创建订单请求
 */
struct AddOrderVoReq {
     /** 客户端ID */
     1:required string clientId; 

     /** 会员号 */
     2:required i64 memberCode;

     /** 会员名称 */
     3:required string memberName;

     /** 是否为会员 */
     4:optional bool isVip;

     /** 会员等级 */
     5:optional i32 memberLevel; 
     
     /** 订单来源 */
     6:required string createSource;                     

     /** 接收券和短信的手机号 */
     7:optional string phone;                                     
     
     /** 提货信息编号 */
     8:optional string deliverId;                                 
     
     /** 收货信息编号 */
     9:optional string recvId;                                    
     
     /** 银行卡号（线下积分兑换时必须）*/
     10:optional string cardNo;                                   
     
     /** 备注 */
     11:optional string remark;                                   
     
     /** 商户信息 */
     12:required list<AddMerchantVo> addMerchantVoList;    
     
     /** 订单请求类型：["1":"线下积分兑换订单","3":"在线积分兑换订单","2":其他订单] */
     13:required string orderRequestType;    

     /** 银行积分（线下积分兑换时必须）*/
     14:optional i32 bankPoint;

     /** ip地址 */
     15:optional string ip;

     /** 是否购物车订单（true:购物车下单；false:立即购买下单） */
     16:optional bool isShoppingCartOrder;

     /** 银行操作员ID(线下积分兑换) */
     17:optional string operatorId;

     /** 银行操作员名(线下积分兑换) */
     18:optional string operatorName;

     /** 方付通积分（收银台改造后，可以传积分）*/
     19:optional double fftPoint;

     /** 红包ID */
     20:optional string redPacketId;

     /** 现金券ID */
     21:optional string cashCouponId;

     /** 积分对应的现金 */
     22:optional double pointMoney;

     /** 积分比例 */
     23:optional i64 pointRate;
}

/**
 * 1.创建订单响应
 */
struct AddOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;
    
     /** 总金额 */
     3:double totalPrice;

     /** 商户信息 */
     4:list<MerchantReturnVo> merchantReturnVoList;

     /** VIP优惠金额 */
     5:double vipDiscount;

     /** 现金 */
     6:double cash;

     /** 方付通积分 */
     7:double froadPoint;

     /** 银行积分 */
     8:i32 bankPoint;

     /** 0:不跳收银台，1：跳收银台  2：（全红包/现金券）订单已支付完成 */
     9:string isNeedCash;
}



/**
 * 2.获取订单概要请求
 */
struct GetOrderSummaryVoReq {
     /** 客户端ID */
     1:required string clientId;            
     
     /** 会员ID */
     2:required i64 memberCode;          
     
     /** 订单状态 */
     3:optional string orderStatus;               
     
     /** 开始时间 */
     4:optional i64 startTime;                    
     
     /** 结束时间 */
     5:optional i64 endTime;                      
     
     /** 分页信息 */
     6:required Common.PageVo page;      
}

/**
 * 2.获取订单概要响应
 */
struct GetOrderSummaryVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;                     
     
     /** 订单概要信息 */
     2:list<OrderSummaryVo> orderSummaryVoList;      
     
     /** 分页信息 */
     3:optional Common.PageVo page;                  
}

/**
 * 3.获取订单详情请求
 */
struct GetOrderDetailVoReq {
     /** 订单ID */
     1:required string orderId;  

     /** 客户端ID */
     2:required string clientId; 
}

/**
 * 3.获取订单详情响应
 */
struct GetOrderDetailVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;         
     
     /** 订单详细信息 */
     2:OrderDetailVo orderDetailVo;      
}

/**
 * 4.获取面对面支付订单概要请求
 */
struct GetQrcodeOrderSummaryVoReq {
     /** 客户端ID */
     1:required string clientId;            
     
     /** 会员ID */
     2:required i64 memberCode;          
     
     /** 订单状态 */
     3:string orderStatus;               
     
     /** 开始时间 */
     4:i64 startTime;                    
     
     /** 结束时间 */
     5:i64 endTime;                      
     
     /** 分页信息 */
     6:required Common.PageVo page;      
}

/**
 * 4.获取面对面支付订单概要响应
 */
struct GetQrcodeOrderSummaryVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 面对面支付订单概要信息 */
     2:list<QrcodeOrderSummaryVo> orderSummaryVoList;
     
     /** 分页信息 */
     3:optional Common.PageVo page;
}

/**
 * 5.获取面对面支付订单详情请求
 */
struct GetQrcodeOrderDetailVoReq {
     /** 客户端ID */
     1:required string clientId;          
     
     /** 会员ID */
     2:required i64 memberCode;        
     
     /** 订单ID */
     3:required string orderId;           
}

/**
 * 5.获取面对面支付订单详情响应
 */
struct GetQrcodeOrderDetailVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;                
     
     /** 面对面支付订单详细信息 */
     2:QrcodeOrderDetailVo qrcodeOrderDetailVo; 
}

/**
 * 6.取消订单请求
 */
struct DeleteOrderVoReq {	
     /** 订单ID */
     1:required string orderId;  
     
     /** 客户端ID */
     2:required string clientId;
}

/**
 * 6.取消订单响应
 */
struct DeleteOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
}

/**
 * 7.订单发货
 */
struct ShippingOrderVoReq {
     /** 订单ID */
     1:required string orderId;          
     
     /** 子订单ID */
     2:required string subOrderId;       
     
     /** 物流公司ID */
     3:required string deliveryCorpId;   
     
     /** 物流公司名称 */
     4:required string deliveryCorpName; 
     
     /** 物流单号 */
     5:required string trackingNo;       
     
     /** 备注 */
     6:string remark;                    

     /**商户用户号*/
     7:required string merchantUserId;
}

/**
 * 7.1 订单收货
 */
struct ReceiptOrderReq {
	/**订单号*/
     1:required string orderId;
     
     /**子订单号*/
     2:required string subOrderId;

     /**客户端号*/
     3:required string clientId;
}

/**
 * 7.订单发货
 */
struct ShippingOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;      
}

/**
 * 8.面对面支付创建订单请求
 */
struct AddQrcodeOrderVoReq {
     /** 客户端ID */
     1:required string clientId;                            
     
     /** 会员号 */
     2:required i64 memberCode;                          
     
     /** 会员名称 */
     3:required string memberName;                       
     
     /** 订单来源 */
     4:required string createSource;                     
     
     /** 面对面二维码 */
     5:required string qrcode;
     
     /** 备注 */
     6:string remark;

     /** 用户注册手机号 */
     7:required string phone;
     /** 红包ID */
     8:string redPacketId;

     /** 现金券ID */
     9:string cashCouponId;
}

/**
 * 8.面对面支付创建订单响应
 */
struct AddQrcodeOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;            
     
     /** 商户号 */
     3:string merchantId;  

     /** 商户名称 */
     4:string merchantName;

     /** 支付金额 */
     5:double cost;
     
     /** 备注 */
     6:string remark;

     /** 0:不跳收银台，1：跳收银台  2：（全红包/现金券）订单已支付完成 */
     7:string isNeedCash;
}

/**
 * 9.获取VIP节约金额请求
 */
struct GetVipDiscountVoReq {
     /** 客户端ID */
     1:required string clientId;                            
     
     /** 会员号 */
     2:required i64 memberCode;                          
}

/**
 * 9.获取VIP节约金额响应
 */
struct GetVipDiscountVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 支付金额 */
     2:double money;
}

/**
 * 10.获取积分兑换订单列表（在线积分/网点礼品）请求
 */
struct GetPointExchangeListVoReq {
     /** 客户端ID */
     1:required string clientId;                            
     
     /** 会员号 */
     2:required i64 memberCode;     

     /** 在线/网点积分兑换：在线积分列表传0，网点积分列表传1 */
     3:required string queryFlag;
     
     /** 分页信息 */
     4:optional Common.PageVo page;
}

/**
 * (10).获取积分兑换订单列表（线上/线下）实体
 */
struct PointExchangeVo {
     /** 商品ID */
     1:string productId;   
     
     /** 商品名称 */
     2:string productName;      

     /** 商品图片 */
     3:string productImage;   

     /** 单价 */
     4:double money;           

     /** 数量 */
     5:i32 quantity;           
     
     /** 订单状态 */
     6:string orderStatus;     

     /** 兑换时间 */
     7:i64 exchangeTime;
     
     /** 订单号 */
     8:string orderId;

     /** 线上:0 ，线下:1 */
     9:string type;

     /** 机构号 */
     10:string orgCode

     /** 机构名称 */
     11:string orgName;
}

/**
 * 10.获取积分兑换订单列表（在线积分/网点礼品）响应
 */
struct GetPointExchangeListVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 商品ID */
     2:list<PointExchangeVo> pointExchangeVoList;
     
     /** 分页信息 */
     3:optional Common.PageVo page;
}

/**
 * 11.获取积分兑换订单详情（在线积分/网点礼品）请求
 */
struct GetPointExchangeDetailVoReq {
     /** 客户端ID */
     1:required string clientId;                            
     
     /** 会员号 */
     2:required i64 memberCode; 

     /** 订单号 */
     3:required string orderId;
}

/**
 * 11.获取积分兑换订单详情（在线积分/网点礼品）响应
 */
struct GetPointExchangeDetailVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;

     /** 积分兑换详情 */
     2:PointExchangeVo pointExchangeVo;
     
     /** 配送|提货信息 */
     3:DeliverInfoDetailVo deliverInfoDetailVo;
     
}

/**
 * 12.操作库存请求
 */
struct StoreVoReq {
     /**客户端号*/
     1:required string clientId;

     /**订单号*/
     2:required string orderId;
     
     /**操作类型:1 加库存（还库存）  0 减库存（扣库存）*/
     3:required string operationType;
}

/**
 * 12.操作库存响应
 */
struct StoreVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
}

/**
 * 13.获取子订单商品
 */
struct GetSubOrderProductVoReq {

     /** 客户端ID */
     1:required string clientId;
     
     /** 子订单ID */
     2:string subOrderId;

     /** 商品ID */
     3:string productId;

     /** 订单ID */
     4:string orderId;
     
}

/**
 * 13.获取子订单商品
 */
struct GetSubOrderProductVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
     
     /** 商品总价 */
     2:double totalMoney;
}

/**
 * 14.通过二维码获取订单
 */
struct GetOrderByQrcodeVoReq {

     /** 客户端ID */
     1:required string clientId;
     
     /** 二维码 */
     2:required string qrcode;
     
}

/**
 * 14.通过二维码获取订单
 */
struct GetOrderByQrcodeVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;

     /** 订单号 */
     2:string orderId;
     
     /** 订单状态 */
     3:string orderStatus;

     /** 支付时间 */
     4:i64 paymentTime;

     /** 支付金额 */
     5:double totalPrice;

     /** 会员名称 */
     6:string memberName;

     /** 结算状态 */
     7:string settlementStatus;

     /** 会员号 */
     8:i64 memberCode;

     /** 门店ID */
     9:string outletId;
}

/**
 * 15.获取指定用户商品限购数量请求
 */
struct GetMemberBuyLimitVoReq {
     /** 客户端ID */
     1:required string clientId;

     /** 会员号 */
     2:required i64 memberCode;

     /** 是否为会员 */
     3:optional bool isVip;

     /** 商品号 */
     4:required string productId;

     /** 商户号 */
     5:required string merchantId;
}

/**
 * 15.获取指定用户商品限购数量响应
 */
struct GetMemberBuyLimitVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo; 

     /** 会员价可购买数量 */
     2:i32 vipQuantity;

     /** 普通价购买数量 */
     3:i32 quantity;

     /** 总共可购买数量 */
     4:i32 totalQuantity;
}

/**
 * 16.获取订单支付结果请求
 */
struct GetOrderPaymentResultVoReq {
     /** 订单ID */
     1:required string orderId;  

     /** 客户端ID */
     2:required string clientId; 
}

/**
 * 16.获取订单支付结果响应
 */
struct GetOrderPaymentResultVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;         
     
     /** 订单状态 */
     2:string orderStatus;

     /** 订单支付结果描述 */
     3:string remark;
}

/**
 * 17.创建VIP订单请求
 */
struct AddVIPOrderVoReq {
     /** 客户端ID */
     1:required string clientId; 

     /** 会员号 */
     2:required i64 memberCode;

     /** 会员名称 */
     3:required string memberName;

     /** 是否为会员 */
     4:optional bool isVip;

     /** 会员等级 */
     5:optional i32 memberLevel; 
     
     /** 订单来源 */
     6:required string createSource;

     /** 接短信的手机号 */
     7:required string phone;
     
     /** 备注 */
     8:optional string remark;
     
     /** VIP商品ID */
     9:required string productId;

     /** VIP所属行政区域ID */
     10:optional string areaId;

     /** VIP所属行政区域名称 */
     11:optional string areaName;

     /** VIP所属开户行机构号 */
     12:required string bankLabelID;

     /** 开通方式 */
     13:required string clientChannel;
}

/**
 * 17.创建VIP订单响应
 */
struct AddVIPOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;

     /** 订单总金额 */
     3:double totalPrice;
    
}

/**
 * 18.获取子订单请求
 */
struct GetSubOrderVoReq {
     /** 客户端ID */
     1:required string clientId; 
     
     /** 子订单号 */
     2:required string subOrderId;

}

/**
 * 18.获取子订单响应
 */
struct GetSubOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;

     /** 子订单ID */
     3:string subOrderId;

     /** 支付时间 */
     4:i64 paymentTime;

     /** 商品信息 */
     5:list<ProductDetailVo> productList;
    
}


/**
* 19.更新精品商城子订单配送状态请求
*/
struct UpdateSubOrderLogisticVoReq {
	
 	 /** 客户端ID */
     1:required string clientId; 
     
     /** 子订单号 */
     2:required string subOrderId;
     
     /** 配送状态*/
     3:required string deliveryState;
}

/**
* 19.更新精品商城子订单配送状态响应
*/
struct UpdateSubOrderLogisticVoRes {
	
 	/** 结果信息 */
     1:Common.ResultVo resultVo;  
}


/**
 * 20.是否跳收银台接口请求
 */
struct CashierVoReq {
     /** 客户端ID */
     1:required string clientId; 

     /** 会员号 */
     2:required i64 memberCode;

     /** 会员名称 */
     3:required string memberName;
     
     /** 订单ID */
     4:required string orderId;
}

/**
 * 20.是否跳收银台接口响应
 */
struct CashierVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;
    
     /** 总金额 */
     3:double totalPrice;

     /** 现金 */
     4:double cash;

     /** 方付通积分 */
     5:double froadPoint;

     /** 银行积分 */
     6:i32 bankPoint;

     /** 0:不跳收银台，1：跳收银台  2：（全红包/现金券）订单已支付完成 */
     7:string isNeedCash;
}

/**
 * 21.创建惠付订单请求
 */
struct AddPrefPayOrderReq {
     /** 客户端ID */
     1:required string clientId;                            
     
     /** 会员号 */
     2:required i64 memberCode;                          
     
     /** 会员名称 */
     3:required string memberName;                       
     
     /** 订单来源 */
     4:required string createSource;                     
     
     /** 商品ID */
     5:required string productId;
     
     /** 备注 */
     6:string remark;

     /** 用户注册手机号 */
     7:required string phone;

     /** 红包ID */
     8:string redPacketId;

     /** 现金券ID */
     9:string cashCouponId;

     /** 银行积分*/
     10:optional i32 bankPoint;

     /** 方付通积分*/
     11:optional double fftPoint;

     /** 积分对应的现金 */
     12:optional double pointMoney;

     /** 积分比例 */
     13:optional i64 pointRate;
}

/**
 * 21.创建惠付订单响应
 */
struct AddPrefPayOrderRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;  
     
     /** 订单ID */
     2:string orderId;            
     
     /** 商户号 */
     3:string merchantId;  

     /** 商户名称 */
     4:string merchantName;

     /** 支付金额 */
     5:double cost;
     
     /** 备注 */
     6:string remark;

     /** 0:不跳收银台，1：跳收银台  2：（全红包/现金券）订单已支付完成 */
     7:string isNeedCash;
}

/**
 * 21.关闭订单请求
 */
struct CloseOrderVoReq {	
     /** 订单ID */
     1:required string orderId;  
     
     /** 客户端ID */
     2:required string clientId;
}

/**
 * 22.关闭订单响应
 */
struct CloseOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
}

/**
 * 23.支付中关单后订单退款请求(退积分)
 */
struct RefundPayingOrderVoReq {	
     /** 订单ID */
     1:required string orderId;  
     
     /** 客户端ID */
     2:required string clientId;
}

/**
 * 23.支付中关单后订单退款响应(退积分)
 */
struct RefundPayingOrderVoRes {
     /** 结果信息 */
     1:Common.ResultVo resultVo;
}