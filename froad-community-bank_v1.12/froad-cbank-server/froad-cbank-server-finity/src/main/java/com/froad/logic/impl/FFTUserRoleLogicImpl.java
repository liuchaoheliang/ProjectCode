package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.FFTUserOrgMapper;
import com.froad.db.mysql.mapper.FFTUserRoleMapper;
import com.froad.db.mysql.mapper.OrgRoleMapper;
import com.froad.enums.Source;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.FFTUserRoleLogic;
import com.froad.po.FFTUserOrg;
import com.froad.po.FFTUserRole;
import com.froad.po.OrgRole;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: FFTOrgLogicImpl.java</p>
 * <p>Description: 描述 </p> 组织Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTUserRoleLogicImpl implements FFTUserRoleLogic {

	/**
     * 新增 用户角色、用户组织关系
     * @param userId 用户Id
     * @param orgIds 组织Id集合
     * @param roleIds 角色Id集合
     * @return Long    主键ID
     */
	@Override
	public Long addFFTUserRole(Long userId, List<String> orgIds,List<Long> roleIds) throws FroadServerException, Exception {
		Long id = 0l; 
		SqlSession sqlSession = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTUserOrgMapper fftUserOrgMapper = null;
		
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);
			
			
			/**角色分配**/
			List<FFTUserRole> fftUserRoleList = new ArrayList<FFTUserRole>();
			//1.继承组织角色
			FFTUserRole fftUserRole = null;
			List<OrgRole> orgRoles = null;
			if(Checker.isNotEmpty(orgIds)){
				//组织可多角色，用户需继承组织下所有角色
				orgRoles = orgRoleMapper.findOrgRoleByOrgIds(orgIds);
				for(String orgId:orgIds){
					for(OrgRole orgRole:orgRoles){
						if(orgId.equals(orgRole.getOrgId())){
							fftUserRole = new FFTUserRole();
							fftUserRole.setUserId(userId);
							fftUserRole.setRoleId(orgRole.getRoleId());
							fftUserRole.setSource(Integer.valueOf(Source.orgSource.getType()));
							fftUserRole.setOrgId(orgId);
							
							fftUserRoleList.add(fftUserRole);
						}
					}
				}
			}
			
			Set<Long> userRoleIds = new HashSet<Long>();
			if(Checker.isNotEmpty(orgRoles)){
				//取出去重的组织角色roleId集合
				Set<Long> orgRoleIds = new HashSet<Long>();//组织角色roleId集合
				for(OrgRole orgRole:orgRoles){
					orgRoleIds.add(orgRole.getRoleId());
				}
				//再从web后端传过来的roleIds与组织角色orgRoleIds相比较，取出不同的roleId，设为用户直接分配的角色
				for(long roleId:roleIds){
					if(!orgRoleIds.contains(roleId)){
						userRoleIds.add(roleId);
					}
				}
				
			}else{
				for(long roleId:roleIds){
					userRoleIds.add(roleId);
				}
			}
			
			
			//2.用户直接分配
			if(Checker.isNotEmpty(userRoleIds)){
				for(long roleId:userRoleIds){
					fftUserRole = new FFTUserRole();
					fftUserRole.setUserId(userId);
					fftUserRole.setRoleId(roleId);
					fftUserRole.setSource(Integer.valueOf(Source.userSource.getType()));
					
					fftUserRoleList.add(fftUserRole);
				}
			}
			if(Checker.isNotEmpty(fftUserRoleList)){
				fftUserRoleMapper.addFFTUserRoleByBatch(fftUserRoleList);
			}
			
			
			/**组织配置**/
			if(Checker.isNotEmpty(orgIds)){
				List<FFTUserOrg> fftUserOrgList = new ArrayList<FFTUserOrg>();
				FFTUserOrg fftUserOrg = null;
				for(String orgId:orgIds){
					fftUserOrg = new FFTUserOrg();
					fftUserOrg.setOrgId(orgId);
					fftUserOrg.setUserId(userId);
					
					fftUserOrgList.add(fftUserOrg);
				}
				fftUserOrgMapper.addFFTUserOrgByBatch(fftUserOrgList);
			}
			
			
			//提交
			sqlSession.commit(true);
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return id; 
	}

	/**
     * 修改用户组织、用户角色关系
     * @param userId 用户Id
     * @param orgIds 组织Id集合
     * @param roleIds 角色Id集合
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateFFTUserRole(Long userId, List<String> orgIds,List<Long> roleIds) throws FroadServerException, Exception {
		Boolean result = false; 
		SqlSession sqlSession = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTUserOrgMapper fftUserOrgMapper = null;
		
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);
			
			
			/**角色分配**/
			List<FFTUserRole> fftUserRoleList = new ArrayList<FFTUserRole>();
			//1.继承组织角色
			FFTUserRole fftUserRole = null;
			List<OrgRole> orgRoles = null;
			//先删再加
			fftUserRoleMapper.deleteFFTUserRoleByUserId(userId);
			if(Checker.isNotEmpty(orgIds)){
				//组织可多角色，用户需继承组织下所有角色
				orgRoles = orgRoleMapper.findOrgRoleByOrgIds(orgIds);
				for(String orgId:orgIds){
					for(OrgRole orgRole:orgRoles){
						if(orgId.equals(orgRole.getOrgId())){
							fftUserRole = new FFTUserRole();
							fftUserRole.setUserId(userId);
							fftUserRole.setRoleId(orgRole.getRoleId());
							fftUserRole.setSource(Integer.valueOf(Source.orgSource.getType()));
							fftUserRole.setOrgId(orgId);
							
							fftUserRoleList.add(fftUserRole);
						}
					}
				}
			}
			
			Set<Long> userRoleIds = new HashSet<Long>();
			if(Checker.isNotEmpty(orgRoles)){
				//取出去重的组织角色roleId集合
				Set<Long> orgRoleIds = new HashSet<Long>();//组织角色roleId集合
				for(OrgRole orgRole:orgRoles){
					orgRoleIds.add(orgRole.getRoleId());
				}
				//再从web后端传过来的roleIds与组织角色orgRoleIds相比较，取出不同的roleId，设为用户直接分配的角色
				for(long roleId:roleIds){
					if(!orgRoleIds.contains(roleId)){
						userRoleIds.add(roleId);
					}
				}
				
			}else{
				for(long roleId:roleIds){
					userRoleIds.add(roleId);
				}
			}
			
			
			//2.用户直接分配
			if(Checker.isNotEmpty(userRoleIds)){
				for(long roleId:userRoleIds){
					fftUserRole = new FFTUserRole();
					fftUserRole.setUserId(userId);
					fftUserRole.setRoleId(roleId);
					fftUserRole.setSource(Integer.valueOf(Source.userSource.getType()));
					
					fftUserRoleList.add(fftUserRole);
				}
			}
			
			if(Checker.isNotEmpty(fftUserRoleList)){
				fftUserRoleMapper.addFFTUserRoleByBatch(fftUserRoleList);
			}
			
			
			/**组织配置**/
			List<FFTUserOrg> fftUserOrgList = new ArrayList<FFTUserOrg>();
			FFTUserOrg fftUserOrg = null;
			//先删再加
			fftUserOrgMapper.deleteFFTUserOrgByUserId(userId);
			if(Checker.isNotEmpty(orgIds)){
				for(String orgId:orgIds){
					fftUserOrg = new FFTUserOrg();
					fftUserOrg.setOrgId(orgId);
					fftUserOrg.setUserId(userId);
					
					fftUserOrgList.add(fftUserOrg);
				}
				fftUserOrgMapper.addFFTUserOrgByBatch(fftUserOrgList);
			}
			
			
			result = true;
			if (result) { // 修改成功
				sqlSession.commit(true);
			} else { // 修改失败
				sqlSession.rollback(true); 
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
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
     * 删除用户角色 FFTUserRole
     * @param userId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteFFTUserRole(Long userId) throws FroadServerException,Exception {
		Boolean result = false;
		SqlSession sqlSession = null;
		FFTUserRoleMapper fftuserRoleMapper = null;
		FFTUserOrgMapper fftUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftuserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);
			
			//删除用户角色关系
			fftuserRoleMapper.deleteFFTUserRoleByUserId(userId);
			//删除用户组织关系
			fftUserOrgMapper.deleteFFTUserOrgByUserId(userId);
			
			result = true;
			if (result) { // 删除成功
				sqlSession.commit(true);
			} else { // 删除失败
				sqlSession.rollback(true); 
			}
			
		} catch (Exception e) { 
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
     * 用户角色查询
     * @param userId 用户Id
     * @return FFTUserRole集合
     * 
     **/
	@Override
	public List<FFTUserRole> findFFTUserRoleByUserId(Long userId) throws Exception {
		
		List<FFTUserRole> result = new ArrayList<FFTUserRole>(); 
		SqlSession sqlSession = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);

			result = fftUserRoleMapper.findFFTUserRoleByUserId(userId);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	
}