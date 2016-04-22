namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "OrderVo.thrift"

/**
 * 订单管理模块
 */   
service OrderService extends BizMonitor.BizMonitorService {
     /**
      * 创建订单接口
      */
     OrderVo.AddOrderVoRes addOrder(1:OrderVo.AddOrderVoReq addOrderVoReq);

     /**
      * 获取订单概要
      */
     OrderVo.GetOrderSummaryVoRes getOrderList(1:OrderVo.GetOrderSummaryVoReq getOrderSummaryVoReq);

     /**
      * 获取订单详情
      */
     OrderVo.GetOrderDetailVoRes getOrderDetail(1:OrderVo.GetOrderDetailVoReq getOrderDetailVoReq);

     /**
      * 获取面对面支付订单概要
      */
     OrderVo.GetQrcodeOrderSummaryVoRes getQrcodeOrderList(1:OrderVo.GetQrcodeOrderSummaryVoReq getQrcodeOrderSummaryVoReq);

     /**
      * 获取面对面支付订单详情
      */
     OrderVo.GetQrcodeOrderDetailVoRes getQrcodeOrderDetail(1:OrderVo.GetQrcodeOrderDetailVoReq getQrcodeOrderDetailVoReq);

     /**
      * 取消订单
      */
     OrderVo.DeleteOrderVoRes deleteOrder(1:OrderVo.DeleteOrderVoReq deleteOrderVoReq);
     
     /**
      * 订单发货
      */
     OrderVo.ShippingOrderVoRes shippingOrder(1:OrderVo.ShippingOrderVoReq shippingOrderVoReq);

     /**
      * 订单收货
      */
     OrderVo.ShippingOrderVoRes receiptOrder(1:OrderVo.ReceiptOrderReq receiptOrderReq);
     
     /**
      * 创建面对面支付订单
      */
     OrderVo.AddQrcodeOrderVoRes addQrcodeOrder(1:OrderVo.AddQrcodeOrderVoReq addQrcodeOrderVoReq);
     
     /**
      * 获取VIP节约金额
      */
     OrderVo.GetVipDiscountVoRes getVipDiscount(1:OrderVo.GetVipDiscountVoReq getVipDiscountVoReq);
     
     /**
      * 获取积分兑换订单列表（在线积分/网点礼品）
      */
     OrderVo.GetPointExchangeListVoRes getPointExchangeList(1:OrderVo.GetPointExchangeListVoReq getPointExchangeListVoReq);

     /**
      * 获取积分兑换订单详情（在线积分/网点礼品）
      */
     OrderVo.GetPointExchangeDetailVoRes getPointExchangeDetail(1:OrderVo.GetPointExchangeDetailVoReq getPointExchangeDetailVoReq);

     /**
      * 操作库存
      */
     OrderVo.StoreVoRes storeProcess(1:OrderVo.StoreVoReq storeVoReq);
      
     /**
      * 获取子订单商品价格
      */
     OrderVo.GetSubOrderProductVoRes getSubOrderProduct(1:OrderVo.GetSubOrderProductVoReq getSubOrderProductVoReq);

     /**
      * 根据二维码获取订单信息
      */
     OrderVo.GetOrderByQrcodeVoRes getOrderByQrcode(1:OrderVo.GetOrderByQrcodeVoReq getOrderByQrcodeVoReq);

     /**
      * 获取会员可购买数量
      */
     OrderVo.GetMemberBuyLimitVoRes getMemberBuyLimit(1:OrderVo.GetMemberBuyLimitVoReq getMemberBuyLimitVoReq);

     /**
      * 获取订单支付结果描述
      */
     OrderVo.GetOrderPaymentResultVoRes getOrderPaymentResult(1:OrderVo.GetOrderPaymentResultVoReq getOrderPaymentResultVoReq);

     /**
      * 购买VIP（开通VIP资格）
      */
     OrderVo.AddVIPOrderVoRes addVIPOrder(1:OrderVo.AddVIPOrderVoReq addVIPOrderVoReq);

     /**
      * 获取子订单
      */
     OrderVo.GetSubOrderVoRes getSubOrder(1:OrderVo.GetSubOrderVoReq getSubOrderVoReq);
     
     /**
     * 更新子订单物流状态
     */
     OrderVo.UpdateSubOrderLogisticVoRes updateSubOrderLogistic(1:OrderVo.UpdateSubOrderLogisticVoReq updateSubOrderLogisticVoReq);

     /**
     * 判断是否跳收银台
     */
     OrderVo.CashierVoRes checkBeforeCashier(1:OrderVo.CashierVoReq cashierVoReq);

     /**
      * 创建惠付订单
      */
     OrderVo.AddPrefPayOrderRes addPrefPayOrder(1:OrderVo.AddPrefPayOrderReq addPrefPayOrderReq);

     /**
      * 关闭订单
      */
     OrderVo.CloseOrderVoRes closeOrder(1:OrderVo.CloseOrderVoReq closeOrderVoReq);

     /**
      * 获取用户最近一笔成功支付的VIP订单号
      */
     string getVipOrderId(1:string clientId,2:i64 memberCode);

     /**
      * 支付中关单后订单退款处理(退积分)
      */
     OrderVo.RefundPayingOrderVoRes refundPayingOrder(1:OrderVo.RefundPayingOrderVoReq refundPayingOrderVoReq);

}