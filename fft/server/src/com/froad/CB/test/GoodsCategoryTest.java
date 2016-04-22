package com.froad.CB.test;

import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsCategory;
import com.froad.CB.service.GoodsCategoryService;
import com.froad.util.DateUtil;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class GoodsCategoryTest {
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@org.junit.Test
	public void addGoodsCategory(){
		String new_date=DateUtil.formatDate2Str(new Date());
		
		GoodsCategory goodsCategory=new GoodsCategory();
		goodsCategory.setName("家用电器");
		goodsCategory.setDescription("这里是描述");
		goodsCategory.setOrderList("10");
		goodsCategory.setState("30");
		goodsCategory.setCreateTime(new_date);
		goodsCategory.setUpdateTime(new_date);
		Integer id=goodsCategoryService.addGoodsCategory(goodsCategory);
		System.out.println("保存ID:"+id);
	}
	
	@org.junit.Test
	public void getGoodsCategoryById(){
		GoodsCategory goodsCategory=goodsCategoryService.getGoodsCategoryById(100001001);
		System.out.println("名称:"+goodsCategory.getName());
	}
	
	@org.junit.Test
	public void updateGoodsCategory(){
		GoodsCategory goodsCategory=new GoodsCategory();
		goodsCategory.setId(100001001);
		goodsCategory.setRemark("更新测试");
		System.out.println("结果:"+goodsCategoryService.updateGoodsCategory(goodsCategory));
	}
	
	@org.junit.Test
	public void getRootGoodsCategoryList(){
		List<GoodsCategory> rootGoodsCategoryList=goodsCategoryService.getRootGoodsCategoryList();
		System.out.println("结果:"+rootGoodsCategoryList.size());
	}
	
	@org.junit.Test
	public void getChildrenGoodsCategoryList(){
		
		GoodsCategory goodsCategory=goodsCategoryService.getGoodsCategoryById(100001001);
		List<GoodsCategory> childrenGoodsCategoryList=goodsCategoryService.getChildrenGoodsCategoryList(goodsCategory);
		
		System.out.println("结果:"+childrenGoodsCategoryList.size());
	}
	
	@org.junit.Test
	public void getAllGoodsCategory(){
		List<GoodsCategory> list=goodsCategoryService.getAllGoodsCategory();
		GoodsCategory category=null;
		for (int i = 0; i < list.size(); i++) {
			category=list.get(i);
			System.out.println("public static final String CATEGORY_HFCZ=\""+category.getId()+"\";//"+category.getName());
		}
	}
	
	
}
