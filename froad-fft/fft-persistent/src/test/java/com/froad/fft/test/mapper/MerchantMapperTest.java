package com.froad.fft.test.mapper;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.MerchantMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.Refunds;


	 /**
 * 文件名：MerchantMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月28日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 上午11:25:44 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class MerchantMapperTest {
	@Resource
	MerchantMapperImpl merchantMapperImpl;
	
	
	@Test
	public void add(){
		Merchant merchant=new Merchant();
		merchant.setCreateTime(new Date());
		merchant.setUpdateTime(new Date());
		merchant.setName("name");
		merchant.setFullName("fullName");
		merchant.setLogo("logo");
		merchant.setAddress("address");
		merchant.setTel("581231214");
		merchant.setContactName("contactName");
		merchant.setContactPhone("contactPhone");
		merchant.setLegalName("legalName");
		merchant.setLegalCredentType("legalCredentType");
		merchant.setLegalCredentNo("legalCredentNo");
		merchant.setContractBegintime(new Date());
		merchant.setContractEndtime(new Date());
		merchant.setContractStaff("contractStaff");
		merchant.setReviewStaff("reviewStaff");
		merchant.setIsAudit(true);
		merchant.setOrderValue(3);
		merchant.setType(MerchantType.froad);
		merchant.setAreaId(232123124L);
		merchant.setMerchantCategoryId(12312412312L);
		merchant.setClientId(11111111L);
		
		long id=merchantMapperImpl.saveMerchant(merchant);
		System.out.println(id);
	}
	
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<Merchant> pageFilter=new PageFilter<Merchant>();
		
		Merchant merchant=new Merchant();
//		merchant.setName("name");
		merchant.setDataState(DataState.entering);
//		merchant.setFullName("fullName");
//		merchant.setLogo("logo");
//		merchant.setAddress("address");
//		merchant.setTel("581231214");
//		merchant.setContactName("contactName");
//		merchant.setContactPhone("contactPhone");
//		merchant.setLegalName("legalName");
//		merchant.setLegalCredentType("legalCredentType");
//		merchant.setLegalCredentNo("legalCredentNo");
//		merchant.setContractBegintime(new Date());
//		merchant.setContractEndtime(new Date());
//		merchant.setContractStaff("contractStaff");
//		merchant.setReviewStaff("reviewStaff");
//		merchant.setIsAudit(false);
//		merchant.setOrderValue(3);
//		merchant.setType(MerchantType.froad);
//		merchant.setAreaId(232123124L);
//		merchant.setMerchantCategoryId(12312412312L);
//		merchant.setClientId(11111111L);;
		
		pageFilter.setFilterEntity(merchant);
		page.setPageSize(20);
		page.setPageFilter(pageFilter);
		
		
		List<Merchant> list= merchantMapperImpl.selectMerchantByPage(page);
		int n=merchantMapperImpl.selectMerchantByPageCount(page);
		System.out.println(list.size()+"条数："+n);
		
	}
	
	@Test
	public void select(){
		System.out.println(JSON.toJSONString(merchantMapperImpl.selectMerchantById(2L)));
	}
	
}
