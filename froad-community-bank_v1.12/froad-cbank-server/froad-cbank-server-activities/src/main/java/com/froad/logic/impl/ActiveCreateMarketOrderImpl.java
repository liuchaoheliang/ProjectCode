package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.ActiveType;
import com.froad.enums.ModuleID;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveCreateMarketOrder;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.CreateMarketOrderReq;
import com.froad.po.CreateResult;
import com.froad.po.MarketSubOrder;
import com.froad.po.MarketSubOrderProduct;
import com.froad.po.Result;
import com.froad.po.VouchersInfo;
import com.froad.util.ActiveUtils;
import com.froad.util.Arith;
import com.froad.util.SimpleID;

public class ActiveCreateMarketOrderImpl implements ActiveCreateMarketOrder {

	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	private static ActiveOrderHandler activeOrderHandler = new ActiveOrderHandlerImpl();
	private static SimpleID simple = new SimpleID(ModuleID.activeOrder);
	
	
	/**
     * 创建营销订单
     * <br>
     * CreateResult.Result.resultCode = 0000 成功
     * CreateResult.id = 营销订单编号
     * <br>
     * CreateResult.Result.resultCode != 不成功
     * CreateResult.Result.resultDesc = 失败信息
     */
	@Override
	public CreateResult createMarketOrder(
			CreateMarketOrderReq createMarketOrderReq) {
		
		LogCvt.info("创建营销订单 createMarketOrder 逻辑:");
		
		CreateResult createResult = new CreateResult();
		
		String orderId = createMarketOrderReq.getOrderId();
		String clientId = createMarketOrderReq.getClientId();
		Long memberCode = createMarketOrderReq.getMemberCode();
		
		List<String> activeIds = new ArrayList<String>();
		List<String> activeGiveIds = new ArrayList<String>();
		
		LogCvt.debug("循环订单 "+orderId+" 所有子订单商品");
		boolean isExistActive = false; // 初始 - 没有满减活动
		boolean isExistActiveGive = false; // 初始 - 没有满赠活动
		List<MarketSubOrder> marketSubOrderList = createMarketOrderReq.getMarketSubOrderList();
		for( MarketSubOrder marketSubOrder : marketSubOrderList ){
			List<MarketSubOrderProduct> marketSubOrderProductList = marketSubOrder.getMarketSubOrderProductList();
			for( MarketSubOrderProduct marketSubOrderProduct : marketSubOrderProductList ){
				String activeId = marketSubOrderProduct.getActiveId();
				if( activeId != null && !"".equals(activeId) ){
					activeIds.add(activeId);
					isExistActive = true; // 有满减活动
				}
				String activeGiveIdS = marketSubOrderProduct.getActiveIdGive();
				if( activeGiveIdS != null && !"".equals(activeGiveIdS) ){
					activeGiveIds.add(activeGiveIdS);
					isExistActiveGive = true; // 有满赠活动
				}
			}
		}
		
		List<String> vouchersIds = null;
		vouchersIds = createMarketOrderReq.getVouchersIds();
		LogCvt.debug("订单 "+orderId+" 包含的满减促销活动:"+activeIds);
		LogCvt.debug("订单 "+orderId+" 包含的满赠促销活动:"+activeGiveIds);
		LogCvt.debug("订单 "+orderId+" 包含的代金券(红包):"+vouchersIds);
		// 1 防止没有活动  也没有  代金券(红包)
		if( !isExistActive && !isExistActiveGive && ( vouchersIds == null || vouchersIds.isEmpty() || vouchersIds.size() < 1 || ( vouchersIds.size() == 1 && null == vouchersIds.get(0) ) ) ){ 
			createResult.setResult(new Result(ResultCode.failed.getCode(), "订单中没有商品参加营销满减或满赠活动  并且 没有使用红包(代金券)"));
			return createResult;
		}
		
		// 2 创建 满减订单 - 如果存在满减活动
		Result createCutResult = null;
		if( isExistActive ){
			createCutResult = createCutOrder(createMarketOrderReq);
		}
		LogCvt.debug("订单 "+orderId+" 创建满减订单结果:"+JSON.toJSONString(createCutResult));
		
		// 2.1 创建 满减订单 失败
		if( createCutResult != null && !ResultCode.success.getCode().equals(createCutResult.getResultCode()) ){
			
			// 回退满减资格
			Result result1 = ActiveCreateOrderGoBackLogicImpl.goBackCutActive(activeIds, clientId, memberCode);
			LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result1));
			
			// 如果有红包 - 则回退资格,更新红包状态为初始化
			// TODO 如果更新失败则要看日志手工更新 - 后面加上监控
			try {
				if( vouchersIds != null && vouchersIds.size() < 1 ){
					Result result2 = ActiveCreateOrderGoBackLogicImpl.goBackVouchers(vouchersIds, clientId, memberCode, orderId, 1);
					LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result2));
					Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIds);
					LogCvt.debug("更新券状态:"+updateResult);
				}
			} catch (Exception e) {
				LogCvt.debug("更新券状态 异常");
			}
			createResult.setResult(createCutResult);
			return createResult;
		}
		// 2.1 创建 满减订单 成功
		if( createCutResult != null && ResultCode.success.getCode().equals(createCutResult.getResultCode()) ){ 
			createResult.setVouchersOrderId(createCutResult.getResultDesc());
		}
		
		// 2.2 没有满减活动 有满赠活动 - 创建订单主表
		Result createGiveResult = null;
		if( !isExistActive && isExistActiveGive ){
			createGiveResult = createGiveOrder(createMarketOrderReq);
			LogCvt.debug("订单 "+orderId+" 创建满赠订单结果: "+JSON.toJSONString(createGiveResult));
			// 2.3 创建 满赠订单 失败
			if( createGiveResult != null && !ResultCode.success.getCode().equals(createGiveResult.getResultCode()) ){
				// 如果有红包 - 则回退资格,更新红包状态为初始化
				// TODO 如果更新失败则要看日志手工更新 - 后面加上监控
				try {
					if( vouchersIds != null && vouchersIds.size() < 1 ){
						Result result2 = ActiveCreateOrderGoBackLogicImpl.goBackVouchers(vouchersIds, clientId, memberCode, orderId, 1);
						LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result2));
						Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIds);
						LogCvt.debug("更新券状态:"+updateResult);
					}
				} catch (Exception e) {
					LogCvt.debug("更新券状态 异常");
				}
				createResult.setResult(createCutResult);
				return createResult;
			}
			// 2.4 创建 满赠订单 成功
			if( createGiveResult != null && ResultCode.success.getCode().equals(createGiveResult.getResultCode()) ){ 
				createResult.setVouchersOrderId(createGiveResult.getResultDesc());
			}
		}
		
		
		// 3 创建 代金券(红包)订单 - 如果有代金券/红包
		Result createVouchersResult = null;
		if( vouchersIds != null && !vouchersIds.isEmpty() && vouchersIds.size() > 0 ){
			createVouchersResult = createVouchersOrder(createMarketOrderReq, isExistActive, isExistActiveGive);
		}
		LogCvt.debug("订单 "+orderId+" 创建代金券(红包)订单结果:"+JSON.toJSONString(createVouchersResult));
		
		// 3.1 创建代金券(红包)订单失败
		if( createVouchersResult != null && !ResultCode.success.getCode().equals(createVouchersResult.getResultCode()) ){
			
			// 如果有创建满减订单 成功
			if( createCutResult != null && ResultCode.success.getCode().equals(createCutResult.getResultCode()) ){
				// 回退满减资格
				Result result1 = ActiveCreateOrderGoBackLogicImpl.goBackCutActive(activeIds, clientId, memberCode);
				LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result1));
				LogCvt.debug("订单 "+orderId+" 满减订单创建成功,代金券(红包)订单创建失败 - 删除创建的满减订");
				// 删除创建的满减订单
				ActiveCreateOrderGoBackLogicImpl.deleteMarketOrder(orderId);
			}
			// 回退红包资格,修改券状态
			try {
				Result result4 = ActiveCreateOrderGoBackLogicImpl.goBackVouchers(vouchersIds, clientId, memberCode, orderId, 1);
				LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result4));
				// TODO 如果更新失败则要看日志手工更新 - 后面加上监控
				Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIds);
				LogCvt.debug("更新券状态:"+updateResult);
			} catch (Exception e) {
				LogCvt.debug("更新券状态 异常");
			}
			createResult.setResult(createVouchersResult);
			return createResult;
		}
		// 3.1 创建代金券(红包)订单 成功
		if( createVouchersResult != null && ResultCode.success.getCode().equals(createVouchersResult.getResultCode()) ){ 
			createResult.setVouchersOrderId(createVouchersResult.getResultDesc());
		}
		
		createResult.setResult(new Result(ResultCode.success.getCode(), ResultCode.success.getMsg()));
		return createResult;
	}
	
	/**
	 * 创建满减订单
	 * */
	private static Result createCutOrder(CreateMarketOrderReq createMarketOrderReq){
		
		// 1 组装营销订单主表信息
		ActiveOrder activeOrder = assembledActiveOrder(createMarketOrderReq);
		LogCvt.debug("组装营销订单主表信息:"+JSON.toJSONString(activeOrder));
		// 如果组装信息失败
		if( activeOrder == null ){
			return new Result(ResultCode.failed.getCode(), "组装营销订单主表信息失败");
		}
				
		// 2 组装满减订单详情列表
		List<ActiveOrderDetail> activeOrderDetailList = assembledCutActiveOrderDetailList(createMarketOrderReq);
		LogCvt.debug("组装满减订单详情列表:"+JSON.toJSONString(activeOrderDetailList));
		// 如果组装信息失败
		if( activeOrderDetailList == null || activeOrderDetailList.size() < 0 ){
			return new Result(ResultCode.failed.getCode(), "组装满减订单详情信息失败");
		}
				
		// 3 回填 ActiveOrder.detail
		String detail = getOrderDetail_cut(activeOrderDetailList);
		activeOrder.setDetail(detail);
				
		// 4 新增营销订单主表信息 和  详情
		boolean result = activeOrderHandler.addActiveOrderAndOrderDetailList(activeOrder, activeOrderDetailList);
		LogCvt.debug("新增满减订单主表信息和详情列表 结果:"+result);
		if( !result ){
			return new Result(ResultCode.failed.getCode(), "新增满减订单信息失败");
		}
		
		return new Result(ResultCode.success.getCode(), activeOrder.getId());
	}

	/**
	 * 创建满赠订单
	 * */
	private static Result createGiveOrder(CreateMarketOrderReq createMarketOrderReq){
		
		try{
			// 1 组装营销订单主表信息
			ActiveOrder activeOrder = assembledActiveOrder(createMarketOrderReq);
			LogCvt.debug("组装营销订单主表信息:"+JSON.toJSONString(activeOrder));
			// 如果组装信息失败
			if( activeOrder == null ){
				return new Result(ResultCode.failed.getCode(), "组装营销订单主表信息失败");
			}
		
			// 4 新增营销订单主表信息 和  详情
			Long result = activeOrderHandler.addActiveOrder(activeOrder);
			LogCvt.debug("新增营销订单主表信息 结果:"+result);
			if( result <= 0 ){
				return new Result(ResultCode.failed.getCode(), "新增营销订单信息失败");
			}
			return new Result(ResultCode.success.getCode(), activeOrder.getId());
		}catch(Exception e){
			LogCvt.error("新增订单信息异常 ",e);
			return new Result(ResultCode.failed.getCode(), "创建营销订单主表信息失败");
		}
	}
	
	/**
	 * 创建代金券(红包)订单
	 * */
	private static Result createVouchersOrder(CreateMarketOrderReq createMarketOrderReq, boolean isExistActive, boolean isExistActiveGive){
		
		// 1 组装营销订单主表信息 - 如果之前没有满减(满赠)促销活动则新建订单主表
		ActiveOrder activeOrder = null;
		if( !isExistActive && !isExistActiveGive ){
			activeOrder = assembledActiveOrder(createMarketOrderReq);
			LogCvt.debug("组装营销订单主表信息:"+JSON.toJSONString(activeOrder));
			// 如果组装信息失败
			if( activeOrder == null ){
				return new Result(ResultCode.failed.getCode(), "组装营销订单主表信息失败");
			}
		}else{ // 如果之前有满减(满赠)促销活动则获取订单主表
			ActiveOrder find_Order = new ActiveOrder();
			find_Order.setOrderId(createMarketOrderReq.getOrderId());
			try {
				activeOrder = activeOrderHandler.findOneActiveOrder(find_Order);
				if( activeOrder == null ){
					return new Result(ResultCode.failed.getCode(), "获取营销订单主表信息失败");
				}
			} catch (Exception e) {
				LogCvt.error("获取订单主表 异常", e);
				return new Result(ResultCode.failed.getCode(), "获取营销订单主表信息失败");
			}
		}
		
		// 2 组装代金券(红包)订单详情列表
		List<ActiveOrderDetail> activeOrderDetailList = assembledVouActiveOrderDetailList(createMarketOrderReq);
		LogCvt.debug("组装代金券(红包)订单详情列表:"+JSON.toJSONString(activeOrderDetailList));
		// 如果组装信息失败
		if( activeOrderDetailList == null || activeOrderDetailList.size() < 0 ){
			return new Result(ResultCode.failed.getCode(), "组装代金券(红包)订单详情信息失败");
		}
		
		// 3 回填 ActiveOrder.detail
		if( !isExistActive ){ // 之前没有满减促销活动
			String detail = getOrderDetail_vou(activeOrderDetailList);
			activeOrder.setDetail(detail);
		}else{ // 之前有满减促销活动
			String detail = getOrderDetail_fold_vou(activeOrder.getDetail(), activeOrderDetailList);
			activeOrder.setDetail(detail);
		}
		
		// 4 新增营销订单主表信息 和  详情
		if( !isExistActive && !isExistActiveGive ){ // 之前没有满减(满赠)促销活动
			// 新增主表和详情
			boolean result = activeOrderHandler.addActiveOrderAndOrderDetailList(activeOrder, activeOrderDetailList);
			LogCvt.debug("新增订单主表信息和详情列表 结果:"+result);
			if( !result ){
				return new Result(ResultCode.failed.getCode(), "新增代金券(红包)订单信息失败");
			}
		}else{ // 之前有满减(满赠)促销活动
			// 修改主表的回填交易明细 - 新增详情
			ActiveOrder updateActiveOrder = new ActiveOrder();
			updateActiveOrder.setId(activeOrder.getId());
			updateActiveOrder.setDetail(activeOrder.getDetail());
			boolean result = activeOrderHandler.updateActiveOrderdetailAndAddOrderDetailList(updateActiveOrder, activeOrderDetailList);
			LogCvt.debug("修改主表的回填交易明细 - 新增详情 结果:"+result);
			if( !result ){
				return new Result(ResultCode.failed.getCode(), "新增代金券(红包)订单信息失败");
			}
		}
		
		return new Result(ResultCode.success.getCode(), activeOrder.getId());
	}
	
	/**
	 * 组装 营销订单主表信息
	 * */
	private static ActiveOrder assembledActiveOrder(CreateMarketOrderReq createMarketOrderReq){
		
		try{
			
			ActiveOrder activeOrder = new ActiveOrder();
			
			Date now = new Date();
			
			activeOrder.setId(simple.nextId());
			activeOrder.setClientId(createMarketOrderReq.getClientId());
			activeOrder.setCreateTime(now);
			activeOrder.setUpdateTime(now);
			activeOrder.setOrderId(createMarketOrderReq.getOrderId());
			activeOrder.setStatus(Boolean.TRUE);
			Double orderOriMoney = createMarketOrderReq.getOrderOriMoney();
			Double orderMoney = createMarketOrderReq.getOrderMoney();
			activeOrder.setOrderOriMoney(Arith.mul(orderOriMoney, 1000));
			activeOrder.setOrderMoney(Arith.mul(orderMoney, 1000));
			activeOrder.setOrderOriModifyMoney(Arith.mul(orderOriMoney, 1000));
			activeOrder.setOrderModifyMoney(Arith.mul(orderMoney, 1000));
			activeOrder.setPayTime(null);
			activeOrder.setMemberCode(createMarketOrderReq.getMemberCode());
			activeOrder.setMemberName(createMarketOrderReq.getMemberName());
			activeOrder.setPhone(createMarketOrderReq.getPhone());
			activeOrder.setDetail(null);
			
			return activeOrder;
		}catch(Exception ex){
			LogCvt.error("组装 营销订单主表信息 异常:", ex);
			return null;
		}
		
	}
	
	/**
	 * 组装满减订单详情列表
	 * */
	private static List<ActiveOrderDetail> assembledCutActiveOrderDetailList(CreateMarketOrderReq createMarketOrderReq){

		LogCvt.debug("组装满减订单详情列表");
		
		try{
			
			List<ActiveOrderDetail> activeOrderDetailList = new ArrayList<ActiveOrderDetail>();
			
			// 每个子订单商品都设置一个子订单id - 拼装【营销订单详情】使用
			// 取出子订单中的所有商品
			List<MarketSubOrder> marketSubOrderList = createMarketOrderReq.getMarketSubOrderList();
			List<MarketSubOrderProduct> allSubOrderProductList = getMarketSubOrderProductList(marketSubOrderList);
			
			LogCvt.debug("全部的子订单商品列表 "+JSON.toJSONString(allSubOrderProductList));
			
			// 过滤重复的活动id
			List<String> activeIdList = filterRepeat3(allSubOrderProductList);
			LogCvt.debug("订单中包含的满减活动id列表 "+JSON.toJSONString(activeIdList));
						
			String clientId = createMarketOrderReq.getClientId();
			String orderId = createMarketOrderReq.getOrderId();
			
			// 得到有活动的营销订单详情
			List<ActiveOrderDetail> existActiveOrderDetailList = calcExistActiveSubOrderProduct(activeIdList, allSubOrderProductList, clientId, orderId);
			LogCvt.debug("得到有满减的营销订单详情 "+JSON.toJSONString(existActiveOrderDetailList));
			activeOrderDetailList.addAll(existActiveOrderDetailList);
			
			// 得到没活动的营销订单详情
			List<ActiveOrderDetail> notExistActiveOrderDetailList = calcNotExistActiveSubOrderProduct(allSubOrderProductList, clientId, orderId);
			LogCvt.debug("得到没满减的营销订单详情 "+JSON.toJSONString(notExistActiveOrderDetailList));
			activeOrderDetailList.addAll(notExistActiveOrderDetailList);
			
			return activeOrderDetailList;
			
		}catch(Exception ex){
			LogCvt.error("组装 满减订单详情列表 异常:", ex);
			return null;
		}
		
	}
	
	/**
	 * 得到主订单信息的详情 - 满减
	 * */
	private static String getOrderDetail_cut(List<ActiveOrderDetail> activeOrderDetailList){
		List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			Map<String, Object> productMap = new HashMap<String, Object>();
			productMap.put(ActiveUtils.ACT_TYPE, ActiveType.fullCut.getCode());
			productMap.put(ActiveUtils.ACT_ID, activeOrderDetail.getActiveId());
			productMap.put(ActiveUtils.PRO_ID, activeOrderDetail.getProductId());
			productMap.put(ActiveUtils.PRO_NAME, activeOrderDetail.getProductName());
			productMap.put(ActiveUtils.GEN_PRICE, activeOrderDetail.getProductPrice());
			productMap.put(ActiveUtils.VIP_PRICE, activeOrderDetail.getProductVipPrice());
			productMap.put(ActiveUtils.GEN_DIS_MONEY, activeOrderDetail.getNormalPriceMarket()); // 平摊的满减金额
			productMap.put(ActiveUtils.GEN_DIS_COUNT, activeOrderDetail.getNormalPriceCount());
			productMap.put(ActiveUtils.VIP_DIS_MONEY, activeOrderDetail.getVipPriceMarket()); // 平摊的满减金额
			productMap.put(ActiveUtils.VIP_DIS_COUNT, activeOrderDetail.getVipPriceCount());
			productMapList.add(productMap);
		}
		String detail = JSON.toJSONString(productMapList);
		LogCvt.debug("回填满减detail:"+detail);
		return detail;
	}
	
	/**
	 * 组装代金券/红包订单详情列表
	 * */
	private static List<ActiveOrderDetail> assembledVouActiveOrderDetailList(CreateMarketOrderReq createMarketOrderReq){
		LogCvt.debug("组装代金券/红包订单详情列表");
		try{
			
			List<ActiveOrderDetail> activeOrderDetailList = new ArrayList<ActiveOrderDetail>();
			
			// 从子订单列表里取出所有子订单商品列表 
			// 每个子订单商品都设置一个子订单id - 拼装【营销订单详情】使用
			List<MarketSubOrder> marketSubOrderList = createMarketOrderReq.getMarketSubOrderList();
			List<MarketSubOrderProduct> allSubOrderProductList = getMarketSubOrderProductList(marketSubOrderList);
						
			LogCvt.debug("全部的子订单商品列表 "+JSON.toJSONString(allSubOrderProductList));
			
			// 取出订单中的
			List<String> vouchersIds = createMarketOrderReq.getVouchersIds();
			LogCvt.debug("订单中包含的代金券/红包id列表 "+JSON.toJSONString(vouchersIds));
			List<VouchersInfo> vouchersInfoList = getVouchersInfoList(vouchersIds);
			LogCvt.debug("订单中包含的代金券/红包信息列表 "+JSON.toJSONString(vouchersInfoList));
			
			// 计算子订单商品的总价
			// TODO 现在代金券/红包没有使用范围,所以分摊到全部商品 - 后续会有变化
			Double sumMoney = calcProductsSumMoney(allSubOrderProductList);
			LogCvt.debug("订单内商品总价:"+sumMoney);
			// 子订单商品的总价  减去  已经分摊的满减额
			for( MarketSubOrderProduct subOrderProduct : allSubOrderProductList ){
				Double pcm = subOrderProduct.getCutMoney();
				if( pcm != null && pcm > 0 ){
					sumMoney = Arith.sub(sumMoney, pcm);
				}
			}
			LogCvt.debug("订单内商品总价  减去  已经分摊的满减额:"+sumMoney);
			
			// 累计使用的红包总金额
			Double foldVouMoney = 0.0;
			
			String clientId = createMarketOrderReq.getClientId();
			String orderId = createMarketOrderReq.getOrderId();
			
			// 循环代金券/红包信息列表 - 组装订单详情
			for( VouchersInfo vouchersInfo : vouchersInfoList ){
				
				String vouchersId = vouchersInfo.getVouchersId();
				String orderMarketType = "0";
				String activeId = vouchersInfo.getActiveId();
				String activeType = vouchersInfo.getType();
				
				// 红包金额
				Double vouMoney = Arith.div(vouchersInfo.getVouchersMoney(), 1000, 2); 
				
				// 剩余的商品总额(商品总价  减去  累计红包总金额)
				Double surplusSumMoney = Arith.sub(sumMoney, foldVouMoney);
				// 如果剩余总额 <= 红包金额(防止订单总额小于红包金额)
				if( surplusSumMoney <= vouMoney ){
					// 参与分摊的红包金额 = 剩余总额
					vouMoney = surplusSumMoney;
				}
				
				// 商品不止一种
				if( allSubOrderProductList.size() > 1 ){
					
					// 已经分摊的红包额
					Double alreadyVouMoney = 0.0;
					
					for( int i = 0; i < allSubOrderProductList.size(); i++ ){
						
						MarketSubOrderProduct subOrderProduct = allSubOrderProductList.get(i);
						// 单种商品总价
						Double productMoney = 0.0;
						// 单种商品普通总价
						Double productGeneralMoney = Arith.mul2(subOrderProduct.getGeneralPrice(), subOrderProduct.getGeneralCount());
						// 单种商品vip总价
						Double productVipMoney = Arith.mul2(subOrderProduct.getVipPrice(), subOrderProduct.getVipCount());
						productMoney = Arith.add(productMoney, productGeneralMoney);
						productMoney = Arith.add(productMoney, productVipMoney);
						// 单种商品满减额
						Double pcm = subOrderProduct.getCutMoney();
						if( pcm != null && pcm > 0 ){
							Double tempD = ActiveUtils.calcSameActiveShareCutMoney(productGeneralMoney, productMoney, pcm);
							productGeneralMoney = Arith.sub(productGeneralMoney, tempD);
							productVipMoney = Arith.sub(productVipMoney, Arith.sub(pcm, tempD));
							productMoney = Arith.sub(productMoney, pcm);
							
						}
						
						// 计算单种商品的占比红包金额 
						Double singleVouMoney = ActiveUtils.calcSameActiveShareCutMoney(productMoney, sumMoney, vouMoney);
						// 商品的占比红包金额 + 已经分摊的红包额 >= 红包金额
						if( Arith.add(singleVouMoney, alreadyVouMoney) >= vouMoney ){
							
							// 商品的占比红包金额 = 红包金额 - 已经分摊的红包额
							singleVouMoney = Arith.sub(vouMoney, alreadyVouMoney);
						}
						
						// 累加 已经分摊的红包额
						alreadyVouMoney = Arith.add(alreadyVouMoney, singleVouMoney);
						
						// 单种商品普通红包金额 = 单种商品普通总价 / 单种商品总价 * 单种商品红包金额
						Double generalVouMoney = ActiveUtils.calcSameActiveShareCutMoney(productGeneralMoney, productMoney, singleVouMoney);
						// 单种商品vip红包金额 = 单种商品红包金额 - 单种商品普通红包金额
						Double vipVouMoney = Arith.sub(singleVouMoney, generalVouMoney);
						
						// 拼装营销活动订单详情
						ActiveOrderDetail activeOrderDetail = assembledActiveOrderDetail(clientId, 
																						 orderId, 
																						 orderMarketType, 
																						 activeId, 
																						 activeType, 
																						 subOrderProduct.getSubOrderId(), 
																						 subOrderProduct.getProductId(), 
																						 subOrderProduct.getProductName(),
																						 subOrderProduct.getGeneralPrice(),
																						 subOrderProduct.getVipPrice(),
																						 generalVouMoney,
																						 subOrderProduct.getGeneralCount(),
																						 vipVouMoney,
																						 subOrderProduct.getVipCount(),
																						 vouchersId);
						
						LogCvt.debug("得到订单详情:"+JSON.toJSONString(activeOrderDetail));
						activeOrderDetailList.add(activeOrderDetail);
					}
					
				}else{ // 商品种类只有一个
					MarketSubOrderProduct subOrderProduct = allSubOrderProductList.get(0);
					LogCvt.debug("计算订单商品:"+JSON.toJSONString(subOrderProduct));
					// 商品总价
					Double productMoney = 0.0;
					// 商品普通总价
					Double productGeneralMoney = Arith.mul2(subOrderProduct.getGeneralPrice(), subOrderProduct.getGeneralCount());
					// 商品vip总价
					Double productVipMoney = Arith.mul2(subOrderProduct.getVipPrice(), subOrderProduct.getVipCount());
					productMoney = Arith.add(productMoney, productGeneralMoney);
					productMoney = Arith.add(productMoney, productVipMoney);
					
					// 商品普通红包金额 = 商品普通总价 / 商品总价 * 红包总金额
					Double generalVouMoney = Arith.round(Arith.div(productGeneralMoney, productMoney, 2) * vouMoney, 2);
					// 商品vip红包金额 = 红包总金额 - 商品普通红包金额
					Double vipVouMoney = Arith.sub(vouMoney, generalVouMoney);
					
					// 拼装营销活动订单详情
					ActiveOrderDetail activeOrderDetail = assembledActiveOrderDetail(clientId, 
							 														 orderId, 
							 														 orderMarketType, 
							 														 activeId, 
							 														 activeType, 
							 														 subOrderProduct.getSubOrderId(), 
							 														 subOrderProduct.getProductId(), 
							 														 subOrderProduct.getProductName(),
							 														 subOrderProduct.getGeneralPrice(),
							 														 subOrderProduct.getVipPrice(),
							 														 generalVouMoney,
							 														 subOrderProduct.getGeneralCount(),
							 														 vipVouMoney,
							 														 subOrderProduct.getVipCount(),
							 														vouchersId);
					
					LogCvt.debug("得到订单详情:"+JSON.toJSONString(activeOrderDetail));
					activeOrderDetailList.add(activeOrderDetail);
				}
				
				// 累计红包总金额
				foldVouMoney = Arith.add(foldVouMoney, vouMoney);
				
			}
			
			return activeOrderDetailList;
		
		}catch(Exception ex){
			LogCvt.error("组装 代金券/红包订单详情列表 异常:", ex);
			return null;
		}
	}
	
	/**
	 * 得到主订单信息的详情 - 代金券/红包
	 * */
	private static String getOrderDetail_vou(List<ActiveOrderDetail> activeOrderDetailList){
		List<Map<String, Object>> productMapList = new ArrayList<Map<String, Object>>();
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			Map<String, Object> productMap = new HashMap<String, Object>();
			productMap.put(ActiveUtils.ACT_TYPE, ActiveType.vouchers.getCode());
			productMap.put(ActiveUtils.ACT_ID, activeOrderDetail.getActiveId());
			productMap.put(ActiveUtils.PRO_ID, activeOrderDetail.getProductId());
			productMap.put(ActiveUtils.PRO_NAME, activeOrderDetail.getProductName());
			productMap.put(ActiveUtils.GEN_PRICE, activeOrderDetail.getProductPrice());
			productMap.put(ActiveUtils.VIP_PRICE, activeOrderDetail.getProductVipPrice());
			productMap.put(ActiveUtils.GEN_DIS_MONEY, activeOrderDetail.getNormalPriceMarket()); // 平摊的代金券/红包金额
			productMap.put(ActiveUtils.GEN_DIS_COUNT, activeOrderDetail.getNormalPriceCount());
			productMap.put(ActiveUtils.VIP_DIS_MONEY, activeOrderDetail.getVipPriceMarket()); // 平摊的代金券/红包金额
			productMap.put(ActiveUtils.VIP_DIS_COUNT, activeOrderDetail.getVipPriceCount());
			productMap.put(ActiveUtils.VOU_ID, activeOrderDetail.getTicketId()); // 红包/代金券id
			productMapList.add(productMap);
		}
		String detail = JSON.toJSONString(productMapList);
		LogCvt.debug("回填代金券/红包detail:"+detail);
		return detail;
	}
	
	/**
	 * 得到主订单信息的详情
	 * <br>
	 * 在以前的基础上再拼装代金券/红包金额
	 * */
	private static String getOrderDetail_fold_vou(String detail, List<ActiveOrderDetail> activeOrderDetailList){
		List<Map<String, Object>> productMapList = (List<Map<String, Object>>)JSON.parse(detail);
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			Map<String, Object> productMap = new HashMap<String, Object>();
			productMap.put(ActiveUtils.ACT_TYPE, ActiveType.vouchers.getCode());
			productMap.put(ActiveUtils.ACT_ID, activeOrderDetail.getActiveId());
			productMap.put(ActiveUtils.PRO_ID, activeOrderDetail.getProductId());
			productMap.put(ActiveUtils.PRO_NAME, activeOrderDetail.getProductName());
			productMap.put(ActiveUtils.GEN_PRICE, activeOrderDetail.getProductPrice());
			productMap.put(ActiveUtils.VIP_PRICE, activeOrderDetail.getProductVipPrice());
			productMap.put(ActiveUtils.GEN_DIS_MONEY, activeOrderDetail.getNormalPriceMarket()); // 平摊的代金券/红包金额
			productMap.put(ActiveUtils.GEN_DIS_COUNT, activeOrderDetail.getNormalPriceCount());
			productMap.put(ActiveUtils.VIP_DIS_MONEY, activeOrderDetail.getVipPriceMarket()); // 平摊的代金券/红包金额
			productMap.put(ActiveUtils.VIP_DIS_COUNT, activeOrderDetail.getVipPriceCount());
			productMap.put(ActiveUtils.VOU_ID, activeOrderDetail.getTicketId()); // 红包/代金券id
			productMapList.add(productMap);
		}
		detail = JSON.toJSONString(productMapList);
		LogCvt.debug("回填叠加detail:"+detail);
		return detail;
	}
	
	/**
	 * 从子订单列表里取出所有子订单商品列表
	 * <br>
	 * 每个子订单商品都设置一个子订单id - 拼装【营销订单详情】使用
	 * */ 
	private static List<MarketSubOrderProduct> getMarketSubOrderProductList(List<MarketSubOrder> marketSubOrderList){
		List<MarketSubOrderProduct> allSubOrderProductList = new ArrayList<MarketSubOrderProduct>();
		for( MarketSubOrder marketSubOrder : marketSubOrderList ){
			String subOrderId = marketSubOrder.getSubOrderId();
			List<MarketSubOrderProduct> marketSubOrderProductList = marketSubOrder.getMarketSubOrderProductList();
			for( MarketSubOrderProduct marketSubOrderProduct : marketSubOrderProductList ){
				marketSubOrderProduct.setSubOrderId(subOrderId);
			}
			allSubOrderProductList.addAll(marketSubOrderProductList);
		}
		return allSubOrderProductList;
	}
	
	/** 
	 * 过滤重复的活动id
	 */
	private static List<String> filterRepeat3(List<MarketSubOrderProduct> subOrderProductList){
		List<String> activeIdList = new ArrayList<String>();
		for( MarketSubOrderProduct orderProduct : subOrderProductList ){
			String ativeId = orderProduct.getActiveId();
			if( ativeId != null && !"".equals(ativeId) && !activeIdList.contains(ativeId) ){
				activeIdList.add(ativeId);
			}
		}
		return activeIdList;
	}
	
	/**
	 * 得到有活动的营销订单详情
	 * */
	private static List<ActiveOrderDetail> calcExistActiveSubOrderProduct(List<String> activeIdList, List<MarketSubOrderProduct> allSubOrderProductList, String clientId, String orderId){
		
		List<ActiveOrderDetail> activeOrderDetailList = new ArrayList<ActiveOrderDetail>();
		
		for( String activeId : activeIdList ){
			// 找出同活动的子订单商品
			List<MarketSubOrderProduct> sameActiveSubOrderProductList = findSameActiveSubOrderProduct(activeId, allSubOrderProductList);
			// 按商品名称 降序排列
			Collections.sort(sameActiveSubOrderProductList);
			LogCvt.debug("订单内同活动 "+activeId+" 的商品:"+JSON.toJSONString(sameActiveSubOrderProductList));
			List<ActiveOrderDetail> sameActiveOrderDetailList = calcSameActiveSubOrderProduct(activeId, sameActiveSubOrderProductList, clientId, orderId);
			LogCvt.debug("订单内同活动 "+activeId+" 的订单详情列表:"+JSON.toJSONString(sameActiveOrderDetailList));
			activeOrderDetailList.addAll(sameActiveOrderDetailList);
		}
		
		return activeOrderDetailList;
	}
	
	/**
	 * 得到没活动的营销订单详情
	 * */
	private static List<ActiveOrderDetail> calcNotExistActiveSubOrderProduct(List<MarketSubOrderProduct> allSubOrderProductList, String clientId, String orderId){
		
		List<ActiveOrderDetail> activeOrderDetailList = new ArrayList<ActiveOrderDetail>();
		
		Double zero = 0.0;
		for( MarketSubOrderProduct subOrderProduct : allSubOrderProductList ){
			
			String activeId = subOrderProduct.getActiveId();
			if( activeId == null || "".equals(activeId) ){
				// 拼装营销活动订单详情
				ActiveOrderDetail activeOrderDetail = assembledActiveOrderDetail(clientId, 
									 											 orderId, 
									 											 null, 
									 											 null, 
									 											 null, 
									 											 subOrderProduct.getSubOrderId(), 
									 											 subOrderProduct.getProductId(), 
									 											 subOrderProduct.getProductName(),
									 											 subOrderProduct.getGeneralPrice(),
									 											 subOrderProduct.getVipPrice(),
									 											 zero,
									 											 subOrderProduct.getGeneralCount(),
									 											 zero,
									 											 subOrderProduct.getVipCount(),
									 											 null);
				activeOrderDetailList.add(activeOrderDetail);
			}
			
			
		}
		
		return activeOrderDetailList;
	}
	
	/**
	 * 得到代金券/红包信息列表 - 根据代金券/红包id列表
	 * <br>
	 * 根据代金券/红包所属活动的到期时间由近及远排序
	 * */
	private static List<VouchersInfo> getVouchersInfoList(List<String> vouchersIds){
		
		try{
			List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();
			
			for( String vouchersId : vouchersIds ){
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setVouchersId(vouchersId);
				vouchersInfo = vouchersInfoHandler.findOneVouchersInfo(vouchersInfo);
				vouchersInfoList.add(vouchersInfo);
			}
			
			Collections.sort(vouchersInfoList);
			
			return vouchersInfoList;
		}catch(Exception e){
			LogCvt.error("得到代金券/红包信息 异常", e);
			return null;
		}
		
	}
	
	/**
	 * 计算总价
	 * */
	private static Double calcProductsSumMoney(List<MarketSubOrderProduct> subOrderProductList){
		Double sumMoney = 0.0;
		for( MarketSubOrderProduct marketSubOrderProduct : subOrderProductList ){
			// 普通单价 * 普通数量
			sumMoney = Arith.add(sumMoney, Arith.mul2(marketSubOrderProduct.getGeneralPrice(), marketSubOrderProduct.getGeneralCount()));
			// vip单价 * vip数量
			sumMoney = Arith.add(sumMoney, Arith.mul2(marketSubOrderProduct.getVipPrice(), marketSubOrderProduct.getVipCount()));
		}
		return sumMoney;
	}
	
	/**
	 * 拼装营销活动订单详情
	 * */
	private static ActiveOrderDetail assembledActiveOrderDetail(String clientId, 
																String orderId, 
																String orderMarketType, 
																String activeId,
																String type,
																String subOrderId,
																String productId,
																String productName,
																Double productPrice,
																Double productVipPrice,
																Double normalPriceMarket,
																Integer normalPriceCount,
																Double vipPriceMarket,
																Integer vipPriceCount,
																String ticketId){
		
		Date now = new Date();
		
		ActiveOrderDetail activeOrderDetail = new ActiveOrderDetail();
		activeOrderDetail.setClientId(clientId);
		activeOrderDetail.setCreateTime(now);
		activeOrderDetail.setUpdateTime(now);
		activeOrderDetail.setOrderId(orderId);
		activeOrderDetail.setOrderMarketType(orderMarketType);
		activeOrderDetail.setActiveId(activeId);
		activeOrderDetail.setType(type);
		activeOrderDetail.setSubOrderId(subOrderId);
		activeOrderDetail.setProductId(productId);
		activeOrderDetail.setProductName(productName);
		activeOrderDetail.setProductPrice(Arith.mul(productPrice, 1000));
		activeOrderDetail.setProductVipPrice(Arith.mul(productVipPrice, 1000));
		activeOrderDetail.setOrgNormalPriceMarket(Arith.mul(normalPriceMarket, 1000));
		activeOrderDetail.setOrgNormalPriceCount(normalPriceCount);
		activeOrderDetail.setNormalPriceMarket(Arith.mul(normalPriceMarket, 1000));
		activeOrderDetail.setNormalPriceCount(normalPriceCount);
		activeOrderDetail.setOrgVipPriceMarket(Arith.mul(vipPriceMarket, 1000));
		activeOrderDetail.setOrgVipPriceCount(vipPriceCount);
		activeOrderDetail.setVipPriceMarket(Arith.mul(vipPriceMarket, 1000));
		activeOrderDetail.setVipPriceCount(vipPriceCount);
		activeOrderDetail.setTicketId(ticketId);
		
		return activeOrderDetail;
	}
	
	/**
	 * 找出同活动的子订单商品
	 * */
	private static List<MarketSubOrderProduct> findSameActiveSubOrderProduct(String activeId, List<MarketSubOrderProduct> allSubOrderProductList){
	
		List<MarketSubOrderProduct> sameActiveSubOrderProductList = new ArrayList<MarketSubOrderProduct>();
		for( MarketSubOrderProduct marketSubOrderProduct : allSubOrderProductList ){
			if( activeId.equals(marketSubOrderProduct.getActiveId()) ){
				sameActiveSubOrderProductList.add(marketSubOrderProduct);
			}
		}
		return sameActiveSubOrderProductList;
	}
	
	/**
	 * 计算同活动的子订单商品 - 得到营销订单详情
	 * */
	private static List<ActiveOrderDetail> calcSameActiveSubOrderProduct(String activeId, List<MarketSubOrderProduct> sameActiveSubOrderProductList, String clientId, String orderId){
		
		List<ActiveOrderDetail> activeOrderDetailList = new ArrayList<ActiveOrderDetail>();
		
		// 1 获取缓存中的活动细则
		Map<String, String> active = RedisCommon.readFullCutActive(clientId, activeId);
		String activeType = active.get("type"); // 活动类型
		Long retMoney = Long.parseLong(active.get("retMoney"));
		Double cutMoney = Arith.div((double)retMoney, 1000, 2); // 满减总金额
		String orderMarketType = active.get("isPrePay"); // 0-减 1-送(支付前/后)
		
		// 计算相同活动子订单商品的总价
		Double sumMoney = calcProductsSumMoney(sameActiveSubOrderProductList);
		LogCvt.debug("活动 "+activeId+" 满减金额:"+cutMoney+" - 同活动商品总价:"+sumMoney);
		
		// 商品不止一种
		if( sameActiveSubOrderProductList.size() > 1 ){
			
			// 已经分摊的满减额
			Double alreadyCotMoney = 0.0;
			for( int i = 0; i < sameActiveSubOrderProductList.size(); i++ ){
				
				MarketSubOrderProduct subOrderProduct = sameActiveSubOrderProductList.get(i);
				// 单种商品总价
				Double productMoney = 0.0;
				// 单种商品普通总价
				Double productGeneralMoney = Arith.mul2(subOrderProduct.getGeneralPrice(), subOrderProduct.getGeneralCount());
				// 单种商品vip总价
				Double productVipMoney = Arith.mul2(subOrderProduct.getVipPrice(), subOrderProduct.getVipCount());
				productMoney = Arith.add(productMoney, productGeneralMoney);
				productMoney = Arith.add(productMoney, productVipMoney);
				
				// 计算同活动不同商品的占比满减金额 
				Double singleCutMoney = ActiveUtils.calcSameActiveShareCutMoney(productMoney, sumMoney, cutMoney);
				// 商品的占比满减金额 + 已经分摊的满减额 >= 满减金额
				if( Arith.add(singleCutMoney, alreadyCotMoney) >= cutMoney ){
					
					// 商品的占比满减金额 = 满减金额 - 已经分摊的满减额
					singleCutMoney = Arith.sub(cutMoney, alreadyCotMoney);
				}
				
				// 记录商品分摊的满减金额
				subOrderProduct.setCutMoney(singleCutMoney);
				
				// 累加 已经分摊的满减额
				alreadyCotMoney = Arith.add(alreadyCotMoney, singleCutMoney);
				
				// 单种商品普通满减金额 = 单种商品普通总价 / 单种商品总价 * 单种商品满减金额
				Double generalCutMoney = ActiveUtils.calcSameActiveShareCutMoney(productGeneralMoney, productMoney, singleCutMoney);
				// 单种商品vip满减金额 = 单种商品满减金额 - 单种商品普通满减金额
				Double vipCutMoney = Arith.sub(singleCutMoney, generalCutMoney);
				
				// 拼装营销活动订单详情
				ActiveOrderDetail activeOrderDetail = assembledActiveOrderDetail(clientId, 
																				 orderId, 
																				 orderMarketType, 
																				 activeId, 
																				 activeType, 
																				 subOrderProduct.getSubOrderId(), 
																				 subOrderProduct.getProductId(), 
																				 subOrderProduct.getProductName(),
																				 subOrderProduct.getGeneralPrice(),
																				 subOrderProduct.getVipPrice(),
																				 generalCutMoney,
																				 subOrderProduct.getGeneralCount(),
																				 vipCutMoney,
																				 subOrderProduct.getVipCount(),
																				 activeId);
				LogCvt.debug("得到订单详情:"+JSON.toJSONString(activeOrderDetail));
				activeOrderDetailList.add(activeOrderDetail);
			}
			
		}else{ // 相同活动的商品种类只有一个
			
			MarketSubOrderProduct subOrderProduct = sameActiveSubOrderProductList.get(0);
			LogCvt.debug("计算订单商品:"+JSON.toJSONString(subOrderProduct));
			// 第一条商品总价
			Double productMoney = 0.0;
			// 第一条商品普通总价
			Double productGeneralMoney = Arith.mul2(subOrderProduct.getGeneralPrice(), subOrderProduct.getGeneralCount());
			// 第一条商品vip总价
			Double productVipMoney = Arith.mul2(subOrderProduct.getVipPrice(), subOrderProduct.getVipCount());
			productMoney = Arith.add(productMoney, productGeneralMoney);
			productMoney = Arith.add(productMoney, productVipMoney);
			// 第一条商品普通满减金额 = 第一条商品普通总价 / 第一条商品总价 * 满减总金额
			Double generalCutMoney = Arith.round(Arith.div(productGeneralMoney, productMoney, 2) * cutMoney, 2);
			// 第一条商品vip满减金额 = 第一条商品满减金额 - 单种商品普通满减金额
			Double vipCutMoney = Arith.sub(cutMoney, generalCutMoney);
			
			// 记录商品分摊的满减金额
			subOrderProduct.setCutMoney(cutMoney);
			
			// 拼装营销活动订单详情
			ActiveOrderDetail activeOrderDetail = assembledActiveOrderDetail(clientId, 
					 														 orderId, 
					 														 orderMarketType, 
					 														 activeId, 
					 														 activeType, 
					 														 subOrderProduct.getSubOrderId(), 
					 														 subOrderProduct.getProductId(), 
					 														 subOrderProduct.getProductName(),
					 														 subOrderProduct.getGeneralPrice(),
					 														 subOrderProduct.getVipPrice(),
					 														 generalCutMoney,
					 														 subOrderProduct.getGeneralCount(),
					 														 vipCutMoney,
					 														 subOrderProduct.getVipCount(),
					 														activeId);
			LogCvt.debug("得到订单详情:"+JSON.toJSONString(activeOrderDetail));
			activeOrderDetailList.add(activeOrderDetail);
		}
		
		return activeOrderDetailList;
	}
}
