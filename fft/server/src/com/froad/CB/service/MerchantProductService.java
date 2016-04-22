package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantProduct;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:25:49
 * @version 1.0
 * 商户商品
 */
@WebService
public interface MerchantProductService {
	
	/**
	 * 增加商户商品
	 * @param record
	 * @return Integer
	 */
	public Integer addMerchantProduct(MerchantProduct merchantProduct);	
	
	/**
	 * ID查找商品信息
	 * @param id
	 * @return MerchantProduct
	 */
	public MerchantProduct getMerchantProductById(Integer id);	
	
	/**
     * 方法描述：按主键编号更新商品
     * @param: MerchantProduct
     * @return: Integer 受影响行数
     * @version: 1.0
     */
	Integer updateByPrimaryKeySelective(MerchantProduct merchantProduct);

	/**
     * 方法描述：按主键编号更新商品
     * @param: MerchantProduct
     * @return: Integer 受影响行数
     * @version: 1.0
     */
	Integer updateByMerchantId(MerchantProduct merchantProduct);
	
	/**
	 * 删除商户商品
	 * @param merchantProduct
	 * @return Integer
	 */
	public Integer deleteProductById(MerchantProduct merchantProduct);	
	
	/**
	 * 通过商户ID查询出商户商品信息
	 * @param merchantId
	 * @return 商户商品信息 List<MerchantProduct>
	 */
	public List<MerchantProduct> getMerchantProductByMerchantId(String merchantId);
	
	/**
	 * 通过者会员ID查询出商户商品信息
	 * @param userId
	 * @return 商户商品信息 List<MerchantProduct>
	 */
	public List<MerchantProduct> getMerchantProductByUserId(String userId);
	
	/**
	 * 多条件分页查询商品
	 * @param product
	 * @return MerchantProduct
	 */
	public MerchantProduct getMerchantProductByPager(MerchantProduct pager);
	
	/**
	 * 通过商户商品信息ID,更新商户商品信息
	 * @param merchantProduct
	 * @return 更新后的商户商品信息
	 * @throws Exception 
	 */
	MerchantProduct updMerchantProductInfo(MerchantProduct merchantProduct) throws Exception;
	
	/**
	 * 根据商户ID查询产品
	 * @param merchantId
	 * @return
	 */
	public List<MerchantProduct> getMerchantProductListByMerchantId(MerchantProduct merchantProduct);
	
	/**
	 * 通过商户ID或者会员ID查询出商户商品信息
	 * @param merchantId
	 * @param userId
	 * @return 商户商品信息
	 */
	List<MerchantProduct> getMerchantProductInfo(String merchantId,String userId);
}
