package com.froad.CB.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.Goods;
import com.froad.CB.service.GoodsService;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class GoodsTest {
	
	@Autowired
	private GoodsService goodsService;
	
	@org.junit.Test
	public void addGoodsType(){
		Goods goods=new Goods();
		goods.setPageNumber(1);
		goods.setPageSize(20);
		
		goods=goodsService.getGoodsByPager(goods);
		
		System.out.println(goods.getList().size());
	}
	
	
}
