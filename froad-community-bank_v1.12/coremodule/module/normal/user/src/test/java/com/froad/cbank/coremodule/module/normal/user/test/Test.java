package com.froad.cbank.coremodule.module.normal.user.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.MessageServiceClient;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.service.UserSpecService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/conf/soft/spring/spring-config.xml","classpath:/conf/soft/spring/spring-expand-hessian.xml"})
public class Test {
	
	@Resource
	private UserSpecService userService;
	
	/**
	 *@description 
	 *@author Liebert
	 *@date 2015年5月2日 下午1:47:38
	 */
	public static void main(String[] args) {
		String key = "170bb30d7159ee89ef92352ca8c275cd9f7e3e53f6c6951b4691d6d93f0763f1";
		String decKey = DESUtil.decrypt(key);
		long auditTime = Long.valueOf(decKey.substring(0,13));
		long memberCode = Long.parseLong(decKey.substring(13));
		
		System.out.println(new Date(auditTime));
		//是否过期
		if(new Date().after(DateUtils.addDays(new Date(auditTime), 1))){
			System.out.println("已过期");
		}
		
	}
	
}
