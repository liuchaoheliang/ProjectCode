package com.froad.cbank.coremodule.module.normal.user.controller.product;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.enums.SeckillProductStatus;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.support.SeckillProductSupport;

/**
 * 类描述：秒杀商品相关
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:55:42
 * @version v1.0
 */
@Controller
@RequestMapping(value = "/seckillProduct")
public class SeckillProductController extends BasicSpringController {

	@Resource
	private SeckillProductSupport seckillProductSupport;
	
	/**
	 * 方法描述：获取秒杀商品列表
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月3日 下午6:55:42
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void ProductList(ModelMap model, HttpServletRequest req, PagePojo pagePojo) {
		
		String clientId = getClient(req);
		
//		JSONObject obj = toJsonObj(req);
		String status = req.getParameter("status"); // 必填
		
		// 执行查询
		if (!StringUtil.empty(clientId)) {
			//条件过滤
			Map<String,String> filter=new HashMap<String, String>();
			if (StringUtil.empty(status)) {
				filter.put("status", SeckillProductStatus.ing.getCode()); 
			} else {
				filter.put("status", status);
			}
			
			// 分页数据
//			Integer pageNumber = obj.getInteger("pageNumber") == null ? 1 : obj.getInteger("pageNumber");
//			Integer pageSize = obj.getInteger("pageSize") == null ? 10 : obj.getInteger("pageSize");
			
			model.putAll(seckillProductSupport.getProductList(clientId, JSON.toJSONString(filter), pagePojo));
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}

	/**
	 * 方法描述：获取秒杀商品详情
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月3日 下午6:55:42
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void getPresellDetail(ModelMap model, HttpServletRequest req,
			String productId, String type) {
		String clientId = getClient(req);
		if (!StringUtil.empty(productId) && !StringUtil.empty(clientId)) {
			model.putAll(seckillProductSupport.getProductDetail(clientId, productId, type));
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}

	}

}
