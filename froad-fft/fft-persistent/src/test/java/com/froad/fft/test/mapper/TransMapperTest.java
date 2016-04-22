package com.froad.fft.test.mapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowProfileStatement.Type;
import com.froad.fft.persistent.api.impl.TransMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.common.enums.DataState;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.Trans;


	 /**
 * 文件名：TransMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月27日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月27日 下午4:24:14 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class TransMapperTest {
	
	@Resource	
	TransMapperImpl transMapperImpl;
	
	@Test
	public void add(){
//		Trans trans=new Trans();
//		trans.setCreateTime(new Date());
//		trans.setUpdateTime(new Date());
//		trans.setSn("sn");
	}
	
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月3日 下午7:55:23
	  */
	@Test
	public void page(){
		
		Page page=new Page();
		PageFilter<Trans> pageFilter=new PageFilter<Trans>();
		
		Trans trans=new Trans();
//		merchant.setName("name");
//		trans.setMemberCode(50000037167L);
		trans.setMerchantId(1L);
		List<Long> ids=new ArrayList<Long>();
		ids.add(1L);
		ids.add(3L);
		trans.setSysUserIds(ids);
//		trans.setType(TransType.presell);
		pageFilter.setFilterEntity(trans);
		page.setPageSize(100);
		page.setPageFilter(pageFilter);
		
		
		List<Trans> list= transMapperImpl.selectTransByPage(page);
		int n=transMapperImpl.selectTransByPageCount(page);
		System.out.println(list.size()+"条数："+n);
	}
	
	@Test
	public void updateStateToSuccess(){
		List<Long> ids=new ArrayList<Long>();
		ids.add(1l);
		ids.add(2l);
//		transMapperImpl.updateStateToSuccessByIds(ids);
	}
	
	@Test
	public void selectById(){
		Long id=25l;
		Trans trans=transMapperImpl.selectTransById(id);
		System.out.println(trans.getPayMethod());
//		System.out.println(trans.getDetailsList().get(0).getProductName());
//		System.out.println(trans.getPayList().size());
	}
}
