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
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.MerchantUserMapper;
import com.froad.db.redis.MerchantUserRedis;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
//import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantOperateLogLogic;
import com.froad.logic.MerchantRoleLogic;
import com.froad.logic.MerchantUserLogic;
import com.froad.monitor.MerchantUserMonitor;
import com.froad.po.Merchant;
import com.froad.po.MerchantOperateLog;
import com.froad.po.MerchantRole;
import com.froad.po.MerchantUser;
import com.froad.po.MerchantUserAddRes;
import com.froad.po.MerchantUserCheckRes;
import com.froad.po.MerchantUserLoginRes;
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
	
	private MerchantRoleLogic merchantRoleLogic = new MerchantRoleLogicImpl();
	private MerchantOperateLogLogic merchantOperateLogLogic = new MerchantOperateLogLogicImpl();

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     */
	@Override
	public MerchantUserAddRes addMerchantUser(MerchantUser merchantUser) {
		// TODO Auto-generated method stub

		MerchantUserAddRes merchantUserAddRes = new MerchantUserAddRes(); 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			String addResult = checkAdd(merchantUser);
			if( addResult != null ){
//				Result result = new Result(ResultCode.add_merchant_user_param_error.getCode(), ResultCode.add_merchant_user_param_error.getMsg()+addResult);
				Result result = new Result(ResultCode.add_merchant_user_param_error.getCode(), addResult);
				merchantUserAddRes.setResult(result);
				return merchantUserAddRes;
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
					merchantUserAddRes.setResult(result);
					merchantUserAddRes.setId(merchantUser.getId());
					sqlSession.commit(true);
				}else{
					sqlSession.rollback(true); 
					Result result = new Result(ResultCode.add_merchant_user_redis_lapse.getCode(), ResultCode.add_merchant_user_redis_lapse.getMsg());
					merchantUserAddRes.setResult(result);
				}
			}else{
				sqlSession.rollback(true); 
				Result result = new Result(ResultCode.add_merchant_user_db_lapse.getCode(), ResultCode.add_merchant_user_db_lapse.getMsg());
				merchantUserAddRes.setResult(result);
			}
			

			/**********************操作Mongodb数据库**********************/

		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantUser失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_user_app_lapse.getCode(), e.getMessage());
			merchantUserAddRes.setResult(result);
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			LogCvt.error("添加MerchantUser失败，原因:" + e.getMessage(), e); 
			Result result = new Result(ResultCode.add_merchant_user_app_lapse);
			merchantUserAddRes.setResult(result);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return merchantUserAddRes; 

	}

	private static final String CLIENT_ID_NOT_NULL = "客户端编号clientId不能为空";
	private static final String MERCHANT_ID_NOT_NULL = "商户编号merchantId不能为空";
	private static final String USERNAME_NOT_NULL = "用户名不能为空";
	private static final String USERNAME_NOT_PASS = "会员名由英文字母、数字组成";
	private static final String USERNAME_NOT_ONLY = "该登录名已被使用";
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
     * 删除 MerchantUser
     * @param merchantUser
     * @return Result
     */
	@Override
	public Result deleteMerchantUser(MerchantUser merchantUser) throws FroadServerException,Exception{
		// TODO Auto-generated method stub

		Result result = null; 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try { 
			if( merchantUser.getId() == null || merchantUser.getId() <= 0 ){
				LogCvt.info(ResultCode.del_merchant_user_id_empty.getMsg());
				return new Result(ResultCode.del_merchant_user_id_empty.getCode(), ResultCode.del_merchant_user_id_empty.getMsg());
			}
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			boolean mapperResult = merchantUserMapper.deleteMerchantUser(merchantUser); 
			
			// 修改不成功 函数返回 后续代码不运行
			if( mapperResult == false ){
				sqlSession.rollback(true); 
				result = new Result(ResultCode.del_merchant_user_db_lapse.getCode(), ResultCode.del_merchant_user_db_lapse.getMsg());
			}else{
				merchantUser = merchantUserMapper.findMerchantUserById(merchantUser.getId());
				/**********************操作Redis缓存**********************/
				boolean redisResult = MerchantUserRedis.del_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser);
				if( redisResult ){
					sqlSession.commit(true);
					result = new Result(ResultCode.success.getCode(), ResultCode.success.getMsg());
				}else{
					sqlSession.rollback(true); 
					result = new Result(ResultCode.del_merchant_user_redis_lapse.getCode(), ResultCode.del_merchant_user_redis_lapse.getMsg());
				}
			}
			
			tickOutToken(merchantUser.getId()); // 踢出登录的用户
			/**********************操作Mongodb数据库**********************/

			
			
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
//			LogCvt.error("删除MerchantUser失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.del_merchant_user_app_lapse.getCode(), ResultCode.del_merchant_user_app_lapse.getMsg()+e.getMessage());
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
//			LogCvt.error("删除MerchantUser失败，原因:" + e.getMessage(), e); 
			result = new Result(ResultCode.del_merchant_user_app_lapse);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

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
				LogCvt.info(ResultCode.upd_merchant_user_id_empty.getMsg());
				return new Result(ResultCode.upd_merchant_user_id_empty.getCode(), ResultCode.upd_merchant_user_id_empty.getMsg());
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
//		if( merchantUser.getMerchantId() == null || merchantUser.getMerchantId().length() <= 0 ){
//			LogCvt.info(MERCHANT_ID_NOT_NULL);
//			return MERCHANT_ID_NOT_NULL;
//		}
		return null;
	}
	
	@Override
	public MerchantUser getMerchantUserById(long id) {
		// TODO Auto-generated method stub
		
		MerchantUser result = null;
		
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			result = merchantUserMapper.findMerchantUserById(id);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("查询MerchantUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>    结果集合 
     */
	@Override
	public List<MerchantUser> findMerchantUser(MerchantUser merchantUser) {
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

			result = merchantUserMapper.findMerchantUser(merchantUser); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
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

	/**
	 * 登录login
	 * @param username
	 * @param password
	 * @param operatorIp
	 * @return MerchantUserLoginRes
	 * */
	@Override
	public MerchantUserLoginRes login(String username, String password, OriginVo originVo) {
		boolean isLoginSuccess = false;
		MerchantUserLoginRes merchantUserLoginRes = new MerchantUserLoginRes();
		
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		MerchantMapper merchantMapper = null;
		MerchantUser merchantUser = null;
		
		try{
			
			if( username == null || username.length() <= 0 ){
				throw new FroadServerException("商户用户登录,用户名不能为空");
			}
			if( password == null || password.length() <= 0 ){
				throw new FroadServerException("商户用户登录,密码不能为空");
			}

			if( StringUtils.isBlank(originVo.getClientId()) ){
				throw new FroadServerException("商户用户登录,客户端Id不能为空");
			}
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			
			MerchantOperateLog operateLog = new MerchantOperateLog();
			
			operateLog.setUsername(username);
			operateLog.setOperatorIp(originVo.getOperatorIp());
			operateLog.setClientId(originVo.getClientId());

			
			// 根据 username 查询出用户信息
			merchantUser = merchantUserMapper.findMerchantUserByUsername(username,originVo.getClientId());
			
			if( merchantUser == null ){ // 无 username 的用户信息
				LogCvt.info("没有 username 为  "+username+" 的 MerchantUser 信息");
				// 设置用户名错误的登录结果
				setNotUsernameLoginRes(merchantUserLoginRes);
				operateLog.setDescription("登录失败-无此用户名");
			}else{ // 有 username 的用户信息
				LogCvt.info("存在 username 为  "+username+" 的 MerchantUser 信息");
				boolean isDelete = merchantUser.getIsDelete();
				String dPassword = merchantUser.getPassword();
				if(isDelete) { // 用户已经被删除
					Result result = new Result();
					result.setResultCode(ResultCode.login_is_delete.getCode());
					result.setResultDesc(ResultCode.login_is_delete.getMsg());
					merchantUserLoginRes.setResult(result);
					operateLog.setUserId(merchantUser.getId());
					operateLog.setClientId(merchantUser.getClientId());
					operateLog.setDescription("商户用户已经被删除");
					
					int count = loginFailureCountCumulate_1(merchantUser);
//					merchantUserLoginRes.setLoginFailureCount(count);
				} else if( !dPassword.equals(password)){ // 密码错误
					// 登录失败次数 累加 1
					int count = loginFailureCountCumulate_1(merchantUser);
					// 设置密码错误的登录结果
//					setPasswordErrorLoginRes(merchantUserLoginRes, count);
					Result result = new Result();
					result.setResultCode(ResultCode.login_password_error.getCode());
					result.setResultDesc(ResultCode.login_password_error.getMsg());
					merchantUserLoginRes.setResult(result);
					merchantUserLoginRes.setLoginFailureCount(count);
					
					operateLog.setDescription("登录失败-密码错误");
				} else { // 密码正确
					
					try{
						merchantMapper = sqlSession.getMapper(MerchantMapper.class);
						Merchant merchant = merchantMapper.findMerchantByMerchantId(merchantUser.getMerchantId());
						
						if( merchant == null || !ProductAuditState.passAudit.getCode().equals(merchant.getAuditState()) ){
							
							Result result = new Result();
							result.setResultCode(ResultCode.login_not_pass_audit.getCode());
							result.setResultDesc(ResultCode.login_not_pass_audit.getMsg());
							merchantUserLoginRes.setResult(result);
							operateLog.setUserId(merchantUser.getId());
							operateLog.setClientId(merchantUser.getClientId());
							operateLog.setDescription("商户尚未通过审核!");
						}else{
							
							// 设置登录成功的登录结果
							setLoginSuccessRes(merchantUserLoginRes, merchantUser);
							operateLog.setUserId(merchantUser.getId());
							operateLog.setClientId(merchantUser.getClientId());
							operateLog.setDescription("登录成功");
							
							if( merchant != null ){
								operateLog.setOrgCode(merchant.getOrgCode());
								operateLog.setOrgName(merchant.getMerchantName());
							}
							
							isLoginSuccess = true;
						}
					}catch(Exception e){
						LogCvt.error("MerchantUser登录login 查询商户信息失败，原因:" + e.getMessage(), e); 
					}
				}
			}
			
			merchantOperateLogLogic.addBankOperateLog(operateLog);
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("MerchantUser登录login失败，原因:" + e.getMessage(),  e); 
		} finally { 
			if(!isLoginSuccess) { // 登录错误  上报业务监控
				MerchantUserMonitor.sendMerchantUserLoginServiceFaildNumber(); // 发送业务监控
			}
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return merchantUserLoginRes;
	}
	
	/**
     * 用户登出
     * @param token
     * @return boolean
     * 
     * @param token
     */
	@Override
    public Boolean logout(String token){
		// TODO Auto-generated method stub
		
		// 结果
		Boolean result = null;
		
		try{
			if( token == null || token.length() <= 0 ){
				LogCvt.info("商户用户退出登录,token不能为空");
				return result;
			}
			String userId = MerchantUserRedis.get_cbbank_merchant_token_token_value(token);
			if (StringUtils.isNotBlank(userId)) {
				result = MerchantUserRedis.del_cbbank_merchant_token_token_value(userId);
			}
			result = MerchantUserRedis.del_cbbank_merchant_token_token_value(token);
			if (result)
				LogCvt.info("商户用户[" + userId + "]退出登录成功!!!");
		}catch(Exception e){
			LogCvt.error("用户登出失败，原因:" + e.getMessage(), e); 
			result = false;
		}
    	return result;
    }

	/**
     * 校验 token
     * @param token值
     * @param userId
     * @return MerchantUserCheckRes
     */
	@Override
	public MerchantUserCheckRes tokenCheck(String token, long userId) {
		// TODO Auto-generated method stub

		// 结果
//		MerchantUser result = new MerchantUser(); 
		MerchantUserCheckRes result = new MerchantUserCheckRes();
		
		// Redis 操作
//		RedisManager redisManager = new RedisManager();
		
		try{
		
			if( token == null || token.length() <= 0 ){
				LogCvt.info("MerchantUser校验 token,token不能为空");
				result.setResult(new Result(ResultCode.notAllParameters,"token值"));
				return result;
			}
			if( userId <= 0 ){
				LogCvt.info("MerchantUser校验 token,userId不能为空");
				result.setResult(new Result(ResultCode.notAllParameters,"userId"));
				return result;
			}
			
			// 根据 userId 得到已经存在的 token 值
			String rToken = MerchantUserRedis.get_cbbank_merchant_token_token_value(String.valueOf(userId));
			
			// 已经存在的 token 为空 - 未登录或token过期
			if( rToken == null ){ 
				LogCvt.info("校验 token 不通过 - "+ ResultCode.check_token_logout.getMsg() +": userId:"+userId+" - token:"+token);
				result.setResult(new Result(ResultCode.check_token_logout));
				return result;
			}
			
			// 已经存在的token值 = 传进来校验的token值 - 登录过且token未过期
			if( StringUtils.equals(rToken, token) ){
				// 重新设置 30分钟
				MerchantUserRedis.set_cbbank_merchant_token_token_value(token, String.valueOf(userId));
				MerchantUserRedis.set_cbbank_merchant_token_token_value(String.valueOf(userId), token);
				result.setResult(new Result(ResultCode.success));
				result.setMerchantUser(this.getMerchantUserById(userId));
				return result;
			}else{
				// 已经存在的token值 != 传进来校验的token值 - 传进来的token是被踢的
				result.setResult(new Result(ResultCode.check_token_out));
				return result;
			}
			
			// 根据 token 取出值
//			String key = new StringBuffer("cbbank:merchant_token:").append(token).toString();
//			String value = redisManager.getString(key);
//			String value = MerchantUserRedis.get_cbbank_merchant_token_token_value(token);
			
			// 校验不通过
//			if( value == null || "".equals(value) ){
//				LogCvt.info("校验 token 不通过: userId:"+userId+" - value:"+value);
//			
//			}else{ // 校验通过
//				
//				LogCvt.info("校验 token 通过: userId:"+userId+" - value:"+value);
//				
//				// 重新设置 30分钟
//				redisManager.putExpire(key, ""+userId, SECONDS_BY_30_MINUTE);
//				MerchantUserRedis.set_cbbank_merchant_token_token_value(token, String.valueOf(userId));
//				MerchantUserRedis.set_cbbank_merchant_token_token_value(String.valueOf(userId), token);
//				
//				result = this.getMerchantUserById(userId);
//			}
			
		}catch(Exception e){
			LogCvt.error("校验 token失败，原因:" + e.getMessage(), e); 
//			result = null;
		}

		return result;
	}

	/**
     * 查询 MerchantUser
     * @param username
     * @return MerchantUser
     * 
     * @param username
     */
	@Override
	public MerchantUser getMerchantUserByUsername(String username,com.froad.thrift.vo.OriginVo originVo) {
		// TODO Auto-generated method stub
		MerchantUser result = null;
		
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			result = merchantUserMapper.findMerchantUserByUsername(username,originVo.getClientId());
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询MerchantUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

	// 设置用户名错误的登录结果
	private void setNotUsernameLoginRes(MerchantUserLoginRes merchantUserLoginRes){
	
		Result result = new Result();
		result.setResultCode(ResultCode.login_not_exist.getCode()); 
		result.setResultDesc(ResultCode.login_not_exist.getMsg());
		merchantUserLoginRes.setResult(result);
	}
	
	// 登录失败次数 累加 1
	private int loginFailureCountCumulate_1(MerchantUser merchantUser){
		
//		RedisManager redisManager = new RedisManager();
		
		String clientId = merchantUser.getClientId(); 
		String merchantId = merchantUser.getMerchantId();
		long userId = merchantUser.getId();
		
		int count = 0;
		
		// 查询缓存中的登录失败次数(如果没信息则为0) 
//		String key = new StringBuffer("cbbank:merchant_outlet_user_login:")
//		                      .append(clientId)
//		                      .append(":")
//		                      .append(merchantId)
//		                      .append(":")
//		                      .append(userId).toString();
//		String field = redisManager.getString(key);
		String field = MerchantUserRedis.get_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(clientId, merchantId, userId);
		if( field == null || "".equals(field) ){
			count = 0;
		}else{
			count = Integer.parseInt(field);
		}
		
		// +1
		count++;
		
		// 设置
//		redisManager.putString(key, ""+count);
		MerchantUserRedis.set_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(clientId, merchantId, userId, count);
		
		return count;
	}
	
	// 设置密码错误的登录结果
//	private void setPasswordErrorLoginRes(MerchantUserLoginRes merchantUserLoginRes, int count){
//		
//		Result result = new Result();
//		result.setResultCode(ResultCode.login_password_error.getCode());
//		result.setResultDesc(ResultCode.login_password_error.getMsg());
//		merchantUserLoginRes.setResult(result);
//		merchantUserLoginRes.setLoginFailureCount(count);
//	}
	
	// 设置登录成功的登录结果
	private void setLoginSuccessRes(MerchantUserLoginRes merchantUserLoginRes, MerchantUser merchantUser){
		
		Result result = new Result();
		result.setResultCode(ResultCode.success.getCode());
		result.setResultDesc(ResultCode.success.getMsg());
		merchantUserLoginRes.setResult(result);
		merchantUserLoginRes.setMerchantUser(merchantUser);
		
		
		Long roleId = merchantUser.getMerchantRoleId();
		MerchantRole merchantRole = merchantRoleLogic.findMerchantRoleById(roleId);
		if( merchantRole != null ){
			String name = merchantRole.getName();
			if( name != null && name.indexOf("管理员") != -1 ){
				merchantUserLoginRes.setIsAdmin(true);
			}else{
				merchantUserLoginRes.setIsAdmin(false);
			}
		}else{
			merchantUserLoginRes.setIsAdmin(false);
		}
		
		// 踢出已经登录的用户
//		String old_token = MerchantUserRedis.get_cbbank_merchant_token_token_value(String.valueOf(merchantUser.getId()));
//		if (StringUtils.isNotBlank(old_token)) {
//			LogCvt.info("商户用户[" + merchantUser.getUsername() + "]已经登录, 将之前的token踢出");
//			MerchantUserRedis.del_cbbank_merchant_token_token_value(String.valueOf(merchantUser.getId()));
//			MerchantUserRedis.del_cbbank_merchant_token_token_value(old_token);
//		}
		tickOutToken(merchantUser.getId()); // 先踢出原来已经登录的token
		
		// 设置缓存的 token 值
//		setRedisOfToken(token, merchantUser.getId());
//		MerchantUserRedis.set_cbbank_merchant_token_token_value(token, String.valueOf(merchantUser.getId()));
		
		// 设置缓存的 userid 值
//		MerchantUserRedis.set_cbbank_merchant_token_token_value(String.valueOf(merchantUser.getId()), token);
		
		String token = null;
//		token = SimpleID.toIDString((new SimpleID(MERCHANT_USER_TOKEN).nextId()));
//		String token = UUID.randomUUID().toString();
//		token = token.replaceAll("-", "");
		token = setNewToken(merchantUser.getId());
		
		merchantUserLoginRes.setToken(token); // 设置token值
		
		// 登录失败次数 清零
//		loginFailureCountEmpty(merchantUser);
		MerchantUserRedis.set_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(merchantUser.getClientId(), merchantUser.getMerchantId(), merchantUser.getId(), 0);
	}
	
	// 设置缓存的 token 值
//	private void setRedisOfToken(String token, long userId){
//		
//		RedisManager redisManager = new RedisManager();
//		
//		String key = new StringBuffer("cbbank:merchant_token:").append(token).toString();
//		
//		// 设置
//		redisManager.putExpire(key, ""+userId, SECONDS_BY_30_MINUTE);
//		
//	}
	
	// 登录失败次数 清零
//	private void loginFailureCountEmpty(MerchantUser merchantUser){
//		
//		RedisManager redisManager = new RedisManager();
//		
//		String key = new StringBuffer("cbbank:merchant_outlet_user_login:")
//        .append(merchantUser.getClientId())
//        .append(":")
//        .append(merchantUser.getMerchantId())
//        .append(":")
//        .append(merchantUser.getId()).toString();
//		
//		// 设置
//		redisManager.putString(key, "0");
//		
//	}
	
	/**
	 * 踢出token
	 * @Title: tickOutToken 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param userId
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	private boolean tickOutToken(Long userId){
	// 踢出已经登录的用户
		String old_token = MerchantUserRedis.get_cbbank_merchant_token_token_value(String.valueOf(userId));
		if (StringUtils.isNotBlank(old_token)) {
			LogCvt.info("商户用户ID[" + userId + "]已经登录, 将之前的token踢出");
			MerchantUserRedis.del_cbbank_merchant_token_token_value(String.valueOf(userId));
			MerchantUserRedis.del_cbbank_merchant_token_token_value(old_token);
		}
		return true;
	}
	
	/**
	 * 设置token
	 * @Title: setToken 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param userId
	 * @return
	 * @return String    返回类型 
	 * @throws
	 */
	private String setNewToken(Long userId) {
//		String token = SimpleID.toIDString((new SimpleID(MERCHANT_USER_TOKEN).nextId()));
		String token = UUID.randomUUID().toString();
		token = token.replaceAll("-", "");
		
		// 设置缓存的 token 值
//		setRedisOfToken(token, merchantUser.getId());
		MerchantUserRedis.set_cbbank_merchant_token_token_value(token, String.valueOf(userId));
		
		// 设置缓存的 userid 值
		MerchantUserRedis.set_cbbank_merchant_token_token_value(String.valueOf(userId), token);
		return token;
	}
	
	/**
     * 查询 MerchantUserList
     * @return List<MerchantUserVo>
     * 
     * @param roleId
     * @param merchantIdList
     */
	@Override
	public List<MerchantUser> getMerchantUserByRoleIdAndMerchantIds(
			long roleId, List<String> merchantIdList) {
		// TODO Auto-generated method stub
		
		List<MerchantUser> result = new ArrayList<MerchantUser>(); 
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		
		try{
			
			if( roleId <= 0 || merchantIdList == null || merchantIdList.size() <= 0 ){
				LogCvt.info("查询MerchantUserList,方法getMerchantUserByRoleIdAndMerchantIds,参数不能为空 roleId:"+roleId+" - merchantIdList:"+merchantIdList);
				return result; 
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);

			result = merchantUserMapper.getMerchantUserByRoleIdAndMerchantIds(roleId, merchantIdList); 
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询MerchantUserList失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	@Override
	public int findLoginErrorCount(String clientId, String merchantId,
			long userId) throws Exception{
		String count = MerchantUserRedis.get_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(clientId, merchantId,userId);
		return Integer.parseInt(count == null ? "0" : count);
	}

	/**
	 * 通过用户名和客户端id获取用户失败次数信息.
	 */
	@Override
	public MerchantUserLoginRes getLoginFailureCount(OriginVo originVo,	String username) {
		MerchantUserLoginRes merchantUserLoginRes = new MerchantUserLoginRes();
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		MerchantUser merchantUser = null;
		Result result = new Result();
		try{
			if( username == null || username.length() <= 0 ){
				throw new FroadServerException("商户用户登录,用户名不能为空");
			}
			if( StringUtils.isBlank(originVo.getClientId()) ){
				throw new FroadServerException("商户用户登录,客户端Id不能为空");
			}
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			// 根据 username 查询出用户信息
			merchantUser = merchantUserMapper.findMerchantUserByUsername(username,originVo.getClientId());
			int count = 0;
			if(null != merchantUser){
				String clientId = merchantUser.getClientId(); 
				String merchantId = merchantUser.getMerchantId();
				long userId = merchantUser.getId();
				
				String field = MerchantUserRedis.get_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(clientId, merchantId, userId);
				if( field == null || "".equals(field) ){
					count = 0;
				}else{
					count = Integer.parseInt(field);
				}
				result.setResultCode(ResultCode.success.getCode());
				result.setResultDesc(ResultCode.success.getMsg());
			}else{
				result.setResultCode(ResultCode.login_not_exist.getCode());
				result.setResultDesc(ResultCode.login_not_exist.getMsg());
			}
			merchantUserLoginRes.setResult(result);
			merchantUserLoginRes.setMerchantUser(merchantUser);
			merchantUserLoginRes.setLoginFailureCount(count);
		} catch (Exception e) { 
			LogCvt.error("MerchantUser登录获取用户登录失败次数失败，原因:" + e.getMessage(),  e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return merchantUserLoginRes;
	}
}
