
	 /**
  * 文件名：TestMerchant.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.impl.MerchantExportServiceImpl;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.service.MerchantService;
import com.froad.fft.service.impl.MerchantServiceImpl;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午12:34:15 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestMerchant {
	
	@Resource
	MerchantExportServiceImpl merchantExportServiceImpl;
	
	
	@Test
	public void add(){
		MerchantDto merchant=new MerchantDto();
		merchant.setCreateTime(new Date());
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
		merchant.setOrderValue(3);
		merchant.setType(com.froad.fft.enums.MerchantType.froad);
		merchant.setAreaId(232123124L);
		merchant.setMerchantCategoryId(12312412312L);
		merchant.setClientId(11111111L);
		
		long id=merchantExportServiceImpl.addMerchant(null, null, merchant);
		System.out.println(id);
	}
	
	
	@Test
	public void page(){
		PageDto page=new PageDto();
		PageFilterDto<MerchantDto> pageFilter=new PageFilterDto<MerchantDto>();
		
		MerchantDto merchantDto=new MerchantDto();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(merchantDto);
		page.setPageSize(5);
		page.setPageFilterDto(pageFilter);
		
		page= merchantExportServiceImpl.findMerchantByPage(null, null, page);
		System.out.println(page.getResultsContent().size()+"条数："+page.getTotalCount());
		
	}
	
	
	@Test
	public void select(){
		MerchantDto merchantDto=merchantExportServiceImpl.findMerchantById(null, null, 1L);
		System.out.println(JSON.toJSONString(merchantDto));
	}
}
