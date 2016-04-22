package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BankOperatorMapper;
import com.froad.db.redis.BankLoginTokenRedis;
import com.froad.db.redis.BankOperatorRedis;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OperatorType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankOperatorLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.res.BankOperatorCheckRes;
import com.froad.logic.res.LoginBankOperatorRes;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.BankOperateLog;
import com.froad.po.BankOperator;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.thrift.vo.PlatType;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: BankOperatorLogic.java</p>
 * <p>Description: 描述 </p> 银行操作员管理Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankOperatorLogicImpl implements BankOperatorLogic {

    /**
     * 增加 BankOperator银行用户
     * @param bankOperator
     * @return Long    主键ID
     */
	@Override
	public Long addBankOperator(BankOperator bankOperator) throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		long bankOperatorId=0l;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			
			//判断username是否已存在，登录名不可重复
			BankOperator filter=new BankOperator();
			filter.setUsername(bankOperator.getUsername());
			filter.setIsDelete(false);
			filter.setClientId(bankOperator.getClientId());
			filter.setType(OperatorType.PLATFORM.getCode());//平台用户
			filter=bankOperatorMapper.findBankOperatorByUsernameId(filter);//查询mysql中的符合username的记录
			if(Checker.isNotEmpty(filter)){
				throw new FroadServerException(filter.getUsername()+"用户名已存在！");
			}
			
			
			
			
			//设置创建时间
			bankOperator.setCreateTime(new Date());//获得系统时间
			//设置是否删除
			bankOperator.setIsDelete(false);
			//设置是否重置密码
			bankOperator.setIsReset(false);
			//设置是否可用-由界面选择传入
			//bankOperator.setStatus(true);
			//用户类型：1、平台用户 2、银行用户
			bankOperator.setType(OperatorType.PLATFORM.getCode());
			bankOperatorMapper.addBankOperator(bankOperator); 
			
			
			/**********************操作Redis缓存**********************/
			BankOperatorRedis.set_cbbank_bank_user_client_id_user_id(bankOperator);
			
			
			bankOperatorId=bankOperator.getId();
			sqlSession.commit(true); 
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return bankOperatorId; 

	}


    /**
     * 删除 BankOperator<传id和client_id作为条件>
     * @描述：删除银行用户，逻辑删除，将其is_delete改为1删除
     * @param bankOperator
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBankOperator(BankOperator bankOperator) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);

			result = bankOperatorMapper.deleteBankOperator(bankOperator); 
			
			/**********************操作Redis缓存**********************/
			//判断clientId是否有值，因删除缓存中的key需要
			if(Checker.isEmpty(bankOperator.getClientId())){
				//根据主键id查bankOperator对象，获得clientId
				List<BankOperator> bankOperators=this.findBankOperator(bankOperator);
				if(bankOperators!=null && bankOperators.size()==1){
					bankOperator=bankOperators.get(0);
					
				}
			}
			
			//删除登录缓存redis
			String token = BankLoginTokenRedis.get_cbbank_bank_token_token(String.valueOf(bankOperator.getId()));
			result=BankLoginTokenRedis.del_cbbank_bank_token_userid(String.valueOf(bankOperator.getId()));
			result=BankLoginTokenRedis.del_cbbank_bank_token_token(token);
			result=BankOperatorRedis.del_cbbank_bank_user_login_client_id_user_id(bankOperator);
			
			//删除用户概要信息Redis
			result=BankOperatorRedis.del_cbbank_bank_user_client_id_user_id(bankOperator);
			
			
			
			if(result){
				sqlSession.commit(true);
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
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
     * 修改 BankOperator
     * @param bankOperator
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBankOperator(BankOperator bankOperator) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			result = bankOperatorMapper.updateBankOperator(bankOperator); 
			
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			//判断clientId是否有值，因删除缓存中的key需要
			if(Checker.isEmpty(bankOperator.getClientId())){
				//根据主键id查bankOperator对象，获得clientId
				List<BankOperator> bankOperators=this.findBankOperator(bankOperator);
				if(bankOperators!=null && bankOperators.size()==1){
					bankOperator=bankOperators.get(0);
					
				}
			}
			//重新设置缓存-1.删除缓存2.查缓存findBankOperatorById此方法中有判断，若查不到重新设置缓存
			result=BankOperatorRedis.del_cbbank_bank_user_client_id_user_id(bankOperator);
			LogCvt.info("删除key：cbbank:bank_user:client_id:user_id完成");
			bankOperator=new CommonLogicImpl().getBankOperatorById(bankOperator.getClientId(), bankOperator.getId());
			if(Checker.isNotEmpty(bankOperator)){
				result=true;
			}else{
				result=false;
			}
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
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
     * 查询银行用户 BankOperator
     * @param bankOperator
     * @return BankOperator    银行用户po
     */
	@Override
	public List<BankOperator> findBankOperator(BankOperator bankOperator) {

		List<BankOperator> operList = new ArrayList<BankOperator>();
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);

			operList = bankOperatorMapper.findBankOperator(bankOperator); 
			
		} catch (Exception e) { 
			LogCvt.error("查询BankOperator失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return operList; 

	}


    /**
     * 分页查询 BankOperator
     * @param page
     * @param bankOperator
     * @return List<BankOperator>    结果集合 
     */
	@Override
	public Page<BankOperator> findBankOperatorByPage(Page<BankOperator> page, BankOperator bankOperator) {

		List<BankOperator> result = new ArrayList<BankOperator>(); 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			
			//判断orgCode级别，若为一级，减少多条件查询
			if(Checker.isNotEmpty(bankOperator)){
				if(Checker.isNotEmpty(bankOperator.getClientId()) && Checker.isNotEmpty(bankOperator.getOrgCode())){
					Org org = new CommonLogicImpl().getOrgByOrgCode(bankOperator.getOrgCode(),bankOperator.getClientId());
					
					if(Checker.isEmpty(org)){
						return new Page<BankOperator>();
					}
					
					//若一级则清空orgCode减少连表查询
					if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
						bankOperator.setOrgCode(null);
					}
				}
			}


			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			//用户类型：1、平台用户 2、银行用户
			bankOperator.setType(OperatorType.PLATFORM.getCode());
			result = bankOperatorMapper.findByPage(page, bankOperator); 
			page.setResultsContent(result);
			
			
		} catch (Exception e) { 
			LogCvt.error("分页查询BankOperator失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	/**
     * 银行用户登录 BankOperator
     * @param username 登录名
     * @param password 密码
     * @return LoginBankOperatorRes 返回token值/银行用户对象/登录失败次数/结果集/
     */
	@Override
	public LoginBankOperatorRes loginBankOperator(String username,String password,String clientId,String operatorIp) throws FroadServerException, Exception {
		
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		BankOperator bankOperatorResult =null;//通过登录名查询返回的用户对象
		
		Object loginFailureCount="-1";//登录失败次数
		String token="";//token值
		ResultCode resultCode=ResultCode.success;//结果Code，默认成功
		LoginBankOperatorRes loginRes=null;//返回值
		try {
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			bankOperatorResult=new BankOperator();

			//查询条件
			BankOperator filter=new BankOperator();
			filter.setUsername(username);
			filter.setIsDelete(false);
			filter.setClientId(clientId);
			filter.setType(OperatorType.PLATFORM.getCode());//平台用户
			bankOperatorResult=bankOperatorMapper.findBankOperatorByUsernameId(filter);//查询mysql中的符合username的记录
			if(Checker.isEmpty(bankOperatorResult)){//未找到记录
				resultCode=ResultCode.login_not_exist;
				
				throw new FroadServerException(resultCode.getMsg());
			}else{
				String userId=String.valueOf(bankOperatorResult.getId());
				String dbPassword=bankOperatorResult.getPassword();
				boolean dbStatus=bankOperatorResult.getStatus();//0-不可用 1-可用
				
				/************更新登录ip和时间*********************/
				BankOperator updateObject = new BankOperator();
				updateObject.setId(bankOperatorResult.getId());
				updateObject.setClientId(clientId);
				updateObject.setLastLoginIp(operatorIp);
				updateObject.setLastLoginTime(new Date());
				this.updateBankOperator(updateObject);
				/*********************************************/
				
				
				if(!dbStatus){//不可用
					
					/**********************操作Redis缓存**********************/
					//将缓存的key:cbbank:bank_user_login:client_id:user_id值中的内容+1
					loginFailureCount=BankOperatorRedis.incr_cbbank_bank_user_login_client_id_user_id(bankOperatorResult.getClientId(), bankOperatorResult.getId());
					resultCode=ResultCode.login_is_delete;
					log(username, clientId, operatorIp, ResultCode.login_is_delete.getMsg(),bankOperatorResult);
					
					
					//将取得的值重新set到LoginBankOperatorRes对象返回
					loginRes=new LoginBankOperatorRes();
					loginRes.setResult(new Result(resultCode));
					loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));
					
					//用户登录失败次数 - 发送监控
					new MonitorManager().send(MonitorPointEnum.Bank_userLogin_failureTimes);
				}else{
					if(password.equals(dbPassword)){//登录成功，将缓存key内容置为0
						LogCvt.info(username+"密码正确，登录成功");
						loginFailureCount=0;
						/**********************操作Redis缓存**********************/
						//将缓存的key:cbbank:bank_user_login:client_id:user_id值中的内容置为0
						BankOperatorRedis.set_cbbank_bank_user_login_client_id_user_id(bankOperatorResult.getClientId(), bankOperatorResult.getId());
						
						//先T掉原先登录的userid重新设置token与userid的关系
						String newToken = BankLoginTokenRedis.get_cbbank_bank_token_token(String.valueOf(userId));
						BankLoginTokenRedis.del_cbbank_bank_token_userid(userId);
						BankLoginTokenRedis.del_cbbank_bank_token_token(newToken);
						//set缓存中的key：cbbank:bank_token:token value:userid
						token=BankLoginTokenRedis.set_cbbank_bank_token_token(bankOperatorResult.getId());
						//set缓存中key：cbbank:bank_token:userid value:token 用于控制用户是否已登录
						BankLoginTokenRedis.set_cbbank_bank_token_userid(String.valueOf(bankOperatorResult.getId()),token);
						
												
						
						//登录成功后判断银行用户缓存是否有值，没值的话设置到缓存中-因系统可以在库里添加初始化的admin用户，故在登录成功后重新设置下缓存
						String dbUsername=BankOperatorRedis.get_cbbank_bank_user_client_id_user_id(clientId, Long.valueOf(userId), "username");
						if(Checker.isEmpty(dbUsername)){
							BankOperatorRedis.set_cbbank_bank_user_client_id_user_id(bankOperatorResult);
						}
						
						resultCode=ResultCode.success;
						log(username, clientId, operatorIp, "登录成功",bankOperatorResult);
						
						//将取得的值重新set到LoginBankOperatorRes对象返回
						loginRes=new LoginBankOperatorRes();
						loginRes.setResult(new Result(resultCode));
						loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));
						loginRes.setToken(token);
						loginRes.setBankOperator(bankOperatorResult);
						
					}else{//登录失败密码错误，将缓存key内容+1，并返回登录失败次数
						/**********************操作Redis缓存**********************/
						//将缓存的key:cbbank:bank_user_login:client_id:user_id值中的内容+1
						loginFailureCount=BankOperatorRedis.incr_cbbank_bank_user_login_client_id_user_id(bankOperatorResult.getClientId(), bankOperatorResult.getId());						
						resultCode=ResultCode.login_password_error;
						log(username, clientId, operatorIp, ResultCode.login_password_error.getMsg(),bankOperatorResult);
						LogCvt.error(username+"密码错误，登录失败，错误次数"+loginFailureCount);
						
						//将取得的值重新set到LoginBankOperatorRes对象返回
						loginRes=new LoginBankOperatorRes();
						loginRes.setResult(new Result(resultCode));
						loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));
						
						//用户登录失败次数 - 发送监控
						new MonitorManager().send(MonitorPointEnum.Bank_userLogin_failureTimes);
					}
				}
				
			}
			
			
		} catch (FroadServerException e) { 
			log(username, clientId, operatorIp, e.getMessage(),bankOperatorResult);
			throw e;
		} catch (Exception e) { 
			log(username, clientId, operatorIp, "登录异常!",bankOperatorResult);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return loginRes;
		
	}

	/**
	 * 添加登录日志记录
	 * @param username
	 * @param clientId 
	 * @param operatorIp
	 * @param desc
	 * @param bankOperatorResult
	 */
	public void log(String username,String clientId, String operatorIp,String desc,BankOperator bankOperatorResult){
		try{
			BankOperateLog bankOperatorLog=new BankOperateLog();
			CommonLogic commonLogic = new CommonLogicImpl();
			
			if(Checker.isNotEmpty(bankOperatorResult)){
				bankOperatorLog.setUserId(bankOperatorResult.getId());
				bankOperatorLog.setRoleId(bankOperatorResult.getRoleId());
				bankOperatorLog.setOrgCode(bankOperatorResult.getOrgCode());
				
				Org findOrg = commonLogic.queryByOrgCode(clientId, bankOperatorResult.getOrgCode());
				if(Checker.isNotEmpty(findOrg)){
					bankOperatorLog.setOrgName(findOrg.getOrgName());
				}
			}
			
			bankOperatorLog.setClientId(clientId);
			bankOperatorLog.setPlatType(PlatType.bank.getValue());
			bankOperatorLog.setUsername(username);
			bankOperatorLog.setDescription(desc);
			bankOperatorLog.setOperatorIp(operatorIp);
			bankOperatorLog.setLogType(true);//是否登录日志
			//添加日志到mysql
			commonLogic.addBankOperateLog(bankOperatorLog);
			
			
			//添加到bank-opt日志文件单独记录
			Map message =new LinkedMap();
			message.put("platType", bankOperatorLog.getPlatType());
			message.put("client_id", bankOperatorLog.getClientId());
			message.put("user_id", bankOperatorLog.getUserId());
			message.put("username", bankOperatorLog.getUsername());
			message.put("role_id", bankOperatorLog.getRoleId());
			message.put("org_code", bankOperatorLog.getOrgCode());
			message.put("org_name", bankOperatorLog.getOrgName());
			message.put("description", bankOperatorLog.getDescription());
			message.put("operator_ip", bankOperatorLog.getOperatorIp());
			LogCvt.info("bank-opt"+JSON.toJSONString(message));
			
			
		}catch(Exception  e){
			LogCvt.error(e.getMessage(),e);
		}
	}

	

	/**
    * 银行用户登录 -验证
    * @param clientId 客户端Id
    * @param userId 用户编号
    * @param token值
    * @return  BankOperatorCheckRes 验证是否匹配
    */
	@Override
	public BankOperatorCheckRes checkToken(String clientId,long userId,String token) throws FroadServerException, Exception{
		
		BankOperatorCheckRes bankOperatorCheckRes = new BankOperatorCheckRes();
		
		try{
			// 根据 userId 得到已经存在的 token 值
			String resultToken = BankLoginTokenRedis.get_cbbank_bank_token_token(String.valueOf(userId));
			// token 为空 - 未登录或token过期
			if(Checker.isEmpty(resultToken)){ 
				LogCvt.info("校验 token 不通过 - "+ ResultCode.check_token_logout.getMsg() +": userId:"+userId+" - token:"+token);
				bankOperatorCheckRes.setResult(new Result(ResultCode.check_token_logout));
				return bankOperatorCheckRes;
			}
						
						
			
			// 已经存在的token值 = 传进来校验的token值 - 登录过且token未过期
			if(String.valueOf(resultToken).equals(token)){
				//重新设置失效时间30分钟
				BankLoginTokenRedis.set_cbbank_bank_token_token_Time(String.valueOf(userId), token);
				//查询对象进行返回
				BankOperator bankOperator=new CommonLogicImpl().getBankOperatorById(clientId, userId);
				
				bankOperatorCheckRes.setResult(new Result(ResultCode.success));
				bankOperatorCheckRes.setBankOperator(bankOperator);
				
			}else{
				// 已经存在的token值 != 传进来校验的token值 - 传进来的token是被踢的
				bankOperatorCheckRes.setResult(new Result(ResultCode.check_token_out));
			}
			
		}catch(FroadServerException e){
			LogCvt.error("校验 token失败，原因:" + e.getMessage(), e); 
			throw e;
		}catch(Exception e){
			LogCvt.error("校验 token失败，原因:" + e.getMessage(), e); 
			throw e;
		}
		
		return bankOperatorCheckRes;
	}
	
	
	
	/**
	 * 银行用户登录退出
	 * @param token值
	 * @return  bool 是否登录退出成功
	 */
	@Override
	public Boolean logoutBankOperator(String token)  throws FroadServerException, Exception{
		//联合登录退出，也用此方法，其中redis key中token<->orgCode+"_"+username有相互对应关系
		//银行pc内部帐号退出，其中redis key中token<->userId有相互对应关系
		String userId=BankLoginTokenRedis.get_cbbank_bank_token_token(token);
		boolean result=BankLoginTokenRedis.del_cbbank_bank_token_token(token);
		if(Checker.isNotEmpty(userId)){
			result=BankLoginTokenRedis.del_cbbank_bank_token_userid(userId);
		}
		
		return result;
	}


	/**
     * 分页查询 BankOperator(只查当前机构下的信息，不涉及下属机构)
     * @param page
     * @param bankOperator
     * @return Page    结果集合 
     */
	@Override
	public Page<BankOperator> findBankOperatorByPageIncurrent(
			Page<BankOperator> page, BankOperator bankOperator) {
		List<BankOperator> result = new ArrayList<BankOperator>(); 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			//用户类型：1、平台用户 2、银行用户
			bankOperator.setType(OperatorType.PLATFORM.getCode());
			result = bankOperatorMapper.findFilterByPage(page, bankOperator); 
			page.setResultsContent(result);
			
			
		} catch (Exception e) { 
			LogCvt.error("分页查询BankOperator失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
		
	
	/**
     * 银行用户登录 BankOperator
     * @param username 登录名
     * @param password 密码
     * @return LoginBankOperatorRes 返回token值/银行用户对象/登录失败次数/结果集/
     */
	@Override
	public LoginBankOperatorRes findLoginFailureCount(String clientId,String username) throws FroadServerException, Exception {
		
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		BankOperator bankOperatorResult =null;//通过登录名查询返回的用户对象
		
		Object loginFailureCount="0";//登录失败次数
		ResultCode resultCode=ResultCode.success;//结果Code，默认成功
		LoginBankOperatorRes loginRes = new LoginBankOperatorRes();//返回值
		try {
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			bankOperatorResult=new BankOperator();

			//查询条件
			BankOperator filter=new BankOperator();
			filter.setUsername(username);
			filter.setIsDelete(false);
			filter.setClientId(clientId);
			filter.setType(OperatorType.PLATFORM.getCode());//平台用户
			bankOperatorResult=bankOperatorMapper.findBankOperatorByUsernameId(filter);//查询mysql中的符合username的记录
			if(Checker.isEmpty(bankOperatorResult)){//未找到记录
				resultCode=ResultCode.login_not_exist;
			}else{
				/**********************操作Redis缓存**********************/
				loginFailureCount=BankOperatorRedis.get_cbbank_bank_user_login_client_id_user_id(bankOperatorResult.getClientId(), bankOperatorResult.getId());	
				if( loginFailureCount == null || "".equals(loginFailureCount) ){
					loginFailureCount = 0;
				}
			}
			loginRes.setResult(new Result(resultCode));
			loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));
		} catch (FroadServerException e) { 
			LogCvt.error("银行用户登录获取错误次数异常，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
			LogCvt.error("银行用户登录获取错误次数异常，原因:" + e.getMessage(),e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return loginRes;
	}
	
}