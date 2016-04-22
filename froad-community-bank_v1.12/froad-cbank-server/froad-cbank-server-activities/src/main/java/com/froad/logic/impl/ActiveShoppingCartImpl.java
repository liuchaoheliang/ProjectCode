package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.RegisterRedis;
import com.froad.db.redis.SupportsRedis;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveShoppingCart;
import com.froad.po.ShoppingCartFullGive;
import com.froad.po.ShoppingCartReq;
import com.froad.po.ShoppingCartReqProduct;
import com.froad.po.ShoppingCartRes;
import com.froad.po.ShoppingCartResActive;
import com.froad.po.ShoppingCartResProduct;
import com.froad.util.ActiveUtils;
import com.froad.util.Arith;
import com.froad.util.DateUtil;

public class ActiveShoppingCartImpl implements ActiveShoppingCart {

	private static OrderMongoService orderMongoService = new OrderMongoServiceImpl();
	
	/**
     * 进入购物车
     */
	@Override
	public ShoppingCartRes goShoppingCart(ShoppingCartReq shoppingCartReq) {
		
		LogCvt.info("进入 购物车 goShoppingCart 逻辑:");
		
		String reqId = shoppingCartReq.getReqId();
		String clientId = shoppingCartReq.getClientId();
		Long menberCode = shoppingCartReq.getMemberCode();
		
		// 购物车 响应 对象
		ShoppingCartRes shoppingCartRes = new ShoppingCartRes();
		// 基本值设置
		shoppingCartRes.setReqId(reqId);
		shoppingCartRes.setClientId(clientId);
		shoppingCartRes.setMemberCode(menberCode);
		
		// 1 处理购物车满减活动 得到对应的"商品-活动"列表
		List<ShoppingCartResProduct> shoppingCartResProductList = disposeShoppingCartFullCut(shoppingCartReq);
		
		// 2 处理购物车满赠活动 得到"购物车满赠 - 列表"
		List<ShoppingCartFullGive> shoppingCartFullGiveList = disposeShoppingCartFullGive(shoppingCartReq, shoppingCartResProductList);
		
		// 设置 对应的列表
		shoppingCartRes.setShoppingCartResProductList(shoppingCartResProductList);
		shoppingCartRes.setShoppingCartFullGiveList(shoppingCartFullGiveList);
		return shoppingCartRes;
	}

	/**
	 * 处理购物车满减活动
	 * */
	private static List<ShoppingCartResProduct> disposeShoppingCartFullCut(ShoppingCartReq shoppingCartReq){
		String reqId = shoppingCartReq.getReqId();
		String clientId = shoppingCartReq.getClientId();
		Long menberCode = shoppingCartReq.getMemberCode();
		List<ShoppingCartReqProduct> shoppingCartReqProductList = shoppingCartReq.getShoppingCartReqProductList();
		
		// 对应的"商品-活动"列表
		List<ShoppingCartResProduct> shoppingCartResProductList = new ArrayList<ShoppingCartResProduct>();
		
		/**   1 把所有满足条件的满减活动找出来        */
		// 循环购物车中所有商品 - 获取满足条件的活动
		List<Map<String, String>> activeMapList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> aml = null;
		String productId = null,activeId = null,type = null;
		for( ShoppingCartReqProduct reqProduct : shoppingCartReqProductList ){
			productId = reqProduct.getProductId();
			aml = checkBuyProductOfAllActiveQuali(clientId, menberCode, productId);
			if( aml != null && aml.size() > 0 ){
				for( Map<String, String> am : aml ){
					activeId = am.get("activeId");
					type = am.get("type");
					if( !am.isEmpty() && type != null ){
						if( type.equals(ActiveType.fullCut.getCode()) ){
							activeMapList.add(am);
							reqProduct.setActiveId_ptmj(activeId);
						}else if( type.equals(ActiveType.firstPayment.getCode()) ){
							activeMapList.add(am);
							reqProduct.setActiveId_sdmj(activeId);
						}
					}
				}
			}
		}
		
		// 过滤重复的活动
		activeMapList = filterRepeat(activeMapList);
		LogCvt.debug("营销活动购物车-请求编号:"+reqId+" 所属营销满减活动:"+JSON.toJSONString(activeMapList));
		// 找出所有满减活动中"减额最低"的满减活动
		Map<String, String> lowestMap = findOutLowest(activeMapList);
		LogCvt.debug("营销活动购物车-请求编号:"+reqId+"减额最低的满减活动:"+JSON.toJSONString(lowestMap));
		
		/**   2 把同活动的商品组合起来扣钱 - 组装返回参数"商品-活动"列表       */
		// 有满足条件的活动
		if( activeMapList.size() > 0 ){
			
			// 计算购物车优惠
			shoppingCartResProductList = calcShoppingCartFullCut(activeMapList, shoppingCartReqProductList);
			// 合并相同商品id的list
			shoppingCartResProductList = mergeSameProductIdList(shoppingCartResProductList);
			// 如果有"减额最低"的满减活动
			if( lowestMap != null ){
				// 设置为删除
				setActiveByDelete(lowestMap.get("activeId"), shoppingCartResProductList);
			}
		}else{ // 没有满足条件的活动
			
			LogCvt.debug("营销活动购物车-请求编号:"+reqId+" 所属营销满减活动为空");
			// 拼装购物车的 商品-活动 列表
			for( ShoppingCartReqProduct reqProduct : shoppingCartReqProductList ){
				ShoppingCartResProduct resProduct = new ShoppingCartResProduct();
				resProduct.setProductId(reqProduct.getProductId());
				shoppingCartResProductList.add(resProduct);
			}
		}
		
		return shoppingCartResProductList;
	}
	
	/** 
	 * 检查并获取购买商品的全部所属活动<br>
	 * 如果没有活动 - 返回空Map<br>
	 * 有活动就返回活动Map<br>
	 * Map中新增字段 activeStatus, statusMsg(状态描述)<br>
	 * activeStatus 0-活动正常 1-活动失效 2-活动全局限制已满 3-时间段内限制已满 4-个人当前限制已满 9-读取限制值失败
	 */
	private static List<Map<String, String>> checkBuyProductOfAllActiveQuali(String clientId, Long memberCode, String productId){
		
		/*********************************/
		/**   3 判断活动的全局限制        */
		/**   4 判断活动的时间段限制        */
		/**   5 判断个人的限制        */
		/*********************************/
		
		LogCvt.debug("试图获取商品 "+productId+" 的全部所属活动");
		
		List<Map<String, String>> activeMapList = new ArrayList<Map<String, String>>();
		Map<String, String> activeMap = null;
		
		try{
		
			// 1 先根据商品Id找出所有活动id
			List<String> activeIds = getActiveIds(productId);
			if( activeIds == null || activeIds.size() < 1 ){
				return new ArrayList<Map<String, String>>();
			}
			
			// 2 根据活动Id把redis中的活动明细找出来
			// 根据不同的活动类型读取不同的redis
			for( String activeId : activeIds ){
				if( activeId.indexOf(ActiveIdCode.MJ.getCode()) > 0 ){
					activeMap = RedisCommon.readFullCutActive(clientId, activeId);
				}else if( activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0 ){
					activeMap = RegisterRedis.readRegisterActive(clientId, activeId);
//				}else if( activeId.indexOf(ActiveIdCode.MZ.getCode()) > 0 ){
//					activeMap = RedisCommon.readFullCutActive(clientId, activeId);
				}else{
					continue;
				}
				if( activeMap == null || activeMap.isEmpty() ){
					LogCvt.debug("获取活动 "+activeId+" 细则,结果为空");
					activeMapList.add(new HashMap<String, String>());
					continue;
				}else{
					LogCvt.debug("获取活动 "+activeId+" 细则:"+activeMap);
				}
				
				String readActiveResult = activeMap.get("result");
				// 初始化活动失败 或 读取活动失败
				if( ResultCode.market_active_rule_init_fail.getCode().equals(readActiveResult) 
					|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("活动 "+activeId+" 初始化或读取 失败");
					activeMapList.add(new HashMap<String, String>());
					continue;
				}
				
				// 活动还未开始生效
				if( ResultCode.market_active_rule_no_start.getCode().equals(readActiveResult) ){
					LogCvt.debug("活动 "+activeId+" 还未开始生效");
					activeMap.put("activeStatus", "5");
					activeMap.put("statusMsg", "还未开始生效");
					activeMapList.add(activeMap);
					continue;
				}
				
				// 失效
				if( ResultCode.market_active_rule_invalid.getCode().equals(readActiveResult) ){
					LogCvt.debug("活动 "+activeId+" 已经失效");
					// 删除所属的两个对应redis
					try{
						List<String> ail = new ArrayList<String>();
						ail.add(activeId);
						RedisCommon.delOverdueActivitiesRedisByActiveId(ail);
					}catch(Exception e){
						LogCvt.error("活动 "+activeId+" 失效-删除所属的两个对应redis 异常",e);
					}
					activeMap.put("activeStatus", "1");
					activeMap.put("statusMsg", "活动失效");
					activeMapList.add(activeMap);
					continue;
				}
				
				String type = activeMap.get("type");
				Boolean checkResult = null;
				// 3 判断活动的全局限制
				if( ActiveType.fullCut.getCode().equals(type) || ActiveType.fullGive.getCode().equals(type) ){
					checkResult = RedisCommon.checkBaseGlobalLimit(clientId, activeId);
				}else if( ActiveType.firstPayment.getCode().equals(type) ){
					checkResult = RegisterRedis.checkBaseGlobalLimit(clientId, activeId);
				}
				LogCvt.debug("活动 "+activeId+" 全局限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					activeMap.put("activeStatus", "2");
					activeMap.put("statusMsg", "活动全局限制已满");
					activeMapList.add(activeMap);
					continue;
				}
				
				checkResult = null;
				// 4 判断活动的时间段限制
				if( ActiveType.fullCut.getCode().equals(type) || ActiveType.fullGive.getCode().equals(type) ){
					checkResult = RedisCommon.checkGlobalLimit(clientId, activeId, null);
				}else if( ActiveType.firstPayment.getCode().equals(type) ){
					checkResult = RegisterRedis.checkGlobalLimit(clientId, activeId, null);
				}
				
				LogCvt.debug("活动 "+activeId+" 时间段限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					activeMap.put("activeStatus", "3");
					activeMap.put("statusMsg", "时间段内限制已满");
					activeMapList.add(activeMap);
					continue;
				}
				
				checkResult = null;
				// 5 判断个人的限制
				if( ActiveType.fullCut.getCode().equals(type) || ActiveType.fullGive.getCode().equals(type) ){
					checkResult = RedisCommon.checkPersonLimit(clientId, activeId, memberCode);
				}
				LogCvt.debug("活动 "+activeId+" 个人限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					activeMap.put("activeStatus", "4");
					activeMap.put("statusMsg", "个人当前限制已满");
					activeMapList.add(activeMap);
					continue;
				}
				
				// 6 判断首单满减
				if( (activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0) && type.equals(ActiveType.firstPayment.getCode()) ){
					// 新用户判断
					long userRegisterTime = VouchersRunLogicImpl.getUserRegisterDate(memberCode, clientId);
					String expireStartTime = activeMap.get("expireStartTime");
					String expireEndTime = activeMap.get("expireEndTime");
					long startTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime).getTime();
					long endTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime();
					if( userRegisterTime < startTime || userRegisterTime > endTime ){
						LogCvt.debug("活动 "+activeId+" 仅能用于新注册用户使用");
						activeMap.put("activeStatus", "5");
						activeMap.put("statusMsg", "仅能用于新注册用户使用");
						activeMapList.add(activeMap);
						continue;
					}
					// 首单判断
					boolean isExistO = orderMongoService.isExistOrder(memberCode, clientId);
					if( isExistO ){
						LogCvt.debug("活动 "+activeId+" 仅能用于未产生交易用户使用");
						activeMap.put("activeStatus", "6");
						activeMap.put("statusMsg", "仅能用于未产生交易用户使用");
						activeMapList.add(activeMap);
						continue;
					}
				}
				
				
				LogCvt.debug("活动 "+activeId+" 正常");
				activeMap.put("activeStatus", "0");
				activeMap.put("statusMsg", "活动正常");
				activeMapList.add(activeMap);
				continue;
			}
			
		}catch(Exception e){
			LogCvt.error("检查并获取购买商品的全部所属活动资质 异常:", e);
			activeMapList = new ArrayList<Map<String, String>>();
		}
		
		return activeMapList;
	}
	
	/**
	 * 根据商品id获取所有活动id
	 * */
	private static List<String> getActiveIds(String productId) {
		try{
			Set<String> setValue = SupportsRedis.get_cbbank_active_product_info(productId);
			LogCvt.debug("商品 "+productId+" 的关联活动:"+JSON.toJSONString(setValue));
			
			if( setValue == null || setValue.size() <= 0 ){
				return null;
			}
			
			String activeValue = null, activeId = null;
			String[] activeAttributes = null;
			List<String> activeIds = new ArrayList<String>();
			for( Iterator<String> iterator = setValue.iterator() ; iterator.hasNext(); ){
				activeValue = iterator.next();
				if( activeValue == null || "".equals(activeValue) ){
					continue;
				}
				activeAttributes = activeValue.split(":");
				if( activeAttributes == null || activeAttributes.length < 3 ){
					continue;
				}
				activeId = activeAttributes[2];
				if( activeId == null || "".equals(activeId) ){
					continue;
				}
				activeIds.add(activeId);
			}
			
			return activeIds;
		}catch(Exception e){
			LogCvt.error("检查购买商品的满减活动资质 异常:", e);
			return null;
		}
	}
	
	/** 
	 * 过滤重复的活动
	 * <br>
	 * null 也过滤掉
	 */
	private static List<Map<String, String>> filterRepeat(List<Map<String, String>> activeMapList){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for( Map<String, String> active : activeMapList ){
			
			if( active.isEmpty() ){
				continue;
			}
			String activeId = active.get("activeId");
			if( null == activeId ){
				continue;
			}
			boolean isSame = false;
			for( Map<String, String> result : resultList ){
				String resultId = result.get("activeId");
				if( resultId.equals(activeId) ){
					isSame = true;
					break;
				}
			}
			if( !isSame ){
				resultList.add(active);
			}
		}
		
		return resultList;
	}
	
	/** 
	 * 如果有首单满减的活动 则找出所有满减活动中"减额最低"的满减活动
	 */
	private static Map<String, String> findOutLowest(List<Map<String, String>> activeMapList){
		
		Map<String, String> lowestMap = null;
		Double lowest = 0.0;
		
		// 找出所有状态正常的满减活动
		List<Map<String, String>> amtl = new ArrayList<Map<String, String>>();
		for( Map<String, String> active1 : activeMapList ){
			String status = active1.get("activeStatus");
			if( "0".equals(status) ){
				amtl.add(active1);
			}
		}
		
		// 1个正常的满减活动以上才计算
		if( amtl.size() > 1 ){
			for( Map<String, String> active2 : amtl ){
				String activeId = active2.get("activeId");
				// 有首单满减的活动
				if( activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0 ){
					for( Map<String, String> active3 : amtl ){
						Long retMoney = Long.parseLong(active3.get("retMoney"));
						Double cutMoney = Arith.div(retMoney, 1000, 2); // 减多少金额
						if( lowest == 0.0 ){
							// 第一个赋值
							lowest = cutMoney;
							lowestMap = active3;
						}else{
							// 比前面的小
							if( lowest > cutMoney ){
								lowest = cutMoney;
								lowestMap = active3;
							}
						}
					}
				}
			}
		}
		
		return lowestMap;
	}
	
	/**
	 * 计算购物车的满减优惠 - 根据活动
	 * <br>
	 * 循环活动列表 - 同活动的商品组合起来扣钱 + 组合返回参数"商品-活动"列表
	 * */ 
	private static List<ShoppingCartResProduct> calcShoppingCartFullCut(List<Map<String, String>> activeList, List<ShoppingCartReqProduct> productList){
		
		List<ShoppingCartResProduct> shoppingCartResProductList = new ArrayList<ShoppingCartResProduct>();
		
		LogCvt.debug("计算购物车满减优惠情况");
		
		for( Map<String, String> active : activeList ){
			// 找出相同活动的商品
			String activeId = active.get("activeId");
			List<ShoppingCartReqProduct> sameActiveProductList = new ArrayList<ShoppingCartReqProduct>();
			for( ShoppingCartReqProduct product : productList ){
				if( activeId.equals(product.getActiveId_ptmj()) || activeId.equals(product.getActiveId_sdmj()) ){
					sameActiveProductList.add(product);
				}
			}
			
			// 排序
			Collections.sort(sameActiveProductList);
			LogCvt.debug("营销活动购物车-活动id:"+activeId+" 所属商品:"+JSON.toJSONString(sameActiveProductList));
			
			// 计算相同满减活动商品的优惠
			List<ShoppingCartResProduct> sameActiveResProductList = calcSameFullCutProducts(active, sameActiveProductList);
			LogCvt.debug("营销活动购物车-活动id:"+activeId+" 所属商品的优惠情况:"+JSON.toJSONString(sameActiveResProductList));
			shoppingCartResProductList.addAll(sameActiveResProductList);
		}
		
		return shoppingCartResProductList;
	}
	
	/**
	 * 合并相同商品id的list
	 * */
	private static List<ShoppingCartResProduct> mergeSameProductIdList(List<ShoppingCartResProduct> shoppingCartResProductList){
		List<ShoppingCartResProduct> resultList = new ArrayList<ShoppingCartResProduct>();
		// 得到不重复的商品id
		List<String> productIds = new ArrayList<String>();
		String productId = null;
		for( ShoppingCartResProduct resProduct : shoppingCartResProductList ){
			productId = resProduct.getProductId();
			if( !productIds.contains(productId) ){
				productIds.add(productId);
			}
		}
		for( String pId : productIds ){
			ShoppingCartResProduct resProduct = new ShoppingCartResProduct();
			resProduct.setProductId(pId);
			List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
			for( ShoppingCartResProduct resP : shoppingCartResProductList ){
				productId = resP.getProductId();
				if( pId.equals(productId) ){
					shoppingCartResActiveList.add(resP.getShoppingCartResActiveList().get(0));
				}
			}
			resProduct.setShoppingCartResActiveList(shoppingCartResActiveList);
			resultList.add(resProduct);
		}
		return resultList;
	}
	
	/**
	 * 此活动的商品活动设置为删除
	 * */
	private static void setActiveByDelete(String activeId, List<ShoppingCartResProduct> shoppingCartResProductList){
		for( ShoppingCartResProduct resProduct : shoppingCartResProductList ){
			for( ShoppingCartResActive resActive : resProduct.getShoppingCartResActiveList() ){
				if( activeId.equals(resActive.getActiveId()) ){
					resActive.setIsLowestDelete(true);
				}
			}
		}
	}
	
	/** 计算相同满减活动商品的优惠 */
	private static List<ShoppingCartResProduct> calcSameFullCutProducts(Map<String, String> active, List<ShoppingCartReqProduct> sameActiveProductList){
		
		List<ShoppingCartResProduct> resProductList = new ArrayList<ShoppingCartResProduct>();
		
		String activeId = active.get("activeId"); // 活动id
		String activeName = active.get("activeName"); // 活动名称
		String activeType = active.get("type"); // 活动类型
		Long minLimit = 0l, retMoney = 0l;;
		if( ActiveType.fullCut.getCode().equals(activeType) ){
			minLimit = Long.parseLong(active.get("minLimit"));
			retMoney = Long.parseLong(active.get("retMoney"));
		}else if( ActiveType.firstPayment.getCode().equals(activeType) ){
			minLimit = Long.parseLong(active.get("limitMoney"));
			retMoney = Long.parseLong(active.get("cutMoney"));
		}
		Double minMoney = Arith.div(minLimit, 1000, 2); // 金额下限 - 满多少金额
		Double cutMoney = Arith.div(retMoney, 1000, 2); // 减多少金额
		String statusMsg = active.get("statusMsg"); // 状态描述
		
		String activeStatus = active.get("activeStatus"); // 活动状态
		// 活动不是正常状态 cut_money满减额都为0
		if( !"0".equals(activeStatus) ){
			
			LogCvt.debug("活动 "+activeId+" 状态:"+activeStatus+" 为非正常");
			
			for( ShoppingCartReqProduct product : sameActiveProductList ){
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(product.getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				// 这类商品的满减金额都为 0
				ShoppingCartResActive shoppingCartResActive = 
						assembledResActive(activeId, activeName, activeType, activeStatus, 0d, false, statusMsg);
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
			return resProductList;
		}

		LogCvt.debug("活动 "+activeId+" 状态正常 - 满减下限:"+minMoney);
		
		Double sumMoney = 0.0;
		// 计算相同活动商品的总价
		for( ShoppingCartReqProduct product : sameActiveProductList ){
			sumMoney += ( product.getProductTotalMoney() + product.getVipTotalMoney() );
		}
		
		LogCvt.debug("活动 "+activeId+" 满减金额下限 "+minMoney+" - 此活动商品总额:"+sumMoney);
		
		// 相同活动商品的总价超过金额下限 - 算每样商品的满减金额
		if( sumMoney >= minMoney ){
			
			
			LogCvt.debug("同一活动商品类别数:"+sameActiveProductList.size());
			// 相同活动的商品种类不止一个
			if( sameActiveProductList.size() > 1 ){
				
				// 已经分摊的满减额
				Double alreadyCotMoney = 0.0;
				for( int i = 0; i < sameActiveProductList.size(); i++ ){
					
					ShoppingCartReqProduct product = sameActiveProductList.get(i);
					
					// 单类购物总金额
					Double singleSumMoney = product.getProductTotalMoney() + product.getVipTotalMoney();
					// 计算商品的占比满减金额 
					Double singleCutMoney = ActiveUtils.calcSameActiveShareCutMoney(singleSumMoney, sumMoney, cutMoney);
					// 商品的占比满减金额 + 已经分摊的满减额 >= 满减金额
					if( Arith.add(singleCutMoney, alreadyCotMoney) >= cutMoney ){
						
						// 商品的占比满减金额 = 满减金额 - 已经分摊的满减额
						singleCutMoney = Arith.sub(cutMoney, alreadyCotMoney);
					}
					// 累加 已经分摊的满减额
					alreadyCotMoney = Arith.add(alreadyCotMoney, singleCutMoney);
					
					// 构建出参
					ShoppingCartResProduct resP = new ShoppingCartResProduct();
					resP.setProductId(product.getProductId());
					List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
					ShoppingCartResActive shoppingCartResActive = 
							assembledResActive(activeId, activeName, activeType, activeStatus, singleCutMoney, false, statusMsg);
					shoppingCartResActiveList.add(shoppingCartResActive);
					resP.setShoppingCartResActiveList(shoppingCartResActiveList);
					resProductList.add(resP);
					
				}
				
			}else{ // 相同活动的商品种类只有一个
				
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(sameActiveProductList.get(0).getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				// 种类只有一个 满减额就是总的满减额
				ShoppingCartResActive shoppingCartResActive  = 
						assembledResActive(activeId, activeName, activeType, activeStatus, cutMoney, false, statusMsg);
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
		}else{ // 相同活动商品的总价没有达到金额下限
			
			for( ShoppingCartReqProduct product : sameActiveProductList ){
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(product.getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				// 这类商品的满减金额都为 0
				ShoppingCartResActive shoppingCartResActive = 
						assembledResActive(activeId, activeName, activeType, activeStatus, 0d, true, statusMsg);
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
		}
		
		return resProductList;
	}
	
	/** 拼装购物车响应的商品活动信息 */
	private static ShoppingCartResActive assembledResActive(String activeId, String activeName, String activeType, String activeStatus, Double cutMoney, Boolean isMinato, String statusMsg){
		ShoppingCartResActive shoppingCartResActive = new ShoppingCartResActive();
		shoppingCartResActive.setActiveId(activeId);
		shoppingCartResActive.setActiveName(activeName);
		shoppingCartResActive.setActiveType(activeType);
		shoppingCartResActive.setActiveStatus(activeStatus);
		shoppingCartResActive.setCutMoney(cutMoney);
		shoppingCartResActive.setStatusMsg(statusMsg);
		shoppingCartResActive.setIsMinato(isMinato);
		return shoppingCartResActive;
	}
	
	/**
	 * 处理购物车满赠活动
	 * */
	private static List<ShoppingCartFullGive> disposeShoppingCartFullGive(ShoppingCartReq shoppingCartReq, List<ShoppingCartResProduct> shoppingCartResProductList){
		
		String reqId = shoppingCartReq.getReqId();
		String clientId = shoppingCartReq.getClientId();
		Long menberCode = shoppingCartReq.getMemberCode();
		List<ShoppingCartReqProduct> shoppingCartReqProductList = shoppingCartReq.getShoppingCartReqProductList();
		
		List<ShoppingCartFullGive> shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGive>();
		
		/**   1 把所有满足条件的满赠活动找出来        */
		// 循环购物车中所有商品 - 获取满足条件的活动
		List<Map<String, String>> activeMapList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> aml = null;
		String productId = null,activeId = null,type= null;
		for( ShoppingCartReqProduct reqProduct : shoppingCartReqProductList ){
			productId = reqProduct.getProductId();
			aml = checkBuyProductOfAllActiveQuali(clientId, menberCode, productId);
			if( aml != null && aml.size() > 0 ){
				for( Map<String, String> am : aml ){
					activeId = am.get("activeId");
					type = am.get("type");
					if( !am.isEmpty() && type != null && (type.equals(ActiveType.fullGive.getCode())) ){
						activeMapList.add(am);
						reqProduct.setActiveId_mz(activeId);
					}
				}
			}
		}
		
		// 过滤重复的活动
		activeMapList = filterRepeat(activeMapList);
		LogCvt.debug("营销活动购物车-请求编号:"+reqId+" 所属营销满赠活动:"+JSON.toJSONString(activeMapList));
				
		/**   2 把同活动的商品组合起来计算满赠 */
		// 有满足条件的活动
		if( activeMapList.size() > 0 ){
			
			// 计算购物车优惠
			shoppingCartFullGiveList = calcShoppingCartFullGive(activeMapList, shoppingCartReqProductList, shoppingCartResProductList);

		}else{ // 没有满足条件的活动
			LogCvt.debug("营销活动购物车-请求编号:"+reqId+" 所属营销满赠活动为空");
		}
		
		return shoppingCartFullGiveList;
	}
	
	/**
	 * 计算购物车的满赠优惠 - 根据活动
	 * */ 
	private static List<ShoppingCartFullGive> calcShoppingCartFullGive(List<Map<String, String>> activeList, List<ShoppingCartReqProduct> productList, List<ShoppingCartResProduct> shoppingCartResProductList){

		LogCvt.debug("计算购物车满赠优惠情况");

		// 存放购物车满赠信息列表
		List<ShoppingCartFullGive> shoppingCartFullGiveList = new ArrayList<ShoppingCartFullGive>();
		
		// 存放满赠的优惠相应
		List<ShoppingCartResProduct> fullGiveResProductList = new ArrayList<ShoppingCartResProduct>();
		
		for( Map<String, String> active : activeList ){
			// 找出相同活动的商品
			String activeId = active.get("activeId");
			List<ShoppingCartReqProduct> sameActiveProductList = new ArrayList<ShoppingCartReqProduct>();
			for( ShoppingCartReqProduct product : productList ){
				if( activeId.equals(product.getActiveId_mz()) ){
					sameActiveProductList.add(product);
				}
			}
			
			// 排序
			Collections.sort(sameActiveProductList);
			LogCvt.debug("营销活动购物车-活动id:"+activeId+" 所属商品:"+JSON.toJSONString(sameActiveProductList));

			// 计算相同满赠活动商品的优惠
			List<ShoppingCartResProduct> sameActiveResProductList = calcSameFullGiveProducts(active, sameActiveProductList, shoppingCartResProductList);
			LogCvt.debug("营销活动购物车-活动id:"+activeId+" 所属商品的优惠情况:"+JSON.toJSONString(sameActiveResProductList));
			fullGiveResProductList.addAll(sameActiveResProductList);
			
			// 活动正常 && 不凑单 的满赠活动加入列表
			ShoppingCartResActive t1 = sameActiveResProductList.get(0).getShoppingCartResActiveList().get(0);
			if( "0".equals(t1.getActiveStatus()) && !t1.getIsMinato() ){
				ShoppingCartFullGive fullGive = new ShoppingCartFullGive();
				fullGive.setFullGiveActiveId(t1.getActiveId());
				fullGive.setFullGiveActiveName(t1.getActiveName());
				String prePayActiveId = active.get("prePayActiveId");
				String point = active.get("point");
				Boolean isGiveVouchers = false;
				Double vouchersMoney = 0.0;
				// 送红包
				if( prePayActiveId != null && !"".equals(prePayActiveId) ){
					isGiveVouchers = true;
					Map<String, String> vm = VouchersRedis.readVouchersActive(active.get("clientId"), prePayActiveId);
					String vMoney = vm.get("minMoney");
					if( vMoney != null && !"".equals(vMoney) ){
						vouchersMoney = Arith.div(Double.parseDouble(vMoney), 1000, 2);
					}
////				 送联盟积分
//				}else if( point != null && !"".equals(point) || Integer.parseInt(point) > 0 ){
				}
				fullGive.setGiveType(isGiveVouchers?ActiveAwardType.vouchers.getCode():ActiveAwardType.unionIntegral.getCode());
				fullGive.setGiveMoney(isGiveVouchers?vouchersMoney:Double.parseDouble(point));
				shoppingCartFullGiveList.add(fullGive);
			}
		}
		
		// 合并到 商品-活动 列表
		for( ShoppingCartResProduct fullGiveP : fullGiveResProductList ){
			String fgpi = fullGiveP.getProductId();
			boolean isExist = false; // 是否在原有的list中存在
			for( ShoppingCartResProduct shoppingP : shoppingCartResProductList ){
				String sppi = shoppingP.getProductId();
				if( sppi.equals(fgpi) ){
					isExist = true;
					if( shoppingP.getShoppingCartResActiveList() == null ){
						shoppingP.setShoppingCartResActiveList(fullGiveP.getShoppingCartResActiveList());
					}else{
						shoppingP.getShoppingCartResActiveList().addAll(fullGiveP.getShoppingCartResActiveList());
					}
					
				}
			}
			if( !isExist ){ // 在原有的list中不存在
				shoppingCartResProductList.add(fullGiveP);
			}
		}
		
		return shoppingCartFullGiveList;
	}
	
	/** 计算相同满赠活动商品的优惠 */
	private static List<ShoppingCartResProduct> calcSameFullGiveProducts(Map<String, String> active, List<ShoppingCartReqProduct> sameActiveProductList, List<ShoppingCartResProduct> shoppingCartResProductList){
		
		List<ShoppingCartResProduct> resProductList = new ArrayList<ShoppingCartResProduct>();
		
		String activeId = active.get("activeId"); // 活动id
		String activeName = active.get("activeName"); // 活动名称
		String activeType = active.get("type"); // 活动类型
		Long minLimit = Long.parseLong(active.get("minLimit"));
		Double minMoney = Arith.div(minLimit, 1000, 2); // 金额下限 - 满多少金额
		String statusMsg = active.get("statusMsg"); // 状态描述
		String prePayActiveId = active.get("prePayActiveId");
		String point = active.get("point");
		Boolean isGiveVouchers = false;
		Double vouchersMoney = 0.0;
		// 送红包
		if( prePayActiveId != null && !"".equals(prePayActiveId) ){
			isGiveVouchers = true;
			Map<String, String> vm = RedisCommon.readFullCutActive(active.get("clientId"), activeId);
			String vMoney = vm.get("minMoney");
			if( vMoney != null && !"".equals(vMoney) ){
				vouchersMoney = Arith.div(Double.parseDouble(vMoney), 1000, 2);
			}
////		 送联盟积分
//		}else if( point != null && !"".equals(point) || Integer.parseInt(point) > 0 ){
		}
		
		String activeStatus = active.get("activeStatus"); // 活动状态
		// 活动不是正常状态
		if( !"0".equals(activeStatus) ){
			
			LogCvt.debug("活动 "+activeId+" 状态:"+activeStatus+" 为非正常");
			
			for( ShoppingCartReqProduct product : sameActiveProductList ){
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(product.getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				ShoppingCartResActive shoppingCartResActive = 
						assembledResActive(activeId, activeName, activeType, activeStatus, 0d, false, statusMsg);
				shoppingCartResActive.setGiveType(isGiveVouchers?ActiveAwardType.vouchers.getCode():ActiveAwardType.unionIntegral.getCode());
				shoppingCartResActive.setGiveMoney(isGiveVouchers?vouchersMoney:Double.parseDouble(point));
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
			return resProductList;
		}
		
		LogCvt.debug("活动 "+activeId+" 状态正常 - 满赠下限:"+minMoney);
		
		Double sumMoney = 0.0;
		// 计算相同活动商品的总价
		for( ShoppingCartReqProduct product : sameActiveProductList ){
			sumMoney += ( product.getProductTotalMoney() + product.getVipTotalMoney() );
		}
		// 减去已经分摊的满减额(文档要求先算满减再算满赠)
		if( shoppingCartResProductList != null && shoppingCartResProductList.size() > 0 ){
			for( ShoppingCartResProduct resProduct : shoppingCartResProductList ){
				if( resProduct != null && resProduct.getShoppingCartResActiveList() != null && resProduct.getShoppingCartResActiveList().size() > 0 ){
					// 如果此商品在活动内，在总价上减去满减额
					if( isExistActiveProductList(resProduct.getProductId(), sameActiveProductList) ){
						for( ShoppingCartResActive resActive : resProduct.getShoppingCartResActiveList() ){
							Double cutMoney = resActive.getCutMoney();
							if( cutMoney != null && cutMoney > 0.0 ){
								sumMoney -= cutMoney;
							}
						}
					}
				}
			}
		}
		
		
		LogCvt.debug("活动 "+activeId+" 满赠金额下限 "+minMoney+" - 此活动商品总额:"+sumMoney);
		
		// 相同活动商品的总价超过金额下限
		if( sumMoney >= minMoney ){
			
			LogCvt.debug("同一活动商品类别数:"+sameActiveProductList.size());
			// 相同活动的商品种类不止一个
			if( sameActiveProductList.size() > 1 ){
				
				for( int i = 0; i < sameActiveProductList.size(); i++ ){
					
					ShoppingCartResProduct resP = new ShoppingCartResProduct();
					resP.setProductId(sameActiveProductList.get(i).getProductId());
					List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
					ShoppingCartResActive shoppingCartResActive = 
							assembledResActive(activeId, activeName, activeType, activeStatus, 0d, false, statusMsg);
					shoppingCartResActive.setGiveType(isGiveVouchers?ActiveAwardType.vouchers.getCode():ActiveAwardType.unionIntegral.getCode());
					shoppingCartResActive.setGiveMoney(isGiveVouchers?vouchersMoney:Double.parseDouble(point));
					shoppingCartResActiveList.add(shoppingCartResActive);
					resP.setShoppingCartResActiveList(shoppingCartResActiveList);
					resProductList.add(resP);
				}
				
			}else{ // 相同活动的商品种类只有一个
				
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(sameActiveProductList.get(0).getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				ShoppingCartResActive shoppingCartResActive  = 
						assembledResActive(activeId, activeName, activeType, activeStatus, 0d, false, statusMsg);
				shoppingCartResActive.setGiveType(isGiveVouchers?ActiveAwardType.vouchers.getCode():ActiveAwardType.unionIntegral.getCode());
				shoppingCartResActive.setGiveMoney(isGiveVouchers?vouchersMoney:Double.parseDouble(point));
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
			
		}else{ // 相同活动商品的总价没有达到金额下限
			for( ShoppingCartReqProduct product : sameActiveProductList ){
				ShoppingCartResProduct resP = new ShoppingCartResProduct();
				resP.setProductId(product.getProductId());
				List<ShoppingCartResActive> shoppingCartResActiveList = new ArrayList<ShoppingCartResActive>();
				// 这类商品的满减金额都为 0
				ShoppingCartResActive shoppingCartResActive = 
						assembledResActive(activeId, activeName, activeType, activeStatus, 0d, true, statusMsg);
				shoppingCartResActive.setGiveType(isGiveVouchers?ActiveAwardType.vouchers.getCode():ActiveAwardType.unionIntegral.getCode());
				shoppingCartResActive.setGiveMoney(isGiveVouchers?vouchersMoney:Double.parseDouble(point));
				shoppingCartResActiveList.add(shoppingCartResActive);
				resP.setShoppingCartResActiveList(shoppingCartResActiveList);
				resProductList.add(resP);
			}
		}
		
		return resProductList;
	}
	
	/**
	 * 是否存在活动商品中
	 * */
	private static boolean isExistActiveProductList(String productId, List<ShoppingCartReqProduct> sameActiveProductList){
		for( ShoppingCartReqProduct product : sameActiveProductList ){
			if( productId != null && product.getProductId() != null && productId.equals(product.getProductId()) ){
				return true;
			}
		}
		return false;
	}
}
