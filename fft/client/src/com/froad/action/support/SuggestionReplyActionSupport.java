package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.froad.client.suggestionReply.SuggestionReply;
import com.froad.client.suggestionReply.SuggestionReplyService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 意见建议回复 client service  ActionSupport
 */
public class SuggestionReplyActionSupport {
	private static Logger logger = Logger.getLogger(SuggestionReplyActionSupport.class);
	private SuggestionReplyService suggestionReplyService;

	/**
	 * 分页查找  意见和建议回复
	 * @param pager
	 * @return
	 */
	public SuggestionReply getSuggestionReplyListByPager(SuggestionReply suggestionReply){
		try{
			suggestionReply=suggestionReplyService.getSuggestionReplyListByPager(suggestionReply);
			if(suggestionReply==null){
				suggestionReply = new SuggestionReply();
			}
		}
		catch(Exception e){
			logger.error("SuggestionReplyActionSupport.getSuggestionReplyListByPager分页查找意见和建议回复异常,ID："+suggestionReply.getId());
		}
		return suggestionReply;
	}
	
	/**
	 * 查询意见和建议回复信息
	 * @param SuggestionReply
	 * @return SuggestionReply
	 */
	public List<SuggestionReply> getSuggestionReplyInfo(SuggestionReply suggestion){
		List<SuggestionReply> list= null;
		try{
			list = suggestionReplyService.getSuggestionReplyBySuggestionId(suggestion.getSuggestionId());
			if(list == null){
				list = new ArrayList<SuggestionReply>();
			}
		}catch(Exception e){
			logger.error("SuggestionReplyActionSupport.getSuggestionReplyInfo查询意见和建议回复异常,ID："+suggestion.getId());
		}
		return list;
	}
	
	public SuggestionReplyService getSuggestionReplyService() {
		return suggestionReplyService;
	}
	public void setSuggestionReplyService(
			SuggestionReplyService suggestionReplyService) {
		this.suggestionReplyService = suggestionReplyService;
	}
	
}
