package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.po.OrderQueryCondition;
import com.froad.po.Product;
import com.froad.po.Store;
import com.froad.po.base.PageEntity;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thrift.vo.order.CashierVoReq;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoReq;


public interface OrderLogic {
	
	/**
	 * 创建订单
	 * @return 
	 */
	public ResultBean addOrder(OrderMongo order,List<SubOrderMongo> subOrderList);
	
	/**
	 * 创建订单redis缓存
	 * @return 
	 */
	public ResultBean addOrderRedis(OrderMongo order, List<SubOrderMongo> subOrderList);
	
	/**
	 * 创建秒杀订单redis缓存
	 * @return 
	 */
	public ResultBean addOrderRedisForSeckill(OrderMongo order,List<SubOrderMongo> subOrderList);
	
	/**
	 * 订单创建时，建库存:减掉缓存中的store
	 * @return 
	 */
	public ResultBean reduceStore(List<Store> list);
	
	/**
	 * 订单创建失败时，还库存:增加缓存中的store
	 * @return 
	 */
	public ResultBean increaseStore(List<Store> list);
	
	/**
	 * 订单创建成功时，将缓存的数据同步到mysql的库存
	 * @param list
	 * @return
	 */
	public ResultBean updateProductStore(List<Store> list);
	
	/**
	 * 秒杀订单创建成功时，将缓存的数据同步到mysql的库存
	 * @param list
	 * @return
	 */
	public ResultBean updateSeckillProductStore(List<Store> list);
	
	/**
	 * 通过订单号查询大订单
	 * @param orderId 订单号
	 * @return ResultBean（Object : Order 返回大订单）
	 */
	public ResultBean getOrderByOrderId(String clientId,String orderId);
	
	/**
	 * 通过子订单号查询子订单
	 * @param ResultBean（Object : Order 返回子订单）
	 * @return
	 */
	public ResultBean getSubOrderBySubOrderId(String clientId,String subOrderId);
	
	/**
	 * 通过大订单号查询子订单集合
	 * @param orderId 订单号
	 * @return ResultBean（Object : Order 返回子订单集合）
	 */
	public ResultBean getSubOrderListByOrderId(String clientId,String orderId);
	
	/**
	 * 通过大订单号查询库存集合
	 * @param orderId 订单号
	 * @return ResultBean（Object : Order 返回库存集合）
	 */
	public ResultBean getStoreListByOrderId(String clientId,String orderId);
	
	/**
	 * 获取订单概要
	 * @param getOrderSummaryVoReq
	 * @return
	 */
	public ResultBean getOrderSummary(PageEntity<OrderQueryCondition> orderQueryCondition);
	
	/**
	 * 获取订单详情
	 * @param getOrderSummaryVoReq
	 * @return
	 */
	public ResultBean getOrderDetail(OrderQueryCondition orderQueryCondition);

	/**
	 * 获取面对面支付订单概要
	 * @param pageParam
	 * @return
	 */
	public ResultBean getQrcodeOrderSummary(PageEntity<OrderQueryCondition> pageParam);

	/**
	 * 获取面对面支付订单详情
	 * @param orderQueryCondition
	 * @return
	 */
	public ResultBean getQrcodeOrderDetail(OrderQueryCondition orderQueryCondition);
	
	/**
	 * 取消订单
	 * @param orderId 大订单号
	 * @return
	 */
	public ResultBean deleteOrder(String clientId,String orderId);

	/**
	 * 订单发货
	 * @param shippingOrderMongo 订单发货表（mongo）
	 * @return
	 */
	public ResultBean shippingOrder(ShippingOrderMongo shippingOrderMongo);
	
	/**
	 * 订单收货
	 * @param shippingOrderMongo
	 * @return
	 *<pre>
	 *
	 * @Description: 订单收货， 把发货状态修改为收货状态 
	 * @version V1.0 修改人：Arron 日期：2015年4月14日 下午3:42:20 
	 *
	 *</pre>
	 */
	public ResultBean receiptOrder(ShippingOrderMongo shippingOrderMongo);

	/**
	 * 创建面支付订单
	 * @param orderMongo 大订单
	 * @param qrcode 二维码
	 * @return
	 */
	public ResultBean addQrcodeOrder(OrderMongo orderMongo);
	
	/**
	 * 支付完成更新订单
	 * @param order
	 * @return
	 */
	public ResultBean updateOrderForPay(OrderMongo order);
	
	/**
	 * 更新商品销售数量
	 * @param order
	 * @return
	 */
	public ResultBean updateProductSellCount(OrderMongo order);
	
	/**
	 * 返还商品销售数量
	 * @param order
	 * @return
	 */
	public ResultBean returnProductSellCount(List<Product> products);
	
	/**
	 * 更新秒杀商品销售数量
	 * @param order
	 * @return
	 */
	public ResultBean updateSeckillProductSellCount(OrderMongo order);
	
    /**
     * 根据订单号， 更新订单状态
     * @param orderId 大订单号
     * @return boolean
     *<pre>
     *
     * @Description: 订单状态为1. 订单创建，5.订单支付失败的情况下，修改状态为 4.支付中 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午5:02:41 
     *
     *</pre>
     */
    public boolean updateOrderStatusByOrderId(String orderId);
    
    /**
     * 
     * @param orderId    大订单号
     * @param orderStatus 订单状态
     * @param orderState  订单状态（正常，系统关闭，库存）
     * @return boolean
     *<pre>
     *
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午5:19:45 
     *
     *</pre>
     */
    public boolean updateOrderOfStatusByOrderId(String orderId, String orderStatus, String orderState);

	/**
	 * 获取VIP优惠金额
	 * @param orderQueryCondition
	 * @return
	 */
	public ResultBean getVipDiscount(OrderQueryCondition orderQueryCondition);

	/**
	 * 获取积分兑换列表
	 * @param pageParam
	 * @return
	 */
	public ResultBean getPointExchange(PageEntity<OrderQueryCondition> pageParam);

	/**
	 * 获取积分兑换详情
	 * @param orderQueryCondition
	 * @return
	 */
	public ResultBean getPointExchangeDetail(OrderQueryCondition orderQueryCondition);
	
	/**
	 * 获取提货信息详情
	 * @param deliveryId 提货信息ID
	 * @param recvId 收货信息ID
	 * @return
	 */
	public ResultBean getDeliverInfoDetail(String clientId,long memberCode,String deliveryId,String recvId);

	/**
	 * 查询子订单商品详情
	 * @param clientId
	 * @param subOrderId
	 * @param productId
	 * @return
	 */
	public ResultBean getSubOrderProductDetail(String clientId, String orderId,String subOrderId,String productId);

	/**
	 * 通过二维码获取订单
	 * @param clientId
	 * @param qrcode
	 * @return
	 */
	public ResultBean getOrderByQrcode(String clientId, String qrcode);
	
	/**
	 * 退款更新操作
	 * @param clientId 客户端ID
	 * @param orderId  订单号
	 * @param subOrderId 子订单号
	 * @param refundState 退款状态
	 * @param isAllRefund 是否全部退款
	 * @return
	 */
	public ResultBean updateOrderAfterRefund(String clientId,String orderId,String subOrderId,String refundState,boolean isAllRefund);
	
	
	

	/**
	 * 秒杀创建订单时，减库存:减掉缓存中的store
	 * @return 
	 */
	public ResultBean reduceSeckillStore(List<Store> storeList);

	/**
	 * 秒杀创建订单时，加库存:增加缓存中的store
	 * @param storeList
	 * @return
	 */
	public ResultBean increaseSeckillStore(List<Store> storeList);

	/**
	 * 添加秒杀缓存
	 * @param reqId 请求号
	 * @param order 大订单信息
	 */
	public void addSeckillOrderRedis(String reqId, OrderMongo orderMongo);
	
	/**
	 * 秒杀失败时更新受理号缓存
	 * @param reqId
	 * @param resultFlag SeckillResultFlagEnum
	 */
	public void updateSeckillOrderRedis(String reqId,String resultFlag);
	
	/**
	 * 秒杀更新订单
	 * @param orderMongo
	 * @return
	 */
	public ResultBean updateOrderForSeckill(OrderMongo orderMongo,SubOrderMongo subOrderMongo);

	/**
	 * 获取会员可购买数量
	 * @param orderMongo
	 * @return
	 */
	public ResultBean getMemberBuyLimit(String clientId,String merchantId, long memberCode,boolean isVip, String productId);

	/**
	 * 创建VIP订单（开通VIP）
	 * @param order
	 * @return
	 */
	public ResultBean addVIPOrder(OrderMongo order);

	/**
	 * 获取子订单
	 * @param clientId
	 * @param subOrderId
	 * @return
	 */
	public ResultBean getSubOrder(String clientId, String subOrderId);
	
	/**
	 * 更新每单位商品抵扣积分和商品总抵扣积分
	 * @param clientId
	 * @param orderId
	 * @return
	 */
	public boolean updateUnitProductCutPoint(String clientId,String orderId);
	
	/**
	 * 删除每单位商品抵扣积分和商品总抵扣积分
	 * @param clientId
	 * @param orderId
	 * @return
	 */
	public boolean deleteUnitProductCutPoint(String clientId,String orderId);
	
	/**
	 * 大数据平台-创建订单日志
	 * @param order
	 * @param subOrderList
	 * @param action    订单创建："ORDERADD"，订单修改："ORDERMODIFY"
	 */
	public void createOrderLog(OrderMongo order,List<SubOrderMongo> subOrderList,String action);
	
	/**
	 * 订单创建时校验营销活动
	 * @param subOrderList
	 * @return
	 */
	public ResultBean checkOrderForMarketActive(OrderMongo order,List<SubOrderMongo> subOrderList);
	
	/**
	 * 创建订单失败后回滚活动资格
	 * @param subOrderList
	 * @param isJoinMarketActive 是否参与了营销活动
	 * @return
	 */
	public void createOrderFailureGoBackForMarketActive(OrderMongo orderMongo,List<SubOrderMongo> subOrderList,boolean isJoinMarketActive);
	
	/**
	 * 创建面对面订单失败后回滚活动资格
	 * @param subOrderList
	 * @param isJoinMarketActive 是否参与了营销活动
	 * @return
	 */
	public void createOrderFailureGoBackForMarketActive(OrderMongo orderMongo,boolean isJoinMarketActive);
	
	/**
	 * 创建营销订单
	 * @param order
	 * @param subOrderList
	 */
	public ResultBean createMarketOrderForMarketActive(OrderMongo order,List<SubOrderMongo> subOrderList);

	/**
	 * 修改订单营销活动信息
	 * @param order
	 */
	public ResultBean updateOrderForMarketActive(OrderMongo order);
	
	
	/**
	 * 修改精品商城子订单物流配送状态
	 * updateSubOrderLogistic:(这里用一句话描述这个方法的作用).
	 * 2015年11月30日 下午1:36:31
	 * @param clientId
	 * @param subOrderId
	 * @param deliveryState
	 * @return
	 *
	 */
	public ResultBean updateSubOrderLogistic(String clientId, String subOrderId, String deliveryState);

	/**
	 * 校验是否跳收银台
	 * @param cashierVoReq
	 * @return
	 */
	public ResultBean checkBeforeCashier(CashierVoReq cashierVoReq);
	
	
	/**
	 * 关闭订单
	 * @param orderId 大订单号
	 * @return
	 */
	public ResultBean closeOrder(String clientId,String orderId);
	
	
	/**
	 * 更新营销订单
	 * @param order
	 * @param isPaySuccess
	 */
	public void updateMarketOrder(OrderMongo order,boolean isPaySuccess);
	
	/**
	 * 创建惠付订单
	 * @param orderMongo 大订单
	 * @param qrcode 二维码
	 * @return
	 */
	public ResultBean addPrefPayOrder(OrderMongo orderMongo);
	
	/**
	 * 获取用户最近一笔VIP订单
	 * @param clientId  客户端ID
	 * @param memberCode 会员号
	 * @return
	 */
	public String getVipOrderId(String clientId, long memberCode);
	
}
