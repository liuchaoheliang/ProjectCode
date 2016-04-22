package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Face_Group_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Order_Shipping_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Settlement_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Order_Detail_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Order_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 订单
 * 
 * @ClassName OrderController
 * @author zxl
 * @date 2015年3月21日 下午1:51:13
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {
	
	@Resource
	Order_Service order_Service;
		
	
	/**
	 * 面对面创建
	 * @param model
	 * @param vo
	 */
	
	@CheckPermission(keys={"merchant_order_trancationshier_menu"})
	@RequestMapping(value = "fcreate", method = RequestMethod.POST)
	public void face_Group_Create(ModelMap model,HttpServletRequest request,@RequestBody Add_Face_Group_Req req){
		try {
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(order_Service.face_Group_Create(req));
			
			Monitor.send(MonitorEnums.merchant_goods_create, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
    /**
     * 查询列表，团购和面对面订单
     * @param model
     * @param merchantId
     * @param type
     * @param outletId
     * @param userId
     * @param pageNumber
     * @param pageSize
     */
	@CheckPermission(keys={"merchant_order_orderlist_menu"})
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void query_face_group(ModelMap model,HttpServletRequest request,@RequestBody Query_Face_Group_Req req) {
		try {
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_face_group(req));
			
			Monitor.send(MonitorEnums.merchant_order_list_h5, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	

    /**
     * 查询详细内容，团购和面对面订单详细
     * @param model
     * @param subOrderId
     * @param type
     */
	@RequestMapping(value = "gld", method = RequestMethod.POST)
	public void query_order_detail(ModelMap model,HttpServletRequest request,@RequestBody Query_Face_Group_Detail_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_detail(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
    /**
     * 查询列表，团购和面对面订单pc
     * @param model
     * @param merchantId
     * @param type
     * @param outletId
     * @param userId
     * @param pageNumber
     * @param pageSize
     */
	@CheckPermission(keys={"merchant_order_orderlist_menu"})
	@RequestMapping(value = "qfg", method = RequestMethod.POST)
	public void query_order_group_PC(ModelMap model,HttpServletRequest request,@RequestBody Query_Face_Group_PC_Req req) {
		try {
			LogCvt.info("订单列表查询请求参数："+JSON.toJSONString(req));
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_group_PC(req));
			
			Monitor.send(MonitorEnums.merchant_order_list_pc, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	

    /**
     * 查询详细内容，团购和面对面订单详细pc
     * @param model
     * @param subOrderId
     * @param type
     */
	@CheckPermission(keys={"merchant_order_orderlist_menu"})
	@RequestMapping(value = "gogd", method=RequestMethod.POST)
	public void query_order_group_detail(ModelMap model,HttpServletRequest request,@RequestBody Query_Face_Group_Detail_PC_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_group_detail(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	

	/**
	 * 订单发货
	 * @tilte shippingOrder
	 * @author zxl
	 * @date 2015年4月8日 下午5:25:06
	 * @param model
	 * @param po
	 */
	
	@CheckPermission(keys={"merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "ship", method = RequestMethod.POST)
	public void shippingOrder(ModelMap model,HttpServletRequest request,@RequestBody Order_Shipping_Req req){
		try {
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.shippingOrder(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 获取二维码信息
	 * @tilte query_qrcode_detail
	 * @author zxl
	 * @date 2015年4月30日 下午2:53:56
	 * @param model
	 * @param request
	 * @param qrcode
	 */
	
	@CheckPermission(keys={"merchant_order_codemanage_menu","merchant_order_trancationshier_menu"})
	@RequestMapping(value = "qrc", method = RequestMethod.GET)
	public void query_qrcode_detail(ModelMap model,HttpServletRequest request,String qrcode){
		try {
			if(StringUtils.isBlank(qrcode)){
				throw new MerchantException(EnumTypes.fail.getCode(),"二维码不能为空");
			}
			model.putAll(order_Service.query_qrcode_detail(request.getAttribute(Constants.CLIENT_ID).toString(),qrcode));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 结算汇总
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys={"merchant_index_welcome_menu"})
	@RequestMapping(value = "hzdd", method = RequestMethod.POST)
	public void query_order_settlement(ModelMap model,HttpServletRequest request,@RequestBody Query_Merchant_Settlement_Req req) {
		LogCvt.info("结算汇总"+JSON.toJSONString(req));
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_settlement(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * query_order_down:(订单下载).
	 *
	 * @author wm
	 * 2015年9月9日 下午2:28:13
	 * @param model
	 * @param request
	 * @param req
	 *
	 */
	@CheckPermission(keys={"merchant_order_orderlist_menu"})
	@RequestMapping(value = "orderDown", method = RequestMethod.POST)
	public void query_order_down(ModelMap model,HttpServletRequest request,@RequestBody Query_Face_Group_PC_Req req) {
		try {
			LogCvt.info("订单下载请求参数："+JSON.toJSONString(req));
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_down(req));
			
			Monitor.send(MonitorEnums.merchant_order_list_h5, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	 /**
     * 查询详细内容新需求
     * @param model
     * @param subOrderId
     * @param type
     */
	@CheckPermission(keys={"merchant_order_orderlist_menu"})
	@RequestMapping(value = "orderDetail", method=RequestMethod.POST)
	public void query_order_detail_pc_new(ModelMap model,HttpServletRequest request,@RequestBody Query_Order_Detail_PC_Req req) {
		try {
			LogCvt.info("订单详情优化请求参数: "+JSON.toJSONString(req));
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(order_Service.query_order_detail_pc_new(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
}
