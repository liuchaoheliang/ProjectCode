package com.froad.cbank.coremodule.module.normal.user.controller.delivery;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryDelPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryPojo;
import com.froad.cbank.coremodule.module.normal.user.support.DeliverySupport;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月24日 下午5:12:48
 * @version 1.0
 * @desc 提货人信息管理
 */
@Controller
@RequestMapping
public class DeliveryController extends BasicSpringController {

	@Resource
	private DeliverySupport deliverySupport;
	
	/**
	 * @desc 获取提货人信息列表
	 * @path /delivery/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午5:41:33
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/delivery/list", method = RequestMethod.GET)
	public void getDeliveryList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, String isDefault) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(deliverySupport.getDeliveryList(clientId, memberCode, isDefault));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 删除提货人信息
	 * @path /delivery/delete
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午5:45:19
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/delivery/delete", method = RequestMethod.POST)
	public void deleteDelivery(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody DeliveryDelPojo pojo) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(deliverySupport.deleteDelivery(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "必要值不可为空");
		}
	}
	
	/**
	 * @desc 更新/新增提货人信息
	 * @path /delivery/update
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午5:45:42
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/delivery/update", method = RequestMethod.POST)
	public void updateDelivery(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody DeliveryPojo pojo) {
		String clientId = getClient(req);
		pojo.setIsDefault(pojo.getIsDefault() == null ? "0" : pojo.getIsDefault());
		if(memberCode != null) {
			model.putAll(deliverySupport.updateDelivery(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
}
