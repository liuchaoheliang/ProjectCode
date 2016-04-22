package com.froad.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;

public class SerialNumberUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static RedisService redis = new RedisManager();

	private final static String PRE_AUDIT_SEQ_KEY = "audit:serialid:seq:";

	private final static String PRE_TASK_SEQ_KEY = "task:serialid:seq:";

	/**
	 * 审核流水号
	 * 
	 * @return
	 */
	public synchronized static String getAuditIdSerialNumber() {
		String dateTime = sdf.format(new Date());
		String AUDIT_SEQ_KEY = PRE_AUDIT_SEQ_KEY + dateTime;
		Long serialNo = 1l;
		if (redis.setnx(AUDIT_SEQ_KEY, serialNo.toString()) == 1) {
			// 设置24小时过期
			redis.expire(AUDIT_SEQ_KEY, getExpireTime());
		} else {
			serialNo = redis.incr(AUDIT_SEQ_KEY);
		}
		return dateTime + String.format("%08d", serialNo);
	}

	/**
	 * 任务流水号
	 * 
	 * @return
	 */
	public synchronized static String getTaskIdSerialNumber() {
		String dateTime = sdf.format(new Date());
		String TASK_SEQ_KEY = PRE_TASK_SEQ_KEY + dateTime;
		Long serialNo = 1l;
		if (redis.setnx(TASK_SEQ_KEY, serialNo.toString()) == 1) {
			// 设置24小时过期
			redis.expire(TASK_SEQ_KEY, getExpireTime());
		} else {
			serialNo = redis.incr(TASK_SEQ_KEY);
		}
		return dateTime + String.format("%08d", serialNo);
	}

	/**
	 * 
	 * getExpireTime:获取当前时间到明天的0点0分59秒的秒数差
	 *
	 * @author vania 2015年11月12日 下午3:57:56
	 * @return
	 *
	 */
	private static int getExpireTime() {
		final Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, 1); // 加一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 59); // 59秒
		cal.set(Calendar.MILLISECOND, 0);
		final double diff = cal.getTimeInMillis() - System.currentTimeMillis();
		return (int) diff / 1000;
	}

	public static void main(String[] args) {
		// System.out.println(new SerialNumberUtil().getSerialNumber("0"));
		getExpireTime();
		for (int i = 0; i < 10; i++) {
			System.out.println("序列" + i + ":" + getTaskIdSerialNumber());
			
		}
		// System.out.println(getExpireTime());
	}

}
