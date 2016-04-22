package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Suggestion;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 * 意见和建议
 */
public interface SuggestionDao {
	/**
	  * 方法描述：添加意见建议
	  * @param: Suggestion
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addSuggestion(Suggestion suggestion);

	
	/**
	  * 方法描述：按主键更新意见建议信息
	  * @param: Suggestion
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(Suggestion suggestion);

	
	/**
	  * 方法描述：查询意见建议
	  * @param: id
	  * @return: Suggestion
	  * @version: 1.0
	  */
	Suggestion selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除意见建议
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 意见建议
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询意见建议
	  * @param: userId
	  * @return: List<Suggestion>
	  * @version: 1.0
	  */
	List<Suggestion> getSuggestionByUserId(String userId);
	
	/**
	  * 方法描述：查询意见建议
	  * @param: Suggestion
	  * @return: List<Suggestion>
	  * @version: 1.0
	  */
	List<Suggestion> getSuggestions(Suggestion suggestion);
	
	/**
	 * 分页查询意见建议信息
	 * @param Suggestion
	 * @return Suggestion
	 */
	public Suggestion getSuggestionListByPager(Suggestion suggestion);
}
