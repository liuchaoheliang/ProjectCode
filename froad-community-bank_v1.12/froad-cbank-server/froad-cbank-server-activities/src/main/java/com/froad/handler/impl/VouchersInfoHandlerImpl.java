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
 * @Title: VouchersInfoHandlerImpl.java
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
import com.froad.db.mysql.mapper.VouchersInfoMapper;
import com.froad.db.redis.VouchersRedis;
import com.froad.handler.VouchersInfoHandler;
import com.froad.logback.LogCvt;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersUseDetails;

/**
 * 
 * <p>@Title: VouchersInfoHandler.java</p>
 * <p>Description: 实现对MySQL对应的实体VouchersInfo的CURD操作 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class VouchersInfoHandlerImpl implements VouchersInfoHandler {


    /**
     * 增加 VouchersInfo
     * @param vouchersInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersInfo(VouchersInfo vouchersInfo)  throws Exception {

		Long result = null; 
		SqlSession sqlSession = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.addVouchersInfo(sqlSession, vouchersInfo);

			if ( result > 0) { // 添加成功
				
				cutVouchersUseNumber(vouchersInfo.getVouchersId(), vouchersInfo.getMemberCode());
				
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
     * 增加 VouchersInfo
     * @param sqlSession
     * @param vouchersInfo
     * @return Long    主键ID
     * @throws Exception
     */
	@Override
	public Long addVouchersInfo(SqlSession sqlSession, VouchersInfo vouchersInfo) throws Exception {

		Long result = null; 
		VouchersInfoMapper vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
		if (vouchersInfoMapper.addVouchersInfo(vouchersInfo) > -1) { // 添加成功
			result = vouchersInfo.getId(); 
		}
		return result; 

	}


    /**
     * 删除 VouchersInfo
     * @param vouchersInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersInfo(VouchersInfo vouchersInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersInfo(sqlSession, vouchersInfo); 
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
     * 删除 VouchersInfo
     * @param sqlSession
     * @param vouchersInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersInfo(SqlSession sqlSession, VouchersInfo vouchersInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersInfoMapper.class).deleteVouchersInfo(vouchersInfo); 
	}


    /**
     * 根据id删除 VouchersInfo
     * @param id
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersInfoById(Long id) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.deleteVouchersInfoById(sqlSession, id); 
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
     * 根据id删除 VouchersInfo
     * @param id
     * @param sqlSession
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer deleteVouchersInfoById(SqlSession sqlSession, Long id) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersInfoMapper.class).deleteVouchersInfoById(id); 
	}


    /**
     * 修改 VouchersInfo
     * @param vouchersInfo
     * @return Boolean    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersInfo(VouchersInfo vouchersInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersInfo(sqlSession, vouchersInfo); 
			if (result > 0) { // 修改成功
				
				
				
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
	 * 修改 VouchersInfo 支付中
	 * <br>
	 * 批量
	 * @param List<VouchersInfo>
	 * @return 是否成功
	 * @throws Exception
	 */
	@Override
	public Boolean updateVouchersInfoPayIngBatch(List<VouchersInfo> vouchersInfoList) throws Exception{
		
		SqlSession sqlSession = null;
		try{

			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			for( VouchersInfo vouchersInfo : vouchersInfoList ){
				Integer result = updateVouchersInfoPayIng(sqlSession, vouchersInfo);
				if( result <= -1 ){
					sqlSession.rollback(true);
					return false;
				}
			}
			
			sqlSession.commit(true);
			return true;
			
		} catch (Exception e) {
			LogCvt.error("批量修改 VouchersInfo 支付中 异常", e);
			sqlSession.rollback(true);
			return false;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	}
	
	/**
	 * 修改 VouchersInfo 支付中
	 * */
	private Integer updateVouchersInfoPayIng(SqlSession sqlSession, VouchersInfo vouchersInfo){
		return sqlSession.getMapper(VouchersInfoMapper.class).updateVouchersInfoPayIng(vouchersInfo); 
	}
	
	/**
	 * 修改 VouchersInfo 支付成功
	 * <br>
	 * 批量
	 * @param sqlSession
	 * @param List<vouchersId>
	 * @return 是否成功
	 * @throws Exception
	 */
	public Boolean updateVouchersInfoPaySuccessBatch(List<String> vouchersIdList, Long memberCode, String orderId) throws Exception{
		SqlSession sqlSession = null;
		try{

			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			for( String vouchersId : vouchersIdList ){
				Integer result = updateVouchersInfoPaySuccess(sqlSession, vouchersId, memberCode, orderId);
				if( result <= -1 ){
					sqlSession.rollback(true);
					return false;
				}
			}
			
			sqlSession.commit(true);
			return true;
			
		} catch (Exception e) {
			LogCvt.error("批量修改 VouchersInfo 支付成功 异常", e);
			sqlSession.rollback(true);
			return false;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	}
	
	/**
	 * 修改 VouchersInfo 支付成功
	 * */
	private Integer updateVouchersInfoPaySuccess(SqlSession sqlSession, String vouchersId, Long memberCode, String orderId){
		return sqlSession.getMapper(VouchersInfoMapper.class).updateVouchersInfoPaySuccess(vouchersId, memberCode, orderId); 
	}
	
	/**
	 * 修改 VouchersInfo 初始化
	 * <br>
	 * 批量
	 * @param sqlSession
	 * @param List<vouchersId>
	 * @return 是否成功
	 * @throws Exception
	 */
	public Boolean updateVouchersInfoInitBatch(List<String> vouchersIdList) throws Exception{
		SqlSession sqlSession = null;
		try{

			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			for( String vouchersId : vouchersIdList ){
				Integer result = updateVouchersInfoInit(sqlSession, vouchersId);
				if( result <= -1 ){
					sqlSession.rollback(true);
					return false;
				}
			}
			
			sqlSession.commit(true);
			return true;
			
		} catch (Exception e) {
			LogCvt.error("批量修改 VouchersInfo 初始化 异常", e);
			sqlSession.rollback(true);
			return false;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	}
	
	/**
	 * 修改 VouchersInfo 支付成功
	 * */
	private Integer updateVouchersInfoInit(SqlSession sqlSession, String vouchersId){
		return sqlSession.getMapper(VouchersInfoMapper.class).updateVouchersInfoInit(vouchersId); 
	}
	
    /**
     * 修改 VouchersInfo
     * @param sqlSession
     * @param vouchersInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersInfo(SqlSession sqlSession, VouchersInfo vouchersInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersInfoMapper.class).updateVouchersInfo(vouchersInfo); 
	}


    /**
     * 根据id修改 VouchersInfo
     * @param VouchersInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersInfoById(VouchersInfo vouchersInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();

			result = this.updateVouchersInfoById(sqlSession, vouchersInfo); 
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
     * 根据id修改 VouchersInfo
     * @param sqlSession
     * @param vouchersInfo
     * @return Integer    受影响行数
     * @throws Exception
     */
	@Override
	public Integer updateVouchersInfoById(SqlSession sqlSession, VouchersInfo vouchersInfo) throws Exception {
		/**********************操作MySQL数据库**********************/
		return sqlSession.getMapper(VouchersInfoMapper.class).updateVouchersInfoById(vouchersInfo); 
	}


    /**
     * 根据id查询单个 VouchersInfo
     * @param id
     * @return VouchersInfo    单个VouchersInfo
     * @throws Exception
     */
	@Override
	public VouchersInfo findVouchersInfoById(Long id) throws Exception {

		VouchersInfo result = null; 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findVouchersInfoById(id); 
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
	 * 根据活动id查询单个可用 VouchersInfo
	 * 
	 * @param activeId
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	public VouchersInfo findAvailableVouchersInfoByActiveId(String activeId) throws Exception{
		VouchersInfo result = null; 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findAvailableVouchersInfoByActiveId(activeId); 
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据活动id查询单个可用 VouchersInfo 异常", e);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	/**
	 * 根据活动id查询全部可用 VouchersInfo
	 * 
	 * @param activeId
	 * @return VouchersInfo 单个VouchersInfo
	 * @throws Exception
	 */
	@Override
	public List<VouchersInfo> findAvailableVouchersListByActiveId(String activeId) throws Exception{
		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findAvailableVouchersListByActiveId(activeId); 
		} catch (Exception e) { 
			result = null; 
			LogCvt.error("根据活动id查询全部可用 VouchersInfo 异常", e);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result; 
	}
	
    /**
     * 查询一个 VouchersInfo
     * @param vouchersInfo
     * @return VouchersInfo    单个VouchersInfo
     * @throws Exception
     */
	@Override
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo) throws Exception {

		VouchersInfo result = null; 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findOneVouchersInfo(vouchersInfo); 
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
     * 查询统计 VouchersInfo
     * @param vouchersInfo
     * @return Integer    返回记录数VouchersInfo
     * @throws Exception
     */
	@Override
	public Integer countVouchersInfo(VouchersInfo vouchersInfo) throws Exception {

		Integer result = null; 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.countVouchersInfo(vouchersInfo); 
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
     * 查询 VouchersInfo
     * @param vouchersInfo
     * @return List<VouchersInfo>    VouchersInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo) throws Exception {

		return this.findVouchersInfo(vouchersInfo, null); 

	}


    /**
     * 查询 VouchersInfo
     * @param vouchersInfo
     * @param order 排序字段,如：列名1 DESC, 列名2 ASC, ...... , 列名N 排序方式
     * @return List<VouchersInfo>    VouchersInfo集合 
     * @throws Exception
     */
	@Override
	public List<VouchersInfo> findVouchersInfo(VouchersInfo vouchersInfo, String order) throws Exception {

		List<VouchersInfo> result = null; 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findVouchersInfo(vouchersInfo, order); 
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
     * 分页查询 VouchersInfo
     * @param page
     * @param vouchersInfo
     * @return Page<VouchersInfo>    VouchersInfo分页结果 
     * @throws Exception 
     */
	@Override
	public Page<VouchersInfo> findVouchersInfoByPage(Page<VouchersInfo> page, VouchersInfo vouchersInfo) throws Exception {

		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findByPage(page, vouchersInfo); 
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

	   /**
     * 分页查询 VouchersInfo
     * @param page
     * @param vouchersInfo
     * @return Page<VouchersInfo>    VouchersInfo分页结果 
     * @throws Exception 
     */
	@Override
	public Page<VouchersInfo> findTemporaryByPage(Page<VouchersInfo> page, VouchersInfo vouchersInfo) throws Exception {

		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);

			result = vouchersInfoMapper.findTemporaryByPage(page, vouchersInfo); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			LogCvt.error("查询红包临时券码异常" + e.getMessage(),e);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
	
	
	/**
     * 分页查询 VouchersInfo 未过期
     * @param page
     * @param Long memberCode
     * @return List<VouchersInfo>
     * @throws Exception 
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<VouchersInfo> findVouchersInfoUnExpiredByPage(Page page, Long memberCode) throws Exception {
		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findUnExpiredByPage(page, memberCode);
		} catch (Exception e) { 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	/**
     * 分页查询 VouchersInfo 已过期
     * @param page
     * @param Long memberCode
     * @return List<VouchersInfo>
     * @throws Exception 
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<VouchersInfo> findVouchersInfoExpiredByPage(Page page, Long memberCode) throws Exception {
		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findExpiredByPage(page, memberCode);
		} catch (Exception e) { 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	/**
     * 分页查询 VouchersInfo 已使用
     * @param page
     * @param Long memberCode
     * @return List<VouchersInfo>
     * @throws Exception 
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<VouchersInfo> findVouchersInfoUseByPage(Page page, Long memberCode) throws Exception {
		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findUseByPage(page, memberCode);
		} catch (Exception e) { 
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}


	 /**
	  * @Title: findRepeatVoucherList
	  * @Description: TODO
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param vouchersIdList
	  * @return
	  * @see com.froad.handler.VouchersInfoHandler#findRepeatVoucherList(java.util.List)
	  */	
	@Override
	public List<VouchersInfo> findRepeatVoucherList(List<String> vouchersIdList) {
		List<VouchersInfo> result = new ArrayList<VouchersInfo>(); 
		SqlSession sqlSession = null;
		VouchersInfoMapper vouchersInfoMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			vouchersInfoMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = vouchersInfoMapper.findRepeatVoucherList(vouchersIdList);
		} catch (Exception e) { 
			LogCvt.error("新增红包规则时查询券码是否重复异常 " + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}


	@Override
	public List<VouchersUseDetails> findVouchersUseDetailsByPage(
			Page<VouchersUseDetails> page, VouchersInfo vouchersInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<VouchersUseDetails> vouchersUseDetailsList= new ArrayList<VouchersUseDetails>();
		try {
			VouchersInfoMapper vouchersMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			vouchersUseDetailsList= vouchersMapper.findVouchersUseDetailsByPage(page, vouchersInfo);
			page.setResultsContent(vouchersUseDetailsList);
		} catch (Exception e) {
			LogCvt.error("查询红包券码使用明细异常 " + e.getMessage(), e);
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return vouchersUseDetailsList;
	}
	
	@Override
	public List<VouchersUseDetails> findVouchersUseDetails(
			VouchersInfo vouchersInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<VouchersUseDetails> vouchersUseDetailsList= new ArrayList<VouchersUseDetails>();
		try {
			VouchersInfoMapper vouchersMapper = sqlSession.getMapper(VouchersInfoMapper.class);
			vouchersUseDetailsList= vouchersMapper.findVouchersUseDetails(vouchersInfo);
		} catch (Exception e) {
			LogCvt.error("查询红包券码使用明细异常 " + e.getMessage(), e);
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return vouchersUseDetailsList;
	}


	@Override
	public Integer deleteVouchersInfoByActiveId(String activeId) {
		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			VouchersInfoMapper mapper = sqlSession.getMapper(VouchersInfoMapper.class);
			result = mapper.deleteVouchersInfoByActiveId(activeId); 
			if (result > -1) { // 删除成功

				sqlSession.commit(true);

			} else { // 删除失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("删除红包规则对应券码异常");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}	
	
	
	public Integer updateVouchersInfoByVouchersId(VouchersInfo vouchersInfo) throws Exception {

		Integer result = -1; 
		SqlSession sqlSession = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			VouchersInfoMapper mapper = sqlSession.getMapper(VouchersInfoMapper.class);
			String vouchersId = vouchersInfo.getVouchersId();
			Long memberCode = vouchersInfo.getMemberCode();
			result = mapper.updateVouchersInfoByVouchersId(vouchersInfo);
			if (result > 0) { // 成功
				cutVouchersUseNumber(vouchersId, memberCode);
				sqlSession.commit(true);

			} else { // 失败
				sqlSession.rollback(true); 
			}
		} catch (Exception e) { 
			result = -1; 
			e.printStackTrace();
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e; 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	private static void cutVouchersUseNumber(String vouchersId, Long memberCode){
		try{
			int number = VouchersRedis.getVouchersUseNumber(vouchersId);
			if( number != 999999 && number != -1 && number != 0 ){
				// 减去 券码使用次数
				VouchersRedis.putVouchersUseNumber(vouchersId, number-1);
				
				// 加上 代金券使用人
				VouchersRedis.accumulateVouchersUser(vouchersId, memberCode);
				
			}
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
		}
	}
	
}