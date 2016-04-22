package com.froad.singleton;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.ReportMongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mappers.MerchantMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.DateUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MerchantSingleton {
	
	private static MerchantSingleton instance = null;
	
	/** 商户信息缓存 */
	private static volatile Map<String, MerchantDetail> merchantMap = new HashMap<String, MerchantDetail>();
	/** 虚拟商户标识缓存 */
	private static volatile Map<String, String> merchantStatusMap = new HashMap<String, String>();
	
	/** 商户信息缓存 */
	private static volatile Map<String, Merchant> mysqlMap = new HashMap<String, Merchant>();
	
	private static ReportMongoManager reportMongo = new ReportMongoManager();
	
	private static CommonLogic commonLogic = new CommonLogicImpl();
	
	/** 控制刷新缓存,每天刷新一次 */
	private static int todayKey = 0;
	
	private MerchantSingleton(){
	}
	
	/**
	 * get org instance
	 * 
	 * @param session
	 * @return
	 */
	public static MerchantSingleton getInstance(){
		if (null == instance){
			synchronized (MerchantSingleton.class){
				if (null == instance){
					
					instance = new MerchantSingleton();
					
					
				}
			}
		}
		
		return instance;
	}
	
	public static MerchantDetail getMerchantDetail(String merchantId){
		MerchantDetail merchant = merchantMap.get(merchantId);
		if (merchant == null) {
			DBObject dbObj = new BasicDBObject();
			dbObj.put("_id", merchantId);
			merchant = reportMongo.findOne(dbObj, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
			if (merchant != null) {
				merchantMap.put(merchantId, merchant);
			}
		}
		return merchant;
	}
	
	public static String getMerchantStatus(String clientId, String merchantId){
		String merchantStatus = merchantStatusMap.get(merchantId);
		if (merchantStatus == null) {
			// 获取merchant_status
			Map<String, String> merchantM = commonLogic.getMerchantRedis(clientId, merchantId);
			if (merchantM != null) {
				merchantStatus = merchantM.get("merchant_status");
				merchantStatusMap.put(merchantId, merchantStatus);
			}
		}
		
		return merchantStatus;
	}
	
	/**
	 * 从Mysql获取商户信息
	 */
	public static Merchant getMerchant(String merchantId){
		
		synchronized (MerchantSingleton.class){
			int today = Integer.parseInt(DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, new Date()));
			if (todayKey != today) {
				mysqlMap = new HashMap<String, Merchant>();
				
				SqlSession sqlSession = null;
				try {
					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
					MerchantMapper merchantMapper = sqlSession.getMapper(MerchantMapper.class);
					
					List<Merchant> list = merchantMapper.findAllRealMerchants();
					for (int i = 0; list != null && i < list.size(); i++) {
						mysqlMap.put(list.get(i).getMerchantId(), list.get(i));
					}
					
					todayKey = today;
				} catch(Exception e) {
					LogCvt.error("查询商户信息异常", e);
				} finally {
					if (sqlSession != null) {
						sqlSession.close();
					}
				}
				
			}
			
			return mysqlMap.get(merchantId);
		}
		
	}
	
}
