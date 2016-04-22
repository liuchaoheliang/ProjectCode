/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ActiveBaseRuleHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.ActiveBaseRuleMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;

/**
 * 
 * <p>@Title: ActiveBaseRuleHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveBaseRule的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveBaseRuleHandlerImpl implements ActiveBaseRuleHandler {

    /**
     * 增加 ActiveBaseRule
     * @param activeBaseRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveBaseRule(ActiveBaseRule activeBaseRule)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveBaseRule(sqlSession, activeBaseRule);

			if (null != result) { // 添加成功

				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
			LogCvt.info("添加[addActiveBaseRule]失败"+e.getMessage());
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 增加 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception {

		Long result = null; 
		activeBaseRule.setCreateTime(new Date());
		activeBaseRule.setUpdateTime(new Date());
		ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);
		if (activeBaseRuleMapper.addActiveBaseRule(activeBaseRule) > 0) { // 添加成功
			result = activeBaseRule.getId(); 
		}
		return result; 

	}


    /**
     * 删除 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveBaseRule(sqlSession, activeBaseRule); 
			if (result > 0) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			LogCvt.info("删除[deleteActiveBaseRule]失败"+e.getMessage());
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			 throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveBaseRuleMapper.class).deleteActiveBaseRule(activeBaseRule); 
	}


    /**
     * 根据id删除 ActiveBaseRule
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveBaseRuleById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveBaseRuleById(sqlSession, id); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);
				

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id删除 ActiveBaseRule
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveBaseRuleById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveBaseRuleMapper.class).deleteActiveBaseRuleById(id); 
	}


    /**
     * 修改 ActiveBaseRule
     * @param activeBaseRule
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveBaseRule(sqlSession, activeBaseRule); 
			if (result > 0) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			LogCvt.info("更新[updateActiveBaseRule]失败"+e.getMessage());
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveBaseRule(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveBaseRuleMapper.class).updateActiveBaseRule(activeBaseRule); 
	}


    /**
     * 根据id修改 ActiveBaseRule
     * @param ActiveBaseRuleLogic
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveBaseRuleById(ActiveBaseRule activeBaseRule) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveBaseRuleById(sqlSession, activeBaseRule); 
			if (result > 0) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据id修改 ActiveBaseRule
     * @param sqlSession
     * @param activeBaseRule
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveBaseRuleById(SqlSession sqlSession, ActiveBaseRule activeBaseRule) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveBaseRuleMapper.class).updateActiveBaseRuleById(activeBaseRule); 
	}


    /**
     * 根据id查询单个 ActiveBaseRule
     * @param id
     * @return ActiveBaseRule    单个ActiveBaseRule
     * @throws Exception
     */
	@Override
	public ActiveBaseRule findActiveBaseRuleById(Long id) throws Exception {

		ActiveBaseRule result = null; 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);

			result = activeBaseRuleMapper.findActiveBaseRuleById(id); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询一个 ActiveBaseRule
     * @param activeBaseRule
     * @return ActiveBaseRule    单个ActiveBaseRule
     * @throws Exception
     */
	@Override
	public ActiveBaseRule findOneActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception {

		ActiveBaseRule result = null; 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);

			result = activeBaseRuleMapper.findOneActiveBaseRule(activeBaseRule); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询统计 ActiveBaseRule
     * @param activeBaseRule
     * @return Integer    返回记录数ActiveBaseRule
     * @throws Exception
     */
	@Override
	public Integer countActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);

			result = activeBaseRuleMapper.countActiveBaseRule(activeBaseRule); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 ActiveBaseRule
     * @param activeBaseRule
     * @return List<ActiveBaseRule>    ActiveBaseRule集合 
     * @throws Exception
     */
	@Override
	public List<ActiveBaseRule> findActiveBaseRule(ActiveBaseRule activeBaseRule) throws Exception {

		return this.findActiveBaseRule(activeBaseRule, null); 

	}


    /**
     * 查询 ActiveBaseRule
     * @param activeBaseRule
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveBaseRule>    ActiveBaseRule集合 
     * @throws Exception
     */
	@Override
	public List<ActiveBaseRule> findActiveBaseRule(ActiveBaseRule activeBaseRule, String order) throws Exception {

		List<ActiveBaseRule> result = null; 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);

			result = activeBaseRuleMapper.findActiveBaseRule(activeBaseRule, order); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 ActiveBaseRule
     * @param page
     * @param activeBaseRule
     * @return Page<ActiveBaseRule>    ActiveBaseRule分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ActiveBaseRule> findActiveBaseRuleByPage(Page<ActiveBaseRule> page, ActiveBaseRule activeBaseRule) throws Exception {

		List<ActiveBaseRule> result = new ArrayList<ActiveBaseRule>(); 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);
			if(activeBaseRule.getActiveId() != null && !activeBaseRule.getActiveId().equals("")) {
				result = activeBaseRuleMapper.findSustainRuleInfoByPage(page, activeBaseRule);
			} else {
				result = activeBaseRuleMapper.findSuntainActiveBaseRuleListByPage(page, activeBaseRule); 
			}
			
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.info("分页查询，findActiveBaseRuleByPage 失败"+e.getMessage());
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	@Override
	public ResultBean disableActiveBaseRuleByActiveId(String clientId, String  activeId, String operator) throws Exception {
		ResultBean resultBean=new ResultBean(ResultCode.success,"禁止活动信息成功");
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			Integer dis = this.disableActiveBaseRuleByActiveId(sqlSession,clientId, activeId, operator);  
			if (dis > 0) { // 禁止成功
				//删除redis
				SupportsRedis.del_cbbank_active_product_info(activeId);
				SupportsRedis.del_cbbank_active_merchant_info(activeId);
				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			resultBean= new ResultBean(ResultCode.failed,"禁止活动信息失败");
			LogCvt.error("禁止ActiveRuleInfo失败，原因:" + e.getMessage(), e); 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 
	}


	public Integer disableActiveBaseRuleByActiveId(SqlSession sqlSession,String clientId, String activeId, String operator)
			throws Exception {
		Date updateTime = new Date();
		return sqlSession.getMapper(ActiveBaseRuleMapper.class).disableActiveBaseRuleByActiveId(clientId, activeId, operator, updateTime);
	}


	@Override
	public ActiveBaseRule findOneActiveBaseRuleByActiveNameAndClientId(
			ActiveBaseRule activeBaseRule) {
		SqlSession sqlSession = null;
		ActiveBaseRule result = null; 
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);
			result = activeBaseRuleMapper.findActiveBaseRuleByActiveNameAndClientId(activeBaseRule);
		} catch (Exception e) { 
			LogCvt.error("添加[findOneActiveBaseRuleByActiveNameAndClientId]方法异常, " +e.getMessage());
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	@Override
	public ActiveBaseRule findOneActiveBaseRuleByActiveId(
			ActiveBaseRule activeBaseRule) {
		SqlSession sqlSession = null;
		ActiveBaseRule result = null; 
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);
			result = activeBaseRuleMapper.findActiveBaseRuleByActiveId(activeBaseRule.getActiveId());
		} catch (Exception e) { 
			LogCvt.error("添加[findOneActiveBaseRuleByActiveNameAndClientId]方法异常, " +e.getMessage());
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	@Override
	public List<ActiveBaseRule> findSuntainActiveBaseRule(
			ActiveBaseRule activeBaseRule) {
		List<ActiveBaseRule> result = null; 
		SqlSession sqlSession = null;
		ActiveBaseRuleMapper activeBaseRuleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeBaseRuleMapper = sqlSession.getMapper(ActiveBaseRuleMapper.class);
			
			result = activeBaseRuleMapper.findSuntainActiveBaseRuleList(activeBaseRule); 
		} catch (Exception e) { 
			LogCvt.error("获取红包支持活动列表异常：" + e.getMessage(),e);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	

	

//    /**
//     * 根据id删除 ActiveBaseRule
//     * @param activeId
//     * @return Integer    受影响行数
//     * @throws Exception
//     */
//	@Override
//	public Integer deleteActiveBaseRuleByActiveId(String activeId) throws Exception {
//
//		Integer result = -1; 
//		SqlSession sqlSession = null;
//		try { 
//			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//
//			result = this.deleteActiveBaseRuleByActiveId(sqlSession, activeId); 
//			if (result > -1) { // 删除成功
//
//				sqlSession.commit(true);
//
//			} else { // 删除失败
//				sqlSession.rollback(true); 
//			}
//		} catch (Exception e) { 
//			result = -1; 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
//			throw e; 
//		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
//		} 
//		return result; 
//
//	}
//
//
//    /**
//     * 根据id删除 ActiveBaseRule
//     * @param id
//     * @param sqlSession
//     * @return Integer    受影响行数
//     * @throws Exception
//     */
//	public Integer deleteActiveBaseRuleByActiveId(SqlSession sqlSession, String activeId) throws Exception {
//		/**********************操作MySQL数据库**********************/
//		return sqlSession.getMapper(ActiveBaseRuleMapper.class).deleteActiveBaseRuleByActiveId(activeId); 
//	}	

}