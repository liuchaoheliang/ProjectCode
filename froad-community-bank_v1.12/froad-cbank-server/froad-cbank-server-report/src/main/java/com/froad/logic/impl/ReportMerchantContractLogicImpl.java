package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.ReportMyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportSignMerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryDetailMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryMapper;
import com.froad.enums.MerchantTypeEnum;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantContractLogic;
import com.froad.po.CommonParam;
import com.froad.po.MerchantContractDeatailRes;
import com.froad.po.MerchantContractRankRes;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportSignMerchant;
import com.froad.po.ReportSignSummary;
import com.froad.po.ReportSignSummaryDetail;
import com.froad.po.TypePercentInfo;
import com.froad.po.mongo.ReportSignMerchantMongo;
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

public class ReportMerchantContractLogicImpl implements
		ReportMerchantContractLogic {
	
	@Override
	public List<MerchantContractRankRes> merchantContractRank(CommonParam param)
			throws FroadBusinessException, Exception {
		List<MerchantContractRankRes> contracts = new ArrayList<MerchantContractRankRes>();
		SqlSession rpSqlSession = null;
		Date curDate = new Date();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignSummaryMapper signSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
			
			Date maxDate = signSummaryMapper.findSummaryMaxDate();
			Integer maxDateStr = Integer.valueOf(DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, maxDate));
			Integer nowDateStr = Integer.valueOf(DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, curDate));
			Date begDate = nowDateStr >= maxDateStr ? TaskTimeUtil.getStartDay(maxDate) : TaskTimeUtil.getStartDay(0);
			Date endDate = nowDateStr >= maxDateStr ? TaskTimeUtil.getEndDay(maxDate) : TaskTimeUtil.getEndDay(0);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportSignSummary> summarys = signSummaryMapper.selectContractRank(begDate, endDate, user, flag);
			for(int i = 0; i < summarys.size(); i++){
				ReportSignSummary s = summarys.get(i);
				MerchantContractRankRes m = new MerchantContractRankRes();
				m.setConstractStaff(s.getSignUserName());
				m.setCount(s.getTotalMerchants().intValue());
				m.setSort(i);
				contracts.add(m);
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return contracts;
	}

	@Override
	public List<TypePercentInfo> merchantTypeAddPercent(CommonParam param)
			throws FroadBusinessException, Exception {
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		Map<String, TypePercentInfo> infoMap = new HashMap<String, TypePercentInfo>();
		SqlSession rpSqlSession = null;
		String merchantType = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			Date curDate = new Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date begDate = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportSignMerchant> addRanks = signMerchantMapper.selecTypeAddPercent(begDate, endDate, user, flag);
			Integer totalNew = 0;
			for(ReportSignMerchant m : addRanks){
				totalNew += m.getNewCount();
			}
			
			for(ReportSignMerchant m : addRanks){
				TypePercentInfo type = new TypePercentInfo();
				type.setType(m.getType());
				type.setTypeName(MerchantTypeEnum.getByType(m.getType()).getDesc());
				type.setPercent(ObjectUtils.equals(0, totalNew) ?  0d : Arith.div(m.getNewCount(), totalNew, 4));
//				infos.add(type);
				infoMap.put(m.getType(), type);
			}
			
			//固定返回顺序
			merchantType = MerchantTypeEnum.group.getType();
			if (infoMap.containsKey(merchantType)){
				infos.add(infoMap.get(merchantType));
			}
			merchantType = MerchantTypeEnum.face.getType();
			if (infoMap.containsKey(merchantType)){
				infos.add(infoMap.get(merchantType));
			}
			merchantType = MerchantTypeEnum.special.getType();
			if (infoMap.containsKey(merchantType)){
				infos.add(infoMap.get(merchantType));
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	
	@Override
	public List<MerchantContractRankRes> merchantContractAddRank(
			CommonParam param) throws FroadBusinessException, Exception {
		List<MerchantContractRankRes> contracts = new ArrayList<MerchantContractRankRes>();
		SqlSession rpSqlSession = null;
		ReportSignSummaryDetail detail = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignSummaryDetailMapper signSummaryDetailMapper = rpSqlSession.getMapper(ReportSignSummaryDetailMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			Date curDate = new Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date begDate = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportSignSummaryDetail> details = signSummaryDetailMapper.selectContractAddRank(begDate, endDate, user, flag);
			for(int i = 0; i < details.size(); i++){
				detail = details.get(i);
				MerchantContractRankRes m = new MerchantContractRankRes();
				m.setConstractStaff(detail.getSignUserName());
				m.setCount(detail.getNewTotalMerchant().intValue());
				m.setSort(i);
				contracts.add(m);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		
		return contracts;
	}

	@Override
	public List<MerchantContractDeatailRes> merchantContractDetailList(
			CommonParam param) throws FroadBusinessException, Exception {
		List<MerchantContractDeatailRes> res = new ArrayList<MerchantContractDeatailRes>();
		SqlSession rpSqlSession = null;
		Set<String> leftSet = null;
		Iterator<String> iterator = null;
		String signUserName = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			ReportSignSummaryMapper signSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Date begDate = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date lbegDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getStartDay(new Date()) : TaskTimeUtil.getStartDay(param.getEndDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			List<ReportSignMerchant> signs = signMerchantMapper.selectContractDetailList(begDate, endDate, user, flag);
			
			Integer totalNews = 0;
			for(ReportSignMerchant m : signs){
				totalNews += m.getNewCount();
			}
			
			List<ReportSignSummary> summarys = signSummaryMapper.getTotalMechantsBySignUser(lbegDate, endDate, user, flag);
			Map<String, Integer> summaryMap = new HashMap<String, Integer>();
			for(ReportSignSummary s : summarys){
				summaryMap.put(s.getSignUserName(), s.getTotalMerchants().intValue());
			}
			
			leftSet = new HashSet<String>();
			leftSet.addAll(summaryMap.keySet());
			// 去掉已统计的新增或动帐
			for (int j = 0; j < signs.size(); j++) {
				leftSet.remove(signs.get(j).getSignUserName());
			}
			iterator = leftSet.iterator();
			while (iterator.hasNext()) {
				signUserName = iterator.next();
				if (summaryMap.get(signUserName) > 0) {
					ReportSignMerchant sign = new ReportSignMerchant();
					sign.setNewCount(0);
					sign.setChangeCount(0);
					sign.setTotalMerchants(summaryMap.get(sign
							.getSignUserName()));
					sign.setSignUserName(signUserName);
					sign.setTotalOrders(0l);
					sign.setTotalAmount(0l);
					signs.add(sign);
				}
			}
			
			for(ReportSignMerchant sign : signs){
				MerchantContractDeatailRes m = new MerchantContractDeatailRes();
				m.setAddCount(sign.getNewCount());
				m.setChangeCount(sign.getChangeCount());
				m.setTotalCount(summaryMap.get(sign.getSignUserName()));
				if (Checker.isNotEmpty(sign.getSignUserName())){
					m.setConstractStaff(sign.getSignUserName());
				} else {
					m.setConstractStaff("无签约人");
				}
				m.setAddPercent(ObjectUtils.equals(0, totalNews) ? 0 : Arith.div(sign.getNewCount(), totalNews, 4));
				m.setOrderCount(sign.getTotalOrders().intValue());
				m.setTotalAmount(Arith.div(sign.getTotalAmount(), 1000, 2));
				res.add(m);
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return res;
	}

	@Override
	public Page<MerchantContractDeatailRes> merchantContractDetailListByPage(
			Page<MerchantContractDeatailRes> page, CommonParam param)
			throws FroadBusinessException, Exception {
		SqlSession rpSqlSession = null;
		StringBuffer keyStr = null;
		String md5Key = null;
		String redisKey = null;
		Map<String, String> hash = null;
		RedisUtil redisUtil = null;
		
		List<MerchantContractDeatailRes> res = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			ReportSignSummaryMapper signSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Date begDate = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date lbegDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getStartDay(new Date()) : TaskTimeUtil.getStartDay(param.getEndDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			// 检查是否命中缓存
			keyStr = new StringBuffer();
			keyStr.append(begDate);
			keyStr.append(endDate);
			keyStr.append(user.getClientId());
			keyStr.append(flag);
			keyStr.append(user.getForgCode());
			keyStr.append(user.getSorgCode());
			keyStr.append(user.getTorgCode());
			keyStr.append(user.getLorgCode());
			LogCvt.info("查询条件: " + keyStr.toString());
			md5Key = MD5Util.getMD5Str(keyStr.toString());
			redisKey = RedisUtil.cbbank_merchant_contract_md5key(md5Key);
			redisUtil = new RedisUtil();
			hash = redisUtil.hgetAll(redisKey);
			
			if (Checker.isEmpty(hash)){
				LogCvt.info("没有命中缓存，使用mysql查找");
				
//				List<ReportSignMerchant> signs = signMerchantMapper.selectContractDetailListByPage(begDate, endDate, user, page, flag);
				Integer totalNews = signMerchantMapper.getTotalNewMerchants(begDate, endDate, user, flag);
				if(Checker.isEmpty(totalNews)){
					totalNews = 0;
				}
				
				List<ReportSignSummary> summarys = signSummaryMapper.getTotalMechantsBySignUser(lbegDate, endDate, user, flag);
				Map<String, Integer> summaryMap = new HashMap<String, Integer>();
				for(ReportSignSummary s : summarys){
					summaryMap.put(s.getSignUserName(), s.getTotalMerchants().intValue());
				}
				
				//异步插入查找结果到mongodb
				asyncToMongo(redisUtil, begDate, endDate, user, flag, md5Key, totalNews, summaryMap);
				
				hash = redisUtil.hgetAll(redisKey);
				while (Checker.isEmpty(hash)) {
					Thread.sleep(100);
					hash = redisUtil.hgetAll(redisKey);
				}
				
				MongoUtil mongoUtil = null;
				List<ReportSignMerchantMongo> signMongos = null;
				DBObject dbObject = null;
				String collection = null;
				int limit = 0;
				
				mongoUtil = new MongoUtil();
				dbObject = new BasicDBObject();
				dbObject.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				collection = hash.get("collection");
				limit = page.getPageSize();
				signMongos = mongoUtil.findByLimit(dbObject, collection, limit, ReportSignMerchantMongo.class);
				
				LogCvt.info("签约人列表mongo分页"+signMongos.size()+"记录");
				res = new ArrayList<MerchantContractDeatailRes>();
				for(ReportSignMerchantMongo s : signMongos){
					MerchantContractDeatailRes m = new MerchantContractDeatailRes();
					m.setConstractStaff(s.getSignMerchant().getSignUserName());
					m.setAddCount(s.getSignMerchant().getNewCount());
					m.setChangeCount(s.getSignMerchant().getChangeCount());
					m.setTotalCount(s.getSignMerchant().getTotalMerchants());
					m.setAddPercent(ObjectUtils.equals(0, totalNews) ? 0 : Arith.div(s.getSignMerchant().getNewCount(), totalNews, 4));
					m.setOrderCount(s.getSignMerchant().getTotalOrders().intValue());
					m.setTotalAmount(Arith.div(s.getSignMerchant().getTotalAmount(), 1000, 2));
					res.add(m);
				}
				
				page.setTotalCount(Integer.valueOf(hash.get("count")));
			} else {
				LogCvt.info("命中缓存，使用mongo查找");
				
				// mongodb获取分页信息
				MongoUtil mongoUtil = null;
				List<ReportSignMerchantMongo> signMongos = null;
				DBObject dbObject = null;
				String collection = null;
				int limit = 0;
				Integer totalNews = 0;
				
				totalNews = Integer.valueOf(hash.get("total_news"));
				
				mongoUtil = new MongoUtil();
				dbObject = new BasicDBObject();
				dbObject.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				collection = hash.get("collection");
				limit = page.getPageSize();
//				mongoUtil.dropCollection(collection);//删除旧数据，以防存在同名集合
				signMongos = mongoUtil.findByLimit(dbObject, collection, limit, ReportSignMerchantMongo.class);
				
				res = new ArrayList<MerchantContractDeatailRes>();
				for(ReportSignMerchantMongo s : signMongos){
					MerchantContractDeatailRes m = new MerchantContractDeatailRes();
					m.setConstractStaff(s.getSignMerchant().getSignUserName());
					m.setAddCount(s.getSignMerchant().getNewCount());
					m.setChangeCount(s.getSignMerchant().getChangeCount());
					m.setTotalCount(s.getSignMerchant().getTotalMerchants());
					m.setAddPercent(ObjectUtils.equals(0, totalNews) ? 0 : Arith.div(s.getSignMerchant().getNewCount(), totalNews, 4));
					m.setOrderCount(s.getSignMerchant().getTotalOrders().intValue());
					m.setTotalAmount(Arith.div(s.getSignMerchant().getTotalAmount(), 1000, 2));
					res.add(m);
				}
				
				page.setTotalCount(Integer.valueOf(hash.get("count")));
			}
			
			page.setResultsContent(res);
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
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
			final ReportBankUser user, final Boolean flag, final String md5Key, 
			final Integer totalNews, final Map<String, Integer> summaryMap) {
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				List<ReportSignMerchant> signs = null;
				ReportSignMerchant sign = null;
				MongoUtil mongoUtil = null;
				ReportSignMerchantMongo signMongo = null;
				List<DBObject> list = null;
				long totalCount = 0;
				String collection = "cb_contract_" + md5Key;
				Map<String, String> hash = null;
				Map<String, String> keyHash = null;
				String redisKey = null;
				List<List<DBObject>> splitList = null;
				ReportSignMerchantMapper signMerchantMapper = null;
				SqlSession rpSqlSession = null;
				Set<String> leftSet = null;
				Iterator<String> iterator = null;
				String signUserName = null;
				
				LogCvt.info(new StringBuffer("开始生成mongo临时集合：").append(collection).toString());
				
				rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
				signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
				signs = signMerchantMapper.selectContractDetailList(begDate, endDate, user, flag);
				LogCvt.info(new StringBuffer("总记录数：").append(signs == null ? 0 : signs.size()).toString());
				
				leftSet = new HashSet<String>();
				leftSet.addAll(summaryMap.keySet());
				// 去掉已统计的新增或动帐
				for (int j = 0; j < signs.size(); j++) {
					leftSet.remove(signs.get(j).getSignUserName());
				}
				iterator = leftSet.iterator();
				while (iterator.hasNext()) {
					signUserName = iterator.next();
					if (summaryMap.get(signUserName) > 0) {
						sign = new ReportSignMerchant();
						sign.setNewCount(0);
						sign.setChangeCount(0);
						sign.setTotalMerchants(summaryMap.get(sign.getSignUserName()));
						sign.setSignUserName(signUserName);
						sign.setTotalOrders(0l);
						sign.setTotalAmount(0l);
						signs.add(sign);
					}
				}
				
				if (Checker.isEmpty(signs)){
					return;
				}
				
				list = new ArrayList<DBObject>();
				for (int i = 0; i < signs.size(); i++){
					signMongo = new ReportSignMerchantMongo();
					signMongo.setIndex(i+1);
					sign = signs.get(i);
					sign.setTotalMerchants(summaryMap.get(sign.getSignUserName()));
					if (Checker.isEmpty(sign.getSignUserName())){
						sign.setSignUserName("无签约人");
					}
					signMongo.setSignMerchant(sign);
					
					DBObject mongoObj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(signMongo));
					list.add(mongoObj);
				}
				
				mongoUtil = new MongoUtil();
				mongoUtil.dropCollection(collection);
				splitList = CollectionsUtil.splitList(list, 1000);
				for (int i = 0; i < splitList.size(); i++){
					mongoUtil.insert(splitList.get(i), collection);
					LogCvt.info(new StringBuffer(i+1).append("批次已生成到mongo").toString());
				}
				
				LogCvt.info("异步生成查询结果到mongo完成");
				
				totalCount = signs.size();
				hash = new HashMap<String, String>();
				hash.put("count", String.valueOf(totalCount));
				hash.put("total_news", String.valueOf(totalNews));
				hash.put("collection", collection);
				redisKey = RedisUtil.cbbank_merchant_contract_md5key(md5Key);
				redisUtil.hmset(redisKey, hash);
				
				// push redis key to collection hash
				keyHash = new HashMap<String, String>();
				keyHash.put(redisKey, collection);
				redisUtil.hmset(RedisUtil.MONGO_COLLECTION_KEYS, keyHash);
				
				if(rpSqlSession != null){
					rpSqlSession.close();
				}
			}
		});
	}
}
