package com.froad.cbank.coremodule.module.normal.user.controller.receiver;

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
import com.froad.cbank.coremodule.module.normal.user.pojo.ReceiverDelPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ReceiverPojo;
import com.froad.cbank.coremodule.module.normal.user.support.ReceiverSupport;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月24日 下午4:21:54
 * @version 1.0
 * @desc 收货人地址类
 */
@Controller
@RequestMapping
public class ReceiverController extends BasicSpringController {

	@Resource
	private ReceiverSupport receiverSupport;
	
	/**
	 * @desc 根据用户ID获取收货地址列表
	 * @path /receiver/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:14:22
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/receiver/list", method = RequestMethod.GET)
	public void getReceiverList(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, String isDefault, String type) {
		String clientId = getClient(req);

		model.putAll(receiverSupport.getReceiverList(clientId, memberCode, isDefault, type));

	}
	
	/**
	 * @desc 删除收货地址
	 * @path /receiver/delete
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:21:29
	 * @param memberCode(必填)/recvId(必填)
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/receiver/delete", method = RequestMethod.POST)
	public void deleteReceiver(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody ReceiverDelPojo pojo) {
		String clientId = getClient(req);
		if(memberCode != null) {
			model.putAll(receiverSupport.deleteReceiver(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
	
	/**
	 * @desc 更新/新增 收货地址
	 * @path /receiver/update
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年3月24日 下午5:24:11
	 * @param 
	 * @return 
	 */
	@Token
	@RequestMapping(value = "/receiver/update", method = RequestMethod.POST)
	public void updateReceiver(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody ReceiverPojo pojo) {
		String clientId = getClient(req);
		pojo.setIsDefault(pojo.getIsDefault() == null ? "0" : pojo.getIsDefault());
		if(memberCode != null) {
			model.putAll(receiverSupport.updateReceiver(clientId, memberCode, pojo));
		} else {
			model.put("code", "9999");
			model.put("message", "用户ID不可为空");
		}
	}
}