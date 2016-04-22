package com.froad.singleton;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.mappers.ProductMapper;
import com.froad.po.ProductCategory;

public class ProductCategorySingleton {
	private static volatile List<ProductCategory> instance = null;
	
	private ProductCategorySingleton(){
	}
	
	/**
	 * get product category instance
	 * 
	 * @param session
	 * @return
	 */
	public static List<ProductCategory> getInstance(SqlSession session){
		ProductMapper mapper = null;
		
		if (null == instance){
			synchronized (ProductCategorySingleton.class){
				if (null == instance){
					mapper = session.getMapper(ProductMapper.class);
					instance = mapper.findProductCategory();
				}
			}
		}
		return instance;
	}
	
	/**
	 * destroy instance
	 */
	public static void destroyInstance(){
		if (instance != null){
			instance.clear();
			instance = null;
		}
	}
}
