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
 * @Title: DeliveryCorpLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.DeliveryCorp;

/**
 * 
 * <p>@Title: DeliveryCorpLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface DeliveryCorpLogic {


    /**
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return Long    主键ID
     */
	public ResultBean addDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 删除 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	public ResultBean deleteDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 修改 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	public ResultBean updateDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorp>    结果集合 
     */
	public List<DeliveryCorp> findDeliveryCorp(DeliveryCorp deliveryCorp);



    /**
     * 分页查询 DeliveryCorp
     * @param page
     * @param deliveryCorp
     * @return Page<DeliveryCorp>    结果集合 
     */
	public Page<DeliveryCorp> findDeliveryCorpByPage(Page<DeliveryCorp> page, DeliveryCorp deliveryCorp);



}