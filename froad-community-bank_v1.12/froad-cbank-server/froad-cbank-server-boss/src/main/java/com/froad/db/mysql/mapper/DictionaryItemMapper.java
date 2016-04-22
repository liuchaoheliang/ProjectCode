package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.DictionaryItem;

/**
 * 
 * <p>@Title: DictionaryItemMapper.java</p>
 * <p>Description: 描述 </p> 字典条目Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryItemMapper {


    /**
     * 增加 DictionaryItem
     * @param dictionaryItem
     * @return Long    主键ID
     */
	public Long addDictionaryItem(DictionaryItem dictionaryItem);



    /**
     * 批量删除 DictionaryItem
     * @param itemIds
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryItemBatch(@Param("itemIdList") List<Long> itemIds);

	
	/**
     * 批量删除该字典下多个条目
     * @param dicIds
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryItemBatchByDicId(@Param("dicIdList") List<Long> dicIds);

	
	
	/**
     * 修改 DictionaryItem
     * @param dictionaryItem
     * @return Boolean    是否成功
     */
	public Boolean updateDictionaryItem(DictionaryItem dictionaryItem);
	
	
	/**
     * 查询单个DictionaryItem
     * @param id 主键id
     * @return DictionaryItem 字典条目对象
     */
	public DictionaryItem findDictionaryItemById(Long id);
	
	
	/**
     * 查询 DictionaryItem
     * @param dictionaryItem
     * @return List<dictionaryItem>    返回结果集
     */
	public List<DictionaryItem> findDictionaryItem(DictionaryItem dictionaryItem);

	
	/**
     * 分页查询 DictionaryItem
     * @param page 
     * @param dictionaryItem
     * @return List<DictionaryItem>    返回分页查询结果集
     */
	public List<DictionaryItem> findByPage(Page page, @Param("dictionaryItem")DictionaryItem DictionaryItem);



}