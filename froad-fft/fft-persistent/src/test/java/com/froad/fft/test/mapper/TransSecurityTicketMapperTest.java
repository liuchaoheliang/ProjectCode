
	 /**
  * 文件名：TransSecurityTicketMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年4月8日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.TransSecurityTicketMapper;
import com.froad.fft.persistent.api.impl.TransSecurityTicketMapperImpl;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.TransSecurityTicket;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月8日 上午10:48:48 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class TransSecurityTicketMapperTest {
@Resource
private TransSecurityTicketMapperImpl securityTicketMapperImpl;

@Test
public void add(){
	TransSecurityTicket ticket=new TransSecurityTicket();
	ticket.setCreateTime(new Date());
	ticket.setUpdateTime(new Date());
	ticket.setTransType(TransType.presell);
	ticket.setSecuritiesNo("111111");
	ticket.setExpireTime(new Date());
	ticket.setMemberCode(12312313L);
	ticket.setSmsTime(new Date());
	ticket.setTransId(15L);
	ticket.setSysUserId(1L);
	ticket.setIsConsume(true);
	ticket.setConsumeTime(new Date());
	Long id=securityTicketMapperImpl.saveTransSecurityTicket(ticket);
	System.out.println(id);

}

@Test
public void update(){
	TransSecurityTicket ticket=new TransSecurityTicket();
	ticket.setId(2L);
	ticket.setTransType(TransType.presell);
	ticket.setSecuritiesNo("111111");
	ticket.setExpireTime(new Date());
	ticket.setMemberCode(12312313L);
	ticket.setTransId(15L);
	ticket.setSysUserId(1L);
	ticket.setIsConsume(true);
	ticket.setConsumeTime(new Date());
	boolean id=securityTicketMapperImpl.updateTransSecurityTicketById(ticket);
	System.out.println(id);
}
		
	
}
