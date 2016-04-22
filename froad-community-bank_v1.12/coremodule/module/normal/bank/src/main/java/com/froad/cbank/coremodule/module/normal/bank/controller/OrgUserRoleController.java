package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.Map;

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
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.service.OrgUserRoleBankService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrgUserRoleBankResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrgUserRoleReqVo;
import com.froad.thrift.vo.OriginVo;

/**
 * 
 * 类名: UserManagerController 
 * 描述: 银行用户管理相关业务类
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年8月3日 下午5:35:23 
 *
 */
@Controller
@RequestMapping(value = "/orgUserRole")
public class OrgUserRoleController {
	
	@Resource
	private OrgUserRoleBankService orgUserRoleBankService;
	@Resource
	private LoginService loginService;
	@Resource
	private BankOrgService bankOrgService;
	/**
	 * 
	 * 方法名称: bankOrgList 
	 * 简要描述: 银行用户列表条件查询
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月4日 上午11:18:30
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param req
	 * 方法参数: @param voReq
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"role_list_menu","role_list_select_bind"})
	@RequestMapping(value = "/lt", method = RequestMethod.POST)
	public void bankOrgList(ModelMap model, HttpServletRequest req,
			@RequestBody OrgUserRoleReqVo voReq) {
		model.clear();
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			// LogCvt.info("==========从url中获取的clientId: " + clientId
			// + "==========");
			voReq.setClientId(clientId);
			// voReq.setClientId("chongqing");
			Map<String, Object> map = orgUserRoleBankService
					.getOrgUserRoleByPage(voReq);
			model.putAll(map);
		} catch (Exception e) {
			LogCvt.info("银行用户列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}
	
	/**
	 * 
	 * 方法名称: getUserRoleDetail 
	 * 简要描述: 银行用户详情请求
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月5日 下午3:29:01
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param req
	 * 方法参数: @param id
	 * 返回类型: void
	 * @throws
	 */
	
	@CheckPermission(keys={"role_edit","role_list_edit"})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getUserRoleDetail(ModelMap model, HttpServletRequest req,
			Long id) {
		if (id == null) {
			model.clear();
			model.put("code", "608");
			model.put("message", "主键id不能为空");
			return;
		}
		try {
			OrgUserRoleBankResVo resVo=orgUserRoleBankService.getOrgUserRoleById(id);
			if(resVo!=null){
				BankOrgVo bankOrg = bankOrgService.bankOrgDetail(resVo.getClientId(), resVo.getOrgCode());
				if(bankOrg!=null){
					resVo.setProinceAgency(bankOrg.getProinceAgency());
					resVo.setProinceAgencyName(bankOrg.getProinceAgencyName());
					resVo.setCityAgency(bankOrg.getCityAgency());
					resVo.setCityAgencyName(bankOrg.getCityAgencyName());
					resVo.setCountyAgency(bankOrg.getCountyAgency());
					resVo.setCountyAgencyName(bankOrg.getCountyAgencyName()); 
				}
				if("4".equals(resVo.getOrgLevel())){//如果是三级机构
					resVo.setParentOrgCode(resVo.getCountyAgency());
				}else if("3".equals(resVo.getOrgLevel())){
					resVo.setParentOrgCode(resVo.getCityAgency());
				}else if("2".equals(resVo.getOrgLevel())){
					resVo.setParentOrgCode(resVo.getProinceAgency());
					if(StringUtil.isBlank(resVo.getProinceAgency()))
						resVo.setProinceAgency(resVo.getOrgCode());
 				}else if("1".equals(resVo.getOrgLevel())){//如果是顶级机构前端要求父级机构也相同传入处理
					resVo.setParentOrgCode(resVo.getOrgCode());
					if(StringUtil.isBlank(resVo.getProinceAgency()))
						resVo.setProinceAgency(resVo.getOrgCode());
				}
			}  
			model.addAttribute(resVo);
		} catch (Exception e) {
			LogCvt.info("银行用户详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}
	
	/**
	 * 
	 * 方法名称: updateOrgUserRole 
	 * 简要描述: 修改银行用户接口
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月5日 下午3:36:30
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param req
	 * 方法参数: @param resVo
	 * 返回类型: void
	 * @throws
	 */
	@CheckPermission(keys={"role_edit_update"})
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void updateOrgUserRole(ModelMap model, HttpServletRequest req,
			@RequestBody OrgUserRoleReqVo resVo) {
		// 校验请求参数
		verify(model, resVo);
		try {
			OriginVo originVo = loginService.getOriginVo(req);
			model.putAll(orgUserRoleBankService.updateOrgUserRole(resVo,
					originVo));
		} catch (Exception e) {
			LogCvt.info("银行用户修改异常" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * 方法名称: verify 
	 * 简要描述: 参数校验 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月6日 上午11:24:46
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param model
	 * 方法参数: @param resVo
	 * 返回类型: void
	 * @throws
	 */
	private void verify(ModelMap model, OrgUserRoleReqVo resVo) {
		Long id = resVo.getId();
		if (id == null) {
			model.clear();
			model.put("code", "608");
			model.put("message", "主键id不能为空");
			return;
		}
		if (StringUtil.isEmpty(resVo.getRoleName())) {
			model.clear();
			model.put("code", "608");
			model.put("message", "角色名称不能为空");
			return;
		}
		if (resVo.getRoleId() == null) {
			model.clear();
			model.put("code", "608");
			model.put("message", "角色id不能为空");
			return;
		}
	}
	
}
