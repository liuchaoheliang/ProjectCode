package com.froad.cbank.coremodule.normal.boss.controller.message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.message.MessagesVoReq;
import com.froad.cbank.coremodule.normal.boss.support.message.MessageLogSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 短信日志相关</p>
 * <p>说明: 短信日志相关的业务</p>
 * <p>创建时间：2015年5月11日上午11:38:58</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value = "smsLog")
public class MessageLogController{
	
	@Resource
	private MessageLogSupport messageLogSupport;
	/**
	 * 
	 * <p>功能简述：短信日志列表接口</p> 
	 * <p>使用说明：根据查询条件查询短信列表</p> 
	 * <p>创建时间：2015年5月11日上午11:42:04</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 */
	@Auth(keys={"boss_sms_menu"})
	@RequestMapping(method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,MessagesVoReq messagesVoReq) {
		LogCvt.info("短信日志列表查询请求参数:", JSON.toJSONString(messagesVoReq));
		try {
			model.clear();
			model.putAll(messageLogSupport.list(messagesVoReq));
		} catch (Exception e) {
			LogCvt.error("短信日志列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "短信日志列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：短信重发</p> 
	 * <p>使用说明：根据id重发短信</p> 
	 * <p>创建时间：2015年5月11日下午3:17:07</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 * @param id
	 */
	@Auth(keys={"boss_sms_rev"})
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public void sendAgain(ModelMap model, HttpServletRequest request,
			@PathVariable("id") String id) {
		LogCvt.info("短信重发请求参数:", id);
		try {
			model.clear();
			model.putAll(messageLogSupport.sendAgain(id));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("短信重发请求异常"+e.getMessage(), e);
			new RespError(model, "短信重发失败!!!");
		}
	}
}
