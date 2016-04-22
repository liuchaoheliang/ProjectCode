package com.froad.logic.impl;

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
import com.froad.db.mysql.rp_mappers.ReportMerchantSaleMapper;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ReportOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantProductLogic;
import com.froad.po.BusinessSaleDetail;
import com.froad.po.CommonParam;
import com.froad.po.MerchantSaleDetail;
import com.froad.po.PayTypeCount;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantSale;
import com.froad.po.SaleCountDetail;
import com.froad.po.SaleTrend;
import com.froad.po.SaleTypeCount;
import com.froad.po.TypePercentInfo;
import com.froad.po.mongo.SaleCountDetailMongo;
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

/**
 * 社区银行业务销售统计
 * @ClassName: ReportMerchantProductLogicImpl 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月5日 下午10:21:25
 */
public class ReportMerchantProductLogicImpl implements ReportMerchantProductLogic {
	
	/**
	 * 销售走势图
	* @Title: getSaleTrend 
	* @Description: 
	* @author longyunbo
	* @param @return
	* @return 
	* @throws
	 */
	public List<SaleTrend> getSaleTrend(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<SaleTrend> saleTrendList = new ArrayList<SaleTrend>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
			ReportBankUser user = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<ReportMerchantSale> reportMerchantSaleList = reportMerchantSaleMapper.findSaleTrend(user, orgCode);
			
			Calendar c = Calendar.getInstance();
			int nowWeek = c.get(Calendar.WEEK_OF_YEAR);
			int begWeek = nowWeek - 11;
			SaleTrend saleTrend = null;
			ReportMerchantSale sale = null;
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			for(int i=0; i<reportMerchantSaleList.size(); i++){
				sale = reportMerchantSaleList.get(i);
				map.put(sale.getWeek(), Arith.div(sale.getSaleProductAmount(), 1000, 2));
			}
			
			if(begWeek >= 0){
				for(int i = 0; i < 12; i++){
					saleTrend = new SaleTrend();
					saleTrend.setWeek(i+1);
					saleTrend.setSaleProductAmount(map.containsKey(begWeek+i) ? map.get(begWeek+i) : 0d);
					saleTrendList.add(saleTrend);
				}
			}else{
				for(int i = 0; i < 12; i++){
					int cweek = (begWeek+i) <= 0 ? 52+(begWeek+i) : (begWeek+i);
					saleTrend = new SaleTrend();
					saleTrend.setWeek(i+1);
					saleTrend.setSaleProductAmount(map.containsKey(cweek) ? map.get(cweek) : 0d);
					saleTrendList.add(saleTrend);
				}
			}
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计销售走势图获取失败，原因:" + e.getMessage(),e);
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
	 * 业务销售类型占比
	* @Title: getSaleTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return 
	* @throws
	 */
	public List<TypePercentInfo> getSaleTypePercent(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
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
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			
			List<SaleTypeCount> saleTypeCountList = reportMerchantSaleMapper.findSaleTypeCount(begDate, endDate, bu, orgCode);

			Long total = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			for(SaleTypeCount s : saleTypeCountList){
				total += s.getTotalAmount();
				map.put(s.getType(), s.getTotalAmount());
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
			
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计业务销售类型占比失败，原因:" + e.getMessage(),e);
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
	 * 支付方式占比
	* @Title: getPayTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return 
	* @throws
	 */
	public List<TypePercentInfo> getPayTypePercent(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<TypePercentInfo> infos = new ArrayList<TypePercentInfo>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
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
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<PayTypeCount> payTypeCountList = reportMerchantSaleMapper.findPayTypeCount(start, end, bu, orgCode);
			Long total = 0l;
			Map<String, Long> map = new HashMap<String, Long>();
			for(PayTypeCount s : payTypeCountList){
				total += s.getTotalOrder();
				map.put(s.getPaymentMethod(), s.getTotalOrder());
			}
			
			Set<String> set = map.keySet();
			for(String type : set){
				TypePercentInfo info = new TypePercentInfo();
				info.setType(type);
				info.setTypeName(PaymentMethod.getByCode(type).getDescribe());
				Long totals = map.get(type);
				info.setPercent(ObjectUtils.equals(0l, total) ? 0d : Arith.div(totals, total, 4));
				infos.add(info);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计支付方式占比失败，原因:" + e.getMessage(),e);
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
	 * 业务销售统计详情列表
	* @Title: getSaleCountDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return 
	* @throws
	 */
	public List<SaleCountDetail> getSaleCountDetail(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<SaleCountDetail> infos = new ArrayList<SaleCountDetail>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<SaleCountDetail> saleCountDetailList = reportMerchantSaleMapper.findSaleCountDetail(start, end, bu, orgCode);
			
//			ReportBankUser rb = reportBankUserMapper.selectIsExist(bu);
//			List<ReportBankUser> finds = reportBankUserMapper.selectMerchantDetailList(start, start, bu);
			
			for(SaleCountDetail sd : saleCountDetailList){
				SaleCountDetail saleCountDetail = new SaleCountDetail();
				saleCountDetail.setBuyCount(sd.getBuyCount());
				saleCountDetail.setOrderCount(sd.getOrderCount());
				saleCountDetail.setProductSaleAmount(Arith.div(sd.getProductSaleAmount(), 1000, 2));
				saleCountDetail.setProductSaleCount(sd.getProductSaleCount());
				saleCountDetail.setTotalAmount(Arith.div(sd.getTotalAmount(), 1000, 2));
				saleCountDetail.setAverAmount(Arith.div(ObjectUtils.equals(0, sd.getBuyCount()) ? 0d :Arith.div(sd.getTotalAmount(), sd.getBuyCount()), 1000, 2));
				
				if(Checker.isNotEmpty(sd.getLorgCode())){
					saleCountDetail.setOrgCode(sd.getLorgCode());
					saleCountDetail.setOrgName(sd.getLorgName());
				}else if(Checker.isNotEmpty(sd.getTorgCode())){
					saleCountDetail.setOrgCode(sd.getTorgCode());
					saleCountDetail.setOrgName(sd.getTorgName());
				}else if(Checker.isNotEmpty(sd.getSorgCode())){
					saleCountDetail.setOrgCode(sd.getSorgCode());
					saleCountDetail.setOrgName(sd.getSorgName());
				}else if(Checker.isNotEmpty(sd.getForgCode())){
					saleCountDetail.setOrgCode(sd.getForgCode());
					saleCountDetail.setOrgName(sd.getForgName());
				}
				
				infos.add(saleCountDetail);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计业务销售统计详情列表失败，原因:" + e.getMessage(),e);
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
	 * 业务销售统计详情列表(分页)
	* @Title: getSaleCountDetailListByPage 
	* @Description: 
	* @author longyunbo
	* @param @param page
	* @param @param param
	* @param @return
	* @return 
	* @throws
	 */
	public Page<SaleCountDetail> getSaleCountDetailListByPage(Page<SaleCountDetail> page, CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<SaleCountDetail> infos = new ArrayList<SaleCountDetail>();
		RedisUtil redis = null;
		MongoUtil mongo = null;
		StringBuffer keyStr = null;
		String md5Key = null;
		String redisKey = null;
		Map<String, String> resultMap = null;
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			
			redis = new RedisUtil();
			mongo = new MongoUtil();
			keyStr = new StringBuffer();
			
			keyStr.append(start).append(end).append(param.getClientId()).append(param.getOrgCode()).append(param.getFlag());
			md5Key = MD5Util.getMD5Str(keyStr.toString());
			redisKey = RedisUtil.cbbank_merchant_sale_md5key(md5Key);
			resultMap = redis.hgetAll(redisKey);
			
			if(Checker.isEmpty(resultMap)){
				List<SaleCountDetail> saleCountDetailList = reportMerchantSaleMapper.findSaleCountDetailListByPage(start, end, bu, page, orgCode);
				for(SaleCountDetail sd : saleCountDetailList){
					SaleCountDetail saleCountDetail = new SaleCountDetail();
					saleCountDetail.setBuyCount(sd.getBuyCount());
					saleCountDetail.setOrderCount(sd.getOrderCount());
					saleCountDetail.setProductSaleAmount(Arith.div(sd.getProductSaleAmount(), 1000, 2));
					saleCountDetail.setProductSaleCount(sd.getProductSaleCount());
					saleCountDetail.setTotalAmount(Arith.div(sd.getTotalAmount(), 1000, 2));
					saleCountDetail.setAverAmount(Arith.div(ObjectUtils.equals(0, sd.getBuyCount()) ? 0d :Arith.div(sd.getTotalAmount(), sd.getBuyCount()), 1000, 2));
					
					if(Checker.isNotEmpty(sd.getLorgCode())){
						saleCountDetail.setOrgCode(sd.getLorgCode());
						saleCountDetail.setOrgName(sd.getLorgName());
					}else if(Checker.isNotEmpty(sd.getTorgCode())){
						saleCountDetail.setOrgCode(sd.getTorgCode());
						saleCountDetail.setOrgName(sd.getTorgName());
					}else if(Checker.isNotEmpty(sd.getSorgCode())){
						saleCountDetail.setOrgCode(sd.getSorgCode());
						saleCountDetail.setOrgName(sd.getSorgName());
					}else if(Checker.isNotEmpty(sd.getForgCode())){
						saleCountDetail.setOrgCode(sd.getForgCode());
						saleCountDetail.setOrgName(sd.getForgName());
					}
					
					infos.add(saleCountDetail);
				}
				
				asyncToMongo(redis, md5Key, start, end, bu, orgCode);
			}else{
				page.setTotalCount(Integer.parseInt(resultMap.get("count")));
				String collection = resultMap.get("collection");
				
				DBObject query = new BasicDBObject();
				query.put("index", new BasicDBObject(QueryOperators.GT, (page.getPageNumber() - 1) * page.getPageSize()));
				List<SaleCountDetailMongo> mongos = mongo.findByLimit(query, collection, page.getPageSize(), SaleCountDetailMongo.class);
				
				for(SaleCountDetailMongo sd : mongos){
					infos.add(sd.getSaleCountDetail());
				}
			}
			
			page.setResultsContent(infos);
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计业务销售统计详情列表分页失败，原因:" + e.getMessage(),e);
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
	 * 商户业务销售详情
	* @Title: getMerchantSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return 
	* @throws
	 */
	public List<MerchantSaleDetail> getMerchantSaleDetail(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<MerchantSaleDetail> infos = new ArrayList<MerchantSaleDetail>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<MerchantSaleDetail> merchantSaleDetailList = reportMerchantSaleMapper.findMerchantSaleDetail(start, end, bu, orgCode);
			String merchantTypes = null;
			for(MerchantSaleDetail msd : merchantSaleDetailList){
				MerchantSaleDetail merchantSaleDetail = new MerchantSaleDetail();
				merchantSaleDetail.setMerchantId(msd.getMerchantId());
				merchantSaleDetail.setMerchantName(msd.getMerchantName());
				merchantTypes = msd.getMerchantType();
				merchantSaleDetail.setMerchantType(merchantTypes);
				merchantSaleDetail.setGroupOrderCount(msd.getGroupOrderCount());
				merchantSaleDetail.setFaceOrderCount(msd.getFaceOrderCount());
				merchantSaleDetail.setSpecialOrderCount(msd.getSpecialOrderCount());
				merchantSaleDetail.setPresellOrderCount(msd.getPresellOrderCount());
				merchantSaleDetail.setOrderCount(msd.getOrderCount());
				merchantSaleDetail.setOrderAmount(Arith.div(msd.getOrderAmount(), 1000, 2));
				merchantSaleDetail.setRefundAmount(Arith.div(msd.getRefundAmount(), 1000, 2));
				
				if(Checker.isNotEmpty(msd.getLorgCode())){
					merchantSaleDetail.setOrgCode(msd.getLorgCode());
					merchantSaleDetail.setOrgName(msd.getLorgName());
				}else if(Checker.isNotEmpty(msd.getTorgCode())){
					merchantSaleDetail.setOrgCode(msd.getTorgCode());
					merchantSaleDetail.setOrgName(msd.getTorgName());
				}else if(Checker.isNotEmpty(msd.getSorgCode())){
					merchantSaleDetail.setOrgCode(msd.getSorgCode());
					merchantSaleDetail.setOrgName(msd.getSorgName());
				}else if(Checker.isNotEmpty(msd.getForgCode())){
					merchantSaleDetail.setOrgCode(msd.getForgCode());
					merchantSaleDetail.setOrgName(msd.getForgName());
				}
				infos.add(merchantSaleDetail);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计商户业务销售详情失败，原因:" + e.getMessage(),e);
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
	 * 业务类型销售统计详情列表
	* @Title: getBusinessSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<BusinessSaleDetail>
	* @throws
	 */
	public List<BusinessSaleDetail> getBusinessSaleDetail(CommonParam param) throws FroadBusinessException, Exception{
		SqlSession rpSqlSession  = null;
		List<BusinessSaleDetail> infos = new ArrayList<BusinessSaleDetail>();
		try {
			rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
			ReportMerchantSaleMapper reportMerchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
			
			Date start = Checker.isEmpty(param.getBegDate())? TaskTimeUtil.getStartDay(DateUtils.addDays(new Date(), DateConfigUtil.QUERY_MAX_DAY)) : TaskTimeUtil.getStartDay(param.getBegDate(), 1);
			Date end = Checker.isEmpty(param.getEndDate())? TaskTimeUtil.getEndDay(new Date()) : TaskTimeUtil.getEndDay(param.getEndDate(), 1);
			
			ReportBankUser bu = CommonUtil.setOrgCodeByLevel(param.getClientId(), param.getOrgCode());
			String orgCode = null;
			if (param.getFlag() != null && param.getFlag()){
				orgCode = param.getOrgCode();
			}
			List<BusinessSaleDetail> businessSaleDetailList = reportMerchantSaleMapper.findBusinessSaleDetail(start, end, bu, orgCode);
			for(BusinessSaleDetail bs : businessSaleDetailList){
				BusinessSaleDetail businessSaleDetail = new BusinessSaleDetail();
				businessSaleDetail.setType(ReportOrderType.getByType(bs.getType()).getOrderDesc());
//				businessSaleDetail.setOrgCode(orgCode);
//				businessSaleDetail.setOrgName(orgName);
				businessSaleDetail.setOrderCount(bs.getOrderCount());
				businessSaleDetail.setOrderAmount(Arith.div(bs.getOrderAmount(), 1000, 2));
				businessSaleDetail.setCashAmount(Arith.div(bs.getCashAmount(), 1000, 2));
				businessSaleDetail.setBankPointAmount(Arith.div(bs.getBankPointAmount(), 1000, 2));
				businessSaleDetail.setFftPointAmount(Arith.div(bs.getFftPointAmount(), 1000, 2));
				businessSaleDetail.setProductCount(bs.getProductCount());
				businessSaleDetail.setProductAmount(Arith.div(bs.getProductAmount(), 1000, 2));
				businessSaleDetail.setBuyCount(bs.getBuyCount());
				
				if(Checker.isNotEmpty(bs.getLorgCode())){
					businessSaleDetail.setOrgCode(bs.getLorgCode());
					businessSaleDetail.setOrgName(bs.getLorgName());
				}else if(Checker.isNotEmpty(bs.getTorgCode())){
					businessSaleDetail.setOrgCode(bs.getTorgCode());
					businessSaleDetail.setOrgName(bs.getTorgName());
				}else if(Checker.isNotEmpty(bs.getSorgCode())){
					businessSaleDetail.setOrgCode(bs.getSorgCode());
					businessSaleDetail.setOrgName(bs.getSorgName());
				}else if(Checker.isNotEmpty(bs.getForgCode())){
					businessSaleDetail.setOrgCode(bs.getForgCode());
					businessSaleDetail.setOrgName(bs.getForgName());
				}
				infos.add(businessSaleDetail);
			}
		} catch (FroadBusinessException e) {
			throw e;
		} catch (Exception e) {
			LogCvt.error("统计业务类型销售统计详情列表失败，原因:" + e.getMessage(),e);
			throw e;
		}finally{
			if(rpSqlSession != null) {
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		}
		return infos;
	}
	
	private void asyncToMongo(final RedisUtil redis, final String md5Key, 
			final Date begDate, final Date endDate, final ReportBankUser user, final String orgCode){
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				SqlSession rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
				ReportMerchantSaleMapper merchantSaleMapper = rpSqlSession.getMapper(ReportMerchantSaleMapper.class);
				
				List<DBObject> mongoList = new ArrayList<DBObject>();
				String collection = "cb_merchant_sale_" + md5Key;
				String redisKey = RedisUtil.cbbank_merchant_sale_md5key(md5Key);
				DBObject object = null;
				SaleCountDetail sd = null;
				SaleCountDetail detail = null;
				SaleCountDetailMongo detailMongo = null;
				
				List<SaleCountDetail> saleCountDetailList = merchantSaleMapper.findSaleCountDetail(begDate, endDate, user, orgCode);
				for(int i = 0; i < saleCountDetailList.size(); i++){
					sd = saleCountDetailList.get(i);
					
					detail = new SaleCountDetail();
					detail.setBuyCount(sd.getBuyCount());
					detail.setOrderCount(sd.getOrderCount());
					detail.setProductSaleAmount(Arith.div(sd.getProductSaleAmount(), 1000, 2));
					detail.setProductSaleCount(sd.getProductSaleCount());
					detail.setTotalAmount(Arith.div(sd.getTotalAmount(), 1000, 2));
					detail.setAverAmount(Arith.div(ObjectUtils.equals(0, sd.getBuyCount()) ? 0d :Arith.div(sd.getTotalAmount(), sd.getBuyCount()), 1000, 2));
					if(Checker.isNotEmpty(sd.getLorgCode())){
						detail.setOrgCode(sd.getLorgCode());
						detail.setOrgName(sd.getLorgName());
					}else if(Checker.isNotEmpty(sd.getTorgCode())){
						detail.setOrgCode(sd.getTorgCode());
						detail.setOrgName(sd.getTorgName());
					}else if(Checker.isNotEmpty(sd.getSorgCode())){
						detail.setOrgCode(sd.getSorgCode());
						detail.setOrgName(sd.getSorgName());
					}else if(Checker.isNotEmpty(sd.getForgCode())){
						detail.setOrgCode(sd.getForgCode());
						detail.setOrgName(sd.getForgName());
					}
					
					detailMongo = new SaleCountDetailMongo();
					detailMongo.setIndex(i+1);
					detailMongo.setSaleCountDetail(detail);
					object = (DBObject) JSON.parse(JSonUtil.toJSonString(detailMongo));
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
				hash.put("count", String.valueOf(saleCountDetailList.size()));
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

