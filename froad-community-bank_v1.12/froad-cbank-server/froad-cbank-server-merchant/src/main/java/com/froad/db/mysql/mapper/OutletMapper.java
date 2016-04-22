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
import com.froad.po.OutletPrefer;

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
     * 增加 Outlet
     * @param outlet
     * @return Long    主键ID
     */
	public Long addOutlet(Outlet outlet);



    /**
     * 批量增加 Outlet
     * @param List<Outlet>
     * @return Boolean    是否成功
     */
	public Boolean addOutletByBatch(List<Outlet> outletList);



    /**
     * 删除 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	public Boolean deleteOutlet(Outlet outlet);
	
	
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
	public Boolean updateOutlet(Outlet outlet);



    /**
     * 查询一个 Outlet
     * @param outlet
     * @return Outlet    返回结果
     */
	public Outlet findOutletById(Long id);
	
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
	public Outlet findOutletByOutletId(String outletId);



    /**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    返回结果集
     */
	public List<Outlet> findOutlet(Outlet outlet);
	
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
	public Integer countOutlet(Outlet outlet);

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
	 * 查询指定客户端和机构码下的所有的虚拟门店
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
	
	
	public Boolean disableOutlet(@Param("outletId") String outletId);
	/**
	 * 
	 * @Title: updateOutletByOutletId 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年10月21日
	 * @modify: Yaolong Liang 2015年10月21日
	 * @param outlet
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean updateOutletByOutletId(Outlet outlet);
	/**
	 * 
	 * @Title: countIsDefault 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年11月23日
	 * @modify: Yaolong Liang 2015年11月23日
	 * @param outlet
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer countIsDefault(Outlet outlet);
	
	
    /**
     * 惠付分页查询
     * @param page 
     * @param outletPrefer
     * @return List<outletPrefer>    返回分页查询结果集
     */
	public List<OutletPrefer> findOutletPreferByPage(Page<OutletPrefer> page, @Param("outletPrefer") OutletPrefer outletPrefer);
}