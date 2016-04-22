package com.froad.cbank.coremodule.normal.boss.controller.refund;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.ValidBeanField;
import com.froad.cbank.coremodule.framework.common.valid.ValidException;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundListReq;
import com.froad.cbank.coremodule.normal.boss.support.refund.RefunSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：退款相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午1:36:16 
 */
@Controller
@RequestMapping("/refund")
public class RefundController{
	
	@Resource
	RefunSupport refunSupport;	
	
	/**
	  * 方法描述：退款列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_refund_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, RefundListReq pojo){
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("退款订单列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			pojo.setSource(StringUtil.isBlank(pojo.getSource()) ? "2" : pojo.getSource());
			model.putAll(refunSupport.queryRefundList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	  * 方法描述：退款详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:44:01
	  */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model, RefundDetailReq pojo){
		try {
			ValidBeanField.valid(pojo);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("退款订单详情请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(refunSupport.detail(pojo));
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
	 * 退款订单列表导出
	 * @path /refund/refundListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午3:05:23
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_refund_export"})
	@RequestMapping(value = "/refundListExport", method = RequestMethod.GET)
	public void refundListExport(ModelMap model, RefundListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("退款订单列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			pojo.setSource(StringUtil.isBlank(pojo.getSource()) ? "2" : pojo.getSource());
			model.putAll(refunSupport.refundListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
