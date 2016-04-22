package com.froad.action.web.suggestion;

import java.util.ArrayList;
import java.util.List;

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
public class SuggestionAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -2779835082780292664L;
	private UserActionSupport userActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private SuggestionActionSupport suggestionActionSupport;
	private SuggestionReplyActionSupport suggestionReplyActionSupport;
	private User user;
	private Suggestion pager;
	private Suggestion suggestion;
	private SuggestionReply suggestionReply;
	private List<SuggestionReply> suggestionReplyList = new ArrayList<SuggestionReply>();
	private String message;
	/**
	 * 进入意见与建议页面
	 * @return
	 */
	public String toSuggestion(){
		if(suggestion == null){
			suggestion = new Suggestion();
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return "failse";
		}else{
			user = userActionSupport.queryUserAllByUserID(userId);
		}
		return "success";
	}
	
	/**
	 * 添加意见和建议
	 * @return
	 */
	public String addSuggestion(){
		//添加意见与建议
		if(suggestion == null || Assert.empty(suggestion.getContent())){
			suggestion = new Suggestion();
			return "failse";
		}
		if(Assert.empty(suggestion.getUserId()) || Assert.empty(suggestion.getUserMobile())
				|| Assert.empty(suggestion.getUserName())){
			user = userActionSupport.queryUserAllByUserID((String)getSession(MallCommand.USER_ID));
			suggestion.setUserId(user.getUserID());
			suggestion.setUserEmail(user.getEmail());
			suggestion.setUserName(user.getUsername());
			suggestion.setUserMobile(user.getMobilephone());
		}
		suggestion.setState("30");
		suggestion.setIsReply("0");//默认尚未回复
		suggestionActionSupport.addSuggestion(suggestion);
		return getSuggestionList();
	}
	
	/**
	 * 查询意见与建议信息
	 * @return
	 */
	public String getSuggestionInfo(){
		//查询意见与建议信息
		if(suggestion == null || Assert.empty(suggestion.getId())){
			return getSuggestionList();
		}
		if(Assert.empty(suggestion.getUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			suggestion.setUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(suggestion.getUserId());
			if(user!=null && !Assert.empty(user.getMobilephone())){
				String xingStr = user.getMobilephone().trim().substring(3, 8);
				user.setMobilephone(user.getMobilephone().replace(xingStr, "*****"));
			}
		}

		suggestion = suggestionActionSupport.getSuggestionInfo(suggestion);
		if(suggestion == null){
			suggestion = new Suggestion();
		}
		return "success";
	}
	
	/**
	 * 查询意见与建议回复信息
	 * @return
	 */
	public String getSuggestionReplyInfo(){
		//查询意见与建议信息
		if(suggestion == null || Assert.empty(suggestion.getId())){
			return getSuggestionList();
		}
		if(Assert.empty(suggestion.getUserId())){
			String userId = (String)getSession(MallCommand.USER_ID);
			suggestion.setUserId(userId);
		}else{
			user = userActionSupport.queryUserAllByUserID(suggestion.getUserId());
		}
		if(suggestionReply == null){
			suggestionReply = new SuggestionReply();
		}
		suggestionReply.setSuggestionId(String.valueOf(suggestion.getId()));
		suggestionReplyList = suggestionReplyActionSupport.getSuggestionReplyInfo(suggestionReply);
		if(suggestion == null){
			suggestion = new Suggestion();
		}
		return "success";
	}
	
	/**
	 * 查询意见与建议列表
	 * @return
	 */
	public String getSuggestionList(){
		//查询查询意见与建议列表
		if(pager == null){
			pager = new Suggestion();
		}
		if(user == null){
			user = new User();
		}
		String userId = (String)getSession(MallCommand.USER_ID);
		if(!Assert.empty(userId)){
			pager.setUserId(userId);
			user.setUserID(userId);
		}
		pager.setPageSize(10);
		pager = suggestionActionSupport.getSuggestionListByPager(pager);
		return "successSuggestList";
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

	public Suggestion getPager() {
		return pager;
	}

	public void setPager(Suggestion pager) {
		this.pager = pager;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<SuggestionReply> getSuggestionReplyList() {
		return suggestionReplyList;
	}

	public void setSuggestionReplyList(List<SuggestionReply> suggestionReplyList) {
		this.suggestionReplyList = suggestionReplyList;
	}
	
}
