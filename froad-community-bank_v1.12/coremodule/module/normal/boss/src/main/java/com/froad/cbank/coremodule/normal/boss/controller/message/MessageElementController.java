package com.froad.cbank.coremodule.normal.boss.controller.message;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.support.message.MessageElementSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 短信元素</p>
 * <p>说明: 处理短信元素相关的业务</p>
 * <p>创建时间：2015年5月12日下午1:45:23</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value="smsElement")
public class MessageElementController{

	@Resource
	private MessageElementSupport messageElementSupport;
	
	/**
	 * 
	 * <p>功能简述：短信元素列表</p> 
	 * <p>使用说明：查询短信元素列表</p> 
	 * <p>创建时间：2015年5月12日下午1:54:55</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void list(ModelMap model,String pageNumber,String pageSize){
		try {
			model.putAll(messageElementSupport.list(pageNumber, pageSize));
		} catch (Exception e) {
			LogCvt.error("短信元素列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "短信元素列表查询失败!!!");
		}
	}
	
}
