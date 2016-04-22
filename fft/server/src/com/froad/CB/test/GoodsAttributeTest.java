package com.froad.CB.test;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsAttribute;
import com.froad.CB.service.GoodsAttributeService;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class GoodsAttributeTest {
	
	@Autowired
	private GoodsAttributeService goodsAttributeService;
	
	@org.junit.Test
	public void addGoodsAttribute(){
		GoodsAttribute goodsAttribute=new GoodsAttribute();
		goodsAttribute.setGoodsTypeId("100001001");
		goodsAttribute.setName("尺寸");
		goodsAttribute.setAttributeType("1");
		goodsAttribute.setIsRequired("0");
		goodsAttribute.setState("30");
		Integer id=goodsAttributeService.addGoodsAttribute(goodsAttribute);
		System.out.println("保存ID:"+id);
	}
	
	@org.junit.Test
	public void getGoodsAttributeById(){
		GoodsAttribute goodsAttribute=goodsAttributeService.getGoodsAttributeById(100001001);
		System.out.println("名称："+goodsAttribute.getName());
	}
	
	@org.junit.Test
	public void updateGoodsAttribute(){
		GoodsAttribute goodsAttribute=new GoodsAttribute();
		goodsAttribute.setId(100001001);
		goodsAttribute.setRemark("更新测试");
		
		boolean result=goodsAttributeService.updateGoodsAttribute(goodsAttribute);
		System.out.println("结果："+result);
	}
	
	@org.junit.Test
	public void getGoodsAttributeList(){
		
		GoodsAttribute goodsAttribute=new GoodsAttribute();
//		goodsAttribute.setId(100001001);
		goodsAttribute.setRemark("测试");
		List<GoodsAttribute> goodsAttributeList=goodsAttributeService.getGoodsAttributeList(goodsAttribute);
		System.out.println("结果："+goodsAttributeList.size());
	}
	
}
