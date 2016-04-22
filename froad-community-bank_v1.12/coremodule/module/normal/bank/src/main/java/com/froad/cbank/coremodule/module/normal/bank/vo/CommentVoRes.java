package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商品评价信息实体类
 * @author Administrator
 *
 */
public class CommentVoRes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String commentId;        //评价ID
	private String orgName;         //所属机构
	private String orgCode;         //所属机构
	private String productName;     //商品名称
	private String merchantName;    //商户名称
	private String comment;         //评价内容
	private String commentDate;     //评论时间
	private String startLevel;       //评论星级
	private String answerState;    //回复状态
	private String type;           //商品评价类型
	private String orderType;        //商品交易类型
	private String outletName;      //门店名称
	
	
	
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
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
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getStartLevel() {
		return startLevel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getStarLevel() {
		return startLevel;
	}
	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}
	public String getAnswerState() {
		return answerState;
	}
	public void setAnswerState(String answerState) {
		this.answerState = answerState;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
