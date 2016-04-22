package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.Command;
import com.froad.CB.dao.BuyerChannelDao;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.service.impl.BuyerChannelServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class BuyerChannelServiceImplTest {
	@Resource
	private BuyerChannelServiceImpl buyerChannelServiceImpl;
	
	@Resource
	private BuyerChannelDao buyerChannelDao;
	
	@Test
	public void testAddBuyerChannel() {
		BuyerChannel buyerChannel = new BuyerChannel();
		buyerChannel.setAccountName("countName");
		buyerChannel.setAccountNumber("11111111111111");
		buyerChannel.setBuyersId("100001001");
		buyerChannel.setBuyersRuleId("100001001");
		buyerChannel.setChannelId("100001001");
		buyerChannel.setIsDefault("1");
		buyerChannel.setRemark("备注");
		buyerChannel.setState("20");
		buyerChannel.setUserId("c3bb0ee3c8085601255a45bd369b3593");
		buyerChannelServiceImpl.addBuyerChannel(buyerChannel);
	}

	@Test
	public void testUpdateById() {
		BuyerChannel buyerChannel = new BuyerChannel();
		Integer id = 1000010001;
		buyerChannel.setId(id);
		buyerChannel.setAccountName("name");
		buyerChannel.setAccountNumber("12121212122222222222221");
		buyerChannelServiceImpl.updateById(buyerChannel);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		buyerChannelServiceImpl.selectById(id);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		buyerChannelServiceImpl.deleteById(id);
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		buyerChannelServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetBuyerChannelByUserId() {
		String userId="c3bb0ee3c8085601255a45bd369b3593";
		buyerChannelServiceImpl.getBuyerChannelByUserId(userId);
	}

	@Test
	public void testUpdateChannel(){
		BuyerChannel buyerChannel=new BuyerChannel();
		buyerChannel.setBuyersId("100001004");
		buyerChannel.setChannelId("100001001");
		buyerChannel.setAccountName("李振荣");
		buyerChannel.setAccountNumber("0000057877881889");
		buyerChannel.setPhone("13111111111");
		buyerChannel.setState(Command.STATE_START);
		
		int rows=buyerChannelDao.updateChannelByBuyerId(buyerChannel);
		System.out.println(rows);

	}
	
	public void setBuyerChannelDao(BuyerChannelDao buyerChannelDao) {
		this.buyerChannelDao = buyerChannelDao;
	}

}
