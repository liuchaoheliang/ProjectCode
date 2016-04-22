package com.froad.util.payment;

import java.util.Calendar;
import java.util.Date;

import com.froad.common.beans.ResultBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.util.SimpleID;

/**
 * EasyTools 简易实用方法
* <p>Function: Esy</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 下午1:49:39
* @version 1.0
 */
public class EsyT {
	
	private EsyT(){};
	
	//-----------------------------------beans---------------------------------------
	/**
	 * 创建ID服务
	 */
	private static SimpleID simpleIDService;
	/**
	 * 业务监控服务
	 */
	private static MonitorService monitorService;
	
	//-----------------------------------beans---------------------------------------
	
	
	/**
	 * 获取唯一随机ID
	* <p>Function: simpleID</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 上午9:47:22
	* @version 1.0
	* @return
	 */
	public static String simpleID(){
		if(simpleIDService == null){
			simpleIDService = new SimpleID(Const.PAYMENT_MODULE_ID);
		}
		return simpleIDService.nextId();
	}

	/**
	 * 获取系统当前时间
	* <p>Function: currTime</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-20 下午1:50:51
	* @version 1.0
	* @return
	 */
	public static long currTime(){
		return System.currentTimeMillis();
	}

	/**
	 * 判断ResultBean的结果是否成功
	* <p>Function: isResultBeanSuccess</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-21 下午5:13:17
	* @version 1.0
	* @param resultBean
	* @return
	 */
	public static boolean isResultBeanSuccess(ResultBean resultBean){
		if(ResultCode.success.getCode().equals(resultBean.getCode())){
			return true;
		}
		return false;
	}
	
	/**
	 * 业务监控上报
	* <p>Function: sendPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午2:29:18
	* @version 1.0
	* @param point
	 */
	public static void sendPoint(MonitorPointEnum monitorPointEnum){
		if(monitorService == null){
			monitorService = new MonitorManager();
		}
		monitorService.send(monitorPointEnum);
	}
	
	/**
	 * 获取现在到一个自然年的天数偏移量
	* <p>Function: nowToNaturalYearDaysOffset</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年6月23日 下午2:58:14
	* @version 1.0
	* @return
	 */
	public static Date nowToNaturalYearDaysOffset(){
		Date dateNow = new Date();
		return TimeHelper.offsetDate(dateNow, Calendar.YEAR, 1);
	}
	/**
	 * 计算指定时间正向偏移一年后的新date
	 * targetToNaturalYearDaysOffset:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年12月7日 下午1:13:43
	 * @param date
	 * @return
	 *
	 */
	public static Date targetToNaturalYearDaysOffset(Date date){
		return TimeHelper.offsetDate(date, Calendar.YEAR, 1);
	}
	
	public static int offsetTwoTimeDays(long timeFuture,long timeNow){
		return (int) ((timeFuture - timeNow)/(60 * 60 * 24 * 1000));
	}
	/**
	 * 获取支付对接的转意remark code（this is a big 坑）
	* <p>Function: getSpecialRemarkValue</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-24 下午3:22:06
	* @version 1.0
	* @param key
	* @return
	 */
	public static String getSpecialRemarkValue(String key){
		return Const.getSpecialRemarkValue(key);
	}

}
