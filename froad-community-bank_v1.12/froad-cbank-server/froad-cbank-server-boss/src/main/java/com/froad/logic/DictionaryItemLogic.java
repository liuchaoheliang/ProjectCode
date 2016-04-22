package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.BankOperator;
import com.froad.po.DictionaryItem;

/**
 * 
 * <p>@Title: DictionaryItemLogic.java</p>
 * <p>Description: 描述 </p> 字典条目Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryItemLogic {


    /**
     * 增加 DictionaryItem
     * @param dictionaryItem
     * @return Long    主键ID
     */
	public Long addDictionaryItem(DictionaryItem dictionaryItem) throws FroadServerException, Exception;


	/**
     * 删除 DictionaryItem
     * @param dictionaryItem
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryItemBatch(List<Long> itemIds) throws FroadServerException, Exception;
	
	
	/**
     * 修改 DictionaryItem
     * @param dictionaryItem
     * @return Boolean    是否成功
     */
	public Boolean updateDictionaryItem(DictionaryItem dictionaryItem) throws FroadServerException, Exception;
	
	

	/**
     * 分页查询 DictionaryItem
     * @param page
     * @param dictionaryItem
     * @return Page    结果集合 
     */
	public Page<DictionaryItem> findDictionaryItemByPage(Page<DictionaryItem> page, DictionaryItem dictionaryItem) throws Exception;



}