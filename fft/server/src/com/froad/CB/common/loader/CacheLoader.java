package com.froad.CB.common.loader;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.SpringContextUtil;
import com.froad.CB.po.MerchantIndustry;
import com.froad.CB.po.PointCashRule;
import com.froad.CB.po.complaint.ComplaintCategory;
import com.froad.CB.po.tag.TagClassifyA;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.po.tag.TagDistrictB;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;

/** 
 * @author FQ 
 * @date 2013-1-4 下午01:37:29
 * @version 1.0
 * 
 */
public class CacheLoader{
	
	private static Logger logger = Logger.getLogger(CacheLoader.class);
	
	public CacheLoader(){
		loadCache();
	}

	public void loadCache(){
		logger.info("基础数据初始化加载开始");
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance(); 
		SqlMapClientTemplate sqlMapClientTemplate=(SqlMapClientTemplate)SpringContextUtil.getBean("sqlMapClientTemplate");
		logger.info("加载投诉分类");
		List<ComplaintCategory> complaintCategoryList=sqlMapClientTemplate.queryForList("complaintCategory.selectAll");
		if(complaintCategoryList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.COMPLAINTCATEGORY_CACHE_KEY, complaintCategoryList);
			logger.info("投诉分类 缓存数量："+complaintCategoryList.size());
		}
		
		logger.info("加载分类一级标签");
		List<TagClassifyA> tagClassifyAList=sqlMapClientTemplate.queryForList("tagClassifyA.getAllTagClassifyA");
		if(tagClassifyAList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYA_CACHE_KEY, tagClassifyAList);
			logger.info("分类一级标签 缓存数量："+tagClassifyAList.size());
		}
		
		logger.info("加载分类二级标签");
		List<TagClassifyB> tagClassifyBList=sqlMapClientTemplate.queryForList("tagClassifyB.getAllTagClassifyB");
		if(tagClassifyBList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYB_CACHE_KEY, tagClassifyBList);
			logger.info("分类二级标签 缓存数量："+tagClassifyBList.size());
		}
		
		logger.info("加载一级商圈标签");
		List<TagDistrictA> tagDistrictAList=sqlMapClientTemplate.queryForList("tagDistrictA.getAllTagDistrictA");
		if(tagDistrictAList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTA_CACHE_KEY, tagDistrictAList);
			logger.info("商圈一级标签 缓存数量："+tagDistrictAList.size());
		}
		
		logger.info("加载二级商圈标签");
		List<TagDistrictB> tagDistrictBList=sqlMapClientTemplate.queryForList("tagDistrictB.getAllTagDistrictB");
		if(tagDistrictBList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTB_CACHE_KEY, tagDistrictBList);
			logger.info("二级商圈标签 缓存数量："+tagDistrictBList.size());
		}
		
		logger.info("加载商户在线合作申请行业");
		List<MerchantIndustry> merchantIndustryList=sqlMapClientTemplate.queryForList("merchantIndustry.selectAll");
		if(merchantIndustryList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.MERCHANT_INDUSTRY_CACHE_KEY,merchantIndustryList);
			logger.info("商户在线合作申请行业 缓存数量："+merchantIndustryList.size());
		}
		
		logger.info("加载积分与现金规则");
		List<PointCashRule> pointCashRuleList=sqlMapClientTemplate.queryForList("pointCashRule.getAllPointCashRule");
		if(pointCashRuleList.size()>0){
			ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.POINTCASHRULE_CACHE_KEY,pointCashRuleList);
			logger.info("积分与现金规则 缓存数量："+pointCashRuleList.size());
		}
		logger.info("基础数据初始化加载结束");
	}

}
