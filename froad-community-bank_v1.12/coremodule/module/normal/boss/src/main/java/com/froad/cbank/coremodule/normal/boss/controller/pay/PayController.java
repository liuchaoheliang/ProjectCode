package com.froad.cbank.coremodule.normal.boss.controller.pay;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PaymentInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.support.pay.PaySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：支付记录相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午1:59:50 
 */
@Controller
@RequestMapping("/pay")
public class PayController{
	
	@Resource
	private PaySupport paysupport;
	
	/**
	  * 方法描述：支付记录查询列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_pay_menu"})
	@RequestMapping(value = "/payList", method = RequestMethod.GET)
	public void list(ModelMap model, PaymentInfoVoReq pojo){
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("支付订单列表查询请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(paysupport.queryPaymentList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 支付订单列表导出
	 * @path /pay/payListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月31日 上午10:00:52
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_pay_export"})
	@RequestMapping(value = "/payListExport", method = RequestMethod.GET)
	public void payListExport(ModelMap model, PaymentInfoVoReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("支付订单列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(paysupport.payListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
