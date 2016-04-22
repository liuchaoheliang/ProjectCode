/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossOutletRecommendActivityLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月23日下午2:28:26
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientCommonMapper;
import com.froad.db.mysql.mapper.MerchantCommonMapper;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.db.mysql.mapper.RecommendActivityTagMapper;
import com.froad.db.mysql.mapper.WeightActivityTagMapper;
import com.froad.db.redis.ActivityNoLockRedis;
import com.froad.db.redis.RecommendActivityTagRedis;
import com.froad.db.redis.WeightActivityTagRedis;
import com.froad.enums.ActivityTagStatusEnum;
import com.froad.enums.ActivityTypeEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.OutletActivityLogic;
import com.froad.po.Client;
import com.froad.po.InputRelateOutletActivityPo;
import com.froad.po.Merchant;
import com.froad.po.Outlet;
import com.froad.po.OutletWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateOutletActivityPo;
import com.froad.po.WeightActivityTag;
import com.froad.util.Checker;

/**
 * ClassName:BossOutletRecommendActivityLogicImpl
 * Reason:	 门店推荐活动标签管理
 * Date:     2015年10月23日 下午2:28:26
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class OutletActivityLogicImpl implements OutletActivityLogic{

	@Override
	public Page<RecommendActivityTag> findOutletTagByPage(RecommendActivityTag recommend, Page<RecommendActivityTag> page)
			throws Exception {
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper mapper = session.getMapper(RecommendActivityTagMapper.class);
			
			recommend.setActivityType(ActivityTypeEnum.outlet.getType());//查询门店推荐活动
			
			List<RecommendActivityTag> list = mapper.findByPage(recommend, page);
			
			page.setResultsContent(list);
		} catch (Exception e) {
			LogCvt.error("门店推荐活动列表查询异常", e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return page;
	}

	@Override
	public RecommendActivityTag findOutletTagDetail(Long activityId, String clientId, String activityNo, String operator)
			throws FroadBusinessException, Exception {
		SqlSession session = null;
		
		RecommendActivityTag query = null;
		RecommendActivityTag result = new RecommendActivityTag();
		try {
			if(Checker.isEmpty(activityId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "活动ID不能为空!");
			}
			if(Checker.isEmpty(clientId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			boolean flag = false;
			if(Checker.isNotEmpty(operator)){
				ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
				flag = lockRedis.setexLockInfo(clientId, ActivityTypeEnum.outlet.getType(), activityNo, operator);
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			RecommendActivityTagRedis recommendRedis = new RecommendActivityTagRedis();
			
			if(flag){
				// 清空权重关联表数据
				weightMapper.deleteTempWeightInfoByActivityNo(activityId, activityNo, clientId);
				// 重新加载权重关联表数据
				weightMapper.insertTempWeightInfoByActivityNo(activityId, activityNo, clientId);
			}
			
			result = recommendRedis.findRecommendActivityTagInfo(clientId, activityId);
			if(Checker.isEmpty(result)){
				// 缓存查不到就查mysql
				query = new RecommendActivityTag();
				query.setId(activityId);
				query.setClientId(clientId);
				query.setActivityType(ActivityTypeEnum.outlet.getType());
				result = recommendMapper.findActivityDetail(query);
				if(Checker.isNotEmpty(result)){
					// 重新设置缓存
					recommendRedis.addActivityTagRedis(result);
				}else{
					result = new RecommendActivityTag();
				}
			}
			session.commit(true);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			throw e;
		} catch (Exception e) {
			LogCvt.error("门店推荐活动详情查询异常", e);
			session.rollback(true);	
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	@Override
	public Page<OutletWeightActivityTag> findRelateOutletInfoByPage(WeightActivityTag weightTag, Page<OutletWeightActivityTag> page) throws FroadBusinessException, Exception {
		SqlSession session = null;
		try {
			if(Checker.isEmpty(weightTag.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper watMapper = session.getMapper(WeightActivityTagMapper.class);

			List<OutletWeightActivityTag> details = watMapper.findOutletDetailByPage(weightTag, page);
			page.setResultsContent(details);
			
		}catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			throw e;
		} catch (Exception e) {
			LogCvt.error("关联门店分页查询异常", e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return page;
	}

	@Override
	public Boolean enableOutletRecommendActivityTag(RecommendActivityTag recommend) throws FroadBusinessException,
			Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			if(Checker.isEmpty(recommend.getId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "活动ID不能为空!");
			}
			
			if(Checker.isEmpty(recommend.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			
			recommend.setActivityType(ActivityTypeEnum.outlet.getType());
			recommend.setUpdateTime(new Date());
			isSuccess = recommendMapper.updateRecommendActivityTag(recommend);
			
			if(isSuccess){
				//更新缓存
				RecommendActivityTagRedis redis = new RecommendActivityTagRedis();
				redis.updateRecommendStatus(recommend.getClientId(), recommend.getId(), recommend.getStatus());
			}
			
			session.commit(true);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			throw e;
		} catch (Exception e) {
			LogCvt.error("启用/禁用门店推荐活动标签异常", e);
			session.rollback(true);
			isSuccess = false;
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean adjustOutletWeight(WeightActivityTag weight) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			if(Checker.isEmpty(weight.getId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "活动ID不能为空!");
			}
			
			if(Checker.isEmpty(weight.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			if(Checker.isEmpty(weight.getWeight())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "权重不能为空!");
			}else{
				Pattern pattern = Pattern.compile("^-?[0-9]+");
				Matcher isNum = pattern.matcher(weight.getWeight());
				if(!isNum.matches()){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "权重只能为整数!");
				}
			}
			
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			String operator = lockRedis.getLockInfo(weight.getClientId(), ActivityTypeEnum.outlet.getType(), weight.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(weight.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(weight.getClientId(), ActivityTypeEnum.outlet.getType(), weight.getActivityNo(), weight.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			
			weight.setUpdateTime(new Date());
			isSuccess = weightMapper.updateWeightActivityTag(weight);
			
			session.commit(true);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			throw e;
		} catch (Exception e) {
			LogCvt.error("调整关联门店权重异常", e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean deleteRelateOutlet(WeightActivityTag weight) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			if(Checker.isEmpty(weight.getId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "权重关联ID不能为空!");
			}
			
			if(Checker.isEmpty(weight.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			String operator = lockRedis.getLockInfo(weight.getClientId(), ActivityTypeEnum.outlet.getType(), weight.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(weight.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(weight.getClientId(), ActivityTypeEnum.outlet.getType(), weight.getActivityNo(), weight.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			
			isSuccess = weightMapper.delWeightActivityTag(weight.getId(), weight.getClientId());
			
			session.commit(true);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			throw e;
		} catch (Exception e) {
			LogCvt.error("删除关联门店异常", e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean addOutletActivityTag(RecommendActivityTag recommend) throws Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			RecommendActivityTagRedis recommendRedis = new RecommendActivityTagRedis();
			WeightActivityTagRedis weightRedis = new WeightActivityTagRedis();
			
			recommend.setActivityType(ActivityTypeEnum.outlet.getType());
			recommend.setStatus(ActivityTagStatusEnum.enable.getStatus());
			recommend.setCreateTime(new Date());
			recommendMapper.addRecommendActivityTag(recommend);
			
			recommendRedis.addActivityTagRedis(recommend);
			recommendRedis.recordActivityId(recommend);
			
			// 更新临时表中的activityId
			weightMapper.updateTempActivityIdByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			//根据活动编号把临时表数据加载到主表中
			weightMapper.insertWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			List<WeightActivityTag> weightTags = weightMapper.findWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			for(WeightActivityTag wt : weightTags){
				// 添加权重关联缓存信息
				weightRedis.addWeightActivityTagRedis(wt);
			}
			
			// 清空临时表信息
			weightMapper.deleteTempWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			
			
			// 清除缓存锁
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			lockRedis.delLockInfo(recommend.getClientId(), ActivityTypeEnum.outlet.getType(), recommend.getActivityNo());
			
			session.commit(true);
			isSuccess = true;
		} catch (Exception e) {
			LogCvt.error("添加门店推荐活动标签异常", e);
			session.rollback(true);
			isSuccess = false;
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	
	@Override
	public Boolean updateOutletActivityTag(RecommendActivityTag recommend) throws Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			RecommendActivityTagRedis recommendRedis = new RecommendActivityTagRedis();
			WeightActivityTagRedis weightRedis = new WeightActivityTagRedis();
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			
			// 编辑缓存锁信息
			String operator = lockRedis.getLockInfo(recommend.getClientId(), ActivityTypeEnum.outlet.getType(), recommend.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(recommend.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(recommend.getClientId(), ActivityTypeEnum.outlet.getType(), recommend.getActivityNo(), recommend.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			recommend.setActivityType(ActivityTypeEnum.outlet.getType());
			recommend.setUpdateTime(new Date());
			// 更新推荐活动信息
			recommendMapper.updateRecommendActivityTag(recommend);
			// 刷新推荐活动缓存信息
			recommendRedis.updateActivityTagRedis(recommend);
			
			// 先根据主表清除权重关联缓存所有信息
			List<WeightActivityTag> weightInfos = weightMapper.findWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			for(WeightActivityTag weightTag : weightInfos){
				weightRedis.delWeightActivityTagInfo(weightTag.getClientId(), weightTag.getId());
			}

			// 清除主表数据
			weightMapper.deleteWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			// 把权重关联临时表数据全部刷到主表中
			weightMapper.insertWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			weightInfos = weightMapper.findWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			for(WeightActivityTag weightTag : weightInfos){
				// 重新塞缓存
				weightRedis.addWeightActivityTagRedis(weightTag);
			}
			// 清除临时表数据
			weightMapper.deleteTempWeightInfoByActivityNo(recommend.getId(), recommend.getActivityNo(), recommend.getClientId());
			
			session.commit(true);
			isSuccess = true;
			// 删除活动锁缓存
			lockRedis.delLockInfo(recommend.getClientId(), ActivityTypeEnum.outlet.getType(), recommend.getActivityNo());
						
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			throw e;
		} catch (Exception e) {
			LogCvt.error("修改门店活动", e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean relateOutletInfo(RelateOutletActivityPo po) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			OutletMapper outletMapper = session.getMapper(OutletMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			
			if(Checker.isEmpty(po.getOutletId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店Id不能为空!");
			}
			if(Checker.isEmpty(po.getWeight())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "权重不能为空!");
			}else{
				Pattern pattern = Pattern.compile("^-?[0-9]+");
				Matcher isNum = pattern.matcher(po.getWeight());
				if(!isNum.matches()){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "权重只能为整数!");
				}
			}
			if(Checker.isEmpty(po.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			String operator = lockRedis.getLockInfo(po.getClientId(), ActivityTypeEnum.outlet.getType(), po.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(po.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(po.getClientId(), ActivityTypeEnum.outlet.getType(), po.getActivityNo(), po.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			Client client = clientMapper.findClientById(po.getClientId());
			Outlet outlet = outletMapper.getOutletByOutletId(po.getOutletId());
			if(Checker.isEmpty(outlet)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店Id:"+po.getOutletId()+" 不存在!");
			}
			if(!po.getClientId().equals(outlet.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店Id:"+po.getOutletId()+" 不属于"+client.getName()+"客户端!");
			}
			
			Long activityId = null == po.getActivityId() || 0l == po.getActivityId() ? null : po.getActivityId();
			int count = weightMapper.findTempRelateActivityCount(activityId, po.getActivityNo(), po.getClientId(), outlet.getOutletId());
			if(count == 0){
				WeightActivityTag weightTag = new WeightActivityTag();
				weightTag.setClientId(po.getClientId());
				weightTag.setActivityId(activityId);
				weightTag.setActivityNo(po.getActivityNo());
				weightTag.setElementId(outlet.getOutletId());
				weightTag.setWeight(po.getWeight());
				weightTag.setActivityType(ActivityTypeEnum.outlet.getType());
				weightTag.setOperator(po.getOperator());
				weightTag.setCreateTime(new Date());
				// 添加临时表记录
				weightMapper.addTempWeightActivityTag(weightTag);
			}else{
				WeightActivityTag weightTag = new WeightActivityTag();
				weightTag.setClientId(po.getClientId());
				weightTag.setActivityId(activityId);
				weightTag.setActivityNo(po.getActivityNo());
				weightTag.setElementId(outlet.getOutletId());
				weightTag.setWeight(po.getWeight());
				weightTag.setActivityType(ActivityTypeEnum.outlet.getType());
				weightTag.setOperator(po.getOperator());
				weightTag.setUpdateTime(new Date());
				// 如存在,更新记录
				weightMapper.updateWeightActivityTag(weightTag);
			}
			
			session.commit(true);
			isSuccess = true;
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} catch (Exception e) {
			LogCvt.error("关联门店异常", e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public Boolean inputrelateOutletInfo(List<InputRelateOutletActivityPo> infos, String clientId, Long activityId,
			String activityNo, String operator) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			OutletMapper outletMapper = session.getMapper(OutletMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			Pattern pattern = null;
			Matcher isNum = null;
			Outlet outlet = null;
			
			if(Checker.isEmpty(clientId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			if(Checker.isEmpty(activityNo)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "活动编号不能为空!");
			}
			if(Checker.isEmpty(operator)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "操作员不能为空!");
			}
			
			ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
			String redisOperator = lockRedis.getLockInfo(clientId, ActivityTypeEnum.outlet.getType(), activityNo);
			if(Checker.isNotEmpty(redisOperator) && !redisOperator.equals(operator)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(redisOperator)){
				boolean flag = lockRedis.setexLockInfo(clientId, ActivityTypeEnum.outlet.getType(), activityNo, operator);
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			Client client = clientMapper.findClientById(clientId);
			for(InputRelateOutletActivityPo info : infos){
				if(!activityNo.equals(info.getActivityNo())){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行数据活动编号与本次活动不一致。请修改后重新操作。");
				}
				
				if(Checker.isEmpty(info.getWeight())){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "权重不能为空!");
				}else{
					pattern = Pattern.compile("^-?[0-9]+");
					isNum = pattern.matcher(info.getWeight());
					if(!isNum.matches()){
						throw new FroadBusinessException(ResultCode.failed.getCode(), "权重只能为整数!");
					}
				}
				
				outlet = outletMapper.getOutletByOutletId(info.getOutletId());
				if(Checker.isEmpty(outlet)){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行门店ID不存在!");
				}else{
					if(!clientId.equals(outlet.getClientId())){
						throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行门店ID不属于"+client.getName()+"客户端!");
					}
				}
				
				activityId = null == activityId || 0l == activityId ? null : activityId;
				int count = weightMapper.findTempRelateActivityCount(activityId, activityNo, clientId, info.getOutletId());
				if(count == 0){
					WeightActivityTag wt  = new WeightActivityTag();
					wt.setClientId(clientId);
					wt.setActivityId(activityId);
					wt.setActivityNo(activityNo);
					wt.setElementId(info.getOutletId());
					wt.setWeight(info.getWeight());
					wt.setActivityType(ActivityTypeEnum.outlet.getType());
					wt.setOperator(operator);
					wt.setCreateTime(new Date());
					// 添加临时表记录
					weightMapper.addTempWeightActivityTag(wt);
				}else{
					WeightActivityTag wt = new WeightActivityTag();
					wt.setClientId(clientId);
					wt.setActivityId(activityId);
					wt.setActivityNo(activityNo);
					wt.setElementId(info.getOutletId());
					wt.setWeight(info.getWeight());
					wt.setActivityType(ActivityTypeEnum.outlet.getType());
					wt.setOperator(operator);
					wt.setUpdateTime(new Date());
					// 如存在,更新记录
					weightMapper.updateWeightActivityTag(wt);
				}
			}
			
			session.commit(true);
			isSuccess = true;
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} catch (Exception e) {
			LogCvt.error("解析上传关联门店信息异常", e);
			isSuccess = false;
			session.rollback(true);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return isSuccess;
	}

	@Override
	public String queryOutletNameAndMerchantNameByOutletId(String clientId, String outletId) throws FroadBusinessException, Exception {
		SqlSession session = null;
		String name = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			OutletMapper outletMapper = session.getMapper(OutletMapper.class);
			MerchantCommonMapper merchantCommonMapper = session.getMapper(MerchantCommonMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			
			Client client = clientMapper.findClientById(clientId);
			Outlet outlet = outletMapper.getOutletByOutletId(outletId);//根据门店id查询门店信息
			if(Checker.isEmpty(outlet)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店id:" + outletId + " 不存在!");
			}
			if(!outlet.getClientId().equals(clientId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店Id:"+outletId+" 不属于"+client.getName()+"客户端!");
			}
			
			String outletName = outlet.getOutletName();
			
			Merchant merchant = merchantCommonMapper.findMerchantByMerchantId(outlet.getMerchantId());//根据门店信息里的商户id查询商户信息
			if(Checker.isEmpty(merchant)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "门店id:" + outletId + " 所属商户不存在!");
			}
			
			String merchantName = merchant.getMerchantName();
			name = outletName + "," + merchantName;
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			name = "";
			throw e;
		} catch (Exception e) {
			LogCvt.error("根据门店id查询门店名称和商户名称异常", e);
			name = "";
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return name;
	}

	@Override
	public List<WeightActivityTag> findAllRelateOutlets(Long activityId, String clientId, String activityNo)
			throws FroadBusinessException, Exception {
		List<WeightActivityTag> weightTags = new ArrayList<WeightActivityTag>();
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper tagMapper = session.getMapper(WeightActivityTagMapper.class);
			
			weightTags = tagMapper.queryRelateOutlets(activityId, clientId, activityNo);
		} catch (Exception e) {
			LogCvt.error("查询所有关联商户活动标签信息异常", e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return weightTags;
	}


	
}
