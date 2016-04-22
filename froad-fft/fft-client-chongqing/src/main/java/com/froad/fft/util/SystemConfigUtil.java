package com.froad.fft.util;

import org.apache.log4j.Logger;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.support.base.SystemConfigSupport;

/**
 * 系统配置
 * @author FQ
 *
 */
public class SystemConfigUtil {
	
	final static Logger logger = Logger.getLogger(SystemConfigUtil.class);
	
	public static SystemConfig getSystemConfig(){
		
		SystemConfigSupport systemConfigSupport=SpringUtil.getBean("systemConfigSupportImpl",SystemConfigSupport.class);
		
		SystemConfig systemConfig=new SystemConfig();
		SystemConfigDto systemConfigDto=systemConfigSupport.getSystemConfig();
		systemConfig.setSiteUrl(systemConfigDto.getClientSiteUrl_243());
		
		systemConfig.setIsLoginFailureLock(systemConfigDto.getIsLoginFailureLock());
		systemConfig.setLoginFailureLockCount(systemConfigDto.getLoginFailureLockCount());
		systemConfig.setLoginFailureLockTime(systemConfigDto.getLoginFailureLockTime());
		
		systemConfig.setCookiePath(systemConfigDto.getCookiePath());
		systemConfig.setCookieDomain(systemConfigDto.getCookieDomain());
		systemConfig.setFtpUrl(systemConfigDto.getFtpUrl());
		return systemConfig;
	}
}
