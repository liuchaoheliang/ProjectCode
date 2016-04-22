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
 * @Title: ResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ResourceMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.ResourceLogic;
import com.froad.po.Resource;

/**
 * 
 * <p>
 * @Title: ResourceLogic.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class ResourceLogicImpl implements ResourceLogic {
	
	@Override
	public Long insertSelective(Resource resource) {
		Long result = -1l;
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class); 
			if (resourceMapper.insertSelective(resource) > -1) { // 添加成功
				result = resource.getId();
				sqlSession.commit(true);

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = -1l;
			if (null != sqlSession)
				sqlSession.rollback(true);
			// LogCvt.error("添加Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	/**
	 * 增加 Resource
	 * 
	 * @param resource
	 * @return Long 主键ID
	 */
	@Override
	public Long addResource(Resource resource) {

		Long result = -1l;
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);

			if (resourceMapper.addResource(resource) > -1) { // 添加成功
				result = resource.getId();
				sqlSession.commit(true);

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = -1l;
			if (null != sqlSession)
				sqlSession.rollback(true);
			// LogCvt.error("添加Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 删除 Resource
	 * 
	 * @param resource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteResource(Resource resource) {

		Boolean result = false;
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);

			result = resourceMapper.deleteResource(resource);
			if (result) { // 删除成功
				sqlSession.commit(true);
				result = true;

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("删除Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 修改 Resource
	 * 
	 * @param resource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateResource(Resource resource) {

		Boolean result = false;
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);

			result = resourceMapper.updateResource(resource);
			if (result) { // 修改成功
				sqlSession.commit(true);

				result = true;

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("修改Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 查询 Resource
	 * 
	 * @param resource
	 * @return List<Resource> 结果集合
	 */
	@Override
	public List<Resource> findResource(Resource resource) {

		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);

			result = resourceMapper.findResource(resource);
			// sqlSession.commit(true);
		} catch (Exception e) {
			result = null;
			LogCvt.error("查询Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 分页查询 Resource
	 * 
	 * @param page
	 * @param resource
	 * @return Page<Resource> 结果集合
	 */
	@Override
	public Page<Resource> findResourceByPage(Page<Resource> page, Resource resource) {

		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);

			result = resourceMapper.findByPage(page, resource);
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) {
			// if(null != sqlSession)
			// sqlSession.rollback(true);
			LogCvt.error("分页查询Resource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return page;

	}

	/**   
	 * 根据角色查询资源
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月08日 下午6:56:30  
	 * @param resource
	 * @param roleId
	 * @return  
	 * @throws  
	 */
	@Override
	public List<Resource> findResourceByRole(Resource resource, Long roleId) {
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);
			result = resourceMapper.findResourceByRole(resource, roleId);
		} catch (Exception e) {
			LogCvt.error("findResourceByRole根据角色查询资源失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	@Override
	public List<Resource> findResourceByRoles(Resource resource, List<Long> roles) {
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);
			result = resourceMapper.findResourceByRoles(resource, roles);
		} catch (Exception e) {
			LogCvt.error("findResourceByRoles根据角色查询资源失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}
	
	/**   
	 * 根据用户查询资源
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月15日 下午6:56:30  
	 * @param resource
	 * @param userId
	 * @return  
	 * @throws  
	 */
	@Override
	public List<Resource> findResourceByUser(Resource resource, Long userId,Integer userType) {
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		ResourceMapper resourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			resourceMapper = sqlSession.getMapper(ResourceMapper.class);
			result = resourceMapper.findResourceByUser(resource, userId,userType);
		} catch (Exception e) {
			LogCvt.error("findResourceByRole根据用户查询资源失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

}