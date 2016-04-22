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
 * @Title: RoleLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.RoleMapper;
import com.froad.db.mysql.mapper.RoleResourceMapper;
import com.froad.enums.Platform;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.RoleLogic;
import com.froad.logic.RoleResourceLogic;
import com.froad.po.Role;
import com.froad.po.RoleResource;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: RoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class RoleLogicImpl implements RoleLogic {


    /**
     * 增加 Role
     * @param role
     * @return Long    主键ID
     */
	@Override
	public Long addRole(Role role, List<Long> resourceIds)  throws FroadServerException, Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		RoleMapper roleMapper = null;
		RoleResourceMapper roleResourceMapper = null;
		try { 
			//一个clientId只能允许一个管理员roleid
			if(role.getTag().equals("0")){
				//判断是否已添加管理员角色
				Role find = new Role();
				find.setClientId(role.getClientId());
				find.setTag("0");
				find.setIsDelete(false);
				
				List<Role> roles = this.findRole(find);
				if(Checker.isNotEmpty(roles)){
					throw new FroadServerException("管理员角色已存在，不允许添加！");
				}
			}
			
			//验证一个clientId下角色名称唯一性
			Role find = new Role();
			find.setClientId(role.getClientId());
			find.setRoleName(role.getRoleName());
			find.setIsDelete(false);
			List<Role> roles = this.findRole(find);
			if(Checker.isNotEmpty(roles)){
				throw new FroadServerException("角色名已存在！");
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleMapper = sqlSession.getMapper(RoleMapper.class);
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			//设置是否删除
			role.setIsDelete(false);
			//设置是否有效
			role.setStatus(true);
			//添加角色
			roleMapper.addRole(role); 
			//添加角色资源信息
			for(int i=0; i<resourceIds.size(); i++){
				RoleResource roleResource = new RoleResource();
				roleResource.setPlatform(Platform.bank.getType());
				roleResource.setRoleId(role.getId());
				roleResource.setResourceId(resourceIds.get(i));
				result = roleResourceMapper.addRoleResource(roleResource);
			}
			
			if (result > -1) { // 新增成功
				sqlSession.commit(true);
			}
			
		} catch (Exception e) { 
			result = -1l; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("添加Role失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 Role
     * @param role
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteRole(Role role)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		RoleMapper roleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleMapper = sqlSession.getMapper(RoleMapper.class);

			result = roleMapper.deleteRole(role); 
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
			LogCvt.error("删除Role失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 Role
     * @param role
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateRole(Role role, List<Long> resourceIds) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		RoleMapper roleMapper = null;
		RoleResourceLogic roleResourceLogic = null;		
		try { 
			
			//若修改角色名称，需判断角色名称不能相同
			if(Checker.isNotEmpty(role.getRoleName())){
				Role find = this.findRoleById(role.getId());
				if(!find.getRoleName().equals(role.getRoleName())){
					//验证一个clientId下角色名称唯一性
					find = new Role();
					find.setClientId(role.getClientId());
					find.setRoleName(role.getRoleName());
					find.setIsDelete(false);
					List<Role> roles = this.findRole(find);
					if(Checker.isNotEmpty(roles)){
						throw new FroadServerException("角色名已存在！");
					}
				}
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleMapper = sqlSession.getMapper(RoleMapper.class);

			//值不为空才更新
			if(Checker.isNotEmpty(role.getStatus()) 
					|| Checker.isNotEmpty(role.getRemark())
					|| Checker.isNotEmpty(role.getRoleName())){
				result = roleMapper.updateRole(role); 
				sqlSession.commit(true);
			}
			
			//修改角色资源关系
			roleResourceLogic = new RoleResourceLogicImpl();
			roleResourceLogic.updateRoleResource(role.getId(), resourceIds);
			
		} catch (Exception e) { 
			result = false; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("修改Role失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	/**
     * 根据id查询 单个BankRole
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	@Override
	public Role findRoleById(Long roleId) {
		
		Role resultRole =new Role();
		//过滤条件BankRole
		Role role =new Role();
		role.setId(roleId);
		role.setStatus(true);
		role.setIsDelete(false);
		List<Role>  roles = this.findRole(role);
		if(roles!=null && roles.size()==1){
			resultRole=roles.get(0);
			return resultRole;
		}else{
			return new Role();
		}
		
	}


    /**
     * 查询 Role
     * @param role
     * @return List<Role>    结果集合 
     */
	@Override
	public List<Role> findRole(Role role) {

		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		RoleMapper roleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleMapper = sqlSession.getMapper(RoleMapper.class);

			result = roleMapper.findRole(role); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			result = null; 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			LogCvt.error("查询Role失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Role
     * @param page
     * @param role
     * @return Page<Role>    结果集合 
     */
	@Override
	public Page<Role> findRoleByPage(Page<Role> page, Role role) {

		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		RoleMapper roleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleMapper = sqlSession.getMapper(RoleMapper.class);

			result = roleMapper.findByPage(page, role); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			LogCvt.error("分页查询Role失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}