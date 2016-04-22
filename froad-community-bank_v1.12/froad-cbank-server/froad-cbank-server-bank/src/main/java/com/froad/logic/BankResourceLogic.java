package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.BankResource;

/**
 * 
 * <p>@Title: BankResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankResourceLogic {


    /**
     * 增加 BankResource
     * @param bankResource
     * @return Long    主键ID
     */
	public Long addBankResource(BankResource bankResource) throws FroadServerException, Exception;



    /**
     * 删除 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	public Boolean deleteBankResource(long id) throws FroadServerException, Exception;



    /**
     * 修改 BankResource
     * @param bankResource
     * @return Boolean    是否成功
     */
	public Boolean updateBankResource(BankResource bankResource) throws FroadServerException, Exception;



    /**
     * 查询 BankResource
     * @param bankResource
     * @return List<BankResource>    结果集合 
     */
	public List<BankResource> findBankResource(BankResource bankResource);



    /**
     * 分页查询 BankResource
     * @param page
     * @param bankResource
     * @return Page    结果集合 
     */
	public Page<BankResource> findBankResourceByPage(Page<BankResource> page, BankResource bankResource);



}