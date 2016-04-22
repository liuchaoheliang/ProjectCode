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
 * @Title: UserResourceLogicImpl.java
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
import com.froad.db.mysql.mapper.UserResourceMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.UserResourceLogic;
import com.froad.po.UserResource;

/**
 * 
 * <p>@Title: UserResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class UserResourceLogicImpl implements UserResourceLogic {


    /**
     * 增加 UserResource
     * @param userResource
     * @return Long    主键ID
     */
	@Override
	public Long addUserResource(UserResource userResource) {

		Long result = -1l; 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);

			if (userResourceMapper.addUserResource(userResource) > -1) { // 添加成功
				result = userResource.getUserId(); 
				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1l; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("添加UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	
	 /**
     * 批量增加 UserResource
     * @param userResource
     * @return Long    主键ID
     */
	public Boolean addUserResourceByBatch(List<UserResource> userResourceList) {
		Boolean result = false; 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);

			if (userResourceMapper.addUserResourceByBatch(userResourceList)) { // 添加成功
				result = true;
				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = false; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("添加UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	


    /**
     * 删除 UserResource
     * @param userResource
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteUserResource(UserResource userResource) {

		Boolean result = false; 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);

			result = userResourceMapper.deleteUserResource(userResource); 
			if (result) { // 删除成功
				sqlSession.commit(true);
				result = true;

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = false; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("删除UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 UserResource
     * @param userResource
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateUserResource(Long userId, List<Long> resourceIds) {

		Boolean result = false; 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		UserResource userResource = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);
			// 修改处理逻辑： 先清除之前的资源关系，在新建现有传入资源关系
			userResource = new UserResource();
			userResource.setUserId(userId);
			userResource.setUserType(1);
			userResourceMapper.deleteUserResource(userResource);
			
			List<UserResource> userResourceList = new ArrayList<UserResource>();
			for(Long resourceId : resourceIds){
				userResource = new UserResource();
				userResource.setUserId(userId);
				userResource.setResourceId(resourceId);
				userResource.setUserType(1);
				userResourceList.add(userResource);
			}
			result = userResourceMapper.addUserResourceByBatch(userResourceList);
			if (result) { // 修改成功
				sqlSession.commit(true);
				result = true;

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = false; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("修改UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 UserResource
     * @param userResource
     * @return List<UserResource>    结果集合 
     */
	@Override
	public List<UserResource> findUserResource(UserResource userResource) {

		List<UserResource> result = new ArrayList<UserResource>(); 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);

			result = userResourceMapper.findUserResource(userResource); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			result = null; 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			LogCvt.error("查询UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 UserResource
     * @param page
     * @param userResource
     * @return Page<UserResource>    结果集合 
     */
	@Override
	public Page<UserResource> findUserResourceByPage(Page<UserResource> page, UserResource userResource) {

		List<UserResource> result = new ArrayList<UserResource>(); 
		SqlSession sqlSession = null;
		UserResourceMapper userResourceMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			userResourceMapper = sqlSession.getMapper(UserResourceMapper.class);

			result = userResourceMapper.findByPage(page, userResource); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			LogCvt.error("分页查询UserResource失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}



}