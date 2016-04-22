package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.goods.Goods;
import com.froad.client.goods.GoodsService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品 client service  ActionSupport
 */
public class GoodsActionSupport {
	private static Logger logger = Logger.getLogger(GoodsActionSupport.class);
	private GoodsService goodsService;
	
	/**
	 * ID查找返利架的商品信息
	 * @param id
	 * @return
	 */
	public Goods getGoodById(String id){
		Goods mp = null;
		try{
			if(!Assert.empty(id)){
				mp = goodsService.getGoodsById(Integer.valueOf(id));				
			}
		} catch (Exception e) {
			logger.error("GoodsActionSupport.getGoodById出错 商品ID："+id, e);
		}
		return mp;
	}
	
	
	public GoodsService getGoodsService() {
		return goodsService;
	}
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}	
}
