package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductBuyLimit implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7655687109840079889L;
    
    /**
    * 限购活动对应的id
    */
   private long id;
   /**
    * 最小购买数量
    */
   private int min;
    /**
     * 最大购买数量
     */
    private int max;
    /**
     * vip最大购买数量
     */
    private int maxVip;
    /**
     * 贴膜卡最大购买数量
     */
    private int maxCard;
    /**
     * 限制开始时间
     */
    private long startTime;
    /**
     * 限制结束时间
     */
    private long endTime;
    
    @JSONField(name = "id")
    public long getId() {
        return id;
    }

    @JSONField(name = "id")
    public void setId(long id) {
        this.id = id;
    }
    
    @JSONField(name = "min")
    public int getMin() {
    	return min;
    }

    @JSONField(name = "min")
    public void setMin(int min) {
    	this.min = min;
    }
    
    @JSONField(name = "max")
    public int getMax() {
        return max;
    }
    
    @JSONField(name = "max")
    public void setMax(int max) {
        this.max = max;
    }
    
    @JSONField(name = "max_vip")
    public int getMaxVip() {
        return maxVip;
    }
    
    @JSONField(name = "max_vip")
    public void setMaxVip(int maxVip) {
        this.maxVip = maxVip;
    }
    
    @JSONField(name = "max_card")
    public int getMaxCard() {
        return maxCard;
    }
    
    @JSONField(name = "max_card")
    public void setMaxCard(int maxCard) {
        this.maxCard = maxCard;
    }
    
    @JSONField(name = "start_time")
    public long getStartTime() {
        return startTime;
    }
    
    @JSONField(name = "start_time")
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    @JSONField(name = "end_time")
    public long getEndTime() {
        return endTime;
    }
    
    @JSONField(name = "end_time")
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
}
