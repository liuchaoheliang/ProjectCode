package com.froad.logic;

import java.util.Map;

import com.froad.po.AcceptOrder;
import com.froad.po.SeckillProduct;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;

/**
 * 秒杀商品逻辑接口
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午7:26:03
 * @version v1.0
 */
public interface SeckillProductLogic {
	
	/**
	 * 获取受理订单号
	 * 
	 * @param memberCode 用户唯一标识
	 * @param productId 秒杀商品ID
	 * 
	 * @return String 受理订单号
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public String getAcceptOrderId(Long memberCode, String productId);
	
	
	/**
	 * 获取秒杀商品信息
	 * 
	 * @param clientId 客户端ID
	 * @param productId 秒杀商品ID
	 * 
	 * @return SeckillProduct 秒杀商品信息
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public SeckillProduct getProduct(String clientId, String productId);
	
	/**
	 * 获取基本商品信息详情
	 * 
	 * @param clientId 客户端ID
	 * @param merchantId 商户ID
	 * @param productId 秒杀商品ID
	 * 
	 * @return ProductDetail 商品详情
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public ProductDetail getProductDetail(String clientId, String productId);
	
	/**
	 * 检查秒杀商品的销售数量是否超过了总库存
	 * 
	 * @param clientId 客户端ID
	 * @param productId 秒杀商品ID
	 * @param buyNum 购买数量
	 * @param store 商品总库存(秒杀商品中不变的库存数)
	 * 
	 * @return boolean true-成功，false-库存不足
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public boolean checkProductSoldCount(String clientId, String productId, int buyNum, int store);
	
	/**
	 * 增量更新秒杀商品的销售数量
	 * 
	 * @param clientId 客户端ID
	 * @param productId 秒杀商品ID
	 * @param buyNum 购买数量
	 * @param store 商品总库存(秒杀商品中不变的库存数)
	 * 
	 * @return boolean true-成功，false-库存不足
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public boolean updateProductSoldCount(String clientId, String productId, int buyNum, int store);
	
	/**
	 * 获取用户购买数量
	 * 
	 * @param memberCode 用户唯一标识
	 * @param productId 秒杀商品ID
	 * 
	 * @return int 用户已购买的数量
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public int getMemberBuyNum(Long memberCode, SeckillProduct product);
	
	/**
	 * 记录用户购买数量
	 * 
	 * @param memberCode 用户唯一标识
	 * @param buyNum 购买数量
	 * @param product 秒杀商品
	 * 
	 * @return int 用户真实的购买数量
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public int countMemberBuyNum(Long memberCode, int buyNum, SeckillProduct product);
	
	/**
	 * 创建受理订单
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public boolean createAcceptOrder(AcceptOrder acceptOrder);
	
	/**
	 * 获取受理订单信息
	 * 
	 * @param acceptOrderId 受理订单号
	 * 
	 * @param Map<String, String>
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public Map<String, String> getAcceptOrderById(String acceptOrderId);
	
	/**
	 * 获取秒杀商品库存
	 * 
	 * @param clientId 客户端ID
	 * @param productId 秒杀商品ID
	 * 
	 * @return long 库存数
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public long getStock(String clientId, String productId);
	
	/**
	 * 队列中任务的个数加1
	 * 
	 * @return long 个数
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public long addQueueJobCount();
	
	/**
	 * 根据秒杀大订单ID从mongo中获取商品信息
	 * 
	 * @param orderId 大订单号
	 * 
	 * @return ProductMongo
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public ProductMongo getProductMongo(String orderId);
	
}
