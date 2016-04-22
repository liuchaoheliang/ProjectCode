package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductArea {
    
    private Long areaId;//区id
    private String areaName;//区名称
    
    @JSONField(name="area_id")
    public Long getAreaId() {
        return areaId;
    }
    
    @JSONField(name="area_id")
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    
    @JSONField(name="area_name")
    public String getAreaName() {
        return areaName;
    }
    
    @JSONField(name="area_name")
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
}
