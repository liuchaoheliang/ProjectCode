package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.goodsCategory.GoodsCategoryService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品分类 client service  ActionSupport
 */
public class GoodsCategoryActionSupport {
	private static Logger logger = Logger.getLogger(GoodsCategoryActionSupport.class);
	private GoodsCategoryService goodsCategoryService;
	
	
	
	public GoodsCategoryService getGoodsCategoryService() {
		return goodsCategoryService;
	}
	public void setGoodsCategoryService(GoodsCategoryService goodsCategoryService) {
		this.goodsCategoryService = goodsCategoryService;
	}
	
}
