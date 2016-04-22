package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.brand.BrandService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 品牌 client service  ActionSupport
 */
public class BrandActionSupport {
	private static Logger logger = Logger.getLogger(BrandActionSupport.class);
	private BrandService brandService;
	
	
	
	
	public BrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
}
