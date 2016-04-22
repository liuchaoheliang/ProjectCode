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

import com.froad.db.mysql.bean.Page;
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
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return Long    主键ID
     */
	public Long addDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 批量增加 DeliveryCorp
     * @param List<DeliveryCorp>
     * @return Boolean    是否成功
     */
	public Boolean addDeliveryCorpByBatch(List<DeliveryCorp> deliveryCorpList);



    /**
     * 删除 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	public Boolean deleteDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 修改 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	public Boolean updateDeliveryCorp(DeliveryCorp deliveryCorp);


    /**
     * 查询一个 DeliveryCorp
     * @param deliveryCorp
     * @return DeliveryCorp    返回结果
     */
	public DeliveryCorp findDeliveryCorpById(Long id);

    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorp>    返回结果集
     */
	public List<DeliveryCorp> findDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 分页查询 DeliveryCorp
     * @param page 
     * @param deliveryCorp
     * @return List<DeliveryCorp>    返回分页查询结果集
     */
	public List<DeliveryCorp> findByPage(Page page, @Param("deliveryCorp")DeliveryCorp deliveryCorp);



}