package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.ActiveReturnGoodsHandler;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.ActiveFindMarketOrderLogicImpl;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.Result;
import com.froad.po.ReturnMarketOrderBackReq;
import com.froad.po.ReturnMarketOrderReq;
import com.froad.po.ReturnMarketOrderRes;
import com.froad.po.ReturnSubOrderBackReq;
import com.froad.po.ReturnSubOrderProductBackReq;
import com.froad.po.ReturnSubOrderProductReq;
import com.froad.po.ReturnSubOrderProductRes;
import com.froad.po.ReturnSubOrderReq;
import com.froad.po.ReturnSubOrderRes;
import com.froad.util.ActiveUtils;
import com.froad.util.Validator;

public class ActiveReturnGoodsHandlerImpl implements ActiveReturnGoodsHandler {
	private ActiveOrderHandler  activeOrderHandler=new ActiveOrderHandlerImpl();
	private ActiveOrderDetailHandler ActiveOrderDetailHandler=new  ActiveOrderDetailHandlerImpl();
	
	
	/**
	  * @Title: selectActiveOrderAndDetail
	  * @Description:退货查询交易
	  * @author: zengfanting 2015年11月17日
	  * @modify: lf 2015年12月06日
	  * @param returnMarketOrderReqVo
	  * @return
	  * @throws Exception
	 */
	@Override
	public ReturnMarketOrderRes selectActiveOrderAndDetail(ReturnMarketOrderReq returnMarketOrderReq) throws Exception {
		 
		ReturnMarketOrderRes returnMarketOrderRes = new ReturnMarketOrderRes();
		 
		try{
			 
			String clientId = returnMarketOrderReq.getClientId();
			String orderId = returnMarketOrderReq.getOrderId();
			String reqId = returnMarketOrderReq.getReqId();

			// 先查询是否有订单支付
			ActiveOrder activeOrder = new ActiveOrder();
			activeOrder.setClientId(clientId);
			activeOrder.setOrderId(orderId);
			activeOrder = activeOrderHandler.findOneActiveOrder(activeOrder);
			if( activeOrder == null ){
				LogCvt.info("--退货查询交易--没有对应的订单可退货--");
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "没有对应的订单"));
				return returnMarketOrderRes;
			}
			
			// 订单没支付 - 不可退货
			if( activeOrder.getPayTime() == null ){
				LogCvt.info("--退货查询交易--订单未支付，无法退货--");
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "订单未支付，无法退货"));
				return returnMarketOrderRes;
			}
			
			// 存储未更改前的内容退货订单明细值
			List<ActiveOrderDetail> orgActiveOrderDetailList = new ArrayList<ActiveOrderDetail>();
			
			List<ReturnSubOrderReq> returnSubOrderReqList = returnMarketOrderReq.getReturnSubOrderReqList();
			// 循环请求退款子订单列表
			for (ReturnSubOrderReq returnSubOrderReq : returnSubOrderReqList) {
				
				String subOrderId = returnSubOrderReq.getSubOrderId();
				
				List<ReturnSubOrderProductReq> returnSubOrderProductReqList = returnSubOrderReq.getReturnSubOrderProductReqList();
				// 循环请求退款子订单的商品列表
				for (ReturnSubOrderProductReq returnSubOrderProductReq : returnSubOrderProductReqList) {
					
					String productId = returnSubOrderProductReq.getProductId(); // 退货商品id
					
					ActiveOrderDetail activeOrderDetail = new ActiveOrderDetail();
					activeOrderDetail.setOrderId(orderId);
					activeOrderDetail.setClientId(clientId);
					activeOrderDetail.setSubOrderId(subOrderId);
					activeOrderDetail.setProductId(productId);
					
					// 获取子订单商品的订单详情列表 - 可能多条(比如:同时享受了满减和红包,就有两条)
					List<ActiveOrderDetail> activeOrderDetailList = ActiveOrderDetailHandler.findActiveOrderDetail(activeOrderDetail);
	
					if( activeOrderDetailList == null || activeOrderDetailList.size() <= 0 ){
						LogCvt.info("--退货查询交易--没有对应的订单详情可退货--");
						returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "查询订单明细失败"));
						return returnMarketOrderRes;
					}
					
					Integer normalCount = returnSubOrderProductReq.getNormalCount(); // 退货普通数量
					Integer vipCount = returnSubOrderProductReq.getVipCount(); // 退货vip数量
					
					ActiveOrderDetail oneDetail = activeOrderDetailList.get(0);
					// 有退 普通数量 - 退货普通数量 > 交易剩余个数
					if( normalCount > 0 && normalCount > oneDetail.getNormalPriceCount() ){
						LogCvt.info("--交易失败,查询退货的商品数大于实际剩余交易个数----");
						returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "交易失败,查询退货的商品数大于实际剩余交易个数"));
						return returnMarketOrderRes;
					}
					if( vipCount > 0 && vipCount > oneDetail.getVipPriceCount() ){
						LogCvt.info("--交易失败,查询退货的商品数大于实际剩余交易个数----");
						returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "交易失败,查询退货的商品数大于实际剩余交易个数"));
						return returnMarketOrderRes;
					}
					
					// 组装订单的退货详情 - 根据退货数量
					activeOrderDetailList = assemblyActiveOrderDetailList(activeOrderDetailList, normalCount, vipCount);
					orgActiveOrderDetailList.addAll(activeOrderDetailList);
					
				}
			}
			
			// 组装返回参数
			returnMarketOrderRes.setReqId(reqId);
			returnMarketOrderRes.setClientId(clientId);
			returnMarketOrderRes.setOrderId(orderId);
			
			List<ReturnSubOrderRes> returnSubOrderResList = new ArrayList<ReturnSubOrderRes>();
			List<String> subOrderIdList = ActiveUtils.countSubOrderId(orgActiveOrderDetailList);
			for( String subOrderId : subOrderIdList ){
				ReturnSubOrderRes returnSubOrderRes = new ReturnSubOrderRes();
				List<ReturnSubOrderProductRes> returnSubOrderProductResList = new ArrayList<ReturnSubOrderProductRes>();
				List<ActiveOrderDetail> sameSubOrderIdDetailList = ActiveUtils.countSameSubOrderId(orgActiveOrderDetailList, subOrderId);
				for( ActiveOrderDetail detail : sameSubOrderIdDetailList ){
					String productId = detail.getProductId();
					double price = detail.getNormalPriceMarket();
					int count = detail.getNormalPriceCount();
					double vipPrice = detail.getVipPriceMarket();
					int vipCount = detail.getVipPriceCount();
					String type = detail.getType();
					ReturnSubOrderProductRes returnSubOrderProductRes = null;
					for( ReturnSubOrderProductRes product : returnSubOrderProductResList ){
						if( productId.equals(product.getProductId()) ){
							returnSubOrderProductRes = product;
							break;
						}
					}
					if( returnSubOrderProductRes == null ){
						returnSubOrderProductRes = new ReturnSubOrderProductRes();
						returnSubOrderProductRes.setProductId(productId);
						if( ActiveType.fullCut.getCode().equals(type) ){
							returnSubOrderProductRes.setPrice(price/1000);
							returnSubOrderProductRes.setVipPrice(vipPrice/1000);
						}else if( ActiveType.vouchers.getCode().equals(type) ){
							returnSubOrderProductRes.setVouPrice(price/1000);
							returnSubOrderProductRes.setVipVouPrice(vipPrice/1000);
						}
						returnSubOrderProductRes.setNormalCount(count);
						returnSubOrderProductRes.setVipCount(vipCount);
						returnSubOrderProductResList.add(returnSubOrderProductRes);
					}else{
						if( ActiveType.fullCut.getCode().equals(type) ){
							returnSubOrderProductRes.setPrice(price/1000);
							returnSubOrderProductRes.setVipPrice(vipPrice/1000);
						}else if( ActiveType.vouchers.getCode().equals(type) ){
							returnSubOrderProductRes.setVouPrice(price/1000);
							returnSubOrderProductRes.setVipVouPrice(vipPrice/1000);
						}
					}
					
				}
				returnSubOrderRes.setSubOrderId(subOrderId);
				returnSubOrderRes.setReturnSubOrderProductResList(returnSubOrderProductResList);
				returnSubOrderResList.add(returnSubOrderRes);
			}
			returnMarketOrderRes.setReturnSubOrderResList(returnSubOrderResList);
			
			returnMarketOrderRes.setResult(new Result(ResultCode.success));
		} catch (Exception e) { 
			returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "退货查询交易,系统异常"));
			throw e; 
		}
		return returnMarketOrderRes;
	}
	
	/**
	 * 组装退货的详情 - 根据退货数量
	 * */
	private static List<ActiveOrderDetail> assemblyActiveOrderDetailList(List<ActiveOrderDetail> activeOrderDetailList, Integer normalCount, Integer vipCount){
		
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			// 有退 普通数量
			if( normalCount > 0 ){
				
				// 优惠交易原总个数
				Integer totalCount = activeOrderDetail.getOrgNormalPriceCount(); 
				// 优惠交易原总价格
				Double	totalMoney = Double.parseDouble(""+activeOrderDetail.getOrgNormalPriceMarket());
				// 优惠交易剩余个数
				Integer curCount = activeOrderDetail.getNormalPriceCount();       
				// 优惠交易剩余总价
				Double  curMoney = Double.parseDouble(""+activeOrderDetail.getNormalPriceMarket()); 
				
				// 退全部 ( 退货普通数量 == 交易剩余个数 )
				if( normalCount == curCount ){
					// 本次退货的金额
					Double cutMoney=ActiveUtils.settlement(true, totalMoney/1000, totalCount, normalCount);
					if(cutMoney>curMoney||cutMoney<0) cutMoney=curMoney;
					activeOrderDetail.setNormalPriceMarket((int)(cutMoney*1000));
				}else{ // 退部分 ( 退货普通数量 < 交易剩余个数 )
					Double cutMoney=ActiveUtils.settlement(false, totalMoney/1000, totalCount, normalCount);
					if(cutMoney>curMoney||cutMoney<0) cutMoney=curMoney;
					activeOrderDetail.setNormalPriceMarket((int)(cutMoney*1000));
				}
				activeOrderDetail.setNormalPriceCount(normalCount);
				//需要改变总价
			}else{
				activeOrderDetail.setNormalPriceMarket(0);
				activeOrderDetail.setNormalPriceCount(0);
			}
			
			// 有 退vip数量
			if( vipCount > 0 ){
				
				// 优惠交易原总个数
				Integer totalCount = activeOrderDetail.getOrgVipPriceCount();
				// 优惠交易原总价格
				Double	totalMoney = Double.parseDouble(""+activeOrderDetail.getOrgVipPriceMarket()); //商品总数量
				// 优惠交易剩余个数
				Integer curCount = activeOrderDetail.getVipPriceCount();
				// 优惠交易剩余总价
				Double  curMoney = Double.parseDouble(""+activeOrderDetail.getVipPriceMarket());
				
				// 退全部 ( 退货vip数量 == 交易剩余个数 )
				if( vipCount == curCount ){
					//本次退货的金额
					Double cutMoney = ActiveUtils.settlement(true, totalMoney/1000, totalCount, vipCount);
					if(cutMoney>curMoney||cutMoney<0) cutMoney=curMoney;
					activeOrderDetail.setVipPriceMarket((int)(cutMoney*1000));
				}else{ // 退部分 ( 退货vip数量 < 交易剩余个数 )
					Double cutMoney=ActiveUtils.settlement(false, totalMoney/1000, totalCount, vipCount);
					if(cutMoney>curMoney||cutMoney<0) cutMoney=curMoney;
					activeOrderDetail.setVipPriceMarket((int)(cutMoney*1000));
				}
				
				activeOrderDetail.setVipPriceCount(vipCount);
				//需要改变总价
			}else{
				activeOrderDetail.setVipPriceMarket(0);
				activeOrderDetail.setVipPriceCount(0);
			}
		}
		return activeOrderDetailList;
	}
	
	
	/**
	  * @Title: updateActiveOrderAndDetail
	  * @Description: 退货交易
	  * @author: zengfanting 2015年11月18日
	  * @modify: lf 2015年12月06日
	  * @param returnMarketOrderReqVo
	  * @return
	  * @throws Exception
	  * @see com.froad.handler.ActiveReturnGoodsHandler#updateActiveOrderAndDetail(com.froad.thrift.vo.active.ReturnMarketOrderReqVo)
	 */
	@Override
	public ReturnMarketOrderRes updateActiveOrderAndDetail(ReturnMarketOrderReq returnMarketOrderReq) throws Exception {
		
		Integer result = -1; 
		SqlSession sqlSession = null;
		ReturnMarketOrderRes returnMarketOrderRes = new ReturnMarketOrderRes();
		 
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(); 
			String clientId = returnMarketOrderReq.getClientId();
			String orderId = returnMarketOrderReq.getOrderId();
			String reqId = returnMarketOrderReq.getReqId();
			List<ReturnSubOrderReq> subOrderList = returnMarketOrderReq.getReturnSubOrderReqList();

			Date  updateTime = new Date();
			
			// 先查询订单是否有
			ActiveOrder findOrder = new ActiveOrder();
			findOrder.setClientId(clientId);
			findOrder.setOrderId(orderId);
			findOrder = activeOrderHandler.findOneActiveOrder(findOrder);
			if( findOrder == null ){
				LogCvt.info("--退货交易--没有对应的订单可退货--");
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "没有对应的订单"));
				return returnMarketOrderRes;
			}
			
			// 订单没支付不可退货
			if( findOrder.getPayTime() == null ){
				LogCvt.info("--退货交易--订单未支付，无法退货--");
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "订单未支付，无法退货"));
				return returnMarketOrderRes;
			}
			
			
			//更新订单主表的退款编号和退款金额，多次退款则编号用","分开,金额累加
			String returnBillNo = returnMarketOrderReq.getReturnBillNo();
			Double returnMoney = returnMarketOrderReq.getReturnMoney();
			if(Validator.isEmptyStr(findOrder.getReturnBillNo())) {//首次退款
				findOrder.setReturnBillNo(returnBillNo);
				findOrder.setReturnMoney(returnMoney);
			} else {
				String existReturnBillNo = findOrder.getReturnBillNo();
				Double existReturnMoney = findOrder.getReturnMoney();
				findOrder.setReturnBillNo(existReturnBillNo+","+returnBillNo);
				findOrder.setReturnMoney(existReturnMoney+returnMoney);
			}
		    result = activeOrderHandler.updateActiveOrderReturnBillNoAndReturnMoney(sqlSession, findOrder);
			if(result>0) {
				LogCvt.info("--更新订单主表--更新退款编号,退款金额完毕--");
			} else {
				LogCvt.info("--更新订单主表--更新退款编号,退款金额失败--");
				sqlSession.rollback(true);
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "交易失败,更新退款编号,退款金额失败"));
				return returnMarketOrderRes;
			}
			
			// 存储未更改前的内容 - 退货订单详情
			List<ActiveOrderDetail> activeOrderDetailListPublic = new ArrayList<ActiveOrderDetail>();
			
			// 循环子订单列表
			// 修改所有相关订单详情
			for (ReturnSubOrderReq returnSubOrder : subOrderList) {
				// 子订单编号
				String subOrderId = returnSubOrder.getSubOrderId();
				//循环子订单的商品列表
				List<ReturnSubOrderProductReq> productList = returnSubOrder.getReturnSubOrderProductReqList();
				for (ReturnSubOrderProductReq returnSubOrderProduct : productList) {
					
					String productId = returnSubOrderProduct.getProductId(); // 退货的商品id
					int normalCount = returnSubOrderProduct.getNormalCount(); // 普通的退货数量
					int vipCount = returnSubOrderProduct.getVipCount(); // vip的退货数量
					ActiveOrderDetail findOrderDetail = new ActiveOrderDetail();
					findOrderDetail.setOrderId(orderId);
					findOrderDetail.setClientId(clientId);
					findOrderDetail.setSubOrderId(subOrderId);
					findOrderDetail.setProductId(productId);
					// 获取子订单内某商品的订单详情列表
					// 可能多条(比如商品享受了满减和红包,则满减有一条详情,红包也有一条详情,多红包就多条详情)
					List<ActiveOrderDetail> filterDetailList = ActiveOrderDetailHandler.findActiveOrderDetail(sqlSession, findOrderDetail);
					if( filterDetailList == null || filterDetailList.size() <= 0 ){
						LogCvt.info("--退货交易--查询订单明细失败--");
						returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "查询订单明细失败"));
						return returnMarketOrderRes;
					}
					
					// 存储未更改前的内容 - 退货订单详情
					activeOrderDetailListPublic.addAll(filterDetailList);
					
					// 循环子订单内某商品的订单详情列表
					for( ActiveOrderDetail filterDetail : filterDetailList ){
						// 普通的退货数量 > 剩余的交易数量 或者 vip的退货数量 > 剩余的交易数量
						if( normalCount > filterDetail.getNormalPriceCount() || vipCount > filterDetail.getVipPriceCount() ){
							LogCvt.info("--交易失败,退货的商品数大于实际交易剩余数----");
							returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "交易失败,退货的商品数大于实际交易剩余数"));
							return returnMarketOrderRes;
						}
						// 有 退普通数量
						if( normalCount > 0 ){
							double disMoney = calcDdiscountMoneyByReturnCount(normalCount, false, filterDetail);
							filterDetail.setNormalPriceMarket((int)(disMoney*1000));
							filterDetail.setNormalPriceCount(normalCount);
						}
						// 有 退vip数量
						if( vipCount > 0 ){
							double disMoney = calcDdiscountMoneyByReturnCount(normalCount, true, filterDetail);
							filterDetail.setVipPriceMarket((int)(disMoney*1000));
							filterDetail.setVipPriceCount(normalCount);
						}
						filterDetail.setUpdateTime(updateTime);
						result=ActiveOrderDetailHandler.updateActiveOrderDetailCount(sqlSession, filterDetail);
						if(result<=-1){
							LogCvt.info("--退货交易--更新订单明细失败--");
							sqlSession.rollback(true);
							returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "更新订单明细失败"));
							return returnMarketOrderRes;
						}
					}
				}
			}
			
			// 查询订单下面的所有订单详情列表
			ActiveOrderDetail activeOrderDetail_find = new ActiveOrderDetail();
			activeOrderDetail_find.setOrderId(orderId);
			activeOrderDetail_find.setClientId(clientId);
			List<ActiveOrderDetail> activeOrderDetailList=ActiveOrderDetailHandler.findActiveOrderDetail(sqlSession, activeOrderDetail_find);
			if(activeOrderDetailList==null||activeOrderDetailList.size()<1){
				LogCvt.info("--退货交易--查询订单明细失败--");
				sqlSession.rollback(true);
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "查询订单明细失败"));
				return returnMarketOrderRes;
			}
			
			// 组装返回参数
			List<ReturnSubOrderRes> returnSubOrderResList = new ArrayList<ReturnSubOrderRes>();
			List<String> subOrderIdList = ActiveUtils.countSubOrderId(activeOrderDetailListPublic);
			for( String subOrderId : subOrderIdList ){
				ReturnSubOrderRes returnSubOrderRes = new ReturnSubOrderRes();
				List<ReturnSubOrderProductRes> returnSubOrderProductResList = new ArrayList<ReturnSubOrderProductRes>();
				List<ActiveOrderDetail> sameSubOrderIdDetailList = ActiveUtils.countSameSubOrderId(activeOrderDetailListPublic, subOrderId);
				for( ActiveOrderDetail detail : sameSubOrderIdDetailList ){
					String productId = detail.getProductId();
					double price = detail.getNormalPriceMarket();
					int count = detail.getNormalPriceCount();
					double vipPrice = detail.getVipPriceMarket();
					int vipCount = detail.getVipPriceCount();
					String type = detail.getType();
					ReturnSubOrderProductRes returnSubOrderProductRes = null;
					for( ReturnSubOrderProductRes product : returnSubOrderProductResList ){
						if( productId.equals(product.getProductId()) ){
							returnSubOrderProductRes = product;
							break;
						}
					}
					if( returnSubOrderProductRes == null ){
						returnSubOrderProductRes = new ReturnSubOrderProductRes();
						returnSubOrderProductRes.setProductId(productId);
						if( ActiveType.fullCut.getCode().equals(type) 
								|| ActiveType.registerGive.getCode().equals(type) ){//首单满减
							returnSubOrderProductRes.setPrice(price/1000);
							returnSubOrderProductRes.setVipPrice(vipPrice/1000);
						}else if( ActiveType.vouchers.getCode().equals(type) ){
							returnSubOrderProductRes.setVouPrice(price/1000);
							returnSubOrderProductRes.setVipVouPrice(vipPrice/1000);
						}
						returnSubOrderProductRes.setNormalCount(count);
						returnSubOrderProductRes.setVipCount(vipCount);
						returnSubOrderProductResList.add(returnSubOrderProductRes);
					}else{
						if( ActiveType.fullCut.getCode().equals(type) 
								|| ActiveType.registerGive.getCode().equals(type)){//首单满减
							returnSubOrderProductRes.setPrice(price/1000);
							returnSubOrderProductRes.setVipPrice(vipPrice/1000);
						}else if( ActiveType.vouchers.getCode().equals(type) ){
							returnSubOrderProductRes.setVouPrice(returnSubOrderProductRes.getVouPrice()+price/1000);
							returnSubOrderProductRes.setVipVouPrice(returnSubOrderProductRes.getVipVouPrice()+vipPrice/1000);
						}
					}
				}
				returnSubOrderRes.setSubOrderId(subOrderId);
				returnSubOrderRes.setReturnSubOrderProductResList(returnSubOrderProductResList);
				returnSubOrderResList.add(returnSubOrderRes);
			}
			returnMarketOrderRes.setReturnSubOrderResList(returnSubOrderResList);
			returnMarketOrderRes.setReqId(reqId);
			returnMarketOrderRes.setClientId(clientId);
			returnMarketOrderRes.setOrderId(orderId);
		
			Integer oriDiscountMoney = 0; // 累计 订单优惠金额
			Integer oriMoney = 0;       // 累计 订单原始金额
			List<Map<String, Object>> detailMapList = new ArrayList<Map<String, Object>>();
			// 循环订单详情列表 - 组装主表的详情
			for( ActiveOrderDetail orderDetail : activeOrderDetailList ){
				
				// 普通优惠总价 + vip优惠总价
				int tmp2=orderDetail.getNormalPriceMarket()+orderDetail.getVipPriceMarket();
				oriDiscountMoney=oriDiscountMoney+tmp2;
				// 普通单价 * 普通优惠数量 + vip单价 * vip优惠数量
				int tmp=orderDetail.getProductPrice()*orderDetail.getNormalPriceCount()+orderDetail.getProductVipPrice()*orderDetail.getVipPriceCount();
				oriMoney=oriMoney+tmp;

				Map<String,Object> detailMap = toMap(orderDetail);
				detailMapList.add(detailMap);
			}
			// 修改订单主表
			ActiveOrder activeOrder =new ActiveOrder();
			activeOrder.setClientId(clientId);
			activeOrder.setOrderId(orderId);
			activeOrder.setUpdateTime(updateTime);
			activeOrder.setOrderOriModifyMoney(oriMoney); // 订单原始金额
			activeOrder.setOrderModifyMoney(oriMoney-oriDiscountMoney); // 订单成交金额
			activeOrder.setDetail(JSON.toJSONString(detailMapList));
			result=activeOrderHandler.updateActiveOrderByOrderId(sqlSession,activeOrder);
			if (result > 0) { // 修改成功
				returnMarketOrderRes.setResult(new Result(ResultCode.success));
				sqlSession.commit(true);
			} else { // 修改失败
				returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "修改订单失败,订单无效或修改条件错误"));
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			returnMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "退货交易,系统异常"));
			if(null != sqlSession) sqlSession.rollback(true);  
		} finally { 
			if(null != sqlSession)  
			sqlSession.close();  
		}
		return returnMarketOrderRes;
	}

	/**
	 * 根据退货数量 计算平摊的优惠金额
	 * */
	private static double calcDdiscountMoneyByReturnCount(int returnCount, boolean isVip, ActiveOrderDetail activeOrderDetail){

		double disMoney = 0.0; // 优惠金额
		
		int    totalCount = 0;  // 原优惠数量
		double totalMoney = 0; // 原优惠总价
		int      curCount = 0;       // 剩余数量
		double   curMoney = 0;      // 剩余总价
		if( isVip ){
			totalCount = activeOrderDetail.getOrgVipPriceCount();  // 原优惠数量
			totalMoney = activeOrderDetail.getOrgVipPriceMarket(); // 原优惠总价
			curCount = activeOrderDetail.getVipPriceCount();     // 剩余数量
			curMoney = activeOrderDetail.getVipPriceMarket();    // 剩余总价
		}else{
			totalCount = activeOrderDetail.getOrgNormalPriceCount();  // 原优惠数量
			totalMoney = activeOrderDetail.getOrgNormalPriceMarket(); // 原优惠总价
			curCount = activeOrderDetail.getNormalPriceCount();       // 剩余数量
			curMoney = activeOrderDetail.getNormalPriceMarket();      // 剩余总价
		}
		// 有退最后一件商品
		if( returnCount == curCount ){
			// 本次退货金额
			disMoney=ActiveUtils.settlement(true, totalMoney/1000, totalCount, returnCount);
		}else{
			disMoney=ActiveUtils.settlement(false, totalMoney/1000, totalCount, returnCount);
		}
		if(disMoney>curMoney||disMoney<0) disMoney=curMoney;
		return 0;
	}
	
	/**
	 * 转换成Map
	 * */
	private static Map<String, Object> toMap(ActiveOrderDetail activeOrderDetail){
		Map<String,Object> productMap = new HashMap<String,Object>();
		String type = activeOrderDetail.getType();
		productMap.put(ActiveUtils.ACT_TYPE, type);
		productMap.put(ActiveUtils.ACT_ID, activeOrderDetail.getActiveId());
		productMap.put(ActiveUtils.PRO_ID, activeOrderDetail.getProductId());
		productMap.put(ActiveUtils.PRO_NAME, activeOrderDetail.getProductName());
		productMap.put(ActiveUtils.GEN_PRICE, activeOrderDetail.getProductPrice());
		productMap.put(ActiveUtils.VIP_PRICE, activeOrderDetail.getProductVipPrice());
		productMap.put(ActiveUtils.GEN_DIS_MONEY, activeOrderDetail.getNormalPriceMarket()); // 平摊的满减金额
		productMap.put(ActiveUtils.GEN_DIS_COUNT, activeOrderDetail.getNormalPriceCount());
		productMap.put(ActiveUtils.VIP_DIS_MONEY, activeOrderDetail.getVipPriceMarket()); // 平摊的满减金额
		productMap.put(ActiveUtils.VIP_DIS_COUNT, activeOrderDetail.getVipPriceCount());
		if( ActiveType.vouchers.getCode().equals(type) ){
			productMap.put(ActiveUtils.VOU_ID, activeOrderDetail.getTicketId()); // 红包/代金券id
		}
		return productMap;
	}
	
	/**
	  * @Title: updateActiveOrderAndDetailBack
	  * @Description: 订单退货回退
	  * @author: zengfanting 2015年11月13日
	  * @modify: lf 2015年12月08日
	  * @param returnMarketOrderBackReqVo
	  * @return
	  * @throws Exception
	  * @see com.froad.handler.ActiveReturnGoodsHandler#updateActiveOrderAndDetailBack(com.froad.thrift.vo.active.ReturnMarketOrderBackReqVo)
	 */
	@Override
	public Result updateActiveOrderAndDetailBack(ReturnMarketOrderBackReq returnMarketOrderBackReq)throws Exception {
		Integer result = 0; 
		SqlSession sqlSession = null;
		 
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(); 
			String clientId = returnMarketOrderBackReq.getClientId();
			String orderId = returnMarketOrderBackReq.getOrderId();
			Date  updateTime = new Date();
			

			
			
			// 循环子订单列表
			// 修改所有相关订单详情
			List<ReturnSubOrderBackReq> returnSubOrderBackReqList = returnMarketOrderBackReq.getReturnSubOrderBackReqList();
			for( ReturnSubOrderBackReq returnSubOrderBackReq : returnSubOrderBackReqList ) {
				String subOrderId = returnSubOrderBackReq.getSubOrderId();
				// 循环子订单里面的商品列表
				List<ReturnSubOrderProductBackReq> returnSubOrderProductBackReqList = returnSubOrderBackReq.getReturnSubOrderProductBackReqList();
				for( ReturnSubOrderProductBackReq returnSubOrderProductBack : returnSubOrderProductBackReqList ) {
					String productId = returnSubOrderProductBack.getProductId(); // 退货的商品id
					int normalCount = returnSubOrderProductBack.getNormalCount(); // 普通的退货数量
					int vipCount = returnSubOrderProductBack.getVipCount(); // vip的退货数量
					ActiveOrderDetail findOrderDetail = new ActiveOrderDetail();
					findOrderDetail.setOrderId(orderId);
					findOrderDetail.setClientId(clientId);
					findOrderDetail.setSubOrderId(subOrderId);
					findOrderDetail.setProductId(productId);
					// 获取子订单内某商品的订单详情列表
					// 可能多条(比如商品享受了满减和红包,则满减有一条详情,红包也有一条详情,多红包就多条详情)
					List<ActiveOrderDetail> activeOrderDetailList = ActiveOrderDetailHandler.findActiveOrderDetail(sqlSession, findOrderDetail);
					if( activeOrderDetailList == null || activeOrderDetailList.size() <= 0 ){
						LogCvt.info("--退货交易--查询订单明细失败--");
						return new Result(ResultCode.failed.getCode(), "查询订单明细失败");
					}
					// 循环子订单内某商品的订单详情列表
					for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
						// 有 回退普通数量
						if( normalCount > 0 ){
							if( normalCount > (activeOrderDetail.getOrgNormalPriceCount()-activeOrderDetail.getNormalPriceCount()) ){
								LogCvt.info("--交易失败,退货回退的商品数大于实际库存数----");
								return new Result(ResultCode.failed.getCode(), "交易失败,退货回退的商品数大于实际库存数");
							}
							double disMoney = calcDdiscountMoneyByReturnCount(normalCount, false, activeOrderDetail);
							activeOrderDetail.setNormalPriceMarket((int)(disMoney*1000));
							activeOrderDetail.setNormalPriceCount(normalCount);
						}
						// 有 回退vip数量
						if( vipCount > 0 ){
							if(vipCount>(activeOrderDetail.getOrgVipPriceCount()-activeOrderDetail.getVipPriceCount())){
								LogCvt.info("--交易失败,退货回退的商品数大于实际库存数----");
								return new Result(ResultCode.failed.getCode(), "交易失败,退货回退的商品数大于实际库存数");
							}
							double disMoney = calcDdiscountMoneyByReturnCount(normalCount, true, activeOrderDetail);
							activeOrderDetail.setVipPriceMarket((int)(disMoney*1000));
							activeOrderDetail.setVipPriceCount(normalCount);
						}
						activeOrderDetail.setUpdateTime(updateTime);
						result=ActiveOrderDetailHandler.updateActiveOrderDetailCountBack(sqlSession, activeOrderDetail);
						LogCvt.info("--退货回退交易--更新订单明细---受影响行结果-"+result);
						if(result<=-1){
							LogCvt.info("--退货回退交易--更新订单明细失败--");
							sqlSession.rollback(true);
							return new Result(ResultCode.failed.getCode(), "更新订单明细失败");
						}
					}
				}
			}
			
			// 查询订单下面的所有订单详情列表
			ActiveOrderDetail activeOrderDetail_find = new ActiveOrderDetail();
			activeOrderDetail_find.setOrderId(orderId);
			activeOrderDetail_find.setClientId(clientId);
			List<ActiveOrderDetail> activeOrderDetailList=ActiveOrderDetailHandler.findActiveOrderDetail(sqlSession, activeOrderDetail_find);
			if(activeOrderDetailList==null||activeOrderDetailList.size()<1){
				LogCvt.info("--退货交易--查询订单明细失败--");
				sqlSession.rollback(true);
				return new Result(ResultCode.failed.getCode(), "查询订单明细失败");
			}
			
			Integer oriDiscountMoney = 0; // 累计 订单优惠金额
			Integer oriMoney = 0;       // 累计 订单原始金额
			List<Map<String, Object>> detailMapList = new ArrayList<Map<String, Object>>();
			// 循环订单详情列表 - 组装主表的详情
			for( ActiveOrderDetail orderDetail : activeOrderDetailList ){
				
				// 普通优惠总价 + vip优惠总价
				int tmp2=orderDetail.getNormalPriceMarket()+orderDetail.getVipPriceMarket();
				oriDiscountMoney=oriDiscountMoney+tmp2;
				// 普通单价 * 普通优惠数量 + vip单价 * vip优惠数量
				int tmp=orderDetail.getProductPrice()*orderDetail.getNormalPriceCount()+orderDetail.getProductVipPrice()*orderDetail.getVipPriceCount();
				oriMoney=oriMoney+tmp;

				Map<String,Object> detailMap = toMap(orderDetail);
				detailMapList.add(detailMap);
			}
			
			ActiveOrder activeOrder =new ActiveOrder();
			activeOrder.setClientId(clientId);
			activeOrder.setOrderId(orderId);
			activeOrder.setUpdateTime(updateTime);
			activeOrder.setOrderOriModifyMoney(oriMoney);
			activeOrder.setOrderModifyMoney(oriMoney-oriDiscountMoney);
			activeOrder.setDetail(JSON.toJSONString(detailMapList));
			result=activeOrderHandler.updateActiveOrderByOrderId(sqlSession,activeOrder);
			
			//更新订单主表的退款编号和退款金额，多次退款则编号用","分开,金额累加
			String returnBillNo = returnMarketOrderBackReq.getReturnBillNo();
			Double returnMoney = returnMarketOrderBackReq.getReturnMoney();
			if(Validator.isEmptyStr(activeOrder.getReturnBillNo())) {//首次退款
				activeOrder.setReturnBillNo(returnBillNo);
				activeOrder.setReturnMoney(returnMoney);
			} else {
				String existReturnBillNo = activeOrder.getReturnBillNo();
				Double existReturnMoney = activeOrder.getReturnMoney();
				activeOrder.setReturnBillNo(existReturnBillNo+","+returnBillNo);
				activeOrder.setReturnMoney(existReturnMoney+returnMoney);
			}
		    result = activeOrderHandler.updateActiveOrderReturnBillNoAndReturnMoney(sqlSession, activeOrder);
			if(result>0) {
				LogCvt.info("--更新订单主表--更新退款编号,退款金额完毕--");
			} else {
				LogCvt.info("--更新订单主表--更新退款编号,退款金额失败--");
				
			}
			
			if (result > -1) { // 修改成功
				sqlSession.commit(true);
				return new Result(ResultCode.success);
			} else { // 修改失败
				sqlSession.rollback(true); 
				return new Result(ResultCode.failed.getCode(), "退货回退交易，更新明细表失败");
			}
			
		} catch (Exception e) { 
			if(null != sqlSession) sqlSession.rollback(true);  
			return new Result(ResultCode.failed.getCode(), "退货回退交易,系统异常");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		}
		
	}
}
