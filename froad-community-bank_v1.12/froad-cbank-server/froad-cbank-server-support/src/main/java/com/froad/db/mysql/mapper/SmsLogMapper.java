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
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.SmsLog;

/**
 * 
 * <p>@Title: SmsLogMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface SmsLogMapper {


    /**
     * 增加 SmsLog
     * @param smsLog
     * @return Long    主键ID
     */
	public Long addSmsLog(SmsLog smsLog);



    /**
     * 批量增加 SmsLog
     * @param List<SmsLog>
     * @return Boolean    是否成功
     */
	public Boolean addSmsLogByBatch(List<SmsLog> smsLogList);



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
     * 查询一个 SmsLog
     * @param smsLog
     * @return SmsLog    返回结果
     */
	public SmsLog findSmsLogById(Long id);

    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLog>    返回结果集
     */
	public List<SmsLog> findSmsLog(@Param("smsLog")SmsLog smsLog,@Param("smsTypes")Integer[] smsTypes);



    /**
     * 分页查询 SmsLog
     * @param page 
     * @param smsLog
     * @return List<SmsLog>    返回分页查询结果集
     */
	public List<SmsLog> findByPage(Page page, @Param("smsLog")SmsLog smsLog,@Param("smsTypes")Integer[] smsTypes);



}