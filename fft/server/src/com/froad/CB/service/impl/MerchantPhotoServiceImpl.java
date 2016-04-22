package com.froad.CB.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.merchant.MerchantPhotoDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantPhoto;
import com.froad.CB.service.MerchantPhotoService;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-5  
 * @version 1.0
 * 商户相册service
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantPhotoService")
public class MerchantPhotoServiceImpl implements MerchantPhotoService {
	private static final Logger logger=Logger.getLogger(MerchantPhotoServiceImpl.class);
	private MerchantPhotoDAO merchantPhotoDao;
	private MerchantDAO merchantDao;
	
	@Override
	public Integer addMerchantPhoto(MerchantPhoto merchantPhoto) {
		if(merchantPhoto==null){
			logger.info("增加相册图片不能为空！");
			return null;
		}
		return merchantPhotoDao.insert(merchantPhoto);
	}

	@Override
	public MerchantPhoto getMerchantPhotoById(Integer id) {
		if(Assert.empty(id)){
			logger.info("查询相册图片id不能为空！");
			return null;
		}
		return merchantPhotoDao.selectByPrimaryKey(id);
	}

	@Override
	public List<MerchantPhoto> getMerchantPhotoByMerchantId(String merchantId) {
		if(Assert.empty(merchantId)){
			logger.info("查询相册图片merchantId不能为空！");
			return null;
		}
		return merchantPhotoDao.getMerchantPhotosByByMerchantId(merchantId);
	}

	@Override
	public List<MerchantPhoto> getMerchantPhotoByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			return null;
		}
		Merchant queryCon=new Merchant();
		queryCon.setUserId(userId);
		List<Merchant> results=null;
		try {
			results = merchantDao.select(queryCon);
		} catch (SQLException e) {
			logger.info("userId查询相册图片userId查询商户信息出错！");
			e.printStackTrace();
		}
		Merchant merchant=results!=null?results.get(0):new Merchant();
		return merchantPhotoDao.getMerchantPhotosByByMerchantId(String.valueOf(merchant.getId()));
	}
	
	@Override
	public List<MerchantPhoto> getMerchantPhotos(MerchantPhoto merchantPhoto) {
		if(merchantPhoto == null){
			logger.info("查询相册图片merchantPhoto不能为空！");
			return null;
		}
		return merchantPhotoDao.selectMerchantPhotos(merchantPhoto);
	}

	@Override
	public boolean updMerchantPhotoInfo(MerchantPhoto merchantPhoto) {
		if(merchantPhoto == null){
			logger.info("更新相册图片merchantPhoto不能为空！");
			return false;
		}
		Integer num = merchantPhotoDao.updateById(merchantPhoto);
		return num==null||num==0||num==-1?false:true;
	}

	@Override
	public Integer deletePhotoById(MerchantPhoto merchantPhoto) {
		if(Assert.empty(merchantPhoto.getId())){
			logger.info("删除相册图片merchantPhoto.id不能为空！");
			return null;
		}
		return merchantPhotoDao.deleteStateByPrimaryKey(String.valueOf(merchantPhoto.getId()));
	}

	@Override
	public MerchantPhoto getMerchantPhotoByPager(MerchantPhoto pager) {
		if(pager == null){
			logger.info("分页查询相册图片pager不能为空！");
			return null;
		}
		return merchantPhotoDao.getMerchantPhotoByPager(pager);
	}

	@Override
	public List<MerchantPhoto> getMerchantPhotoInfo(String merchantId, String userId) {
		List<MerchantPhoto> result=new ArrayList<MerchantPhoto>();
		if(merchantId!=null&&!"".equals(merchantId)&&userId!=null&&!"".equals(userId)){
			Merchant merchant=getMerchantBaseInfoByUserId(userId);
			if(merchantId.equals(String.valueOf(merchant.getId()))){
				return getMerchantPhotoInfoByMerchantId(merchantId);
			}else{
				return result;
			}
		}
		if(merchantId!=null&&!"".equals(merchantId)){
			return getMerchantPhotoInfoByMerchantId(merchantId);
		} 
		if(userId!=null&&!"".equals(userId)){
			return getMerchantPhotoInfoByUserId(userId);
		}
		return result;
	}
	
	public List<MerchantPhoto> getMerchantPhotoInfoByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			return null;
		}
		Merchant merchant=getMerchantBaseInfoByUserId(userId);
		return merchant==null?null:getMerchantPhotoInfoByMerchantId(String.valueOf(merchant.getId()));
	}
	
	public Merchant getMerchantBaseInfoByUserId(String userId) {
		if(userId==null||"".equals(userId))
			return null;
		Merchant queryCon=new Merchant();
		queryCon.setUserId(userId);
		List<Merchant> results;
		try {
			results = merchantDao.select(queryCon);
			return (results==null||results.size()==0)?null:results.get(0);
		} catch (SQLException e) {
			logger.error("商家列表信息查询异常",e);
			return null;
		}
	}
	
	public List<MerchantPhoto> getMerchantPhotoInfoByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId))
			return null;
		MerchantPhoto queryCon=new MerchantPhoto();
		queryCon.setMerchantId(merchantId);
		List<MerchantPhoto> results=merchantPhotoDao.selectMerchantPhotos(queryCon);
		return results;
	}
	
	public MerchantPhotoDAO getMerchantPhotoDao() {
		return merchantPhotoDao;
	}

	public void setMerchantPhotoDao(MerchantPhotoDAO merchantPhotoDao) {
		this.merchantPhotoDao = merchantPhotoDao;
	}

	public MerchantDAO getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDAO merchantDao) {
		this.merchantDao = merchantDao;
	}
	
}
