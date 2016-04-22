package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;


public class Product_Comment_Replay_Req extends BasePojo {
	/**
	 * 评论id
	 */
	@NotEmpty(value="评论id不能为空")
	private String commentId; 
	/**
	 * 商品Id
	 */
	private String productId; 
	/**
	 * 回复评价内容
	 */
	@NotEmpty(value="回复不能为空")
	private String recomment;
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRecomment() {
		return recomment;
	}
	public void setRecomment(String recomment) {
		this.recomment = recomment;
	} 

	

}
