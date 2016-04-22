package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.common.mongo.OutletCommentMongo;

public class PointInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int starLevel; // required
	public String commentDescription; // required
	
	public int getStarLevel() {
		return starLevel;
	}
	@JSONField(name=OutletCommentMongo.MONGO_KEY_SORG_CODE)
	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}
	public String getCommentDescription() {
		return commentDescription;
	}
	@JSONField(name=OutletCommentMongo.MONGO_KEY_COMMENT_DESCRIPTION)
	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

}
