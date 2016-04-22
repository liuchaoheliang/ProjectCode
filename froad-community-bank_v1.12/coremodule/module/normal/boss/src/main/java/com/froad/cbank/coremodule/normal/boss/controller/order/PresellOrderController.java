package com.froad.cbank.coremodule.normal.boss.controller.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.PresellOrderReq;
import com.froad.cbank.coremodule.normal.boss.support.order.PresellOrderSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 预售订单
 * @ClassName PresellOrderController
 * @author zxl
 * @date 2015年8月20日 上午11:15:15
 */
@Controller
@RequestMapping("/presellorder")
public class PresellOrderController {
	
	@Resource
	PresellOrderSupport presellOrderSupport;

	/**
	  * 方法描述：分页查询预售交易账单
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: liaopeixin
	  * @time: 2015年8月20日 下午2:42:15
	  */
	@Auth(keys={"boss_presell_bill_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void presellBillList(ModelMap model, PresellOrderReq voReq){
		LogCvt.info("预售交易账单查询条件：" + JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(presellOrderSupport.querypresellBillList(voReq));
		} catch (Exception e) {
			LogCvt.error("预售交易账单查询请求异常" + e.getMessage(), e);
			new RespError(model, "预售交易账单查询失败!!!");
		}	
	}
	
	/**
	 * 预售账单导出
	 * @path /presellorder/export
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午1:23:12
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_presell_bill_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportPresellBill(ModelMap model, PresellOrderReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("预售账单导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(presellOrderSupport.querypresellBillListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
