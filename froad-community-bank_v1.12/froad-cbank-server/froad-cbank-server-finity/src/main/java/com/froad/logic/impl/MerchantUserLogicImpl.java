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
 * @Title: MerchantUserLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantUserMapper;
import com.froad.db.reids.MerchantUserRedis;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
//import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantUserLogic;
import com.froad.po.Merchant;
import com.froad.po.MerchantUser;
import com.froad.po.CommonAddRes;
import com.froad.po.Result;
import com.froad.thrift.vo.OriginVo;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: MerchantUserLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class MerchantUserLogicImpl implements MerchantUserLogic {

//	private static final int SECONDS_BY_30_MINUTE = 1800;
	

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     */
	@Override
	public CommonAddRes addMerchantUser(MerchantUser merchantUser) {
		// TODO Auto-generated method stub

		CommonAddRes commonAddRes = new CommonAddRes(); 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			String addResult = checkAdd(merchantUser);
			if( addResult != null ){
//				Result result = new Result(ResultCode.add_merchant_user_param_error.getCode(), ResultCode.add_merchant_user_param_error.getMsg()+addResult);
				Result result = new Result(ResultCode.add_merchant_user_param_error.getCode(), addResult);
				commonAddRes.setResult(result);
				return commonAddRes;
			}
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			if( merchantUser.getCreateTime() == null ){
				merchantUser.setCreateTime(new Date());
			}
			if (merchantUserMapper.addMerchantUser(merchantUser) > -1) { 
				
				/**********************操作Redis缓存**********************/
				boolean redisResult = MerchantUserRedis.set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser);
				if( redisResult ){
					Result result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
					commonAddRes.setResult(result);
					commonAddRes.setId(merchantUser.getId());
					sqlSession.commit(true);
				}else{
					sqlSession.rollback(true); 
					Result result = new Result(ResultCode.add_merchant_user_redis_lapse.getCode(), ResultCode.add_merchant_user_redis_lapse.getMsg());
					commonAddRes.setResult(result);
				}
			}else{
				sqlSession.rollback(true); 
				Result result = new Result(ResultCode.add_merchant_user_db_lapse.getCode(), ResultCode.add_merchant_user_db_lapse.getMsg());
				commonAddRes.setResult(result);
			}
			

			/**********************操作Mongodb数据库**********************/

		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantUser失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_user_app_lapse.getCode(), e.getMessage());
			commonAddRes.setResult(result);
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantUser失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_user_app_lapse);
			commonAddRes.setResult(result);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return commonAddRes; 

	}

	private static final String CLIENT_ID_NOT_NULL = "客户端编号clientId不能为空";
	private static final String MERCHANT_ID_NOT_NULL = "商户编号merchantId不能为空";
	private static final String USERNAME_NOT_NULL = "用户名不能为空";
	private static final String USERNAME_NOT_PASS = "会员名由英文字母、数字组成";
	private static final String USERNAME_NOT_ONLY = "该会员名已被使用";
	private static final String PHONE_NOT_ONLY = "手机号phone不是唯一值";
	// 检查 - 新增前
	private String checkAdd(MerchantUser merchantUser){
		if( merchantUser.getClientId() == null || merchantUser.getClientId().length() <= 0 ){
			LogCvt.info(CLIENT_ID_NOT_NULL);
			return CLIENT_ID_NOT_NULL;
		}
		if( merchantUser.getMerchantId() == null || merchantUser.getMerchantId().length() <= 0 ){
			LogCvt.info(MERCHANT_ID_NOT_NULL);
			return MERCHANT_ID_NOT_NULL;
		}
		String username = merchantUser.getUsername();
		if( username == null || username.length() <= 0 ){
			LogCvt.info(USERNAME_NOT_NULL);
			return USERNAME_NOT_NULL;
		}
		if( !Checker.isUsername(username) ){
			LogCvt.info(USERNAME_NOT_PASS);
			return USERNAME_NOT_PASS;
		}
		SqlSession sqlSession = null;
		try{
			MerchantUserMapper merchantUserMapper = null;
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			if( null != merchantUserMapper.findMerchantUserByUsername(username,merchantUser.getClientId()) ){
				LogCvt.info(USERNAME_NOT_ONLY);
				return USERNAME_NOT_ONLY;
			}
//			String phone = merchantUser.getPhone();
//			if( phone != null && phone.length() > 0 ){
//				if( null != merchantUserMapper.findMerchantUserByPhone(phone) ){
//					LogCvt.info(PHONE_NOT_ONLY);
//					return PHONE_NOT_ONLY;
//				}
//			}
			
		}catch(Exception e){
			LogCvt.error("MerchantUser新增 检查商户用户名或手机号码是否唯一失败，原因:" + e.getMessage(), e); 
			return "检查商户用户名或手机号码是否唯一失败";
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return null;
	}



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	@Override
	public Result updateMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception{
		// TODO Auto-generated method stub

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			String updateResult = checkUpdate(merchantUser);
			if( updateResult != null ){
				LogCvt.info(ResultCode.add_merchant_user_param_error.getMsg()+":"+updateResult);
				return new Result(ResultCode.add_merchant_user_param_error.getCode(), updateResult);
			}
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			if( merchantUserMapper.updateMerchantUser(merchantUser) ){
				/**********************操作Redis缓存**********************/
				merchantUser = merchantUserMapper.findMerchantUserById(merchantUser.getId());
				boolean redisResult = MerchantUserRedis.set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser);
				if( redisResult ){
					sqlSession.commit(true);
					result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
				}else{
					sqlSession.rollback(true); 
					result = new Result(ResultCode.upd_merchant_user_redis_lapse.getCode(), ResultCode.upd_merchant_user_redis_lapse.getMsg());
				}
			}else{
				sqlSession.rollback(true); 
				result = new Result(ResultCode.upd_merchant_user_db_lapse.getCode(), ResultCode.upd_merchant_user_db_lapse.getMsg());
			} 

			/**********************操作Mongodb数据库**********************/

			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = new Result(ResultCode.upd_merchant_user_app_lapse.getCode(), ResultCode.upd_merchant_user_app_lapse.getMsg()+e.getMessage());
//			LogCvt.error("修改MerchantUser失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = new Result(ResultCode.upd_merchant_user_app_lapse.getCode(), ResultCode.upd_merchant_user_app_lapse.getMsg());
//			LogCvt.error("修改MerchantUser失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	private static final String ID_NOT_NULL = "添加或更新MerchantUser失败,主键id不能为空";
	// 检查 - 更新前
	private String checkUpdate(MerchantUser merchantUser){
//		if( merchantUser.getClientId() == null || merchantUser.getClientId().length() <= 0 ){
//			LogCvt.info(CLIENT_ID_NOT_NULL);
//			return CLIENT_ID_NOT_NULL;
//		}
		if( merchantUser.getId() == null || merchantUser.getId() <= 0 ){
			LogCvt.info(ID_NOT_NULL);
			return ID_NOT_NULL;
		}
		String username = merchantUser.getUsername();
		if( username == null || username.length() <= 0 ){
			LogCvt.info(USERNAME_NOT_NULL);
			return USERNAME_NOT_NULL;
		}
		if( !Checker.isUsername(username) ){
			LogCvt.info(USERNAME_NOT_PASS);
			return USERNAME_NOT_PASS;
		}
		SqlSession sqlSession = null;
		try{
			MerchantUserMapper merchantUserMapper = null;
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			MerchantUser user=merchantUserMapper.findMerchantUserByUsername(username,merchantUser.getClientId());
			if( null !=user && user.getId()!=null &&  user.getId().longValue()!=merchantUser.getId().longValue() ){
				LogCvt.info(USERNAME_NOT_ONLY);
				return USERNAME_NOT_ONLY;
			}  
		}catch(Exception e){
			LogCvt.error("MerchantUser新增 检查商户用户名或手机号码是否唯一失败，原因:" + e.getMessage(), e); 
			return "检查商户用户名或手机号码是否唯一失败";
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		}  
		return null;
	}



	@Override
	public List<MerchantUser> findMerchantUser(Page<MerchantUser> page,MerchantUser merchantUser) throws FroadServerException, Exception {
		List<MerchantUser> result = new ArrayList<MerchantUser>(); 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			result = merchantUserMapper.findMerchantUser(page,merchantUser); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			LogCvt.error("查询MerchantUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	
	  /**
     * 分页查询 MerchantUser
     * @param page
     * @param merchantUser
     * @return Page<MerchantUser>    结果集合 
     */
	@Override
	public Page<MerchantUser> findMerchantUserByPage(Page<MerchantUser> page, MerchantUser merchantUser) {
		// TODO Auto-generated method stub

		List<MerchantUser> result = new ArrayList<MerchantUser>(); 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			result = merchantUserMapper.findByPage(page, merchantUser); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("分页查询MerchantUser失败，原因:" + e.getMessage(),  e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
	

}
