package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;

/**
 * 商品评价信息实体类
 * @author Administrator
 *
 */
public class CommentVoReq extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String type;             //商品类型
	private String commentId;       //评论ID
	private String loginName;       //登陆名
	private String orgCode;         //所属机构
	private String productId;       //商品ID
	private String productName;     //商品名称
	private String merchantId;      //商户ID
	private String merchantName;    //商户名称
	private String startLevel;       //评论星级
	private String answerState;     //回复状态
	private String answer;          //回复内容
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	public String getStartLevel() {
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
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
