package com.froad.fft.test.mapper;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.MerchantAccountMapper;
import com.froad.fft.persistent.api.impl.MerchantAccountMapperImpl;
import com.froad.fft.persistent.common.enums.AccountType;
import com.froad.fft.persistent.entity.ClientChannel;
import com.froad.fft.persistent.entity.MerchantAccount;


	 /**
 * 文件名：MerchantAccountMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月27日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 上午9:50:48 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class MerchantAccountMapperTest {

//	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/applicationContext-remote.xml");
//	MerchantAccountMapperImpl merchantAccountMapperImpl = (MerchantAccountMapperImpl) context.getBean("merchantAccountMapperImpl");	
	
	@Resource
	MerchantAccountMapperImpl merchantAccountMapperImpl;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	
	@Test	
	public void add(){
		MerchantAccount merchantAccount=new MerchantAccount();
		merchantAccount.setMerchantId(213123131241L);
		merchantAccount.setCreateTime((new Date()));
		merchantAccount.setUpdateTime(new Date());
		merchantAccount.setAcctName("acctName");
		merchantAccount.setAcctNumber("acctNumber");
		merchantAccount.setIsEnabled(true);
		merchantAccount.setAcctType(AccountType.collect);
		merchantAccount.setFundsChannelId(123142543545L);
		Long id= merchantAccountMapperImpl.saveMerchantAccount(merchantAccount);
		System.out.println(id);
	}
	
	@Test	
	public void select(){
		MerchantAccount merchantAccount1=merchantAccountMapperImpl.selectMerchantAccountById(3L);
		System.out.println(JSON.toJSONString(merchantAccount1));
	}
	
	@Test
	public void update(){
		MerchantAccount merchantAccount=new MerchantAccount();
		merchantAccount.setId(3L);
		merchantAccount.setMerchantId(213123131241L);
		merchantAccount.setCreateTime((new Date()));
		merchantAccount.setUpdateTime(new Date());
		merchantAccount.setAcctName("reacctName");
		merchantAccount.setAcctNumber("reacctNumber");
		merchantAccount.setIsEnabled(false);
		merchantAccount.setAcctType(AccountType.deduct);
		merchantAccount.setFundsChannelId(1232133321123L);
		boolean flag= merchantAccountMapperImpl.updateMerchantAccountById(merchantAccount);
		System.out.println(flag);
	}
	
}
