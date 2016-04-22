package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.enums.ProductType;
import com.froad.handler.ProductHandler;
import com.froad.logback.LogCvt;
import com.froad.po.MinatoSingleProductResultInfo;
import com.froad.po.Product;
import com.froad.po.ProductDetail;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;

public class ProductHandlerImpl implements ProductHandler {

	/**
	 * @Fields manager : MongoDB操作方法
	 */
	private MongoManager manager = new MongoManager();
	
	@Override
	public List<Product> findProductByMerchantId(String merchantId) {
		List<Product> result = new ArrayList<Product>(); 
		SqlSession sqlSession = null;
		ProductMapper productMapper = null;
		try { 
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				productMapper = sqlSession.getMapper(ProductMapper.class);
				//MySql查询数据
				result = productMapper.findProductByMerchantId(merchantId);
		} catch (Exception e) { 
			LogCvt.error("查询Product失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}

	
	@Override
	public List<ProductDetail> getProductIdAndMerchantIdByClientId(String clientId) {
		
		BasicDBObject query = new BasicDBObject();
		// 查询条件
		//query.put("merchant_id", new BasicDBObject("$in", merchantIds));
		query.put("client_id", clientId);
		query.put("is_marketable", "1");// 只查询已上架商品
		query.put("product_type", ProductType.group.getCode());//一期只有团购商品
		List<ProductDetail> productDetailList =  
				(List<ProductDetail>) manager.findAll(
						query, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
		return productDetailList;
	}
	

}
