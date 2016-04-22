/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductActivityTagLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月28日上午11:35:35
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientCommonMapper;
import com.froad.db.mysql.mapper.ProductCommonMapper;
import com.froad.db.mysql.mapper.RecommendActivityTagMapper;
import com.froad.db.mysql.mapper.WeightActivityTagMapper;
import com.froad.db.redis.ActivityNoLockRedis;
import com.froad.db.redis.RecommendActivityTagRedis;
import com.froad.db.redis.WeightActivityTagRedis;
import com.froad.enums.ActivityTagStatusEnum;
import com.froad.enums.ActivityTypeEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductActivityTagLogic;
import com.froad.po.Client;
import com.froad.po.InputRelateProductActivityPo;
import com.froad.po.Product;
import com.froad.po.ProductWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateProductActivityPo;
import com.froad.po.WeightActivityTag;
import com.froad.util.Checker;

/**
 * ClassName:ProductActivityTagLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 上午11:35:35
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProductActivityTagLogicImpl implements ProductActivityTagLogic {

	@Override
	public Page<RecommendActivityTag> findProductTagByPage(RecommendActivityTag recommend,
			Page<RecommendActivityTag> page) throws Exception {
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper mapper = session.getMapper(RecommendActivityTagMapper.class);
			
			recommend.setActivityType(ActivityTypeEnum.product.getType());
			
			List<RecommendActivityTag> list = mapper.findByPage(recommend, page);
			
			page.setResultsContent(list);
		} catch (Exception e) {
			LogCvt.error("商品推荐活动列表查询异常", e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		
		return page;
	}

	@Override
	public RecommendActivityTag findProductTagDetail(Long id, String clientId, String activityNo, String operator)
			throws FroadBusinessException, Exception {
		SqlSession session = null;
		
		RecommendActivityTag query = null;
		RecommendActivityTag result = new RecommendActivityTag();
		try {
			if(Checker.isEmpty(id)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "活动ID不能为空!");
			}
			if(Checker.isEmpty(clientId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			boolean flag = false;
			if(Checker.isNotEmpty(operator)){
				ActivityNoLockRedis lockRedis = new ActivityNoLockRedis();
				flag = lockRedis.setexLockInfo(clientId, ActivityTypeEnum.product.getType(), activityNo, operator);
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			RecommendActivityTagRedis recommendRedis = new RecommendActivityTagRedis();
			
			if(flag){
				// 清空权重关联表数据
				weightMapper.deleteTempWeightInfoByActivityNo(id, activityNo, clientId);
				// 重新加载权重关联表数据
				weightMapper.insertTempWeightInfoByActivityNo(id, activityNo, clientId);
			}
			
			result = recommendRedis.findRecommendActivityTagInfo(clientId, id);
			if(Checker.isEmpty(result)){
				// 缓存查不到就查mysql
				query = new RecommendActivityTag();
				query.setId(id);
				query.setClientId(clientId);
				query.setActivityType(ActivityTypeEnum.product.getType());
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
			LogCvt.error("商品推荐活动详情查询异常", e);
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
	public Page<ProductWeightActivityTag> findRelateProductInfoByPage(WeightActivityTag weightTag,
			Page<ProductWeightActivityTag> page) throws FroadBusinessException, Exception {
		SqlSession session = null;
		try {
			if(Checker.isEmpty(weightTag.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "客户端号不能为空!");
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			
			List<ProductWeightActivityTag> details = weightMapper.findProductDetailByPage(weightTag, page);
			page.setResultsContent(details);
			
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			throw e;
		} catch (Exception e) {
			LogCvt.error("关联商品分页查询异常", e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return page;
	}

	@Override
	public Boolean enableProductRecommendActivityTag(RecommendActivityTag recommend) throws FroadBusinessException,
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
			
			recommend.setActivityType(ActivityTypeEnum.product.getType());
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
			LogCvt.error("启用/禁用商品推荐活动标签异常", e);
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
	public Boolean adjustProductWeight(WeightActivityTag weight) throws FroadBusinessException, Exception {
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
			String operator = lockRedis.getLockInfo(weight.getClientId(), ActivityTypeEnum.product.getType(), weight.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(weight.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(weight.getClientId(), ActivityTypeEnum.product.getType(), weight.getActivityNo(), weight.getOperator());
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
			LogCvt.error("调整关联商品权重异常", e);
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
	public Boolean deleteRelateProduct(WeightActivityTag weight) throws FroadBusinessException, Exception {
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
			String operator = lockRedis.getLockInfo(weight.getClientId(), ActivityTypeEnum.product.getType(), weight.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(weight.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(weight.getClientId(), ActivityTypeEnum.product.getType(), weight.getActivityNo(), weight.getOperator());
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
			LogCvt.error("删除关联商品异常", e);
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
	public Boolean addProductActivityTag(RecommendActivityTag recommend) throws Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			RecommendActivityTagMapper recommendMapper = session.getMapper(RecommendActivityTagMapper.class);
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			RecommendActivityTagRedis recommendRedis = new RecommendActivityTagRedis();
			WeightActivityTagRedis weightRedis = new WeightActivityTagRedis();
			
			recommend.setActivityType(ActivityTypeEnum.product.getType());
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
			lockRedis.delLockInfo(recommend.getClientId(), ActivityTypeEnum.product.getType(), recommend.getActivityNo());
			
			session.commit(true);
			isSuccess = true;
		} catch (Exception e) {
			LogCvt.error("添加商品推荐活动标签异常", e);
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
	public Boolean updateProductActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception {
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
			String operator = lockRedis.getLockInfo(recommend.getClientId(), ActivityTypeEnum.product.getType(), recommend.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(recommend.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(recommend.getClientId(), ActivityTypeEnum.product.getType(), recommend.getActivityNo(), recommend.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			recommend.setActivityType(ActivityTypeEnum.product.getType());
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
			lockRedis.delLockInfo(recommend.getClientId(), ActivityTypeEnum.product.getType(), recommend.getActivityNo());
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			isSuccess = false;
			throw e;
		} catch (Exception e) {
			LogCvt.error("修改商品活动异常", e);
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
	public Boolean relateProductInfo(RelateProductActivityPo po) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			ProductCommonMapper productMapper = session.getMapper(ProductCommonMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			
			if(Checker.isEmpty(po.getProductId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品id不能为空!");
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
			String operator = lockRedis.getLockInfo(po.getClientId(), ActivityTypeEnum.product.getType(), po.getActivityNo());
			if(Checker.isNotEmpty(operator) && !operator.equals(po.getOperator())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(operator)){
				boolean flag = lockRedis.setexLockInfo(po.getClientId(), ActivityTypeEnum.product.getType(), po.getActivityNo(), po.getOperator());
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			Client client = clientMapper.findClientById(po.getClientId());
			Product product = null;
			Map<String, String> param = new HashMap<String, String>();
			param.put("productId", po.getProductId());
			List<Product> products = productMapper.getProductByPid(param);
			if(Checker.isEmpty(products)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+po.getProductId()+" 信息不存在!");
			}
			for(Product p : products){
				if(ProductAuditState.passAudit.getCode().equals(p.getAuditState())){
					product = p;
					break;
				}
			}
			if(Checker.isEmpty(product)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+po.getProductId()+" 所属商品审核未通过!");
			}
			
			if(!po.getClientId().equals(product.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+po.getProductId()+" 所属商品不属于"+client.getName()+"客户端!");
			}
			
			Long activityId = null == po.getActivityId() || 0l == po.getActivityId() ? null : po.getActivityId();
			int count = weightMapper.findTempRelateActivityCount(activityId, po.getActivityNo(), po.getClientId(), product.getProductId());
			if(count == 0){
				WeightActivityTag weightTag = new WeightActivityTag();
				weightTag.setClientId(po.getClientId());
				weightTag.setActivityId(activityId);
				weightTag.setActivityNo(po.getActivityNo());
				weightTag.setElementId(product.getProductId());
				weightTag.setWeight(po.getWeight());
				weightTag.setActivityType(ActivityTypeEnum.product.getType());
				weightTag.setOperator(po.getOperator());
				weightTag.setCreateTime(new Date());
				// 添加临时表记录
				weightMapper.addTempWeightActivityTag(weightTag);
			}else{
				WeightActivityTag weightTag = new WeightActivityTag();
				weightTag.setClientId(po.getClientId());
				weightTag.setActivityId(activityId);
				weightTag.setActivityNo(po.getActivityNo());
				weightTag.setActivityType(ActivityTypeEnum.product.getType());
				weightTag.setElementId(product.getProductId());
				weightTag.setWeight(po.getWeight());
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
			LogCvt.error("关联商品异常", e);
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
	public String queryProductNameByProductId(String clientId, String productId) throws FroadBusinessException,
			Exception {
		SqlSession session = null;
		String productName = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ProductCommonMapper productMapper = session.getMapper(ProductCommonMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			
			Client client = clientMapper.findClientById(clientId);
			Product product = null;
			Map<String, String> param = new HashMap<String, String>();
			param.put("productId", productId);
			List<Product> products = productMapper.getProductByPid(param);
			if(Checker.isEmpty(products)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+productId+" 信息不存在!");
			}
			for(Product p : products){
				if(ProductAuditState.passAudit.getCode().equals(p.getAuditState())){
					product = p;
					break;
				}
			}
			if(Checker.isEmpty(product)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+productId+" 所属商品审核未通过!");
			}
			
			if(!clientId.equals(product.getClientId())){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "商品ID:"+productId+" 所属商品不属于"+client.getClientId()+"客户端!");
			}
			
			productName = product.getName();
			
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			productName = "";
			throw e;
		} catch (Exception e) {
			LogCvt.error("根据营业执照查询商户名称异常", e);
			productName = "";
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return productName;
	}

	@Override
	public Boolean inputrelateProductInfo(List<InputRelateProductActivityPo> infos, String clientId, Long activityId,
			String activityNo, String operator) throws FroadBusinessException, Exception {
		SqlSession session = null;
		Boolean isSuccess = false;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper weightMapper = session.getMapper(WeightActivityTagMapper.class);
			ProductCommonMapper productMapper = session.getMapper(ProductCommonMapper.class);
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			Pattern pattern = null;
			Matcher isNum = null;
			Product product = null;
			
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
			String redisOperator = lockRedis.getLockInfo(clientId, ActivityTypeEnum.product.getType(), activityNo);
			if(Checker.isNotEmpty(redisOperator) && !redisOperator.equals(operator)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
			}else if(Checker.isEmpty(redisOperator)){
				boolean flag = lockRedis.setexLockInfo(clientId, ActivityTypeEnum.product.getType(), activityNo, operator);
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "该活动信息正在被别的用户修改，请稍后再操作。");
				}
			}
			
			Client client = clientMapper.findClientById(clientId);
			for(InputRelateProductActivityPo info : infos){
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
				
				Map<String, String> param = new HashMap<String, String>();
				param.put("productId", info.getProductId());
				List<Product> products = productMapper.getProductByPid(param);
				
				if(Checker.isEmpty(products)){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行商品ID:"+info.getProductId()+" 信息不存在!");
				}
				
				for(Product p : products){
					if(ProductAuditState.passAudit.getCode().equals(p.getAuditState())){
						product = p;
						break;
					}
				}
				
				if(Checker.isEmpty(product)){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行商品ID:"+info.getProductId()+" 所属商品审核未通过!");
				}
				
				if(!clientId.equals(product.getClientId())){
					throw new FroadBusinessException(ResultCode.failed.getCode(), "第"+info.getId()+"行商品ID:"+info.getProductId()+" 所属商品不属于"+client.getName()+"客户端!");
				}
				
				activityId = null == activityId || 0l == activityId ? null : activityId;
				int count = weightMapper.findTempRelateActivityCount(activityId, activityNo, clientId, info.getProductId());
				if(count == 0){
					WeightActivityTag wt  = new WeightActivityTag();
					wt.setClientId(clientId);
					wt.setActivityId(activityId);
					wt.setActivityNo(activityNo);
					wt.setElementId(info.getProductId());
					wt.setWeight(info.getWeight());
					wt.setActivityType(ActivityTypeEnum.product.getType());
					wt.setOperator(operator);
					wt.setCreateTime(new Date());
					// 添加临时表记录
					weightMapper.addTempWeightActivityTag(wt);
				}else{
					WeightActivityTag wt = new WeightActivityTag();
					wt.setClientId(clientId);
					wt.setActivityId(activityId);
					wt.setActivityNo(activityNo);
					wt.setElementId(info.getProductId());
					wt.setWeight(info.getWeight());
					wt.setActivityType(ActivityTypeEnum.product.getType());
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
			LogCvt.error("解析上传关联商品信息异常", e);
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
	public List<WeightActivityTag> findAllRelateProducts(Long activityId, String clientId, String activityNo)
			throws FroadBusinessException, Exception {
		List<WeightActivityTag> weightTags = new ArrayList<WeightActivityTag>();
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WeightActivityTagMapper tagMapper = session.getMapper(WeightActivityTagMapper.class);
			
			weightTags = tagMapper.queryRelateProducts(activityId, clientId, activityNo);
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
