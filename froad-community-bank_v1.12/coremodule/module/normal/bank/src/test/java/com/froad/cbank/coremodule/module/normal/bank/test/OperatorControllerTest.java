package com.froad.cbank.coremodule.module.normal.bank.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.controller.OperatorController;
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantManageService;
import com.froad.thrift.service.FinityUserResourceService;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.ResourceVo;
import com.froad.thrift.vo.finity.UserResourceVo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml","classpath:/springmvc-thrift.xml"})
public class OperatorControllerTest {
	
	@Resource
	MerchantRoleResourceService.Iface merchantRoleResourceService;
	
	@Resource
	FinityUserResourceService.Iface finityUserResourceService;
	


	@Test
	public void addFinityUserResource() throws TException{
		Long userId = 100383885L;
		String clientId = "chongqing";
		LogCvt.info("新增商户资源数据开始----"+userId);
		//商户新增成功，需要绑定商户用户-资源，超级管理员默认绑定所有资源信息 
		List<UserResourceVo> userResourceVoList=new ArrayList<UserResourceVo>(); 
		MerchantRoleResourceVo merchantRoleResourceVo= merchantRoleResourceService.getMerchantRoleResource(clientId+"_999999999");
		if(merchantRoleResourceVo!=null && merchantRoleResourceVo.getResources()!=null && merchantRoleResourceVo.getResources().size()>0){
			for(ResourceVo vo:merchantRoleResourceVo.getResources()){ 
				UserResourceVo urVo=new UserResourceVo();
				urVo.setResourceId(vo.getResource_id());
				urVo.setUserId(userId);
				urVo.setUserType(1);//商户用户
				userResourceVoList.add(urVo); 
			}
			finityUserResourceService.addUserResourceByBatch(userResourceVoList);//批量新建商户资源关系end
			LogCvt.info("新增商户资源数据开始----end : "+userResourceVoList.size());
		} 
		
	}

}