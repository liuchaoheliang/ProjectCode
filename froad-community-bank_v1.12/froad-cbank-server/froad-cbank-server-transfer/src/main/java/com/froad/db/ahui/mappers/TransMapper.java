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
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.ahui.entity.TransDto;

/**
 *  订单模块Mybatis接口
  * @ClassName: OrderMapper
  * @Description: TODO
  * @author share 2015年5月1日
  * @modify share 2015年5月1日
 */
public interface TransMapper {

	
	
    /**
     *  结算交易订单查询
      * @Title: queryNewId
      * @Description: TODO
      * @author: share 2015年5月1日
      * @modify: share 2015年5月1日
      * @param @return    
      * @return List<Trans>    
      * @throws
     */
	public List<TransDto> queryBySettlement();
	
	/**
	 *  查询所有订单
	  * @Title: queryAllOrderList
	  * @Description: TODO
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param @return    
	  * @return List<TransDto>    
	  * @throws
	 */
	public List<TransDto> queryAllOrderList();
	
	/**
	 *  查询ByID
	  * @Title: queryById
	  * @Description: TODO
	  * @author: share 2015年5月2日
	  * @modify: share 2015年5月2日
	  * @param @param id
	  * @param @return    
	  * @return TransDto    
	  * @throws
	 */
	public TransDto queryById(@Param("id")Long id);
	
	/**
	 *  按订单SN查询订单信息
	  * @Title: queryBySn
	  * @Description: TODO
	  * @author: share 2015年5月3日
	  * @modify: share 2015年5月3日
	  * @param @param sn
	  * @param @return    
	  * @return TransDto    
	  * @throws
	 */
	public TransDto queryBySn(@Param("sn")String sn);
	
	/**
	 * 待发送卷信息查询
	  * @Title: queryForPresellTicke
	  * @Description: TODO
	  * @author: share 2015年5月3日
	  * @modify: share 2015年5月3日
	  * @param @return    
	  * @return List<TransDto>    
	  * @throws
	 */
	public List<TransDto> queryForPresellTicke();
	/**
	 * 查询订单号
	  * @Title: queryTransBySn
	  * @Description: TODO
	  * @author: Yaren Liang 2015年5月7日
	  * @modify: Yaren Liang 2015年5月7日
	  * @param @param sn
	  * @param @return    
	  * @return TransDto    
	  * @throws
	 */
    public TransDto  queryTransBySn(String sn);
}