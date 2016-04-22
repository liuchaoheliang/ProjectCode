package com.froad.singleton;

import java.util.HashMap;
import java.util.Map;

import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;

public class ProductSingleton {
	
	private static ProductSingleton instance = null;
	
	/** 产品名称缓存 */
	private static volatile Map<String, String> prodcutNameMap = new HashMap<String, String>();
	
	private static CommonLogic commonLogic = new CommonLogicImpl();
	
	private ProductSingleton(){
	}
	
	/**
	 * get org instance
	 * 
	 * @param session
	 * @return
	 */
	public static ProductSingleton getInstance(){
		if (null == instance){
			synchronized (ProductSingleton.class){
				if (null == instance){
					
					instance = new ProductSingleton();
				}
			}
		}
		
		return instance;
	}
	
	public static String getProductName(String clientId, String merchantId, String productId){
		String productName = prodcutNameMap.get(productId);
		if (productName == null) {
			// 获取product_name
			Map<String, String> productM = commonLogic.getProductRedis(clientId, merchantId, productId);
			if (productM != null) {
				productName = productM.get("product_name");
				prodcutNameMap.put(productId, productName);
			}
		}
		
		return productName;
	}
}
