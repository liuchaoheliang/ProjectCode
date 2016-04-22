package com.froad.db.mysql.mapper;

import java.util.List;
import com.froad.po.DictionaryCategory;

/**
 * 
 * <p>@Title: DictionaryCategoryMapper.java</p>
 * <p>Description: 描述 </p> 字典类别Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryCategoryMapper {


    /**
     * 增加 DictionaryCategory
     * @param dictionaryCategory
     * @return Long    主键ID
     */
	public Long addDictionaryCategory(DictionaryCategory dictionaryCategory);



    /**
     * 删除 DictionaryCategory
     * @param dictionaryCategory
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryCategory(Long id);

	
	/**
     * 修改 DictionaryCategory
     * @param dictionaryCategory
     * @return Boolean    是否成功
     */
	public Boolean updateDictionaryCategory(DictionaryCategory dictionaryCategory);
	
	/**
     * 查询单个 DictionaryCategory
     * @param dictionaryCategory
     * @return DictionaryCategory对象
     */
	public DictionaryCategory findOnlyDictionaryCategory(DictionaryCategory dictionaryCategory);
	
	
	/**
     * 查询类别id的子类别信息
     * @param id
     * @return DictionaryCategory集合对象
     */
	public List<DictionaryCategory> findChildList(Long id);

	

    /**
     * 查询 DictionaryCategory
     * @param dictionaryCategory
     * @return List<DictionaryCategory>    返回结果集
     */
	public List<DictionaryCategory> findDictionaryCategory(DictionaryCategory dictionaryCategory);



}