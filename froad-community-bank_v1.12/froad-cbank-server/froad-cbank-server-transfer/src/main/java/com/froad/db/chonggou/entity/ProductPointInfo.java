package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductPointInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3590690565199000958L;
    
    /**
     * 评价星级
     */
    private int starLevel;
    /**
     * 评价内容
     */
    private String commentDescription;
    
    @JSONField(name = "star_level")
    public int getStarLevel() {
        return starLevel;
    }
    
    @JSONField(name = "star_level")
    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }
    
    @JSONField(name = "comment_description")
    public String getCommentDescription() {
        return commentDescription;
    }
    
    @JSONField(name = "comment_description")
    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

}
