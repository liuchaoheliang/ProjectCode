package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.MerchantOnlineApplyDao;
import com.froad.CB.po.MerchantOnlineApply;
import com.froad.CB.service.MerchantOnlineApplyService;

@WebService(endpointInterface="com.froad.CB.service.MerchantOnlineApplyService")
public class MerchantOnlineApplyServiceImpl implements MerchantOnlineApplyService{

	private static final Logger logger=Logger.getLogger(MerchantOnlineApplyServiceImpl.class);
	
	private MerchantOnlineApplyDao merchantOnlineApplyDao;
	
	@Override
	public Integer add(MerchantOnlineApply merchantOnlineApply) {
		if(merchantOnlineApply==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return merchantOnlineApplyDao.add(merchantOnlineApply);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		merchantOnlineApplyDao.deleteById(id);
	}

	@Override
	public MerchantOnlineApply getById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return merchantOnlineApplyDao.getById(id);
	}

	@Override
	public void updateById(MerchantOnlineApply apply) {
		if(apply==null||apply.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		merchantOnlineApplyDao.updateById(apply);
	}

	public void setMerchantOnlineApplyDao(
			MerchantOnlineApplyDao merchantOnlineApplyDao) {
		this.merchantOnlineApplyDao = merchantOnlineApplyDao;
	}

	@Override
	public List<MerchantOnlineApply> getByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空，查询失败");
			return null;
		}
		return merchantOnlineApplyDao.getByUserId(userId);
	}

	@Override
	public MerchantOnlineApply getMerchantApplyByPager(MerchantOnlineApply apply) {
		if(apply==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return merchantOnlineApplyDao.getMerchantApplyByPager(apply);
	}

}
