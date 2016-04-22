package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.ClientPaymentChannel;

/**
 * 
 * <p>@Title: ClientPaymentChannelCommonMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日  挪至Common包
 */
public interface ClientPaymentChannelCommonMapper {


    /**
     * 查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannel>    返回结果集
     */
	public List<ClientPaymentChannel> findClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);


}