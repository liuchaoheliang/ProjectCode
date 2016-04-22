package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.froad.CB.po.Seller;
import com.froad.CB.service.impl.SellersServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SellersServiceImplTest {
	@Resource
	private SellersServiceImpl sellersServiceImpl;
	
	@Test
	public void testAddSeller() {
		Seller seller = new Seller();
		seller.setRemark("备注信息");
		seller.setState("20");
		seller.setMerchantId("100001001");
		seller.setUserId("c3bb0ee3c8085601255a45bd369b3593");
		sellersServiceImpl.addSeller(seller);
	}

	@Test
	public void testUpdateById() {
		Seller seller = new Seller();
		Integer id=100001001;
		seller.setId(id);
		seller.setState("30");
		sellersServiceImpl.updateById(seller);
	}

	@Test
	public void testSelectById() {
		Integer id=100001005;
		Seller seller=sellersServiceImpl.selectById(id);
		System.out.println("seller===>"+seller.getSellerChannelList().get(0).getAccountName());
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		sellersServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetSellerByUserId() {
		String userId="org_fft";
		List<Seller> seller=sellersServiceImpl.getSellerByUserId(userId);
		System.out.println("seller: "+seller);
	}

	@Test
	public void testGetSellerByMerchantId() {
		String merchantId="100001003";
		List<Seller> sellerList=sellersServiceImpl.getSellerByMerchantId(merchantId);
		System.out.println("seller  "+sellerList);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		sellersServiceImpl.deleteById(id);
	}
}
