package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.MerchantClerkUrlDao;
import com.froad.CB.po.MerchantClerkUrl;
import com.froad.CB.service.MerchantClerkUrlService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantClerkUrlService")
public class MerchantClerkUrlServiceImpl implements MerchantClerkUrlService {
	private static final Logger logger=Logger.getLogger(MerchantClerkUrlServiceImpl.class);
	private MerchantClerkUrlDao merchantClerkUrlDao;
	
	@Override
	public Integer addMerchantClerkUrl(MerchantClerkUrl merchantClerkUrl) {
		if(merchantClerkUrl==null){
			logger.info("参数为空，添加失败");
			return null;
		}
		return merchantClerkUrlDao.addMerchantClerkUrl(merchantClerkUrl);
	}

	@Override
	public List<MerchantClerkUrl> getMerchantClerkUrl() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		List<MerchantClerkUrl> list=(List<MerchantClerkUrl>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_URL_CACHE_KEY);
		if(list!=null){
			return list;
		}
		
		logger.info("缓存 key=merchantClerkUrl 不存在数据,查找数据库！");
		list=merchantClerkUrlDao.getMerchantClerkUrl();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_URL_CACHE_KEY, list);
		return list;
	}

	public MerchantClerkUrlDao getMerchantClerkUrlDao() {
		return merchantClerkUrlDao;
	}

	public void setMerchantClerkUrlDao(MerchantClerkUrlDao merchantClerkUrlDao) {
		this.merchantClerkUrlDao = merchantClerkUrlDao;
	}

}
