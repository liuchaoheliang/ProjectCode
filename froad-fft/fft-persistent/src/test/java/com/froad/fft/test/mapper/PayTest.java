
	 /**
  * 文件名：PayTest.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.impl.PayMapperImpl;
import com.froad.fft.persistent.common.enums.PayRole;
import com.froad.fft.persistent.common.enums.PayState;
import com.froad.fft.persistent.common.enums.PayType;
import com.froad.fft.persistent.common.enums.PayTypeDetails;
import com.froad.fft.persistent.entity.Pay;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午6:09:10 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class PayTest {
	@Resource
	PayMapperImpl payMapperImpl;
	Pay pay=new Pay();
	Pay pay1=new Pay();
	
	
	@Test
	public void add(){
//		pay.setCreateTime(new Date());
//		pay.setUpdateTime(new Date());
		pay.setPayOrg("210");
		pay.setSn("sn");
		pay.setTransId(123123L);
		pay.setBillNo("1231234");
		pay.setFromAccountName("asdad");
		pay.setFromAccountNo("213123");
		pay.setPayValue("23");
		pay.setPayType(PayType.cash);
		pay.setPayTypeDetails(PayTypeDetails.REBATE_POINTS);
		pay.setStep(2);
		pay.setPayState(PayState.hfcz_request_success);		
		pay.setFromPhone("13038389921");
		pay.setRefundPointNo("123123123");
		pay.setFromRole(PayRole.member);
		pay.setRefundOrderId("1231231231");
		
		pay1.setPayOrg("110");
		pay1.setSn("resn");
		pay1.setTransId(12222L);
		pay1.setBillNo("111111");
		pay1.setFromAccountName("asdasdsdad");
		pay1.setFromAccountNo("44444");
		pay1.setPayValue("243");
		pay1.setPayType(PayType.points);
		pay1.setPayTypeDetails(PayTypeDetails.BUY_POINTS_FEE);
		pay1.setStep(2);
		pay1.setPayState(PayState.hfcz_request_success);		
		pay1.setFromPhone("13038389991");
		pay1.setRefundPointNo("2222222");
		pay1.setFromRole(PayRole.fft);
		pay1.setRefundOrderId("77777");
		List<Pay> list=new ArrayList<Pay>();
		list.add(pay);
		list.add(pay1);
		payMapperImpl.saveBatchPay(list);
		System.out.println("OK");
	}
	
	@Test
	public void add1(){
		pay.setCreateTime(new Date());
		pay.setUpdateTime(new Date());
		pay.setPayOrg("240");
		pay.setSn("sn");
		pay.setTransId(123123L);
		pay.setBillNo("1231234");
		pay.setFromAccountName("asdad");
		pay.setFromAccountNo("213123");
		pay.setPayValue("23");
		pay.setPayType(PayType.cash);
		pay.setPayTypeDetails(PayTypeDetails.REBATE_POINTS);
		pay.setStep(2);
		pay.setPayState(PayState.hfcz_request_success);
		
		pay.setFromPhone("13038389921");
		pay.setRefundPointNo("123123123");
		pay.setFromRole(PayRole.member);
		pay.setRefundOrderId("1231231231");
		long id=payMapperImpl.savePay(pay);
		System.out.println(id);
	}
	
	@Test
	public void update (){
		pay.setId(1L);
		pay.setCreateTime(new Date());
		pay.setUpdateTime(new Date());
		pay.setPayOrg("310");
		pay.setBillNo("1ddasd");
		pay.setFromAccountName("reasdad");
		pay.setFromAccountNo("re213123");
		pay.setPayValue("re23");
		pay.setPayType(PayType.points);
		pay.setPayTypeDetails(PayTypeDetails.BUY_POINTS_FEE);
		pay.setStep(3);
		pay.setPayState(PayState.lottery_success);
		
		pay.setFromPhone("re13038389921");
		pay.setRefundPointNo("re123123123");
		pay.setFromRole(PayRole.fft);
		pay.setRefundOrderId("re1231231231");
		boolean id=payMapperImpl.updatePayById(pay);
		System.out.println(id);
	}
	
}
