package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.goodsType.GoodsTypeService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品类型 client service  ActionSupport
 */
public class GoodsTypeActionSupport {
	private static Logger logger = Logger.getLogger(GoodsTypeActionSupport.class);
	private GoodsTypeService goodsTypeService;
	
	
	public GoodsTypeService getGoodsTypeService() {
		return goodsTypeService;
	}
	public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}
	
}
