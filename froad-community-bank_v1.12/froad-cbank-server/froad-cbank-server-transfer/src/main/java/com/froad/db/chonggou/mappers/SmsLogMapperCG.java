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
 * @Title: SmsLogMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;


import com.froad.db.chonggou.entity.SmsLogCG;

/**
 * 
 * <p>@Title: SmsLogMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface SmsLogMapperCG {


    /**
     * 增加 SmsLog
     * @param smsLog
     * @return Long    主键ID
     */
	public Long addSmsLog(SmsLogCG smsLog);




    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLog>    返回结果集
     */
	public List<SmsLogCG> findSmsLog(SmsLogCG smsLog);



}