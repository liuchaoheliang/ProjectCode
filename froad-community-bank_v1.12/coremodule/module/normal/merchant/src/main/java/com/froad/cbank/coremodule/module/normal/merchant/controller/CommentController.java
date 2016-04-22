package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Comment_Count_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Comment_Replay_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Product_Comment_Replay_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Details_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Comment_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 评价
 * @ClassName CommentController
 * @author zxl
 * @date 2015年3月21日 下午1:53:50
 */
@Controller
@RequestMapping(value = "/comment" )
public class CommentController {

	@Resource
	Comment_Service comment_Service;

	/**
	 * 商品评价列表
	 * @tilte list
	 * @author zxl
	 * @date 2015年3月23日 上午11:44:59
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_comment_productreviews_menu"})
	@RequestMapping(value = "qpcl" , method = RequestMethod.POST)
	public void query_product_comment_list(ModelMap model,HttpServletRequest request,@RequestBody Query_Product_Comment_Req req){
		try {
			long begin = System.currentTimeMillis();
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(comment_Service.query_product_comment_list(req));
			
			Monitor.send(MonitorEnums.merchant_goods_commect, String.valueOf(System.currentTimeMillis()-begin));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	
    /**
     * 查询商品评价详情
     * @param model
     * @param req
     */
	@CheckPermission(keys={"merchant_comment_productreviews_menu"})
	@RequestMapping(value = "qpd" , method = RequestMethod.POST)
	public void query_product_detail(ModelMap model,HttpServletRequest request,@RequestBody Query_Product_Comment_Details_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(comment_Service.query_product_detail(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品评价回复
	 * @tilte replay
	 * @author zxl
	 * @date 2015年3月23日 上午11:45:12
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_comment_productreviews_menu"})
	@RequestMapping(value = "rpy" , method = RequestMethod.POST)
	public void product_replay(ModelMap model,HttpServletRequest request,@RequestBody Product_Comment_Replay_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(comment_Service.product_replay(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 商品批量回复
	 * @tilte product_replay_batch
	 * @author zxl
	 * @date 2015年4月21日 下午4:39:07
	 * @param model
	 * @param request
	 * @param req
	 */
	@CheckPermission(keys={"merchant_comment_productreviews_menu"})
	@RequestMapping(value = "rpyb" , method = RequestMethod.POST)
	public void product_replay_batch(ModelMap model,HttpServletRequest request,@RequestBody Product_Comment_Replay_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(comment_Service.product_replay_batch(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
    /**
     * 商户门店评价统计（全部）
     * @param model
     * @param pojo
     */
	@RequestMapping(value = "qocca" , method = RequestMethod.POST)
	public void query_outlet_comment_count_all(ModelMap model,HttpServletRequest request,@RequestBody Outlet_Comment_Count_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(comment_Service.query_outlet_comment_count_all(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 查询商户门店评价
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_comment_merchantreviews_menu"})
	@RequestMapping(value = "qoc" , method = RequestMethod.POST)
	public void query_outlet_comment(ModelMap model,HttpServletRequest request, @RequestBody Query_Outlet_Comment_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(comment_Service.query_outlet_comment(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	
	/**
	 * 查询商户门店评价详情
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_comment_merchantreviews_menu"})
	@RequestMapping(value = "pcd" , method = RequestMethod.POST)
	public void query_outlet_comment_detail(ModelMap model, HttpServletRequest request, @RequestBody Query_Outlet_Comment_Detail_Req req ){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(comment_Service.query_outlet_comment_detail(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	
     /**
      * 商户门店评价回复
      * @param model
      * @param pojo
      */
	@CheckPermission(keys={"merchant_comment_merchantreviews_menu"})
	@RequestMapping(value = "roc" , method = RequestMethod.POST)
	public void replay_outlet_comment(ModelMap model,HttpServletRequest request, @RequestBody Outlet_Comment_Replay_Req req ){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(comment_Service.replay_outlet_comment(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	
//    /**
//     * 商户门店评价统计（单个）
//     * @param model
//     * @param pojo
//     */
//	@RequestMapping(value = "qoccc" , method = RequestMethod.POST)
//	public void query_outlet_comment_count(ModelMap model,HttpServletRequest request,@RequestBody Outlet_Comment_Count_Req req){
//		try {
//			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
//			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
//			model.putAll(comment_Service.query_outlet_comment_count(req));
//		} catch (MerchantException e) {
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error(e.getMessage(), e);
//			new RespError(model);
//		}
//	}
}
