/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:AuditRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年10月29日下午1:56:57
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.db.redis;

import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.mq.AuditMQ;
import com.froad.thread.FroadThreadPool;
import com.froad.util.JSonUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:AuditRedis Reason: 审核操作redis Date: 2015年10月29日 下午1:56:57
 * 
 * @author vania
 * @version
 * @see
 */
public class AuditRedis {
	private static RedisService redisService = new RedisManager();

	/**
	 * 
	 * lockInstance:获取审核流程锁
	 *
	 * @author vania 2015年10月29日 下午1:55:10
	 * @param clientId
	 * @param instanceId
	 * @return
	 *
	 */
	public static boolean lockInstance(String clientId, String instanceId) {
		return lockInstance(RedisKeyUtil.cbbank_fallow_audit_instance_lock_client_id_instance_id(clientId, instanceId));
	}

	/**
	 * 
	 * lockInstance:获取审核流程锁
	 *
	 * @author vania 2015年10月29日 下午1:54:47
	 * @param key
	 * @return
	 *
	 */
	public static boolean lockInstance(String key) {
		boolean result = redisService.setnx(key, "1") > 0l;
		if (result) {
			int seconds = 300;
			redisService.expire(key, seconds);
			LogCvt.info("设置审核流程锁:" + key + "超时间为" + seconds + "秒");
		}
		return result;
	}

	/**
	 * 
	 * unLockInstance:释放审核流程锁
	 *
	 * @author vania 2015年10月29日 下午1:55:17
	 * @param clientId
	 * @param instanceId
	 * @return
	 *
	 */
	public static boolean unLockInstance(String clientId, String instanceId) {
		return unLockInstance(RedisKeyUtil.cbbank_fallow_audit_instance_lock_client_id_instance_id(clientId, instanceId));
	}

	/**
	 * 
	 * unLockInstance:释放审核流程锁
	 *
	 * @author vania 2015年10月29日 下午1:55:31
	 * @param key
	 * @return
	 *
	 */
	public static boolean unLockInstance(String key) {
		boolean result = redisService.del(key) > 0l;
		if (result) {
			LogCvt.info("释放审核流程锁完成");
		}
		return result;
	}

	/**
	 * 
	 * sentMQ:执行异步通知
	 *
	 * @author vania 2015年10月29日 下午2:06:31
	 * @param mq_key
	 * @param mq
	 *
	 */
	public static void sentMQ(final String mq_key, final AuditMQ mq) {
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				String message = JSonUtil.toJSonString(mq);
				;
				if (redisService.lpush(mq_key.toString(), message) > 0)
					LogCvt.info(StringUtils.leftPad("", 20, '>') + "执行当前审核任务结束, MQ异步lpush通知业务系统成功! key==>" + mq_key.toString() + "\t" + "values==>" + message + StringUtils.rightPad("", 20, '<'));
			}
		});
	}
}
