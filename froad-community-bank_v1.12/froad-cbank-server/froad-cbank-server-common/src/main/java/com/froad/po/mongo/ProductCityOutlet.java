package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductCityOutlet {
    
    private Long cityId;//城市id
    private String cityName;//城市名称
    private List<ProductOutlet> orgOutlets;//该城市下的机构门店

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
    
    @JSONField(name="org_outlets")
    public List<ProductOutlet> getOrgOutlets() {
        return orgOutlets;
    }

    @JSONField(name="org_outlets")
    public void setOrgOutlets(List<ProductOutlet> orgOutlets) {
        this.orgOutlets = orgOutlets;
    }

}
