package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商品评价信息实体类
 * @author Administrator
 *
 */
public class CommentVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String commentId;        //评价ID
	private String orgName;         //所属网点
	private String productId;       //商品ID
	private String productName;     //商品名称
	private String merchantId;       //商户ID
	private String merchantName;    //商户名称
	private String outletId;        //门店ID
	private String outletName;      //门店名称
	private String comment;         //评价内容
	private String startDate;       //评论开始时间
	private String endDate;         //评论结束时间
	private String commentDate;     //评论时间
	private String commentPerson;   //评论人
	private String startLevel;       //评论星级
	private Boolean answerState;    //回复状态
	private String answer;          //回复
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentPerson() {
		return commentPerson;
	}
	public void setCommentPerson(String commentPerson) {
		this.commentPerson = commentPerson;
	}
	public String getStartLevel() {
		return startLevel;
	}
	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}
	public Boolean getAnswerState() {
		return answerState;
	}
	public void setAnswerState(Boolean answerState) {
		this.answerState = answerState;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
		
	
}
