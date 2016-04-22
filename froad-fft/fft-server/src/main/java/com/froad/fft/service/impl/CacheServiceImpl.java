package com.froad.fft.service.impl;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.fft.service.CacheService;

@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {
	
	private static Logger logger = Logger.getLogger(CacheServiceImpl.class);

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	
	@Override
	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	@Override
	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			logger.info("CacheNaems:"+JSON.toJSONString(cacheNames));
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	@CacheEvict(value = {
			"systemConfig",
			"sysClient",
			"agreement",
			"smsContent"
			}, allEntries = true)
	public void clear() {
		logger.info("执行清空ehcache");
	}

}
