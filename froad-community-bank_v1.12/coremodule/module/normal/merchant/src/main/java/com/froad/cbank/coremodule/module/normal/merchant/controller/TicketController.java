package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_List_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Ticket_Code_Use_Banth_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Ticket_Code_Use_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Ticket_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 券码
 * @ClassName TicketController
 * @author zxl
 * @date 2015年4月17日 上午9:48:47
 */
@Controller
public class TicketController {
	
	@Resource
	Ticket_Service ticket_Service;
	
	/**
     * 提货查询
     * @param model
     * @param merchantId
     * @param outletId
     * @param code
     * @param userId
     */
	
	
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/qcl", method = RequestMethod.POST)
	public void query_code_list(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_List_Req req){
		try {
			LogCvt.info("提货码请求参数："+JSON.toJSONString(req));
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.query_code_list(req));
			Monitor.send(MonitorEnums.merchant_ticket_list, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 提货码统计
	 * @tilte query_code_count
	 * @author args
	 * @date 2015年3月23日 下午2:06:57
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/count", method = RequestMethod.POST)
	public void query_code_count(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_List_Req req) {
		try {
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.query_code_count(req));
			Monitor.send(MonitorEnums.merchant_ticket_list, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 提货码详情查询
	 * @tilte querySecuritiesDetail
	 * @author zxl
	 * @date 2015年3月23日 下午2:06:57
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/lsd", method = RequestMethod.POST)
	public void querySecuritiesDetail(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_Detail_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.querySecuritiesDetail(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	

	/**
	 * 提货码使用
	 * @tilte querySecuritiesDetail
	 * @author zxl
	 * @date 2015年3月23日 下午2:09:55
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/su", method = RequestMethod.POST)
	public void securitiesUsed(ModelMap model,HttpServletRequest request,@RequestBody  Ticket_Code_Use_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.securitiesUsed(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
     * 获取券列表(相同商品合并成一个)
     * all
     * @param merchantId
     * @param outletId
     * @param code
     * @param userId
     */
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/all", method = RequestMethod.POST)
	public void query_code_list_all(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_List_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(ticket_Service.query_code_list_all(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	

	/**
	 * 提货码使用
	 * @tilte querySecuritiesDetail
	 * all
	 * @date 2015年3月23日 下午2:09:55
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_order_deliveryverification_menu"})
	@RequestMapping(value = "/order/seall", method = RequestMethod.POST)
	public void securitiesUsed_all(ModelMap model,HttpServletRequest request,@RequestBody  Ticket_Code_Use_Banth_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(ticket_Service.securitiesUsed_all(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
     * 商户验证码记录query_code_list
     * total
     * @param merchantId
     * @param outletId
     * @param code
     * @param userId
     */
	@RequestMapping(value = "/order/total", method = RequestMethod.POST)
	public void query_code_list_count(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_List_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.query_code_list_count(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * downTicket:(提货码下载).
	 *
	 * @author wm
	 * 2015年9月9日 下午2:29:23
	 * @param model
	 * @param request
	 * @param req
	 *
	 */
	
	
	
	@CheckPermission(keys={"merchant_order_codemanage_menu"})
	@RequestMapping(value = "/ticket/ticketDown", method = RequestMethod.POST)
	public void downTicket(ModelMap model,HttpServletRequest request,@RequestBody Query_Ticket_List_Req req){
		try {
			LogCvt.info("提货码下载请求参数："+JSON.toJSONString(req));
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(ticket_Service.query_ticket_down(req));
			Monitor.send(MonitorEnums.merchant_ticket_list, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
}
