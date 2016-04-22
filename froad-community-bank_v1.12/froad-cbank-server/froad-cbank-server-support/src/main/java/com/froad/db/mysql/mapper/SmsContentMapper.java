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
 * @Title: SmsContentMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.SmsContent;

/**
 * 
 * <p>@Title: SmsContentMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface SmsContentMapper {


    /**
     * 增加 SmsContent
     * @param smsContent
     * @return Long    主键ID
     */
	public Long addSmsContent(SmsContent smsContent);



    /**
     * 批量增加 SmsContent
     * @param List<SmsContent>
     * @return Boolean    是否成功
     */
	public Boolean addSmsContentByBatch(List<SmsContent> smsContentList);



    /**
     * 删除 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	public Boolean deleteSmsContent(SmsContent smsContent);



    /**
     * 修改 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	public Boolean updateSmsContent(SmsContent smsContent);


	 /**
     * 查询一个 SmsContent
     * @param smsContent
     * @return SmsContent    返回结果
     */
	public SmsContent findSmsContentById(Long id);
	
	
    /**
     * 查询 SmsContent
     * @param smsContent
     * @return List<SmsContent>    返回结果集
     */
	public List<SmsContent> findSmsContent(SmsContent smsContent);



    /**
     * 分页查询 SmsContent
     * @param page 
     * @param smsContent
     * @return List<SmsContent>    返回分页查询结果集
     */
	public List<SmsContent> findByPage(Page page, @Param("smsContent")SmsContent smsContent);

	/**
     * 查询 SmsContent
     * @return SmsContentVo
     * 
     * @param clientId
     * @param smsType
     */
	public SmsContent findSmsContentByClientIdAndType(@Param("client")String clientId,
			@Param("smsType")int smsType);
}