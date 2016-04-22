package com.froad.cbank.coremodule.module.normal.bank.test;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.cbank.coremodule.module.normal.bank.service.FinitRoleService;
import com.froad.cbank.coremodule.module.normal.bank.vo.RoleReq;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class HessianTest {

	@Test
	public void main(){
		FinitRoleService finitRoleService =  new FinitRoleService();
		RoleReq role = new RoleReq();
		role.setRoleId(100000000l);
		try {
			finitRoleService.getResourceList(role, "0");
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}