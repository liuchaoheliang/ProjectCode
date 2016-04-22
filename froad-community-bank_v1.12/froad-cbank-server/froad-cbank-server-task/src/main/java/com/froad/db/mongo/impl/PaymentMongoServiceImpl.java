package com.froad.db.mongo.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.PaymentMongoService;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;
import com.froad.util.MongoTableName;
import com.froad.util.TaskTimeUtil;
import com.froad.util.TimeHelper;
import com.froad.util.TimeHelper.TimeType;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class PaymentMongoServiceImpl implements PaymentMongoService {

	private MongoManager manager = new MongoManager();
	
	private int OFF_DAY = 1;
	
	@Override
	public List<PaymentMongo> queryPaymentOfDaily() {
		return queryPaymentOfDaily(null);
	}
	
	@Override
	public List<PaymentMongo> queryPaymentOfDaily(String orderId) {
		DBObject where = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		List<PaymentMongo> paymentList = new ArrayList<PaymentMongo>();
		
		Date yesterday = TimeHelper.skewingDate(new Date(),Calendar.DAY_OF_YEAR,-1 * OFF_DAY);
		String dateYesterdayStr = TimeHelper.formatDate(TimeType.yyyySplitMMSplitdd, yesterday);
		String dateTodayStr = TimeHelper.formatDate(TimeType.yyyySplitMMSplitdd, new Date());
		String dateStrStart = dateYesterdayStr + "|02:00:00";
		String dateStrEnd = dateTodayStr + "|01:59:59";
		
		LogCvt.info("基数据抓取指定时间: " + dateStrStart + "-" + dateStrEnd);
		Long startTime = TimeHelper.parseDate(dateStrStart, TimeType.DEFAULT).getTime();
		Long endTime = TimeHelper.parseDate(dateStrEnd, TimeType.DEFAULT).getTime();
		try {
//			where.put("is_enable", true);
			if(!StringUtils.isEmpty(orderId)){
				where.put("order_id", orderId);
			}
	        values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GT, startTime)));
	        values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LT, endTime)));
	        if (!values.isEmpty()) {
	            where.put(QueryOperators.AND, values);
	        }
	        paymentList =  (List<PaymentMongo>) manager.findAll(where, MongoTableName.CB_PAYMENT, PaymentMongo.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogCvt.error("定时任务: 查询支付信息失败------系统异常------");
			LogCvt.error(e.getMessage(), e);
		}
		return paymentList;
	}

	@Override
	public List<String> queryOrderIdDistinctOfDaily() {
		DBObject where = new BasicDBObject();
		BasicDBList values = new BasicDBList();
		
		Date yesterday = TimeHelper.skewingDate(new Date(),Calendar.DAY_OF_YEAR,-1 * OFF_DAY);
		String dateYesterdayStr = TimeHelper.formatDate(TimeType.yyyySplitMMSplitdd, yesterday);
		String dateTodayStr = TimeHelper.formatDate(TimeType.yyyySplitMMSplitdd, new Date());
		String dateStrStart = dateYesterdayStr + "|02:00:00";
		String dateStrEnd = dateTodayStr + "|01:59:59";
		LogCvt.info("基数据抓取指定时间: " + dateStrStart + "-" + dateStrEnd);
		
		Long startTime = TimeHelper.parseDate(dateStrStart, TimeType.DEFAULT).getTime();
		Long endTime = TimeHelper.parseDate(dateStrEnd, TimeType.DEFAULT).getTime();
		
		
		try {
//			where.put("is_enable", true);
	        values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GT, startTime)));
	        values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LT, endTime)));
//			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.GT, TimeHelper.parseDate("2015-10-01|00:00:00", TimeType.DEFAULT).getTime())));
//			values.add(new BasicDBObject("create_time", new BasicDBObject(QueryOperators.LT, TimeHelper.parseDate("2015-10-01|23:59:59", TimeType.DEFAULT).getTime())));
	        if (!values.isEmpty()) {
	            where.put(QueryOperators.AND, values);
	        }
	        List<String> orderIds = manager.distinct(MongoTableName.CB_PAYMENT, "order_id", where);
	        return orderIds;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogCvt.error("定时任务: 查询支付信息失败------系统异常------");
			LogCvt.error(e.getMessage(), e);
		}
		return null;
	}
	
	@Override
	public OrderMongo queryByOrderId(String orderId) {
		DBObject query = new BasicDBObject();
		query.put("_id", orderId);
		//待优化 只需要查一个字段
		OrderMongo order = manager.findOne(query, MongoTableName.CB_ORDER, OrderMongo.class);
		if(order == null){
			return null;
		}
		return order;
	}



	

}
