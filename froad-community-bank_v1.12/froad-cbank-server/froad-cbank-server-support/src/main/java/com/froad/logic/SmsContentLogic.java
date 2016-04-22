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
 * @Title: SmsContentLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.SmsContent;

/**
 * 
 * <p>@Title: SmsContentLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface SmsContentLogic {


    /**
     * 增加 SmsContent
     * @param smsContent
     * @return Long    主键ID
     */
	public ResultBean addSmsContent(SmsContent smsContent);



    /**
     * 删除 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	public ResultBean deleteSmsContent(SmsContent smsContent);



    /**
     * 修改 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	public ResultBean updateSmsContent(SmsContent smsContent);



    /**
     * 查询 SmsContent
     * @param smsContent
     * @return List<SmsContent>    结果集合 
     */
	public List<SmsContent> findSmsContent(SmsContent smsContent);



    /**
     * 分页查询 SmsContent
     * @param page
     * @param smsContent
     * @return Page<SmsContent>    结果集合 
     */
	public Page<SmsContent> findSmsContentByPage(Page<SmsContent> page, SmsContent smsContent);


	/**
     * 查询 SmsContent
     * @return SmsContentVo
     * 
     * @param clientId
     * @param smsType
     */
	public SmsContent getSmsContentByClientIdAndType(String clientId,
			int smsType);
}