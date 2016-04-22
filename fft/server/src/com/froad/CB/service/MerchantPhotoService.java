package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantPhoto;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:28:24
 * @version 1.0
 * 商户相册
 */
@WebService
public interface MerchantPhotoService {
	
	/**
	 * 增加商户相册
	 * @param merchantPhoto
	 * @return Integer
	 */
	public Integer addMerchantPhoto(MerchantPhoto merchantPhoto);
	
	/**
	 * ID查找相册图片信息
	 * @param id
	 * @return MerchantPhoto
	 */
	public MerchantPhoto getMerchantPhotoById(Integer id);
	
	/**
	 * 通过商户ID查询出商户相册信息
	 * @param merchantId
	 * @return List<MerchantPhoto>
	 */
	public List<MerchantPhoto> getMerchantPhotoByMerchantId(String merchantId);
	
	/**
	 * 通过会员ID查询出商户相册信息
	 * @param userId
	 * @return List<MerchantPhoto>
	 */
	public List<MerchantPhoto> getMerchantPhotoByUserId(String userId);
	
	/**
	 * 查询商户相册信息
	 * @param MerchantPhoto
	 * @return List<MerchantPhoto>
	 */
	public List<MerchantPhoto> getMerchantPhotos(MerchantPhoto merchantPhoto);
	
	/**
	 * 更新商户相册
	 * @param merchantPhoto
	 * @return boolean
	 */
	public boolean updMerchantPhotoInfo(MerchantPhoto merchantPhoto);
	
	/**
	 * 删除 商户相册
	 * @param merchantPhoto
	 * @return Integer
	 */
	public Integer deletePhotoById(MerchantPhoto merchantPhoto);
	
	
	/**
	 * 多条件分页查询商户相册
	 * @param MerchantPhoto
	 * @return MerchantPhoto
	 */
	public MerchantPhoto getMerchantPhotoByPager(MerchantPhoto pager);

	/**
	 * 通过商户ID或者会员ID查询出商户相册信息
	 * @param merchantId
	 * @param userId
	 * @return 商户相册信息
	 */
	List<MerchantPhoto> getMerchantPhotoInfo(String merchantId,String userId);
}
