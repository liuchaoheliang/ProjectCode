package com.froad.cbank.coremodule.normal.boss.pojo.product;

import java.io.Serializable;

public class ProductCommentVoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commentId;// 评价Id
	private String clientId;// 客户端
	private String productName;// 商品名称
	private String userName;// 用户名
	private String isPass;// 是否通过
	private String startLevel;// 星级
	private String isTop;// 是否置顶
	private String createTime;// 创建时间
	private String content;// 评价内容

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
