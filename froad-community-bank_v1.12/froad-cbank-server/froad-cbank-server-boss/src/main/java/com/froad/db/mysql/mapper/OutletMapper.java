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
 * @Title: OutletMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Outlet;

/**
 * 
 * <p>@Title: OutletMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OutletMapper {






    /**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    返回结果集
     */
	public List<Outlet> findOutlet(Outlet outlet);
	

	/**
	 * 查询预售网点
	 * @Title: findBankOutletByMerchantIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantIdList
	 * @return
	 * @return List<Outlet>    返回类型 
	 * @throws
	 */
	public List<Outlet> findBankOutletByMerchantIdList(@Param("merchantIdList") List<String> merchantIdList);
	
	/**
	 * 查询机构对应的虚拟门店
	 * @Title: findBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return Outlet    返回类型 
	 * @throws
	 */
	public Outlet findBankOutlet(@Param("clientId") String clientId, @Param("orgCode") String orgCode);

    /**
     * 分页查询 Outlet
     * @param page 
     * @param outlet
     * @return List<Outlet>    返回分页查询结果集
     */
	public List<Outlet> findByPage(Page<Outlet> page, @Param("outlet") Outlet outlet, @Param("orderBy") LinkedHashMap<String, String> orderBy);
	

	/**
	 * 查询提货网点(预售用到)
	 * @Title: findSubBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return List<Outlet>    返回类型 
	 * @throws
	 */
	public List<Outlet> findSubBankOutlet(@Param("clientId") String clientId, @Param("orgCode") String orgCode);
	
	/**
	 * 根据outletId 查询收获地址id(预售用到)
	 * @Title: getOutletByOutletId 
	 * @author liuyanyun
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return Outlet    返回类型 
	 * @throws
	 */
	public Outlet getOutletByOutletId(@Param("outletId") String outletId);
	
}