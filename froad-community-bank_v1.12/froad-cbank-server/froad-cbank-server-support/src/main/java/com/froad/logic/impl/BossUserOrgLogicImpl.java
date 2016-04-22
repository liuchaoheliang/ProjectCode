/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossUserOrgs   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossUserOrgLogicImpl.java
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
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.BossUserOrgMapper;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossUserOrgLogic;
import com.froad.po.BossUserOrg;

/**
 * 
 * <p>@Title: BossUserOrgLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossUserOrgLogicImpl implements BossUserOrgLogic {


	/**
     * 增加 BossUserOrg
     * @param bossUserOrg
     * @return Long    主键ID
     */
	@Override
	public ResultBean addBossUserOrg(BossUserOrg bossUserOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加用户机构关联信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		BossUserOrgMapper bossUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserOrgMapper = sqlSession.getMapper(BossUserOrgMapper.class);

			if (bossUserOrgMapper.addBossUserOrg(bossUserOrg) > -1) { 
				result = bossUserOrg.getId(); 
			}
			sqlSession.commit(true); 
			 resultBean= new ResultBean(ResultCode.success,"添加用户机构关联信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加BossUserOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加用户机构关联信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteBossUserOrg(BossUserOrg bossUserOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除用户机构关联信息成功");
		SqlSession sqlSession = null;
		BossUserOrgMapper bossUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserOrgMapper = sqlSession.getMapper(BossUserOrgMapper.class);

			bossUserOrgMapper.deleteBossUserOrg(bossUserOrg); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除BossUserOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除用户机构关联信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 BossUserOrg
     * @param bossUserOrg
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateBossUserOrg(BossUserOrg bossUserOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		SqlSession sqlSession = null;
		BossUserOrgMapper bossUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserOrgMapper = sqlSession.getMapper(BossUserOrgMapper.class);

			bossUserOrgMapper.updateBossUserOrg(bossUserOrg); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改BossUserOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改用户机构关联信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 BossUserOrg
     * @param bossUserOrg
     * @return List<BossUserOrg>    结果集合 
     */
	@Override
	public List<BossUserOrg> findBossUserOrg(BossUserOrg bossUserOrg) {

		List<BossUserOrg> result = new ArrayList<BossUserOrg>(); 
		SqlSession sqlSession = null;
		BossUserOrgMapper bossUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserOrgMapper = sqlSession.getMapper(BossUserOrgMapper.class);

			result = bossUserOrgMapper.findBossUserOrg(bossUserOrg); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询BossUserOrg失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossUserOrg
     * @param page
     * @param bossUserOrg
     * @return Page<BossUserOrg>    结果集合 
     */
	@Override
	public Page<BossUserOrg> findBossUserOrgByPage(Page<BossUserOrg> page, BossUserOrg bossUserOrg) {

		List<BossUserOrg> result = new ArrayList<BossUserOrg>(); 
		SqlSession sqlSession = null;
		BossUserOrgMapper bossUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserOrgMapper = sqlSession.getMapper(BossUserOrgMapper.class);

			result = bossUserOrgMapper.findByPage(page, bossUserOrg); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossUserOrg失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}