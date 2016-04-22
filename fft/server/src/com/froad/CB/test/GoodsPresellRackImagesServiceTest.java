package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.CB.dao.impl.GoodsPresellRackImagesDaoImpl;
import com.froad.CB.po.GoodsPresellRackImages;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsPresellRackImagesServiceTest {
	@Resource
	private GoodsPresellRackImagesDaoImpl presellRackImagesDaoImpl;
	
	
	@Test
	public void add(){
		GoodsPresellRackImages goodsPresellRackImages=new GoodsPresellRackImages();
		goodsPresellRackImages.setGoodsPresellRackId("2001010132");
		goodsPresellRackImages.setImagesUrl("localhost:8080//Test");
		goodsPresellRackImages.setRemark("afwg23r23e");
		System.out.println(presellRackImagesDaoImpl.add(goodsPresellRackImages));
		JSONObject.toJSONString(presellRackImagesDaoImpl.getById(100001001));
	}
	@Test
	public void updateById(){
		GoodsPresellRackImages goodsPresellRackImages=new GoodsPresellRackImages();
		goodsPresellRackImages.setId(100001001);
		goodsPresellRackImages.setState("10");
		goodsPresellRackImages.setImagesUrl("D:/loacl:/images");
		goodsPresellRackImages.setRemark("remark");
		System.out.println(presellRackImagesDaoImpl.updateById(goodsPresellRackImages));
		
	}
	

}
