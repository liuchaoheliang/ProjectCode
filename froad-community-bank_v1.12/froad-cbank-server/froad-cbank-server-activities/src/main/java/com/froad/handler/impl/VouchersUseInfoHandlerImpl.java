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
 * @Title: VouchersUseInfoHandlerImpl.java
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
import com.froad.db.mysql.mapper.VouchersUseInfoMapper;
import com.froad.handler.VouchersUseInfoHandler;
import com.froad.po.VouchersUseInfo;

/**
 * 
 * <p>@Title: VouchersUseInfoHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体VouchersUseInfo的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersUseInfoHandlerImpl implements VouchersUseInfoHandler {


    /**
     * 增加 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersUseInfo(VouchersUseInfo vouchersUseInfo)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addVouchersUseInfo(sqlSession, vouchersUseInfo);

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
     * 增加 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception {

		Long result = null; 
		VouchersUseInfoMapper vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);
		if (vouchersUseInfoMapper.addVouchersUseInfo(vouchersUseInfo) > -1) { // 添加成功
			result = vouchersUseInfo.getId(); 
		}
		return result; 

	}


    /**
     * 删除 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersUseInfo(sqlSession, vouchersUseInfo); 
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
     * 删除 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersUseInfoMapper.class).deleteVouchersUseInfo(vouchersUseInfo); 
	}


    /**
     * 根据id删除 VouchersUseInfo
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersUseInfoById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersUseInfoById(sqlSession, id); 
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
     * 根据id删除 VouchersUseInfo
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersUseInfoById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersUseInfoMapper.class).deleteVouchersUseInfoById(id); 
	}


    /**
     * 修改 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersUseInfo(sqlSession, vouchersUseInfo); 
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
     * 修改 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersUseInfo(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersUseInfoMapper.class).updateVouchersUseInfo(vouchersUseInfo); 
	}


    /**
     * 根据id修改 VouchersUseInfo
     * @param VouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersUseInfoById(VouchersUseInfo vouchersUseInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersUseInfoById(sqlSession, vouchersUseInfo); 
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
     * 根据id修改 VouchersUseInfo
     * @param sqlSession
     * @param vouchersUseInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersUseInfoById(SqlSession sqlSession, VouchersUseInfo vouchersUseInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersUseInfoMapper.class).updateVouchersUseInfoById(vouchersUseInfo); 
	}


    /**
     * 根据id查询单个 VouchersUseInfo
     * @param id
     * @return VouchersUseInfo    单个VouchersUseInfo
     * @throws Exception
     */
	@Override
	public VouchersUseInfo findVouchersUseInfoById(Long id) throws Exception {

		VouchersUseInfo result = null; 
		SqlSession sqlSession = null;
		VouchersUseInfoMapper vouchersUseInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);

			result = vouchersUseInfoMapper.findVouchersUseInfoById(id); 
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
     * 查询一个 VouchersUseInfo
     * @param vouchersUseInfo
     * @return VouchersUseInfo    单个VouchersUseInfo
     * @throws Exception
     */
	@Override
	public VouchersUseInfo findOneVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception {

		VouchersUseInfo result = null; 
		SqlSession sqlSession = null;
		VouchersUseInfoMapper vouchersUseInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);

			result = vouchersUseInfoMapper.findOneVouchersUseInfo(vouchersUseInfo); 
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
     * 查询统计 VouchersUseInfo
     * @param vouchersUseInfo
     * @return Integer    返回记录数VouchersUseInfo
     * @throws Exception
     */
	@Override
	public Integer countVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		VouchersUseInfoMapper vouchersUseInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);

			result = vouchersUseInfoMapper.countVouchersUseInfo(vouchersUseInfo); 
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
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo) throws Exception {

		return this.findVouchersUseInfo(vouchersUseInfo, null); 

	}


    /**
     * 查询 VouchersUseInfo
     * @param vouchersUseInfo
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersUseInfo>    VouchersUseInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersUseInfo> findVouchersUseInfo(VouchersUseInfo vouchersUseInfo, String order) throws Exception {

		List<VouchersUseInfo> result = null; 
		SqlSession sqlSession = null;
		VouchersUseInfoMapper vouchersUseInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);

			result = vouchersUseInfoMapper.findVouchersUseInfo(vouchersUseInfo, order); 
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
     * 分页查询 VouchersUseInfo
     * @param page
     * @param vouchersUseInfo
     * @return Page<VouchersUseInfo>    VouchersUseInfo分页结果 
     * @throws Exception 
     */
	@Override
	public Page<VouchersUseInfo> findVouchersUseInfoByPage(Page<VouchersUseInfo> page, VouchersUseInfo vouchersUseInfo) throws Exception {

		List<VouchersUseInfo> result = new ArrayList<VouchersUseInfo>(); 
		SqlSession sqlSession = null;
		VouchersUseInfoMapper vouchersUseInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersUseInfoMapper = sqlSession.getMapper(VouchersUseInfoMapper.class);

			result = vouchersUseInfoMapper.findByPage(page, vouchersUseInfo); 
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