package com.froad.cbank.coremodule.normal.boss.controller.pay;

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
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PayExceptionReq;
import com.froad.cbank.coremodule.normal.boss.support.pay.PayExceptionSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 支付异常订单管理
 * @ClassName PayExceptionController
 * @author zxl
 * @date 2015年10月10日 上午11:07:35
 */
@Controller
@RequestMapping(value="/pay/ex")
public class PayExceptionController {
	
	@Resource
	PayExceptionSupport payExceptionSupport;
	
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
	public void list(ModelMap model, PayExceptionReq req){	
		try {
			model.clear();
			model.putAll(payExceptionSupport.list(req));
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
	public void detail(ModelMap model, String paymentId,String exceptionType){
		try {
			model.clear();
			
			if(StringUtils.isBlank(paymentId)){
				new RespError(model, "支付id不能为空");
				return;
			}
			
			if(StringUtils.isBlank(exceptionType)){
				new RespError(model, "异常类型不能为空");
				return;
			}
			
			model.putAll(payExceptionSupport.detail(paymentId,exceptionType));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 退还积分&现金
	 * @tilte returnCash
	 * @author zxl
	 * @date 2015年10月14日 上午9:52:04
	 * @param model
	 * @param paymentId
	 */
	@Auth(keys={"boss_exorder_do"})
	@RequestMapping(value = "/re", method = RequestMethod.POST)
	public void re(ModelMap model, @RequestBody Map<Object,Object> map){
		try {
			model.clear();
			
			if(StringUtils.isBlank((String)map.get("paymentId"))){
				new RespError(model, "支付id不能为空");
				return;
			}
			
			if(StringUtils.isBlank((String)map.get("exceptionType"))){
				new RespError(model, "异常类型不能为空");
				return;
			}
			
			model.putAll(payExceptionSupport.re((String)map.get("paymentId"),(String)map.get("exceptionType")));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
}
