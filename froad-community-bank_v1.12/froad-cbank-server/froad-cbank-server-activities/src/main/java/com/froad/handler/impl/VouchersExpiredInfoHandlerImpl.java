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
 * @Title: VouchersExpiredInfoHandlerImpl.java
 * @Package com.froad.handler.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.VouchersExpiredInfoMapper;
import com.froad.handler.VouchersExpiredInfoHandler;
import com.froad.po.VouchersExpiredInfo;

/**
 * 
 * <p>@Title: VouchersExpiredInfoHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体VouchersExpiredInfo的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersExpiredInfoHandlerImpl implements VouchersExpiredInfoHandler {


    /**
     * 增加 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addVouchersExpiredInfo(sqlSession, vouchersExpiredInfo);

			if (null != result) { // 添加成功

				sqlSession.commit(true); 

			} else { // 添加失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = null; 
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
     * 增加 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		Long result = null; 
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);
		if (vouchersExpiredInfoMapper.addVouchersExpiredInfo(vouchersExpiredInfo) > -1) { // 添加成功
			result = vouchersExpiredInfo.getId(); 
		}
		return result; 

	}


    /**
     * 删除 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersExpiredInfo(sqlSession, vouchersExpiredInfo); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
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
     * 删除 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersExpiredInfoMapper.class).deleteVouchersExpiredInfo(vouchersExpiredInfo); 
	}


    /**
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersExpiredInfoById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersExpiredInfoById(sqlSession, id); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
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
     * 根据id删除 VouchersExpiredInfo
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersExpiredInfoById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersExpiredInfoMapper.class).deleteVouchersExpiredInfoById(id); 
	}


    /**
     * 修改 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersExpiredInfo(sqlSession, vouchersExpiredInfo); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
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
     * 修改 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersExpiredInfo(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersExpiredInfoMapper.class).updateVouchersExpiredInfo(vouchersExpiredInfo); 
	}


    /**
     * 根据id修改 VouchersExpiredInfo
     * @param VouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersExpiredInfoById(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersExpiredInfoById(sqlSession, vouchersExpiredInfo); 
			if (result > -1) { // 修改成功

				sqlSession.commit(true);

			} else { // 修改失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
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
     * 根据id修改 VouchersExpiredInfo
     * @param sqlSession
     * @param vouchersExpiredInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersExpiredInfoById(SqlSession sqlSession, VouchersExpiredInfo vouchersExpiredInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersExpiredInfoMapper.class).updateVouchersExpiredInfoById(vouchersExpiredInfo); 
	}


    /**
     * 根据id查询单个 VouchersExpiredInfo
     * @param id
     * @return VouchersExpiredInfo    单个VouchersExpiredInfo
     * @throws Exception
     */
	@Override
	public VouchersExpiredInfo findVouchersExpiredInfoById(Long id) throws Exception {

		VouchersExpiredInfo result = null; 
		SqlSession sqlSession = null;
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);

			result = vouchersExpiredInfoMapper.findVouchersExpiredInfoById(id); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询一个 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return VouchersExpiredInfo    单个VouchersExpiredInfo
     * @throws Exception
     */
	@Override
	public VouchersExpiredInfo findOneVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		VouchersExpiredInfo result = null; 
		SqlSession sqlSession = null;
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);

			result = vouchersExpiredInfoMapper.findOneVouchersExpiredInfo(vouchersExpiredInfo); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询统计 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return Integer    返回记录数VouchersExpiredInfo
     * @throws Exception
     */
	@Override
	public Integer countVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);

			result = vouchersExpiredInfoMapper.countVouchersExpiredInfo(vouchersExpiredInfo); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		return this.findVouchersExpiredInfo(vouchersExpiredInfo, null); 

	}


    /**
     * 查询 VouchersExpiredInfo
     * @param vouchersExpiredInfo
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersExpiredInfo>    VouchersExpiredInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersExpiredInfo> findVouchersExpiredInfo(VouchersExpiredInfo vouchersExpiredInfo, String order) throws Exception {

		List<VouchersExpiredInfo> result = null; 
		SqlSession sqlSession = null;
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);

			result = vouchersExpiredInfoMapper.findVouchersExpiredInfo(vouchersExpiredInfo, order); 
		} catch (Exception e) { 
			result = null; 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 VouchersExpiredInfo
     * @param page
     * @param vouchersExpiredInfo
     * @return Page<VouchersExpiredInfo>    VouchersExpiredInfo分页结果 
     * @throws Exception 
     */
	@Override
	public Page<VouchersExpiredInfo> findVouchersExpiredInfoByPage(Page<VouchersExpiredInfo> page, VouchersExpiredInfo vouchersExpiredInfo) throws Exception {

		List<VouchersExpiredInfo> result = new ArrayList<VouchersExpiredInfo>(); 
		SqlSession sqlSession = null;
		VouchersExpiredInfoMapper vouchersExpiredInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersExpiredInfoMapper = sqlSession.getMapper(VouchersExpiredInfoMapper.class);

			result = vouchersExpiredInfoMapper.findByPage(page, vouchersExpiredInfo); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// if(null != sqlSession)  
				// sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}