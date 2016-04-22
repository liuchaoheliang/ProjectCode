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
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.DeliveryCorp;

/**
 * 
 * <p>@Title: DeliveryCorpMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface DeliveryCorpMapper {


    /**
     * 根据ID查询 DeliveryCorp
     * @param deliveryCorp
     * @return DeliveryCorp    返回结果
     */
	public DeliveryCorp findDeliveryCorpById(Long id);

	
	/**
	 * 
	 * findAllDeliveryCorp:(查询所有的物流公司信息).
	 *
	 * @author huangyihao
	 * 2015年11月30日 下午1:30:57
	 * @return
	 *
	 */
	public List<DeliveryCorp> findAllDeliveryCorp();
	
	
	/**
	 * 
	 * findByCorpCode:(根据corpCode查询).
	 *
	 * @author huangyihao
	 * 2015年12月18日 上午9:39:53
	 * @param corpCode
	 * @return
	 *
	 */
	public DeliveryCorp findByCorpCode(@Param("corpCode")String corpCode);
	
}