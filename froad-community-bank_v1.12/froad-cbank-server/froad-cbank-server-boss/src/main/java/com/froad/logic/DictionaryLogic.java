package com.froad.logic;


import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Dictionary;

/**
 * 
 * <p>@Title: DictionaryLogic.java</p>
 * <p>Description: 描述 </p> 字典Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryLogic {


    /**
     * 增加 Dictionary
     * @param dictionary
     * @return Long    主键ID
     */
	public Long addDictionary(Dictionary dictionary) throws FroadServerException, Exception;


	/**
     * 批量删除 Dictionary
     * @param dictionary
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryBatch(List<Long> dicIds) throws FroadServerException, Exception;
	
	
	/**
     * 修改 Dictionary
     * @param dictionary
     * @return Boolean    是否成功
     */
	public Boolean updateDictionary(Dictionary dictionary) throws FroadServerException, Exception;

	
	
	/**
     * 查询 Dictionary
     * @param id 字典Id
     * @return dictionary对象  
     */
	public Dictionary findDictionaryById(Long id) throws Exception;

	
	/**
     * 分页查询 Dictionary
     * @param page
     * @param dictionary
     * type 1-当前类别下字典查询  2-当前类别下所有子类别中字典查询
     * @return Page    结果集合 
     */
	public Page<Dictionary> findDictionaryByPage(Page<Dictionary> page, Dictionary dictionary,int type) throws Exception;



}