package com.froad.cbank.coremodule.normal.boss.controller.message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.message.SmsTemplateVoReq;
import com.froad.cbank.coremodule.normal.boss.support.message.MessageTemplateSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * <p>标题: 短信模板</p>
 * <p>说明: 处理短信短信相关业务的类</p>
 * <p>创建时间：2015年5月12日上午9:48:44</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Controller
@RequestMapping(value = "smsTemplate")
public class MessageTemplateController{

	@Resource
	private MessageTemplateSupport messageTemplateSupport;

	/**
	 * 
	 * <p>功能简述：短信模板列表</p>
	 * <p>使用说明：查询短信模板列表</p>
	 * <p>创建时间：2015年5月12日上午10:06:36</p>
	 * <p>作者: 陈明灿</p> 
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,SmsTemplateVoReq smsTemplateVoReq) {
		LogCvt.info("短信模板列表查询请求参数:", JSON.toJSONString(smsTemplateVoReq));
		try {
			model.clear();
			model.putAll(messageTemplateSupport.list(smsTemplateVoReq));
		} catch (Exception e) {
			LogCvt.error("短信模板列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "短信模板列表查询失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：新增短信模板</p> 
	 * <p>使用说明：新增短信模板</p> 
	 * <p>创建时间：2015年5月12日下午5:28:33</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(ModelMap model, HttpServletRequest request,
			SmsTemplateVoReq smsTemplateVoReq) {
		LogCvt.info("保存短信模板请求参数:", JSON.toJSONString(smsTemplateVoReq));
		try {
			model.putAll(messageTemplateSupport.saveOrUpdate(smsTemplateVoReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("保存短信模板请求异常"+e.getMessage(), e);
			new RespError(model, "保存短信模板失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>功能简述：修改短信模板</p> 
	 * <p>使用说明：修改短信模板</p> 
	 * <p>创建时间：2015年5月14日下午4:11:03</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param request
	 * @param smsTemplateVoReq
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public void update(ModelMap model, HttpServletRequest request,
			SmsTemplateVoReq smsTemplateVoReq) {
		LogCvt.info("修改短信模板请求参数:", JSON.toJSONString(smsTemplateVoReq));
		try {
			model.clear();
			model.putAll(messageTemplateSupport.saveOrUpdate(smsTemplateVoReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("修改短信模板请求异常"+e.getMessage(), e);
			new RespError(model, "修改短信模板失败!!!");
		}
	}
	/**
	 * 
	 * <p>功能简述：删除短信模板</p> 
	 * <p>使用说明：根据id删除短信模板</p> 
	 * <p>创建时间：2015年5月12日下午3:38:58</p>
	 * <p>作者: 陈明灿</p>
	 * @param model
	 * @param id
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void deleteById(ModelMap model, @PathVariable("id") String id) {
		try {
			model.clear();
			if (StringUtil.isBlank(id)) {
				new RespError(model, "d不能为空!!!");
				return;
			}
			model.putAll(messageTemplateSupport.deleteById(id));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("删除短信模板请求异常"+e.getMessage(), e);
			new RespError(model, "删除短信模板失败!!!");
		}
	}
}
