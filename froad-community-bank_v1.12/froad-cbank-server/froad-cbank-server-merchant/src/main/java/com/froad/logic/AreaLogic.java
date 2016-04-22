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
	
	

}