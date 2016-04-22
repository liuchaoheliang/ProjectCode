package com.froad.CB.test;

import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsType;
import com.froad.CB.service.GoodsTypeService;
import com.froad.util.DateUtil;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class GoodsTypeTest {
	
	@Autowired
	private GoodsTypeService goodsTypeService;
	
	@org.junit.Test
	public void addGoodsType(){
		GoodsType goodsType=new GoodsType();
		goodsType.setName("笔记本");
		goodsType.setState("30");
		Integer id=goodsTypeService.addGoodsType(goodsType);
		System.out.println("保存ID:"+id);
	}
	
	@org.junit.Test
	public void getGoodsTypeById(){
		GoodsType goodsType=goodsTypeService.getGoodsTypeById(100001001);
		System.out.println("名称："+goodsType.getName());
	}
	
	@org.junit.Test
	public void updateGoodsType(){
		GoodsType goodsType=new GoodsType();
		goodsType.setId(100001001);
		goodsType.setRemark("更新测试");
		boolean result=goodsTypeService.updateGoodsType(goodsType);
		System.out.println("结果："+result);
	}
	
	@org.junit.Test
	public void getGoodsTypeList(){
		GoodsType goodsType=new GoodsType();
		goodsType.setId(100001001);
		
		List<GoodsType> goodsTypeList=goodsTypeService.getGoodsTypeList(goodsType);
		System.out.println("结果："+goodsTypeList.size());
	}
	
	@org.junit.Test
	public void getAllGoodsType(){
		List<GoodsType> goodsTypeList=goodsTypeService.getAllGoodsType();
		System.out.println("结果："+goodsTypeList.size());
	}
	
}
