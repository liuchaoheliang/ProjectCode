package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsGroupRack;
import com.froad.CB.service.impl.GoodsGroupRackServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class GoodsGroupTest {

	@Resource
	private GoodsGroupRackServiceImpl goodsGroupRackServiceImpl;
	
	@Test
	public void queryTest(){
		Integer id=100001478;
		GoodsGroupRack group=goodsGroupRackServiceImpl.getGoodsGroupRackById(id);
		System.out.println("goodsname: "+group);
	}
}
