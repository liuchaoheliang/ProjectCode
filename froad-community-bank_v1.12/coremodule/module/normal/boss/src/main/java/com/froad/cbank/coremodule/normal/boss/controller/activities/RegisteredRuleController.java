package com.froad.cbank.coremodule.normal.boss.controller.activities;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.ValidBeanField;
import com.froad.cbank.coremodule.framework.common.valid.ValidException;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleDisReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.RegisteredRuleUpdReq;
import com.froad.cbank.coremodule.normal.boss.support.activities.RegisteredRuleSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * 注册规则
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月2日 上午10:09:07
 */
@Controller
@RequestMapping(value = "/registered")
public class RegisteredRuleController {

	@Resource
	private RegisteredRuleSupport registerRuleSupport;
	
	/**
	 * 获取注册促销规则列表
	 * @path /registered/list
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:03:23
	 * @param model
	 */
	@Auth(keys={"boss_register_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void queryRegisteredRuleList(ModelMap model, RegisteredRuleListReq pojo) {
		try {
			ValidBeanField.valid(pojo);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("注册促销列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(registerRuleSupport.queryRegisteredRuleList(pojo));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取注册促销规则详情
	 * @path /registered/detail
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:04:19
	 * @param model
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void queryRegisteredRuleDetail(ModelMap model, String clientId, String activeId) {
		try {
			if(LogCvt.isDebugEnabled()){
				HashMap<String, String> pojo = new HashMap<String, String>();
				pojo.put("clientId", clientId);
				pojo.put("activeId", activeId);
				LogCvt.debug("注册促销详情请求参数：" + JSON.toJSONString(pojo));
			}
			if(StringUtil.isBlank(activeId)) {
				throw new BossException("注册规则ID为空");
			}
			model.clear();
			model.putAll(registerRuleSupport.queryRegisteredRuleDetail(clientId, activeId));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 新增注册促销规则
	 * @path /registered/add
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:02:19
	 * @param model
	 */
	@Auth(keys={"boss_register_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addRegisteredRule(ModelMap model, HttpServletRequest req, @RequestBody RegisteredRuleAddReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("注册促销新增请求参数：" + JSON.toJSONString(pojo));
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			
			if(pojo.getPerBankIntegral() == null) {
				pojo.setPerBankIntegral(0);
			}
			if(pojo.getTotalBankIntegral() == null) {
				pojo.setTotalBankIntegral(0);
			}
			if(pojo.getPerUnionIntegral() == null) {
				pojo.setPerUnionIntegral(0);
			}
			if(pojo.getTotalUnionIntegral() == null) {
				pojo.setTotalUnionIntegral(0);
			}
			if("2".equals(pojo.getAwardType())) {//红包
				if(StringUtils.isBlank(pojo.getVouchersActiveId())) {
					throw new BossException("红包规则ID为空");
				}
			}
			if("4".equals(pojo.getAwardType())) {//联盟积分
				if(pojo.getPerUnionIntegral() <= 0) {
					throw new BossException("联盟积分需为大于0的整数");
				}
				if(pojo.getTotalUnionIntegral() < 0) {
					throw new BossException("联盟积分总额需为大于等于0的整数");
				}
				if(pojo.getTotalUnionIntegral() > 0 && (pojo.getTotalUnionIntegral() % pojo.getPerUnionIntegral() != 0)) {
					throw new BossException("联盟积分总额不为0的情况下，需为联盟积分的整倍数");
				}
			}
			if(pojo.getIsAwardCre() == null) {
				throw new BossException("是否奖励推荐人为空");
			}
			if(pojo.getTriggerType() == null) {
				throw new BossException("触发方式为空");
			}
			model.clear();
			model.putAll(registerRuleSupport.addRegisteredRule(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 修改注册促销规则
	 * @path /registered/update
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:05:09
	 * @param model
	 */
	@Auth(keys={"boss_register_modify"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateRegisteredRule(ModelMap model, HttpServletRequest req, @RequestBody RegisteredRuleUpdReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("注册促销修改请求参数：" + JSON.toJSONString(pojo));
			}
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			
			if(pojo.getPerBankIntegral() == null) {
				pojo.setPerBankIntegral(0);
			}
			if(pojo.getTotalBankIntegral() == null) {
				pojo.setTotalBankIntegral(0);
			}
			if(pojo.getPerUnionIntegral() == null) {
				pojo.setPerUnionIntegral(0);
			}
			if(pojo.getTotalUnionIntegral() == null) {
				pojo.setTotalUnionIntegral(0);
			}
			if("2".equals(pojo.getAwardType())) {//红包
				if(StringUtils.isBlank(pojo.getVouchersActiveId())) {
					throw new BossException("红包规则ID为空");
				}
			}
			if("4".equals(pojo.getAwardType())) {//联盟积分
				if(pojo.getPerUnionIntegral() <= 0) {
					throw new BossException("联盟积分需为大于0的整数");
				}
				if(pojo.getTotalUnionIntegral() < 0) {
					throw new BossException("联盟积分总额需为大于等于0的整数");
				}
				if(pojo.getTotalUnionIntegral() > 0 && (pojo.getTotalUnionIntegral() % pojo.getPerUnionIntegral() != 0)) {
					throw new BossException("联盟积分总额不为0的情况下，需为联盟积分的整倍数");
				}
			}
			if(pojo.getIsAwardCre() == null) {
				throw new BossException("是否奖励推荐人为空");
			}
			if(pojo.getTriggerType() == null) {
				throw new BossException("触发方式为空");
			}
			model.clear();
			model.putAll(registerRuleSupport.updateRegisteredRule(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 禁用注册促销规则
	 * @path /registered/disable
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:06:04
	 * @param model
	 */
	@Auth(keys={"boss_register_disable"})
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	public void disableRegisteredRule(ModelMap model, HttpServletRequest req, @RequestBody RegisteredRuleDisReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("注册促销禁用请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(registerRuleSupport.disableRegisteredRule(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 注册促销规则 下载明细
	 * @path /registered/detail
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年12月3日 上午11:04:19
	 * @param model
	 */
	@Auth(keys={"boss_register_export"})
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadRegisteredRule(ModelMap model, String clientId, String activeId, String triggerTypeTip) {
		try {
			if(LogCvt.isDebugEnabled()){
				HashMap<String, String> pojo = new HashMap<String, String>();
				pojo.put("clientId", clientId);
				pojo.put("activeId", activeId);
				pojo.put("triggerTypeTip", triggerTypeTip);
				LogCvt.debug("注册促销规则下载请求参数：" + JSON.toJSONString(pojo));
			}
			if(StringUtil.isBlank(activeId)) {
				throw new BossException("注册规则ID为空");
			}
			model.clear();
			model.putAll(registerRuleSupport.downloadRegisteredRule(clientId, activeId ,triggerTypeTip));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
