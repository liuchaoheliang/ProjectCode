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
 * @Title: ClientPaymentChannelMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ClientPaymentChannel;

/**
 * 
 * <p>@Title: ClientPaymentChannelMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ClientPaymentChannelMapper {


    /**
     * 增加 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Long    主键ID
     */
	public Long addClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);



    /**
     * 批量增加 ClientPaymentChannel
     * @param List<ClientPaymentChannel>
     * @return Boolean    是否成功
     */
	public Boolean addClientPaymentChannelByBatch(List<ClientPaymentChannel> clientPaymentChannelList);



    /**
     * 删除 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Boolean    是否成功
     */
	public Boolean deleteClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);



    /**
     * 修改 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Boolean    是否成功
     */
	public Boolean updateClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);



    /**
     * 查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannel>    返回结果集
     */
	public List<ClientPaymentChannel> findClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);



    /**
     * 分页查询 ClientPaymentChannel
     * @param page 
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannel>    返回分页查询结果集
     */
	public List<ClientPaymentChannel> findByPage(Page page, @Param("clientPaymentChannel")ClientPaymentChannel clientPaymentChannel);



}