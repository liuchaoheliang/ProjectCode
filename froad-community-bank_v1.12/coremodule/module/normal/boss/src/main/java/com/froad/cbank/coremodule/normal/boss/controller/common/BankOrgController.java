package com.froad.cbank.coremodule.normal.boss.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.common.BankOrgVo;
import com.froad.cbank.coremodule.normal.boss.support.common.BankOrgSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 获取机构
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/bankOrg")
public class BankOrgController {

	@Resource
	private BankOrgSupport bankOrgSupport;
	/**
	 * @Title: 当前机构获取下级机构（联动查询）
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/sc",method = RequestMethod.GET)
	 public void selectOrgCode(ModelMap model,HttpServletRequest req,String clientId,String orgCode,String orgLevel) {
		LogCvt.info("机构联动查询请求参数：[clientId]:"+clientId+",[orgCode] :"+orgCode+",[orgLevel] :"+orgLevel);
		try {
			List<BankOrgVo> bankOrgList = bankOrgSupport.selectOrgCode(clientId,orgCode,orgLevel);
			model.put("bankOrgList", bankOrgList);
		} catch (Exception e) {
			LogCvt.error("机构联动查询请求异常"+e.getMessage(), e);
			new RespError(model, "查询活动列表失败!!!");
		}
		
	}
}
