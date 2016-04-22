/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossRoles   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossRoleLogicImpl.java
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
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.BossRoleMapper;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossRoleLogic;
import com.froad.po.BossRole;

/**
 * 
 * <p>@Title: BossRoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossRoleLogicImpl implements BossRoleLogic {


	/**
     * 增加 BossRole
     * @param bossRole
     * @return Long    主键ID
     */
	@Override
	public ResultBean addBossRole(BossRole bossRole) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加角色信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			if (bossRoleMapper.addBossRole(bossRole) > -1) { 
				result = bossRole.getId(); 
			}
			sqlSession.commit(true); 
			 resultBean= new ResultBean(ResultCode.success,"添加角色信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加BossRole失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加角色信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteBossRole(BossRole bossRole) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除角色信息成功");
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			bossRoleMapper.deleteBossRole(bossRole); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除BossRole失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除角色信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 BossRole
     * @param bossRole
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateBossRole(BossRole bossRole) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			bossRoleMapper.updateBossRole(bossRole); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改BossRole失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改角色信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 BossRole
     * @param bossRole
     * @return List<BossRole>    结果集合 
     */
	@Override
	public List<BossRole> findBossRole(BossRole bossRole) {

		List<BossRole> result = new ArrayList<BossRole>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			result = bossRoleMapper.findBossRole(bossRole); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询BossRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossRole
     * @param page
     * @param bossRole
     * @return Page<BossRole>    结果集合 
     */
	@Override
	public Page<BossRole> findBossRoleByPage(Page<BossRole> page, BossRole bossRole) {

		List<BossRole> result = new ArrayList<BossRole>(); 
		SqlSession sqlSession = null;
		BossRoleMapper bossRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossRoleMapper = sqlSession.getMapper(BossRoleMapper.class);

			result = bossRoleMapper.findByPage(page, bossRole); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}