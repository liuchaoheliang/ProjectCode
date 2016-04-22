package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsExchangeRackImages;
import com.froad.CB.service.impl.GoodsExchangeRackImagesServiceImpl;


/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-9  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsExchangeRackImagesServiceImplTest {
	@Resource
	private GoodsExchangeRackImagesServiceImpl goodsExchangeRackImagesServiceImpl;
	
	@Test
	public void updateGoodsExchangeRackImages(){
		GoodsExchangeRackImages goodsExchangeRackImages = new GoodsExchangeRackImages();
		//goodsExchangeRackImages.setId(100001001);
		goodsExchangeRackImages.setGoodsExchangeRackId("100001001");
		goodsExchangeRackImages.setImagesUrl("/zh_fenfentong/100001001/goods/goodsExchangeRack/ce260c243d7a3633bc3bc23c49c03617.jpg");
		//goodsExchangeRackImages.setImagesUrl("/test_update");
		goodsExchangeRackImages.setState("30");
		boolean result=goodsExchangeRackImagesServiceImpl.updateGoodsExchangeRackImages(goodsExchangeRackImages);
		System.out.println(result);
	}
}
