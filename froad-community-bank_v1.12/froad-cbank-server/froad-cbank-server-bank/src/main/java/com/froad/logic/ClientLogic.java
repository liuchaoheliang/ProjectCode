package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Client;

/**
 * 
 * <p>@Title: ClientLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ClientLogic {


    /**
     * 增加 Client
     * @param client
     * @return Long    主键ID
     */
	public String addClient(Client client) throws FroadServerException, Exception;



    /**
     * 删除 Client
     * @param client
     * @return Boolean    是否成功
     */
	public Boolean deleteClient(Client client) throws FroadServerException, Exception;



    /**
     * 修改 Client
     * @param client
     * @return Boolean    是否成功
     */
	public Boolean updateClient(Client client) throws FroadServerException, Exception;


    /**
     * 查询 Client
     * @param client
     * @return List<Client>    结果集合 
     */
	public List<Client> findClient(Client client);



    /**
     * 分页查询 Client
     * @param page
     * @param client
     * @return Page    结果集合 
     */
	public Page<Client> findClientByPage(Page<Client> page, Client client);



}