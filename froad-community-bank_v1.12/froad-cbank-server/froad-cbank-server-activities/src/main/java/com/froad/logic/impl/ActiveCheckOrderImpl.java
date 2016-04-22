package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.RegisterRedis;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveLimitType;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.enums.VouchersStatus;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveCheckOrder;
import com.froad.po.CheckOrderReq;
import com.froad.po.CheckOrderRes;
import com.froad.po.FullGiveActive;
import com.froad.po.OrderProduct;
import com.froad.po.Result;
import com.froad.po.VouchersInfo;
import com.froad.util.ActiveUtils;
import com.froad.util.Arith;
import com.froad.util.DateUtil;

public class ActiveCheckOrderImpl implements ActiveCheckOrder {

	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	private static OrderMongoService orderMongoService = new OrderMongoServiceImpl();
	
	/**
     * 订单校验
     * <br>
     * CheckOrderRes.Result.resultCode = 0000 成功
     * CheckOrderRes.orderResList[n] 订单的各种满减金额
     * <br>
     * CheckOrderRes.Result.resultCode = 6666 没有传递活动
     * CheckOrderRes.orderResList = null
     * <br>
     * CheckOrderRes.Result.resultCode = 9999 校验失败
     * CheckOrderRes.Result.resultDesc 失败信息
     * <br>
     */
	@Override
	public CheckOrderRes checkOrder(CheckOrderReq checkOrderReq) {
		
		LogCvt.info("订单校验(计算) checkOrder 逻辑:");
		
		CheckOrderRes checkOrderRes = new CheckOrderRes();
		
		try{
			
			String reqId = checkOrderReq.getReqId();
			String clientId = checkOrderReq.getClientId();
			Long memberCode = checkOrderReq.getMemberCode();
			List<OrderProduct> orderProductList = checkOrderReq.getOrderProductList();
			
			checkOrderRes.setReqId(reqId);
			checkOrderRes.setClientId(clientId);
			checkOrderRes.setMemberCode(memberCode);
			
			/*********************************/
			/**   1 判断是否活动(满减活动和满赠活动)和是否有用红包(代金券)
			/**   2 有满减营销活动             订单校验 - 满减
			/**   2.1 如果校验有一点不通过则进行返回
			/**   2.2 有满赠营销活动        订单检验 - 满赠
			/**   3 有用红包(代金券)    订单校验 - 红包/代金券
			/**   3.1 如果校验有一点不通过则进行返回
			/**   4 减去活动购买资格
			/**   5 减去红包购买限额
			/*********************************/
			
			
			// 1 判断是否有活动(满减活动和满赠活动)和是否有用红包(代金券)
			List<String> activeIdList = filterRepeat2(orderProductList);
			List<String> activeIdGiveList = filterRepeat4(orderProductList);
			List<String> vouchersIds = checkOrderReq.getVouchersIds();
			if( ( activeIdList == null || activeIdList.isEmpty() || activeIdList.size() < 1 || ( activeIdList.size() == 1 && null == activeIdList.get(0) ) )
					&&
				( activeIdGiveList == null || activeIdGiveList.isEmpty() || activeIdGiveList.size() < 1 || ( activeIdGiveList.size() == 1 && null == activeIdGiveList.get(0) ) )	
					&&
				( vouchersIds == null || vouchersIds.isEmpty() || vouchersIds.size() < 1 || ( vouchersIds.size() == 1 && null == vouchersIds.get(0) ) ) ){
				LogCvt.debug("订单请求 "+reqId+" 中没有满减营销活动 并且 没有满赠营销活动 并且 没有使用红包(代金券)");
				checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "订单请求 "+reqId+" 中没有满减营销活动 并且 没有满赠营销活动 并且 没有使用红包(代金券)"));
				return checkOrderRes;
			}
			
			LogCvt.debug("订单请求 "+reqId+" 中包含的满减活动"+JSON.toJSONString(activeIdList));
			LogCvt.debug("订单请求 "+reqId+" 中包含的满赠活动"+JSON.toJSONString(activeIdGiveList));
			LogCvt.debug("订单请求 "+reqId+" 中包含的红包券码"+JSON.toJSONString(vouchersIds));
			// 没有满减活动
			if( activeIdList == null || activeIdList.isEmpty() || activeIdList.size() < 1 || ( activeIdList.size() == 1 && null == activeIdList.get(0) ) ){
				LogCvt.debug("订单请求 "+reqId+" 中没有满减营销活动");
				// 响应的 订单成交总金额 = 请求的 订单总金额
				checkOrderRes.setOrderDealMoney(checkOrderReq.getOrderMoney());
			}else{ // 有满减营销活动
				// 2 订单校验 - 满减
				// 如果校验有一点不通过则进行返回 - 通过后会在[订单成交总金额]上减去各活动的满减额
				checkOrderOfCut(checkOrderReq, activeIdList, checkOrderRes);
				if( !ResultCode.success.getCode().equals(checkOrderRes.getResult().getResultCode()) ){
					LogCvt.debug("订单请求 "+reqId+" 中有满减活动校验不通过");
					return checkOrderRes;
				}
			}
			
			// 没有满赠活动
			if( activeIdGiveList == null || activeIdGiveList.isEmpty() || activeIdGiveList.size() < 1 || ( activeIdGiveList.size() == 1 && null == activeIdGiveList.get(0) ) ){
				LogCvt.debug("订单请求 "+reqId+" 中没有满赠营销活动");
			}else{ // 有满赠营销活动
				// 2.2 订单检验 - 满赠
				// 如果校验有一点不通过则进行返回
				checkOrderOfGive(checkOrderReq, activeIdGiveList, checkOrderRes);
				if( !ResultCode.success.getCode().equals(checkOrderRes.getResult().getResultCode()) ){
					LogCvt.debug("订单请求 "+reqId+" 中有满赠活动校验不通过");
					return checkOrderRes;
				}
			}
			
			// 没有用红包(代金券)
			if( vouchersIds == null || vouchersIds.isEmpty() || vouchersIds.size() < 1 || ( vouchersIds.size() == 1 && null == vouchersIds.get(0) ) ){
				LogCvt.debug("订单请求 "+reqId+" 中没有使用红包(代金券)");
			}else{ // 有用红包(代金券)
				// 3 订单校验 - 红包/代金券
				// 如果校验有一点不通过则进行返回 - 通过后会在[订单成交总金额]上减去各红包/代金券的金额
				checkOrderOfVouchers(checkOrderReq, checkOrderRes);
				if( !ResultCode.success.getCode().equals(checkOrderRes.getResult().getResultCode()) ){
					LogCvt.debug("订单请求 "+reqId+" 中有红包/代金券校验不通过");
					return checkOrderRes;
				}
			}
			
			// 4 减去活动购买限额
			if( activeIdList != null && activeIdList.size() > 0 ){
				LogCvt.debug("订单请求 "+reqId+" 中减去活动购买限额");
				cutBackActiveLimit(clientId, memberCode, activeIdList);
			}
			
			
			// 5 减去红包购买限额
			if( vouchersIds != null && vouchersIds.size() > 0 ){
				LogCvt.debug("订单请求 "+reqId+" 中减去红包购买限额");
				cutBackVouchersLimit(clientId, memberCode, checkOrderReq.getVouchersActiveIds());
				
				// 6 修改红包状态- 支付中
				updateVouchersPayIng(vouchersIds, "", memberCode);
			}
			
		}catch(Exception e){
			LogCvt.error("订单校验 异常", e);
			checkOrderRes.setResult(new Result(ResultCode.failed));
		}

		return checkOrderRes;
	}

	/** 
	 * 过滤重复的活动id
	 */
	private static List<String> filterRepeat2(List<OrderProduct> orderProductList){
		List<String> activeIdList = new ArrayList<String>();
		for( OrderProduct orderProduct : orderProductList ){
			String ativeId = orderProduct.getActiveId();
			if( ativeId!= null && !"".equals(ativeId) && !activeIdList.contains(ativeId) ){
				activeIdList.add(ativeId);
			}
		}
		return activeIdList;
	}
	
	/** 
	 * 过滤重复的活动id
	 */
	private static List<String> filterRepeat4(List<OrderProduct> orderProductList){
		List<String> activeIdGiveList = new ArrayList<String>();
		for( OrderProduct orderProduct : orderProductList ){
			String ativeId = orderProduct.getActiveIdGive();
			if( ativeId!= null && !"".equals(ativeId) && !activeIdGiveList.contains(ativeId) ){
				activeIdGiveList.add(ativeId);
			}
		}
		return activeIdGiveList;
	}
	
	/** 
	 * 订单校验 - 满减
	 */
	private static void checkOrderOfCut(CheckOrderReq checkOrderReq, List<String> activeIdList, CheckOrderRes checkOrderRes){
		
		/*********************************/
		/**   1 验证所有活动 1-是否正常 2-限制是否超出 3-同活动商品总额是否满额
		/**   2 如果验证有一点不通过则进行返回
		/*********************************/
		
		String reqId = checkOrderReq.getReqId();
		String orderMoney = checkOrderReq.getOrderMoney().toString();
		
		// 2 验证所有活动 1-是否正常 2-限制是否超出 3-同活动商品总额是否满额
		// 验证时会循环所有活动-顺便减去各活动的满减额(就是实际成交金额)
		Result result = checkAllActives(activeIdList, checkOrderReq, orderMoney);
		if( !ResultCode.success.getCode().equals(result.getResultCode()) ){
			LogCvt.debug("订单请求 "+reqId+" 满减活动校验不通过");
			checkOrderRes.setResult(result);
		}else{
			LogCvt.debug("订单请求 "+reqId+" 满减活动校验通过");
			checkOrderRes.setResult(new Result(ResultCode.success)); // 操作成功
			checkOrderRes.setOrderDealMoney(Double.parseDouble(result.getResultDesc()));
		}
		
	}
	
	/** 
	 * 订单校验 - 满赠
	 */
	private static void checkOrderOfGive(CheckOrderReq checkOrderReq, List<String> activeIdGiveList, CheckOrderRes checkOrderRes){
	
		/*********************************/
		/**   1 验证所有活动 1-是否正常 2-限制是否超出 3-同活动商品总额是否满额(减去满减额)
		/**   2 如果验证有一点不通过则进行返回
		/*********************************/
		
		String reqId = checkOrderReq.getReqId();
		
		Result result = checkAllActivesGive(activeIdGiveList, checkOrderReq, checkOrderRes);
		if( !ResultCode.success.getCode().equals(result.getResultCode()) ){
			LogCvt.debug("订单请求 "+reqId+" 满赠活动校验不通过");
			checkOrderRes.setResult(result);
		}else{
			LogCvt.debug("订单请求 "+reqId+" 满赠活动校验通过");
			checkOrderRes.setResult(new Result(ResultCode.success)); // 操作成功
		}
	}
	
	/** 
	 * 订单校验 - 红包/代金券
	 */
	private static void checkOrderOfVouchers(CheckOrderReq checkOrderReq, CheckOrderRes checkOrderRes){
		
		/*********************************/
		/**   1 验证所有红包/代金券
		 *    1.1 券码存在
		 *    1.2 红包不属于会员
		 *    1.3 券状态是未使用
		 *    1.4 券所属代金券活动(正常读取、状态正常、已开始、未过期)
		 *    1.4.2 支持面对面订单判断
		 *    1.4.3 只支持新用户
		 *    1.5 代金券活动是支持其它促销 - 判断订单参与的营销活动是否在支付范围内
		 *    1.6 代金券活动使用范围不限定 - 判断商品总额是否满足
		 *    1.7 代金券个人限制判断
		 *    1.8 代金券时间段限制判断
		 */
		/**   2 如果验证有一点不通过则进行返回
		/*********************************/
		
		String clientId = checkOrderReq.getClientId();
		Long memberCode = checkOrderReq.getMemberCode();
		List<String> vouchersIds = checkOrderReq.getVouchersIds();
		List<String> sustainActiveIds = checkOrderReq.getSustainActiveIds();
		List<String> vouchersActiveIds = new ArrayList<String>();
		
		Boolean isF2FOrder = checkOrderReq.getIsF2FOrder();
		
		try{
			
			// 所有红包/代金券的总额
			Double sumVouchersMoney = 0.0;
			
			for( String vouchersId : vouchersIds ){
				
				// 1.1 券码存在
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setVouchersId(vouchersId);
				vouchersInfo = vouchersInfoHandler.findOneVouchersInfo(vouchersInfo);
				LogCvt.debug("校验券码 内容:"+JSON.toJSONString(vouchersInfo));
				if( vouchersInfo == null || vouchersInfo.getId() <= 0 ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 不存在");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 不存在"));
					return;
				}
				// 1.2 红包不属于会员
				Long vouchersMCode = vouchersInfo.getMemberCode();
				LogCvt.debug("vouchersMCode != null:"+(vouchersMCode != null)+" - vouchersMCode != 0:"+(vouchersMCode != 0)+" - !vouchersMCode.equals(memberCode):"+(!vouchersMCode.equals(memberCode)));
				if( vouchersMCode != null && vouchersMCode!= 0 && !vouchersMCode.equals(memberCode) ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 不属于 "+memberCode+" 所有");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 不属于本人所有"));
					return;
				}
				// 1.3 券状态是未使用
				String status = vouchersInfo.getStatus();
				if( !VouchersStatus.init.getCode().equals(status) ){
//					LogCvt.debug("代金券码/红包 "+vouchersId+" 已使用");
//					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包 "+vouchersId+" 已使用"));
//					return;
					// TODO 2016-07-01 00:00:00 放开上面的注释 去掉下面的代码
					int number = VouchersRedis.getVouchersUseNumber(vouchersId);
					if( number == 0 ){
						LogCvt.info("此红包券 "+vouchersId+" 已经被使用");
						checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 已经被使用"));
						return;
					}else if( number < 0){
						LogCvt.info("此红包券 "+vouchersId+" 已使用次数读取失败");
						checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 已使用次数读取失败"));
						return;
					}else if( number != 999999 ){
						Set<String> users = VouchersRedis.getVouchersUsers(vouchersId);
						if( users != null ){
							if( users.contains(memberCode.toString()) ){
								LogCvt.info("此红包券已被该用户使用");
								checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 此红包券已被该用户使用"));
								return;
							}
						}
					}else if( number == 999999 ){
						LogCvt.info("此红包券 "+vouchersId+" 已经被使用");
						checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "券码 "+vouchersId+" 已经被使用"));
						return;
					}
				}
				// 1.4 券所属代金券活动(正常读取、状态正常、已开始、未过期)
				String activeId = vouchersInfo.getActiveId();
				Map<String, String> active = VouchersRedis.readVouchersActive(clientId, activeId);
				LogCvt.debug("代金券码/红包 "+vouchersId+" 所属活动 "+activeId+" 明细:"+JSON.toJSONString(active));
				if( active == null || active.isEmpty() ){
					LogCvt.debug("代金券码/红包所属活动 "+activeId+" 没有明细");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动明细为空,活动无效"));
					return;
				}
				// 1.4.2 支持面对面订单判断
				if( isF2FOrder ){
					String isFtof = active.get("isFtof");
					if( "0".equals(isFtof) ){
						LogCvt.debug("代金券码/红包所属活动 "+activeId+" 不支持面对面支付");
						checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动不支持面对面支付"));
						return;
					}
				}
				// 1.4.3 只支持新用户
				String isOnlyNewUsers = active.get("isOnlyNewUsers");
				if("1".equals(isOnlyNewUsers)) {
					//调用 
					long userRegisterTime = VouchersRunLogicImpl.getUserRegisterDate(memberCode, clientId);
					String expireStartTime = active.get("expireStartTime");
					String expireEndTime = active.get("expireEndTime");
					long startTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime).getTime();
					long endTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime();
					if( userRegisterTime < startTime || userRegisterTime > endTime ){
						LogCvt.debug("代金券码/红包所属活动 "+activeId+" 仅能用于新注册用户使用");
						checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动仅能用于新注册用户使用"));
						return ;
					}
				}
				String readActiveResult = active.get("result");
				// 失效
				if( ResultCode.market_active_rule_invalid.getCode().equals(readActiveResult) ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 所属活动 "+activeId+" 已经失效");
					// TODO 如果使用限制范围不是"不限制" 则删除所属的对应redis
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动已经失效"));
					return;
				}
				// 初始化活动失败 或 读取活动失败
				if( ResultCode.market_active_rule_init_fail.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 所属活动 "+activeId+" 初始化失败或读取失败");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动初始化失败或读取失败"));
					return;
				}
				
				// 活动还未开始生效
				if( ResultCode.market_active_rule_no_start.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 所属活动 "+activeId+" 活动还未开始生效");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动还未开始生效"));
					return;
				}
				
				// 不是0
				if( !"0".equals(readActiveResult) ){
					LogCvt.debug("代金券码/红包 "+vouchersId+" 所属活动 "+activeId+" 状态非正常");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动状态非正常"));
					return;
				}
				
				// 1.5 代金券活动是支持其它促销 - 判断订单参与的营销活动是否在支付范围内
				String isOtherActive = active.get("isOtherActive");
				if( "1".equals(isOtherActive) ){
					Set<String> activeIdSustains = VouchersRedis.getSustainActiveIds(clientId, activeId);
					LogCvt.debug("代金券码/红包所属活动支持其他促销,支持促销活动列表:"+activeIdSustains);
					if( activeIdSustains == null || activeIdSustains.isEmpty() || activeIdSustains.size() < 1 ){
						LogCvt.debug("代金券码/红包所属活动支持其他促销,支持列表为空");
					}else{
						LogCvt.debug("订单中参与的促销活动列表:"+sustainActiveIds);
						if( sustainActiveIds != null && !sustainActiveIds.isEmpty() && sustainActiveIds.size() > 0 ){
							for( String sustainActiveId : sustainActiveIds ){
								// 订单中参与的促销活动不在支持的促销活动以内
								if( !activeIdSustains.contains(sustainActiveId) ){
									LogCvt.debug("订单中参与的促销活动 "+sustainActiveId+" 不在支持的促销活动以内");
									checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "订单中参与的促销活动不在支持的促销活动以内"));
									return;
								}
							}
						}
					}
				}
				
				// 1.6 代金券活动使用范围不限定 - 判断商品总额是否满足
				String limitType = active.get("limitType");
				Double orderMinMoney = Double.parseDouble(active.get("orderMinMoney"));
				if( ActiveLimitType.Notlimited.getCode().equals(limitType) ){
					if( orderMinMoney > 0 ){
						// TODO 订单最小限额暂时不会大于0,后续有改变后再写判断实现
						// 与订单中的所有商品总额进行比较
					}
				}else{ // 范围限定
					if( orderMinMoney > 0 ){
						// TODO 订单最小限额暂时不会大于0,后续有改变后再写判断实现
						// 与订单中的红包使用范围内商品的商品总额比较
					}
				}
				
				// 1.7 代金券个人限制判断
				boolean checkResult = VouchersRedis.checkPersonLimit(clientId, activeId, memberCode);
				LogCvt.debug("活动 "+activeId+" 个人限制:"+checkResult);
				if( !checkResult ){
					LogCvt.debug("代金券码/红包所属活动 "+activeId+" 个人限制已满");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动 个人限制已满"));
					return;
				}
				
				// 1.8 代金券时间段限制判断
				checkResult = VouchersRedis.checkGlobalLimit(clientId, activeId, "");
				LogCvt.debug("活动 "+activeId+" 时间段限制:"+checkResult);
				if( !checkResult ){
					LogCvt.debug("该券码/红包所属活动 "+activeId+" 当前使用额已满");
					checkOrderRes.setResult(new Result(ResultCode.failed.getCode(), "代金券码/红包所属活动 当前使用额已满"));
					return;
				}
				
				vouchersActiveIds.add(activeId);
				Double vouchersMoney = Arith.div(vouchersInfo.getVouchersMoney(), 1000, 2);
				LogCvt.debug("红包 "+vouchersId+" 金额 "+vouchersMoney);
				sumVouchersMoney = Arith.add(sumVouchersMoney, vouchersMoney);
			}
			
			checkOrderReq.setVouchersActiveIds(vouchersActiveIds);
			
			// 减去累加的代金券(红包)的钱
			Double orderDealMoney = checkOrderRes.getOrderDealMoney(); // 订单成交总金额
			LogCvt.debug("现成交金额 "+orderDealMoney+" 红包总额 "+sumVouchersMoney);
			// 订单成交总金额 <= 所有红包/代金券的总额 --- 不够减
			if( orderDealMoney <= sumVouchersMoney ){
				checkOrderRes.setOrderDealMoney(0.0);
			}else{ // 订单成交总金额 > 所有红包/代金券的总额 --- 够减
				checkOrderRes.setOrderDealMoney(Arith.sub(orderDealMoney, sumVouchersMoney));
			}
			
			checkOrderRes.setResult(new Result(ResultCode.success)); // 操作成功
			
		}catch(Exception ex){
			LogCvt.error("订单校验 - 红包/代金券 异常", ex);
			
		}
		
	}
	
	/**
	 * 减去活动限额
	 * */
	private static void	cutBackActiveLimit(String clientId, Long memberCode, List<String> activeIdList){
		
		/*********************************/
		/**   1 减 - 个人限额  */
		/**   2 减 - 活动时间段限额        */
		/**   3 减 - 活动全局限额        */
		/*********************************/
		
		try{
			
			int cutResult = -1;
			for( String activeId : activeIdList ){
				
				LogCvt.debug("活动 "+activeId+" 删减资格");
				
				// 1 减 - 个人限额
				if( activeId.indexOf(ActiveIdCode.MJ.getCode()) > 0 ){
					cutResult = RedisCommon.cutPersonLimit(clientId, activeId, memberCode);
					LogCvt.debug("活动 "+activeId+" 删减个人限额结果 "+(cutResult==0));
				}
				
				cutResult = -1;
				// 2 减 - 活动时间段限额
				if( activeId.indexOf(ActiveIdCode.MJ.getCode()) > 0 ){
					cutResult = RedisCommon.cutGlobalLimit(clientId, activeId);
				}else if( activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0 ){
					cutResult = RegisterRedis.cutGlobalLimit(clientId, activeId);
				}
				LogCvt.debug("活动 "+activeId+" 删减时间段限额结果 "+(cutResult==0));
				
				// 3 减 - 活动全局限额
				if( activeId.indexOf(ActiveIdCode.MJ.getCode()) > 0 ){
					cutResult = RedisCommon.cutBaseGlobalLimit(clientId, activeId);
				}else if( activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0 ){
					cutResult = RegisterRedis.cutBaseGlobalLimit(clientId, activeId);
				}
				
				LogCvt.debug("活动 "+activeId+" 删减全局限额结果 "+(cutResult==0));
			}
			
		}catch(Exception ex){
			LogCvt.error("减去活动限额 异常:", ex);
		}
	}
	
	/**
	 * 减去红包购买限额
	 * */
	private static void cutBackVouchersLimit(String clientId, Long memberCode, List<String> vouchersActiveIds){
		
		try{
			
			for( String activeId : vouchersActiveIds ){
				// 减 - 个人限额
				int cutResult = VouchersRedis.cutPersonLimit(clientId, activeId, memberCode);
				LogCvt.debug("代金券(红包)所属活动 "+activeId+" 删减个人 "+memberCode+" 限额结果 "+(cutResult==0));
				// 减 - 时间段限额
				cutResult = VouchersRedis.cutGlobalLimit(clientId, activeId);
				LogCvt.debug("代金券(红包)所属活动 "+activeId+" 删减时间段限额结果 "+(cutResult==0));
			}
			
		}catch(Exception ex){
			LogCvt.error("减去红包购买限额 异常:", ex);
		}
	}
	
	/**
	 * 修改券状态-支付中
	 * */
	private static Result updateVouchersPayIng(List<String> vouchersIds, String orderId, Long menberCode){
		try{
			Date now = new Date();
			List<VouchersInfo> vouchersInfoList = new ArrayList<VouchersInfo>();
			for( String vouchersId : vouchersIds ){
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setVouchersId(vouchersId);
				vouchersInfo.setUpdateTime(now);
				vouchersInfo.setStatus("1"); // 支付中
				vouchersInfo.setUseOrderId(orderId);
				vouchersInfo.setUserMemberCode(menberCode);
				vouchersInfo.setUseTime(now);
				vouchersInfoList.add(vouchersInfo);
			}
			Boolean result = vouchersInfoHandler.updateVouchersInfoPayIngBatch(vouchersInfoList);
			if( !result ){
				return new Result(ResultCode.failed.getCode(), "修改代金券/红包状态  失败");
			}
			return new Result(ResultCode.success);
		} catch (Exception e){
			LogCvt.error("修改代金券/红包状态  异常",e);
			return new Result(ResultCode.failed.getCode(), "修改代金券/红包状态  异常");
		}
	}
	
	/** 
	 * 校验所有活动 - 满减
	 * <br>
	 * 1-是否正常 2-限制是否超出 3-同活动商品总额是否满额
	 * <br>
	 * 减去各活动的满减额(就是实际成交金额)
	 */
	private static Result checkAllActives(List<String> activeIdList, CheckOrderReq checkOrderReq, String orderMoney){
		
		try{
			String clientId = checkOrderReq.getClientId();
			Long memberCode = checkOrderReq.getMemberCode();
			List<OrderProduct> orderProductList = checkOrderReq.getOrderProductList();
			
			for( String activeId : activeIdList ){
				
				LogCvt.debug("校验订单中满减活动 "+activeId);
				// 1 获取缓存中的满减活动细则
				Map<String, String> active = RedisCommon.readFullCutActive(clientId, activeId);
				LogCvt.debug("订单中满减活动 "+activeId+" 明细:"+JSON.toJSONString(active));
				if( active == null || active.isEmpty() ){
					LogCvt.debug("订单中满减活动 "+activeId+" 没有明细");
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeId+"]明细为空,活动无效");
				}
				String readActiveResult = active.get("result");
				String activeName = active.get("activeName");
				// 失效
				if( ResultCode.market_active_rule_invalid.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满减活动 "+activeId+" 已经失效");
					// 删除所属的两个对应redis
					try{
						List<String> ail = new ArrayList<String>();
						ail.add(activeId);
						RedisCommon.delOverdueActivitiesRedisByActiveId(ail);
					}catch(Exception e){
						LogCvt.error("活动 "+activeId+" 失效-删除所属的两个对应redis 异常",e);
					}
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeId+"]已经失效");
				}
				// 初始化满减活动失败 或 读取满减活动失败
				if( ResultCode.market_active_rule_init_fail.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满减活动 "+activeId+" 初始化失败或读取失败");
					return new Result(ResultCode.failed.getCode(), "读取满减活动["+activeName+"-"+activeId+"]失败");
				}
				
				// 满减活动还未开始生效
				if( ResultCode.market_active_rule_no_start.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满减活动 "+activeId+" 还未开始生效");
					return new Result(ResultCode.failed.getCode(), "读取满减活动["+activeName+"-"+activeId+"]还未开始生效");
				}
				
				String type = active.get("type");
				Boolean checkResult = null;
				// 2 判断满减活动的全局限制
				if( ActiveType.fullCut.getCode().equals(type) ){
					checkResult = RedisCommon.checkBaseGlobalLimit(clientId, activeId);
				}else if( ActiveType.firstPayment.getCode().equals(type) ){
					checkResult = RegisterRedis.checkBaseGlobalLimit(clientId, activeId);
				}
				LogCvt.debug("满减活动 "+activeId+" 全局限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					LogCvt.debug("订单中满减活动 "+activeId+"-"+activeName+" 全局限制已满");
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]的全局限制限制已满");
				}
				
				checkResult = null;
				// 3 判断满减活动的时间段限制
				if( ActiveType.fullCut.getCode().equals(type) ){
					checkResult = RedisCommon.checkGlobalLimit(clientId, activeId, null);
				}else if( ActiveType.firstPayment.getCode().equals(type) ){
					checkResult = RegisterRedis.checkGlobalLimit(clientId, activeId, null);
				}
				LogCvt.debug("满减活动 "+activeId+" 时间段限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					LogCvt.debug("订单中满减活动 "+activeId+"-"+activeName+" 时间段限制已满");
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]的时间段内限制限制已满");
				}
				
				checkResult = null;
				// 4 判断个人的限制
				if( ActiveType.fullCut.getCode().equals(type) ){
					checkResult = RedisCommon.checkPersonLimit(clientId, activeId, memberCode);
				}
				LogCvt.debug("满减活动 "+activeId+" 个人限制:"+checkResult);
				if( checkResult != null && !checkResult ){
					LogCvt.debug("订单中满减活动 "+activeId+"-"+activeName+" 个人限制已满");
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]个人当前限制已满");
				}
				
				// 4.2 判断首单满减
				if( (activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0) && type.equals(ActiveType.firstPayment.getCode()) ){
					// 新用户判断
					long userRegisterTime = VouchersRunLogicImpl.getUserRegisterDate(memberCode, clientId);
					String expireStartTime = active.get("expireStartTime");
					String expireEndTime = active.get("expireEndTime");
					long startTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime).getTime();
					long endTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime();
					if( userRegisterTime < startTime || userRegisterTime > endTime ){
						LogCvt.debug("订单中满减活动 "+activeId+" 仅能用于新注册用户使用");
						return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]仅能用于新注册用户使用");
					}
					// 首单判断
					boolean isExistO = orderMongoService.isExistOrder(memberCode, clientId);
					if( isExistO ){
						LogCvt.debug("订单中满减活动 "+activeId+" 仅能用于未产生交易用户使用");
						return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]仅能用于未产生交易用户使用");
					}
				}
				
				// 5 判断同满减活动的商品总金额是否超过最低限额
				// 找出相同满减活动的商品
				List<OrderProduct> sameActiveProductList = findSameOrderProduct(activeId, orderProductList, true);
				LogCvt.debug("订单中满减活动 "+activeId+"-"+activeName+" 所属商品:"+JSON.toJSONString(sameActiveProductList));
				Long minLimit = Long.parseLong(active.get("minLimit"));
				Double limitMoney = Arith.div((double)minLimit, 1000, 2);
				boolean isOut = isCountOutActiveMinLimit(activeId, limitMoney, sameActiveProductList, true);
				if( !isOut ){
					LogCvt.debug("订单中满减活动 "+activeId+"-"+activeName+" 商品总价未达到活动满减的最低限额");
					return new Result(ResultCode.failed.getCode(), "满减活动["+activeName+"-"+activeId+"]的商品总价未达到活动满减的最低限额");
				}

				Double cutMoney = Arith.div(Long.parseLong(active.get("retMoney")), 1000, 2); // 满减额
				orderMoney = ""+Arith.sub(Double.parseDouble(orderMoney), cutMoney); // 订单总金额 减去 此活动的满减额
				// 分摊满减额
				shareCutMoney(cutMoney, sameActiveProductList);
				LogCvt.debug("订单中满减活动["+activeName+"-"+activeId+"]通过校验");
			}
			
			return new Result(ResultCode.success.getCode(), orderMoney);
			
		}catch(Exception e){
			LogCvt.error("检查满减活动资质购买 异常:", e);
			return new Result(ResultCode.failed.getCode(), "检查满减活动资质购买 异常");
		}
	}
	
	/** 
	 * 校验所有活动 - 满赠
	 * <br>
	 * 1-是否正常 2-限制是否超出 3-同活动商品总额是否满额
	 */
	private static Result checkAllActivesGive(List<String> activeIdGiveList, CheckOrderReq checkOrderReq, CheckOrderRes checkOrderRes){
		
		try{
			String clientId = checkOrderReq.getClientId();
			Long memberCode = checkOrderReq.getMemberCode();
			List<OrderProduct> orderProductList = checkOrderReq.getOrderProductList();
		    List<FullGiveActive> fullGiveActiveList = new ArrayList<FullGiveActive>(); // 满赠活动响应 - 列表
			
			for( String activeId : activeIdGiveList ){
				LogCvt.debug("校验订单中满赠活动 "+activeId);
				// 1 获取缓存中的满赠活动细则
				Map<String, String> active = RedisCommon.readFullCutActive(clientId, activeId);
				LogCvt.debug("订单中满赠活动 "+activeId+" 明细:"+JSON.toJSONString(active));
				if( active == null || active.isEmpty() ){
					LogCvt.debug("订单中满赠活动 "+activeId+" 没有明细");
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeId+"]明细为空,活动无效");
				}
				
				String readActiveResult = active.get("result");
				String activeName = active.get("activeName");
				// 失效
				if( ResultCode.market_active_rule_invalid.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满赠活动 "+activeId+" 已经失效");
					// 删除所属的两个对应redis
					try{
						List<String> ail = new ArrayList<String>();
						ail.add(activeId);
						RedisCommon.delOverdueActivitiesRedisByActiveId(ail);
					}catch(Exception e){
						LogCvt.error("活动 "+activeId+" 失效-删除所属的两个对应redis 异常",e);
					}
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeId+"]已经失效");
				}
				// 初始化满赠活动失败 或 读取满赠活动失败
				if( ResultCode.market_active_rule_init_fail.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满赠活动 "+activeId+" 初始化失败或读取失败");
					return new Result(ResultCode.failed.getCode(), "读取满赠活动["+activeName+"-"+activeId+"]失败");
				}
				
				// 满赠活动还未开始生效
				if( ResultCode.market_active_rule_no_start.getCode().equals(readActiveResult) 
						|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
					LogCvt.debug("订单中满减赠活动 "+activeId+" 还未开始生效");
					return new Result(ResultCode.failed.getCode(), "读取满赠活动["+activeName+"-"+activeId+"]还未开始生效");
				}
				
				// 2 判断满赠活动的全局限制
				boolean checkResult = RedisCommon.checkBaseGlobalLimit(clientId, activeId);
				LogCvt.debug("满赠活动 "+activeId+" 全局限制:"+checkResult);
				if( !checkResult ){
					LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 全局限制已满");
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]的全局限制限制已满");
				}
				
				// 3 判断满赠活动的时间段限制
				checkResult = RedisCommon.checkGlobalLimit(clientId, activeId, null);
				LogCvt.debug("满赠活动 "+activeId+" 时间段限制:"+checkResult);
				if( !checkResult ){
					LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 时间段限制已满");
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]的时间段内限制限制已满");
				}
				
				// 4 判断个人的限制
				checkResult = RedisCommon.checkPersonLimit(clientId, activeId, memberCode);
				LogCvt.debug("满赠活动 "+activeId+" 个人限制:"+checkResult);
				if( !checkResult ){
					LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 个人限制已满");
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]个人当前限制已满");
				}
				
				// 5 判断同满赠活动的商品总金额是否超过最低限额
				// 找出相同满赠活动的商品
				List<OrderProduct> sameActiveProductList = findSameOrderProduct(activeId, orderProductList, false);
				LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 所属商品:"+JSON.toJSONString(sameActiveProductList));
				Long minLimit = Long.parseLong(active.get("minLimit"));
				Double limitMoney = Arith.div((double)minLimit, 1000, 2);
				boolean isOut = isCountOutActiveMinLimit(activeId, limitMoney, sameActiveProductList, false);
				if( !isOut ){
					LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 商品总价未达到活动满赠的最低限额");
					return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]的商品总价未达到活动满减的最低限额");
				}

				// 拼装满赠
				String prePayActiveId = active.get("prePayActiveId");
				String point = active.get("point");
				FullGiveActive fullGiveActive = new FullGiveActive();
				fullGiveActive.setActiveId(activeId);
				if( prePayActiveId != null && !"".equals(prePayActiveId) ){
					fullGiveActive.setActiveType(ActiveAwardType.vouchers.getCode());
					Map<String, String> vm = VouchersRedis.readVouchersActive(active.get("clientId"), prePayActiveId);
					String vMoney = vm.get("minMoney");
					if( vMoney != null && !"".equals(vMoney) ){
						fullGiveActive.setMonry(Arith.div(Double.parseDouble(vMoney), 1000, 2));
					}
				}else if( point != null && !"".equals(point) || Integer.parseInt(point) > 0 ){
					fullGiveActive.setActiveType(ActiveAwardType.unionIntegral.getCode());
					fullGiveActive.setMonry(Double.parseDouble(point));
				}
				fullGiveActiveList.add(fullGiveActive);
				
				LogCvt.debug("订单中满赠活动["+activeName+"-"+activeId+"]通过校验");
				
			}
			
			checkOrderRes.setFullGiveActiveList(fullGiveActiveList);
			return new Result(ResultCode.success);
			
		}catch(Exception e){
			LogCvt.error("检查满赠活动资质购买 异常:", e);
			return new Result(ResultCode.failed.getCode(), "检查满赠活动资质购买 异常");
		}
	}
	
	/**
	 * 找出相同活动的商品
	 * */
	private static List<OrderProduct> findSameOrderProduct(String activeId, List<OrderProduct> orderProductList, boolean isCut){
		List<OrderProduct> sameActiveProductList = new ArrayList<OrderProduct>();
		for( OrderProduct orderProduct : orderProductList ){
			if( isCut ){
				if( activeId.equals(orderProduct.getActiveId()) ){
					sameActiveProductList.add(orderProduct);
				}
			}else{
				if( activeId.equals(orderProduct.getActiveIdGive()) ){
					sameActiveProductList.add(orderProduct);
				}
			}
		}
		return sameActiveProductList;
	}
	
	/**
	 * 计算相同活动商品的总金额是否超过活动最低限额
	 * */
	private static Boolean isCountOutActiveMinLimit(String activeId, Double limitMoney, List<OrderProduct> sameActiveProductList, boolean isCut){

		Double sum = calcProductListTotalPrice(sameActiveProductList);
		
		// 满赠
		if( !isCut ){
			// 减去商品中分摊掉的满减额(文档要求先算满减再算满赠)
			Double cutMonrySum = 0.0, cutMonry = 0.0;
			for( OrderProduct orderProduct : sameActiveProductList ){
				cutMonry = orderProduct.getCutMonry();
				if( cutMonry != null && cutMonry > 0.0 ){
					cutMonrySum += cutMonry;
				}
			}
			LogCvt.debug("订单中同活动 "+activeId+" 的商品总价 "+sum+" - 已经分摊的满减额 "+cutMonrySum);
			sum -= cutMonrySum;
		}
		
		LogCvt.debug("订单中同活动 "+activeId+" 的商品总价 "+sum+" 限制 "+limitMoney+" 才能满减或满赠");
		
		return sum >= limitMoney;
	}
	
	/**
	 * 分摊满减额
	 * */
	private static void shareCutMoney(Double cutMonry, List<OrderProduct> sameActiveProductList){
		// 相同活动的商品种类不止一个
		if( sameActiveProductList.size() > 1 ){
			// 同类商品总额
			Double sumMonry = calcProductListTotalPrice(sameActiveProductList);
			// 已经分摊的满减额
			Double alreadyCotMonry = 0.0;
			for( int i = 0; i < sameActiveProductList.size(); i++ ){
				OrderProduct orderProduct = sameActiveProductList.get(i);
				// 单类购物总金额
				Double singleSumMonry = calcProductTotalPrice(orderProduct);
				// 计算商品的占比满减金额 
				Double singleCutMonry = ActiveUtils.calcSameActiveShareCutMoney(singleSumMonry, sumMonry, cutMonry);
				// 商品的占比满减金额 + 已经分摊的满减额 >= 满减金额
				if( Arith.add(singleCutMonry, alreadyCotMonry) >= cutMonry ){
					// 商品的占比满减金额 = 满减金额 - 已经分摊的满减额
					singleCutMonry = Arith.sub(cutMonry, alreadyCotMonry);
				}
				orderProduct.setCutMonry(singleCutMonry);
				// 累加 已经分摊的满减额
				alreadyCotMonry = Arith.add(alreadyCotMonry, singleCutMonry);
			}
		}else{
			sameActiveProductList.get(0).setCutMonry(cutMonry);
		}
	}
	
	/**
	 * 计算相同活动商品列表的总金额
	 * */
	private static Double calcProductListTotalPrice(List<OrderProduct> sameActiveProductList){
		
		Double sum = new Double(0.0);
		for( OrderProduct orderProduct : sameActiveProductList ){
			sum = Arith.add(sum, calcProductTotalPrice(orderProduct));
		}
		return sum;
	}
	
	/**
	 * 计算订单商品的总金额
	 * */
	private static Double calcProductTotalPrice(OrderProduct orderProduct){
		Double sum = new Double(0.0);
		Double generalPrice = orderProduct.getGeneralPrice();
		Integer generalCount = orderProduct.getGeneralCount();
		Double vipPrice = orderProduct.getVipPrice();
		Integer vipCount = orderProduct.getVipCount();
		if( generalPrice != null && generalCount != null ){
			sum = Arith.add(sum, Arith.mul2(generalPrice, generalCount));
		}
		if( vipPrice != null && vipCount != null ){
			sum = Arith.add(sum, Arith.mul2(vipPrice, vipCount));
		}
		return sum;
	}
}
