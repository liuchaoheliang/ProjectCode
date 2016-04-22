package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.goodsAttribute.GoodsAttributeService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品属性 client service  ActionSupport
 */
public class GoodsAttributeActionSupport {
	private static Logger logger = Logger.getLogger(GoodsAttributeActionSupport.class);
	private GoodsAttributeService goodsAttributeService;
	
	
	public GoodsAttributeService getGoodsAttributeService() {
		return goodsAttributeService;
	}
	public void setGoodsAttributeService(GoodsAttributeService goodsAttributeService) {
		this.goodsAttributeService = goodsAttributeService;
	}
	
}
