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
 * @Title: CashLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Cash;

/**
 * 
 * <p>@Title: CashLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface CashLogic {


    /**
     * 增加 Cash
     * @param cash
     * @return Long    主键ID
     */
	public Long addCash(Cash cash);



    /**
     * 删除 Cash
     * @param cash
     * @return Boolean    是否成功
     */
	public Boolean deleteCash(Cash cash);



    /**
     * 修改 Cash
     * @param cash
     * @return Boolean    是否成功
     */
	public Boolean updateCash(Cash cash);



    /**
     * 查询 Cash
     * @param cash
     * @return List<Cash>    结果集合 
     */
	public List<Cash> findCash(Cash cash);



    /**
     * 分页查询 Cash
     * @param page
     * @param cash
     * @return Page<Cash>    结果集合 
     */
	public Page<Cash> findCashByPage(Page<Cash> page, Cash cash);



}