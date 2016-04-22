package com.froad.action.web.suggestion;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.SuggestionActionSupport;
import com.froad.action.support.SuggestionReplyActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.suggestion.Suggestion;
import com.froad.client.suggestionReply.SuggestionReply;
import com.froad.client.user.User;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-12  
 * @version 1.0
 */
public class SuggestionReplyAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -5927597014788457245L;
	private UserActionSupport userActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private SuggestionActionSupport suggestionActionSupport;
	private SuggestionReplyActionSupport suggestionReplyActionSupport;
	private User user;
	private SuggestionReply pager;
	private Suggestion suggestion;
	private SuggestionReply suggestionReply;
	private String message;
	
	/**
	 * 查询意见与建议回复信息
	 * @return
	 */
	public String getSuggestionReplyByPager(){
		//查询意见与建议信息
		if(suggestion == null || Assert.empty(suggestion.getId())){
			return "failse";
		}
		if(Assert.empty(suggestion.getUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			suggestion.setUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(suggestion.getUserId());
		}
		if(pager == null){
			pager = new SuggestionReply();
		}
		suggestion = suggestionActionSupport.getSuggestionInfo(suggestion);
		pager.setSuggestionId(String.valueOf(suggestion.getId()));
		pager.setPageSize(10);
		pager = suggestionReplyActionSupport.getSuggestionReplyListByPager(pager);
		if(suggestion == null){
			suggestion = new Suggestion();
		}
		return "success";
	}
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public SuggestionActionSupport getSuggestionActionSupport() {
		return suggestionActionSupport;
	}
	public void setSuggestionActionSupport(
			SuggestionActionSupport suggestionActionSupport) {
		this.suggestionActionSupport = suggestionActionSupport;
	}
	public SuggestionReplyActionSupport getSuggestionReplyActionSupport() {
		return suggestionReplyActionSupport;
	}
	public void setSuggestionReplyActionSupport(
			SuggestionReplyActionSupport suggestionReplyActionSupport) {
		this.suggestionReplyActionSupport = suggestionReplyActionSupport;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SuggestionReply getPager() {
		return pager;
	}

	public void setPager(SuggestionReply pager) {
		this.pager = pager;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SuggestionReply getSuggestionReply() {
		return suggestionReply;
	}

	public void setSuggestionReply(SuggestionReply suggestionReply) {
		this.suggestionReply = suggestionReply;
	}
	
	
}
