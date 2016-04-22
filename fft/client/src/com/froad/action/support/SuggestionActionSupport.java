package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.suggestion.Suggestion;
import com.froad.client.suggestion.SuggestionService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 意见建议 client service  ActionSupport
 */
public class SuggestionActionSupport {
	private static Logger logger = Logger.getLogger(SuggestionActionSupport.class);
	private SuggestionService suggestionService;
	
	/**
	 * 查询意见和建议信息
	 * @param Suggestion
	 * @return Suggestion
	 */
	public Suggestion getSuggestionInfo(Suggestion suggestion){
		Suggestion suggest= null;
		try{
			suggest = suggestionService.selectById(suggestion.getId());
			if(suggest == null){
				suggest = suggestion;
			}
		}catch(Exception e){
			logger.error("SuggestionActionSupport.getSuggestionInfo查询意见和建议异常,ID："+suggestion.getId());
		}
		return suggest;
	}
	
	/**
	 * 增加意见和建议
	 * @param mp
	 * @return
	 */
	public Integer addSuggestion(Suggestion mp){		
		logger.info("增加相册相片信息");
		Integer num = null;
		try {
			num = suggestionService.addSuggestion(mp);
			if(num == null){
				num = new Integer(0);
			}
		} catch (Exception e) {
			logger.error("SuggestionActionSupport.addSuggestion增加意见和建议异常 userID："+mp.getUserId(), e);
			num = new Integer(0);
		}
		return num;
	}
	
	/**
	 * 分页查找  意见和建议
	 * @param pager
	 * @return
	 */
	public Suggestion getSuggestionListByPager(Suggestion suggestion){
		try{
			suggestion=suggestionService.getSuggestionListByPager(suggestion);
			if(suggestion==null){
				suggestion = new Suggestion();
			}
		}
		catch(Exception e){
			logger.error("SuggestionActionSupport.getSuggestionListByPager分页查找意见和建议异常,用户ID："+suggestion.getUserId());
		}
		return suggestion;
	}
	
	public SuggestionService getSuggestionService() {
		return suggestionService;
	}

	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}
	
	
	
}
