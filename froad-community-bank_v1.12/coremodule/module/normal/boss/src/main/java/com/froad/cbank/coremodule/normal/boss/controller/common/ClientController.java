package com.froad.cbank.coremodule.normal.boss.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/** 
 * <p>标题: 客户端</p>
 * <p>说明: ****</p>
 * <p>创建时间：2015-4-27下午3:32:51</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
public class ClientController{
	
	@Resource
	private ClientSupport clientSupport;

	/**
	 * 获取客户端
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "/client/lt", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request) {
		try{
			List<ClientRes> res = clientSupport.getClient();
			model.put("clientList", res);
		} catch (Exception e) {
			LogCvt.error("获取客户端请求异常"+e.getMessage(), e);
			new RespError(model, "获取客户端失败!!!");
		}
	}
	
}
