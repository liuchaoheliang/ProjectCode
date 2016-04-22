package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ActiveSerchProductMapper;
import com.froad.handler.ActiveSearchProductHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.po.WeightActivityTag;

public class ActiveSearchProductHandlerImpl implements ActiveSearchProductHandler{

	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 查询商品活动信息数据逻辑实现
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo
	  * @return
	  * @see com.froad.handler.ActiveSearchMerchantHandler#findActiveRuleByMerchantIds(
	  * com.froad.po.ActiveSearchMerchantInfo)
	  */	
	@Override
	public List<ActiveResultInfo> findActiveRuleByProductIds(
			ActiveSearchProductInfo activeSearchProductInfo) {		
		/* ------------------  mysql中查找 -------------------*/
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
		try {
			ActiveSerchProductMapper mapper = sqlSession.getMapper(ActiveSerchProductMapper.class); 
			activeResultInfoList = 
					mapper.findActiveProductInfo(activeSearchProductInfo);
		} catch (Exception e) {
			LogCvt.error("获取商品活动信息 " + e.getMessage(), e);
		} finally {
			if(null != sqlSession) {  
				sqlSession.close(); 
			}
		}
		
		return activeResultInfoList;
	}

	 /**
	  * @Title: findProductIdsByactiveId
	  * @Description: 获取对应活动的商品ID集合
	  * @author: shenshaocheng 2015年11月10日
	  * @modify: shenshaocheng 2015年11月10日
	  * @param activeId
	  * @return
	  * @see com.froad.handler.ActiveSearchProductHandler#findProductIdsByactiveId(java.lang.String)
	  */	
	@Override
	public List<WeightActivityTag> findProductIdsByactiveId(String activeId, String clientId) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<WeightActivityTag> productIdSet = new ArrayList<WeightActivityTag>();
		try {
			ActiveSerchProductMapper mapper = sqlSession.getMapper(ActiveSerchProductMapper.class); 
			productIdSet = mapper.findProductIdSet(activeId, clientId);
		} catch (Exception e) {
			LogCvt.error("获取商品ID集合 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return productIdSet;
	}

	@Override
	public List<WeightActivityTag> findProductIdsByMerchant(String activeId,
			String clientId) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<WeightActivityTag> productIdSet = new ArrayList<WeightActivityTag>();
		try {
			ActiveSerchProductMapper mapper = sqlSession.getMapper(ActiveSerchProductMapper.class); 
			productIdSet = mapper.findProductIdSetByMerchant(activeId, clientId);
		} catch (Exception e) {
			LogCvt.error("获取商品ID集合 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return productIdSet;
	}

	@Override
	public List<WeightActivityTag> findProductIdsByOutlet(String activeId,
			String clientId) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<WeightActivityTag> productIdSet = new ArrayList<WeightActivityTag>();
		try {
			ActiveSerchProductMapper mapper = sqlSession.getMapper(ActiveSerchProductMapper.class); 
			productIdSet = mapper.findProductIdSetByOutlet(activeId, clientId);
		} catch (Exception e) {
			LogCvt.error("获取商品ID集合 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return productIdSet;
	}

	@Override
	public List<ActiveResultInfo> findOverdueActivitiesByIds(
			List<String> activeIdList) {
		/* ------------------  mysql中查找 -------------------*/
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
		try {
			ActiveSerchProductMapper mapper = sqlSession.getMapper(ActiveSerchProductMapper.class); 
			activeResultInfoList = 
					mapper.findOverdueActivitiesByIds(activeIdList);
		} catch (Exception e) {
			LogCvt.error("获取商品活动信息 " + e.getMessage(), e);
		} finally {
			if(null != sqlSession) {  
				sqlSession.close(); 
			}
		}
		
		return activeResultInfoList;
	}

}
