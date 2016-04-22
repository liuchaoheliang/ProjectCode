/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: OutletInfo.java
 * @Package com.froad.po.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.po.mongo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>
 * Title: OutletInfo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年3月21日 上午10:11:47
 */

public class OutletDetail implements java.io.Serializable {

    /**
     * @Fields serialVersionUID : TODO
     */

    private static final long  serialVersionUID = -7370245180138018030L;
    @JSONField(name = "_id", serialize = true, deserialize = true)
    private String             id;

    @JSONField(name = "create_time", serialize = true, deserialize = true)
    private Long               createTime;

    @JSONField(name = "client_id", serialize = true, deserialize = true)
    private String             clientId;

    @JSONField(name = "merchant_id", serialize = true, deserialize = true)
    private String             merchantId;

    @JSONField(name = "merchant_name", serialize = true, deserialize = true)
    private String             merchantName;

    @JSONField(name = "area_id", serialize = true, deserialize = true)
    private Long               areaId;

    @JSONField(name = "tree_path_name", serialize = true, deserialize = true)
    private String             treePathName;

    @JSONField(name = "is_enable", serialize = true, deserialize = true)
    private Boolean            isEnable;

    @JSONField(name = "outlet_name", serialize = true, deserialize = true)
    private String             outletName;

    @JSONField(name = "outlet_fullname", serialize = true, deserialize = true)
    private String             outletFullName;

    @JSONField(name = "category_info", serialize = true, deserialize = true)
    private List<CategoryInfo> categoryInfo;

    @JSONField(name = "type_info", serialize = true, deserialize = true)
    private List<TypeInfo>     typeInfo;

    @JSONField(name = "outlet_status", serialize = true, deserialize = true)
    private Boolean            outletStatus;

    @JSONField(name = "address", serialize = true, deserialize = true)
    private String             address;

    @JSONField(name = "phone", serialize = true, deserialize = true)
    private String             phone;

    @JSONField(name = "description", serialize = true, deserialize = true)
    private String             description;

    @JSONField(name = "prefer_details", serialize = true, deserialize = true)
    private String             preferDetails;

    private double             dis;

    public OutletDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getTreePathName() {
        return treePathName;
    }

    public void setTreePathName(String treePathName) {
        this.treePathName = treePathName;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletFullName() {
        return outletFullName;
    }

    public void setOutletFullName(String outletFullName) {
        this.outletFullName = outletFullName;
    }

    public List<CategoryInfo> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(List<CategoryInfo> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public List<TypeInfo> getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(List<TypeInfo> typeInfo) {
        this.typeInfo = typeInfo;
    }

    public Boolean getOutletStatus() {
        return outletStatus;
    }

    public void setOutletStatus(Boolean outletStatus) {
        this.outletStatus = outletStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreferDetails() {
        return preferDetails;
    }

    public void setPreferDetails(String preferDetails) {
        this.preferDetails = preferDetails;
    }

    public double getDis() {
        return dis;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }

}