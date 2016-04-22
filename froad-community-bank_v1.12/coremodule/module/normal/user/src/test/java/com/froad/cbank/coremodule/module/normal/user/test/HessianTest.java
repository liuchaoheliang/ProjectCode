package com.froad.cbank.coremodule.module.normal.user.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.utils.PasswordProcessor;
import com.pay.user.dto.MemberApplySpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.ClientChannel;
import com.pay.user.service.UserOuterSpecService;
import com.pay.user.service.UserSpecService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class HessianTest {

	@Resource
	private UserSpecService userSpecService;
	
	@Resource
	private UserOuterSpecService userOuterSpecService;
	
	public static void main(String[] args) {
		String code = "ca33adbfbc368f677451cb230eff502d1e27a408992a69584691d6d93f0763f1";
		String decKey = DESUtil.decrypt(code);
		System.out.println(decKey);
		System.out.println(decKey.substring(0,13));
		System.out.println(decKey.substring(13));
		Long auditTime = Long.valueOf(decKey.substring(0,13));
		System.out.println(new Date(auditTime));
		
	}
	
	
	
	
	@Test
	public void testPasswordDecrypt(){
		String msg = "374DDAE3FDB25C36F6CA25AD01C73C0918EAD7B1307AE8E8921CFDE8C5CE0369613A4D90E5251F2100695BFA8CA5444ADC6B5C78B8F329D6B05EC6E572ED7F754226A359E75EE731963FEAF801F17C8DC4D761AED07320E0F34A51424D3DEE6E326BA60DD7763E03F6B162712AF77B6CA15238C8C5BB518D559DA08C9227AA8A";
		String decryptMsg = null;
		try {
			decryptMsg = PasswordProcessor.decrypt(msg);
		} catch (Exception e) {
			LogCvt.error("联合登录信息解密异常：" + msg ,e);
		}

		System.out.println(decryptMsg);
		
		
	}
}