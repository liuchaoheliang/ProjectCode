package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;

public class RecvInfo implements java.io.Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 792893904460210361L;

    public RecvInfo() {
        super();
    }

    public RecvInfo(String recvId, String consignee, String address, String phone, String isDefault, Long areaId) {
        super();
        this.recvId = recvId;
        this.consignee = consignee;
        this.address = address;
        this.phone = phone;
        this.isDefault = isDefault;
        this.areaId = areaId;
    }

    @JSONField(name = "consignee")
    private String consignee;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "phone")
    private String phone;
    @JSONField(name = "isdefault")
    private String isDefault;
    @JSONField(name = "recv_id")
    private String recvId;

    @JSONField(name = "area_id")
    private Long   areaId;

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

}
