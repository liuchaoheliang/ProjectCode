package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.WeightActivityTagMapper;
import com.froad.handler.WeightActivityTagHandler;
import com.froad.logback.LogCvt;
import com.froad.po.WeightActivityTag;

public class WeightActivityTagHandlerImpl implements WeightActivityTagHandler {

	@Override
	public List<WeightActivityTag> findWeightActivityTagByActivityIdAndActivityType(
			String activityId, String activityType, String clientId) {
		

		List<WeightActivityTag> result = new ArrayList<WeightActivityTag>(); 
		SqlSession sqlSession = null;
		WeightActivityTagMapper weightActivityTagMapper = null;
		try { 
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				weightActivityTagMapper = sqlSession.getMapper(WeightActivityTagMapper.class);
				//MySql查询数据
				result = weightActivityTagMapper.findWeightActivityTagByActivityId(activityId, activityType, clientId);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询WeightActivityTag失败，原因:" + e.getMessage(), e); 
			e.printStackTrace();
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	@Override
	public List<WeightActivityTag> findAllWeightActivityTag(String clientId) {
		List<WeightActivityTag> result = new ArrayList<WeightActivityTag>(); 
		SqlSession sqlSession = null;
		WeightActivityTagMapper weightActivityTagMapper = null;
		try { 
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				weightActivityTagMapper = sqlSession.getMapper(WeightActivityTagMapper.class);
				//MySql查询数据
				result = weightActivityTagMapper.findAllWeightActivityTag(clientId);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询WeightActivityTag失败，原因:" + e.getMessage(), e); 
			e.printStackTrace();
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	@Override
	public int findActive(String clientId, String itemType, String itemId, String activeId) {
		int result = 0;
		SqlSession sqlSession = null;
		WeightActivityTagMapper weightActivityTagMapper = null;
		try { 
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				weightActivityTagMapper = sqlSession.getMapper(WeightActivityTagMapper.class);
				//MySql查询数据
				result = weightActivityTagMapper.findActive(clientId, itemType, itemId, activeId);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询全场活动失败，原因:" + e.getMessage(), e); 
			e.printStackTrace();
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}
	
}
