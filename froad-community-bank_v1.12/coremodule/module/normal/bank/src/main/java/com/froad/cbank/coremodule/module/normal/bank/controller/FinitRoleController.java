package com.froad.cbank.coremodule.module.normal.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;

/**
 * 角色管理
 * @author ylchu
 *
 * 此controller作废 by 刘黄乐 20151012
 */
@Controller
@RequestMapping(value = "/finitrole")
public class FinitRoleController extends BasicSpringController {

//	@Resource
//	private FinitRoleService rService;
	/**
	 * @Title: 角色信息列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
//	@RequestMapping(value = "/lt",method = RequestMethod.POST)
//	public void roleListByPage(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
//		try {
//			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
//			model.putAll(rService.findPageByConditions(role));
//		} catch (TException e) {
//			LogCvt.info("角色列表查询"+e.getMessage(), e);
//			model.clear();
//			model.put("code", EnumTypes.thrift_err.getCode());
//			model.put("message", EnumTypes.thrift_err.getMessage());
//		}
//	}
	
	/**
	 * @Title: 角色信息列表查询(非分页)
	 * @author ylchu
	 * @param model
	 * @param req
	 */
//	@RequestMapping(value = "/alt",method = RequestMethod.POST)
//	public void roleAllList(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
//		try {
//			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
//			model.putAll(rService.getRoleList(role));
//		} catch (TException e) {
//			LogCvt.info("获取所有角色信息查询"+e.getMessage(), e);
//			model.clear();
//			model.put("code", EnumTypes.thrift_err.getCode());
//			model.put("message", EnumTypes.thrift_err.getMessage());
//		}
//	}
	
	/**
	 * @Title: 角色信息新增
	 * @author ylchu
	 * @param model
	 * @param req
	 */
/*	@RequestMapping(value = "/ad",method = RequestMethod.POST)
	public void roleAdd(ModelMap model,HttpServletRequest req,@RequestBody RoleVo role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.roleAdd(role,req));
		} catch (TException e) {
			LogCvt.info("角色新增"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}*/
	
	/**
	 * @Title: 角色信息修改
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	/*@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	public void roleUpdate(ModelMap model,HttpServletRequest req,@RequestBody RoleVo role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.roleUpdate(role,req));
		} catch (TException e) {
			LogCvt.info("角色修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}*/
	
	/**
	 * @Title: 角色信息查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */	
//	@RequestMapping(value = "/dl",method = RequestMethod.GET)
//	public void roleDetail(ModelMap model,HttpServletRequest req,String roleId,RoleReq role){
//		try {
//			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
//			model.putAll(rService.roleDetail(role));
//		} catch (TException e) {
//			LogCvt.info("角色详情查询"+e.getMessage(), e);
//			model.clear();
//			model.put("code", EnumTypes.thrift_err.getCode());
//			model.put("message", EnumTypes.thrift_err.getMessage());
//		}
//	}

	/**
	 * @Title: 角色信息删除
	 * @author ylchu
	 * @param model
	 * @param req
	 */
//	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
//	public void roleDelete(ModelMap model,HttpServletRequest req,String roleId,RoleReq role){
//		try {
//			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
//			model.putAll(rService.roleDelete(role,req));
//		} catch (TException e) {
//			LogCvt.info("角色删除"+e.getMessage(), e);
//			model.clear();
//			model.put("code", EnumTypes.thrift_err.getCode());
//			model.put("message", EnumTypes.thrift_err.getMessage());
//		}
//	}
	
	/**
	 * 获取全部资源
	 * @param model
	 * @param req
	 */
/*	@RequestMapping(value = "rlt",method = RequestMethod.POST)
	public void resourceList(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.getResourceList(role));
		} catch (TException e) {
			LogCvt.info("获取资源"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}*/
}
