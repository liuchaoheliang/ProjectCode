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
 * @Title: MerchantRoleLogicImpl.java
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
import com.froad.db.mysql.mapper.MerchantRoleMapper;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantRoleLogic;
import com.froad.po.MerchantRole;
import com.froad.po.MerchantRoleAddRes;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantRoleLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantRoleLogicImpl implements MerchantRoleLogic {


    /**
     * 增加 MerchantRole
     * @param merchantRole
     * @return MerchantRoleAddRes
     */
	@Override
	public MerchantRoleAddRes addMerchantRole(MerchantRole merchantRole) {

		MerchantRoleAddRes merchantRoleAddRes = new MerchantRoleAddRes(); 
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);

			if (merchantRoleMapper.addMerchantRole(merchantRole) > -1) { 
				sqlSession.commit(true); 
				Result result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
				merchantRoleAddRes.setResult(result);
				merchantRoleAddRes.setId(merchantRole.getId());
			}else{
				sqlSession.rollback(true);
				Result result = new Result(ResultCode.add_merchant_role_db_lapse.getCode(), ResultCode.add_merchant_role_db_lapse.getMsg());
				merchantRoleAddRes.setResult(result);
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantRole失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_role_app_lapse.getCode(), e.getMessage());
			merchantRoleAddRes.setResult(result);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantRole失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_role_app_lapse);
			merchantRoleAddRes.setResult(result);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return merchantRoleAddRes; 

	}


    /**
     * 删除 MerchantRole
     * @param merchantRole
     * @return Result
     */
	@Override
	public Result deleteMerchantRole(MerchantRole merchantRole) throws FroadServerException,Exception{

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);

			if( merchantRoleMapper.deleteMerchantRole(merchantRole) ){
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
			} else {
				sqlSession.rollback(true);
				result = new Result(ResultCode.del_merchant_role_db_lapse.getCode(), ResultCode.del_merchant_role_db_lapse.getMsg()); 
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.del_merchant_role_app_lapse.getCode(), e.getMessage());
//			LogCvt.error("删除MerchantRole失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.del_merchant_role_app_lapse);
//			LogCvt.error("删除MerchantRole失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 MerchantRole
     * @param merchantRole
     * @return Result
     */
	@Override
	public Result updateMerchantRole(MerchantRole merchantRole) throws FroadServerException,Exception{

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);

			if( merchantRoleMapper.updateMerchantRole(merchantRole) ){
				sqlSession.commit(true);
				result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg()); 
			} else {
				sqlSession.rollback(true);
				result = new Result(ResultCode.upd_merchant_role_db_lapse.getCode(), ResultCode.upd_merchant_role_db_lapse.getMsg()); 
			}

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.upd_merchant_role_app_lapse.getCode(), e.getMessage());  
//			LogCvt.error("修改MerchantRole失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);
			result = new Result(ResultCode.upd_merchant_role_app_lapse.getCode(), ResultCode.upd_merchant_role_db_lapse.getMsg());  
//			LogCvt.error("修改MerchantRole失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
     * 查询 MerchantRole
     * @param roleId
     * @return MerchantRole
     */
	@Override
	public MerchantRole findMerchantRoleById(Long roleId){
		
		MerchantRole result = null;
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);
			
			result = merchantRoleMapper.findMerchantRoleById(roleId);
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			result = null;
			LogCvt.error("查询MerchantRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
    /**
     * 查询 MerchantRole
     * @param merchantRole
     * @return List<MerchantRole>    结果集合 
     */
	@Override
	public List<MerchantRole> findMerchantRole(MerchantRole merchantRole) {

		List<MerchantRole> result = new ArrayList<MerchantRole>(); 
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);

			result = merchantRoleMapper.findMerchantRole(merchantRole); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询MerchantRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 MerchantRole
     * @param page
     * @param merchantRole
     * @return Page<MerchantRole>    结果集合 
     */
	@Override
	public Page<MerchantRole> findMerchantRoleByPage(Page<MerchantRole> page, MerchantRole merchantRole) {

		List<MerchantRole> result = new ArrayList<MerchantRole>(); 
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);

			result = merchantRoleMapper.findByPage(page, merchantRole); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("分页查询MerchantRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	@Override
	public MerchantRole findMerchantRoleByClientIdAndDesc(String clientId,
			String roleDesc) {
		
		MerchantRole result = null;
		SqlSession sqlSession = null;
		MerchantRoleMapper merchantRoleMapper = null;
		try { 
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantRoleMapper = sqlSession.getMapper(MerchantRoleMapper.class);
			
			result = merchantRoleMapper.findMerchantRoleByClientAndDescription(clientId,roleDesc);
			
		} catch (Exception e) { 
			result = null;
			LogCvt.error("查询MerchantRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


}