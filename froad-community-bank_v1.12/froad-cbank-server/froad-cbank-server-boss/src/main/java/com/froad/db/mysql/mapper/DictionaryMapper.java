package com.froad.db.mysql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.froad.db.mysql.bean.Page;
import com.froad.po.Dictionary;

/**
 * 
 * <p>@Title: DictionaryMapper.java</p>
 * <p>Description: 描述 </p> 字典Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public interface DictionaryMapper {


    /**
     * 增加 Dictionary
     * @param dictionary
     * @return Long    主键ID
     */
	public Long addDictionary(Dictionary dictionary);



    /**
     * 删除 Dictionary
     * @param dicIds
     * @return Boolean    是否成功
     */
	public Boolean deleteDictionaryBatch(@Param("dicIdList") List<Long> dicIds);

	
	/**
     * 修改 Dictionary
     * @param dictionary
     * @return Boolean    是否成功
     */
	public Boolean updateDictionary(Dictionary dictionary);
	
	
	/**
     * 根据主键id查询Dictionary对象
     * @param dictionary
     * @return Dictionary对象
     */
	public Dictionary findDictionaryById(Long id);
	
	
	/**
     * 查询 Dictionary
     * @param dictionary
     * @return List<Dictionary>    返回结果集
     */
	public List<Dictionary> findDictionary(Dictionary dictionary);


    /**
     * 分页查询 Dictionary
     * @param page 
     * @param dictionary
     * @return List<Dictionary>    返回分页查询结果集
     */
	public List<Dictionary> findByPage(Page page, @Param("dictionary")Dictionary dictionary,@Param("childIds") List<Long> childIds);



}