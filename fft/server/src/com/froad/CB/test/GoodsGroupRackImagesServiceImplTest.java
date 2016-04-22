package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.GoodsGroupRackImages;
import com.froad.CB.service.impl.GoodsGroupRackImagesServiceImpl;


/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-9  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsGroupRackImagesServiceImplTest {
	@Resource
	private GoodsGroupRackImagesServiceImpl goodsGroupRackImagesServiceImpl;
	
	@Test
	public void updateGoodsGroupRackImages(){
		GoodsGroupRackImages goodsGroupRackImages = new GoodsGroupRackImages();
		//goodsGroupRackImages.setId(100001001);
		goodsGroupRackImages.setGoodsGroupRackId("100001001");
		goodsGroupRackImages.setImagesUrl("/zh_fenfentong/100001001/goods/goodsExchangeRack/ce260c243d7a3633bc3bc23c49c03617.jpg");
		//goodsGroupRackImages.setImagesUrl("/test_update");
		goodsGroupRackImages.setRemark("测试更新");
		goodsGroupRackImages.setState("30");
		boolean result=goodsGroupRackImagesServiceImpl.updateGoodsGroupRackImages(goodsGroupRackImages);
		System.out.println(result);
	}
}
