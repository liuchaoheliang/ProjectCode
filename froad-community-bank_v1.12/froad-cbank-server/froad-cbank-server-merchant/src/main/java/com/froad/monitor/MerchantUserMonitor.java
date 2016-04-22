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
 * @Title: MerchantUserMonitor.java
 * @Package com.froad.monitor
 * @Description: TODO
 * @author vania
 * @date 2015年5月19日
 */

package com.froad.monitor;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.froad.monitor.impl.MonitorManager;
import com.froad.thread.FroadThreadPool;

/**
 * <p>
 * Title: MerchantUserMonitor.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年5月19日 下午1:36:09
 */

public class MerchantUserMonitor {
	private static MonitorManager monitorManager = new MonitorManager();

//	private static final String businame = MonitorManager.propertiesMap.get("monitor.businessName").trim();
	private static final String businame = Constants.MONITOR_BUSINAME;
	
	private static final String merchantUserLoginServiceInvokeNumberAttrId = MonitorManager.propertiesMap.get("monitor.merchantUserLogin.service.invokeNumber.attrId").trim();
	private static final String merchantUserLoginServiceInvokeNumberType = MonitorManager.propertiesMap.get("monitor.merchantUserLogin.service.invokeNumber.type").trim();
	
	private static final String merchantUserLoginServiceFaildNumberAttrId = MonitorManager.propertiesMap.get("monitor.merchantUserLogin.service.faildNumber.attrId").trim();
	private static final String merchantUserLoginServiceFaildNumberType = MonitorManager.propertiesMap.get("monitor.merchantUserLogin.service.faildNumber.type").trim();
	

	/**
	 * 商户用户登录接口调用次数监控
	 * @Title: sendMerchantUserLoginServiceInvokeNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendMerchantUserLoginServiceInvokeNumber() {
		LogCvt.info("商户用户登录接口调用次数监控");
		monitorManager.send(businame, merchantUserLoginServiceInvokeNumberAttrId, "1", merchantUserLoginServiceInvokeNumberType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, merchantUserLoginServiceInvokeNumberAttrId, "1", merchantUserLoginServiceInvokeNumberType, "");		
//			}
//		});
		
	}
	
	/**
	 * 商户用户登录接口调用失败次数监控
	 * @Title: sendMerchantUserLoginServiceFaildNumber 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendMerchantUserLoginServiceFaildNumber() {
		LogCvt.info("商户用户登录接口调用失败次数监控");
		monitorManager.send(businame, merchantUserLoginServiceFaildNumberAttrId, "1", merchantUserLoginServiceFaildNumberType, "");		
//		FroadThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				monitorManager.send(businame, merchantUserLoginServiceFaildNumberAttrId, "1", merchantUserLoginServiceFaildNumberType, "");		
//			}
//		});
		
	}

}
