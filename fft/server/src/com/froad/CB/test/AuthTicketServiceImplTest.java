package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.AuthTicket;
import com.froad.CB.service.impl.AuthTicketServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AuthTicketServiceImplTest {
	
	@Resource
	private AuthTicketServiceImpl authTicketServiceImpl;

	@Test
	public void getBySelective(){
		AuthTicket authTicket=new AuthTicket();
		authTicket.setMerchantId("100001043");
		authTicket.setSecuritiesNo("47517714526018");
		List<AuthTicket> list=authTicketServiceImpl.getAuthTickBySelective(authTicket);
		System.out.println("list============="+list);
		if(list!=null&&list.size()>0){
			System.out.println("list============="+list.get(0).getCreateTime());
		}
	}
}
