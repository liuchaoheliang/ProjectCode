package com.froad.jetty.service;

import com.froad.jetty.vo.FillOrderRequestVo;
import com.froad.jetty.vo.PlaceOrderRequestVo;
import com.froad.jetty.vo.PlaceOrderResponseVo;
import com.froad.jetty.vo.QueryOrderResponseVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.po.SeckillProduct;

/**
 * 秒杀订单业务
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 上午10:18:29
 * @version v1.0
 */
public interface OrderService {
	
	/**
	 * 计算下单的金额
	 * 
	 * @param loginId 用户ID
	 * @param reqVo 请求数据
	 * @param product 秒杀商品对象
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月08日 上午11:23:56
	 */
	public ResponseVo calculateAmount(String loginId, PlaceOrderRequestVo reqVo, SeckillProduct product);
	
	/**
	 * 检查贴膜卡号是否正确
	 * 
	 * @param reqVo 请求数据
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo checkFoilCardNum(PlaceOrderRequestVo reqVo);
	
	/**
	 * 秒杀下单之前的逻辑检查
	 * 
	 * @param reqVo 请求数据
	 * @param product 秒杀商品对象
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo prePlaceOrder(PlaceOrderRequestVo reqVo, SeckillProduct product);
	
	/**
	 * 秒杀下单之前的逻辑检查
	 * 
	 * @param reqVo 请求数据
	 * @param product 秒杀商品对象
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo prePlaceOrder2(PlaceOrderRequestVo reqVo, SeckillProduct product);
	
	/**
	 * 秒杀下单
	 * 
	 * @param loginId 用户ID
	 * @param reqVo 请求数据
	 * @param resVo 成功后相应数据格式
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo placeOrder(String loginId, PlaceOrderRequestVo reqVo, PlaceOrderResponseVo resVo);
	
	/**
	 * 秒杀下单
	 * 
	 * @param loginId 用户ID
	 * @param reqVo 请求数据
	 * @param resVo 成功后相应数据格式
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo placeOrder2(String loginId, PlaceOrderRequestVo reqVo, PlaceOrderResponseVo resVo);
	
	/**
	 * 查询订单
	 * 
	 * @param acceptOrderId 受理订单号
	 */
	public QueryOrderResponseVo queryOrder(String acceptOrderId);
	
	/**
	 * 填充秒杀订单
	 * <br/><br/>
	 * 弃用本方法于2015年6月23日 上午10:26:03
	 * 
	 * @param reqVo 请求数据
	 * 
	 * @return ResponseVo 处理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	@Deprecated
	public ResponseVo fillOrder(FillOrderRequestVo reqVo);
	
	/**
	 * 生成受理订单号
	 */
	public String genAcceptOrderId();
	
}
