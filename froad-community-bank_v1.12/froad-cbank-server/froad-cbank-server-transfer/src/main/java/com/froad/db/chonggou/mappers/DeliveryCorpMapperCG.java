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
 * @Title: DeliveryCorpMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.DeliveryCorpCG;
import com.froad.db.mysql.bean.Page;

/**
 * 
 * <p>@Title: DeliveryCorpMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface DeliveryCorpMapperCG {


    /**
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return Long    主键ID
     */
	public Long addDeliveryCorp(DeliveryCorpCG deliveryCorp);

	
    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorp>    返回结果集
     */
	public List<DeliveryCorpCG> findDeliveryCorp(DeliveryCorpCG deliveryCorp);

	
	



}