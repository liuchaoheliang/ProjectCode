/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossUsers   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossUserLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.BossUserMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossUserLogic;
import com.froad.po.BossOperateLog;
import com.froad.po.BossUser;
import com.froad.po.BossUserCheckRes;
import com.froad.po.BossUserLoginRes;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: BossUserLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossUserLogicImpl implements BossUserLogic {


	 /**
     * 增加 BossUser
     * @param bossUser
     * @return Long    主键ID
     */
	@Override
	public ResultBean addBossUser(BossUser bossUser) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"添加机构用户信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			if( bossUserMapper.findBossUserByUsername(bossUser.getUsername()) != null ){
				resultBean= new ResultBean(ResultCode.failed,"添加机构用户信息失败 - 此用户名已经存在");
			}else{
				if (bossUserMapper.addBossUser(bossUser) > -1) { 
					result = bossUser.getId(); 
				}
				sqlSession.commit(true); 
				 resultBean= new ResultBean(ResultCode.success,"添加机构用户信息成功",result);
			}
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("添加BossUser失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"添加机构用户信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteBossUser(BossUser bossUser) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除机构用户信息成功");
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			bossUserMapper.deleteBossUser(bossUser); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("删除BossUser失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"删除机构用户信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 BossUser
     * @param bossUser
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateBossUser(BossUser bossUser) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改活动信息成功");
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			bossUserMapper.updateBossUser(bossUser); 
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			LogCvt.error("修改BossUser失败，原因:" + e.getMessage(), e); 
			resultBean= new ResultBean(ResultCode.failed,"修改机构用户信息失败");
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 BossUser
     * @param bossUser
     * @return List<BossUser>    结果集合 
     */
	@Override
	public List<BossUser> findBossUser(BossUser bossUser) {

		List<BossUser> result = new ArrayList<BossUser>(); 
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			result = bossUserMapper.findBossUser(bossUser); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询BossUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossUser
     * @param page
     * @param bossUser
     * @return Page<BossUser>    结果集合 
     */
	@Override
	public Page<BossUser> findBossUserByPage(Page<BossUser> page, BossUser bossUser) {

		List<BossUser> result = new ArrayList<BossUser>(); 
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			result = bossUserMapper.findByPage(page, bossUser); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 是否存在username
     * @return bool
     * 
     * @param username
     */
	@Override
	public boolean isExistUsername(String username) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		
		if( username == null || "".equals(username) ){
			result = true;
		}
		
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);
		
			BossUser bossUser = bossUserMapper.findBossUserByUsername(username);
			if( bossUser != null ){
				result = true; 
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("判断是否存在username失败，原因:" + e.getMessage(), e); 
			result = true;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	/**
	 * 登录login
	 * @param username
	 * @param password
	 * @param operatorIp
	 * @return BossUserLoginRes
	 * */
	@Override
	public BossUserLoginRes login(String username, String password,
			String operatorIp) {
		// TODO Auto-generated method stub
		
		BossUserLoginRes bossUserLoginRes = new BossUserLoginRes();
		
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		BossUser bossUser = null;
		
		try{
			StringUtils.isBlank(username);
			if( StringUtils.isBlank(username) ){
				LogCvt.info("商户用户登录,用户名username不能为空");
				bossUserLoginRes.setResult(new Result(ResultCode.login_username_not));
				return bossUserLoginRes;
			}
			if( StringUtils.isBlank(password) ){
				LogCvt.info("商户用户登录,密码password不能为空");
				bossUserLoginRes.setResult(new Result(ResultCode.login_password_not));
				return bossUserLoginRes;
			}
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);
			
			BossOperateLog operateLog = new BossOperateLog();
			operateLog.setUsername(username);
			operateLog.setOperatorIp(operatorIp);
			
			// 根据 username 查询出用户信息
			bossUser = bossUserMapper.findBossUserByUsername(username);
			
			if( bossUser == null ){ // 无 username 的用户信息
				LogCvt.info("没有 username 为  "+username+" 的 BossUser 信息");
				// 设置用户名错误的登录结果
				bossUserLoginRes.setResult(new Result(ResultCode.login_not_exist));
				operateLog.setDescription("登录失败-无此用户名");
			}else{
				LogCvt.info("存在 username 为  "+username+" 的 BossUser 信息");
				Boolean isEnable = bossUser.getIsEnable();
				String dPassword = bossUser.getPassword();
				if( !isEnable ){ // 用户未启用
					bossUserLoginRes.setResult(new Result(ResultCode.login_is_delete));
					int count = loginFailureCountCumulate_1(bossUser);
					bossUserLoginRes.setLoginFailureCount(count);
					operateLog.setUserId(bossUser.getId());
					operateLog.setClientId("Boss用户");
					operateLog.setDescription("Boss用户未被启用");
				} else if( !dPassword.equals(password)){ // 密码错误
					bossUserLoginRes.setResult(new Result(ResultCode.login_password_error));
					int count = loginFailureCountCumulate_1(bossUser);
					bossUserLoginRes.setLoginFailureCount(count);
					operateLog.setDescription("登录失败-密码错误");
				} else { // 密码正确
					// 设置登录成功的登录结果
					setLoginSuccessRes(bossUserLoginRes, bossUser);
					operateLog.setUserId(bossUser.getId());
					operateLog.setClientId("Boss用户");
					operateLog.setDescription("登录成功");
				}
			}
			
		} catch (Exception e) { 
			LogCvt.error("BossUser登录login失败，原因:" + e.getMessage(),  e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		}
		
		return bossUserLoginRes;
	}

	// 登录失败次数 累加 1
	private int loginFailureCountCumulate_1(BossUser bossUser){
		
		long userId = bossUser.getId();
		int count = 0;
		
		// 查询缓存中的登录失败次数(如果没信息则为0) 
		String field = SupportsRedis.get_cbbank_boss_user_login_user_id(userId);
		if( field == null || "".equals(field) ){
			count = 0;
		}else{
			count = Integer.parseInt(field);
		}
		
		// +1
		count++;
		
		boolean rResult = SupportsRedis.set_cbbank_boss_user_login_user_id(userId, count);
		LogCvt.info("设置BoosUser:"+userId+" 登录时缓存的登录失败次数:"+(rResult?"成功":"失败"));
		
		return count;
	}
	
	// 设置登录成功的登录结果
	private void setLoginSuccessRes(BossUserLoginRes bossUserLoginRes, BossUser bossUser){
		
		bossUserLoginRes.setResult(new Result(ResultCode.success));
		
		long userId = bossUser.getId();
		
		// 踢出已经登录的用户token
		boolean rR_1 = tickOutToken(userId);
		LogCvt.info("BoosUser:"+userId+" 登录成功,清除缓存中的用户token:"+(rR_1?"成功":"失败"));
		
		// 获取设置的新的token
		String token = setNewToken(userId);
		
		bossUserLoginRes.setToken(token);
		
		// 登录失败次数 清零
		boolean rR_2 = SupportsRedis.set_cbbank_boss_user_login_user_id(userId, 0);
		LogCvt.info("BoosUser:"+userId+" 登录成功,清除缓存中的登录失败次数:"+(rR_2?"成功":"失败"));
		
		bossUserLoginRes.setBossUser(bossUser);
	}
	
	// 踢出已经登录的用户token
	private boolean tickOutToken(Long userId){
		String oldToken = SupportsRedis.get_cbbank_boss_user_token_token_value(String.valueOf(userId));
		if (StringUtils.isNotBlank(oldToken)) {
			LogCvt.info("BossUser:" + userId + " 已经登录, 将之前的token踢出");
			SupportsRedis.del_cbbank_boss_user_token_token_value(String.valueOf(userId));
			SupportsRedis.del_cbbank_boss_user_token_token_value(oldToken);
		}
		return true;
	}
	
	// 获取设置的新的token
	private String setNewToken(Long userId) {
		
		String token = UUID.randomUUID().toString();
		token = token.replaceAll("-", "");
		
		SupportsRedis.set_cbbank_boss_user_token_token_value(String.valueOf(userId), token);
		SupportsRedis.set_cbbank_boss_user_token_token_value(token, String.valueOf(userId));
		
		return token;
	}
	
	/**
     * 用户登出
     * @param token
     * @return boolean
     * 
     * @param token
     */
	@Override
	public Boolean logout(String token) {
		// TODO Auto-generated method stub
		// 结果
		Boolean result = null;
		
		try{
			if( token == null || token.length() <= 0 ){
				LogCvt.info("Boss用户退出登录,token不能为空");
				return result;
			}
			
			String userId = SupportsRedis.get_cbbank_boss_user_token_token_value(token);
			if (StringUtils.isNotBlank(userId)) {
				result = SupportsRedis.del_cbbank_boss_user_token_token_value(userId);
			}
			
			result = SupportsRedis.del_cbbank_boss_user_token_token_value(token);
			if (result)
				LogCvt.info("Boss用户:" + userId + " 退出登录成功!!!");
			
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
     * @return bossUserCheckRes
     */
	@Override
	public BossUserCheckRes tokenCheck(String token, long userId) {
		// TODO Auto-generated method stub
		
		// 结果
		BossUserCheckRes result = new BossUserCheckRes();
//		BossUser result = new BossUser(); 
		
		try{
			
			if( token == null || token.length() <= 0 ){
				LogCvt.info("BossUser校验 token,token不能为空");
				result.setResult(new Result(ResultCode.notAllParameters,"token值"));
				return result;
			}
			if( userId <= 0 ){
				LogCvt.info("BossUser校验 token,userId不能为空");
				result.setResult(new Result(ResultCode.notAllParameters,"userId"));
				return result;
			}
			
			// 根据 userId 得到已经存在的 token 值
			String rToken = SupportsRedis.get_cbbank_boss_user_token_token_value(String.valueOf(userId));
			
			// 已经存在的 token 为空 - 未登录或token过期
			if( rToken == null ){ 
				LogCvt.info("校验 token 不通过 - "+ ResultCode.check_token_logout.getMsg() +": userId:"+userId+" - token:"+token);
				result.setResult(new Result(ResultCode.check_token_logout));
				return result;
			}
			
			// 已经存在的token值 = 传进来校验的token值 - 登录过且token未过期
			if( StringUtils.equals(rToken, token) ){
				// 重新设置 30分钟
				SupportsRedis.set_cbbank_boss_user_token_token_value(String.valueOf(userId), token);
				SupportsRedis.set_cbbank_boss_user_token_token_value(token, String.valueOf(userId));
				result.setResult(new Result(ResultCode.success));
				result.setBossUser(this.findBossUserByid(userId));
				return result;
			}else{
				// 已经存在的token值 != 传进来校验的token值 - 传进来的token是被踢的
				result.setResult(new Result(ResultCode.check_token_out));
				return result;
			}
						
		}catch(Exception e){
			LogCvt.error("校验 token失败，原因:" + e.getMessage(), e); 
		}
		
		return result;
	}

	public BossUser findBossUserByid(long userId){
		
		BossUser result = new BossUser();
		SqlSession sqlSession = null;
		BossUserMapper bossUserMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossUserMapper = sqlSession.getMapper(BossUserMapper.class);

			result = bossUserMapper.findBossUserById(userId); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询BossUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}
}