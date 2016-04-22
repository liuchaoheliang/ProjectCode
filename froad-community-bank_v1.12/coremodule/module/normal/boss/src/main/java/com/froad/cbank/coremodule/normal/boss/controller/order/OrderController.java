package com.froad.cbank.coremodule.normal.boss.controller.order;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.valid.ValidBeanField;
import com.froad.cbank.coremodule.framework.common.valid.ValidException;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.OrderDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.OrderListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.TicketInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.support.order.OrderSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：订单接口相关类
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015
 * @author: wwh
 */
@Controller
@RequestMapping("/order")
public class OrderController{

	@Resource
	OrderSupport orderSupport;	
	
	/**
	 * 订单列表
	 * @path /order/list
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月6日 上午10:20:23
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_order_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, OrderListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("查询订单列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(orderSupport.getOrderList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	 * 订单详情
	 * @path /order/detail
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月2日 下午1:45:31
	 * @param model
	 * @param orderId
	 * @param clientId
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model, OrderDetailReq pojo) {
		try {
			ValidBeanField.valid(pojo);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("查询订单详情请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(orderSupport.getOrderDetail(pojo));
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
	 * 订单列表导出
	 * @path /order/orderListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午2:43:59
	 * @param model
	 * @param orderReq
	 */
	@Auth(keys={"boss_order_export"})
	@RequestMapping(value = "/orderListExport", method = RequestMethod.GET)
	public void orderListExport(ModelMap model, OrderListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("订单列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(orderSupport.orderListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	  * 方法描述：分页查询团购券
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: liaopeixin
	  * @time: 2015年8月7日 下午2:42:15
	  */
	@Auth(keys={"boss_group_menu"})
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public void ticketGroupList(ModelMap model, TicketInfoVoReq voReq){
		LogCvt.info("团购券列表查询条件：" + JSON.toJSONString(voReq));
		try {
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(orderSupport.queryGroupList(voReq));
		} catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error("团购券列表查询请求异常" + e.getMessage(), e);
			new RespError(model, "团购券列表查询失败!!!");
		}	
	}
	
	/**
	  * 方法描述：分页查询预售券
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 廖培鑫
	  * @time: 2015年8月7日 下午8:42:15
	  */
	@Auth(keys={"boss_presell_menu"})
	@RequestMapping(value = "/presell", method = RequestMethod.GET)
	public void ticketPresellList(ModelMap model, TicketInfoVoReq voReq){
		LogCvt.info("预售券列表查询条件：" + JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(orderSupport.queryPresellList(voReq));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("预售券列表查询请求异常" + e.getMessage(), e);
			new RespError(model, "预售券列表查询失败!!!");
		}	
	}
	
	/**
	 * 
	 * <p>功能简述：—— 预售券详情查询</p> 
	 * <p>创建时间：2015-8-14下午2:12:08</p>
	 * <p>作者: 廖培鑫</p>
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/presellDetail", method = RequestMethod.GET)
	public void ticketPresellDetail(ModelMap model, TicketInfoVoReq voReq) {
		LogCvt.info("预售券详情查询条件：" + JSON.toJSONString(voReq));
		try {
			model.clear();
			model.put("presellTktInfo",orderSupport.queryPresellDetail(voReq));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("预售券详情查询请求异常" + e.getMessage(), e);
			new RespError(model, "预售券详情查询失败!!!");
		}	
	}

	/**
	 * 团购券订单列表导出
	 * @path /order/groupListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月7日 下午2:56:00
	 * @param model
	 * @param voReq
	 */
	@Auth(keys={"boss_group_export"})
	@RequestMapping(value = "/groupListExport", method = RequestMethod.GET)
	public void groupListExport(ModelMap model, TicketInfoVoReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("团购券订单列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(StringUtils.isBlank(pojo.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(pojo.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(orderSupport.groupListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	 * 预售券订单列表导出
	 * @path /order/presellListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月7日 下午2:56:00
	 * @param model
	 * @param pojo
	 */
	@Auth(keys={"boss_presell_export"})
	@RequestMapping(value = "/presellListExport", method = RequestMethod.GET)
	public void presellListExport(ModelMap model, TicketInfoVoReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("预售券订单列表导出请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(orderSupport.presellListExport(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
}
