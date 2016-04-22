package com.froad.fft.template.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.Assert;

import com.froad.fft.util.FreeMarkerUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 指令模型(自定义宏)  基类
 * @author FQ
 *
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

	private static final String ID_PARAMETER_NAME = "id";//"ID"参数名称
	
	
	/**
	 * 获取ID
	 * 
	 * @param params 参数
	 * @return ID
	 */
	protected Long getId(Map<String, TemplateModel> params) throws TemplateModelException {
		return FreeMarkerUtil.getParameter(ID_PARAMETER_NAME, Long.class, params);
	}
	
	/**
	 * 获取变量
	 * 
	 * @param name 名称
	 * @param env Environment
	 * @return 变量
	 */
	public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(env);
		return env.getVariable(name);
	}
	
	/**
	 * 设置局部变量
	 * 
	 * @param name 名称
	 * @param value 变量值
	 * @param env Environment
	 * @param body TemplateDirectiveBody
	 */
	protected void setLocalVariable(String name, Object value, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateModel sourceVariable = FreeMarkerUtil.getVariable(name, env);
		FreeMarkerUtil.setVariable(name, value, env);
		body.render(env.getOut());
		FreeMarkerUtil.setVariable(name, sourceVariable, env);
	}
}
