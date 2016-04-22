package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BankOperatorMapper;
import com.froad.db.mysql.mapper.OrgUserRoleMapper;
import com.froad.db.redis.BankLoginTokenRedis;
import com.froad.db.redis.BankOperatorRedis;
import com.froad.db.redis.OrgUserRoleRedis;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OperatorType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrgUserRoleLogic;
import com.froad.logic.res.LoginBankOperatorRes;
import com.froad.logic.res.OrgUserRoleCheckRes;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.BankOperator;
import com.froad.po.BankRole;
import com.froad.po.Client;
import com.froad.po.Org;
import com.froad.po.OrgUserRole;
import com.froad.po.Result;
import com.froad.support.Support;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;

/**
 * 
 * <p>@Title: OrgUserRoleLogic.java</p>
 * <p>Description: 描述 </p> 银行联合登录-帐号表Logic实现类
 * @author f-road-ll
 * @version 1.0
 * @created 2015年3月17日
 */
public class OrgUserRoleLogicImpl implements OrgUserRoleLogic {

	
	
    /**
     * 增加 OrgUserRole
     * @param orgUserRole
     * @return Long    主键ID
     */
	@Override
	public Long addOrgUserRole(OrgUserRole orgUserRole, BankOperator bankOperator)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		OrgUserRoleMapper orgUserRoleMapper = null;
		BankOperatorMapper bankOperatorMapper = null;
		long orgUserRoleId=0l;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			//判断username是否已存在，登录名不可重复
			BankOperator filter=new BankOperator();
			filter.setUsername(bankOperator.getUsername());
			filter.setIsDelete(false);
			filter.setClientId(bankOperator.getClientId());
			filter.setType(OperatorType.BANK.getCode());
			filter=bankOperatorMapper.findBankOperatorByUsernameId(filter);//查询mysql中的符合username的记录
			if(Checker.isNotEmpty(filter)){
				throw new FroadServerException(filter.getUsername()+"用户名已存在！");
			}
			
			//设置创建时间
			bankOperator.setCreateTime(new Date());//获得系统时间
			//设置是否删除
			bankOperator.setIsDelete(false);
			//设置是否重置密码
			bankOperator.setIsReset(true);
			//设置是否可用
			bankOperator.setStatus(true);
			//用户类型：1、平台用户 2、银行用户
			bankOperator.setType(OperatorType.BANK.getCode());
			bankOperatorMapper.addBankOperator(bankOperator); 
			
			/**********************操作Redis缓存**********************/
			BankOperatorRedis.set_cbbank_bank_user_client_id_user_id(bankOperator);
			
			
			/**********************操作MySQL数据库**********************/
			orgUserRoleMapper = sqlSession.getMapper(OrgUserRoleMapper.class);

			
			/**
			 * 验证orgCode及username是否存在联合帐号表中
			 */
			OrgUserRole filter1 = new OrgUserRole();
			filter1.setOrgCode(orgUserRole.getOrgCode());
			filter1.setUsername(orgUserRole.getUsername());
			filter1.setClientId(orgUserRole.getClientId());
			List<OrgUserRole> orgUserRoleList=this.findOrgUserRole(filter1);
			if(Checker.isNotEmpty(orgUserRoleList)){
				throw new FroadServerException(filter.getOrgCode()+":"+filter.getUsername()+"联合登录账号已存在！");
			}
			
			orgUserRole.setUserId(bankOperator.getId());
			orgUserRoleMapper.addOrgUserRole(orgUserRole); 
			
			
			/**********************操作Redis缓存**********************/
			OrgUserRoleRedis.set_cbbank_bank_level_role_client_id_org_code_username(orgUserRole);
			
			orgUserRoleId=orgUserRole.getId();
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
		return orgUserRoleId; 

	}


    /**
     * 删除 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteOrgUserRole(OrgUserRole orgUserRole)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgUserRoleMapper orgUserRoleMapper = null;
		try {
			/**********************操作Redis缓存**********************/
			OrgUserRole find = new OrgUserRole();
			find.setId(orgUserRole.getId());
			//根据主键过滤查询出对象，删除缓存信息
			List<OrgUserRole> findList = this.findOrgUserRole(find);
			if(Checker.isNotEmpty(findList)){
				OrgUserRole oldOrgUserRole = findList.get(0);
				//删除缓存对象信息
				result=OrgUserRoleRedis.del_cbbank_bank_level_role_client_id_org_code_username(oldOrgUserRole);
			}
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgUserRoleMapper = sqlSession.getMapper(OrgUserRoleMapper.class);
			result = orgUserRoleMapper.deleteOrgUserRole(orgUserRole); 
			
			//提交
			if(result){
				sqlSession.commit(true);
			}else { // 删除失败
				sqlSession.rollback(true); 
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
     * 修改 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateOrgUserRole(OrgUserRole orgUserRole)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgUserRoleMapper orgUserRoleMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgUserRoleMapper = sqlSession.getMapper(OrgUserRoleMapper.class);

			//若修改所属orgCode，则对应的orgLevel级别也要对应的修改过来
			if(Checker.isNotEmpty(orgUserRole.getOrgCode())){
				orgUserRole.setOrgLevel(new CommonLogicImpl().getOrgByOrgCode(orgUserRole.getOrgCode(), orgUserRole.getClientId()).getOrgLevel());
			}
			
			result = orgUserRoleMapper.updateOrgUserRole(orgUserRole); 
			
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			OrgUserRole find = new OrgUserRole();
			find.setId(orgUserRole.getId());
			//根据主键过滤查询出对象，将最新修改过后的数据重新set到缓存中
			List<OrgUserRole> findList = this.findOrgUserRole(find);
			if(Checker.isNotEmpty(findList)){
				orgUserRole = findList.get(0);
				//缓存对象信息
				result=OrgUserRoleRedis.set_cbbank_bank_level_role_client_id_org_code_username(orgUserRole);
			}
			
			/**********************操作cb_bank_operator表中的角色id**********************/
			BankOperator updateObject = new BankOperator();
			updateObject.setId(orgUserRole.getUserId());
			updateObject.setClientId(orgUserRole.getClientId());
			updateObject.setRoleId(orgUserRole.getRoleId());
			if(Checker.isNotEmpty(orgUserRole.getOrgCode())){
				updateObject.setOrgCode(orgUserRole.getOrgCode());
			}
			new BankOperatorLogicImpl().updateBankOperator(updateObject);
			LogCvt.debug("修改角色同步到cb_bank_operator表中完成！");
			
			
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
     * 查询 OrgUserRole缓存role_id
     * @param orgCode 机构编号
     * @param username 登录名
     * @return role_id
     */
	@Override
	public Long findOrgUserRoleByRedis(String clientId,String orgCode, String username) {
		long roleId=-1;
		try{
			roleId=OrgUserRoleRedis.get_cbbank_bank_level_role_client_id_org_code_username(clientId, orgCode, username, "role_id");
			if(roleId==-1){//若从缓存中没查到数据，需从mysql中查
				OrgUserRole orgUserRole = new OrgUserRole();
				orgUserRole.setClientId(clientId);
				orgUserRole.setOrgCode(orgCode);
				orgUserRole.setUsername(username);
				List<OrgUserRole> orgRoles=this.findOrgUserRole(orgUserRole);
				if(Checker.isNotEmpty(orgRoles)){
					roleId=orgRoles.get(0).getRoleId();
				}
			}
		}catch (Exception e) { 
			LogCvt.error("查询银行联合登录帐号OrgUserRole缓存数据失败，原因:" + e.getMessage(), e); 
		}
		
		return roleId;
	}
	
	

    /**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return List<OrgUserRole>    结果集合 
     */
	@Override
	public List<OrgUserRole> findOrgUserRole(OrgUserRole orgUserRole) {

		List<OrgUserRole> result = new ArrayList<OrgUserRole>(); 
		SqlSession sqlSession = null;
		OrgUserRoleMapper orgUserRoleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgUserRoleMapper = sqlSession.getMapper(OrgUserRoleMapper.class);

			result = orgUserRoleMapper.findOrgUserRole(orgUserRole); 
		} catch (Exception e) { 
			LogCvt.error("查询OrgUserRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 OrgUserRole
     * @param page
     * @param orgUserRole
     * @return List<OrgUserRole>    结果集合 
     */
	@Override
	public Page<OrgUserRole> findOrgUserRoleByPage(Page<OrgUserRole> page, OrgUserRole orgUserRole) {

		List<OrgUserRole> result = new ArrayList<OrgUserRole>(); 
		SqlSession sqlSession = null;
		OrgUserRoleMapper orgUserRoleMapper = null;
		try { 
			//判断orgCode级别，若为一级，减少多条件查询
			if(Checker.isNotEmpty(orgUserRole)){
				if(Checker.isNotEmpty(orgUserRole.getClientId()) && Checker.isNotEmpty(orgUserRole.getOrgCode())){
					
					String[] orgArray = orgUserRole.getOrgCode().split(",");
					List<String> orgCodes = new ArrayList<String>();
					if (orgArray.length == 1) {
						Org org = new CommonLogicImpl().getOrgByOrgCode(orgUserRole.getOrgCode(),orgUserRole.getClientId());
						
						if(Checker.isEmpty(org)){
							return new Page<OrgUserRole>();
						}
						
						//若一级则清空orgCode减少连表查询
						if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
							orgUserRole.setOrgCodes(null);
						}else{
							orgCodes.add(orgUserRole.getOrgCode());
							orgUserRole.setOrgCodes(orgCodes);
						}
					}else{
						for (String orgcode : orgArray) {
							orgCodes.add(orgcode);
						}
						orgCodes = new CommonLogicImpl().queryLastOrgCode(orgUserRole.getClientId(), orgCodes);
						orgUserRole.setOrgCodes(orgCodes);
					}
				}
			}
			
			
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgUserRoleMapper = sqlSession.getMapper(OrgUserRoleMapper.class);

			result = orgUserRoleMapper.findByPage(page, orgUserRole); 
			page.setResultsContent(result);
		} catch (Exception e) {
			LogCvt.error("分页查询OrgUserRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

   /**
    * 银行联合登录
    * @param clientId 客户端Id
    * @param username 登录名
    * @param password 登录密码
    * @param operatorIp 登录ip
    * @return  LoginOrgUserRoleRes 返回token值/银行联合登录帐号对象
    */
	@Override
	public LoginBankOperatorRes loginOrgUserRole(String clientId, String username,String password,String operatorIp)  throws FroadServerException, Exception{
		LoginBankOperatorRes loginRes = null;
		Result result = new Result();
		BankOperatorLogicImpl bankOperatorImpl = new BankOperatorLogicImpl();
		CommonLogic commonLogic = new CommonLogicImpl();
		/***********************1.先调银行系统校验****************************************/
		
		/**
		 * 调用order模块发往银行系统校验用户信息
		 */
		Client clientResult = commonLogic.getClientById(clientId);
		String verifyOrg="";
		if(Checker.isNotEmpty(clientResult)){
			verifyOrg = clientResult.getBankZH();//获取校验机构，即银行组号
		}else{
			LogCvt.error("获取客户端信息异常"+clientId);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("获取客户端信息异常"+clientId);
			loginRes = new LoginBankOperatorRes();
			loginRes.setResult(result);
			return loginRes;
		}
		//校验银行组号是否有值
		if(Checker.isEmpty(verifyOrg)){
			LogCvt.error("银行组号为空，获取银行组号异常"+clientId);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("银行组号为空，获取银行组号异常"+clientId);
			loginRes = new LoginBankOperatorRes();
			loginRes.setResult(result);
			return loginRes;
		}
		
		
		Object loginFailureCount = -1;//登录错误次数
		//默认操作失败的Result
		Result checkResult = new Result(ResultCode.failed.getCode(),ResultCode.failed.getMsg());
		try{
			checkResult = new Support().employeeCodeVerify(clientId, verifyOrg, username, password, "100");
		}catch(Exception e){
			//根据需求，银行登陆验证失败统一返回此提示
			checkResult.setResultDesc("登录名或密码有误");
			//用户登录失败次数 - 发送监控
			new MonitorManager().send(MonitorPointEnum.Bank_userLogin_failureTimes);
			//记录日志，数据库中的description字段是varchar(250)允许500个字符
			if(checkResult.getResultDesc().length() > 500 ){
				bankOperatorImpl.log(username, clientId, operatorIp, "连接订单模块系统异常，详见日志文件！",null);
			}else{
				bankOperatorImpl.log(username, clientId, operatorIp, checkResult.getResultDesc(),null);
			}
			
			
			result.setResultCode(checkResult.getResultCode());
			result.setResultDesc(checkResult.getResultDesc());
			loginRes = new LoginBankOperatorRes();
			loginRes.setResult(result);
			//登录错误次数+1
			loginFailureCount = BankOperatorRedis.incr_cbbank_bank_user_login_client_id_username(clientId,username);
			loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));//登录错误次数
			
			return loginRes;
			
		}
		
		
		LogCvt.info("联合登录帐号"+username+"登录成功！进行后续的OrgUserRole数据操作...");
		/***********************2.内部表OrgUserRole处理****************************************/
		String jsonStr = checkResult.getResultDesc();
		String orgCode = JSonUtil.toObject(jsonStr, Map.class).get("orgNo").toString();
		String token="";//token值
		
		
		/**
		 * 验证orgCode及username是否存在联合帐号表中
		 */
		OrgUserRole orgUserRole = new OrgUserRole();
		orgUserRole.setUsername(username);
		orgUserRole.setClientId(clientId);
		List<OrgUserRole> orgUserRoleList=this.findOrgUserRole(orgUserRole);
        if(orgUserRoleList!=null && orgUserRoleList.size()==1){
        	//登录数据有的话，直接从数据库中查
        	orgUserRole=orgUserRoleList.get(0);
        	
        	/************更新登录ip和时间*********************/
    		BankOperator updateObject = new BankOperator();
    		updateObject.setId(orgUserRole.getUserId());
    		updateObject.setClientId(clientId);
    		updateObject.setLastLoginIp(operatorIp);
    		updateObject.setLastLoginTime(new Date());
    		bankOperatorImpl.updateBankOperator(updateObject);
    		/*********************************************/


    		//如本地用户机构值与银行返回的机构不符，以银行机构号为准，同步更新本地机构号--先不上
        	String ori_orgCode = orgUserRole.getOrgCode();
        	if (!ori_orgCode.equals(orgCode)) {
        		LogCvt.info("原机构" + ori_orgCode+"：返回机构：" + orgCode);
        		//闵总说先把联合登录机构同步需求回滚  保证和风那边数据权限正确
//        		orgUserRole.setOrgCode(orgCode);
//            	this.updateOrgUserRole(orgUserRole);	
			}
        	
        	//将本地用户机构赋值到银行返回的机构，以本地机构号为准
        	orgCode = orgUserRole.getOrgCode();
        	
        	//将缓存的key:cbbank:bank_user_login:client_id:username值中的登录错误次数内容置为0
			BankOperatorRedis.set_cbbank_bank_user_login_client_id_username(clientId,username);
			
			//记录成功日志
			BankOperator bankOperatorResult = new BankOperator();
			bankOperatorResult.setId(orgUserRole.getId());
			bankOperatorResult.setRoleId(orgUserRole.getRoleId());
			bankOperatorResult.setOrgCode(orgCode);
			bankOperatorImpl.log(username, clientId, operatorIp, "登录成功",bankOperatorResult);
			
    		
    		//先T掉原先登录的username重新设置token,token规则重新定义为userId
    		String oldToken = BankLoginTokenRedis.get_cbbank_bank_token_token(String.valueOf(orgUserRole.getUserId()));
    		BankLoginTokenRedis.del_cbbank_bank_token_userid(String.valueOf(orgUserRole.getUserId()));
    		BankLoginTokenRedis.del_cbbank_bank_token_token(oldToken);
    		//重新设置token,用于控制用户是否已登录
    		token=BankLoginTokenRedis.set_cbbank_bank_token_token(orgUserRole.getUserId());
    		BankLoginTokenRedis.set_cbbank_bank_token_userid(String.valueOf(orgUserRole.getUserId()),token);
    		
    		//将银行用户信息组装到平台用户对象返回
    		bankOperatorResult.setClientId(orgUserRole.getClientId());
    		bankOperatorResult.setUsername(orgUserRole.getUsername());
    		bankOperatorResult.setId(orgUserRole.getUserId());
    		
    		//set值进行返回
    		loginRes=new LoginBankOperatorRes();
    		loginRes.setToken(token);
    		loginRes.setBankOperator(bankOperatorResult);
    		loginRes.setResult(new Result());
    		loginRes.setLoginFailureCount(0);
    		return loginRes;
        }else{
        	LogCvt.info("用户["+username+"]第一次登录，添加联合登录帐号表信息");
        }
		
		
		// 1.根据orgCode查询机构对象
		Org org = commonLogic.getOrgByOrgCode(orgCode, clientId);
		if (Checker.isEmpty(org)) {
			error("联合登陆返回组织机构不存在", orgCode);
		}
		
		// 2.根据client_id和org_level从缓存中查询到role_id
		String orgLevel = org.getOrgLevel();// 机构级别
		Long roleId = new OrgLevelLogicImpl().findOrgLevelByRedis(clientId, orgLevel);
		if (Checker.isEmpty(roleId)) {
			error("查找角色ID失败", clientId + ":" + orgLevel);
		}

		// 3.根据role_id查询出角色名称
		BankRole bankRole = new BankRoleLogicImpl().findBankRoleById(roleId);
		String roleName = "";// 角色名称
		if (Checker.isNotEmpty(bankRole)) {
			roleName = bankRole.getRoleName();
		} else {
			error("查询角色名称失败", roleId);
		}

		// 4.将coreModule后台传过来的机构编号和登录名添加到cb_org_user_role帐号表中
		orgUserRole = new OrgUserRole();
		orgUserRole.setOrgCode(orgCode);
		orgUserRole.setUsername(username);
		Long orgUserRoleId=0l;
		
		// 5.保存用户信息到cb_bank_operator和cb_org_user_role
		BankOperator bankOperator = new BankOperator();
		bankOperator.setClientId(clientId);
		bankOperator.setUsername(username);
		bankOperator.setName(JSonUtil.toObject(jsonStr, Map.class).get("name").toString());
		bankOperator.setPassword("");
		bankOperator.setOrgCode(orgCode);
		bankOperator.setMobile(JSonUtil.toObject(jsonStr, Map.class).get("mobilePhone").toString());
		bankOperator.setEmail(JSonUtil.toObject(jsonStr, Map.class).get("email").toString());
		bankOperator.setRoleId(roleId);
		
		if (orgUserRoleList.size() == 0) {
			orgUserRole.setRoleName(roleName);
			orgUserRole.setRoleId(roleId);
			orgUserRole.setClientId(clientId);
			orgUserRole.setOrgLevel(orgLevel);
			orgUserRoleId=this.addOrgUserRole(orgUserRole, bankOperator);
		}

		// 6.记录成功日志
		BankOperator bankOperatorResult = new BankOperator();
		bankOperatorResult.setId(orgUserRoleId);
		bankOperatorResult.setRoleId(roleId);
		bankOperatorResult.setOrgCode(orgCode);
		bankOperatorImpl.log(username, clientId, operatorIp, "登录成功",bankOperatorResult);
		/************更新登录ip和时间*********************/
		BankOperator updateObject = new BankOperator();
		updateObject.setId(orgUserRole.getUserId());
		updateObject.setClientId(clientId);
		updateObject.setLastLoginIp(operatorIp);
		updateObject.setLastLoginTime(new Date());
		bankOperatorImpl.updateBankOperator(updateObject);
		/*********************************************/
		
		
		// 7.set缓存<key结构cbbank:bank_token:token> 银行联合登陆的token与平台用户登录token合并，采用userId保存
		String tokenResult = BankLoginTokenRedis.set_cbbank_bank_token_token(orgUserRole.getUserId());
		BankLoginTokenRedis.set_cbbank_bank_token_userid(String.valueOf(orgUserRole.getUserId()),tokenResult);

		// 8.设置LoginOrgUserRoleRes值
		loginRes = new LoginBankOperatorRes();
		loginRes.setToken(tokenResult);
		loginRes.setBankOperator(bankOperator);
		loginRes.setResult(new Result());
		//将缓存的key:cbbank:bank_user_login:client_id:username值中的登录错误次数内容置为0
		BankOperatorRedis.set_cbbank_bank_user_login_client_id_username(clientId,username);
		loginRes.setLoginFailureCount(0);
		
		return loginRes;
	}


	private void error(String mess,Object value) throws FroadServerException {
		LogCvt.error(mess+":"+value); 
		throw new FroadServerException(mess+":"+value);
	}

	
	/**
    * token验证（废弃，共用平台用户的checkToken）
    * @param token值
    * @param orgCode 机构编号
    * @param username 登录名
    * @return  bool 验证是否匹配
    */
	@Override
	public OrgUserRoleCheckRes checkToken(String clientId,String orgCode,String username,String token)  throws FroadServerException, Exception{

		OrgUserRoleCheckRes orgUserRoleCheckRes = new OrgUserRoleCheckRes();
		
		try{
			String source = orgCode+"_"+username;
			// 根据 source 得到已经存在的 token 值
			String resultToken = BankLoginTokenRedis.get_cbbank_bank_token_token(source);
			// token 为空 - 未登录或token过期
			if(Checker.isEmpty(resultToken)){ 
				LogCvt.info("校验 token 不通过 - "+ ResultCode.check_token_logout.getMsg() +": orgCode/username:"+orgCode+"/"+username+" - token:"+token);
				orgUserRoleCheckRes.setResult(new Result(ResultCode.check_token_logout));
				return orgUserRoleCheckRes;
			}
						
						
			
			// 已经存在的token值 = 传进来校验的token值 - 登录过且token未过期
			if(String.valueOf(resultToken).equals(token)){
				//重新设置失效时间30分钟
				BankLoginTokenRedis.set_cbbank_bank_token_token_Time(String.valueOf(source), token);
				//查询对象进行返回
				OrgUserRole resultOrgUserRole = null;
				OrgUserRole find = new OrgUserRole();
				find.setOrgCode(orgCode);
				find.setUsername(username);
				find.setClientId(clientId);
				List<OrgUserRole> orgUserRoles = this.findOrgUserRole(find);
				if(Checker.isNotEmpty(orgUserRoles)){
					resultOrgUserRole=orgUserRoles.get(0);
				}
				
				orgUserRoleCheckRes.setResult(new Result(ResultCode.success));
				orgUserRoleCheckRes.setOrgUserRole(resultOrgUserRole);
				
			}else{
				// 已经存在的token值 != 传进来校验的token值 - 传进来的token是被踢的
				orgUserRoleCheckRes.setResult(new Result(ResultCode.check_token_out));
			}
			
		}catch(FroadServerException e){
			LogCvt.error("联合登录校验 token失败，原因:" + e.getMessage(), e); 
			throw e;
		}catch(Exception e){
			LogCvt.error("联合登录校验 token失败，原因:" + e.getMessage(), e); 
			throw e;
		}
		
		return orgUserRoleCheckRes;
		
		
	}


	 /**
	    * 银行用户联合登录获取错误次数
	    * @param clientId 客户端Id
	    * @param username 登录名
	    * @return  LoginOrgUserRoleRes 返回token值/银行联合登录帐号对象
	    */
		@Override
		public LoginBankOperatorRes getLoginFailureCount(String clientId, String username)  throws FroadServerException, Exception{
			LoginBankOperatorRes loginRes = new LoginBankOperatorRes();
			Result result = new Result();
			Object loginFailureCount = 0;//登录错误次数
			try {
				loginFailureCount = BankOperatorRedis.get_cbbank_bank_user_login_client_id_username(clientId,username);
				if( loginFailureCount == null || "".equals(loginFailureCount) ){
					loginFailureCount = 0;
					result.setResult(ResultCode.login_not_exist);
				}else{
					result.setResult(ResultCode.success);
				}
				loginRes.setResult(result);
				loginRes.setLoginFailureCount(Integer.parseInt(String.valueOf(loginFailureCount)));//登录错误次数
			
			} catch (FroadServerException e) { 
				LogCvt.error("银行用户联合登录获取错误次数异常，原因:" + e.getMessage(), e); 
				throw e;
			} catch (Exception e) { 
				LogCvt.error("银行用户联合登录获取错误次数异常，原因:" + e.getMessage(),e);
				throw e;
			}
			return loginRes;
		}
}