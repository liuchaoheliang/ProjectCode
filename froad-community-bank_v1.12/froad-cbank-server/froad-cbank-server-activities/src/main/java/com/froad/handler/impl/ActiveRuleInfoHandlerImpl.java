package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.ActiveBaseRuleMapper;
import com.froad.db.mysql.mapper.ActiveDetailRuleMapper;
import com.froad.db.mysql.mapper.ActiveRuleInfoMapper;
import com.froad.db.mysql.mapper.ActiveTagRelationMapper;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveItemType;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.handler.ActiveRuleInfoHandler;
import com.froad.handler.ActiveTagRelationHandler;
import com.froad.handler.ProductHandler;
import com.froad.handler.WeightActivityTagHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveDetailRule;
import com.froad.po.ActiveRuleInfo;
import com.froad.po.ActiveRuleInfoDetail;
import com.froad.po.ActiveTagRelation;
import com.froad.po.Product;
import com.froad.po.ProductDetail;
import com.froad.po.WeightActivityTag;
import com.froad.util.ActiveUtils;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class ActiveRuleInfoHandlerImpl implements ActiveRuleInfoHandler {

	private WeightActivityTagHandler weightActivityTagHandler = new WeightActivityTagHandlerImpl();
	private ProductHandler productHandler = new ProductHandlerImpl();
	
	private ActiveTagRelationHandler activeTagRelationHandler = new ActiveTagRelationHandlerImpl();

	// private static SimpleID simpleID = new SimpleID(ModuleID.order);

	@Override
	public ResultBean addActiveRuleInfo(ActiveRuleInfo activeRuleInfo) {
		Long begin = System.currentTimeMillis();
		LogCvt.debug("添加[ActiveRuleInfo] 开始 : ");
		ResultBean resultBean = new ResultBean(ResultCode.success, "添加活动信息成功");
		SqlSession sqlSession = null;
		Boolean result = false;

		try {
			// 第一期 一个商品只能绑定一个活动处理 start
			String itemType = activeRuleInfo.getActiveTagRelation()
					.getItemType();
			String clientId = activeRuleInfo.getActiveBaseRule().getClientId();
			String itemId = activeRuleInfo.getActiveTagRelation().getItemId();
			if (null != isExistProductActive(clientId, //判断有没没过期的全场活动
					ActiveItemType.unLimint.getCode(), "" , null, activeRuleInfo.getActiveBaseRule().getType())) {
				return resultBean = new ResultBean(ResultCode.failed,
						"添加活动失败,全场商品绑定了一个全场活动!");
			} 
			if(ActiveItemType.goods.getCode().equals(itemType))
			{
				if (null != isExistProductActive(clientId, //判断输入的商品标签是否已经参加活动
						itemType, itemId, null, activeRuleInfo.getActiveBaseRule().getType())) {
					return resultBean = new ResultBean(ResultCode.failed,
							"添加活动失败,该商品标签已经绑定了活动!");
				}
			}
			if(activeTagRelationHandler.countLimitProductActivityTag(clientId, activeRuleInfo.getActiveBaseRule().getType()) > 0)
			{
				if (ActiveItemType.unLimint.getCode().equals(itemType)) {//已经存在一个限定商品活动，则无法在创建全场活动
					return resultBean = new ResultBean(ResultCode.failed,
							"添加活动失败,有商品绑定了活动，无法全场绑定!");
				}
			}
			
//			if (activeTagRelationHandler.countActivityTag(itemId, itemType, clientId) > 0) {
//				return resultBean = new ResultBean(ResultCode.failed,
//						"添加活动失败,该标签已经绑定了活动!");
//			}
			// 第一期 一个商品只能绑定一个活动处理 end
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveRuleInfo(sqlSession, activeRuleInfo);

			if (result) { // 添加成功

				sqlSession.commit(true);
				Long end = System.currentTimeMillis();
				LogCvt.debug("添加[ActiveRuleInfo] 结束 : " + end + " 毫秒");
				LogCvt.debug("添加[addActiveRuleInfo] 耗时 : " + (end - begin)
						+ " 毫秒");
			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			resultBean = new ResultBean(ResultCode.failed, "添加活动信息失败");
			LogCvt.error(e.getMessage(), e);
			if (null != sqlSession)
				sqlSession.rollback(true);
		}
		try {
			// 添加 redis
			Long redisBegin = System.currentTimeMillis();
			LogCvt.debug("添加[ActiveRuleInfo]redis 开始 : " + redisBegin + " 毫秒");

			setActiveInfoRedis(activeRuleInfo, false);
			RedisCommon.initFullCutActive(activeRuleInfo.getActiveBaseRule(),
					activeRuleInfo.getActiveDetailRule());

			Long redisEnd = System.currentTimeMillis();
			LogCvt.debug("添加[ActiveRuleInfo]redis 结束 : " + redisEnd + " 毫秒");
			LogCvt.debug("添加[addActiveRuleInfo] 耗时 : "
					+ (redisEnd - redisBegin) + " 毫秒");
		} catch (Exception ex) {
			LogCvt.error("添加[ActiveRuleInfo]redis失败，原因:" + ex.getMessage(), ex);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		Long end = System.currentTimeMillis();
		LogCvt.debug("添加[addActiveRuleInfo] 总耗时 : " + (begin - end));
		return resultBean;
	}

	public Boolean addActiveRuleInfo(SqlSession sqlSession,
			ActiveRuleInfo activeRuleInfo) throws Exception {

		Boolean result = false;
		ActiveBaseRule activeBaseRule = activeRuleInfo.getActiveBaseRule();
		ActiveDetailRule activeDetailRule = activeRuleInfo
				.getActiveDetailRule();
		ActiveTagRelation activeTagRelation = activeRuleInfo
				.getActiveTagRelation();
		// 创建活动ID
		String clientId = activeBaseRule.getClientId();

		// 创建活动时候同时设置 create time && update time
		activeBaseRule.setCreateTime(new Date());
		activeBaseRule.setUpdateTime(new Date());
		activeTagRelation.setCreateTime(new Date());
		activeTagRelation.setUpdateTime(new Date());

		// 保存活动状态
		activeBaseRule.setStatus(ActiveStatus.launch.getCode());

		activeBaseRule.setOperator(activeBaseRule.getOperator());

		ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession
				.getMapper(ActiveBaseRuleMapper.class);
		ActiveDetailRuleMapper activeDetailRuleMapper = sqlSession
				.getMapper(ActiveDetailRuleMapper.class);
		ActiveTagRelationMapper activeTagRelationMapper = sqlSession
				.getMapper(ActiveTagRelationMapper.class);

		String maxSequenceStr = activeBaseRuleMapper
				.getMaxActiveByClientId(clientId, ActiveIdCode.MJ.getCode().toString());
		Integer seq = 0;
		if(maxSequenceStr == null)
		{
			seq = 1;
		}
		else
		{
			 seq = Integer.valueOf(maxSequenceStr.substring(
						maxSequenceStr.lastIndexOf("-") + 1, maxSequenceStr.length())) + 1;
		}

		LogCvt.debug("当前数据库中该客户端最大的序列号是: " + maxSequenceStr);
		String activeId = ActiveUtils.generateActiveId(seq, clientId, ActiveIdCode.MJ.getCode());
		activeBaseRule.setActiveId(activeId);
		activeDetailRule.setActiveId(activeId);
		activeTagRelation.setActiveId(activeId);

		if (activeBaseRuleMapper.addActiveBaseRule(activeBaseRule) > -1
				&& activeDetailRuleMapper.addActiveDetailRule(activeDetailRule) > -1
				&& activeTagRelationMapper
						.addActiveTagRelation(activeTagRelation) > -1) { // 添加成功
			result = true;
		}

		return result;

	}

	// @Override
	// public ResultBean disableActiveRuleInfo(String clientId, Long id) {
	// ResultBean resultBean=new ResultBean(ResultCode.success,"添加活动信息成功");
	// try {
	// //activeBaseRuleHandler.disableActiveBaseRuleById(clientId, id);
	//
	// } catch (Exception e) {
	// resultBean= new ResultBean(ResultCode.failed,"禁止活动信息失败");
	// LogCvt.error("禁止ActiveRuleInfo失败，原因:" + e.getMessage(), e);
	// }
	// return resultBean;
	// }

	public ResultBean updateActiveRuleInfoByActiveId(
			ActiveRuleInfo activeRuleInfo) {

		ResultBean resultBean = new ResultBean(ResultCode.success, "更新活动信息成功");
		Long begin = System.currentTimeMillis();
		LogCvt.debug("updateActiveRuleInfoByActiveId 开始 : " + begin + " 毫秒");
		SqlSession sqlSession = null;
		// 第一期 一个商品只能绑定一个活动处理 start
		String itemType = activeRuleInfo.getActiveTagRelation()
				.getItemType();
		String clientId = activeRuleInfo.getActiveBaseRule().getClientId();
		String itemId = activeRuleInfo.getActiveTagRelation().getItemId();
		String activeId = activeRuleInfo.getActiveTagRelation().getActiveId();
		Boolean result = false;
		try {
			if (null != isExistProductActive(clientId, //判断有没没过期的全场活动
					ActiveItemType.unLimint.getCode(), "" , activeId, activeRuleInfo.getActiveBaseRule().getType())) {
				return resultBean = new ResultBean(ResultCode.failed,
						"更新活动失败,全场商品绑定了一个全场活动!");
			} 
			if(ActiveItemType.goods.getCode().equals(itemType))
			{
				if (null != isExistProductActive(clientId, //判断输入的商品标签是否已经参加活动
						itemType, itemId, activeId, activeRuleInfo.getActiveBaseRule().getType())) {
					return resultBean = new ResultBean(ResultCode.failed,
							"更新活动失败,该商品标签已经绑定了其他活动!");
				}
			}
			if(activeTagRelationHandler.countLimitProductActivityTag(clientId, activeRuleInfo.getActiveBaseRule().getType()) > 0)
			{
				if (ActiveItemType.unLimint.getCode().equals(itemType)) {//已经存在一个限定商品活动，则无法在创建全场活动
					return resultBean = new ResultBean(ResultCode.failed,
							"更新活动失败,有商品绑定了活动，无法全场绑定!");
				}
			}
			
//			if (isExistProductActive(clientId, //判断有没没过期的全场活动
//					ActiveItemType.unLimint.getCode(), "" , activeId)) {
//				return resultBean = new ResultBean(ResultCode.failed,
//						"添加活动失败,全场商品绑定了一个全场活动!");
//			} 
//			if (isExistProductActive(clientId, //判断输入的商品标签是否已经参加活动
//					ActiveItemType.goods.getCode(), itemId , activeId)) {
//				return resultBean = new ResultBean(ResultCode.failed,
//						"添加活动失败,该商品标签已经绑定了活动!");
//			}
//			if(ActiveItemType.goods.getCode().equals(itemType))
//			{
//				if (ActiveItemType.unLimint.getCode().equals(itemType)) {//已经存在一个限定商品活动，则无法在创建全场活动
//					return resultBean = new ResultBean(ResultCode.failed,
//							"添加活动失败,有商品绑定了活动，无法全场绑定!");
//				}
//			}

//			if (ActiveItemType.goods.getCode().equals(itemType)) {
//				if (checkProductBindedActive(itemId, itemType, clientId)) {
//					return resultBean = new ResultBean(ResultCode.failed,
//							"更新活动失败,该商品标签已经绑定了活动!");
//				}
//			}
//			if(activeTagRelationHandler.countActivityTag(itemId, itemType, clientId) >0){
//				return resultBean = new ResultBean(ResultCode.failed,
//						"添加活动失败,该标签已经绑定了活动!");
//			}
			// 第一期 一个商品只能绑定一个活动处理 end

			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveRuleInfoByActiveId(sqlSession,
					activeRuleInfo);

			if (result) { // 更新成功
			// //活动ID
			// String activeId =
			// activeRuleInfo.getActiveBaseRule().getActiveId();
			// //删除旧缓存
			// SupportsRedis.del_cbbank_active_merchant_info(activeId);
			// SupportsRedis.del_cbbank_active_product_info(activeId);
			// //更新redis
			// //1)ItemType 0商品 1门店 2商户
			// String itemType =
			// activeRuleInfo.getActiveTagRelation().getItemType();
			// String itemId =
			// activeRuleInfo.getActiveTagRelation().getItemId();
			// if(itemType.equals(ActiveItemType.unLimint.getCode()))
			// {
			// //找到所有的商品和商户
			// List<WeightActivityTag> allItems =
			// weightActivityTagHandler.findAllWeightActivityTag();
			// for(WeightActivityTag tag : allItems)
			// {
			// if(ActiveItemType.goods.getCode().equals(tag.getActivityType()))
			// {
			// String productId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_product_product_id(productId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_product_info_active_id(productId,
			// activeId);
			// }
			// else
			// if(ActiveItemType.merchant.getCode().equals(tag.getActivityType()))
			// {
			// String merchantId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_merchant_merchant_id(merchantId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_merchant_info_active_id(merchantId,
			// activeId);
			// setActiveInfoProductByMerchantId(tag.getElementId() ,
			// activeRuleInfo);
			// }
			// else //门户
			// {
			// }
			// }
			// }
			// else
			// if(activeRuleInfo.getActiveTagRelation().getItemType().equals(ActiveItemType.goods.getCode()))
			// {
			// //activity type 0商户 1门店 2商品
			// //根据item type 去 cb_weight_activity_tag 获取商品
			// List<WeightActivityTag> goodsList=
			// weightActivityTagHandler.findWeightActivityTagByActivityIdAndActivityType(itemId,
			// itemType);
			// //LogCvt.debug("共有 : "+goodsList.size() +"商品参加了活动");
			// for(WeightActivityTag tag : goodsList)
			// {
			// String productId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_product_product_id(productId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_product_info_active_id(productId,
			// activeId);
			// }
			// }
			// else
			// if(activeRuleInfo.getActiveTagRelation().getItemType().equals(ActiveItemType.merchant.getCode()))//商户
			// {
			// //根据item type 去 cb_weight_activity_tag
			// 获取商户ID(目前跟获得商品一样，以后可能需要获得商户下的商品)
			// List<WeightActivityTag> merchantsList=
			// weightActivityTagHandler.findWeightActivityTagByActivityIdAndActivityType(itemId,
			// itemType);
			// //LogCvt.debug("共有 : "+goodsList.size() +"商品参加了活动");
			// for(WeightActivityTag tag : merchantsList)
			// {
			// String merchantId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_merchant_merchant_id(merchantId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_merchant_info_active_id(merchantId,
			// activeId);
			// setActiveInfoProductByMerchantId(tag.getElementId() ,
			// activeRuleInfo);
			// }
			// }
			// else
			// if(activeRuleInfo.getActiveTagRelation().getItemType().equals(ActiveItemType.portal.getCode()))
			// {
			// //门店以后拓展
			// }
				sqlSession.commit(true);
				Long end = System.currentTimeMillis();
				LogCvt.debug("updateActiveRuleInfoByActiveId 结束 : " + end
						+ " 毫秒");
				LogCvt.debug("更新[updateActiveRuleInfoByActiveId] 耗时 : "
						+ (end - begin) + " 毫秒");

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			resultBean = new ResultBean(ResultCode.failed, "更新活动信息失败");
			LogCvt.error("更新ActiveRuleInfo失败，原因:" + e.getMessage(), e);
			if (null != sqlSession)
				sqlSession.rollback(true);
		}
		try {
			try {
				Long redisBegin = System.currentTimeMillis();
				LogCvt.debug("更新[ActiveRuleInfo]redis 开始 : " + redisBegin
						+ " 毫秒");
				
				//更新满减活动，删除redis
				String Rediskey = RedisKeyUtil.cbbank_full_cut_active_client_id_active_id(clientId, activeId);
				SupportsRedis.deleteRedisKey(Rediskey);
				
				setActiveInfoRedis(activeRuleInfo, true);
				Long redisEnd = System.currentTimeMillis();

				LogCvt.debug("更新[ActiveRuleInfo]redis 结束 : " + redisEnd + " 毫秒");
				LogCvt.debug(" 更新[ActiveRuleInfo]redis 耗时 : "
						+ (redisEnd - redisBegin) + " 毫秒");
			} catch (Exception e) {
				LogCvt.error("更新ActiveRuleInfo Redis失败，原因:" + e.getMessage(), e);
			}
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return resultBean;
	}

	public Boolean updateActiveRuleInfoByActiveId(SqlSession sqlSession,
			ActiveRuleInfo activeRuleInfo) throws Exception {
		Long begin = System.currentTimeMillis();
		LogCvt.debug("更新[ActiveRuleInfo] : 开始 " + begin + " 毫秒");
		Boolean result = false;
		ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession
				.getMapper(ActiveBaseRuleMapper.class);
		ActiveDetailRuleMapper activeDetailRuleMapper = sqlSession
				.getMapper(ActiveDetailRuleMapper.class);
		ActiveTagRelationMapper activeTagRelationMapper = sqlSession
				.getMapper(ActiveTagRelationMapper.class);

		ActiveBaseRule activeBaseRule = activeRuleInfo.getActiveBaseRule();
		ActiveDetailRule activeDetailRule = activeRuleInfo
				.getActiveDetailRule();
		ActiveTagRelation activeTagRelation = activeRuleInfo
				.getActiveTagRelation();

		String activeId = activeBaseRule.getActiveId();

		ActiveBaseRule oldActiveBaseRule = activeBaseRuleMapper
				.findActiveBaseRuleByActiveId(activeId); // 数据库的数据
		ActiveDetailRule oldActiveDetailRule = activeDetailRuleMapper
				.findActiveDetailRuleByActiveId(activeId);
		ActiveTagRelation oldActiveTagRelation = activeTagRelationMapper
				.findActiveTagRelationByActiveId(activeId);

		if (Checker.isEmpty(oldActiveBaseRule)) {
			throw new FroadServerException("活动[" + activeId + "]基本信息记录不存在！");
		} else if (Checker.isEmpty(oldActiveDetailRule)) {
			throw new FroadServerException("活动[" + activeId + "]详细信息记录不存在！");
		} else if (Checker.isEmpty(oldActiveTagRelation)) {
			throw new FroadServerException("活动[" + activeId + "]关联信息记录不存在！");
		}

		// 更新活动时候同时更新update time
		activeBaseRule.setUpdateTime(new Date());
		activeTagRelation.setUpdateTime(new Date());
		if (activeRuleInfo.getActiveDetailRule().getIsPrePay()) {
			activeDetailRule.setMaxCount(activeDetailRule.getMaxMoney()
					/ activeDetailRule.getRetMoney());
		}
		
		if (activeBaseRuleMapper.updateActiveBaseRulebyActiveId(activeBaseRule) > 0
				&& activeDetailRuleMapper
						.updateActiveDetailRuleByActiveId(activeDetailRule) > 0
				&& activeTagRelationMapper
						.updateActiveTagRelationByActiveId(activeTagRelation) > 0) { // 更新成功
			result = true;
		}
		Long end = System.currentTimeMillis();
		LogCvt.debug("更新[ActiveRuleInfo] 结束  " + end + " 毫秒");
		System.out.println("updateActiveRuleInfoByActiveId 消耗 : "
				+ (end - begin) + " 毫秒");
		return result;

	}

	@Override
	public ActiveRuleInfo getActiveRuleInfo(String clientId, String activeId) {
		ActiveRuleInfo result = null;
		SqlSession sqlSession = null;
		try {
			Long begin = System.currentTimeMillis();
			LogCvt.debug("查询[ActiveRuleInfo] 开始 " + begin + " 毫秒");
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession
					.getMapper(ActiveBaseRuleMapper.class);
			ActiveDetailRuleMapper activeDetailRuleMapper = sqlSession
					.getMapper(ActiveDetailRuleMapper.class);
			ActiveTagRelationMapper activeTagRelationMapper = sqlSession
					.getMapper(ActiveTagRelationMapper.class);

			ActiveBaseRule activeBaseRule = activeBaseRuleMapper
					.findActiveBaseRuleByActiveId(activeId);
			ActiveDetailRule activeDetailRule = activeDetailRuleMapper
					.findActiveDetailRuleByActiveId(activeId);
			ActiveTagRelation activeTagRelation = activeTagRelationMapper
					.findActiveTagRelationByActiveId(activeId);
			if (null != activeBaseRule) {
				result = new ActiveRuleInfo();
				result.setActiveBaseRule(activeBaseRule);
				result.setActiveDetailRule(activeDetailRule);
				result.setActiveTagRelation(activeTagRelation);
			}
			Long end = System.currentTimeMillis();
			LogCvt.debug("查询[ActiveRuleInfo] 结束 " + begin + " 毫秒");
			LogCvt.debug("查询[ActiveRuleInfo] 耗时 " + (end - begin) + " 毫秒");
		} catch (Exception e) {
			LogCvt.error("查询ActiveRuleInfo失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	public Page<ActiveRuleInfo> getAllActiveRuleInfoByPage(
			Page<ActiveRuleInfo> page, ActiveRuleInfo activeRuleInfo) {
		SqlSession sqlSession = null;
		List<ActiveRuleInfoDetail> list = null;
		List<ActiveRuleInfo> result = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			ActiveRuleInfoMapper activeRuleInfoMapper = sqlSession
					.getMapper(ActiveRuleInfoMapper.class);

			list = activeRuleInfoMapper.findAllActiveRuleInfoByPage(page,
					activeRuleInfo);
			result = new ArrayList<ActiveRuleInfo>();
			for (ActiveRuleInfoDetail detail : list) {
				ActiveRuleInfo active = setActiveRuleInfo(detail);
				result.add(active);
			}
			// page.setTotalCount(result.size());
			page.setResultsContent(result);
		} catch (Exception e) {
			LogCvt.error("查询ActiveRuleInfo失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return page;
	}

	private ActiveRuleInfo setActiveRuleInfo(ActiveRuleInfoDetail detail) {
		ActiveBaseRule activeBaseRule = new ActiveBaseRule();
		ActiveDetailRule activeDetailRule = new ActiveDetailRule();
		ActiveTagRelation activeTagRelation = new ActiveTagRelation();
		ActiveRuleInfo active = new ActiveRuleInfo();

		activeBaseRule.setClientId(detail.getClientId());
		activeBaseRule.setActiveId(detail.getActiveId());
		activeBaseRule.setType(detail.getType());
		activeBaseRule.setStatus(detail.getStatus());
		activeBaseRule.setCreateTime(detail.getCreateTime());
		activeBaseRule.setUpdateTime(detail.getUpdateTime());
		activeBaseRule.setActiveName(detail.getActiveName());
		activeBaseRule.setActiveLogo(detail.getActiveLogo());
		activeBaseRule.setExpireStartTime(detail.getExpireStartTime());
		activeBaseRule.setExpireEndTime(detail.getExpireEndTime());
		activeBaseRule.setLimitType(detail.getLimitType());
		activeBaseRule.setMerchantRate(detail.getMerchantRate());
		activeBaseRule.setBankRate(detail.getBankRate());
		activeBaseRule.setFftRate(detail.getFftRate());
		activeBaseRule.setSettleType(detail.getSettleType());
		activeBaseRule.setDescription(detail.getDescription());
		activeBaseRule.setOperator(detail.getOperator());

		activeDetailRule.setActiveId(detail.getActiveId());
		activeDetailRule.setClientId(detail.getClientId());
		activeDetailRule.setMinLimit(detail.getMinLimit());
		activeDetailRule.setMaxCount(detail.getMaxCount());
		activeDetailRule.setIsPerDay(detail.getIsPerDay());
		activeDetailRule.setPerDay(detail.getPerDay());
		activeDetailRule.setPerCount(detail.getPerCount());
		activeDetailRule.setIsTotalDay(detail.getIsTotalDay());
		activeDetailRule.setTotalDay(detail.getTotalDay());
		activeDetailRule.setTotalCount(detail.getTotalCount());
		activeDetailRule.setIsPaperPay(detail.getIsPaperPay());
		activeDetailRule.setIsPrePay(detail.getIsPrePay());
		activeDetailRule.setRetMoney(detail.getRetMoney());
		activeDetailRule.setMaxMoney(detail.getMaxMoney());
		activeDetailRule.setPrePayActiveId(detail.getPrePayActiveId());
		activeDetailRule.setProductId(detail.getProductId());
		activeDetailRule.setProductCount(detail.getProductCount());
		activeDetailRule.setPoint(detail.getPoint());
		activeDetailRule.setPointType(detail.getPointType());
		activeDetailRule.setPointCount(detail.getPointCount());
		activeDetailRule.setPayMethod(detail.getPayMethod());

		activeTagRelation.setActiveId(detail.getActiveId());
		activeTagRelation.setClientId(detail.getClientId());
		activeTagRelation.setItemId(detail.getItemId());
		activeTagRelation.setItemType(detail.getItemType());
		activeTagRelation.setCreateTime(detail.getCreateTime());
		activeTagRelation.setUpdateTime(detail.getUpdateTime());

		active.setActiveBaseRule(activeBaseRule);
		active.setActiveDetailRule(activeDetailRule);
		active.setActiveTagRelation(activeTagRelation);

		return active;
	}

	private void setActiveInfoProductByMerchantId(String merchantId,
			ActiveRuleInfo activeRuleInfo) {
		// 通过商户ID找到商品
		List<Product> productList = productHandler
				.findProductByMerchantId(merchantId);// 商户ID
		Set<String> porductIdSet = new HashSet<String>();
		Set<String> redisKeys = new HashSet<String>();
		for (Product prod : productList) {
			String productId = prod.getProductId();
			porductIdSet.add(productId);
			redisKeys.add(setRedisProductKeys(productId));

		}
		SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
				activeRuleInfo.getActiveBaseRule());
		SupportsRedis.set_cbbank_active_product_info_active_id(porductIdSet,
				activeRuleInfo.getActiveBaseRule().getActiveId());
	}

	private void setActiveInfoRedis(ActiveRuleInfo activeRuleInfo,
			boolean isUpdateFlag) throws Exception {
		String itemType = activeRuleInfo.getActiveTagRelation().getItemType();
		String activeId = activeRuleInfo.getActiveBaseRule().getActiveId();
		String itemId = activeRuleInfo.getActiveTagRelation().getItemId();
		String clientId = activeRuleInfo.getActiveBaseRule().getClientId();

		if (isUpdateFlag) {
			Long begin = System.currentTimeMillis();
			LogCvt.debug("删除redis[" + activeId + "]时间开始 : " + begin + " 毫秒");
			SupportsRedis.del_cbbank_active_merchant_info(activeId);
			SupportsRedis.del_cbbank_active_product_info(activeId);
			Long end = System.currentTimeMillis();
			LogCvt.debug("删除redis[" + activeId + "]时间结束 : " + end + " 毫秒");
			LogCvt.debug("删除redis[" + activeId + "]时间消耗 : " + (end - begin)
					+ " 毫秒");
		}
		// 活动类型：1商户活动；2门店活动；3商品活动
		if (itemType.equals(ActiveItemType.unLimint.getCode())) {
			// 找到所有的商品和商户
			// List<WeightActivityTag> allItems =
			// weightActivityTagHandler.findAllWeightActivityTag(clientId);
			// for(WeightActivityTag tag : allItems)
			// {
			// if(ActiveItemType.goods.getCode().equals(tag.getActivityType()))
			// {
			// String productId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_product_product_id(productId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_product_info_active_id(productId,
			// activeId);
			// }
			// else
			// if(ActiveItemType.merchant.getCode().equals(tag.getActivityType()))
			// {
			// String merchantId = tag.getElementId();
			// SupportsRedis.set_cbbank_active_merchant_merchant_id(merchantId,
			// activeRuleInfo.getActiveBaseRule());
			// SupportsRedis.set_cbbank_active_merchant_info_active_id(merchantId,
			// activeId);
			// setActiveInfoProductByMerchantId(tag.getElementId() ,
			// activeRuleInfo);
			// }
			// else //门户
			// {
			// }
			// }
			Long begin = System.currentTimeMillis();
			LogCvt.debug("全场商品活动范围 redis产生时间开始 : " + begin + " 毫秒");
			List<ProductDetail> productInfoList = productHandler
					.getProductIdAndMerchantIdByClientId(clientId);
			System.out.println("productInfoList :" + productInfoList.size());
			Set<String> productIdSet = new HashSet<String>();
			Set<String> redisKeys = new HashSet<String>();
			for (ProductDetail detail : productInfoList) {
				String productId = detail.getProductId();
				productIdSet.add(productId);

				redisKeys.add(setRedisProductKeys(productId));
				// 设置商户
				// SupportsRedis.set_cbbank_active_merchant_merchant_id(merchantId,
				// activeRuleInfo.getActiveBaseRule());
				// SupportsRedis.set_cbbank_active_merchant_info_active_id(merchantId,
				// activeId);

			}
			SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
					activeRuleInfo.getActiveBaseRule());
			SupportsRedis.set_cbbank_active_product_info_active_id(
					productIdSet, activeId);

			Long end = System.currentTimeMillis();
			LogCvt.debug("全场商品活动范围 redis产生时间结束 : " + end + " 毫秒");
			LogCvt.debug("全场商品活动范围 redis产生耗费 : " + (end - begin) + " 毫秒");

		} else if (activeRuleInfo.getActiveTagRelation().getItemType()
				.equals(ActiveItemType.goods.getCode())) {
			Long begin = System.currentTimeMillis();
			LogCvt.debug("限定活动范围[商品] redis产生时间开始 : " + begin);
			// 根据item type 去 cb_weight_activity_tag 获取商品
			List<WeightActivityTag> goodsList = weightActivityTagHandler
					.findWeightActivityTagByActivityIdAndActivityType(itemId,
							itemType, clientId);
			// LogCvt.debug("共有 : "+goodsList.size() +"商品参加了活动");
			Set<String> productIdSet = new HashSet<String>();
			Set<String> redisKeys = new HashSet<String>();
			if(goodsList.size() >0)
			{			
				for (WeightActivityTag tag : goodsList) {
				String productId = tag.getElementId();
				productIdSet.add(productId);
				// SupportsRedis.set_cbbank_active_product_product_id(productId,
				// activeRuleInfo.getActiveBaseRule());
				redisKeys.add(setRedisProductKeys(productId));

			}
			SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
					activeRuleInfo.getActiveBaseRule());
			SupportsRedis.set_cbbank_active_product_info_active_id(
					productIdSet, activeId);
				
			}

			Long end = System.currentTimeMillis();
			LogCvt.debug("限定活动范围[商品] redis产生时间结束 : " + end + " 毫秒");
			LogCvt.debug("限定活动范围[商品] redis产生时间耗费 : " + (end - begin) + " 毫秒");
		} else if (activeRuleInfo.getActiveTagRelation().getItemType()
				.equals(ActiveItemType.merchant.getCode()))// 商户
		{
			Long begin = System.currentTimeMillis();
			LogCvt.debug("限定活动范围[商户] redis产生时间开始 : " + begin);
			// 根据item type 去 cb_weight_activity_tag
			// 获取商户ID(目前跟获得商品一样，以后可能需要获得商户下的商品)
			List<WeightActivityTag> merchantsList = weightActivityTagHandler
					.findWeightActivityTagByActivityIdAndActivityType(itemId,
							itemType, clientId);
			// LogCvt.debug("共有 : "+goodsList.size() +"商品参加了活动");
			for (WeightActivityTag tag : merchantsList) {
				//String merchantId = tag.getElementId();
				// SupportsRedis.set_cbbank_active_merchant_merchant_id(merchantId,
				// activeRuleInfo.getActiveBaseRule());
				// SupportsRedis.set_cbbank_active_merchant_info_active_id(merchantId,
				// activeId);
				setActiveInfoProductByMerchantId(tag.getElementId(),
						activeRuleInfo);
			}
			Long end = System.currentTimeMillis();
			LogCvt.debug("限定活动范围[商户] redis产生时间结束 : " + end);
			LogCvt.debug("限定活动范围[商品] redis时间产生耗费 : " + (end - begin) + " 毫秒");
		} else if (activeRuleInfo.getActiveTagRelation().getItemType()
				.equals(ActiveItemType.portal.getCode())) {
			// 门店以后拓展
		}
	}

	private String setRedisProductKeys(String productId) {
		return RedisKeyUtil.cbbank_active_product_product_id(productId);
	}

//	private boolean checkProductBindedActive(String activityId,
//			String activityType, String clientId) {
//		boolean result = false;
//		List<WeightActivityTag> productIdList = weightActivityTagHandler
//				.findWeightActivityTagByActivityIdAndActivityType(activityId,
//						activityType, clientId);
//
//		for (int i = 0; i < productIdList.size(); i++) {
//			String productId = productIdList.get(i).getElementId();
//			if (SupportsRedis.isProductBindedActive(productId)) {
//				result = true;
//			}
//		}
//
//		return result;
//	}

	public ActiveTagRelation isExistProductActive(String clientId, String itemType, String itemId, String activeId, String type) {
		
		return activeTagRelationHandler.getAvailableActive(clientId, itemType, itemId, activeId, type);
		
	}

}
