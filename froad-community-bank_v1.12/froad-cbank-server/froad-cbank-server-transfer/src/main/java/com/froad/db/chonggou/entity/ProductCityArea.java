package com.froad.db.chonggou.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductCityArea {
    
    private Long cityId;//城市id
    private String cityName;//城市名称
    private List<ProductArea> countys;//城市下的区
    
    @JSONField(name="city_id")
    public Long getCityId() {
        return cityId;
    }
    
    @JSONField(name="city_id")
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    @JSONField(name="city_name")
    public String getCityName() {
        return cityName;
    }
    
    @JSONField(name="city_name")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    @JSONField(name="countys")
    public List<ProductArea> getCountys() {
        return countys;
    }
    
    @JSONField(name="countys")
    public void setCountys(List<ProductArea> countys) {
        this.countys = countys;
    }

}
