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
 * @Title: CashMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Cash;

/**
 * 
 * <p>@Title: CashMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface CashMapper {


    /**
     * 增加 Cash
     * @param cash
     * @return Long    主键ID
     */
	public Long addCash(Cash cash);



    /**
     * 批量增加 Cash
     * @param List<Cash>
     * @return Boolean    是否成功
     */
	public Boolean addCashByBatch(List<Cash> cashList);



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
     * 查询一个 Cash
     * @param cash
     * @return Cash    返回结果
     */
	public Cash findCashById(Long id);



    /**
     * 查询 Cash
     * @param cash
     * @return List<Cash>    返回结果集
     */
	public List<Cash> findCash(Cash cash);



    /**
     * 分页查询 Cash
     * @param page 
     * @param cash
     * @return List<Cash>    返回分页查询结果集
     */
	public List<Cash> findByPage(Page page, @Param("cash")Cash cash);



}