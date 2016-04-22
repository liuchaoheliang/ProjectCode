package com.froad.po;
/**
 *   org_code po
  * @ClassName: BankOrgCode
  * @Description: TODO
  * @author Yaren Liang 2015年4月10日
  * @modify Yaren Liang 2015年4月10日
 */
public class BankOrgCode {
	private Long id;
	private String clientId; 
	private String orgCode;
	private String provinceAgency;
	private String cityAgency;
	private String countyAgency;
	private String orgLevel;
	private String orgName;
	private String merchantId;
	private String outletId;
	private Long areaId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public String getProvinceAgency() {
		return provinceAgency;
	}
	
	public void setProvinceAgency(String provinceAgency) {
		this.provinceAgency = provinceAgency;
	}
	
	public String getCityAgency() {
		return cityAgency;
	}
	
	public void setCityAgency(String cityAgency) {
		this.cityAgency = cityAgency;
	}
	
	public String getCountyAgency() {
		return countyAgency;
	}
	
	public void setCountyAgency(String countyAgency) {
		this.countyAgency = countyAgency;
	}
	
	public String getOrgLevel() {
		return orgLevel;
	}
	
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	
	public String getOrgName() {
        return orgName;
    }
	
    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
	
}

