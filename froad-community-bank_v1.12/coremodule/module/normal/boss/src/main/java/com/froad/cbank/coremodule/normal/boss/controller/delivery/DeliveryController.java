package com.froad.cbank.coremodule.normal.boss.controller.delivery;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliveryVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliveryVoRes;
import com.froad.cbank.coremodule.normal.boss.support.delivery.DeliverySupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 配送管理</p>
 * <p>说明: 处理配送管理相关的类</p>
 * <p>创建时间：2015年4月25日下午3:59:15</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

	@Resource
	private DeliverySupport deliverySupport;
	
	/**
	 * 
	 * <p>功能简述：配送订单列表</p> 
	 * <p>使用说明：查询配送订单列表</p> 
	 * <p>创建时间：2015年4月25日下午4:11:33</p>
	 * <p>作者: 陈明灿</p>
	 * @param map
	 * @param req
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void deliveryList(ModelMap model, HttpRequest request,
			@RequestBody DeliveryVoReq req) {
		LogCvt.info("配送订单列表请求参数："+JSON.toJSONString(req));
		try {
			model.clear();
			ArrayList<DeliveryVoRes> list = deliverySupport.list(req);
			model.put("list", list);
		} catch (Exception e) {
			LogCvt.error("配送订单列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "配送订单列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：配送订单详情查询</p> 
	 * <p>使用说明：根据订单Code查询配送详情</p> 
	 * <p>创建时间：2015年4月27日上午10:14:52</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param orderCode
	 */
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public void queryOrderByCOde(ModelMap model, HttpRequest request,
			@RequestParam("orderCode") String orderCode) {
		LogCvt.info("配送订单详情请求参数:" + orderCode);
		try {
			model.clear();
			if (StringUtil.isNotBlank(orderCode)) {
				model.putAll(deliverySupport.queryOrderByCode(orderCode));
			} else {
				new RespError(model, "订单号不能为空!!!");
			}
		} catch (Exception e) {
			LogCvt.error("配送订单列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "配送订单列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：更新配送订单</p> 
	 * <p>使用说明：更新配送订单</p> 
	 * <p>创建时间：2015年4月27日下午1:51:14</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(ModelMap model, HttpRequest request,
			@RequestBody DeliveryVoReq req) {
		LogCvt.info("更新配送订单请求参数:" + JSON.toJSONString(req));
		try {
			if (StringUtil.isNotBlank(req.getLogisticsName())
					&& StringUtil.isNotBlank(req.getLogisticsCode())) {
				model.putAll(deliverySupport.update(req));
			}else{
				new RespError(model, "更新配送订单请求参数不全!!!");
			}
		} catch (Exception e) {
			LogCvt.error("更新配送订单请求异常"+e.getMessage(), e);
			new RespError(model, "更新配送订单失败!!!");
		}
	}
}

