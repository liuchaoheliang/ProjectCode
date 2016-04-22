package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.common.FeatFormulaErr2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.Pager.OrderType;
import com.froad.CB.po.activity.SpringFestivalCoupon;
import com.froad.CB.service.activity.impl.SpringFestivalCouponServiceImpl;
import com.sun.corba.se.spi.orbutil.fsm.FSM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringFestivalCouponServiceTest {
	@Resource
	private SpringFestivalCouponServiceImpl festivalCouponServiceImpl;
	
	@Test
	public void addSpringFestivalCoupon(){
		SpringFestivalCoupon festivalCoupon=new SpringFestivalCoupon();
		festivalCoupon.setWorthCash("5元券");
		festivalCoupon.setBeCode("1000");
		festivalCoupon.setSecuritiesNo("12131323121312");
		festivalCoupon.setType("1");
		festivalCoupon.setIsGot("1");
		festivalCoupon.setIsConsume("0");
		festivalCoupon.setAwardLevel(3);
		festivalCoupon.setGotTime("2013-09-11 | 12:00:00");
		festivalCoupon.setBeName("操作员1");
		festivalCoupon.setMemberCode("100001231");
		festivalCoupon.setMerchantId("500003212");
		if(festivalCouponServiceImpl.addSpringFestivalCoupon(festivalCoupon)){
			System.out.println("Ok");
		}else{
			System.out.println("no");
		}
		
		
	}
	
	@Test
	public void getById(){
		SpringFestivalCoupon festivalCoupon=new SpringFestivalCoupon();
		festivalCoupon=festivalCouponServiceImpl.getSpringFestivalCouponById("100000101");
		System.out.println(festivalCoupon.getSecuritiesNo()+"============================");		
	}
	
	
	
	@Test
	public void getByCondition(){
		SpringFestivalCoupon festivalCoupon=new SpringFestivalCoupon();
		festivalCoupon.setAwardLevel(1);
		festivalCoupon.setSecuritiesNo("15568682729429");
		List<SpringFestivalCoupon> list=festivalCouponServiceImpl.getSpringFestivalCouponByCndition(festivalCoupon);
		System.out.println(list.size());
	}
	
	@Test
	public void updateById(){
		SpringFestivalCoupon festivalCoupon=new SpringFestivalCoupon();
		festivalCoupon.setId(100000001);
		festivalCoupon.setIsConsume("0");
		festivalCouponServiceImpl.updateSpringFestivalCouponById(festivalCoupon);
	}
	
	
	@Test
	public void getByPager(){
		SpringFestivalCoupon festivalCoupon=new SpringFestivalCoupon();
		festivalCoupon.setPageSize(5);
		festivalCoupon.setPageNumber(1);
		festivalCoupon.setAwardLevel(1);
		festivalCoupon.setOrderBy("id");
		festivalCoupon.setOrderType(OrderType.desc);
		festivalCoupon=festivalCouponServiceImpl.getSpringFestivalCouponByPager(festivalCoupon);	
//		System.out.println("==========="+festivalCoupon.getList().size());
		List<?> list=festivalCoupon.getList();
		for(Object l:list){
			SpringFestivalCoupon fs=(SpringFestivalCoupon) l;
			System.out.println("===="+fs.getId()+"======"+fs.getCreateTime());
		}
	}
}
