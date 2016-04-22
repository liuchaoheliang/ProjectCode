package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.merchantProduct.MerchantProduct;
import com.froad.client.merchantProduct.MerchantProductService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户产品信息  client service  ActionSupport
 */
public class MerchantProductActionSupport {
	private static Logger logger = Logger.getLogger(MerchantProductActionSupport.class);
	private MerchantProductService merchantProductService;
	
	/**
	 * ID查找商品
	 * @param id
	 * @return
	 */
	public MerchantProduct getMerchantProductById(String id){
		MerchantProduct mp = null;
		try{
			if(!Assert.empty(id)){
				mp = merchantProductService.getMerchantProductById(Integer.valueOf(id));				
			}
		} catch (Exception e) {
			logger.error("MerchantProductActionSupport.getMerchantProductById出错 商品ID："+id, e);
		}
		return mp;
	}
	
	/**
	 * 分页查询 商户商品
	 * @param complaint
	 * @return
	 */
	public MerchantProduct queryMerchantProductByPager(MerchantProduct merchantProduct){
		try{
			merchantProduct=merchantProductService.getMerchantProductByPager(merchantProduct);
		}
		catch(Exception e){
			logger.error("MerchantProductActionSupport.queryMerchantProductByPager分页查找商户产品异常 商户ID："+merchantProduct.getMerchantId());
		}
		return merchantProduct;
	}
	
	/**
	 * 增加商户产品
	 * @param mp
	 * @return
	 */
	public Integer addMerchantProducts(MerchantProduct mp){		
		logger.info("增加产品");
		Integer num = null;
		try {
			num = merchantProductService.addMerchantProduct(mp);
			if(num == null){
				num = new Integer(0);
			}
		} catch (Exception e) {
			logger.error("MerchantProductActionSupport.addMerchantProducts出错 商户ID："+mp.getMerchantId());
			e.printStackTrace();
			num = new Integer(0);
		}
		return num;
	}
	
	/**
	 * 修改商户产品信息
	 * @param merchantProduct
	 * @return
	 */
	public MerchantProduct updMerchantProducts(MerchantProduct merchantProduct){		
		logger.info("修改产品");		
		MerchantProduct mp = new MerchantProduct();
		try {
			mp = merchantProductService.updMerchantProductInfo(merchantProduct);
			if(mp == null){
				 mp = new MerchantProduct();
			}
		} catch (Exception e) {
			logger.error("MerchantProductActionSupport.updMerchantProducts出错 产品ID："+merchantProduct.getId());
			e.printStackTrace();
			mp = new MerchantProduct();
		}
		return mp;
	}
	
	/**
	 * 通过商户ID或者会员ID查询出商户商品信息
	 * @param merchantId
	 * @param userId
	 * @return 商户商品信息
	 */
	public List<MerchantProduct> getMerchantProductListByMerchantId(MerchantProduct merchantProduct){
		List<MerchantProduct> merchantProductList = null;
		try {
			merchantProductList = merchantProductService.getMerchantProductListByMerchantId(merchantProduct);
			if(merchantProductList == null){
				merchantProductList = new ArrayList<MerchantProduct>();
			}
		} catch (Exception e) {
			logger.error("MerchantProductActionSupport.getMerchantProductListByMerchantId 出错merchantId:"+merchantProduct.getMerchantId());
			e.printStackTrace();
			merchantProductList = new ArrayList<MerchantProduct>();
		}
		return merchantProductList;
	}
	
	/**
	 * 通过商户ID或者会员ID查询出商户商品信息
	 * @param merchantId
	 * @param userId
	 * @return 商户商品信息
	 */
	public List<MerchantProduct> getMerchantProductInfo(String merchantId,String userId){
		List<MerchantProduct> merchantProductList = null;
		try {
			merchantProductList = merchantProductService.getMerchantProductInfo(merchantId, userId);
			if(merchantProductList == null){
				merchantProductList = new ArrayList<MerchantProduct>();
			}
		} catch (Exception e) {
			logger.error("MerchantProductActionSupport.getMerchantProductInfo出错merchantId:"+merchantId+",userId:"+userId);
			e.printStackTrace();
			merchantProductList = new ArrayList<MerchantProduct>();
		}
		return merchantProductList;
	}
	
	/**
	 * 根据ID删除产品，返回产品列表
	 * @param mp
	 * @return
	 */
	public int deleteProductAndReListInfo(MerchantProduct mp){
		logger.info("删除产品");
		List<MerchantProduct> list = new ArrayList<MerchantProduct>();
		int count = 0;
		try {
			count = merchantProductService.deleteProductById(mp);
		} catch (Exception e) {
			logger.error("MerchantActionSupport.deleteProductAndReListInfo出错 id:"+mp.getId());
			e.printStackTrace();
		}
//		if(count>0){
//			list = frontMerchantService.getMerchantProductInfo(mp.getMerchantId(), "");
//		}
		return count;
	}
	
	public MerchantProductService getMerchantProductService() {
		return merchantProductService;
	}
	public void setMerchantProductService(
			MerchantProductService merchantProductService) {
		this.merchantProductService = merchantProductService;
	}
	
}
