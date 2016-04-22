package com.froad.cbank.coremodule.normal.boss.controller.refund;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundExceptionReq;
import com.froad.cbank.coremodule.normal.boss.support.refund.RefundExceptionSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 退款异常订单管理
 * @ClassName RefundExceptionController
 * @author zxl
 * @date 2015年10月10日 上午11:07:35
 */
@Controller
@RequestMapping(value="/refund/ex")
public class RefundExceptionController {
	
	@Resource
	RefundExceptionSupport refundExceptionSupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年9月18日 下午2:08:28
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_exorder_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, RefundExceptionReq req){	
		try {
			model.clear();
			model.putAll(refundExceptionSupport.list(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	
	/**
	 * 详情
	 * @tilte detail
	 * @author zxl
	 * @date 2015年9月21日 上午10:32:12
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_exorder_menu"})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model, String paymentId){
		try {
			model.clear();
			
			if(StringUtils.isBlank(paymentId)){
				new RespError(model, "id不能为空");
				return;
			}
			
			model.putAll(refundExceptionSupport.detail(paymentId));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 再次退款
	 * @tilte re
	 * @author zxl
	 * @date 2015年10月12日 上午10:26:34
	 * @param model
	 * @param map
	 */
	@Auth(keys={"boss_exorder_do"})
	@RequestMapping(value = "/re", method = RequestMethod.POST)
	public void re(ModelMap model, @RequestBody Map<Object,Object> map){
		try {
			model.clear();
			if(StringUtils.isBlank((String)map.get("refundId"))){
				new RespError(model, "id不能为空");
				return;
			}
			model.putAll(refundExceptionSupport.re((String)map.get("refundId")));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
}
