package com.froad.CB.test.trans;

import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.constant.PayState;
import com.froad.CB.common.constant.RuleDetailCategory;
import com.froad.CB.dao.impl.PayDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.service.impl.PayServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class PayServiceTest{

	@Resource
	private PayServiceImpl payServiceImpl;
	
	@Resource
	private PayDaoImpl payDaoImpl;
	
	@Test
	public void addPay() {
		Pay pay=new Pay();
		pay.setTransId("1001");
		pay.setTrackNo("100001");
		pay.setType("1000");//1000 资金 2000 积分
		pay.setFromAccountName("张三");
		pay.setFromAccountNo("62222021001");
		pay.setPayValue("1");
		pay.setToAccountName("李三");
		pay.setToAccountNo("622201111111");
		pay.setStep("1");
		
		payServiceImpl.addPay(pay);
	}

	@Test
	public void updatePay(){
		Pay pay=new Pay();
		pay.setTransId("100001121");
		pay.setType(RuleDetailCategory.Points.getValue());
		pay.setState(PayState.POINT_PAY_SUCCESS);
		pay.setResultCode("0000");
		payDaoImpl.updatePay(pay);
		System.out.println("完成");
	}
	
	@Test
	public void deleteById() {
		Integer id=100001001;
		
		payServiceImpl.deleteById(id);
	}
	
	

	@Test
	public void getPayById() {
		Integer id=100001001;
		
		Pay pay=payServiceImpl.getPayById(id);
		System.out.println("accountName: "+pay.getFromAccountName());
	}

	@Test
	public void updateById() {
		Pay pay=new Pay();
		pay.setId(100001001);
		pay.setState("30");
		pay.setType("2000");
		pay.setFromAccountName("张五");
		
		payServiceImpl.updateById(pay);
	}

	@Test
	public void updateStateById() {
		Integer id=100001001;
		String state="50";
		
		payServiceImpl.updateStateById(id, state);
	}


	@Test
	public void getAllWithoutPays() {	
		List<Pay> list=payDaoImpl.getAllWithoutPays();
		System.out.println("+++++++++++++++++++++++"+list.size());
	}
	
}
