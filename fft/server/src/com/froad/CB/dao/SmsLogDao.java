package com.froad.CB.dao;

import com.froad.CB.po.SmsLog;

public interface SmsLogDao {

	
	/**
	  * 方法描述：添加短信发送记录
	  * @param: SmsLog
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 3:45:40 PM
	  */
	public Integer add(SmsLog smsLog);
	
	
	/**
	  * 方法描述：查询短信发送记录
	  * @param: SmsLog
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 3:46:35 PM
	  */
	public SmsLog getById(Integer id);
	
	
	/**
	  * 方法描述：修改消息发送记录
	  * @param: SmsLog
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 3:47:04 PM
	  */
	public void updateById(SmsLog smsLog);
	
	
	/**
	  * 方法描述：更新短信的发送结果
	  * @param: id 主键编号
	  * @param: result 短信的发送结果
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 18, 2013 5:43:00 PM
	  */
	public void updateResultById(Integer id,boolean result);
	
	
	/**
	  * 方法描述：删除消息发送记录
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 3:47:26 PM
	  */
	public void deleteById(Integer id);
}
