package com.froad.support;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ProductType;
import com.froad.po.DeliverInfo;
import com.froad.po.OrderQueryByBankCondition;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.OrderQueryByMerchantPhoneCondition;
import com.froad.po.OrderQueryCondition;
import com.froad.po.OrderQueryMerchantManageCondition;
import com.froad.po.PresellShip;
import com.froad.po.Product;
import com.froad.po.ProductMonthCount;
import com.froad.po.ProductSeckill;
import com.froad.po.QueryBoutiqueOrderByBankDto;
import com.froad.po.RecvInfo;
import com.froad.po.Ticket;
import com.froad.po.base.PageEntity;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.thrift.vo.order.OrderSummaryVo;
import com.froad.thrift.vo.order.QrcodeOrderDetailVo;
import com.mongodb.DBObject;

/**
 * 订单数据接口API
 */
public interface OrderSupport {

    /**
     * 获取商品详情 Mongo
     * 
     * @param prodcutId
     *            商品ID
     * @return
     */
    public abstract ProductDetail queryProductDetail(String prodcutId);

    /**
     * 添加大订单 Mongo
     * 
     * @param orderMongo
     *            大订单信息
     * @return
     */
    public abstract boolean addOrder(OrderMongo orderMongo);

    /**
     * 添加子订单集合 Mongo
     * 
     * @param subOrderMongoList
     *            子订单集合
     * @return
     */
    public abstract boolean addSubOrderList(List<SubOrderMongo> subOrderMongoList);

    /**
     * 通过订单号查询大订单 Mongo
     * 
     * @param orderId
     * @return
     */
    public abstract OrderMongo getOrderByOrderId(String clientId, String orderId);
    
    /**
     * 通过订单号集合查询大订单 Mongo
     * @param orderIdList
     * @return
     */
    public List<OrderMongo> getOrderListByOrderIdList(String clientId,List<String> orderIdList);

    /**
     * 根据商品ID，订单ID获取商品详情
     * 
     * @Title: getProductMongo
     * @Description: TODO
     * @author: share 2015年3月27日
     * @modify: share 2015年3月27日
     * @param @param orderId
     * @param @param subOrderId
     * @param @param productId
     * @param @return
     * @return ProductMongo
     * @throws
     */
    public abstract ProductMongo getProductMongo(String orderId, String subOrderId, String productId);

    /**
     * 添加大订单缓存 Redis
     * 
     * @param orderId
     * @return
     */
    public abstract boolean addOrderRedis(OrderMongo order);
    
    
    /**
     * 添加会员商品购买历史记录
     * @param list
     * @return
     */
    public abstract boolean addMemberBuyHistory(List<SubOrderMongo> list);
    
    /**
     * 添加会员商品购买历史记录-秒杀
     * @param list
     * @param isIncr  是否增加，true:增加；false：减少
     * @return
     */
    public boolean updateMemberBuyHistoryForSeckill(List<SubOrderMongo> list,boolean isIncr);
    
    /**
     * 减去会员商品购买历史记录
     * @param list
     * @return
     */
    public abstract boolean subtractMemberBuyHistory(String clientId,long memberCode,String productId,Long count,Long vipCount);
    
    /**
     * 减去会员商品购买历史记录
     * @param list
     * @return
     */
    public boolean subtractMemberBuyHistoryForSeckill(String clientId,long memberCode,String productId,Long count,Long vipCount);
    
    /**
     * 更新预售商品已提货数量：count>0，增加；count<0 减少
     * @param clientId 客户端ID
     * @param orgCode 提货网点
     * @param productId 商品ID
     * @param count 数量
     * @return
     */
    public boolean processPresellOutletTokenCountRedis(String clientId, String orgCode,String productId,int count);

    /**
     * 取消订单：用户关闭订单
     * 
     * @param orderId
     * @return
     */
    public abstract boolean deleteOrder(String orderId);
    
    /**
     * 取消子订单：用户关闭子订单
     * 
     * @param orderId
     * @return
     */
    public boolean deleteSubOrder(String orderId);

    /**
     * 获取订单概要信息
     * 
     * @param orderQueryCondition
     *            订单查询条件
     * @return
     */
    public abstract List<OrderSummaryVo> getOrderSummary(OrderQueryCondition orderQueryCondition);

    /**
     * 通过子订单号查询子订单
     * 
     * @param subOrderId
     *            子订单号
     * @return
     */
    public abstract SubOrderMongo getSubOrderBySubOrderId(String client, String subOrderId);
    @Deprecated
    public abstract SubOrderMongo getSubOrderBySubOrderId(String subOrderId);

    /**
     * 子订单集合查询
     * 
     * @param orderId
     *            大订单号
     * @return List<SubOrderMongo>
     */
    public abstract List<SubOrderMongo> getSubOrderListByOrderId(String clientId, String orderId);
    
    @Deprecated
    public abstract List<SubOrderMongo> getSubOrderListByOrderId(String orderId);
    
    /**
     * 子订单集合查询
     * 
     * @param subOrderMongo
     * @return List<SubOrderMongo>
     */
    public abstract List<SubOrderMongo> getSubOrderListByCondition(SubOrderMongo subOrderMongo);

    /**
     * 子订单集合查询
     * 
     * @param orderId
     *            大订单号
     * @return List<SubOrderMongo>
     */
    public abstract List<SubOrderMongo> getSubOrderListByOrderId(String orderId, String[] types);

    /**
     * 查询用户订单购买数
     * 
     * @Title: countMemberOrder
     * @Description: TODO
     * @author: share 2015年3月28日
     * @modify: share 2015年3月28日
     * @param @param memberCode
     * @param @param productId
     * @param @return
     * @return Map<String,Object>
     * @throws
     */
    public abstract Map<String, Object> countMemberOrder(long memberCode, String productId);

    /**
     * 获取预售商品运费
     * 
     * @param clientId
     *            客户端ID
     * @param merchantId
     *            商户ID
     * @param productId
     *            商品ID
     * @return
     */
    public abstract Map<String, String> getProductDeliveryMoney(String clientId, String merchantId, String productId);

    /**
     * 获取面对面支付订单概要
     * 
     * @param orderQueryCondition
     *            查询条件
     * @return MongoPage 面对面支付订单概要集合分页
     */
    public abstract MongoPage getQrcodeOrderSummaryList(PageEntity<OrderQueryCondition> pageCondition);
    
    /**
     * 获取面对面支付订单详情
     * 
     * @param orderQueryCondition
     *            查询条件
     * @return QrcodeOrderDetailVo 面对面支付订单详情
     */
    public abstract QrcodeOrderDetailVo getQrcodeOrderDetail(OrderQueryCondition orderQueryCondition,String paymentChannel);

    /**
     * 添加订单发货信息
     * 
     * @param shippingOrderMongo
     *            订单发货表Mongo
     * @return
     */
    public abstract boolean addShippingInfo(ShippingOrderMongo shippingOrderMongo);
    
    
    /**
     * 修改发货信息
     * @param shippingOrderMongo {@link ShippingOrderMongo}
     * @return {@link Boolean}
     *<pre>
     *
     * @Description: 修改发货信息 
     * @version V1.0 修改人：Arron 日期：2015年4月18日 下午5:40:15 
     *
     *</pre>
     */
    public abstract boolean updateShippingInfo(ShippingOrderMongo shippingOrderMongo);
    
    /**
     * 通过ID获取发货信息
     * @param id 发货表编号
     * @return {@link ShippingOrderMongo}
     *<pre>
     * @Description: 获取物流信息 
     * @version V1.0 修改人：Arron 日期：2015年4月18日 下午5:39:01 
     *</pre>
     */
    public abstract ShippingOrderMongo getShippingInfo(String id);
    /**
     * 修改发货状态
     * @param shippingOrderMongo
     * @return
     *<pre>
     *
     * @Description: 发货状态修改为收货 
     * @version V1.0 修改人：Arron 日期：2015年4月14日 下午3:44:17 
     *
     *</pre>
     */
    public abstract boolean updateShippingInfoOfShippedToReceiptStatus(ShippingOrderMongo shippingOrderMongo);
    
    /**
     * 更新子订单状态---已发货修改为收货
     * @param subOrder
     * @return
     *<pre>
     *
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年4月15日 下午4:08:36 
     *
     *</pre>
     */
    public abstract boolean receiptSubOrder(String subOrder,String orderId);

    /**
     * 根据条件查询大订单分页
     * 
     * @param pageCondition
     *            分页条件
     * @return
     */
    public abstract MongoPage getOrderByConditioinOfPage(PageEntity<OrderQueryCondition> pageCondition);

    /**
     * 查询面对面订单分页
     * 
     * @param pageCondition
     *            查询条件
     * @return MongoPage
     * 
     *         <pre>
     * 
     * @Description: TODO 查询面对面订单 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午2:07:45
     * 
     * </pre>
     */
    public abstract MongoPage queryOrderByFacetfaceConditionOfPage(PageEntity<OrderQueryByMerchantPhoneCondition> pageCondition);
    
    
    /**
     * 商户管理平台查询订单-分页
     * @param pageCondition
     * @return
     *<pre>
     *
     * @Description: 商户管理平台面对面订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午12:33:27 
     *
     *</pre>
     */
    public abstract MongoPage queryOrderByFacetfaceOfMerchantConditionOfPage(PageEntity<OrderQueryMerchantManageCondition> pageCondition);
    
    /**
     * 商户管理平台查询订单-全部
     * @param pageCondition 查询条件
     * @return List
     *<pre>
     *
     * @Description: 提供所有数据下载 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午2:38:57 
     *
     *</pre>
     */
    public List<?> queryOrderByFacetfaceOfMerchantConditionOfAll(OrderQueryMerchantManageCondition condition);
    
    /**
     * 商户管理平台查询订单-分页
     * @param pageEntity 查询条件
     * @return {@link MongoPage}
     *<pre>
     *
     * @Description: 商户分页查询团购等子订单 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午2:58:14 
     *
     *</pre>
     */
    public abstract MongoPage querySubOrderOfMerchantConditonOfPage(PageEntity<OrderQueryMerchantManageCondition> pageEntity);
    /**
     * 商户管理平台查询订单-全部
     * @param condition 查询条件
     * @return {@link List}
     *<pre>
     *
     * @Description: 全部数据-团购等子订单
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午2:58:18 
     *
     *</pre>
     */
    public abstract List<?> querySubOrderOfMerchantConditonOfAll(OrderQueryMerchantManageCondition condition);
    
    
    
    
    /**
     * 根据clientId，merchantId,type,outletId 分页查询子订单
     * @param pageCondition
     * @return
     *<pre>
     *
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午4:43:17 
     *
     *</pre>
     */
    public abstract MongoPage queryBySubOrderByCondition(PageEntity<OrderQueryByMerchantPhoneCondition> pageCondition);
    
    
    /**
     * 根据订单号查找大订单内容
     * @param orderId 订单号
     * @return
     *<pre>
     *
     * @Description: 根据订单号查找订单 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午6:46:11 
     *
     *</pre>
     */
    public abstract OrderMongo getOrderById(String clientId, String orderId);
    
    /**容错需要*/
    @Deprecated
    public abstract OrderMongo getOrderById(String orderId);
    
    /**
     * 根据提货号查找提货信息
     * @param clientId 客户端号
     * @param memberCode 会员号
     * @param deliveryId 提货号
     * @return
     *<pre>
     *
     * @Description: 根据提货号查找提货信息
     * @version V1.0 修改人：zhangkai 日期：2015年4月8日 下午6:46:11 
     *
     *</pre>
     */
    public abstract DeliverInfo getDeliveryInfo(String clientId,long memberCode,String deliveryId);
    
    /**
     * 根据收货号查找收货信息
     * @param clientId 客户端号
     * @param memberCode 会员号
     * @param recvId 收货号
     * @return
     *<pre>
     *
     * @Description: 根据收货号查找收货信息
     * @version V1.0 修改人：zhangkai 日期：2015年4月8日 下午6:46:11 
     *
     *</pre>
     */
    public abstract RecvInfo getRecvInfo(String clientId,long memberCode,String recvId);
    
    /**
     * 根据子订单信息查询收货信息
     * @param subOrderList 子订单集合
     * @param isNeedAreaDetail 是否显示地址详细省市区
     * @return 返回map   order_id : RecvInfo
     */
    public Map<String,RecvInfo> getRecvInfoMap(List<SubOrderMongo> subOrderList,boolean isNeedAreaDetail);
    
    /**
     * 根据手机号码查找所有的收货地址
     * @param phone
     * @return
     *<pre>
     *
     * @Description: 手机号码查找收货地址 
     * @version V1.0 修改人：Arron 日期：2015年4月28日 下午4:07:21 
     *
     *</pre>
     */
    public List<RecvInfo> queryRecvInfos(String phone);
    
    
    
    /**
     * 根据二维码查询对应product_id +"_"+ merchant_user_id
     * @param clientId 客户端号
     * @param qrcode 二维码
     * @return {id:product_id +"_"+ merchant_user_id,state:"0" 或  "1"}
     *<pre>
     *
     * @Description: 根据二维码查询对应product_id +"_"+ merchant_user_id
     * @version V1.0 修改人：zhangkai 日期：2015年4月8日 下午6:46:11 
     *
     *</pre>
     */
    public abstract Map<String,String> getIdByQrcode(String clientId,String qrcode);
    
    /**
     * 根据二维码更新对应状态
     * @param qrcode 二维码
     * @return true 成功     false 失败
     *<pre>
     *
     * @Description: 根据二维码更新对应状态
     * @version V1.0 修改人：zhangkai 日期：2015年4月8日 下午6:46:11 
     *
     *</pre>
     */
    public abstract boolean updateQrcodeState(String clientId,String qrcode,String state);
    
    /**
     * 银行管理-面对面订单管理
     * @param pageEntity 查询条件
     * @return  {@link MongoPage}
     *<pre>
     *
     * @Description: 面对面订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月10日 上午10:40:59 
     *
     *</pre>
     */
    public abstract MongoPage queryOrderOfFacetfaceByBank(PageEntity<OrderQueryByBankCondition> pageEntity);
    
    /**
     * 银行管理-预售，团购订单查询
     * @param pageEntity
     * @return
     *<pre>
     *
     * @Description: 根据交易类型订单查询 
     * @version V1.0 修改人：Arron 日期：2015年4月10日 下午1:53:02 
     *
     *</pre>
     */
    public abstract MongoPage querySubOrderByBank(PageEntity<OrderQueryByBankCondition> pageEntity);
    
    /**
     * 银行管理平台精品商城订单查询
     * queryBoutiqueSubOrderByBank:(这里用一句话描述这个方法的作用).
     *
     * @author zhouzhiwei
     * 2015年12月15日 上午11:48:56
     * @param reqPage
     * @return
     *
     */
    public abstract MongoPage queryBoutiqueSubOrderByBank(PageEntity<QueryBoutiqueOrderByBankDto> reqPage);
    
    /**
     * 根据条件更新大订单
     * @param order
     * @return
     *<pre>
     *
     * @Description: 根据条件更新订单
     * @version V1.0 修改人：zhangkai 日期：2015年4月10日 下午1:53:02 
     *
     *</pre>
     */
    public abstract boolean updateOrderByCondion(OrderMongo order);
    
    /**
     * 根据订单号， 更新订单状态
     * @param orderId
     * @return
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
     * @param orderId
     * @param orderStatus
     * @param orderState
     * @return
     *<pre>
     *
     * @Description: 修改为指定状态 
     * @version V1.0 修改人：Arron 日期：2015年4月11日 下午5:19:45 
     *
     *</pre>
     */
    public boolean updateOrderOfStatusByOrderId(String orderId, String orderStatus, String orderState);

	/**
	 * 获取VIP优惠金额
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public abstract String getVipDiscount(String clientId, Long memberCode);
	
	/**
	 * 获取积分兑换列表
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public abstract MongoPage getPointsOrderListOfPage(PageEntity<OrderQueryCondition> pageCondition);

	/**
	 * 获取积分兑换详情
	 * @param orderQueryCondition
	 * @return
	 */
	public abstract SubOrderMongo getPointsOrderDetail(OrderQueryCondition orderQueryCondition);

	/**
	 * 获取子订单商品详情
	 * @param clientId
	 * @param subOrderId
	 * @param productId
	 * @return
	 */
	public abstract ProductMongo getSubOrderProductDetail(String clientId,String orderId,String subOrderId, String productId);
	
	/**
	 * 消费券后更新子订单
	 * @param orderId
	 * @param subOrderId
	 * @param productId
	 * @param productType
	 * @param outletId
	 * @param deliveryState
	 * @return
	 */
	public abstract boolean updateSubOrderAfterConsumed(String orderId,String subOrderId, String productId,ProductType productType,String outletId, String deliveryState);
	
	/**
	 * 退款操作后更新子订单退款状态
	 * @param clientId 客户端Id
	 * @param orderId 订单号
	 * @param subOrderId 子订单号
	 * @param refundState 退款状态
	 * @param isAllRefund 是否全部退款
	 * @return
	 */
	public boolean updateSubOrderAfterRefund(String clientId,String orderId,String subOrderId,String refundState);

	/**
	 * 更新商品销售数量
	 * @param product
	 * @return
	 */
	public abstract boolean updateProductSellCount(Product product,ProductMonthCount productMonthCount);

	/**
	 * 通过二维码获取面对面订单
	 * @param clientId
	 * @param qrcode
	 * @return
	 */
	public abstract OrderMongo getOrderByQrcode(String clientId, String qrcode);
	
	/**
	 * 获取商户详情
	 * @param clientId
	 * @param qrcode
	 * @return
	 */
	public abstract MerchantDetail getMerchantDetail(String clientId, String merchantId);

	/**
	 * Boss平台查询订单列表
	 * @param pageEntity {@link PageEntity}
	 * @return {@link MongoPage}
	 *<pre>
	 *
	 * @Description: 通过条件 
	 * @version V1.0 修改人：Arron 日期：2015年4月28日 下午5:00:15 
	 *
	 *</pre>
	 */
    public abstract MongoPage queryOrderOfBossByCondition(PageEntity<OrderQueryByBossCondition> pageEntity);
    
	/**
     * 从Redis中获取商品基本信息
     * 
     * @param clientId
     * @param merchantId
     * @param productId
     * @return <pre>
     * 
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年5月1日 下午12:32:03
     * 
     * </pre>
     */
	public abstract Map<String, String> getProduct(String clientId, String merchantId, String productId);

	/**
	 * 添加秒杀缓存
	 * @param reqId
	 * @param order
	 * @return
	 */
	public abstract void addSeckillOrderRedis(String reqId, OrderMongo order);
	
	/**
	 * 更新秒杀受理结果缓存
	 * @param reqId
	 * @param resultFlag
	 */
	public abstract void updateSeckillOrderRedis(String reqId, String resultFlag);
	
	/**
	 * 秒杀更新订单
	 * @param orderMongo
	 * @return
	 */
	public abstract boolean updateOrderForSeckill(OrderMongo orderMongo);

	/**
	 * 更新秒杀子订单
	 * @param subOrderMongo
	 * @return
	 */
	public abstract boolean updateSubOrderForSeckill(SubOrderMongo subOrderMongo);

	/**
	 * 更新商品销量
	 * @param productList
	 * @param productMonthCountList
	 * @return
	 */
	public abstract boolean updateProductSellCount(List<Product> productList,List<ProductMonthCount> productMonthCountList);
	
	/**
	 * 更新秒杀商品销量
	 * @param productList
	 * @param productMonthCountList
	 * @return
	 */
	public abstract boolean updateSeckillProductSellCount(List<ProductSeckill> productList,List<ProductMonthCount> productMonthCountList);
	
	/**
	 * 更新商品库存
	 * @param list
	 * @return
	 */
	public boolean updateProductStore(List<Product> list);

    /**
     * 通过clientId + orderId + subOrderId
     * 
     * @param clientId
     * @param orderId
     * @param subOrderId
     * @return
     */
    public abstract SubOrderMongo getSubOrderByClient(String clientId, String orderId, String subOrderId);

	/**
	 * 查询积分赠送订单明细
	 * @param pageEntity
	 * @return
	 */
	public abstract MongoPage queryGivePointsProductByBoss(PageEntity<OrderQueryByBossCondition> pageEntity);
	
	/**
	 * 送分时-更新赠送积分状态
	 * @param clientId
	 * @param orderId
	 * @param isSuccess  是否赠送成功      赠送成功：true       赠送失败：false
	 */
	public void updateGivePointStatus(String clientId,String orderId,boolean isSuccess);
	
	/**
	 * 退分时-更新赠送积分状态
	 * @param clientId
	 * @param orderId
	 * @param isSuccess  是否退分成功      退分成功：true       退分失败：false
	 */
	public void updateRefundPointStatus(String clientId,String subOrderId,String productId,boolean isSuccess);
	
	/**
	 * 查询预售订单商品配送信息
	 * @param page
	 * @param ship
	 * @return
	 */
	public Page<PresellShip> queryPresellShipByPage(Page<PresellShip> page, PresellShip ship);

	/**
	 * find order information by pipe line
	 * 
	 * @param pipeline
	 * @return
	 */
	public Iterator<DBObject> findOrderByPipeline(List<DBObject> pipeline);
	
	/**
	 * find sub order information by pipe line
	 * 
	 * @param pipeline
	 * @return
	 */
	public Iterator<DBObject> findSubOrderByPipeline(List<DBObject> pipeline);
	
	/**
	 * 根据商户用户ID集合查询商户用户名Map
	 * @param list
	 * @return 商户用户名Map(key=id,value=username)
	 */
	public Map<Long,String> findMerchantUserNameByIdList(List<Long> list);
	
	/**
	 * 根据商户用户ID查询商户用户名
	 * @param id
	 */
	public String findMerchantUserNameById(Long id);
	
	
	/**
	 * 根据地区ID集合查询地区所属省市区名Map
	 * @param list
	 * @return 地区所属省市区名Map(key=id,value=name)
	 */
	public Map<Long,String> findAreaNameById(List<Long> list);
	
	/**
	 * 查询券记录
	 * @param orderIdList
	 * @param subOrderIdList
	 * @param productIdList
	 * @return
	 */
	public abstract List<Ticket> getTickets(List<String> orderIdList,List<String> subOrderIdList, List<String> productIdList);

	/**
	 * 根据大订单号list集合，查询面对面订单结算信息的map
	 * @param orderIdList
	 * @param isShowDescribe  是否显示结算状态的中文描述（true:返回结算状态中文描述   false:返回结算状态code）
	 * @return {订单号：结算状态中文描述}
	 */
	public abstract Map<String, String> getSettlementByOrderIdList(List<String> orderIdList,boolean isShowDescribe);

	/**
	 * 根据大订单号list集合，查询订单结算信息的map
	 * @param subOrderIdList
	 * @return {子订单ID_商品ID：已结算数量}
	 */
	public abstract Map<String, Integer> getSettlementBySubOrderIdList(List<String> subOrderIdList);
	
	/**
	 * 更新单位商品抵扣积分
	 * @param subOrderMongo
	 */
	public abstract void updateUnitProdcutCutPoint(SubOrderMongo subOrderMongo);
	
	/**
	 * 删除单位商品抵扣积分
	 * @param subOrderMongo
	 */
	public abstract void deleteUnitProdcutCutPoint(SubOrderMongo subOrderMongo);

	/**
	 * 更新订单信息
	 * @param order
	 * @return
	 */
	public abstract boolean updateOrder(OrderMongo order);
	
	/**
	 * 更新子订单配送状态
	 * updateSubOrderLogistic:(这里用一句话描述这个方法的作用).
	 * @param clientId
	 * @param subOrderId
	 * @param deliveryState
	 * @return
	 *
	 */
	public abstract boolean updateSubOrderLogistic(String clientId, String subOrderId, String deliveryState);
	
	/**
	 * 获取用户VIP订单集合
	 * @param clientId  客户端ID
	 * @param memberCode  会员号
	 * @param isPaySuccess  是否支付成功
	 * @return
	 *
	 */
	public abstract List<OrderMongo> getVipOrderList(String clientId,Long memberCode,boolean isPaySuccess);
	
	/**
	 * 更新满赠金额和满赠积分
	 * @param clientId
	 * @param orderId
	 * @param giveMoney
	 * @param givePoints
	 * @return
	 */
	public abstract boolean updateOrderForGive(String clientId,String orderId,Integer giveMoney,Integer givePoints);
}
