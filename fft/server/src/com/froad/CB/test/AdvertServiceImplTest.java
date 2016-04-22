package com.froad.CB.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.Command;
import com.froad.CB.po.Advert;
import com.froad.CB.po.Agreement;
import com.froad.CB.service.AdvertService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AdvertServiceImplTest {
	@Autowired
	AdvertService  advertServiceImpl;
	static Integer pkid;
	
	@Test
	public void testAddAdvert() {
		Advert advert=new Advert();
		advert.setType("1");
		advert.setName("advert 1");
		advert.setPosition("1");
		advert.setState(Command.STATE_CREATE);
		try {
			pkid=advertServiceImpl.addAdvert(advert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(pkid);
	}

	@Test
	public void testGetAdverts() {
		Advert advert=new Advert();
		advert.setType("1");
		List<Advert> r=advertServiceImpl.getAdverts(advert);
		Assert.assertNotNull(r);
	}

	@Test
	public void testStopAdvert() {
		Advert advert=new Advert();
		advert.setId(pkid);
		advert.setState(Command.STATE_STOP);
		boolean r=advertServiceImpl.stopAdvert(advert);
		Assert.assertTrue(r);
	}

	@Test
	public void testUpdateAdvert() {
		Advert advert=new Advert();
		advert.setId(pkid);
		List<Advert> r=advertServiceImpl.getAdverts(advert);
		Advert pkidA=null;
		if(r==null||r.size()==0){
			Advert nadvert=new Advert();
			nadvert.setType("1");
			nadvert.setName("advert 1");
			nadvert.setState(Command.STATE_CREATE);
			try {
				pkid=advertServiceImpl.addAdvert(advert);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pkidA=nadvert;
		}else{
			pkidA=r.get(0);
		}
		pkidA.setName(pkidA.getName()+"_"+pkid);
		boolean ur=advertServiceImpl.updateAdvert(pkidA);
		Assert.assertTrue(ur);
	}

	@Test
	public void testPager(){
		Advert pager=new Advert();
		pager.setPageSize(5);
		pager.setBeginTime("2013-03-13|00:00:00");
		pager.setEndTime("2014-03-13|00:00:00");
		pager=advertServiceImpl.getAdvertByPager(pager);
		System.out.println("总页数:"+pager.getPageCount());
		System.out.println("总数:"+pager.getTotalCount());
	}
//	
//	@Test
//	public void testAdd(){
//		Advert advert=new Advert();
//		advert.setType("1");
//		advert.setName("测试");
//		advert.setPosition("1");
//		advert.setState("10");
//		advert.setModule("4");
//		
//		Agreement agreement=new Agreement();
//		agreement.setType("0");
//		agreement.setContent("测试");
//		agreement.setState("10");
//		
//		boolean result=false;
//		try{
//			result=advertServiceImpl.addAdvertTest(advert, agreement);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		System.out.println("result:"+result);
//	}
}
