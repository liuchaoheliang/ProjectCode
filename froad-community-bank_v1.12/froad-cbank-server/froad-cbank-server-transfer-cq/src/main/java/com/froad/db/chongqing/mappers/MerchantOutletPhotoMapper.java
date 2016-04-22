package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantOutletPhoto;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantOutletPhotoMapper {

	/**
	 *  保存
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param photo    
	  * @return void    
	  * @throws
	 */
	void insert(MerchantOutletPhoto photo);

	/**
	 *  分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<MerchantOutletPhoto>    
	  * @throws
	 */
	List<MerchantOutletPhoto> selectOfPage(PageEntity<MerchantOutletPhoto> pageEntity);

	/**
	 *  按注解ID查询
	  * @Title: selectById
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param id
	  * @param @return    
	  * @return MerchantOutletPhoto    
	  * @throws
	 */
	MerchantOutletPhoto selectById(Long id);

	/**
	 *  按条件查询
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param photo
	  * @param @return    
	  * @return List<MerchantOutletPhoto>    
	  * @throws
	 */
	List<MerchantOutletPhoto> selectByCondition(MerchantOutletPhoto photo);
	
	List<MerchantOutletPhoto> selectByGroup();
	
	
	List<MerchantOutletPhoto> selectMerchantPhotoAll();

	/**
	 *  按ID更新
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param photo
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	boolean updateById(MerchantOutletPhoto photo);

	/**
	 *  按商户ID查询相册信息
	  * @Title: selectMerchantOutlePhotoByOutletId
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param id
	  * @param @return    
	  * @return List<MerchantOutletPhoto>    
	  * @throws
	 */
	List<MerchantOutletPhoto> selectMerchantOutlePhotoByOutletId(Long id);
	
	/**
     * 按照片ID删除商户门店照片
     * @author luofan
     * @version $Id: 2015年1月11日 15:46:48 Exp $
     * @param id
     * @return
     */
    public long deleteMerchantOutletPhotoById(Long id);

    /**
     * 按商户门店ID删除商户门店照片
     * @author luofan
     * @version $Id: 2015年1月11日 15:46:48 Exp $
     * @param merchantOutletId
     * @return
     */
    public long deleteMerchantOutletPhotoByMerchantOutletId(Long merchantOutletId);

}

