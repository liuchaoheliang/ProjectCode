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
 * @Title: ClientMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BankAccessDto;
import com.froad.po.Client;

/**
 * 
 * <p>@Title: ClientMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ClientMapper {

	/**
	 * 获取boss设置过client配置信息和没有设置过client配置信息的集合列表。type：1设置过；2：没有设置过
	 * findExistBossClientList:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年9月18日 下午1:55:01
	 * @param type
	 * @return
	 *
	 */
   public List<Client> findExistBossClientList(@Param("type") String type);
   
   /**
    * 获取已配置的boss客户端信息
    * findClientList:(这里用一句话描述这个方法的作用).
    * @author zhouzhiwei
    * 2015年9月18日 下午3:16:54
    * @param clientId
    * @return
    *
    */
   public List<BankAccessDto> findClientList(@Param("clientId")String clientId,@Param("pageNumber") int pageNumber, @Param("pageSize")int pageSize );
   
   /**
    * 
    * findClientListCount:(这里用一句话描述这个方法的作用).
    *
    * @author zhouzhiwei
    * 2015年9月25日 下午2:04:32
    * @param clientId
    * @return
    *
    */
   public int findClientListCount(@Param("clientId")String clientId);
   
   
   public List<Client> findClient(Client client);


}