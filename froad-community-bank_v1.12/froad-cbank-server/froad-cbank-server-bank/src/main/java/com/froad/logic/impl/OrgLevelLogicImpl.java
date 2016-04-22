package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.OrgLevelMapper;
import com.froad.db.redis.OrgLevelRedis;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.OrgLevelLogic;
import com.froad.po.OrgLevel;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: OrgLevelLogic.java</p>
 * <p>Description: 描述 </p> 银行联合登录-机构级别角色关系Logic实现类
 * @author f-road-ll
 * @version 1.0
 * @created 2015年3月17日
 */
public class OrgLevelLogicImpl implements OrgLevelLogic {


    /**
     * 增加银行联合登录-机构角色关系表 OrgLevel
     * @param orgLevel
     * @return Long    主键ID
     */
	@Override
	public Long addOrgLevel(OrgLevel orgLevel)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		OrgLevelMapper orgLevelMapper = null;
		long orgLevelId=0l;
		try { 
			//验证client_id+role_id+org_level是否已存在
			List<OrgLevel> filter=this.findOrgLevel(orgLevel);
			if(Checker.isNotEmpty(filter)){
				throw new FroadServerException(orgLevel.getClientId()+":"+orgLevel.getRoleId()+":"+orgLevel.getOrgLevel()+"初始化联合登录默认角色已存在！");
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);

			orgLevelMapper.addOrgLevel(orgLevel); 
			
			
			/**********************操作Redis缓存**********************/
			OrgLevelRedis.set_cbbank_bank_level_role_client_id_org_level(orgLevel);
			
			orgLevelId=Long.valueOf(orgLevel.getId());
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
		return orgLevelId; 

	}


    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteOrgLevel(OrgLevel orgLevel)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgLevelMapper orgLevelMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
			OrgLevel find = new OrgLevel();
			find.setId(orgLevel.getId());
			//根据主键过滤查询出对象，删除缓存信息
			List<OrgLevel> findList = this.findOrgLevel(find);
			if(Checker.isNotEmpty(findList)){
				OrgLevel oldOrgLevel = findList.get(0);
				//删除缓存对象信息
				result=OrgLevelRedis.del_cbbank_bank_level_role_client_id_org_level(oldOrgLevel);
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);
			result = orgLevelMapper.deleteOrgLevel(orgLevel); 
			
			//提交
			if(result){
				sqlSession.commit(true);
			}else { // 删除失败
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
     * 修改 OrgLevel
     * @param orgLevel
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateOrgLevel(OrgLevel orgLevel)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgLevelMapper orgLevelMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);

			result = orgLevelMapper.updateOrgLevel(orgLevel); 
			
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			OrgLevel find = new OrgLevel();
			find.setId(orgLevel.getId());
			//根据主键过滤查询出对象，将最新修改过后的数据重新set到缓存中
			List<OrgLevel> findList = this.findOrgLevel(find);
			if(Checker.isNotEmpty(findList)){
				orgLevel = findList.get(0);
				//缓存对象信息
				result=OrgLevelRedis.set_cbbank_bank_level_role_client_id_org_level(orgLevel);
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
     * 查询 OrgLevel缓存数据role_id
     * @param clientId 客户端id
     * @param orgLevel 机构级别
     * @return role_id 角色id
     */
	@Override
	public Long findOrgLevelByRedis(String clientId, String orgLevel) {
		Long roleId=null;
		try{
			roleId=OrgLevelRedis.get_cbbank_bank_level_role_client_id_org_level(clientId, orgLevel, "role_id");
			if(roleId==-1){//若从缓存中没查到数据，需从mysql中查
				OrgLevel orgLevelPo =new OrgLevel();
				orgLevelPo.setClientId(clientId);
				orgLevelPo.setOrgLevel(orgLevel);
				List<OrgLevel> orgs=this.findOrgLevel(orgLevelPo);
				if(orgs!=null&&orgs.size()==1){
					roleId=orgs.get(0).getRoleId();
					return roleId;
				}else{
					return null;
				}
			}
		}catch (Exception e) { 
			LogCvt.error("查询OrgLevel缓存数据失败，原因:" + e.getMessage(), e); 
		}
		return null;
	}
	
	
	
    /**
     * 查询 OrgLevel
     * @param orgLevel
     * @return List<OrgLevel>    结果集合 
     */
	@Override
	public List<OrgLevel> findOrgLevel(OrgLevel orgLevel) {

		List<OrgLevel> result = new ArrayList<OrgLevel>(); 
		SqlSession sqlSession = null;
		OrgLevelMapper orgLevelMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);

			result = orgLevelMapper.findOrgLevel(orgLevel); 
		} catch (Exception e) { 
			LogCvt.error("查询OrgLevel失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 OrgLevel
     * @param page
     * @param orgLevel
     * @return List<OrgLevel>    结果集合 
     */
	@Override
	public Page<OrgLevel> findOrgLevelByPage(Page<OrgLevel> page, OrgLevel orgLevel) {

		List<OrgLevel> result = new ArrayList<OrgLevel>(); 
		SqlSession sqlSession = null;
		OrgLevelMapper orgLevelMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgLevelMapper = sqlSession.getMapper(OrgLevelMapper.class);

			result = orgLevelMapper.findByPage(page, orgLevel); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询OrgLevel失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}




}