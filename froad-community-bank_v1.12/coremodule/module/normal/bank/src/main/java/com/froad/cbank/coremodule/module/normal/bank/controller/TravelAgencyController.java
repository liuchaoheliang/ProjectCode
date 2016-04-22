package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.List;

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
import com.froad.cbank.coremodule.module.normal.bank.service.OperatorService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVoReq;

/**
 * 法人行社管理
 * @author yfy
 * @date 2015-3-20 下午13:36:28
 */
@Controller
@RequestMapping(value = "/travelAgency")
public class TravelAgencyController extends BasicSpringController{

	@Resource
	private BankOrgService bankOrgService;
	@Resource
	private OperatorService operatorService;
	@Resource
	private LoginService loginService;
	/**
	 * @Title: 获取法人行社
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"legal_manage_menu","legal_manage_mod"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	 public void selectTwoOrgCode(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getOrgCode())){
				List<BankOrgVo> bankOrgList = operatorService.selectTwoOrgCode(voReq.getClientId(),voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			}else{
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "参数不全");
			}	
		} catch (Exception e) {
			LogCvt.info("银行法人行社机构查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", "银行法人行社机构查询异常");
		}
		
	}
	
	/**
	 * @Title: 获取法人行社(只展示新增部分)
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/nlt", method = RequestMethod.POST)
	public void queryTwoOrgCode(ModelMap model, HttpServletRequest req,
			@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())) {
				List<BankOrgVo> bankOrgList = operatorService.queryTwoOrgCode(
						voReq.getClientId(), voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			} else {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.info("银行法人行社机构查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", "银行法人行社机构查询异常");
		}

	}

	/**
	 * @Title: 获取已生成的法人行社
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"legal_manage_menu","legal_manage_mod"})
	@RequestMapping(value = "/olt",method = RequestMethod.POST)
	 public void selectTwoOverOrgCode(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getOrgCode())){
				List<BankOrgVo> bankOrgList = operatorService.selectTwoOverOrgCode(voReq.getClientId(),voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.info("银行法人行社机构查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 法人行社批量生成
	 * @author yfy
	 * @date 2015-3-20 下午13:58:13
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"legal_manage_add"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
    public void operatorAddAll(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq){
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getOrgCodeList() != null && voReq.getOrgCodeList().size() > 0 
					&& StringUtil.isNotEmpty(voReq.getUserPassword())){
				MsgVo msgVo = operatorService.operatorAddAll(voReq.getClientId(),
						voReq.getOrgCodeList(),voReq.getUserPassword(), voReq.getPrefix(),
						loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.info("批量生产管理员"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * * @Title: 批量重置密码
	 * @author yfy
	 * @date  2015-3-20 下午14:33:42
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"legal_manage_mod_it"})
	@RequestMapping(value = "/it",method = RequestMethod.POST)
    public void operatorLogPasswordUpdate(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		 
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getOrgCodeList() != null && voReq.getOrgCodeList().size() > 0 
					&& StringUtil.isNotEmpty(voReq.getUserPassword())){
				MsgVo msgVo = operatorService.operatorLogPasswordUpdate(voReq.getClientId(),
						voReq.getOrgCodeList(),voReq.getUserPassword(),
						loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.info("批量重置密码"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * * @Title: 批量删除
	 * @author yfy
	 * @date  2015-3-20 下午14:33:42
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"legal_manage_mod_de"})
	@RequestMapping(value = "/de",method = RequestMethod.POST)
    public void operatorDelete(ModelMap model,HttpServletRequest req,@RequestBody OperatorVoReq voReq) {
		 
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getOrgCodeList() != null && voReq.getOrgCodeList().size() > 0 ){
				MsgVo msgVo = operatorService.operatorDelete(voReq.getClientId(),
						voReq.getOrgCodeList(), loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}
		} catch (Exception e) {
			LogCvt.info("批量删除法人行社管理员"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
}
