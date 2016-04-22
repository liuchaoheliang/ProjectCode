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
 * @Title: RoleImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.thrift.TException;

import com.froad.enums.MonitorPointEnum;
import com.froad.enums.Platform;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BossRoleLogic;
import com.froad.logic.RoleLogic;
import com.froad.logic.impl.BossRoleLogicImpl;
import com.froad.logic.impl.RoleLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Result;
import com.froad.po.Role;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.FinityRoleListVoRes;
import com.froad.thrift.vo.finity.FinityRoleVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: FinityRoleServiceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2016年1月7日
 */
public class FinityRoleServiceImpl extends BizMonitorBaseService  implements FinityRoleService.Iface {
	private RoleLogic roleLogic = new RoleLogicImpl();
	private BossRoleLogic bossRoleLogic = new BossRoleLogicImpl();
	public FinityRoleServiceImpl(String name, String version) {
		super(name, version);
	}
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	
	
	 /**
     * 增加 Role
     * @param Role
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addFinityRole(OriginVo originVo,FinityRoleVo roleVo,List<Long> resourceIds) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加角色");
			LogUtils.addLog(originVo);
			Long id = 0l;
			//vo 转 po 
			Role role=(Role)BeanUtil.copyProperties(Role.class, roleVo);
			if(Checker.isEmpty(role) || Checker.isEmpty(resourceIds)){
				throw new FroadServerException("添加Role失败,原因:添加对象不能为空!");
			}
			
			if(!Checker.isEmpty(role.getPlatform()) && (role.getPlatform().equals(Platform.boss.getType()) 
					|| role.getPlatform().equals(Platform.merchant.getType())
					|| role.getPlatform().equals(Platform.bank.getType()))){
				if(Checker.isEmpty(role.getIsAdmin())){
					throw new FroadServerException("添加Role失败,原因:isAdmin不能为空!");
				}
				if(Checker.isEmpty(role.getIsDelete())){
					throw new FroadServerException("添加Role失败,原因:isDelete不能为空!");
				}
				if(Checker.isEmpty(role.getUserId())){
					throw new FroadServerException("添加Role失败,原因:userId不能为空!");
				}
				id = bossRoleLogic.addRole(role, resourceIds);
			}else{
				if(Checker.isEmpty(role.getClientId())){
					throw new FroadServerException("添加Role失败,原因:ClientId不能为空!");
				}
				if(Checker.isEmpty(role.getTag())){
					throw new FroadServerException("添加Role失败,原因:Tag不能为空!");
				}
				if(Checker.isEmpty(role.getOrgCode())){
					throw new FroadServerException("添加Role失败,原因:OrgCode不能为空!");
				}
				
				id = roleLogic.addRole(role,resourceIds);
			}
			
			
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加角色失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加角色addRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加角色addRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}


    /**
     * 修改 Role
     * @param role
     * @return boolean    
     */
	@Override
	public ResultVo updateFinityRole(OriginVo originVo,FinityRoleVo roleVo,List<Long> resourceIds) throws TException {
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改角色");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			Role role = (Role)BeanUtil.copyProperties(Role.class, roleVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(role.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			//参数不全
			if(Checker.isEmpty(resourceIds)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if(!Checker.isEmpty(role.getPlatform()) && (role.getPlatform().equals(Platform.boss.getType()) 
					|| role.getPlatform().equals(Platform.merchant.getType())
					|| role.getPlatform().equals(Platform.bank.getType()))){
				if (!bossRoleLogic.updateRole(role, resourceIds)) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("修改角色DB操作异常");
				}
			}else{
				if (!roleLogic.updateRole(role, resourceIds)) {
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc("修改角色DB操作异常");
				}
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("修改角色updateRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改角色updateRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 Role
     * @param role
     * @return List<RoleVo>
     */
	@Override
	public List<FinityRoleVo> getFinityRole(FinityRoleVo roleVo) throws TException {
		LogCvt.info("查询Role");
		List<FinityRoleVo> roleVoList = null;
		try{
			Role role = new Role();
			try {
				//处理long原始类型的数据处理
				BeanUtils.copyProperties(role, roleVo);
				if(roleVo.getId()<1){
					role.setId(null);
				}
				if(roleVo.getUserId()<1){
					role.setUserId(null);
				}
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			} 
	
			if(!Checker.isEmpty(role.getPlatform()) && (role.getPlatform().equals(Platform.boss.getType()) 
					|| role.getPlatform().equals(Platform.merchant.getType())
					|| role.getPlatform().equals(Platform.bank.getType()))){
				List<Role> roleList = bossRoleLogic.findRole(role);
				roleVoList = new ArrayList<FinityRoleVo>();
				for (Role po : roleList) {
					FinityRoleVo vo = (FinityRoleVo)BeanUtil.copyProperties(FinityRoleVo.class, po);
					roleVoList.add(vo);
				}
			}else{
				List<Role> roleList = roleLogic.findRole(role);
				roleVoList = new ArrayList<FinityRoleVo>();
				for (Role po : roleList) {
					FinityRoleVo vo = (FinityRoleVo)BeanUtil.copyProperties(FinityRoleVo.class, po);
					roleVoList.add(vo);
				}
				
			}
		}catch (FroadServerException e) {
			LogCvt.error("查询角色getFinityRole失败，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.failed.getCode());
//			result.setResultDesc(e.getMessage());
			monitorService.send(MonitorPointEnum.Finity_Role_Select_Failure);
		}catch (Exception e) {
			LogCvt.error("查询角色getFinityRole异常，原因:" + e.getMessage(), e);
//			result.setResultCode(ResultCode.finity_error.getCode());
//			result.setResultDesc(ResultCode.finity_error.getMsg());
			monitorService.send(MonitorPointEnum.Finity_Role_Select_Failure);
		}
		return roleVoList;
	}
	
	/**
	 * 根据UserId、platform获取所能查看范围的角色列表
	 * @param userId 用户Id
	 * @param platform 平台
	 */
	@Override
	public FinityRoleListVoRes getFinityRoleByUserId(long userId,String platform) throws TException {
		FinityRoleListVoRes voRes = new FinityRoleListVoRes();
		Result result = new Result();
		LogCvt.info("查询Role");
		List<FinityRoleVo> roleVoList = null;
		try{
			List<Role> roleList = bossRoleLogic.findRoleList(userId,platform);
			roleVoList = (List<FinityRoleVo>)BeanUtil.copyProperties(FinityRoleVo.class, roleList);
		}catch (FroadServerException e) {
			LogCvt.error("查询用户下角色列表getFinityRoleByUserId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			monitorService.send(MonitorPointEnum.Finity_Role_Select_Failure);
		} catch (Exception e) {
			LogCvt.error("查询用户下角色列表getFinityRoleByUserId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
			monitorService.send(MonitorPointEnum.Finity_Role_Select_Failure);
		}
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(roleVoList==null?new ArrayList<FinityRoleVo>():roleVoList);
		return voRes;
	}
	
	
	/**
	 * 用户登录时获取的所属角色
	 * @param userId 用户Id
	 * @return FinityRoleListVoRes
	 */
	@Override
	public FinityRoleListVoRes getFinityRoleByUserIdLogin(long userId) throws TException {
		FinityRoleListVoRes voRes = new FinityRoleListVoRes();
		Result result = new Result();
		LogCvt.info("用户登录时获取的所属角色");
		List<FinityRoleVo> roleVoList = null;
		try{
			List<Role> roleList = bossRoleLogic.findRoleByUserIdLogin(userId);
			roleVoList = (List<FinityRoleVo>)BeanUtil.copyProperties(FinityRoleVo.class, roleList);
		}catch (FroadServerException e) {
			LogCvt.error("用户登录时获取的所属角色getFinityRoleByUserIdLogin失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			//用户登录获取角色 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Role_Login_Failure);
		} catch (Exception e) {
			LogCvt.error("用户登录时获取的所属角色getFinityRoleByUserIdLogin异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
			//用户登录获取角色 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Role_Login_Failure);
		}
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(roleVoList==null?new ArrayList<FinityRoleVo>():roleVoList);
		return voRes;
	}
	
	
	
	public ResultVo deleteFinityRole(OriginVo originVo,String platform, long roleId) throws TException {
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除角色");
			LogUtils.addLog(originVo);
			
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(roleId)){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			//参数不全
			if(Checker.isEmpty(platform)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			Role role = new Role();
			role.setPlatform(platform);
			role.setId(roleId);
			if (!bossRoleLogic.deleteRole(role)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除角色DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除角色deleteRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除角色deleteRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	/**
	 * 角色详情
	 * @param roleId 角色Id
	 */
	@Override
	public FinityRoleListVoRes getFinityRoleById(long roleId) throws TException {
		FinityRoleListVoRes voRes = new FinityRoleListVoRes();
		Result result = new Result();
		List<FinityRoleVo> roleVoList = null;
		
		try{
			Role role = new Role();
			role.setId(roleId);
			List<Role> roleList = bossRoleLogic.findRole(role);
			roleVoList = (List<FinityRoleVo>)BeanUtil.copyProperties(FinityRoleVo.class, roleList);
		}catch (FroadServerException e) {
			LogCvt.error("查询角色详情getFinityRoleById失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("查询角色详情getFinityRoleById异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(roleVoList==null?new ArrayList<FinityRoleVo>():roleVoList);
		return voRes;
	}


}
