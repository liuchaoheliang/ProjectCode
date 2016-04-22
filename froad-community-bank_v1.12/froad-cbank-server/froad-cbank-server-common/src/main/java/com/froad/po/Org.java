package com.froad.po;

import com.froad.util.JSonUtil;



/**
 * 机构信息po
 * CbOrg entity. 
 */

public class Org  implements java.io.Serializable {


    // Fields    

     /**
	 * 序列化
	 */
	private static final long serialVersionUID = -1909369496395238848L;
	private Long id;				//自增主键ID
     private String clientId;			//客户端ID
     private String bankType;		//银行类型
     private String orgCode;		//机构代码
     private String orgName;		//机构名
     private String provinceAgency;	//一级org_code
     private String cityAgency;		//二级org_code
     private String countyAgency;	//三级org_code
     private String phone;			//联系电话
     private String merchantId;		//机构对应商户ID
     private String outletId;			//机构对应门店ID
     private Long areaId;			//机构地区
     private String orgLevel;		//机构级别1-2-3-4-
     private Boolean needReview;	//是否需要双人审核
     private Boolean orgType;		//0-部门机构，1-业务机构
     private Boolean isEnable;		//是否禁用 0-禁用 1-启用
     private Integer limit;         //机构名称模糊查询用户限制查询条数


    // Constructors
    public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

    public Org() {
    }

	/** minimal constructor */
    public Org(String bankType, String orgCode, String orgName, String merchantId, String outletId, Long areaId, String orgLevel, Boolean needReview,Boolean orgType,Boolean isEnable) {
        this.bankType = bankType;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.merchantId = merchantId;
        this.outletId = outletId;
        this.areaId = areaId;
        this.orgLevel = orgLevel;
        this.needReview=needReview;
        this.orgType = orgType;
        this.isEnable=isEnable;
    }
    
    /** full constructor */
    public Org(String clientId, String bankType, String orgCode, String orgName, String provinceAgency, String cityAgency, String countyAgency, String phone, String merchantId, String outletId, Long areaId, String orgLevel, Boolean needReview,Boolean orgType,Boolean isEnable) {
        this.clientId = clientId;
        this.bankType = bankType;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.provinceAgency = provinceAgency;
        this.cityAgency = cityAgency;
        this.countyAgency = countyAgency;
        this.phone = phone;
        this.merchantId = merchantId;
        this.outletId = outletId;
        this.areaId = areaId;
        this.orgLevel = orgLevel;
        this.needReview=needReview;
        this.orgType = orgType;
        this.isEnable=isEnable;
    }

   
    // Property accessors

	public Boolean getNeedReview() {
		return needReview;
	}

	public void setNeedReview(Boolean needReview) {
		this.needReview = needReview;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}
	
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getClientId() {
        return this.clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    

    public String getBankType() {
        return this.bankType;
    }
    
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }
    
    

    public String getOrgCode() {
        return this.orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    

    public String getOrgName() {
        return this.orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    

    public String getProvinceAgency() {
        return this.provinceAgency;
    }
    
    public void setProvinceAgency(String provinceAgency) {
        this.provinceAgency = provinceAgency;
    }
    
    

    public String getCityAgency() {
        return this.cityAgency;
    }
    
    public void setCityAgency(String cityAgency) {
        this.cityAgency = cityAgency;
    }
    
    

    public String getCountyAgency() {
        return this.countyAgency;
    }
    
    public void setCountyAgency(String countyAgency) {
        this.countyAgency = countyAgency;
    }
    
    

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public String getMerchantId() {
        return this.merchantId;
    }
    
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    

    public String getOutletId() {
        return this.outletId;
    }
    
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    
    

    public Long getAreaId() {
        return this.areaId;
    }
    
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    
    

    public String getOrgLevel() {
        return this.orgLevel;
    }
    
    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }
    

    public Boolean getOrgType() {
        return this.orgType;
    }
    
    public void setOrgType(Boolean orgType) {
        this.orgType = orgType;
    }
   
    public String toString(){
    	return JSonUtil.toJSonString(this);
    }

}