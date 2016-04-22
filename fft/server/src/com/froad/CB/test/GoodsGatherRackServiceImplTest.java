package com.froad.CB.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.froad.CB.po.GoodsGatherRack;
import com.froad.CB.service.impl.GoodsGatherRackServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsGatherRackServiceImplTest {
	@Resource
	private GoodsGatherRackServiceImpl goodsGatherRackServiceImpl;
	
	@Test
	public void testAddGoodsGatherRack() {
		GoodsGatherRack goodsGatherRack = new GoodsGatherRack();
		goodsGatherRack.setCashPricing("100");
		goodsGatherRack.setGoodsId("100001001");
		goodsGatherRack.setInspectors("lqp");
		goodsGatherRack.setIsCash("1");
		goodsGatherRack.setIsRack("1");
		goodsGatherRack.setMarketTotalNumber("100");
		goodsGatherRack.setRackTime("2013-02-01|08:45:21");
		goodsGatherRack.setState("20");
		goodsGatherRackServiceImpl.addGoodsGatherRack(goodsGatherRack);
	}

	@Test
	public void testUpdateById() {
		GoodsGatherRack goodsGatherRack = new GoodsGatherRack();
		Integer id=100001001;
		goodsGatherRack.setId(id);
		goodsGatherRackServiceImpl.updateById(goodsGatherRack);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		goodsGatherRackServiceImpl.selectById(id);
	}

	

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		goodsGatherRackServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetGoodsGatherRackByGoodsId() {
		Integer goodsId=100001001;
		goodsGatherRackServiceImpl.selectById(goodsId);
	}

	@Test
	public void testGetGoodsGatherRack() {
		GoodsGatherRack goodsGatherRack = new GoodsGatherRack();
		goodsGatherRack.setState("50");
		goodsGatherRackServiceImpl.getGoodsGatherRack(goodsGatherRack);
	}

	@Test
	public void testGetGoodsGatherRackListByPager() {
		GoodsGatherRack goodsGatherRack = new GoodsGatherRack();
		goodsGatherRack.setState("50");
		goodsGatherRackServiceImpl.getGoodsGatherRackListByPager(goodsGatherRack);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		goodsGatherRackServiceImpl.deleteById(id);
	}
}
