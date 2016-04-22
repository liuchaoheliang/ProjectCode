package com.froad.CB.test;

import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsGoodsAttributeMapStore;
import com.froad.CB.service.GoodsGoodsAttributeMapStoreService;
import com.froad.util.DateUtil;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class GoodsGoodsAttributeMapStoreTest {
	
	@Autowired
	private GoodsGoodsAttributeMapStoreService goodsGoodsAttributeMapStoreService;
	
	@org.junit.Test
	public void addGoodsGoodsAttributeMapStore(){
		
		GoodsGoodsAttributeMapStore goodsGoodsAttributeMapStore=new GoodsGoodsAttributeMapStore();
		
		goodsGoodsAttributeMapStoreService.addGoodsGoodsAttributeMapStore(goodsGoodsAttributeMapStore);
	}
}
