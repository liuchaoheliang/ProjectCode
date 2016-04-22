package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.SmsLog;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface SmsLogMapper {
	/**
	 * 
	  * @Title: findSmsLog
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月25日
	  * @modify: Yaren Liang 2015年6月25日
	  * @param @param smsLog
	  * @param @return    
	  * @return List<SmsLog>    
	  * @throws
	 */
	public List<SmsLog> findSmsLog(SmsLog smsLog);
	
	/**
	 * 
	  * @Title: findALLSms
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月30日
	  * @modify: Yaren Liang 2015年6月30日
	  * @param @return    
	  * @return List<SmsLog>    
	  * @throws
	 */
	public List<SmsLog> findALLSms();
}
