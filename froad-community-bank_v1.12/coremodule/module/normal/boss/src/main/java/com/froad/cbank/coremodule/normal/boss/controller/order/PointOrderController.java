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
import com.froad.cbank.coremodule.normal.boss.pojo.order.PointOrderReq;
import com.froad.cbank.coremodule.normal.boss.support.order.PointOrderSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述:boss线上积分兑换查询
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author "chenzhangwei"
 * @time 2015年8月20日 上午11:37:33
 * @email "chenzhangwei@f-road.com.cn"
 */
@Controller
@RequestMapping("/pointorder")
public class PointOrderController {
	
	@Resource
	private PointOrderSupport pointOrderSupport;

	/**
	 * 方法描述:分页查询boss线上积分兑换订单列表
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年8月20日 上午11:43:18
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_order_point_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void pointOrderListPage(ModelMap model, PointOrderReq voReq) {
		LogCvt.info("分页查询积分兑换条件：" + JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(pointOrderSupport.queryPonitOrderByPage(voReq));
		} catch (Exception e) {
			LogCvt.error("积分兑换订单列表查询请求异常" + e.getMessage(), e);
			new RespError(model, "积分兑换订单列表查询失败!!!");
		}
	}

	/**
	 * 线上积分兑换列表导出
	 * @path /pointorder/export
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月8日 下午2:32:28
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_order_point_export"})
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportPointOrder(ModelMap model, PointOrderReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("线上积分兑换列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(pointOrderSupport.exportPointOrder(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
