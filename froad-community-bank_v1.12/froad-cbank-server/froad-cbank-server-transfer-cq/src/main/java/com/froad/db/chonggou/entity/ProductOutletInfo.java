package com.froad.db.chonggou.entity;

import java.util.Date;




/**
 * CbOutlet po. 
 */


public class ProductOutletInfo implements java.io.Serializable {


	/**
     * 
     */
    private static final long serialVersionUID = -2391893084100766988L;
    
    private Long id;
	private Date createTime;
	private String clientId;
	private String merchantId;
	private String outletId;
	private Long areaId;
	private String outletName;
	private String outletFullname;
	private Boolean outletStatus;
	private String address;
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
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
    public String getOutletId() {
        return outletId;
    }
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    public String getOutletName() {
        return outletName;
    }
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
    public String getOutletFullname() {
        return outletFullname;
    }
    public void setOutletFullname(String outletFullname) {
        this.outletFullname = outletFullname;
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



}