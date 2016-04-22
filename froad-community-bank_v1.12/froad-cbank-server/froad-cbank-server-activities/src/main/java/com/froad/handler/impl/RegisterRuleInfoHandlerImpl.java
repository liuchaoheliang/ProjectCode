/**
 * @Title: RegisterRuleInfoHandlerImpl.java
 * @Package com.froad.thrift.service.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
 **/

package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveBaseRuleMapper;
import com.froad.db.mysql.mapper.ActiveTagRelationMapper;
import com.froad.db.mysql.mapper.RegistDetailRuleMapper;
import com.froad.db.mysql.mapper.RegisteredRuleInfoMapper;
import com.froad.db.redis.RegisterRedis;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveItemType;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ProductHandler;
import com.froad.handler.RegisterRuleInfoHandler;
import com.froad.handler.WeightActivityTagHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveTagRelation;
import com.froad.po.ProductDetail;
import com.froad.po.RegistDetailRule;
import com.froad.po.RegisteredExportDetail;
import com.froad.po.RegisteredInfo;
import com.froad.po.RegisteredRuleInfo;
import com.froad.po.WeightActivityTag;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.ActiveUtils;
import com.froad.util.RedisKeyUtil;

/**
 * @ClassName: RegisterRuleInfoHandlerImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public class RegisterRuleInfoHandlerImpl implements RegisterRuleInfoHandler {
	
	private ProductHandler productHandler = new ProductHandlerImpl();
	
	private WeightActivityTagHandler weightActivityTagHandler = new WeightActivityTagHandlerImpl();

	/**
	 * @Title: addRegisteredRuleInfo
	 * @Description: TODO
	 * @author: Joker 2015年12月1日
	 * @modify: Joker 2015年12月1日
	 * @param originVo
	 * @param registeredRuleInfo
	 * @return
	 * @see com.froad.handler.RegisterRuleInfoHandler#addRegisteredRuleInfo(com.froad.thrift.vo.OriginVo,
	 *      com.froad.po.RegisteredRuleInfo)
	 */

	@Override
	public ResultVo addRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo) {

		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),
				"注册促销活动新增成功");
		try {
			ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession
					.getMapper(ActiveBaseRuleMapper.class);
			RegistDetailRuleMapper registDetailRuleMapper = sqlSession
					.getMapper(RegistDetailRuleMapper.class);
			ActiveTagRelationMapper activeTagRelationMapper = sqlSession
					.getMapper(ActiveTagRelationMapper.class);
			ActiveBaseRule activeBaseRule = registeredRuleInfo
					.getActiveBaseRule();
			ActiveTagRelation activeTagRelation = registeredRuleInfo
					.getActiveTagRelation();
			RegistDetailRule registeredDetailRule = registeredRuleInfo
					.getRegisteredDetailRule();
			String clientId = activeBaseRule.getClientId();
			Date beginTime = activeBaseRule.getExpireStartTime();
			Date endTime = activeBaseRule.getExpireEndTime();

			// 一段时间内只能有一个有效的注册送活动
			ActiveBaseRule zcActiveBaseRule = new ActiveBaseRule();
			zcActiveBaseRule.setClientId(clientId);
			zcActiveBaseRule.setType(ActiveType.registerGive.getCode());
			zcActiveBaseRule.setStatus(ActiveStatus.launch.getCode());
			List<ActiveBaseRule> existsRegisterRuleInfoList = activeBaseRuleMapper
					.findActiveBaseRule(zcActiveBaseRule, null);
			for (ActiveBaseRule existsDetail : existsRegisterRuleInfoList) {
				if (existsDetail.getExpireStartTime().compareTo(endTime)<=0
						&& existsDetail.getExpireEndTime().compareTo(beginTime)>=0) {
					resultVo = new ResultVo(ResultCode.failed.getCode(),
							"注册促销送活动新增失败,一个时间内只能有一个有效的注册送活动");
					return resultVo;
				}
			}

			String maxSequenceStr = activeBaseRuleMapper
					.getMaxActiveByClientId(clientId, ActiveIdCode.ZC.getCode()
							.toString());
			Integer seq = 0;
			if (maxSequenceStr == null) {
				seq = 1;
			} else {
				seq = Integer.valueOf(maxSequenceStr.substring(
						maxSequenceStr.lastIndexOf("-") + 1,
						maxSequenceStr.length())) + 1;
			}
			LogCvt.debug("当前数据库中该客户端最大的序列号是: " + maxSequenceStr);
			String activeId = ActiveUtils.generateActiveId(seq, clientId,
					ActiveIdCode.ZC.getCode());

			// set fields
			activeBaseRule.setActiveId(activeId);
			registeredDetailRule.setActiveId(activeId);
			activeTagRelation.setActiveId(activeId);
			// 创建活动时候同时设置 create time && update time
			activeBaseRule.setCreateTime(new Date());
			activeBaseRule.setUpdateTime(new Date());
			activeTagRelation.setCreateTime(new Date());
			activeTagRelation.setUpdateTime(new Date());
			// 保存活动状态
			activeBaseRule.setStatus(ActiveStatus.launch.getCode());
			activeBaseRule.setOperator(activeBaseRule.getOperator());
			// 首单才需要保存标签关联
			if(registeredDetailRule.getTriggerType()) {
				activeTagRelationMapper
				.addActiveTagRelation(registeredRuleInfo
						.getActiveTagRelation());
			}
			if (activeBaseRuleMapper.addActiveBaseRule(registeredRuleInfo
					.getActiveBaseRule()) > 0
					&& registDetailRuleMapper
							.addRegistDetailRule(registeredRuleInfo
									.getRegisteredDetailRule()) > 0
					) {			
				
				sqlSession.commit(true);
				// add redis				
				this.setActiveRuleRedisInfo(registeredRuleInfo, clientId, activeId);
			} else {
				sqlSession.rollback(true);
			}

		} catch (Exception e) {
			LogCvt.error("注册促销送活动新增错误 " + e.getMessage(), e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), "注册促销送活动新增失败 ");
			if (null != sqlSession)
				sqlSession.rollback(true);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return resultVo;

	}

	/**
	 * @Title: getRegisteredRuleInfoById
	 * @Description: TODO
	 * @author: Joker 2015年12月2日
	 * @modify: Joker 2015年12月2日
	 * @param clientId
	 * @param activeId
	 * @return
	 * @see com.froad.handler.RegisterRuleInfoHandler#getRegisteredRuleInfoById(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public RegisteredRuleInfo getRegisteredRuleInfoById(String clientId,
			String activeId) {
		SqlSession sqlSession = null;
		RegisteredRuleInfo registeredRuleInfo = null;
		RegisteredRuleInfo result = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			RegisteredRuleInfoMapper registeredRuleInfoMapper = sqlSession
					.getMapper(RegisteredRuleInfoMapper.class);
			registeredRuleInfo = new RegisteredRuleInfo();
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setClientId(clientId);
			activeBaseRule.setActiveId(activeId);
			registeredRuleInfo.setActiveBaseRule(activeBaseRule);
			
			result = registeredRuleInfoMapper
					.findRegisteredRuleInfo(registeredRuleInfo);
			
		} catch (Exception e) {
			LogCvt.error("查询注册送规则错误 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultVo updateRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo) {
		ResultVo resultVo = new ResultVo();
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		try {			
			registeredRuleInfo
			.getActiveTagRelation().setActiveId(registeredRuleInfo
					.getActiveBaseRule().getActiveId());
			registeredRuleInfo
			.getRegisteredDetailRule().setActiveId(registeredRuleInfo
					.getActiveBaseRule().getActiveId());
			ActiveBaseRuleMapper baseRuleMapper = sqlSession
					.getMapper(ActiveBaseRuleMapper.class);
			ActiveTagRelationMapper tagRelationMapper = sqlSession
					.getMapper(ActiveTagRelationMapper.class);
			RegistDetailRuleMapper registDetailRuleMapper = sqlSession
					.getMapper(RegistDetailRuleMapper.class);
			// 一段时间内只能有一个有效的注册送活动
			ActiveBaseRule zcActiveBaseRule = new ActiveBaseRule();
			zcActiveBaseRule.setClientId(registeredRuleInfo.getActiveBaseRule().getClientId());
			zcActiveBaseRule.setType(ActiveType.registerGive.getCode());
			zcActiveBaseRule.setStatus(ActiveStatus.launch.getCode());
			List<ActiveBaseRule> existsRegisterRuleInfoList = baseRuleMapper
					.findActiveBaseRule(zcActiveBaseRule, null);
			Date beginTime = registeredRuleInfo.getActiveBaseRule().getExpireStartTime();
			Date endTime = registeredRuleInfo.getActiveBaseRule().getExpireEndTime();
			for (ActiveBaseRule existsDetail : existsRegisterRuleInfoList) {
				if (existsDetail.getExpireStartTime().compareTo(endTime)<=0
						&& existsDetail.getExpireEndTime().compareTo(beginTime)>=0
						&& !existsDetail.getActiveId().equals(
								registeredRuleInfo.getActiveBaseRule().getActiveId())) {
					resultVo = new ResultVo(ResultCode.failed.getCode(),
							"更新注册送活动失败,一个时间内只能有一个有效的注册送活动");
					return resultVo;
				}
			}
			long result = baseRuleMapper.updateActiveBaseRulebyActiveId(registeredRuleInfo
					.getActiveBaseRule());
			if(registeredRuleInfo.getRegisteredDetailRule()!= null 
					&& registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
				result += tagRelationMapper.updateActiveTagRelationByActiveId(registeredRuleInfo
						.getActiveTagRelation());
			}
			
			result += registDetailRuleMapper
					.updateRegistDetailRule(registeredRuleInfo
							.getRegisteredDetailRule());
			if (result > 0) {
				sqlSession.commit();
				LogCvt.debug("注册活动更新成功");
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc("注册活动更新成功");
				// 更新活动明细redis
				String key = RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(
						registeredRuleInfo.getActiveBaseRule().getClientId(), 
						registeredRuleInfo.getActiveBaseRule().getActiveId());
				SupportsRedis.deleteRedisKey(key);
				RegisterRedis.initRegisterActive(
						registeredRuleInfo.getActiveBaseRule(), 
						registeredRuleInfo.getRegisteredDetailRule());
				// 更新首单满减redis
				if(registeredRuleInfo.getRegisteredDetailRule() != null 
						&& registeredRuleInfo.getRegisteredDetailRule().getTriggerType()) {
					SupportsRedis.del_cbbank_active_product_info(
							registeredRuleInfo.getActiveBaseRule().getActiveId());
					this.setActiveRuleRedisInfo(registeredRuleInfo, 
							registeredRuleInfo.getActiveBaseRule().getClientId(), 
							registeredRuleInfo.getActiveBaseRule().getActiveId());
				}				
			}			
		} catch (Exception e) {
			LogCvt.error("注册活动更新异常：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("注册活动更新失败");
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return resultVo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RegisteredRuleInfo> getRegisteredRuleInfoByPage(Page pageVo,
			RegisteredRuleInfo registeredRuleInfo) {
		List<RegisteredRuleInfo> registeredRuleInfosList = new ArrayList<RegisteredRuleInfo>();
		List<RegisteredInfo> registeredInfosList = new ArrayList<RegisteredInfo>();
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		try {
			RegisteredRuleInfoMapper mapper = sqlSession
					.getMapper(RegisteredRuleInfoMapper.class);
			registeredInfosList = mapper
					.findregisterRuleListByPage(pageVo, registeredRuleInfo.getActiveBaseRule());
			registeredRuleInfosList = this.assembleVouchersInfosList(registeredInfosList);
			LogCvt.debug("分页查询成功，查询到 " + registeredRuleInfosList.size() + "条数据。");
		} catch (Exception e) {
			LogCvt.error("查询活动分页信息异常：" + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return registeredRuleInfosList;
	}

	@Override
	public List<RegisteredExportDetail> getRegisteredExportDetail(
			RegisteredRuleInfo registeredRuleInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		List<RegisteredExportDetail> registeredExportDetailsList = new ArrayList<RegisteredExportDetail>();
		try {
			RegisteredRuleInfoMapper mapper = sqlSession
					.getMapper(RegisteredRuleInfoMapper.class);
			registeredExportDetailsList = mapper
					.findRegisteredExportDetailInfo(registeredRuleInfo);
		} catch (Exception e) {
			LogCvt.error("查询注册活动交易明细异常：" + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return registeredExportDetailsList;
	}
	
	 /**
	  * @Title: assembleVouchersInfosList
	  * @Description: 组装返回Po
	  * @author: shenshaocheng 2015年12月4日
	  * @modify: shenshaocheng 2015年12月4日
	  * @return
	 */
	private List<RegisteredRuleInfo> assembleVouchersInfosList(List<RegisteredInfo> registeredInfoList) {
		List<RegisteredRuleInfo> registeredRuleInfoList = new ArrayList<RegisteredRuleInfo>();
		for(RegisteredInfo registeredInfo : registeredInfoList) {
			ActiveBaseRule baseRule = new ActiveBaseRule();
			ActiveTagRelation tagRelation = new ActiveTagRelation();
			RegistDetailRule detailRule = new RegistDetailRule();
			RegisteredRuleInfo registeredRuleInfo = new RegisteredRuleInfo();
			baseRule.setActiveId(registeredInfo.getActiveId());
			baseRule.setActiveName(registeredInfo.getActiveName());
			baseRule.setActiveLogo(registeredInfo.getActiveLogo());
			baseRule.setBankRate(registeredInfo.getBankRate());
			baseRule.setClientId(registeredInfo.getClientId());
			baseRule.setCreateTime(registeredInfo.getCreateTime());
			baseRule.setDescription(registeredInfo.getDescription());
			baseRule.setExpireEndTime(registeredInfo.getExpireEndTime());
			baseRule.setExpireStartTime(registeredInfo.getExpireStartTime());
			baseRule.setFftRate(registeredInfo.getFftRate());
			baseRule.setLimitType(registeredInfo.getLimitType());
			baseRule.setMerchantRate(registeredInfo.getMerchantRate());
			baseRule.setOperator(registeredInfo.getOperator());
			baseRule.setSettleType(registeredInfo.getSettleType());
			baseRule.setStatus(registeredInfo.getStatus());
			baseRule.setType(registeredInfo.getType());
			baseRule.setUpdateTime(registeredInfo.getUpdateTime());
			
			tagRelation.setActiveId(registeredInfo.getActiveId());
			tagRelation.setClientId(registeredInfo.getClientId());
			tagRelation.setCreateTime(registeredInfo.getCreateTime());
			tagRelation.setItemId(registeredInfo.getItemId());
			tagRelation.setItemType(registeredInfo.getItemType());
			tagRelation.setUpdateTime(registeredInfo.getUpdateTime());
			
			detailRule.setActiveId(registeredInfo.getActiveId());
			detailRule.setClientId(registeredInfo.getClientId());
			detailRule.setAwardCount(registeredInfo.getAwardCount());
			detailRule.setAwardType(registeredInfo.getAwardType());
			detailRule.setCreAwardCount(registeredInfo.getCreAwardCount());
			detailRule.setCreAwardType(registeredInfo.getCreAwardType());
			detailRule.setCreProductId(registeredInfo.getCreProductId());
			detailRule.setCreVouchersActiveId(registeredInfo.getCreVouchersActiveId());
			detailRule.setCutMoney(registeredInfo.getCutMoney());
			detailRule.setTotalMoney(registeredInfo.getTotalMoney());
			detailRule.setIsAwardCre(registeredInfo.getIsAwardCre());
			detailRule.setIsLimitCreCount(registeredInfo.getIsLimitCreCount());
			detailRule.setIsTotalDay(registeredInfo.getIsTotalDay());
			detailRule.setLimitMoney(registeredInfo.getLimitMoney());
			detailRule.setProductCount(registeredInfo.getProductCount());
			detailRule.setProductId(registeredInfo.getProductId());
			detailRule.setTotalCount(registeredInfo.getTotalCount());
			detailRule.setTotalDay(registeredInfo.getTotalDay());
			detailRule.setTriggerType(registeredInfo.getTriggerType());
			detailRule.setVouchersActiveId(registeredInfo.getVouchersActiveId());
			
			registeredRuleInfo.setActiveBaseRule(baseRule);
			registeredRuleInfo.setActiveTagRelation(tagRelation);
			registeredRuleInfo.setRegisteredDetailRule(detailRule);
			
			registeredRuleInfoList.add(registeredRuleInfo);
		}
		
		return registeredRuleInfoList;
	}
	
	private String setRedisProductKeys(String productId) {
		return RedisKeyUtil.cbbank_active_product_product_id(productId);
	}
	
	/**
	 * 设置注册活动Redis
	 * @param registeredRuleInfo 注册活动信息
	 * @param clientId 客户端ID
	 * @param activeId 活动ID
	 */
	private void setActiveRuleRedisInfo(RegisteredRuleInfo registeredRuleInfo,
			String clientId, String activeId) {
		// 1.设置注册活动明细redsi
		RegisterRedis.initRegisterActive(registeredRuleInfo.getActiveBaseRule(), registeredRuleInfo.getRegisteredDetailRule());
		// 2.注册类型为首单时，需要添加满减活动与商品关系redis
		if (registeredRuleInfo.getRegisteredDetailRule()
				.getTriggerType()) {
			// 1、全场满减；2、商品满减
			if (registeredRuleInfo.getActiveTagRelation().getItemType()
					.equals(ActiveItemType.unLimint.getCode())) {
				Long begin = System.currentTimeMillis();
				LogCvt.debug("注册首单限定全场满减 redis产生时间开始 : " + begin);
				List<ProductDetail> productInfoList = productHandler
						.getProductIdAndMerchantIdByClientId(clientId);
				Set<String> productIdSet = new HashSet<String>();
				Set<String> redisKeys = new HashSet<String>();
				for (ProductDetail detail : productInfoList) {
					String productId = detail.getProductId();
					productIdSet.add(productId);
					redisKeys.add(setRedisProductKeys(productId));
				}

				SupportsRedis.set_cbbank_active_product_product_id(
						redisKeys,
						registeredRuleInfo.getActiveBaseRule());
				SupportsRedis.set_cbbank_active_product_info_active_id(
						productIdSet, activeId);

				Long end = System.currentTimeMillis();
				LogCvt.debug("注册首单限定全场满减 redis产生时间结束 : " + end + " 毫秒");
				LogCvt.debug("注册首单限定全场满减  redis产生时间耗费 : " + (end - begin) + " 毫秒");
			} else if(registeredRuleInfo.getActiveTagRelation().getItemType()
					.equals(ActiveItemType.goods.getCode())){
				Long begin = System.currentTimeMillis();
				LogCvt.debug("注册首单限定活动范围[商品] redis产生时间开始 : " + begin);
				// 根据item type 去 cb_weight_activity_tag 获取商品
				List<WeightActivityTag> goodsList = weightActivityTagHandler
						.findWeightActivityTagByActivityIdAndActivityType(registeredRuleInfo.getActiveTagRelation().getItemId(),
								registeredRuleInfo.getActiveTagRelation().getItemType(), clientId);
				// LogCvt.debug("共有 : "+goodsList.size() +"商品参加了活动");
				Set<String> productIdSet = new HashSet<String>();
				Set<String> redisKeys = new HashSet<String>();
				if(goodsList.size() >0)
				{			
					for (WeightActivityTag tag : goodsList) {
					String productId = tag.getElementId();
					productIdSet.add(productId);
					redisKeys.add(setRedisProductKeys(productId));

				}
				SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
						registeredRuleInfo.getActiveBaseRule());
				SupportsRedis.set_cbbank_active_product_info_active_id(
						productIdSet, activeId);
					
				}

				Long end = System.currentTimeMillis();
				LogCvt.debug("注册首单限定活动范围[商品] redis产生时间结束 : " + end + " 毫秒");
				LogCvt.debug("注册首单限定活动范围[商品] redis产生时间耗费 : " + (end - begin) + " 毫秒");
			}
		}
	}
}
