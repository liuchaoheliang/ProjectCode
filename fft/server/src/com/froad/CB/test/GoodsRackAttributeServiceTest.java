package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsRackAttribute;
import com.froad.CB.service.impl.GoodsRackAttributeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class GoodsRackAttributeServiceTest {

	@Resource
	private GoodsRackAttributeServiceImpl goodsRackAttributeServiceImpl;
	
	@Test
	public void getAll(){
		List<GoodsRackAttribute> list=goodsRackAttributeServiceImpl.getGoodsRackAttributeList();
		GoodsRackAttribute attr=null;
		for (int i = 0; i <list.size(); i++) {
			attr=list.get(i);
			System.out.println("ALIAS_CODES.put("+attr.getAliasCode()+","+attr.getName()+")");
//			System.out.println("public static final String ID_PHONE_NO=\""+attr.getId()+"\";//"+attr.getName());
		}
	}
}
