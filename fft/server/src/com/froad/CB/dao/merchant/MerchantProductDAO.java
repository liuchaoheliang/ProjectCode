package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantProduct;

public interface MerchantProductDAO {

	
	/**
     * 方法描述：添加商品
     * @param: MerchantProduct
     * @return: 主键编号
     * @version: 1.0
     */
	Integer insert(MerchantProduct record);

	
	/**
     * 方法描述：按主键编号更新商品
     * @param: MerchantProduct
     * @return: 受影响行数
     * @version: 1.0
     */
	int updateByPrimaryKeySelective(MerchantProduct record);

	public int updateByMerchantId(MerchantProduct record);
	/**
     * 方法描述：按主键查询商品
     * @param: id 主键编号
     * @return: MerchantProduct
     * @version: 1.0
     */
	MerchantProduct selectByPrimaryKey(Integer id);

	/**
     * 方法描述：按主键删除商品
     * @param: id 主键编号
     * @return: 受影响行数
     * @version: 1.0
     */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	public int deleteStateByPrimaryKey(String id);
	
	/**
	 * 若是 queryCon为空，则查询所有的
	 * @param queryCon
	 * @return
	 */
	List<MerchantProduct> selectMerchantProducts(MerchantProduct queryCon);
	
	
	/**
	  * 方法描述：多条件分页查询商品信息
	  * @param: MerchantProduct
	  * @return: MerchantProduct
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 2:58:51 PM
	  */
	public MerchantProduct getMerchantProductByPager(MerchantProduct product);

}