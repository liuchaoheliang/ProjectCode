package com.froad.logic.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.ReportMyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportProductMapper;
import com.froad.db.mysql.rp_mappers.ReportProductSummaryMapper;
import com.froad.enums.ReportOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportProductLogic;
import com.froad.po.CommonParam;
import com.froad.po.ProductCategoryCount;
import com.froad.po.ProductSaleDetail;
import com.froad.po.ProductSaleTrend;
import com.froad.po.ProductTypeCount;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantSale;
import com.froad.po.ReportProductSummary;
import com.froad.po.TypePercentInfo;
import com.froad.po.mongo.ProductSaleDetailMongo;
import com.froad.thread.FroadThreadPool;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.CommonUtil;
import com.froad.util.DateConfigUtil;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MD5Util;
import com.froad.util.MongoUtil;
import com.froad.util.RedisUtil;
import com.froad.util.TaskTimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

/**
 * 社区银行商品统计
 * @ClassName: ReportProductServiceImpl 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月5日 下午10:21:04
 */
public class ReportProductServiceLogicImpl implements ReportProductLogic {
	
	/**
	 * 商品销售走势
	* @Title: getProductSaleTrend 
	* @Description: 
	* @author longyunbo
	* @param @return
	* @return List<ProductSaleTrend>
	* @throws
	 */
	public List<ProductSaleTrend> getProductSaleTrend(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<ProductSaleTrend> saleTrendList = new ArrayList<ProductSaleTrend>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportProductMapper reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<ReportMerchantSale> reportMerchantSaleList = reportProductMapper.findSaleTrend(user, orgCode);
			
			Calendar c = Calendar.getInstance();
			int nowWeek = c.get(Calendar.WEEK_OF_YEAR);
			int begWeek = nowWeek - 11;
			ProductSaleTrend saleTrend = null;
			ReportMerchantSale sale = null;
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for(int i=0; i<reportMerchantSaleList.size(); i++){
				sale = reportMerchantSaleList.get(i);
				map.put(sale.getWeek(), sale.getSaleProductNumber().intValue());
			}
			
			if(begWeek >= 0){
				for(int i = 0; i < 12; i++){
					saleTrend = new ProductSaleTrend();
					saleTrend.setWeek(i+1);
					saleTrend.setSaleProductNumber(map.containsKey(begWeek+i) ? map.get(begWeek+i) : 0);
					saleTrendList.add(saleTrend);
				}
			}else{
				for(int i = 0; i < 12; i++){
					int cweek = (begWeek+i) <= 0 ? 52+(begWeek+i) : (begWeek+i);
					saleTrend = new ProductSaleTrend();
					saleTrend.setWeek(i+1);
					saleTrend.setSaleProductNumber(map.containsKey(cweek) ? map.get(cweek) : 0);
					saleTrendList.add(saleTrend);
				}
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计商品走势图失败，原因:" + e.getMessage(),e);
			throw e;
		} finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return saleTrendList;
	}
	
	
	/**
	 * 商品业务类型占比
	* @Title: getProductTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<ProductTypePercent>
	* @throws
	 */
	public List<TypePercentInfo> getProductTypePercent(CommonParam param) throws Exception{
		SqlSession rpSqlSession  = null;
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportProductMapper reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			Date curDate = new Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<ProductTypeCount> productTypeCountList = reportProductMapper.findProductTypeCount(start, end, user, orgCode); 
			Long total = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			for(ProductTypeCount s : productTypeCountList){
				total += s.getSaleProductNumber();
				map.put(s.getType(), s.getSaleProductNumber());
			}
			
			Set<String> set = map.keySet();
			for(String type : set){
				TypePercentInfo info = new TypePercentInfo();
				info.setType(type);
				if (type.equals(ReportOrderType.presell.getCode()) && param.getClientId().equals("chongqing")){
					info.setTypeName("惠预售");
				} else {
					info.setTypeName(ReportOrderType.getByType(type).getProductDesc());
				}
				Long totals = map.get(type);
				info.setPercent(ObjectUtils.equals(0l, total) ? 0d : Arith.div(totals, total, 4));
				infos.add(info);
			}
			
		} catch (Exception e) {
			LogCvt.error("统计商品业务类型占比失败，原因:" + e.getMessage(),e);
			throw e;
		} finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	
	/**
	 * 商品类目占比
	* @Title: getProductCategoryPercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<ProductCategoryPercent>
	* @throws
	 */
	public List<TypePercentInfo> getProductCategoryPercent(CommonParam param) throws Exception{
		SqlSession rpSqlSession  = null;
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportProductMapper reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			Date curDate = new Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<ProductCategoryCount> productCategoryCountList = reportProductMapper.findProductCategoryCount(start, end, user, orgCode);
			Long total = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			Map<String,String> categoryMap = new HashMap<String,String>();
			
			for(ProductCategoryCount s : productCategoryCountList){
				total += s.getSaleProductAmount();
				map.put(s.getType(), s.getSaleProductAmount());
				categoryMap.put(s.getType(),s.getTypeName());
			}
			
			Set<String> set = map.keySet();
			for(String type : set){
				TypePercentInfo info = new TypePercentInfo();
				info.setType(type);
				Long totals = map.get(type);
				info.setTypeName(categoryMap.get(type));
				info.setPercent(ObjectUtils.equals(0l, total) ? 0d : Arith.div(totals, total, 4));
				infos.add(info);
			}
			
		} catch (Exception e) {
			LogCvt.error("统计商品类目占比失败，原因:" + e.getMessage(),e);
			throw e;
		} finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	
	/**
	 * 商品销售详情列表
	* @Title: getProductSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<ProductSaleDetail>
	* @throws
	 */
	public List<ProductSaleDetail> getProductSaleDetail(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<ProductSaleDetail> infos = new ArrayList<ProductSaleDetail>();
		Map<String, Long> countMap = null;
		int productCount = 0;
		ReportProductSummaryMapper summaryMapper = null;
		List<ReportProductSummary> countList = null;
		SimpleDateFormat dateFormat = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportProductMapper reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<ProductSaleDetail> productSaleDetailList = reportProductMapper.findProductSaleDetail(start, end, bu, orgCode);
			
			summaryMapper = rpSqlSession.getMapper(ReportProductSummaryMapper.class);
			dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT2);
			countList = summaryMapper.findByDate(param.getClientId(), Integer.valueOf(dateFormat.format(end)));
			countMap = new HashMap<String, Long>();
			if (Checker.isNotEmpty(countList)){
				for (ReportProductSummary summary : countList){
					countMap.put(summary.getOrgCode(), summary.getTotalProducts());
				}
			}
			
			for(ProductSaleDetail ps : productSaleDetailList){
				ProductSaleDetail productSaleDetail = new ProductSaleDetail();
				productCount = 0;
				//商品数不统计面对面商品和下架商品
				if (countMap.containsKey(ps.getOrgCode())){
					productCount = countMap.get(ps.getOrgCode()).intValue();
				}
				productSaleDetail.setProductCount(productCount);
				if (ps.getAddProductCount() > productCount){
					productSaleDetail.setAddProductCount(productCount);
				} else {
					productSaleDetail.setAddProductCount(ps.getAddProductCount());//新增不统计面对面商品
				}
				productSaleDetail.setProductSaleCount(ps.getProductSaleCount());
				productSaleDetail.setProductSaleAmount(Arith.div(ps.getProductSaleAmount(), 1000, 2));
				productSaleDetail.setRefundAmount(Arith.div(ps.getRefundAmount(), 1000, 2));
				productSaleDetail.setOrgCode(ps.getOrgCode());
				productSaleDetail.setOrgName(ps.getOrgName());
				infos.add(productSaleDetail);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计商品销售详情列表失败，原因:" + e.getMessage(),e);
			throw e;
		} finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	
	/**
	 * 商品销售详情列表(分页)
	* @Title: getProductSaleDetailListByPage 
	* @Description: 
	* @author longyunbo
	* @param @param page
	* @param @param param
	* @param @return
	* @return Page<ProductSaleDetail>
	* @throws
	 */
	public Page<ProductSaleDetail> getProductSaleDetailListByPage(Page<ProductSaleDetail> page, CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		StringBuffer keyStr = null;
		String md5Key = null;
		String redisKey = null;
		Map<String, String> hash = null;
		RedisUtil redisUtil = null;
		int productCount = 0;
		Map<String, Long> countMap = null;
		ReportProductSummaryMapper summaryMapper = null;
		List<ReportProductSummary> countList = null;
		SimpleDateFormat dateFormat = null;
		
		List<ProductSaleDetail> infos = new ArrayList<ProductSaleDetail>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportProductMapper reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			
			keyStr = new StringBuffer();
			keyStr.append(start);
			keyStr.append(end);
			keyStr.append(param.getClientId());
			keyStr.append(param.getOrgCode());
			keyStr.append(param.getFlag());
			md5Key = MD5Util.getMD5Str(keyStr.toString());
			redisKey = RedisUtil.cbbank_product_sale_md5key(md5Key);
			redisUtil = new RedisUtil();
			hash = redisUtil.hgetAll(redisKey);
			
			if (Checker.isEmpty(hash)){
				LogCvt.info("没有命中缓存，使用mysql查找");
				//异步插入查找结果到mongodb
				asyncToMongo(redisUtil, start, end, bu, orgCode, md5Key, param.getClientId());
				
				List<ProductSaleDetail> productSaleDetailList = reportProductMapper.findProductSaleDetailListByPage(start, end, bu, page, orgCode);
				
				summaryMapper = rpSqlSession.getMapper(ReportProductSummaryMapper.class);
				dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT2);
				countList = summaryMapper.findByDate(param.getClientId(), Integer.valueOf(dateFormat.format(end)));
				countMap = new HashMap<String, Long>();
				if (Checker.isNotEmpty(countList)){
					for (ReportProductSummary summary : countList){
						countMap.put(summary.getOrgCode(), summary.getTotalProducts());
					}
				}
				
				for(ProductSaleDetail ps : productSaleDetailList){
					ProductSaleDetail productSaleDetail = new ProductSaleDetail();
					productCount = ps.getProductCount();
					//商品数不统计面对面商品和下架商品
					productCount = 0;
					//商品数不统计面对面商品和下架商品
					if (countMap.containsKey(ps.getOrgCode())){
						productCount = countMap.get(ps.getOrgCode()).intValue();
					}
					if (ps.getAddProductCount() > productCount){
						productSaleDetail.setAddProductCount(productCount);
					} else {
						productSaleDetail.setAddProductCount(ps.getAddProductCount());//新增不统计面对面商品
					}
					productSaleDetail.setProductCount(productCount);//商品数不统计面对面商品
					productSaleDetail.setProductSaleCount(ps.getProductSaleCount());
					productSaleDetail.setProductSaleAmount(Arith.div(ps.getProductSaleAmount(), 1000, 2));
					productSaleDetail.setRefundAmount(Arith.div(ps.getRefundAmount(), 1000, 2));
					productSaleDetail.setOrgCode(ps.getOrgCode());
					productSaleDetail.setOrgName(ps.getOrgName());
					infos.add(productSaleDetail);
				}
			} else {
				LogCvt.info("命中缓存，使用mongo查找");
				
				// mongodb获取分页信息
				MongoUtil mongoUtil = null;
				List<ProductSaleDetailMongo> productSaleMongos = null;
				DBObject dbObject = null;
				String collection = null;
				int limit = 0;
				
				mongoUtil = new MongoUtil();
				dbObject = new BasicDBObject();
				dbObject.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				collection = hash.get("collection");
				limit = page.getPageSize();
				productSaleMongos = mongoUtil.findByLimit(dbObject, collection, limit, ProductSaleDetailMongo.class);
				
				for(ProductSaleDetailMongo ps : productSaleMongos){
					ProductSaleDetail productSaleDetail = new ProductSaleDetail();
					productSaleDetail.setAddProductCount(ps.getProductSaleDetail().getAddProductCount());//新增不统计面对面商品
					productCount = ps.getProductSaleDetail().getProductCount();
					productSaleDetail.setProductCount(productCount);//商品数不统计面对面商品
					productSaleDetail.setProductSaleCount(ps.getProductSaleDetail().getProductSaleCount());
					productSaleDetail.setProductSaleAmount(Arith.div(ps.getProductSaleDetail().getProductSaleAmount(), 1000, 2));
					productSaleDetail.setRefundAmount(Arith.div(ps.getProductSaleDetail().getRefundAmount(), 1000, 2));
					productSaleDetail.setOrgCode(ps.getProductSaleDetail().getOrgCode());
					productSaleDetail.setOrgName(ps.getProductSaleDetail().getOrgName());
					infos.add(productSaleDetail);
				}
				
				page.setTotalCount(Integer.valueOf(hash.get("count")));
			}
			
			page.setResultsContent(infos);
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计商品销售详情分页列表失败，原因:" + e.getMessage(),e);
			throw e;
		} finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return page;
	}
	
	/**
	 * 异步插入查找结果到mongo
	 * 
	 * @param redisUtil
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @param flag
	 */
	private void asyncToMongo(final RedisUtil redisUtil, final Date begDate, final Date endDate,
			final ReportBankUser user, final String orgCode, final String md5Key, final String clientId) {
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				List<ProductSaleDetail> productSaleDetailList = null;
				MongoUtil mongoUtil = null;
				ProductSaleDetailMongo productSaleMongo = null;
				List<DBObject> list = null;
				long totalCount = 0;
				String collection = "cb_product_sale_" + md5Key;
				Map<String, String> hash = null;
				Map<String, String> keyHash = null;
				String redisKey = null;
				List<List<DBObject>> splitList = null;
				ReportProductMapper reportProductMapper = null;
				SqlSession rpSqlSession = null;
				Map<String, Long> countMap = null;
				int productCount = 0;
				ReportProductSummaryMapper summaryMapper = null;
				List<ReportProductSummary> countList = null;
				SimpleDateFormat dateFormat = null;
				ProductSaleDetail productSale = null;
				
				LogCvt.info(new StringBuffer("开始生成mongo临时集合：").append(collection).toString());
				
				rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
				reportProductMapper = rpSqlSession.getMapper(ReportProductMapper.class);
				productSaleDetailList = reportProductMapper.findProductSaleDetail(begDate, endDate, user, orgCode);
				LogCvt.info(new StringBuffer("总记录数：").append(productSaleDetailList == null ? 0 : productSaleDetailList.size()).toString());
				
				if (Checker.isEmpty(productSaleDetailList)){
					return;
				}
				
				summaryMapper = rpSqlSession.getMapper(ReportProductSummaryMapper.class);
				dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT2);
				countList = summaryMapper.findByDate(clientId, Integer.valueOf(dateFormat.format(endDate)));
				countMap = new HashMap<String, Long>();
				if (Checker.isNotEmpty(countList)){
					for (ReportProductSummary summary : countList){
						countMap.put(summary.getOrgCode(), summary.getTotalProducts());
					}
				}
				
				list = new ArrayList<DBObject>();
				for (int i = 0; i < productSaleDetailList.size(); i++){
					productSaleMongo = new ProductSaleDetailMongo();
					productSaleMongo.setIndex(i+1);
					productSale = productSaleDetailList.get(i);
					productCount = productSale.getProductCount();
					//商品数不统计面对面商品和下架商品
					productCount = 0;
					//商品数不统计面对面商品和下架商品
					if (countMap.containsKey(productSale.getOrgCode())){
						productCount = countMap.get(productSale.getOrgCode()).intValue();
					}	
					productSale.setProductCount(productCount);
					if (productSale.getAddProductCount() > productCount){
						productSale.setAddProductCount(productCount);
					}
					productSaleMongo.setProductSaleDetail(productSale);
					
					DBObject mongoObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(productSaleMongo));
					list.add(mongoObj);
				}
				
				totalCount = productSaleDetailList.size();
				hash = new HashMap<String, String>();
				hash.put("count", String.valueOf(totalCount));
				hash.put("collection", collection);
				redisKey = RedisUtil.cbbank_product_sale_md5key(md5Key);
				redisUtil.hmset(redisKey, hash);
				
				// push redis key to collection hash
				keyHash = new HashMap<String, String>();
				keyHash.put(redisKey, collection);
				redisUtil.hmset(RedisUtil.MONGO_COLLECTION_KEYS, keyHash);
				
				mongoUtil = new MongoUtil();
				mongoUtil.dropCollection(collection);
				splitList = CollectionsUtil.splitList(list, 1000);
				for (int i = 0; i < splitList.size(); i++){
					mongoUtil.insert(splitList.get(i), collection);
					LogCvt.info(new StringBuffer(i+1).append("批次已生成到mongo").toString());
				}
				
				LogCvt.info("异步生成查询结果到mongo完成");
			}
		});
	}
}
