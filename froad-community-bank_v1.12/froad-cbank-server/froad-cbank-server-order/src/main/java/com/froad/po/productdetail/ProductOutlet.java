package com.froad.po.productdetail;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

@Deprecated
public class ProductOutlet implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2780795577646880680L;
    
    private String outletId;          	//门店ID
    private String outletName;    		//门店名称
    private String address;				//门店地址
    private String outletImage;    		//门店图片
    
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

}
