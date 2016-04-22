package com.froad.cbank.coremodule.module.normal.bank.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.service.OperatorLogService;
import com.froad.cbank.coremodule.module.normal.bank.service.OperatorService;
import com.froad.cbank.coremodule.module.normal.bank.service.RoleService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVoRes;

/**
 * 银行管理员
 * @author yfy
 * @date 2015-3-19 下午17:51:28
 */
@Controller
@RequestMapping(value = "/operator")
public class OperatorController extends BasicSpringController{
	
	@Resource
	private OperatorService operatorService;
	@Resource 
	private BankOrgService bankOrgService;
	@Resource
	private RoleService roleService;
	@Resource
	private OperatorLogService operatorLogService;
	@Resource
	private LoginService loginService;
	
	/**
	 * @Title: 银行管理员列表条件查询
	 * @author yfy
	 * @date 2015-3-20 上午9:42:28
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"organizationmnage_right_ad_user","operation_manage_menu","operation_manage_select_bind","organizationmnage_role_menu","organizationmnage_sure","organizationmnage_user_menu","organizationmnage_user_lt_bind","organizationmnage_sure_lt_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void operatorList(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			QueryResult<OperatorVoRes> queryVo = operatorService.findPageByConditions(voReq);

			model.put("operatorList", queryVo.getResult());
			model.put("totalCount", queryVo.getTotalCount());
			model.put("pageCount", queryVo.getPageCount());
			model.put("pageNumber", queryVo.getPageNumber());//总页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "银行管理员列表条件查询成功");	
			
		} catch (Exception e) {
			LogCvt.info("银行管理员列表条件查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	/**
	 * @Title: 银行管理员新增
	 * @author yfy
	 * @date 2015-3-20 上午9:42:28
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"add_ope_ad","organizationmnage_user_sure","organizationmnage_user_rp","organizationmnage_user_sure_add","organizationmnage_right_ad_user"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
    public void operatorAdd(ModelMap model,HttpServletRequest req,@RequestBody OperatorVo oper){
		try {
			oper.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = operatorService.verify(oper);
			if(msgVo.getResult() && StringUtil.isNotEmpty(oper.getLoginName())){
				msgVo = operatorService.operatorAdd(oper,loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("id", msgVo.getId());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				if(msgVo.getResult()){
					model.put("message", "操作员ID不为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
			
		} catch (Exception e) {
			LogCvt.info("银行管理新增"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 银行管理员修改
	 * @author yfy
	 * @date 2015-3-20 上午9:50:24
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"mod_ope_ue","organizationmnage_user_sure","organizationmnage_user_sure_ue"})
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	 public void operatorUpdate(ModelMap model,HttpServletRequest req,@RequestBody OperatorVo oper) {
		try {
			oper.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = operatorService.verify(oper);
			if(msgVo.getResult() && oper.getOperatorId() != null){
				msgVo = operatorService.operatorUpdate(oper,loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				if(msgVo.getResult()){
					model.put("message", "操作员ID不为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 银行管理员禁用/启用
	 * @author yfy
	 * @date 2015-3-20 上午10:03:20
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"operation_manage_it_down","operation_manage_it_up"})
	@RequestMapping(value = "/it",method = RequestMethod.POST)
    public void operatorIsEnable(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getOperatorId() != null && voReq.getState() != null && voReq.getOrgCode() != null){
				MsgVo msgVo = operatorService.operatorIsEnable(voReq.getClientId(),voReq.getOrgCode(),
						voReq.getOperatorId(), voReq.getState(),loginService.getOriginVo(req));	
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else if(!msgVo.getResult()){
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				if(voReq.getOperatorId() == null){
					model.put("message", "操作员ID不能为空");	
				}
				if(voReq.getState() == null){
					model.put("message", "禁/启用状态不能为空");
				}
				if(voReq.getOrgCode() == null){
					model.put("message", "组织编码不能为空");
				}
				model.put("code", EnumTypes.empty.getCode());
				
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员"+(voReq.getState()?"启用":"禁用")+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 银行管理员重置密码
	 * @author yfy
	 * @date 2015-3-20 上午10:03:20
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"organizationmnage_right_rp_user","operation_manage_rp","organizationmnage_rp","organizationmnage_user_de"})
	@RequestMapping(value = "/rp",method = RequestMethod.POST)
    public void operatorResetPassword(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getOperatorId() != null ){
				MsgVo msgVo = operatorService.operatorResetPassword(voReq.getClientId(),
						voReq.getOperatorId(), voReq.getUserPassword(),loginService.getOriginVo(req));	
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else if(!msgVo.getResult()){
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "操作员ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员密码重置"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 银行管理员详情 
	 * @author yfy
	 * @date 2015-3-20 上午10:12:20
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"mod_ope","organizationmnage_user_menu","organizationmnage_role_menu"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void operatorDetail(ModelMap model,HttpServletRequest req,Long operatorId) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(operatorId != null){
				OperatorVo operator = operatorService.operatorDetail(clientId,operatorId);
				if(operator != null){
					model.put("operator", operator);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "操作员ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员详情查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 银行管理员删除
	 * @author yfy
	 * @date 2015-3-20 上午9:33:20
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"operation_manage_de","organizationmnage_de","organizationmnage_right_de_user"})
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
    public void operatorDelete(ModelMap model,HttpServletRequest req,Long operatorId) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(operatorId != null){
				MsgVo msgVo = operatorService.operatorDelete(clientId,operatorId,
						loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "操作员ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员删除"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
}
