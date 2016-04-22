package com.froad.CB.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.StickerCareBean;
import com.froad.CB.service.StickerCardService;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class StickerCardTest {
	
	@Autowired
	private StickerCardService stickerCardService;
	
	@org.junit.Test
	public void addStickerCard(){
		
		StickerCareBean stickerCard=new StickerCareBean();
		
		stickerCard.setPhone("15026409159");
		stickerCard.setAccountName("方泉");
		stickerCard.setAccountNumber("123456789");
		stickerCard.setIdentificationCard("420625198801132519");
		stickerCard.setPointValue("100");
		
		boolean result=stickerCardService.addStickerCard(stickerCard);
		
		System.out.println("结果:"+result);
	}
	
	
}
