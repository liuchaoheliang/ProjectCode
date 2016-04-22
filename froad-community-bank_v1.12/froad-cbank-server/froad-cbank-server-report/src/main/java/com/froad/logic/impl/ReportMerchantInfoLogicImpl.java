package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.froad.db.mysql.rp_mappers.ReportMerchantOutletMapper;
import com.froad.db.mysql.rp_mappers.ReportSignMerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryDetailMapper;
import com.froad.db.mysql.rp_mappers.ReportSignSummaryMapper;
import com.froad.db.mysql.rp_mappers.ReportSignTypeSummaryMapper;
import com.froad.enums.MerchantTypeEnum;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantInfoLogic;
import com.froad.po.CommonParam;
import com.froad.po.MerchantBussinessRes;
import com.froad.po.MerchantDetailRes;
import com.froad.po.MerchantTrend;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantOutletCount;
import com.froad.po.ReportSignMerchant;
import com.froad.po.ReportSignSummary;
import com.froad.po.ReportSignSummaryDetail;
import com.froad.po.ReportSignTypeSummary;
import com.froad.po.TypePercentInfo;
import com.froad.po.mongo.ReportSignMerchantMongo;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.vo.report.ReportMerchantOutletVo;
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

public class ReportMerchantInfoLogicImpl implements ReportMerchantInfoLogic {
	
	@Override
	public List<MerchantTrend> getMerchantTrend(CommonParam param) throws FroadBusinessException, Exception {
		SqlSession rpSqlSession  = null;
		
		List<MerchantTrend> trendsList = new ArrayList<MerchantTrend>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignSummaryDetailMapper reportSignSummaryDetailMapper = rpSqlSession.getMapper(ReportSignSummaryDetailMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			List<ReportSignSummaryDetail> details = reportSignSummaryDetailMapper.selectMerchantIncrCount(user, flag);
			
			ReportSignSummaryDetail detail = null;
			MerchantTrend trend = null;
			
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			for(int i = 0; i < details.size(); i++){
				detail = details.get(i);
				map.put(detail.getWeek(), detail.getNewTotalMerchant().intValue());
			}
			
			Calendar c = Calendar.getInstance();
			int nowWeek = c.get(Calendar.WEEK_OF_YEAR);
			int begWeek = nowWeek - 11;
			if(begWeek >= 0){
				for(int i = 0; i < 12; i++){
					trend = new MerchantTrend();
					trend.setWeek(i+1);
					trend.setAddCount(map.containsKey(begWeek+i) ? map.get(begWeek+i) : 0);
					trendsList.add(trend);
				}
			}else{
				for(int i = 0; i < 12; i++){
					int cweek = (begWeek+i) <= 0 ? 52+(begWeek+i) : (begWeek+i);
					trend = new MerchantTrend();
					trend.setWeek(i+1);
					trend.setAddCount(map.containsKey(cweek) ? map.get(cweek) : 0);
					trendsList.add(trend);
				}
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
		
		return trendsList;
	}

	@Override
	public List<TypePercentInfo> getMerchantTypePercent(CommonParam param) throws FroadBusinessException, Exception {
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		SqlSession rpSqlSession  = null;
		Map<String, TypePercentInfo> infoMap = new HashMap<String, TypePercentInfo>();
		String merchantType = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignTypeSummaryMapper reportSignTypeSummaryMapper = rpSqlSession.getMapper(ReportSignTypeSummaryMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			// 不能查询当天数据
			Date curDate = new Date();
			if (Checker.isNotEmpty(param.getBegDate()) && param.getBegDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setBegDate(TaskTimeUtil.getStartDay(DateUtils.addDays(curDate, -1)));
			}
			if (Checker.isNotEmpty(param.getEndDate()) && param.getEndDate().getTime() >= TaskTimeUtil.getStartDay(curDate).getTime()){
				param.setEndDate(TaskTimeUtil.getEndDay(DateUtils.addDays(curDate, -1)));
			}
			
			Date maxDate = reportSignTypeSummaryMapper.findTypeSummaryMaxDate();
			Date date = Checker.isNotEmpty(param.getEndDate()) ? param.getEndDate() : new Date();
			Integer maxDateStr = Integer.valueOf(DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, maxDate));
			Integer dateStr = Integer.valueOf(DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date));
			Date start = dateStr >= maxDateStr ? TaskTimeUtil.getStartDay(maxDate) : TaskTimeUtil.getStartDay(date, 1);
			Date end = dateStr >= maxDateStr ? TaskTimeUtil.getEndDay(maxDate) : TaskTimeUtil.getEndDay(date, 1);
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportSignTypeSummary> summarys = reportSignTypeSummaryMapper.selectMerchantTypeCount(start, end, user, flag);
			Long summaryTotal = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			// 获取总数
			for(ReportSignTypeSummary s : summarys){
				summaryTotal += s.getTotalMerchants();
				map.put(s.getType(), s.getTotalMerchants());
			}
			
			Set<String> set = map.keySet();
			for(String type : set){
				TypePercentInfo typeInfo = new TypePercentInfo();
				typeInfo.setType(type);
				typeInfo.setTypeName(MerchantTypeEnum.getByType(type).getDesc());
				Long merchants = map.get(type);
				typeInfo.setPercent(ObjectUtils.equals(0l, summaryTotal) ? 0d : Arith.div(merchants.doubleValue(), summaryTotal.doubleValue(), 4));
//				infos.add(typeInfo);
				infoMap.put(type, typeInfo);
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
	public List<TypePercentInfo> getMerchantBussinessPercent(CommonParam param) throws FroadBusinessException, Exception {
		SqlSession rpSqlSession  = null;
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignMerchantMapper reportSignMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			
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
			List<ReportSignMerchant> totals = reportSignMerchantMapper.selectMerchantBussinessAmount(begDate, endDate, user, flag);
			Long totalAmount = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			// 获取总数
			for(ReportSignMerchant m : totals){
				totalAmount += m.getTotalAmount();
				map.put(m.getType(), m.getTotalAmount());
			}
			
			Set<String> set = map.keySet();
			for(String type : set){
				TypePercentInfo typeInfo = new TypePercentInfo();
				typeInfo.setType(type);
				typeInfo.setTypeName(MerchantTypeEnum.getByType(type).getOrderDesc());
				typeInfo.setPercent(ObjectUtils.equals(0l, totalAmount) ? 0d : Arith.div(map.get(type).doubleValue(), totalAmount.doubleValue(), 4));
				infos.add(typeInfo);
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
	public List<MerchantDetailRes> getMerchantDetailList(CommonParam param) throws FroadBusinessException, Exception {
		List<MerchantDetailRes> details = new ArrayList<MerchantDetailRes>();
		SqlSession rpSqlSession = null;
		Set<String> leftSet = null;
		Iterator<String> iterator = null;
		String orgInfo = null;
		MerchantDetailRes detail = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignSummaryMapper signSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Date begDate = Checker.isEmpty(param.getBegDate()) ? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date lbegDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getStartDay(new Date()) : TaskTimeUtil.getStartDay(param.getEndDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			List<ReportSignMerchant> signs = signMerchantMapper.selectMerchantDetailList(begDate, endDate, user, flag);
			if(Checker.isEmpty(signs)){
				return details;
			}
			
			Integer totalMerchants = signSummaryMapper.selectTotalMerchantsByOrg(lbegDate, endDate, user, flag);
			if(Checker.isEmpty(totalMerchants)){
				return details;
			}
			
			List<ReportSignSummary> summarys = signSummaryMapper.getTotalMerchantsByOrg(lbegDate, endDate, user, flag);
			Map<String, Integer> summaryMap = new HashMap<String, Integer>();
			for(ReportSignSummary s : summarys){
				if(Checker.isNotEmpty(s.getLorgCode())){
					summaryMap.put(s.getLorgCode()+"_"+s.getLorgName(), s.getTotalMerchants().intValue());
				}else if(Checker.isNotEmpty(s.getTorgCode())){
					summaryMap.put(s.getTorgCode()+"_"+s.getTorgName(), s.getTotalMerchants().intValue());
				}else if(Checker.isNotEmpty(s.getSorgCode())){
					summaryMap.put(s.getSorgCode()+"_"+s.getSorgName(), s.getTotalMerchants().intValue());
				}else if(Checker.isNotEmpty(s.getForgCode())){
					summaryMap.put(s.getForgCode()+"_"+s.getForgName(), s.getTotalMerchants().intValue());
				}
			}
			
			leftSet = new HashSet<String>();
			leftSet.addAll(summaryMap.keySet());
			// 去掉已统计的新增或动帐
			for (int j = 0; j < signs.size(); j++) {
				ReportSignMerchant sign = signs.get(j);
				if(Checker.isNotEmpty(sign.getLorgCode())){
					leftSet.remove(sign.getLorgCode()+"_"+sign.getLorgName());
				}else if(Checker.isNotEmpty(sign.getTorgCode())){
					leftSet.remove(sign.getTorgCode()+"_"+sign.getTorgName());
				}else if(Checker.isNotEmpty(sign.getSorgCode())){
					leftSet.remove(sign.getSorgCode()+"_"+sign.getSorgName());
				}else if(Checker.isNotEmpty(sign.getForgCode())){
					leftSet.remove(sign.getForgCode()+"_"+sign.getForgName());
				}
			}
			
			for(ReportSignMerchant sign : signs){
				detail = new MerchantDetailRes();
				Integer total = 0;
				detail.setClientId(sign.getClientId());
				// 根据机构号等级设置
				if(Checker.isNotEmpty(sign.getLorgCode())){
					detail.setOrgCode(sign.getLorgCode());
					detail.setOrgName(sign.getLorgName());
					total = summaryMap.get(sign.getLorgCode()+"_"+sign.getLorgName());
				}else if(Checker.isNotEmpty(sign.getTorgCode())){
					detail.setOrgCode(sign.getTorgCode());
					detail.setOrgName(sign.getTorgName());
					total = summaryMap.get(sign.getTorgCode()+"_"+sign.getTorgName());
				}else if(Checker.isNotEmpty(sign.getSorgCode())){
					detail.setOrgCode(sign.getSorgCode());
					detail.setOrgName(sign.getSorgName());
					total = summaryMap.get(sign.getSorgCode()+"_"+sign.getSorgName());
				}else if(Checker.isNotEmpty(sign.getForgCode())){
					detail.setOrgCode(sign.getForgCode());
					detail.setOrgName(sign.getForgName());
					total = summaryMap.get(sign.getForgCode()+"_"+sign.getForgName());
				}
				
				detail.setChangeCount(sign.getChangeCount());
				detail.setAddCount(sign.getNewCount());
				detail.setTotalCount(total);
				detail.setPercent(ObjectUtils.equals(0, totalMerchants) ? 0d : Arith.div(total, totalMerchants, 4));
				detail.setOrderCount(sign.getTotalOrders().intValue());
				// 金额/1000
				detail.setTotalAmount(Arith.div(sign.getTotalAmount(), 1000, 2));
				details.add(detail);
			}
			
			iterator = leftSet.iterator();
			while (iterator.hasNext()) {
				orgInfo = iterator.next();
				if (summaryMap.get(orgInfo) > 0) {
					detail = new MerchantDetailRes();
					String[] orgInfos = orgInfo.split("_");
					detail.setClientId(param.getClientId());
					detail.setOrgCode(orgInfos[0]);
					detail.setOrgName(orgInfos[1]);
					detail.setChangeCount(0);
					detail.setAddCount(0);
					detail.setTotalCount(summaryMap.get(orgInfo));
					detail.setPercent(ObjectUtils.equals(0, totalMerchants) ? 0d : Arith.div(summaryMap.get(orgInfo), totalMerchants, 4));
					detail.setOrderCount(0);
					detail.setTotalAmount(0d);
					details.add(detail);
				}
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
		return details;
	}
	

	@Override
	public List<MerchantBussinessRes> getMerchantBussinessList(CommonParam param) throws FroadBusinessException, Exception {
		SqlSession rpSqlSession = null;
		List<MerchantBussinessRes> vos = new ArrayList<MerchantBussinessRes>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignTypeSummaryMapper signTypeSummaryMapper = rpSqlSession.getMapper(ReportSignTypeSummaryMapper.class);
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			Date begDate = Checker.isEmpty(param.getBegDate()) ? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date lbegDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), -1)) : TaskTimeUtil.getStartDay(param.getEndDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			List<ReportSignTypeSummary> merchantCounts = signTypeSummaryMapper.selectMerchantCount(lbegDate, endDate, user, flag);
			
			
			List<ReportSignMerchant> nCcounts = signMerchantMapper.selectNewCancelMerchants(begDate, endDate, user, flag);
			Map<String, ReportSignMerchant> nCMap = new HashMap<String, ReportSignMerchant>();
			for(ReportSignMerchant u : nCcounts){
				if(Checker.isNotEmpty(u.getLorgCode())){
					nCMap.put(u.getLorgCode(), u);
				}
				if(Checker.isNotEmpty(u.getTorgCode())){
					nCMap.put(u.getTorgCode(), u);
				}
				if(Checker.isNotEmpty(u.getSorgCode())){
					nCMap.put(u.getSorgCode(), u);
				}
				if(Checker.isNotEmpty(u.getForgCode())){
					nCMap.put(u.getForgCode(), u);
				}
			}
			
			// 循环设置返回结果
			for(ReportSignTypeSummary s : merchantCounts){
				MerchantBussinessRes res = new MerchantBussinessRes();
				res.setClientId(s.getClientId());
				
				ReportSignMerchant nc = null;
				// 根据机构号等级设置
				if(Checker.isNotEmpty(s.getLorgCode())){
					res.setOrgCode(s.getLorgCode());
					res.setOrgName(s.getLorgName());
					nc = nCMap.get(s.getLorgCode());
				}else if(Checker.isNotEmpty(s.getTorgCode())){
					res.setOrgCode(s.getTorgCode());
					res.setOrgName(s.getTorgName());
					nc = nCMap.get(s.getTorgCode());
				}else if(Checker.isNotEmpty(s.getSorgCode())){
					res.setOrgCode(s.getSorgCode());
					res.setOrgName(s.getSorgName());
					nc = nCMap.get(s.getSorgCode());
				}else if(Checker.isNotEmpty(s.getForgCode())){
					res.setOrgCode(s.getForgCode());
					res.setOrgName(s.getForgName());
					nc = nCMap.get(s.getForgCode());
				}
				res.setFaceTotalCount(s.getFaceTotalMerchants());
				res.setGroupTotalCount(s.getGroupTotalMerchants());
				res.setSpecialTotalCount(s.getSpecialTotalMerchants());
				
				res.setFaceAddCount(Checker.isEmpty(nc) ? 0 : nc.getFaceNewCount());
				res.setFaceCancelCount(Checker.isEmpty(nc) ? 0 : nc.getFaceCancelCount());
				res.setGroupAddCount(Checker.isEmpty(nc) ? 0 : nc.getGroupNewCount());
				res.setGroupCancelCount(Checker.isEmpty(nc) ? 0 : nc.getGroupCancelCount());
				res.setSpecialAddCount(Checker.isEmpty(nc) ? 0 : nc.getSpecialNewCount());
				res.setSpecialCancelCount(Checker.isEmpty(nc) ? 0 : nc.getSpecialCancelCount());
				vos.add(res);
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
		return vos;
	}

	@Override
	public Page<MerchantDetailRes> getMerchantDetailListByPage(
			Page<MerchantDetailRes> page, CommonParam param)
			throws FroadBusinessException, Exception {
		List<MerchantDetailRes> details = null;
		SqlSession rpSqlSession = null;
		MerchantDetailRes detail = null;
		RedisUtil redis = null;
		Map<String, String> resultMap = null;
		Integer totalMerchants = 0;
		MongoUtil mongo = null;
		String collection = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportSignSummaryMapper signSummaryMapper = rpSqlSession.getMapper(ReportSignSummaryMapper.class);
			ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			redis = new RedisUtil();
			mongo = new MongoUtil();
			
			Date begDate = Checker.isEmpty(param.getBegDate()) ? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date lbegDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getStartDay(new Date()) : TaskTimeUtil.getStartDay(param.getEndDate(), 1);
			Date endDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			Boolean flag = Checker.isNotEmpty(param.getFlag()) ? param.getFlag() : false;
			
			StringBuilder strb = new StringBuilder();
			strb.append(begDate).append(endDate).append(param.getClientId()).append(param.getOrgCode()).append(flag);
			
			String md5key = MD5Util.getMD5Str(strb.toString());
			String redisKey = RedisUtil.cbbank_merchant_info_md5key(md5key);
			resultMap = redis.hgetAll(redisKey);
			
			if(Checker.isEmpty(resultMap)){
				
//				signs = signMerchantMapper.selectMerchantDetailListByPage(begDate, endDate, user, page, flag);
				
				totalMerchants = signSummaryMapper.selectTotalMerchantsByOrg(lbegDate, endDate, user, flag);
				if(Checker.isEmpty(totalMerchants)){
					page.setResultsContent(new ArrayList<MerchantDetailRes>());
					return page;
				}
				
				List<ReportSignSummary> summarys = signSummaryMapper.getTotalMerchantsByOrg(lbegDate, endDate, user, flag);
				Map<String, Integer> summaryMap = new HashMap<String, Integer>();
				for(ReportSignSummary s : summarys){
					if(Checker.isNotEmpty(s.getLorgCode())){
						summaryMap.put(s.getLorgCode()+"_"+s.getLorgName(), s.getTotalMerchants().intValue());
					}else if(Checker.isNotEmpty(s.getTorgCode())){
						summaryMap.put(s.getTorgCode()+"_"+s.getTorgName(), s.getTotalMerchants().intValue());
					}else if(Checker.isNotEmpty(s.getSorgCode())){
						summaryMap.put(s.getSorgCode()+"_"+s.getSorgName(), s.getTotalMerchants().intValue());
					}else if(Checker.isNotEmpty(s.getForgCode())){
						summaryMap.put(s.getForgCode()+"_"+s.getForgName(), s.getTotalMerchants().intValue());
					}
				}
				
				asyncToMongo(param.getClientId(), md5key, totalMerchants, begDate, endDate, user, flag, redis, summaryMap);
				
				resultMap = redis.hgetAll(redisKey);
				while (Checker.isEmpty(resultMap)) {
					Thread.sleep(100);
					resultMap = redis.hgetAll(redisKey);
				}
				
				List<ReportSignMerchantMongo> signMongos = null;
				collection = resultMap.get("mongo_collection");
				DBObject query = new BasicDBObject();
				query.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				signMongos = mongo.findByLimit(query, collection, page.getPageSize(), ReportSignMerchantMongo.class);
				
				details = new ArrayList<MerchantDetailRes>();
				for(ReportSignMerchantMongo sign : signMongos){
					detail = new MerchantDetailRes();
					detail.setClientId(sign.getSignMerchant().getClientId());
					// 根据机构号等级设置
					if(Checker.isNotEmpty(sign.getSignMerchant().getLorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getLorgCode());
						detail.setOrgName(sign.getSignMerchant().getLorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getTorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getTorgCode());
						detail.setOrgName(sign.getSignMerchant().getTorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getSorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getSorgCode());
						detail.setOrgName(sign.getSignMerchant().getSorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getForgCode())){
						detail.setOrgCode(sign.getSignMerchant().getForgCode());
						detail.setOrgName(sign.getSignMerchant().getForgName());
					}
					
					detail.setChangeCount(sign.getSignMerchant().getChangeCount());
					detail.setAddCount(sign.getSignMerchant().getNewCount());
					detail.setTotalCount(sign.getSignMerchant().getTotalMerchants());
					detail.setPercent(ObjectUtils.equals(0, totalMerchants) ? 0d : Arith.div(sign.getSignMerchant().getTotalMerchants(), totalMerchants, 4));
					detail.setOrderCount(sign.getSignMerchant().getTotalOrders().intValue());
					// 金额/1000
					detail.setTotalAmount(Arith.div(sign.getSignMerchant().getTotalAmount(), 1000, 2));
					details.add(detail);
				}
				
				page.setTotalCount(Integer.valueOf(resultMap.get("total_size")));
				
			}else{
				List<ReportSignMerchantMongo> signMongos = null;
				collection = resultMap.get("mongo_collection");
				totalMerchants = Integer.valueOf(resultMap.get("total_merchants"));
				
				DBObject query = new BasicDBObject();
				query.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
//				mongo.dropCollection(collection);//删除旧数据，以防存在同名集合
				signMongos = mongo.findByLimit(query, collection, page.getPageSize(), ReportSignMerchantMongo.class);
				
				details = new ArrayList<MerchantDetailRes>();
				for(ReportSignMerchantMongo sign : signMongos){
					detail = new MerchantDetailRes();
					detail.setClientId(sign.getSignMerchant().getClientId());
					// 根据机构号等级设置
					if(Checker.isNotEmpty(sign.getSignMerchant().getLorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getLorgCode());
						detail.setOrgName(sign.getSignMerchant().getLorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getTorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getTorgCode());
						detail.setOrgName(sign.getSignMerchant().getTorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getSorgCode())){
						detail.setOrgCode(sign.getSignMerchant().getSorgCode());
						detail.setOrgName(sign.getSignMerchant().getSorgName());
					}else if(Checker.isNotEmpty(sign.getSignMerchant().getForgCode())){
						detail.setOrgCode(sign.getSignMerchant().getForgCode());
						detail.setOrgName(sign.getSignMerchant().getForgName());
					}
					
					detail.setChangeCount(sign.getSignMerchant().getChangeCount());
					detail.setAddCount(sign.getSignMerchant().getNewCount());
					detail.setTotalCount(sign.getSignMerchant().getTotalMerchants());
					detail.setPercent(ObjectUtils.equals(0, totalMerchants) ? 0d : Arith.div(sign.getSignMerchant().getTotalMerchants(), totalMerchants, 4));
					detail.setOrderCount(sign.getSignMerchant().getTotalOrders().intValue());
					// 金额/1000
					detail.setTotalAmount(Arith.div(sign.getSignMerchant().getTotalAmount(), 1000, 2));
					details.add(detail);
				}
				
				page.setTotalCount(Integer.valueOf(resultMap.get("total_size")));
			}
			
			page.setResultsContent(details);
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if(rpSqlSession != null) {
				rpSqlSession.close();
			}
		}
		return page;
	}

	
	private void asyncToMongo(final String clientId, final String md5key, final Integer totalMerchants, 
			final Date begDate, final Date endDate, 
			final ReportBankUser user,final Boolean flag, 
			final RedisUtil redis, final Map<String, Integer> summaryMap){
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				SqlSession rpSqlSession = null;
				String collection = null;
				List<DBObject> list = null;
				ReportSignMerchantMongo signMongo = null;
				ReportSignMerchant sign = null;
				Set<String> leftSet = null;
				Iterator<String> iterator = null;
				String orgInfo = null;
				int index = 0;
				
				rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
				ReportSignMerchantMapper signMerchantMapper = rpSqlSession.getMapper(ReportSignMerchantMapper.class);
				
				List<ReportSignMerchant> signs = signMerchantMapper.selectMerchantDetailList(begDate, endDate, user, flag);
				
				leftSet = new HashSet<String>();
				leftSet.addAll(summaryMap.keySet());
				// 去掉已统计的新增或动帐
				for (int j = 0; j < signs.size(); j++) {
					sign = signs.get(j);
					if(Checker.isNotEmpty(sign.getLorgCode())){
						leftSet.remove(sign.getLorgCode()+"_"+sign.getLorgName());
					}else if(Checker.isNotEmpty(sign.getTorgCode())){
						leftSet.remove(sign.getTorgCode()+"_"+sign.getTorgName());
					}else if(Checker.isNotEmpty(sign.getSorgCode())){
						leftSet.remove(sign.getSorgCode()+"_"+sign.getSorgName());
					}else if(Checker.isNotEmpty(sign.getForgCode())){
						leftSet.remove(sign.getForgCode()+"_"+sign.getForgName());
					}
				}
				
				collection = "cb_merchant_info_"+md5key;
				
				list = new ArrayList<DBObject>();
				for(int i=0; i<signs.size(); i++){
					signMongo = new ReportSignMerchantMongo();
					index = i + 1;
					signMongo.setIndex(index);
					sign = signs.get(i);
					
					Integer total = 0;
					if(Checker.isNotEmpty(sign.getLorgCode())){
						total = summaryMap.get(sign.getLorgCode()+"_"+sign.getLorgName());
					}else if(Checker.isNotEmpty(sign.getTorgCode())){
						total = summaryMap.get(sign.getTorgCode()+"_"+sign.getTorgName());
					}else if(Checker.isNotEmpty(sign.getSorgCode())){
						total = summaryMap.get(sign.getSorgCode()+"_"+sign.getSorgName());
					}else if(Checker.isNotEmpty(sign.getForgCode())){
						total = summaryMap.get(sign.getForgCode()+"_"+sign.getForgName());
					}
					sign.setTotalMerchants(total);
					signMongo.setSignMerchant(sign);
					DBObject obj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(signMongo));
					list.add(obj);
				}
				
				iterator = leftSet.iterator();
				while (iterator.hasNext()) {
					orgInfo = iterator.next();
					if (summaryMap.get(orgInfo) > 0) {
						signMongo = new ReportSignMerchantMongo();
						String[] orgInfos = orgInfo.split("_");
						index += 1;
						signMongo.setIndex(index);
						
						sign = new ReportSignMerchant();
						sign.setClientId(clientId);
						sign.setLorgCode(orgInfos[0]);
						sign.setLorgName(orgInfos[1]);
						sign.setChangeCount(0);
						sign.setNewCount(0);
						sign.setTotalMerchants(summaryMap.get(orgInfo));
						sign.setTotalOrders(0l);
						sign.setTotalAmount(0l);
						signMongo.setSignMerchant(sign);
						DBObject obj = (BasicDBObject) JSON.parse(JSonUtil.toJSonString(signMongo));
						list.add(obj);
					}
				}
				
				if(Checker.isEmpty(list)){
					return;
				}
				
				MongoUtil mongo = new MongoUtil();
				mongo.dropCollection(collection);
				List<List<DBObject>> splitList = CollectionsUtil.splitList(list, 1000);
				for (int i = 0; i < splitList.size(); i++){
					mongo.insert(splitList.get(i), collection);
					LogCvt.info(new StringBuffer(i+1).append("批次已生成到mongo").toString());
				}
				
				Map<String, String> hash = new HashMap<String, String>();
				hash.put("total_size", String.valueOf(list.size()));
				hash.put("total_merchants", String.valueOf(totalMerchants));
				hash.put("mongo_collection", collection);
				redis.hmset(RedisUtil.cbbank_merchant_info_md5key(md5key), hash);
				
				// push redis key to collection hash
				Map<String, String> keyHash = new HashMap<String, String>();
				keyHash = new HashMap<String, String>();
				keyHash.put(RedisUtil.cbbank_merchant_info_md5key(md5key), collection);
				redis.hmset(RedisUtil.MONGO_COLLECTION_KEYS, keyHash);
				
				if(rpSqlSession != null){
					rpSqlSession.close();
				}
			}
		});
	}

	@Override
	public List<ReportMerchantOutletVo> getMerchantOutletList(CommonParam param)
			throws FroadBusinessException, Exception {
		List<ReportMerchantOutletVo> voList = null;
		ReportMerchantOutletVo outletVo = null;
		SqlSession rpSqlSession = null;
		ReportMerchantOutletMapper mapper = null;
		ReportBankUser user = null;
		String orgCode = null;
		List<ReportMerchantOutletCount> outletCountList = null;
		ReportMerchantOutletCount reportOutletCount = null;
		
		voList = new ArrayList<ReportMerchantOutletVo>();
		rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
		mapper = rpSqlSession.getMapper(ReportMerchantOutletMapper.class);
		
		if (param.getFlag() == true){
			orgCode = param.getOrgCode();
		}
		
		user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
		
//		Date begDate = Checker.isEmpty(param.getBegDate()) ? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
		Date endDate = Checker.isEmpty(param.getEndDate()) ? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
		
		outletCountList = mapper.findMerchantOutletCount(param.getClientId(),
				user.getForgCode(), user.getSorgCode(), orgCode,
				null, endDate);
		if (Checker.isNotEmpty(outletCountList)){
			for (int i = 0; i < outletCountList.size(); i++){
				reportOutletCount = outletCountList.get(i);
				outletVo = new ReportMerchantOutletVo();
				outletVo.setOrgName(reportOutletCount.getOrgName());
				outletVo.setOutletName(reportOutletCount.getOutletName());
				outletVo.setOutletAddress(reportOutletCount.getOutletAddress());
				outletVo.setMerchantName(reportOutletCount.getMerchantName());
				outletVo.setMerchantOutletCount(reportOutletCount
						.getMerchantOutletCount() <= 0 ? 0 : reportOutletCount
						.getMerchantOutletCount());
				if (outletVo.getMerchantOutletCount() == 0){
					continue;//不展示门店数量为0的商户
				}
				voList.add(outletVo);
			}
		}
		
		return voList;
	}
	
}
