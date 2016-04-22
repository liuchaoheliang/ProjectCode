package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BossRoleMapper;
import com.froad.db.mysql.mapper.FFTOrgMapper;
import com.froad.db.mysql.mapper.FFTOrgReMapper;
import com.froad.db.mysql.mapper.FFTUserOrgMapper;
import com.froad.db.mysql.mapper.FFTUserRoleMapper;
import com.froad.db.mysql.mapper.OrgRoleMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.Platform;
import com.froad.enums.Source;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.FFTOrgLogic;
import com.froad.po.FFTOrg;
import com.froad.po.FFTOrgRe;
import com.froad.po.FFTUserOrg;
import com.froad.po.FFTUserRole;
import com.froad.po.OrgRole;
import com.froad.util.Checker;
import com.froad.util.SimpleID;

/**
 * 
 * <p>@Title: FFTOrgLogicImpl.java</p>
 * <p>Description: 描述 </p> 组织Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTOrgLogicImpl implements FFTOrgLogic {


    /**
     * 增加组织 FFTOrg
     * @param fftOrg
     * @param roleIds 角色分配
     * @param reOrgIds 权限组织配置
     * @return Long    主键ID
     */
	@Override
	public Long addFFTOrg(FFTOrg fftOrg,List<Long> roleIds, List<String> reOrgIds) throws FroadServerException, Exception{

		Long id = 0l; 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTOrgReMapper fftOrgReMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			
			//String clientId = "";//所属客户端
			String code = "";//组织编码
			String platform = fftOrg.getPlatform().toLowerCase();//默认web后端传过来的bank
			
			long parentId = fftOrg.getParentId();
			//根据上级id取得clientId
			FFTOrg parentFFTOrg = fftOrgMapper.findFFTOrgById(parentId);
			if(Checker.isNotEmpty(parentFFTOrg)){
				//clientId = parentFFTOrg.getClientId();
				code = fftOrg.getCode();
				platform = parentFFTOrg.getPlatform();//平台名称(若上级有值，则以上级为准)
			}else{
				throw new FroadServerException("父级组织不存在！");
			}
			
			
			
			//boss自身组织
			if(fftOrg.getPlatform().equals(Platform.boss.getType())){
				
				//自身组织
				fftOrg.setOrgId(new SimpleID(ModuleID.fftorg).nextId());//普通组织orgId由simpleId生成
				//boss的code先初始化空字符串，后续再更新
				fftOrg.setCode("");
				
				fftOrg.setPlatform(platform);
				fftOrg.setIsDelete(false);
				fftOrg.setTreePath("");
				
				/**添加FFTOrg**/
				fftOrgMapper.addFFTOrg(fftOrg);
				id = fftOrg.getId();
				
		        /**修改treePath及code**/
				String treePath = parentFFTOrg.getTreePath();
				FFTOrg setFFTOrg = new FFTOrg();
				setFFTOrg.setTreePath((treePath.trim() + "," + id).trim());
				if(platform.equals(Platform.boss.getType())){
					setFFTOrg.setCode(String.valueOf(id));
					code = String.valueOf(id) ;
				}
				setFFTOrg.setId(id);
				fftOrgMapper.updateFFTOrg(setFFTOrg);
		        
			}
			//接入行组织
			else if(fftOrg.getPlatform().equals(Platform.bank.getType())){
				//新增银行组织时，web后端会先去调bank模块添加机构、商户、门店信息，而后再添加本组织表
				//因：在原cb_org机构表中增加了insert/update触发器同步到cb_fft_org组织表，故而此处不需新增组织表
							
				//查询sql触发器新增的主键id返回
				FFTOrg addFFTOrg = fftOrgMapper.findFFTOrgByOrgId(fftOrg.getOrgId());
				if(Checker.isNotEmpty(addFFTOrg)){
					id = addFFTOrg.getId();
				}
			}
			
			String orgId = fftOrg.getOrgId();//组织Id(boss对应simpleId生成的Id，bank对应cb_org表主键id)
			/**角色分配**/
			if(Checker.isNotEmpty(roleIds)){
				orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
				OrgRole orgRole = null;
				List<OrgRole> orgRoleList = new ArrayList<OrgRole>();
				for(long roleId:roleIds){
					orgRole = new OrgRole();
					orgRole.setOrgId(orgId);
					orgRole.setRoleId(roleId);
					orgRoleList.add(orgRole);
				}
				orgRoleMapper.addOrgRoleByBatch(orgRoleList);
			}
			
			/**数据权限**/
			if(Checker.isNotEmpty(reOrgIds)){
				fftOrgReMapper = sqlSession.getMapper(FFTOrgReMapper.class);
				List<FFTOrgRe> fftOrgReList = new ArrayList<FFTOrgRe>();
				FFTOrgRe fftOrgRe = null;
				for(String reOrgId:reOrgIds){
					fftOrgRe = new FFTOrgRe();
					fftOrgRe.setOrgId(orgId);
					fftOrgRe.setReOrgId(reOrgId);
					fftOrgRe.setCode(code);
					fftOrgRe.setPlatform(platform);
					fftOrgReList.add(fftOrgRe);
				}
				fftOrgReMapper.addFFTOrgReByBatch(fftOrgReList);
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
     * 修改 FFTOrg
     * @param fftOrg  修改信息
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateFFTOrg(FFTOrg fftOrg,List<Long> roleIds, List<String> reOrgIds) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		OrgRoleMapper orgRoleMapper = null;
		FFTOrgReMapper fftOrgReMapper = null;
		FFTUserRoleMapper fftUserRoleMapper = null;
		FFTUserOrgMapper fftUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			
			FFTOrg oldFFTOrg = fftOrgMapper.findFFTOrgById(fftOrg.getId());
			//boss自身组织
			if(oldFFTOrg.getPlatform().equals(Platform.boss.getType())){
				/**组织基本信息**/
				fftOrgMapper.updateFFTOrg(fftOrg);
			}
			//接入行组织
			else if(fftOrg.getPlatform().equals(Platform.bank.getType())){
				//修改银行组织时，web后端会先去调bank模块添加机构、商户、门店信息，而后再添加本组织表
				//因：在原cb_org机构表中增加了insert/update触发器同步到cb_fft_org组织表，故而此处不需修改组织表
				
				//暂无特殊操作
			}
			
			
			/**角色分配**/
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			String orgId = oldFFTOrg.getOrgId();
			//先删再加
			orgRoleMapper.deleteOrgRoleByOrgId(orgId);
			if(Checker.isNotEmpty(roleIds)){
				OrgRole orgRole = null;
				List<OrgRole> orgRoleList = new ArrayList<OrgRole>();
				for(long roleId:roleIds){
					orgRole = new OrgRole();
					orgRole.setOrgId(orgId);
					orgRole.setRoleId(roleId);
					orgRoleList.add(orgRole);
				}
				orgRoleMapper.addOrgRoleByBatch(orgRoleList);
			}
			
			/**数据权限**/
			fftOrgReMapper = sqlSession.getMapper(FFTOrgReMapper.class);
			//先删再加
			fftOrgReMapper.deleteFFTOrgRe(orgId);
			if(Checker.isNotEmpty(reOrgIds)){
				FFTOrgRe fftOrgRe = null;
				List<FFTOrgRe> fftOrgReList = new ArrayList<FFTOrgRe>();
				for(String reOrgId:reOrgIds){
					fftOrgRe = new FFTOrgRe();
					fftOrgRe.setOrgId(orgId);
					fftOrgRe.setReOrgId(reOrgId);
					fftOrgRe.setCode(oldFFTOrg.getCode());
					fftOrgRe.setPlatform(oldFFTOrg.getPlatform());
					fftOrgReList.add(fftOrgRe);
				}
				fftOrgReMapper.addFFTOrgReByBatch(fftOrgReList);
			}
			
			/**用户继承组织得来的角色，用户角色关系修改**/
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);
			//查询哪些用户继承了该组织的角色
			//List<FFTUserRole> fftUserRoles = fftUserRoleMapper.findFFTUserRoleByOrgId(orgId,Integer.valueOf(Source.orgSource.getType()));
			//查询哪些用户所属该组织
			List<FFTUserOrg> fftUserOrgs = fftUserOrgMapper.findFFTUserOrgByOrgId(orgId);
			
			//先删再加
			fftUserRoleMapper.deleteFFTUserRoleByOrgId(orgId);
			if(Checker.isNotEmpty(roleIds)){
				List<FFTUserRole> addFFTUserRoleList = new ArrayList<FFTUserRole>();
				FFTUserRole addFFTUserRole = null;
				for(FFTUserOrg f : fftUserOrgs){
					for(long roleId : roleIds){
						addFFTUserRole = new FFTUserRole();
						addFFTUserRole.setOrgId(orgId);
						addFFTUserRole.setSource(Integer.valueOf(Source.orgSource.getType()));
						addFFTUserRole.setRoleId(roleId);
						addFFTUserRole.setUserId(f.getUserId());
						
						addFFTUserRoleList.add(addFFTUserRole);
					}
				}
				if(Checker.isNotEmpty(addFFTUserRoleList)){
					fftUserRoleMapper.addFFTUserRoleByBatch(addFFTUserRoleList);
				}
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
     * 分页查询 FFTOrg
     * @param page
     * @param FFTOrg
     * @return Page<FFTOrg>    结果集合 
     */
	@Override
	public Page<FFTOrg> findFFTOrgByPage(Page<FFTOrg> page, FFTOrg fftOrg) throws Exception{

		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findByPage(page, fftOrg); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	/**
     * 删除组织 FFTOrg
     * @param id
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteFFTOrg(Long id) throws FroadServerException, Exception {
		Boolean result = false;
		SqlSession sqlSession = null;
		FFTUserRoleMapper fftUserRoleMapper = null;//用户角色
		OrgRoleMapper orgRoleMapper = null;//组织角色
		FFTUserOrgMapper fftUserOrgMapper = null;//用户组织
		FFTOrgReMapper fftOrgReMapper = null;//组织数据权限
		FFTOrgMapper fftOrgMapper = null;//组织
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);
			fftOrgReMapper = sqlSession.getMapper(FFTOrgReMapper.class);
			fftUserRoleMapper = sqlSession.getMapper(FFTUserRoleMapper.class);
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);
			
			/**
			 * 若组织下存在子组织则不允许删除，删除时提示"此组织下存在子组织不允许删除！"。
			 * 若组织存在用户则不允许删除，并提示"此组织下存在用户，不允许删除！"。
			 * 若组织下无任何子组织和用户则直接删除，包括关联角色信息，数据权限
			 */
			FFTOrg find = new FFTOrg();
			find.setIsDelete(false);
			find.setParentId(id);
			List<FFTOrg> fftOrgs = fftOrgMapper.findFFTOrg(find);
			if(Checker.isNotEmpty(fftOrgs)){
				throw new FroadServerException("此组织下存在子组织不允许删除！");
			}
			
			FFTOrg resultFFTOrg = fftOrgMapper.findFFTOrgById(id);
			if(Checker.isEmpty(resultFFTOrg)){
				throw new FroadServerException("组织信息有误！");
			}
			String orgId = resultFFTOrg.getOrgId();
			//查询该组织下有效用户的用户组织关系信息
			List<FFTUserOrg> fftuserOrgs = fftUserOrgMapper.findFFTUserOrgByOrgIdStatus(orgId);
			if(Checker.isNotEmpty(fftuserOrgs)){
				throw new FroadServerException("此组织下存在用户，不允许删除！");
			}
			
			//删除用户继承组织得来的角色
			result = fftUserRoleMapper.deleteFFTUserRoleByOrgId(orgId);
			//删除组织角色
			result = orgRoleMapper.deleteOrgRoleByOrgId(orgId);
			//删除用户组织关系
			result = fftUserOrgMapper.deleteFFTUserOrgByOrgId(orgId);
			//删除组织数据权限
			result = fftOrgReMapper.deleteFFTOrgRe(orgId);
			//删除组织表
			result = fftOrgMapper.deleteFFTOrg(id);
			
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
     * 组织详情
     * @param id 主键Id
     * @return FFTOrg 结果对象
     */
	@Override
	public FFTOrg findFFTOrgDetail(Long id) throws Exception {
		FFTOrg result = new FFTOrg(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findFFTOrgById(id);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}



	/**
     * 组织列表拉取-查全部
     * @param userId 用户Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public List<FFTOrg> findFFTOrgByUserId(Long userId) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=boss的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			if(isAdminRole){
				FFTOrg find = new FFTOrg();
				find.setIsDelete(false);
				result = fftOrgMapper.findFFTOrg(find);
			}else{
				result = fftOrgMapper.findFFTOrgReByUserId(userId,null,null);
			}
			
			
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	/**
     * 组织列表拉取-查顶级2级
     * @param userId 用户Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public List<FFTOrg> findFFTOrgByUserIdTwoLevel(Long userId) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=boss的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			if(isAdminRole){
//				FFTOrg find = new FFTOrg();
//				find.setIsDelete(false);
//				find.setLevel(1);
//				result = fftOrgMapper.findFFTOrg(find);
//				//查最顶级
//				find = new FFTOrg();
//				find.setIsDelete(false);
//				find.setLevel(0);
//				result.add(fftOrgMapper.findFFTOrg(find).get(0));
				//因boss的level只是个属性，不做级别控制，故而不能从level判断
				result = fftOrgMapper.findFFTOrgReByUserIdTwoLevel();
			}else{
				//普通用户只查boss的一二级
				result = fftOrgMapper.findFFTOrgReByBossUserIdTwoLevel(userId);
			}
			
			
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	/**
     * 组织列表拉取-查用户下该组织id下一级的权限组织
     * @param userId 用户Id
     * @param orgId 组织Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public List<FFTOrg> findFFTOrgByUserIdOrgId(Long userId,String orgId) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		//BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			//bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=boss的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			FFTOrg fftOrg = fftOrgMapper.findFFTOrgByOrgId(orgId);
			if(Checker.isEmpty(fftOrg)){
				throw new FroadServerException("组织信息不存在！");
			}
			
			//boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			//if(isAdminRole){
				FFTOrg find = new FFTOrg();
				find.setIsDelete(false);
				find.setParentId(fftOrg.getId());
				result = fftOrgMapper.findFFTOrg(find);
			//}else{
				//result = fftOrgMapper.findFFTOrgReByBossUserIdOrgId(userId,fftOrg.getId());
			//}
			
			
		}catch (FroadServerException e) { 
			throw e;
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	/**
     * 银行渠道列表(获取银行组织的一级)
     * @param userId 用户Id
     * @return List<FFTOrg>    结果集合 
     */
	@Override
	public List<FFTOrg> findFFTOrgInOneByBank(Long userId) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=bank的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			if(isAdminRole){
				FFTOrg find = new FFTOrg();
				find.setIsDelete(false);
				find.setPlatform(Platform.bank.getType());
				find.setLevel(1);
				result = fftOrgMapper.findFFTOrg(find);
			}else{
				//查出用户的所有组织权限，取出clientId，从而取出clientId对应的一级组织
				List<FFTOrg> fftOrgs = fftOrgMapper.findFFTOrgReByUserId(userId,Platform.bank.getType(),null);
				Set<FFTOrg> setFFTOrgs = new HashSet<FFTOrg>(fftOrgs);
				Set<String> clientIds = new HashSet<String>();
				for(FFTOrg f : setFFTOrgs){
					clientIds.add(f.getClientId());
				}
				
				if(Checker.isNotEmpty(clientIds)){
					result = fftOrgMapper.findFFTOrgOneBankByClientIds(new ArrayList<String>(clientIds));
				}
			}
			
			
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	/**
	 * 所属组织-数据权限下拉框
	 * @param userId 用户Id
	 * @param clientId 客户端Id
     * @return List<FFTOrg>    结果集合 
     */
	@Override
	public List<FFTOrg> findFFTOrgByUserIdPlatform(Long userId,String clientId) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=bank的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			if(isAdminRole){
				//result = fftOrgMapper.findFFTOrgNotOne(clientId);
				FFTOrg find = new FFTOrg();
				find.setClientId(clientId);
				find.setPlatform(Platform.bank.getType());
				find.setIsDelete(false);
				result = fftOrgMapper.findFFTOrg(find);
			}else{
				//权限组织
				result = fftOrgMapper.findFFTOrgReByUserId(userId,Platform.bank.getType(),clientId);
				if(Checker.isEmpty(result)){
					return result;
				}
				
				//判断数据权限中有没有一级组织，若有，则查全部非一级组织
				boolean isOneLevel = false;
				for(FFTOrg f : result){
					if(f.getLevel()==1){
						isOneLevel = true;
						continue;
					}
				}
				if(isOneLevel){
					//result = fftOrgMapper.findFFTOrgNotOne(clientId);
					FFTOrg find = new FFTOrg();
					find.setClientId(clientId);
					find.setPlatform(Platform.bank.getType());
					find.setIsDelete(false);
					result = fftOrgMapper.findFFTOrg(find);
				}else{
					//非一级
					Set<Long> ids = new HashSet<Long>();
					for(FFTOrg f : result){
						ids.add(f.getId());
					}
					//查询权限组织下级组织
					result = fftOrgMapper.findChildListByIds(new ArrayList<Long>(ids));
				}
				
				
			}
			
			
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	
	/**
     * 验证组织下是否有下级组织
     * @param userId 用户Id
     * @param id集合，组织表主键id集合
     * @return HashMap<Long, Boolean>    结果集合 
     */
	@Override
	public HashMap<Long, Boolean> isNextFFTOrg(Long userId,List<Long> ids) throws Exception{
		HashMap<Long, Boolean> result = new HashMap<Long, Boolean>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		//BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);
			//bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			/**
			 * 1.从cb_fft_user_role表查出用户关联的角色列表
			 * 2.根据cb_role表判断用户角色列表是否包含超级管理员
			 * --是，拉取cb_fft_org拉取所有组织
			 * --否，1.从cb_fft_user_org获取用户所属platform=boss的组织列表去重
			 *      2.从cb_fft_org_re获取所属组织对应的权限组织ID列表
			 * 
			 */
			
			//boolean isAdminRole = bossRoleMapper.isAdminRole(userId)>0;
			List<FFTOrg> quantitys = null;
			//if(isAdminRole){
				quantitys = fftOrgMapper.findChildListCount(ids);
			//}else{
				//quantitys = fftOrgMapper.findFFTOrgReByBossUserIdCount(userId,ids);
			//}
			
			for(FFTOrg f:quantitys){
				result.put(f.getParentId(), f.getQuantity()>0?true:false);
			}
			
		}catch (FroadServerException e) { 
			throw e;
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	/**
     * 查询 FFTOrg
     * @param fftOrg
     * @return List<FFTOrg>    结果集合 
     */
	@Override
	public List<FFTOrg> findFFTOrgByList(FFTOrg fftOrg) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findFFTOrg(fftOrg);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	/**
     * 查询 FFTOrg  by 组织orgId
     * @param fftOrg
     * @return FFTOrg 对象
     */
	@Override
	public FFTOrg findFFTOrgByOrgId(String orgId) throws Exception {
		FFTOrg result = new FFTOrg(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findFFTOrgByOrgId(orgId);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	/**
     * 组织ID多集合查询
     * @param orgIds 组织Id集合
     * @return List<FFTOrg>    结果集合 
     */
	@Override
	public List<FFTOrg> findFFTOrgByOrgIds(List<String> orgIds) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findFFTOrgByOrgIds(orgIds);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	/**
     * 主键ID多集合查询
     * @param ids 主键id集合
     * @return List<FFTOrg>    结果集合 
     */
	@Override
	public List<FFTOrg> findFFTOrgByIds(List<Long> ids) throws Exception {
		List<FFTOrg> result = new ArrayList<FFTOrg>(); 
		SqlSession sqlSession = null;
		FFTOrgMapper fftOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgMapper = sqlSession.getMapper(FFTOrgMapper.class);

			result = fftOrgMapper.findFFTOrgByIds(ids);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	

	/**
     * 组织数据权限查询
     * @param orgId 组织Id
     * @return List<FFTOrgRe>    结果集合 
     */
	@Override
	public List<FFTOrgRe> findFFTOrgReByOrgId(String orgId) throws Exception {
		List<FFTOrgRe> result = new ArrayList<FFTOrgRe>(); 
		SqlSession sqlSession = null;
		FFTOrgReMapper fftOrgReMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftOrgReMapper = sqlSession.getMapper(FFTOrgReMapper.class);

			result = fftOrgReMapper.findFFTOrgReByOrgId(orgId);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	/**
     * 组织角色查询
     * @param orgId 组织Id
     * @return List<OrgRole>    结果集合 
     */
	@Override
	public List<OrgRole> findOrgRoleByOrgId(String orgId) throws Exception {
		List<OrgRole> result = new ArrayList<OrgRole>(); 
		SqlSession sqlSession = null;
		OrgRoleMapper orgRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);

			result = orgRoleMapper.findOrgRoleByOrgId(orgId);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	/**
     * 组织角色多集合查询
     * @param orgId 组织Id集合
     * @return List<OrgRole>    结果集合 
     */
	@Override
	public List<OrgRole> findOrgRoleByOrgIds(List<String> orgIds) throws Exception {
		List<OrgRole> result = new ArrayList<OrgRole>(); 
		SqlSession sqlSession = null;
		OrgRoleMapper orgRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgRoleMapper = sqlSession.getMapper(OrgRoleMapper.class);

			result = orgRoleMapper.findOrgRoleByOrgIds(orgIds);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	


}