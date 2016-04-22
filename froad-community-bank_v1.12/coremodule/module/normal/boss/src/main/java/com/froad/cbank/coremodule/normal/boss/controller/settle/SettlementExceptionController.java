package com.froad.cbank.coremodule.normal.boss.controller.settle;

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
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettleExceptionReq;
import com.froad.cbank.coremodule.normal.boss.support.settle.SettlementExceptionSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 结算异常订单管理
 * @ClassName SettlementExceptionController
 * @author zxl
 * @date 2015年9月18日 下午2:04:19
 */
@Controller
@RequestMapping(value="/settle/ex")
public class SettlementExceptionController {
	
	@Resource
	SettlementExceptionSupport settlementExceptionSupport;
	
	/**
	 * 列表查询
	 * @tilte settlementPage
	 * @author zxl
	 * @date 2015年9月18日 下午2:08:28
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_exorder_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void settlementPage(ModelMap model, SettleExceptionReq req){	
		try {
			model.clear();
			model.putAll(settlementExceptionSupport.list(req));
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
				new RespError(model, "支付id不能为空");
				return;
			}
			
			model.putAll(settlementExceptionSupport.detail(paymentId));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 再次发起结算
	 * @tilte re
	 * @author zxl
	 * @date 2015年9月21日 上午11:34:51
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
			
			model.putAll(settlementExceptionSupport.re((String)map.get("paymentId")));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
}
