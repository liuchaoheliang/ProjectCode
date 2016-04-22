package com.froad.cbank.coremodule.framework.common.util.web;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class WebConfig extends WebMvcConfigurationSupport {
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);
		handlerMapping.setUseTrailingSlashMatch(false);
		return handlerMapping;
	}
}