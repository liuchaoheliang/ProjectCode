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
 * @Title: ActiveOrderDetailHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveOrderDetailMapper;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveOrderDetail;

/**
 * 
 * <p>@Title: ActiveOrderDetailHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveOrderDetail的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveOrderDetailHandlerImpl implements ActiveOrderDetailHandler {


    /**
     * 增加 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveOrderDetail(ActiveOrderDetail activeOrderDetail)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveOrderDetail(sqlSession, activeOrderDetail);

			if (null != result) { // 添加成功

				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
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
     * 增加 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {

		Long result = null; 
		ActiveOrderDetailMapper activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);
		if (activeOrderDetailMapper.addActiveOrderDetail(activeOrderDetail) > -1) { // 添加成功
			result = activeOrderDetail.getId(); 
		}
		return result; 

	}

	/**
	 * 批量新增 ActiveOrderDetail
	 * @param sqlSession
	 * @param List<ActiveOrderDetail>
	 * */
	@Override
	public boolean addActiveOrderDetailByBatch(SqlSession sqlSession, List<ActiveOrderDetail> activeOrderDetailList){
		try{
			ActiveOrderDetailMapper activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);
			return activeOrderDetailMapper.addActiveOrderDetailByBatch(activeOrderDetailList);
		}catch(Exception e){
			LogCvt.error("新增订单详情异常 ",e);
			return false;
		}
	}

    /**
     * 删除 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveOrderDetail(sqlSession, activeOrderDetail); 
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
     * 删除 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).deleteActiveOrderDetail(activeOrderDetail); 
	}


    /**
     * 根据id删除 ActiveOrderDetail
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderDetailById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveOrderDetailById(sqlSession, id); 
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
     * 根据id删除 ActiveOrderDetail
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderDetailById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).deleteActiveOrderDetailById(id); 
	}


    /**
     * 修改 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveOrderDetail(sqlSession, activeOrderDetail); 
			if (result > -1) { // 修改成功

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
     * 修改 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetail(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).updateActiveOrderDetail(activeOrderDetail); 
	}
	
	
	
	 /**
     * 修改 ActiveOrderDetail购买商品数量
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetailCount(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).updateActiveOrderDetailCount(activeOrderDetail); 
	}
	
	public Integer updateActiveOrderDetailCountBack(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).updateActiveOrderDetailCountBack(activeOrderDetail); 
	}


    /**
     * 根据id修改 ActiveOrderDetail
     * @param ActiveOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetailById(ActiveOrderDetail activeOrderDetail) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveOrderDetailById(sqlSession, activeOrderDetail); 
			if (result > -1) { // 修改成功

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
     * 根据id修改 ActiveOrderDetail
     * @param sqlSession
     * @param activeOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetailById(SqlSession sqlSession, ActiveOrderDetail activeOrderDetail) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderDetailMapper.class).updateActiveOrderDetailById(activeOrderDetail); 
	}
	
	
	
	  /**
     * 根据orderId修改 ActiveOrderDetail
     * @param ActiveOrderDetail
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderDetailByOrderId(ActiveOrderDetail activeOrderDetail) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = sqlSession.getMapper(ActiveOrderDetailMapper.class).updateActiveOrderDetailByOrderId(activeOrderDetail);  
			if (result > -1) { // 修改成功

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
     * 根据id查询单个 ActiveOrderDetail
     * @param id
     * @return ActiveOrderDetail    单个ActiveOrderDetail
     * @throws Exception
     */
	@Override
	public ActiveOrderDetail findActiveOrderDetailById(Long id) throws Exception {

		ActiveOrderDetail result = null; 
		SqlSession sqlSession = null;
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);

			result = activeOrderDetailMapper.findActiveOrderDetailById(id); 
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
     * 查询一个 ActiveOrderDetail
     * @param activeOrderDetail
     * @return ActiveOrderDetail    单个ActiveOrderDetail
     * @throws Exception
     */
	@Override
	public ActiveOrderDetail findOneActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception {

		ActiveOrderDetail result = null; 
		SqlSession sqlSession = null;
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);

			result = activeOrderDetailMapper.findOneActiveOrderDetail(activeOrderDetail); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	public ActiveOrderDetail findOneActiveOrderDetail(SqlSession sqlSession ,ActiveOrderDetail activeOrderDetail) throws Exception {
		ActiveOrderDetailMapper activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);
		return activeOrderDetailMapper.findOneActiveOrderDetail(activeOrderDetail); 
	}
	
	



    /**
     * 查询统计 ActiveOrderDetail
     * @param activeOrderDetail
     * @return Integer    返回记录数ActiveOrderDetail
     * @throws Exception
     */
	@Override
	public Integer countActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);

			result = activeOrderDetailMapper.countActiveOrderDetail(activeOrderDetail); 
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
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @return List<ActiveOrderDetail>    ActiveOrderDetail集合 
     * @throws Exception
     */
	@Override
	public List<ActiveOrderDetail> findActiveOrderDetail(ActiveOrderDetail activeOrderDetail) throws Exception {

		return this.findActiveOrderDetail(activeOrderDetail, null); 

	}
	
	
    /**
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveOrderDetail>    ActiveOrderDetail集合 
     * @throws Exception
     */
	@Override
	public List<ActiveOrderDetail> findActiveOrderDetail(ActiveOrderDetail activeOrderDetail, String order) throws Exception {

		List<ActiveOrderDetail> result = null; 
		SqlSession sqlSession = null;
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);

			result = activeOrderDetailMapper.findActiveOrderDetail(activeOrderDetail, order); 
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
     * 查询 ActiveOrderDetail
     * @param activeOrderDetail
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveOrderDetail>    ActiveOrderDetail集合 
     * @throws Exception
     */
	@Override
	public List<ActiveOrderDetail> findActiveOrderDetail(SqlSession sqlSession,ActiveOrderDetail activeOrderDetail) throws Exception {
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);
		return activeOrderDetailMapper.findActiveOrderDetail(activeOrderDetail, null); 
	}


    /**
     * 分页查询 ActiveOrderDetail
     * @param page
     * @param activeOrderDetail
     * @return Page<ActiveOrderDetail>    ActiveOrderDetail分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ActiveOrderDetail> findActiveOrderDetailByPage(Page<ActiveOrderDetail> page, ActiveOrderDetail activeOrderDetail) throws Exception {

		List<ActiveOrderDetail> result = new ArrayList<ActiveOrderDetail>(); 
		SqlSession sqlSession = null;
		ActiveOrderDetailMapper activeOrderDetailMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderDetailMapper = sqlSession.getMapper(ActiveOrderDetailMapper.class);

			result = activeOrderDetailMapper.findByPage(page, activeOrderDetail); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}