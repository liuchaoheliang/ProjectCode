package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.merchantPhoto.MerchantPhoto;
import com.froad.client.merchantPhoto.MerchantPhotoService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户相册信息  client service  ActionSupport
 */
public class MerchantPhotoActionSupport {
	private static Logger logger = Logger.getLogger(MerchantPhotoActionSupport.class);
	private MerchantPhotoService merchantPhotoService;
	
	/**
	 * 商户ID 查找相册
	 * @param merchatId
	 * @return
	 */
	public List<MerchantPhoto> findMerchantPhotoByMerchatId(String merchatId){
		List<MerchantPhoto> list = new ArrayList<MerchantPhoto>();
		try{
			list = merchantPhotoService.getMerchantPhotoByMerchantId(merchatId);
		}catch(Exception e){
			logger.error("MerchantPhotoActionSupport.findMerchantPhotoByMerchatId商户ID查找相册异常 商户ID："+merchatId, e);
		}
		return list;
	}
	
	/**
	 * 分页查询 商户相册
	 * @param complaint
	 * @return
	 */
	public MerchantPhoto queryMerchantPhotoByPager(MerchantPhoto merchantPhoto){
		try{
			merchantPhoto=merchantPhotoService.getMerchantPhotoByPager(merchantPhoto);
			if(merchantPhoto==null){
				merchantPhoto = new MerchantPhoto();
			}
		}
		catch(Exception e){
			logger.error("MerchantPhotoActionSupport.queryMerchantPhotoByPager分页查找商户相册异常 商户ID："+merchantPhoto.getMerchantId(),e);
		}
		return merchantPhoto;
	}
	
	/**
	 * ID查找相册图片
	 * @param id
	 * @return
	 */
	public MerchantPhoto getMerchantPhotoById(String id){
		MerchantPhoto mp = new MerchantPhoto();
		try{
			mp = merchantPhotoService.getMerchantPhotoById(Integer.valueOf(id));
			if(mp == null){
				 mp = new MerchantPhoto();
			}
		} catch (Exception e) {
			logger.error("MerchantPhotoActionSupport.getMerchantPhotoById出错 图片ID："+id, e);
			mp = new MerchantPhoto();
		}
		return mp;
	}
	
	/**
	 * 增加相册图片
	 * @param mp
	 * @return
	 */
	public Integer addMerchantPhoto(MerchantPhoto mp){		
		logger.info("增加相册相片信息");
		Integer num = null;
		try {
			num = merchantPhotoService.addMerchantPhoto(mp);
			if(num == null){
				num = new Integer(0);
			}
		} catch (Exception e) {
			logger.error("MerchantPhotoActionSupport.addMerchantPhoto商户ID查找相册异常 商户ID："+mp.getMerchantId(), e);
			num = new Integer(0);
		}
		return num;
	}
	
	/**
	 * 更新商户相册信息
	 * @param merchantPhoto
	 * @return
	 */
	public MerchantPhoto updMerchantPhotoInfo(MerchantPhoto merchantPhoto){
		MerchantPhoto mp = new MerchantPhoto();
		logger.info("修改相册相片信息");
		try {
			boolean flag = merchantPhotoService.updMerchantPhotoInfo(merchantPhoto);
			if(flag){
				mp = merchantPhotoService.getMerchantPhotoById(merchantPhoto.getId());
			}else{
				mp = merchantPhoto;
			}
		} catch (Exception e) {
			logger.error("MerchantPhotoActionSupport.updMerchantPhotoInfo出错,商户ID："+merchantPhoto.getMerchantId());
			e.printStackTrace();
			mp = merchantPhoto;
		}
		return mp;
	}
	
	/**
	 * 通过商户ID或者会员ID查询出商户相册信息
	 * @param merchantId
	 * @param userId
	 * @return 商户相册信息
	 */
	public List<MerchantPhoto> getMerchantPhotoInfo(String merchantId,String userId){
		List<MerchantPhoto> merchantPhotoList = null;
		try {
			merchantPhotoList = merchantPhotoService.getMerchantPhotoInfo(merchantId, userId);
			if(merchantPhotoList == null){
				merchantPhotoList = new ArrayList<MerchantPhoto>();
			}
		} catch (Exception e) {
			logger.error("MerchantPhotoActionSupport.getMerchantPhotoInfo出错,商户ID："+merchantId+"userID："+userId);
			e.printStackTrace();
			merchantPhotoList = new ArrayList<MerchantPhoto>();
		}
		return merchantPhotoList;
	}
	
	/**
	 * 根据ID删除相册相片，返回相册
	 * @param mp
	 * @return
	 */
	public Integer deletePhotoInfo(MerchantPhoto mp){
		logger.info("删除相片");
		List<MerchantPhoto> list = new ArrayList<MerchantPhoto>();
		Integer count = 0;
		try {
			count = merchantPhotoService.deletePhotoById(mp);
		} catch (Exception e) {
			logger.error("MerchantPhotoActionSupport.deletePhoto出错,商户ID："+mp.getMerchantId()+" 产品ID："+mp.getId());
			e.printStackTrace();
			count = new Integer(0);
		}
		return count;
	}
	
	public MerchantPhotoService getMerchantPhotoService() {
		return merchantPhotoService;
	}
	public void setMerchantPhotoService(MerchantPhotoService merchantPhotoService) {
		this.merchantPhotoService = merchantPhotoService;
	}
	
}
