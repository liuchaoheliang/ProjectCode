package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportOrderProductMapper;
import com.froad.po.ReportOrderProduct;

public class ReportOrderProductUtil {
	private SqlSession sqlSession = null;
	private Map<String, List<ReportOrderProduct>> productMap = null;
	
	// clientId + memberCode + productType
	public static final char KEY_MEMBER_CODE_PRODUCT_TYPE = '1';
	
	// clientId + memberCode
	public static final char KEY_MEMBER_CODE = '2';
	
	// clientId + merchantId + productType
	public static final char KEY_MERCHANT_ID_PRODUCT_TYPE = '3';
	
	/**
	 * constructor
	 * 
	 * @param sqlSession
	 * @param batchDate
	 */
	public ReportOrderProductUtil (SqlSession sqlSession, Integer batchDate, char keyType) {
		this.sqlSession = sqlSession;
		productMap = getProductMap(batchDate, keyType);
	}
	
	/**
	 * get report order product list by clientId + memberCode + orderType
	 * 
	 * @param clientId
	 * @param memberCode
	 * @param orderType
	 * @return
	 */
	public List<ReportOrderProduct> getProductList(String clientId, Long memberCode, String orderType){
		List<ReportOrderProduct> productList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		key.append(orderType);
		
		productList = productMap.get(key.toString());
		
		return productList;
	}
	
	/**
	 * get productList by clientId + memberCode
	 * 
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public List<ReportOrderProduct> getProductList(String clientId, Long memberCode){
		List<ReportOrderProduct> productList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(memberCode);
		
		productList = productMap.get(key.toString());
		
		return productList;
	}
	
	/**
	 * get productList by clientId + merchantId + orderType
	 * 
	 * @param clientId
	 * @param merchantId
	 * @param orderType
	 * @return
	 */
	public List<ReportOrderProduct> getProductList(String clientId, String merchantId, String orderType){
		List<ReportOrderProduct> productList = null;
		StringBuffer key = null;
		
		key = new StringBuffer();
		key.append(clientId);
		key.append(merchantId);
		key.append(orderType);
		
		productList = productMap.get(key.toString());
		
		return productList;
	}
	
	/**
	 * get report order product map
	 * 
	 * 	key = clientId + memberCode + orderType
	 * 
	 * @param batchDate
	 * @return
	 */
	private Map<String, List<ReportOrderProduct>> getProductMap(Integer batchDate, char keyType){
		Map<String, List<ReportOrderProduct>> productMap = null;
		List<ReportOrderProduct> productList = null;
		List<ReportOrderProduct> innerList = null;
		ReportOrderProduct product = null;
		StringBuffer key = null;
		
		productMap = new HashMap<String, List<ReportOrderProduct>>();
		productList = getProductList(batchDate);
		for (int i = 0; i < productList.size(); i++){
			product = productList.get(i);
			key = new StringBuffer();
			if (keyType == KEY_MEMBER_CODE_PRODUCT_TYPE){
				key.append(product.getClientId());
				key.append(product.getMemberCode());
				key.append(product.getProductType());
			} else if (keyType == KEY_MEMBER_CODE) {
				key.append(product.getClientId());
				key.append(product.getMemberCode());
			} else if (keyType == KEY_MERCHANT_ID_PRODUCT_TYPE) {
				key.append(product.getClientId());
				key.append(product.getMerchantId());
				key.append(product.getProductType());
			}
			innerList = productMap.get(key.toString());
			if (null == innerList){
				innerList = new ArrayList<ReportOrderProduct>();
			}
			innerList.add(product);
			productMap.put(key.toString(), innerList);
		}
		
		return productMap;
	}
	
	/**
	 * get order product list
	 * 
	 * @param batchDate
	 * @return
	 */
	private List<ReportOrderProduct> getProductList(Integer batchDate){
		List<ReportOrderProduct> products = null;
		ReportOrderProductMapper mapper = null;
		List<ReportOrderProduct> productList = null;
		Page<ReportOrderProduct> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		mapper = sqlSession.getMapper(ReportOrderProductMapper.class);
		page = new Page<ReportOrderProduct>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		productList = mapper.findByConditionByPage(batchDate, page);
		products = new ArrayList<ReportOrderProduct>();
		while (null != productList && productList.size() > 0){
			products.addAll(productList);
			if (productList.size() < pageSize){
				break;
			}
			pageNumber += 1;
			page.setPageNumber(pageNumber);
			
			sqlSession.commit(false);
			productList = mapper.findByConditionByPage(batchDate, page);
		}
		
		return products;
	}
}
