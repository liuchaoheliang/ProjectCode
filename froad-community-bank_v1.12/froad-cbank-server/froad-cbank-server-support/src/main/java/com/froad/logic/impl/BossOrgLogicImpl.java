/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossOrgs   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossOrgLogicImpl.java
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
import com.froad.db.mysql.mapper.BossOrgMapper;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossOrgLogic;
import com.froad.po.BossOrg;

/**
 * 
 * <p>@Title: BossOrgLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossOrgLogicImpl implements BossOrgLogic {

	/**
     * 增加 BossOrg
     * @param bossOrg
     * @return Long    主键ID
     */
	@Override
	public ResultBean addBossOrg(BossOrg bossOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加机构表信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		BossOrgMapper bossOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossOrgMapper = sqlSession.getMapper(BossOrgMapper.class);

			if (bossOrgMapper.addBossOrg(bossOrg) > -1) { 
				result = bossOrg.getId(); 
			}
			sqlSession.commit(true); 
			 resultBean= new ResultBean(ResultCode.success,"添加机构表信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加BossOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加机构表信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteBossOrg(BossOrg bossOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除机构表信息成功");
		SqlSession sqlSession = null;
		BossOrgMapper bossOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossOrgMapper = sqlSession.getMapper(BossOrgMapper.class);

			bossOrgMapper.deleteBossOrg(bossOrg); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除BossOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除机构表信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 BossOrg
     * @param bossOrg
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateBossOrg(BossOrg bossOrg) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		SqlSession sqlSession = null;
		BossOrgMapper bossOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossOrgMapper = sqlSession.getMapper(BossOrgMapper.class);

			bossOrgMapper.updateBossOrg(bossOrg); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改BossOrg失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改机构表信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 BossOrg
     * @param bossOrg
     * @return List<BossOrg>    结果集合 
     */
	@Override
	public List<BossOrg> findBossOrg(BossOrg bossOrg) {

		List<BossOrg> result = new ArrayList<BossOrg>(); 
		SqlSession sqlSession = null;
		BossOrgMapper bossOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossOrgMapper = sqlSession.getMapper(BossOrgMapper.class);

			result = bossOrgMapper.findBossOrg(bossOrg); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询BossOrg失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossOrg
     * @param page
     * @param bossOrg
     * @return Page<BossOrg>    结果集合 
     */
	@Override
	public Page<BossOrg> findBossOrgByPage(Page<BossOrg> page, BossOrg bossOrg) {

		List<BossOrg> result = new ArrayList<BossOrg>(); 
		SqlSession sqlSession = null;
		BossOrgMapper bossOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossOrgMapper = sqlSession.getMapper(BossOrgMapper.class);

			result = bossOrgMapper.findByPage(page, bossOrg); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossOrg失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}