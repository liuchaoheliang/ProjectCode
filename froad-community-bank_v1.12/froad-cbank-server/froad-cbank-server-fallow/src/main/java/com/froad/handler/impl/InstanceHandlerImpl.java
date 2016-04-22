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
 * @Title: InstanceHandlerImpl.java
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
import com.froad.db.mysql.mapper.InstanceMapper;
import com.froad.logback.LogCvt;
import com.froad.handler.InstanceHandler;
import com.froad.po.mysql.Instance;

/**
 * 
 * <p>@Title: InstanceHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体Instance的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class InstanceHandlerImpl implements InstanceHandler {


    /**
     * 增加 Instance
     * @param instance
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addInstance(Instance instance)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addInstance(sqlSession, instance);

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
     * 增加 Instance
     * @param sqlSession
     * @param instance
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addInstance(SqlSession sqlSession, Instance instance) throws Exception {

		Long result = null; 
		InstanceMapper instanceMapper = sqlSession.getMapper(InstanceMapper.class);
		if (instanceMapper.addInstance(instance) > -1) { // 添加成功
			result = instance.getId(); 
		}
		return result; 

	}


    /**
     * 删除 Instance
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstance(Instance instance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteInstance(sqlSession, instance); 
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
     * 删除 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstance(SqlSession sqlSession, Instance instance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).deleteInstance(instance); 
	}


    /**
     * 根据id删除 Instance
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstanceById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteInstanceById(sqlSession, id); 
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
     * 根据id删除 Instance
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstanceById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).deleteInstanceById(id); 
	}


    /**
     * 根据instanceId删除 Instance
     * @param instanceId
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstanceByInstanceId(String instanceId) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteInstanceByInstanceId(sqlSession, instanceId); 
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
     * 根据instanceId删除 Instance
     * @param instanceId
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteInstanceByInstanceId(SqlSession sqlSession, String instanceId) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).deleteInstanceByInstanceId(instanceId); 
	}


    /**
     * 修改 Instance
     * @param instance
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstance(Instance instance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateInstance(sqlSession, instance); 
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
     * 修改 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstance(SqlSession sqlSession, Instance instance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).updateInstance(instance); 
	}


    /**
     * 根据id修改 Instance
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstanceById(Instance instance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateInstanceById(sqlSession, instance); 
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
     * 根据id修改 Instance
     * @param sqlSession
     * @param instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstanceById(SqlSession sqlSession, Instance instance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).updateInstanceById(instance); 
	}


    /**
     * 根据instanceId修改 Instance
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstanceByInstanceId(Instance instance) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateInstanceByInstanceId(sqlSession, instance); 
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
     * 根据instanceId修改 Instance
     * @param sqlSession
     * @param Instance
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateInstanceByInstanceId(SqlSession sqlSession, Instance instance) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(InstanceMapper.class).updateInstanceByInstanceId(instance); 
	}


    /**
     * 根据id查询单个 Instance
     * @param id
     * @return Instance    单个Instance
     * @throws Exception
     */
	@Override
	public Instance findInstanceById(Long id) throws Exception {

		Instance result = null; 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.findInstanceById(id); 
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
     * 根据instanceId查询单个 Instance
     * @param instanceId
     * @return Instance    单个Instance
     * @throws Exception
     */
	@Override
	public Instance findInstanceByInstanceId(String instanceId) throws Exception {

		Instance result = null; 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.findInstanceByInstanceId(instanceId); 
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
     * 查询一个 Instance
     * @param instance
     * @return Instance    单个Instance
     * @throws Exception
     */
	@Override
	public Instance findOneInstance(Instance instance) throws Exception {

		Instance result = null; 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.findOneInstance(instance); 
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
     * 查询统计 Instance
     * @param instance
     * @return Integer    返回记录数Instance
     * @throws Exception
     */
	@Override
	public Integer countInstance(Instance instance) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.countInstance(instance); 
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
     * 查询 Instance
     * @param instance
     * @return List<Instance>    Instance集合 
     * @throws Exception
     */
	@Override
	public List<Instance> findInstance(Instance instance) throws Exception {

		return this.findInstance(instance, null); 

	}


    /**
     * 查询 Instance
     * @param instance
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<Instance>    Instance集合 
     * @throws Exception
     */
	@Override
	public List<Instance> findInstance(Instance instance, String order) throws Exception {

		List<Instance> result = null; 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.findInstance(instance, order); 
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
     * 分页查询 Instance
     * @param page
     * @param instance
     * @return Page<Instance>    Instance分页结果 
     * @throws Exception 
     */
	@Override
	public Page<Instance> findInstanceByPage(Page<Instance> page, Instance instance) throws Exception {

		List<Instance> result = new ArrayList<Instance>(); 
		SqlSession sqlSession = null;
		InstanceMapper instanceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			instanceMapper = sqlSession.getMapper(InstanceMapper.class);

			result = instanceMapper.findByPage(page, instance); 
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