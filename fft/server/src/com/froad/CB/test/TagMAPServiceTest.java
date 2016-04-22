package com.froad.CB.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.tag.TagMAP;
import com.froad.CB.service.impl.TagMapServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TagMAPServiceTest {

	@Resource
	private TagMapServiceImpl tagMapServiceImpl;
	
	
	@Test
	public void insetBatch(){
		List list=new ArrayList();
		
		TagMAP tagMap1=new TagMAP();
		tagMap1.setMerchantId("100001124");
		tagMap1.setTagClassifyAId("100001004");
		tagMap1.setTagDistrictAId("100001001");
		list.add(tagMap1);
		
		TagMAP tagMap2=new TagMAP();
		tagMap2.setMerchantId("100001124");
		tagMap2.setTagClassifyAId("100001003");
		tagMap2.setTagDistrictAId("100001001");
		list.add(tagMap2);
		
		tagMapServiceImpl.insertBatch(list);
	}
	
}
