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
 * @Title: BossResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.BossResourceMapper;
import com.froad.db.mysql.mapper.FFTUserRoleMapper;
import com.froad.db.mysql.mapper.RoleResourceMapper;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BossResourceLogic;
import com.froad.po.FFTUserRole;
import com.froad.po.Resource;
import com.froad.po.RoleResource;

/**
 * 
 * <p>
 * @Title: BossResourceLogic.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossResourceLogicImpl implements BossResourceLogic {
	
	@Override
	public Long insertSelective(Resource resource) {
		Long result = -1l;
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class); 
			if (bossResourceMapper.addResource(resource) > -1) { // 添加成功
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
	public Long addResource(Resource resource)  throws FroadServerException, Exception{

		Long result = -1l;
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		Boolean bool = false;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);

			//验证resource_key唯一性
			
			Resource IsExistsResource = bossResourceMapper.IsExistsResourceKey(resource.getResourceKey());
			if(IsExistsResource != null){
				return -2l;
//				throw new FroadServerException("资源Key已存在！");
			}
			//设置初始值
			resource.setIsDelete(false);
			resource.setStatus(true);
//			Timestamp sysdate = new Timestamp(System.currentTimeMillis()); 
			resource.setUpdateTime(new Date());
			//添加资源
			bossResourceMapper.addResource(resource);
			result = resource.getId();
			
			//获取父级资源信息
			long parentId = resource.getParentResourceId();
//			Resource parentResource = bossResourceMapper.findResourceById(parentId);
			Resource setResource = new Resource();
			String treePath = "";
			/**修改treePath**/
			//顶级资源
			if(parentId == 0){
				treePath = Long.toString(resource.getId());
				setResource.setTreePath(treePath.trim());
			}else{
				Resource parentResource = bossResourceMapper.findResourceById(parentId);
				treePath = parentResource.getTreePath();
				setResource.setTreePath((treePath.trim() + "," + result).trim());
			}
			setResource.setId(result);
			bool = bossResourceMapper.updateResource(setResource);
			
			if (bool) { // 添加成功
				result = setResource.getId();
				sqlSession.commit(true);

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}  catch (Exception e) {
			e.printStackTrace();
			result = -1l;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
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
	public Boolean deleteResource(Resource resource)   throws FroadServerException, Exception {

		Boolean result = false;
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		RoleResourceMapper roleResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);

			result = bossResourceMapper.deleteResource(resource);
			if(result){
				result = bossResourceMapper.deleteResourceByTreePath(resource);
			}
			if(result){
				//查询该资源对应的父级资源
				//若对应的父级资源的子资源全被删除，则把父级资源及自己对应的子资源删除
				//若对应的父级资源的子资源未全部删除，则把自己对应的子资源删除
				Resource res = bossResourceMapper.findResourceById(resource.getId());
				Long parentId = res.getParentResourceId();
				List<Resource> resList = bossResourceMapper.findChildrenById(parentId);
//				Resource parentRes = bossResourceMapper.findResourceById(parentId);
				List<Long> resourceIds = new ArrayList<Long>();
				//如果父级资源的子资源未全部被删除
				if(resList != null && resList.size() >1){
					List<Resource> childrenResource =  bossResourceMapper.findResourceByTreePath(resource.getId());
					for(Resource rs : childrenResource){
						resourceIds.add(rs.getId());
					}
					roleResourceMapper.deleteRoleResourceChildren(resourceIds);
				}else{
					Resource parentRes = bossResourceMapper.findResourceById(parentId);
					Resource parentResource = new Resource();
					parentResource.setId(parentId);
					parentResource.setPlatform(parentRes.getPlatform());
					bossResourceMapper.deleteResource(parentResource);
					
					RoleResource roleResource = new RoleResource();
					roleResource.setResourceId(parentId);
					roleResourceMapper.deleteRoleResource(roleResource);
				}
//				if(parentRes != null && parentRes.getIsDelete() == false ){
//					
//				}
//				RoleResource roleResource = new RoleResource();
//				List<RoleResource> roleResourceList= roleResourceMapper.findRoleResource(roleResource);
			}
			
			if (result) { // 删除成功
				sqlSession.commit(true);
				result = true;

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}  catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
//			LogCvt.error("删除Resource失败，原因:" + e.getMessage(), e);
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
	public Boolean updateResource(Resource resource)   throws FroadServerException, Exception{

		Boolean result = false;
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
		
			Resource find = new Resource();
			find.setPlatform(resource.getPlatform());
			find.setId(resource.getId());
			List<Resource> resources = this.findResource(find);
			if(resources == null || resources.size() ==0){
				throw new FroadServerException("资源Id不存在！");
			}
			if(!resource.getResourceKey().equals(resources.get(0).getResourceKey())){
				//验证resource_key唯一性
				Resource IsExistsResource = bossResourceMapper.IsExistsResourceKey(resource.getResourceKey());
				if(IsExistsResource != null){
					throw new FroadServerException("资源编号已存在！");
				}
			}
			if(resource.getParentResourceId() != null){
				throw new FroadServerException("父级ID不能修改！");
			}
			if(resource.getTreePath() != null){
				throw new FroadServerException("treePath不能修改！");
			}
			if(resource.getPlatform() != null){
				throw new FroadServerException("平台名称不能修改！");
			}
			
//			Timestamp sysdate = new Timestamp(System.currentTimeMillis()); 
			resource.setUpdateTime(new Date());

			result = bossResourceMapper.updateResource(resource);
			if (result) { // 修改成功
				sqlSession.commit(true);

				result = true;

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			throw e;
//			LogCvt.error("修改Resource失败，原因:" + e.getMessage(), e);
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
	public List<Resource> findResource(Resource resource)   throws  Exception {

		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);

			result = bossResourceMapper.findResource(resource);
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
	public List<Resource> findResourceByRole(Resource resource, Long roleId) throws Exception{
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
			result = bossResourceMapper.findResourceByRole(resource, roleId);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	@Override
	public List<Resource> findResourceByRoles(Resource resource, List<Long> roles) throws Exception{
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
			result = bossResourceMapper.findResourceByRoles(resource, roles);
		} catch (Exception e) {
			throw e;
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
	public List<Resource> findResourceByUser(Resource resource, Long userId)   throws FroadServerException, Exception {
		List<Resource> result = new ArrayList<Resource>();
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		RoleResourceMapper roleResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			//根据用户Id获取cb_fft_user_role中的角色集合
			 List<FFTUserRole>  fftUserRoleList = fftUserRoleMapper.findFFTUserRoleByUserId(userId);
			 if(fftUserRoleList == null || fftUserRoleList.size()==0){
				 throw new FroadServerException("未找到该用户对应的角色！");
			 }

			 Set<Long> roleIds = new HashSet<Long>();
			 for(FFTUserRole fuser : fftUserRoleList){
				 roleIds.add(fuser.getRoleId());
			 }
			 RoleResource roleResource = null;
			 List<RoleResource>  roleResources = null;
			 Set<Long>  resourceIds = new HashSet<Long>();
			 List<Long>  resourceIdList = new ArrayList<Long>();
			 //根据角色Id去cb_role_resource表中获取对应的资源并去重
			 for(Long roleId : roleIds){
				 roleResource = new RoleResource();
				 roleResource.setRoleId(roleId);
				 roleResource.setPlatform(resource.getPlatform());
				 roleResources = roleResourceMapper.findRoleResource(roleResource);
				 if(roleResources != null && roleResources.size() != 0){
					 for(RoleResource roleresource : roleResources){
						 resourceIds.add(roleresource.getResourceId());
					 }
				 }else{
					 throw new FroadServerException("未找到该用户的角色资源！该用户角色信息,ID:"+ roleResource.getRoleId() + ",platform:" + roleResource.getPlatform() );
				 }
				
			 }
			 
			 for(Long id : resourceIds){
				 resourceIdList.add(id);
			 }
			 result =  bossResourceMapper.findResourceByIds(resourceIdList);
		} catch (Exception e) {
			LogCvt.error("findResourceByUser根据用户查询资源失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 菜单调整
	 */
	public Boolean moveMenu(List<Resource> resourceList) {
		Boolean result = false;
		SqlSession sqlSession = null;
		BossResourceMapper bossResourceMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossResourceMapper = sqlSession.getMapper(BossResourceMapper.class);
			
			for(Resource resource : resourceList){
				result = bossResourceMapper.updateResource(resource);
				if(!result){
					break;
				}
			}
			if(result){
				sqlSession.commit(true);
			}
			
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("菜单调整失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;
	}

	
	

}