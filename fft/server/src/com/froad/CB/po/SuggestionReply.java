package com.froad.CB.po;

import com.froad.CB.common.Pager;

/** 
 * @author FQ 
 * @date 2013-2-4 上午11:49:47
 * @version 1.0
 * 意见和建议 回复
 */
public class SuggestionReply extends Pager{
	private Integer id;
	private String suggestionId;//建议ID
	private String title;//标题
	private String content;//内容
	private String replyAccount;//回复人
	
	private String state;//状态
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private	String remark;//备注
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSuggestionId() {
		return suggestionId;
	}
	public void setSuggestionId(String suggestionId) {
		this.suggestionId = suggestionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplyAccount() {
		return replyAccount;
	}
	public void setReplyAccount(String replyAccount) {
		this.replyAccount = replyAccount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
