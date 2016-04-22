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
 * @Title: MerchantOperateLogLogic.java
 * @Package com.froad.logic
 * @Description: TODO
 * @author vania
 * @date 2015年4月8日
 */

package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantOperateLog;


/**    
 * <p>Title: MerchantOperateLogLogic.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月8日 上午11:37:32   
 */

public interface MerchantOperateLogLogic {
	/**
	 * 日志消息前缀
	 */
	public static final String MESSAGE_PREFIX = "MERCHANT-OPT:";
	
	/**
	 * 添加操作日志
	 * @Title: addBankOperateLog 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantOperatorLog
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean addBankOperateLog(MerchantOperateLog merchantOperatorLog) ;
	
	/**
	 * 分页查询操作日志
	 * @Title: findBankOperateLogByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param merchantOperatorLog
	 * @return
	 * @return Page<MerchantOperateLog>    返回类型 
	 * @throws
	 */
	public Page<MerchantOperateLog> findBankOperateLogByPage(Page<MerchantOperateLog> page, MerchantOperateLog merchantOperatorLog) ;
}
