package com.froad.CB.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.MerchantUserSetDao;
import com.froad.CB.po.MerchantUserSet;
import com.froad.CB.service.MerchantUserSetService;
import com.froad.util.Assert;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

@WebService(endpointInterface="com.froad.CB.service.MerchantUserSetService")
public class MerchantUserSetServiceImpl implements MerchantUserSetService{
	
	private static final Logger logger=Logger.getLogger(MerchantUserSetServiceImpl.class);

	private MerchantUserSetDao merchantUserSetDao;
	
	@Override
	public Integer addMerchantUserSet(MerchantUserSet merchantUserSet) {
		if(merchantUserSet==null){
			logger.info("参数为空，添加失败");
			return null;
		}
		return merchantUserSetDao.addMerchantUserSet(merchantUserSet);
	}

	@Override
	public List<MerchantUserSet> getMerchantClerk(MerchantUserSet merchantUserSet){
		if(merchantUserSet == null || (Assert.empty(merchantUserSet.getUserId()) && Assert.empty(merchantUserSet.getLoginName()))){
			logger.info("参数为空，查询失败");
			return null;
		}
		List<MerchantUserSet> record = merchantUserSetDao.getMerchantClerk(merchantUserSet);
		return record;
	}
	
	@Override
	public List<MerchantUserSet> getMerchantClerkList(MerchantUserSet merchantUserSet){
//		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();	
//		List<MerchantUserSet> list= (List<MerchantUserSet>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_USER_SET_KEY);
//		if(list!=null){		
//			return list;
//		}		
//		logger.info("缓存 key=merchantUserSetList 不存在数据,查找数据库！");
		List<MerchantUserSet> list=new ArrayList<MerchantUserSet>();
		list = merchantUserSetDao.getMerchantClerk(merchantUserSet);		
//		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_USER_SET_KEY, list);
		return list;
	}
	
	@Override
	public String getMaxClerkBeCode(MerchantUserSet merchantUserSet){
		String beCode = merchantUserSetDao.getMaxClerkBeCode(merchantUserSet);
		return beCode;
	}
	
	@Override
	public Integer updateByUserIdAndBecode(MerchantUserSet merchantUserSet){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getUserId()) || Assert.empty(merchantUserSet.getBeCode())){
			logger.info("参数为空，更新失败");
			return null;
		}
		Integer rows = merchantUserSetDao.updateByUserIdAndBecode(merchantUserSet);
        return rows;
	}
	
	@Override
	public Integer updatePwdByUserIdAndBecode(MerchantUserSet merchantUserSet){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getUserId()) || Assert.empty(merchantUserSet.getBeCode())){
			logger.info("参数为空，更新失败");
			return null;
		}
		Integer rows = merchantUserSetDao.updatePwdByUserIdAndBecode(merchantUserSet);
        return rows;
	}
	
	@Override
	public Integer deleteByUserIdAndBecode(MerchantUserSet merchantUserSet){
		if(merchantUserSet == null || Assert.empty(merchantUserSet.getUserId()) || Assert.empty(merchantUserSet.getBeCode())){
			logger.info("参数为空，删除失败");
			return null;
		}
        Integer rows = merchantUserSetDao.deleteByUserIdAndBecode(merchantUserSet);
        return rows;
	}
	
	@Override
	public MerchantUserSet getMerchantUserSetListByPager(MerchantUserSet merchantUserSet) {
		if(merchantUserSet==null){
			logger.info("分页查询操作员参数对象不能为空！");
			return null;
		}
		return merchantUserSetDao.getMerchantUserSetListByPager(merchantUserSet);
	}
	
	@Override
	public String getBelongUserBecode(String storeId) {
		return merchantUserSetDao.getBelongUserBecode(storeId);
	}
	
	public void removeCacheMerchantUserSet(){
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		ehCacheUtil.remove(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_USER_SET_KEY);
	}
	
	public void setMerchantUserSetDao(MerchantUserSetDao merchantUserSetDao) {
		this.merchantUserSetDao = merchantUserSetDao;
	}

	

}
