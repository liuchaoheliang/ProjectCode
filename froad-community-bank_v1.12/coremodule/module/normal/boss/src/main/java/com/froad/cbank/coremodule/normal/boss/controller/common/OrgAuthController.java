package com.froad.cbank.coremodule.normal.boss.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.support.common.OrgAuthSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 数据权限控制
 * @ClassName OrgAuthController
 * @author zxl
 * @date 2016年1月18日 下午5:40:09
 */
@Controller
@RequestMapping(value = "/orgAuth")
public class OrgAuthController {
	
	@Resource
	OrgAuthSupport orgAuthSupport;
	
	/**
	 * 获取有数据权限银行
	 * @tilte bank
	 * @author zxl
	 * @date 2016年1月18日 下午6:13:06
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/bank", method = RequestMethod.GET)
	public void bank(ModelMap model, HttpServletRequest request) {
		try{
			BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
			model.put("clientList", orgAuthSupport.getBank(user.getId()));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model, "获取所属银行失败!");
		}
	}
	
	/**
	 * 获取数据权限机构
	 * @tilte org
	 * @author zxl
	 * @date 2016年1月18日 下午6:13:14
	 * @param model
	 * @param request
	 * @param clientId
	 */
	@RequestMapping(value = "/org", method = RequestMethod.GET)
	public void org(ModelMap model, HttpServletRequest request,String clientId) {
		try{
			BossUser user = (BossUser)request.getAttribute(Constants.BOSS_USER);
			model.put("orgList", orgAuthSupport.getOrg(user.getId(),clientId));
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model, "获取所属组织失败!");
		}
	}
	
}
