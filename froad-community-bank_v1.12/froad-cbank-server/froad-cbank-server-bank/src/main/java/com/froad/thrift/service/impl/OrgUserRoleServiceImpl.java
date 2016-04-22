package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.db.redis.OrgUserRoleRedis;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrgUserRoleLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.OrgUserRoleLogicImpl;
import com.froad.logic.res.LoginBankOperatorRes;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Org;
import com.froad.po.OrgUserRole;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.vo.LoginBankOperatorVoRes;
import com.froad.thrift.vo.OrgUserRolePageVo;
import com.froad.thrift.vo.OrgUserRolePageVoRes;
import com.froad.thrift.vo.OrgUserRoleVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: OrgUserRoleImpl.java</p>
 * <p>Description: 描述 </p> 银行联合登录帐号管理thrift实现类
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class OrgUserRoleServiceImpl extends BizMonitorBaseService implements OrgUserRoleService.Iface {
	private OrgUserRoleLogic orgUserRoleLogic = new OrgUserRoleLogicImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	public OrgUserRoleServiceImpl(String name, String version) {
		super(name, version);
	}

	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	

    /**
     * 增加 OrgUserRole
     * @param orgUserRole
     * @return long    主键ID
     */
//	@Override
//	public CommonAddVoRes addOrgUserRole(OriginVo originVo,OrgUserRoleVo orgUserRoleVo) throws TException {
//		
//		CommonAddVoRes voRes = new CommonAddVoRes();
//		Result result = new Result();
//		try{
//			//添加操作日志记录
//			originVo.setDescription("add添加银行联合登录帐号信息");
//			LogUtils.addLog(originVo);
//			
//			// vo 转 po 
//			OrgUserRole orgUserRole =(OrgUserRole)BeanUtil.copyProperties(OrgUserRole.class, orgUserRoleVo);
//	
//			if(Checker.isEmpty(orgUserRole)){
//				throw new FroadServerException("添加OrgUserRole失败,原因:添加对象不能为空!");
//			}
//			if(Checker.isEmpty(orgUserRole.getClientId())){
//				throw new FroadServerException("添加OrgUserRole失败,原因:ClientId不能为空!");
//			}
//			if(Checker.isEmpty(orgUserRole.getRoleId())){
//				throw new FroadServerException("添加OrgUserRole失败,原因:RoleId不能为空!");
//			}
//			if(Checker.isEmpty(orgUserRole.getOrgCode())){
//				throw new FroadServerException("添加OrgUserRole失败,原因:OrgCode不能为空!");
//			}
//			if(Checker.isEmpty(orgUserRole.getUsername())){
//				throw new FroadServerException("添加OrgUserRole失败,原因:Username不能为空!");
//			}
//			if(Checker.isEmpty(orgUserRole.getOrgLevel())){
//				throw new FroadServerException("添加OrgUserRole失败,原因:OrgLevel不能为空!");
//			}
//			
//			
//			Long id = orgUserRoleLogic.addOrgUserRole(orgUserRole);
//			if(id != null && id > 0){
//				voRes.setId(id);
//			}else{
//				result.setResultCode(ResultCode.failed.getCode());
//				result.setResultDesc("添加银行联合登录帐号失败");
//			}
//		}catch (FroadServerException e) {
//			LogCvt.error("添加银行联合登录帐号addOrgUserRole失败，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//		}catch (Exception e) {
//			LogCvt.error("添加银行联合登录帐号addOrgUserRole异常，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//		}
//		
//		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
//		return voRes;
//		
//	}



    /**
     * 删除 OrgUserRole
     * @param orgUserRole
     * @return boolean    
     */
//	@Override
//	public ResultVo deleteOrgUserRole(OriginVo originVo,OrgUserRoleVo orgUserRoleVo) throws TException {
//		
//		Result result = new Result();
//		try{
//			//添加操作日志记录
//			originVo.setDescription("delete删除银行联合登录帐号信息");
//			LogUtils.addLog(originVo);
//					
//			// vo 转 po
//			OrgUserRole orgUserRole =(OrgUserRole)BeanUtil.copyProperties(OrgUserRole.class, orgUserRoleVo);
//			//验证update和delete时id不能为空
//			if(Checker.isEmpty(orgUserRole.getId())){
//				result = new Result(ResultCode.no_id_error);
//				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
//			}
//			
//			if (!orgUserRoleLogic.deleteOrgUserRole(orgUserRole)) {
//				result.setResultCode(ResultCode.failed.getCode());
//				result.setResultDesc("删除银行联合登录帐号表DB操作异常");
//			}
//		}catch (FroadServerException e) {
//			LogCvt.error("删除银行联合登录帐号deleteOrgUserRole失败，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//		} catch (Exception e) {
//			LogCvt.error("删除银行联合登录帐号deleteOrgUserRole异常，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//		}
//		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
//	}



    /**
     * 修改角色
     * @param orgUserRole 银行用户对象
     * @return ResultVo 结果集    
     */
	@Override
	public ResultVo updateOrgUserRole(OriginVo originVo,OrgUserRoleVo orgUserRoleVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行用户帐号信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po
			OrgUserRole orgUserRole =(OrgUserRole)BeanUtil.copyProperties(OrgUserRole.class, orgUserRoleVo);
			//验证update和delete时id不能为空
			if(Checker.isEmpty(orgUserRole.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			//验证clientId不能为空
			if(Checker.isEmpty(originVo.getClientId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			//验证roleId和roleName不能为空
			if(Checker.isEmpty(orgUserRole.getRoleId()) || Checker.isEmpty(orgUserRole.getRoleName())){
				LogCvt.error("银行用户修改角色中：修改要素roleId和roleName参数不全");
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			orgUserRole.setClientId(originVo.getClientId());
			if (! orgUserRoleLogic.updateOrgUserRole(orgUserRole)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改银行用户帐号表DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改银行用户帐号updateOrgUserRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改银行用户帐号updateOrgUserRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}

	/**
	 * 获取银行用户帐号详情
	 * @param id 主键id
	 */
	@Override
	public OrgUserRolePageVo getOrgUserRoleById(long id) throws TException {
		
		//验证update和delete时id不能为空
		if(Checker.isEmpty(id)){
			return new OrgUserRolePageVo();
		}
		
		OrgUserRolePageVo vo = null;
		OrgUserRole find = new OrgUserRole();
		find.setId(id);
		List<OrgUserRole> orgUserRoleList = orgUserRoleLogic.findOrgUserRole(find);
		if(Checker.isNotEmpty(orgUserRoleList)){
			OrgUserRole po = orgUserRoleList.get(0);
			
			vo = new OrgUserRolePageVo();
			vo.setId(po.getId());
			vo.setClientId(po.getClientId());
			vo.setRoleId(po.getRoleId());
			vo.setRoleName(po.getRoleName());
			vo.setOrgCode(po.getOrgCode());
			vo.setOrgLevel(po.getOrgLevel());
			vo.setUsername(po.getUsername());
			vo.setOrgName(commonLogic.queryByOrgCode(po.getClientId(), po.getOrgCode()).getOrgName());
			
			/**********************操作Redis缓存**********************/
			OrgUserRoleRedis.set_cbbank_bank_level_role_client_id_org_code_username(po);
		}
		
		return vo==null?new OrgUserRolePageVo():vo;
	}
	
	

    /**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return List<OrgUserRoleVo>
     */
	@Override
	public List<OrgUserRoleVo> getOrgUserRole(OrgUserRoleVo orgUserRoleVo) throws TException {
		OrgUserRole orgUserRole =(OrgUserRole)BeanUtil.copyProperties(OrgUserRole.class, orgUserRoleVo);
		
		List<OrgUserRole> orgUserRoleList = orgUserRoleLogic.findOrgUserRole(orgUserRole);
		List<OrgUserRoleVo> orgUserRoleVoList=(List<OrgUserRoleVo>)BeanUtil.copyProperties(OrgUserRoleVo.class, orgUserRoleList);
		return orgUserRoleVoList==null?new ArrayList<OrgUserRoleVo>():orgUserRoleVoList;
	}


	 /**
	  * 银行用户分页查询
     * @param orgUserRole
     * @return OrgUserRolePageVoRes
     */
	@Override
	public OrgUserRolePageVoRes getOrgUserRoleByPage(PageVo pageVo, OrgUserRoleVo orgUserRoleVo) throws TException {
		Page<OrgUserRole> page = null;
		OrgUserRole orgUserRole = null;
		
		//判断orgCode有值时，clientId不可为空
		if(Checker.isNotEmpty(orgUserRoleVo)){
			if(Checker.isNotEmpty(orgUserRoleVo.getOrgCode())){
				if(Checker.isEmpty(orgUserRoleVo.getClientId())){
					LogCvt.error("所属机构有值，clientId不可为空");
					return new OrgUserRolePageVoRes();
				}
			}
		}
				
				
				
		/*vo转po*/
		page=(Page<OrgUserRole>)BeanUtil.copyProperties(Page.class, pageVo);
		orgUserRole=(OrgUserRole)BeanUtil.copyProperties(OrgUserRole.class, orgUserRoleVo);

		page = orgUserRoleLogic.findOrgUserRoleByPage(page, orgUserRole);

		
		/******************set机构名称**************************/
		List<OrgUserRole> orgUserRoleList = (List<OrgUserRole>)page.getResultsContent();
		List<OrgUserRolePageVo> orgUserRolePageVoList = new ArrayList<OrgUserRolePageVo>();
		if(Checker.isNotEmpty(orgUserRoleList)){
			OrgUserRolePageVo vo = new OrgUserRolePageVo();
			String orgName="";
			for(OrgUserRole po : orgUserRoleList){
				//查询机构名称
				Org org =  commonLogic.queryByOrgCode(po.getClientId(), po.getOrgCode());
				if(Checker.isNotEmpty(org)) {
					orgName = org.getOrgName();
				}else{
					orgName = "";	
				}
				
				vo = new OrgUserRolePageVo();
				vo.setId(po.getId());
				vo.setClientId(po.getClientId());
				vo.setRoleId(po.getRoleId());
				vo.setRoleName(po.getRoleName());
				vo.setOrgCode(po.getOrgCode());
				vo.setOrgName(orgName);
				vo.setOrgLevel(po.getOrgLevel());
				vo.setUsername(po.getUsername());
				orgUserRolePageVoList.add(vo);
			}
		}
		
		
		/*po转vo*/
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		
		//返回对象set值
		OrgUserRolePageVoRes orgUserRolePageVoRes = new OrgUserRolePageVoRes();
		orgUserRolePageVoRes.setOrgUserRolePageVoList(orgUserRolePageVoList);
		orgUserRolePageVoRes.setPage(pageVo);
		
		return orgUserRolePageVoRes;
	}


	/**
     * 银行联合登录加token值
     * @param orgCode 机构编号
     * @param username 登录名
     * @param token值 (用户第一次登录可为空后续登录验证操作需传token值过来验证)
     * @return Boolean 是否成功
     */
	@Override
	public LoginBankOperatorVoRes loginOrgUserRole(OriginVo originVo, String username, String password)
			throws TException {
		LogCvt.info("银行联合登录用户"+username+"正在进行登录操作");
		
		LoginBankOperatorVoRes loginVoRes = new LoginBankOperatorVoRes();
		ResultVo result = new ResultVo();
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
			
			
			LoginBankOperatorRes loginRes= orgUserRoleLogic.loginOrgUserRole(originVo.getClientId(),username,password,originVo.getOperatorIp());
			loginVoRes=(LoginBankOperatorVoRes)BeanUtil.copyProperties(LoginBankOperatorVoRes.class, loginRes);
		}catch (FroadServerException e) {
			LogCvt.error("联合登录用户OrgUserRole登录失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		} catch (Exception e) {
			LogCvt.error("联合登录用户OrgUserRole登录异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
			loginVoRes.setResult(result);
			//用户登录失败次数 - 发送监控
			monitorService.send(MonitorPointEnum.Bank_userLogin_failureTimes);
		}
		return loginVoRes==null?new LoginBankOperatorVoRes():loginVoRes;
	}



	
	/**
     * 银行联合登录
     * @param token 类似uuid的值
     * @param orgCode 机构编号
     * @param username 登录名
     * @return Boolean 是否成功
     */
//	@Override
//	public OrgUserRoleCheckVoRes checkToken(String clientId,String orgCode,String username,String token)
//			throws TException {
//		LogCvt.info("银行联合登录token"+token+"验证checkToken");
//		
//		OrgUserRoleCheckVoRes orgVoRes = new OrgUserRoleCheckVoRes();
//		Result result = new Result();
//		try{
//			if(Checker.isEmpty(clientId) || Checker.isEmpty(token) || Checker.isEmpty(orgCode) || Checker.isEmpty(username)){
//				result = new Result(ResultCode.notAllParameters);
//				orgVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
//				return orgVoRes;
//			}
//			
//			OrgUserRoleCheckRes orgUserRoleCheckRes = orgUserRoleLogic.checkToken(clientId,orgCode, username, token);
//			orgVoRes = (OrgUserRoleCheckVoRes)BeanUtil.copyProperties(OrgUserRoleCheckVoRes.class, orgUserRoleCheckRes);
//			
//		} catch (FroadServerException e) {
//			LogCvt.error("银行联合登录token验证checkToken失败，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
//			orgVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
//		}catch (Exception e) {
//			LogCvt.error("银行联合登录token验证checkToken异常，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.bank_error.getCode());
//			result.setResultDesc(ResultCode.bank_error.getMsg());
//			orgVoRes.setResult((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
//		}
//		return orgVoRes;
//		
//	}


	/**
     * 银行用户联合登录获取错误次数
     * @param username 登录名
     * @return LoginBankOperatorVoRes 登录响应对象
     */
	@Override
	public LoginBankOperatorVoRes getLoginFailureCount(OriginVo originVo, String username)
			throws TException {
		LogCvt.info("银行联合登录用户"+username+"正在进行获取错误次数操作");
		
		LoginBankOperatorVoRes loginVoRes = new LoginBankOperatorVoRes();
		ResultVo result = new ResultVo();
		try{
			LoginBankOperatorVoRes loginBankOperatorVoRes = new LoginBankOperatorVoRes();
			//用户名不能为空
			if(Checker.isEmpty(username)){
				LogCvt.error(ResultCode.login_username_not.getMsg());
				result.setResultCode(ResultCode.login_username_not.getCode());
				result.setResultDesc(ResultCode.login_username_not.getMsg());
				loginBankOperatorVoRes.setResult(result);
				
				return loginBankOperatorVoRes;
			}
			
			//clientId不能为空
			if(Checker.isEmpty(originVo) || Checker.isEmpty(originVo.getClientId())){
				LogCvt.error("clientId不能为空");
				result.setResultCode(ResultCode.notAllParameters.getCode());
				result.setResultDesc("clientId不能为空");
				loginBankOperatorVoRes.setResult(result);
				
				return loginBankOperatorVoRes;
			}
			
			LoginBankOperatorRes loginRes= orgUserRoleLogic.getLoginFailureCount(originVo.getClientId(),username);
			loginVoRes=(LoginBankOperatorVoRes)BeanUtil.copyProperties(LoginBankOperatorVoRes.class, loginRes);
		}catch (FroadServerException e) {
			LogCvt.error("联合登录用户获取错误次数失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			loginVoRes.setResult(result);
		} catch (Exception e) {
			LogCvt.error("联合登录用户获取错误次数异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
			loginVoRes.setResult(result);
		}
		return loginVoRes==null?new LoginBankOperatorVoRes():loginVoRes;
	}

}
