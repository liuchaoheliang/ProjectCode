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
 * 
 * @Title: SmsLogLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.SmsLog;

/**
 * 
 * <p>@Title: SmsLogLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface SmsLogLogic {


    /**
     * 增加 SmsLog
     * @param smsLog
     * @return Long    主键ID
     */
	public Long addSmsLog(SmsLog smsLog);



    /**
     * 删除 SmsLog
     * @param smsLog
     * @return Boolean    是否成功
     */
	public Boolean deleteSmsLog(SmsLog smsLog);



    /**
     * 修改 SmsLog
     * @param smsLog
     * @return Boolean    是否成功
     */
	public Boolean updateSmsLog(SmsLog smsLog);



    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLog>    结果集合 
     */
	public List<SmsLog> findSmsLog(SmsLog smsLog,String flag);



    /**
     * 分页查询 SmsLog
     * @param page
     * @param smsLog
     * @return Page<SmsLog>    结果集合 
     */
	public Page<SmsLog> findSmsLogByPage(Page<SmsLog> page, SmsLog smsLog,String flag);



}