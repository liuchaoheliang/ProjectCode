package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BossRoleMapper;
import com.froad.db.mysql.mapper.FFTOrgMapper;
import com.froad.db.mysql.mapper.FFTUserRoleMapper;
import com.froad.db.mysql.mapper.OrgRoleMapper;
import com.froad.db.mysql.mapper.RoleResourceMapper;
import com.froad.enums.Platform;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.BossRoleLogic;
import com.froad.logic.RoleResourceLogic;
import com.froad.po.FFTOrg;
import com.froad.po.OrgRole;
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
public class BossRoleLogicImpl implements BossRoleLogic {


    /**
     * 增加 Role
     * @param role
     * @return Long    主键ID
     */
	@Override
	public Long addRole(Role role, List<Long> resourceIds)  throws FroadServerException, Exception{

		Long result = -1l; 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		RoleResourceMapper roleResourceMapper = null;
		try { 
			
			//验证角色名称唯一性
			Role find = new Role();
			find.setPlatform(role.getPlatform());
			find.setRoleName(role.getRoleName());
			find.setIsDelete(false);
			List<Role> roles = this.findRole(find);
			if(Checker.isNotEmpty(roles)){
				throw new FroadServerException("角色名已存在！");
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			//设置是否删除
			role.setIsDelete(false);
			role.setIsAdmin(false);
			//添加角色
			bossRoleMapper.addRole(role); 
			//如果为商户角色，同步到mongo文档cb_merchant_role_resource
			if(role.getPlatform().equals(Platform.merchant.getType())){
				
			}
			//添加角色资源信息
			for(int i=0; i<resourceIds.size(); i++){
				RoleResource roleResource = new RoleResource();
				roleResource.setPlatform(role.getPlatform());
				roleResource.setRoleId(role.getId());
				roleResource.setResourceId(resourceIds.get(i));
				result = roleResourceMapper.addRoleResource(roleResource);
			}
			
			if (result > -1) { // 新增成功
				sqlSession.commit(true);
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}catch (Exception e) { 
			result = -1l; 
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
     * 删除 Role
     * @param role
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteRole(Role role)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTOrgMapper fftOrgMapper = null;
		RoleResourceMapper roleResourceMapper = null;
		RoleResource roleResource = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			
			Role find = new Role();
			find.setPlatform(role.getPlatform());
			find.setId(role.getId());
			List<Role> roles = this.findRole(find);
			if(Checker.isNotEmpty(roles) && roles.get(0).getIsAdmin() == true){
				throw new FroadServerException("不允许删除角色！");
			}else{
				//获取角色关联的组织列表
				OrgRole orgRole = new OrgRole();
				orgRole.setRoleId(role.getId());
				List<OrgRole> orgRoles = orgRoleMapper.findOrgRole(orgRole);
				//角色对应的组织列表是否有一级组织
				for(OrgRole or : orgRoles){
					FFTOrg to = fftOrgMapper.findFFTOrgByOrgId(or.getOrgId());
					if(role.getPlatform().equals(Platform.bank.getType()) && to.getLevel()==0){
						throw new FroadServerException("不允许删除角色！");
					}
				}
				//删除cb_role中的数据
				result = bossRoleMapper.deleteRole(role); 
				//删除cb_role_resource的数据
				roleResource = new RoleResource();
				roleResource.setRoleId(role.getId());
				roleResourceMapper.deleteRoleResource(roleResource);
				//删除cb_org_role的数据--需要一个根据role_id删除组织角色的接口
				orgRoleMapper.deleteOrgRoleByRoleId(role.getId());
				//删除cb_fft_user_role的数据--需要一个根据role_id删除用户角色关系表的接口
				fftUserRoleMapper.deleteFFTUserRoleByRoleId(role.getId());
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
		}catch (Exception e) { 
			result = false; 
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
     * 修改 Role
     * @param role
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateRole(Role role, List<Long> resourceIds) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		RoleResourceLogic roleResourceLogic = null;		
		try { 
			
			//若修改角色名称，需判断角色名称不能相同
			if(Checker.isNotEmpty(role.getRoleName())){
				Role find = this.findRoleById(role.getId());
				if(!find.getRoleName().equals(role.getRoleName())){
					//验证角色名称唯一性
					find = new Role();
					find.setPlatform(role.getPlatform());
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
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			//值不为空才更新
			if(Checker.isNotEmpty(role.getPlatform())
					|| Checker.isNotEmpty(role.getRemark())
					|| Checker.isNotEmpty(role.getRoleName())){
				result = bossRoleMapper.updateRole(role); 
				sqlSession.commit(true);
			}
			
			//修改角色资源关系
			roleResourceLogic = new RoleResourceLogicImpl();
			roleResourceLogic.updateBossRoleResource(role.getId(),role.getPlatform(),resourceIds);
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}catch (Exception e) { 
			result = false; 
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
     * 根据id查询 单个BankRole
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	@Override
	public Role findRoleById(Long roleId) throws FroadServerException, Exception{
		try{
			Role resultRole =new Role();
			//过滤条件BankRole
			Role role =new Role();
			role.setId(roleId);
	//		role.setStatus(true);
			role.setIsDelete(false);
			List<Role>  roles = this.findRole(role);
			if(roles!=null && roles.size()==1){
				resultRole=roles.get(0);
				return resultRole;
			}else{
				return new Role();
			}
		}catch (FroadServerException e) { 
			throw e;
		}catch (Exception e) { 
			throw e;
		}
	}
	
	
	
	  /**
     * 查询 Role
     * @param role
     * @return List<Role>    结果集合 
     */
	@Override
	public List<Role> findRole(Role role) throws FroadServerException, Exception{

		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			result = bossRoleMapper.findRole(role); 
			// sqlSession.commit(true);
		}catch (FroadServerException e) { 
			throw e;
		}catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 根据UserId、platform获取所能查看范围的角色列表
     * @param userId 用户Id
     * @param platform 平台
     * @return List<Role>    结果集合 
     */
	public List<Role> findRoleList(Long userId,String platform) throws FroadServerException, Exception{
		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			
			boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			if(isAdminRole){//超级管理员
				Role find = new Role();
				find.setPlatform(platform);
				find.setIsDelete(false);
				result = bossRoleMapper.findRole(find);
			}else{
//				//普通用户可查看的角色列表：1-用户角色分配 2-组织继承的 3-本身添加人的 4-所属组织下子组织孙组织所有的角色
//				result = bossRoleMapper.findUserRoleByUserId(userId,platform);
//				//list转换成set
//				Set<Role>  roleSet = new HashSet<Role>(result);
//				
//				Role find = new Role();
//				find.setUserId(userId);
//				find.setPlatform(platform);
//				find.setIsDelete(false);
//				List<Role> userRoles = bossRoleMapper.findRole(find);
//				
//				//去重
//				if(Checker.isNotEmpty(userRoles)){
//					for(Role u : userRoles){
//						roleSet.add(u);
//					}
//				}
				
				//普通用户角色列表只能查看boss的角色
				/**
				 * 1.取出用户所属组织orgId
				 * 2.取出orgId下所有子组织并去重
				 * 3.取出组织下所有角色roleId并去重
				 * 4.取出本身userId添加的roleId并去重
				 * 5.返回角色对象
				 */
				
				
				//用户所属组织
				List<String> orgIds = new FFTUserOrgLogicImpl().findFFTUserOrgByUserId(userId);
				if(Checker.isEmpty(orgIds)){
					return result;
				}
				
				//查出用户所属组织下所有子组织并去重
				List<FFTOrg> fftorgs = null;
				Set<String> orgIdAll = new HashSet<String>();
//				for(String oid : orgIds){
//					fftorgs = fftOrgMapper.findChildList(fftOrgMapper.findFFTOrgByOrgId(oid).getId());
//					for(FFTOrg fo : fftorgs){
//						orgIdAll.add(fo.getOrgId());
//					}
//				}
				
				//将orgIds对象统一查出，取出主键id集合，统一取出所有下级组织orgId集合并去重
				fftorgs = fftOrgMapper.findFFTOrgByOrgIds(orgIds);
				Set<Long> ids = new HashSet<Long>();
				for(FFTOrg fo : fftorgs){
					ids.add(fo.getId());
				}
				fftorgs = null;//清空掉 
				//sql组装
				StringBuffer treePathSql = new StringBuffer();
				treePathSql.append(" AND (");
				List<Long> idsList = new ArrayList<Long>(ids);
				for(int i = 0 ;i<idsList.size(); i++){
					treePathSql.append("tree_path REGEXP '^.*"+idsList.get(i)+".*$'");
					if(i < idsList.size()-1){
						treePathSql.append(" OR ");
					}
				}
				treePathSql.append(")");
				List<FFTOrg> childFFTOrgs = fftOrgMapper.findChildListByIds(idsList);
				for(FFTOrg fo : childFFTOrgs){
					orgIdAll.add(fo.getOrgId());
				}
				
				
				//组织对应的角色并去重
				List<OrgRole> orgRoles = null;
				Set<Long>  roleIdSet = new HashSet<Long>();
//				for(String orgId : orgIdAll){
//					orgRoles = orgRoleMapper.findOrgRoleByOrgId(orgId);
//					for(OrgRole or : orgRoles){
//						roleIdSet.add(or.getRoleId());
//					}
//				}
				
				//一次性查出组织对应的角色roleId集合
				orgRoles = orgRoleMapper.findOrgRoleByOrgIds(new ArrayList<String>(orgIdAll));
				for(OrgRole or : orgRoles){
					roleIdSet.add(or.getRoleId());
				}
				
				
				//再加本身添加的角色
				Role find = new Role();
				find.setUserId(userId);
				find.setPlatform(platform);
				find.setIsDelete(false);
				List<Role> userAddRoles = bossRoleMapper.findRole(find);
				
				//去重
				if(Checker.isNotEmpty(userAddRoles)){
					for(Role u : userAddRoles){
						roleIdSet.add(u.getId());
					}
				}
				
				//set转list
				if(Checker.isNotEmpty(roleIdSet)){
					result = bossRoleMapper.findRoleByIds(new ArrayList<Long>(roleIdSet));
				}
				
				
			}
			
		}catch (FroadServerException e) { 
			throw e;
		}catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


	/**
	 * 用户登录时获取的所属角色
	 * @param userId 用户Id
	 * @return List<Role>
	 */
	public List<Role> findRoleByUserIdLogin(Long userId) throws FroadServerException, Exception{
		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);
			
			//用户登录获取的角色，就是用户角色关系表中的数据
			result = bossRoleMapper.findUserRoleByUserId(userId,Platform.boss.getType());
			
			
		}catch (FroadServerException e) { 
			throw e;
		}catch (Exception e) { 
			throw e;
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
	public Page<Role> findRoleByPage(Page<Role> page, Role role) throws FroadServerException, Exception{

		List<Role> result = new ArrayList<Role>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			result = bossRoleMapper.findByPage(page, role); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}