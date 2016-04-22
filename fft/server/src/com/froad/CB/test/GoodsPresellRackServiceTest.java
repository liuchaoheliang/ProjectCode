package com.froad.CB.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.dao.impl.GoodsPresellRackDaoImpl;
import com.froad.CB.po.GoodsPresellRack;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsPresellRackServiceTest {
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");

	
	@Resource
	private GoodsPresellRackDaoImpl goodsPresellRackDaoImpl;
	
	@Test
	public void add(){
		GoodsPresellRack goodsPresellRack=new GoodsPresellRack();
		goodsPresellRack.setGoodsId("123123113");
		goodsPresellRack.setMerchantId("merchantId");
		goodsPresellRack.setGroupPrice("33.00");
		goodsPresellRack.setBeginTime(sdf.format(new Date()));
		goodsPresellRack.setEndTime(sdf.format(new Date()));
		goodsPresellRack.setIsAllowrefund("0");
		goodsPresellRack.setClusteringNum(1);
		goodsPresellRack.setTrueBuyerNum(2);
		goodsPresellRack.setVirtualBuyerNum(3);
		goodsPresellRack.setIsCluster("isCluster");
		goodsPresellRack.setClusterType("clusterType");
		goodsPresellRack.setSeoTitle("seoTitle");
		goodsPresellRack.setSeoKeyword("seoKeyword");
		goodsPresellRack.setSeoDescription("seoDescription");
		goodsPresellRack.setIsRack("isRack");
		goodsPresellRack.setRackTime("rackTime");
		goodsPresellRack.setMaxSaleNum(33);
		
		System.out.println(goodsPresellRackDaoImpl.add(goodsPresellRack));
		
	}
	
	
	@Test
	public void update(){
		GoodsPresellRack goodsPresellRack=new GoodsPresellRack();
		goodsPresellRack.setId(100001006);
//		goodsPresellRack.setGoodsId("re123123113");
//		goodsPresellRack.setMerchantId("remerchantId");
//		goodsPresellRack.setGroupPrice("333.00");
//		goodsPresellRack.setBeginTime(sdf.format(new Date()));
//		goodsPresellRack.setEndTime(sdf.format(new Date()));
//		goodsPresellRack.setIsAllowrefund("1");
//		goodsPresellRack.setClusteringNum(1);
//		goodsPresellRack.setTrueBuyerNum(2);
//		goodsPresellRack.setVirtualBuyerNum(3);
//		goodsPresellRack.setIsCluster("reisCluster");
//		goodsPresellRack.setClusterType("reclusterType");
//		goodsPresellRack.setSeoTitle("reseoTitle");
//		goodsPresellRack.setSeoKeyword("reseoKeyword");
//		goodsPresellRack.setSeoDescription("reseoDescription");
//		goodsPresellRack.setIsRack("0");
//		goodsPresellRack.setRackTime("rerackTime");
//		goodsPresellRack.setState("10");
		goodsPresellRack.setDeliveryStartTime("2014-04-14 13:00:00");
		goodsPresellRack.setDeliveryEndTime("2014-04-15 14:00:00");
		
		System.out.println(goodsPresellRackDaoImpl.updateById(goodsPresellRack));
	}
	
	
	@Test
	public void getBypager(){
		GoodsPresellRack goodsPresellRack=new GoodsPresellRack();
		goodsPresellRack =goodsPresellRackDaoImpl.getByPager(goodsPresellRack);
		System.out.println(goodsPresellRack.getList().size());
	}
	
	@Test
	public void geyHistory(){
		GoodsPresellRack goodsPresellRack=new GoodsPresellRack();
		goodsPresellRack.setPageSize(5);
		List<GoodsPresellRack> list=goodsPresellRackDaoImpl.getHistory(goodsPresellRack);
		System.out.println(list.size());
	}

}
