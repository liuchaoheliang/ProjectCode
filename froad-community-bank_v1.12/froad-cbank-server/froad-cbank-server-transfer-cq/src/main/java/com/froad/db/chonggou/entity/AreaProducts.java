package com.froad.db.chonggou.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class AreaProducts implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3554290893221364668L;
    
    private String id;
    private String clientId;
    private Long areaId;
    private Long areaParentId;
    private List<String> productIds;
    
    @JSONField(name="_id")
    public String getId() {
        return id;
    }
    
    @JSONField(name="_id")
    public void setId(String id) {
        this.id = id;
    }
    
    @JSONField(name="client_id")
    public String getClientId() {
        return clientId;
    }

    @JSONField(name="client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    @JSONField(name="area_id")
    public Long getAreaId() {
        return areaId;
    }

    @JSONField(name="area_id")
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @JSONField(name="product_ids")
    public List<String> getProductIds() {
        return productIds;
    }

    @JSONField(name="product_ids")
    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
    @JSONField(name="area_parent_id")
    public Long getAreaParentId() {
        return areaParentId;
    }

    @JSONField(name="area_parent_id")
    public void setAreaParentId(Long areaParentId) {
        this.areaParentId = areaParentId;
    }

}
