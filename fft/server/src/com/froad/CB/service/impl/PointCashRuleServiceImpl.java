package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.PointCashRuleDao;
import com.froad.CB.po.MerchantIndustry;
import com.froad.CB.po.PointCashRule;
import com.froad.CB.service.PointCashRuleService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

/** 
 * @author TXL 
 * @date 2013-2-25 pm
 * @version 1.0
 * 
 */
@WebService(endpointInterface = "com.froad.CB.service.PointCashRuleService")
public class PointCashRuleServiceImpl implements PointCashRuleService {

	private PointCashRuleDao pointCashRuleDao;
	private static final Logger log = Logger
			.getLogger(PointCashRuleServiceImpl.class);

	public PointCashRuleDao getPointCashRuleDao() {
		return pointCashRuleDao;
	}
	
	public void setPointCashRuleDao(PointCashRuleDao pointCashRuleDao) {
		this.pointCashRuleDao = pointCashRuleDao;
	}
	
	public PointCashRule getByID(Integer id) {
		if (id == null) {
			log.info("参数ID不能为空！");
			return null;
		}
		return pointCashRuleDao.getByID(id);
	}

	@Override
	public List<PointCashRule> getAllPointCashRule() {
		
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance(); 
		List<PointCashRule> list=(List<PointCashRule>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME, CacheKey.POINTCASHRULE_CACHE_KEY);
		if(list!=null){
			log.info("缓存 key=pointCashRule存在数据！");
			return list;
		}
		log.info("缓存 key=pointCashRule 不存在数据,查找数据库！");
		list=pointCashRuleDao.getAllPointCashRule();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.POINTCASHRULE_CACHE_KEY,list);
		return list;
	}
	
	

}
