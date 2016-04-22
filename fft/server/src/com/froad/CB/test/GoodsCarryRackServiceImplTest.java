package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsCarryRack;
import com.froad.CB.service.impl.GoodsCarryRackServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsCarryRackServiceImplTest {
	@Resource
	private GoodsCarryRackServiceImpl goodsCarryRackServiceImpl;
	
	@Test
	public void testAddGoodsCarryRack() {
		GoodsCarryRack goodsCarryRack = new GoodsCarryRack();
		goodsCarryRack.setBankpointCashPricing("1000|100");
		goodsCarryRack.setBankpointcashRatioPricing("0.1");
		goodsCarryRack.setBankPointPricing("10000");
		goodsCarryRack.setCashPricing("100");
		goodsCarryRack.setFftpointCashPricing("5000|50");
		goodsCarryRack.setFftpointcashRatioPricing("0.1");
		goodsCarryRack.setFftPointPricing("50000");
		goodsCarryRack.setGoodsId("100001001");
		goodsCarryRack.setInspectors("lqp");
		goodsCarryRack.setIsBankPoint("0");
		goodsCarryRack.setIsBankpointCash("0");
		goodsCarryRack.setIsBankpointcashRatioPricing("0");
		goodsCarryRack.setIsCash("1");
		goodsCarryRack.setIsFftPoint("0");
		goodsCarryRack.setIsFftpointCash("0");
		goodsCarryRack.setIsFftpointcashRatioPricing("0");
		goodsCarryRack.setIsRack("1");
		goodsCarryRack.setRackTime("2013-02-01|08:45:21");
		goodsCarryRack.setState("20");
		goodsCarryRackServiceImpl.addGoodsCarryRack(goodsCarryRack);
	}

	@Test
	public void testUpdateById() {
		GoodsCarryRack goodsCarryRack = new GoodsCarryRack();
		Integer id=100001001;
		goodsCarryRack.setId(id);
		goodsCarryRack.setState("30");
		goodsCarryRackServiceImpl.updateById(goodsCarryRack);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		goodsCarryRackServiceImpl.selectById(id);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		goodsCarryRackServiceImpl.deleteById(id);
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		goodsCarryRackServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetGoodsCarryRackByGoodsId() {
		String goodsId="100001001";
		goodsCarryRackServiceImpl.getGoodsCarryRackByGoodsId(goodsId);
	}

	@Test
	public void testGetGoodsCarryRack() {
		GoodsCarryRack goodsCarryRack = new GoodsCarryRack();
		goodsCarryRack.setState("50");
		goodsCarryRackServiceImpl.getGoodsCarryRack(goodsCarryRack);
	}

	@Test
	public void testGetGoodsCarryRackListByPager() {
		GoodsCarryRack goodsCarryRack = new GoodsCarryRack();
		goodsCarryRack.setState("50");
		goodsCarryRackServiceImpl.getGoodsCarryRackListByPager(goodsCarryRack);
	}

}
