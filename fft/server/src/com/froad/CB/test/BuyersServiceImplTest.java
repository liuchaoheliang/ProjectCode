package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.Buyers;
import com.froad.CB.service.impl.BuyersServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class BuyersServiceImplTest {
	@Resource
	private BuyersServiceImpl buyersServiceImpl;
	
	@Test
	public void testAddBuyer() {
		Buyers buyer = new Buyers();
		buyer.setRemark("备注信息");
		buyer.setState("20");
		buyer.setUserId("c3bb0ee3c8085601255a45bd369b3593");
		buyersServiceImpl.addBuyer(buyer);
	}

	@Test
	public void testUpdateById() {
		Integer id=100001001;
		Buyers buyer = new Buyers();
		buyer.setId(id);
		buyer.setState("30");
		buyersServiceImpl.updateById(buyer);
	}

	@Test
	public void testGetById() {
		Integer id=100001001;
		buyersServiceImpl.getById(id);
	}

	@Test
	public void testDeleteById() {
		String id="100001002";
		buyersServiceImpl.deleteById(id);
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		buyersServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetBuyerByUserId() {
		String userId="c3bb0ee3c8085601255a45bd369b3593";
		buyersServiceImpl.getBuyerByUserId(userId);
	}

}
