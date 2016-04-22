/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:AuditInstanceMonitor.java
 * Package Name:com.froad.monitor
 * Date:2015年11月2日下午8:37:58
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.monitor;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.froad.monitor.impl.MonitorManager;

/**
 * ClassName:AuditInstanceMonitor
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月2日 下午8:37:58
 * @author   vania
 * @version  
 * @see 	 
 */
public class AuditInstanceMonitor {
	private static MonitorManager monitorManager = new MonitorManager();

//	private static final String businame = MonitorManager.propertiesMap.get("monitor.businessName").trim();
	private static final String businame = Constants.MONITOR_BUSINAME;
	
	
	private static final String createInstanceServiceFaildNumberAttrId = MonitorManager.propertiesMap.get("monitor.audit.service.createInstance.attrId").trim();
	private static final String createInstanceServiceFaildNumberType = MonitorManager.propertiesMap.get("monitor.audit.service.createInstance.type").trim();
	
	private static final String executeTaskServiceFaildNumberAttrId = MonitorManager.propertiesMap.get("monitor.audit.service.executeTask.attrId").trim();
	private static final String executeTaskServiceFaildNumberType = MonitorManager.propertiesMap.get("monitor.audit.service.executeTask.type").trim();
	
	/**
	 * 执行创建审核流水接口调用失败次数监控
	 * @Title: sendCreateInstanceServiceFaildNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendCreateInstanceServiceFaildNumber() {
		LogCvt.info("创建审核实例接口调用失败次数监控");
		monitorManager.send(businame, createInstanceServiceFaildNumberAttrId, "1", executeTaskServiceFaildNumberType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, merchantUserLoginServiceFaildNumberAttrId, "1", merchantUserLoginServiceFaildNumberType, "");		
//			}
//		});
		
	}
	
	
	/**
	 * 执行审核任务接口调用失败次数监控
	 * @Title: sendExecuteTaskServiceFaildNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendExecuteTaskServiceFaildNumber() {
		LogCvt.info("执行审核任务接口调用失败次数监控");
		monitorManager.send(businame, executeTaskServiceFaildNumberAttrId, "1", createInstanceServiceFaildNumberType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, merchantUserLoginServiceFaildNumberAttrId, "1", merchantUserLoginServiceFaildNumberType, "");		
//			}
//		});
		
	}
}
