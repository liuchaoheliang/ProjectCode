package com.froad.CB.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.Pager.OrderType;
import com.froad.CB.po.announcement.Announcement;
import com.froad.CB.service.AnnouncementService;

/** 
 * @author FQ 
 * @date 2012-11-29 下午02:14:52
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class AnnouncementTest {
	@Autowired
	private AnnouncementService announcementService;
	
	@Test
	public void getAnnouncementList(){
		Announcement a=new Announcement();
		a.setBeginTime("2013-09-13|00:00:00");
		a.setEndTime("2014-09-13|00:00:00");
		a.setOrderBy("important");
		a.setOrderType(OrderType.asc);
//		a.setType("2");
		a=announcementService.getAnnouncementByPager(a);
		System.out.println(a.getList().size());
		
//		if(list!=null && !list.isEmpty()){
//			for(Announcement a:list){
//				System.out.println(a.getTitle());
//				System.out.print(a.getImportant());
//			}
//		}
		
//		System.out.println(list==null ? "null":list.size());
	}
}
