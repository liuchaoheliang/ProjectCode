
	 /**
  * 文件名：TestRefunds.java
  * 版本信息：Version 1.0
  * 日期：2014年3月27日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.impl.RefundsExportServiceImpl;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.persistent.entity.Refunds.State;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午8:07:30 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestRefunds {
	@Resource
	RefundsExportServiceImpl refundsExportServiceImpl;
	
	
	
	@Test
	public void add(){
		RefundsDto refundsDto=new RefundsDto();
		refundsDto.setCreateTime(new Date());
		refundsDto.setSn("sn123");
		refundsDto.setTransId(11111111L);
		refundsDto.setSysUserId("2222222L");
		refundsDto.setState(com.froad.fft.dto.RefundsDto.State.audit_pass);
		refundsDto.setRemark("remark");
		refundsDto.setReason("reason");		
		Long id=refundsExportServiceImpl.saveRefunds(null, null, refundsDto);
		System.out.println(id);
	}
	@Test
	public void update(){
		RefundsDto refundsDto=new RefundsDto();
		refundsDto.setId(3L);
		refundsDto.setCreateTime(new Date());
		refundsDto.setSn("resn123");
		refundsDto.setTransId(33333333L);
		refundsDto.setSysUserId("4444444L");
		refundsDto.setState(com.froad.fft.dto.RefundsDto.State.apply);
		refundsDto.setRemark("xxremark");
		refundsDto.setReason("xxreason");		
		boolean falg=refundsExportServiceImpl.updateRefundsById(null, null, refundsDto);
		System.out.println(falg);
	}
	
	@Test
	public void select(){
		RefundsDto refundsDto=new RefundsDto();
		refundsDto=refundsExportServiceImpl.findRefundsById(null, null, 3L);
		System.out.println(JSON.toJSONString(refundsDto));
	}
	
	
	@Test
	public void page(){
		PageDto page=new PageDto();
		PageFilterDto<RefundsDto> pageFilter=new PageFilterDto<RefundsDto>();
		
		RefundsDto refundsDto=new RefundsDto();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(refundsDto);
		page.setPageSize(5);
		page.setPageFilterDto(pageFilter);
		
		page= refundsExportServiceImpl.findRefundsByPage(null, null, page);
		System.out.println(page.getResultsContent().size()+"条数："+page.getTotalCount());
		
	}
	
	
}
