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
package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.OutletCG;

/**
 * 
 * <p>@Title: OutletMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OutletMapperCG {


    /**
     * 增加 Outlet
     * @param outlet
     * @return Long    主键ID
     */
	public Long addOutlet(OutletCG outletCG);



	
	/**
	 * 查询单个门店信息
	 * @Title: findOneOutletCG 
	 * @Description: 
	 * @author: froad-huangyihao 2015年5月3日
	 * @modify: froad-huangyihao 2015年5月3日
	 * @param outletCG
	 * @return
	 * @throws
	 */
	public OutletCG findOneOutletCG(OutletCG outletCG);
	
	
	public List<OutletCG> findGroupList();
	
	




    /**
     * 删除 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	public Boolean deleteOutlet(OutletCG outlet);
	
	
	/**
     * 物理删除 Outlet
     * @param outletId
     * @return Boolean    是否成功
     */
	public Boolean removeOutlet(String outletId);



    /**
     * 修改 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	public Boolean updateOutlet(OutletCG outlet);



    /**
     * 查询一个 Outlet
     * @param outlet
     * @return Outlet    返回结果
     */
	public OutletCG findOutletById(Long id);
	
	/**
	 * 查询一个 Outlet
	 * @Title: findOutletByOutletId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return Outlet    返回类型 
	 * @throws
	 */           
	public OutletCG findOutletByOutletId(String outletId);

	
	


    /**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    返回结果集
     */
	public List<OutletCG> findOutlet(OutletCG outlet);
	
	/**
	 * 统计门店
	 * @Title: countOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outlet
	 * @return
	 * @return Integer    返回类型 
	 * @throws
	 */
	public Integer countOutlet(OutletCG outlet);

	/**
	 * 查询预售网点
	 * @Title: findBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantIdList
	 * @return
	 * @return List<Outlet>    返回类型 
	 * @throws
	 */
	public List<OutletCG> findBankOutlet(@Param("merchantIdList") List<String> merchantIdList);
	
}