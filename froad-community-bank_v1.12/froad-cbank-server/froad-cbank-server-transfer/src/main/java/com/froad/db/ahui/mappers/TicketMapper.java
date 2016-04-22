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
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.fft.persistent.entity.TransSecurityTicket;

/**
 *  卷模块Mybatis接口
  * @ClassName: OrderMapper
  * @Description: TODO
  * @author share 2015年5月1日
  * @modify share 2015年5月1日
 */
public interface TicketMapper {

	
	
    /**
     *  团购卷查询
      * @Title: queryByGroupTicket
      * @Description: TODO
      * @author: share 2015年5月1日
      * @modify: share 2015年5月1日
      * @param @return    
      * @return List<TransSecurityTicket>    
      * @throws
     */
	public List<TransSecurityTicket> queryByGroupTicket(@Param("transId")Long sn);
	
	/**
	 *  查询所有的卷数据
	  * @Title: queryAll
	  * @Description: TODO
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param @return    
	  * @return List<TransSecurityTicket>    
	  * @throws
	 */
	public List<TransSecurityTicket> queryAll();
	

}