package com.froad.CB.dao.merchant;

import com.froad.CB.po.merchant.MerchantPhoto;
import java.util.*;

public interface MerchantPhotoDAO {
	
	
	/**
	  * 方法描述：添加商户相册
	  * @param: MerchantPhoto
	  * @return: 
	  * @version: 1.0
	  */
	Integer insert(MerchantPhoto record);

	
	/**
	  * 方法描述：按主键更新商户相册
	  * @param: MerchantPhoto
	  * @return: 受影响行数
	  * @version: 1.0
	  */
	Integer updateById(MerchantPhoto record);

	
	/**
	  * 方法描述：按主键查询商户相册
	  * @param: id
	  * @return: MerchantPhoto
	  * @version: 1.0
	  */
	MerchantPhoto selectByPrimaryKey(Integer id);

	
	/**
	  * 方法描述：按主键删除商户相册
	  * @param: id
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
	  * 方法描述：查询商户相册列表
	  * @param: MerchantPhoto 查询条件
	  * @return: List<MerchantPhoto>
	  * @version: 1.0
	  */
	List<MerchantPhoto> selectMerchantPhotos(MerchantPhoto queryCon);

	
	/**
	  * 方法描述：查询商户列表
	  * @param: merchantId 商户编号
	  * @return: List<MerchantPhoto>
	  * @version: 1.0
	  */
	List<MerchantPhoto> getMerchantPhotosByByMerchantId(String merchantId);
	
	MerchantPhoto getMerchantPhotoByPager(MerchantPhoto pager);
	
}