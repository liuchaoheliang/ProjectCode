
	 /**
  * 文件名：TestMerchant.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.impl.MerchantExportServiceImpl;
import com.froad.fft.api.service.impl.UserEngineExportServiceImpl;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.service.MerchantService;
import com.froad.fft.service.impl.MerchantServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestUserEngine {
	
	@Resource
	UserEngineExportServiceImpl userEngineExportServiceImpl;
	
	@Test
	public void register(){
		UserEngineDto userEngineDto = new UserEngineDto("liuchao", "liuchao", "172.18.30.20", "13527459070", null, null);
		userEngineExportServiceImpl.registerUser(ClientAccessType.chongqing,ClientVersion.version_1_0,userEngineDto);
	}
	
}
