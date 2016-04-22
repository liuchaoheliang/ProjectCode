/**
 * 
 * @Title: ClientMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年7月17日  挪至common包
 */
package com.froad.db.mysql.mapper;


import org.apache.ibatis.annotations.Param;

import com.froad.po.Client;

/**
 * 
 * <p>@Title: ClientMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ClientCommonMapper {

	/**
	 * 查询客户端详情
	 * @param clientId 
	 * @return
	 */
	public Client findClientById(String clientId);
	
	
	public Client findClientByName(@Param("clientName")String clientName);

}