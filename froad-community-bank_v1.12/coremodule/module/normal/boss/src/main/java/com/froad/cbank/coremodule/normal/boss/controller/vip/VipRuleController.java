package com.froad.cbank.coremodule.normal.boss.controller.vip;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductDelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BindProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.BoundProductReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleStatReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRuleUpdReq;
import com.froad.cbank.coremodule.normal.boss.support.vip.VipRuleSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月17日 上午11:40:56
 * @version 1.0
 * @desc VIP规则
 */
@Controller
@RequestMapping(value = "/viprule")
public class VipRuleController {

	@Resource
	private VipRuleSupport vipRuleSupport;
	
	/**
	 * @desc 获取VIP规则列表
	 * @path /viprule/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:00:19
	 * @param clientId（客户端ID）
	 * @param status（状态：0-未生效 | 1-已生效 | 2-已作废）
	 * @param name（规则名称）
	 */
	@Auth(keys={"boss_viprole_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getRuleList(ModelMap model, VipRuleListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.getRuleList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 获取VIP规则详情
	 * @path /viprule/detail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:00:42
	 * @param vipId
	 * @return model
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getRuleDetail(ModelMap model, String vipId) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则详情请求参数：" + JSON.toJSONString(vipId));
			}
			model.clear();
			if(StringUtil.isNotBlank(vipId)) {
				model.putAll(vipRuleSupport.getRuleDetail(vipId));
			} else {
				throw new BossException("VIP规则ID为空");
			}
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 新增VIP规则
	 * @path /viprule/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:02:04
	 * @param pojo
	 * @return model
	 */
	@Auth(keys={"boss_viprole_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addRule(ModelMap model, HttpServletRequest req, @RequestBody VipRuleAddReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则新增请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(pojo.getVipPrice() < 0) {
				throw new BossException("请传入有效的VIP价格");
			}
			model.putAll(vipRuleSupport.addRule(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 修改VIP规则
	 * @path /viprule/upd
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:02:46
	 * @param 
	 * @return 
	 */
	@Auth(keys={"boss_viprole_modify"})
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	public void updateRule(ModelMap model, HttpServletRequest req, @RequestBody VipRuleUpdReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则修改请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.updateRule(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * VIP规则删除
	 * @path /viprule/del/{vipId}
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月23日 上午11:32:27
	 * @param model
	 * @param vipId
	 */
	@Auth(keys={"boss_viprole_delete"})
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public void deleteRule(ModelMap model, HttpServletRequest req, String vipId) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则删除请求参数：" + JSON.toJSONString(vipId));
			}
			model.clear();
			if(StringUtils.isNotBlank(vipId)) {
				model.putAll(vipRuleSupport.deleteRule(vipId, org));
			} else {
				throw new BossException("VIP规则ID为空");
			}
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 修改VIP规则状态（"启用/作废"）
	 * @path /viprule/stat
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:04:08
	 * @param 
	 * @return 
	 */
	@Auth(keys={"boss_viprole_enable","boss_viprole_disable"})
	@RequestMapping(value = "/stat", method = RequestMethod.POST)
	public void changeStatus(ModelMap model, HttpServletRequest req, @RequestBody VipRuleStatReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则状态变更请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.changeStatus(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 获取已绑定商品列表
	 * @path /viprule/pro/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月23日 下午2:42:12
	 * @param model
	 */
	@RequestMapping(value = "/pro/list", method = RequestMethod.GET)
	public void getBoundProductList(ModelMap model, BoundProductReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则已绑定商品列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.getBoundProductList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 获取可绑定商品列表
	 * @path /viprule/unpro/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:05:17
	 * @param pojo
	 * @return model
	 */
	@RequestMapping(value = "/unpro/list", method = RequestMethod.GET)
	public void getBindProductList(ModelMap model, BindProductReq pojo) {
		try {
			ValidBeanField.valid(pojo);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP规则可绑定商品列表请求参数：" + JSON.toJSONString(pojo));
			}
			if(pojo.getPriceStart() < 0 || pojo.getPriceEnd() < 0 || (pojo.getPriceStart() > 0 && pojo.getPriceEnd() > 0 && pojo.getPriceStart() > pojo.getPriceEnd())) {
				throw new BossException("请传入有效的价格筛选值");
			}
			model.clear();
			model.putAll(vipRuleSupport.getBindProductList(pojo));
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
	 * @desc 新增VIP商品
	 * @path /viprule/pro/add
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:07:44
	 * @param pojo
	 * @return model
	 */
	@RequestMapping(value = "/pro/add", method = RequestMethod.POST)
	public void addBindProduct(ModelMap model, HttpServletRequest req, @RequestBody BindProductAddReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP商品新增请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.addBindProduct(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 删除VIP商品
	 * @path /viprule/pro/del
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午4:09:31
	 * @param pojo
	 * @return model
	 */
	@RequestMapping(value = "/pro/del", method = RequestMethod.POST)
	public void deleteBindProduct(ModelMap model, HttpServletRequest req, @RequestBody BindProductDelReq pojo) {
		try {
			OriginVo org = (OriginVo) req.getAttribute(Constants.ORIGIN);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP商品删除请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipRuleSupport.deleteBindProduct(pojo, org));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
