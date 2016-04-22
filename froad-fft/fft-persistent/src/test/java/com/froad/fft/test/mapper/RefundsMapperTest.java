package com.froad.fft.test.mapper;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.RefundsMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.persistent.entity.Refunds.State;


	 /**
 * 文件名：RefundsMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月27日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午4:09:42 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class RefundsMapperTest {
	@Resource
	private RefundsMapperImpl refundsMapperImpl;
	
	@Test
	public void add(){
		Refunds refunds=new Refunds();
		refunds.setCreateTime(new Date());
		refunds.setUpdateTime(new Date());
		refunds.setSn("sn");
		refunds.setTransId(1231231L);
		refunds.setSysUserId("12334253L");
		refunds.setState(State.audit_pass);
		refunds.setRemark("remark");
		refunds.setReason("reason");		
		Long id=refundsMapperImpl.saveRefunds(refunds);
		System.out.println(id);
	}
	
	@Test
	public void update(){
		Refunds refunds=new Refunds();
		refunds.setId(1L);
		refunds.setCreateTime(new Date());
		refunds.setUpdateTime(new Date());
		refunds.setSn("resn");
		refunds.setTransId(2222222L);
		refunds.setSysUserId("333333333L");
		refunds.setState(State.audit_no_pass);
		refunds.setRemark("exremark");
		refunds.setReason("exreason");		
		boolean flag=refundsMapperImpl.updateRefundsById(refunds);
		System.out.println(flag);
	}
	
	
	@Test
	public void select(){
		Refunds refunds=new Refunds();
		refunds=refundsMapperImpl.selectRefundsById(1L);
		System.out.println(JSON.toJSON(refunds));
	}
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<Refunds> pageFilter=new PageFilter<Refunds>();
		
		Refunds refunds=new Refunds();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(refunds);
		page.setPageSize(1);
		page.setPageFilter(pageFilter);
				
		List<Refunds> list=refundsMapperImpl.selectRefundsByPage(page);
		int n=refundsMapperImpl.selectRefundsByPageCount(page);
		System.out.println(list.size());
		System.out.println(n);
	}
	
}
