package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillResultFlagEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.OrderLogic;
import com.froad.logic.impl.OrderLogicImpl;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.po.OrderInfo;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.service.impl.validation.OrderValidation;
import com.froad.thrift.vo.order.AddDeliveryInfoVoReq;
import com.froad.thrift.vo.order.AddDeliveryInfoVoRes;
import com.froad.thrift.vo.order.AddOrderForSeckillVoReq;
import com.froad.thrift.vo.order.AddOrderForSeckillVoRes;
import com.froad.thrift.vo.order.AddSeckillOrderVoReq;
import com.froad.thrift.vo.order.AddSeckillOrderVoRes;

public class SeckillOrderServiceImpl extends BizMonitorBaseService implements SeckillOrderService.Iface {
	
	public SeckillOrderServiceImpl() {}
	
	public SeckillOrderServiceImpl(String name, String version) {
		super(name, version);
	}
	
	private OrderValidation orderValidation = new OrderValidation();
	
	private OrderLogic orderLogic = new OrderLogicImpl();
	
	private  PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();

	@Override
	public AddSeckillOrderVoRes addOrder(
			AddSeckillOrderVoReq addSeckillOrderVoReq) throws TException {
		 AddSeckillOrderVoRes addSeckillOrderVoRes = processSeckill(addSeckillOrderVoReq);
		 return addSeckillOrderVoRes;
	}
	
	public AddSeckillOrderVoRes processSeckill(AddSeckillOrderVoReq addSeckillOrderVoReq){
		long st = System.currentTimeMillis();
		LogCvt.info("[秒杀模块]-创建订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addSeckillOrderVoReq,true));
		AddSeckillOrderVoRes addSeckillOrderVoRes = new AddSeckillOrderVoRes();
		addSeckillOrderVoRes.setResultCode(ResultCode.success.getCode());
		addSeckillOrderVoRes.setResultDesc("秒杀订单创建成功");
		
		/**
		 * 1.参数校验（商户、商品、门店、配送、活动、限购、vip等有效性校验 、库存校验、 VO转PO）
		 */
		ResultBean validResult = orderValidation.validateAddSeckillOrderParam(addSeckillOrderVoReq);
		OrderInfo orderInfo = (OrderInfo) validResult.getData();
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			
			orderLogic.updateSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),SeckillResultFlagEnum.order_failed.getCode());
			
			LogCvt.error(validResult.getMsg());
			addSeckillOrderVoRes.setResultCode(validResult.getCode());
			addSeckillOrderVoRes.setResultDesc(validResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
			LogCvt.info("[秒杀模块]-创建订单-失败");
			return addSeckillOrderVoRes;
		}
		
		/**
		 * 2.订单支付校验
		 */
		PayThriftVoBean tempBean = new PayThriftVoBean();
		tempBean.setCashAmount(addSeckillOrderVoReq.getCashAmount());
		tempBean.setCashOrgNo(addSeckillOrderVoReq.getCashOrgNo());
		tempBean.setCashType(addSeckillOrderVoReq.getCashType());
		tempBean.setClientId(addSeckillOrderVoReq.getClientId());
		tempBean.setFoilCardNum(addSeckillOrderVoReq.getFoilCardNum());
		tempBean.setOrderId(orderInfo.getOrder().getOrderId());
		tempBean.setPayType(addSeckillOrderVoReq.getPayType());
		tempBean.setPointAmount(addSeckillOrderVoReq.getPointAmount());
		tempBean.setPointOrgNo(addSeckillOrderVoReq.getPointOrgNo());
		ResultBean payValidResult = paymentCoreLogic.verifyCoreForSeckillingTrade(orderInfo.getOrder(), tempBean);
		if(!StringUtils.equals(ResultCode.success.getCode(), payValidResult.getCode())){
			
			orderLogic.updateSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),SeckillResultFlagEnum.order_failed.getCode());
			
			LogCvt.error(payValidResult.getMsg());
			addSeckillOrderVoRes.setResultCode(payValidResult.getCode());
			addSeckillOrderVoRes.setResultDesc(payValidResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
			LogCvt.info("[秒杀模块]-支付校验-失败");
			return addSeckillOrderVoRes;
		}
		
		/**
		 * 3.获取支付订单
		 */
		OrderMongo payOrder = (OrderMongo) payValidResult.getData();
		
		/**
		 * 4.减库存
		 */
		ResultBean freezeStoreResult = orderLogic.reduceSeckillStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), freezeStoreResult.getCode())){
			
			orderLogic.updateSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),SeckillResultFlagEnum.order_failed.getCode());
			
			LogCvt.error(freezeStoreResult.getMsg());
			addSeckillOrderVoRes.setResultCode(freezeStoreResult.getCode());
			addSeckillOrderVoRes.setResultDesc(freezeStoreResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
			LogCvt.info("[秒杀模块]-创建订单-失败");
			return addSeckillOrderVoRes;
		}
		
		/**
		 * 5.插入订单表和子订单表
		 */
		ResultBean addOrderResult = orderLogic.addOrder(payOrder,orderInfo.getSubOrderList());
		if(!StringUtils.equals(ResultCode.success.getCode(), addOrderResult.getCode())){
			
			orderLogic.updateSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),SeckillResultFlagEnum.order_failed.getCode());
			
			LogCvt.error(addOrderResult.getMsg());
			addSeckillOrderVoRes.setResultCode(addOrderResult.getCode());
			addSeckillOrderVoRes.setResultDesc(addOrderResult.getMsg());
			
			//插入失败，还库存
			LogCvt.info("插入订单表和子订单表失败，开始还库存...");
			ResultBean freeStoreResult = orderLogic.increaseSeckillStore(orderInfo.getStoreList());
			if(!StringUtils.equals(ResultCode.success.getCode(), freeStoreResult.getCode())){
				LogCvt.error(freeStoreResult.getMsg());
				addSeckillOrderVoRes.setResultCode(freeStoreResult.getCode());
				addSeckillOrderVoRes.setResultDesc(freeStoreResult.getMsg());
				LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
				LogCvt.info("[秒杀模块]-创建订单-失败");
				return addSeckillOrderVoRes;
			}
			
			return addSeckillOrderVoRes;
		}
		
		/**
		 * 6.订单创建完成，创建订单缓存、订单创建时间缓存到redis 
		 */
		//添加秒杀受理缓存-添加订单ID
		orderLogic.addSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),orderInfo.getOrder());
		
		//秒杀受理缓存-更新订单受理状态
		orderLogic.updateSeckillOrderRedis(addSeckillOrderVoReq.getReqId(),SeckillResultFlagEnum.order_success.getCode());
		
		//订单创建时间、用户购买记录
		orderLogic.addOrderRedisForSeckill(orderInfo.getOrder(),orderInfo.getSubOrderList());
		
		/**
		 * 7.支付
		 */
		ResultBean payResult = paymentCoreLogic.doPayCoreForSeckillingTrade(payOrder, tempBean);
		if(!StringUtils.equals(ResultCode.success.getCode(), payResult.getCode())){
			LogCvt.error(payResult.getMsg());
			addSeckillOrderVoRes.setResultCode(payResult.getCode());
			addSeckillOrderVoRes.setResultDesc(payResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
			LogCvt.info("[秒杀模块]-支付-失败");
			return addSeckillOrderVoRes;
		}
		
		/**
		 * 8.订单创建完成，将库存更新到mysql
		 */
		ResultBean updateProductStoreResult = orderLogic.updateSeckillProductStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), updateProductStoreResult.getCode())){
			LogCvt.error("秒杀库存从redis更新到mysql失败，原因："+updateProductStoreResult.getMsg());
			/*addSeckillOrderVoRes.setResultCode(updateProductStoreResult.getCode());
			addSeckillOrderVoRes.setResultDesc(updateProductStoreResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));*/
			LogCvt.info("[秒杀模块]-创建订单成功-更新库存失败");
			/*return addSeckillOrderVoRes;*/
		}

		/*addSeckillOrderVoRes.setResultCode(seckillResult.getCode());
		addSeckillOrderVoRes.setResultDesc(seckillResult.getMsg());*/
		addSeckillOrderVoRes.setOrderId(orderInfo.getOrder().getOrderId());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));
		LogCvt.info("[秒杀模块]-创建订单-结束");
		return addSeckillOrderVoRes;
	}

	@Override
	public AddDeliveryInfoVoRes updateDeliveryInfo(
			AddDeliveryInfoVoReq addDeliveryInfoVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[秒杀模块]-补全收货信息-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addDeliveryInfoVoReq,true));
		AddDeliveryInfoVoRes addDeliveryInfoVoRes = new AddDeliveryInfoVoRes();
		addDeliveryInfoVoRes.setResultCode(ResultCode.success.getCode());
		addDeliveryInfoVoRes.setResultDesc("秒杀订单创建成功");
		
		/**
		 * 1.参数校验
		 */
		ResultBean validResult = orderValidation.validateAddDeliveryInfoParam(addDeliveryInfoVoReq);
		OrderInfo orderInfo = (OrderInfo) validResult.getData();
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			addDeliveryInfoVoRes.setResultCode(validResult.getCode());
			addDeliveryInfoVoRes.setResultDesc(validResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addDeliveryInfoVoRes,true));
			LogCvt.info("[秒杀模块]-创建订单-失败");
			return addDeliveryInfoVoRes;
		}
		
		/**
		 * 2.PO转VO
		 */
		OrderMongo orderMongo = new OrderMongo();
		orderMongo.setOrderId(addDeliveryInfoVoReq.getOrderId());
		orderMongo.setRecvId(addDeliveryInfoVoReq.getRecvId());
		orderMongo.setDeliverId(addDeliveryInfoVoReq.getDeliverId());
		
		ProductMongo productMongo = new ProductMongo();
		productMongo.setOrgCode(addDeliveryInfoVoReq.getOrgCode());
		productMongo.setOrgName(addDeliveryInfoVoReq.getOrgName());
		productMongo.setDeliveryOption(addDeliveryInfoVoReq.getDeliveryType());
		List<ProductMongo> products = new ArrayList<ProductMongo>();
		products.add(productMongo);
		
		SubOrderMongo subOrderMongo = new SubOrderMongo();
		subOrderMongo.setOrderId(addDeliveryInfoVoReq.getOrderId());
		subOrderMongo.setProducts(products);
		
		/**
		 * 3.更新订单
		 */
		ResultBean addOrderResult = orderLogic.updateOrderForSeckill(orderMongo,subOrderMongo);
		if(!StringUtils.equals(ResultCode.success.getCode(), addOrderResult.getCode())){
			LogCvt.error(addOrderResult.getMsg());
			addDeliveryInfoVoRes.setResultCode(addOrderResult.getCode());
			addDeliveryInfoVoRes.setResultDesc(addOrderResult.getMsg());
			return addDeliveryInfoVoRes;
		}
		
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addDeliveryInfoVoRes,true));
		LogCvt.info("[秒杀模块]-创建订单-结束");
		return addDeliveryInfoVoRes;
	}

	@Deprecated
	@Override
	public AddOrderForSeckillVoRes addOrderForSeckill(
			AddOrderForSeckillVoReq addOrderForSeckillVoReq) throws TException {
		long st = System.currentTimeMillis();
		LogCvt.info("[秒杀模块]-创建订单-开始");
		LogCvt.info("请求参数：" + JSON.toJSONString(addOrderForSeckillVoReq,true));
		AddOrderForSeckillVoRes addOrderForSeckillVoRes = new AddOrderForSeckillVoRes();
		addOrderForSeckillVoRes.setResultCode(ResultCode.success.getCode());
		addOrderForSeckillVoRes.setResultDesc("秒杀订单创建成功");
		
		/**
		 * 1.参数校验（商户、商品、门店、配送、活动、限购、vip等有效性校验 、库存校验、 VO转PO）
		 */
		ResultBean validResult = orderValidation.validateAddOrderForSeckillParam(addOrderForSeckillVoReq);
		OrderInfo orderInfo = (OrderInfo) validResult.getData();
		if(!StringUtils.equals(ResultCode.success.getCode(), validResult.getCode())){
			LogCvt.error(validResult.getMsg());
			addOrderForSeckillVoRes.setResultCode(validResult.getCode());
			addOrderForSeckillVoRes.setResultDesc(validResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderForSeckillVoRes,true));
			LogCvt.info("[秒杀模块]-创建订单-失败！！");
			return addOrderForSeckillVoRes;
		}
		
		/**
		 * 4.减库存
		 */
		ResultBean freezeStoreResult = orderLogic.reduceSeckillStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), freezeStoreResult.getCode())){
			LogCvt.error(freezeStoreResult.getMsg());
			addOrderForSeckillVoRes.setResultCode(freezeStoreResult.getCode());
			addOrderForSeckillVoRes.setResultDesc(freezeStoreResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderForSeckillVoRes,true));
			LogCvt.info("[秒杀模块]-创建订单-失败！！");
			return addOrderForSeckillVoRes;
		}
		
		/**
		 * 5.插入订单表和子订单表
		 */
		ResultBean addOrderResult = orderLogic.addOrder(orderInfo.getOrder(),orderInfo.getSubOrderList());
		if(!StringUtils.equals(ResultCode.success.getCode(), addOrderResult.getCode())){
			LogCvt.error(addOrderResult.getMsg());
			addOrderForSeckillVoRes.setResultCode(addOrderResult.getCode());
			addOrderForSeckillVoRes.setResultDesc(addOrderResult.getMsg());
			
			//插入失败，还库存
			LogCvt.info("插入订单表和子订单表失败，开始还库存...");
			ResultBean freeStoreResult = orderLogic.increaseSeckillStore(orderInfo.getStoreList());
			if(!StringUtils.equals(ResultCode.success.getCode(), freeStoreResult.getCode())){
				LogCvt.error(freeStoreResult.getMsg());
				addOrderForSeckillVoRes.setResultCode(freeStoreResult.getCode());
				addOrderForSeckillVoRes.setResultDesc(freeStoreResult.getMsg());
				LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderForSeckillVoRes,true));
				LogCvt.info("[秒杀模块]-创建订单-失败！！");
				return addOrderForSeckillVoRes;
			}
			
			return addOrderForSeckillVoRes;
		}
		
		/**
		 * 7.订单创建完成，将库存更新到mysql
		 */
		ResultBean updateProductStoreResult = orderLogic.updateSeckillProductStore(orderInfo.getStoreList());
		if(!StringUtils.equals(ResultCode.success.getCode(), updateProductStoreResult.getCode())){
			LogCvt.error("秒杀库存从redis更新到mysql失败，原因："+updateProductStoreResult.getMsg());
			/*addSeckillOrderVoRes.setResultCode(updateProductStoreResult.getCode());
			addSeckillOrderVoRes.setResultDesc(updateProductStoreResult.getMsg());
			LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addSeckillOrderVoRes,true));*/
			LogCvt.info("[秒杀模块]-创建订单成功-更新库存失败！！");
			/*return addSeckillOrderVoRes;*/
		}

		/**
		 * 8.订单创建完成，创建订单缓存、订单创建时间缓存到redis 
		 */
		orderLogic.addSeckillOrderRedis(addOrderForSeckillVoReq.getReqId(),orderInfo.getOrder());
		
		addOrderForSeckillVoRes.setOrderId(orderInfo.getOrder().getOrderId());
		LogCvt.info("耗时（"+(System.currentTimeMillis() - st)+"）毫秒，响应结果：" + JSON.toJSONString(addOrderForSeckillVoRes,true));
		LogCvt.info("[秒杀模块]-创建订单-成功！！");
		return addOrderForSeckillVoRes;
	}

}
