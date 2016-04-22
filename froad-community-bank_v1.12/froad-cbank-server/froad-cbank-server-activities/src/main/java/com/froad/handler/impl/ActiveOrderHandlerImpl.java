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
 * @Title: ActiveOrderHandlerImpl.java
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
import com.froad.db.mysql.mapper.ActiveOrderMapper;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;

/**
 * 
 * <p>@Title: ActiveOrderHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体ActiveOrder的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ActiveOrderHandlerImpl implements ActiveOrderHandler {

	private static ActiveOrderDetailHandler activeOrderDetailHandler = new ActiveOrderDetailHandlerImpl();

    /**
     * 增加 ActiveOrder
     * @param activeOrder
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveOrder(ActiveOrder activeOrder)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addActiveOrder(sqlSession, activeOrder);
			LogCvt.debug("新增数据表【营销订单】 "+activeOrder.getId()+" 结果:"+result);
			
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
     * 增加 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception {

		Long result = null; 
		ActiveOrderMapper activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);
		result=activeOrderMapper.addActiveOrder(activeOrder) ;
		if (result > -1) { // 添加成功
			return result;
		}
		return result; 

	}

	/**
	 * 增加 营销订单主表 和 营销订单详情
	 * @param ActiveOrder
	 * @param List<ActiveOrderDetail>
	 * return boolean
	 * */
	@Override
	public boolean addActiveOrderAndOrderDetailList(ActiveOrder activeOrder, List<ActiveOrderDetail> activeOrderDetailList){
		SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			Long result = this.addActiveOrder(sqlSession, activeOrder);
			LogCvt.debug("新增【营销订单主表】 "+activeOrder.getId()+" 结果:"+result);
			
			if (null != result) { // 新增 营销订单主表 成功
			
				boolean batchResult = activeOrderDetailHandler.addActiveOrderDetailByBatch(sqlSession, activeOrderDetailList);
				LogCvt.debug("新增【营销订单详情】 "+activeOrderDetailList.size()+"条 - 结果:"+batchResult);
				
				if( batchResult ){ // 批量新增 营销订单详情 成功
					sqlSession.commit(true);
					return true;
				}else{// 批量新增 营销订单详情 失败
					sqlSession.rollback(true);
					return false;
				}
				
			}else{ // 新增 营销订单主表 失败
				sqlSession.rollback(true);
				return false;
			}
		} catch (Exception e) { 
			LogCvt.error("新增订单信息异常 ",e);
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			return false;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	}

	/**
	 * 修改 营销订单主表详情 和 新增营销订单详情列表
	 * @param ActiveOrder
	 * @param List<ActiveOrderDetail>
	 * return boolean
	 * */
	@Override
	public boolean updateActiveOrderdetailAndAddOrderDetailList(ActiveOrder activeOrder, List<ActiveOrderDetail> activeOrderDetailList){
		SqlSession sqlSession = null;
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			Integer result = this.updateActiveOrder(sqlSession, activeOrder);
			LogCvt.debug("修改【营销订单主表】详情 "+activeOrder.getId()+" 结果:"+result);
			
			if (null != result && result > 0 ) { // 新增 营销订单主表 成功
				
				boolean batchResult = activeOrderDetailHandler.addActiveOrderDetailByBatch(sqlSession, activeOrderDetailList);
				LogCvt.debug("新增【营销订单详情】 "+activeOrderDetailList.size()+"条 - 结果:"+batchResult);
				
				if( batchResult ){ // 批量新增 营销订单详情 成功
					sqlSession.commit(true);
					return true;
				}else{// 批量新增 营销订单详情 失败
					sqlSession.rollback(true);
					return false;
				}
				
			}else{ // 新增 营销订单主表 失败
				sqlSession.rollback(true);
				return false;
			}
		} catch (Exception e) { 
			LogCvt.error("修改 营销订单主表详情 和 新增营销订单详情列表异常 ",e);
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			return false;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	}
	
    /**
     * 删除 ActiveOrder
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrder(ActiveOrder activeOrder) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveOrder(sqlSession, activeOrder); 
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
     * 删除 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderMapper.class).deleteActiveOrder(activeOrder); 
	}


    /**
     * 根据id删除 ActiveOrder
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteActiveOrderById(sqlSession, id); 
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
     * 根据id删除 ActiveOrder
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteActiveOrderById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderMapper.class).deleteActiveOrderById(id); 
	}


    /**
     * 修改 ActiveOrder
     * @param activeOrder
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrder(ActiveOrder activeOrder) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveOrder(sqlSession, activeOrder); 
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
     * 修改 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrder(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderMapper.class).updateActiveOrder(activeOrder); 
	}
	
	
    /**
     * 根据id修改 ActiveOrder
     * @param ActiveOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderById(ActiveOrder activeOrder) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateActiveOrderById(sqlSession, activeOrder); 
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
     * 根据id修改 ActiveOrder
     * @param sqlSession
     * @param activeOrder
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateActiveOrderById(SqlSession sqlSession, ActiveOrder activeOrder) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(ActiveOrderMapper.class).updateActiveOrderById(activeOrder); 
	}


    /**
     * 根据id查询单个 ActiveOrder
     * @param id
     * @return ActiveOrder    单个ActiveOrder
     * @throws Exception
     */
	@Override
	public ActiveOrder findActiveOrderById(Long id) throws Exception {

		ActiveOrder result = null; 
		SqlSession sqlSession = null;
		ActiveOrderMapper activeOrderMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);

			result = activeOrderMapper.findActiveOrderById(id); 
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
     * 查询一个 ActiveOrder
     * @param activeOrder
     * @return ActiveOrder    单个ActiveOrder
     * @throws Exception
     */
	@Override
	public ActiveOrder findOneActiveOrder(ActiveOrder activeOrder) throws Exception {

		ActiveOrder result = null; 
		SqlSession sqlSession = null;
		ActiveOrderMapper activeOrderMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);

			result = activeOrderMapper.findOneActiveOrder(activeOrder); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	public ActiveOrder findOneActiveOrder(SqlSession sqlSession,ActiveOrder activeOrder) throws Exception {
		ActiveOrderMapper activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);
		return  activeOrderMapper.findOneActiveOrder(activeOrder); 
	}


    /**
     * 查询统计 ActiveOrder
     * @param activeOrder
     * @return Integer    返回记录数ActiveOrder
     * @throws Exception
     */
	@Override
	public Integer countActiveOrder(ActiveOrder activeOrder) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		ActiveOrderMapper activeOrderMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);

			result = activeOrderMapper.countActiveOrder(activeOrder); 
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
     * 查询 ActiveOrder
     * @param activeOrder
     * @return List<ActiveOrder>    ActiveOrder集合 
     * @throws Exception
     */
	@Override
	public List<ActiveOrder> findActiveOrder(ActiveOrder activeOrder) throws Exception {

		return this.findActiveOrder(activeOrder, null); 

	}


    /**
     * 查询 ActiveOrder
     * @param activeOrder
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<ActiveOrder>    ActiveOrder集合 
     * @throws Exception
     */
	@Override
	public List<ActiveOrder> findActiveOrder(ActiveOrder activeOrder, String order) throws Exception {

		List<ActiveOrder> result = null; 
		SqlSession sqlSession = null;
		ActiveOrderMapper activeOrderMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);

			result = activeOrderMapper.findActiveOrder(activeOrder, order); 
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
     * 分页查询 ActiveOrder
     * @param page
     * @param activeOrder
     * @return Page<ActiveOrder>    ActiveOrder分页结果 
     * @throws Exception 
     */
	@Override
	public Page<ActiveOrder> findActiveOrderByPage(Page<ActiveOrder> page, ActiveOrder activeOrder) throws Exception {

		List<ActiveOrder> result = new ArrayList<ActiveOrder>(); 
		SqlSession sqlSession = null;
		ActiveOrderMapper activeOrderMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeOrderMapper = sqlSession.getMapper(ActiveOrderMapper.class);

			result = activeOrderMapper.findByPage(page, activeOrder); 
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


	@Override
	public Integer updateActiveOrderByOrderId(ActiveOrder activeOrder)throws Exception {
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result=sqlSession.getMapper(ActiveOrderMapper.class).updateActiveOrderByOrderId(activeOrder);
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
	

	
	@Override
	public Integer updateActiveOrderByOrderId(SqlSession sqlSession,ActiveOrder activeOrder)throws Exception {
		
			return sqlSession.getMapper(ActiveOrderMapper.class).updateActiveOrderByOrderId(activeOrder);
	}


	@Override
	public Integer updateActiveOrderReturnBillNoAndReturnMoney(SqlSession sqlSession, 
			ActiveOrder activeOrder) throws Exception {
		Integer result = -1; 
		try { 

			result=sqlSession.getMapper(ActiveOrderMapper.class).updateActiveOrderReturnBillNoAndReturnMoney(activeOrder);
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

}