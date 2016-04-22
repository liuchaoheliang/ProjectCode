namespace java com.froad.thrift.vo.order

/**
 * 1.创建秒杀订单请求
 */
struct AddSeckillOrderVoReq {
     /** 客户端ID */
     1:required string clientId; 

     /** 会员号 */
     2:required i64 memberCode;

     /** 会员名称 */
     3:required string memberName;

     /** ------------------订单信息------------------ */
     
     /** 订单来源 */
     4:required string createSource;    

     /** 请求号 */
     5:required string reqId;
     
     /** 商户ID */
     6:required string merchantId; 

     /** 商品ID */
     7:required string productId;

     /** 商品名称 */
     8:required string productName;

     /** 商品图片 */
     9:required string productImage;

     /** 商品类型 */
     10:required string productType;

     /** 普通价 */
     11:required double money; 

     /** 普通价购买数量 */
     12:required i32 quantity;       

     /** VIP价：没有就传0 */
     13:required double vipMoney; 

     /** VIP价购买数量：没有就传0 */
     14:required i32 vipQuantity;  

     /** 运费 */
     15:required double deliveryMoney; 
     
      /** 备注 */
     16:optional string remark; 
     
     /** ------------------支付信息------------------ */

     /** 积分机构号 */
     17:optional string pointOrgNo; 

     /** 现金机构号 */
     18:optional string cashOrgNo; 

     /** 支付类型 */
     19:optional i32 payType; 

     /** 现金支付类型 */
     20:optional i32 cashType;
    
     /** 积分值 */
     21:optional double pointAmount;

     /** 现金值 */
     22:optional double cashAmount;

     /** 贴膜卡号码*/
     23:optional string foilCardNum;

     24:optional string token;

     /** 商品默认配送方式 */
     25:required string deliveryOption;

     /** 补全收货信息 */
     26:required AddDeliveryInfoVoReq addDeliveryInfoVoReq;
}

/**
 * 1.创建秒杀订单响应
 */
struct AddSeckillOrderVoRes{
     /** 返回码 : 0000:成功 ,失败：9999 */
     1:string resultCode; 

     /** 结果码描述信息 */
     2:string resultDesc; 

     /** HTML结构第三方支付表单*/
     3:string resultForm;

     /** 订单号 */
     4:string orderId;
}

/**
 * 1.1 创建秒杀订单请求-改造（版本1.1 - 2015.06.07需求变更）
 */
struct AddOrderForSeckillVoReq {
     /** 客户端ID */
     1:required string clientId; 

     /** 会员号 */
     2:required i64 memberCode;

     /** 会员名称 */
     3:required string memberName;

     /** ------------------订单信息------------------ */
     
     /** 订单来源 */
     4:required string createSource;    

     /** 请求号 */
     5:required string reqId;
     
     /** 商户ID */
     6:required string merchantId; 

     /** 商品ID */
     7:required string productId;

     /** 商品名称 */
     8:required string productName;

     /** 商品图片 */
     9:required string productImage;

     /** 商品类型 */
     10:required string productType;

     /** 普通价 */
     11:required double money; 

     /** 普通价购买数量 */
     12:required i32 quantity;       

     /** VIP价：没有就传0 */
     13:required double vipMoney; 

     /** VIP价购买数量：没有就传0 */
     14:required i32 vipQuantity;  

     /** 运费 */
     15:required double deliveryMoney; 
     
      /** 备注 */
     16:optional string remark; 
     
     /** ------------------配送信息------------------ */

     /** 提货信息编号 */
     17:optional string deliverId; 
     
     /** 收货信息编号 */
     18:optional string recvId;

     /** 接收券和短信的手机号 */
     19:optional string phone;  

     /** 预售商品自提网点|线下积分兑换网点 */
     20:optional string orgCode;      

     /** 预售商品自提网点|线下积分兑换网点名称 */        
     21:optional string orgName; 
     
     /** 配送|自提方式 */
     22:optional string deliveryType;
}

/**
 * 1.1 创建秒杀订单响应（版本1.1 - 2015.06.07需求变更）
 */
struct AddOrderForSeckillVoRes{
     /** 返回码 : 0000:成功 ,失败：9999 */
     1:string resultCode; 

     /** 结果码描述信息 */
     2:string resultDesc; 

     /** 订单号 */
     3:string orderId;
}


/**
 * 2.补全收货信息请求
 */
struct AddDeliveryInfoVoReq {
      /** 订单号 */
     1:string orderId;
     
     /** 提货信息编号 */
     2:optional string deliverId; 
     
     /** 收货信息编号 */
     3:optional string recvId;

     /** 接收券和短信的手机号 */
     4:optional string phone;  

     /** 预售商品自提网点|线下积分兑换网点 */
     5:optional string orgCode;      

     /** 预售商品自提网点|线下积分兑换网点名称 */        
     6:optional string orgName; 
     
     /** 配送|自提方式 */
     7:optional string deliveryType;
}

/**
 * 2.补全收货信息响应
 */
struct AddDeliveryInfoVoRes {
     /** 返回码 : 0000:成功 ,失败：9999 */
     1:string resultCode; 

     /** 结果码描述信息 */
     2:string resultDesc; 
}