package com.froad.logic;

import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.po.DictionaryCategory;
import com.froad.thrift.vo.ExportResultRes;

/**
 * 
 * <p>@Title: DictionaryCategoryLogic.java</p>
 * <p>Description: 描述 </p> 字典类别Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryCategoryLogic {


    /**
     * 增加 DictionaryCategory
     * @param dictionaryCategory
     * @return Long    主键ID
     */
	public Long addDictionaryCategory(DictionaryCategory dictionaryCategory) throws FroadServerException, Exception;


	/**
     * 修改DictionaryCategory
     * @param dictionaryCategory
     * @return Boolean    是否成功
     */
	public Boolean updateDictionaryCategory(DictionaryCategory dictionaryCategory) throws FroadServerException, Exception;

    /**
     * 删除 DictionaryCategory
     * @param dictionaryCategory
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryCategory(Long id) throws FroadServerException, Exception;


    /**
     * 查询 DictionaryCategory
     * @param dictionaryCategory
     * @return List<DictionaryCategory>    结果集合 
     */
	public List<DictionaryCategory> findDictionaryCategory(DictionaryCategory dictionaryCategory) throws Exception;

	/**
     * 查询类别id的子类别信息
     * @param id 类别id
     * @return List<DictionaryCategory>    结果集合 
     */
	public List<DictionaryCategory> findChildList(Long id) throws Exception;
	
	/**
	 * 导出脚本(导出该类别下所有的字典、条目信息)
	 * 
	 * findDictionaryCategoryExport:导出脚本
	 * @author froad-ll
	 * 2015年11月30日 下午1:47:37
	 * @param type 1-类别Id 2-字典Id
	 * @param ids 类别id或字典Id集合
	 * @return ExportResultRes
	 */
	public ExportResultRes findDictionaryCategoryExport(Integer type,List<Long> ids) throws Exception;

}