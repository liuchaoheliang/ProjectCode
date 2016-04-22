package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.SellerChannel;
import com.froad.CB.service.impl.SellerChannelServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SellerChannelServiceImplTest {
	@Resource
	private SellerChannelServiceImpl sellerChannelServiceImpl;
	@Test
	public void testAddSellerChannel() {
		SellerChannel sellerChannel = new SellerChannel();
		sellerChannel.setAccountName("countName");
		sellerChannel.setAccountNumber("11111111111111");
		sellerChannel.setSellerId("100001001");
		sellerChannel.setSellerRuleId("100001001");
		sellerChannel.setMerchantId("100001001");
		sellerChannel.setChannelId("100001001");
		sellerChannel.setIsDefault("1");
		sellerChannel.setRemark("备注");
		sellerChannel.setState("20");
		sellerChannel.setUserId("c3bb0ee3c8085601255a45bd369b3593");
		sellerChannelServiceImpl.addSellerChannel(sellerChannel);
	}

	@Test
	public void testUpdateById() {
		SellerChannel sellerChannel = new SellerChannel();
		Integer id=100001001;
		sellerChannel.setId(id);
		sellerChannel.setState("30");
		sellerChannelServiceImpl.updateById(sellerChannel);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		sellerChannelServiceImpl.selectById(id);
	}

	

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		sellerChannelServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetSellerChannelByUserId() {
		String userId="100001001";
		sellerChannelServiceImpl.getSellerChannelByUserId(userId);
	}

	@Test
	public void testGetSellerChannelByMerchantId() {
		String merchantId="100001003";
		List<SellerChannel> channel=sellerChannelServiceImpl.getSellerChannelByMerchantId(merchantId);
		System.out.println("channel: "+channel);
	}

	@Test
	public void testGetSellerChannelBySellerId() {
		String sellerId="100001001";
		sellerChannelServiceImpl.getSellerChannelBySellerId(sellerId);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		sellerChannelServiceImpl.deleteById(id);
	}
}
