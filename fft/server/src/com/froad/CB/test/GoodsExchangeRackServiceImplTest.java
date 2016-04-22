package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.CB.po.GoodsExchangeRack;
import com.froad.CB.service.impl.GoodsExchangeRackServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-3  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsExchangeRackServiceImplTest {
	@Resource
	private GoodsExchangeRackServiceImpl goodsExchangeRackServiceImpl;
	
	@Test
	public void testAddGoodsExchangeRack() {
		GoodsExchangeRack goodsExchangeRack = new GoodsExchangeRack();
		goodsExchangeRack.setBankpointCashPricing("1000|100");
		goodsExchangeRack.setBankpointcashRatioPricing("0.1");
		goodsExchangeRack.setBankPointPricing("10000");
		goodsExchangeRack.setCashPricing("100");
		goodsExchangeRack.setFftpointCashPricing("5000|50");
		goodsExchangeRack.setFftpointcashRatioPricing("0.1");
		goodsExchangeRack.setFftPointPricing("50000");
		goodsExchangeRack.setGoodsId("100001001");
		goodsExchangeRack.setInspectors("lqp");
		goodsExchangeRack.setIsBankPoint("0");
		goodsExchangeRack.setIsBankpointCash("0");
		goodsExchangeRack.setIsBankpointcashRatioPricing("0");
		goodsExchangeRack.setIsCash("1");
		goodsExchangeRack.setIsFftPoint("0");
		goodsExchangeRack.setIsFftpointCash("0");
		goodsExchangeRack.setIsFftpointcashRatioPricing("0");
		goodsExchangeRack.setIsRack("1");
		goodsExchangeRack.setRackTime("2013-02-01|08:45:21");
		goodsExchangeRack.setState("20");
		goodsExchangeRack.setIsPresentPoints(1);
		goodsExchangeRackServiceImpl.addGoodsExchangeRack(goodsExchangeRack);
	}

	@Test
	public void testUpdateById() {
		GoodsExchangeRack goodsExchangeRack = new GoodsExchangeRack();
		Integer id=100001205;
		goodsExchangeRack.setId(id);
		goodsExchangeRack.setState("30");
		goodsExchangeRack.setIsPresentPoints(0);
		goodsExchangeRackServiceImpl.updateById(goodsExchangeRack);
	}

	@Test
	public void testSelectById() {
		Integer id=100001002;
		GoodsExchangeRack rack=goodsExchangeRackServiceImpl.selectById(id);
		System.out.println("rack: "+rack.getGoods());
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		goodsExchangeRackServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetGoodsExchangeRackByGoodsId() {
		String goodsId="100001001";
		System.out.println(JSONObject.toJSONString(goodsExchangeRackServiceImpl.getGoodsExchangeRackByGoodsId(goodsId)));
	}

	@Test
	public void testGetGoodsExchangeRack() {
		GoodsExchangeRack goodsExchangeRack = new GoodsExchangeRack();
		goodsExchangeRack.setState("50");
		goodsExchangeRackServiceImpl.getGoodsExchangeRack(goodsExchangeRack);
	}

	@Test
	public void testGetGoodsExchangeRackListByPager() {
		GoodsExchangeRack goodsExchangeRack = new GoodsExchangeRack();
		goodsExchangeRack.setPageSize(9);
//		goodsExchangeRack.setState("50");
//		goodsExchangeRack.setGoodsCategoryId("100001005");
		GoodsExchangeRack rack=goodsExchangeRackServiceImpl.getGoodsExchangeRackListByPager(goodsExchangeRack);
		List<?> list=rack.getList();
		GoodsExchangeRack tmp=null;
		for (int i = 0; i <list.size(); i++) {
			tmp=(GoodsExchangeRack)list.get(i);
			System.out.println("list.size=="+tmp);
		}
	}
	
	@Test
	public void testDeleteById() {
		String id="100001001";
		goodsExchangeRackServiceImpl.deleteById(id);
	}

}
