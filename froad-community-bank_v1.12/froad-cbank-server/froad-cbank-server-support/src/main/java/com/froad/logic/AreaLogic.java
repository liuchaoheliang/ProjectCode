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
 * @Title: AreaLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.Area;

/**
 * 
 * <p>@Title: AreaLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface AreaLogic {


    /**
     * 增加 Area
     * @param area
     * @return Long    主键ID
     */
	public ResultBean addArea(Area area);



    /**
     * 删除 Area
     * @param area
     * @return Boolean    是否成功
     */
	public ResultBean deleteArea(Area area);



    /**
     * 修改 Area
     * @param area
     * @return Boolean    是否成功
     */
	public ResultBean updateArea(Area area);



    /**
     * 查询 Area
     * @param area
     * @return List<Area>    结果集合 
     */
	public List<Area> findArea(Area area);



    /**
     * 分页查询 Area
     * @param page
     * @param area
     * @return Page<Area>    结果集合 
     */
	public Page<Area> findAreaByPage(Page<Area> page, Area area);


	/** 
	 * 根据id获取地区
	* @Title: findAreaById 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @param 
	* @return Area
	* @throws 
	*/
	public Area findAreaById(Long id);
	
	/**
     * 根据 areaCode 获取地区
     * @return AreaVo
     * 
     * @param areaCode
     */
	public Area findAreaByAreaCode(String areaCode);
	
	/**
     * 根据 areaCode 和 clientId 获取地区 
     * @return AreaVo
     */
	public Area findAreaByAreaCodeAndClientId(String areaCode, String clientId);
	
	
	/** 根据id获取子集地区
	* @Title: findChildrenInfoById 
	* @Description: 
	* @author longyunbo
	* @param  id
	* @param  areaCode
	* @return List<Area>
	* @throws 
	*/
	public List<Area> findChildrenInfoById(Long id, String areaCode);
	
	/** 根据id获取子集地区
	* @Title: findChildrenInfoById 
	* @Description: 
	* @param  id
	* @param  areaCode
	* @param  clientId
	* @return List<Area>
	* @throws 
	*/
	public List<Area> findChildrenInfo(Long id, String areaCode, String clientId);
	
	/**
     * 判断areaCode是否属于clientId的范围内
     * @return boolean
     * 
     * @param areaCode
     * @param clientId
     */
	public boolean isAreaCodeScopeOfClientId(String areaCode, String clientId);

	/**
     * 根据clientId获取省级地区
     * @return List<Area>
     * 
     * @param clientId
     */
	public List<Area> findProvinceAreaByClientId(String clientId);
}