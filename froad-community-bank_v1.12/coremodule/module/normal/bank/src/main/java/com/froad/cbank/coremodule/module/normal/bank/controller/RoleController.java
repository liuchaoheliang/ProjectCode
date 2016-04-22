package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.FinitRoleService;
import com.froad.cbank.coremodule.module.normal.bank.service.OperatorService;
import com.froad.cbank.coremodule.module.normal.bank.service.RoleService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleVo;

/**
 * 角色管理
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BasicSpringController {

	@Resource
	private RoleService rService;
	@Resource
	private FinitRoleService finitRoleService;
	@Resource 
	private OperatorService operatorService;
	/**
	 * @Title: 角色信息列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"role_manage_menu","role_new_lt_menu"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	public void roleListByPage(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.findPageByConditions(role));
		} catch (TException e) {
			LogCvt.info("角色列表查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 角色信息列表查询(非分页)
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"role_list_edit","role_edit","add_ope","mod_ope","organizationmnage_user_menu","role_new_lt_menu","organizationmnage_right_ad_user","organizationmnage_role_menu"})
	@RequestMapping(value = "/alt",method = RequestMethod.POST)
	public void roleAllList(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.getRoleList(role));
		} catch (TException e) {
			LogCvt.info("获取所有角色信息查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 角色信息新增
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"add_role_ad","role_new_lt_sure","role_new_lt_ad","role_new_lt_sure_add"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
	public void roleAdd(ModelMap model,HttpServletRequest req,@RequestBody RoleVo role){
		try {
			if(role==null || role.getResourceList()==null || role.getResourceList().size()<1){
				model.clear();
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", "请选择角色对应资源信息");
			}else{
				role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
				model.putAll(finitRoleService.roleAdd(role,req));
			}
		} catch (TException e) {
			LogCvt.info("角色新增"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 角色信息修改
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"mod_role_ue","role_new_lt_sure","role_new_lt_sure_ue"})
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	public void roleUpdate(ModelMap model,HttpServletRequest req,@RequestBody RoleVo role){
		try {
			if(role==null || role.getResourceList()==null || role.getResourceList().size()<1){
				model.clear();
				model.put("code", EnumTypes.illlegal.getCode());
				model.put("message", "请选择角色对应资源信息");
			}else{
				role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
				model.putAll(finitRoleService.roleUpdate(role,req));
			}
		} catch (TException e) {
			LogCvt.info("角色修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 角色信息查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */	
	@CheckPermission(keys={"mod_role","role_new_lt_menu","role_new_lt_sure"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
	public void roleDetail(ModelMap model,HttpServletRequest req,String roleId,RoleReq role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.roleDetail(role));
		} catch (TException e) {
			LogCvt.info("角色详情查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 角色信息删除
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"role_manage_de","role_new_lt_de"})
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
	public void roleDelete(ModelMap model,HttpServletRequest req,String roleId,RoleReq role){
		try {
			role.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			model.putAll(rService.roleDelete(role,req));
		} catch (TException e) {
			LogCvt.info("角色删除"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 获取全部资源（根据角色获取）
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"mod_role","add_role","role_new_lt_menu","role_new_lt_sure"})
	@RequestMapping(value = "rlt",method = RequestMethod.POST)
	public void resourceList(ModelMap model,HttpServletRequest req,@RequestBody RoleReq role){
		try {
			String clientId=req.getAttribute(Constants.CLIENT_ID)+"";
			if(role!=null && role.getRoleId()==null){//如果未传入角色id，则获取当前用户角色信息
				String userId = req.getHeader("userId");
				OperatorVo operator=operatorService.operatorDetail(clientId, Long.valueOf(userId));
				role.setRoleId(operator.getRoleId());
  			}
			role.setClientId(clientId);
			model.putAll(finitRoleService.getResourceList(role));
		} catch (TException e) {
			LogCvt.info("获取资源"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
}
