package com.froad.CB.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.merchant.ShareMerchantDao;
import com.froad.CB.po.merchant.ShareMerchant;
import com.froad.CB.service.ShareMerchantService;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.ShareMerchantService")
public class ShareMerchantServiceImpl implements ShareMerchantService{
	
	private static final Logger logger=Logger.getLogger(ShareMerchantServiceImpl.class);
	
	private ShareMerchantDao shareMerchantDao;

	@Override
	public Integer addShareMerchant(ShareMerchant shareMerchant) {
		if(shareMerchant==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		String time=DateUtil.formatDate2Str(new Date());
		shareMerchant.setCreateTime(time);
		shareMerchant.setUpdateTime(time);
		return shareMerchantDao.addShareMerchant(shareMerchant);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败！");
			return false;
		}
		return shareMerchantDao.deleteById(id);
	}

	@Override
	public ShareMerchant getShareMerchantById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败！");
			return null;
		}
		return shareMerchantDao.getShareMerchantById(id);
	}

	@Override
	public ShareMerchant getShareMerchantByPager(ShareMerchant shareMerchant) {
		if(shareMerchant==null){
			logger.error("参数为空，分页查询失败！");
			return null;
		}
		return shareMerchantDao.getShareMerchantByPager(shareMerchant);
	}

	@Override
	public List<ShareMerchant> getShareMerchantByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空，查询失败！");
			return null;
		}
		return shareMerchantDao.getShareMerchantByUserId(userId);
	}

	@Override
	public boolean updateById(ShareMerchant shareMerchant) {
		if(shareMerchant==null||shareMerchant.getId()==null){
			logger.error("参数为空，更新失败！");
			return false;
		}
		return shareMerchantDao.updateById(shareMerchant);
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，状态更新失败！");
			return false;
		}
		return shareMerchantDao.updateStateById(id, state);
	}

	public void setShareMerchantDao(ShareMerchantDao shareMerchantDao) {
		this.shareMerchantDao = shareMerchantDao;
	}

}
