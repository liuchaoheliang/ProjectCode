package com.froad.logic.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.ReportMyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportUserDetailMapper;
import com.froad.db.mysql.rp_mappers.ReportUserSummaryMapper;
import com.froad.db.mysql.rp_mappers.ReportUserTransMapper;
import com.froad.enums.ReportOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportUserLogic;
import com.froad.po.CommonParam;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUserDetail;
import com.froad.po.ReportUserSummary;
import com.froad.po.ReportUserTrans;
import com.froad.po.TypePercentInfo;
import com.froad.po.UserSummary;
import com.froad.po.UserTradeDetail;
import com.froad.po.UserTradeInfo;
import com.froad.po.UserTrend;
import com.froad.po.mongo.UserSummaryMongo;
import com.froad.thread.FroadThreadPool;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.CommonUtil;
import com.froad.util.DateConfigUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MD5Util;
import com.froad.util.MongoUtil;
import com.froad.util.RedisUtil;
import com.froad.util.TaskTimeUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;
import com.pay.user.helper.CreateChannel;

public class ReportUserLogicImpl implements ReportUserLogic {
	
	
	@Override
	public List<UserTrend> userTrend(CommonParam param)
			throws FroadBusinessException, Exception {
		List<UserTrend> trends = new ArrayList<UserTrend>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserSummaryMapper bankUserSummaryMapper = rpSqlSession.getMapper(ReportUserSummaryMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportUserSummary> list = bankUserSummaryMapper.selectMerchantTrend(bankJgm, flag);
			
			Calendar c = Calendar.getInstance();
			int nowWeek = c.get(Calendar.WEEK_OF_YEAR);
			int begWeek = nowWeek - 11;
			ReportUserSummary summary = null;
			UserTrend trend = null;
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			
			for(int i=0; i<list.size(); i++){
				summary = list.get(i);
				map.put(summary.getWeek(), summary.getTotalUser().intValue());
			}
			
			if(begWeek >= 0){
				for(int i = 0; i < 12; i++){
					trend = new UserTrend();
					trend.setWeek(i+1);
					trend.setUserCount(map.containsKey(begWeek+i) ? map.get(begWeek+i) : 0);
					trends.add(trend);
				}
			}else{
				for(int i = 0; i < 12; i++){
					int cweek = (begWeek+i) <= 0 ? 52+(begWeek+i) : (begWeek+i);
					trend = new UserTrend();
					trend.setWeek(i+1);
					trend.setUserCount(map.containsKey(cweek) ? map.get(cweek) : 0);
					trends.add(trend);
				}
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return trends;
	}

	
	@Override
	public List<TypePercentInfo> userTradeTypePercent(CommonParam param)
			throws FroadBusinessException, Exception {
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserTransMapper bankUserTransMapper = rpSqlSession.getMapper(ReportUserTransMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			java.util.Date curDate = new java.util.Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportUserTrans> list = bankUserTransMapper.selectConsumeTradeType(begDate, endDate, bankJgm, flag);
			Long totalUsers = 0l;
			for(ReportUserTrans b : list){
				totalUsers += b.getTotalUsers();
			}
			
			for(ReportUserTrans b : list){
				TypePercentInfo type = new TypePercentInfo();
				type.setType(b.getOrderType());
				type.setTypeName(Checker.isNotEmpty(ReportOrderType.getByType(b
						.getOrderType())) ? ReportOrderType.getByType(
						b.getOrderType()).getProductDesc() : "");
				type.setPercent(ObjectUtils.equals(0l, totalUsers) ? 0d : Arith.div(b.getTotalUsers(), totalUsers, 4));
				infos.add(type);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}


	@Override
	public List<TypePercentInfo> userConsumeTypePercent(CommonParam param)
			throws FroadBusinessException, Exception {
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserTransMapper bankUserTransMapper = rpSqlSession.getMapper(ReportUserTransMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			java.util.Date curDate = new java.util.Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportUserTrans> list = bankUserTransMapper.selectConsumeTradeType(begDate, endDate, bankJgm, flag);
			Long totalAmount = 0l;
			for(ReportUserTrans b : list){
				totalAmount += b.getTotalOrderAmount();
			}
			
			for(ReportUserTrans b : list){
				TypePercentInfo type = new TypePercentInfo();
				type.setType(b.getOrderType());
				type.setTypeName(Checker.isNotEmpty(ReportOrderType.getByType(b
						.getOrderType())) ? ReportOrderType.getByType(
						b.getOrderType()).getProductDesc() : "");
				type.setPercent(ObjectUtils.equals(0l, totalAmount) ? 0d : Arith.div(b.getTotalOrderAmount(), totalAmount, 4));
				infos.add(type);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}

	
	@Override
	public List<UserSummary> userSummaryList(CommonParam param)
			throws FroadBusinessException, Exception {
		List<UserSummary> summarys = new ArrayList<UserSummary>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserDetailMapper bankUserDetailMapper = rpSqlSession.getMapper(ReportUserDetailMapper.class);
			ReportUserSummaryMapper userSummaryMapper = rpSqlSession.getMapper(ReportUserSummaryMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			List<ReportUserDetail> list = bankUserDetailMapper.selectUserSummaryList(begDate, endDate, bankJgm, flag);
			Long totalNewUsers = bankUserDetailMapper.getTotalNewUsers(begDate, endDate, bankJgm, flag);
			List<ReportUserSummary> totalSummarys = userSummaryMapper.findTotalUserByCardBin(endDate, bankJgm, flag);
			
			Map<String,Long> totalInfo = new HashMap<String, Long>();
			for(ReportUserSummary s : totalSummarys){
				totalInfo.put(s.getClientId()+s.getBankCardId(), s.getTotalUser());
			}
			
			for(ReportUserDetail b : list){
				UserSummary s = new UserSummary();
				if(Checker.isNotEmpty(b.getSorgCode())){
					s.setOrgCode(b.getSorgCode());
					s.setOrgName(b.getSorgName());
				}else if(Checker.isNotEmpty(b.getForgCode())){
					s.setOrgCode(b.getForgCode());
					s.setOrgName(b.getForgName());
				}
				s.setAddCount(b.getNewCount().intValue());
				s.setChangeCount(b.getChangeCount().intValue());
				s.setTotalCount(totalInfo.get(b.getClientId()+b.getBankCardId()).intValue());
				s.setPercent(ObjectUtils.equals(0l, totalNewUsers) ? 0d : Arith.div(b.getNewCount(), totalNewUsers, 4));
				s.setOrderCount(b.getTotalOrder().intValue());
				s.setTotalAmount(Arith.div(b.getTotalAmount(), 1000, 2));
				summarys.add(s);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return summarys;
	}

	
	@Override
	public Page<UserSummary> userSummaryListByPage(CommonParam param,
			Page<UserSummary> page) throws FroadBusinessException, Exception {
		List<UserSummary> summarys = new ArrayList<UserSummary>();
		SqlSession rpSqlSession = null;
		RedisUtil redis = null;
		MongoUtil mongo = null;
		StringBuffer keyStr = null;
		Map<String, String> resultMap = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserDetailMapper bankUserDetailMapper = rpSqlSession.getMapper(ReportUserDetailMapper.class);
			ReportUserSummaryMapper userSummaryMapper = rpSqlSession.getMapper(ReportUserSummaryMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			redis = new RedisUtil();
			mongo = new MongoUtil();
			keyStr = new StringBuffer();
			
			keyStr.append(begDate).append(endDate).append(param.getClientId()).append(param.getOrgCode()).append(flag);
			String md5Key = MD5Util.getMD5Str(keyStr.toString());
			String redisKey = RedisUtil.cbbank_user_summary_md5key(md5Key);
			resultMap = redis.hgetAll(redisKey);
			
			if(Checker.isEmpty(resultMap)){
				List<ReportUserDetail> list = bankUserDetailMapper.selectUserSummaryListByPage(begDate, endDate, bankJgm, page, flag);
				Long totalNewUsers = bankUserDetailMapper.getTotalNewUsers(begDate, endDate, bankJgm, flag);
				List<ReportUserSummary> totalSummarys = userSummaryMapper.findTotalUserByCardBin(endDate, bankJgm, flag);
				
				Map<String, Integer> totalInfo = new HashMap<String, Integer>();
				for(ReportUserSummary s : totalSummarys){
					totalInfo.put(s.getClientId()+s.getBankCardId(), s.getTotalUser().intValue());
				}
				
				for(ReportUserDetail b : list){
					UserSummary s = new UserSummary();
					if(Checker.isNotEmpty(b.getSorgCode())){
						s.setOrgCode(b.getSorgCode());
						s.setOrgName(b.getSorgName());
					}else if(Checker.isNotEmpty(b.getForgCode())){
						s.setOrgCode(b.getForgCode());
						s.setOrgName(b.getForgName());
					}
					s.setAddCount(b.getNewCount().intValue());
					s.setChangeCount(b.getChangeCount().intValue());
					s.setTotalCount(totalInfo.get(b.getClientId()+b.getBankCardId()));
					s.setPercent(ObjectUtils.equals(0l, totalNewUsers) ? 0d : Arith.div(b.getNewCount(), totalNewUsers, 4));
					s.setOrderCount(b.getTotalOrder().intValue());
					s.setTotalAmount(Arith.div(b.getTotalAmount(), 1000, 2));
					summarys.add(s);
				}
				
				asyncToMongo(redis, md5Key, begDate, endDate, bankJgm, flag, totalNewUsers.intValue(), totalInfo);
				
			}else{
				page.setTotalCount(Integer.parseInt(resultMap.get("count")));
				String collection = resultMap.get("collection");
				
				DBObject query = new BasicDBObject();
				query.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				List<UserSummaryMongo> mongos = mongo.findByLimit(query, collection, page.getPageSize(), UserSummaryMongo.class);
				
				for(UserSummaryMongo s : mongos){
					summarys.add(s.getUserSummary());
				}
				
			}
			page.setResultsContent(summarys);
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return page;
	}
	
	
	@Override
	public List<UserTradeDetail> userTradeDetailList(CommonParam param)
			throws FroadBusinessException, Exception {
		List<UserTradeDetail> details = new ArrayList<UserTradeDetail>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserTransMapper bankUserTransMapper = rpSqlSession.getMapper(ReportUserTransMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			List<ReportUserTrans> list = bankUserTransMapper.selectUserTradeDetailList(begDate, endDate, bankJgm, flag);
			for (ReportUserTrans b : list) {
				UserTradeDetail d = new UserTradeDetail();
				if(Checker.isNotEmpty(b.getSorgCode())){
					d.setOrgCode(b.getSorgCode());
					d.setOrgName(b.getSorgName());
				}else if(Checker.isNotEmpty(b.getForgCode())){
					d.setOrgCode(b.getForgCode());
					d.setOrgName(b.getForgName());
				}
				d.setUserName(b.getUserName());
				d.setMobile(b.getMobile());
				d.setRegTime(b.getRegTime());
				d.setRegType(CreateChannel.valueOfName(b.getRegType()));
				d.setTotalOrderNumber(b.getTotalOrderNumber().intValue());//TODO
				d.setTotalOrderAmount(Arith.div(b.getTotalOrderAmount(), 1000, 2));//TODO
				d.setTotalPointNumber(b.getTotalPointNumber().intValue());
				d.setTotalPointAmount(Arith.div(b.getTotalPointAmount(), 1000, 2));
				d.setTotalFilmNumber(b.getTotalFilmNumber().intValue());
				d.setTotalFilmAmount(Arith.div(b.getTotalFilmAmount(), 1000, 2));
				d.setTotalQuickNumber(b.getTotalQuickNumber().intValue());
				d.setTotalQuickAmount(Arith.div(b.getTotalQuickAmount(), 1000, 2));
				d.setTotalPointFilmNumber(b.getTotalPointFilmNumber().intValue());
				d.setTotalPointFilmAmount(Arith.div(b.getTotalPointFilmAmount(), 1000, 2));
				d.setTotalPointQuickNumber(b.getTotalPointQuickNumber().intValue());
				d.setTotalPointQuickAmount(Arith.div(b.getTotalPointQuickAmount(), 1000, 2));
				details.add(d);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return details;
	}
	
	
	@Override
	public List<UserTradeInfo> userTradeInfoList(CommonParam param)
			throws FroadBusinessException, Exception {
		List<UserTradeInfo> infos = new ArrayList<UserTradeInfo>();
		SqlSession rpSqlSession = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportUserTransMapper bankUserTransMapper = rpSqlSession.getMapper(ReportUserTransMapper.class);
			
			ReportBankOrg bankJgm = CommonUtil.setJgmByLevel(param.getClientId(), param.getOrgCode());
			Date begDate = Checker.isEmpty(param.getBegDate()) ? new Date(DateUtils.addDays(new java.util.Date(), DateConfigUtil.QUERY_MAX_DAY).getTime()) : new Date(DateUtils.addDays(param.getBegDate(), 1).getTime());
			Date endDate = Checker.isEmpty(param.getEndDate()) ? new Date(System.currentTimeMillis()) : new Date(DateUtils.addDays(param.getEndDate(), 1).getTime());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportUserTrans> tradeInfos = bankUserTransMapper.selectUserTradeInfoList(begDate, endDate, bankJgm, flag);
			for(ReportUserTrans tran : tradeInfos){
				UserTradeInfo info = new UserTradeInfo();
				info.setUserName(tran.getUserName());
				info.setMobile(tran.getMobile());
				info.setIsVip(BooleanUtils.toString(tran.getIsVip(), "1", "0"));
				info.setOrderCount(tran.getTotalOrderNumber().intValue());
				info.setTotalAmount(Arith.div(tran.getTotalOrderAmount(), 1000, 2));
				info.setProductNumber(tran.getTotalProductNumber().intValue());
				info.setProductAmount(Arith.div(tran.getTotalProductAmount(), 1000, 2));
				info.setRefundsAmount(Arith.div(tran.getTotalRefundsAmount(), 1000, 2));
				infos.add(info);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null){
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	
	private void asyncToMongo(final RedisUtil redis, final String md5Key, 
			final Date begDate, final Date endDate, final ReportBankOrg bankJgm, final Boolean flag, 
			final Integer totalNews, final Map<String, Integer> totalInfo){
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				SqlSession rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
				ReportUserDetailMapper bankUserDetailMapper = rpSqlSession.getMapper(ReportUserDetailMapper.class);
				List<DBObject> mongoList = new ArrayList<DBObject>();
				String collection = "cb_user_summary_" + md5Key;
				String redisKey = RedisUtil.cbbank_user_summary_md5key(md5Key);
				DBObject object = null;
				ReportUserDetail detail = null;
				UserSummaryMongo summaryMongo = null;
				UserSummary summary = null;
				
				List<ReportUserDetail> list = bankUserDetailMapper.selectUserSummaryList(begDate, endDate, bankJgm, flag);
				for(int i = 0; i < list.size(); i++){
					detail = list.get(i);
					summary = new UserSummary();
					
					if(Checker.isNotEmpty(detail.getSorgCode())){
						summary.setOrgCode(detail.getSorgCode());
						summary.setOrgName(detail.getSorgName());
					}else if(Checker.isNotEmpty(detail.getForgCode())){
						summary.setOrgCode(detail.getForgCode());
						summary.setOrgName(detail.getForgName());
					}
					summary.setAddCount(detail.getNewCount().intValue());
					summary.setChangeCount(detail.getChangeCount().intValue());
					summary.setTotalCount(totalInfo.get(detail.getClientId()+detail.getBankCardId()));
					summary.setPercent(ObjectUtils.equals(0l, totalNews) ? 0d : Arith.div(detail.getNewCount(), totalNews, 4));
					summary.setOrderCount(detail.getTotalOrder().intValue());
					summary.setTotalAmount(Arith.div(detail.getTotalAmount(), 1000, 2));
					
					summaryMongo = new UserSummaryMongo();
					summaryMongo.setIndex(i+1);
					summaryMongo.setUserSummary(summary);
					object = (DBObject) JSON.parse(JSonUtil.toJSonString(summaryMongo));
					mongoList.add(object);
				}
				
				if(Checker.isNotEmpty(mongoList)){
					List<List<DBObject>> splitList = CollectionsUtil.splitList(mongoList, CollectionsUtil.MAX_INSERT_SIZE);
					MongoUtil mongo = new MongoUtil();
					mongo.dropCollection(collection);
					for(List<DBObject> dbList : splitList){
						mongo.insert(dbList, collection);
					}
				}
				
				Map<String, String> hash = new HashMap<String, String>();
				hash.put("count", String.valueOf(list.size()));
				hash.put("total_news", String.valueOf(totalNews));
				hash.put("collection", collection);
				redis.hmset(redisKey, hash);
				
				// push redis key to collection hash
				Map<String, String> keyHash = new HashMap<String, String>();
				keyHash.put(redisKey, collection);
				redis.hmset(RedisUtil.MONGO_COLLECTION_KEYS, keyHash);
				
				LogCvt.info("异步生成查询结果到mongo完成");
				
				if(rpSqlSession != null){
					rpSqlSession.close();
				}
			}
		});
	}

}
