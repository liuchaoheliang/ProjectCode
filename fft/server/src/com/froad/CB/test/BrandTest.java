package com.froad.CB.test;

import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.Brand;
import com.froad.CB.service.BrandService;
import com.froad.util.DateUtil;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class BrandTest {
	
	@Autowired
	private BrandService brandService;
	
	@org.junit.Test
	public void addBrand(){
		String new_date=DateUtil.formatDate2Str(new Date());
		
		Brand brand=new Brand();
		brand.setName("三星");
		brand.setUrl("http://www.samsung.com");
		brand.setBrandDesc("三星现在是最大的手机厂商");
		brand.setOrderList("50");
		brand.setState("30");
		Integer id=brandService.addBrand(brand);
		System.out.println("保存ID:"+id);
	}
	
	@org.junit.Test
	public void getBrandById(){
		Brand brand=brandService.getBrandById(100001001);
	    System.out.println("品牌名称："+brand.getName());
	}
	
	@org.junit.Test
	public void updateBrand(){
		Brand brand=new Brand();
		brand.setId(100001001);
		brand.setRemark("测试更新");
		boolean result=brandService.updateBrand(brand);
		System.out.println("更新结果："+result);
	}
	
	@org.junit.Test
	public void getBrandList(){
		Brand brand=new Brand();
		//brand.setId(100001001);
		brand.setRemark("测试");
		
		List<Brand> brandList=brandService.getBrandList(brand);
		System.out.println("结果："+brandList==null ? "null" : brandList.size());
	}
	
	@org.junit.Test
	public void getAllBrand(){
		List<Brand> brandList=brandService.getAllBrand();
		System.out.println("结果："+brandList.size());
	}
}
