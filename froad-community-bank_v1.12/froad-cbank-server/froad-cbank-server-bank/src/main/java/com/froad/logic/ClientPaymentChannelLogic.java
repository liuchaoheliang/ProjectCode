package com.froad.logic;


import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.ClientPaymentChannel;

/**
 * 
 * <p>@Title: ClientPaymentChannelLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ClientPaymentChannelLogic {


    /**
     * 增加 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Long    主键ID
     */
	public String addClientPaymentChannel(ClientPaymentChannel clientPaymentChannel) throws FroadServerException, Exception;



    /**
     * 删除 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Boolean    是否成功
     */
	public Boolean deleteClientPaymentChannel(ClientPaymentChannel clientPaymentChannel) throws FroadServerException, Exception;



    /**
     * 修改 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Boolean    是否成功
     */
	public Boolean updateClientPaymentChannel(ClientPaymentChannel clientPaymentChannel) throws FroadServerException, Exception;


    /**
     * 分页查询 ClientPaymentChannel
     * @param page
     * @param clientPaymentChannel
     * @return Page    结果集合 
     */
	public Page<ClientPaymentChannel> findClientPaymentChannelByPage(Page<ClientPaymentChannel> page, ClientPaymentChannel clientPaymentChannel);



}