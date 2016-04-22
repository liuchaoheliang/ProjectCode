package com.froad.cbank.coremodule.module.normal.user.controller.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONException;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrderDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.support.OrderSupport;

/** 
 * 个人中心-订单类
 * @ClassName: OrderController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015-3-30 上午11:36:43 
 */ 
@Controller
@RequestMapping
public class OrderController extends BasicSpringController {

	@Resource
	private OrderSupport orderSupport;
	
	/**
	 * 我的订单列表
	 * @Title: orderList 
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.user
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:47:51
	 * @url /user/order_list
	 * @method get
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/list", method = RequestMethod.GET)
	public void orderList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode) {
		long startTime = System.currentTimeMillis();
		
		String clientId = getClient(req);
		String orderStatus = req.getParameter("orderStatus");
		Integer pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		Integer pageSize=Integer.parseInt(req.getParameter("pageSize"));
		Integer lastPageNumber = Integer.parseInt(req.getParameter("lastPageNumber"));
		Long firstRecordTime = Long.parseLong(req.getParameter("firstRecordTime"));
		Long lastRecordTime = Long.parseLong(req.getParameter("lastRecordTime"));
		Date startDate = null;
		Date endDate = null;
		
		try{
			startDate = StringUtil.isBlank(req.getParameter("startTime")) ?  null : new Date(Long.parseLong(req.getParameter("startTime")));
			endDate = StringUtil.isBlank(req.getParameter("endTime")) ? null : new Date(Long.parseLong(req.getParameter("endTime")));
		}catch(JSONException e1){
			LogCvt.error(String.format("查询订单日期参数转换异常: startTime:%s, endTime:%s", req.getParameter("startTime"), req.getParameter("endTime")));
		}
		
		
		
		model.putAll(orderSupport.getOrderList(clientId, memberCode, orderStatus, startDate, endDate, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));
		
		//查询订单列表耗时监控
		Monitor.send(MonitorEnums.user_order_list_query_time, Long.toString(System.currentTimeMillis() - startTime));
		//查询订单列表耗时监控
		
	}
	
	/**
	 * 订单详情
	 * @Title: orderDetail 
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.user
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:47:45
	 * @param model
	 * @param orderId
	 * @param subOrderId
	 * @url /user/detail/{orderId}/{subOrderId}
	 * @method get
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/detail", method = RequestMethod.GET)
	public void orderDetail(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, String orderId) {
		long startTime = System.currentTimeMillis();
		
		String clientId = getClient(req);
		
		model.putAll(orderSupport.getOrderDetail(clientId, memberCode, orderId));
		
		//查询订单列表耗时监控
		Monitor.send(MonitorEnums.user_order_detail_query_time, Long.toString(System.currentTimeMillis() - startTime));
		//查询订单列表耗时监控
	}
	
	
	/**
	 * 取消订单
	 * @Title: orderCancel 
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.user
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:48:25
	 * @param model
	 * @param orderId
	 * @param orderType
	 * @url /user/refund/{orderId}/{orderType}
	 * @method patch
	 */
	@Token
	@RequestMapping(value = "/myorder/cancel", method = RequestMethod.POST)
	public void orderCancel(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody OrderDetailPojo order) {
		
		String clientId = getClient(req);
		
		LogCvt.info(String.format("用户取消订单>> memberCode:%s ，订单号：%s", memberCode, order.getOrderId()));
		
		model.putAll(orderSupport.cancelOrder(clientId, order.getOrderId()));
		
	}
	
	
	/**
	 * 确认收货
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午3:04:11
	 */
	@Token
	@RequestMapping(value = "/myorder/receipt", method = RequestMethod.POST)
	public void orderComfirm(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody OrderDetailPojo order){
		
		String clientId = getClient(req);
		
		LogCvt.info(String.format("用户确认收货>> memberCode:%s ，订单号：%s， 子订单号：%s", memberCode, order.getOrderId(), order.getSubOrderId()));
		
		model.putAll(orderSupport.comfirmReceipt(clientId, order.getOrderId(), order.getSubOrderId()));
	}
	
	
	

	/**
	 * 积分兑换订单列表
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 上午11:32:48
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/pointOrderList", method = RequestMethod.GET)
	public void getPointExchangeOrderList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode){
		String clientId = getClient(req);

		String orderType = req.getParameter("orderType");
		Integer pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		Integer pageSize=Integer.parseInt(req.getParameter("pageSize"));
		Integer lastPageNumber = Integer.parseInt(req.getParameter("lastPageNumber"));
		Long firstRecordTime = Long.parseLong(req.getParameter("firstRecordTime"));
		Long lastRecordTime = Long.parseLong(req.getParameter("lastRecordTime"));
		
		model.putAll(orderSupport.getPointExchangeOrderList(clientId, memberCode, orderType, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));
	}
	
	
	
	/**
	 * 积分兑换 订单详情
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 上午11:36:13
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/pointOrderDetail", method = RequestMethod.GET)
	public void getPointExchangeOrderDetail(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, String orderId){
		String clientId = getClient(req);
		model.putAll(orderSupport.getPointExchangeOrderDetail(clientId, memberCode, orderId));
	}
	


	
	
	
	/**
	 * 面对面订单列表
	 * @description 
	 * @author Liebert
	 * @date 2015年5月6日 下午2:44:04
	 *
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/qrcode_list", method = RequestMethod.GET)
	public void qrcodeList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode) {

		String clientId = getClient(req);
		
		String orderStatus = req.getParameter("orderStatus");
		Integer pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		Integer pageSize=Integer.parseInt(req.getParameter("pageSize"));
		Integer lastPageNumber = Integer.parseInt(req.getParameter("lastPageNumber"));
		Long firstRecordTime = Long.parseLong(req.getParameter("firstRecordTime"));
		Long lastRecordTime = Long.parseLong(req.getParameter("lastRecordTime"));

		model.putAll(orderSupport.getQrcodeOrderList(clientId, memberCode, orderStatus, pageNumber, pageSize, lastPageNumber, firstRecordTime, lastRecordTime));

	}
	


	
	/**
	 * 面对面订单详情
	 * @description 
	 * @author Liebert
	 * @date 2015年5月6日 下午2:44:15
	 *
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/myorder/qrcode_detail", method =  RequestMethod.GET)
	public void qrcodeDetail(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, String orderId) {
		String clientId = getClient(request);
			
		model.putAll(orderSupport.getQrcodeOrderDetail(clientId, memberCode, orderId));
			
	}
	
	/**
	 * 查看物流信息
	 * @author yfy
	 * @date 2015年12月1日 下午2:29:15
	 * @param request
	 * @param model
	 * @param subOrderId
	 */
	@Token
	@RequestMapping(value = "/myorder/logistics", method =  RequestMethod.GET)
	public void getLogistics(HttpServletRequest request, ModelMap model,String subOrderId) {
		model.clear();
		if(StringUtil.isNotBlank(subOrderId)){			
			model.putAll(orderSupport.getLogistics(subOrderId));
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"必要参数为空");
		}
	}

	/**
	 * 精品商城确认收货
	 * @author yfy
	 * 2015年11月9日 下午7:15:37
	 * @param model
	 * @param request
	 * @param subOrderId
	 * @param deliveryState
	 */
	@Token
	@RequestMapping(value = "/myorder/confirmReceipt", method = RequestMethod.GET)
	public void confirmReceipt(ModelMap model, HttpServletRequest request, String subOrderId) {
		String clientId = getClient(request);
		if(StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(subOrderId)){			
			model.putAll(orderSupport.confirmReceipt(clientId, subOrderId));
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"必要参数为空");
		}
	}
}
