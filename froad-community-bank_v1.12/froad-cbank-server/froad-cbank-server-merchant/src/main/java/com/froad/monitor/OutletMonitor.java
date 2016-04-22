/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: OutletMonitor.java
 * @Package com.froad.monitor
 * @Description: TODO
 * @author vania
 * @date 2015年5月19日
 */

package com.froad.monitor;

import org.apache.commons.lang.math.NumberUtils;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.froad.monitor.impl.MonitorManager;
import com.froad.thread.FroadThreadPool;

/**
 * <p>
 * Title: OutletMonitor.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年5月19日 下午1:36:09
 */

public class OutletMonitor {
	private static MonitorManager monitorManager = new MonitorManager();

//	private static final String businame = MonitorManager.propertiesMap.get("monitor.businessName").trim();
	private static final String businame = Constants.MONITOR_BUSINAME;
	
	private static final String hottestOutletServiceInvokeNumberAttrId = MonitorManager.propertiesMap.get("monitor.hottestOutlet.service.invokeNumber.attrId").trim();
	private static final String hottestOutletServiceInvokeNumberType = MonitorManager.propertiesMap.get("monitor.hottestOutlet.service.invokeNumber.type").trim();
	
	private static final String hottestOutletServiceConsumingTimeAttrId = MonitorManager.propertiesMap.get("monitor.hottestOutlet.service.consumingTime.attrId").trim();
	private static final String hottestOutletServiceConsumingTimeType = MonitorManager.propertiesMap.get("monitor.hottestOutlet.service.consumingTime.type").trim();
	
	private static final String hottestOutletMongodbConsumingTimeAttrId = MonitorManager.propertiesMap.get("monitor.hottestOutlet.mongodb.consumingTime.attrId").trim();
	private static final String hottestOutletMongodbConsumingTimeType = MonitorManager.propertiesMap.get("monitor.hottestOutlet.mongodb.consumingTime.type").trim();

	private static final String nearOutletServiceInvokeNumberAttrId = MonitorManager.propertiesMap.get("monitor.nearOutlet.service.invokeNumber.attrId").trim();
	private static final String nearOutletServiceInvokeNumberType = MonitorManager.propertiesMap.get("monitor.nearOutlet.service.invokeNumber.type").trim();
	
	private static final String nearOutletServiceConsumingTimeAttrId = MonitorManager.propertiesMap.get("monitor.nearOutlet.service.consumingTime.attrId").trim();
	private static final String nearOutletServiceConsumingTimeType = MonitorManager.propertiesMap.get("monitor.nearOutlet.service.consumingTime.type").trim();
	
	private static final String nearOutletMongodbConsumingTimeAttrId = MonitorManager.propertiesMap.get("monitor.nearOutlet.mongodb.consumingTime.attrId").trim();
	private static final String nearOutletMongodbConsumingTimeType = MonitorManager.propertiesMap.get("monitor.nearOutlet.mongodb.consumingTime.type").trim();

	/**
	 * 搜索热门门店接口调用次数监控
	 * @Title: sendHottestOutletServiceInvokeNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendHottestOutletServiceInvokeNumber() {
		LogCvt.info("搜索热门门店接口调用次数监控");
		monitorManager.send(businame, hottestOutletServiceInvokeNumberAttrId, "1", hottestOutletServiceInvokeNumberType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, hottestOutletServiceInvokeNumberAttrId, "1", hottestOutletServiceInvokeNumberType, "");		
//			}
//		});
		
	}

	/**
	 * 搜索热门门店接口耗时监控
	 * 
	 * @Title: sendHottestOutletServiceConsumingTime
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param time
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendHottestOutletServiceConsumingTime(final long time) {
		LogCvt.info("调用搜索热门门店接口耗时：" + time + "毫秒");
		monitorManager.send(businame, hottestOutletServiceConsumingTimeAttrId, String.valueOf(time), hottestOutletServiceConsumingTimeType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, hottestOutletServiceConsumingTimeAttrId, String.valueOf(time), hottestOutletServiceConsumingTimeType, "");		
//			}
//		});
		
	}
	/**
	 * 搜索热门门店数据库耗时监控
	 * @Title: sendHottestOutletMongodbConsumingTime 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param time
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendHottestOutletMongodbConsumingTime(final long time) {
		LogCvt.info("调用搜索热门门店数据库耗时：" + time + "毫秒");
		monitorManager.send(businame, hottestOutletMongodbConsumingTimeAttrId, String.valueOf(time), hottestOutletMongodbConsumingTimeType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, hottestOutletMongodbConsumingTimeAttrId, String.valueOf(time), hottestOutletMongodbConsumingTimeType, "");		
//			}
//		});
		
	}
	
	/**
	 * 附近搜索门店接口调用次数监
	 * @Title: sendNearOutletServiceInvokeNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendNearOutletServiceInvokeNumber() {
		LogCvt.info("调用附近搜索门店接口调用次数监控");
		monitorManager.send(businame, nearOutletServiceInvokeNumberAttrId, "1", nearOutletServiceInvokeNumberType, "");
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, nearOutletServiceInvokeNumberAttrId, "1", nearOutletServiceInvokeNumberType, "");
//
//			}
//		});
		
	}

	/**
	 * 附近搜索门店接口耗时监控
	 * 
	 * @Title: sendNearOutletServiceConsumingTime
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param time
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendNearOutletServiceConsumingTime(final long time) {
		LogCvt.info("调用附近搜索门店接口耗时：" + time + "毫秒");
		monitorManager.send(businame, nearOutletServiceConsumingTimeAttrId, String.valueOf(time), nearOutletServiceConsumingTimeType, "");
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, nearOutletServiceConsumingTimeAttrId, String.valueOf(time), nearOutletServiceConsumingTimeType, "");
//
//			}
//		});
		
	}
	
	/**
	 * 附近搜索门店数据库耗时监控
	 * @Title: sendNearOutletMongodbConsumingTime 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param time
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendNearOutletMongodbConsumingTime(final long time) {
		LogCvt.info("调用附近搜索门店数据库耗时：" + time + "毫秒");
		monitorManager.send(businame, nearOutletMongodbConsumingTimeAttrId, String.valueOf(time), nearOutletMongodbConsumingTimeType, "");
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, nearOutletMongodbConsumingTimeAttrId, String.valueOf(time), nearOutletMongodbConsumingTimeType, "");
//				
//			}
//		});
		
	}
}
