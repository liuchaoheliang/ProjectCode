package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankOperatorLogic;
import com.froad.logic.impl.BankOperatorLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.res.BankOperatorCheckRes;
import com.froad.logic.res.LoginBankOperatorRes;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.BankOperator;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.vo.BankOperatorCheckVoRes;
import com.froad.thrift.vo.BankOperatorPageVoRes;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.LoginBankOperatorVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: BankOperatorImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankOperatorServiceImpl extends BizMonitorBaseService implements BankOperatorService.Iface {
	private BankOperatorLogic bankOperatorLogic = new BankOperatorLogicImpl();
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	
	public BankOperatorServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 BankOperator
     * @param bankOperator
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addBankOperator(OriginVo originVo,BankOperatorVo bankOperatorVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			
			//添加操作日志记录
			originVo.setDescription("添加银行操作员");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			BankOperator bankOperator =(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);
			
			if(Checker.isEmpty(bankOperator)){
				throw new FroadServerException("添加BankOperator失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getClientId())){
				throw new FroadServerException("添加BankOperator失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getUsername())){
				throw new FroadServerException("添加BankOperator失败,原因:Username不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getPassword())){
				throw new FroadServerException("添加BankOperator失败,原因:Password不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getStatus())){
				throw new FroadServerException("添加BankOperator失败,原因:Status不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getOrgCode())){
				throw new FroadServerException("添加BankOperator失败,原因:OrgCode不能为空!");
			}
			if(Checker.isEmpty(bankOperator.getRoleId())){
				throw new FroadServerException("添加BankOperator失败,原因:RoleId不能为空!");
			}
			//检查username登录名是否符合规范
			if(!Checker.isUsername(bankOperator.getUsername())){
				throw new FroadServerException("用户名格式不正确！");
			}
			
			Long id = bankOperatorLogic.addBankOperator(bankOperator);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加银行操作员失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("添加银行操作员addBankOperator失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加银行操作员addBankOperator异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
	}



    /**
     * 删除 BankOperator
     * @param bankOperator
     * @return boolean    
     */
	@Override
	public ResultVo deleteBankOperator(OriginVo originVo,BankOperatorVo bankOperatorVo) throws TException {
		Result result = new Result();
		
		try{
			//添加操作日志记录
			originVo.setDescription("删除银行操作员");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			BankOperator bankOperator =(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);
			//验证update和delete时id不能为空
			if(Checker.isEmpty(bankOperator.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			//控制当前登录人不能删除自己帐号
			if(Checker.isNotEmpty(originVo.getOperatorId())){
				if(originVo.getOperatorId() == bankOperatorVo.getId()){
					throw new FroadServerException("不允许删除当前登录人帐号！");
				}
			}
			
			
			if (!bankOperatorLogic.deleteBankOperator(bankOperator)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除银行操作员DB操作失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("删除银行操作员deleteBankOperator失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除银行操作员deleteBankOperator异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 BankOperator
     * @param bankOperator
     * @return boolean    
     */
	@Override
	public ResultVo updateBankOperator(OriginVo originVo,BankOperatorVo bankOperatorVo) throws TException {
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行操作员");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			BankOperator bankOperator =(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);
			//验证update和delete时id不能为空
			if(Checker.isEmpty(bankOperator.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			
			if (!bankOperatorLogic.updateBankOperator(bankOperator)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改银行操作员DB操作失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("修改银行操作员updateBankOperator失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改银行操作员updateBankOperator异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 BankOperator
     * @param bankOperator
     * @return List<BankOperatorVo>
     */
	@Override
	public List<BankOperatorVo> getBankOperator(BankOperatorVo bankOperatorVo) throws TException {
		BankOperator bankOperator =(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);

		List<BankOperator> bankOperatorList = bankOperatorLogic.findBankOperator(bankOperator);
		List<BankOperatorVo> bankOperatorVoList =(List<BankOperatorVo>)BeanUtil.copyProperties(BankOperatorVo.class, bankOperatorList);
		
		return bankOperatorVoList==null?new ArrayList<BankOperatorVo>():bankOperatorVoList;
	}


	 /**
     * 分页查询 BankOperator
     * @parm pageVo 分页信息page
     * @param bankOperator 查询条件
     * @return BankOperatorPageVoRes
     */
	@Override
	public BankOperatorPageVoRes getBankOperatorByPage(PageVo pageVo, BankOperatorVo bankOperatorVo) throws TException {
		Page<BankOperator> page = null;
		BankOperator bankOperator = null;
		List<BankOperatorVo> bankOperatorVoList=null;
		
		//判断orgCode有值时，clientId不可为空
		if(Checker.isNotEmpty(bankOperatorVo)){
			if(Checker.isNotEmpty(bankOperatorVo.getOrgCode())){
				if(Checker.isEmpty(bankOperatorVo.getClientId())){
					LogCvt.error("所属机构有值，clientId不可为空");
					return new BankOperatorPageVoRes();
				}
			}
		}
		
		
		/*将vo转成po*/
		page=(Page<BankOperator>)BeanUtil.copyProperties(Page.class, pageVo);
		bankOperator=(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);

		page = bankOperatorLogic.findBankOperatorByPage(page, bankOperator);
		
		/*将po转成vo*/
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bankOperatorVoList=(List<BankOperatorVo>)BeanUtil.copyProperties(BankOperatorVo.class, page.getResultsContent());
		
		//返回对象set值
		BankOperatorPageVoRes bankOperatorPageVoRes = new BankOperatorPageVoRes();
		bankOperatorPageVoRes.setPage(pageVo);
		bankOperatorPageVoRes.setBankOperatorVoList(bankOperatorVoList);
		
		return bankOperatorPageVoRes;
	}

	/**
	 * 银行用户登录
	 * @param username 登录名
	 * @param password 密码
	 * @param token值 (用户第一次登录可为空后续登录验证操作需传token值过来验证)
	 * @return LoginBankOperatorVoRes 包含ResultVo结果集及登录失败次数
	 */
	@Override
	public LoginBankOperatorVoRes loginBankOperator(OriginVo originVo,String username,String password)
			throws TException {
		
		ResultVo result = new ResultVo();
		LoginBankOperatorVoRes loginVoRes = new LoginBankOperatorVoRes();
		try{
			//用户登录次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_times);
			
			LoginBankOperatorVoRes loginBankOperatorVoRes = new LoginBankOperatorVoRes();
			//用户名不能为空
			if(Checker.isEmpty(username)){
				LogCvt.error(ResultCode.login_username_not.getMsg());
				result.setResultCode(ResultCode.login_username_not.getCode());
				result.setResultDesc(ResultCode.login_username_not.getMsg());
				loginBankOperatorVoRes.setResult(result);
				
				//用户登录失败次数 - 发送监控
				monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
				
				return loginBankOperatorVoRes;
			}
			//密码不能为空
			if(Checker.isEmpty(password)){
				LogCvt.error(ResultCode.login_password_not.getMsg());
				result.setResultCode(ResultCode.login_password_not.getCode());
				result.setResultDesc(ResultCode.login_password_not.getMsg());
				loginBankOperatorVoRes.setResult(result);
				
				//用户登录失败次数 - 发送监控
				monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
				
				return loginBankOperatorVoRes;
			}
			//clientId不能为空
			if(Checker.isEmpty(originVo) || Checker.isEmpty(originVo.getClientId())){
				LogCvt.error("clientId不能为空");
				result.setResultCode(ResultCode.notAllParameters.getCode());
				result.setResultDesc("clientId不能为空");
				loginBankOperatorVoRes.setResult(result);
				
				//用户登录失败次数 - 发送监控
				monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
				
				return loginBankOperatorVoRes;
			}
			
			
			LoginBankOperatorRes loginRes=bankOperatorLogic.loginBankOperator(username, password,originVo.getClientId(),originVo.getOperatorIp());
			loginVoRes=(LoginBankOperatorVoRes)BeanUtil.copyProperties(LoginBankOperatorVoRes.class, loginRes);
		
			 
		}catch (FroadServerException e) {
			LogCvt.error("银行用户BankOperator登录失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		} catch (Exception e) {
			LogCvt.error("银行用户BankOperator登录异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		}
		return loginVoRes==null?new LoginBankOperatorVoRes():loginVoRes;
	}

	/**
     * 银行用户token校验
     * @param clientId 客户端Id
     * @param token 类似uuid的值
     * @param userId 用户编号
     * @return Boolean 是否成功
     */
	@Override
	public BankOperatorCheckVoRes checkToken(String clientId,long userId,String token)
			throws TException {
		
		BankOperatorCheckVoRes bankOperatorCheckVoRes = new BankOperatorCheckVoRes();
		Result result = new Result();
		
		try{
			if(Checker.isEmpty(clientId) || Checker.isEmpty(userId) || Checker.isEmpty(token)){
				LogCvt.error(ResultCode.notAllParameters.getMsg());
				result = new Result(ResultCode.notAllParameters);
				bankOperatorCheckVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
				return bankOperatorCheckVoRes;
			}
			
			BankOperatorCheckRes bankOperatorCheckRes = bankOperatorLogic.checkToken(clientId,userId, token);
			bankOperatorCheckVoRes=(BankOperatorCheckVoRes)BeanUtil.copyProperties(BankOperatorCheckVoRes.class, bankOperatorCheckRes);
			
		}catch (FroadServerException e) {
			LogCvt.error("银行用户token校验失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			bankOperatorCheckVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		}catch (Exception e) {
			LogCvt.error("银行用户token校验异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
			bankOperatorCheckVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		}
		
		return bankOperatorCheckVoRes;
		
	}
	
	

	/**
     * 根据id查询单个BankOperatorVo
     * @param clientId 客户端id
     * @param userId 用户编号
     * @return BankOperatorVo
     */
	@Override
	public BankOperatorVo getBankOperatorById(String clientId, long userId)
			throws TException {
		if(Checker.isEmpty(clientId) || userId < 0){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new BankOperatorVo();
		}
		
		BankOperator bankOperator=new CommonLogicImpl().getBankOperatorById(clientId, userId);
		BankOperatorVo bankOperatorVo=(BankOperatorVo)BeanUtil.copyProperties(BankOperatorVo.class, bankOperator);
		
		return bankOperatorVo==null?new BankOperatorVo():bankOperatorVo;
	}

	/**
     * 银行用户退出BankOperatorVo
     * @param token
     * @return boolean 是否成功
     */
	@Override
	public ResultVo logoutBankOperator(String token) throws TException {
		
		Result result = new Result();
		try{
			if(Checker.isEmpty(token)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			if (!bankOperatorLogic.logoutBankOperator(token)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("银行用户登录退出token"+token+"异常");
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
		
	}


	@Override
	public BankOperatorPageVoRes getBankOperatorByPageInCurrentOrg(
			PageVo pageVo, BankOperatorVo bankOperatorVo) throws TException {
		Page<BankOperator> page = null;
		BankOperator bankOperator = null;
		List<BankOperatorVo> bankOperatorVoList=null;
		
		//判断orgCode有值时，clientId不可为空
		if(Checker.isNotEmpty(bankOperatorVo)){
			if(Checker.isNotEmpty(bankOperatorVo.getOrgCode())){
				if(Checker.isEmpty(bankOperatorVo.getClientId())){
					LogCvt.error("所属机构有值，clientId不可为空");
					return new BankOperatorPageVoRes();
				}
			}
		}
		
		
		/*将vo转成po*/
		page=(Page<BankOperator>)BeanUtil.copyProperties(Page.class, pageVo);
		bankOperator=(BankOperator)BeanUtil.copyProperties(BankOperator.class, bankOperatorVo);

		page = bankOperatorLogic.findBankOperatorByPageIncurrent(page, bankOperator);
		
		/*将po转成vo*/
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bankOperatorVoList=(List<BankOperatorVo>)BeanUtil.copyProperties(BankOperatorVo.class, page.getResultsContent());
		
		//返回对象set值
		BankOperatorPageVoRes bankOperatorPageVoRes = new BankOperatorPageVoRes();
		bankOperatorPageVoRes.setPage(pageVo);
		bankOperatorPageVoRes.setBankOperatorVoList(bankOperatorVoList);
		
		return bankOperatorPageVoRes;
	}


	/**
	 * 银行用户登录获取错误次数
	 * @param username 登录名
	 * @return LoginBankOperatorVoRes 包含ResultVo结果集及登录失败次数
	 */
	@Override
	public LoginBankOperatorVoRes getLoginFailureCount(OriginVo originVo,String username)
			throws TException {
		
		ResultVo result = new ResultVo();
		LoginBankOperatorVoRes loginVoRes = new LoginBankOperatorVoRes();
		try{
			//用户登录次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_times);
			
			LoginBankOperatorVoRes loginBankOperatorVoRes = new LoginBankOperatorVoRes();
			//用户名不能为空
			if(Checker.isEmpty(username)){
				LogCvt.error(ResultCode.login_username_not.getMsg());
				result.setResultCode(ResultCode.login_username_not.getCode());
				result.setResultDesc(ResultCode.login_username_not.getMsg());
				loginBankOperatorVoRes.setResult(result);
				
				//用户登录失败次数 - 发送监控
				monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
				
				return loginBankOperatorVoRes;
			}
			//clientId不能为空
			if(Checker.isEmpty(originVo) || Checker.isEmpty(originVo.getClientId())){
				LogCvt.error("clientId不能为空");
				result.setResultCode(ResultCode.notAllParameters.getCode());
				result.setResultDesc("clientId不能为空");
				loginBankOperatorVoRes.setResult(result);
				
				//用户登录失败次数 - 发送监控
				monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
				
				return loginBankOperatorVoRes;
			}
			
			
			LoginBankOperatorRes loginRes=bankOperatorLogic.findLoginFailureCount(originVo.getClientId(),username);
			loginVoRes=(LoginBankOperatorVoRes)BeanUtil.copyProperties(LoginBankOperatorVoRes.class, loginRes);
		
			 
		}catch (FroadServerException e) {
			LogCvt.error("银行用户登录获取错误次数失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		} catch (Exception e) {
			LogCvt.error("银行用户登录获取错误次数异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		}
		return loginVoRes==null?new LoginBankOperatorVoRes():loginVoRes;
	}
	
	
	
}
