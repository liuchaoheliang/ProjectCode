package com.froad.cbank.coremodule.normal.boss.controller.external;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.NoToken;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.external.CallBackVo;
import com.froad.cbank.coremodule.normal.boss.support.external.KuaidiSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 对外接口快递100
 * @author yfy
 * @date: 2016年1月11日 上午10:41:18
 */
@Controller
@RequestMapping(value = "/kuaidi")
public class kuaidiController {

	@Resource
	private KuaidiSupport kuaidiSupport;
	/**
	 * 快递100回调
	 * @author yfy
	 * @date: 2016年1月11日 下午11:11:18
	 * @param voReq
	 */
	@NoToken
	@RequestMapping(value = "callback", method = RequestMethod.POST)
	public void callback(ModelMap model, HttpServletRequest request) {
		LogCvt.info("快递100回调请求参数:"+JSON.toJSONString(request.getParameter("param")));
		try {
			String param = request.getParameter("param");
			CallBackVo  vo =  JSON.parseObject(param.replaceAll("\'", ""), CallBackVo.class);
			model.clear();
			model.putAll(kuaidiSupport.callback(vo));
		} catch (Exception e) {
			LogCvt.error("快递100回调请求异常"+e.getMessage(), e);
			new RespError(model, "快递100回调请求失败!!!");
		}
	}
}
