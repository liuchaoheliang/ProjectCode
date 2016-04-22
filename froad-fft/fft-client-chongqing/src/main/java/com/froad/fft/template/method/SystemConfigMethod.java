package com.froad.fft.template.method;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.support.base.SystemConfigSupport;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 自定方法  - 系统配置
 * @author FQ
 *
 */

@Component("systemConfigMethod")
public class SystemConfigMethod implements TemplateMethodModel {

	@Resource
	private SystemConfigSupport systemConfigSupport;

	@Override
	public Object exec(List arguments) throws TemplateModelException {

		SystemConfig systemConfig = new SystemConfig();
		SystemConfigDto systemConfigDto = systemConfigSupport.getSystemConfig();
		systemConfig.setSiteUrl("http://localhost:8080/fft-client-chongqing");

		systemConfig.setIsLoginFailureLock(systemConfigDto
				.getIsLoginFailureLock());
		systemConfig.setLoginFailureLockCount(systemConfigDto
				.getLoginFailureLockCount());
		systemConfig.setLoginFailureLockTime(systemConfigDto
				.getLoginFailureLockTime());

		systemConfig.setCookiePath(systemConfigDto.getCookiePath());
		systemConfig.setCookieDomain(systemConfigDto.getCookieDomain());
		systemConfig.setFtpUrl(systemConfigDto.getFtpUrl());

		return systemConfig;
	}

}
