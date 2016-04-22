package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.SuggestionReply;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 */
@WebService
public interface SuggestionReplyService {
	/**
	  * 方法描述：添加意见建议回复
	  * @param: SuggestionReply
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addSuggestionReply(SuggestionReply suggestionReply);

	
	/**
	  * 方法描述：按主键更新意见建议回复信息
	  * @param: SuggestionReply
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(SuggestionReply suggestionReply);

	
	/**
	  * 方法描述：查询意见建议回复
	  * @param: id
	  * @return: SuggestionReply
	  * @version: 1.0
	  */
	SuggestionReply selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除意见建议回复
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 意见建议回复
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
	/**
	  * 方法描述：查询意见建议回复
	  * @param: suggestionId
	  * @return: List<SuggestionReply>
	  * @version: 1.0
	  */
	List<SuggestionReply> getSuggestionReplyBySuggestionId(String suggestionId);
	
	/**
	  * 方法描述：查询意见建议回复
	  * @param: SuggestionReply
	  * @return: List<SuggestionReply>
	  * @version: 1.0
	  */
	List<SuggestionReply> getSuggestionReplys(SuggestionReply suggestionReply);
	
	/**
	 * 分页查询意见建议回复信息
	 * @param SuggestionReply
	 * @return SuggestionReply
	 */
	public SuggestionReply getSuggestionReplyListByPager(SuggestionReply suggestionReply);
}
