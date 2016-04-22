package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.MerchantIndustryDao;
import com.froad.CB.po.MerchantIndustry;
import com.froad.CB.service.MerchantIndustryService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

@WebService(endpointInterface="com.froad.CB.service.MerchantIndustryService")
public class MerchantIndustryServiceImpl implements MerchantIndustryService{
	
	private static final Logger logger=Logger.getLogger(MerchantIndustryServiceImpl.class);
	
	private MerchantIndustryDao merchantIndustryDao;
	
	@Override
	public Integer add(MerchantIndustry merchantIndustry) {
		if(merchantIndustry==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return merchantIndustryDao.add(merchantIndustry);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		merchantIndustryDao.deleteById(id);
	}

	@Override
	public MerchantIndustry getById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return merchantIndustryDao.getById(id);
	}

	@Override
	public void updateById(MerchantIndustry merchantIndustry) {
		if(merchantIndustry==null||merchantIndustry.getId()==null){
			logger.error("参数为空，修改失败");
			return;
		}
		merchantIndustryDao.updateById(merchantIndustry);
	}

	public void setMerchantIndustryDao(MerchantIndustryDao merchantIndustryDao) {
		this.merchantIndustryDao = merchantIndustryDao;
	}

	@Override
	public List<MerchantIndustry> getAll() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance(); 
		List<MerchantIndustry> list=(List<MerchantIndustry>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME, CacheKey.MERCHANT_INDUSTRY_CACHE_KEY);
		if(list!=null){
			logger.info("缓存 key=merchantIndustry存在数据！");
			return list;
		}
		logger.info("缓存 key=merchantIndustry 不存在数据,查找数据库！");
		list=merchantIndustryDao.getAll();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_INDUSTRY_CACHE_KEY,list);
		return list;
	}

}
