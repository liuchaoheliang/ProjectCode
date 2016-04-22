package com.froad.CB.test.trans;

import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.dao.GoodsDao;
import com.froad.CB.dao.impl.CommonGoodsDaoImpl;
import com.froad.CB.dao.impl.GoodsDaoImpl;
import com.froad.CB.po.Goods;
import com.froad.CB.service.impl.GoodsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class GoodsServiceTest{

	@Resource
	private GoodsServiceImpl goodsServiceImpl;
	
	@Resource
	private GoodsDaoImpl goodsDaoImpl;
	
	@Resource
	private CommonGoodsDaoImpl commonGoodsDaoImpl;
	
	@Test
	public void addGoods() {
		Goods goods=new Goods();
		goods.setMerchantId("100001001");
		goods.setGoodsSn("1001");
		goods.setGoodsName("牛蒡");
		goods.setPrice("350");
		goods.setGoodsType("100001001");
		
		goodsServiceImpl.addGoods(goods);
	}

	@Test
	public void deleteById() {
		Integer id=100001003;
		
		goodsServiceImpl.deleteById(id);
	}

	@Test
	public void getGoodsById() {
		Integer id=100001001;
		
		Goods goods=goodsServiceImpl.getGoodsById(id);
		System.out.println("name: "+goods.getGoodsName());
	}

	@Test
	public void updateById() {
		Goods goods=new Goods();
		goods.setId(100001001);
		goods.setBrandId("100001001");
		goods.setState("30");
		
		goodsServiceImpl.updateById(goods);
	}

	@Test
	public void updateStateById() {
		Integer id=100001001;
		String state="50";
		
		goodsServiceImpl.updateStateById(id, state);
	}

	@Test
	public void getGoodsByMerchantId(){
		String merchantId="100001001";
		List<Goods> list=goodsServiceImpl.getGoodsByMerchantId(merchantId);
		System.out.println("list.size=="+list.size());
	}
	
	@Test
	public void getCommonGoods(){
		Object obj=commonGoodsDaoImpl.getCommonGoodsById("goodsGroupRack", 100001002);
		System.out.println("obj is : "+obj);
	}
	
	@Test
	public void reduceStoreById(){
		int reduceNumber=3;
		Integer id=100001001;
		goodsDaoImpl.reduceStoreById(reduceNumber, id);
		System.out.println("完成");
	}
}
