package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductOutlet implements Serializable {
    
    /**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 7574442991426766479L;
	/**
     * 
     */
    
    private String orgCode;//机构代码
    private String outletId;           //门店ID
    private String outletName;    //门店名称
    private String address;//门店地址
    private String outletImage;    //门店图片
    private String phone;//门店电话
    private Long areaId;//区id
    
    @JSONField(name="org_code")
    public String getOrgCode() {
        return orgCode;
    }

    @JSONField(name="org_code")
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    @JSONField(name="outlet_id")
    public String getOutletId() {
        return outletId;
    }
    
    @JSONField(name="outlet_id")
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    
    @JSONField(name="outlet_name")
    public String getOutletName() {
        return outletName;
    }
    
    @JSONField(name="outlet_name")
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
    
    @JSONField(name="address")
    public String getAddress() {
        return address;
    }
    
    @JSONField(name="address")
    public void setAddress(String address) {
        this.address = address;
    }
    
    @JSONField(name="outlet_image")
    public String getOutletImage() {
        return outletImage;
    }

    @JSONField(name="outlet_image")
    public void setOutletImage(String outletImage) {
        this.outletImage = outletImage;
    }
    @JSONField(name="phone")
    public String getPhone() {
        return phone;
    }

    @JSONField(name="phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @JSONField(name="area_id")
    public Long getAreaId() {
        return areaId;
    }

    @JSONField(name="area_id")
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

}
