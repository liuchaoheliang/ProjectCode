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
     * 增加 Client
     * @param client
     * @return Long    主键ID
     */
	public Long addClient(Client client);



    /**
     * 批量增加 Client
     * @param List<Client>
     * @return Boolean    是否成功
     */
	public Boolean addClientByBatch(List<Client> clientList);



    /**
     * 删除 Client
     * @param client
     * @return Boolean    是否成功
     */
	public Boolean deleteClient(Client client);



    /**
     * 修改 Client
     * @param client
     * @return Boolean    是否成功
     */
	public Boolean updateClient(Client client);



    /**
     * 查询 Client
     * @param client
     * @return List<Client>    返回结果集
     */
	public List<Client> findClient(Client client);



    /**
     * 分页查询 Client
     * @param page 
     * @param client
     * @return List<Client>    返回分页查询结果集
     */
	public List<Client> findByPage(Page page, @Param("client")Client client);



}