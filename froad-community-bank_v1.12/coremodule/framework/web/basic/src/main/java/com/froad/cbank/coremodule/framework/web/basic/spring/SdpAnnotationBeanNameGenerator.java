package com.froad.cbank.coremodule.framework.web.basic.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ObjectUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

/** 
 * 自定义Spring注解bean的命名策略
 * @ClassName: SdpAnnotationBeanNameGenerator 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015-3-19 上午11:44:14 
 */ 
public class SdpAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String className = definition.getBeanClassName();
		try{
			Class<?> clazz = Class.forName(className);
			
			className = ObjectUtil.getBaseClassName(className);
			className = StringUtil.lowerFirst(className);
			Controller controller = clazz.getAnnotation(Controller.class);
			Repository repository = clazz.getAnnotation(Repository.class);
			Service service = clazz.getAnnotation(Service.class);
			if((repository != null && StringUtil.isBlank(repository.value())) || (service != null && StringUtil.isBlank(service.value()))) {
				if (className.toLowerCase().endsWith("support")) {
					className = StringUtil.lowerFirst(className);
				}
			} else if(controller != null && StringUtil.isBlank(controller.value())) {
				className = StringUtil.replace(definition.getBeanClassName().toLowerCase(), "\\.", "_");
			} else {
				className = super.buildDefaultBeanName(definition);
			}
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			className = super.buildDefaultBeanName(definition);
		}
		return className;
	}
}
