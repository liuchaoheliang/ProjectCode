package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductImageCG implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8306848382205973262L;
    
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片原图地址
     */
    private String source;
    /**
     * 图片大图地址
     */
    private String large;
    /**
     * 图片中图地址
     */
    private String medium;
    /**
     * 图片小图地址
     */
    private String thumbnail;
    
    @JSONField(name = "title")
    public String getTitle() {
        return title;
    }
    
    @JSONField(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }
    
    @JSONField(name = "source")
    public String getSource() {
        return source;
    }
    
    @JSONField(name = "source")
    public void setSource(String source) {
        this.source = source;
    }
    
    @JSONField(name = "large")
    public String getLarge() {
        return large;
    }
    
    @JSONField(name = "large")
    public void setLarge(String large) {
        this.large = large;
    }
    
    @JSONField(name = "medium")
    public String getMedium() {
        return medium;
    }
    
    @JSONField(name = "medium")
    public void setMedium(String medium) {
        this.medium = medium;
    }
    
    @JSONField(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }
    
    @JSONField(name = "thumbnail")
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
