package com.froad.cbank.coremodule.normal.boss.controller.order;

import java.net.SocketTimeoutException;

import javax.annotation.Resource;

import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DiscontPayListVoReq;
import com.froad.cbank.coremodule.normal.boss.support.order.DiscountPaySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


/**
 * 
 * @ClassName: DiscountPayController
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月29日 上午9:50:46 
 * @desc <p>惠付订单controller</p>
 */

@Controller
@RequestMapping(value="/discountPay")
public class DiscountPayController {
	
	/**
	 * 注入惠付订单support
	 */
	@Resource
	DiscountPaySupport discountPaySupport;
	
	/**
	 * 
	 * <p>Title:惠付订单列表查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月29日 下午2:51:38 
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_order_face_menu"})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void list(ModelMap model,DiscontPayListVoReq req){
		LogCvt.info("惠付订单列表查询条件： "+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(discountPaySupport.list(req));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model, e);
		}
	}
	
	/**
	 * 
	 * <p>Title:惠付订单详情查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月29日 下午5:00:11 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public void detail(ModelMap model,DiscontPayListVoReq req){
		if(!StringUtil.isNotEmpty(req.getClientId())){
			new RespError(model, "惠付订单详情查询客户端不能为空");
		}
		if(!StringUtil.isNotEmpty(req.getOrderId())){
			new RespError(model, "惠付订单详情查询订单编号不能为空");
		}
		LogCvt.info("惠付订单详情查询条件： "+JSON.toJSONString(req));
		
		try {
			model.clear();
			model.putAll(discountPaySupport.detail(req.getClientId(), req.getOrderId()));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model, e);;
		}
	}
	
	/**
	 * 
	 * <p>Title: 惠付订单列表导出</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月29日 下午3:48:56 
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_order_face_export"})
	@RequestMapping(value="/csv",method=RequestMethod.GET)
	public void csv(ModelMap model,DiscontPayListVoReq req){
		LogCvt.info("惠付订单列表导出条件： "+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(discountPaySupport.exportDiscountPayOrderList(req));
		} catch (TTransportException  e) {
			if(e.getCause() instanceof SocketTimeoutException) {
				 new RespError(model,"惠付订单列表详细导出超时，请稍后重试");
			} 
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model, e);
		}
	}
	
	
	
	
}
